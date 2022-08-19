package com.cyberpeace.cr.crbase.service.impl;

import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.ExportTrainMDService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class ExportTrainMDServiceImpl implements ExportTrainMDService {

	public static Map<Integer, ExportTrainMDService> register = Maps.newHashMap();

	public static final String BASE_MD_FILE = "README.md";

	public static final String IMG = "img";

	public static final String WRITE_UP_File = "writeup.md";

	public static final String TASK = "task";

	public static final String MD_LOGO = "./logo.png";

	@Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	@PostConstruct
	public void init() {
		if (register.containsKey(getCode())) {
			throw new Error("declareMessageType=[" + getCode() + "] repeat");
		}

		register.put(getCode(), this);
	}

	public Integer getCode() {
		return -1;
	}

	@Override
	public void chooseType(List<String> resourceIds, String path, Integer type) {

		List<BusinessTemplate> businessTemplateList = businessTemplateMapper.getAllByIds(resourceIds, type);
		if(businessTemplateList.size() == 0){
			File file = new File(path);
			file.mkdirs();
			return;
		}
		ExportTrainMDService exportTrainMDService = register.get(type);
		exportTrainMDService.exportTrainMD(businessTemplateList, path);

	}

	@Override
	public void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path) {

	}
}
