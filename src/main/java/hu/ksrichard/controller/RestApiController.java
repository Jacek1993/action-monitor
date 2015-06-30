package hu.ksrichard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class for providing basic informations about application
 * @author ksrichard
 */
@RestController
@PropertySource(value = { "classpath:application.properties" })
public class RestApiController {

    @Autowired
    private Environment environment;

    /**
     * Returns the current status of the application
     * @return {@link String}
     */
    @RequestMapping(value = "/status",method = RequestMethod.GET)
    public String status(){
        return "OK";
    }

    /**
     * Returns the actual version of the application
     * @return {@link String}
     */
    @RequestMapping(value = "/version",method = RequestMethod.GET)
    public String version() {
        return environment.getProperty("application.version");
    }

}
