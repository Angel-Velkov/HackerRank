package javaSHA_256;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static javaSHA_256.JavaSHA_256.getSHA_256;

// Original function
  /*
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner input = new Scanner(System.in);
        MessageDigest m = MessageDigest.getInstance("SHA-256");
        m.reset();
        m.update(input.nextLine().getBytes());
        for (byte i : m.digest()) {
            System.out.print(String.format("%02x", i));
        }
        System.out.println();
    }
     */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line = reader.readLine();

        System.out.println(getSHA_256(line));
    }
}
