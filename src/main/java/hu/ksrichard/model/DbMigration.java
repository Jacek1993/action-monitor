package hu.ksrichard.model;

import javax.persistence.*;

/**
 * Database migration entity to store already migrated classes
 * @author ksrichard
 */
@Entity
@Table(name = "db_migrations")
public class DbMigration {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "migration_class")
    private String migrationClass;

    @Column(name = "migrated")
    private Boolean migrated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMigrationClass() {
        return migrationClass;
    }

    public void setMigrationClass(String migrationClass) {
        this.migrationClass = migrationClass;
    }

    public Boolean getMigrated() {
        return migrated;
    }

    public void setMigrated(Boolean migrated) {
        this.migrated = migrated;
    }
}
