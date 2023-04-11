package com.cyberpeace.common.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.ShapeType;
import com.spire.doc.fields.ShapeObject;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class WaterMarkingUtils {

    private static final Color DEFAULTCOLOR = new Color(Integer.parseInt("#D3D3D3".substring(1), 16));

    public static void main(String[] args) {
//        String markText = "南京赛宁";
//        setExcelWaterMark("D:/cpms/app1.xlsx","D:/cpms/app2.xlsx",markText);
//
//        setExcelWaterMark("D:/cpms/app1.xls","D:/cpms/app2.xls",markText);
//		// 原图位置, 输出图片位置, 水印文字颜色, 水印文字 支持doc docx
//        try {
//            addWordWaterMark("D:/cpms/app1.docx", String.format("D:/cpms/app2_%s.docx",-10),"南京赛宁", -10);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            addPPTWatermark("D:/cpms/","app1.pptx", "南京赛宁", null, "D:/cpms");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            setPdfWatermark("D:/cpms/app1.pdf", "D:/cpms/app2.pdf", "南京赛宁");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void setExcelWaterMark(String inputSrc, String outputSrc, String waterMarkName){
        try {
            //读取excel文件
            Workbook wb =null;
            if (inputSrc.endsWith("xls")) {
                wb = new HSSFWorkbook(new FileInputStream(inputSrc));
            }else if (inputSrc.endsWith("xlsx")){
                wb = new XSSFWorkbook(new FileInputStream(inputSrc));
            }
            //获取excel sheet个数
            int sheets = wb.getNumberOfSheets();
            //循环sheet给每个sheet添加水印
            for (int i = 0; i < sheets; i++) {
                Sheet sheet = wb.getSheetAt(i);
                //获取excel实际所占行
                int row = sheet.getFirstRowNum() + sheet.getLastRowNum();
                //获取excel实际所占列
                Row row1 = sheet.getRow(sheet.getFirstRowNum());
                if (row1==null){
                    continue;
                }
                int cell = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum() + 1;
                //根据行与列计算实际所需多少水印
                int xCount = Math.max(cell / 5 + 1, 20);
                int yCount = Math.max(row / 5 + 1, 20);
                putWaterRemarkToExcel(wb, sheet, waterMarkName, 0, 0, 5, 5, xCount, yCount, 0, 0);

                //设置为受保护
                sheet.protectSheet(waterMarkName);
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                wb.write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            wb.close();
            byte[] content = os.toByteArray();
            // Excel文件生成后存储的位置。
            File file1 = new File(outputSrc);
            OutputStream fos = null;
            try {
                fos = new FileOutputStream(file1);
                fos.write(content);
                os.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @param wb   Excel Workbook
     * @param sheet  需要打水印的Excel
     * @param waterRemark  水印文字
     * @param startXCol   水印起始列
     * @param startYRow  水印起始行
     * @param betweenXCol  水印横向之间间隔多少列
     * @param betweenYRow  水印纵向之间间隔多少行
     * @param XCount    横向共有水印多少个
     * @param YCount    纵向共有水印多少个
     * @param waterRemarkWidth   水印图片宽度为多少列
     * @param waterRemarkHeight   水印图片高度为多少行
     * @throws IOException
     */
    public static void putWaterRemarkToExcel(Workbook wb, Sheet sheet, String waterRemark, int startXCol,
                                              int startYRow, int betweenXCol, int betweenYRow, int XCount, int YCount, int waterRemarkWidth,
                                              int waterRemarkHeight) throws IOException {

        // 加载图片
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        BufferedImage bufferImg = createWaterMarkImage(waterRemark);
        ImageIO.write(bufferImg, "png", byteArrayOut);
        // 开始打水印
        Drawing drawing = sheet.createDrawingPatriarch();
        Drawing<?> drawingPatriarch = sheet.getDrawingPatriarch();
        // 按照共需打印多少行水印进行循环
        for (int yCount = 0; yCount < YCount; yCount++) {
            // 按照每行需要打印多少个水印进行循环
            for (int xCount = 0; xCount < XCount; xCount++) {
                // 创建水印图片位置
                int xIndexInteger = startXCol + (xCount * waterRemarkWidth) + (xCount * betweenXCol);
                int yIndexInteger = startYRow + (yCount * waterRemarkHeight) + (yCount * betweenYRow);
                /*
                 * 参数定义： 第一个参数是（x轴的开始节点）； 第二个参数是（是y轴的开始节点）； 第三个参数是（是x轴的结束节点）；
                 * 第四个参数是（是y轴的结束节点）； 第五个参数是（是从Excel的第几列开始插入图片，从0开始计数）；
                 * 第六个参数是（是从excel的第几行开始插入图片，从0开始计数）； 第七个参数是（图片宽度，共多少列）；
                 * 第8个参数是（图片高度，共多少行）；
                 */
                ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, xIndexInteger,
                        yIndexInteger, xIndexInteger + waterRemarkWidth, yIndexInteger + waterRemarkHeight);

                Picture pic = drawing.createPicture(anchor,
                        wb.addPicture(byteArrayOut.toByteArray(), Workbook.PICTURE_TYPE_PNG));
                pic.resize();
            }
        }
    }


    public static BufferedImage createWaterMarkImage(String waterMark) {
        String[] textArray = waterMark.split("\n");
        Font font = new Font("STSong-Light", Font.PLAIN, 20);
        Integer width = 500;
        Integer height = 200;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 背景透明 开始
        Graphics2D g = image.createGraphics();
        image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g.dispose();
        // 背景透明 结束
        g = image.createGraphics();
        g.setColor(new Color(Integer.parseInt("#D3D3D3".substring(1), 16)));// 设定画笔颜色
        g.setFont(font);// 设置画笔字体
        g.shear(0.1, -0.26);// 设定倾斜度
        // 设置字体平滑
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int y = 150;
        for (int i = 0; i < textArray.length; i++) {
            g.drawString(textArray[i], 0, y);// 画出字符串
            y = y + font.getSize();
        }
        g.dispose();// 释放画笔
        return image;
    }

    /**
     * word文字水印
     *
     * @param inputPath
     * @param outPath
     * @param markStr
     */
    public static void addWordWaterMark(String inputPath, String outPath, String markStr) {
        //加载示例文档
        Document doc = new Document();
        doc.loadFromFile(inputPath);
        //添加艺术字并设置大小
        ShapeObject shape = new ShapeObject(doc, ShapeType.Text_Plain_Text);
        shape.setWidth(50);
        shape.setHeight(15);
        //设置艺术字文本内容、位置及样式
        shape.setVerticalPosition(30);
        shape.setHorizontalPosition(20);
        shape.setRotation(-10);
        shape.getWordArt().setSize(10);
//        shape.getWordArt().setSmallCaps();
        shape.getWordArt().setFontFamily("STSong-Light");
        shape.getWordArt().setText(markStr);
        int rgb = Integer.parseInt("#F5F5F5".substring(1), 16);
        shape.setFillColor(new Color(rgb));
//        shape.setFillColor(new Color(Integer.parseInt("#DCDCDC".substring(1), 16)));
//        shape.setLineStyle(ShapeLineStyle.Single);
        shape.setStrokeColor(new Color(rgb));
        shape.setStrokeWeight(1);


        Section section;

        com.spire.doc.HeaderFooter header;
        for (int n = 0; n < doc.getSections().getCount(); n++) {
            section = doc.getSections().get(n);
            //获取section的页眉
            header = section.getHeadersFooters().getHeader();
            Paragraph paragraph;

            if (header.getParagraphs().getCount() > 0) {
                //如果页眉有段落，取它第一个段落
                paragraph = header.getParagraphs().get(0);
            } else {
                //否则新增加一个段落到页眉
                paragraph = header.addParagraph();
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    //复制艺术字并设置多行多列位置
                    shape = (ShapeObject) shape.deepClone();
                    shape.setVerticalPosition(50 + 150 * i);
                    shape.setHorizontalPosition(20 + 160 * j);
                    paragraph.getChildObjects().add(shape);
                }
            }
        }
        //保存文档
        doc.saveToFile(outPath, FileFormat.Docx_2013);
    }

    /**
     * 给幻灯片母版添加文本达成水印效果
     * @param path
     * @param fileName
     * @param watermark
     * @param cipher
     * @param outPath 输出路径
     * @throws Exception
     */
    public static boolean addPPTWatermark(String path, String fileName, String watermark, String cipher,String outPath) throws Exception{
        File file = new File(path + fileName);

        XMLSlideShow slideShow = new XMLSlideShow(new FileInputStream(file));
        POIXMLProperties.CustomProperties cp = slideShow.getProperties().getCustomProperties();
        Dimension dimension = slideShow.getPageSize();
//        Font font = Font.createFont(Font.TRUETYPE_FONT,new FileInputStream("STSong-Light")).deriveFont(Font.BOLD,50);
        Font font = new Font("STSong-Light", Font.PLAIN, 35);
        BufferedImage bufferedImage = waterMarkByText(1920,1080,watermark,DEFAULTCOLOR,font,-10.0,0.1f);
        File outputfile = new File(outPath+File.separator+"pptWatermark.png");
        ImageIO.write(bufferedImage, "png", outputfile);
        byte[] pictureData = IOUtils.toByteArray(new FileInputStream(outputfile));
        XSLFPictureData pd = slideShow.addPicture(pictureData, PictureData.PictureType.PNG);

        //不判断，直接给所有母版添加水印
        for (XSLFSlideMaster master : slideShow.getSlideMasters()) {
            XSLFPictureShape pictShape = master.createPicture(pd);
            java.awt.Rectangle rect = new Rectangle(0, 0, (int)dimension.getWidth(), (int)dimension.getHeight());
            pictShape.setAnchor(rect);
        }
        //添加自定义属性
        if (cp.getProperty("cipher")!=null) {
            cp.addProperty("cipher",cipher);
        }
        FileOutputStream out = new FileOutputStream(file);
        slideShow.write(out);
        out.flush();
        FileUtils.copyFile(file, new File(outPath+file.getName()));
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        slideShow.close();
        System.out.println("明水印增加完成：{}"+file.getName());
        return true;
    }

    /**
     * 生成背景透明的 文字水印
     *
     * @param width     生成图片宽度
     * @param height    生成图片高度
     * @param text      水印文字
     * @param color     颜色对象
     * @param font      awt字体
     * @param degree    水印文字旋转角度
     * @param alpha     水印不透明度0f-1.0f
     */
    public static BufferedImage waterMarkByText(int width, int height, String text, Color color, Font font, Double degree, float alpha) {
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 得到画笔对象
        Graphics2D g2d = buffImg.createGraphics();
        // 增加下面的代码使得背景透明
        buffImg = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = buffImg.createGraphics();

        // 设置对线段的锯齿状边缘处理
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // 设置水印旋转
        if (null != degree) {
            //注意rotate函数参数theta，为弧度制，故需用Math.toRadians转换一下
            //以矩形区域中央为圆心旋转
            g2d.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
        }

        // 设置颜色
        g2d.setColor(color);

        // 设置 Font
        g2d.setFont(font);

        // 设置透明度:1.0f为透明度 ，值从0-1.0，依次变得不透明
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // 获取真实宽度
        float realWidth = 22.5f*getStringLength(text);

        // 计算绘图偏移x、y，使得使得水印文字在图片中居中,x、y坐标是基于Graphics2D.rotate过后的坐标系
//        float x = 0.5f * width - 0.5f * fontSize * realWidth;
//        float y = 0.5f * height + 0.5f * fontSize;

        // 取绘制的字串宽度、高度中间点进行偏移，使得文字在图片坐标中居中
        for (float x = -1000,i = 0;x < width + 1400;x+=realWidth+300,i++){
            for (float y = -1000 + i*125;y < height + 1400; y+=250){//对半错列
                g2d.drawString(text, x, y);
            }
        }
        // 释放资源
        g2d.dispose();
        return buffImg;
    }

    //获取ASCII长度，统一中英文长度不一样的情况
    public static int getStringLength(String s){
        int length = 0;
        for(int i = 0; i < s.length(); i++)
        {
            int ascii = Character.codePointAt(s, i);
            if(ascii >= 0 && ascii <=255) {
                length++;
            } else {
                length += 2;
            }

        }
        return length;
    }

    /**
     * pdf设置文字水印
     *
     * @param inputPath
     * @param outPath
     * @param markStr
     * @throws DocumentException
     * @throws IOException
     */
    public static void setPdfWatermark(String inputPath, String outPath, String markStr)
            throws DocumentException, IOException {
        File file = new File(outPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedOutputStream bufferOut = null;
        try {
            bufferOut = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        // 水印的高和宽（参数可调节）
        int textH = 75;
        int textW = 170;
        // 间隔距离（参数可调节）
        int interval = 30;

        PdfReader reader = new PdfReader(inputPath);
        PdfStamper stamper = new PdfStamper(reader, bufferOut);
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        // BaseFont base = BaseFont.createFont("/data/tmis/uploads/file/font/simsun.ttc,1", BaseFont.IDENTITY_H,
        // BaseFont.EMBEDDED);
        PdfGState gs = new PdfGState();
        com.itextpdf.text.Rectangle pageRect = null;
        for (int i = 1; i < total; i++) {
            pageRect = reader.getPageSizeWithRotation(i);
            content = stamper.getOverContent(i);// 在内容上方加水印
            //content = stamper.getUnderContent(i);// 在内容下方加水印
            gs.setFillOpacity(0.2f);
            // content.setGState(gs);
            content.beginText();
//			content.setRGBColorFill(192, 192, 192);
            content.setRGBColorFill(128, 128, 128);
            content.setFontAndSize(base, 18);
            content.setTextMatrix(300, 250);
            for (int height = interval + textH; height < pageRect.getHeight();
                 height = height + textH * 3) {
                for (int width = interval + textW; width < pageRect.getWidth() + textW;
                     width = width + textW * 2) {
                    // rotation:倾斜角度
                    content.showTextAligned(Element.ALIGN_LEFT, markStr, width - textW, height - textH, 30);
                }
            }
//			content.showTextAligned(Element.ALIGN_CENTER, markStr, 250, 400, 55);
            // content.showTextAligned(Element.ALIGN_CENTER,
            // "检测管理信息系统！",400,250, 55);
            content.setRGBColorFill(0, 0, 0);
            content.setFontAndSize(base, 8);
            content.endText();
        }
        stamper.close();
        try {
            bufferOut.flush();
            bufferOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
