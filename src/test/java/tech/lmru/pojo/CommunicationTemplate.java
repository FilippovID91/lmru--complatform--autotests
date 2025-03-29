package tech.lmru.pojo;

import lombok.Getter;

@Getter
public class CommunicationTemplate {

    private String templateTitle;
    private String templateText;

    public CommunicationTemplate(String templateTitle, String templateText) {
        this.templateTitle = templateTitle;
        this.templateText = templateText;
    }
}