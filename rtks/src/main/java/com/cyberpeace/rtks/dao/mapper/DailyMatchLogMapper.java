package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.DailyMatchLog;

@Mapper
@Repository
public interface DailyMatchLogMapper extends BaseMapper<DailyMatchLog> {

	List<DailyMatchLog> selectByUserId(@Param("userId") String userId);
}
