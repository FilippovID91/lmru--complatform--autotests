package tech.lmru.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GenericSmsHistory {

    private String receiverContains;
    private String startDate;
    private String endDate;

    public GenericSmsHistory(String receiverContains, String startDate, String endDate) {
        this.receiverContains = receiverContains;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static GenericSmsHistory genericSmsHistoryBody() {
        return new GenericSmsHistory(
                null,
                null,
                null);
    }
}