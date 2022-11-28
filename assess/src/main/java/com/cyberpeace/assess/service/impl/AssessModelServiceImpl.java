package com.cyberpeace.assess.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.controller.req.AssessListReq;
import com.cyberpeace.assess.dao.entity.AssessModel;
import com.cyberpeace.assess.dao.mapper.AssessModelMapper;
import com.cyberpeace.assess.service.AssessModelService;
import com.cyberpeace.assess.service.bo.TableColumn;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.util.JsonUtil;

/**
 *
 */
@Service
public class AssessModelServiceImpl extends ServiceImpl<AssessModelMapper, AssessModel> implements AssessModelService {

	@Autowired
	AssessModelMapper assessModelMapper;

	@Override
	public IPage<AssessModel> page(AssessListReq pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<AssessModel> result = assessModelMapper.listAssessModel(page,
				pageReqBase.getPattern(), pageReqBase.getType(), pageReqBase.getTag());

		return result;
	}

	public static JSONObject parseRmTheory(String filePath) {
		// 获取excel数据
		ExcelDataHandler dataHandler = new ExcelDataHandler(filePath, 0);

		List<List<String>> sheetData = dataHandler.getSheetData(0);

		dataHandler.closeIO();
		List<TableColumn> tableColumns = getTableColumn(sheetData.get(0));

		List<JSONObject> tableData = Lists.newArrayList();
		for (int i = 1; i < sheetData.size(); i++) {
			com.alibaba.fastjson.JSONObject json = new JSONObject();

			for (int j = 0; j < sheetData.get(i).size(); j++) {
				String key = "prop" + j;

				json.put(key, sheetData.get(i).get(j));

			}
			tableData.add(json);
		}
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("tableColumn", tableColumns);
		jsonObject.put("tableData", tableData);

		return jsonObject;

	}

	private static List<TableColumn> getTableColumn(List<String> columns) {
		List<TableColumn> tableColumns = Lists.newArrayList();
		for (int i = 0; i < columns.size(); i++) {
			TableColumn tableColumn = new TableColumn();
			tableColumn.setLabel(columns.get(i));
			tableColumn.setProp("prop" + i);
			tableColumns.add(tableColumn);
		}

		return tableColumns;

	}

	@Override
	public String getDataBaseTable(AssessModel assessModel) {
		try {
			return JsonUtil.toJsonString(parseRmTheory(assessModel.getAttachmentUrl()));
		} catch (Exception e) {
			return "";
		}

	}
}
