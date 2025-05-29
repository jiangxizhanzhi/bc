package com.cyberpeace.cr.tool.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.dao.mapper.ToolFileMapper;
import com.cyberpeace.cr.tool.service.ToolFileService;
import com.google.common.io.Files;
import org.springframework.web.multipart.MultipartFile;

/**
 * 工具文件Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码是一个 `ToolFileServiceImpl` 类的实现，它实现了 `ToolFileService` 接口。该类的主要功能是处理工具文件的上传、保存和管理。以下是对代码的简要分析： 
 * ### 主要组件 
 * 1. **日志记录**： 
 * - 使用 `org.slf4j.Logger` 进行日志记录，记录器名为 `"resourceImport"`。 
 * 2. **属性定义**： 
 * - `savePath`：保存文件的基础路径。 
 * - `uploadPath`：实际上传文件的路径，通过 `init()` 方法初始化。 
 * 3. **初始化方法**： 
 * - `@PostConstruct` 注解的 `init()` 方法，在 Bean 初始化时调用，设置 `uploadPath` 并确保目录存在。 
 * 4. **文件保存方法**： 
 * - `saveFile(String filePath, boolean transferName)`：根据文件路径保存文件，并返回 `ToolFile` 对象。 
 * - `newToolFile(String OriginalFileName, String FileName)`：创建并保存 `ToolFile` 对象。 
 * - `saveFile(MultipartFile multipartFile)`：从 `MultipartFile` 保存文件，并返回 `ToolFile` 对象。 
 * 5. **辅助方法**： 
 * - `ensureDir(String uploadPath)`：确保指定路径存在，如果不存在则创建。 
 * ### 关键功能 
 * - **文件上传与保存**：支持两种方式上传文件，一种是通过文件路径，另一种是通过 `MultipartFile` 对象。 
 * - **文件重命名**：可以使用 UUID 生成新的文件名，或者保留原始文件名。 
 * - **错误处理**：在文件操作过程中出现异常时，抛出 `BusinessException` 异常，并记录错误日志。 
 * ### 总结 
 * - **技术栈**：使用了 MyBatis Plus 进行数据访问，Guava 和 Apache Commons Lang 等常用工具库。 
 * - **设计模式**：符合单一职责原则，每个方法专注于特定的任务。 
 * - **安全性**：文件操作中考虑了异常处理，确保系统稳定性。 
 * 这段代码展示了如何处理文件上传和保存的基本流程，同时保证了良好的错误处理机制和日志记录。 
**/
@Service
public class ToolFileServiceImpl extends ServiceImpl<ToolFileMapper, ToolFile> implements ToolFileService {

	/**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	/**
	 * 保存路径
	 */
	private final String savePath = "tool"+ File.separator + "tool" + File.separator;

	/**
	 * 上传路径
	 */
	private String uploadPath;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		uploadPath = ConfigUtil.getContextProperty("cr.local.upload.path") + File.separator
				+ savePath;
		ensureDir(uploadPath);
	}

	/**
	 * 保存文件
	 *
	 * @param filePath     文件路径
	 * @param transferName 转移名称
	 * @return {@code ToolFile }
	 */
	@Override
	public ToolFile saveFile(String filePath, boolean transferName) {
		String fileSuffix = filePath.substring(filePath.lastIndexOf("."));
		String fileName = UUID.randomUUID() + fileSuffix;
		// 工具库的附件，使用原始的附件名
		if (!transferName) {
			String[] ss = StringUtils.split(filePath, File.separator);
			if (null != ss || ss.length > 0) {
				fileName = ss[ss.length - 1];
			}
		}
		String fullPath = uploadPath + fileName;

		File file = new File(filePath);
		File newFile = new File(fullPath);
		try {
			Files.copy(file, newFile);
		} catch (IOException e) {
			log.error("copy File {} to {}", filePath, fullPath);
			throw new BusinessException(ErrorCode.FAILED_TO_COPY_FILE);
			//("复制文件失败: " + filePath + " ,to:" + fullPath);
		}

		return newToolFile(fileName, fileName);
	}

	/**
	 * 新建工具文件
	 *
	 * @param OriginalFileName 源语言文件名称
	 * @param FileName         文件名称
	 * @return {@code ToolFile }
	 */
	private ToolFile newToolFile(String OriginalFileName, String FileName) {
		ToolFile toolFile = new ToolFile();
		toolFile.setFileName(OriginalFileName);
		toolFile.setSavePath(savePath + FileName);
		save(toolFile);
		return toolFile;
	}

	/**
	 * 保存文件
	 *
	 * @param multipartFile Multipart 文件
	 * @return {@code ToolFile }
	 */
	@Override
	public ToolFile saveFile(MultipartFile multipartFile) {
		File newFile;
		try {
			newFile = FileUtil.saveFile(multipartFile, uploadPath);
		} catch (IOException e) {
			// 保存文件
			log.error("[TOOL]save file{} to {} path error: {}", multipartFile.getOriginalFilename(), uploadPath,
					e.getMessage());
			throw new BusinessException(ErrorCode.FAILED_TO_SAVE_FILE);
		}

		return newToolFile(multipartFile.getOriginalFilename(), newFile.getName());
	}

	/**
	 * 确保 dir
	 *
	 * @param uploadPath 上传路径
	 */
	private void ensureDir(String uploadPath) {
		File f = new File(uploadPath);

		if (!f.exists() || !f.isDirectory()) {
			f.mkdirs();
		}
	}
}

