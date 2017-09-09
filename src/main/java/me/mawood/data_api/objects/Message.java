package me.mawood.data_api.objects;

import java.time.Instant;

public class Message
{

    private final String content;
    private final Instant timestamp;

    public Message(String content) {
        this.content = content;
        timestamp = Instant.now();
    }

    public String getContent() {
        return content;
    }

    public Instant getTimestamp()
    {
        return timestamp;
    }
}