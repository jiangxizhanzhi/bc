package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrUserReport;

@Mapper
@Repository
public interface CrUserReportMapper extends BaseMapper<CrUserReport> {

	List<CrUserReport> selectUserReportByRoomId(String roomId);

	Boolean batchInsert(@Param("crUserReportList") List<CrUserReport> crUserReportList);
}
