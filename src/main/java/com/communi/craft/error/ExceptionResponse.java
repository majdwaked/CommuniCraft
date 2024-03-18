package com.communi.craft.error;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse
{
    private int status;

    private String message;

    private long timestamp;
}