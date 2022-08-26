/*
 * Copyright (C) Anoud Technologies, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.model.wa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author ravindar.singh
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EngatiMessage {

    ObjectMapper objectMapper = new ObjectMapper();

    public static enum EngatiPacketType {

        USER_MESSAGE("USER_MESSAGE", "001"), START_CHAT("START_CHAT", "002"), AGENT_MESSAGE("AGENT_MESSAGE", "003"), RESOLVE_CHAT("RESOLVE_CHAT", "004"), STATUS_PACKET("STATUS_PACKET", "005");

        private final String code;
        private final String mode;

        EngatiPacketType(String code, String mode) {
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

        public static EngatiPacketType fromCode(String code) {
            for (EngatiPacketType b : EngatiPacketType.values()) {
                if (code == null ? b.code == null : code.equals(b.code)) {
                    return b;
                }
            }
            return USER_MESSAGE;
        }
    }

    private EngatiPacketType externalPacketType;
    private EngatiMessageBody body;
    private String platform;
    private String userId;
    private String botKey;
    private String botIdentifier;

    // Template message
    private String phoneNumber;
    private EngatiTemplate payload;

    public EngatiPacketType getExternalPacketType() {
        return externalPacketType;
    }

    public void setExternalPacketType(EngatiPacketType externalPacketType) {
        this.externalPacketType = externalPacketType;
    }

    public EngatiMessageBody getBody() {
        return body;
    }

    public void setBody(EngatiMessageBody body) {
        this.body = body;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBotKey() {
        return botKey;
    }

    public void setBotKey(String botKey) {
        this.botKey = botKey;
    }

    public String getBotIdentifier() {
        return botIdentifier;
    }

    public void setBotIdentifier(String botIdentifier) {
        this.botIdentifier = botIdentifier;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EngatiTemplate getPayload() {
        return payload;
    }

    public void setPayload(EngatiTemplate payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {

        }
        return "EngatiMessage{" + "externalPacketType=" + externalPacketType + ", body=" + body + ", platform=" + platform + ", userId=" + userId + ", botKey=" + botKey + ", botIdentifier=" + botIdentifier + '}';
    }

}
