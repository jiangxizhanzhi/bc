package com.cyberpeace.cs.service.impl;

import java.io.File;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.ShellCommand;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.ShellUtils;
import com.cyberpeace.common.util.TarGzUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.UpdateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdateServiceImpl implements UpdateService {

	@Override
	public void backup() {
		String backup = AttachmentPath.UPDATE_BACKUP.getDesc();
		String backupData = backup + TimeUtil.format9(new Date());

		try {
			String backupWar = backup + CommonConstant.UPDATE_WAR_NAME;
			FileUtil.deleteDir(backupWar);

			// 备份后台代码
			FileUtil.copyFile(CommonConstant.WAR_FILE_PATH, backupWar);
			FileUtil.copyFile(CommonConstant.WAR_FILE_PATH,
					backupData + File.separator + CommonConstant.UPDATE_WAR_NAME);

			String backupPage = backup + CommonConstant.BACKUP_PAGES_NAME;
			FileUtil.deleteDir(backupPage);

			// 备份前端代码
			TarGzUtils.compress(CommonConstant.PAGES_FILE_PATH, backup,
					CommonConstant.BACKUP_PAGES_NAME, true);
			TarGzUtils.compress(CommonConstant.PAGES_FILE_PATH, backupData,
					CommonConstant.BACKUP_PAGES_NAME, true);
		} catch (Exception e) {
			log.error("Backup file failed! catch error:", e);
			throw new BusinessException(ErrorCode.BACKUP_FILE_FAILED);
		}
	}

	@Override
	public String decompress(MultipartFile updateFile) {

		String updatePath = AttachmentPath.UPDATE.getDesc();

		// 删除之前的记录
		File destFile = new File(updatePath, updateFile.getOriginalFilename());
		File war = new File(updatePath, CommonConstant.UPDATE_WAR_NAME);
		File pages = new File(updatePath, CommonConstant.BACKUP_PAGES_NAME);
		FileUtil.deleteDir(destFile);
		FileUtil.deleteDir(war);
		FileUtil.deleteDir(pages);

		File saveFile = FileUtil.saveFile(updateFile, updatePath, false);
		TarGzUtils.decompress(saveFile.getAbsolutePath(), updatePath);

		return updatePath;
	}

	@Override
	public void restore() {
		String backup = AttachmentPath.UPDATE_BACKUP.getDesc();
		String backupWar = backup + CommonConstant.UPDATE_WAR_NAME;
		String backPage = backup + CommonConstant.BACKUP_PAGES_NAME;

		// 回滚war
		FileUtil.copyFile(backupWar, CommonConstant.WAR_FILE_PATH);

		// 回滚页面
		FileUtil.deleteDir(CommonConstant.PAGES_FILE_PATH);
		TarGzUtils.decompress(backPage, CommonConstant.APPS_FILE_PATH);
	}

	@Override
	public void restart() {
		String update = AttachmentPath.UPDATE.getDesc();
		String updateWar = update + CommonConstant.UPDATE_WAR_NAME;
		if (!new File(updateWar).exists()) {
			return;
		}

		String updateScript = update + CommonConstant.UPDATE_SCRIPT_NAME;
		FileUtil.writeToDestFile(ShellCommand.RESTART_TOMCAT, updateScript);
		String updateLog = update + CommonConstant.UPDATE_SCRIPT_LOG;
		ShellUtils.execShell(String.format(ShellCommand.EXEC_SH, updateScript, updateLog));
	}

	@Override
	public void update(String outPath) {
		File war = new File(outPath + CommonConstant.UPDATE_WAR_NAME);
		File page = new File(outPath + CommonConstant.BACKUP_PAGES_NAME);

		try {
			updateWar(war);
			updatePage(page);
		} catch (Exception e) {
			log.error("Update failed! catch error:", e);
			restore();
			throw new BusinessException(ErrorCode.UPDATE_FILE_FAILED);
		}

	}

	/**
	 * 更新后端war
	 */
	private void updateWar(File updateWar) {
		if (!updateWar.exists()) {
			return;
		}
		FileUtil.copyFile(updateWar, new File(CommonConstant.WAR_FILE_PATH));
	}

	/**
	 * 更新前端页面
	 */
	private void updatePage(File updatePage) {
		if (!updatePage.exists()) {
			return;
		}

		// 将前端文件解压到临时文件夹
		String current = TimeUtil.format9(new Date());
		File tempPath = new File(updatePage.getParent(), current);
		if (!tempPath.exists()) {
			tempPath.mkdirs();
		}

		try {
			TarGzUtils.decompress(updatePage.getAbsolutePath(), tempPath.getAbsolutePath());
			// 更新前端文件
			File pagesFile = new File(tempPath.getAbsolutePath(), "pages");
			File[] pages = pagesFile.listFiles();
			for (File page : pages) {
				updateSubPage(page);
			}
		} catch (Exception e) {
			log.error("File update failed! catch error:", e);
			throw new BusinessException(ErrorCode.UPDATE_FILE_FAILED);
		} finally {
			FileUtil.deleteDir(tempPath);
		}
	}

	private void updateSubPage(File subPage) {
		if (subPage.isDirectory()
				|| !subPage.getName().endsWith(FileSunffixConstant.TAR.getDesc())) {
			return;
		}
		String fileName = FileUtil.getFileName(subPage.getAbsolutePath(), false);
		String rmPage = CommonConstant.PAGES_FILE_PATH + File.separator + fileName;
		FileUtil.deleteDir(rmPage);
		TarGzUtils.decompress(subPage.getAbsolutePath(), CommonConstant.PAGES_FILE_PATH);
	}
}
