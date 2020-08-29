package fr.neamar.kiss.pojo;

public final class SearchPojo extends Pojo {
    public enum Type {
        SEARCH,
        URL,
        CALCULATOR,
        IP
    }

    public String query;
    public final String url;
    public final Type type;

    public SearchPojo(String query, String url, Type type) {
        this(url, query, url, type);
    }

    public SearchPojo(String id, String query, String url, Type type) {
        super(id);

        this.query = query;
        this.url = url;
        this.type = type;
    }

    @Override
    public String getHistoryId() {
        // Search POJO should not appear in history
        return "";
    }
}
