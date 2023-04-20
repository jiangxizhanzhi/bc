package com.cyberpeace.common.util;

import com.cyberpeace.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class CmdExecuter {
	public static String exec(List<String> cmd) {
		String convertedTime = null;
		Process proc = null;
		BufferedReader stdout = null;
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(cmd);
			builder.redirectErrorStream(true);
			proc = builder.start();
			stdout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line;
			int lineNumber = 1;
			List<String> returnStringList = new LinkedList<>();
			while ((line = stdout.readLine()) != null) {
				if(line.contains("error")){
					throw new BusinessException(line);
				}
				log.info("第" + lineNumber + "行:" + line);
				lineNumber = lineNumber + 1;
				returnStringList.add(FFMPEG.dealString(line));
			}
			String info = "";
			for (int i = returnStringList.size() - 1; i >= 0; i--) {
				if (null != returnStringList.get(i) && returnStringList.get(i).startsWith("frame=")) {
					info = returnStringList.get(i);
					break;
				}
			}
			if (null != info) {
				convertedTime = info.split("time=")[1].split("bitrate=")[0].trim();
			}
		} catch (IndexOutOfBoundsException ex) {
			log.info(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				proc.waitFor();
				stdout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return convertedTime;
	}

}

