package com.cyberpeace.cs.dao.mapper;

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
}
