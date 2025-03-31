package tech.lmru.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.lmru.Constant.*;
import static tech.lmru.client.CommunicationDatabase.getPostponedCommunicationTask;
import static tech.lmru.client.JdbcClient.closeConnectionDB;
import static tech.lmru.client.JdbcClient.getConnectionDB;
import static tech.lmru.steps.StepsCommunicationCreate.*;
import static tech.lmru.steps.StepsCommunication.communicationSend;
import static tech.lmru.steps.StepsCommunicationHistory.*;
import static tech.lmru.steps.StepsGenericSmsHistory.*;

@Epic("Complatform autotest")
@Feature("Complatform autotest")
@Story("Отложенная отправка коммуникаций")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class PostponedSendingCommunicationsTest {
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
    @AllureId("281511")
    @DisplayName("postponedCommunicationTrueWithStoreIdTest_1")
    @Description("Отложенная отправка коммуникаций - время отправки входит в интервал шаблона (store_id передан)")
    public void postponedCommunicationTrueWithStoreIdTest_1() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "281511";
        String contactPhoneFake = "79049" + allureId;
        String communicationName = "autotestTriggerPostponed";

        communicationAutotestTriggerPostponedCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactPhoneFake, CONTACT_TYPE_PHONE,
                allureId, randomId, "25");
        sleep(SLEEPING_TIME);

        List<Integer> checkPostponedCommunicationTaskId = getPostponedCommunicationTask(communicationEventId);

        step("Проверка отсутствия перехода коммуникации в отложенную отправку");
        assertTrue(checkPostponedCommunicationTaskId.isEmpty());

        responseGenericSmsHistory = doGetGenericSmsHistory(contactPhoneFake, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory, "+" + contactPhoneFake, "Complatform autotest: номер " + allureId + ", id " + randomId + ", storeId 25" + ", sms");
    }


    @Test
    @Order(2)
    @AllureId("281534")
    @DisplayName("postponedCommunicationFalseWithStoreIdTest_2")
    @Description("Отложенная отправка коммуникаций - время отправки не входит в интервал шаблона (store_id передан)")
    public void postponedCommunicationFalseWithStoreIdTest_2() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "281534";
        String contactEmailReal = CONTACT_EMAIL_REAL;
        String communicationName = "autotestServicePostponed";

        communicationAutotestServicePostponedCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactEmailReal, CONTACT_TYPE_EMAIL,
                allureId, randomId, "53");
        sleep(SLEEPING_TIME);

        List<Integer> checkPostponedCommunicationTaskId = getPostponedCommunicationTask(communicationEventId);

        step("Проверка перехода коммуникации в отложенную отправку");
        assertFalse(checkPostponedCommunicationTaskId.isEmpty());

        responseCommunicationHistory = postCommunicationHistory(communicationHistoryBody(communicationName, contactEmailReal, CHANNEL_NAME_EMAIL, HISTORY_START_SEARCH_DATE, HISTORY_END_SEARCH_DATE));
        checkCommunicationNotSent(responseCommunicationHistory, communicationEventId);
    }

    @Test
    @Order(3)
    @AllureId("281537")
    @DisplayName("postponedCommunicationFalseWithoutStoreIdTest_3")
    @Description("Отложенная отправка коммуникаций - время отправки входит в интервал шаблона (store_id не передан)")
    public void postponedCommunicationTrueWithoutStoreIdTest_3() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "281537";
        String contactPhoneFake = "79049" + allureId;
        String communicationName = "autotestTriggerPostponed";

        communicationAutotestTriggerPostponedCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactPhoneFake, CONTACT_TYPE_PHONE,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        List<Integer> checkPostponedCommunicationTaskId = getPostponedCommunicationTask(communicationEventId);

        step("Проверка отсутствия перехода коммуникации в отложенную отправку");
        assertTrue(checkPostponedCommunicationTaskId.isEmpty());

        responseGenericSmsHistory = doGetGenericSmsHistory(contactPhoneFake, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory, "+" + contactPhoneFake, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
    }

    @Test
    @Order(4)
    @AllureId("281581")
    @DisplayName("postponedCommunicationFalseWithoutStoreIdTest_4")
    @Description("Отложенная отправка коммуникаций - время отправки не входит в интервал шаблона (store_id не передан)")
    public void postponedCommunicationFalseWithoutStoreIdTest_4() throws InterruptedException, SQLException {
        String randomId = RandomStringUtils.randomNumeric(6);
        String allureId = "281581";
        String contactPhoneFake = "79049" + allureId;
        String communicationName = "autotestTriggerPostponed";

        communicationAutotestTriggerPostponedCheckOrCreate();
        communicationEventId = communicationSend(communicationName, contactPhoneFake, CONTACT_TYPE_PHONE,
                allureId, randomId, null);
        sleep(SLEEPING_TIME);

        List<Integer> checkPostponedCommunicationTaskId = getPostponedCommunicationTask(communicationEventId);

        step("Проверка отсутствия перехода коммуникации в отложенную отправку");
        assertTrue(checkPostponedCommunicationTaskId.isEmpty());

        responseGenericSmsHistory = doGetGenericSmsHistory(contactPhoneFake, GENERIC_SMS_START_SEARCH_DATE, GENERIC_SMS_END_SEARCH_DATE);
        checkGenericSmsSending(responseGenericSmsHistory, "+" + contactPhoneFake, "Complatform autotest: номер " + allureId + ", id " + randomId + ", sms");
    }
}
