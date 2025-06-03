package com.cyberpeace.iov.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.iov.dao.entity.VehicleType;
import com.cyberpeace.iov.dao.mapper.VehicleTypeMapper;
import com.cyberpeace.iov.service.VehicleTypeService;
import com.cyberpeace.iov.service.bo.frame.VehicleTypeBo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleServiceImpl extends ServiceImpl<VehicleTypeMapper, VehicleType> implements VehicleTypeService {

	@Override
	public ListRsp<VehicleTypeBo> pageType(PageReqBase req) {
		Page page = req.toPageParam();
		LambdaQueryWrapper<VehicleType> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(VehicleType::getUpdateTime);
		if (StringUtils.isNotBlank(req.getPattern())) {
			wrapper.like(VehicleType::getName, req.getPattern());
		}
		IPage<VehicleType> iPage = this.getBaseMapper().selectPage(page, wrapper);
		iPage.convert(item -> {
			VehicleTypeBo bo = new VehicleTypeBo();
			bo.setResourceId(item.getId());
			bo.setName(item.getName());
			return bo;
		});
		return PageUtil.getListResult(iPage);
	}

	@Override
	public void create(VehicleTypeBo req) {
		VehicleType e = new VehicleType();
		e.setName(req.getName());
		this.save(e);
	}

	@Override
	public void modify(VehicleTypeBo req) {
		VehicleType e = req.bo2Entity();
		e.setId(req.getResourceId());
		this.updateById(e);
	}

	@Override
	public void deleteByIds(List<Integer> ids) {
		this.removeByIds(ids);
	}

	@Override
	public VehicleTypeBo getDetail(Integer resourceId) {
		VehicleType e = this.getById(resourceId);
		if (e == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		VehicleTypeBo bo = BeanUtils.copy(e, VehicleTypeBo.class);
		bo.setResourceId(e.getId());
		return bo;
	}

	@Override
	public List<VehicleTypeBo> listBos() {
		LambdaQueryWrapper<VehicleType> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(VehicleType::getUpdateTime);
		List<VehicleType> list = this.list(wrapper);
		List<VehicleTypeBo> res = StreamApiUtils.map(list, e -> {
			VehicleTypeBo bo = BeanUtils.copy(e, VehicleTypeBo.class);
			bo.setResourceId(e.getId());
			return bo;
		});
		return res;
	}
}
