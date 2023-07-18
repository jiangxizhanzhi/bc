package com.cyberpeace.cs.service;

import com.cyberpeace.cs.dao.entity.SystemProperty;

import java.util.List;

public interface SystemPropertyDealService {


    void dealSystemPropertyListByType(List<SystemProperty> systemPropertyList,Integer type);
}
