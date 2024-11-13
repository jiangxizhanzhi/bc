package com.cyberpeace.cs.dao.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.VideoProgress;

/**
 * 视频进展Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
@Repository
public interface VideoProgressMapper extends BaseMapper<VideoProgress> {

	/**
	 * 查询由路径和用户id
	 *
	 * @param videoPath 视频路径
	 * @param userId    用户id
	 * @return {@code VideoProgress }
	 */
	VideoProgress selectByPathAndUserId(@Param("videoPath") String videoPath,
			@Param("userId") String userId);

	/**
	 * 查询由用户id和路径
	 *
	 * @param userId 用户id
	 * @param paths  路径
	 * @return {@code List<VideoProgress> }
	 */
	List<VideoProgress> selectByUserIdAndPaths(@Param("userId") String userId,
			@Param("paths") Collection<String> paths);
}
