package hu.ksrichard.repo;

import hu.ksrichard.model.Message;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for managing {@link Message} entities in database
 * @author ksrichard
 */
public interface MessageRepository extends CrudRepository<Message,Integer> {
}