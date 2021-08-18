package com.cyberpeace.oj.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class RoleRelatedAddReq implements Serializable {

    @NotBlank
    private String role_id;

    @NotNull
    private List<RoleRelatedInput> items;
}
