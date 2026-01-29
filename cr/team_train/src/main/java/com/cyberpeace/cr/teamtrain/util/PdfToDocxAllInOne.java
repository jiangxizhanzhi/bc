package com.cyberpeace.cr.teamtrain.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 综合PDF转DOCX转换器（支持文本、图片、表格）
 */
public class PdfToDocxAllInOne {

    private static final float TABLE_ROW_TOLERANCE = 3.0f; // 表格行识别容差

    /**
     * 主转换方法
     */
    public boolean convert(String pdfPath, String docxPath, String imageDir) {
        try (PDDocument pdfDoc = PDDocument.load(new File(pdfPath));
             XWPFDocument wordDoc = new XWPFDocument()) {

            System.out.println("开始综合转换: " + pdfPath);

            // 创建图片保存目录
            File imageFolder = new File(imageDir);
            if (!imageFolder.exists()) {
                imageFolder.mkdirs();
            }

            // 逐页处理
            for (int pageNum = 0; pageNum < pdfDoc.getNumberOfPages(); pageNum++) {
                System.out.println("=== 处理第 " + (pageNum + 1) + " 页 ===");

                PDPage page = pdfDoc.getPage(pageNum);
                PageAnalysisResult result = analyzePage(pdfDoc, pageNum);

                // 处理本页内容
                processPageContent(result, wordDoc, imageDir, pageNum + 1);

                // 添加分页符
                if (pageNum < pdfDoc.getNumberOfPages() - 1) {
                    addPageBreak(wordDoc);
                }
            }

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
     * 页面分析结果
     */
    static class PageAnalysisResult {
        List<TextElement> textElements = new ArrayList<>();
        List<ImageElement> imageElements = new ArrayList<>();
        List<TableElement> tableElements = new ArrayList<>();
        List<ContentBlock> contentBlocks = new ArrayList<>(); // 文本块
    }

    static class TextElement {
        String text;
        float x;
        float y;
        float width;
        float height;
        float fontSize;
        String fontName;
        boolean isBold;

        @Override
        public String toString() {
            return String.format("Text['%s' (%.1f,%.1f) %.1fpt]",
                    text.length() > 20 ? text.substring(0, 20) + "..." : text,
                    x, y, fontSize);
        }
    }

    static class ImageElement {
        BufferedImage image;
        float x;
        float y;
        float width;
        float height;
        String imageName;

        @Override
        public String toString() {
            return String.format("Image[%s (%.1f,%.1f) %.1fx%.1f]",
                    imageName, x, y, width, height);
        }
    }

    static class TableElement {
        float startY;
        float endY;
        List<TableRow> rows = new ArrayList<>();

        @Override
        public String toString() {
            return String.format("Table[%.1f-%.1f, %d行]", startY, endY, rows.size());
        }
    }

    static class TableRow {
        float y;
        List<TableCell> cells = new ArrayList<>();
    }

    static class TableCell {
        String text;
        float x;
        float width;
    }

    static class ContentBlock {
        ContentType type;
        float startY;
        float endY;
        Object content; // TextElement列表或TableElement

        enum ContentType {
            PARAGRAPH, TABLE, IMAGE
        }
    }

    /**
     * 分析页面内容
     */
    private PageAnalysisResult analyzePage(PDDocument pdfDoc, int pageNum) throws IOException {
        PageAnalysisResult result = new PageAnalysisResult();

        // 1. 提取图片
        extractImages(pdfDoc, pageNum, result);

        // 2. 提取文本和位置信息
        extractTextWithPositions(pdfDoc, pageNum, result);

        // 3. 识别表格
        detectTables(result);

        // 4. 组织内容块
        organizeContentBlocks(result);

        return result;
    }

    /**
     * 提取图片
     */
    private void extractImages(PDDocument pdfDoc, int pageNum, PageAnalysisResult result) throws IOException {
        PDPage page = pdfDoc.getPage(pageNum);
        PDResources resources = page.getResources();

        int imageIndex = 0;
        for (org.apache.pdfbox.cos.COSName name : resources.getXObjectNames()) {
            org.apache.pdfbox.pdmodel.graphics.PDXObject xobject = resources.getXObject(name);

            if (xobject instanceof PDImageXObject) {
                PDImageXObject image = (PDImageXObject) xobject;

                ImageElement imgElement = new ImageElement();
                imgElement.image = image.getImage();
                imgElement.imageName = String.format("page%d_img%d.png", pageNum + 1, ++imageIndex);
                imgElement.width = image.getWidth();
                imgElement.height = image.getHeight();

                // 简化：PDFBox不直接提供图片位置，需要从图形状态获取
                // 这里先设置为估计位置
                imgElement.x = 100;
                imgElement.y = 100 * imageIndex;

                result.imageElements.add(imgElement);
                System.out.println("  找到图片: " + imgElement.imageName);
            }
        }
    }

    /**
     * 提取文本及其位置
     */
    private void extractTextWithPositions(PDDocument pdfDoc, int pageNum, PageAnalysisResult result) throws IOException {
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                for (TextPosition tp : textPositions) {
                    TextElement element = new TextElement();
                    element.text = tp.getUnicode();
                    element.x = tp.getX();
                    element.y = tp.getY();
                    element.width = tp.getWidth();
                    element.height = tp.getHeight();
                    element.fontSize = tp.getFontSize();
                    element.fontName = tp.getFont().getName();
                    element.isBold = isBoldFont(tp);

                    result.textElements.add(element);
                }
            }

            private boolean isBoldFont(TextPosition tp) {
                String fontName = tp.getFont().getName().toLowerCase();
                return fontName.contains("bold") || fontName.contains("black") ||
                        fontName.contains("粗") || fontName.contains("b") ||
                        fontName.contains("heavy");
            }
        };

        stripper.setSortByPosition(true);
        stripper.setStartPage(pageNum + 1);
        stripper.setEndPage(pageNum + 1);
        stripper.getText(pdfDoc);

        System.out.println("  提取到 " + result.textElements.size() + " 个文本元素");
    }

