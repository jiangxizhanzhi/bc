/**
 * 该代码定义了一个名为 `CrMarkMapper` 的接口，用于操作数据库中的 `CrMark` 实体类。以下是对其的简要分析： 
 * 1. **注解与继承**： 
 * - 使用了 `@Mapper` 注解，表明这是一个 MyBatis 的 Mapper 接口。 
 * - 继承了 `BaseMapper<CrMark>`，这意味着它自动获得了 MyBatis-Plus 提供的基本 CRUD 操作功能。 
 * 2. **方法定义**： 
 * - `selectByRoomId(String roomId)`：根据房间 ID 查询所有相关的标记记录。 
 * - `selectByRoomIdAndUserId(String roomId, String userId)`：根据房间 ID 和用户 ID 查询相关的标记记录。 
 * 3. **用途**： 
 * - 此 Mapper 主要用于管理靶场标记（`CrMark`）的数据，支持基于房间 ID 或结合房间 ID 和用户 ID 的查询操作。 
 * 总体而言，这段代码是一个典型的 MyBatis Mapper 接口，利用了 MyBatis-Plus 的便捷功能来实现数据访问层的功能。 
**/
package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrMark;

/**
 * 靶场标记Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface CrMarkMapper extends BaseMapper<CrMark> {

	/**
	 * 查询由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrMark> }
	 */
	List<CrMark> selectByRoomId(@Param("roomId") String roomId);

	/**
	 * 查询由房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code List<CrMark> }
	 */
	List<CrMark> selectByRoomIdAndUserId(@Param("roomId") String roomId,
			@Param("userId") String userId);
}
