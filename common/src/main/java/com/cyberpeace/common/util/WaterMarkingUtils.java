package com.cyberpeace.common.util;

import static java.util.regex.Pattern.compile;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaterMarkingUtils {

    private static final Color DEFAULTCOLOR = new Color(Integer.parseInt("#000000".substring(1), 16));

    public static final File FONT_SIMHEI = new File("/usr/share/fonts/simhei.ttf");
//    public static final File FONT_SIMHEI = new File("C:\\Windows\\Fonts\\simhei.ttf");

    public static void main(String[] args) {
        String markText = "南京赛宁";
        String handout = "E:\\赛宁\\需求任务\\1.pdf";
        String[] split = handout.split("/");
        if(split.length < 1){
            return;
        }
//        if(!handout.startsWith("D:/cpms")){
//            handout = "E:\\cpms" + handout;
//        }
        String handoutPath = split[split.length - 1];
        String[] split1 = handoutPath.split("\\.");
        if(split1.length < 1){
            return;
        }
        String handoutName = split1[0];
        String replacehandout = "E:\\cpms\\1.pdf";
        try {
            setPdfWatermark(handout,replacehandout, markText);
        } catch (Exception e) {
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
            log.info(e.getMessage());
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
     * @throws IOException IO异常
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
        int width = 500;
        int height = 200;
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
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));
        // 设置字体平滑
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int y = 150;
        for (String s : textArray) {
            g.drawString(s, 0, y);// 画出字符串
            y = y + font.getSize();
        }
        g.dispose();// 释放画笔
        return image;
    }

    /**
     * 获取水印文字图片流
     *
     * @param text 填入水印文字
     * @return 水印文件填充的byte数组流
     */
    private static ByteArrayOutputStream getImage(String text) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            // 导出到字节流B
            log.info("save water marking font!");
            Font font = Font.createFont(Font.TRUETYPE_FONT,new FileInputStream(FONT_SIMHEI)).deriveFont(Font.BOLD,35);
            BufferedImage image = waterMarkByText(1920,1080,text,DEFAULTCOLOR,font,-10.0,0.1f);
            ImageIO.write(image, "png", os);
            log.info("save water marking pic!!");
        } catch (IOException | FontFormatException e) {
            log.error("getImage fail: 创建水印图片IO异常", e);
            throw new RuntimeException("getImage fail: 创建水印图片IO异常");
        }
        return os;
    }

    /**
     * PPT设置水印
     *
     * @param path
     * @param targetpath
     * @param markStr
     * @throws IOException
     */
    public static void setPPTWaterMark(String path, String targetpath, String markStr) {
        XMLSlideShow slideShow;
        try {
            slideShow = new XMLSlideShow(new FileInputStream(path));
        } catch (IOException e) {
            log.error("setPPTWaterMark fail:", e);
            throw new RuntimeException("setPPTWaterMark fail:获取PPT文件失败");
        }
        ByteArrayOutputStream os = null;
        FileOutputStream out = null;
        try {
            //获取水印
            log.info("start to get water marking");
            os = getImage(markStr);
            PictureData pictureData1 = slideShow.addPicture(os.toByteArray(), PictureData.PictureType.PNG);
            for (XSLFSlide slide : slideShow.getSlides()) {
                XSLFPictureShape pictureShape = slide.createPicture(pictureData1);
//                pictureShape.setAnchor(new java.awt.Rectangle(250, 0, 500, 500));
                pictureShape.setAnchor(pictureShape.getAnchor());
            }
            log.info("water marking add success!!");
            out = new FileOutputStream(targetpath);
            slideShow.write(out);
        } catch (IOException e) {
            log.error("setPPTWaterMark fail:" + e);
            throw new RuntimeException("setPPTWaterMark fail：生成ppt文件失败");
        } finally {
            if (slideShow != null) {
                try {
                    slideShow.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
        PdfGState gs = new PdfGState();
        com.itextpdf.text.Rectangle pageRect = null;
        for (int i = 1; i < total; i++) {
            pageRect = reader.getPageSizeWithRotation(i);
            content = stamper.getOverContent(i);// 在内容上方加水印
            //content = stamper.getUnderContent(i);// 在内容下方加水印
            gs.setFillOpacity(0.0f);
             content.setGState(gs);
            content.beginText();
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

    /**
     * 添加word水印
     * @param filePath 文件路径
     * @param watermark 水印
     * @param cipher 自定义属性
     * @throws IOException
     * @throws XmlException
     */
    public static void addWordWaterMark1(String filePath, String watermark, String cipher, String outFileName) throws IOException, InvalidFormatException, FontFormatException {
        File file = new File(filePath);
        log.info(String.format("current word filepath:%s", filePath));
        log.info(String.format("current outfile %s", outFileName));
        InputStream in = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(in);
        POIXMLProperties.CustomProperties cp = document.getProperties().getCustomProperties();
        OutputStream out = new FileOutputStream(file);
        //添加自定义属性
        if (cp.getProperty("cipher")!=null) {
            cp.addProperty("cipher",cipher);
        }
        // 没有header.xml添加xml
        if (document.getHeaderFooterPolicy().getHeader(STHdrFtr.DEFAULT)==null
                && document.getHeaderFooterPolicy().getHeader(STHdrFtr.FIRST)==null
                && document.getHeaderFooterPolicy().getHeader(STHdrFtr.EVEN)==null) {
            document.createHeader(HeaderFooterType.DEFAULT);
        }
        // 生成图片水印
        Font font = Font.createFont(Font.TRUETYPE_FONT,new FileInputStream(FONT_SIMHEI)).deriveFont(Font.BOLD,16);
        BufferedImage bufferedImage = waterMarkByText(1200,1600,watermark,DEFAULTCOLOR,font,315.0,0.5f);
        File watermarkPic = new File("/cpms","wordWatermark.png");
        ImageIO.write(bufferedImage, "png", watermarkPic);
        document.addPictureData(new FileInputStream(watermarkPic), XWPFDocument.PICTURE_TYPE_PNG);
        document.write(out);
        out.flush();
        File outFile = new File(outFileName);
        FileUtils.copyFile(file, outFile);
        editWordHeader(outFile);//添加水印
        log.info("明水印增加完成：{}"+file.getName());
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (document != null) {
            document.close();
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 为word添加图片水印
     * 对文档以压缩包文件打开，编辑下行目录中header.xml文件，header文件管理水印的生成，并将生成的水印图片添加到资源目录
     * @param file 输出的文件
     * @throws IOException
     */
    public static void editWordHeader(File file) throws IOException {
        List<File> headers,rels,picts;
        File outPath = new File(file.getParent()+"\\"+file.getName().split("\\.")[0]);
//        ZipUtil.unZip(file,outPath.getAbsolutePath());
        File wordDirectory = new File(outPath.getAbsolutePath()+File.separator+"word");
        File _relsDirectory = new File(outPath.getAbsolutePath()+File.separator+"word"+File.separator+"_rels");
        File mediaDirectory = new File(outPath.getAbsolutePath()+File.separator+"word"+File.separator+"media");
        //编辑header.xml文件
        if (!wordDirectory.exists()) {
            wordDirectory.mkdirs();
        }
        headers = Arrays.asList(Objects.requireNonNull(wordDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().matches("header[0-9]*\\.xml");
            }
        })));
        if (headers.size()==0) {//没有header文件则创建
            File newHeader = createHeader(wordDirectory.getAbsolutePath());
            headers.add(newHeader);
        }
        editHeader(headers);
        //确认新增的水印图片ID
        picts = Arrays.asList(Objects.requireNonNull(mediaDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().matches("image[0-9]*\\.png");
            }
        })));
        int pictId = 0;
        for (File pict : picts){
            int nav = Integer.parseInt(pict.getName().replace("image","").split("\\.")[0]);
            if (nav > pictId) {
                pictId  = nav;
            }
        }
        String pictName = "image"+pictId+".png";
        //编辑header.xml.rels文件
        if (!_relsDirectory.exists()) {
            _relsDirectory.mkdirs();
        }
        rels = Arrays.asList(Objects.requireNonNull(_relsDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().matches("header[0-9]*\\.xml\\.rels");
            }
        })));
        if (rels.size()==0 || rels.size()!=headers.size()) {
            rels = createRels(_relsDirectory.getAbsolutePath(),headers.size());
        }
        editRels(rels,pictName);

