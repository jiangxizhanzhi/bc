package com.cyberpeace.cr.redvsblue.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.context.RbSituation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RbRoomMapper extends BaseMapper<RbRoom> {

	List<RbRoom> selectAll();

	Integer countAll();

	RbRoomData getRbRoomDataByRoomId(String resourceId);

	IPage<RbRoomData> listRbRoom(Page page, ListQuery listQuery);

	TrainingCount getTrainingCount(@Param("roomIds") List<String> roomIds,
			@Param("completedStatus") Integer completedStatus);

	List<RbRoom> selectUnfinishedByIds(@Param("roomIds") List<String> roomIds,
			@Param("endStatus") Integer endStatus);

	Integer countByName(@Param("name") String name);

	List<RbSituation> selectAllRunning();
}
