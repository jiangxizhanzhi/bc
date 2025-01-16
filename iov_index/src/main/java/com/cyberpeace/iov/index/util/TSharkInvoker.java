package com.cyberpeace.iov.index.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TSharkInvoker {

	public static List<String> read(String filePath) {
		ProcessBuilder processBuilder = new ProcessBuilder("tshark", "-r", filePath);
		processBuilder.redirectErrorStream(true);

		List<String> res = Lists.newArrayList();

		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				res.add(line);
			}
			int exitCode = process.waitFor();
			log.info("TShark invoker process exited with code " + exitCode);
		} catch (IOException | InterruptedException e) {
			log.error("TShark invoker error: ", e);
			Thread.currentThread().interrupt();
		}
		return res;
	}

	private TSharkInvoker() {

	}
}
