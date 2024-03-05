package com.cyberpeace.scene.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.scene.dao.entity.ImageEntity;

@Mapper
@Repository
public interface ImageMapper extends BaseMapper<ImageEntity> {

	List<ImageEntity> selectByScenario(@Param("scenario") Integer scenario);
}
