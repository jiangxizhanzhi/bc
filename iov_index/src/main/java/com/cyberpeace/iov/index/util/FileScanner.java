package com.cyberpeace.iov.index.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.iov.index.service.bo.student.ResultFileBo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileScanner {

	public static List<ResultFileBo> scanFiles(String dirPath, boolean ascByTime) {
		Path directory = Paths.get(dirPath);
		List<ResultFileBo> res = Lists.newArrayList();
		List<Path> files = new ArrayList<>();

		try {
			Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
						throws IOException {
					files.add(file);
					return FileVisitResult.CONTINUE;
				}
			});

			for (Path path : files) {
				ResultFileBo bo = new ResultFileBo();
				try {
					BasicFileAttributes attrs = Files.readAttributes(path,
							BasicFileAttributes.class);
					bo.setTimeStamp(attrs.creationTime().toMillis());
					bo.setName(path.getFileName().toString());
					bo.setPath(path.toString());
					res.add(bo);
				} catch (IOException e) {
					log.error("read file attributes error", e);
				}
			}

			// 按创建时间排序
			Comparator<ResultFileBo> comparator = Comparator
					.comparingLong(ResultFileBo::getTimeStamp);
			if (ascByTime) {
				res.sort(comparator);
			} else {
				res.sort(comparator.reversed());
			}
		} catch (IOException e) {
			log.error("scan files error: {}", dirPath, e);
		}
		return res;
	}
}
