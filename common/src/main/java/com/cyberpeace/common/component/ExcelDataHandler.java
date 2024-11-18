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

/**
 * Excel 数据处理程序
 *
 * @author SN
 * @version 1.0.0
 * @since 1.0.0@date 2024/11/18
 */
@Slf4j
public class ExcelDataHandler {

	/**
	 * 表
	 *
	 * @see Sheet
	 */
	private Sheet sheet;

	/**
	 * 练习 册
	 *
	 * @see Workbook
	 */
	private Workbook workbook;

	/**
	 * 文件输入流量
	 *
	 * @see FileInputStream
	 */
	private FileInputStream fileInputStream;

	/**
	 * 构造参数 初始化excel数据
	 *
	 * @param filePath   文件路径
	 * @param sheetIndex sheet表名
	 * @throws
	 * @see String
	 * @see int
	 */
	public ExcelDataHandler(String filePath, int sheetIndex) {
		try {
			// 创建文件流量
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
			throw new BusinessException(ErrorCode.FILE_READ_FAIL, filePath);
		}
	}

	/**
	 * 构造函数，初始化excel数据
	 *
	 * @param filePath  excel路径
	 * @param sheetName sheet表名
	 * @throws
	 * @see String
	 * @see String
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
			log.error("excel fileInputStream close failed!  filePath =[{}],sheetName=[{}]",filePath,sheetName);
			throw new BusinessException(ErrorCode.FILE_READ_FAIL, filePath);
		}
	}

	/**
	 * excel数据读取
	 *
	 * @param startRow
	 * @return
	 * @throws
	 * @see int
	 * @see List<List<String>>
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
	 * @throws
	 * @see int
	 * @see int
	 * @see List<List<String>>
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
	 * @throws
	 * @see int
	 * @see int
	 * @see String
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
				cell.setCellType(CellType.STRING);
				return cell.getStringCellValue().trim();
			}

		}
		return cell.toString().trim();
	}

	/**
	 * 关闭 io
	 *
	 * @throws
	 */
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
	 * 获取excel写对象
	 *
	 * @param filePath 文件路径
	 * @return
	 * @throws
	 * @see String
	 * @see Workbook
	 */
	private static Workbook createWorkbook(String filePath) {
		String fileSuffix = FileUtil.getFileSuffix(filePath);

		if (StringUtils.isBlank(fileSuffix)) {
			log.error("fileSuffix is blank! fileSuffix=[{}]",fileSuffix);
			throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
		}

		if (fileSuffix.equals(FileSunffixConstant.XLSX.getDesc())) {
			return new XSSFWorkbook();
		}

		if (fileSuffix.equals(FileSunffixConstant.XLS.getDesc())) {
			return new HSSFWorkbook();
		}
		log.error("createWorkbook fail! fileSuffix=[{}]",fileSuffix);
		throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
	}

	/**
	 * do export excel （导出 excel）
	 *
	 * @param dataList    传入对象 对象非基本数据类型
	 * @param filePath    excel导出的文件路径
	 * @param sheetName
	 * @param excelHeader 标题
	 * @param workbook    导出格式
	 * @param passFields  pass 字段
	 * @throws
	 * @see List<T>
	 * @see String
	 * @see String
	 * @see ExcelHeader
	 * @see Workbook
	 * @see List<String>
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

					cellNum++;
				}
			}

			workbook.write(new FileOutputStream(filePath));

		} catch (Exception e) {
			log.error(
					"Construct XSSFSheet object failed! file_path=[{}], sheet_name=[{}], catch error:",
					filePath, e);
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
	 *
	 * @param data
	 * @param filePath
	 * @param excelHeader Excel 标题
	 * @throws
	 * @see List<T>
	 * @see String
	 * @see ExcelHeader
	 */
	public static <T> void exportExcel(List<T> data, String filePath, ExcelHeader excelHeader) {

		Workbook workbook = null;
		try {
			FileUtil.createParentPath(filePath);
			workbook = createWorkbook(filePath);
			doExportExcel(data, filePath, CommonConstant.ONE.toString(), excelHeader, workbook,
					null);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				// 关闭
			}
		}
	}

	/**
	 * excel 文件导出
	 *
	 * @param list        导出数据
	 * @param filePath    文件路径
	 * @param sheetName   sheet名
	 * @param passFields  过滤字段
	 * @param excelHeader 导出列头
	 * @return 文件流量
	 * @throws
	 * @see List<T>
	 * @see String
	 * @see String
	 * @see List<String>
	 * @see ExcelHeader
	 * @see File
	 */
	public static <T> File exportExcel(List<T> list, String filePath, String sheetName,
			List<String> passFields, ExcelHeader excelHeader) {

		File file = new File(filePath);
		// 校验文件是否存在 不存在则新建
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			boolean isCreated = parentFile.mkdirs();
			if (!isCreated) {
				log.error("exportExcel fail! filePath=[{}]",filePath);
				throw new BusinessException(ErrorCode.FAILED_TO_MAKE_DIR, parentFile.getPath());
			}
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
			int rowNum = 1;
			if (list.size() != 0) {
				T t = list.get(0);
				Field[] fields = t.getClass().getDeclaredFields();
				Field[] superClassFields = t.getClass().getSuperclass().getDeclaredFields();
				Field[] allFields = ArrayUtils.addAll(fields, superClassFields);
				for (T customersForm : list) {
					int cellNum = 0;
					HSSFRow dataRow = sheet.createRow(rowNum);
					for (Field field : allFields) {
						// 设置允许通过反射访问私有变量
						field.setAccessible(true);
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
		}

		return file;
	}

}
