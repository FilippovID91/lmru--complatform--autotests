package tech.lmru.resources;

import tech.lmru.pojo.CommunicationTask;

public class AutotestServicePostponedTaskData {

    public static CommunicationTask autotestServicePostponedTaskSms1 = new CommunicationTask(
            "SMS",
            1,
            null,
            null,
            "SERVICE",
            "FOUND_ALLOWED",
            "05:00:00",
            "00:00:00",
            1);

    public static CommunicationTask autotestServicePostponedTaskSms2 = new CommunicationTask(
            "SMS",
            1,
            null,
            25,
            "SERVICE",
            "FOUND_ALLOWED",
            "05:00:00",
            "00:00:00",
            1);

    public static CommunicationTask autotestServicePostponedTaskEmail = new CommunicationTask(
            "EMAIL",
            2,
            null,
            53,
            "SERVICE",
            "FOUND_ALLOWED",
            "05:00:00",
            "05:05:00",
            1);
}