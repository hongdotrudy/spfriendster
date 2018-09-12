package com.sp.friendster.model;

import java.util.ArrayList;
import java.util.List;

public class NotifyResponse extends CommonResponse {

    private List<String> recipients = new ArrayList<>();

    public NotifyResponse (boolean success, List<String> recipients){
        super(success);
        this.recipients = recipients;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }
}
