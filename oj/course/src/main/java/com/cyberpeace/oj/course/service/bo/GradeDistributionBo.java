package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 年级分配Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class GradeDistributionBo implements Serializable {

    /**
     * 良好
     *
     * @see Integer
     */
    @ApiModelProperty("良好")
	private Integer excellent = 0;

    /**
     * 满分
     *
     * @see Integer
     */
    @ApiModelProperty("满分")
	private Integer fullScore = 0;

    /**
     * 优秀
     *
     * @see Integer
     */
    @ApiModelProperty("优秀")
	private Integer good = 0;

    /**
     * 未通过
     *
     * @see Integer
     */
    @ApiModelProperty("未通过")
	private Integer notPass = 0;

    /**
     * 及格
     *
     * @see Integer
     */
    @ApiModelProperty("及格")
	private Integer pass = 0;

}
