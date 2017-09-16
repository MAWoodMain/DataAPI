package me.mawood.data_api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.mawood.data_api.objects.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "Root")
@RestController
public class RootController
{
    private static final Log logger = LogFactory.getLog(RootController.class);

    @RequestMapping(value="/api/", method = RequestMethod.GET)
    @ApiOperation(value = "Hello World method", notes = "Includes a timestamp and a hello world message")
    public Message helloWorld(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        logger.debug("Called: GET /");
        return new Message("Hello World");
    }
}