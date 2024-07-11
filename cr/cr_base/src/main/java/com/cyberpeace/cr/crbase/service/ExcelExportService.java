package com.cyberpeace.cr.crbase.service;

import com.spire.xls.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Service
public class ExcelExportService {

    public void exportDataToExcel(HttpServletResponse response, List<String[]> data, String[] headers) throws IOException {
        // 创建Workbook对象
        Workbook workbook = new Workbook();
        // 获取第一个工作表
        Worksheet sheet = workbook.getWorksheets().get(0);

        // 设置表头
        for (int i = 0; i < headers.length; i++) {
            sheet.getRange().get(1, i + 1).setText(headers[i]);
        }

        // 插入数据
        for (int row = 0; row < data.size(); row++) {
            for (int col = 0; col < data.get(row).length; col++) {
                sheet.getRange().get(row + 2, col + 1).setText(data.get(row)[col]);
            }
        }

        // 设置列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.setColumnWidth(i + 1, 20);
        }

        // 设置响应头
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String filename = "train_department_" + LocalDateTime.now().format(formatter) + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);

        // 写入输出流
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.saveToStream(outputStream, FileFormat.Version2013);
        outputStream.flush();
        outputStream.close();
    }
}