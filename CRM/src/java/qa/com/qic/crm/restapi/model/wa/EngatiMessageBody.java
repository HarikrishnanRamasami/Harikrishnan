/*
 * Copyright (C) Anoud Technologies, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.model.wa;

import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author ravindar.singh
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EngatiMessageBody {

    private HashMap<String, String> attributeMap;
    private Message.Type packetType;
    private String livechatCategoryName;
    private EngatiBody text;
    private EngatiBody media;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss.SSSZ")
    private Date timestamp;

    private String code;
    private String description;
    private String status;

    public HashMap<String, String> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(HashMap<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public Message.Type getPacketType() {
        return packetType;
    }

    public void setPacketType(Message.Type packetType) {
        this.packetType = packetType;
    }

    public String getLivechatCategoryName() {
        return livechatCategoryName;
    }

    public void setLivechatCategoryName(String livechatCategoryName) {
        this.livechatCategoryName = livechatCategoryName;
    }

    public EngatiBody getText() {
        return text;
    }

    public void setText(EngatiBody text) {
        this.text = text;
    }

    public EngatiBody getMedia() {
        return media;
    }

    public void setMedia(EngatiBody media) {
        this.media = media;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
