package com.cyberpeace.iov.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cyberpeace.iov.dao.entity.VehicleType;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.iov.dao.entity.VehicleFrame;
import com.cyberpeace.iov.dao.mapper.VehicleFrameMapper;
import com.cyberpeace.iov.service.VehicleFrameService;
import com.cyberpeace.iov.service.VehicleTypeService;
import com.cyberpeace.iov.service.bo.dut.AreaAndCount;
import com.cyberpeace.iov.service.bo.frame.*;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleFrameServiceImpl extends ServiceImpl<VehicleFrameMapper, VehicleFrame> implements VehicleFrameService {

	@Autowired
	private VehicleFrameMapper vehicleFrameMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private VehicleTypeService vehicleTypeService;

	@Override
	public ListRsp<VehicleFrameListBo> pageFrame(PageReqBase req) {
		Page page = req.toPageParam();
		IPage<VehicleFrameListBo> iPage = vehicleFrameMapper.page(page, req.getPattern());
		List<String> userIds = iPage.getRecords().stream().map(VehicleFrameListBo::getCreator)
				.collect(Collectors.toList());
		Map<String, String> userNameMap = remoteUserService.getIdNameMap(userIds);
		List<VehicleTypeBo> types = vehicleTypeService.listBos();
		Map<String, String> typeMap = StreamApiUtils.toMap(types, VehicleTypeBo::getResourceId,
				VehicleTypeBo::getName);
		iPage.getRecords()
				.forEach(r -> {
					r.setCreatorName(userNameMap.getOrDefault(r.getCreator(), ""));
					r.setModelName(typeMap.getOrDefault(r.getModel(), ""));
				});
		return PageUtil.getListResult(iPage);

	}

	@Override
	public void create(VehicleFrameAddReq req) {
		VehicleFrame frame = req2Entity(req);
		this.save(frame);
	}

	@Override
	public void modify(VehicleFrameModifyReq req) {
		VehicleFrame frame = req2Entity(req);
		frame.setResourceId(req.getResourceId());
		this.updateById(frame);
	}

	@Override
	public void deleteByIds(List<String> ids) {
		vehicleFrameMapper.deleteBatchIds(ids);
	}

	@Override
	public VehicleFrameDetailBo getDetail(String resourceId) {
		VehicleFrame frame = this.getById(resourceId);
		VehicleFrameDetailBo bo = BeanUtils.copy(frame, VehicleFrameDetailBo.class);
		bo.setCreatorName(remoteUserService.getById(bo.getCreator()).getName());
		VehicleType type = vehicleTypeService.getById(frame.getModel());
		bo.setModelName(type == null ? "" : type.getName());
		return bo;
	}

	@Override
	public List<FrameTopoItem> getTopoByFrameId(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			return Lists.newArrayList();
		}
		VehicleFrame frame = this.getById(resourceId);
		if (null == frame) {
			return Lists.newArrayList();
		}
		return JsonUtil.toJsonObjectArray(frame.getTopo(), FrameTopoItem.class);
	}

	@Override
	public Map<String, Integer> getNodesCountMap(Collection<String> vehicleFrameIds) {
		Map<String, Integer> res = Maps.newHashMap();
		if (CollectionUtils.isEmpty(vehicleFrameIds)) {
			return res;
		}
		List<VehicleFrame> frameList = vehicleFrameMapper.selectBatchIds(vehicleFrameIds);

		for (VehicleFrame frame : frameList) {
			res.put(frame.getResourceId(),
					JsonUtil.toJsonObjectArray(frame.getTopo(), FrameTopoItem.class).size());
		}
		return res;
	}

	@Override
	public List<AreaAndCount> countDutByArea(String vehicleFrameId) {
		List<FrameTopoItem> dutList = this.getTopoByFrameId(vehicleFrameId);
		List<AreaAndCount> res = this.initCountByArea();
		Map<Integer, List<FrameTopoItem>> mapByArea = StreamApiUtils.groupBy(dutList,
				FrameTopoItem::getArea);
		for (AreaAndCount item : res) {
			item.setCount(mapByArea.getOrDefault(item.getArea(), Lists.newArrayList()).size());
		}
		return res;
	}

	private VehicleFrame req2Entity(VehicleFrameAddReq req) {
		VehicleFrame frame = new VehicleFrame();
		frame.setCreator(UserInfoContext.getUserContext().getUserId());
		frame.setName(req.getName());
		frame.setDescription(req.getDescription());
		frame.setModel(req.getModel());
		frame.setScale(getScale(req.getTopo()));
		frame.setTopo(req.getTopo());

		return frame;
	}

	private int getScale(String topo) {
		try {
			JSONArray array = JsonUtil.stringToArray(topo);
			return array.size();
		} catch (Exception e) {
			log.error("invalid topo json string: {}", topo);
			return 0;
		}
	}

	private List<AreaAndCount> initCountByArea() {
		List<AreaAndCount> res = Lists.newArrayList();
		for (int i = 1; i <= 6; i++) {
			AreaAndCount item = new AreaAndCount();
			item.setArea(i);
			res.add(item);
		}
		return res;
	}

}
