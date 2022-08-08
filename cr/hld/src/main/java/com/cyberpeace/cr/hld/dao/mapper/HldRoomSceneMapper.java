package com.cyberpeace.cr.hld.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.hld.dao.entity.HldRoomScene;
import com.cyberpeace.cr.hld.service.context.HldRoomSceneContext;

@Mapper
@Repository
public interface HldRoomSceneMapper extends BaseMapper<HldRoomScene> {

	List<HldRoomSceneContext> listSceneIds(String hldRoomId);

	HldRoomScene selectByMpSceneId(String mpSceneId);

    List<HldRoomScene> selectByRoomId(String roomId);

}
