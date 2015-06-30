package hu.ksrichard.db.trigger;


import java.sql.Connection;
import java.sql.SQLException;

/**
 * Utility class for Triggers
 * @author ksrichard
 */
public class TriggerUtil {

    /**
     * Adding triggers to a specific database table
     * @param connection SQL connection
     * @param tableName Database table name to add triggers
     * @throws SQLException
     */
    public static void addTriggersToTable(Connection connection,String tableName) throws SQLException {
        connection.prepareCall("CREATE TRIGGER AFTER_UPDATE AFTER UPDATE ON "+tableName+" FOR EACH ROW CALL \"hu.ksrichard.db.trigger.Triggers$UpdateTrigger\"").execute();
        connection.prepareCall("CREATE TRIGGER AFTER_INSERT AFTER INSERT ON "+tableName+" FOR EACH ROW CALL \"hu.ksrichard.db.trigger.Triggers$InsertTrigger\"").execute();
        connection.prepareCall("CREATE TRIGGER AFTER_DELETE AFTER DELETE ON "+tableName+" FOR EACH ROW CALL \"hu.ksrichard.db.trigger.Triggers$DeleteTrigger\"").execute();
    }

}
