package tech.lmru.steps;

import tech.lmru.client.BaseHttpClient;

public class StepsEnvironment extends BaseHttpClient {
    static String variable;

    public static String environmentVariable(String variableTest, String variableStage) {
        String environment_jenkins = System.getenv("ENVIRONMENT_JENKINS");

        if (environment_jenkins.equals("TEST")) {
            variable = variableTest;
        } else if (environment_jenkins.equals("STAGE")) {
            variable = variableStage;
        } else {
            System.out.println("Environment can be TEST or STAGE");
        }
        return variable;
    }
}