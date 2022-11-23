package com.cyberpeace.bs.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.bs.dao.entity.UserSimulator;

@Mapper
@Repository
public interface UserSimulatorMapper extends BaseMapper<UserSimulator> {

	List<UserSimulator> selectAll();

	Integer countAll();

	Integer countByStatus(@Param("statusCode") Integer statusCode);
}
