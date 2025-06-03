package com.cyberpeace.iov.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.iov.dao.entity.VehicleTraffic;
import com.cyberpeace.iov.dao.entity.VehicleType;
import com.cyberpeace.iov.dao.mapper.VehicleTrafficMapper;
import com.cyberpeace.iov.service.VehicleTrafficService;
import com.cyberpeace.iov.service.VehicleTypeService;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficAddReq;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficDetailBo;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficListBo;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficModifyReq;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleTrafficServiceImpl extends ServiceImpl<VehicleTrafficMapper, VehicleTraffic> implements VehicleTrafficService {

	@Autowired
	private VehicleTrafficMapper vehicleTrafficMapper;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private VehicleTypeService vehicleTypeService;

	@Override
	public ListRsp<VehicleTrafficListBo> pageTraffic(PageReqBase req) {
		Page page = req.toPageParam();
		IPage<VehicleTrafficListBo> iPage = vehicleTrafficMapper.page(page, req.getPattern());
		return PageUtil.getListResult(iPage);
	}

	@Override
	public void create(VehicleTrafficAddReq req) {
		VehicleTraffic traffic = req2Entity(req);
		this.save(traffic);
	}

	@Override
	public void modify(VehicleTrafficModifyReq req) {
		VehicleTraffic traffic = req2Entity(req);
		traffic.setResourceId(req.getResourceId());
		this.updateById(traffic);
	}

	@Override
	public void deleteByIds(List<String> ids) {
		vehicleTrafficMapper.deleteBatchIds(ids);
	}

	@Override
	public VehicleTrafficDetailBo getDetail(String resourceId) {
		VehicleTraffic frame = this.getById(resourceId);
		VehicleTrafficDetailBo bo = BeanUtils.copy(frame, VehicleTrafficDetailBo.class);
		VehicleType type = vehicleTypeService.getById(frame.getModel());
		bo.setModelName(type == null ? "" : type.getName());
		AttachmentBo attachment = attachmentService.getOneById(bo.getBlfFile());
		bo.setBlfFileName(attachment.getFileName());
		bo.setBlfFilePath(attachment.getFilePath());
		return bo;
	}

	@Override
	public List<VehicleTrafficDetailBo> listTraffic() {
		List<VehicleTraffic> trafficList = this.list();
		List<VehicleTrafficDetailBo> res = BeanUtils.deepCopyList(trafficList,
				VehicleTrafficDetailBo.class);

		List<Integer> modelIds = StreamApiUtils.map(trafficList, VehicleTraffic::getModel);
		List<VehicleType> modelList = modelIds.isEmpty() ? Lists.newArrayList()
				: vehicleTypeService.listByIds(modelIds);
		Map<Integer, String> modelNameMap = StreamApiUtils.toMap(modelList,
				VehicleType::getId, VehicleType::getName);

		List<String> attachmentIds = trafficList.stream().map(VehicleTraffic::getBlfFile)
				.filter(Objects::nonNull).collect(Collectors.toList());
		List<Attachment> attachmentList = attachmentService.batchGetByIds(attachmentIds);
		Map<String, Attachment> attachmentMap = StreamApiUtils.toMap(attachmentList,
				Attachment::getResourceId, a -> a);
		for (VehicleTrafficDetailBo bo : res) {
			Attachment attachment = attachmentMap.get(bo.getBlfFile());
			if (null != attachment) {
				bo.setBlfFileName(attachment.getFileName());
				bo.setBlfFilePath(utilService.handlerFileUrl(attachment.getFilePath()));
			}
			bo.setModelName(modelNameMap.get(bo.getModel()));
		}
		return res;
	}

	private VehicleTraffic req2Entity(VehicleTrafficAddReq req) {
		VehicleTraffic traffic = new VehicleTraffic();
		traffic.setName(req.getName());
		traffic.setDescription(req.getDescription());
		traffic.setModel(req.getModel());
		traffic.setBlfFile(req.getBlfFile());

		return traffic;
	}
}
