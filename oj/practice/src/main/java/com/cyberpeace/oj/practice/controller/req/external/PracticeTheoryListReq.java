package com.cyberpeace.oj.practice.controller.req.external;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 外部接口使用 无必要勿改
 */
@Data
@ApiModel
public class PracticeTheoryListReq extends PageReqBase {

    @ApiModelProperty(value = "难度: 0-初级, 1-中级, 2-高级", allowableValues = "0, 1, 2")
    private Integer difficulty;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("习题集")
    private String practiceSet;

    @ApiModelProperty(value = "理论基础题型查询: 0-单选, 1-多选, 2-判断, 3-填空, 4-简答", allowableValues = "0, 1, 2, 3, 4")
    private Integer questionType;

    @ApiModelProperty(value = "用户编号")
    private String userId;

}
