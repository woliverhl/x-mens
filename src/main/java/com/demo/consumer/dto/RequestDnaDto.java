package com.demo.consumer.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RequestDnaDto {

    public String[] dna;
}
