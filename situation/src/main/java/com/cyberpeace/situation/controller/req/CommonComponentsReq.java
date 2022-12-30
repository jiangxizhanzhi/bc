package com.cyberpeace.situation.controller.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 29/12/2022 上午10:03
 */

@Data
public class CommonComponentsReq implements Serializable {

    @NotBlank(groups = Update.class)
    private String resourceId;

    @NotBlank(groups = Create.class)
    @ApiModelProperty("组件名称")
    private String name;

    @NotNull(groups = Create.class)
    @ApiModelProperty("组件类型")
    private Integer type;

    @NotBlank(groups = Create.class)
    @ApiModelProperty("封面图")
    private String img;

    @NotBlank(groups = Create.class)
    @ApiModelProperty("实际资源")
    private String url;
}
