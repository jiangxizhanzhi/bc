package com.cyberpeace.oj.practice.controller.admin;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.req.RMImportReq;
import com.cyberpeace.oj.practice.controller.req.RmPracticeImportReq;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "oj-后台习题资源数据导入api")
@RestController
@RequestMapping("/oj/admin/practice/import")
public class AdminPracticeImportController {

	private Map<String, String> pathMap = Maps.newConcurrentMap();

	@Autowired
	private PracticeSetService practiceSetService;

	@ApiOperation("资管理论习题导入")
	@PostMapping("/rm/theory")
	public BaseRsp<Boolean> importRMTheory(@RequestBody @Validated RMImportReq rmImportReq) {

		validateInImport(rmImportReq.getPath());
		try {
			practiceSetService.importRmTheorySet(rmImportReq.getPath());
		} finally {
			pathMap.remove(rmImportReq.getPath());
		}
		return new BaseRsp<>(CommonConstant.TRUE);

	}

	@ApiOperation("资管实操习题资源导入")
	@PostMapping("/rm/ctf")
	public BaseRsp<Boolean>
			importRMCtf(@RequestBody @Validated RmPracticeImportReq practiceImportReq) {

		// 导入前校验
		validateInImport(practiceImportReq.getPath());

		// 执行数据导入
		List<String> existListPath;
		try {
			existListPath = practiceSetService.importRmCtfSet(practiceImportReq.getPath(),
					practiceImportReq.getEnvReqs());
		} finally {
			pathMap.remove(pathMap.get(practiceImportReq.getPath()));
		}

		// 设置返回值
		BaseRsp<Boolean> baseRsp = new BaseRsp<>();
		baseRsp.setData(CommonConstant.TRUE);
		if (CollectionUtils.isNotEmpty(existListPath)) {
			log.error("Exist operation practice is [{}]",
					String.join(CommonConstant.VERTICAL_LINE, existListPath));
			String message = String.format("Exist operation practice is [%s]",
					String.join(CommonConstant.VERTICAL_LINE, existListPath));
			baseRsp.setMessage(message);
		}

		return baseRsp;
	}

	/** 校验资源导入情况 防止接口多次调用*/
	private void validateInImport(String path) {
		if (null != pathMap.putIfAbsent(path, CommonConstant.EMPTY_STRING)) {
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_IN_IMPORT);
		}
	}

}
