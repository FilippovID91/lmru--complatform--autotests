package tech.lmru.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import tech.lmru.steps.StepsCommunication;

import java.sql.SQLException;

import static java.lang.Thread.sleep;
import static tech.lmru.Constant.*;
import static tech.lmru.steps.StepsCommunicationCreate.communicationAutotestTriggerCheckOrCreate;
import static tech.lmru.steps.StepsCommunication.communicationSend;
import static tech.lmru.steps.StepsCommunicationHistory.*;
import static tech.lmru.steps.StepsConsentManager.findClientNumberForContact;
import static tech.lmru.steps.StepsEstimatorLimit.checkEstimatorLimit;
import static tech.lmru.steps.StepsEstimatorLimit.postEstimatorLimit;
import static tech.lmru.steps.StepsGenericSmsHistory.checkGenericSmsSending;
import static tech.lmru.steps.StepsGenericSmsHistory.doGetGenericSmsHistory;

@Epic("Complatform autotest")
@Feature("Complatform autotest")
@Story("Лимиты на отправку коммуникаций")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class LimitsForCommunicationsTest {
    private String communicationEventId;
    private Response responseGenericSmsHistory;
    private Response responseCommunicationHistory;
    private Response responseEstimatorLimits;

    @Test
    @Order(1)
    @AllureId("280716")
    @DisplayName("checkLimitSmsCommunicationAllowedTest_1")
    @Description("Проверка лимитов на отправку коммуникаций - контакт sms, лимит задан, но не превышен {'allowed' : true}")
    public void checkLimitSmsCommunicationAllowedTrueTest_1() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "280716";
        String contactPhoneReal = CONTACT_PHONE_REAL;
        String communicationName = "autotestTrigger";
        String communicationId = String.valueOf(StepsCommunication.getCommunicationId(StepsCommunication.getCommunication(communicationName)));

        responseEstimatorLimits = postEstimatorLimit(communicationId, contactPhoneReal, CHANNEL_NAME_SMS);
        checkEstimatorLimit(responseEstimatorLimits, true);

        communicationAutotestTriggerCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactPhoneReal, CONTACT_TYPE_PHONE,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory = doGetGenericSmsHistory(contactPhoneReal, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory, "+" + contactPhoneReal, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
    }

    @Test
    @Order(2)
    @AllureId("280726")
    @DisplayName("checkLimitSmsCommunicationAllowedTest_2")
    @Description("Проверка лимитов на отправку коммуникаций - контакт push, лимит задан sms, {'allowed' : true}")
    public void checkLimitNullPushCommunicationAllowedTrueTest_2() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "280726";
        String contactClientNumber = findClientNumberForContact("+79049999996");
        String contactPush = "49d904840e4071db5196001e66baef41";
        String communicationName = "autotestTrigger";
        String communicationId = String.valueOf(StepsCommunication.getCommunicationId(StepsCommunication.getCommunication(communicationName)));

        communicationAutotestTriggerCheckOrCreate();
        communicationSend(communicationName, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);

        responseEstimatorLimits = postEstimatorLimit(communicationId, contactPush, CHANNEL_NAME_PUSH);
        checkEstimatorLimit(responseEstimatorLimits, true);

        communicationEventId = communicationSend(communicationName, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseCommunicationHistory = postCommunicationHistory(communicationHistoryBody(communicationName, contactPush, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory, communicationEventId, communicationName, CHANNEL_NAME_PUSH, contactPush);
    }

    @Test
    @Order(3)
    @AllureId("280802")
    @DisplayName("checkLimitSmsCommunicationAllowedFalseTest_3")
    @Description("Проверка лимитов на отправку коммуникаций - sms лимит превышен {'allowed' : false}")
    public void checkLimitSmsCommunicationAllowedFalseTest_3() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "280802";
        String contactPhoneReal = CONTACT_PHONE_REAL;
        String communicationName = "autotestTrigger";
        String communicationId = String.valueOf(StepsCommunication.getCommunicationId(StepsCommunication.getCommunication(communicationName)));

        communicationAutotestTriggerCheckOrCreate();
        communicationSend(communicationName, contactPhoneReal, CONTACT_TYPE_PHONE,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseEstimatorLimits = postEstimatorLimit(communicationId, contactPhoneReal, CHANNEL_NAME_SMS);
        checkEstimatorLimit(responseEstimatorLimits, false);

        communicationEventId = communicationSend(communicationName, contactPhoneReal, CONTACT_TYPE_PHONE,
                allureId, randomId + "allowedFalse", null);
        sleep(SLEEPING_TIME);

        responseCommunicationHistory = postCommunicationHistory(communicationHistoryBody(communicationName, contactPhoneReal, CHANNEL_NAME_SMS, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistoryQuarantine(responseCommunicationHistory, communicationEventId, "+" + contactPhoneReal);
    }

    @Test
    @Order(4)
    @AllureId("281197")
    @DisplayName("checkLimitEmailCommunicationAllowedFalseTest_4")
    @Description("Проверка лимитов на отправку коммуникаций - email лимит превышен {'allowed' : false}")
    public void checkLimitEmailCommunicationAllowedFalseTest_4() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "281197";
        String contactEmailReal = CONTACT_EMAIL_REAL;
        String communicationName = "autotestTrigger";
        String communicationId = String.valueOf(StepsCommunication.getCommunicationId(StepsCommunication.getCommunication(communicationName)));

        communicationAutotestTriggerCheckOrCreate();
        communicationSend(communicationName, contactEmailReal, CONTACT_TYPE_EMAIL,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseEstimatorLimits = postEstimatorLimit(communicationId, contactEmailReal, CHANNEL_NAME_EMAIL);
        checkEstimatorLimit(responseEstimatorLimits, false);

        communicationEventId = communicationSend(communicationName, contactEmailReal, CONTACT_TYPE_EMAIL,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseCommunicationHistory = postCommunicationHistory(communicationHistoryBody(communicationName, contactEmailReal, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistoryQuarantine(responseCommunicationHistory, communicationEventId, contactEmailReal);
    }

    @Test
    @Order(5)
    @AllureId("281512")
    @DisplayName("checkLimitFailedAndCascadeNextChannelTest_5")
    @Description("Проверка лимитов на отправку коммуникаций - отправка сообщения на контакт канала следующего приоритета, при не пройденной проверке по первому")
    public void checkLimitFailedAndCascadeNextChannelTest_5() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "281512";
        String contactPhoneReal = CONTACT_PHONE_REAL;
        String contactEmailReal = CONTACT_EMAIL_REAL;
        String communicationName = "autotestTrigger";
        String contactClientNumber = findClientNumberForContact(CONTACT_EMAIL_REAL);
        String communicationId = String.valueOf(StepsCommunication.getCommunicationId(StepsCommunication.getCommunication(communicationName)));

        communicationAutotestTriggerCheckOrCreate();
        communicationSend(communicationName, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId + "v1", null);
        sleep(SLEEPING_TIME);

        responseEstimatorLimits = postEstimatorLimit(communicationId, contactPhoneReal, CHANNEL_NAME_SMS);
        checkEstimatorLimit(responseEstimatorLimits, false);

        communicationEventId = communicationSend(communicationName, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId + "v2", null);
        sleep(SLEEPING_TIME);

        responseCommunicationHistory = postCommunicationHistory(communicationHistoryBody(communicationName, contactEmailReal, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory, communicationEventId, communicationName, CHANNEL_NAME_EMAIL, contactEmailReal);
        checkCommunicationHistoryQuarantineNotFound(responseCommunicationHistory, communicationEventId, contactPhoneReal);
    }
}