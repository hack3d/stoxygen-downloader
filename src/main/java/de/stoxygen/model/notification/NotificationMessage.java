package de.stoxygen.model.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class NotificationMessage implements Serializable {

    private String severity;

    private String sender;

    private String category;

    private String content;

    @JsonIgnore
    private String contentType;

    private String username;


    public NotificationMessage() {  }

    public NotificationMessage(String severity, String sender, String category, String content, String username) {
        this.severity = severity;
        this.sender = sender;
        this.category = category;
        this.content = content;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "NotificationMessage[severity: " + severity + ", sender: " + sender + ", category: " + category +
                ", content: " + content + ", contentType: "+ contentType + ", username: " + username + "]";
    }
}
