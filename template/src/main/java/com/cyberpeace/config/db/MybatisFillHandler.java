/**
 * 这段代码实现了一个自定义的 MyBatis Plus 元对象处理器 `MybatisFillHandler`，用于在插入和更新数据库记录时自动填充字段值。以下是代码的主要功能和结构分析： 
 * ### 功能分析： 
 * 1. **插入填充 (`insertFill`)：** 
 * - 当执行插入操作时，会调用此方法。 
 * - 自动为实体类中的 `createTime` 和 `updateTime` 字段赋值为当前时间（`new Date()`）。 
 * 2. **更新填充 (`updateFill`)：** 
 * - 当执行更新操作时，会调用此方法。 
 * - 自动为实体类中的 `updateTime` 字段赋值为当前时间（`new Date()`）。 
 * 3. **依赖工具：** 
 * - 使用了 MyBatis Plus 提供的 `MetaObject` 类来动态操作实体对象的属性。 
 * - 通过 `setFieldValByName` 方法设置指定字段的值。 
 * ### 总结： 
 * 该类实现了 MyBatis Plus 的 `MetaObjectHandler` 接口，主要用于自动化处理数据库记录的 `createTime` 和 `updateTime` 字段的填充逻辑。它简化了开发过程，避免了手动设置这些字段的时间值，提高了代码的可维护性和效率。 
**/
package com.cyberpeace.config.db;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * mybatis 填充处理器
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see MetaObjectHandler
 * @since 1.0.0
 */
public class MybatisFillHandler implements MetaObjectHandler {

	/**
	 * 插入填充
	 *
	 * @param metaObject Meta 对象
	 * @throws
	 * @see MetaObject
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		this.setFieldValByName("createTime", new Date(), metaObject);
		this.setFieldValByName("updateTime", new Date(), metaObject);
	}

	/**
	 * 更新填充
	 *
	 * @param metaObject Meta 对象
	 * @throws
	 * @see MetaObject
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		this.setFieldValByName("updateTime", new Date(), metaObject);
	}
}