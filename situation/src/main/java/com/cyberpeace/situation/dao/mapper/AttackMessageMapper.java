package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.AttackMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttackMessageMapper extends BaseMapper<AttackMessage> {

    void add(AttackMessage attackMessage);

    void deleteBySceneId(String sceneId);

}
