package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalCloudTemplateAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalCloudTemplateListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCloudTemplateEnumRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCloudTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestTemplateListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCloudTemplate;

import java.io.IOException;
import java.util.List;

public interface EvalCloudTemplateService extends IService<EvalCloudTemplate> {

	void addCloudTemplate(EvalCloudTemplateAddReq evalCloudTemplateAddReq);

	void modifyCloudTemplate(EvalCloudTemplateAddReq evalCloudTemplateAddReq);

	void deleteCloudTemplate(List<String> ids);

	List<EvalCloudTemplateListRsp> getCloudTemplateList(EvalCloudTemplateListReq evalCloudTemplateListReq);

	EvalCloudTemplateEnumRsp getCloudTemplateEnum();

	EvalCloudTemplateAddReq getCloudTemplate(String resourceId);

	ListRsp<EvalTestTemplateListRsp> templateList(EvalTestListReq evalTestListReq);

	/**
	 * 批量导出场景模板
	 * @param templateIds
	 * @param tempDir
	 * @return
	 * @throws IOException
	 */
    String makeDumpFile(List<String> templateIds, String tempDir) throws IOException;

	/**
	 * 批量导入场景模板
	 * @param unZipPath
	 * @return
	 */
	Boolean batchLoad(String unZipPath);
}
