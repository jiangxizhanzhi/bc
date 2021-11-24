package com.cyberpeace.cr.crbase.dao.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.LocalSceneManager;

@Mapper
@Repository
public interface SceneManagerMapper extends BaseMapper<LocalSceneManager> {}
