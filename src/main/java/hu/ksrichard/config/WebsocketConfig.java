package hu.ksrichard.config;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.hooks.SpringContextHook;
import org.apache.catalina.Context;
import org.apache.tomcat.websocket.server.WsSci;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.Arrays;

/**
 * Websocket configuration class
 * Initialize BrokerService(ActiveMq) and Websocket Endpoint
 * @author ksrichard
 */
@Configuration
@PropertySource("classpath:application.properties")
@EnableAsync
@EnableWebSocketMessageBroker
public class WebsocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Value("${broker.port}")
    private Integer brokerPort;

    @Value("${actions.topic}")
    private String actionsTopic;

    /**
     * Custom {@link TomcatEmbeddedServletContainerFactory} bean
     * @return {@link TomcatEmbeddedServletContainerFactory}
     */
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatContainerFactory() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setTomcatContextCustomizers(Arrays.asList(new TomcatContextCustomizer[]{
                tomcatContextCustomizer()
        }));
        return factory;
    }

    /**
     * {@link TomcatContextCustomizer} for adding WebSocket support
     * @return {@link TomcatContextCustomizer}
     */
    @Bean
    public TomcatContextCustomizer tomcatContextCustomizer() {
        return new TomcatContextCustomizer() {
            @Override
            public void customize(Context context) {
                context.addServletContainerInitializer(new WsSci(), null);
            }
        };
    }

    /**
     * {@link BrokerService} bean for initializing an ActiveMq bean
     * @return {@link BrokerService}
     * @throws Exception
     */
    @Bean
    public BrokerService brokerService() throws Exception {
        final BrokerService broker = BrokerFactory.createBroker("broker:(vm://localhost,stomp://localhost:"+brokerPort+")?persistent=false");
        broker.addShutdownHook(new SpringContextHook());
        final ActiveMQTopic topic = new ActiveMQTopic(actionsTopic);
        broker.setDestinations( new ActiveMQDestination[] { topic }  );
        broker.start();
        return broker;
    }

    /**
     * Configuring message broker
     * @param config A registry for configuring message broker options
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableStompBrokerRelay("/topic").setRelayPort(brokerPort);
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Registering websocket endpoint with SockJs support
     * @param stompEndpointRegistry A contract for registering STOMP over WebSocket endpoints
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/ws").withSockJS();
    }

}
