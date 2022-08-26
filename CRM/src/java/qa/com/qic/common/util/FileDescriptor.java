/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.util;

import java.io.File;

/**
 *
 * @author ravindar.singh
 */
public class FileDescriptor {

    private File file;
    private String fileName;
    private String filePath;
    private String docName;
    private String contentType;
    private String remarks;
    private String fileExtn;
    private String orgFile;

    /**
     * Instantiates a new file descriptor.
     */
    public FileDescriptor() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new file descriptor.
     *
     * @param file the file
     * @param fileName the file name
     * @param docName the doc name
     * @param contentType the content type
     * @param remarks the remarks
     */
    FileDescriptor(File file, String fileName, String docName, String contentType, String remarks) {
        this.file = file;
        this.fileName = fileName;
        this.docName = docName;
        this.contentType = contentType;
        this.remarks = remarks;
    }

    /**
     * Gets the file.
     *
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * Sets the file.
     *
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Gets the file name.
     *
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name.
     *
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Gets the doc name.
     *
     * @return the docName
     */
    public String getDocName() {
        return docName;
    }

    /**
     * Sets the doc name.
     *
     * @param docName the docName to set
     */
    public void setDocName(String docName) {
        this.docName = docName;
    }

    /**
     * Gets the content type.
     *
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type.
     *
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Gets the remarks.
     *
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the remarks.
     *
     * @param remarks the new remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFileExtn() {
        return fileExtn;
    }

    public void setFileExtn(String fileExtn) {
        this.fileExtn = fileExtn;
    }

    public String getOrgFile() {
        return orgFile;
    }

    public void setOrgFile(String orgFile) {
        this.orgFile = orgFile;
    }

}
