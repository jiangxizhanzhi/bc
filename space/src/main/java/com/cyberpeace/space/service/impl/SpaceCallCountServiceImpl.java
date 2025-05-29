package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceCallCount;
import com.cyberpeace.space.dao.mapper.SpaceCallCountMapper;
import com.cyberpeace.space.service.SpaceCallCountService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间调用计数Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/12
 * @see ServiceImpl
 * @see SpaceCallCountService
 * @since 1.0.0
 */
/**
 * ### 分析与总结 
 * #### 1. **代码概述** 
 * 该代码定义了一个 `SpaceCallCountServiceImpl` 类，实现了 `SpaceCallCountService` 接口。主要功能是管理空间调用计数（`SpaceCallCount`），包括新增计数、获取计数以及批量增加计数。 
 * #### 2. **类结构** 
 * - **继承关系**： 
 * - 继承自 `ServiceImpl<SpaceCallCountMapper, SpaceCallCount>`，这是一个基于 MyBatis-Plus 的通用服务实现类，提供了基本的 CRUD 操作。 
 * - **注解**： 
 * - `@Service`：将该类标记为 Spring 容器中的一个 Bean。 
 * - `@Slf4j`：引入日志功能，方便调试和记录日志。 
 * #### 3. **核心方法** 
 * ##### 方法 1: `addCount(String name)` 
 * - 功能：根据名称新增或更新计数。 
 * - 流程： 
 * 1. 调用 `selectByName` 查找是否存在对应的计数记录。 
 * 2. 如果不存在，创建新的 `SpaceCallCount` 对象并设置初始值为 1，然后保存。 
 * 3. 如果存在，将现有计数加 1 并更新数据库。 
 * ##### 方法 2: `getCountByName(String name)` 
 * - 功能：根据名称获取计数值。 
 * - 流程： 
 * 1. 调用 `selectByName` 查找对应记录。 
 * 2. 如果记录不存在，返回默认值 0；否则返回其计数值。 
 * ##### 方法 3: `addCount(String name, int num)` 
 * - 功能：根据名称批量增加计数。 
 * - 流程： 
 * 1. 调用 `selectByName` 查找是否存在对应的计数记录。 
 * 2. 如果不存在，创建新的 `SpaceCallCount` 对象并设置初始值为 `num`，然后保存。 
 * 3. 如果存在，将现有计数加上 `num` 并更新数据库。 
 * ##### 私有方法: `selectByName(String name)` 
 * - 功能：通过名称查询对应的 `SpaceCallCount` 记录。 
 * - 使用 MyBatis-Plus 的 `lambdaQuery` 和条件构造器查找唯一记录。 
 * #### 4. **依赖** 
 * - **Mapper**: `SpaceCallCountMapper` 提供了数据库操作接口。 
 * - **Entity**: `SpaceCallCount` 是数据模型类，包含字段如 `name` 和 `calls`。 
 * - **MyBatis-Plus**: 提供了通用的增删改查功能。 
 * #### 5. **优点** 
 * - **模块化设计**：每个方法职责清晰，便于维护和扩展。 
 * - **异常处理缺失**：未见显式的异常捕获，建议在实际应用中加入异常处理逻辑以增强健壮性。 
 * - **日志记录**：使用 `@Slf4j` 提供了日志记录能力，便于排查问题。 
 * #### 6. **潜在改进** 
 * - **线程安全性**：当前实现假设多线程环境下不会出现竞争条件，若需要支持高并发，需进一步优化。 
 * - **参数校验**：缺少对输入参数的有效性校验，例如 `name` 是否为空或非法字符。 
 * 总体而言，该代码逻辑清晰，符合面向对象的设计原则，适合中小型项目使用。 
**/
@Service
@Slf4j
public class SpaceCallCountServiceImpl extends ServiceImpl<SpaceCallCountMapper, SpaceCallCount> implements SpaceCallCountService {

	/**
	 * 新增计数
	 *
	 * @param name 名称
	 * @throws
	 * @see String
	 */
	@Override
	public void addCount(String name) {
		SpaceCallCount count = this.selectByName(name);
		if (count == null) {
			count = new SpaceCallCount();
			count.setName(name);
			count.setCalls(1);
			this.save(count);
		} else {
			count.setCalls(count.getCalls() + 1);
			this.updateById(count);
		}
	}

	/**
	 * 获取计数由名称
	 *
	 * @param name 名称
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see Integer
	 */
	@Override
	public Integer getCountByName(String name) {
		SpaceCallCount count = this.selectByName(name);
		if (count == null) {
			return 0;
		}
		return count.getCalls();
	}

	/**
	 * 新增计数
	 *
	 * @param name 名称
	 * @param num  数量
	 * @throws
	 * @see String
	 * @see int
	 */
	@Override
	public void addCount(String name, int num) {
		SpaceCallCount count = this.selectByName(name);
		if (count == null) {
			count = new SpaceCallCount();
			count.setName(name);
			count.setCalls(num);
			this.save(count);
		} else {
			count.setCalls(count.getCalls() + num);
			this.updateById(count);
		}
	}

	/**
	 * 查询由名称
	 *
	 * @param name 名称
	 * @return {@code SpaceCallCount }
	 * @throws
	 * @see String
	 * @see SpaceCallCount
	 */
	private SpaceCallCount selectByName(String name) {
		return this.lambdaQuery().eq(SpaceCallCount::getName, name).one();
	}
}

