package tech.lmru.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import tech.lmru.client.BaseHttpClient;
import tech.lmru.pojo.CommunicationHistory;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class StepsCommunicationHistory extends BaseHttpClient {

    // POST /communication-history/v2/history:search
    @Step("Запрос в communication-history истории коммуникаций по контакту")
    public static Response postCommunicationHistory(Object CommunicationHistoryBody) {
        return doPostRequest(COMMUNICATION_HISTORY, CommunicationHistoryBody);
    }

    // Step("Формируем тело запроса для CommunicationHistory")
    public static Object communicationHistoryBody(String communicationName, String contactPhone, String channelNameSms, String historyStartSearchDate, String historyEndSearchDate) {
        return new CommunicationHistory(communicationName, contactPhone, channelNameSms, historyStartSearchDate, historyEndSearchDate);
    }

    @Step("Проверка отправки коммуникации 'communicationEventId': '{communicationEventId}', 'communicationName': '{communicationName}', 'channelName': '{channelName}', 'contact': '{contact}'")
    public static void checkCommunicationHistorySending(Response responseCommunicationHistory, String communicationEventId, String communicationName, String channelName, String contact) {
        responseCommunicationHistory.then().log().all()
                .statusCode(200)
                .and()
                .body("events.find { it.communicationEventId == '" + communicationEventId + "' }.communicationName", equalTo(communicationName))
                .and()
                .body("events.find { it.communicationEventId == '" + communicationEventId + "' }.channelName", equalTo(channelName))
                .and()
                .body("events.find { it.communicationEventId == '" + communicationEventId + "' }.contact", equalTo(contact));
    }

    @Step("Проверка НЕ отправки коммуникации 'communicationEventId': '{communicationEventId}'")
    public static void checkCommunicationNotSent(Response responseCommunicationHistory, String communicationEventId) {
        responseCommunicationHistory.then().log().all()
                .statusCode(200)
                .and()
                .body("events.find { it.communicationEventId == '" + communicationEventId + "' }", equalTo(null));
    }

    @Step("Проверка НЕ отправки из-за карантина коммуникации 'communicationEventId': '{communicationEventId}', 'contact': '{contact}'")
    public static void checkCommunicationHistoryQuarantine(Response responseCommunicationHistory, String communicationEventId, String contact) {
        responseCommunicationHistory.then().log().all()
                .statusCode(200)
                .and()
                .body("events.find { it.communicationEventId == '" + communicationEventId + "' }.errors.description", hasItems("Not allowed to communicate with [" + contact + "] due to quarantine"));
    }

    @Step("Проверка НЕ отправки из-за карантина коммуникации 'communicationEventId': '{communicationEventId}', 'contact': '{contact}'")
    public static void checkCommunicationHistoryQuarantineNotFound(Response responseCommunicationHistory, String communicationEventId, String contact) {
        responseCommunicationHistory.then().log().all()
                .statusCode(200)
                .and()
                .body("events.find { it.communicationEventId == '" + communicationEventId + "' }.errors.description", not("Not allowed to communicate with [" + contact + "] due to quarantine"));
    }

    @Step("Проверка НЕ отправки из-за ошибки сборки шаблона коммуникации'communicationEventId': '{communicationEventId}', 'contact': '{contact}'")
    public static void checkCommunicationHistoryTemplateBuildFailed(Response responseCommunicationHistory, String communicationEventId) {
        responseCommunicationHistory.then().log().all()
                .statusCode(200)
                .and()
                .body("events.find { it.communicationEventId == '" + communicationEventId + "' }.errors.code", hasItems("COMPLATFORM_ERROR"));
    }
}