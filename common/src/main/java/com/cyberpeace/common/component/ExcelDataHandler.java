package com.cyberpeace.common.component;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.FileUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

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

			String fileSuffix = FileUtil.getFileSuffix(filePath);
			if (fileSuffix.equals(FileSunffixConstant.XLS.getDesc())) {
				workbook = new HSSFWorkbook(fileInputStream);
			}
			if (fileSuffix.equals(FileSunffixConstant.XLSX.getDesc())) {
				workbook = new XSSFWorkbook(fileInputStream);
			}

			// 获取sheet
			sheet = workbook.getSheetAt(sheetIndex);
		} catch (Exception e) {
			log.error(
					"Construct XSSFSheet object failed! file_path=[{}], sheet_index=[{}], catch error:",
					filePath, sheetIndex, e);
			try {
				fileInputStream.close();
			} catch (IOException ioException) {
				// 关不掉算了
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

			String fileSuffix = FileUtil.getFileSuffix(filePath);
			if (fileSuffix.equals("xls")) {
				workbook = new HSSFWorkbook(fileInputStream);
			}
			if (fileSuffix.equals("xlsx")) {
				workbook = new XSSFWorkbook(fileInputStream);
			}

			// 获取sheet
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			log.error(
					"Construct XSSFSheet object failed! file_path=[{}], sheet_name=[{}], catch error:",
					filePath, sheetName, e);
			throw new BusinessException(ErrorCode.FILE_READ_FAIL);
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException e) {}
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
		int endIndex = endRow == CommonConstant.ZERO ? rows : (rows < endRow ? rows : endRow);

		if (startRow >= endIndex) {
			return Lists.newArrayList();
		}

		// 行数据存储
		List<List<String>> rowList = Lists.newArrayList();
		for (int rowIndex = startRow; rowIndex < endIndex; rowIndex++) {

			// 列数据存储
			List<String> columnList = Lists.newArrayList();

			Row sheetRow = sheet.getRow(rowIndex);
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
		return cell.toString();
	}

	public void closeIO() {
		try {
			fileInputStream.close();
		} catch (IOException e) {
			// 关不掉没办法
			log.error("fileInputStream close fail!", e);
		}
	}

	public static  <T> File exportExcel(List<T> list, String fileName, String sheetName,
										List<String> passFields, String [] headers, File file) {

		try {
			//创建HSSFWorkbook对象
			HSSFWorkbook wb = new HSSFWorkbook();
			//创建HSSFSheet对象
			HSSFSheet sheet = wb.createSheet(sheetName);
			//创建HSSFRow对象
			HSSFRow row = sheet.createRow(0);
			// 在excel表中添加表头
			for (int i = 0; i < headers.length; i++) {
				//创建HSSFCell对象
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
			int rowNum = 1;
			if(list.size() != 0){
				T t = list.get(0);
				Field[] fields = t.getClass().getDeclaredFields();
				Field[] superClassFields = t.getClass().getSuperclass().getDeclaredFields();
				Field[] allFields = ArrayUtils.addAll(fields, superClassFields);
				for (T customersForm : list) {
					int cellNum = 0;
					HSSFRow dataRow = sheet.createRow(rowNum);
					for (Field field : allFields) {
						//设置允许通过反射访问私有变量
						field.setAccessible(true);
						// 过滤无用字段值
						if (passFields.contains(field.getName())) {
							continue;
						}
						//获取字段的值
						String value = "";
						Object obj = field.get(customersForm);
						if (obj != null) {
							value = obj.toString();
						}
						//获取字段属性名称
						dataRow.createCell(cellNum).setCellValue(value); //设置单元格内容
						cellNum++;
					}
					rowNum++;
				}
			}
			//输出Excel文件
			wb.write(file);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(
					"Construct XSSFSheet object failed! file_path=[{}], sheet_name=[{}], catch error:",
					fileName, sheetName, e);
			throw new BusinessException(ErrorCode.FAILED_TO_WRITE_FILE);
		}
		return file;
	}

}
