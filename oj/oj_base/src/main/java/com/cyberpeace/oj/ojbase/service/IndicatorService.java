package com.cyberpeace.oj.ojbase.service;

import com.cyberpeace.oj.ojbase.dao.entity.Indicator;
import com.cyberpeace.oj.ojbase.service.bo.IndicatorBo;

import java.util.List;

public interface IndicatorService {
    List<IndicatorBo> getIndicators(Integer type);

    List<Indicator> selectIndicators(String attackIdJson);
}
