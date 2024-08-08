package com.cyberpeace.iov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.iov.constant.DutStatus;
import com.cyberpeace.iov.dao.entity.Dut;
import com.cyberpeace.iov.dao.mapper.DutMapper;
import com.cyberpeace.iov.service.DutService;
import com.cyberpeace.iov.service.bo.dut.DutAddReq;
import com.cyberpeace.iov.service.bo.dut.DutListBo;
import com.cyberpeace.iov.service.bo.dut.DutModifyReq;
import com.cyberpeace.iov.service.bo.dut.EnableReq;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DutServiceImpl extends ServiceImpl<DutMapper, Dut> implements DutService {

	@Autowired
	private DutMapper dutMapper;

	@Override
	public ListRsp<DutListBo> pageDuts(PageReqBase req, Integer model, Integer area,
			Integer status) {
		Page page = req.toPageParam();
		IPage<DutListBo> iPage = dutMapper.page(page, model, area, status);
		return PageUtil.getListResult(iPage);
	}

	@Override
	public void create(DutAddReq req) {
		Dut dut = this.req2Entity(req);
		this.save(dut);
	}

	@Override
	public void deleteByIds(List<String> ids) {
		dutMapper.deleteBatchIds(ids);
	}

	@Override
	public List<DutListBo> listDuts(Integer model, Integer area) {
		List<DutListBo> res = this.getBaseMapper().listByModelAndArea(model, area);
		return res;
	}

	@Override
	public void enable(EnableReq req) {
		dutMapper.enable(req.getResourceId(), req.getEnable());
	}

	@Override
	public void modify(DutModifyReq req) {
		Dut dut = req2Entity(req);
		dut.setResourceId(req.getResourceId());
		this.updateById(dut);
	}

	@Override
	public DutListBo getDetail(String resourceId) {
		Dut dut = this.getOrException(resourceId);
		DutListBo bo = BeanUtils.copy(dut, DutListBo.class);
		return bo;
	}

	private Dut req2Entity(DutAddReq req) {
		Dut dut = BeanUtils.copy(req, Dut.class);
		dut.setStatus(DutStatus.ONLINE);
		dut.setEnabled(true);
		return dut;
	}

	private Dut getOrException(String resourceId) {
		Dut dut = dutMapper.selectById(resourceId);
		if (null == dut) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return dut;
	}
}
