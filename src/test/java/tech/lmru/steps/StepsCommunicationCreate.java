package tech.lmru.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import tech.lmru.client.CommunicationDatabase;

import java.sql.SQLException;

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

public class StepsCommunicationCreate {
    static Response response;

    @Step("Проверка, что коммуникация 'autotestService' уже создана")
    public static void communicationAutotestServiceCheckOrCreate() {
        Integer checkCommunicationId = getCommunicationId(getCommunication("autotestService"));

        if (checkCommunicationId == null) {
            step("Создание коммуникации 'autotestService' с шаблонами и тасками");
            communicationCreate("autotestService", "SERVICE", 9, 2);
            Integer communicationId = getCommunicationId(getCommunication("autotestService"));

            communicationTaskCreate(communicationId, getOrCreateTemplateId(pushTemplate), autotestServiceTaskPush1);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(pushTemplateWithStoreId), autotestServiceTaskPush2);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestServiceTaskSms1);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplateWithStoreId), autotestServiceTaskSms2);
            communicationTaskCreate(communicationId, getServiceTemplateIdFromPmp(), autotestServiceTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestServiceKz' уже создана")
    public static void communicationAutotestServiceKzCheckOrCreate() {
        Integer checkCommunicationId = getCommunicationId(getCommunication("autotestServiceKz"));

        if (checkCommunicationId == null) {
            step("Создание коммуникации 'autotestServiceKz' с шаблонами и тасками");
            communicationCreate("autotestServiceKz", "SERVICE", 40, 2);
            Integer communicationId = getCommunicationId(getCommunication("autotestServiceKz"));

            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestServiceKzTaskSms1);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplateWithStoreId), autotestServiceKzTaskSms2);
        }
    }

    @Step("Проверка, что коммуникация 'autotestServiceEmail' уже создана")
    public static void communicationAutotestServiceEmailCheckOrCreate() {
        Integer checkCommunicationId = getCommunicationId(getCommunication("autotestServiceEmail"));

        if (checkCommunicationId == null) {
            step("Создание коммуникации 'autotestServiceEmail' с шаблонами и тасками");
            communicationCreate("autotestServiceEmail", "SERVICE", 9, 2);
            Integer communicationId = getCommunicationId(getCommunication("autotestServiceEmail"));

            communicationTaskCreate(communicationId, getServiceTemplateIdFromPmp(), autotestServiceEmailTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestServiceSmsEmail' уже создана")
    public static void communicationAutotestServiceSmsEmailCheckOrCreate() {
        Integer checkCommunicationId = getCommunicationId(getCommunication("autotestServiceSmsEmail"));

        if (checkCommunicationId == null) {
            step("Создание коммуникации 'autotestServiceSmsEmail' с шаблонами и тасками");
            communicationCreate("autotestServiceSmsEmail", "SERVICE", 9, 2);
            Integer communicationId = getCommunicationId(getCommunication("autotestServiceSmsEmail"));

            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestServiceSmsEmailTaskSms);
            communicationTaskCreate(communicationId, getServiceTemplateIdFromPmp(), autotestServiceSmsEmailTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestServicePostponed' уже создана")
    public static void communicationAutotestServicePostponedCheckOrCreate() {
        Integer checkCommunicationId = getCommunicationId(getCommunication("autotestServicePostponed"));

        if (checkCommunicationId == null) {
            step("Создание коммуникации 'autotestServicePostponed' с шаблонами и тасками");
            communicationCreate("autotestServicePostponed", "SERVICE", 9, 2);
            Integer communicationId = getCommunicationId(getCommunication("autotestServicePostponed"));

            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestServicePostponedTaskSms1);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestServicePostponedTaskSms2);
            communicationTaskCreate(communicationId, getServiceTemplateIdFromPmp(), autotestServicePostponedTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestTrigger' уже создана")
    public static void communicationAutotestTriggerCheckOrCreate() {
        Integer checkCommunicationId = getCommunicationId(getCommunication("autotestTrigger"));

        if (checkCommunicationId == null) {
            step("Создание коммуникации 'autotestTrigger' с шаблонами и тасками");
            communicationCreate("autotestTrigger", "TRIGGER", 9, 3);
            Integer communicationId = getCommunicationId(getCommunication("autotestTrigger"));

            communicationTaskCreate(communicationId, getOrCreateTemplateId(pushTemplate), autotestTriggerTaskPush);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestTriggerTaskSms);
            communicationTaskCreate(communicationId, getTriggerTemplateIdFromPmp(), autotestTriggerTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestTriggerEmail' уже создана")
    public static void communicationAutotestTriggerEmailCheckOrCreate() {
        Integer checkCommunicationId = getCommunicationId(getCommunication("autotestTriggerEmail"));

        if (checkCommunicationId == null) {
            step("Создание коммуникации 'autotestTriggerEmail' с шаблонами и тасками");
            communicationCreate("autotestTriggerEmail", "TRIGGER", 9, 3);
            Integer communicationId = getCommunicationId(getCommunication("autotestTriggerEmail"));

            communicationTaskCreate(communicationId, getTriggerTemplateIdFromPmp(), autotestTriggerEmailTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestTriggerSmsEmail' уже создана")
    public static void communicationAutotestTriggerSmsEmailCheckOrCreate() throws SQLException {
        Integer checkCommunicationId = getCommunicationId(getCommunication("autotestTriggerSmsEmail"));

        if (checkCommunicationId == null) {
            step("Создание коммуникации 'autotestTriggerSmsEmail' с шаблонами и тасками");
            communicationCreate("autotestTriggerSmsEmail", "TRIGGER", 9, 3);
            Integer communicationId = (CommunicationDatabase.getCommunicationId("autotestTriggerSmsEmail")).get(0);

            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestTriggerSmsEmailTaskSms);
            communicationTaskCreate(communicationId, getTriggerTemplateIdFromPmp(), autotestTriggerSmsEmailTaskEmail);
        }
    }

    @Step("Проверка, что коммуникация 'autotestTriggerPostponed' уже создана")
    public static void communicationAutotestTriggerPostponedCheckOrCreate() throws SQLException {
        Integer checkCommunicationId = getCommunicationId(getCommunication("autotestTriggerPostponed"));

        if (checkCommunicationId == null) {
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
        Integer checkCommunicationId = getCommunicationId(getCommunication("autotestMarketing"));

        if (checkCommunicationId == null) {
            step("Создание коммуникации 'autotestMarketing' с шаблонами и тасками");
            communicationCreate("autotestMarketing", "MARKETING", 9, 3);
            Integer communicationId = (CommunicationDatabase.getCommunicationId("autotestMarketing")).get(0);

            communicationTaskCreate(communicationId, getOrCreateTemplateId(pushTemplate), autotestMarketingTaskPush);
            communicationTaskCreate(communicationId, getOrCreateTemplateId(smsTemplate), autotestMarketingTaskSms);
            communicationTaskCreate(communicationId, getTriggerTemplateIdFromPmp(), autotestMarketingTaskEmail);
        }
    }
}