package com.cyberpeace.cr.teamtrain.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 优化版PDF转DOCX转换器
 * 针对训练报告PDF格式进行专门优化
 */
public class OptimizedPdfToDocxConverter {

    /**
     * 主转换方法
     */
    public boolean convert(String pdfPath, String docxPath) {
        try (PDDocument pdfDoc = PDDocument.load(new File(pdfPath));
             XWPFDocument wordDoc = new XWPFDocument()) {

            System.out.println("开始优化转换: " + pdfPath);

            // 提取完整文本
            PDFTextStripper stripper = new PDFTextStripper();
            String fullText = stripper.getText(pdfDoc);

            System.out.println("PDF文本长度: " + fullText.length() + " 字符");

            // 优化处理文本
            String cleanedText = cleanAndFormatText(fullText);

            // 转换为Word文档
            convertToWordDocument(cleanedText, wordDoc);

            // 保存文档
            try (FileOutputStream out = new FileOutputStream(docxPath)) {
                wordDoc.write(out);
            }

            System.out.println("转换完成: " + docxPath);
            return true;

        } catch (Exception e) {
            System.err.println("转换失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 清理和格式化文本
     */
    private String cleanAndFormatText(String text) {
        StringBuilder result = new StringBuilder();

        // 1. 按页分割
        String[] pages = text.split("===== Page \\d+ =====\\s*");

        for (int i = 1; i < pages.length; i++) { // 跳过第一个空的部分
            String page = pages[i].trim();
            if (page.isEmpty()) continue;

            // 添加页码标题
            result.append("【第 ").append(i).append(" 页】\n\n");

            // 处理页面内容
            String processedPage = processPageContent(page);
            result.append(processedPage).append("\n\n");

            // 添加分页标记
            if (i < pages.length - 1) {
                result.append("--- 分页 ---\n\n");
            }
        }

        return result.toString();
    }

    /**
     * 处理单页内容
     */
    private String processPageContent(String pageText) {
        StringBuilder processed = new StringBuilder();
        String[] lines = pageText.split("\n");

        for (String line : lines) {
            String processedLine = processLine(line.trim());
            if (!processedLine.isEmpty()) {
                processed.append(processedLine).append("\n");
            }
        }

        return processed.toString();
    }

    /**
     * 处理单行文本
     */
    private String processLine(String line) {
        if (line.isEmpty()) return "";

        // 1. 处理图片标记 - 转换为描述性文本
        if (line.startsWith("image[[")) {
            return processImageMarker(line);
        }

        // 2. 处理表格开始标记
        if (line.contains("<table>")) {
            return "[表格开始]";
        }

        // 3. 处理表格结束标记
        if (line.contains("</table>")) {
            return "[表格结束]";
        }

        // 4. 处理标题标记
        if (line.startsWith("# ")) {
            return "◆ " + line.substring(2).trim();
        }

        // 5. 清理多余空格
        line = line.replaceAll("\\s+", " ");

        // 6. 处理特殊模式
        if (line.matches(".*\\d{4}-\\s*\\d{2}-\\s*\\d{2}.*")) {
            // 日期行 - 添加标记
            return "📅 " + line;
        }

        if (line.matches(".*\\d+分总分：\\d+.*")) {
            // 得分行 - 突出显示
            return "⭐ " + line;
        }

        if (line.matches(".*学员\\d+.*\\(\\d+\\).*")) {
            // 学员信息行 - 加粗标记
            return "👤 " + line;
        }

        if (line.matches("\\[成功\\].*")) {
            // 成功记录
            return "✅ " + line;
        }

        if (line.contains("★★★★★")) {
            // 评分行
            return "🌟 " + line;
        }

        return line;
    }

    /**
     * 处理图片标记
     */
    private String processImageMarker(String imageLine) {
        // 解析坐标: image[[x1, y1, x2, y2]]
        Pattern pattern = Pattern.compile("image\\[\\[(\\d+),\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)\\]\\]");
        Matcher matcher = pattern.matcher(imageLine);

        if (matcher.find()) {
            int x1 = Integer.parseInt(matcher.group(1));
            int y1 = Integer.parseInt(matcher.group(2));
            int x2 = Integer.parseInt(matcher.group(3));
            int y2 = Integer.parseInt(matcher.group(4));

            int width = x2 - x1;
            int height = y2 - y1;

            // 根据坐标猜测图片类型
            String imageType = guessImageType(x1, y1, width, height);

            return "[图片: " + imageType + ", 位置: " + x1 + "," + y1 + ", 大小: " + width + "x" + height + "]";
        }

        return "[图片]";
    }

    /**
     * 猜测图片类型
     */
    private String guessImageType(int x, int y, int width, int height) {
        if (width == 37 && height == 12) { // 你的PDF中常见的小图片
            return "趋势图块";
        } else if (width > 100 && height > 100) {
            return "统计图表";
        } else if (width > 200) {
            return "数据图表";
        } else {
            return "图标";
        }
    }

    /**
     * 转换为Word文档
     */
    private void convertToWordDocument(String text, XWPFDocument wordDoc) {
        // 分割为段落
        String[] paragraphs = text.split("\n\n");

        for (String para : paragraphs) {
            if (para.trim().isEmpty()) continue;

            // 检查是否是特殊段落
            if (para.startsWith("【第")) {
                // 页码标题
                addPageTitle(para, wordDoc);
            } else if (para.contains("--- 分页 ---")) {
                // 分页符
                addPageBreak(wordDoc);
            } else if (para.contains("[表格开始]")) {
                // 开始处理表格区域
                processTableArea(paragraphs, wordDoc);
            } else {
                // 普通段落
                addParagraphWithFormatting(para, wordDoc);
            }
        }
    }

    /**
     * 添加页码标题
     */
    private void addPageTitle(String title, XWPFDocument wordDoc) {
        XWPFParagraph para = wordDoc.createParagraph();
        para.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = para.createRun();
        run.setText(title);
        run.setBold(true);
        run.setFontSize(14);
        run.setFontFamily("微软雅黑");
        run.setColor("2E74B5"); // 蓝色

        para.setSpacingBefore(100);
        para.setSpacingAfter(50);
    }

    /**
     * 处理表格区域
     */
    private void processTableArea(String[] allParagraphs, XWPFDocument wordDoc) {
        // 这里可以添加表格解析逻辑
        // 由于你的PDF中的表格是HTML格式，需要特殊处理

        // 暂时添加表格占位符
        XWPFParagraph para = wordDoc.createParagraph();
        XWPFRun run = para.createRun();
        run.setText("[表格内容 - 需要单独解析HTML表格]");
        run.setItalic(true);
        run.setColor("FF0000");
    }

    /**
     * 添加带格式的段落
     */
    private void addParagraphWithFormatting(String text, XWPFDocument wordDoc) {
        String[] lines = text.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            XWPFParagraph para = wordDoc.createParagraph();
            XWPFRun run = para.createRun();

            // 设置默认样式
            run.setFontFamily("宋体");
            run.setFontSize(12);

            // 根据内容类型设置格式
            if (line.startsWith("◆ ")) {
                // 标题
                run.setText(line.substring(2));
                run.setBold(true);
                run.setFontSize(16);
                run.setColor("1F4E79"); // 深蓝色
                para.setSpacingBefore(80);
                para.setSpacingAfter(40);

            } else if (line.startsWith("👤 ")) {
                // 学员信息
                run.setText(line.substring(2));
                run.setBold(true);
                run.setFontSize(14);
                run.setColor("C65911"); // 橙色
                para.setSpacingBefore(60);
                para.setSpacingAfter(30);

            } else if (line.startsWith("⭐ ")) {
                // 得分信息
                run.setText(line.substring(2));
                run.setBold(true);
                run.setFontSize(13);
                run.setColor("FF0000"); // 红色
                para.setAlignment(ParagraphAlignment.CENTER);
                para.setSpacingBefore(40);
                para.setSpacingAfter(40);

            } else if (line.startsWith("✅ ")) {
                // 成功记录
                run.setText(line.substring(2));
                run.setFontSize(11);
                run.setColor("00B050"); // 绿色
                para.setIndentationLeft(200); // 缩进
                para.setSpacingAfter(10);

            } else if (line.startsWith("🌟 ")) {
                // 评分
                run.setText(line.substring(2));
                run.setFontSize(11);
                run.setColor("7030A0"); // 紫色
                para.setSpacingAfter(15);

            } else if (line.startsWith("📅 ")) {
                // 日期
                run.setText(line.substring(2));
                run.setFontSize(10);
                run.setColor("7F7F7F"); // 灰色
                para.setSpacingAfter(10);

            } else if (line.contains("[图片:")) {
                // 图片描述
                run.setText(line);
                run.setFontSize(10);
                run.setItalic(true);
                run.setColor("A5A5A5"); // 浅灰色
                para.setAlignment(ParagraphAlignment.CENTER);
                para.setSpacingBefore(20);
                para.setSpacingAfter(20);

            } else if (line.startsWith("训练教员：") || line.startsWith("训练时间：") ||
                    line.startsWith("训练时长：") || line.startsWith("推荐时长：") ||
                    line.startsWith("类别：") || line.startsWith("难度：") ||
                    line.startsWith("训练技能：") || line.startsWith("前置技能：")) {
                // 基本信息
                run.setText(line);
                run.setFontSize(11);
                run.setBold(true);
                para.setSpacingAfter(8);

            } else if (line.contains("攻击趋势") || line.contains("人员得分") ||
                    line.contains("综合得分") || line.contains("训练统计") ||
                    line.contains("技能评分") || line.contains("动态事件") ||
                    line.contains("训练记录")) {
                // 章节标题
                run.setText(line);
                run.setBold(true);
                run.setFontSize(13);
                run.setUnderline(UnderlinePatterns.SINGLE);
                para.setSpacingBefore(50);
                para.setSpacingAfter(30);

            } else if (line.matches("阶段\\d+")) {
                // 阶段标题
                run.setText(line);
                run.setBold(true);
                run.setFontSize(12);
                run.setColor("FFC000"); // 黄色
                para.setIndentationLeft(100);
                para.setSpacingBefore(30);
                para.setSpacingAfter(15);

            } else {
                // 普通文本
                run.setText(line);
                para.setSpacingAfter(20);
            }
        }
    }

    /**
     * 添加分页符
     */
    private void addPageBreak(XWPFDocument wordDoc) {
        // 添加水平线
        XWPFParagraph linePara = wordDoc.createParagraph();
        XWPFRun lineRun = linePara.createRun();
        lineRun.setText("───────────────────── 分页 ─────────────────────");
        lineRun.setFontSize(9);
        lineRun.setColor("CCCCCC");
        linePara.setAlignment(ParagraphAlignment.CENTER);
        linePara.setSpacingBefore(30);
        linePara.setSpacingAfter(30);

        // 添加分页符
        XWPFParagraph breakPara = wordDoc.createParagraph();
        XWPFRun breakRun = breakPara.createRun();
        breakRun.addBreak(BreakType.PAGE);
    }

    /**
     * 批量转换方法
     */
    public void batchConvert(List<String> pdfFiles, String outputDir) {
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        int success = 0;
        for (String pdfFile : pdfFiles) {
            try {
                File file = new File(pdfFile);
                String outputName = file.getName()
                        .replace(".pdf", "_优化版.docx")
                        .replace(".PDF", "_优化版.docx");
                String outputPath = outputDir + File.separator + outputName;

                if (convert(pdfFile, outputPath)) {
                    success++;
                    System.out.println("✓ " + file.getName());
                } else {
                    System.out.println("✗ " + file.getName());
                }

            } catch (Exception e) {
                System.err.println("转换失败: " + pdfFile + " - " + e.getMessage());
            }
        }

        System.out.printf("\n批量转换完成: %d/%d 成功\n", success, pdfFiles.size());
    }
}
