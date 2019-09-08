package com.demo.consumer.dto.util;

import com.demo.consumer.dto.ErrorDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ExceptionMessage extends Exception {

    private ErrorDto errorDto;

}
