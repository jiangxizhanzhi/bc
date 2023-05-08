package com.cyberpeace.job;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.cs.service.SystemUseStatusService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统使用状态工作
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Slf4j
//@Component
public class SystemUseStatusJob {
	@Autowired
	private SystemUseStatusService systemUseStatusService;

	private boolean insertSystemUseStatus;

	@PostConstruct
	public void init() {{
		insertSystemUseStatus = Boolean.parseBoolean(ConfigUtil.getContextProperty("ojj.insertSystemUseStatus"));
	}}

	@Scheduled(fixedRate =  5 * 60 * 1000)
	public void insertSystemUseStatus() {
		log.info("system use status start----------");
		String diskCmd = "df -h /";
		String memoryCmd = "cat /proc/meminfo";
		String cpuCmd = " top -b -n 1";
		if (insertSystemUseStatus) {
			List<String> cpuInfo = execShell(cpuCmd);
			log.info("cpuInfo:{}", cpuInfo);
			List<String> disk = execShell(diskCmd);
			log.info("disk:{}", disk);
			List<String> ram = execShell(memoryCmd);
			log.info("ram:{}", ram);
			double cpu = getCpuUsage(cpuInfo);
			double unixDickUsage = getUnixDiskUsage(disk);
			double memoryUsage = getMemoryUsage(ram);
			systemUseStatusService.insert(String.valueOf(cpu), String.valueOf(memoryUsage), String.valueOf(unixDickUsage));
			log.info("system use status end--------");
		}
	}

	/**
	 * 清理无用数据定时任务
	 */
	@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	public void deleteSystemUseStatus() {

		systemUseStatusService.delete();
	}

	/**
	 * 获取内存的使用率
	 *
	 * @return 内存使用率 0.36
	 */
	public static double getMemoryUsage(List<String> resultInfo) {
		List<String> infos = resultInfo.subList(0,5);
		Map<String, Long> map = new HashMap<>();
		infos.forEach(info->{
			String[] strings = info.split(":");
			map.put(strings[0], strings.length == 2 ? Long.parseLong(strings[1].replace("kB","").trim()) : 1L);
		});
		Long memTotal = map.get("MemTotal");
		Long memFree = map.get("MemFree");
		Long buffers = map.get("Buffers");
		Long cached = map.get("Cached");
		return MathUtils.divide((double) (memTotal - (memFree + buffers + cached)),Double.valueOf(memTotal),2)*100;
	}

	/**
	 * 获取CPU的使用率
	 *
	 * @return CPU使用率 0.36
	 */
	public static Double getCpuUsage(List<String> cpuInfo) {
		return Double.valueOf(Arrays.asList(cpuInfo.get(1).split("\\s+")).get(1));

	}
	/**
	 * 获取disk使用率
	 *
	 * @param resultInfo 命令返回信息
	 * @return 处理后获得的硬盘使用率
	 */
	public static double getUnixDiskUsage(List<String> resultInfo) {
		return Double.parseDouble(resultInfo.get(1).split("\\s+")[4].replace("%", ""));
	}

	public static List<String> execShell(String shell) {
		log.info("Start exec shell! command=[{}]", shell);

		Process process;
		List<String> processResult = Lists.newArrayList();
		StringBuilder builder = new StringBuilder();
		try {
			process = Runtime.getRuntime().exec(shell);
			BufferedReader input = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				builder.append(line).append(System.lineSeparator());
				processResult.add(line);
			}
			log.info(builder.toString());
		} catch (Exception e) {
			log.error("Exec shell error! command=[{}], catch error:", shell, e);
		}
		log.info("End exec shell!");
		return processResult;
	}
}