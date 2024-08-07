package com.cyberpeace.iov.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.iov.dao.entity.Dut;
import com.cyberpeace.iov.service.bo.dut.DutListBo;

@Mapper
@Repository
public interface DutMapper extends BaseMapper<Dut> {

	List<DutListBo> listByModelAndArea(@Param("model") Integer model, @Param("area") Integer area);

	IPage<DutListBo> page(@Param("page") Page page, @Param("model") Integer model,
			@Param("area") Integer area, @Param("status") Integer status);

	void enable(@Param("resourceId") String resourceId, @Param("enable") Boolean enable);
}