    /**
     * 识别表格
     */
    private void detectTables(PageAnalysisResult result) {
        // 按Y坐标分组（行）
        Map<Float, List<TextElement>> rows = new TreeMap<>(Collections.reverseOrder());

        for (TextElement element : result.textElements) {
            Float rowKey = findRowKey(rows.keySet(), element.y);
            if (rowKey == null) {
                rowKey = element.y;
                rows.put(rowKey, new ArrayList<>());
            }
            rows.get(rowKey).add(element);
        }

        // 识别表格区域
        List<TableCandidate> candidates = new ArrayList<>();
        TableCandidate currentCandidate = null;

        for (Map.Entry<Float, List<TextElement>> entry : rows.entrySet()) {
            float y = entry.getKey();
            List<TextElement> rowElements = entry.getValue();

            // 判断是否为表格行
            if (isTableRow(rowElements)) {
                if (currentCandidate == null) {
                    currentCandidate = new TableCandidate();
                    currentCandidate.startY = y;
                    currentCandidate.rows = new ArrayList<>();
                }
                currentCandidate.rows.add(rowElements);
                currentCandidate.endY = y;
            } else {
                // 非表格行，结束当前表格候选
                if (currentCandidate != null && currentCandidate.rows.size() >= 2) {
                    candidates.add(currentCandidate);
                }
                currentCandidate = null;
            }
        }

        // 处理最后一个表格
        if (currentCandidate != null && currentCandidate.rows.size() >= 2) {
            candidates.add(currentCandidate);
        }

        // 转换表格候选为表格元素
        for (TableCandidate candidate : candidates) {
            TableElement table = createTableFromRows(candidate.rows);
            if (table != null) {
                result.tableElements.add(table);
                System.out.println("  识别到表格: " + table.rows.size() + "行");
            }
        }
    }

    static class TableCandidate {
        float startY;
        float endY;
        List<List<TextElement>> rows;
    }

    /**
     * 查找行键
     */
    private Float findRowKey(Set<Float> rowKeys, float y) {
        for (Float key : rowKeys) {
            if (Math.abs(key - y) <= TABLE_ROW_TOLERANCE) {
                return key;
            }
        }
        return null;
    }

    /**
     * 判断是否为表格行
     */
    private boolean isTableRow(List<TextElement> elements) {
        if (elements.size() < 2) {
            return false;
        }

        // 按X坐标排序
        elements.sort(Comparator.comparingDouble(e -> e.x));

        // 检查列对齐
        int columnCount = elements.size();
        float[] gaps = new float[columnCount - 1];

        for (int i = 0; i < columnCount - 1; i++) {
            gaps[i] = elements.get(i + 1).x - (elements.get(i).x + elements.get(i).width);
        }

        // 检查是否有明显的列间隔
        int consistentGaps = 0;
        for (float gap : gaps) {
            if (gap > 5 && gap < 50) { // 合理的列间距
                consistentGaps++;
            }
        }

        // 如果大部分间隔一致，可能是表格
        return consistentGaps >= columnCount / 2;
    }

