package com.cyberpeace.cs.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;

@Mapper
@Repository
public interface LocalSceneMapper extends BaseMapper<LocalScene> {

	LocalScene getByBusinessInstanceId(String businessInstanceId);

}
