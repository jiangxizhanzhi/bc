package com.cyberpeace.cr.report_score.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.report_score.dao.entity.ReportScore;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReportScoreMapper extends BaseMapper<ReportScore> {
    IPage<ReportScore> list(Page page);
}
