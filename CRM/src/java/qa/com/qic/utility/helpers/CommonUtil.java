/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.utility.helpers;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ravindar.singh
 */
public final class CommonUtil {

    public static Cookie getCookie(HttpServletRequest request, final String name) {
        Cookie cookies[] = request.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (name.equalsIgnoreCase(c.getName())) {
                    cookie = c;
                }
            }
        }
        return cookie;
    }

    public static Cookie removeCookie(HttpServletRequest request, final String name) {
        Cookie cookies[] = request.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (name.equalsIgnoreCase(c.getName())) {
                    cookie = c;
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                }
            }
        }
        return cookie;
    }

    public static String getRealPath() throws UnsupportedEncodingException {
        String path = CommonUtil.class.getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        String pathArr[] = fullPath.split("/WEB-INF/classes/");
        fullPath = pathArr[0];
        return new File(fullPath).getPath() + File.separatorChar;
    }
}