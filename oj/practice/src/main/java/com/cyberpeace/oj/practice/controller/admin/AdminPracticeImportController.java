package com.cyberpeace.oj.practice.controller.admin;

import java.util.List;

import com.cyberpeace.common.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.ojbase.controller.req.RMImportReq;
import com.cyberpeace.oj.practice.controller.req.DuplicatePracticeSetInspectReq;
import com.cyberpeace.oj.practice.controller.req.RmPracticeImportReq;
import com.cyberpeace.oj.practice.service.PracticeSetImportService;
import com.cyberpeace.oj.practice.service.PracticeSetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 管理实践导入Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Slf4j(topic = "resourceImport")
@Api(tags = "oj-后台习题资源数据导入api")
@RestController
@RequestMapping("/oj/admin/practice/import")
public class AdminPracticeImportController {

    /**
     * 实践设置Service
     *
     * @see PracticeSetService
     */
    @Autowired
	private PracticeSetService practiceSetService;

    /**
     * 实践设置导入Service
     *
     * @see PracticeSetImportService
     */
    @Autowired
	private PracticeSetImportService practiceSetImportService;

    /**
     * 导入 rmtheory
     *
     * @param rmImportReq 马币导入请求体
     * @return {@code BaseRsp<Boolean> }
     * @throws
     * @see RMImportReq
     * @see BaseRsp<Boolean>
     */
    @ApiOperation("资管理论习题导入")
	@PostMapping("/rm/theory")
	public BaseRsp<Boolean> importRMTheory(@RequestBody @Validated RMImportReq rmImportReq) {
		try {
			log.info("Resource management import start! info=[{}]", rmImportReq.getPath());
			boolean result = practiceSetImportService.importRmTheorySet(rmImportReq.getPath());
			return new BaseRsp<>(result);
		} catch (Exception e) {
			log.error("Resource management theory import failed! catch error:", e);
			throw e;
		}
	}

    /**
     * 导入 RMCTF
     *
     * @param practiceImportReq 实践导入请求体
     * @return {@code BaseRsp<Boolean> }
     * @throws
     * @see RmPracticeImportReq
     * @see BaseRsp<Boolean>
     */
    @ApiOperation("资管实操习题资源导入")
	@PostMapping("/rm/ctf")
	public BaseRsp<Boolean>
			importRMCtf(@RequestBody @Validated RmPracticeImportReq practiceImportReq) {

		// 执行数据导入
		try {
			log.info("Resource management operation import start! info=[{}]",
					JsonUtil.toJsonString(practiceImportReq));
			List<String> existListPath = practiceSetImportService
					.importRmCtfSet(practiceImportReq.getPath(), practiceImportReq.getEnvReqs());

			// 设置返回值
			BaseRsp<Boolean> baseRsp = new BaseRsp<>(true);
			if (CollectionUtils.isNotEmpty(existListPath)) {
				log.error("Exist operation practice is [{}]",
						String.join(CommonConstant.VERTICAL_LINE, existListPath));
				String message = String.format("Exist operation practice is [%s]",
						String.join(CommonConstant.VERTICAL_LINE, existListPath));
				baseRsp.setMessage(message);
			}

			return baseRsp;
		} catch (Exception e) {
			log.error("Resource management operation import failed! catch error:", e);
			throw e;
		}
	}

    /**
     * 导出
     *
     * @param practiceSetId      实践设置id
     * @param practiceId         实践id
     * @param newPath            新建路径
     * @param disableImageExport 禁用图像导出
     * @return {@code BaseRsp<String> }
     * @throws
     * @see String
     * @see String
     * @see Boolean
     * @see Boolean
     * @see BaseRsp<String>
     */
    @ApiOperation("实操习题导出")
	@GetMapping("/rm/ctf/export")
	@ApiImplicitParams({ @ApiImplicitParam(name = "set_id", value = "习题编号"),
			@ApiImplicitParam(name = "practice_id", value = "习题编号"),
			@ApiImplicitParam(name = "new_path", value = "是否生成新路径"),
			@ApiImplicitParam(name = "disable_image_export", value = "是否禁止镜像导出") })
	public BaseRsp<String> export(
			@RequestParam(name = "set_id", required = false) String practiceSetId,
			@RequestParam(name = "practice_id", required = false) String practiceId,
			@RequestParam(name = "new_path", required = false, defaultValue = "false") Boolean newPath,
			@RequestParam(name = "disable_image_export", required = false) Boolean disableImageExport) {

		String filePath = practiceSetService.exportPracticeSet(practiceSetId, practiceId, newPath,
				disableImageExport);

		return new BaseRsp<>(filePath);
	}

    /**
     * 重复实践设置检查
     *
     * @param practiceSetsReq 实践集请求体
     * @return {@code BaseRsp<List<String>> }
     * @throws
     * @see DuplicatePracticeSetInspectReq
     * @see BaseRsp<List<String>>
     */
    @ApiOperation("习题集重复校验")
	@PostMapping("/inspect_duplication")
	public BaseRsp<List<String>> duplicatePracticeSetInspect(
			@RequestBody @Validated DuplicatePracticeSetInspectReq practiceSetsReq) {

		List<String> result = practiceSetService
				.duplicatePracticeSetInspect(practiceSetsReq.getFileNames());

		return new BaseRsp<>(result);

	}

}
