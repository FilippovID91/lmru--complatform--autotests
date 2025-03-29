package tech.lmru.resources;

import tech.lmru.pojo.CommunicationTask;

public class AutotestServiceSmsEmailTaskData {

    public static CommunicationTask autotestServiceSmsEmailTaskSms = new CommunicationTask(
            "SMS",
            1,
            null,
            null,
            "SERVICE",
            "FOUND_ALLOWED",
            null,
            null,
            1);

    public static CommunicationTask autotestServiceSmsEmailTaskEmail = new CommunicationTask(
            "EMAIL",
            2,
            null,
            null,
            "SERVICE",
            "FOUND_ALLOWED",
            null,
            null,
            1);
}