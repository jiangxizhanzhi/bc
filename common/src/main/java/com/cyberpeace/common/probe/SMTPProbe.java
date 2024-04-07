package com.cyberpeace.common.probe;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SMTPProbe {

	private String host;

	private Integer port;

	public static final String PROTOCOL = "smtp";

	public SMTPProbe(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	public boolean probe() {
		String opensslCmd = String.format("openssl s_client -connect %s:%d -tls1_2", this.host,
				this.port);
		String[] cmd;
		if (OsUtil.isLinux()) {
			cmd = new String[] { "/bin/sh", "-c", opensslCmd };
		} else if (OsUtil.isWindows()) {
			cmd = new String[] { "cmd.exe", "/c", opensslCmd };
		} else {
			return true;
		}
		log.info("smtp probe cmd: {}", String.join(" | ", cmd));
		return opensslProbe(cmd);
	}

	private boolean opensslProbe(String[] command) {
		boolean flag = false;

		Process process = null;
		BufferedReader reader = null;
		String line = "";

		StringBuilder exeResult = new StringBuilder();
		try {
			process = Runtime.getRuntime().exec(command);
			// 关闭输出，避免死锁
			process.getOutputStream().close();
			process.getErrorStream().close();
			// 获取标准输出
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = reader.readLine()) != null) {
				exeResult.append(line).append("\n");
				if (line.startsWith("220 ") && line.endsWith("ready")) {
					flag = true;
				}
			}

			process.waitFor();
			log.info("smtp probe res:\n{}", exeResult);
		} catch (Exception e) {
			log.error("smtp probe error: ", e);
		} finally {
			try {
				if (null != process) {
					process.destroy();
					process = null;
				}
				if (null != reader) {
					reader.close();
				}
			} catch (Exception e) {}
		}
		return flag;
	}

}

class OsUtil {

	private static final String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isLinux() {
		return OS.contains("linux");
	}

	public static boolean isWindows() {
		return OS.contains("windows");
	}
}
