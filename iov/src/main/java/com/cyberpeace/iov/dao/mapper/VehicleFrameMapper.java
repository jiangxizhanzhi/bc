package com.cyberpeace.iov.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.iov.dao.entity.VehicleFrame;
import com.cyberpeace.iov.service.bo.frame.VehicleFrameListBo;

@Mapper
@Repository
public interface VehicleFrameMapper extends BaseMapper<VehicleFrame> {

	IPage<VehicleFrameListBo> page(@Param("page") Page page, @Param("pattern") String pattern);
}
