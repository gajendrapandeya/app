package com.copy.lms.ResponceModel;

public class NetMessageDataThreadsMessages {
    private String thread_id;
    private NetMessageDataThreadsMessagesSender sender;
    private String created_at;
    private String id;
    private String body;
    private String sender_id;

    public String getThread_id() {
        return this.thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public NetMessageDataThreadsMessagesSender getSender() {
        return this.sender;
    }

    public void setSender(NetMessageDataThreadsMessagesSender sender) {
        this.sender = sender;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSender_id() {
        return this.sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }
}
