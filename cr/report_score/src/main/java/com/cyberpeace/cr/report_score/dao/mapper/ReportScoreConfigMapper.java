package com.cyberpeace.cr.report_score.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.report_score.dao.entity.ReportScoreConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReportScoreConfigMapper extends BaseMapper<ReportScoreConfig> {

	List<ReportScoreConfig> getTypeConfigs(@Param("type") String type);
}
