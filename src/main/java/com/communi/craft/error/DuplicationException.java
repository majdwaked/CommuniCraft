package com.communi.craft.error;

public class DuplicationException extends RuntimeException
{
    public DuplicationException(String message)
    {
        super(message);
    }

    public DuplicationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DuplicationException(Throwable cause)
    {
        super(cause);
    }
}