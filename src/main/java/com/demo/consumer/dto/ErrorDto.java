package com.demo.consumer.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ErrorDto {
    private String code;
    private String msg;
}
