package com.cyberpeace.iov.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.iov.dao.entity.VehicleTraffic;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficListBo;

@Mapper
@Repository
public interface VehicleTrafficMapper extends BaseMapper<VehicleTraffic> {

	IPage<VehicleTrafficListBo> page(@Param("page") Page page, @Param("pattern") String pattern);
}
