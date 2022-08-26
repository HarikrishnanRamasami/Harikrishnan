/*
 * Copyright (C) Anoud Technologies, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.model.wa;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author ravindar.singh
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EngatiTemplateResponse {

    private ResponseObject responseObject;
    private Status status;

    public ResponseObject getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(ResponseObject responseObject) {
        this.responseObject = responseObject;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class ResponseObject {

        private List<Status> errors;

        public List<Status> getErrors() {
            return errors;
        }

        public void setErrors(List<Status> errors) {
            this.errors = errors;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Status {

        private String code;
        private String desc;
        private String title;
        private String details;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        @Override
        public String toString() {
            return "Status{" + "code=" + code + ", desc=" + desc + ", title=" + title + ", details=" + details + '}';
        }
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {

        }
        return "EngatiTemplateResponse{" + "responseObject=" + responseObject + ", status=" + status + '}';
    }
}
