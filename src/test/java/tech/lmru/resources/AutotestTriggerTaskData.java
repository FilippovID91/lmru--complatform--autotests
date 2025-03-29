package tech.lmru.resources;

import tech.lmru.pojo.CommunicationTask;

public class AutotestTriggerTaskData {

    public static CommunicationTask autotestTriggerTaskPush = new CommunicationTask(
            "PUSH",
            1,
            null,
            null,
            "TRIGGER",
            "FOUND_ALLOWED",
            null,
            null,
            1);

    public static CommunicationTask autotestTriggerTaskSms = new CommunicationTask(
            "SMS",
            2,
            null,
            null,
            "TRIGGER",
            "FOUND_ALLOWED",
            null,
            null,
            1);

    public static CommunicationTask autotestTriggerTaskEmail = new CommunicationTask(
            "EMAIL",
            3,
            null,
            null,
            "TRIGGER",
            "FOUND_ALLOWED",
            null,
            null,
            1);
}