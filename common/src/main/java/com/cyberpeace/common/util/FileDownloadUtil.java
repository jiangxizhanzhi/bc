package com.cyberpeace.common.util;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileDownloadUtil {
    /**
     * 说明：根据指定URL将⽂件下载到指定⽬标位置
     *
     * @param urlPath     下载路径
     * @param downloadDir ⽂件存放⽬录
     * @return返回下载⽂件
     */
    @SuppressWarnings("finally")
    public static File downloadFile(String urlPath, String fileName, String downloadDir) {
        File file = null;
        try {
// 统⼀资源
            URL url = new URL(urlPath);
// 连接类的⽗类，抽象类
            URLConnection urlConnection = url.openConnection();
// http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
//设置超时
            httpURLConnection.setConnectTimeout(1000 * 5);
//设置请求⽅式，默认是GET
            httpURLConnection.setRequestMethod("POST");
// 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
// 打开到此 URL引⽤的资源的通信链接（如果尚未建⽴这样的连接）。
            httpURLConnection.connect();
// ⽂件⼤⼩
            int fileLength = httpURLConnection.getContentLength();
// 控制台打印⽂件⼤⼩
            System.out.println("您要下载的⽂件⼤⼩为:" + fileLength / (1024) + "KB");
// 建⽴链接从请求中获取数据
            URLConnection con = url.openConnection();
            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
// 指定⽂件名称(有需求可以⾃定义)
            String fileFullName = fileName;
// 指定存放位置(有需求可以⾃定义)
            String path = downloadDir + File.separatorChar + fileFullName;
            file = new File(path);
// 校验⽂件夹⽬录是否存在，不存在就创建⼀个⽬录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[2048];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
// 控制台打印⽂件下载的百分⽐情况
                System.out.println("下载了-------> " + len * 100 / fileLength + "%\n");
            }
// 关闭资源
            bin.close();
            System.out.println("⽂件下载成功！");
        } catch (MalformedURLException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("⽂件下载失败！");
        } finally {
            return file;
        }
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
// 指定资源地址，下载⽂件测试
      //  downloadFile("http://10.100.1.28/mp_scene_collection_godfs/group1/default/20220623/03/18/1/377fd7de2288301737823d02f0720861.log", "C:/work/");
    }
}