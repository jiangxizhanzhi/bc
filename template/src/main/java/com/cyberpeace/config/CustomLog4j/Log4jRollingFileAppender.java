package com.cyberpeace.config.CustomLog4j;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.LoggingEvent;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

/**
 * 名称: TraceLogFileAppender.java<br>
 * 描述: 该appender是仿照log4j中RollingFileAppender写的
 * 可配置备份天数,包含当天
 * filesize为字节数<br>
 * 批次：name<br> 
 * 修改时间:<br> 
 * 编写人员:xxx <br>
 * 版本号:1.0 
 */
@Slf4j
public class Log4jRollingFileAppender extends DailyRollingFileAppender {

	protected long maxFileSize = 10485760L;//file.length,字节数
	protected int maxDaysIndex = 1;//备份天数,包含当天

	private long nextRollover = 0;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
	private static String separator = ".";

	public Log4jRollingFileAppender() {}

//	public Log4jRollingFileAppender(Layout layout, String filename, boolean append)
//			throws IOException
//	{
//		super(layout, filename, append);
//	}
//
//	public Log4jRollingFileAppender(Layout layout, String filename)
//			throws IOException
//	{
//		super(layout, filename);
//	}
	public int getMaxDaysIndex(){return maxDaysIndex;}

	public long getMaximumFileSize()
	{
		return maxFileSize;
	}

	public void setMaxDaysIndex(int maxDays)
	{
		maxDaysIndex = maxDays;
	}

	public void setMaximumFileSize(long maxFileSize)
	{
		this.maxFileSize = maxFileSize;
	}

	public void setMaxFileSize(String value)
	{
		maxFileSize = OptionConverter.toFileSize(value, maxFileSize + 1L);
	}

	protected void setQWForFiles(Writer writer)
	{
		qw = new CustomCountingQuietWriter(writer, errorHandler,this.encoding);
	}

	//启动时专用
	public synchronized void setFile(String fileName, boolean append,
									 boolean bufferedIO, int bufferSize) throws IOException {
		String temp = getStartupFileName(fileName);
		super.setFile(temp, append, this.bufferedIO, this.bufferSize);
		if (append) {
			File f = new File(temp);
			((CustomCountingQuietWriter)qw).setCount(f.length());
		}
		//每次启动时，检验是否超出备份数量,超出则删除最旧日期
		countFile(temp,maxDaysIndex);
	}

	private String getStartupFileName(String name) {
		String filePath = name.substring(0,name.lastIndexOf("/")+1);
		String[] strs = name.split("\\.");
		File[] files = new File(filePath).listFiles(new CustomLogStartupFileFilter(strs[1]));

		//treemap默认是升序,取最后一个即为最新文件.
		TreeMap<Long, String> treeMap = new TreeMap<Long, String>();
		if(files==null || files.length == 0){
			if(strs.length==3){
				StringBuffer sb = new StringBuffer();
				String now = sdf.format(new Date());
				return sb.append(strs[0]).append(separator)
						.append(strs[1]).append("_")
						.append(now).append(separator).append("log").toString();
			}
			return name;
		}else{
			for(File temp : files){
				treeMap.put(temp.lastModified(),temp.getPath());
			}
			name = treeMap.lastEntry().getValue();
			String now = sdf.format(new Date());
			String[] strs1 = name.split("\\.");
			log.info(strs1.toString());
			if(strs1.length == 5){//符合日志文件名格式
				StringBuffer sb = new StringBuffer();
				if(now.equals(strs1[2])){
					return sb.append(strs[0]).append(separator)
							.append(strs1[1]).append(separator)
							.append(strs1[2]).append("_")
							.append("log").toString();
				}else{
					return sb.append(strs[0]).append(separator)
							.append(strs1[1]).append("_")
							.append(now).append(separator)
							.append("log").toString();
				}
			}
		}
		return name;
	}

	public synchronized void setCustomFile(String fileName, boolean append,
										   boolean bufferedIO, int bufferSize) throws IOException {
		super.setFile(fileName, append, this.bufferedIO, this.bufferSize);
		if (append) {
			File f = new File(fileName);
			((CustomCountingQuietWriter)qw).setCount(f.length());
		}
	}

	protected void subAppend(LoggingEvent event) {
		String nowDate = sdf.format(new Date());
		String[] strs = fileName.split("\\.");
		String fileDate  = strs[2];
		if(!nowDate.equals(fileDate)){
			try {
				//新建文件,当切换日期时,文件名中的序号都是从1开始
				String newFileName = getFileName(fileName,1);
				this.setCustomFile(newFileName, true, bufferedIO, bufferSize);
				//计算备份天数,多出删掉
				countFile(newFileName,maxDaysIndex);
			} catch (IOException e) {
				if (e instanceof InterruptedIOException) {
					Thread.currentThread().interrupt();
				}
			}
		}
		super.subAppend(event);//先写入数据
		if ((fileName != null) && (qw != null)) {
			long size = ((CustomCountingQuietWriter)qw).getCount();
			if ((size >= maxFileSize) && (size >= nextRollover)) {
				this.closeFile();
				fileName = getFileName(fileName,1);
				try {
					this.setCustomFile(fileName, true, bufferedIO, bufferSize);
					nextRollover = 0;
				} catch (IOException e) {
					if (e instanceof InterruptedIOException) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
	}

	/**
	 * 系统名称.实例名称.日期.文件编号.log
	 * @param name
	 * @param i
	 * @return
	 */
	private static String getFileName(String name,int i) {
		String date = sdf.format(new Date());
		String filePath = name.substring(0, name.lastIndexOf("/")+1);
		String currentName = name.substring(name.lastIndexOf("/")+1);
		String[] str = currentName.split("\\.");
		if(str.length>=3){//启动时,文件名:系统名.实例名.log,长度为3
			String fileName = "";
			fileName = str[0]+separator+str[1]+separator;
			int nums = 0;
			if(str.length==5){
				String num = str[3];
				String fileDate = str[2];
				if(!"".equals(num) && num != null && date.equals(fileDate)){
					nums = Integer.parseInt(num);
				}
			}
			nums= nums + i;

			fileName = filePath + fileName  + ".log";
			name = fileName;
		}
		return name;
	}
	/**
	 * 获取几天前的日期,格式yyyyMMdd
	 * @param days 天数
	 * @return
	 */
	public static String getBeforeDate(int days){
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, -days);
		Date resultDate = cal.getTime();
		return sdf.format(resultDate);
	}

	/**
	 *
	 * @param fileName 文件名称（包括路径）
	 * @param MaxDaysIndex 计算日志文件个数，当达到最大保留数时，将最早生成的日志文件删除
	 */
	public static void countFile(String fileName,int MaxDaysIndex){
		String delDate = getBeforeDate(MaxDaysIndex);
		String filePath = fileName.substring(0, fileName.lastIndexOf("/"));
		File file = new File(filePath);
		if(file.isDirectory()){
			File[] files = file.listFiles(new CustomLogFileFilter(delDate));
			if(files != null && files.length>0){
				for(File temp:files){
					temp.delete();
				}
			}
		}
	}

}

