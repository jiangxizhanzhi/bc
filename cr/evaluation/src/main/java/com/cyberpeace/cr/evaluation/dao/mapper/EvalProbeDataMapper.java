package com.cyberpeace.cr.evaluation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalProbeData;

/**
 * 测评探针数据Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface EvalProbeDataMapper extends BaseMapper<EvalProbeData> {
}
