/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;

import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class EmailEventRecorder extends HttpServlet {

    private static final long serialVersionUID = -7443078731019895690L;
    private static final Logger logger = LogUtil.getLogger(EmailEventRecorder.class);

    public EmailEventRecorder() {
        super();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        showEmpImage(request, response);
    }

    private void showEmpImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String t = request.getParameter("t");
        String fileName = "/resource/pixel.png";
        InputStream in = null;
        OutputStream outputStream = null;
        String type = request.getServletPath();
        boolean isDebug = Boolean.TRUE;
        if (isDebug) {
            logger.info("Email event => Token: {}, Type: {}", t, type);
        }
        Boolean isOpenMail = Boolean.FALSE;
        try {
            if ("/p".equals(type)) {
                isOpenMail = Boolean.TRUE;
            }
            String url = null;
            if (t != null && !"".equals(t.trim()) && !"TOKEN".equals(t)) {
                CommonDAO dao = new CommonDAO("001");
                String[] tokens = t.split("=");
                String ut = "";
                if (tokens.length == 2) {
                    t = tokens[0];
                    ut = tokens[1];
                }
                if (isDebug) {
                    logger.info("Email event => Token: {}, URL Token: {}, URL: {}", new Object[]{t, ut, url});
                }
                int d = dao.updateEmailOpenCount(t, isOpenMail);
                if (isDebug) {
                    logger.info("Email event => Token: {}, Updated: {}", new Object[]{t, d});
                }
                if ("/r".equals(type)) {
                    url = dao.getEmailUrl(t, ut);
                }
            }
            if ("/p".equals(type)) {
                response.setContentType("image/png");
                response.setHeader("Content-Disposition", "inline; filename=" + fileName);
                outputStream = response.getOutputStream();
                in = this.getClass().getClassLoader().getResourceAsStream(fileName);
                if (in != null) {
                    byte[] buff = new byte[5242880];
                    int bytesRead;
                    while (-1 != (bytesRead = in.read(buff, 0, buff.length))) {
                        response.getOutputStream().write(buff, 0, bytesRead);
                    }
                }
            } else if ("/r".equals(type) && url != null) {
                response.sendRedirect(url.replace("TOKEN", t));
            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.sendError(response.SC_CONFLICT, "Campiagn expired");
            }
        } catch (Exception e) {
            logger.error("Exception {}", e);
        } finally {
            if (in != null) {
                in.close();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Email tracker";
    }

}
