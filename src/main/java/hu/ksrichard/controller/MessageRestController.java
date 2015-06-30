package hu.ksrichard.controller;

import hu.ksrichard.model.CrudRequest;
import hu.ksrichard.model.CrudResponse;
import hu.ksrichard.model.CrudResponseStatus;
import hu.ksrichard.model.Message;
import hu.ksrichard.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST Controller for managing messages
 * @author ksrichard
 */
@RestController
@RequestMapping("/message")
public class MessageRestController {

    @Autowired
    private MessageService messageService;

    /**
     * Get All Messages
     * @return {@link Iterable<Message>}
     */
    @ResponseBody
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public Iterable<Message> allMessages(){
        return messageService.findAll();
    }

    /**
     * Add new message
     * @param request Request that must contain JSON for adding a new message
     * @param result Result for getting validation errors on {@param request}
     * @return {@link CrudResponse}
     */
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody CrudResponse addMessage(@RequestBody @Valid CrudRequest request, BindingResult result){
        CrudResponse response = new CrudResponse(CrudResponseStatus.SUCCESSFULL);
        if(result.hasErrors()){
            response.setStatus(CrudResponseStatus.FAILED);
           return response;
        }
        Message msg = new Message(request.getMessage());
        try{
            messageService.insert(msg);
            response.setEntity(msg);
        } catch (IllegalArgumentException ex){
            response.setStatus(CrudResponseStatus.FAILED);
        }
        return response;
    }

    /**
     * Delete a message
     * @param message Message object to delete
     * @return {@link CrudResponse}
     */
    @RequestMapping(value = "/{message}",method = RequestMethod.DELETE)
    public @ResponseBody CrudResponse deleteMessage(@PathVariable Message message){
        CrudResponse response = new CrudResponse(CrudResponseStatus.SUCCESSFULL);
        if(message == null){
            response.setStatus(CrudResponseStatus.FAILED);
            return response;
        }
        try{
            messageService.delete(message);
        }catch (IllegalArgumentException ex){
            response.setStatus(CrudResponseStatus.FAILED);
        }
        return response;
    }

    /**
     * Update a message
     * @param message Message to update
     * @param request Request that must contain JSON for updating message
     * @param result Result for getting validation errors on {@param request}
     * @return {@link CrudResponse}
     */
    @RequestMapping(value = "/{message}",method = RequestMethod.POST)
    public @ResponseBody CrudResponse updateMessage(@PathVariable Message message, @RequestBody @Valid CrudRequest request,BindingResult result){
        CrudResponse response = new CrudResponse(CrudResponseStatus.SUCCESSFULL);
        if(result.hasErrors()){
            response.setStatus(CrudResponseStatus.FAILED);
            return response;
        }
        if(message == null){
            response.setStatus(CrudResponseStatus.FAILED);
            return response;
        }
        try{
            message.setMessage(request.getMessage());
            messageService.update(message);
            response.setEntity(message);
        } catch (IllegalArgumentException ex){
            response.setStatus(CrudResponseStatus.FAILED);
        }
        return response;
    }

}
