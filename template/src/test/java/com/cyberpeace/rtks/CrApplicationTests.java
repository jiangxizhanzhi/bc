package com.cyberpeace.rtks;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.assess.service.bo.TableColumn;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.util.JsonUtil;

class CrApplicationTests {

	public static void parseRmTheory(String filePath) {
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
		JsonUtil.toJsonString(sheetData);

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

	public static void main(String[] args) {
		parseRmTheory("c:\\test\\aaa.xls");

		String filePath = "C:\\test\\1.csv";
		// 解析xml文件得到一个List集合

		try {
			// List<Map> maps = CsvUtil.parseCsv(filePath, Map.class);

			// System.out.println(maps);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
