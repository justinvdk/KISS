package fr.neamar.kiss.dataprovider.simpleprovider;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import fr.neamar.kiss.pojo.SearchPojo;
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
                            SearchPojo pojo = new SearchPojo(
                                    "ip://",
                                    String.format("%s: %s", intf.getDisplayName(), inetAddress.getHostAddress()),
                                    "",
                                    SearchPojo.Type.IP);
                            if (s.equals("ip")) {
                                pojo.relevance = 50;
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
