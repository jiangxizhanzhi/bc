package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.IdListReq;
import com.cyberpeace.cr.crbase.service.context.effect.*;

public interface SituationEffectService {

	List<SituationEffectCategoryName> listCategory();

	void createCategory(SituationEffectCategoryAddReq req);

	void modifyCategory(SituationEffectCategoryModifyReq req);

	void deleteCategory(IdListReq req);

	void createEffect(SituationEffectAddReq req);

	void modifyEffect(SituationEffectModifyReq req);

	void deleteEffect(String id);

	ListRsp<SituationEffectBo> pageEffect(SituationEffectPageReq req);

	ListRsp<SituationEffectCategoryName> pageCategory(PageReqBase reqBase);

	SituationEffectBo getDetail(String resourceId);

	void createMapping(SituationEffectMappingAddReq req);

	SituationEffectMappingBo getMappingDetail(String resourceId);

	void modifyMapping(SituationEffectMappingModifyReq req);

	void deleteMapping(List<String> ids);

	ListRsp<SituationEffectMappingBo> pageMapping(PageReqBase req);
}
