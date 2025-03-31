package tech.lmru.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import tech.lmru.client.BaseHttpClient;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.not;

public class StepsGenericSmsHistory extends BaseHttpClient {

    // POST /generic-sms/v2/sms
    @Step("Запрос в generic-sms истории коммуникаций по контакту '{receiverContains}'")
    public static Response doGetGenericSmsHistory(String receiverContains, String startDate, String endDate) {
        return doGetRequestWithThreeQueryParams(GENERIC_SMS, "receiverContains", receiverContains, "startDate", startDate, "endDate", endDate);
    }

    @Step("Проверка отправки коммуникации на контакт '{receiver}' с сообщением '{message}'")
    public static void checkGenericSmsSending(Response responseGenericSmsHistory, String receiver, String message) {
        responseGenericSmsHistory.then().log().all()
                .statusCode(200)
                .and()
                .body("sms.find { it.receiver == '" + receiver + "' }.message", equalTo(message));
    }

    @Step("Проверка отсутствия инфомрации по отправке коммуникации на контакт '{receiver}' с сообщением '{message}'")
    public static void checkGenericSmsNotFoundCommunicationMessage(Response responseGenericSmsHistory, String receiver, String message) {
        int statusCode = responseGenericSmsHistory.getStatusCode();
        if (statusCode == 200) {
            responseGenericSmsHistory.then().log().all()
                    .statusCode(200)
                    .and()
                    .body("sms.find { it.receiver == '" + receiver + "' }.message", not(equalTo(message)));
        } else {
            responseGenericSmsHistory.then().log().all()
                    .statusCode(404)
                    .and()
                    .body("message", equalTo("No message was found according to the request."));
        }
    }
}