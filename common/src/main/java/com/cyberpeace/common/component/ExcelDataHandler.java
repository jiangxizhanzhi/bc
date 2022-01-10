package com.cyberpeace.common.component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.cyberpeace.common.constant.FileSunffixConstant;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.FileUtil;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

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

}
