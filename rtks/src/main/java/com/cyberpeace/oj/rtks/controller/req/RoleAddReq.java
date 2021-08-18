package com.cyberpeace.oj.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class RoleAddReq implements Serializable {

    private String resourceId;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String levelId;

    @NotNull
    private int category;

}
