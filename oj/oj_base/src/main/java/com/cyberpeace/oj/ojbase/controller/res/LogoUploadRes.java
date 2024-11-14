package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 图标上传结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
@AllArgsConstructor
public class LogoUploadRes implements Serializable {

	/**
	 * 可访问路径
	 */
	@ApiModelProperty("可访问路径")
    private String url;

}
