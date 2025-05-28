package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceAptReport;

import java.util.List;

/**
 * 空间容易报告Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceAptReportService extends IService<SpaceAptReport> {

	String dump(List<String> ids);

	Boolean loadReport(String realPath);
}
