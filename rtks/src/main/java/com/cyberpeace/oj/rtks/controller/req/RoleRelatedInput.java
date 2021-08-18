package com.cyberpeace.oj.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class RoleRelatedInput implements Serializable {

    @NotNull
    private String relatedId;

    @NotNull
    private int type;
}
