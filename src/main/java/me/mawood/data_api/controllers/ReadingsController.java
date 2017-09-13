package me.mawood.data_api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "Readings")
@RequestMapping("/device")
public class ReadingsController
{
    private static final DeviceAccessor deviceAccessor = (DeviceAccessor)SQLDataAccessorFactory.getInstance(AccessorType.DEVICE);
    private static final DataTypeAccessor dataTypeAccessor = (DataTypeAccessor)SQLDataAccessorFactory.getInstance(AccessorType.DATA_TYPE);
    private static final ReadingAccessor readingAccessor = (ReadingAccessor)SQLDataAccessorFactory.getInstance(AccessorType.READING);

    private static final Log logger = LogFactory.getLog(ReadingsController.class);

    @RequestMapping(value = "/{deviceTag}/{dataTypeTag}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Gets readings by device tag", notes = "Optional time range in epoch millis")
    public Response<Reading[]> readingGet(@PathVariable String deviceTag, @PathVariable String dataTypeTag,
                               @RequestParam(value = "start", required=false) Long start, @RequestParam(value = "end", required=false) Long end,
                               HttpServletResponse response)
    {
        logger.info("Called: GET /device/"+deviceTag+"/"+dataTypeTag+"/");
        try
        {
            response.setStatus(HttpServletResponse.SC_OK);
            Device device = deviceAccessor.getDeviceFromTag(deviceTag);
            DataType dataType = dataTypeAccessor.getDataTypeFromTag(dataTypeTag);
            Collection<Reading> readings = readingAccessor.getReadingsFor(device,dataType,start,end);
            return new Response<>(readings.toArray(new Reading[readings.size()]));
        } catch (Exception e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response<>(false, "SQL error");
        }
    }

    @RequestMapping(value = "/{deviceTag}/{dataTypeTag}", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "Submits readings by device tag", notes = "Optional time range in epoch millis")
    public Response readingPost(@PathVariable String deviceTag, @PathVariable String dataTypeTag,
                                @RequestBody Reading[] readings,
                                HttpServletResponse response)
    {
        logger.info("Called: POST /device/"+deviceTag+"/"+dataTypeTag+"/");
        try
        {
            response.setStatus(HttpServletResponse.SC_OK);
            Device device = deviceAccessor.getDeviceFromTag(deviceTag);
            DataType dataType = dataTypeAccessor.getDataTypeFromTag(dataTypeTag);
            int count = readingAccessor.insertReadings(device,dataType,readings);
            return new Response<>(true,"Done, inserted " + count + " readings of " + readings.length);
        } catch (SQLException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        }
    }

    @RequestMapping(value = "/{deviceTag}/{dataTypeTag}", method = RequestMethod.DELETE, produces = "application/json")
    @ApiOperation(value = "Deletes readings by device tag", notes = "Required time range in epoch millis")
    public Response<String> readingDelete(@PathVariable String deviceTag, @PathVariable String dataTypeTag,
                               @RequestParam(value = "start") Long start, @RequestParam(value = "end") Long end,
                               HttpServletResponse response)
    {
        logger.info("Called: DELETE /device/"+deviceTag+"/"+dataTypeTag+"/");
        try
        {
            response.setStatus(HttpServletResponse.SC_OK);
            Device device = deviceAccessor.getDeviceFromTag(deviceTag);
            DataType dataType = dataTypeAccessor.getDataTypeFromTag(dataTypeTag);
            int count = readingAccessor.deleteReadings(device,dataType,start,end);
            return new Response<>(true,"Done, deleted " + count);
        } catch (Exception e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response<>(false, "SQL error");
        }
    }
}
