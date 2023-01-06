package com.cyberpeace.integratation.service;

import java.util.List;
import java.util.Map;

import com.cyberpeace.integratation.model.situation.BcContext;
import com.cyberpeace.integratation.model.situation.ModelContext;
import com.cyberpeace.integratation.model.situation.ModelTypeContext;

public interface RemoteOperationService {

	List<BcContext> getBcList(String type);

	List<ModelTypeContext> getModelTypeList();

	List<ModelContext> getModelList();

	Map<String, ModelContext> getModelListMap();

	Map<String, ModelTypeContext> getModelTypeListMap();
}
