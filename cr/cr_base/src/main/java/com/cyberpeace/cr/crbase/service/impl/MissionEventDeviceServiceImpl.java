package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.crbase.dao.entity.MissionEventDevice;
import com.cyberpeace.cr.crbase.dao.mapper.MissionEventDeviceMapper;
import com.cyberpeace.cr.crbase.service.MissionEventDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MissionEventDeviceServiceImpl extends ServiceImpl<MissionEventDeviceMapper, MissionEventDevice> implements MissionEventDeviceService {
}
