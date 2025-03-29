package tech.lmru.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommunicationHistory {

    private String communicationName;
    private String contact;
    private String channelName;
    private String startSearchDate;
    private String endSearchDate;

    public CommunicationHistory(String communicationName, String contact, String channelName, String startSearchDate, String endSearchDate) {
        this.communicationName = communicationName;
        this.contact = contact;
        this.channelName = channelName;
        this.startSearchDate = startSearchDate;
        this.endSearchDate = endSearchDate;
    }
}