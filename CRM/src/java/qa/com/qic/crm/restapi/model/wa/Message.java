/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.model.wa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author ravindar.singh
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    public static enum Type {

        TEXT("TEXT", "T"), IMAGE("IMAGE", "I"), VOICE("VOICE", "A"), AUDIO("AUDIO", "A"), VIDEO("VIDEO", "V"), DOCUMENT("DOCUMENT", "D"), LOCATION("LOCATION", "L"),
        AGENT_PARTICIPATION_STATUS("AGENT_PARTICIPATION_STATUS","P");

        private String code;
        private String mode;

        Type(String code, String mode) {
            this.code = code;
            this.mode = mode;
        }

        @JsonValue
        public String getCode() {
            return code;
        }

        public String getMode() {
            return mode;
        }

        public static Type fromCode(String code) {
            for (Type b : Type.values()) {
                if (code == null ? b.code == null : code.equals(b.code)) {
                    return b;
                }
            }
            return TEXT;
        }

        public static Type fromMode(String mode) {
            for (Type b : Type.values()) {
                if (mode == null ? b.mode == null : mode.equals(b.mode)) {
                    return b;
                }
            }
            return TEXT;
        }
    }

    private Type type;
    private String text;
    // If type = DOCUMENT or IMAGE
    private String caption;
    private String url;
    // If type = LOCATION
    private Double longitude;
    private Double latitude;
    private String locationName;
    private String address;
    private ReplyMessage context;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public ReplyMessage getContext() {
        return context;
    }

    public void setContext(ReplyMessage context) {
        this.context = context;
    }

}
