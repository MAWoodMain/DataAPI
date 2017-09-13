package me.mawood.data_api.objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

@ApiModel("Message")
public class Message
{

    private final String message;
    private final long timestamp;

    public Message(String content) {
        this.message = content;
        timestamp = Instant.now().toEpochMilli();
    }

    @ApiModelProperty(value = "A text message", required = true)
    public String getMessage() {
        return message;
    }

    @ApiModelProperty(value = "A timestamp in epoch millis", required = true)
    public long getTimestamp()
    {
        return timestamp;
    }
}