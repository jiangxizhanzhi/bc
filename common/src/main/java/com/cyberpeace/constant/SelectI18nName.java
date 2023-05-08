package com.cyberpeace.constant;

import com.cyberpeace.common.model.CommonContext;

/**
 * 适用于数据库中保存中英文名称(cnName, enName)再根据上下文取国际化名称的情形
 *
 * @author shazhichuan
 */
public interface SelectI18nName {

	/**
	 * 返回中文名称
	 *
	 * @return 中文名称
	 */
	String getCnName();

	/**
	 * 返回英文名称
	 *
	 * @return 英文名称
	 */
	String getEnName();

	/**
	 * 获取国际化名称的默认方法
	 *
	 * @return 国际化名称
	 */
	default String selectI18nName() {
		return CommonContext.isChinese() ? getCnName() : getEnName();
	}
}
