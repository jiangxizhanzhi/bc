package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 课堂标志在请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class ClassroomSignInReq implements Serializable {

    /**
     * 课堂编号
     *
     * @see String
     */
    @ApiModelProperty("课堂编号")
	@NotBlank
	private String resourceId;

}
