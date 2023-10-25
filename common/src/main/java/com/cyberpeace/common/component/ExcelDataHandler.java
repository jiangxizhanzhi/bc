package com.cyberpeace.common.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONException;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.ExcelHeader;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

/**
 * excel数据处理器 需要什么方法自己加
 */
@Slf4j
public class ExcelDataHandler {

	private Sheet sheet;

	private Workbook workbook;

	private FileInputStream fileInputStream;

	/**
	 * 构造参数 初始化excel数据
	 *
	 * @param filePath   文件路径
	 * @param sheetIndex sheet表名
	 */
	public ExcelDataHandler(String filePath, int sheetIndex) {
		try {
			// 创建文件流
			fileInputStream = new FileInputStream(filePath);

			workbook = WorkbookFactory.create(fileInputStream);

			// 获取sheet
			sheet = workbook.getSheetAt(sheetIndex);
		} catch (Exception e) {
			log.error(
					"Construct XSSFSheet object failed! file_path=[{}], sheet_index=[{}], catch error:",
					filePath, sheetIndex, e);
			try {
				fileInputStream.close();
				workbook.close();
			} catch (Exception e2) {
				// 关不掉算了
				log.error("excel fileInputStream close failed!  catch error:", e2);
			}
			throw new BusinessException(ErrorCode.FILE_READ_FAIL);
		}
	}

	/**
	 * 构造函数，初始化excel数据
	 *
	 * @param filePath  excel路径
	 * @param sheetName sheet表名
	 */
	public ExcelDataHandler(String filePath, String sheetName) {
		try {
			fileInputStream = new FileInputStream(filePath);

			workbook = WorkbookFactory.create(fileInputStream);

			// 获取sheet
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			log.error(
					"Construct XSSFSheet object failed! file_path=[{}], sheet_name=[{}], catch error:",
					filePath, sheetName, e);

			try {
				workbook.close();
				fileInputStream.close();
			} catch (IOException ioException) {
				// 关不掉这个没办法了
			}
			log.error("excel fileInputStream close failed!  filePath =[{}],sheetName=[{}]",
					filePath, sheetName);
			throw new BusinessException(ErrorCode.FILE_READ_FAIL);
		}
	}

	/**
	 * excel数据读取
	 *
	 * @param startRow
	 * @return
	 */
	public List<List<String>> getSheetData(int startRow) {
		return getSheetData(startRow, CommonConstant.ZERO);
	}

	/**
	 * excel文件数据读取
	 *
	 * @param startRow 开始行读取 0从第一行开始读取
	 * @param endRow   结束行 0=不限制结尾 非0则读取startRow-endRow之间的数据
	 * @return
	 */
	public List<List<String>> getSheetData(int startRow, int endRow) {

		int rows = sheet.getPhysicalNumberOfRows();
		int endIndex = endRow == CommonConstant.ZERO ? rows : Math.min(rows, endRow);

		if (startRow >= endIndex) {
			return Lists.newArrayList();
		}

		// 行数据存储
		List<List<String>> rowList = Lists.newArrayList();
		for (int rowIndex = startRow; rowIndex < endIndex; rowIndex++) {

			// 列数据存储
			List<String> columnList = Lists.newArrayList();

			Row sheetRow = sheet.getRow(rowIndex);
			if (null == sheetRow) {
				break;
			}

			for (int cellIndex = 0; cellIndex < sheetRow.getLastCellNum(); cellIndex++) {
				String cellData = getExcelDateByIndex(rowIndex, cellIndex);
				columnList.add(cellData);
			}
			rowList.add(columnList);
		}

		return rowList;
	}

