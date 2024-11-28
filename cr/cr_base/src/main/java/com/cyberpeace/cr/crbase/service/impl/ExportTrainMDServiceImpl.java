package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MdConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.mdparser.MdParser;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.ExportTrainMDService;
import com.cyberpeace.cr.crbase.service.context.md.FileMD;
import com.cyberpeace.cr.crbase.service.context.md.JudgeMD;
import com.cyberpeace.cr.crbase.service.context.md.MultipleChoiceMD;
import com.cyberpeace.cr.crbase.service.context.md.RadioMD;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.cyberpeace.integratation.model.scene.SceneDumpContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出训练md服务实现
 *
 * @author SN
 * @date 2024/11/13
 */
@Service
public class ExportTrainMDServiceImpl implements ExportTrainMDService {

	/**
	 * 注册
	 */
	public static Map<Integer, ExportTrainMDService> register = Maps.newHashMap();

	/**
	 * 基础markdown文件
	 */
	public static final String BASE_MD_FILE = "README.md";

	/**
	 * IMG
	 */
	public static final String IMG = "img";

	/**
	 * 写入向上文件
	 */
	public static final String WRITE_UP_FILE = "writeup.md";

	/**
	 * 任务
	 */
	public static final String TASK = "task";

	/**
	 * markdown图标
	 */
	public static final String MD_LOGO = "./logo.png";

	/**
	 * 环境
	 */
	public static final String ENV = "env";

	/**
	 * 图像
	 */
	public static final String IMAGES = "images";

	/**
	 * 版本
	 */
	public static final String VERSION = "version.txt";

	/**
	 * 任务计划
	 */
	public static final String MISSIONS_SCHEME = "missions_scheme";

	/**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	/**
	 * 业务模板Mapper
	 */
	@Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	/**
	 * 远程场景Service
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		if (register.containsKey(getCode())) {
			throw new Error("declareMessageType=[" + getCode() + "] repeat");
		}

		register.put(getCode(), this);
	}

	/**
	 * 获取代号
	 *
	 * @return {@code Integer }
	 */
	public Integer getCode() {
		return -1;
	}

