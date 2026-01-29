package com.cyberpeace.cr.teamtrain.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

/**
 * 增强版PDF转DOCX转换器
 */
public class EnhancedPdfToDocxConverter {

    /**
     * 按页转换，保留分页信息
     */
    public boolean convertWithPagination(String pdfPath, String docxPath) throws IOException {
        try (PDDocument pdfDoc = PDDocument.load(new File(pdfPath));
             XWPFDocument wordDoc = new XWPFDocument()) {

            PDFTextStripper stripper = new PDFTextStripper();

            System.out.println("PDF页数: " + pdfDoc.getNumberOfPages());

            // 逐页处理
            for (int pageNum = 1; pageNum <= pdfDoc.getNumberOfPages(); pageNum++) {
                System.out.println("正在处理第 " + pageNum + " 页...");

                // 设置提取范围
                stripper.setStartPage(pageNum);
                stripper.setEndPage(pageNum);

                // 提取当前页文本
                String pageText = stripper.getText(pdfDoc);

                // 处理页面文本
                processPageText(pageText, wordDoc, pageNum);

                // 如果不是最后一页，添加分页符
                if (pageNum < pdfDoc.getNumberOfPages()) {
                    XWPFParagraph para = wordDoc.createParagraph();
                    XWPFRun run = para.createRun();
                    run.addBreak(BreakType.PAGE);
                }
            }

            // 保存文档
            try (FileOutputStream out = new FileOutputStream(docxPath)) {
                wordDoc.write(out);
            }

            System.out.println("转换完成，共 " + pdfDoc.getNumberOfPages() + " 页");
            return true;

        }
    }

    /**
     * 处理单页文本
     */
    private void processPageText(String pageText, XWPFDocument wordDoc, int pageNum) {
        // 按行分割
        String[] lines = pageText.split("\n");

        // 添加页码标记（可选）
        if (pageNum > 1) {
            addPageHeader(wordDoc, "第 " + pageNum + " 页");
        }

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                continue;
            }

            XWPFParagraph paragraph = wordDoc.createParagraph();
            XWPFRun run = paragraph.createRun();

            // 应用样式
            applyStyles(line, run, paragraph, i);

            // 设置文本
            run.setText(line);

            // 设置字体
            run.setFontFamily("宋体");
            run.setFontSize(getFontSize(line));

            // 段落间距
            paragraph.setSpacingAfter(80);
        }
    }

    /**
     * 添加页眉
     */
    private void addPageHeader(XWPFDocument wordDoc, String headerText) {
        XWPFParagraph header = wordDoc.createParagraph();
        XWPFRun run = header.createRun();
        run.setText(headerText);
        run.setFontSize(10);
        run.setColor("666666");
        header.setAlignment(ParagraphAlignment.RIGHT);
        header.setSpacingAfter(50);
    }

    /**
     * 应用样式
     */
    private void applyStyles(String text, XWPFRun run, XWPFParagraph paragraph, int lineIndex) {
        // 判断是否为大标题（第一行且较短）
        if (lineIndex == 0 && text.length() < 40) {
            run.setBold(true);
            run.setFontSize(18);
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            paragraph.setSpacingBefore(200);  // 页面上方间距
            paragraph.setSpacingAfter(300);   // 页面下方间距
            return;
        }

        // 判断是否为章节标题
        if (isChapterTitle(text)) {
            run.setBold(true);
            run.setFontSize(16);
            paragraph.setSpacingBefore(150);
            paragraph.setSpacingAfter(100);
            return;
        }

        // 判断是否为小节标题
        if (isSectionTitle(text)) {
            run.setBold(true);
            run.setFontSize(14);
            paragraph.setSpacingBefore(100);
            paragraph.setSpacingAfter(50);
            return;
        }

        // 普通段落
        paragraph.setFirstLineIndent(400);  // 首行缩进
    }

    /**
     * 获取字体大小
     */
    private int getFontSize(String text) {
        if (isChapterTitle(text)) return 16;
        if (isSectionTitle(text)) return 14;
        if (text.length() < 30 && text.matches(".*[A-Z].*")) return 12;  // 英文标题
        return 12;  // 默认大小
    }

    /**
     * 判断是否为章节标题
     */
    private boolean isChapterTitle(String text) {
        return text.matches("^第[一二三四五六七八九十]+章\\s+.*") ||
                text.matches("^[一二三四五六七八九十]+、.*") ||
                (text.length() < 50 && (text.endsWith("章") || text.contains("CHAPTER")));
    }

    /**
     * 判断是否为小节标题
     */
    private boolean isSectionTitle(String text) {
        return text.matches("^[0-9]+\\.[0-9]+\\s+.*") ||
                text.matches("^[0-9]+、.*") ||
                (text.length() < 100 &&
                        (text.endsWith("：") || text.endsWith(":")) &&
                        !text.contains("。"));
    }
}
