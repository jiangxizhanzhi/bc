package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;

/**
 * 靶场得分Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口 `CrScoreMapper`，用于操作数据库中的 `CrScore` 表。以下是对代码的简要分析： 
 * 1. **注解与继承**： 
 * - 使用了 `@Mapper` 注解，表明这是一个 MyBatis 的 Mapper 接口。 
 * - 继承了 `com.baomidou.mybatisplus.core.mapper.BaseMapper<CrScore>`，这提供了通用的 CRUD 操作方法。 
 * 2. **接口方法**： 
 * - `getByRoomIdAndUser`：根据房间 ID 和用户 ID 查询单个 `CrScore` 记录。 
 * - `selectByRoomIdAndUserIds`：根据房间 ID 和多个用户 ID 查询多个 `CrScore` 记录。 
 * - `selectByRoomId`：根据房间 ID 查询多个 `CrScore` 记录，使用了 `@Param` 注解来指定参数名称。 
 * 3. **注解与作用域**： 
 * - `@Repository` 注解表明这是一个 Spring 管理的持久层组件。 
 * 4. **功能描述**： 
 * - 这个接口主要用于管理靶场得分数据，提供基于房间和用户的查询功能。 
 * 总体来说，这个接口设计简洁，功能明确，适合用于处理与 `CrScore` 相关的数据库操作。 
**/
@Mapper
@Repository
public interface CrScoreMapper extends BaseMapper<CrScore> {

	/**
	 * 获取由房间id和用户
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code CrScore }
	 */
	CrScore getByRoomIdAndUser(String roomId, String userId);

	/**
	 * 查询由房间id和用户id列表
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return {@code List<CrScore> }
	 */
	List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds);

	/**
	 * 查询由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrScore> }
	 */
	List<CrScore> selectByRoomId(@Param("roomId") String roomId);
}

