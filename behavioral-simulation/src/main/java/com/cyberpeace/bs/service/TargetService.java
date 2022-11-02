package com.cyberpeace.bs.service;

import com.cyberpeace.bs.service.bo.TargetBo;

import java.util.List;

public interface TargetService {
    /**
     * 获取行为设备列表
     * @param type 设备类型
     * @param search 名称搜索
     * @return 设备列表
     */
    List<TargetBo> getTargets(Integer type, String search);
}
