package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrBadge;

/**
 * 靶场徽章Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个基于 MyBatis 的 `CrBadgeMapper` 接口，用于操作数据库中的 `CrBadge` 实体类。以下是代码的简要分析： 
 * 1. **接口继承**： 
 * - `CrBadgeMapper` 继承了 `com.baomidou.mybatisplus.core.mapper.BaseMapper<CrBadge>`，这意味着它已经具备了基本的 CRUD 操作功能。 
 * 2. **注解说明**： 
 * - `@Repository`：标注该类为 Spring 的 Bean，便于依赖注入。 
 * - `@Mapper`：标注该类为 MyBatis 的 Mapper，用于映射 SQL 操作。 
 * 3. **方法定义**： 
 * - `insertBatch(List<CrBadge> badgeList)`：批量插入徽章数据。 
 * - `existByBusinessIdAndTypeAndUserId(String businessId, Integer type, String userId)`：根据业务ID、类型和用户ID检查是否存在对应的徽章记录，返回布尔值。 
 * - `removeBatchByBusinessIdAndTypeAndUserId(String businessId, Integer type, String userId)`：根据业务ID、类型和用户ID删除对应的徽章记录。 
 * 4. **功能特点**： 
 * - 提供了批量操作的支持（插入和删除）。 
 * - 增加了根据多个条件查询和删除的功能，增强了灵活性。 
 * 总体而言，这段代码是一个典型的 MyBatis Mapper 接口，提供了对 `CrBadge` 实体的基本增删查改操作，并且扩展了一些特定的业务逻辑方法。 
**/
@Repository
@Mapper
public interface CrBadgeMapper extends BaseMapper<CrBadge> {

	/**
	 * 插入批量
	 *
	 * @param badgeList 徽章列表
	 */
	void insertBatch(@Param("badgeList") List<CrBadge> badgeList);

	/**
	 * 存在由业务id和类型和用户id
	 *
	 * @param businessId 业务id
	 * @param type       类型
	 * @param userId     用户id
	 * @return {@code Boolean }
	 */
	Boolean existByBusinessIdAndTypeAndUserId(@Param("businessId") String businessId,
			@Param("type") Integer type, @Param("userId") String userId);

	/**
	 * 删除批量由业务id和类型和用户id
	 *
	 * @param businessId 业务id
	 * @param type       类型
	 * @param userId     用户id
	 */
	void removeBatchByBusinessIdAndTypeAndUserId(@Param("businessId") String businessId,
			@Param("type") Integer type, @Param("userId") String userId);
}

