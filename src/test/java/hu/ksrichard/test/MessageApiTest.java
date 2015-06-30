package hu.ksrichard.test;

import hu.ksrichard.Application;
import hu.ksrichard.model.CrudResponseStatus;
import hu.ksrichard.model.Message;
import hu.ksrichard.test.util.JsonUtil;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Class for testing REST web services
 * @author ksrichard
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@PropertySource("classpath:application.properties")
public class MessageApiTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Value("${application.version}")
    private String appVersion;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Testing if status webservice works fine
     * @throws Exception
     */
    @Test
    public void testStatusOK() throws Exception {
        this.mockMvc.perform(get("/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }

    /**
     * Testing if version webservice works fine
     * @throws Exception
     */
    @Test
    public void testGetVersion() throws Exception {
        this.mockMvc.perform(get("/version"))
                .andExpect(status().isOk())
                .andExpect(content().string(appVersion));
    }

    /**
     * Test for add new message
     * @throws Exception
     */
    @Test
    public void testAddMessage() throws Exception {
        Message msg = new Message("new message");
        this.mockMvc.perform(
                post("/message")
                .content(JsonUtil.getJson(msg))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("status").value(CrudResponseStatus.SUCCESSFULL.toString()))
                .andExpect(jsonPath("entity").value(Matchers.notNullValue()))
                .andExpect(jsonPath("entity.id").value(Matchers.notNullValue()))
                .andExpect(jsonPath("entity.message").value(msg.getMessage()));
    }

    /**
     * Test for delete a message
     * @throws Exception
     */
    @Test
    public void testDeleteMessage() throws Exception {
        this.mockMvc.perform(
                delete("/message/23")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("status").value(CrudResponseStatus.SUCCESSFULL.toString()))
                .andExpect(jsonPath("entity").value(Matchers.nullValue()));
    }

    /**
     * Test for update a message
     * @throws Exception
     */
    @Test
    public void testUpdateMessage() throws Exception {
        Message msg = new Message("brand-new message");
        Integer id = 1;
        this.mockMvc.perform(
                post("/message/"+id)
                        .content(JsonUtil.getJson(msg))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("status").value(CrudResponseStatus.SUCCESSFULL.toString()))
                .andExpect(jsonPath("entity").value(Matchers.notNullValue()))
                .andExpect(jsonPath("entity.id").value(id))
                .andExpect(jsonPath("entity.message").value(msg.getMessage()));
    }

    /**
     * Test for get all the messages
     * @throws Exception
     */
    @Test
    public void testGetAllMessages() throws Exception {
        this.mockMvc.perform(
                get("/message/all")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("$", Matchers.hasSize(50)));
    }

    /**
     * Test for add new message with empty Message
     * @throws Exception
     */
    @Test
    public void testAddMessageEmptyMessage() throws Exception {
        Message msg = new Message("");
        this.mockMvc.perform(
                post("/message")
                        .content(JsonUtil.getJson(msg))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("status").value(CrudResponseStatus.FAILED.toString()))
                .andExpect(jsonPath("entity").value(Matchers.nullValue()));
    }

    /**
     * Test for add new message with wrong JSON body
     * @throws Exception
     */
    @Test
    public void testAddMessageWrongJSON() throws Exception {
        this.mockMvc.perform(
                post("/message")
                        .content("{ message:\"new message\" ")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }

    /**
     * Test for delete a message with wrong ID
     * @throws Exception
     */
    @Test
    public void testDeleteMessageWrongId() throws Exception {
        this.mockMvc.perform(
                delete("/message/999")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("status").value(CrudResponseStatus.FAILED.toString()))
                .andExpect(jsonPath("entity").value(Matchers.nullValue()));
    }

    /**
     * Test for update a message with wrong message ID
     * @throws Exception
     */
    @Test
    public void testUpdateMessageWrongId() throws Exception {
        Message msg = new Message("brand-new message");
        Integer id = 999;
        this.mockMvc.perform(
                post("/message/" + id)
                        .content(JsonUtil.getJson(msg))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("status").value(CrudResponseStatus.FAILED.toString()))
                .andExpect(jsonPath("entity").value(Matchers.nullValue()));
    }

    /**
     * Test for update a message with empty message
     * @throws Exception
     */
    @Test
    public void testUpdateMessageEmptyMessage() throws Exception {
        Message msg = new Message("");
        Integer id = 1;
        this.mockMvc.perform(
                post("/message/" + id)
                        .content(JsonUtil.getJson(msg))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("status").value(CrudResponseStatus.FAILED.toString()))
                .andExpect(jsonPath("entity").value(Matchers.nullValue()));
    }

    /**
     * Test for update a message with wrong JSON
     * @throws Exception
     */
    @Test
    public void testUpdateMessageWrongJSON() throws Exception {
        Integer id = 1;
        this.mockMvc.perform(
                post("/message/" + id)
                        .content("{ message:\"brand-new message\" ")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }


}