    /**
     * 从行创建表格
     */
    private TableElement createTableFromRows(List<List<TextElement>> rowGroups) {
        if (rowGroups.isEmpty()) {
            return null;
        }

        TableElement table = new TableElement();
        table.startY = rowGroups.get(0).get(0).y;
        table.endY = rowGroups.get(rowGroups.size() - 1).get(0).y;

        // 确定列数（取最大列数）
        int maxCols = 0;
        for (List<TextElement> row : rowGroups) {
            maxCols = Math.max(maxCols, row.size());
        }

        // 创建表格数据结构
        for (List<TextElement> rowGroup : rowGroups) {
            TableRow tableRow = new TableRow();
            tableRow.y = rowGroup.get(0).y;

            // 按X坐标排序
            rowGroup.sort(Comparator.comparingDouble(e -> e.x));

            // 创建单元格
            for (TextElement element : rowGroup) {
                TableCell cell = new TableCell();
                cell.text = element.text;
                cell.x = element.x;
                cell.width = element.width;
                tableRow.cells.add(cell);
            }

            // 补齐缺失的单元格
            while (tableRow.cells.size() < maxCols) {
                TableCell emptyCell = new TableCell();
                emptyCell.text = "";
                tableRow.cells.add(emptyCell);
            }

            table.rows.add(tableRow);
        }

        return table;
    }

    /**
     * 组织内容块
     */
    private void organizeContentBlocks(PageAnalysisResult result) {
        // 收集所有元素并按Y坐标排序
        List<PageItem> allItems = new ArrayList<>();

        // 添加文本元素
        for (TextElement text : result.textElements) {
            PageItem item = new PageItem();
            item.type = PageItem.Type.TEXT;
            item.y = text.y;
            item.content = text;
            allItems.add(item);
        }

        // 添加表格元素
        for (TableElement table : result.tableElements) {
            PageItem item = new PageItem();
            item.type = PageItem.Type.TABLE;
            item.y = table.startY;
            item.content = table;
            allItems.add(item);
        }

        // 添加图片元素
        for (ImageElement image : result.imageElements) {
            PageItem item = new PageItem();
            item.type = PageItem.Type.IMAGE;
            item.y = image.y;
            item.content = image;
            allItems.add(item);
        }

        // 按Y坐标排序（从上到下）
        allItems.sort((a, b) -> Float.compare(b.y, a.y));

        // 创建内容块
        ContentBlock currentBlock = null;

        for (PageItem item : allItems) {
            if (currentBlock == null) {
                currentBlock = new ContentBlock();
                currentBlock.type = item.type == PageItem.Type.TABLE ?
                        ContentBlock.ContentType.TABLE : ContentBlock.ContentType.PARAGRAPH;
                currentBlock.startY = item.y;
                currentBlock.content = new ArrayList<>();
            }

            // 检查是否应该开始新块
            float lastY = getLastY(currentBlock);
            if (Math.abs(item.y - lastY) > 20) { // 间隔太大，新块
                if (!((List)currentBlock.content).isEmpty()) {
                    result.contentBlocks.add(currentBlock);
                }
                currentBlock = new ContentBlock();
                currentBlock.type = item.type == PageItem.Type.TABLE ?
                        ContentBlock.ContentType.TABLE : ContentBlock.ContentType.PARAGRAPH;
                currentBlock.startY = item.y;
                currentBlock.content = new ArrayList<>();
            }

            ((List)currentBlock.content).add(item.content);
            currentBlock.endY = item.y;
        }

        // 添加最后一个块
        if (currentBlock != null && !((List)currentBlock.content).isEmpty()) {
            result.contentBlocks.add(currentBlock);
        }

        // 按Y排序块
        result.contentBlocks.sort((a, b) -> Float.compare(b.startY, a.startY));
    }

