package com.cyberpeace.assess.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.dao.entity.AssessFileData;
import com.cyberpeace.assess.dao.mapper.AssessFileDataMapper;
import com.cyberpeace.assess.service.AssessFileDataService;
import com.cyberpeace.assess.service.bo.TableColumn;
import com.cyberpeace.common.component.CsvUtil;
import com.cyberpeace.common.component.ExcelDataHandler;

/**
 *
 */
@Service
public class AssessFileDataServiceImpl extends ServiceImpl<AssessFileDataMapper, AssessFileData> implements AssessFileDataService {

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
	public void parseModelFile(String resourceId, Integer interfaceType, String attachmentUrl) {
		try {
			if (interfaceType.equals(2)) {
				List<List<String>> sheetData = Lists.newArrayList();
				if (attachmentUrl.contains(".csv")) {

					sheetData = CsvUtil.parseDatalistCsv(attachmentUrl);

				}
				if (attachmentUrl.contains(".xls")) {
					ExcelDataHandler dataHandler = new ExcelDataHandler(attachmentUrl, 0);

					sheetData = dataHandler.getSheetData(0);

					dataHandler.closeIO();
				}

				String data = getModelFileString(sheetData);

				AssessFileData assessFileData = new AssessFileData();
				assessFileData.setRelatedId(resourceId);
				assessFileData.setPath(attachmentUrl);
				assessFileData.setContent(data);

				save(assessFileData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getModelFileString(List<List<String>> sheetData) {

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

		return JSONObject.toJSONString(jsonObject);
	}
}
