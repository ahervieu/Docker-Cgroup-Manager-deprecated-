package org.kevoree.docker.containerdriver.cgroupDriver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aymeric on 01/12/14.
 */
public class NetworkDriver {


    public static void setIncomingTraffic(String bridge, int traffic)
    {
        try {
            DataOutputStream os = null;
            Process p ;
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(" sudo tc qdisc del dev "+bridge+" root netem rate " + traffic);
            os.writeBytes(" sudo tc qdisc add dev "+bridge+" root netem rate " + traffic);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setIncomingCorruptionRate(String bridge, int rate)
    {
        try {
            DataOutputStream os = null;
            Process p ;
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(" sudo tc qdisc del dev "+bridge+" root netem corrupt " + rate+"%");
            os.writeBytes(" sudo tc qdisc add dev "+bridge+" root netem corrupt " + rate+"%");
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setIncomingLossRate(String bridge, int rate)
    {
        try {
            DataOutputStream os = null;
            Process p ;
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(" sudo tc qdisc del dev "+bridge+" root netem loss " + rate+"%");
            os.writeBytes(" sudo tc qdisc add dev "+bridge+" root netem loss " + rate+"%");
            os.flush();
            os.close();
        } catch (IOException e) {

        }
    }

    public static void setIncomingDelay(String bridge, int delay)
    {
        try {
            DataOutputStream os = null;
            Process p ;
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(" sudo tc qdisc del dev "+bridge+" root netem delay " + delay+"ms");
            os.writeBytes(" sudo tc qdisc add dev "+bridge+" root netem delay " + delay+"ms");
            os.flush();
            os.close();
        } catch (IOException e) {

        }
    }

    public static void setOutgoingTraffic(String containerId, int traffic)
    {

    }

    public static void setOutgoingCorruptionRate(String containerId, int rate)
    {

    }

    public static void setOutgoingLossRate(String containerId, int rate)
    {

    }

    public static void setOutgoingDelay(String containerId, int delay)
    {

    }

    public static void addIpTableRule()
    {
        DataOutputStream os = null;
        String value = "";
        try {
            Process p ;
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            os.writeBytes("iptables -I INPUT -i docker0 -p icmp -j LOG");
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // http://stackoverflow.com/questions/21724225/docker-how-to-get-veth-bridge-interface-pair-easily

    public static String  getContainerBridge(String Ip)
    {
        String bridge = "";
        DataOutputStream os = null;
        String value = "";
        try {
            Process p = Runtime.getRuntime().exec("ping -c "+Ip);
            p.waitFor();
            Process p2 = Runtime.getRuntime().exec("dmesg");
            BufferedReader reader = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            p2.waitFor();
            String line ="";
            Boolean stop = false ;
            while ((line = reader.readLine())!= null && !stop) {
                if(line.contains(Ip))
                {
                    stop = true ;
                    String pattern = "PHYSIN=([a-z,0-9]*) " ;
                    Pattern pat = Pattern.compile(pattern);
                    Matcher m = pat.matcher(line) ;
                    m.find();
                    bridge =m.group(1);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bridge ;


    }

}
