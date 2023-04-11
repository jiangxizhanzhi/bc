package com.cyberpeace.common.util;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.TargetMode;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

//@Slf4j
//@Component
public class OfficeWatermarkerUtils{


    private static String defaultColor="#000000";

    private static int DEFAULTCOLOR=0x000000;

//    public static final File FONT_HEI = new File("C:\\Users\\dell\\Downloads\\simhei.ttf");
    public static final File FONT_HEI = new File("/cpms/built_in/simhei.ttf");

    public static void main(String[] args) {
        try {
//            Font font = Font.createFont(Font.TRUETYPE_FONT,new FileInputStream(FONT_HEI)).deriveFont(Font.BOLD,50);
//            BufferedImage bufferedImage = waterMarkByText(600,900,"156-17376552318",new Color(DEFAULTCOLOR),font,315.0,0.5f);
//            File outputfile = new File("E:\\download\\111.png");
//            ImageIO.write(bufferedImage, "png", outputfile);
            boolean addWordWaterMark = addWordWaterMark("D:/cpms/app1.docx", "南京赛宁", null, "D:/cpms/app2.docx");
//            addExcelWatermark1("D:/cpms/", "app1.xls","南京赛宁", null, "D:/cpms");
//            addExcelWaterMark("D:/cpms/app1.xlsx", "D:/cpms/app2.xlsx", "南京赛宁");
//            addPPTWatermark("D:/cpms/", "app1.pptx","南京赛宁", null, "D:/cpms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加word水印
     * @param filePath 文件路径
     * @param watermark 水印
     * @param cipher 自定义属性
     * @param outPath 输出路径
     * @throws IOException
     * @throws XmlException
     */
    public static boolean addWordWaterMark(String filePath, String watermark, String cipher, String outPath) throws IOException, InvalidFormatException, FontFormatException {
        File file = new File(filePath);
        InputStream in = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(in);
        POIXMLProperties.CustomProperties cp = document.getProperties().getCustomProperties();
        OutputStream out = new FileOutputStream(file);
        //添加自定义属性
        if (cp.getProperty("cipher")!=null) {
            cp.addProperty("cipher",cipher);
        }
//        没有header.xml添加xml
        if (document.getHeaderFooterPolicy().getHeader(STHdrFtr.DEFAULT)==null
                && document.getHeaderFooterPolicy().getHeader(STHdrFtr.FIRST)==null
                && document.getHeaderFooterPolicy().getHeader(STHdrFtr.EVEN)==null) {
            document.createHeader(HeaderFooterType.DEFAULT);
        }
//        生成图片水印
        Font font = Font.createFont(Font.TRUETYPE_FONT,new FileInputStream(FONT_HEI)).deriveFont(Font.BOLD,50);
        BufferedImage bufferedImage = waterMarkByText(1200,1600,watermark,new Color(DEFAULTCOLOR),font,315.0,0.5f);
//        File watermarkPic = new File("D:/cpms"+File.separator+"wordWatermark.png");
        File watermarkPic = new File("/cpms"+File.separator+"wordWatermark.png");
        ImageIO.write(bufferedImage, "png", watermarkPic);
        document.addPictureData(new FileInputStream(watermarkPic), XWPFDocument.PICTURE_TYPE_PNG);
        document.write(out);
        out.flush();
        File outFile = new File(outPath);
        FileUtils.copyFile(file, outFile);
        editWordHeader(outFile);//添加水印
        System.out.println("明水印增加完成：{}"+file.getName());
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
        return true;
    }

    /**
     * 添加excel水印
     * @param path 路径
     * @param fileName 文件名
     * @param watermark 水印
     * @param cipher 密文
     * @param outPath 输出路径
     * @throws Exception
     */
    public static boolean addExcelWatermark1(String path,String fileName,String watermark,String cipher,String outPath) throws Exception {
        //创建水印图片
        String bg = "excelWatermark.png";
        File bgFile = new File(path+bg);
        System.out.println("生成图片路径：{}"+bgFile.getAbsolutePath());
        Font font = Font.createFont(Font.TRUETYPE_FONT,new FileInputStream(FONT_HEI)).deriveFont(Font.BOLD,50);
        createImage(watermark, font, bgFile, 0.2f);

        File file = new File(path + fileName);

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        POIXMLProperties.CustomProperties cp = workbook.getProperties().getCustomProperties();
        InputStream is;
        byte[] bytes;
        int pictureIdx;
        is = new FileInputStream(path + bg);
        bytes = IOUtils.toByteArray(is);
        pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        is.close();

        for (int index = 0; index < workbook.getNumberOfSheets(); index++) {
            XSSFSheet sheet = workbook.getSheetAt(index);
            String rID = sheet.addRelation(null, XSSFRelation.IMAGES,workbook
                    .getAllPictures().get(pictureIdx)).getRelationship().getId();
            sheet.getCTWorksheet().addNewPicture().setId(rID);
        }
        //添加自定义属性
        if (cp.getProperty("cipher")!=null) {
            cp.addProperty("cipher",cipher);
        }
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.flush();
        FileUtils.copyFile(file, new File(outPath+File.separator+file.getName()));
        workbook.close();
        System.out.println("明水印增加完成：{}"+fileName);

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * excel添加水印
     *
     * @param inputSrc      原始文件路径
     * @param outputSrc     生成文件路径
     * @param waterMarkName 水印内容
     */
    private static void addExcelWaterMark(String inputSrc, String outputSrc, String waterMarkName) {
//        byte[] fileData = OfficeWaterMarkUtil.getBytesByFile(inputSrc);
        File file = new File(inputSrc);

        BufferedImage image = createWaterMarkImage(waterMarkName);
        // 导出到字节流B
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            InputStream sbs = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(sbs);
            int pictureIdx = workbook.addPicture(os.toByteArray(), Workbook.PICTURE_TYPE_PNG);
            POIXMLDocumentPart poixmlDocumentPart = workbook.getAllPictures().get(pictureIdx);

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
                XSSFSheet sheet = workbook.getSheetAt(i);
                PackagePartName ppn = poixmlDocumentPart.getPackagePart().getPartName();
                String relType = XSSFRelation.IMAGES.getRelation();
                PackageRelationship pr = sheet.getPackagePart().addRelationship(ppn, TargetMode.INTERNAL, relType, null);
                sheet.getCTWorksheet().addNewPicture().setId(pr.getId());
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                workbook.write(bos);
            } finally {
                bos.close();
            }
            bos.writeTo(new BufferedOutputStream(new FileOutputStream(outputSrc)));
        } catch (IOException e) {
            System.out.println("Excel加水印失败"+ e);
            throw new RuntimeException("excel makewater fail：生成ppt文件失败");
        }

    }

    public static BufferedImage createWaterMarkImage(String waterMark) {
        String[] textArray = waterMark.split("\n");
        Font font = new Font("microsoft-yahei", Font.PLAIN, 20);
        Integer width = 500;
        Integer height = 200;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 背景透明 开始
        Graphics2D g = image.createGraphics();
        image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g.dispose();
        // 背景透明 结束
        g = image.createGraphics();
        g.setColor(new Color(Integer.parseInt("#C5CBCF".substring(1), 16)));// 设定画笔颜色
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
        Font font = Font.createFont(Font.TRUETYPE_FONT,new FileInputStream(FONT_HEI)).deriveFont(Font.BOLD,50);
        BufferedImage bufferedImage = waterMarkByText(1920,1080,watermark,new Color(DEFAULTCOLOR),font,315.0,0.2f);
        File outputfile = new File(outPath+File.separator+"pptWatermark.png");
        ImageIO.write(bufferedImage, "png", outputfile);
        byte[] pictureData = IOUtils.toByteArray(new FileInputStream(outputfile));
        XSLFPictureData pd = slideShow.addPicture(pictureData, PictureData.PictureType.PNG);

        //不判断，直接给所有母版添加水印
        for (XSLFSlideMaster master : slideShow.getSlideMasters()) {
            XSLFPictureShape pictShape = master.createPicture(pd);
            Rectangle rect = new Rectangle(0, 0, (int)dimension.getWidth(), (int)dimension.getHeight());
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

    private static final String watermarkXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<w:hdr xmlns:wpc=\"http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas\" xmlns:cx=\"http://schemas.microsoft.com/office/drawing/2014/chartex\" xmlns:cx1=\"http://schemas.microsoft.com/office/drawing/2015/9/8/chartex\" xmlns:cx2=\"http://schemas.microsoft.com/office/drawing/2015/10/21/chartex\" xmlns:cx3=\"http://schemas.microsoft.com/office/drawing/2016/5/9/chartex\" xmlns:cx4=\"http://schemas.microsoft.com/office/drawing/2016/5/10/chartex\" xmlns:cx5=\"http://schemas.microsoft.com/office/drawing/2016/5/11/chartex\" xmlns:cx6=\"http://schemas.microsoft.com/office/drawing/2016/5/12/chartex\" xmlns:cx7=\"http://schemas.microsoft.com/office/drawing/2016/5/13/chartex\" xmlns:cx8=\"http://schemas.microsoft.com/office/drawing/2016/5/14/chartex\" xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:aink=\"http://schemas.microsoft.com/office/drawing/2016/ink\" xmlns:am3d=\"http://schemas.microsoft.com/office/drawing/2017/model3d\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:wp14=\"http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing\" xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" xmlns:w10=\"urn:schemas-microsoft-com:office:word\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:w14=\"http://schemas.microsoft.com/office/word/2010/wordml\" xmlns:w15=\"http://schemas.microsoft.com/office/word/2012/wordml\" xmlns:w16cex=\"http://schemas.microsoft.com/office/word/2018/wordml/cex\" xmlns:w16cid=\"http://schemas.microsoft.com/office/word/2016/wordml/cid\" xmlns:w16=\"http://schemas.microsoft.com/office/word/2018/wordml\" xmlns:w16sdtdh=\"http://schemas.microsoft.com/office/word/2020/wordml/sdtdatahash\" xmlns:w16se=\"http://schemas.microsoft.com/office/word/2015/wordml/symex\" xmlns:wpg=\"http://schemas.microsoft.com/office/word/2010/wordprocessingGroup\" xmlns:wpi=\"http://schemas.microsoft.com/office/word/2010/wordprocessingInk\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\" xmlns:wps=\"http://schemas.microsoft.com/office/word/2010/wordprocessingShape\" mc:Ignorable=\"w14 w15 w16se w16cid w16 w16cex w16sdtdh wp14\"><w:p w14:paraId=\"2FD6A8B5\" w14:textId=\"7B4DEC51\" w:rsidR=\"007C48C4\" w:rsidRDefault=\"003B2BB3\">WPHEADER<w:r><w:rPr><w:noProof/></w:rPr><w:pict w14:anchorId=\"776FC76C\"><v:shapetype id=\"_x0000_t75\" coordsize=\"21600,21600\" o:spt=\"75\" o:preferrelative=\"t\" path=\"m@4@5l@4@11@9@11@9@5xe\" filled=\"f\" stroked=\"f\"><v:stroke joinstyle=\"miter\"/><v:formulas><v:f eqn=\"if lineDrawn pixelLineWidth 0\"/><v:f eqn=\"sum @0 1 0\"/><v:f eqn=\"sum 0 0 @1\"/><v:f eqn=\"prod @2 1 2\"/><v:f eqn=\"prod @3 21600 pixelWidth\"/><v:f eqn=\"prod @3 21600 pixelHeight\"/><v:f eqn=\"sum @0 0 1\"/><v:f eqn=\"prod @6 1 2\"/><v:f eqn=\"prod @7 21600 pixelWidth\"/><v:f eqn=\"sum @8 21600 0\"/><v:f eqn=\"prod @7 21600 pixelHeight\"/><v:f eqn=\"sum @10 21600 0\"/></v:formulas><v:path o:extrusionok=\"f\" gradientshapeok=\"t\" o:connecttype=\"rect\"/><o:lock v:ext=\"edit\" aspectratio=\"t\"/></v:shapetype><v:shape id=\"WordPictureWatermark\" o:spid=\"_x0000_s1032\" type=\"#_x0000_t75\" style=\"position:absolute;left:0;text-align:left;margin-left:0;margin-top:0;width:600pt;height:850pt;z-index:-251657216;mso-position-horizontal:center;mso-position-horizontal-relative:margin;mso-position-vertical:center;mso-position-vertical-relative:margin\" o:allowincell=\"f\"><v:imagedata r:id=\"r_id\" o:title=\"background\" gain=\"19661f\" blacklevel=\"22938f\"/></v:shape></w:pict></w:r><w:r w:rsidR=\"00995F1F\"><w:rPr><w:noProof/></w:rPr><w:pict w14:anchorId=\"51BBCB7B\"><v:shapetype id=\"_x0000_t136\" coordsize=\"21600,21600\" o:spt=\"136\" adj=\"10800\" path=\"m@7,l@8,m@5,21600l@6,21600e\"><v:formulas><v:f eqn=\"sum #0 0 10800\"/><v:f eqn=\"prod #0 2 1\"/><v:f eqn=\"sum 21600 0 @1\"/><v:f eqn=\"sum 0 0 @2\"/><v:f eqn=\"sum 21600 0 @3\"/><v:f eqn=\"if @0 @3 0\"/><v:f eqn=\"if @0 21600 @1\"/><v:f eqn=\"if @0 0 @2\"/><v:f eqn=\"if @0 @4 21600\"/><v:f eqn=\"mid @5 @6\"/><v:f eqn=\"mid @8 @5\"/><v:f eqn=\"mid @7 @8\"/><v:f eqn=\"mid @6 @7\"/><v:f eqn=\"sum @6 0 @5\"/></v:formulas><v:path textpathok=\"t\" o:connecttype=\"custom\" o:connectlocs=\"@9,0;@10,10800;@11,21600;@12,10800\" o:connectangles=\"270,180,90,0\"/><v:textpath on=\"t\" fitshape=\"t\"/><v:handles><v:h position=\"#0,bottomRight\" xrange=\"6629,14971\"/></v:handles><o:lock v:ext=\"edit\" text=\"t\" shapetype=\"t\"/></v:shapetype><v:shape id=\"_x0000_s1030\" type=\"#_x0000_t136\" style=\"position:absolute;left:0;text-align:left;margin-left:0;margin-top:0;width:50pt;height:50pt;z-index:251655168;visibility:hidden\"><o:lock v:ext=\"edit\" selection=\"t\" text=\"f\" shapetype=\"f\"/></v:shape></w:pict></w:r></w:p></w:hdr>";
    private static final String watermarkRels = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\"><Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/image\" Target=\"media/PICTNAME\"/></Relationships>";
    /**
     * 为word添加图片水印
     * 对文档以压缩包文件打开，编辑下行目录中header.xml文件，header文件管理水印的生成，并将生成的水印图片添加到资源目录
     * @param file 输出的文件
     * @throws IOException
     */
    public static void editWordHeader(File file) throws IOException {
        List<File> headers,rels,picts;
        File outPath = new File(file.getParent()+"\\"+file.getName().split("\\.")[0]);
        ZipUtils.unZip(file,outPath.getAbsolutePath());
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

        ZipUtils.zip(outPath,new File(file.getAbsolutePath()));
        if (outPath.exists()) {
            FileUtil.del(outPath);
        }
    }

    private static void editHeader(List<File> files) throws IOException {
        //保留原文档页眉部分，w:pPr记录页眉样式，w:r记录页眉文本
        Pattern pattern = compile("<w:pPr[\\s>].*</w:r>");
        Matcher matcher;
        for (File header : files){
            String headerContent="";
            String str = FileUtils.readFileToString(header, "UTF-8");
            matcher = pattern.matcher(str);
            while (matcher.find()){
                headerContent+=matcher.group();
            }
            //直接覆盖header文件，不对存在的页眉做处理
            FileUtils.writeStringToFile(header,watermarkXml.replace("WordPictureWatermark","WordPictureWatermark"+new Date().getTime()%10000000)
                    .replace("r_id","rId1").replace("WPHEADER",headerContent));
        }
    }

    private static void editRels(List<File> files,String pictName) throws IOException {
        for (File rels : files){
            FileUtils.writeStringToFile(rels,watermarkRels.replace("r_id","rId1").replace("PICTNAME",pictName));
        }
    }

    private static File createHeader(String path){
        File file = new File(path+File.separator+"header1.xml");
        try {
            file.createNewFile();
//            FileUtils.writeStringToFile(file,watermarkXml.replace("WordPictureWatermark","WordPictureWatermark"+new Date().getTime()%10000000)
//                    .replace("r_id","rId1"));
        }catch (IOException e) {
            System.out.println("文件创建失败:{}"+file.getName());
        }
        return file;
    }

    private static List<File> createRels(String path,int fileCount){
        List<File> files= new ArrayList<File>();
        for (int i = 1; i <= fileCount; i++) {
            File file = new File(path+File.separator+"header"+i+".xml.rels");
            try {
                file.createNewFile();
                files.add(file);
//                FileUtils.writeStringToFile(file,watermarkRels.replace("r_id","rId1"));
            }catch (IOException e) {
                System.out.println("文件创建失败:{}"+file.getName());
            }
        }
        return files;
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
     * 计算Font文本样式的文字宽和高
     * @param text
     * @param font
     * @return
     */
    private static int[] getWidthAndHeight(String text, Font font) {
        Rectangle2D r = font.getStringBounds(text, new FontRenderContext(AffineTransform.getScaleInstance(1, 1), false, false));
        int unitHeight = (int) Math.floor(r.getHeight());//
        // 获取整个str用了font样式的宽度这里用四舍五入后+1保证宽度绝对能容纳这个字符串作为图片的宽度
        int width = (int) Math.round(r.getWidth()) + 1;
        //把单个字符的高度+3保证高度绝对能容纳字符串作为图片的高度
        int height = unitHeight + 3;
        return new int[]{width, height};
    }

    /**
     * 创建文字图片水印
     * @param text
     * @param font
     * @param outFile
     * @throws Exception
     */
    public static void createImage(String text, Font font, File outFile, float alpha) throws Exception {
        int[] arr = getWidthAndHeight(text, font);
        int width=800,height = 600;
        //创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);//创建图片画布

        Graphics2D g = (Graphics2D) image.getGraphics();
        //先用白色填充整张图片,也就是背景
        g.setColor(Color.WHITE);
        //画出矩形区域，以便于在矩形区域内写入文字
        g.fillRect(0, 0, width, height);
        //再换成黑色，以便于写入文字
        g.setColor(new Color(DEFAULTCOLOR));
        g.setFont(font);
        // 设置透明度:1.0f为透明度 ，值从0-1.0，依次变得不透明
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
//        g.shear(0,-0.3);
        g.rotate(Math.toRadians(-45));
        //画出一行字符串并使其画布居中
        g.drawString(text, (float) -(width-arr[0]-arr[1])/2, (float) ((Math.sqrt(2))*(height-arr[1])/2)+arr[1]*5/2);
        g.dispose();
        ImageIO.write(image, "png", outFile);//输出png图片

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


}

