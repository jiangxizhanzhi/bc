package com.cyberpeace.oj.practice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.practice.entity.PracticeOperation;
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

@Slf4j(topic = "resourceImport")
@Service
public class PracticeSetImportServiceImpl extends ServiceImpl<PracticeSetMapper, PracticeSet> implements PracticeSetImportService {

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private UtilService utilService;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private PracticeOperationService practiceOperationService;

	@Autowired
	private PracticeOperationImportService practiceOperationImportService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;
	
	@Autowired
	private PracticeTheoryImportService practiceTheoryImportService;

	@Override
	public List<String> importRmCtfSet(String path, Map<String, String> envReqs) {
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

		// 执行数据导入
		for (String ctfPracticeDir : ctfPracticeDirs) {
			try {
				PracticeOperation operation = practiceOperationImportService
						.parseRmCtf(ctfPracticeDir, envReqs, practiceSet);
				operationList.add(operation);
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

		boolean isTrue = "是".equals(practiceSet.getIsTrue());
		operationList.forEach(a->a.setIsTrue(isTrue));
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				saveOrUpdate(practiceSet);
				if (CollectionUtils.isNotEmpty(operations)) {
					practiceOperationService.saveBatch(operationList);
				}
			}
		});

		return existOperations;
	}

	@Override
	public boolean importRmTheorySet(String path) {

		// 习题集数据插入处理
		PracticeSet practiceSet = handlerPracticeSet(path);

		// 习题数据导入
		List<PracticeTheory> theoryList = practiceTheoryImportService.parseRmTheory(path,
				practiceSet);
		boolean isTrue = "是".equals(practiceSet.getIsTrue());
		theoryList.forEach(a->a.setIsTrue(isTrue));

		return transactionTemplate.execute(status -> {
			saveOrUpdate(practiceSet);
			return practiceTheoryService.saveBatch(theoryList);
		});
	}


	/**
	 * 习题集数据读取
	 *
	 * @param path 习题集路径
	 * @return
	 */
	private PracticeSet handlerPracticeSet(String path) {

		// 读取习题集数据信息
		File setMarkdownFile = new File(path, ResourceImportConstant.README_NAME);
		JSONObject setData = MdUtil.parseJsonMarkdown(setMarkdownFile);
		JSONObject setJson = JsonUtil.filedConvert(setData,
				ResourceImportConstant.PRACTICE_SET_DATA_MAPPING);

		// 格式化习题习题集数据 并存储数据库
		PracticeSet practiceSet = jsonParsePracticeSet(setJson);

		// logo文件地址转换
		File file = new File(path, ResourceImportConstant.PNG_LOGO);
		File destFile = new File(AttachmentPath.LOGO.getDesc(), ResourceImportConstant.PNG_LOGO);
		if (!file.exists()) {
			file = new File(path, ResourceImportConstant.JPG_LOGO);
			destFile = new File(AttachmentPath.LOGO.getDesc(), ResourceImportConstant.JPG_LOGO);
		}
		String destLogoPath = FileUtil.copyFile(file.getAbsolutePath(), destFile.getAbsolutePath(),
				true);
		practiceSet.setLogo(utilService.replaceFileUrl(destLogoPath));

		// 名称数据校验
		String existSetId = practiceSetMapper.selectByName(practiceSet.getName());

		practiceSet.setBuiltin(CommonConstant.TRUE);
		practiceSet.setCreateUserId(ResourceImportConstant.SUPER_ADMIN_HASH);
		practiceSet.setIsPublic(CommonConstant.FALSE);
		practiceSet.setOperationAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
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
	 * @param setJson Json化的习题集数据
	 * @return
	 */
	private PracticeSet jsonParsePracticeSet(JSONObject setJson) {
		// 习题集类型处理
		String typeName = (String) setJson.remove("practice_type");
		Integer practiceType = EnumUtils.getEnumCodeByDesc(typeName,
				PracticeSet.PracticeType.class);

		PracticeSet practiceSet = setJson.toJavaObject(PracticeSet.class);
		practiceSet.setPracticeType(practiceType);

		return practiceSet;

	}

}
