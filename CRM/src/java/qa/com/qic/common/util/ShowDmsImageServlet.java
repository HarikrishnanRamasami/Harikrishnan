/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;

import com.qa.dms.upload.UploadRmiClient;
import com.qa.dms.upload.dto.UploadParameters;

import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author kosuri.rao
 */
public class ShowDmsImageServlet extends HttpServlet {

    private static final long serialVersionUID = -7443078731019895690L;
    private static final Logger logger = LogUtil.getLogger(ShowDmsImageServlet.class);

    public ShowDmsImageServlet() {
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
        response.setContentType("text/html;charset=UTF-8");
        //String docCode = request.getParameter("docCode") == null ? request.getParameter("para1") : request.getParameter("docCode");//doc code
        String module = request.getParameter("module");//module[compcode, ]
        String project = request.getParameter("project");//project[DMS_MEDICAL]
        String para1 = request.getParameter("para1");//transId
        String para2 = request.getParameter("para2");//docType
        String para3 = request.getParameter("para3");//memSrNo
        String para4 = request.getParameter("para4");//memSrNo
        String docName = request.getParameter("docName");//memSrNo
        String isThumbNail = request.getParameter("isThumbNail");//
        String disposition = request.getParameter("disposition");//
        if (isThumbNail != null && isThumbNail.contains("|")) {
            StringTokenizer st = new StringTokenizer(isThumbNail, "|");
            while (st.hasMoreTokens()) {
                String[] kv = st.nextToken().split("=");
                switch (kv[0]) {
                    case "isThumbNail":
                        isThumbNail = kv[1];
                        break;
                    case "module":
                        module = kv[1];
                        break;
                    case "project":
                        project = kv[1];
                        break;
                    case "para1":
                        para1 = kv[1];
                        break;
                    case "para2":
                        para2 = kv[1];
                        break;
                    case "para3":
                        para3 = kv[1];
                        break;
                    case "docName":
                        docName = kv[1];
                        break;
                }
            }
        }
        logger.debug("DMS params => module: {}, project: {}, para1: {}, para2: {}, para3: {}, para4: {}, docName: {}", new Object[]{module, project, para1, para2, para3, para4, docName});
        InputStream in = null;
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            Map<String, Object> map = null;
            UploadRmiClient service = new UploadRmiClient();
            Map<String, Object> props = new HashMap<>();
            props.put("DOC_PARA_1", para1);
            props.put("DOC_PARA_2", para2);
            if (StringUtils.isNoneBlank(para3)) {
                props.put("DOC_PARA_3", para3);
            }
            if (StringUtils.isNoneBlank(para4)) {
                props.put("DOC_PARA_4", para4);
            }
            UploadParameters params = new UploadParameters();

            params.setProjectId(ApplicationConstants.DMS_PROJECT_ID(module, project));
            params.setModule(module);
            if (StringUtils.isNoneBlank(docName)) {
                params.setDocName(docName);
            }
            params.setParams(props);

            File f = null;
            byte[] data = null;
            String mimeType = "jpg";
            try {
                if (para1.equals(ApplicationConstants.DMS_MEMBERS_DOC_TYPE) && StringUtils.isBlank(para2)) {
                    f = new File(getServletContext().getRealPath("/") + "/images/ImageNotFound.jpg");
                } else {

                    map = service.retrieveContent(params, ApplicationConstants.DMS_PROJECT_ID(module, project));
                    data = (byte[]) map.get("FILE_DATA");
                    mimeType = String.valueOf(map.get("MIME_TYPE"));
                    if (data != null && data.length > 0) {
                        f = File.createTempFile("fild", null, null);
                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(data);
                        fos.close();
                    }
                }
            } catch (Exception e) {
                f = new File(getServletContext().getRealPath("/") + "/images/ImageNotFound.jpg");
                logger.info("Image not found in DMS!");
            }

            if ("Y".equals(isThumbNail)) {
                BufferedImage image = ImageIO.read(f);
                //BufferedImage scaledImg = Scalr.resize(image, Method.SPEED, 100, Scalr.OP_ANTIALIAS);
                BufferedImage scaledImg = Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,
                        365, 375, Scalr.OP_ANTIALIAS);
                ByteArrayOutputStream bas = new ByteArrayOutputStream();
                ImageIO.write(scaledImg, "png", bas);
                in = new ByteArrayInputStream(bas.toByteArray());
            } else if (f != null) {
                in = new FileInputStream(f);
            }
            if ("pdf".equalsIgnoreCase(mimeType)) {
                response.setContentType("application/pdf");
            } else {
                response.setContentType(mimeType);
            }
            if (StringUtils.isNotBlank(disposition)) {
                response.setHeader("Content-Disposition", disposition + ";filename=" + docName);
            } else {
                response.setHeader("Content-Disposition", "inline;filename=" + docName);
            }

            if (in != null) {
                byte[] buff = new byte[5242880];
                int bytesRead;
                while (-1 != (bytesRead = in.read(buff, 0, buff.length))) {
                    outputStream.write(buff, 0, bytesRead);
                }
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
        return "Fetching data from DMS";
    }

}
