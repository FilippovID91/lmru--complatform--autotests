package tech.lmru.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommunicationCreate {

    private String communicationName;
    private String communicationType;
    private int buId;
    private int communicationPriority;

    public CommunicationCreate(String communicationName, String communicationType, int buId, int communicationPriority) {
        this.communicationName = communicationName;
        this.communicationType = communicationType;
        this.buId = buId;
        this.communicationPriority = communicationPriority;
    }
}