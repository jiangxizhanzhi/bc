package com.cyberpeace.space.service.context;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * id列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IdListReq {

	/**
	 * id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("ID列表")
	private List<String> ids;

	/**
	 * 获取id列表
	 *
	 * @return list<字符串>
	 * @throws
	 * @see List<String>
	 */
	public List<String> getIds() {
		if (CollectionUtils.isEmpty(ids)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		return this.ids;
	}
}
