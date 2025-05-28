/**
 * 这段代码定义了一个名为 `SystemPropertyService` 的接口，它是基于 MyBatis-Plus 提供的通用 Service 接口 `IService` 扩展而来。以下是代码的简要分析： 
 * 1. **继承关系**： 
 * - `SystemPropertyService` 继承了 `com.baomidou.mybatisplus.extension.service.IService`，这意味着它具备了 MyBatis-Plus 提供的基本 CRUD 操作能力，并且泛型参数为 `SystemProperty`，表示该 Service 操作的对象类型是 `SystemProperty`。 
 * 2. **功能扩展**： 
 * - 在继承通用 Service 的基础上，接口中新增了一个自定义方法 `getExportBuiltin()`。 
 * - 该方法返回一个布尔值（`boolean`），用于获取某种导出内置的状态，但具体逻辑未在接口中实现，需由实现类完成。 
 * 3. **注释说明**： 
 * - 接口上方有 Javadoc 注释，表明其用途为“系统配置 Service”，并标明作者和日期。 
 * - 方法 `getExportBuiltin()` 也有详细的注释，描述了方法的功能和返回值类型。 
 * 总结：这是一个扩展了 MyBatis-Plus 功能的服务接口，主要用于操作 `SystemProperty` 对象，并额外提供了一个自定义方法来获取导出内置的状态信息。 
**/
package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.SystemProperty;

/**
 * 系统配置Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface SystemPropertyService extends IService<SystemProperty> {

	/**
	 * 获取导出内置
	 *
	 * @return boolean
	 */
	boolean getExportBuiltin();
}
