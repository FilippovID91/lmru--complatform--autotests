package tech.lmru.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import tech.lmru.client.BaseHttpClient;
import tech.lmru.pojo.CommunicationTemplate;

public class StepsTemplateManager extends BaseHttpClient {

    // POST /templates
    @Step("Создание нового шаблона коммуникации в Template manager")
    public static Response communicationTemplateCreate(CommunicationTemplate communicationTemplate) {
        return doPostRequest(TEMPLATE_MANAGER, communicationTemplate);
    }

    // GET /templates
    @Step("Запрос информации по шаблонам в Template manager")
    public static Response getTemplates() {
        return doGetRequest(TEMPLATE_MANAGER);
    }

    // GET /templates (PMP-message)
    @Step("Запрос информации по шаблонам в PMP-message")
    public static Response getTemplateFromPmp(int templateIdPmp) {
        return doGetRequest(PMP_MESSAGE + "/" + templateIdPmp);
    }

    @Step("Получение ID шаблона в Template manager")
    public static Integer getTemplateId(Response response, String templateText) {
        return response.getBody().path("find {it.templateText == '" + templateText + "'}.id");
    }

    @Step("Получение ID шаблона в Template manager. Если шаблон не найден, то создание нового шаблона")
    public static Integer getOrCreateTemplateId(CommunicationTemplate communicationTemplate) {
        Response response = getTemplates();
        String templateText = communicationTemplate.getTemplateText();
        Integer templateId = getTemplateId(response, templateText);
        if (templateId == null) {
            templateId = getTemplateId(communicationTemplateCreate(communicationTemplate), templateText);
        }
        return templateId;
    }

    @Step("Получение ID шаблона сервисных коммуникаций в PMP-message")
    public static int getServiceTemplateIdFromPmp() {
        int templateIdPmp;
        Response response = getTemplateFromPmp(385);
        String checkTemplate = response.then().
                extract().
                body().
                jsonPath().getString("mappings.providerTemplateId");
        if (checkTemplate.contains("4b192524-e7a1-11ef-8e7d-065df5e1a0eb")) {
            templateIdPmp = 385;
        } else {
            templateIdPmp = 518;
        }
        return templateIdPmp;
    }

    @Step("Получение ID шаблона триггерных коммуникаций в PMP-message")
    public static int getTriggerTemplateIdFromPmp() {
        int templateIdPmp;
        Response response = getTemplateFromPmp(389);
        String checkTemplate = response.then().
                extract().
                body().
                jsonPath().getString("mappings.providerTemplateId");
        if (checkTemplate.contains("ba8f5b10-041d-11f0-b863-fea3f6928b5d")) {
            templateIdPmp = 389;
        } else {
            templateIdPmp = 521;
        }
        return templateIdPmp;
    }
}