package com.cyberpeace.oj.practice.controller.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@ApiModel
@Data
public class PracticeDelReq {

    @ApiModelProperty("练习分类ID")
    @JsonProperty("ids")
    private List<String> resourceIds;
}
