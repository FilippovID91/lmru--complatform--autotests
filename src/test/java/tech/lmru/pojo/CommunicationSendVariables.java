package tech.lmru.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommunicationSendVariables {

    private String key;
    private String val;

    public CommunicationSendVariables(String key, String val) {
        this.key = key;
        this.val = val;
    }
}