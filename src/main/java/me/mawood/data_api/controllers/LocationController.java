package me.mawood.data_api.controllers;

import me.mawood.data_api.objects.DataType;
import me.mawood.data_api.objects.Location;
import me.mawood.data_api.objects.Reading;
import me.mawood.data_api.objects.Response;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessor;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessorFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */

@RestController
@RequestMapping("/location")
public class LocationController
{
    private static final SQLDataAccessor sql = SQLDataAccessorFactory.getInstance();

    @RequestMapping(value = "/{locationTag}/{dataTypeTag}", method = RequestMethod.GET, produces = "application/json")
    public Response<Collection<Reading>> readingGet(@PathVariable String locationTag, @PathVariable String dataTypeTag,
                                                 @RequestParam(value = "start", required=false) Long start, @RequestParam(value = "end", required=false) Long end)
    {
        try
        {
            Location location = sql.getLocationFromTag(locationTag);
            DataType dataType = sql.getDataTypeFromTag(dataTypeTag);
            return new Response<>(sql.getReadingsFor(location,dataType,start,end));
        } catch (Exception e)
        {
            return new Response<>(e);
        }
    }

    @RequestMapping(value = "/{locationTag}/{dataTypeTag}", method = RequestMethod.POST, produces = "application/json")
    public Response<String> readingPost(@PathVariable String locationTag, @PathVariable String dataTypeTag,
                                        @RequestBody Collection<Reading> readings)
    {
        try
        {
            Location location = sql.getLocationFromTag(locationTag);
            DataType dataType = sql.getDataTypeFromTag(dataTypeTag);
            int count = sql.insertReadings(location,dataType,readings);
            return new Response<>(true,"Done, inserted " + count + " readings of " + readings.size());
        } catch (SQLException e)
        {
            return new Response<>(e);
        }
    }
}
