package tech.lmru.resources;

import tech.lmru.pojo.CommunicationTask;

public class AutotestTriggerSmsEmailTaskData {

    public static CommunicationTask autotestTriggerSmsEmailTaskSms = new CommunicationTask(
            "SMS",
            1,
            null,
            null,
            null,
            "FOUND_ALLOWED",
            null,
            null,
            1);

    public static CommunicationTask autotestTriggerSmsEmailTaskEmail = new CommunicationTask(
            "EMAIL",
            2,
            null,
            null,
            "TRIGGER",
            "FOUND_ALLOWED",
            null,
            null,
            1);
}