package com.cyberpeace.situation.controller.req;

import com.cyberpeace.situation.dao.entity.LayerShow;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 13/1/2023 上午11:50
 */
@Data
public class LayerShowReq extends LayerShow implements Serializable {
    @ApiModelProperty("组合图层")
    private List<LayerShowReq> children;
}
