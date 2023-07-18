package com.cyberpeace.cs.service.impl;

import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import com.cyberpeace.cs.service.SystemPropertyDealService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("systemPropertyDealServiceImpl")
public class SystemPropertyDealServiceImpl implements SystemPropertyDealService {

    public static Map<Integer,SystemPropertyDealService> systemPropertyDealServiceMap =new HashMap<>();

    @PostConstruct
    public void init() {
        systemPropertyDealServiceMap.put(1, SpringContextUtil.getBean("systemPropertyUrlDealServiceImpl"));
    }


    @Override
    public void dealSystemPropertyListByType(List<SystemProperty> systemPropertyList, Integer type) {
        SystemPropertyDealService dealService = systemPropertyDealServiceMap.get(type);
        if (dealService!=null){
            dealService.dealSystemPropertyListByType(systemPropertyList,type);
        }
    }
}
