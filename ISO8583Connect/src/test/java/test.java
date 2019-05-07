import org.jpos.util.LogEvent;
import org.jpos.util.Logger;

import java.io.*;

public class test {

    private static final String RESPONSE="024549534f30313530303030313531323130f030a0010ec180180000000004000028313634323133363833363931303639313036333831303030303030303030303030303030313138313436313930353036303030303030303530363335363036313131313131393039383138313832363431554e49303030303030444f504e3031343020202020202020204445504152544d454e5420202020203032342b30303030353238333336372b30303030353238323936373335363031324e465542415350422b3030303031335542493150524f313131303050313533363931303230313038303538393330303345465433343132303139303530332020343231333638333639313036392044202020202020312e303032303139303530332020343231333638333639313036392044202020202020312e303032303139303530332020343231333638333639313036392044202020202020312e303032303139303530332020343231333638333639313036392044202020202020312e303032303139303530332020343231333638333639313036392044202020202020312e303032303139303530362020343231333638333639313036392044202020202020312e303032303139303530362020343231333638333639313036392044202020202020312e303032303139303530362020343231333638333639313036392044202020202020312e303032303139303530362020343231333638333639313036392044202020202020312e3030415641494c20414d542052732e3030303035323832392e36372b";
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
            int hLen = 12;
            int l = 0;
            byte[] b = new byte[2];
          while (l == 0) {
                serverIn.readFully(b,0,2);
                l = ((int)b[0] &0xFF) << 8 | (int)b[1] &0xFF;
                if (l == 0) {

                }
            }
           int len= l - 1;   // trailler length

          //  if (len == -1) {
                if (hLen > 0) {
                    byte[] header = new byte[hLen];
                    serverIn.readFully(header, 0, hLen);
                    System.out.println(header.toString());
                    len -= header.length;
                }

            //}
            System.out.println("got-message-length"+ Integer.toString(l));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

}
}
