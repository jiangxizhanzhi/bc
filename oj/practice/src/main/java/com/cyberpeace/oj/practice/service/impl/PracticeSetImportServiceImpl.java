package com.cyberpeace.oj.practice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.cs.service.context.scene.PluginElement;
import com.cyberpeace.cs.service.context.scene.TopoData;
import com.cyberpeace.cs.service.context.scene.TopoElement;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationTarget;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.service.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 实践设置导入Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ServiceImpl
 * @see PracticeSetImportService
 * @since 1.0.0
 */
@Slf4j(topic = "resourceImport")
@Service
public class PracticeSetImportServiceImpl extends ServiceImpl<PracticeSetMapper, PracticeSet> implements PracticeSetImportService {

	/**
	 * 事务模板
	 *
	 * @see TransactionTemplate
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * utilService
	 *
	 * @see UtilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 实践设置Mapper
	 *
	 * @see PracticeSetMapper
	 */
	@Autowired
	private PracticeSetMapper practiceSetMapper;

	/**
	 * 实践实操Service
	 *
	 * @see PracticeOperationService
	 */
	@Autowired
	private PracticeOperationService practiceOperationService;

	/**
	 * 实践实操导入Service
	 *
	 * @see PracticeOperationImportService
	 */
	@Autowired
	private PracticeOperationImportService practiceOperationImportService;

	/**
	 * 实践理论Service
	 *
	 * @see PracticeTheoryService
	 */
	@Autowired
	private PracticeTheoryService practiceTheoryService;

	/**
	 * 实践理论导入Service
	 *
	 * @see PracticeTheoryImportService
	 */
	@Autowired
	private PracticeTheoryImportService practiceTheoryImportService;


	@Autowired
	private RemoteTopoService remoteTopoService;
	
