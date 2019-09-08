package com.demo.consumer.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseDb {

    private int count_mutant_dna;
    private int count_human_dna;
    private int ratio;
}
