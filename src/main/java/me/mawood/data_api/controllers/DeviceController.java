package me.mawood.data_api.controllers;

import me.mawood.data_api.objects.DataType;
import me.mawood.data_api.objects.Device;
import me.mawood.data_api.objects.Reading;
import me.mawood.data_api.objects.Response;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessor;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessorFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Collection;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */

@RestController
@RequestMapping("/device")
public class DeviceController
{
    private static final SQLDataAccessor sql = SQLDataAccessorFactory.getInstance();

    private static final Log logger = LogFactory.getLog(DeviceController.class);

    @RequestMapping(value = "/{deviceTag}/{dataTypeTag}", method = RequestMethod.GET, produces = "application/json")
    public Response readingGet(@PathVariable String deviceTag, @PathVariable String dataTypeTag,
                               @RequestParam(value = "start", required=false) Long start, @RequestParam(value = "end", required=false) Long end,
                               HttpServletResponse response)
    {

        logger.info("Called: GET /device/"+deviceTag+"/"+dataTypeTag+"/");
        try
        {
            response.setStatus(HttpServletResponse.SC_OK);
            Device device = sql.getDeviceFromTag(deviceTag);
            DataType dataType = sql.getDataTypeFromTag(dataTypeTag);
            return new Response<>(sql.getReadingsFor(device,dataType,start,end));
        } catch (Exception e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        }
    }

    @RequestMapping(value = "/{deviceTag}/{dataTypeTag}", method = RequestMethod.POST, produces = "application/json")
    public Response readingPost(@PathVariable String deviceTag, @PathVariable String dataTypeTag,
                                @RequestBody Collection<Reading> readings,
                                HttpServletResponse response)
    {
        logger.info("Called: POST /device/"+deviceTag+"/"+dataTypeTag+"/");
        try
        {
            response.setStatus(HttpServletResponse.SC_OK);
            Device device = sql.getDeviceFromTag(deviceTag);
            DataType dataType = sql.getDataTypeFromTag(dataTypeTag);
            int count = sql.insertReadings(device,dataType,readings);
            return new Response<>(true,"Done, inserted " + count + " readings of " + readings.size());
        } catch (SQLException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        }
    }
}
