package com.cyberpeace.iov.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.iov.dao.entity.VehicleType;
import com.cyberpeace.iov.service.bo.frame.VehicleTypeBo;

import java.util.List;

public interface VehicleTypeService extends IService<VehicleType> {
    ListRsp<VehicleTypeBo> pageType(PageReqBase req);

    void create(VehicleTypeBo req);

    void modify(VehicleTypeBo req);

    void deleteByIds(List<Integer> ids);

    VehicleTypeBo getDetail(Integer resourceId);

    List<VehicleTypeBo> listBos();

}
