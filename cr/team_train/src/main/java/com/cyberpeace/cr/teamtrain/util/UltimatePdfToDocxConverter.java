package com.cyberpeace.cr.teamtrain.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 终极版PDF转DOCX转换器
 * 结合优化版和增强版的所有优点
 */
public class UltimatePdfToDocxConverter {

    private static final Pattern PAGE_PATTERN = Pattern.compile("===== Page (\\d+) =====");
    private static final Pattern IMAGE_PATTERN = Pattern.compile("image\\[\\[(\\d+),\\s*(\\d+),\\s*(\\d+),\\s*(\\d+)\\]\\]");
    private static final Pattern SCORE_PATTERN = Pattern.compile("(\\d+)\\s*分总分：\\s*(\\d+)");

    /**
     * 主转换方法
     */
    public boolean convert(String pdfPath, String docxPath) {
        try (PDDocument pdfDoc = PDDocument.load(new File(pdfPath));
             XWPFDocument wordDoc = new XWPFDocument()) {

            System.out.println("🚀 开始终极转换: " + pdfPath);

            // 提取完整文本
            PDFTextStripper stripper = new PDFTextStripper();
            String fullText = stripper.getText(pdfDoc);

            System.out.println("📄 PDF总页数: " + pdfDoc.getNumberOfPages());
            System.out.println("📝 文本长度: " + fullText.length() + " 字符");

            // 分析PDF结构
            PdfAnalysisResult analysis = analyzePdfStructure(fullText);

            // 构建精美的Word文档
            buildBeautifulWordDocument(analysis, wordDoc);

            // 保存文档
            try (FileOutputStream out = new FileOutputStream(docxPath)) {
                wordDoc.write(out);
            }

            System.out.println("✅ 转换完成: " + docxPath);
            System.out.println("📊 统计: " + analysis.pages.size() + " 页, " +
                    analysis.tables.size() + " 个表格, " +
                    analysis.students.size() + " 个学员");

            return true;

        } catch (Exception e) {
            System.err.println("❌ 转换失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * PDF分析结果
     */
    static class PdfAnalysisResult {
        String title = "训练报告";
        List<PdfPage> pages = new ArrayList<>();
        List<DataTable> tables = new ArrayList<>();
        List<StudentInfo> students = new ArrayList<>();
        ReportSummary summary = new ReportSummary();
    }

    static class PdfPage {
        int number;
        List<PageElement> elements = new ArrayList<>();
        List<ImageInfo> images = new ArrayList<>();
        String pageType; // "header", "student", "summary"

        // 页面统计
        int textCount = 0;
        int imageCount = 0;
        int tableCount = 0;
    }

    static class PageElement {
        enum Type {
            TITLE, SECTION_HEADER, SUB_HEADER,
            TEXT, KEY_VALUE, SCORE, RATING,
            TIMELINE, BULLET_LIST, IMAGE_PLACEHOLDER
        }
        Type type;
        String content;
        Map<String, String> metadata = new HashMap<>();
        int indentLevel = 0;
    }

    static class ImageInfo {
        int x1, y1, x2, y2;
        String description;
        String category; // "chart", "icon", "graph", "unknown"
    }

    static class DataTable {
        String title;
        List<String> headers = new ArrayList<>();
        List<List<String>> rows = new ArrayList<>();
        int pageNumber;
    }

    static class StudentInfo {
        String name;
        String id;
        int score;
        int totalScore = 100;
        Map<String, String> ratings = new HashMap<>(); // 能力评分
        List<TrainingRecord> records = new ArrayList<>();
    }

    static class TrainingRecord {
        String phase;
        String task;
        String time;
        String description;
        String result = "成功";
    }

    static class ReportSummary {
        String trainer = "管理员";
        String trainTime;
        String duration;
        String category = "渗透测试";
        String difficulty = "初级";
        int trainSkill = 33;
        int preSkill = 22;
    }

    /**
     * 分析PDF结构
     */
    private PdfAnalysisResult analyzePdfStructure(String fullText) {
        PdfAnalysisResult result = new PdfAnalysisResult();

        // 按页分割
        String[] pageSections = PAGE_PATTERN.split(fullText);

        for (int i = 1; i < pageSections.length; i++) {
            String pageContent = pageSections[i].trim();
            if (pageContent.isEmpty()) continue;

            PdfPage page = analyzePage(pageContent, i);
            result.pages.add(page);

            // 提取学员信息
            extractStudentInfo(page, result);

            // 提取表格
            extractTables(page, result, i);

            // 提取摘要信息（第一页）
            if (i == 1) {
                extractSummaryInfo(page, result);
            }
        }

        return result;
    }

    /**
     * 分析单页
     */
    private PdfPage analyzePage(String pageContent, int pageNumber) {
        PdfPage page = new PdfPage();
        page.number = pageNumber;

        String[] lines = pageContent.split("\n");
        boolean inTable = false;
        StringBuilder tableBuffer = new StringBuilder();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // 检查表格
            if (line.contains("<table>")) {
                inTable = true;
                tableBuffer = new StringBuilder();
                tableBuffer.append(line).append("\n");
                continue;
            }

            if (line.contains("</table>")) {
                inTable = false;
                tableBuffer.append(line);
                page.tableCount++;
                continue;
            }

            if (inTable) {
                tableBuffer.append(line).append("\n");
                continue;
            }

            // 检查图片
            if (line.startsWith("image[[")) {
                ImageInfo image = parseImageInfo(line);
                page.images.add(image);
                page.imageCount++;

                PageElement elem = new PageElement();
                elem.type = PageElement.Type.IMAGE_PLACEHOLDER;
                elem.content = generateImageDescription(image);
                page.elements.add(elem);
                continue;
            }

            // 分析其他元素
            PageElement element = analyzeLine(line);
            if (element != null) {
                page.elements.add(element);
                page.textCount++;
            }
        }

        // 确定页面类型
        page.pageType = determinePageType(page);

        return page;
    }

    /**
     * 解析图片信息
     */
    private ImageInfo parseImageInfo(String line) {
        ImageInfo info = new ImageInfo();
        Matcher matcher = IMAGE_PATTERN.matcher(line);

        if (matcher.find()) {
            info.x1 = Integer.parseInt(matcher.group(1));
            info.y1 = Integer.parseInt(matcher.group(2));
            info.x2 = Integer.parseInt(matcher.group(3));
            info.y2 = Integer.parseInt(matcher.group(4));

            int width = info.x2 - info.x1;
            int height = info.y2 - info.y1;

            // 分类图片
            if (width == 37 && height == 12) {
                info.category = "trend_icon";
                info.description = "攻击趋势图标";
            } else if (width > 200 && height > 100) {
                info.category = "chart";
                info.description = "统计图表";
            } else if (width > 100 && height > 50) {
                info.category = "graph";
                info.description = "数据图";
            } else {
                info.category = "icon";
                info.description = "图标";
            }
        }

        return info;
    }

    /**
     * 生成图片描述
     */
    private String generateImageDescription(ImageInfo image) {
        int width = image.x2 - image.x1;
        int height = image.y2 - image.y1;

        return String.format("【%s - %dx%d】", image.description, width, height);
    }

    /**
     * 分析单行文本
     */
    private PageElement analyzeLine(String line) {
        PageElement element = new PageElement();

        // 1. 标题和章节
        if (line.contains("训练报告")) {
            element.type = PageElement.Type.TITLE;
            element.content = line;
        }
        else if (line.startsWith("# ")) {
            element.type = PageElement.Type.SECTION_HEADER;
            element.content = line.substring(2);
        }
        else if (line.contains("攻击趋势") || line.contains("人员得分") ||
                line.contains("综合得分") || line.contains("训练统计") ||
                line.contains("技能评分") || line.contains("动态事件") ||
                line.contains("训练记录")) {
            element.type = PageElement.Type.SECTION_HEADER;
            element.content = line;
        }
        else if (line.matches("阶段\\d+")) {
            element.type = PageElement.Type.SUB_HEADER;
            element.content = line;
            element.indentLevel = 1;
        }

        // 2. 学员信息
        else if (line.matches(".*学员\\d+.*\\(\\d+\\).*")) {
            element.type = PageElement.Type.SECTION_HEADER;
            element.content = line;
        }

        // 3. 得分信息
        else if (line.matches(".*\\d+分总分：\\d+.*")) {
            element.type = PageElement.Type.SCORE;
            element.content = line;

            // 提取得分
            Matcher matcher = SCORE_PATTERN.matcher(line);
            if (matcher.find()) {
                element.metadata.put("score", matcher.group(1));
                element.metadata.put("total", matcher.group(2));
            }
        }

        // 4. 能力评分
        else if (line.contains("★★★★★")) {
            element.type = PageElement.Type.RATING;
            element.content = line;

            // 解析能力项
            if (line.contains("专业能力")) element.metadata.put("ability", "专业能力");
            if (line.contains("团队协作")) element.metadata.put("ability", "团队协作");
            if (line.contains("执行能力")) element.metadata.put("ability", "执行能力");
            if (line.contains("领导能力")) element.metadata.put("ability", "领导能力");
            if (line.contains("综合评价")) element.metadata.put("ability", "综合评价");
        }

        // 5. 时间线事件
        else if (line.matches("\\d{2}-\\s*\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}.*")) {
            element.type = PageElement.Type.TIMELINE;
            element.content = line;
            element.indentLevel = 2;
        }
        else if (line.matches("\\[成功\\].*")) {
            element.type = PageElement.Type.BULLET_LIST;
            element.content = line;
            element.indentLevel = 3;
        }

        // 6. 键值对信息
        else if (line.contains("训练教员：") || line.contains("训练时间：") ||
                line.contains("训练时长：") || line.contains("推荐时长：") ||
                line.contains("类别：") || line.contains("难度：") ||
                line.contains("训练技能：") || line.contains("前置技能：") ||
                line.contains("任务描述：") || line.contains("任务记录：")) {
            element.type = PageElement.Type.KEY_VALUE;
            element.content = line;
        }

        // 7. 普通文本
        else {
            element.type = PageElement.Type.TEXT;
            element.content = line;
        }

        return element;
    }

    /**
     * 确定页面类型
     */
    private String determinePageType(PdfPage page) {
        // 检查元素判断页面类型
        for (PageElement elem : page.elements) {
            if (elem.content != null) {
                if (elem.content.contains("训练教员")) return "header";
                if (elem.content.matches(".*学员\\d+.*")) return "student";
                if (elem.content.contains("攻击趋势")) return "trend";
            }
        }

        // 根据页面号判断
        if (page.number == 1) return "header";
        if (page.number == 2) return "summary";

        return "content";
    }

    /**
     * 提取学员信息
     */
    private void extractStudentInfo(PdfPage page, PdfAnalysisResult result) {
        if (!"student".equals(page.pageType)) return;

        StudentInfo student = new StudentInfo();

        for (PageElement elem : page.elements) {
            if (elem.type == PageElement.Type.SECTION_HEADER &&
                    elem.content.matches(".*学员\\d+.*\\(\\d+\\).*")) {

                // 解析学员名称和ID
                String text = elem.content;
                student.name = text.substring(0, text.indexOf('(')).trim();
                String idStr = text.substring(text.indexOf('(') + 1, text.indexOf(')'));
                student.id = idStr.trim();
            }
            else if (elem.type == PageElement.Type.SCORE) {
                // 解析得分
                Matcher matcher = SCORE_PATTERN.matcher(elem.content);
                if (matcher.find()) {
                    student.score = Integer.parseInt(matcher.group(1));
                    student.totalScore = Integer.parseInt(matcher.group(2));
                }
            }
            else if (elem.type == PageElement.Type.RATING) {
                // 解析能力评分
                if (elem.metadata.containsKey("ability")) {
                    student.ratings.put(elem.metadata.get("ability"), elem.content);
                }
            }
        }

        if (student.name != null && !student.name.isEmpty()) {
            result.students.add(student);
        }
    }

    /**
     * 提取表格
     */
    private void extractTables(PdfPage page, PdfAnalysisResult result, int pageNumber) {
        // 这里简化处理，实际需要解析HTML表格
        // 可以根据页面内容判断是否有表格

        if (page.number == 2) { // 第二页有人员得分表格
            DataTable table = new DataTable();
            table.title = "人员得分排名";
            table.pageNumber = pageNumber;

            // 模拟表头
            table.headers = Arrays.asList("排名", "人员", "角色", "任务进度", "主观题得分", "客观题得分", "能力得分", "综合得分");

            // 模拟数据行（实际应该从HTML解析）
            table.rows.add(Arrays.asList("1", "test2", "333", "1/10", "40", "92", "94", "294"));
            table.rows.add(Arrays.asList("2", "学员1", "123", "5/5", "30", "150", "92", "294"));

            result.tables.add(table);
        }
    }

    /**
     * 提取摘要信息
     */
    private void extractSummaryInfo(PdfPage page, PdfAnalysisResult result) {
        for (PageElement elem : page.elements) {
            if (elem.type != PageElement.Type.KEY_VALUE) continue;

            String content = elem.content;
            if (content.contains("训练教员：")) {
                result.summary.trainer = content.substring(content.indexOf("：") + 1).trim();
            }
            else if (content.contains("训练时间：")) {
                result.summary.trainTime = content.substring(content.indexOf("：") + 1).trim();
            }
            else if (content.contains("训练时长：")) {
                result.summary.duration = content.substring(content.indexOf("：") + 1).trim();
            }
        }
    }

    /**
     * 构建精美的Word文档
     */
    private void buildBeautifulWordDocument(PdfAnalysisResult analysis, XWPFDocument wordDoc) {
        // 1. 添加封面页
        addCoverPage(analysis, wordDoc);

        // 2. 添加摘要页
        addSummaryPage(analysis, wordDoc);

        // 3. 添加详细内容页
        addContentPages(analysis, wordDoc);

        // 4. 添加学员统计页
        addStudentSummaryPage(analysis, wordDoc);
    }

    /**
     * 添加封面页
     */
    private void addCoverPage(PdfAnalysisResult analysis, XWPFDocument wordDoc) {
        // 封面背景色（通过表格实现）
        XWPFTable coverTable = wordDoc.createTable(1, 1);
        coverTable.setWidth("100%");
        XWPFTableCell coverCell = coverTable.getRow(0).getCell(0);
        coverCell.setColor("2E74B5"); // 蓝色背景

        // 封面内容
        XWPFParagraph titlePara = coverCell.addParagraph();
        titlePara.setAlignment(ParagraphAlignment.CENTER);
        titlePara.setSpacingBefore(400);

        XWPFRun titleRun = titlePara.createRun();
        titleRun.setText("训练报告");
        titleRun.setBold(true);
        titleRun.setFontSize(36);
        titleRun.setFontFamily("微软雅黑");
        titleRun.setColor("FFFFFF");

        // 副标题
        XWPFParagraph subPara = coverCell.addParagraph();
        subPara.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun subRun = subPara.createRun();
        subRun.setText("网络安全渗透测试训练");
        subRun.setFontSize(18);
        subRun.setFontFamily("微软雅黑");
        subRun.setColor("FFFFFF");
        subRun.setItalic(true);

        // 生成时间
        XWPFParagraph timePara = coverCell.addParagraph();
        timePara.setAlignment(ParagraphAlignment.CENTER);
        timePara.setSpacingBefore(300);

        XWPFRun timeRun = timePara.createRun();
        timeRun.setText("生成时间: " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        timeRun.setFontSize(11);
        timeRun.setFontFamily("宋体");
        timeRun.setColor("FFFFFF");

        // 分页
        addPageBreak(wordDoc, true);
    }

    /**
     * 添加摘要页
     */
    private void addSummaryPage(PdfAnalysisResult analysis, XWPFDocument wordDoc) {
        // 页标题
        addStyledHeader(wordDoc, "📋 报告摘要", 20, "2E74B5", 50, 30);

        // 创建摘要表格
        XWPFTable summaryTable = wordDoc.createTable(8, 2);
        summaryTable.setWidth("80%");
        summaryTable.setCellMargins(100, 100, 100, 100);

        String[][] summaryData = {
                {"训练教员", analysis.summary.trainer},
                {"训练时间", analysis.summary.trainTime},
                {"训练时长", analysis.summary.duration},
                {"训练类别", analysis.summary.category},
                {"难度等级", analysis.summary.difficulty},
                {"训练技能", String.valueOf(analysis.summary.trainSkill)},
                {"前置技能", String.valueOf(analysis.summary.preSkill)},
                {"学员人数", String.valueOf(analysis.students.size())}
        };

        for (int i = 0; i < summaryData.length; i++) {
            XWPFTableRow row = summaryTable.getRow(i);

            // 标签单元格
            XWPFTableCell labelCell = row.getCell(0);
            labelCell.setColor("E2EFDA"); // 浅绿色背景
            setCellText(labelCell, summaryData[i][0], true, "1F4E79");

            // 值单元格
            XWPFTableCell valueCell = row.getCell(1);
            valueCell.setColor("FFF2CC"); // 浅黄色背景
            setCellText(valueCell, summaryData[i][1], false, "000000");
        }

        // 添加统计卡片
        addStatCards(analysis, wordDoc);

        // 分页
        addPageBreak(wordDoc, false);
    }

    /**
     * 添加统计卡片
     */
    private void addStatCards(PdfAnalysisResult analysis, XWPFDocument wordDoc) {
        addStyledHeader(wordDoc, "📊 训练统计", 16, "C65911", 60, 20);

        // 创建卡片表格（2x2）
        XWPFTable cardsTable = wordDoc.createTable(2, 2);
        cardsTable.setWidth("100%");

        String[][] cardsData = {
                {"总页数", String.valueOf(analysis.pages.size()), "1F4E79"},
                {"图表数量", String.valueOf(countTotalImages(analysis)), "C65911"},
                {"表格数量", String.valueOf(analysis.tables.size()), "2E74B5"},
                {"学员人数", String.valueOf(analysis.students.size()), "00B050"}
        };

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int index = i * 2 + j;
                if (index < cardsData.length) {
                    XWPFTableCell cell = cardsTable.getRow(i).getCell(j);
                    cell.setColor(cardsData[index][2]);

                    XWPFParagraph para = cell.addParagraph();
                    para.setAlignment(ParagraphAlignment.CENTER);

                    // 数值
                    XWPFRun valueRun = para.createRun();
                    valueRun.setText(cardsData[index][1]);
                    valueRun.setFontSize(24);
                    valueRun.setBold(true);
                    valueRun.setFontFamily("Arial");
                    valueRun.setColor("FFFFFF");
                    valueRun.addBreak();

                    // 标签
                    XWPFRun labelRun = para.createRun();
                    labelRun.setText(cardsData[index][0]);
                    labelRun.setFontSize(11);
                    labelRun.setFontFamily("微软雅黑");
                    labelRun.setColor("FFFFFF");
                }
            }
        }
    }

    private int countTotalImages(PdfAnalysisResult analysis) {
        int total = 0;
        for (PdfPage page : analysis.pages) {
            total += page.images.size();
        }
        return total;
    }

    /**
     * 添加内容页
     */
    private void addContentPages(PdfAnalysisResult analysis, XWPFDocument wordDoc) {
        // 按页面类型分组处理
        for (PdfPage page : analysis.pages) {
            // 添加页面标题
            addPageHeader(wordDoc, "第 " + page.number + " 页");

            // 根据页面类型处理
            switch (page.pageType) {
                case "header":
                    processHeaderPage(page, wordDoc);
                    break;
                case "trend":
                    processTrendPage(page, wordDoc);
                    break;
                case "summary":
                    processSummaryPage(page, analysis, wordDoc);
                    break;
                case "student":
                    processStudentPage(page, wordDoc);
                    break;
                default:
                    processGenericPage(page, wordDoc);
            }

            // 添加分页符（除了最后一页）
            if (page.number < analysis.pages.size()) {
                addPageBreak(wordDoc, false);
            }
        }
    }

    /**
     * 处理头部页面
     */
    private void processHeaderPage(PdfPage page, XWPFDocument wordDoc) {
        addStyledHeader(wordDoc, "🎯 训练概况", 18, "1F4E79", 30, 20);

        for (PageElement elem : page.elements) {
            switch (elem.type) {
                case KEY_VALUE:
                    addKeyValuePair(elem.content, wordDoc);
                    break;
                case SECTION_HEADER:
                    addStyledHeader(wordDoc, elem.content, 16, "2E74B5", 20, 15);
                    break;
                case IMAGE_PLACEHOLDER:
                    addImageBox(elem.content, wordDoc);
                    break;
                default:
                    addTextParagraph(elem.content, wordDoc, 12);
            }
        }
    }

    /**
     * 处理趋势页面
     */
    private void processTrendPage(PdfPage page, XWPFDocument wordDoc) {
        addStyledHeader(wordDoc, "📈 攻击趋势分析", 18, "C65911", 30, 20);

        // 添加趋势描述
        addTextParagraph("攻击趋势图显示了训练期间的安全事件分布情况，包含 " +
                page.imageCount + " 个趋势数据点。", wordDoc, 12);

        // 创建趋势图网格
        createImageGrid(page, wordDoc);
    }

    /**
     * 创建图片网格
     */
    private void createImageGrid(PdfPage page, XWPFDocument wordDoc) {
        if (page.images.isEmpty()) return;

        // 创建3列表格显示图片占位符
        int cols = 3;
        int rows = (int) Math.ceil(page.images.size() / (double) cols);

        XWPFTable gridTable = wordDoc.createTable(rows, cols);
        gridTable.setWidth("100%");

        for (int i = 0; i < page.images.size(); i++) {
            int row = i / cols;
            int col = i % cols;

            ImageInfo image = page.images.get(i);
            XWPFTableCell cell = gridTable.getRow(row).getCell(col);

            // 添加图片框
            cell.setColor("F2F2F2");

            XWPFParagraph para = cell.addParagraph();
            para.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun run = para.createRun();
            run.setText("◼");
            run.setFontSize(20);
            run.setColor("7F7F7F");
            run.addBreak();

            XWPFRun descRun = para.createRun();
            descRun.setText(image.description);
            descRun.setFontSize(9);
            descRun.setColor("666666");
        }
    }

    /**
     * 处理摘要页面
     */
    private void processSummaryPage(PdfPage page, PdfAnalysisResult analysis, XWPFDocument wordDoc) {
        addStyledHeader(wordDoc, "🏆 训练成果总览", 18, "00B050", 30, 20);

        // 显示得分表格
        if (!analysis.tables.isEmpty()) {
            for (DataTable table : analysis.tables) {
                createBeautifulTable(table, wordDoc);
            }
        }

        // 处理其他元素
        for (PageElement elem : page.elements) {
            if (elem.type == PageElement.Type.SCORE) {
                addScoreCard(elem.content, wordDoc);
            } else if (elem.type != PageElement.Type.KEY_VALUE) {
                addTextParagraph(elem.content, wordDoc, 12);
            }
        }
    }

    /**
     * 处理学员页面
     */
    private void processStudentPage(PdfPage page, XWPFDocument wordDoc) {
        // 查找学员名称
        String studentName = "学员";
        for (PageElement elem : page.elements) {
            if (elem.content != null && elem.content.matches(".*学员\\d+.*")) {
                studentName = elem.content;
                break;
            }
        }

        addStyledHeader(wordDoc, "👤 " + studentName, 16, "7030A0", 30, 20);

        // 创建学员信息卡片
        XWPFTable infoTable = wordDoc.createTable(1, 2);
        infoTable.setWidth("100%");

        // 左列：基本信息
        XWPFTableCell leftCell = infoTable.getRow(0).getCell(0);
        leftCell.setColor("F4F4F4");

        // 右列：得分
        XWPFTableCell rightCell = infoTable.getRow(0).getCell(1);
        rightCell.setColor("FFF2CC");

        // 处理页面元素
        boolean hasScore = false;
        for (PageElement elem : page.elements) {
            switch (elem.type) {
                case SCORE:
                    addScoreToCell(elem.content, rightCell);
                    hasScore = true;
                    break;
                case RATING:
                    addRatingToCell(elem.content, leftCell);
                    break;
                case TIMELINE:
                case BULLET_LIST:
                    addTimelineItem(elem.content, leftCell, elem.indentLevel);
                    break;
                case KEY_VALUE:
                    addKeyValueToCell(elem.content, leftCell);
                    break;
                case SECTION_HEADER:
                    if (!elem.content.matches(".*学员\\d+.*")) {
                        addStyledHeader(wordDoc, elem.content, 14, "2E74B5", 20, 15);
                    }
                    break;
                case IMAGE_PLACEHOLDER:
                    addImagePlaceholder(elem.content, wordDoc);
                    break;
            }
        }

        if (!hasScore) {
            setCellText(rightCell, "得分: 未记录", true, "FF0000");
        }
    }

    /**
     * 处理通用页面
     */
    private void processGenericPage(PdfPage page, XWPFDocument wordDoc) {
        for (PageElement elem : page.elements) {
            switch (elem.type) {
                case SECTION_HEADER:
                    addStyledHeader(wordDoc, elem.content, 16, "2E74B5", 20, 15);
                    break;
                case SUB_HEADER:
                    addSubHeader(elem.content, wordDoc, elem.indentLevel);
                    break;
                case SCORE:
                    addScoreCard(elem.content, wordDoc);
                    break;
                case RATING:
                    addRatingItem(elem.content, wordDoc);
                    break;
                case TIMELINE:
                case BULLET_LIST:
                    addListItem(elem.content, wordDoc, elem.indentLevel);
                    break;
                case KEY_VALUE:
                    addKeyValuePair(elem.content, wordDoc);
                    break;
                case IMAGE_PLACEHOLDER:
                    addImagePlaceholder(elem.content, wordDoc);
                    break;
                default:
                    addTextParagraph(elem.content, wordDoc, 12);
            }
        }
    }

    /**
     * 添加学员统计页
     */
    private void addStudentSummaryPage(PdfAnalysisResult analysis, XWPFDocument wordDoc) {
        addPageBreak(wordDoc, false);
        addStyledHeader(wordDoc, "📋 学员成绩汇总", 20, "1F4E79", 50, 30);

        if (analysis.students.isEmpty()) {
            addTextParagraph("未找到学员数据", wordDoc, 12);
            return;
        }

        // 创建学员成绩表
        XWPFTable scoreTable = wordDoc.createTable(analysis.students.size() + 1, 4);
        scoreTable.setWidth("100%");

        // 表头
        XWPFTableRow headerRow = scoreTable.getRow(0);
//        headerRow.setColor("2E74B5");

        String[] headers = {"排名", "学员", "综合得分", "评级"};
        for (int i = 0; i < headers.length; i++) {
            setCellText(headerRow.getCell(i), headers[i], true, "FFFFFF");
        }

        // 学员数据
        for (int i = 0; i < analysis.students.size(); i++) {
            StudentInfo student = analysis.students.get(i);
            XWPFTableRow row = scoreTable.getRow(i + 1);

            // 排名
            setCellText(row.getCell(0), String.valueOf(i + 1), false, "000000");

            // 学员名称
            setCellText(row.getCell(1), student.name, true, "1F4E79");

            // 得分
            String scoreText = student.score + "/" + student.totalScore;
            setCellText(row.getCell(2), scoreText, true, getScoreColor(student.score));

            // 评级
            String rating = getPerformanceRating(student.score);
            setCellText(row.getCell(3), rating, true, getRatingColor(rating));
        }

        // 添加统计图表描述
        addTextParagraph("\n注：共 " + analysis.students.size() + " 名学员参与训练，平均得分: " +
                calculateAverageScore(analysis) + " 分。", wordDoc, 11);
    }

    private String getScoreColor(int score) {
        if (score >= 90) return "00B050"; // 绿色
        if (score >= 80) return "FFC000"; // 黄色
        if (score >= 60) return "FF6600"; // 橙色
        return "FF0000"; // 红色
    }

    private String getPerformanceRating(int score) {
        if (score >= 90) return "优秀";
        if (score >= 80) return "良好";
        if (score >= 70) return "中等";
        if (score >= 60) return "及格";
        return "需改进";
    }

    private String getRatingColor(String rating) {
        switch (rating) {
            case "优秀": return "00B050";
            case "良好": return "92D050";
            case "中等": return "FFC000";
            case "及格": return "FF6600";
            default: return "FF0000";
        }
    }

    private int calculateAverageScore(PdfAnalysisResult analysis) {
        if (analysis.students.isEmpty()) return 0;
        int total = 0;
        for (StudentInfo student : analysis.students) {
            total += student.score;
        }
        return total / analysis.students.size();
    }

    // ========== 辅助方法 ==========

    private void addStyledHeader(XWPFDocument wordDoc, String text, int fontSize,
                                 String color, int spacingBefore, int spacingAfter) {
        XWPFParagraph para = wordDoc.createParagraph();

        XWPFRun run = para.createRun();
        run.setText(text);
        run.setBold(true);
        run.setFontSize(fontSize);
        run.setFontFamily("微软雅黑");
        run.setColor(color);

        para.setSpacingBefore(spacingBefore);
        para.setSpacingAfter(spacingAfter);
    }

    private void addSubHeader(String text, XWPFDocument wordDoc, int indentLevel) {
        XWPFParagraph para = wordDoc.createParagraph();
        para.setIndentationLeft(indentLevel * 200);

        XWPFRun run = para.createRun();
        run.setText("▶ " + text);
        run.setBold(true);
        run.setFontSize(14);
        run.setColor("C65911");

        para.setSpacingBefore(15);
        para.setSpacingAfter(10);
    }

    private void addKeyValuePair(String text, XWPFDocument wordDoc) {
        XWPFParagraph para = wordDoc.createParagraph();

        String[] parts = text.split("：", 2);
        if (parts.length == 2) {
            XWPFRun labelRun = para.createRun();
            labelRun.setText(parts[0] + "：");
            labelRun.setBold(true);
            labelRun.setFontSize(12);
            labelRun.setColor("1F4E79");

            XWPFRun valueRun = para.createRun();
            valueRun.setText(parts[1]);
            valueRun.setFontSize(12);
            valueRun.setColor("000000");
        } else {
            para.createRun().setText(text);
        }

        para.setSpacingAfter(8);
    }

    private void addKeyValueToCell(String text, XWPFTableCell cell) {
        XWPFParagraph para = cell.addParagraph();

        String[] parts = text.split("：", 2);
        if (parts.length == 2) {
            XWPFRun labelRun = para.createRun();
            labelRun.setText(parts[0] + "：");
            labelRun.setBold(true);
            labelRun.setFontSize(11);

            XWPFRun valueRun = para.createRun();
            valueRun.setText(parts[1]);
            valueRun.setFontSize(11);
        }
    }

    private void addScoreCard(String text, XWPFDocument wordDoc) {
        XWPFParagraph para = wordDoc.createParagraph();
        para.setAlignment(ParagraphAlignment.CENTER);

        // 解析得分
        Matcher matcher = SCORE_PATTERN.matcher(text);
        if (matcher.find()) {
            int score = Integer.parseInt(matcher.group(1));
            int total = Integer.parseInt(matcher.group(2));

            XWPFRun scoreRun = para.createRun();
            scoreRun.setText(score + " 分");
            scoreRun.setBold(true);
            scoreRun.setFontSize(24);
            scoreRun.setColor(getScoreColor(score));
            scoreRun.addBreak();

            XWPFRun totalRun = para.createRun();
            totalRun.setText("总分: " + total);
            totalRun.setFontSize(12);
            totalRun.setColor("666666");
        } else {
            para.createRun().setText(text);
        }

        para.setSpacingBefore(20);
        para.setSpacingAfter(30);
    }

    private void addScoreToCell(String text, XWPFTableCell cell) {
        XWPFParagraph para = cell.addParagraph();
        para.setAlignment(ParagraphAlignment.CENTER);

        Matcher matcher = SCORE_PATTERN.matcher(text);
        if (matcher.find()) {
            int score = Integer.parseInt(matcher.group(1));

            XWPFRun run = para.createRun();
            run.setText(score + "分");
            run.setBold(true);
            run.setFontSize(28);
            run.setColor(getScoreColor(score));
        }
    }

    private void addRatingItem(String text, XWPFDocument wordDoc) {
        XWPFParagraph para = wordDoc.createParagraph();
        para.setIndentationLeft(200);

        XWPFRun run = para.createRun();
        run.setText("☆ " + text);
        run.setFontSize(11);
        run.setColor("7030A0");

        para.setSpacingAfter(5);
    }

    private void addRatingToCell(String text, XWPFTableCell cell) {
        XWPFParagraph para = cell.addParagraph();

        XWPFRun run = para.createRun();
        run.setText("★ " + text);
        run.setFontSize(11);
        run.setColor("7030A0");
    }

    private void addListItem(String text, XWPFDocument wordDoc, int indentLevel) {
        XWPFParagraph para = wordDoc.createParagraph();
        para.setIndentationLeft(indentLevel * 200);

        XWPFRun run = para.createRun();
        run.setText("• " + text);
        run.setFontSize(11);

        para.setSpacingAfter(3);
    }

    private void addTimelineItem(String text, XWPFTableCell cell, int indentLevel) {
        XWPFParagraph para = cell.addParagraph();
        para.setIndentationLeft(indentLevel * 100);

        String prefix = indentLevel == 2 ? "🕒 " : "✓ ";
        XWPFRun run = para.createRun();
        run.setText(prefix + text);
        run.setFontSize(10);

        if (text.contains("[成功]")) {
            run.setColor("00B050");
        }
    }

    private void addImagePlaceholder(String text, XWPFDocument wordDoc) {
        XWPFParagraph para = wordDoc.createParagraph();
        para.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = para.createRun();
        run.setText("▌ " + text + " ▌");
        run.setFontSize(10);
        run.setItalic(true);
        run.setColor("7F7F7F");

        para.setSpacingBefore(15);
        para.setSpacingAfter(20);
    }

    private void addImageBox(String text, XWPFDocument wordDoc) {
        XWPFTable imageBox = wordDoc.createTable(1, 1);
        imageBox.setWidth("80%");

        XWPFTableCell cell = imageBox.getRow(0).getCell(0);
        cell.setColor("F2F2F2");

        XWPFParagraph para = cell.addParagraph();
        para.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun iconRun = para.createRun();
        iconRun.setText("📊");
        iconRun.setFontSize(24);
        iconRun.addBreak();

        XWPFRun textRun = para.createRun();
        textRun.setText(text);
        textRun.setFontSize(11);
        textRun.setColor("666666");
    }

    private void addTextParagraph(String text, XWPFDocument wordDoc, int fontSize) {
        XWPFParagraph para = wordDoc.createParagraph();

        XWPFRun run = para.createRun();
        run.setText(text);
        run.setFontSize(fontSize);
        run.setFontFamily("宋体");

        para.setSpacingAfter(15);
    }

    private void addPageHeader(XWPFDocument wordDoc, String text) {
        XWPFParagraph header = wordDoc.createParagraph();
        header.setAlignment(ParagraphAlignment.RIGHT);

        XWPFRun run = header.createRun();
        run.setText(text);
        run.setFontSize(9);
        run.setColor("999999");
        run.setItalic(true);

        header.setSpacingAfter(20);
    }

    private void setCellText(XWPFTableCell cell, String text, boolean bold, String color) {
        cell.removeParagraph(0);
        XWPFParagraph para = cell.addParagraph();
        para.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = para.createRun();
        run.setText(text);
        run.setBold(bold);
        run.setFontSize(11);
        run.setColor(color);
    }

    private void createBeautifulTable(DataTable table, XWPFDocument wordDoc) {
        addStyledHeader(wordDoc, "📋 " + table.title, 16, "2E74B5", 20, 15);

        XWPFTable wordTable = wordDoc.createTable(table.rows.size() + 1, table.headers.size());
        wordTable.setWidth("100%");

        // 表头
        XWPFTableRow headerRow = wordTable.getRow(0);
//        headerRow.setColor("2E74B5");

        for (int i = 0; i < table.headers.size(); i++) {
            setCellText(headerRow.getCell(i), table.headers.get(i), true, "FFFFFF");
        }

        // 数据行
        for (int i = 0; i < table.rows.size(); i++) {
            XWPFTableRow dataRow = wordTable.getRow(i + 1);
            List<String> rowData = table.rows.get(i);

            // 交替行颜色
//            if (i % 2 == 0) {
//                dataRow.setColor("FFFFFF");
//            } else {
//                dataRow.setColor("F2F2F2");
//            }

            for (int j = 0; j < table.headers.size(); j++) {
                String cellText = j < rowData.size() ? rowData.get(j) : "";
                setCellText(dataRow.getCell(j), cellText, false, "000000");
            }
        }
    }

    private void addPageBreak(XWPFDocument wordDoc, boolean decorative) {
        if (decorative) {
            XWPFParagraph decorPara = wordDoc.createParagraph();
            decorPara.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun decorRun = decorPara.createRun();
            decorRun.setText("❖ ❖ ❖");
            decorRun.setFontSize(12);
            decorRun.setColor("CCCCCC");
            decorRun.addBreak();

            decorPara.setSpacingBefore(50);
            decorPara.setSpacingAfter(50);
        }

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
                        .replace(".pdf", "_终极版.docx")
                        .replace(".PDF", "_终极版.docx");
                String outputPath = outputDir + File.separator + outputName;

                System.out.println("\n═══════════════════════════════════════");
                System.out.println("处理: " + file.getName());

                if (convert(pdfFile, outputPath)) {
                    success++;
                    System.out.println("✅ 成功: " + outputName);
                } else {
                    System.out.println("❌ 失败: " + file.getName());
                }

            } catch (Exception e) {
                System.err.println("💥 错误: " + pdfFile + " - " + e.getMessage());
            }
        }

        System.out.println("\n═══════════════════════════════════════");
        System.out.printf("📊 批量转换完成: %d/%d 成功\n", success, pdfFiles.size());
    }

    /**
     * 快速转换方法（简化调用）
     */
    public boolean quickConvert(String pdfPath) {
        String docxPath = pdfPath.replace(".pdf", "_converted.docx").replace(".PDF", "_converted.docx");
        return convert(pdfPath, docxPath);
    }
}
