package me.mawood.data_api.controllers;

import java.sql.SQLException;

import me.mawood.data_api.objects.Message;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessor;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessorFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class RootController
{
    private static final Log logger = LogFactory.getLog(RootController.class);

    @RequestMapping(value="/", method = RequestMethod.GET)
    public Message helloWorld(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        logger.info("Called: GET /");
        return new Message("Hello World");
    }
}