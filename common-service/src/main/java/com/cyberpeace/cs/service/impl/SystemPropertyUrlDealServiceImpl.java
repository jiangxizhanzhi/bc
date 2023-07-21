package com.cyberpeace.cs.service.impl;

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import com.cyberpeace.cs.service.SystemPropertyDealService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("systemPropertyUrlDealServiceImpl")
public class SystemPropertyUrlDealServiceImpl implements SystemPropertyDealService {
    @Override
    public void dealSystemPropertyListByType(List<SystemProperty> systemPropertyList, Integer type) {
        for (SystemProperty systemProperty : systemPropertyList) {
            if (StringUtils.isNotBlank(systemProperty.getContent())) {
                systemProperty.setContent(systemProperty.getContent()+"?token="+ CommonContext.getAuthorization());
            }
        }
    }
}
