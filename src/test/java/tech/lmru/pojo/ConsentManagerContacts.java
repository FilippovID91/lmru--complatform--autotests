package tech.lmru.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ConsentManagerContacts {

    private String[] contacts;

    public ConsentManagerContacts(String[] contacts) {
        this.contacts = contacts;
    }
}