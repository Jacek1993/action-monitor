package hu.ksrichard.converter;

import hu.ksrichard.model.Message;
import hu.ksrichard.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Simple converter for converting incoming message id to {@link Message} object
 * @author ksrichard
 */
@Component
public class StringToMessageConverter implements Converter<String,Message> {

    private static Logger logger = LoggerFactory.getLogger(StringToMessageConverter.class);

    @Autowired
    private MessageService messageService;

    /**
     * Conversion method
     * @param id ID for a message from request
     * @return {@link hu.ksrichard.model.Message}
     */
    @Override
    public Message convert(String id) {
        Integer realId = Integer.parseInt(id);
        Message msg = null;
        try{
            msg = messageService.findById(realId);
        } catch (IllegalArgumentException ex){
            logger.error("Message not found by ID: "+id);
        }
        return msg;
    }
}
