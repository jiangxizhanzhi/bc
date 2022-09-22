package com.cyberpeace.cs.service;

import org.springframework.web.multipart.MultipartFile;

public interface UpdateService {

    /**
     * 文件备份
     * @return
     */
    void backup();

    /**
     * 更新文件上传解压
     * @param updateFile
     * @return
     */
    String decompress(MultipartFile updateFile);

    /**
     * 恢复备份
     */
    void restore();

    /**
     * 重启服务
     */
    void restart();

    /**
     * 服务更新
     * @param outPath
     */
    void update(String outPath);
}
