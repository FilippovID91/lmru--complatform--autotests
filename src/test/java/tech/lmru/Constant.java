package tech.lmru;

import java.time.LocalDate;

public class Constant {

    public final static String CONTACT_PHONE_REAL = "79049811113";
    public final static String CONTACT_EMAIL_REAL = "autotestcomplatform@gmail.com";

    public final static String CONTACT_TYPE_PHONE = "PHONE";
    public final static String CONTACT_TYPE_EMAIL = "EMAIL";
    public final static String CONTACT_TYPE_CLIENT_NUMBER = "CLIENT_NUMBER";

    public final static String CHANNEL_NAME_SMS = "SMS";
    public final static String CHANNEL_NAME_PUSH = "PUSH";
    public final static String CHANNEL_NAME_EMAIL = "EMAIL";

    public final static String GENERIC_SMS_START_SEARCH_DATE = String.valueOf(LocalDate.now());
    public final static String GENERIC_SMS_END_SEARCH_DATE = String.valueOf(LocalDate.now());
    public final static String HISTORY_START_SEARCH_DATE = LocalDate.now() + "T00:00:00.000+03:00";
    public final static String HISTORY_END_SEARCH_DATE = LocalDate.now() + "T23:59:59.000+03:00";

    public final static int SLEEPING_TIME = 2000;
}