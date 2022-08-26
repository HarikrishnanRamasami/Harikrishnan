/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.common.dms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author sutharsan.g
 */
public class DmsAction extends ActionSupport implements Preparable, SessionAware {

    private static final Logger logger = LogUtil.getLogger(DmsAction.class);
    private Map<String, Object> session = null;
    private String pageAction;
    private DmsDAO dao;
    private transient TDocInfoBean docInfoBean;
    private transient TDocErrorInfo docErrorInfo;
    private List<TDocInfoBean> docInfoList = null;
    private InputStream fileInputStream;
    private String filePath;
    private String fileName;

    @Override
    public void prepare() throws Exception {
        dao = new DmsDAO((String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE));
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    public String getPageAction() {
        return pageAction;
    }

    public void setPageAction(String pageAction) {
        this.pageAction = pageAction;
    }

    public String downloadDmsFile() throws FileNotFoundException, IOException {
        String dmsUrl = dao.getDmsUrl();
        String urlString = dmsUrl + getDocInfoBean().getCdiLink() + "&CDisp=attachment";
        fileInputStream = new URL(urlString).openStream();
        fileName = getDocInfoBean().getCdiDocName();
        filePath = fileName;
        return SUCCESS;
    }

    public String LoadDmsDocs() {
        TDocInfoBean docInfoBean = new TDocInfoBean();
        try {
            docInfoList = dao.loadUploadInformation(getDocInfoBean(), (String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE));
        } catch (Exception e) {
            logger.error("Error in LoadDmsDocsm => {}", e);
        }
        return SUCCESS;
    }

    public List<TDocInfoBean> getDocInfoList() {
        return docInfoList;
    }

    public void setDocInfoList(List<TDocInfoBean> docInfoList) {
        this.docInfoList = docInfoList;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public TDocInfoBean getDocInfoBean() {
        return docInfoBean;
    }

    public void setDocInfoBean(TDocInfoBean docInfoBean) {
        this.docInfoBean = docInfoBean;
    }

    public TDocErrorInfo getDocErrorInfo() {
        return docErrorInfo;
    }

    public void setDocErrorInfo(TDocErrorInfo docErrorInfo) {
        this.docErrorInfo = docErrorInfo;
    }

}
