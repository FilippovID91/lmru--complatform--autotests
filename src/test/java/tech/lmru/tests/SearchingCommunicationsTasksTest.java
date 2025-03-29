package tech.lmru.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import java.sql.SQLException;

import static java.lang.Thread.sleep;
import static tech.lmru.Constant.*;
import static tech.lmru.client.JdbcClient.*;
import static tech.lmru.communications.CommunicationsCreate.*;
import static tech.lmru.steps.StepsCommunication.*;
import static tech.lmru.steps.StepsCommunicationHistory.*;
import static tech.lmru.steps.StepsConsentManager.findClientNumberForContact;
import static tech.lmru.steps.StepsGenericSmsHistory.*;

@Epic("Complatform autotest")
@Feature("Complatform autotest")
@Story("Поиск заданий на отправку коммуникаций")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class SearchingCommunicationsTasksTest {
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
    @AllureId("280606")
    @DisplayName("searchTaskWithStoreIdRuTest_1")
    @Description(value = "Поиск заданий на отправку коммуникаций - с учетом связки преданного 'store_id' и 'bu_id' - РФ")
    public void searchTaskWithStoreIdRuTest_1() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "280606";
        String contactPhone = "79049" + allureId;
        String communicationName = "autotestService";

        communicationAutotestServiceCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactPhone, CONTACT_TYPE_PHONE,
                allureId, randomId, "25");
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory = doGetGenericSmsHistory(contactPhone, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory, "+" + contactPhone, "Complatform autotest: номер " + allureId + ", id " + randomId + ", storeId 25" + ", sms");
    }

    @Test
    @Order(2)
    @AllureId("280628")
    @DisplayName("searchTaskWithStoreIdKzTest_2")
    @Description("Поиск заданий на отправку коммуникаций - с учетом связки преданного 'store_id' и 'bu_id' - Казахстан")
    public void searchTaskWithStoreIdKzTest_2() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "280628";
        String contactPhone = "79049" + allureId;
        String communicationName = "autotestServiceKz";

        communicationAutotestServiceKzCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactPhone, CONTACT_TYPE_PHONE,
                allureId, randomId, "113");
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory = doGetGenericSmsHistory(contactPhone, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory, "+" + contactPhone, "Complatform autotest: номер " + allureId + ", id " + randomId + ", storeId 113" + ", sms");
    }

    @Test
    @Order(3)
    @AllureId("280629")
    @DisplayName("searchTaskWithOtherStoreIdTest_3")
    @Description("Поиск заданий на отправку коммуникаций - выбор таски без учета 'store_id', если не найдена таска для преданного 'store_id'")
    public void searchTaskWithOtherStoreIdTest_3() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "280629";
        String contactPhone = "79049" + allureId;
        String communicationName = "autotestService";

        communicationAutotestServiceCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactPhone, CONTACT_TYPE_PHONE,
                allureId, randomId, "50");
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory = doGetGenericSmsHistory(contactPhone, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory, "+" + contactPhone, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
    }

    @Test
    @Order(4)
    @AllureId("280701")
    @DisplayName("searchTaskForPhoneWithoutStoreIdTest_4")
    @Description("Поиск заданий на отправку коммуникаций - контакт 'phone', не предан 'store_id'")
    public void searchTaskForPhoneWithoutStoreIdTest_4() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "280701";
        String contactPhone = "79049" + allureId;
        String communicationName = "autotestService";

        communicationAutotestServiceCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactPhone, CONTACT_TYPE_PHONE,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory = doGetGenericSmsHistory(contactPhone, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory, "+" + contactPhone, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
    }

    @Test
    @Order(5)
    @AllureId("280703")
    @DisplayName("searchTaskForEmailWithStoreIdTest_5")
    @Description("Поиск заданий на отправку коммуникаций - контакт 'email', передан 'storeId'")
    public void searchTaskForEmailWithStoreIdTest_5() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "280703";
        String contactEmailReal = CONTACT_EMAIL_REAL;
        String communicationName = "autotestService";

        communicationAutotestServiceCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactEmailReal, CONTACT_TYPE_EMAIL,
                allureId, randomId, "25");
        sleep(SLEEPING_TIME);

        responseCommunicationHistory = postCommunicationHistory(communicationHistoryBody(communicationName, contactEmailReal, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationHistorySending(responseCommunicationHistory, communicationEventId, communicationName, CHANNEL_NAME_EMAIL, contactEmailReal);
    }

    @Test
    @Order(6)
    @AllureId("280704")
    @DisplayName("searchTaskForClientNumberWithStoreIdTest_6")
    @Description("Поиск заданий на отправку коммуникаций - контакт 'client_number', не передан 'storeId'")
    public void searchTaskForClientNumberWithoutStoreIdTest_6() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "280704";
        String contactPhoneReal = CONTACT_PHONE_REAL;
        String communicationName = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact("+" + contactPhoneReal);

        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseGenericSmsHistory = doGetGenericSmsHistory(contactPhoneReal, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory, "+" + contactPhoneReal, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
    }

    @Test
    @Order(7)
    @AllureId("280705")
    @DisplayName("searchTaskNotFoundTest_7")
    @Description("Поиск заданий на отправку коммуникаций - контакт 'client_number', не передан 'storeId'")
    public void searchTaskNotFoundTest_7() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "280705";
        String contactEmailReal = CONTACT_EMAIL_REAL;
        String communicationName = "autotestService";

        communicationAutotestServiceCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactEmailReal, CONTACT_TYPE_EMAIL,
                allureId, randomId, "50");
        sleep(SLEEPING_TIME);

        responseCommunicationHistory = postCommunicationHistory(communicationHistoryBody(communicationName, contactEmailReal, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory, communicationEventId);
        }

    @Test
    @Order(8)
    @AllureId("280706")
    @DisplayName("searchTaskNotFoundChannelTest_8")
    @Description("Поиск заданий на отправку коммуникаций - не найден подходящий канал таски")
    public void searchTaskNotFoundChannelTest_8() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "280706";
        String contactPush = "49d904840e4071db5196001e66baef41";
        String communicationName = "autotestServiceSmsEmail";
        String contactClientNumber = findClientNumberForContact(contactPush);

        communicationAutotestServiceSmsEmailCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactClientNumber, CONTACT_TYPE_CLIENT_NUMBER,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        responseCommunicationHistory = postCommunicationHistory(communicationHistoryBody(communicationName, contactPush, CHANNEL_NAME_PUSH, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory, communicationEventId);
    }
}