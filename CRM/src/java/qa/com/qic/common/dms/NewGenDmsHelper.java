/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.common.dms;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Logger;

import com.newgen.ws.client.CustomAddDocClient;
import com.newgen.ws.client.DocumentData;

import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author sutharsan.g
 */
public class NewGenDmsHelper {

    private static final Logger logger = LogUtil.getLogger(NewGenDmsHelper.class);
    private DmsDAO documentUploadDAO;
    private static final String QLM_DATA_CLASS_NAME = "QLMMEDICAL";
    private static final String QLM_PROJECT_TYPE = "QLMMEDICAL";
    private static final String RETAIL_DATA_CLASS_NAME = "QICRetail";
    private static final String RETAIL_PROJECT_TYPE = "QICRetail";
    private String company;

    public NewGenDmsHelper(String company) {
        this.company = company;
        documentUploadDAO = new DmsDAO(company);
    }

    public void saveSingleDoc(File file, String fileName, String docCode, String remarks, String docType,
            HashMap<String, String> dataClassPropMap, String transactionId, String transactionSrNo, String refNo, String lob,
            String userId) throws Exception {

        saveMultipleDoc(new File[]{file}, new String[]{fileName}, docCode, remarks, docType, dataClassPropMap,
                transactionId, transactionSrNo, refNo, lob, userId);
    }

    public void saveMultipleDoc(File[] fileArr, String[] fileNameArr, String docCodeArr, String remarksArr, String docType,
            HashMap<String, String> dataClassPropMap, String transactionId, String transactionSrNo, String refNo, String lob,
            String userId) throws Exception {
        HashMap<Integer, String> folderHierarchyMap = new HashMap<>();
        CustomAddDocClient customAddDocClient = new CustomAddDocClient();
        //String[] fileExtension = null;
        List<DocumentData> objDocumentDataList = new ArrayList<>();

        for (int i = 0; i < fileArr.length; i++) {
            if (fileNameArr[i] == null) {
                continue;
            }
            DocumentData objDocumentData = new DocumentData();
            objDocumentData.setDocumentName(fileNameArr[i]);
            objDocumentData.setFileExtension(FilenameUtils.getExtension(fileNameArr[i]));
            objDocumentData.setFilePath(fileArr[i].getAbsolutePath());
            objDocumentDataList.add(objDocumentData);
        }
        if (company.equals(ApplicationConstants.COMPANY_CODE_MED_DOHA)) {
            folderHierarchyMap.put(1, QLM_PROJECT_TYPE);
            folderHierarchyMap.put(2, company);
            folderHierarchyMap.put(3, "CRM_TASKS");
            folderHierarchyMap.put(4, String.valueOf(transactionId));
            dataClassPropMap.put("DataClassName", QLM_DATA_CLASS_NAME);
        } else {
            folderHierarchyMap.put(1, RETAIL_PROJECT_TYPE);
            folderHierarchyMap.put(2, company);
            folderHierarchyMap.put(3, "CRM_TASKS");
            folderHierarchyMap.put(4, String.valueOf(transactionId));
            dataClassPropMap.put("DataClassName", RETAIL_DATA_CLASS_NAME);
        }
        String output = customAddDocClient.uploadMultipleDocuments((company.equals(ApplicationConstants.COMPANY_CODE_MED_DOHA) ? QLM_DATA_CLASS_NAME : RETAIL_DATA_CLASS_NAME), objDocumentDataList, folderHierarchyMap, dataClassPropMap);
        logger.info("File Upload returns output from DMS system = " + output);
        output = output.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&");
        List<KeyValue> keyValues = getDmsDocTypeList(company, docType);
        Map<String, String> docTypeCodes = getDocMap(keyValues);
        logger.info("DMS File Upload Doc Types from DB =>" + docTypeCodes);

        List<String> urlLink = new LinkedList<>();
        List<String> dmsCode = new LinkedList<>();
        List<String> docName = new LinkedList<>();
        List<String> fileExtn = new LinkedList<>();
        List<String> errorCode = new LinkedList<>();
        List<String> errorDesc = new LinkedList<>();
        List<String> errDmsCode = new LinkedList<>();
        List<String> errFileType = new LinkedList<>();
        List<String> errFileExtn = new LinkedList<>();
        List<String> errFileRemarks = new LinkedList<>();
        List<String> errFileName = new LinkedList<>();

        for (int i = 1; i <= fileArr.length; i++) {
            String docResponse = output.substring(output.indexOf("<DocResponse" + i + ">") + ("<docResponse" + i + ">").length(), output.indexOf("</DocResponse" + i + ">"));
            String docUrl = docResponse.substring(docResponse.indexOf("<DocUrl>") + "<DocUrl>".length(), docResponse.lastIndexOf("</DocUrl>"));
            if (docUrl != null && !"".equalsIgnoreCase(docUrl.trim())) {
                urlLink.add(docUrl);
                docName.add(fileNameArr[i - 1]);
                fileExtn.add(objDocumentDataList.get(i - 1).getFileExtension());
            } else {
                errorCode.add(docResponse.substring(docResponse.indexOf("<StatusCode>") + "<StatusCode>".length(), docResponse.lastIndexOf("</StatusCode>")));
                errorDesc.add(docResponse.substring(docResponse.indexOf("<StatusDesc>") + "<StatusDesc>".length(), docResponse.lastIndexOf("</StatusDesc>")));
                errFileExtn.add(objDocumentDataList.get(i - 1).getFileExtension());
                errFileName.add(fileNameArr[i - 1]);
            }
        }
        String status = null;
        if (output != null) {
            if (urlLink.size() > 0) {
                System.out.println("Url Link" + urlLink);
                status = insertUploadedDocInfo(transactionId, transactionSrNo, refNo, docType, docCodeArr, docName, fileExtn, remarksArr, urlLink, userId, company);
                logger.info("insertUploadedDocInfo().. status=" + status);
            }
            if (errDmsCode.size() > 0) {
                status = insertErrorDocInfo(transactionId, transactionSrNo, refNo, docType, errFileType, errFileExtn, remarksArr, errFileName, userId, lob,
                        errorCode, errorDesc, company);
                logger.info("insertErrorDocInfo().. status=" + status);
            }
        }
        logger.info("<========= DMS File Upload End =========>");
    }

