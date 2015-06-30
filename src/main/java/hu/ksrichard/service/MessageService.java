package hu.ksrichard.service;

import hu.ksrichard.model.Message;
import hu.ksrichard.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * Service for managing messages
 * @author ksrichard
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void insert(Message msg){
        messageRepository.save(msg);
    }

    public void delete(Message msg) throws IllegalArgumentException{
        messageRepository.delete(msg);
    }

    public void update(Message msg) {
        messageRepository.save(msg);
    }

    public Message findById(Integer id) throws IllegalArgumentException {
        return messageRepository.findOne(id);
    }

    public Iterable<Message> findAll() {
        return messageRepository.findAll();
    }

}
