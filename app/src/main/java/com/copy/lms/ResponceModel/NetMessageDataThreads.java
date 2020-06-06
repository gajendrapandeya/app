package com.copy.lms.ResponceModel;

import java.util.ArrayList;

public class NetMessageDataThreads {
    private NetMessageDataThreadsPivot pivot;
    private ArrayList<NetMessageDataThreadsMessages> messages;
    private String  id;

    public NetMessageDataThreadsPivot getPivot() {
        return this.pivot;
    }

    public void setPivot(NetMessageDataThreadsPivot pivot) {
        this.pivot = pivot;
    }

    public ArrayList<NetMessageDataThreadsMessages> getMessages() {
        return this.messages;
    }

    public void setMessages(ArrayList<NetMessageDataThreadsMessages> messages) {
        this.messages = messages;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
