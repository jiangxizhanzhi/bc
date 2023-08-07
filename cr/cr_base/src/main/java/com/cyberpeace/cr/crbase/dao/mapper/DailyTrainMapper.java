package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrFlagTask;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DailyTrainMapper extends BaseMapper<CrFlagTask> {

//	IPage<SingleRoomList> singleTrainSearch(@Param("page") Page page,
//											@Param("user_id") String userId, @Param("pattern") String pattern,
//											@Param("category") Integer category, @Param("difficult") Integer difficult,
//											@Param("business_type") Integer businessType);
//
//
//	IPage<TeamRoomData> listTeamRoom(Page page, ListQuery listQuery);
//
//	IPage<RbRoomData> listRbRoom(Page page, ListQuery listQuery);

}
