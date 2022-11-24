package com.cyberpeace.common.component;

import java.io.*;
import java.util.*;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.compress.utils.Lists;

import com.alibaba.fastjson.JSON;

public class CsvUtil {

	/**
	 * 读取流中前面的字符，看是否有bom，如果有bom，将bom头先读掉丢弃
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static InputStream getInputStream(InputStream in) throws IOException {

		PushbackInputStream testin = new PushbackInputStream(in);
		int ch = testin.read();
		if (ch != 0xEF) {
			testin.unread(ch);
		} else if ((ch = testin.read()) != 0xBB) {
			testin.unread(ch);
			testin.unread(0xef);
		} else if ((ch = testin.read()) != 0xBF) {
			throw new IOException("错误的UTF-8格式文件");
		} else {}
		return testin;
	}

	/**
	 * 解析csv
	 *
	 * @param filePath
	
	 * @return
	 * @throws Exception
	 */
	public static List<List<String>> parseDatalistCsv(String filePath) throws Exception {

		List<List<String>> lists = Lists.newArrayList();

		File file = new File(filePath);
		// 解析xml文件得到一个List集合
		FileInputStream fileInputStream = new FileInputStream(file);
		// 读取csv文件
		// 读取流中前面的字符，看是否有bom，如果有bom，将bom头先读掉丢弃
		InputStream inputStream = getInputStream(fileInputStream);
		BufferedInputStream in = new BufferedInputStream(inputStream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in, getCharSet(in)));
		String line = "";

		while ((line = br.readLine()) != null) {
			String[] lines = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
			List<String> list = Lists.newArrayList();
			for (int i = 0; i < lines.length; i++) {
				// if (firstLines[i].startsWith("#")) {
				// firstLines[i] = firstLines[i].replaceAll("#", "");
				// }
				String s = lines[i];
				s = s.trim().replaceAll("\"\"", "\"");
				if (s.startsWith("\"")) {
					s = s.substring(1, s.length() - 1);
				}
				list.add(s);
			}
			lists.add(list);
		}
		inputStream.close();
		in.close();
		br.close();
		return lists;

	}
	
	/**
	 * 解析csv
	 *
	 * @param filePath
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> parseCsv(String filePath, Class<T> clazz) throws Exception {
		File file = new File(filePath);
		// 解析xml文件得到一个List集合
		FileInputStream fileInputStream = new FileInputStream(file);
		// 读取csv文件
		// 读取流中前面的字符，看是否有bom，如果有bom，将bom头先读掉丢弃
		InputStream inputStream = getInputStream(fileInputStream);
		BufferedInputStream in = new BufferedInputStream(inputStream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in, getCharSet(in)));
		String line = "";
		List<Map<String, Object>> lists = new ArrayList<>();
		String firstLine = br.readLine();
		String[] firstLines = firstLine.split(",");

		while ((line = br.readLine()) != null) {
			String[] lines = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
			Map<String, Object> map = new HashMap<>();
			for (int i = 0; i < lines.length; i++) {
				if (firstLines[i].startsWith("#")) {
					firstLines[i] = firstLines[i].replaceAll("#", "");
				}
				String s = lines[i];
				s = s.trim().replaceAll("\"\"", "\"");
				if (s.startsWith("\"")) {
					s = s.substring(1, s.length() - 1);
				}
				map.put(firstLines[i], s);
			}
			lists.add(map);
		}
		inputStream.close();
		in.close();
		br.close();
		if (null != lists) {
			// 使用泛型方式转换集合
			List<T> list = JSON.parseArray(JSON.toJSONString(lists), clazz);
			return list;
		}
		return null;
	}

	/**
	 * 解析csv
	 *
	 * @param file
	 * @param clazz
	 * @param <T>
	 * @param startLine 开始行
	 * @param endLine   结束行
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> parseCsv(File file, Class<T> clazz, Integer startLine,
			Integer endLine) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(file);
		// 读取csv文件
		// 读取流中前面的字符，看是否有bom，如果有bom，将bom头先读掉丢弃
		InputStream inputStream = getInputStream(fileInputStream);
		BufferedInputStream in = new BufferedInputStream(inputStream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in, getCharSet(in)));
		String line = "";
		List<Map<String, Object>> lists = new ArrayList<>();
		String firstLine = br.readLine();
		String[] firstLines = firstLine.split(",");
		Integer l = 0;
		while ((line = br.readLine()) != null) {
			l++;
			if (l <= endLine && l >= startLine) {
				String[] lines = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
				Map<String, Object> map = new HashMap<>();
				for (int i = 0; i < lines.length; i++) {
					if (firstLines[i].startsWith("#")) {
						firstLines[i] = firstLines[i].replaceAll("#", "");
					}
					String s = lines[i];
					s = s.trim().replaceAll("\"\"", "\"");
					if (s.startsWith("\"")) {
						s = s.substring(1, s.length() - 1);
					}
					map.put(firstLines[i], s);
				}
				lists.add(map);
			} else if (l > endLine) {
				break;
			}
		}
		inputStream.close();
		in.close();
		br.close();
		if (null != lists) {
			// 使用泛型方式转换集合
			List<T> list = JSON.parseArray(JSON.toJSONString(lists), clazz);
			return list;
		}
		return null;
	}

	/**
	 * 获取编码类型，解决乱码问题
	 *
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	private static String getCharSet(BufferedInputStream stream) throws Exception {
		String charSet;
		byte[] buffer = new byte[3];
		// 因流读取后再读取可能会缺少内容，此处需要先读，然后再还原
		stream.mark(stream.available() + 1);
		stream.read(buffer);
		stream.reset();
		String b0 = Integer.toHexString(buffer[0] & 0xFF);
		String b1 = Integer.toHexString(buffer[1] & 0xFF);
		String b2 = Integer.toHexString(buffer[2] & 0xFF);
		String header = b0 + b1 + b2;
		switch (header) {
			case "d5cbba":
				charSet = "GB2312";
				break;
			// case "236167":
			// charSet = "GB2312";
			// break;
			default:
				charSet = "UTF-8";
				break;
		}
		return charSet;
	}

	/**
	 * 创建csv文件
	 * @param exportData
	 * @param map
	 * @param outPutPath
	 * @param fileName
	 * @return
	 */
	public static File createCSVFile(List exportData, HashMap map, String outPutPath,
			String fileName) {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			// 定义文件名格式并创建
			csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
			System.out.println("csvFile：" + csvFile);
			// utf8使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"), 1024);
			System.out.println("csvFileOutputStream：" + csvFileOutputStream);
			// 写入文件头部
			for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
					.hasNext();) {
				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
				csvFileOutputStream.write("\"" + (String) propertyEntry.getValue() != null
						? (String) propertyEntry.getValue()
						: "" + "\"");
				if (propertyIterator.hasNext()) {
					// 使用,分隔
					csvFileOutputStream.write(",");
				}
			}
			csvFileOutputStream.newLine();
			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				Object row = (Object) iterator.next();
				for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
						.hasNext();) {
					java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
							.next();
					csvFileOutputStream.write(
							(String) BeanUtils.getProperty(row, (String) propertyEntry.getKey()));
					if (propertyIterator.hasNext()) {
						// 使用,分隔
						csvFileOutputStream.write(",");
					}
				}
				if (iterator.hasNext()) {
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}
}