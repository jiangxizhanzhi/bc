package com.cyberpeace.oj.practice.controller.admin;

import java.io.File;
import java.util.List;

import com.cyberpeace.integratation.service.RemoteUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.ojbase.controller.req.RMImportReq;
import com.cyberpeace.oj.practice.controller.req.DuplicatePracticeSetInspectReq;
import com.cyberpeace.oj.practice.controller.req.RmPracticeImportReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.md.MdTheoryParser;
import com.cyberpeace.oj.practice.service.md.MdTheoryPractice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "oj-后台习题资源数据导入api")
@RestController
@RequestMapping("/oj/admin/practice/import")
public class AdminPracticeImportController {

	@Autowired
	private PracticeSetService practiceSetService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@GetMapping("/test")
	public BaseRsp<Boolean> test() {
		UserInfo userInfo = remoteUserService.getById(ResourceImportConstant.SUPER_ADMIN_HASH);

		UserInfoContext.setUserContext(userInfo);

		String path = "C:\\test";
		MdTheoryParser mdTheoryParser = new MdTheoryParser("C:\\test/README.md");
		MdTheoryPractice mdTheoryPractice = mdTheoryParser.read(MdTheoryPractice.class);
		List<PracticeTheory> theories = practiceSetService.importMdTheoryPractice(path,
				mdTheoryPractice);

		List<PracticeTheory> theoryList = practiceTheoryService
				.parseLessonPracticeTheory("C:\\test");

		MdTheoryPractice mdTheory = practiceSetService.exportMdTheoryPractice(path, theories);
		mdTheoryParser.write(mdTheory, new File("C:\\test/set.md"));

		return new BaseRsp<>(true);
	}

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

	@ApiOperation("实操习题导出")
	@GetMapping("/rm/ctf/export")
	@ApiImplicitParams({ @ApiImplicitParam(name = "set_id", value = "习题编号"),
			@ApiImplicitParam(name = "practice_id", value = "习题编号"),
			@ApiImplicitParam(name = "new_path", value = "是否生成新路径") })
	public BaseRsp<String> export(
			@RequestParam(name = "set_id", required = false) String practiceSetId,
			@RequestParam(name = "practice_id", required = false) String practiceId,
			@RequestParam(name = "new_path", required = false,
					defaultValue = "false") Boolean newPath) {

		String filePath = practiceSetService.exportPracticeSet(practiceSetId, practiceId, newPath);

		return new BaseRsp<>(filePath);
	}

	@ApiOperation("习题集重复校验")
	@PostMapping("/inspect_duplication")
	public BaseRsp<List<String>> duplicatePracticeSetInspect(
			@RequestBody @Validated DuplicatePracticeSetInspectReq practiceSetsReq) {

		List<String> result = practiceSetService
				.duplicatePracticeSetInspect(practiceSetsReq.getFileNames());

		return new BaseRsp<>(result);

	}

}
