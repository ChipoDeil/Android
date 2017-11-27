package com.example.chipodeil.authproject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chipodeil on 26.11.2017.
 */

public class Holder {
    public static String encodePass(String pass) throws NoSuchAlgorithmException {

        String password = pass;

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public static boolean shouldRandomize = false;
}
