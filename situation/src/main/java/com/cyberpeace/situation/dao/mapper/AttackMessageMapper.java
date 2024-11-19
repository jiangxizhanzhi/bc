package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.AttackMessage;

/**
 * 攻击消息Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface AttackMessageMapper extends BaseMapper<AttackMessage> {

	/**
	 * 新增
	 *
	 * @param attackMessage 攻击消息
	 * @throws
	 * @see AttackMessage
	 */
	void add(AttackMessage attackMessage);

	/**
	 * 删除由场景id
	 *
	 * @param sceneId 场景id
	 * @throws
	 * @see String
	 */
	void deleteBySceneId(String sceneId);

}
