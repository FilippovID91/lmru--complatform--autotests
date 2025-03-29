package tech.lmru.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommunicationTask {

    private String channel;
    private int channelPriority;
    private Integer templateId;
    private Integer storeId;
    private String consentType;
    private String consentCheckStrategy;
    private String fromTime;
    private String toTime;
    private int attemptsCount;

    public CommunicationTask(String channel, int channelPriority, Integer templateId,
                             Integer storeId, String consentType, String consentCheckStrategy, String fromTime, String toTime, int attemptsCount) {
        this.channel = channel;
        this.channelPriority = channelPriority;
        this.templateId = templateId;
        this.storeId = storeId;
        this.consentType = consentType;
        this.consentCheckStrategy = consentCheckStrategy;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.attemptsCount = attemptsCount;
    }

}