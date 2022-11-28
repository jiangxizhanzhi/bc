package com.cyberpeace.assess.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.controller.res.AssessDataSqlEcharts;
import com.cyberpeace.assess.dao.entity.AssessFileData;
import com.cyberpeace.assess.dao.entity.AssessModel;
import com.cyberpeace.assess.dao.mapper.AssessFileDataMapper;
import com.cyberpeace.assess.service.AssessFileDataService;
import com.cyberpeace.assess.service.bo.TableColumn;
import com.cyberpeace.common.component.CsvUtil;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.FileUtil;

/**
 *
 */
@Service
public class AssessFileDataServiceImpl extends ServiceImpl<AssessFileDataMapper, AssessFileData> implements AssessFileDataService {

	@Autowired
	private AssessFileDataMapper assessFileDataMapper;

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
	public void parseModelFile(String relatedId, Integer interfaceType, String attachmentUrl) {
		try {
			if (AssessModel.interfaceType.DATABASE.getCode().equals(interfaceType)) {
				List<List<String>> sheetData = Lists.newArrayList();

				String fileSuffix = FileUtil.getFileSuffix(attachmentUrl);

				if (fileSuffix.equals(FileSunffixConstant.CSV.getDesc())) {

					sheetData = CsvUtil.parseDatalistCsv(attachmentUrl);

				}
				if (fileSuffix.equals(FileSunffixConstant.XLS.getDesc())
						|| fileSuffix.equals(FileSunffixConstant.XLSX.getDesc())) {

					ExcelDataHandler dataHandler = new ExcelDataHandler(attachmentUrl, 0);

					sheetData = dataHandler.getSheetData(0);

					dataHandler.closeIO();
				}

				String data = getModelFileString(sheetData);

				AssessFileData assessFileData = new AssessFileData();
				assessFileData.setRelatedId(relatedId);
				assessFileData.setPath(attachmentUrl);
				assessFileData.setContent(data);

				assessFileDataMapper.deleteByRelatedId(relatedId);
				save(assessFileData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDataBaseTableByRelatedId(String relatedId) {
		return assessFileDataMapper.getDataBaseTableByRelatedId(relatedId);
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

	@Override
	public void parseDataFile(String resourceId, Integer inputType, String inputData) throws Exception {
		String content = "";
		if(inputType.equals(CommonConstant.ONE)){
			content = saveSqlData(inputData);
		} else if(inputType.equals(CommonConstant.TWO)){
			saveExcelData(resourceId, inputData);
		} else {
			throw new BusinessException("input type error");
		}
		AssessFileData assessFileData = new AssessFileData();
		assessFileData.setRelatedId(resourceId);
		assessFileData.setContent(content);
		assessFileData.setPath(inputData);
		assessFileDataMapper.insert(assessFileData);
	}

	private void saveExcelData(String resourceId, String inputData) {

	}

	private String saveSqlData(String inputData) throws Exception {

		String sqlStr = readFileByLines(inputData);
		String[] split = sqlStr.split(";");
		if(split.length <= 0){
			throw new BusinessException("sql file is invalid");
		}

		List<String> sql = Arrays.asList(split);
		List<String> fieldList = new ArrayList();
		List<JSONObject> jsonObjects = new ArrayList<>();
		sql.forEach( s->{
			parseSqlContent(s, jsonObjects, fieldList);
		});
		AssessDataSqlEcharts assessDataSqlEcharts = new AssessDataSqlEcharts();

		assessDataSqlEcharts.setDimensions(fieldList);
		assessDataSqlEcharts.setSource(jsonObjects);
		String content = JSONObject.toJSONString(assessDataSqlEcharts);
		return content;


	}

	private void parseSqlContent(String str,List<JSONObject> jsonObjects, List<String> fieldList) {
		Pattern pattern = Pattern.compile(PatternConstant.INSERT_SQL);
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			List<String> valList = new ArrayList();
			String fields = matcher.group(1);
			String[] split = fields.split(",");
			String values = matcher.group(2);
			String[] split1 = values.split(",");
			valList.addAll(Arrays.asList(split1));
			if(split.length < 2){
				throw new BusinessException("The number of fields cannot be less than 2");
			}
			if(fieldList.isEmpty()){
				JSONObject jsonObject = new JSONObject();
				fieldList.addAll(Arrays.asList(split));
				fieldList.forEach(val ->{
					int index = fieldList.indexOf(val);
					jsonObject.put(val, valList.get(index));
				});
				jsonObjects.add(jsonObject);
			}
			valList.addAll(Arrays.asList(split1));
		}
	}

	private String readFileByLines(String filePath) throws Exception {
		StringBuffer str = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), "UTF-8"));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				// System.out.println("line " + line + ": " + tempString);

				str = str.append(" " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		return str.toString();
	}

}
