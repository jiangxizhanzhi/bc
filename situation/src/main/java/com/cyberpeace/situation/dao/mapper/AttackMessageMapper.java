package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.AttackMessage;

/**
 * 攻击消息映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
public interface AttackMessageMapper extends BaseMapper<AttackMessage> {

	/**
	 * 新增攻击消息
	 *
	 * @param attackMessage 攻击消息
	 */
	void add(AttackMessage attackMessage);

	/**
	 * 通过场景id删除
	 *
	 * @param sceneId 场景id
	 */
	void deleteBySceneId(String sceneId);

}
