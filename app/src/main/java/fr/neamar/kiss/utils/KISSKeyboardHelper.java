package fr.neamar.kiss.utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;

import fr.neamar.kiss.MainActivity;
import fr.neamar.kiss.R;
import fr.neamar.kiss.ui.SearchEditText;

public class KISSKeyboardHelper {
    private final Keyboard keyboardQwerty123;
    private final Keyboard keyboardQwerty4;
    private final Keyboard keyboardNum123;
    private final Keyboard keyboardNum4;

    private final KeyboardView keyboardView123;
    private final KeyboardView keyboardView4;
    private final View externalFavoriteBar;
    private final SearchEditText searchEditText;


    public KISSKeyboardHelper(MainActivity mainActivity) {
        this.keyboardView123 = mainActivity.findViewById(R.id.keyboard123);
        this.keyboardView4 = mainActivity.findViewById(R.id.keyboard4);
        this.externalFavoriteBar = mainActivity.findViewById(R.id.externalFavoriteBar);
        this.searchEditText = mainActivity.findViewById(R.id.searchEditText);

        keyboardQwerty123 = new Keyboard(mainActivity, R.xml.keyboard_qwerty123);
        keyboardQwerty4   = new Keyboard(mainActivity, R.xml.keyboard_qwerty4);
        keyboardNum123    = new Keyboard(mainActivity, R.xml.keyboard_num123);
        keyboardNum4      = new Keyboard(mainActivity, R.xml.keyboard_num4);

        KeyboardView.OnKeyboardActionListener onKeyboardActionListener = new KeyToEditableActionListener();
        keyboardView123.setOnKeyboardActionListener(onKeyboardActionListener);
        keyboardView4.setOnKeyboardActionListener(onKeyboardActionListener);
    }

    public void showNum() {
        keyboardView123.setKeyboard(keyboardNum123);
        keyboardView4.setKeyboard(keyboardNum4);
        showBottom(true);
    }

    public void showQwerty() {
        keyboardView123.setKeyboard(keyboardQwerty123);
        keyboardView4.setKeyboard(keyboardQwerty4);
        showBottom(false);
    }

    public void showBottom(boolean show) {
        if (show) {
            keyboardView4.setVisibility(View.VISIBLE);
            externalFavoriteBar.setVisibility(View.INVISIBLE);
        } else {
            keyboardView4.setVisibility(View.INVISIBLE);
            externalFavoriteBar.setVisibility(View.VISIBLE);
        }
    }

    private class KeyToEditableActionListener implements KeyboardView.OnKeyboardActionListener {

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onRelease(int primaryCode) {
            int length = searchEditText.length();
            if (length > 0) {
                showBottom(true);
            }
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    if (length > 0) {
                        searchEditText.getText().delete(length - 1, length);
                    }
                    if (length == 1) {
                        showBottom(false);
                    }
                    break;
                case Keyboard.KEYCODE_MODE_CHANGE:
                    if (keyboardView123.getKeyboard() == keyboardQwerty123) {
                        showNum();
                    } else if (keyboardView123.getKeyboard() == keyboardNum123) {
                        showQwerty();
                    }
                    break;
                default:
            }
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (primaryCode > 0) {
                try {
                    for (char c : Character.toChars(primaryCode)) {
                        searchEditText.append(Character.toString(c));
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeUp() {
        }
    }
}
