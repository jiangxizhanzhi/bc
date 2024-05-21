package com.cyberpeace.common.annoations;

import java.lang.annotation.*;

import com.cyberpeace.common.enums.BehaviorModuleEnum;
import com.cyberpeace.common.enums.BehaviorTypeEnum;

/**
 * @author SN
 * @date 2024/05/20
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BehaviorRecord {

	/**
	 * 行为所属业务模块
	 * @return {@code BehaviorModuleEnum}
	 */
	BehaviorModuleEnum module();

	/**
	 * 行为类型
	 * @return {@code BehaviorTypeEnum}
	 */
	BehaviorTypeEnum action();

	/**
	 * 行为目标的id字段
	 * @return {@code String}
	 */
	String target() default "resource_id";

	/**
	 * 目标名称在response中的字段
	 * @return {@code String}
	 */
	String name() default "name";

	/**
	 * 行为结果在response中的字段
	 * @return {@code String}
	 */
	String result() default "";
}
