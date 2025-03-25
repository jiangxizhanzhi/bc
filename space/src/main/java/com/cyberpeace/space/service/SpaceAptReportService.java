package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceAptReport;

import java.util.List;

public interface SpaceAptReportService extends IService<SpaceAptReport> {

	String dump(List<String> ids);

	Boolean loadReport(String realPath);
}