    private float getLastY(ContentBlock block) {
        if (block == null || ((List)block.content).isEmpty()) {
            return 0;
        }
        Object lastItem = ((List)block.content).get(((List)block.content).size() - 1);
        if (lastItem instanceof TextElement) {
            return ((TextElement) lastItem).y;
        } else if (lastItem instanceof TableElement) {
            return ((TableElement) lastItem).endY;
        } else if (lastItem instanceof ImageElement) {
            return ((ImageElement) lastItem).y;
        }
        return 0;
    }

    static class PageItem {
        enum Type { TEXT, TABLE, IMAGE }
        Type type;
        float y;
        Object content;
    }

    /**
     * 处理页面内容
     */
    private void processPageContent(PageAnalysisResult result, XWPFDocument wordDoc,
                                    String imageDir, int pageNum) throws Exception {

        System.out.println("  处理 " + result.contentBlocks.size() + " 个内容块");

        // 添加页眉
        addPageHeader(wordDoc, pageNum);

        // 处理每个内容块
        for (ContentBlock block : result.contentBlocks) {
            switch (block.type) {
                case PARAGRAPH:
                    processParagraphBlock(block, wordDoc);
                    break;
                case TABLE:
                    processTableBlock(block, wordDoc);
                    break;
                case IMAGE:
                    processImageBlock(block, wordDoc, imageDir, pageNum);
                    break;
            }
        }

        // 添加页脚
        addPageFooter(wordDoc, pageNum);
    }

    /**
     * 处理段落块
     */
    private void processParagraphBlock(ContentBlock block, XWPFDocument wordDoc) {
        List<Object> items = (List<Object>) block.content;
        List<TextElement> textElements = new ArrayList<>();

        for (Object item : items) {
            if (item instanceof TextElement) {
                textElements.add((TextElement) item);
            }
        }

        if (textElements.isEmpty()) {
            return;
        }

        // 按行分组
        Map<Float, StringBuilder> lines = new TreeMap<>(Collections.reverseOrder());

        for (TextElement element : textElements) {
            Float lineY = findLine(lines.keySet(), element.y);
            if (lineY == null) {
                lineY = element.y;
                lines.put(lineY, new StringBuilder());
            }
            lines.get(lineY).append(element.text);
        }

        // 创建段落
        for (StringBuilder lineText : lines.values()) {
            String text = lineText.toString().trim();
            if (!text.isEmpty()) {
                addParagraphToWord(text, wordDoc);
            }
        }
    }

    private Float findLine(Set<Float> lines, float y) {
        for (Float lineY : lines) {
            if (Math.abs(lineY - y) <= 3.0f) {
                return lineY;
            }
        }
        return null;
    }

    /**
     * 处理表格块
     */
    private void processTableBlock(ContentBlock block, XWPFDocument wordDoc) {
        List<Object> items = (List<Object>) block.content;

        for (Object item : items) {
            if (item instanceof TableElement) {
                TableElement table = (TableElement) item;
                createWordTable(table, wordDoc);
            }
        }
    }

    /**
     * 处理图片块
     */
    private void processImageBlock(ContentBlock block, XWPFDocument wordDoc,
                                   String imageDir, int pageNum) throws Exception {
        List<Object> items = (List<Object>) block.content;
        int imgIndex = 1;

        for (Object item : items) {
            if (item instanceof ImageElement) {
                ImageElement image = (ImageElement) item;

                // 保存图片
                String imagePath = imageDir + File.separator + image.imageName;
                saveImage(image.image, imagePath);

                // 插入到Word
                insertImageIntoWord(imagePath, image, wordDoc);

                imgIndex++;
            }
        }
    }

    /**
     * 创建Word表格
     */
    private void createWordTable(TableElement pdfTable, XWPFDocument wordDoc) {
        if (pdfTable.rows.isEmpty()) {
            return;
        }

        // 确定列数
        int maxCols = 0;
        for (TableRow row : pdfTable.rows) {
            maxCols = Math.max(maxCols, row.cells.size());
        }

        // 创建Word表格
        XWPFTable wordTable = wordDoc.createTable(pdfTable.rows.size(), maxCols);

        for (int i = 0; i < pdfTable.rows.size(); i++) {
            TableRow pdfRow = pdfTable.rows.get(i);
            XWPFTableRow wordRow = wordTable.getRow(i);

            for (int j = 0; j < pdfRow.cells.size() && j < maxCols; j++) {
                TableCell pdfCell = pdfRow.cells.get(j);
                XWPFTableCell wordCell = wordRow.getCell(j);

                if (wordCell != null) {
                    // 清除默认段落
                    wordCell.removeParagraph(0);

                    // 添加内容
                    XWPFParagraph para = wordCell.addParagraph();
                    XWPFRun run = para.createRun();
                    run.setText(pdfCell.text);
                    run.setFontSize(10);

                    // 如果是第一行，作为表头
                    if (i == 0) {
                        run.setBold(true);
                        para.setAlignment(ParagraphAlignment.CENTER);
                    }
                }
            }
        }

        // 表格样式
        wordTable.setWidth("100%");

        // 表格后添加空行
        wordDoc.createParagraph();
    }

