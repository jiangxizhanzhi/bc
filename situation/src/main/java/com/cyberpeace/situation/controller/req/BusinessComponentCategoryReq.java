package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 业务组件类别请求
 *
 * @author dell
 * @date 2023/06/20
 */
@Data
public class BusinessComponentCategoryReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	@NotBlank(groups = Update.class)
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	@NotBlank(groups = Create.class)
	private String name;
}
