/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.utility.helpers;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ravindar.singh
 */
public class AuthUtil {

    public static String sign(String data) {
        String key = "";
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec("82RAVI92BF81306FAB95E1B6372FC0D5".getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (final byte element : rawHmac) {
                sb.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
            }
            key = sb.toString().toUpperCase();
        } catch (Exception e) {

        }
        return key;
    }
}
