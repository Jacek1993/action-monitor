package hu.ksrichard.model;

import javax.persistence.*;

/**
 * Entity for "messages" table
 * @author ksrichard
 */
@Entity
@Table(name = "messages")
public class Message {

    public Message(){
    }

    public Message(String message){
        this.message = message;
    }

    public Message(Integer id, String message){
        this.message = message;
        this.id = id;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
