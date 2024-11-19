package com.cyberpeace.job;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.threadlocal.ThreadLocalContext;
import com.cyberpeace.cs.service.SystemUseStatusService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统使用状态工作
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Slf4j
@Component
public class SystemUseStatusJob {
	/**
	 * 系统使用状态Service
	 *
	 * @see SystemUseStatusService
	 */
	@Autowired
	private SystemUseStatusService systemUseStatusService;

	/**
	 * 插入系统使用状态
	 */
	private boolean insertSystemUseStatus;

	/**
	 * 初始化
	 *
	 * @throws
	 */
	@PostConstruct
	public void init() {
		insertSystemUseStatus = Boolean.parseBoolean(ConfigUtil.getContextProperty("ojj.insertSystemUseStatus"));
	}

	/**
	 * 插入系统使用状态
	 *
	 * @throws
	 */
	@Scheduled(fixedRate =  5 * 60 * 1000)
	public void insertSystemUseStatus() {
		log.info("insertSystemUseStatus system use status start----------");
		String diskCmd = "df -h /";
		String memoryCmd = "cat /proc/meminfo";
		String cpuCmd = " top -b -n 1";
		if (insertSystemUseStatus) {
			try {
				ThreadLocalContext.whiteUpdateJobThreadLocal.set(true);
				List<String> cpuInfo = execShell(cpuCmd);
				log.info("cpuInfo:{}", cpuInfo.get(2));
				List<String> disk = execShell(diskCmd);
				log.info("disk:{}", disk);
				List<String> ram = execShell(memoryCmd);
				log.info("ram:{}", ram);
				double cpu = getCpuUsage(cpuInfo);
				double unixDickUsage = getUnixDiskUsage(disk);
				double memoryUsage = getMemoryUsage(ram);
				systemUseStatusService.insert(String.valueOf(cpu), String.valueOf(memoryUsage), String.valueOf(unixDickUsage));
			}finally {
				ThreadLocalContext.whiteUpdateJobThreadLocal.remove();
			}
		}
		log.info("insertSystemUseStatus system use status end--------");
	}

	/**
	 * 清理无用数据定时任务
	 *
	 * @throws
	 */
	@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	public void deleteSystemUseStatus() {

		systemUseStatusService.delete();
	}

	/**
	 * 获取内存的使用率
	 *
	 * @param resultInfo 结果信息
	 * @return 内存使用率 0.36
	 * @throws
	 * @see List<String>
	 * @see double
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
	 * @param cpuInfo CPU信息
	 * @return CPU使用率 0.36
	 * @throws
	 * @see List<String>
	 * @see Double
	 */
	public static Double getCpuUsage(List<String> cpuInfo) {
		Double id = Double.parseDouble(Arrays.asList(cpuInfo.get(2).split("\\s+")).get(7));
		DecimalFormat df = new DecimalFormat("#.#");
		df.setRoundingMode(RoundingMode.DOWN);
		return Double.parseDouble(df.format(100 - id));
	}

	/**
	 * 获取disk使用率
	 *
	 * @param resultInfo 命令返回信息
	 * @return 处理后获得的硬盘使用率
	 * @throws
	 * @see List<String>
	 * @see double
	 */
	public static double getUnixDiskUsage(List<String> resultInfo) {
		return Double.parseDouble(resultInfo.get(1).split("\\s+")[4].replace("%", ""));
	}

	/**
	 * 执行 shell
	 *
	 * @param shell 壳
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see List<String>
	 */
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