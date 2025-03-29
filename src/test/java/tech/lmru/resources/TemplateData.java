package tech.lmru.resources;

import tech.lmru.pojo.CommunicationTemplate;

public class TemplateData {

    public static CommunicationTemplate pushTemplate = new CommunicationTemplate(
            "Complatform autotest: push",
            "Complatform autotest: номер ${autotestNumber}, id ${autotestId}, push");

    public static CommunicationTemplate pushTemplateWithStoreId = new CommunicationTemplate(
            "Complatform autotest: push",
            "Complatform autotest: номер ${autotestNumber}, id ${autotestId}, storeId ${storeId}, push");

    public static CommunicationTemplate smsTemplate = new CommunicationTemplate(
            null,
            "Complatform autotest: номер ${autotestNumber}, id ${autotestId}, sms");

    public static CommunicationTemplate smsTemplateWithStoreId = new CommunicationTemplate(
            null,
            "Complatform autotest: номер ${autotestNumber}, id ${autotestId}, storeId ${storeId}, sms");
}