    /**
     * 保存图片
     */
    private void saveImage(BufferedImage image, String filePath) throws IOException {
        ImageIO.write(image, "PNG", new File(filePath));
    }

    /**
     * 插入图片到Word
     */
    private void insertImageIntoWord(String imagePath, ImageElement image,
                                     XWPFDocument wordDoc) throws Exception {

        XWPFParagraph para = wordDoc.createParagraph();
        para.setAlignment(ParagraphAlignment.CENTER);

        try (FileInputStream is = new FileInputStream(imagePath)) {
            XWPFRun run = para.createRun();

            // 计算合适的大小
            int maxWidth = 500; // 最大显示宽度
            int originalWidth = (int) image.width;
            int originalHeight = (int) image.height;

            int displayWidth = originalWidth;
            int displayHeight = originalHeight;

            if (originalWidth > maxWidth) {
                displayWidth = maxWidth;
                displayHeight = (int) ((float) originalHeight * maxWidth / originalWidth);
            }

            // 插入图片
            run.addPicture(is,
                    XWPFDocument.PICTURE_TYPE_PNG,
                    imagePath,
                    Units.toEMU(displayWidth),
                    Units.toEMU(displayHeight));

            // 添加图片说明
            XWPFRun captionRun = para.createRun();
            captionRun.addBreak();
            captionRun.setText("图片");
            captionRun.setFontSize(9);
            captionRun.setColor("666666");
        }

        para.setSpacingAfter(100);
    }

    /**
     * 添加段落到Word
     */
    private void addParagraphToWord(String text, XWPFDocument wordDoc) {
        XWPFParagraph para = wordDoc.createParagraph();
        XWPFRun run = para.createRun();
        run.setText(text);
        run.setFontFamily("宋体");
        run.setFontSize(12);

        // 判断是否为标题
        if (isHeading(text)) {
            run.setBold(true);
            run.setFontSize(14);
            para.setSpacingBefore(100);
        } else if (isTitle(text)) {
            run.setBold(true);
            run.setFontSize(16);
            para.setAlignment(ParagraphAlignment.CENTER);
            para.setSpacingBefore(150);
        }

        para.setSpacingAfter(80);
    }

    private boolean isHeading(String text) {
        return text.length() < 50 &&
                (text.endsWith("：") || text.endsWith(":") ||
                        text.matches("^[一二三四五六七八九十]+[、.]") ||
                        text.matches("^[0-9]+\\.[0-9]+"));
    }

    private boolean isTitle(String text) {
        return text.length() < 30 &&
                !text.contains("。") && !text.contains("，") &&
                !text.contains(".") && !text.contains(",");
    }

    /**
     * 添加页眉
     */
    private void addPageHeader(XWPFDocument wordDoc, int pageNum) {
        XWPFParagraph header = wordDoc.createParagraph();
        XWPFRun run = header.createRun();
        run.setText("第 " + pageNum + " 页");
        run.setFontSize(9);
        run.setColor("999999");
        header.setAlignment(ParagraphAlignment.RIGHT);
        header.setSpacingAfter(30);
    }

    /**
     * 添加页脚
     */
    private void addPageFooter(XWPFDocument wordDoc, int pageNum) {
        XWPFParagraph footer = wordDoc.createParagraph();
        XWPFRun run = footer.createRun();
        run.setText("— " + pageNum + " —");
        run.setFontSize(8);
        run.setColor("CCCCCC");
        run.setItalic(true);
        footer.setAlignment(ParagraphAlignment.CENTER);
        footer.setSpacingAfter(50);
    }

    /**
     * 添加分页符
     */
    private void addPageBreak(XWPFDocument wordDoc) {
        XWPFParagraph para = wordDoc.createParagraph();
        XWPFRun run = para.createRun();
        run.addBreak(BreakType.PAGE);
    }
}
