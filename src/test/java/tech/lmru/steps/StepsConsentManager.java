package tech.lmru.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import tech.lmru.client.BaseHttpClient;
import tech.lmru.pojo.ConsentManagerContacts;

public class StepsConsentManager extends BaseHttpClient {

    // GET /communication-consent-manager/v1/{{contactId}}/customers
    @Step("Запрос в communication-consent-manager-v1 информации по contactId")
    public static Response getConsentManagerV2(String contactId) {
        return doGetRequest(CONSENT_MANAGER_V1 + "/" + contactId + "/customers");
    }

    // POST /communication-consent-manager/v3/contacts:search
    @Step("Запрос в communication-consent-manager-v3 информации по контактам/клиентам")
    public static Response postConsentManagerV3(Object ConsentManager) {
        return doPostRequest(CONSENT_MANAGER_V3 + ":search", ConsentManager);
    }

    @Step("Формируем тело запроса по контакту '{contacts}' для ConsentManager")
    public static Object setConsentManagerContactBody(String[] contacts) {
        return new ConsentManagerContacts(contacts);
    }

    //@Step("Формируем тело запроса по клиенту {clientNumber} для ConsentManager")
    //public static void setConsentManagerClientNumberBody(ConsentManagerClientNumbers consentManagerBody, String[] clientNumber) {
    //    consentManagerBody.setClientNumber(clientNumber);
    //}

    @Step("Получение contactId по контакту")
    public static String getContactId(Response responseContactId) {
        return responseContactId.then().log().all()
                .extract()
                .body()
                .jsonPath().get("id[0]");
    }

    @Step("Получение clientNumber по contactId")
    public static String getClientNumberId(Response responseClientNumberId) {
        return responseClientNumberId.then().log().all()
                .extract()
                .body()
                .jsonPath().get("clientNumber[0]");
    }

    @Step("Поиск clientNumber по контакту '{contact}'")
    public static String findClientNumberForContact(String contact) {
        String[] contacts = new String[1];
        contacts[0] = contact;
        Response responseConsentManagerV3 = postConsentManagerV3(setConsentManagerContactBody(contacts));
        String contactId = String.valueOf(getContactId(responseConsentManagerV3));
        Response responseConsentManagerV2 = getConsentManagerV2(contactId);
        return getClientNumberId(responseConsentManagerV2);
    }
}