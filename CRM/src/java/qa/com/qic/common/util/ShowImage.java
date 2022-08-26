/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.common.util;

/**
 *
 * @author sutharsan.g
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Logger;

import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author karthick.pandi
 */
public class ShowImage extends HttpServlet {

    private static final long serialVersionUID = -7443078731019895690L;
    private static final Logger LOGGER = LogUtil.getLogger(ShowImage.class);

    public ShowImage() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String fileName = request.getParameter("s");// File name
            String type = request.getParameter("t");// Content typr
            String companycode = request.getParameter("c");// Comapny code
            if (fileName == null || fileName.equals("")) {
                fileName = "sign".equalsIgnoreCase(type) ? "SignNotFound.png" : "ImageNotFound.png";
            }
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            InputStream in = null;
            try {
                if (type != null && type.equals(fileType)) {
                    response.setContentType(type);
                }
                String imageName = FilenameUtils.removeExtension(fileName);
                response.setHeader("Content-Disposition", "inline; filename=" + imageName);
                String prefixFilePath = ApplicationConstants.FILE_STORE_LOC("R", companycode) + ApplicationConstants.CAMPAIGN_IMAGE_PATH + "\\";
                LOGGER.info("prefixFilePath:" + prefixFilePath + imageName);
                if (prefixFilePath != null && (prefixFilePath.contains("//") || prefixFilePath.contains("://"))) {
                    URL url = new URL(prefixFilePath + imageName);
                    in = url.openStream();
                } else {
                    File f = new File(prefixFilePath + imageName);
                    if (!f.exists() || !f.canRead()) {
                        f = new File(prefixFilePath + ("sign".equalsIgnoreCase(type) ? "SignNotFound.png" : "ImageNotFound.png"));
                    }
                    if (f.exists()) {
                        in = new FileInputStream(f);
                    }
                }
                if (in != null) {
                    byte[] buff = new byte[5242880];
                    int bytesRead;
                    while (-1 != (bytesRead = in.read(buff, 0, buff.length))) {
                        response.getOutputStream().write(buff, 0, bytesRead);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Exception {}", e);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
            response.getOutputStream().flush();
        } finally {
            response.getOutputStream().close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Show the company logo on the webpage";
    }
}
