package tech.lmru.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Objects;

import static io.restassured.RestAssured.given;

public class BaseHttpClient {
    //static String environment_jenkins = "STAGE";

public static String environment() {
    String environment_jenkins = System.getProperty("ENVIRONMENT_JENKINS");
    if (environment_jenkins.equals("STAGE")) {
        BASE_URI = "https://complatform-yc-customer-stage.apps.lmru.tech";}
    else if (environment_jenkins.equals("TEST")) {
        BASE_URI = "https://complatform-test-yc-customer-stage.apps.lmru.tech";
    }
    else {System.out.println("Environment can be TEST or STAGE");};
    return BASE_URI;
}

public static String BASE_URI = environment();
    public static String COMMUNICATION = "/communication/v1/communications"; //communications
    public static String COMMUNICATION_SEND = "/communication/v1/communications:send";
    public static String COMMUNICATION_TASK = "/communication-tasks";
    public static String COMMUNICATION_HISTORY = "/communication-history/v2/history:search";
    public static String TEMPLATE_MANAGER = "/template-manager/v1/templates";
    public static String CONSENT_MANAGER_V1 = "/communication-consent-manager/v1/contacts";
    public static String CONSENT_MANAGER_V3 = "/communication-consent-manager/v3/contacts";
    public static String PMP_MESSAGE = "/pmp-message/v1/templates";
    public static String GENERIC_SMS = "/generic-sms/v2/sms";
    public static String ESTIMATOR = "/estimator2/v1/estimations";

    public static Response doGetRequest(String uri) {
        return given().log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(uri);
    }

    public static Response doGetRequestWithQueryParams(String uri, String queryParam, String query) {
        return given().log().all()
                .filter(new AllureRestAssured())
                .queryParam(queryParam, query)
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(uri);
    }

    public static Response doGetRequestWithThreeQueryParams(String uri, String queryParam1, String query1, String queryParam2, String query2,String queryParam3, String query3) {
        return given().log().all()
                .filter(new AllureRestAssured())
                .queryParam(queryParam1, query1)
                .queryParam(queryParam2, query2)
                .queryParam(queryParam3, query2)
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(uri);
    }

    protected static Response doPostRequest (String uri, Object body) {
        return given().log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(body)
                .when()
                .post(uri);
    }

    protected static Response doPostRequestWithQueryParams (String uri, String queryParam, String query, Object body) {
        return given().log().all()
                .filter(new AllureRestAssured())
                .queryParam(queryParam, query)
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(body)
                .when()
                .post(uri);
    }

    protected static Response doDeleteRequest (String uri) {
        return given().log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .delete(uri);
    }
}