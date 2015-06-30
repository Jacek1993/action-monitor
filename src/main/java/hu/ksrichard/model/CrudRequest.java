package hu.ksrichard.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * DTO object for JSON requests
 * @author ksrichard
 */
public class CrudRequest {

    @NotEmpty
    @NotBlank
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
