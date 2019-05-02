import org.jpos.util.LogEvent;
import org.jpos.util.Logger;

import java.io.*;

public class test {

    private static final String RESPONSE="024549534f30313530303030313531323130f030a0010ec18018000000000400002831363435323035353531303630303735353833383130303030303030303030303030303031333937373631393034" +
            "3238303030303030303432383335363036313131313131393039383138313832363431554e49303030303030444f504e3031343020202020202020204445504152544d454e5420202020203032342b30" +
            "303030303030303637362b30303030303030303637363335363031324e465542415350422b3030303031335542493150524f313131303050313535313036303230313030373632373830303345465433" +
            "343132303139303432382020343539323030303031353838322044202020202020312e303032303139303432382020343539323030303031353838322044202020202020312e30303230313930343238" +
            "2020343539323030303031353838322044202020202020322e303032303139303432382020343539323030303031353838322044202020202020322e3030323031393034323820203435393230303030" +
            "31353838322044202020202020312e303032303139303432382020343539323030303031353838322044202020202020312e303032303139303432382020343539323030303031353838322044202020" +
            "202020312e303032303139303432382020343539323030303031353838322044202020202020312e303032303139303432382020343539323030303031353838322044202020202020312e3030415641" +
            "494c20414d542052732e3030303030303030362e37362b";
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    public static void main(String[] args) {

        byte[] response=hexStringToByteArray(RESPONSE);
        InputStream targetStream = new ByteArrayInputStream(response);

        DataInputStream serverIn =  new DataInputStream(targetStream);


        try {
            int l = 0;
            byte[] b = new byte[2];

            while (l == 0) {
                serverIn.readFully(b,0,2);
                l = ((int)b[0] &0xFF) << 8 | (int)b[1] &0xFF;
                if (l == 0) {
                   // serverOut.write(b);
                   /// serverOut.flush();
                    System.out.println("Yes");
                }

            }
            System.out.println("got-message-length"+ Integer.toString(l));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

}
}
