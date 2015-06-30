package hu.ksrichard.model;

/**
 * DTO class for WebSocket messages
 * @author ksrichard
 */
public class JsonMessage {

    private String content;

    public JsonMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
