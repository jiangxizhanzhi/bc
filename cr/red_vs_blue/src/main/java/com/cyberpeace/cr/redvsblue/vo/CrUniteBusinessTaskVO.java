package com.cyberpeace.cr.redvsblue.vo;

import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessLink;
import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessNode;
import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CrUniteBusinessTaskVO extends CrUniteBusinessTask {

    @ApiModelProperty(value = "设备信息", name = "nodes")
    private List<CrUniteBusinessNode> nodes;

    @ApiModelProperty(value = "链路信息", name = "links")
    private List<CrUniteBusinessLink> links;
}
