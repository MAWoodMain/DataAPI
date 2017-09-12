package me.mawood.data_api.controllers;

import me.mawood.data_api.objects.DataType;
import me.mawood.data_api.objects.Device;
import me.mawood.data_api.objects.Reading;
import me.mawood.data_api.objects.Response;
import me.mawood.data_api.sqlAbstraction.AccessorType;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessorFactory;
import me.mawood.data_api.sqlAbstraction.accessors.DataTypeAccessor;
import me.mawood.data_api.sqlAbstraction.accessors.DeviceAccessor;
import me.mawood.data_api.sqlAbstraction.accessors.ReadingAccessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    private static final DeviceAccessor deviceAccessor = (DeviceAccessor)SQLDataAccessorFactory.getInstance(AccessorType.DEVICE);
    private static final DataTypeAccessor dataTypeAccessor = (DataTypeAccessor)SQLDataAccessorFactory.getInstance(AccessorType.DATA_TYPE);
    private static final ReadingAccessor readingAccessor = (ReadingAccessor)SQLDataAccessorFactory.getInstance(AccessorType.READING);

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
            Device device = deviceAccessor.getDeviceFromTag(deviceTag);
            DataType dataType = dataTypeAccessor.getDataTypeFromTag(dataTypeTag);
            return new Response<>(readingAccessor.getReadingsFor(device,dataType,start,end));
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
            Device device = deviceAccessor.getDeviceFromTag(deviceTag);
            DataType dataType = dataTypeAccessor.getDataTypeFromTag(dataTypeTag);
            int count = readingAccessor.insertReadings(device,dataType,readings);
            return new Response<>(true,"Done, inserted " + count + " readings of " + readings.size());
        } catch (SQLException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        }
    }
}
