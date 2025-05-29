package com.cyberpeace.cr.tool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.controller.res.WebToolCategoryRsp;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.service.context.ToolTypeCategory;

import java.util.List;

/**
 * 工具类别Service
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ToolCategoryService` 的接口，该接口扩展了 `IService<ToolCategory>` 接口。这个服务接口主要用于处理与工具分类相关的业务逻辑。以下是对代码的简要分析： 
 * ### 包声明 
 * - `package com.cyberpeace.cr.tool.service;` 
 * - 定义了包名，表明这个接口属于 `com.cyberpeace.cr.tool.service` 包。 
 * ### 导入的类和接口 
 * - 导入了一些常用的工具类和自定义类： 
 * - `com.baomidou.mybatisplus.extension.service.IService<ToolCategory>` 
 * - `com.cyberpeace.common.model.ListRsp` 
 * - `com.cyberpeace.common.model.PageReqBase` 
 * - `com.cyberpeace.cr.tool.controller.res.WebToolCategoryRsp` 
 * - `com.cyberpeace.cr.tool.dao.entity.ToolCategory` 
 * - `com.cyberpeace.cr.tool.service.context.ToolTypeCategory` 
 * ### 接口描述 
 * - 注释说明了这个接口是用于工具类别的服务。 
 * ### 方法定义 
 * 1. **分页查询工具类型** 
 * - `ListRsp<ToolCategory> selectPage(PageReqBase listReq, String type);` 
 * - 根据分页参数和类型进行分页查询。 
 * 2. **查询所有工具分类** 
 * - `List<ToolCategory> selectAll();` 
 * - 返回所有工具分类的信息列表。 
 * 3. **前台查询所有工具分类（国际化处理）** 
 * - `List<WebToolCategoryRsp> webSelectAll(String type);` 
 * - 根据类型查询所有工具分类，并进行国际化处理。 
 * 4. **根据名称获取工具分类** 
 * - `ToolCategory getByName(String name, String type);` 
 * - 根据工具分类名称获取工具分类信息。 
 * 5. **根据类型获取工具分类** 
 * - `List<ToolCategory> getByType(String type);` 
 * - 根据类型获取工具分类列表。 
 * 6. **创建工具分类** 
 * - `ToolCategory saveCategory(String name, String type);` 
 * - 创建新的工具分类。 
 * 7. **批量删除工具分类** 
 * - `void batchDelete(List<String> resourceIds);` 
 * - 根据工具分类ID列表批量删除工具分类。 
 * 8. **验证工具分类名称是否重复** 
 * - `void validateToolCategoryNameExist(ToolCategory toolCategory);` 
 * - 验证工具分类名称是否存在。 
 * 9. **新增工具分类** 
 * - `void saveToolCategory(ToolCategory toolCategory);` 
 * - 保存工具分类。 
 * 10. **更新工具分类** 
 * - `void updateToolCategoryById(ToolCategory toolCategory);` 
 * - 根据ID更新工具分类。 
 * 11. **获取所有类型类别** 
 * - `List<ToolTypeCategory> getAllTypeCategory();` 
 * - 返回所有类型类别。 
 * ### 总结 
 * - 这个接口提供了丰富的工具分类管理功能，包括查询、创建、更新和删除等操作。 
 * - 提供了国际化支持，可以根据不同的语言环境返回相应的工具分类信息。 
 * - 接口设计清晰，方法命名规范，易于理解和使用。 
**/
public interface ToolCategoryService extends IService<ToolCategory> {

	/**
	 * 工具类型分页
	 *
	 * @param listReq 分页参数
	 * @param type
	 * @return 分页后工具分类
	 */
	ListRsp<ToolCategory> selectPage(PageReqBase listReq, String type);

	/**
	 * 查询所有工具分类
	 *
	 * @return 所有工具分类信息列表
	 */
	List<ToolCategory> selectAll();

	/**
	 * 前台查询所有工具分类（区分用户设置语言, 进行国际化处理）
	 *
	 * @param type 类型
	 * @return 所有工具分类列表
	 */
	List<WebToolCategoryRsp> webSelectAll(String type);

	/**
	 * 根据工具分类名称获取工具分类信息
	 *
	 * @param name 工具分类名称
	 * @return 工具分类数据对象
	 */
	ToolCategory getByName(String name, String type);

	List<ToolCategory> getByType(String type);

	/**
	 * 创建工具分类
	 *
	 * @param name 工具分类名称
	 * @return 工具分类数据对象
	 */
	ToolCategory saveCategory(String name, String type);

	/**
	 * 批量删除工具分类
	 *
	 * @param resourceIds 工具分类ID列表
	 */
	void batchDelete(List<String> resourceIds);

	/**
	 * 验证工具分类名称是否重复
	 *
	 * @param toolCategory 工具分类数据对象
	 */
	void validateToolCategoryNameExist(ToolCategory toolCategory);

	/**
	 * 新增工具分类
	 *
	 * @param toolCategory 工具分类数据对象
	 */
	void saveToolCategory(ToolCategory toolCategory);

	/**
	 * 更新工具分类
	 *
	 * @param toolCategory 工具分类数据对象
	 */
	void updateToolCategoryById(ToolCategory toolCategory);

	/**
	 * 获取全部类型类别
	 *
	 * @return {@code List<ToolTypeCategory> }
	 */
	List<ToolTypeCategory> getAllTypeCategory();

}