	/**
	 * 选择类型和导出训练
	 *
	 * @param resourceIds        资源id列表
	 * @param path               路径
	 * @param type               类型
	 * @param sceneNumber        场景数
	 * @param disableImageExport 禁用图像导出
	 * @throws IOException io异常
	 */
	@Override
	public void chooseTypeAndExportTrain(List<String> resourceIds, String path, Integer type, String sceneNumber, Boolean disableImageExport) throws IOException {

		List<BusinessTemplate> businessTemplateList = businessTemplateMapper.getAllByIds(resourceIds, type);
		if(businessTemplateList.size() == 0){
			String ids = StringUtils.join(resourceIds, ",");
			log.error("IDs do not exist! ids = [{}]", ids);
			throw new BusinessException(CrBusinessErrorCode.EXPORT_TRAIN_ID_NOT_EXIST);
		}
		if(businessTemplateList.size() < resourceIds.size()){
			log.warn("部分id不存在");
		}
		ExportTrainMDService exportTrainMDService = register.get(type);
		exportTrainMDService.exportTrainMD(businessTemplateList,
				path + File.separator + "cr" + File.separator + "场景库", sceneNumber, disableImageExport);

		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path + File.separator + VERSION),
				StandardCharsets.UTF_8);
		FileUtil.writeString(IDGeneratorUtil.getID(), osw);
		log.info("export version.txt successfully");
		log.info("----------export train successfully-----------");

	}

	/**
	 * 导出训练markdown
	 *
	 * @param businessTemplateList 业务模板列表
	 * @param path                 路径
	 * @param sceneNumber          场景数
	 * @param disableImageExport   禁用图像导出
	 */
	@Override
	public void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path,
			String sceneNumber, Boolean disableImageExport) {
		String json = JsonUtil.toJsonString(businessTemplateList);
		log.error(
				"Method not implemented! businessTemplateList = [{}], path = [{}], sceneNumber = [{}], disableImageExport = [{}]",
				json, path, sceneNumber, disableImageExport);
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	/**
	 * 导出写入向上
	 *
	 * @param writeUp     题解
	 * @param writeUpFile 写入向上文件
	 * @param title       标题
	 */
	public void exportWriteUp(String writeUp, File writeUpFile, String title){
		try(FileWriter fw = new FileWriter(writeUpFile);
			BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(writeUp);
			bw.flush();
			log.info("export mission [{}] writeup.md successfully", title);
		}catch (Exception e) {
			log.error("Markdown content write failed！file_path=[{}], catch error:", writeUpFile.getAbsolutePath(), e);
			throw new BusinessException(ErrorCode.FAILED_TO_WRITE_FILE);
		}
	}

	/**
	 * 导出场景
	 *
	 * @param topoId             拓扑id
	 * @param envPath            env 路径
	 * @param imagePath          图像路径
	 * @param sceneNumber        场景数
	 * @param disableImageExport 禁用图像导出
	 * @return {@code Map<String, String> }
	 */
	public Map<String, String> exportScene(String topoId, String envPath, String imagePath, String sceneNumber, Boolean disableImageExport){
		SceneDumpContext sceneDumpContext = new SceneDumpContext();
		sceneDumpContext.setId(topoId);
		sceneDumpContext.setScene_number(sceneNumber);
		FileUtil.mkdirs(envPath);
		sceneDumpContext.setEnv_path(envPath);
		FileUtil.mkdirs(imagePath);
		sceneDumpContext.setImage_path(imagePath);
		sceneDumpContext.setDisable_image_export(disableImageExport);
		JSONObject jsonObject = remoteSceneService.sceneDump(sceneDumpContext);
		List<JSONObject> convertInfos = jsonObject.getObject("convert_infos", List.class);
		Map<String, String> convertMap = new HashMap<>();
		for(JSONObject convert: convertInfos){
			convertMap.put(convert.getString("origin_id"), convert.getString("id"));
		}
		return convertMap;
	}

	/**
	 * 验证任务瓦
	 *
	 * @param missionTemplateMap 任务模板映射
	 * @param schemeData         方案数据
	 * @param trainName          训练名称
	 */
	public void validateTaskTile(Map<String, MissionTemplate> missionTemplateMap, List<CrRoleConfigSchemeStage> schemeData, String trainName){
		List<String> titleList = new ArrayList<>();
		for (CrRoleConfigSchemeStage crRoleConfigSchemeStage:schemeData){
			for (CrRoleConfigMission crRoleConfigMission: crRoleConfigSchemeStage.getMissions()){
				MissionTemplate missionTemplate = missionTemplateMap.get(crRoleConfigMission.getResourceId());
				if(missionTemplate != null){
					String taskName = missionTemplate.getTitle();
					if(titleList.contains(taskName)){
						log.error("Many tasks' name in train [{}] both are [{}]!", trainName, taskName);
						throw new BusinessException(CrBusinessErrorCode.EXPORT_TRAIN_DUPLICATED_TASK_TITLE,
								trainName, taskName);
					}
					titleList.add(taskName);
				}
			}
		}
	}

	/**
	 * 导出IMG和写入向上
	 *
	 * @param writeUp 题解
	 * @param title   标题
	 * @param task    任务
	 */
	public void exportImgAndWriteUp(String writeUp, String title, File task){
		File imgFile = new File(task.getPath() + File.separator + IMG);
		if (!imgFile.exists()) {
			imgFile.mkdir();
		}

		List<String> strList = LocalStringUtil.getMatchStr(writeUp, PatternConstant.MARKDOWN_IMAGE);
		for (int j = 0; j < strList.size(); j++) {
			String str = strList.get(j);
			String imgUrl = str.substring(str.lastIndexOf("(") + 1, str.lastIndexOf(")"));
			FileUtil.copyFile(imgUrl, imgFile.getPath() + File.separator + (j + 1) + "." + FileUtil.getFileSuffix(imgUrl), false);
			imgUrl = "./img/" + (j + 1) + "." + FileUtil.getFileSuffix(imgUrl);
			writeUp = writeUp.replace(str, String.format(MdConstant.ONLY_URL_IMG,imgUrl));
		}
		log.info("export mission [{}] img successfully", title);
		File writeUpFile = new File(task.getPath() + File.separator + WRITE_UP_FILE);
		exportWriteUp(writeUp, writeUpFile, title);
	}

	/**
	 * 导出任务
	 *
	 * @param missionTemplate 任务模板
	 * @param taskList        任务列表
	 * @param constantMap     不断映射
	 * @param taskFile        任务文件
	 */
	public void exportMission(MissionTemplate missionTemplate, List<String> taskList, Map<String, String> constantMap,File taskFile){
		//单个任务导出
		taskList.add(missionTemplate.getTitle());
		MdParser mdParser = new MdParser();

		File task = new File(taskFile.getPath() + File.separator + missionTemplate.getTitle());
		FileUtil.mkdirs(task.getPath());
		JSONArray options = null;
		List<String> optionList = null;

		switch (missionTemplate.getType()) {
			case 0:
				//判断
				JudgeMD judgeMD = BeanUtils.copy(missionTemplate, JudgeMD.class);
				judgeMD.setTaskType(constantMap.get(missionTemplate.getCategoryId()));
				mdParser.write(judgeMD, new File(task.getPath() + File.separator + BASE_MD_FILE));
				break;
			case 1:
				//单选
				RadioMD radioMD = BeanUtils.copy(missionTemplate, RadioMD.class);
				options = JSON.parseArray(missionTemplate.getOption());
				optionList = new ArrayList<>();
				for (int i = 0; i < options.size(); i++) {
					optionList.add(options.getJSONObject(i)
										  .getString("optionValue"));
				}
				radioMD.setOptions(optionList);
				radioMD.setTaskType(constantMap.get(missionTemplate.getCategoryId()));
				mdParser.write(radioMD, new File(task.getPath() + File.separator + BASE_MD_FILE));
				break;
			case 2:
				//多选
				MultipleChoiceMD multipleChoiceMD = BeanUtils.copy(missionTemplate,
						MultipleChoiceMD.class);
				options = JSON.parseArray(missionTemplate.getOption());
				optionList = new ArrayList<>();
				for (int i = 0; i < options.size(); i++) {
					optionList.add(options.getJSONObject(i)
										  .getString("optionValue"));
				}
				multipleChoiceMD.setOptions(optionList);
				multipleChoiceMD.setTaskType(constantMap.get(missionTemplate.getCategoryId()));
				mdParser.write(multipleChoiceMD, new File(task.getPath() + File.separator + BASE_MD_FILE));
				break;
			default:
				//其他
				FileMD fileMD = BeanUtils.copy(missionTemplate, FileMD.class);
				fileMD.setTaskType(constantMap.get(missionTemplate.getCategoryId()));
				mdParser.write(fileMD, new File(task.getPath() + File.separator + BASE_MD_FILE));
				break;
		}
		log.info("export mission [{}] README.md successfully", missionTemplate.getTitle());
		//导出img和writeUp
		exportImgAndWriteUp(missionTemplate.getWriteUp(),missionTemplate.getTitle(),task);
	}
}
