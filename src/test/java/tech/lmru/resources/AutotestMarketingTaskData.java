package tech.lmru.resources;

import tech.lmru.pojo.CommunicationTask;

public class AutotestMarketingTaskData {

    public static CommunicationTask autotestMarketingTaskPush = new CommunicationTask(
            "PUSH",
            1,
            null,
            null,
            null,
            "NOT_FOUND_OR_FOUND_ALLOWED",
            null,
            null,
            1);

    public static CommunicationTask autotestMarketingTaskSms = new CommunicationTask(
            "SMS",
            2,
            null,
            null,
            "MARKETING",
            "NOT_FOUND_OR_FOUND_ALLOWED",
            null,
            null,
            1);

    public static CommunicationTask autotestMarketingTaskEmail = new CommunicationTask(
            "EMAIL",
            3,
            null,
            null,
            "MARKETING",
            "NOT_FOUND_OR_FOUND_ALLOWED",
            null,
            null,
            1);
}