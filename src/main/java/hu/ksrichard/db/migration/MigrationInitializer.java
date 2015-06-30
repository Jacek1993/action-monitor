package hu.ksrichard.db.migration;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Class for running Migration process when catch {@link ContextRefreshedEvent}
 * @author ksrichard
 */
@Component
@PropertySource("classpath:application.properties")
public class MigrationInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger logger = LoggerFactory.getLogger(MigrationInitializer.class);

    @Value("${db.migration.base.package}")
    private String migrationBasePackage;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    private ApplicationContext appCtx;

    /**
     * Get all of the Migration classes within package that extends {@link BaseMigration}
     * @param pckg Fully qualified package name
     * @return {@link List<Class<? extends BaseMigration>>}
     */
    private List<Class<? extends BaseMigration>> getMigrations(String pckg){
        Reflections reflections = new Reflections(pckg);
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(Migration.class);
        List<Class<? extends BaseMigration>> migrationClasses = new ArrayList<>();
        for(Class<?> type : types) {
            if (BaseMigration.class.isAssignableFrom(type)) {
                migrationClasses.add((Class<? extends BaseMigration>) type);
            }
        }
        return migrationClasses;
    }

    /**
     * Sorting migration classes depending on {@link hu.ksrichard.db.migration.Migration} annotation's order parameter
     * @param migrationClasses List of Migration classes for sorting
     * @return {@link List<Class<? extends BaseMigration>>}
     */
    private List<Class<? extends BaseMigration>> sortMigrationClasses(List<Class<? extends BaseMigration>> migrationClasses){
        Collections.sort(migrationClasses, new migrationComparator());
        return migrationClasses;
    }

    /**
     * Handling Spring Application Event
     * @param event Spring Application event ({@link ContextRefreshedEvent})
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            runMigrates();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            logger.error("An error occurred while executing migration: "+ e.getMessage());
        }
    }

    /**
     * Comparator for Migration classes
     */
    private class migrationComparator implements java.util.Comparator<Class<? extends BaseMigration>> {
        @Override
        public int compare(Class<? extends BaseMigration> o1, Class<? extends BaseMigration> o2) {
            if(o1.getAnnotation(Migration.class).order() < o2.getAnnotation(Migration.class).order()) return -1;
            if(o1.getAnnotation(Migration.class).order() > o2.getAnnotation(Migration.class).order()) return 1;
            return 0;
        }
    }

    /**
     * Initialize and run all the Migration classes' migrate method
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void runMigrates() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        List<Class<? extends BaseMigration>> migrationClasses = getMigrations(migrationBasePackage);
        migrationClasses = sortMigrationClasses(migrationClasses);
        for(Class<?> migrationClass : migrationClasses){
            java.lang.reflect.Method method = migrationClass.getMethod("migrate");
            Object obj = appCtx.getBean(migrationClass);
            beanFactory.autowireBean(obj);
            method.invoke(obj);
            logger.info(migrationClass.getName() + " migrated!");
        }
    }

}
