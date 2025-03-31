package tech.lmru.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static tech.lmru.client.JdbcClient.statementDB;

public class CommunicationDatabase {

    public static void getChannelId(String type) throws SQLException {

        ResultSet getChannelIdResult = statementDB.executeQuery("SELECT * FROM channel WHERE type = '" + type + "'");

        List<Integer> idChannel = new ArrayList<>();

        while (getChannelIdResult.next()) {
            int id = getChannelIdResult.getInt("id");
            idChannel.add(id);
            System.out.println(type + " id: " + idChannel);
        }
    }

    public static List<Integer> getCommunicationId(String communication_name) throws SQLException {

        ResultSet getCommunicationIdResult = statementDB.executeQuery("SELECT id FROM communication WHERE communication_name = '" + communication_name + "'");

        List<Integer> communicationId = new ArrayList<>();

        while (getCommunicationIdResult.next()) {
            int id = getCommunicationIdResult.getInt("id");
            communicationId.add(id);
            System.out.println(communication_name + " id: " + communicationId);
        }
        return communicationId;
    }

    public static List<Integer> getPostponedCommunicationTask(String communication_event_id) throws SQLException {

        ResultSet getPostponedCommunicationTaskResult = statementDB.executeQuery("SELECT communication_task_id FROM postponed_communication WHERE communication_event_id = '" + communication_event_id + "'");

        List<Integer> postponedCommunicationTaskId = new ArrayList<>();

        while (getPostponedCommunicationTaskResult.next()) {
            int id = getPostponedCommunicationTaskResult.getInt("communication_task_id");
            postponedCommunicationTaskId.add(id);
            System.out.println(communication_event_id + " id: " + postponedCommunicationTaskId);
        }
        return postponedCommunicationTaskId;
    }
}