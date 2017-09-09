package me.mawood.data_api.objects;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */
public class Response<E>
{
    private final E data;
    private final boolean succeeded;
    private final String message;

    public Response(E data, boolean succeeded, String message)
    {
        this.data = data;
        this.succeeded = succeeded;
        this.message = message;
    }

    public Response(E data)
    {
        this(data,true,"");
    }

    public Response(boolean succeeded, String message)
    {
        this(null,succeeded,message);
    }

    public Response(Exception e)
    {
        this(null,false, e.getMessage());
    }

    public E getData()
    {
        return data;
    }

    public boolean isSucceeded()
    {
        return succeeded;
    }

    public String getMessage()
    {
        return message;
    }
}
