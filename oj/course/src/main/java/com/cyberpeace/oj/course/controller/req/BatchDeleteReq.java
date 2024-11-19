package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 批量删除请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class BatchDeleteReq implements Serializable {

    /**
     * 资源编号数组
     *
     * @see List<String>
     */
    @ApiModelProperty("资源编号数组")
    @NotEmpty
    private List<String> resourceIds;

}
