/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.crm.restapi.resources;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author sutharsan.g
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OmniDocsParam implements java.io.Serializable {

    @JsonIgnore
    @FormDataParam("userId")
    private String userId;
    @JsonIgnore
    @FormDataParam("docCode")
    private String docCode;
    @JsonIgnore
    @FormDataParam("company")
    private String company;
    @JsonIgnore
    @FormDataParam("id")
    private String id;
    @JsonIgnore
    @FormDataParam("docType")
    private String docType;
    @FormDataParam("appType")
    private String appType;
    @JsonIgnore
    @FormDataParam("document")
    private List<FormDataBodyPart> document;
    @JsonIgnore
    @FormDataParam("documentName")
    private List<String> documentName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> docId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public List<FormDataBodyPart> getDocument() {
        return document;
    }

    public void setDocument(List<FormDataBodyPart> document) {
        this.document = document;
    }

    public List<String> getDocumentName() {
        return documentName;
    }

    public void setDocumentName(List<String> documentName) {
        this.documentName = documentName;
    }

    public List<String> getDocId() {
        return docId;
    }

    public void setDocId(List<String> docId) {
        this.docId = docId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

}
