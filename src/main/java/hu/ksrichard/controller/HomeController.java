package hu.ksrichard.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller for handling basic pages like index and error pages
 * @author ksrichard
 */
@Controller
public class HomeController implements ErrorController {

    /**
     * Home page
     * @return {@link String}
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String home(){
        return "index";
    }

    /**
     * 404 not found error page
     * @return {@link String}
     */
    @RequestMapping(value = "/404",method = RequestMethod.GET)
    public String notFoundError(){
        return "404";
    }

    /**
     * 500 internal server error page
     * @return {@link String}
     */
    @RequestMapping(value = "/500",method = RequestMethod.GET)
    public String internalServerError(){
        return "500";
    }

    /**
     * Common error page
     * @return {@link String}
     */
    @RequestMapping(value = "/error")
    public void error(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/404");
    }

    /**
     * Spring boot configure common error url
     * @return {@link String}
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
