package hu.ksrichard.config;

import hu.ksrichard.db.trigger.Triggers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;

/**
 * Triggers static class initializer bean
 * It initializes Triggers static class with MessageSendingOperations and actionsTopic
 * @author ksrichard
 */
@Component
@PropertySource("classpath:application.properties")
public class TriggerInitializer {

    /**
     * Constructor to initialize Triggers static class
     * @param operationTemplate Autowired template for sending messages
     * @param actionsTopic Actions topic name
     */
    @Autowired
    public TriggerInitializer(MessageSendingOperations<String> operationTemplate,@Value("${actions.topic}") String actionsTopic){
        Triggers.setTemplate(operationTemplate);
        Triggers.setActionsTopic(actionsTopic);
    }

}
