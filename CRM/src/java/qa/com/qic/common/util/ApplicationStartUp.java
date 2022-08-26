/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.util;

import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.Logger;

import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class ApplicationStartUp extends HttpServlet {

    private static final Logger LOGGER = LogUtil.getLogger(ApplicationStartUp.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOGGER.info("Initializing application startup ...");
        ServletContext sc = config.getServletContext();
        sc.setAttribute("R_TOCKEN", new Random().nextLong());
        LOGGER.info("Application tocken is {}", new Object[]{sc.getAttribute("R_TOCKEN")});
    }
}
