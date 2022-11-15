package com.cyberpeace.bs.service;

import java.util.List;

import com.cyberpeace.bs.service.bo.TargetBo;

public interface MeterService {
    /**
     * 获取行为设备列表
     * @param type 设备类型
     * @param search 名称搜索
     * @return 设备列表
     */
	List<TargetBo> getMeters(Integer type, String search);
}
