package tech.lmru.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static java.lang.Thread.sleep;
import static tech.lmru.Constant.*;
import static tech.lmru.steps.StepsCommunicationCreate.*;
import static tech.lmru.steps.StepsCommunication.communicationSend;
import static tech.lmru.steps.StepsCommunication.communicationWithTwoContactSend;
import static tech.lmru.steps.StepsCommunicationHistory.*;
import static tech.lmru.steps.StepsConsentManager.findClientNumberForContact;
import static tech.lmru.steps.StepsGenericSmsHistory.*;

@Epic("Complatform autotest")
@Feature("Complatform autotest")
@Story("Логика поиска главного контакта и проверка согласий")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class MainContactAndConsentTriggerTest {
    private String communicationEventId1;
    private String communicationEventId2;
    private Response responseGenericSmsHistory1;
    private Response responseGenericSmsHistory2;
    private Response responseCommunicationHistory1;
    private Response responseCommunicationHistory2;

    @Test
    @Order(1)
    @AllureId("282209")
    @DisplayName("mainContactSearchingByClientNumberTriggerV1Test_1")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' (p2). Сценарий 238004 v1")
    public void mainContactSearchingByClientNumberTriggerV1Test_1() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282209";
        String contactPhoneFake1 = "79041111111";
        String contactPhoneFake2 = "79041111112";
        String contactPushFake1 = "id7139test1";
        String contactPushFake2 = "id7139test2";
        String communicationName1 = "autotestTrigger";
        String communicationName2 = "autotestTriggerSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPushFake1);

        communicationAutotestTriggerCheckOrCreate();
        communicationAutotestTriggerSmsEmailCheckOrCreate();
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
        checkCommunicationNotSent(responseCommunicationHistory1, communicationEventId1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake2, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory2, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake2);
    }

    @Test
    @Order(2)
    @AllureId("282210")
    @DisplayName("mainContactSearchingByClientNumberTriggerV2Test_2")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' (p2). Сценарий 238004 v2")
    public void mainContactSearchingByClientNumberTriggerV2Test_2() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282210";
        String contactPhoneFake1 = "79041111115";
        String contactPhoneFake2 = "79041111116";
        String contactPushFake1 = "id7139test5";
        String contactPushFake2 = "id7139test6";
        String communicationName1 = "autotestTrigger";
        String communicationName2 = "autotestTriggerSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPushFake1);

        communicationAutotestTriggerCheckOrCreate();
        communicationAutotestTriggerSmsEmailCheckOrCreate();
        communicationEventId1 = communicationSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        communicationEventId2 = communicationSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake1, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory1, communicationEventId1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake2, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory2, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake2);
    }

    @Test
    @Order(3)
    @AllureId("282212")
    @DisplayName("mainContactSearchingByClientNumberTriggerV3Test_3")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' (p2). Сценарий 238004 v3")
    public void mainContactSearchingByClientNumberTriggerV3Test_3() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282212";
        String contactPhoneFake1 = "79041111117";
        String contactPhoneFake2 = "79041111118";
        String contactPushFake1 = "id7139test7";
        String contactPushFake2 = "id7139test8";
        String communicationName1 = "autotestTrigger";
        String communicationName2 = "autotestTriggerSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPushFake1);

        communicationAutotestTriggerCheckOrCreate();
        communicationAutotestTriggerSmsEmailCheckOrCreate();
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
    @AllureId("282213")
    @DisplayName("mainContactSearchingByPhoneTriggerV1Test_4")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'phone' (p2). Сценарий 238007 v1")
    public void mainContactSearchingByPhoneTriggerV1Test_4() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282213";
        String contactPhoneFake1 = "79041111110";
        String communicationName1 = "autotestTriggerSmsEmail";

        communicationAutotestTriggerSmsEmailCheckOrCreate();
        communicationEventId1 = communicationSend(communicationName1, contactPhoneFake1, CONTACT_TYPE_PHONE,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
    }

    @Test
    @Order(5)
    @AllureId("282214")
    @DisplayName("mainContactSearchingByPhoneTriggerV2Test_5")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'phone' (p2). Сценарий 238007 v2")
    public void mainContactSearchingByPhoneTriggerV2Test_5() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282214";
        String contactPhoneFake1 = "79041111119";
        String communicationName1 = "autotestTriggerSmsEmail";

        communicationAutotestTriggerSmsEmailCheckOrCreate();
        communicationEventId1 = communicationSend(communicationName1, contactPhoneFake1, CONTACT_TYPE_PHONE,
                allureId, randomId, null);
        sleep(SLEEPING_TIME + SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
    }

    @Test
    @Order(6)
    @AllureId("283056")
    @DisplayName("mainContactSearchingByClientNumberAndEmailTriggerV1Test_6")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'email' (p2). Сценарий 238030 v1")
    public void mainContactSearchingByClientNumberAndEmailV1Test_6() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "283056";
        String contactPhoneFake1 = "79041111124";
        String contactPhoneFake2 = "79041111125";
        String contactEmailFake1 = "7139test@gmail.com";
        String contactEmailFake2 = "7139test13@gmail.com";
        String communicationName1 = "autotestTrigger";
        String communicationName2 = "autotestTriggerSmsEmail";
        String contactClientNumber = findClientNumberForContact("+" + contactPhoneFake1);

        communicationAutotestTriggerCheckOrCreate();
        communicationAutotestTriggerSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactEmailFake1, CONTACT_TYPE_EMAIL, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactEmailFake1, CONTACT_TYPE_EMAIL, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake1, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory1, communicationEventId1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake2, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory2, communicationEventId1);
    }

    @Test
    @Order(7)
    @AllureId("282220")
    @DisplayName("mainContactSearchingByClientNumberAndEmailTriggerV2Test_7")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'email' (p2). Сценарий 238030 v2")
    public void mainContactSearchingByClientNumberAndEmailTriggerV2Test_7() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282220";
        String contactPhoneFake1 = "79041111126";
        String contactPhoneFake2 = "79041111127";
        String contactEmailFake1 = "7139test@gmail.com";
        String contactEmailFake2 = "7139test14@gmail.com";
        String communicationName1 = "autotestTriggerEmail";
        String communicationName2 = "autotestTriggerSmsEmail";
        String contactClientNumber = findClientNumberForContact("+" + contactPhoneFake1);

        communicationAutotestTriggerCheckOrCreate();
        communicationAutotestTriggerSmsEmailCheckOrCreate();
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
        checkCommunicationNotSent(responseCommunicationHistory1, communicationEventId1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake2, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory2, communicationEventId1);
    }

    @Test
    @Order(8)
    @AllureId("282238")
    @DisplayName("mainContactSearchingByClientNumberAndPhoneTriggerV1Test_8")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'phone' (p2). Сценарий 238008 v1")
    public void mainContactSearchingByClientNumberAndPhoneTriggerV1Test_8() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282238";
        String contactPhoneFake1 = "79041111110";
        String contactPhoneFake2 = "79041111113";
        String contactPushFake1 = "id7139test3";
        String contactPushFake2 = "id7139test4";
        String communicationName1 = "autotestTrigger";
        String communicationName2 = "autotestTriggerSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPushFake1);

        communicationAutotestTriggerCheckOrCreate();
        communicationAutotestTriggerSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake1, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory1, communicationEventId1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake2, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory2, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake2);
    }

    @Test
    @Order(9)
    @AllureId("282239")
    @DisplayName("mainContactSearchingByClientNumberAndPhoneTriggerV2Test_9")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'phone' (p2). Сценарий 238008 v2")
    public void mainContactSearchingByClientNumberAndPhoneTriggerV2Test_9() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282239";
        String contactPhoneFake1 = "79041111120";
        String contactPhoneFake2 = "79041111115";
        String contactPushFake1 = "id7139test5";
        String contactPushFake2 = "id7139test6";
        String communicationName1 = "autotestTrigger";
        String communicationName2 = "autotestTriggerSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPushFake1);

        communicationAutotestTriggerCheckOrCreate();
        communicationAutotestTriggerSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
        responseGenericSmsHistory2 = doGetGenericSmsHistory(contactPhoneFake2, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory2, "+" + contactPhoneFake2, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake1, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory1, communicationEventId1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactPushFake2, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory2, communicationEventId1, communicationName1, CHANNEL_NAME_PUSH, contactPushFake2);
    }

    @Test
    @Order(10)
    @AllureId("282985")
    @DisplayName("mainContactSearchingByClientNumberAndPhoneTriggerV2Test_10")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'phone' (p2). Сценарий 238008 v3")
    public void mainContactSearchingByClientNumberAndPhoneTriggerV3Test_10() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282985";
        String contactPhoneFake1 = "79041111119";
        String contactPhoneFake2 = "79041111121";
        String contactPushFake1 = "id7139test9";
        String contactPushFake2 = "id7139test10";
        String communicationName1 = "autotestTrigger";
        String communicationName2 = "autotestTriggerSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPushFake1);

        communicationAutotestTriggerCheckOrCreate();
        communicationAutotestTriggerSmsEmailCheckOrCreate();
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
    @AllureId("282986")
    @DisplayName("mainContactSearchingByClientNumberAndEmailTriggerV4Test_11")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'phone' (p2). Сценарий 238008 v4")
    public void mainContactSearchingByClientNumberAndEmailTriggerV4Test_11() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282986";
        String contactPhoneFake1 = "79041111110";
        String contactEmailFake1 = "7139test5@gmail.com";
        String contactEmailFake2 = "7139test6@gmail.com";
        String communicationName1 = "autotestTriggerEmail";
        String communicationName2 = "autotestTriggerSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactEmailFake1);

        communicationAutotestTriggerEmailCheckOrCreate();
        communicationAutotestTriggerSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsNotFoundCommunicationMessage(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake1, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory1, communicationEventId1, communicationName1, CHANNEL_NAME_EMAIL, contactEmailFake1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake2, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory2, communicationEventId1);
    }

    @Test
    @Order(12)
    @AllureId("282983")
    @DisplayName("mainContactSearchingByClientNumberAndEmailTriggerV5Test_12")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'phone' (p2). Сценарий 238008 v5")
    public void mainContactSearchingByClientNumberAndEmailTriggerV5Test_12() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282983";
        String contactPhoneFake1 = "79041111119";
        String contactEmailFake1 = "7139test3@gmail.com";
        String contactEmailFake2 = "7139test4@gmail.com";
        String communicationName1 = "autotestTriggerEmail";
        String communicationName2 = "autotestTriggerSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactEmailFake1);

        communicationAutotestTriggerEmailCheckOrCreate();
        communicationAutotestTriggerSmsEmailCheckOrCreate();
        communicationEventId1 = communicationWithTwoContactSend(communicationName1, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        communicationEventId2 = communicationWithTwoContactSend(communicationName2, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                contactPhoneFake1, CONTACT_TYPE_PHONE, allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory1 = doGetGenericSmsHistory(contactPhoneFake1, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory1, "+" + contactPhoneFake1, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");

        responseCommunicationHistory1 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake1, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory1, communicationEventId1);
        responseCommunicationHistory2 = postCommunicationHistory(communicationHistoryBody(communicationName1, contactEmailFake2, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory2, communicationEventId1);
    }

    @Test
    @Order(13)
    @AllureId("282987")
    @DisplayName("mainContactSearchingByClientNumberAndEmailTriggerV6Test_13")
    @Description("Логика поиска главного контакта и проверки согласий - в случае отправки контактов 'client_number' и 'phone' (p2). Сценарий 238008 v6")
    public void mainContactSearchingByClientNumberAndEmailTriggerV6Test_13() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "282987";
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
}