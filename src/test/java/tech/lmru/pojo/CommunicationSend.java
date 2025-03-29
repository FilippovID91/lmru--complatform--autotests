package tech.lmru.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommunicationSend {

    private String communicationName;
    private Object contacts;
    private Object variables;
    private String storeId;

    public CommunicationSend(String communicationName, Object contacts, Object variables, String storeId) {
        this.communicationName = communicationName;
        this.contacts = contacts;
        this.variables = variables;
        this.storeId = storeId;
    }

}