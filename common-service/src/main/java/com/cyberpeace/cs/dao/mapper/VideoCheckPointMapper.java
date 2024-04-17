package com.cyberpeace.cs.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.VideoCheckPoint;

@Mapper
@Repository
public interface VideoCheckPointMapper extends BaseMapper<VideoCheckPoint> {

	VideoCheckPoint selectByPath(@Param("videoPath") String videoPath);
}
