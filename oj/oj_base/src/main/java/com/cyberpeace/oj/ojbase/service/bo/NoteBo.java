package com.cyberpeace.oj.ojbase.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 注意 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class NoteBo implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("笔记资源编号")
    private String resourceId;

    /**
     * 注释类型
     *
     * @see Integer
     */
    @ApiModelProperty("笔记类型 1=笔记 2=实验报告")
    private Integer noteType;

    /**
     * 业务类型
     *
     * @see Integer
     */
    @ApiModelProperty("资源类型 1=课程 2=课时 3=练习 4=课后练习")
    private Integer businessType;

    /**
     * 内容
     *
     * @see String
     */
    @ApiModelProperty("笔记内容")
    private String content;

    /**
     * 用户id
     *
     * @see String
     */
    @ApiModelProperty("创建人")
    private String userId;

    /**
     * 业务id
     *
     * @see String
     */
    @ApiModelProperty("对应的资源编号")
    private String businessId;

    /**
     * 创建时间
     *
     * @see Date
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间
     *
     * @see Date
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 是提交
     *
     * @see Boolean
     */
    @ApiModelProperty("实验报告是否提交")
    private Boolean isSubmit;

}
