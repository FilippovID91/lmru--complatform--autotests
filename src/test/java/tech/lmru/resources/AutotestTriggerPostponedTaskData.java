package tech.lmru.resources;

import tech.lmru.pojo.CommunicationTask;

public class AutotestTriggerPostponedTaskData {

    public static CommunicationTask autotestTriggerPostponedTaskSms1 = new CommunicationTask(
            "SMS",
            1,
            null,
            null,
            "TRIGGER",
            "NOT_FOUND_OR_FOUND_ALLOWED",
            "05:00:00",
            "00:00:00",
            1);

    public static CommunicationTask autotestTriggerPostponedTaskSms2 = new CommunicationTask(
            "SMS",
            1,
            null,
            25,
            "TRIGGER",
            "NOT_FOUND_OR_FOUND_ALLOWED",
            "05:00:00",
            "00:00:00",
            1);

    public static CommunicationTask autotestTriggerPostponedTaskEmail = new CommunicationTask(
            "EMAIL",
            2,
            null,
            53,
            "TRIGGER",
            "NOT_FOUND_OR_FOUND_ALLOWED",
            "05:00:00",
            "05:05:00",
            1);
}