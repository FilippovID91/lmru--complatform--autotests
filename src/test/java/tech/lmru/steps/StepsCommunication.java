package tech.lmru.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import tech.lmru.client.BaseHttpClient;
import tech.lmru.pojo.*;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;

public class StepsCommunication extends BaseHttpClient {

    // POST /communications
    @Step("Создание новой коммуникации '{communicationName}'")
    public static Response communicationCreate(String communicationName, String communicationType, int buId, int communicationPriority) {
        CommunicationCreate communicationCreate = new CommunicationCreate(communicationName, communicationType, buId, communicationPriority);
        return doPostRequest(COMMUNICATION, communicationCreate);
    }

    // POST /communications/{communicationId}/communication-task
    @Step("Создание новой таски для коммуникации id:'{communicationId}'")
    public static Response communicationTaskCreate(Integer communicationId, Integer templateId, CommunicationTask communicationTask) {
        communicationTask.setTemplateId(Integer.valueOf(templateId));
        return doPostRequest(COMMUNICATION + "/" + communicationId + COMMUNICATION_TASK, communicationTask);
    }

    // POST /communications:send
    @Step("Отправка коммуникации '{communicationName}' на контакт '{contact}' и получение 'communicationEventId'")
    public static String communicationSend(String communicationName, String contact, String contactType,
                                           String autotestNumber, String autotestId, String storeId) {

        CommunicationSendContact communicationContact = new CommunicationSendContact(contact, contactType);
        CommunicationSendVariables communicationVariables2 = new CommunicationSendVariables("autotestId", autotestId);

        Set<CommunicationSendContact> contacts = new HashSet<CommunicationSendContact>();
        contacts.add(communicationContact);

        Set<CommunicationSendVariables> variables = new HashSet<CommunicationSendVariables>();

        if (autotestNumber != null) {
            CommunicationSendVariables communicationVariables1 = new CommunicationSendVariables("autotestNumber", autotestNumber);
            variables.add(communicationVariables1);
        }

        variables.add(communicationVariables2);

        if (storeId != null) {
            CommunicationSendVariables communicationVariables3 = new CommunicationSendVariables("storeId", storeId);
            variables.add(communicationVariables3);
        }

        CommunicationSend communicationSend  = new CommunicationSend(
                communicationName,
                contacts,
                variables,
                storeId);

        Response responseCommunicationSend = doPostRequest(COMMUNICATION_SEND, communicationSend);
        return responseCommunicationSend.jsonPath().get("communicationEventId");
    }

    // POST /communications:send
    @Step("Отправка коммуникации '{communicationName}' на контакты '{contact1}', '{contact2}' и получение 'communicationEventId'")
    public static String communicationWithTwoContactSend(String communicationName, String contact1, String contactType1, String contact2, String contactType2,
                                           String autotestNumber, String autotestId, String storeId) {

        CommunicationSendContact communicationContact1 = new CommunicationSendContact(contact1, contactType1);
        CommunicationSendContact communicationContact2 = new CommunicationSendContact(contact2, contactType2);
        CommunicationSendVariables communicationVariables2 = new CommunicationSendVariables("autotestId", autotestId);

        Set<CommunicationSendContact> contacts = new HashSet<CommunicationSendContact>();
        contacts.add(communicationContact1);
        contacts.add(communicationContact2);

        Set<CommunicationSendVariables> variables = new HashSet<CommunicationSendVariables>();

        if (autotestNumber != null) {
            CommunicationSendVariables communicationVariables1 = new CommunicationSendVariables("autotestNumber", autotestNumber);
            variables.add(communicationVariables1);
        }

        variables.add(communicationVariables2);

        if (storeId != null) {
            CommunicationSendVariables communicationVariables3 = new CommunicationSendVariables("storeId", storeId);
            variables.add(communicationVariables3);
        }

        CommunicationSend communicationSend  = new CommunicationSend(
                communicationName,
                contacts,
                variables,
                storeId);

        Response responseCommunicationSend = doPostRequest(COMMUNICATION_SEND, communicationSend);
        return responseCommunicationSend.jsonPath().get("communicationEventId");
    }

    @Step("Проверка, что тело ответа сожержит ошибку: Attribute 'communicationName' must not be null")
    public static void checkCommunicationNotSent(Response responseCommunicationSend) {
        responseCommunicationSend
                .then().log().all()
                .statusCode(200)
                .and()
                .body("errors.details" , equalTo("Attribute 'communicationName' must not be null"));
    }


    // GET /communications
    @Step("Запрос информации по коммуникации")
    public static Response getCommunication(String communicationName) {
        return doGetRequestWithQueryParams(COMMUNICATION, "communicationName", communicationName);
    }

    @Step("Получение ID коммуникации")
    public static Integer getCommunicationId(Response responseCommunicationId) {
        return responseCommunicationId.then().log().all()
                .extract()
                .body()
                .jsonPath().get("id[0]");
    }
/*
    @Step("Получение ID коммуникации. Если коммуникация не найдена, то создание новой коммуникации")
    public static Integer checkOrCreateCommunication(String communicationName, String communicationType, int buId, int communicationPriority) {
        Response response = getCommunication(communicationName);
        Integer communicationId = getCommunicationId(response);
        if (communicationId == null) {
            communicationCreate(communicationName, communicationType, buId, communicationPriority);
        }
        Response responseNew = getCommunication(communicationName);
        communicationId = getCommunicationId(responseNew);
        return communicationId;
    }*/

}