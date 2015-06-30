package hu.ksrichard.db.migration;

import hu.ksrichard.model.DbMigration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Abstract class to extend all the data migration classes
 * It provides everything that a Migration class need
 * @author ksrichard
 */
public abstract class BaseMigration {

    protected static Logger logger = LoggerFactory.getLogger(BaseMigration.class);

    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }

    /**
     * Method to check if the current Migration class is migrated before or not
     * @return boolean
     */
    private boolean checkIfMigrated(){
        String currentMigrationClass = this.getClass().getName();
        List migrations = getSession()
                .createQuery("from DbMigration migration where migration.migrationClass=:migrationclass")
                .setString("migrationclass",currentMigrationClass).list();
        return migrations.size() > 0;
    }

    /**
     * Insert current Migration to DB
     */
    private void logMigration(){
        DbMigration migration = new DbMigration();
        migration.setMigrated(true);
        migration.setMigrationClass(this.getClass().getName());
        getSession().saveOrUpdate(migration);
    }

    /**
     * Run migration process on current Migration class
     */
    @Transactional
    public void migrate(){
        if(!checkIfMigrated()){
            runMigrate();
            logMigration();
        }
    }

    /**
     * Abstract method which contains migration logic in every instance
     */
    public abstract void runMigrate();

}
