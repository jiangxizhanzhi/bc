package com.cyberpeace.oj.practice.controller.admin;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.ojbase.controller.req.RMImportReq;
import com.cyberpeace.oj.practice.controller.req.RmPracticeImportReq;
import com.cyberpeace.oj.practice.service.PracticeSetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "oj-后台习题资源数据导入api")
@RestController
@RequestMapping("/oj/admin/practice/import")
public class AdminPracticeImportController {

	@Autowired
	private PracticeSetService practiceSetService;

	@ApiOperation("资管理论习题导入")
	@PostMapping("/rm/theory")
	public BaseRsp<Boolean> importRMTheory(@RequestBody @Validated RMImportReq rmImportReq) {
		practiceSetService.importRmTheorySet(rmImportReq.getPath());
		return new BaseRsp<>(CommonConstant.TRUE);

	}

	@ApiOperation("资管实操习题资源导入")
	@PostMapping("/rm/ctf")
	public BaseRsp<Boolean>
			importRMCtf(@RequestBody @Validated RmPracticeImportReq practiceImportReq) {

		// 执行数据导入
		List<String> existListPath = practiceSetService.importRmCtfSet(practiceImportReq.getPath(),
				practiceImportReq.getEnvReqs());

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

}
