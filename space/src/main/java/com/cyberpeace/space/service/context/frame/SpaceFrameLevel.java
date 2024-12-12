package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.StreamApiUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 动作框架层级
 *
 * @author SN
 * @date 2024/11/05
 */
@Getter
@Setter
@NoArgsConstructor
public class SpaceFrameLevel {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 子级
	 */
	private List<SpaceFrameLevel> children;

	public SpaceFrameLevel(String resourceId, String name){
		this.resourceId = resourceId;
		this.name = name;
		this.children = Lists.newArrayList();
	}

	/**
	 * 检查框架
	 *
	 * @param ignoreFirstLevelId 忽视第一级id
	 */
	public void checkLevel(boolean ignoreFirstLevelId) {
		if (null == this.children) {
			this.children = Lists.newArrayList();
		}
		if (!ignoreFirstLevelId && StringUtils.isBlank(this.resourceId)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		List<SpaceFrameLevel> childSet = StreamApiUtils.distinctByColumn(this.children,
				SpaceFrameLevel::getResourceId);
		if (childSet.size() != this.children.size()) {
			throw new BusinessException(ErrorCode.DUPLICATE_ID);
		}
		for (SpaceFrameLevel child : this.children) {
			child.checkLevel(false);
		}
	}

}