	/**
	 * 根据行和列的索引获取单元格的数据
	 *
	 * @param row
	 * @param column
	 * @return
	 */
	public String getExcelDateByIndex(int row, int column) {
		Row sheetRow = sheet.getRow(row);
		Cell cell = sheetRow.getCell(column);
		if (null == cell) {
			return CommonConstant.EMPTY_STRING;
		}

		if (cell.getCellType() == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				return TimeUtil.format1(cell.getDateCellValue());
			} else {
				DataFormatter dataFormatter = new DataFormatter();
				// 处理数值类型，避免自动补零
				double numericValue = cell.getNumericCellValue();
				long longValue = (long) numericValue;
				if (numericValue == longValue) {
					// 如果是整数值，将其转换为字符串，不带小数点和多余的零
					return String.valueOf(longValue);
				} else {
					// 如果是带小数的数字，使用 DataFormatter 格式化
					String formattedValue = dataFormatter.formatCellValue(cell);
					return formattedValue.trim();
				}
			}

		}
		return cell.toString().trim();
	}

	public void closeIO() {
		try {
			fileInputStream.close();
			workbook.close();
		} catch (IOException e) {
			// 关不掉没办法
			log.error("fileInputStream close fail!", e);
		}
	}


	/**
	 * @param dataList    传入对象 对象非基本数据类型
	 * @param filePath    excel导出的文件路径
	 * @param sheetName   
	 * @param excelHeader 标题
	 * @param workbook    导出格式
	 * @param <T>
	 * @return
	 */
	private static <T> void doExportExcel(List<T> dataList, String filePath, String sheetName,
			ExcelHeader excelHeader, Workbook workbook, List<String> passFields) {

		// 先创建父级目录
		FileUtil.createParentPath(filePath);

		// 创建HSSFSheet对象
		Sheet sheet = workbook.createSheet(sheetName);
		// 创建HSSFRow对象
		Row row = sheet.createRow(CommonConstant.ZERO);

		// 在excel表中添加表头
		for (int i = 0; i < excelHeader.getHeader().length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(excelHeader.getHeader()[i]);
		}

		try {
			if (CollectionUtils.isEmpty(dataList)) {
				workbook.write(new FileOutputStream(filePath));
				return;
			}

			dealDataList(dataList, sheet, passFields);
			workbook.write(new FileOutputStream(filePath));

		} catch (Exception e) {
			log.error("Construct XSSFSheet object failed! file_path=[{}], catch error:", filePath,
					e);
			try {
				workbook.close();
			} catch (IOException ioException) {
				// .....
			}
			throw new BusinessException(ErrorCode.FAILED_TO_WRITE_FILE);
		}

	}

	/**
	 * excel文件导出
	 * @param data
	 * @param filePath
	 * @param excelHeader
	 * @param <T>
	 * @return
	 */
	public static <T> void exportExcel(List<T> data, String filePath, ExcelHeader excelHeader) {
		FileUtil.createParentPath(filePath);
		String fileSuffix = FileUtil.getFileSuffix(filePath);
		if (StringUtils.isBlank(fileSuffix)) {
			log.error("fileSuffix is blank! fileSuffix=[{}]", fileSuffix);
			throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
		}

		if (fileSuffix.equals(FileSunffixConstant.XLSX.getDesc()) ) {
			try (Workbook workbook =new XSSFWorkbook()) {
				doExportExcel(data, filePath, CommonConstant.ONE.toString(), excelHeader, workbook,
						null);
			} catch (IOException e) {
				throw new BusinessException();
			}
		} else if (fileSuffix.equals(FileSunffixConstant.XLS.getDesc())) {
			try (Workbook workbook =new HSSFWorkbook()) {
				doExportExcel(data, filePath, CommonConstant.ONE.toString(), excelHeader, workbook,
						null);
			} catch (IOException e) {
				throw new BusinessException();
			}
		} else {
			log.error("createWorkbook fail! fileSuffix=[{}]", fileSuffix);
			throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
		}


	}

	/**
	 * excel 文件导出
	 * @param list 导出数据
	 * @param sheetName sheet名
	 * @param passFields 过滤字段
	 * @param excelHeader 导出列头
	 * @param <T> 数据bean
	 * @return 文件流
	 */
	public static <T> File exportExcel(List<T> list, String filePath, String sheetName,
			List<String> passFields, ExcelHeader excelHeader) {

		File file = new File(filePath);
		// 校验文件是否存在 不存在则新建
		File parentFile = file.getParentFile();
		if (!parentFile.exists() && !parentFile.mkdirs()) {
			log.error("exportExcel fail! filePath=[{}]", filePath);
			throw new BusinessException(ErrorCode.FAILED_TO_MAKE_DIR, parentFile.getPath());
		}

		// 创建HSSFWorkbook对象
		HSSFWorkbook wb = new HSSFWorkbook();

		try {
			// 创建HSSFSheet对象
			HSSFSheet sheet = wb.createSheet(sheetName);
			// 创建HSSFRow对象
			HSSFRow row = sheet.createRow(0);
			// 在excel表中添加表头
			for (int i = 0; i < excelHeader.getHeader().length; i++) {
				// 创建HSSFCell对象
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(excelHeader.getHeader()[i]);
				cell.setCellValue(text);
			}

			dealList(list, sheet, passFields);
			// 输出Excel文件
			wb.write(file);

		} catch (Exception e) {
			log.error(
					"Construct XSSFSheet object failed! file_path=[{}], sheet_name=[{}], catch error:",
					filePath, sheetName, e);
			try {
				wb.close();
			} catch (IOException ioException) {
				// .....
			}
			throw new BusinessException(ErrorCode.FAILED_TO_WRITE_FILE);
		} finally {
			try {
				wb.close();
			} catch (Exception e) {
				// do nothing
			}
		}

		return file;
	}

	// 因为sonar提取方法
	private static <T> void dealList(List<T> list, HSSFSheet sheet, List<String> passFields)
			throws IllegalAccessException {
		int rowNum = 1;
		if (!list.isEmpty()) {
			T t = list.get(0);
			Field[] fields = t.getClass().getDeclaredFields();
			Field[] superClassFields = t.getClass().getSuperclass().getDeclaredFields();
			Field[] allFields = ArrayUtils.addAll(fields, superClassFields);
			for (T customersForm : list) {
				int cellNum = 0;
				HSSFRow dataRow = sheet.createRow(rowNum);
				for (Field field : allFields) {
					// 设置允许通过反射访问私有变量
					ReflectionUtils.makeAccessible(field);
					// 过滤无用字段值
					if (passFields.contains(field.getName())) {
						continue;
					}
					// 获取字段的值
					String value = "";
					Object obj = field.get(customersForm);
					if (obj != null) {
						value = obj.toString();
					}
					// 获取字段属性名称 设置单元格内容
					dataRow.createCell(cellNum).setCellValue(value);
					cellNum++;
				}
				rowNum++;
			}
		}
	}

	private static <T> void dealDataList(List<T> dataList, Sheet sheet, List<String> passFields) {
		Field[] allFields = ObjUtil.getFields(dataList.get(CommonConstant.ZERO));
		for (int i = 1; i <= dataList.size(); i++) {
			int cellNum = 0;
			T t = dataList.get(i - CommonConstant.ONE);
			Row dataRow = sheet.createRow(i);
			for (Field field : allFields) {
				// 需要过滤的字段
				if (CollectionUtils.isNotEmpty(passFields)
						&& passFields.contains(field.getName())) {
					continue;
				}
				dealFields(field,t,dataRow,cellNum);
				cellNum++;
			}
		}

	}
	private static <T> void dealFields (Field field,T t,Row dataRow,int cellNum) {


		Object obj = ObjUtil.getFieldValueByName(field.getName(), t);

		try {
			if (obj instanceof Date) {
				dataRow.createCell(cellNum).setCellValue(TimeUtil.format1((Date) obj));
			} else {
				dataRow.createCell(cellNum).setCellValue(JsonUtil.toJsonString(obj));
			}

		} catch (JSONException e) {
			// 如果对象不能格式化 则认为为基本数据类型
			log.warn("ExcelDataHandler json parse error:", e);

			// 获取字段属性名称 设置单元格内容
			if (obj instanceof Date) {
				dataRow.createCell(cellNum).setCellValue((Date) obj);
			} else {
				dataRow.createCell(cellNum).setCellValue(
						null == obj ? CommonConstant.EMPTY_STRING : obj.toString());
			}
		}
	}
}
