package tech.lmru.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import tech.lmru.steps.StepsCommunication;

import java.sql.SQLException;

import static java.lang.Thread.sleep;
import static tech.lmru.Constant.*;
import static tech.lmru.client.JdbcClient.closeConnectionDB;
import static tech.lmru.client.JdbcClient.getConnectionDB;
import static tech.lmru.communications.CommunicationsCreate.*;
import static tech.lmru.steps.StepsCommunication.communicationSend;
import static tech.lmru.steps.StepsCommunication.communicationWithTwoContactSend;
import static tech.lmru.steps.StepsCommunicationHistory.*;
import static tech.lmru.steps.StepsConsentManager.*;
import static tech.lmru.steps.StepsEstimatorLimit.checkEstimatorLimit;
import static tech.lmru.steps.StepsEstimatorLimit.postEstimatorLimit;
import static tech.lmru.steps.StepsGenericSmsHistory.*;

@Epic("Complatform autotest")
@Feature("Complatform autotest")
@Story("Отложенная отправка коммуникаций")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class MainContactAndConsentServiceTest {
    private String communicationEventId1;
    private String communicationEventId2;
    private Response responseGenericSmsHistory1;
    private Response responseGenericSmsHistory2;
    private Response responseCommunicationHistory1;
    private Response responseCommunicationHistory2;

    @BeforeEach
    public void setup() throws SQLException, ClassNotFoundException {
        getConnectionDB();
    }

    @AfterEach
    public void teardown() throws SQLException, ClassNotFoundException {
        closeConnectionDB();
    }

    @Test
    @Order(1)
    @AllureId("281971")
    @DisplayName("mainContactSearchingByClientNumberServiceV1Test_1")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' (p2). Сценарий 237726 v1")
    public void mainContactSearchingByClientNumberServiceV1Test_1() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "281971";
        String contactPhoneFake1 = "79041111111";
        String contactPhoneFake2 = "79041111112";
        String contactPushFake1 = "id7139test1";
        String contactPushFake2 = "id7139test2";
        String communicationName1 = "autotestService";
        String communicationName2 = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPushFake1);

        communicationAutotestServiceCheckOrCreate();
        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        communicationEventId2 = communicationSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake1, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory1, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake2, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory2, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake2);
    }

    @Test
    @Order(2)
    @AllureId("282018")
    @DisplayName("mainContactSearchingByClientNumberServiceV2Test_2")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' (p2). Сценарий 237726 v2")
    public void mainContactSearchingByClientNumberServiceV2Test_2() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282018";
        String contactPhoneFake1 = "79041111115";
        String contactPhoneFake2 = "79041111116";
        String contactPushFake1 = "id7139test5";
        String contactPushFake2 = "id7139test6";
        String communicationName1 = "autotestService";
        String communicationName2 = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPushFake1);

        communicationAutotestServiceCheckOrCreate();
        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        communicationEventId2 = communicationSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake1, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory1, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake2, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory2, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake2);
    }

    @Test
    @Order(3)
    @AllureId("282095")
    @DisplayName("mainContactSearchingByClientNumberServiceV3Test_3")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' (p2). Сценарий 237726 v3")
    public void mainContactSearchingByClientNumberServiceV3Test_3() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282095";
        String contactPhoneFake1 = "79041111117";
        String contactPhoneFake2 = "79041111118";
        String contactPushFake1 = "id7139test7";
        String contactPushFake2 = "id7139test8";
        String communicationName1 = "autotestService";
        String communicationName2 = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPushFake1);

        communicationAutotestServiceCheckOrCreate();
        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        communicationEventId2 = communicationSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake1, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory1, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake2, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory2, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake2);
    }

    @Test
    @Order(4)
    @AllureId("282110")
    @DisplayName("mainContactSearchingByPhoneServiceV1Test_4")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'phone' (p2). Сценарий 237978 v1")
    public void mainContactSearchingByPhoneServiceV1Test_4() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282110";
        String contactPhoneFake1 = "79041111110";
        String communicationName1 = "autotestServiceSmsEmail";

        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationSend(communicationName1, contactPhoneFake1, CONTACT_TYPE_PHONE,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
    }

    @Test
    @Order(5)
    @AllureId("282135")
    @DisplayName("mainContactSearchingByPhoneServiceV2Test_5")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'phone' (p2). Сценарий 237978 v2")
    public void mainContactSearchingByPhoneServiceV2Test_5() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282135";
        String contactPhoneFake1 = "79041111119";
        String communicationName1 = "autotestServiceSmsEmail";

        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationSend(communicationName1, contactPhoneFake1, CONTACT_TYPE_PHONE,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
    }

    @Test
    @Order(6)
    @AllureId("282136")
    @DisplayName("mainContactSearchingByClientNumberAndEmailServiceV1Test_6")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'email' (p2). Сценарий 238029 v1")
    public void mainContactSearchingByClientNumberAndEmailServiceV1Test_6() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282136";
        String contactPhoneFake1 = "79041111124";
        String contactPhoneFake2 = "79041111125";
        String contactEmailFake1 = "7139test@gmail.com";
        String contactEmailFake2 = "7139test13@gmail.com";
        String communicationName1 = "autotestServiceEmail";
        String communicationName2 = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact("+" + contactPhoneFake1);

        communicationAutotestServiceEmailCheckOrCreate();
        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactEmailFake1, CONTACT_TYPE_EMAIL, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactEmailFake1, CONTACT_TYPE_EMAIL, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake1, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory1, communicationEventId1, communicationName1, CHANNEL_NAME_EMAIL, contactEmailFake1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake2, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory2, communicationEventId1);
    }

    @Test
    @Order(7)
    @AllureId("282154")
    @DisplayName("mainContactSearchingByClientNumberAndEmailServiceV2Test_7")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'email' (p2). Сценарий 238029 v2")
    public void mainContactSearchingByClientNumberAndEmailServiceV2Test_7() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282154";
        String contactPhoneFake1 = "79041111126";
        String contactPhoneFake2 = "79041111127";
        String contactEmailFake1 = "7139test@gmail.com";
        String contactEmailFake2 = "7139test14@gmail.com";
        String communicationName1 = "autotestServiceEmail";
        String communicationName2 = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact("+" + contactPhoneFake1);

        communicationAutotestServiceEmailCheckOrCreate();
        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactEmailFake1, CONTACT_TYPE_EMAIL, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactEmailFake1, CONTACT_TYPE_EMAIL, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake1, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory1, communicationEventId1, communicationName1, CHANNEL_NAME_EMAIL, contactEmailFake1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake2, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory2, communicationEventId1);
    }

    @Test
    @Order(8)
    @AllureId("282155")
    @DisplayName("mainContactSearchingByClientNumberAndPhoneServiceV1Test_8")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'phone' (p2). Сценарий 237979 v1")
    public void mainContactSearchingByClientNumberAndPhoneServiceV1Test_8() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282155";
        String contactPhoneFake1 = "79041111110";
        String contactPhoneFake2 = "79041111113";
        String contactPushFake1 = "id7139test3";
        String contactPushFake2 = "id7139test4";
        String communicationName1 = "autotestService";
        String communicationName2 = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPushFake1);

        communicationAutotestServiceCheckOrCreate();
        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake1, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory1, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake2, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory2, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake2);
    }

    @Test
    @Order(9)
    @AllureId("282156")
    @DisplayName("mainContactSearchingByClientNumberAndPhoneServiceV2Test_9")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'phone' (p2). Сценарий 237979 v2")
    public void mainContactSearchingByClientNumberAndPhoneServiceV2Test_9() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282156";
        String contactPhoneFake1 = "79041111120";
        String contactPhoneFake2 = "79041111115";
        String contactPushFake1 = "id7139test5";
        String contactPushFake2 = "id7139test6";
        String communicationName1 = "autotestService";
        String communicationName2 = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPushFake1);

        communicationAutotestServiceCheckOrCreate();
        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake1, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory1, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake2, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory2, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake2);
    }

    @Test
    @Order(10)
    @DisplayName("mainContactSearchingByClientNumberAndPhoneServiceV3Test_10")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'phone' (p2). Сценарий 237979 v3")
    public void mainContactSearchingByClientNumberAndPhoneServiceV3Test_10() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282156";
        String contactPhoneFake1 = "79041111119";
        String contactPhoneFake2 = "79041111121";
        String contactPushFake1 = "id7139test9";
        String contactPushFake2 = "id7139test10";
        String communicationName1 = "autotestService";
        String communicationName2 = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPushFake1);

        communicationAutotestServiceCheckOrCreate();
        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake1, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory1, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake2, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory2, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake2);
    }

    @Test
    @Order(11)
    @AllureId("282159")
    @DisplayName("mainContactSearchingByClientNumberAndEmailServiceV4Test_11")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'phone' (p2). Сценарий 237979 v4")
    public void mainContactSearchingByClientNumberAndEmailServiceV4Test_11() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282159";
        String contactPhoneFake1 = "79041111110";
        String contactEmailFake1 = "7139test5@gmail.com";
        String contactEmailFake2 = "7139test6@gmail.com";
        String communicationName1 = "autotestServiceEmail";
        String communicationName2 = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactEmailFake1);

        communicationAutotestServiceEmailCheckOrCreate();
        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake1, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory1, communicationEventId1, communicationName1, CHANNEL_NAME_EMAIL, contactEmailFake1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake2, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory2, communicationEventId1);
    }

    @Test
    @Order(12)
    @AllureId("282188")
    @DisplayName("mainContactSearchingByClientNumberAndEmailServiceV5Test_12")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'phone' (p2). Сценарий 237979 v5")
    public void mainContactSearchingByClientNumberAndEmailServiceV5Test_12() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282188";
        String contactPhoneFake1 = "79041111119";
        String contactEmailFake1 = "7139test3@gmail.com";
        String contactEmailFake2 = "7139test4@gmail.com";
        String communicationName1 = "autotestServiceEmail";
        String communicationName2 = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactEmailFake1);

        communicationAutotestServiceCheckOrCreate();
        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake1, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory1, communicationEventId1, communicationName1, CHANNEL_NAME_EMAIL, contactEmailFake1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake2, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory2, communicationEventId1);
    }

    @Test
    @Order(13)
    @AllureId("282189")
    @DisplayName("mainContactSearchingByClientNumberAndEmailServiceV6Test_13")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'phone' (p2). Сценарий 237979 v6")
    public void mainContactSearchingByClientNumberAndEmailServiceV6Test_13() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282189";
        String contactPhoneFake1 = "79041111119";
        String contactPhoneFake2 = "79041111123";
        String contactEmailFake1 = "7139test7@gmail.com";
        String contactEmailFake2 = "7139test8@gmail.com";
        String communicationName1 = "autotestServiceEmail";
        String communicationName2 = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactEmailFake1);

        communicationAutotestServiceCheckOrCreate();
        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake1, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory1, communicationEventId1, communicationName1, CHANNEL_NAME_EMAIL, contactEmailFake1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake2, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory2, communicationEventId1);
    }

    @Test
    @Order(14)
    @AllureId("283253")
    @DisplayName("mainContactSearchingNotActiveContactTest_14")
    @Description("Логика поиска главного контакта и проверки согласий - запрет отправки коммуникации на не активный контакт")
    public void mainContactSearchingNotActiveContactTest_14() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "283253";
        String contactPhoneFake1 = "79049999996";
        String contactClientNumber = findClientNumberForContact("+" + contactPhoneFake1);
        String communicationName = "autotestServiceSmsEmail";

        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId1 = communicationSend(communicationName, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);

        communicationSend(communicationName, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName, contactPhoneFake1, CHANNEL_NAME_SMS, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory1, communicationEventId1);
    }
}