	@Autowired
	private PracticeOperationTargetService practiceOperationTargetService;
	
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 导入资管CTF设置
	 *
	 * @param path    路径
	 * @param envReqs env reqs
	 * @param scriptFileMap
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see Map<String, String>
	 * @see List<String>
	 */
	@Override
	public List<String> importRmCtfSet(String path, Map<String, String> envReqs, Map<String, String> scriptFileMap) {
		// 习题集数据处理
		PracticeSet practiceSet = handlerPracticeSet(path);

		// 遍历习题数据处理
		List<String> ctfPracticeDirs = Lists.newArrayList();
		for (String name : envReqs.keySet()) {
			File tmpFile = new File(path, name);
			if (tmpFile.exists()) {
				ctfPracticeDirs.add(tmpFile.getAbsolutePath());
			}
		}
		if (ctfPracticeDirs.size() == 0) {
			return Lists.newArrayList();
		}

		// 构建数据存储
		List<String> existOperations = Lists.newArrayList();
		List<PracticeOperation> operationList = Lists.newArrayList();
		List<PracticeOperationTarget> targetList = Lists.newArrayList();

		// 执行数据导入
		for (String ctfPracticeDir : ctfPracticeDirs) {
			try {
				PracticeOperation operation = practiceOperationImportService
						.parseRmCtf(ctfPracticeDir, envReqs, practiceSet);
				operationList.add(operation);
				String topoId = operation.getTopoId();
				if (StringUtils.isNotBlank(topoId)) {
					String envKey = new File(path).getName();
					String scriptFilePath = scriptFileMap.get(envKey);
					PracticeOperationTarget target = this.createTarget(topoId, operation, scriptFilePath);
					if (target != null) {
						targetList.add(target);
					}
				}
			} catch (BusinessException e) {

				if (OjBusinessErrorCode.NAME_IS_EXIST.equals(e.getMessage())) {
					log.error("Parse ctf practice exist! ctf_practice_path=[{}], catch error:",
							ctfPracticeDir, e);
					existOperations.add(ctfPracticeDir);
					continue;
				} else {
					log.error("Parse ctf practice fail! ctf_practice_path=[{}], catch error:",
							ctfPracticeDir, e);
					throw e;
				}
			}
		}

		// 数据去重
		List<PracticeOperation> operations = StreamApiUtils.distinctByColumn(operationList,
				PracticeOperation::getTitle);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				saveOrUpdate(practiceSet);
				if (CollectionUtils.isNotEmpty(operations)) {
					practiceOperationService.saveBatch(operationList);
				}
				if (!targetList.isEmpty()) {
					practiceOperationTargetService.saveBatch(targetList);
				}
			}
		});

		return existOperations;
	}

	private PracticeOperationTarget createTarget(String topoId, PracticeOperation operation,
			String scriptFilePath) {
		JSONObject rsp = remoteTopoService.getTopoById(topoId);
		TopoData topoData = rsp.getJSONObject("scene_config").toJavaObject(TopoData.class);
		TopoElement element = null;
		for (TopoElement e : topoData.getTopology().getElements()) {
			if (e.getType() != 1) {
				element = e;
				break;
			}
		}
		if (element == null) {
			return null;
		}
		PracticeOperationTarget target = new PracticeOperationTarget();
		target.setOperationId(operation.getResourceId());
		target.setNodeCommand(element.getNodeCommand());
		target.setResourceId(IDGeneratorUtil.getID());
		String id = element.getId();
		PluginElement pluginElement = topoData.getPlugins().getCpcloud().getElements().get(id);
		if (pluginElement != null) {
			target.setNodeName(pluginElement.getImage());
			target.setNodeId(pluginElement.getImageUuid());
		}
		// script file zip
		AttachmentBo attachmentBo = attachmentService.copyFile(scriptFilePath,
				AttachmentPath.PRACTICE_ATTACHMENT.getCode(), true, true);
		target.setAttachmentId(attachmentBo.getResourceId());
		return target;
	}

	/**
	 * 导入资管理论设置
	 *
	 * @param path 路径
	 * @return boolean
	 * @throws
	 * @see String
	 * @see boolean
	 */
	@Override
	public boolean importRmTheorySet(String path) {

		// 习题集数据插入处理
		PracticeSet practiceSet = handlerPracticeSet(path);

		// 习题数据导入
		List<PracticeTheory> theoryList = practiceTheoryImportService.parseRmTheory(path,
				practiceSet);

		// 支持重复导入，theoryList 里面的数据已被导入，theoryList为空
		if (theoryList.isEmpty()) {
			log.info("practice theory add 0, this path is[{}]", path);
			return true;
		}
		log.info("practice theory add new [{}], this path is[{}]", theoryList.size(), path);


		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				saveOrUpdate(practiceSet);
				return practiceTheoryService.saveBatch(theoryList);
			}
		});
	}


	/**
	 * 习题集数据读取
	 *
	 * @param path 习题集路径
	 * @return
	 * @throws
	 * @see String
	 * @see PracticeSet
	 */
	private PracticeSet handlerPracticeSet(String path) {

		// 读取习题集数据信息
		File setMarkdownFile = new File(path, ResourceImportConstant.README_NAME);
		if (!setMarkdownFile.isFile()) {
			log.error("practiceSet readme.md file not found, file is[{}]", setMarkdownFile.getAbsolutePath());
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, setMarkdownFile.getAbsolutePath(), "文件没有找到");
		}

		JSONObject setData = MdUtil.parseJsonMarkdown(setMarkdownFile);
		JSONObject setJson = JsonUtil.filedConvert(setData,
				ResourceImportConstant.PRACTICE_SET_DATA_MAPPING);

		// 格式化习题习题集数据 并存储数据库
		PracticeSet practiceSet = jsonParsePracticeSet(setJson, setMarkdownFile.getAbsolutePath());

		// logo文件地址转换
		File file = new File(path, ResourceImportConstant.PNG_LOGO);
		File destFile = new File(AttachmentPath.LOGO.getDesc(), ResourceImportConstant.PNG_LOGO);
		if (!file.exists()) {
			file = new File(path, ResourceImportConstant.JPG_LOGO);
			destFile = new File(AttachmentPath.LOGO.getDesc(), ResourceImportConstant.JPG_LOGO);
		}
		if (!file.exists()) {
			log.error("practiceSet logo file not found file is[{}]", file.getAbsolutePath());
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, file.getAbsolutePath(), "文件没有找到");
		}

		String destLogoPath = FileUtil.copyFile(file.getAbsolutePath(), destFile.getAbsolutePath(),
				true);
		practiceSet.setLogo(utilService.replaceFileUrl(destLogoPath));

		// 名称数据校验
		String existSetId = practiceSetMapper.selectByName(practiceSet.getName());

		practiceSet.setBuiltin(CommonConstant.TRUE);
		practiceSet.setCreateUserId(ResourceImportConstant.SUPER_ADMIN_HASH);
		practiceSet.setIsPublic(CommonConstant.FALSE);
		practiceSet.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		practiceSet.setOperationAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		practiceSet.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		practiceSet.setReadAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		practiceSet.setPracticeCount(CommonConstant.ZERO);

		if (StringUtils.isBlank(existSetId)) {
			practiceSet.setResourceId(IDGeneratorUtil.getID());
		} else {
			practiceSet.setResourceId(existSetId);
		}

		return practiceSet;
	}

	/**
	 * 格式化习题集数据
	 *
	 * @param setJson         Json化的习题集数据
	 * @param practiceSetPath 实践设置路径
	 * @return
	 * @throws
	 * @see JSONObject
	 * @see String
	 * @see PracticeSet
	 */
	private PracticeSet jsonParsePracticeSet(JSONObject setJson, String practiceSetPath) {
		// 习题集类型处理
		String typeName = (String) setJson.remove("practice_type");
		Integer practiceType = EnumUtils.getEnumCodeByDesc(typeName,
				PracticeSet.PracticeType.class);

		if (practiceType == null) {
			log.error("Invalid practiceType, practiceType is null");
			final List<String> listDesc = EnumUtils.getListDesc(PracticeSet.PracticeType.class);
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, practiceSetPath, "习题集类型不在范围内" + listDesc.toString());
		}

		PracticeSet practiceSet = setJson.toJavaObject(PracticeSet.class);
		practiceSet.setPracticeType(practiceType);

		return practiceSet;

	}

}
