package javaSHA_256;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static javaSHA_256.JavaSHA_256.getSHA_256;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line = reader.readLine();
        MessageDigest m = MessageDigest.getInstance("SHA-256");
        m.reset();
        m.update(line.getBytes());

        System.out.println("Original:");
        for (byte i : m.digest()) {
            System.out.print(String.format("%02x", i));
        }
        System.out.println();

        //The max length is 64
        System.out.println("Mine:");
        System.out.println(getSHA_256(line));
    }
}
