package com.cyberpeace.integratation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttackLogPageContext implements Serializable {
    private String id;

    private String startTime;

    private String endTime;

    private String sourceIps;

}
