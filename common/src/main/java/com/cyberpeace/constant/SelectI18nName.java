package com.cyberpeace.constant;

import com.cyberpeace.common.model.CommonContext;

/**
 * 适用于数据库中保存中英文名称(cnName, enName)再根据上下文取国际化名称的情形
 *
 * @author shazhichuan@version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
public interface SelectI18nName {
	/**
	 * 返回中文名称
	 *
	 * @return 中文名称
	 * @throws
	 * @see String
	 */
	String getCnName();

	/**
	 * 返回英文名称
	 *
	 * @return 英文名称
	 * @throws
	 * @see String
	 */
	String getEnName();

	/**
	 * 获取国际化名称的默认方法
	 *
	 * @return 国际化名称
	 * @throws
	 * @see String
	 */
	default String selectI18nName() {
		return CommonContext.isChinese() ? getCnName() : getEnName();
	}
}
