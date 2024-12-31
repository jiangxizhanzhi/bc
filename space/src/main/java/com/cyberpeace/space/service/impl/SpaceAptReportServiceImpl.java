package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceAptReport;
import com.cyberpeace.space.dao.mapper.SpaceAptReportMapper;
import com.cyberpeace.space.service.SpaceAptReportService;
import org.springframework.stereotype.Service;

@Service
public class SpaceAptReportServiceImpl extends ServiceImpl<SpaceAptReportMapper, SpaceAptReport> implements SpaceAptReportService {}
