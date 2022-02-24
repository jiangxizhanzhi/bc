package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.integratation.model.AttackLogPageContext;

/**
 * 获取场景态势数据
 */
public interface RemoteSituationService {

    /**
     * 获取场景攻击流量日志
     * @param sceneIds
     * @return
     */
    JSONArray getAttackLog(AttackLogPageContext attackLogPageContext);
}
