package tech.lmru.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import tech.lmru.communications.CommunicationsCreate;

import java.sql.SQLException;

import static java.lang.Thread.sleep;
import static tech.lmru.Constant.*;
import static tech.lmru.client.JdbcClient.*;
import static tech.lmru.communications.CommunicationsCreate.communicationAutotestMarketingCheckOrCreate;
import static tech.lmru.communications.CommunicationsCreate.communicationAutotestTriggerPostponedCheckOrCreate;
import static tech.lmru.steps.StepsCommunication.communicationSend;
import static tech.lmru.steps.StepsCommunicationHistory.*;
import static tech.lmru.steps.StepsConsentManager.findClientNumberForContact;
import static tech.lmru.steps.StepsGenericSmsHistory.*;

@Epic("Complatform autotest")
@Feature("Complatform autotest")
@Story("Сборка шаблонов коммуникаций")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TemplateManagerBuildTest {
    private String communicationEventId;
    private Response responseGenericSmsHistory;
    private Response responseCommunicationHistory;

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
    @AllureId("281330")
    @DisplayName("successfulSmsTemplateBuildTest_1")
    @Description("Template manager - успешная сборка шаблона канала sms")
    public void successfulSmsTemplateBuildTest_1() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "281330";
        String contactPhoneFake = "79049" + allureId;
        String communicationName = "autotestServiceSmsEmail";

        CommunicationsCreate.communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactPhoneFake, CONTACT_TYPE_PHONE,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory = doGetGenericSmsHistory(contactPhoneFake, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory, "+" + contactPhoneFake, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
    }

    @Test
    @Order(2)
    @AllureId("281333")
    @DisplayName("successfulPushTemplateBuildTest_2")
    @Description("Template manager - успешная сборка шаблона канала push")
    public void successfulPushTemplateBuildTest_2() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "281333";
        String contactPush = "49d904840e4071db5196001e66baef41";
        String communicationName = "autotestTrigger";
        String contactClientNumber = findClientNumberForContact(contactPush);

        CommunicationsCreate.communicationAutotestTriggerCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseCommunicationHistory = postCommunicationHistory(communicationHistoryBody(communicationName, contactPush, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory, communicationEventId, communicationName, CHANNEL_NAME_PUSH, contactPush);
    }

    @Test
    @Order(3)
    @AllureId("281366")
    @DisplayName("unsuccessfulTemplateBuildHistoryTest_3")
    @Description("Template manager - отправка сообщения  об ошибке в communication history при ошибках сборки шаблона")
    public void unsuccessfulTemplateBuildHistoryTest_3() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "281366";
        String contactPhoneFake = "79049" + allureId;
        String communicationName = "autotestTriggerPostponed";

        communicationAutotestTriggerPostponedCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactPhoneFake, CONTACT_TYPE_PHONE,
                null, randomId, null);
        sleep(SLEEPING_TIME);

        responseCommunicationHistory = postCommunicationHistory(communicationHistoryBody(communicationName, contactPhoneFake, CHANNEL_NAME_SMS, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistoryTemplateBuildFailed(responseCommunicationHistory, communicationEventId);
    }

    @Test
    @Order(4)
    @AllureId("281513")
    @DisplayName("unsuccessfulSmsTemplateBuildAndCascadeNextChannelTest_4")
    @Description("Template manager - отправка сообщения по каналу следующего приоритета, в случае ошибки сборки шаблона по каналу первого приоритета")
    public void unsuccessfulSmsTemplateBuildAndCascadeNextChannelTest_4() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String communicationName = "autotestMarketing";
        String contactClientNumber = findClientNumberForContact(CONTACT_EMAIL_REAL);

        communicationAutotestMarketingCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                null, randomId, null);
        sleep(SLEEPING_TIME);

        responseCommunicationHistory = postCommunicationHistory(communicationHistoryBody(communicationName, CONTACT_EMAIL_REAL, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory, communicationEventId, communicationName, CHANNEL_NAME_EMAIL, CONTACT_EMAIL_REAL);
    }
}