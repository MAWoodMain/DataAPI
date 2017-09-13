package me.mawood.data_api.controllers;

import io.swagger.annotations.Api;
import me.mawood.data_api.objects.Device;
import me.mawood.data_api.objects.Response;
import me.mawood.data_api.sqlAbstraction.AccessorType;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessorFactory;
import me.mawood.data_api.sqlAbstraction.accessors.DeviceAccessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */

@RestController
@Api(tags = "Devices")
@RequestMapping("/devices")
public class DevicesController
{
    private static final DeviceAccessor deviceAccessor = (DeviceAccessor)SQLDataAccessorFactory.getInstance(AccessorType.DEVICE);
    private static final Log logger = LogFactory.getLog(DevicesController.class);


    @RequestMapping(value="/", method = RequestMethod.GET)
    public Response get(HttpServletResponse response)
    {
        logger.debug("Called: GET /devices/");
        try
        {
            response.setStatus(HttpServletResponse.SC_OK);
            return new Response<>(deviceAccessor.getDevices());
        } catch (SQLException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        }
    }

    @RequestMapping(value="/", method = {RequestMethod.PUT, RequestMethod.POST})
    public Response insert(@RequestBody Device device, HttpServletResponse response)
    {
        logger.debug("Called: PUT/POST /devices/");
        try
        {
            response.setStatus(HttpServletResponse.SC_OK);
            deviceAccessor.addDevice(device);
            return new Response(true,"Added device successfully");
        } catch (SQLException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        }
    }

    @RequestMapping(value="/{deviceTag}", method = {RequestMethod.DELETE})
    public Response delete(@PathVariable String deviceTag, HttpServletResponse response)
    {
        logger.debug("Called: DELETE /devices/");
        try
        {
            Device device = deviceAccessor.getDeviceFromTag(deviceTag);
            deviceAccessor.delete(device);
            return new Response(true,"Deleted device successfully");
        } catch (SQLException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        } catch (IllegalArgumentException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, e.getMessage());
        }
    }

}
