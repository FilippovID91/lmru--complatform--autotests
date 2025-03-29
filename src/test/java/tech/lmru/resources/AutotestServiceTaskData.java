package tech.lmru.resources;

import tech.lmru.pojo.CommunicationTask;

public class AutotestServiceTaskData {

    public static CommunicationTask autotestServiceTaskPush1 = new CommunicationTask(
            "PUSH",
            1,
            null,
            null,
            null,
            "FOUND_ALLOWED",
            null,
            null,
            1);

    public static CommunicationTask autotestServiceTaskPush2 = new CommunicationTask(
            "PUSH",
            1,
            null,
            25,
            "SERVICE",
            "FOUND_ALLOWED",
            null,
            null,
            1);

    public static CommunicationTask autotestServiceTaskSms1 = new CommunicationTask(
            "SMS",
            2,
            null,
            null,
            "SERVICE",
            "FOUND_ALLOWED",
            null,
            null,
            1);

    public static CommunicationTask autotestServiceTaskSms2 = new CommunicationTask(
            "SMS",
            2,
            null,
            25,
            "SERVICE",
            "FOUND_ALLOWED",
            null,
            null,
            1);

    public static CommunicationTask autotestServiceTaskEmail = new CommunicationTask(
            "EMAIL",
            3,
            null,
            25,
            "SERVICE",
            "FOUND_ALLOWED",
            null,
            null,
            1);
}