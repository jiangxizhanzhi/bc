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

	/**
	 * 导出
	 *
	 * @param ids id列表
	 * @return 字符串
	 * @throws
	 * @see List<String>
	 * @see String
	 */
	String dump(List<String> ids);

	/**
	 * 加载报告
	 *
	 * @param realPath 真实路径
	 * @return 布尔
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean loadReport(String realPath);
}
