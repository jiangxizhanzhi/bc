package com.cyberpeace.cr.crbase.service.impl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class ExportTrainMDServiceImpl implements ExportTrainMDService {

	protected static final Map<Integer, ExportTrainMDService> register = Maps.newHashMap();

	public static final String BASE_MD_FILE = "README.md";

	public static final String IMG = "img";

	public static final String WRITE_UP_FILE = "writeup.md";

	public static final String TASK = "task";

	public static final String MD_LOGO = "./logo.png";

	public static final String ENV = "env";

	public static final String IMAGES = "images";

	public static final String VERSION = "version.txt";

	public static final String MISSIONS_SCHEME = "missions_scheme";

	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	@Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@PostConstruct
	public void init() {
		if (register.containsKey(getCode())) {
			throw new BusinessException("declareMessageType=[" + getCode() + "] repeat");
		}

		register.put(getCode(), this);
	}

	public Integer getCode() {
		return -1;
	}

	@Override
	public void chooseTypeAndExportTrain(List<String> resourceIds, String path, Integer type, String sceneNumber, Boolean disableImageExport) throws IOException {

		List<BusinessTemplate> businessTemplateList = businessTemplateMapper.getAllByIds(resourceIds, type);
		if(businessTemplateList.isEmpty()){
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

	@Override
	public void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path,
			String sceneNumber, Boolean disableImageExport) {
		String json = JsonUtil.toJsonString(businessTemplateList);
		log.error(
				"Method not implemented! businessTemplateList = [{}], path = [{}], sceneNumber = [{}], disableImageExport = [{}]",
				json, path, sceneNumber, disableImageExport);
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public void exportWriteUp(String writeUp, File writeUpFile, String title){
		FileWriter fw = null;
		BufferedWriter bw = null;
		try{
			fw = new FileWriter(writeUpFile);
			bw = new BufferedWriter(fw);
			bw.write(writeUp);
			bw.flush();
			log.info("export mission [{}] writeup.md successfully", title);
		}catch (Exception e) {
			log.error("Markdown content write failed！file_path=[{}], catch error:", writeUpFile.getAbsolutePath(), e);
			throw new BusinessException(ErrorCode.FAILED_TO_WRITE_FILE);
		} finally {
			try {
				if (null != fw) {
					fw.close();
				}
				if (null != bw) {
					bw.close();
				}
			} catch (IOException e) {
				// do nothing
			}
		}
	}

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
