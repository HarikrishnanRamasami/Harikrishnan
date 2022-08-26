/*
 * Copyright (C) Anoud Technologies, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.model.wa;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * Sample response:-
 * {\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"bot_key\":\"ac248ca0271a478f\",\"page_number\":1,\"message_count\":33,\"start_date\":\"1970-01-01 00:00:00.000+0000\",\"end_date\":\"2022-07-25 09:06:52.584+0000\",\"conversations\":[{\"response\":\"hi\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 07:53:43.078+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":true,\"file\":null,\"agent_email\":\"External\",\"agent_name\":\"External\"},{\"response\":\"hi\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 07:50:27.668+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":true,\"file\":null,\"agent_email\":\"External\",\"agent_name\":\"External\"},{\"response\":\"Hi\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 07:44:40.622+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":true,\"file\":null,\"agent_email\":\"External\",\"agent_name\":\"External\"},{\"response\":\"unable to find\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 07:33:31.120+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":true,\"file\":null,\"agent_email\":\"External\",\"agent_name\":\"External\"},{\"response\":\"checking connection\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 07:30:48.585+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":true,\"file\":null,\"agent_email\":\"External\",\"agent_name\":\"External\"},{\"response\":\"i am not sure\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 07:18:26.092+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":true,\"file\":null,\"agent_email\":\"External\",\"agent_name\":\"External\"},{\"response\":\"But how come\",\"sender\":\"user\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 07:04:43.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Connection issue\",\"sender\":\"user\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 05:21:01.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Hello \\uD83D\\uDC4B, thanks for your message! A customer service agent will get back to you in few minutes. For urgent inquiries, kindly call our hotline at 8000 742.\\r\\nTo buy a car insurance visit \\uD83D\\uDD17 car.qic-insured.com (24/7)\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 05:11:54.561+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":true,\"file\":null,\"agent_email\":\"External\",\"agent_name\":\"External\"},{\"response\":\"Another time issue\",\"sender\":\"user\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 05:11:28.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Issue resoved\",\"sender\":\"user\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 05:08:07.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Time issue\",\"sender\":\"user\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 05:01:50.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Please\",\"sender\":\"user\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 04:59:20.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Can I contact agent\",\"sender\":\"user\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 04:37:23.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"{\\\"agentId\\\":1,\\\"PARTICIPATION_MESSAGE\\\":\\\"Agent<\\\\/strong> joined the conversation\\\"}\",\"sender\":\"bot\",\"message_type\":\"AGENT_PARTICIPATION_STATUS\",\"timestamp\":\"2022/07/25 04:26:13.856+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Yes\",\"sender\":\"user\",\"message_type\":\"POSTBACK\",\"timestamp\":\"2022/07/25 04:26:11.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"[Yes, No]\",\"sender\":\"bot\",\"message_type\":\"OPTIONS\",\"timestamp\":\"2022/07/25 04:25:45.225+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Would you like to connect Agent?\\n\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 04:25:45.224+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Hi , welcome back. Hope you liked our services.\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 04:25:43.739+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Hello\",\"sender\":\"user\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/25 04:25:41.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Yes\",\"sender\":\"user\",\"message_type\":\"POSTBACK\",\"timestamp\":\"2022/07/24 11:42:01.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"[Yes, No]\",\"sender\":\"bot\",\"message_type\":\"OPTIONS\",\"timestamp\":\"2022/07/24 11:41:46.727+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Would you like to connect Agent?\\n\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/24 11:41:46.725+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Hi , welcome back. Hope you liked our services.\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/24 11:41:45.695+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Hi\",\"sender\":\"user\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/24 11:41:43.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Yes\",\"sender\":\"user\",\"message_type\":\"POSTBACK\",\"timestamp\":\"2022/07/18 09:43:05.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"[Yes, No]\",\"sender\":\"bot\",\"message_type\":\"OPTIONS\",\"timestamp\":\"2022/07/18 09:42:50.982+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Would you like to connect Agent?\\n\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/18 09:42:50.981+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Hi , welcome back. Hope you liked our services.\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/18 09:42:48.874+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Hi\",\"sender\":\"user\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/18 09:42:45.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Hi , welcome back. Hope you liked our services.\",\"sender\":\"bot\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/18 09:37:51.357+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Hi\",\"sender\":\"user\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/18 09:37:49.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null},{\"response\":\"Hi\",\"sender\":\"user\",\"message_type\":\"TEXT\",\"timestamp\":\"2022/07/14 11:20:27.000+0000\",\"user_platform\":\"whatsapp\",\"type\":\"CHAT\",\"user_id\":\"d2eb089f-0ac5-339c-ab36-616f350c2211\",\"attachments\":null,\"is_agent_reply\":false,\"file\":null,\"agent_email\":null,\"agent_name\":null}]}
 *
 * @author ravindar.singh
 */
public class EngatiMessageHistory {

    @JsonProperty(value = "user_id")
    private String userId;
    @JsonProperty(value = "bot_key")
    private String botKey;
    @JsonProperty(value = "page_number")
    private Long pageNumber;
    @JsonProperty(value = "message_count")
    private Long messageCount;
    @JsonProperty(value = "start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date startDate;
    @JsonProperty(value = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date endDate;
    private List<Conversations> conversations;

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

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Long messageCount) {
        this.messageCount = messageCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Conversations> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversations> conversations) {
        this.conversations = conversations;
    }

    public static class Conversations {

        private String response;
        private String sender;
        @JsonProperty(value = "message_type")
        private String messageType;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss.SSSZ")
        private Date timestamp;
        @JsonProperty(value = "user_platform")
        private String userPlatform;
        private String type;
        @JsonProperty(value = "user_id")
        private String userId;
        private String attachments;
        @JsonProperty(value = "is_agent_reply")
        private String isAgentReply;
        private String file;
        @JsonProperty(value = "agent_email")
        private String agentEmail;
        @JsonProperty(value = "agent_name")
        private String agentName;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getUserPlatform() {
            return userPlatform;
        }

        public void setUserPlatform(String userPlatform) {
            this.userPlatform = userPlatform;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAttachments() {
            return attachments;
        }

        public void setAttachments(String attachments) {
            this.attachments = attachments;
        }

        public String getIsAgentReply() {
            return isAgentReply;
        }

        public void setIsAgentReply(String isAgentReply) {
            this.isAgentReply = isAgentReply;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getAgentEmail() {
            return agentEmail;
        }

        public void setAgentEmail(String agentEmail) {
            this.agentEmail = agentEmail;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

    }
}
