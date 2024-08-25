package fr.neamar.kiss.dataprovider.simpleprovider;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import fr.neamar.kiss.pojo.SearchPojo;
import fr.neamar.kiss.pojo.SearchPojoType;
import fr.neamar.kiss.searcher.Searcher;

public class IpProvider extends SimpleProvider {
    @Override
    public void requestResults(String s, Searcher searcher) {
        if (s.contains("ip")) {
            try
            {
                //Enumerate all the network interfaces
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
                {
                    NetworkInterface intf = en.nextElement();
                    // Make a loop on the number of IP addresses related to each Network Interface
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                    {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        // Check if the IP address is not a loopback address
                        if (!inetAddress.isLoopbackAddress()) {
                            String displayName = intf.getDisplayName();
                            SearchPojo pojo = new SearchPojo(
                                    "ip://",
                                    String.format("%s: %s", displayName, inetAddress.getHostAddress()),
                                    "",
                                    SearchPojoType.IP_QUERY);
                            if (s.startsWith("ip")) {
                                pojo.relevance = 50;
                                if (
                                    s.endsWith("4") && inetAddress.getClass() == Inet4Address.class
                                    || s.endsWith("6") && inetAddress.getClass() == Inet6Address.class) {
                                    pojo.relevance += 10;
                                }
                                if (displayName.startsWith(("wlan"))) {
                                    pojo.relevance += 4;
                                }
                                if (displayName.startsWith(("tun"))) {
                                    pojo.relevance += 2;
                                }
                            } else {
                                pojo.relevance = 10;
                            }
                            searcher.addResult(pojo);
                        }
                    }
                }
            }
            catch (SocketException e)
            {
                // That's ok
            }

        }
    }

}
