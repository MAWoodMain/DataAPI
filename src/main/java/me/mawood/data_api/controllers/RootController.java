package me.mawood.data_api.controllers;

import java.sql.SQLException;

import me.mawood.data_api.objects.Message;
import me.mawood.data_api.objects.Response;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessor;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessorFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController
{

    private static final SQLDataAccessor sql = SQLDataAccessorFactory.getInstance();

    @RequestMapping("/")
    public Message helloWorld() {
        return new Message("Hello World");
    }

    @RequestMapping("/locations")
    public Response locations()
    {
        try
        {
            return new Response<>(sql.getLocations());
        } catch (SQLException e)
        {
            return new Response(false, "SQL error");
        }
    }

    @RequestMapping("/datatypes")
    public Response datatypes()
    {
        try
        {
            return new Response<>(sql.getDataTypes());
        } catch (SQLException e)
        {
            return new Response(false, "SQL error");
        }
    }
}