    public List<KeyValue> getDmsDocTypeList(final String type, final String company) {
        return documentUploadDAO.getDmsDocTypeList(type);
    }

    public String insertUploadedDocInfo(final String transactionId, final String transactionSrNo, final String refNo, final String docType,
            final String docCode, final List<String> docName, final List<String> fileExt, final String dmsRemarks,
            final List<String> urlLink, final String userId, final String company) throws Exception {
        return documentUploadDAO.insertUploadedDocInfo(transactionId, transactionSrNo, refNo, docType, docCode, docName, fileExt,
                dmsRemarks, urlLink, userId);
    }

    public String insertErrorDocInfo(final String transactionId, final String transactionSrNo, final String refNo, final String polOrClm,
            final List<String> docName, final List<String> fileExt, final String dmsRemarks, final List<String> errFileNames,
            final String userId, final String lob, final List<String> errorCodes, final List<String> errorDescs, final String company) {
        return documentUploadDAO.insertErrorDocInfo(transactionId, transactionSrNo, refNo, polOrClm, docName, fileExt, dmsRemarks,
                errFileNames, userId, lob, errorCodes, errorDescs);
    }

    private Map<String, String> getDocMap(List<KeyValue> keyValues) {
        Map<String, String> map = new LinkedHashMap<>();
        if (keyValues != null) {
            for (KeyValue keyValue : keyValues) {
                map.put(keyValue.getKey(), keyValue.getValue());
            }
        }
        return map;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

}
