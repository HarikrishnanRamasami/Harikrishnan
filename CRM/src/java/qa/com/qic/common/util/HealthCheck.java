/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * F5 Health check monitor
 *
 * @author ravindar.singh
 */
@WebServlet(name = "HealthCheck",
        description = "F5 Health check monitor",
        urlPatterns = "/healthcheck")
public class HealthCheck extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getOutputStream().write("OK".getBytes());
        } catch (IOException e) {
        } finally {
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }
}
