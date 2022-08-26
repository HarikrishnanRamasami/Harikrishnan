/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.model.wa;

import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author ravindar.singh
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Template {

    private String templateName;
    private String templateNamespace;
    private LinkedList<String> templateData;
    private String language;
    // For attachment message with caption
    private String text;
    private String imageUrl;
    private String audioUrl;
    private String fileUrl;
    // For location message
    private Double longitude;
    private Double latitude;
    private String locationName;
    private String address;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateNamespace() {
        return templateNamespace;
    }

    public void setTemplateNamespace(String templateNamespace) {
        this.templateNamespace = templateNamespace;
    }

    public LinkedList<String> getTemplateData() {
        return templateData;
    }

    public void setTemplateData(LinkedList<String> templateData) {
        this.templateData = templateData;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
