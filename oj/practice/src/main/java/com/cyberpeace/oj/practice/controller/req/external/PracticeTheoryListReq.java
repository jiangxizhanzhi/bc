package com.cyberpeace.oj.practice.controller.req.external;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 外部接口使用 无必要勿改
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class PracticeTheoryListReq extends PageReqBase {

    /**
     * 困难
     */
    @ApiModelProperty(value = "难度: 0-初级, 1-中级, 2-高级", allowableValues = "0, 1, 2")
    private Integer difficulty;

    /**
     * 类别
     */
    @ApiModelProperty("分类")
    private String category;

    /**
     * 练习设置
     */
    @ApiModelProperty("习题集")
    private String practiceSet;

    /**
     * 问题类型
     */
    @ApiModelProperty(value = "理论基础题型查询: 0-单选, 1-多选, 2-判断, 3-填空, 4-简答", allowableValues = "0, 1, 2, 3, 4")
    private Integer questionType;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户编号")
    private String userId;

}
