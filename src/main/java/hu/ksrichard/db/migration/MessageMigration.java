package hu.ksrichard.db.migration;

import hu.ksrichard.model.Message;

/**
 * Migrating test messages
 * @author ksrichard
 */
@Migration(order = 2)
public class MessageMigration extends BaseMigration {
    @Override
    public void runMigrate() {
        for(int i=0;i<50;i++){
            Message msg = new Message();
            msg.setMessage("MSG - "+i);
            getSession().persist(msg);
        }
    }
}
