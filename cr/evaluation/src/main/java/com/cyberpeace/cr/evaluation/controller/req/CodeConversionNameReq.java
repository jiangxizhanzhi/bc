package com.cyberpeace.cr.evaluation.controller.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodeConversionNameReq {
    private Integer code;

    private String name;
}