//        ZipUtil.zip(outPath,new File(file.getAbsolutePath()));
        if (outPath.exists()) {
            FileUtil.deleteDir(outPath);
        }
    }


    private static List<File> createRels(String path,int fileCount){
        List<File> files= new ArrayList<File>();
        for (int i = 1; i <= fileCount; i++) {
            File file = new File(path+File.separator+"header"+i+".xml.rels");
            try {
                file.createNewFile();
                files.add(file);
            }catch (IOException e) {
                log.info("文件创建失败:{}"+file.getName());
            }
        }
        return files;
    }

    private static final String watermarkRels = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\"><Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/image\" Target=\"media/PICTNAME\"/></Relationships>";
    private static final String watermarkXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<w:hdr xmlns:wpc=\"http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas\" xmlns:cx=\"http://schemas.microsoft.com/office/drawing/2014/chartex\" xmlns:cx1=\"http://schemas.microsoft.com/office/drawing/2015/9/8/chartex\" xmlns:cx2=\"http://schemas.microsoft.com/office/drawing/2015/10/21/chartex\" xmlns:cx3=\"http://schemas.microsoft.com/office/drawing/2016/5/9/chartex\" xmlns:cx4=\"http://schemas.microsoft.com/office/drawing/2016/5/10/chartex\" xmlns:cx5=\"http://schemas.microsoft.com/office/drawing/2016/5/11/chartex\" xmlns:cx6=\"http://schemas.microsoft.com/office/drawing/2016/5/12/chartex\" xmlns:cx7=\"http://schemas.microsoft.com/office/drawing/2016/5/13/chartex\" xmlns:cx8=\"http://schemas.microsoft.com/office/drawing/2016/5/14/chartex\" xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:aink=\"http://schemas.microsoft.com/office/drawing/2016/ink\" xmlns:am3d=\"http://schemas.microsoft.com/office/drawing/2017/model3d\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:wp14=\"http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing\" xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" xmlns:w10=\"urn:schemas-microsoft-com:office:word\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:w14=\"http://schemas.microsoft.com/office/word/2010/wordml\" xmlns:w15=\"http://schemas.microsoft.com/office/word/2012/wordml\" xmlns:w16cex=\"http://schemas.microsoft.com/office/word/2018/wordml/cex\" xmlns:w16cid=\"http://schemas.microsoft.com/office/word/2016/wordml/cid\" xmlns:w16=\"http://schemas.microsoft.com/office/word/2018/wordml\" xmlns:w16sdtdh=\"http://schemas.microsoft.com/office/word/2020/wordml/sdtdatahash\" xmlns:w16se=\"http://schemas.microsoft.com/office/word/2015/wordml/symex\" xmlns:wpg=\"http://schemas.microsoft.com/office/word/2010/wordprocessingGroup\" xmlns:wpi=\"http://schemas.microsoft.com/office/word/2010/wordprocessingInk\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\" xmlns:wps=\"http://schemas.microsoft.com/office/word/2010/wordprocessingShape\" mc:Ignorable=\"w14 w15 w16se w16cid w16 w16cex w16sdtdh wp14\"><w:p w14:paraId=\"2FD6A8B5\" w14:textId=\"7B4DEC51\" w:rsidR=\"007C48C4\" w:rsidRDefault=\"003B2BB3\">WPHEADER<w:r><w:rPr><w:noProof/></w:rPr><w:pict w14:anchorId=\"776FC76C\"><v:shapetype id=\"_x0000_t75\" coordsize=\"21600,21600\" o:spt=\"75\" o:preferrelative=\"t\" path=\"m@4@5l@4@11@9@11@9@5xe\" filled=\"f\" stroked=\"f\"><v:stroke joinstyle=\"miter\"/><v:formulas><v:f eqn=\"if lineDrawn pixelLineWidth 0\"/><v:f eqn=\"sum @0 1 0\"/><v:f eqn=\"sum 0 0 @1\"/><v:f eqn=\"prod @2 1 2\"/><v:f eqn=\"prod @3 21600 pixelWidth\"/><v:f eqn=\"prod @3 21600 pixelHeight\"/><v:f eqn=\"sum @0 0 1\"/><v:f eqn=\"prod @6 1 2\"/><v:f eqn=\"prod @7 21600 pixelWidth\"/><v:f eqn=\"sum @8 21600 0\"/><v:f eqn=\"prod @7 21600 pixelHeight\"/><v:f eqn=\"sum @10 21600 0\"/></v:formulas><v:path o:extrusionok=\"f\" gradientshapeok=\"t\" o:connecttype=\"rect\"/><o:lock v:ext=\"edit\" aspectratio=\"t\"/></v:shapetype><v:shape id=\"WordPictureWatermark\" o:spid=\"_x0000_s1032\" type=\"#_x0000_t75\" style=\"position:absolute;left:0;text-align:left;margin-left:0;margin-top:0;width:600pt;height:850pt;z-index:-251657216;mso-position-horizontal:center;mso-position-horizontal-relative:margin;mso-position-vertical:center;mso-position-vertical-relative:margin\" o:allowincell=\"f\"><v:imagedata r:id=\"r_id\" o:title=\"background\" gain=\"19661f\" blacklevel=\"22938f\"/></v:shape></w:pict></w:r><w:r w:rsidR=\"00995F1F\"><w:rPr><w:noProof/></w:rPr><w:pict w14:anchorId=\"51BBCB7B\"><v:shapetype id=\"_x0000_t136\" coordsize=\"21600,21600\" o:spt=\"136\" adj=\"10800\" path=\"m@7,l@8,m@5,21600l@6,21600e\"><v:formulas><v:f eqn=\"sum #0 0 10800\"/><v:f eqn=\"prod #0 2 1\"/><v:f eqn=\"sum 21600 0 @1\"/><v:f eqn=\"sum 0 0 @2\"/><v:f eqn=\"sum 21600 0 @3\"/><v:f eqn=\"if @0 @3 0\"/><v:f eqn=\"if @0 21600 @1\"/><v:f eqn=\"if @0 0 @2\"/><v:f eqn=\"if @0 @4 21600\"/><v:f eqn=\"mid @5 @6\"/><v:f eqn=\"mid @8 @5\"/><v:f eqn=\"mid @7 @8\"/><v:f eqn=\"mid @6 @7\"/><v:f eqn=\"sum @6 0 @5\"/></v:formulas><v:path textpathok=\"t\" o:connecttype=\"custom\" o:connectlocs=\"@9,0;@10,10800;@11,21600;@12,10800\" o:connectangles=\"270,180,90,0\"/><v:textpath on=\"t\" fitshape=\"t\"/><v:handles><v:h position=\"#0,bottomRight\" xrange=\"6629,14971\"/></v:handles><o:lock v:ext=\"edit\" text=\"t\" shapetype=\"t\"/></v:shapetype><v:shape id=\"_x0000_s1030\" type=\"#_x0000_t136\" style=\"position:absolute;left:0;text-align:left;margin-left:0;margin-top:0;width:50pt;height:50pt;z-index:251655168;visibility:hidden\"><o:lock v:ext=\"edit\" selection=\"t\" text=\"f\" shapetype=\"f\"/></v:shape></w:pict></w:r></w:p></w:hdr>";


    private static void editRels(List<File> files,String pictName) throws IOException {
        for (File rels : files){
            FileUtils.writeStringToFile(rels,watermarkRels.replace("r_id","rId1").replace("PICTNAME",pictName));
        }
    }

    private static File createHeader(String path){
        File file = new File(path+File.separator+"header1.xml");
        try {
            file.createNewFile();
        }catch (IOException e) {
            log.info("文件创建失败:{}"+file.getName());
        }
        return file;
    }


    private static void editHeader(List<File> files) throws IOException {
        //保留原文档页眉部分，w:pPr记录页眉样式，w:r记录页眉文本
        Pattern pattern = compile("<w:pPr[\\s>].*</w:r>");
        Matcher matcher;
        for (File header : files){
            StringBuilder headerContent= new StringBuilder();
            String str = FileUtils.readFileToString(header, "UTF-8");
            matcher = pattern.matcher(str);
            while (matcher.find()){
                headerContent.append(matcher.group());
            }
            //直接覆盖header文件，不对存在的页眉做处理
            FileUtils.writeStringToFile(header,watermarkXml.replace("WordPictureWatermark","WordPictureWatermark"+new Date().getTime()%10000000)
                    .replace("r_id","rId1").replace("WPHEADER", headerContent.toString()));
        }
    }




}
