package hu.ksrichard.db.trigger;

import hu.ksrichard.db.ResultSetUtil;
import hu.ksrichard.model.JsonMessage;
import org.h2.tools.TriggerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for containing all the static trigger handlers
 * @author ksrichard
 */
public class Triggers {

    /**
     * Template for sending messages
     */
    public static MessageSendingOperations<String> template;

    /**
     * Actions topic name
     */
    public static String actionsTopic;

    public static void setTemplate(MessageSendingOperations<String> template) {
        Triggers.template = template;
    }

    public static void setActionsTopic(String actionsTopic) {
        Triggers.actionsTopic = actionsTopic;
    }

    /**
     * Handling INSERT event
     */
    public static class InsertTrigger extends TriggerAdapter {
        private static Logger logger = LoggerFactory.getLogger(InsertTrigger.class);
        @Override
        public void fire(Connection connection, ResultSet oldSet, ResultSet newSet) throws SQLException {
            String message = "timestamp=" + System.currentTimeMillis() + " :: a row (" + ResultSetUtil.toString(newSet) + ") was INSERTED!";
            logger.info(message);
            Triggers.template.convertAndSend(Triggers.actionsTopic, new JsonMessage(message));
        }
    }

    /**
     * Handling UPDATE event
     */
    public static class UpdateTrigger extends TriggerAdapter {
        private static Logger logger = LoggerFactory.getLogger(UpdateTrigger.class);
        @Override
        public void fire(Connection connection, ResultSet oldSet, ResultSet newSet) throws SQLException {
            String message = "timestamp="+System.currentTimeMillis()+" :: a row ("+ResultSetUtil.toString(oldSet) + ") was UPDATED to ("+ResultSetUtil.toString(newSet)+")";
            logger.info(message);
            Triggers.template.convertAndSend(Triggers.actionsTopic, new JsonMessage(message));
        }
    }

    /**
     * Handling DELETE event
     */
    public static class DeleteTrigger extends TriggerAdapter {
        private static Logger logger = LoggerFactory.getLogger(DeleteTrigger.class);
        @Override
        public void fire(Connection connection, ResultSet oldSet, ResultSet newSet) throws SQLException {
            String message = "timestamp="+System.currentTimeMillis()+" :: a row ("+ResultSetUtil.toString(oldSet)+") was DELETED!";
            logger.info(message);
            Triggers.template.convertAndSend(Triggers.actionsTopic, new JsonMessage(message));
        }
    }


}
