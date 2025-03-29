package tech.lmru.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EstimatorLimit {

    private String contact;
    private String channelName;

    public EstimatorLimit(String contact, String channelName) {
        this.contact = contact;
        this.channelName = channelName;
    }
}