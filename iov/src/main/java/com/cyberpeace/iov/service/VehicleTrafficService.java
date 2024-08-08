package com.cyberpeace.iov.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.iov.dao.entity.VehicleTraffic;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficAddReq;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficDetailBo;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficListBo;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficModifyReq;

public interface VehicleTrafficService extends IService<VehicleTraffic> {
    ListRsp<VehicleTrafficListBo> pageTraffic(PageReqBase req);

    void create(VehicleTrafficAddReq req);

    void modify(VehicleTrafficModifyReq req);

    void deleteByIds(List<String> ids);

    VehicleTrafficDetailBo getDetail(String resourceId);
}
