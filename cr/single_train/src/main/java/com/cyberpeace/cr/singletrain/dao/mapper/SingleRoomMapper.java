package com.cyberpeace.cr.singletrain.dao.mapper;

import java.util.List;

import com.cyberpeace.cr.singletrain.service.bo.SingleRoomData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoomList;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTrainStats;

/**
 * 单房间Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SingleRoomMapper extends BaseMapper<SingleRoom> {

	/**
	 * 获取单房间
	 *
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @return {@code SingleRoom }
	 * @throws
	 * @see String
	 * @see String
	 * @see SingleRoom
	 */
	SingleRoom getSingleRoom(@Param("business_template_id") String businessTemplateId,
			@Param("user_id") String userId);

	/**
	 * 单训练搜索
	 *
	 * @param page         分页
	 * @param userId       用户id
	 * @param pattern      模式
	 * @param category     类别
	 * @param difficult    难
	 * @param businessType 业务类型
	 * @return {@code IPage<SingleRoomList> }
	 * @throws
	 * @see Page
	 * @see String
	 * @see String
	 * @see Integer
	 * @see Integer
	 * @see Integer
	 * @see IPage<SingleRoomList>
	 */
	IPage<SingleRoomList> singleTrainSearch(@Param("page") Page page,
			@Param("user_id") String userId, @Param("pattern") String pattern,
			@Param("category") Integer category, @Param("difficult") Integer difficult,
			@Param("business_type") Integer businessType);

	/**
	 * 单训练统计
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @param dateRange           日期范围
	 * @param businessType        业务类型
	 * @return {@code List<SingleTrainStats> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 * @see Integer
	 * @see List<SingleTrainStats>
	 */
	List<SingleTrainStats> singleTrainStats(
			@Param("business_template_ids") List<String> businessTemplateIds,
			@Param("date_range") List<String> dateRange,
			@Param("business_type") Integer businessType);

	/**
	 * 计数由用户id
	 *
	 * @param userId 用户id
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see Integer
	 */
	Integer countByUserId(@Param("userId") String userId);

	/**
	 * 计数未完成由用户id
	 *
	 * @param userId 用户id
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see Integer
	 */
	Integer countUnfinishedByUserId(@Param("userId") String userId);

	/**
	 * 查询由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<SingleRoom> }
	 * @throws
	 * @see String
	 * @see List<SingleRoom>
	 */
	List<SingleRoom> selectByUserId(@Param("userId") String userId);

	/**
	 * 查询未完成由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<SingleRoom> }
	 * @throws
	 * @see String
	 * @see List<SingleRoom>
	 */
	List<SingleRoom> selectUnfinishedByUserId(@Param("userId") String userId);

	/**
	 * 查询由房间id列表
	 *
	 * @param roomIds 房间id列表
	 * @return {@code List<SingleRoom> }
	 * @throws
	 * @see List<String>
	 * @see List<SingleRoom>
	 */
	List<SingleRoom> selectByRoomIds(@Param("roomIds") List<String> roomIds);

	/**
	 * 查询由模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code List<SingleRoom> }
	 * @throws
	 * @see String
	 * @see List<SingleRoom>
	 */
	List<SingleRoom> selectByTemplateId(@Param("businessTemplateId") String businessTemplateId);

	/**
	 * 查询都由用户id
	 *
	 * @param userId 用户id
	 * @param type   类型
	 * @return {@code List<SingleRoom> }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see List<SingleRoom>
	 */
	List<SingleRoom> selectAllByUserId(@Param("userId") String userId, @Param("type") Integer type);
}
