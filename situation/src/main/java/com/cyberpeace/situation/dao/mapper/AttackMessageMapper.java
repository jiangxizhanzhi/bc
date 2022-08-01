package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.AttackMessage;

@Mapper
public interface AttackMessageMapper extends BaseMapper<AttackMessage> {

	void add(AttackMessage attackMessage);

	void deleteBySceneId(String sceneId);

}
