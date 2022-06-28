package com.cyberpeace.cs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.SystemUseStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemUseStatusMapper extends BaseMapper<SystemUseStatus> {

	List<SystemUseStatus> selectList();

    void deleteOverdueData();
}
