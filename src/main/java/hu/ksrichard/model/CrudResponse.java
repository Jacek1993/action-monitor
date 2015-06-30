package hu.ksrichard.model;

/**
 * Response class for JSON responses
 * @author ksrichard
 */
public class CrudResponse {

    public CrudResponse(CrudResponseStatus status){
        this.status = status;
    }

    public CrudResponse(Object entity, CrudResponseStatus status){
        this.status = status;
        this.entity = entity;
    }

    private CrudResponseStatus status;

    private Object entity;

    public CrudResponseStatus getStatus() {
        return status;
    }

    public void setStatus(CrudResponseStatus status) {
        this.status = status;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
