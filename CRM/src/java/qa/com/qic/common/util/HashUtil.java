/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import org.apache.logging.log4j.Logger;

import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author sutharsan.g
 */
public class HashUtil {
     private static final Logger logger = LogUtil.getLogger(HashUtil.class);

    /**
     * hexSHA256.
     *
     * @param data the plain text
     * @return the encoded string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     *
     */
    public String hexSHA256(String data) throws NoSuchAlgorithmException {
        if (data == null) {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return byteArray2Hex(md.digest(data.getBytes()));
    }

    /**
     * hexSHA512.
     *
     * @param data the plain text
     * @return the encoded string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     *
     */
    public String hexSHA512(String data) throws NoSuchAlgorithmException {
        if (data == null) {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return byteArray2Hex(md.digest(data.getBytes()));
    }

    /**
     * Byte array2 hex.
     *
     * @param hash the hash
     * @return the string
     */
    private String byteArray2Hex(final byte[] hash) {
        Formatter formatter = null;
        String retresult = null;
        try {
            formatter = new Formatter();
            for (byte b : hash) {
                formatter.format("%02x", b);
            }
            retresult = formatter.toString();
        } catch (Exception e) {
            logger.error("Formatter Exception {}", e);
        } finally {
            if (formatter != null) {
                formatter.close();
            }
        }
        return retresult;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        HashUtil hu = new HashUtil();
        System.out.println("" + hu.hexSHA256("" + "b2af28e7-6e34-49fb-8f31-1c92d01d2801"));
        System.out.println("" + hu.hexSHA256("" + "CA8$W1t(h@2019"));
        System.out.println("" + hu.hexSHA512("katherine123" + "11"));
    }

}
