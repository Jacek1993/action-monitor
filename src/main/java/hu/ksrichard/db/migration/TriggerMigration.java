package hu.ksrichard.db.migration;

import hu.ksrichard.db.trigger.TriggerUtil;
import org.hibernate.jdbc.AbstractWork;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Adding database triggers to "messages" table
 * @author ksrichard
 */
@Migration(order = 1)
public class TriggerMigration extends BaseMigration{
    @Override
    public void runMigrate() {
        getSession().doWork(new AbstractWork() {
            @Override
            public void execute(Connection connection) throws SQLException {
                TriggerUtil.addTriggersToTable(connection,"messages");
            }
        });
    }
}
