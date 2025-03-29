package tech.lmru.communications;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import tech.lmru.client.CommunicationDatabase;

import java.sql.SQLException;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static tech.lmru.resources.AutotestMarketingTaskData.*;
import static tech.lmru.resources.AutotestServiceEmailTaskData.*;
import static tech.lmru.resources.AutotestServiceKzTaskData.*;
import static tech.lmru.resources.AutotestServicePostponedTaskData.*;
import static tech.lmru.resources.AutotestServiceSmsEmailTaskData.*;
import static tech.lmru.resources.AutotestServiceTaskData.*;
import static tech.lmru.resources.AutotestTriggerEmailTaskData.*;
import static tech.lmru.resources.AutotestTriggerPostponedTaskData.*;
import static tech.lmru.resources.AutotestTriggerSmsEmailTaskData.*;
import static tech.lmru.resources.AutotestTriggerTaskData.*;
import static tech.lmru.resources.TemplateData.*;
import static tech.lmru.steps.StepsCommunication.*;
import static tech.lmru.steps.StepsTemplateManager.*;


public class CommunicationsCreate {
    static Response response;

    @Step("Проверка, что коммуникация 'autotestService' уже создана")
    public static void communicationAutotestServiceCheckOrCreate() throws SQLException {
        List<Integer> checkCommunicationId = CommunicationDatabase.getCommunicationId("autotestService");

        if (checkCommunicationId.isEmpty()) {
            step("Создание коммуникации 'autotestService' с шаблонами и тасками");
            communicationCreate("autotestService", "SERVICE", 9, 2);
            Integer communicationId = (CommunicationDatabase.getCommunicationId("autotestService")).get(0);

            communicationTaskCreate(communicationId, getOrCreateTemplateId(pushTemplate), autotestServiceTaskPush1);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(pushTemplateWithStoreId), autotestServiceTaskPush2);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestServiceTaskSms1);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplateWithStoreId), autotestServiceTaskSms2);
            communicationTaskCreate(communicationId, getServiceTemplateIdFromPmp(), autotestServiceTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestServiceKz' уже создана")
    public static void communicationAutotestServiceKzCheckOrCreate() throws SQLException {
        List<Integer> checkCommunicationId = CommunicationDatabase.getCommunicationId("autotestServiceKz");

        if (checkCommunicationId.isEmpty()) {
            step("Создание коммуникации 'autotestServiceKz' с шаблонами и тасками");
            communicationCreate("autotestServiceKz", "SERVICE", 40, 2);
            Integer communicationId = (CommunicationDatabase.getCommunicationId("autotestServiceKz")).get(0);

            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestServiceKzTaskSms1);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplateWithStoreId), autotestServiceKzTaskSms2);
        }
    }

    @Step("Проверка, что коммуникация 'autotestServiceEmail' уже создана")
    public static void communicationAutotestServiceEmailCheckOrCreate() throws SQLException {
        List<Integer> checkCommunicationId = CommunicationDatabase.getCommunicationId("autotestServiceEmail");

        if (checkCommunicationId.isEmpty()) {
            step("Создание коммуникации 'autotestServiceEmail' с шаблонами и тасками");
            communicationCreate("autotestServiceEmail", "SERVICE", 9, 2);
            Integer communicationId = (CommunicationDatabase.getCommunicationId("autotestServiceEmail")).get(0);

            communicationTaskCreate(communicationId, getServiceTemplateIdFromPmp(), autotestServiceEmailTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestServiceSmsEmail' уже создана")
    public static void communicationAutotestServiceSmsEmailCheckOrCreate() throws SQLException {
        List<Integer> checkCommunicationId = CommunicationDatabase.getCommunicationId("autotestServiceSmsEmail");

        if (checkCommunicationId.isEmpty()) {
            step("Создание коммуникации 'autotestServiceSmsEmail' с шаблонами и тасками");
            communicationCreate("autotestServiceSmsEmail", "SERVICE", 9, 2);
            Integer communicationId = (CommunicationDatabase.getCommunicationId("autotestServiceSmsEmail")).get(0);

            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestServiceSmsEmailTaskSms);
            communicationTaskCreate(communicationId, getServiceTemplateIdFromPmp(), autotestServiceSmsEmailTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestServicePostponed' уже создана")
    public static void communicationAutotestServicePostponedCheckOrCreate() throws SQLException {
        List<Integer> checkCommunicationId = CommunicationDatabase.getCommunicationId("autotestServicePostponed");

        if (checkCommunicationId.isEmpty()) {
            step("Создание коммуникации 'autotestServicePostponed' с шаблонами и тасками");
            communicationCreate("autotestServicePostponed", "SERVICE", 9, 2);
            Integer communicationId = (CommunicationDatabase.getCommunicationId("autotestServicePostponed")).get(0);

            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestServicePostponedTaskSms1);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestServicePostponedTaskSms2);
            communicationTaskCreate(communicationId, getServiceTemplateIdFromPmp(), autotestServicePostponedTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestTrigger' уже создана")
    public static void communicationAutotestTriggerCheckOrCreate() throws SQLException {
        List<Integer> checkCommunicationId = CommunicationDatabase.getCommunicationId("autotestTrigger");

        if (checkCommunicationId.isEmpty()) {
            step("Создание коммуникации 'autotestTrigger' с шаблонами и тасками");
            communicationCreate("autotestTrigger", "TRIGGER", 9, 3);
            Integer communicationId = (CommunicationDatabase.getCommunicationId("autotestTrigger")).get(0);

            communicationTaskCreate(communicationId, getOrCreateTemplateId(pushTemplate), autotestTriggerTaskPush);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestTriggerTaskSms);
            communicationTaskCreate(communicationId, getTriggerTemplateIdFromPmp(), autotestTriggerTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestTriggerEmail' уже создана")
    public static void communicationAutotestTriggerEmailCheckOrCreate() throws SQLException {
        List<Integer> checkCommunicationId = CommunicationDatabase.getCommunicationId("autotestTriggerEmail");

        if (checkCommunicationId.isEmpty()) {
            step("Создание коммуникации 'autotestTriggerEmail' с шаблонами и тасками");
            communicationCreate("autotestTriggerEmail", "TRIGGER", 9, 3);
            Integer communicationId = (CommunicationDatabase.getCommunicationId("autotestTriggerEmail")).get(0);

            communicationTaskCreate(communicationId, getTriggerTemplateIdFromPmp(), autotestTriggerEmailTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestTriggerSmsEmail' уже создана")
    public static void communicationAutotestTriggerSmsEmailCheckOrCreate() throws SQLException {
        List<Integer> checkCommunicationId = CommunicationDatabase.getCommunicationId("autotestTriggerSmsEmail");

        if (checkCommunicationId.isEmpty()) {
            step("Создание коммуникации 'autotestTriggerSmsEmail' с шаблонами и тасками");
            communicationCreate("autotestTriggerSmsEmail", "TRIGGER", 9, 3);
            Integer communicationId = (CommunicationDatabase.getCommunicationId("autotestTriggerSmsEmail")).get(0);

            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestTriggerSmsEmailTaskSms);
            communicationTaskCreate(communicationId, getTriggerTemplateIdFromPmp(), autotestTriggerSmsEmailTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestTriggerPostponed' уже создана")
    public static void communicationAutotestTriggerPostponedCheckOrCreate() throws SQLException {
        List<Integer> checkCommunicationId = CommunicationDatabase.getCommunicationId("autotestTriggerPostponed");

        if (checkCommunicationId.isEmpty()) {
            step("Создание коммуникации 'autotestTriggerPostponed' с шаблонами и тасками");
            communicationCreate("autotestTriggerPostponed", "TRIGGER", 9, 3);
            Integer communicationId = (CommunicationDatabase.getCommunicationId("autotestTriggerPostponed")).get(0);

            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestTriggerPostponedTaskSms1);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestTriggerPostponedTaskSms2);
            communicationTaskCreate(communicationId, getTriggerTemplateIdFromPmp(), autotestTriggerPostponedTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestMarketing' уже создана")
    public static void communicationAutotestMarketingCheckOrCreate() throws SQLException {
        List<Integer> checkCommunicationId = CommunicationDatabase.getCommunicationId("autotestMarketing");

        if (checkCommunicationId.isEmpty()) {
            step("Создание коммуникации 'autotestMarketing' с шаблонами и тасками");
            communicationCreate("autotestMarketing", "MARKETING", 9, 3);
            Integer communicationId = (CommunicationDatabase.getCommunicationId("autotestMarketing")).get(0);

            communicationTaskCreate(communicationId, getOrCreateTemplateId(pushTemplate), autotestMarketingTaskPush);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestMarketingTaskSms);
            communicationTaskCreate(communicationId, getTriggerTemplateIdFromPmp(), autotestMarketingTaskEmail);
        }
    }
}