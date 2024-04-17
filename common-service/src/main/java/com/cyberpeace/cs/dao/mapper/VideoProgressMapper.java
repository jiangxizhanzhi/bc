package com.cyberpeace.cs.dao.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.VideoProgress;

@Mapper
@Repository
public interface VideoProgressMapper extends BaseMapper<VideoProgress> {

	VideoProgress selectByPathAndUserId(@Param("videoPath") String videoPath,
			@Param("userId") String userId);

	List<VideoProgress> selectByUserIdAndPaths(@Param("userId") String userId,
			@Param("paths") Collection<String> paths);
}
