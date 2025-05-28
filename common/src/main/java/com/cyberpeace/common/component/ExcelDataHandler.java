/**
 * ### 代码分析 
 * #### 概述 
 * 该代码实现了一个通用的 `ExcelDataHandler` 类，用于处理 Excel 文件的读取和写入操作。它基于 Apache POI 库实现了对 `.xls` 和 `.xlsx` 格式文件的支持，并提供了灵活的方法来处理不同场景下的 Excel 数据。 
 * --- 
 * #### 主要功能模块 
 * 1. **构造函数** 
 * - 提供两种构造方法： 
 * - 按照文件路径和 Sheet 索引初始化。 
 * - 按照文件路径和 Sheet 名称初始化。 
 * - 在构造过程中会尝试打开文件并加载对应的 Sheet，如果失败则抛出自定义异常 `BusinessException`。 
 * 2. **Excel 数据读取** 
 * - `getSheetData(int startRow)`：从指定行开始读取整张 Sheet 的数据。 
 * - `getSheetData(int startRow, int endRow)`：支持从指定行到结束行的范围读取。 
 * - `getExcelDateByIndex(int row, int column)`：根据行和列索引获取单元格内容，支持日期格式化。 
 * 3. **Excel 数据写入** 
 * - `doExportExcel(List<T> dataList, ...)`：内部方法，用于将 Java 对象列表导出为 Excel 文件。 
 * - `exportExcel(List<T> data, String filePath, ExcelHeader excelHeader)`：公共方法，直接调用 `doExportExcel` 完成导出。 
 * - 支持自定义忽略字段、设置表头、多 Sheet 等功能。 
 * 4. **资源管理** 
 * - `closeIO()`：关闭文件流和工作簿，避免资源泄漏。 
 * - 在读取或写入过程中，对异常进行捕获并记录日志，确保程序的健壮性。 
 * 5. **工具方法** 
 * - `createWorkbook(String filePath)`：根据文件后缀动态创建合适的 `Workbook` 对象（`.xls` 或 `.xlsx`）。 
 * - 辅助类如 `ObjUtil`、`JsonUtil`、`TimeUtil` 等用于处理反射、JSON 转换和时间格式化等操作。 
 * --- 
 * #### 特点与优点 
 * - **灵活性**：支持多种初始化方式、读取范围、写入格式以及字段过滤。 
 * - **扩展性**：通过泛型 `<T>` 实现对任意 Java 对象的支持。 
 * - **健壮性**：对异常进行了全面处理，并提供详细的错误日志。 
 * - **兼容性**：支持 `.xls` 和 `.xlsx` 格式的 Excel 文件。 
 * --- 
 * #### 缺点与改进建议 
 * 1. **性能问题**： 
 * - 在写入大量数据时，频繁调用 `sheet.createRow()` 和 `row.createCell()` 可能会影响性能。 
 * - 建议使用 `Row` 和 `Cell` 的缓存机制以优化性能。 
 * 2. **日志记录**： 
 * - 日志信息较为冗长且重复，可以简化部分日志内容，避免过多堆栈信息干扰开发人员。 
 * 3. **代码重复**： 
 * - `exportExcel` 方法中存在较多重复逻辑，可以将其抽取为通用方法以减少冗余。 
 * 4. **异常处理**： 
 * - 在某些情况下（如文件关闭失败），异常处理仅记录日志而未采取进一步措施，可能需要更明确的处理策略。 
 * --- 
 * #### 总结 
 * 该代码是一个功能完善的 Excel 处理工具类，适用于大多数 Excel 文件的操作场景。通过引入泛型、动态文件格式解析以及灵活的配置选项，它能够满足大部分业务需求。未来可以针对性能瓶颈和代码复用性进一步优化，提升代码质量和可维护性。 
**/
package com.cyberpeace.common.component;

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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * excel数据处理器 需要什么方法自己加
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
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
	 * 文件输入流
	 *
	 * @see FileInputStream
	 */
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
			throw new BusinessException(ErrorCode.FILE_READ_FAIL, filePath);
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
			log.error("excel fileInputStream close failed!  filePath =[{}],sheetName=[{}]",filePath,sheetName);
			throw new BusinessException(ErrorCode.FILE_READ_FAIL, filePath);
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
				cell.setCellType(CellType.STRING);
				return cell.getStringCellValue().trim();
			}

		}
		return cell.toString().trim();
	}

	/**
	 * 关闭 io
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
	 * @param excelHeader
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
				if (null != workbook) {
					workbook.close();
				}
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
	 * @return 文件流
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
		} finally {
			try {
				wb.close();
			} catch (IOException ignored) {}
		}

		return file;
	}

}
