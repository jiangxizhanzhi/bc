package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.AttackMessage;

@Mapper
public interface AttackMessageMapper extends BaseMapper<AttackMessage> {

	/**
	 * 增加动效信息
	 * @param attackMessage 动效数据
	 */
	void add(AttackMessage attackMessage);

	/**
	 * 删除场景下的所有动效数据
	 * @param sceneId 场景编号
	 */
	void deleteBySceneId(String sceneId);

}
