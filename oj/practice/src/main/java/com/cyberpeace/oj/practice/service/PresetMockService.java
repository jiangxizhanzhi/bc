package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PresetMock;
import com.cyberpeace.oj.practice.service.bo.mock.PresetMockBo;

import java.util.List;

public interface PresetMockService extends IService<PresetMock> {

    PresetMockBo getBoById(String presetId);

    List<PresetMock> listAllMock(boolean actual);
}
