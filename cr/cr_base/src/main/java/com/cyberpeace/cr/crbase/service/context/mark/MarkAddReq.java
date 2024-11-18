package com.cyberpeace.cr.crbase.service.context.mark;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 标记新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/24
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MarkAddReq implements Serializable {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练房间id")
	private String roomId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 证据
	 *
	 * @see List<EvidenceBo>
	 */
	@ApiModelProperty(value = "证据")
	private List<EvidenceBo> evidence;

	public void validate() {
		CrCommonValidator.validateNameLen(this.name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		if (!StringUtils.isEmpty(description)) {
			CrCommonValidator.validateDescLen(this.description,
					CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		}
	}
}
