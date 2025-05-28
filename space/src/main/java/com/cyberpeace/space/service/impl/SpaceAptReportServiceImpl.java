/**
 * 该代码是一个 `Spring` 服务类 `SpaceAptReportServiceImpl` 的实现，用于处理与空间易用性报告相关的业务逻辑。以下是代码的主要功能和结构分析： 
 * ### 主要功能： 
 * 1. **导出报告**： 
 * - 方法 `dump(List<String> ids)` 接收一组报告的 ID 列表。 
 * - 如果 ID 列表为空，则抛出异常。 
 * - 根据 ID 查询对应的报告数据，并将其转换为 `SpaceAptReportBo` 对象。 
 * - 将报告中的附件复制到指定的目标路径。 
 * - 将所有报告数据以 JSON 格式写入文件。 
 * - 使用 `ZipUtil` 将生成的文件夹压缩成 ZIP 文件，并返回压缩后的路径。 
 * 2. **加载报告**： 
 * - 方法 `loadReport(String realPath)` 接收一个压缩文件的真实路径。 
 * - 解压文件到临时目录。 
 * - 从解压后的目录中读取 JSON 数据文件并解析为 `SpaceAptReportBo` 对象列表。 
 * - 将 `SpaceAptReportBo` 对象转换为实体对象并保存到数据库。 
 * - 复制附件文件到实际存储位置。 
 * 3. **辅助方法**： 
 * - `copyFile(String storeDbPath, String destFilePath, String resourceId)`：将数据库中存储的文件复制到目标路径。 
 * ### 关键点： 
 * - 使用了 `MyBatis-Plus` 的 `ServiceImpl` 作为基础类，继承自 `SpaceAptReportMapper` 和 `SpaceAptReport`。 
 * - 依赖了多个工具类（如 `FileUtil`, `IDGeneratorUtil`, `JsonUtil`, `ZipUtil`）来处理文件操作、JSON 序列化等。 
 * - 数据库操作通过 `baseMapper` 实现。 
 * - 异常处理使用了自定义的 `BusinessException`。 
 * ### 总结： 
 * 此代码实现了报告的导出和加载功能，涉及文件的读写、压缩解压以及数据库交互，具有良好的模块化设计和清晰的功能划分。 
**/
package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ZipUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.space.dao.entity.SpaceAptReport;
import com.cyberpeace.space.dao.mapper.SpaceAptReportMapper;
import com.cyberpeace.space.service.SpaceAptReportService;
import com.cyberpeace.space.service.context.rule.SpaceAptReportBo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * 空间容易报告Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceAptReportService
 * @since 1.0.0
 */
@Service
public class SpaceAptReportServiceImpl extends ServiceImpl<SpaceAptReportMapper, SpaceAptReport> implements SpaceAptReportService {

	@Autowired
	private UtilService utilService;

	/**
	 * 导出
	 *
	 * @param ids id列表
	 * @return 字符串
	 * @throws
	 * @see List<String>
	 * @see String
	 */
	@Override
	public String dump(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new BusinessException(CrBusinessErrorCode.EMPTY_RESOURCE_ID_LIST);
		}
		// 创建文件夹
		String destPath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID();
		String destFilePath = destPath + File.separatorChar
				+ ResourceImportConstant.DUMP_LOAD_FILE_STORE_PATH;
		List<SpaceAptReport> reportList = baseMapper.selectBatchIds(ids);
		List<SpaceAptReportBo> reportData = Lists.newArrayList();
		for (SpaceAptReport report : reportList) {
			SpaceAptReportBo bo = report.entity2Bo(null);
			// 课时附件
			if (StringUtils.isNotBlank(bo.getReportPath())) {
				this.copyFile(bo.getReportPath(), destFilePath, bo.getResourceId());
			}
			reportData.add(bo);
		}
		// 数据写入
		String dataPath = destPath + File.separatorChar + ResourceImportConstant.DATA_JSON;
		FileUtil.writeToDestFile(JsonUtil.arrayToString(reportData), dataPath);

		// 文件压缩
		String zipPath = ZipUtil.toZipDest(destPath, AttachmentPath.OTHER.getDesc());
		FileUtil.deleteDir(destPath, true);
		return zipPath;

	}

	/**
	 * 加载报告
	 *
	 * @param realPath 真实路径
	 * @return 布尔
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean loadReport(String realPath) {
		// 文件解压
		String destFilePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID();
		ZipUtil.unzipToDest(realPath, destFilePath);
		String filePath = destFilePath + File.separatorChar
				+ ResourceImportConstant.DUMP_LOAD_FILE_STORE_PATH;

		// 文件数据读取
		String dataPath = destFilePath + File.separatorChar + ResourceImportConstant.DATA_JSON;
		String content = FileUtil.getContent(new File(dataPath));
		List<SpaceAptReportBo> reportBoList = JsonUtil.toJsonObjectArray(content,
				SpaceAptReportBo.class);
		List<SpaceAptReport> reportList = Lists.newArrayList();

		for (SpaceAptReportBo reportBo : reportBoList) {
			SpaceAptReport report = reportBo.bo2Entity();
			if (StringUtils.isNotBlank(report.getReportPath())) {
				String fileSuffix = FileUtil.getFileSuffix(report.getReportPath());
				String path = filePath + File.separatorChar + report.getResourceId()
						+ CommonConstant.PERIOD + fileSuffix;
				FileUtil.copyFile(path, utilService.getRealPath(report.getReportPath()), false);
			}
            report.setUpdateTime(null);
            report.setCreateTime(null);
			reportList.add(report);
		}
		this.saveBatch(reportList);
		return true;
	}

	/**
	 * @param storeDbPath  存储数据库路径
	 * @param destFilePath 目标文件路径
	 * @param resourceId   资源id
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	private void copyFile(String storeDbPath, String destFilePath, String resourceId) {
		String realPath = utilService.getRealPath(storeDbPath);
		String fileSuffix = FileUtil.getFileSuffix(realPath);
		String fileName = resourceId + CommonConstant.PERIOD + fileSuffix;
		String destPath = destFilePath + File.separatorChar + fileName;

		FileUtil.copyFile(realPath, destPath, false);
	}
}
