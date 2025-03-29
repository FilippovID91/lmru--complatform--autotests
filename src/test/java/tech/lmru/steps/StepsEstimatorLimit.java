package tech.lmru.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import tech.lmru.client.BaseHttpClient;
import tech.lmru.pojo.EstimatorLimit;

import static org.hamcrest.CoreMatchers.equalTo;

public class StepsEstimatorLimit extends BaseHttpClient {

    // POST /estimator2/v1/estimations/{communicationId}:allowed
    @Step("Запрос в estimator2 для проверки лимитов на отправку коммуникаций")
    public static Response postEstimatorLimit(String communicationId, String contact, String channelName) {
        EstimatorLimit estimatorLimit = new EstimatorLimit(contact, channelName);
        return doPostRequest(ESTIMATOR + "/" + communicationId + ":allowed", estimatorLimit);
    }

    @Step("Проверка лимитов на отправку коммуникаций'")
    public static void checkEstimatorLimit(Response estimatorLimit, boolean allowed) {
        estimatorLimit.then().log().all()
                .statusCode(200)
                .and()
                .body("allowed", equalTo(allowed));
    }
}