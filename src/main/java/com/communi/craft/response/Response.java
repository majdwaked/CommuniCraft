package com.communi.craft.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response <T>
{
    private int status;

    private String message;

    private T data;
}