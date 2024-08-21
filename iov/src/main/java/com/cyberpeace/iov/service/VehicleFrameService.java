package com.cyberpeace.iov.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.iov.dao.entity.VehicleFrame;
import com.cyberpeace.iov.service.bo.frame.*;

public interface VehicleFrameService extends IService<VehicleFrame> {

	ListRsp<VehicleFrameListBo> pageFrame(PageReqBase req);

	void create(VehicleFrameAddReq req);

	void modify(VehicleFrameModifyReq req);

	void deleteByIds(List<String> ids);

	VehicleFrameDetailBo getDetail(String resourceId);

	List<FrameTopoItem> getTopoByFrameId(String vehicleFrameId);
}
