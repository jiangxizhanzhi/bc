/**
 * 这段代码定义了一个名为 `SpaceAptReportService` 的接口，它继承自 `IService<SpaceAptReport>`。这个接口扩展了 MyBatis-Plus 提供的基本 CRUD 操作功能，并添加了两个自定义方法： 
 * 1. **dump(List<String> ids)**: 此方法接收一个字符串 ID 列表作为参数，返回一个表示导出结果的字符串。 
 * 2. **loadReport(String realPath)**: 此方法接收一个真实路径作为参数，返回一个布尔值，用于指示加载报告是否成功。 
 * 该接口主要用于处理与 `SpaceAptReport` 实体相关的业务逻辑，包括数据的导入和报告的加载等功能。通过这种方式，开发者可以利用 MyBatis-Plus 的强大功能来简化数据库操作，同时专注于实现特定的业务需求。 
**/
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceAptReport;

import java.util.List;

/**
 * 空间容易报告Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceAptReportService extends IService<SpaceAptReport> {

	/**
	 * 导出
	 *
	 * @param ids id列表
	 * @return 字符串
	 * @throws
	 * @see List<String>
	 * @see String
	 */
	String dump(List<String> ids);

	/**
	 * 加载报告
	 *
	 * @param realPath 真实路径
	 * @return 布尔
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean loadReport(String realPath);
}
