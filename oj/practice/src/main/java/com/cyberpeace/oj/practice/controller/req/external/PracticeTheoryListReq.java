package com.cyberpeace.oj.practice.controller.req.external;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 外部接口使用 无必要勿改
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class PracticeTheoryListReq extends PageReqBase {

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty(value = "难度: 0-初级, 1-中级, 2-高级", allowableValues = "0, 1, 2")
    private Integer difficulty;

    /**
     * 类别
     *
     * @see String
     */
    @ApiModelProperty("分类")
    private String category;

    /**
     * 实践设置
     *
     * @see String
     */
    @ApiModelProperty("习题集")
    private String practiceSet;

    /**
     * 问题类型
     *
     * @see Integer
     */
    @ApiModelProperty(value = "理论基础题型查询: 0-单选, 1-多选, 2-判断, 3-填空, 4-简答", allowableValues = "0, 1, 2, 3, 4")
    private Integer questionType;

    /**
     * 用户id
     *
     * @see String
     */
    @ApiModelProperty(value = "用户编号")
    private String userId;

}
