package tech.lmru.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommunicationSendContact {

    private String contact;
    private String type;

    public CommunicationSendContact(String contact, String type) {
        this.contact = contact;
        this.type = type;
    }
}