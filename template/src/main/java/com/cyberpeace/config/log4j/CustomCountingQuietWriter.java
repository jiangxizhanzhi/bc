package com.cyberpeace.config.log4j;

import org.apache.log4j.helpers.QuietWriter;
import org.apache.log4j.spi.ErrorHandler;

import java.io.IOException;
import java.io.Writer;

/**
 * 重写CustomCountingQuietWriter
 * 计算长度时,设置字符级
 * Created by xxx on 2018/7/24.
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see QuietWriter
 * @since 1.0.0
 */
public class CustomCountingQuietWriter extends QuietWriter {
    /**
     * 计数
     */
    protected long count;
    /**
     * 编码
     *
     * @see String
     */
    protected String encoding;


    /**
     * 自定义计数安静作家
     *
     * @param writer 作家
     * @param eh     啊
     * @throws
     * @see Writer
     * @see ErrorHandler
     */
    public CustomCountingQuietWriter(Writer writer, ErrorHandler eh) {
        super(writer, eh);
        this.encoding = "utf-8";
    }

    /**
     * 写入
     *
     * @param string 字符串
     * @throws
     * @see String
     */
    @Override
    public void write(String string) {
        try {
            this.out.write(string);
            this.count += string.length();
        } catch (IOException var3) {
            this.errorHandler.error("Write failure.", var3, 1);
        }

    }

    /**
     * 获取计数
     *
     * @return long
     * @throws
     * @see long
     */
    public long getCount() {
        return this.count;
    }

    /**
     * 设置计数
     *
     * @param count 计数
     * @throws
     * @see long
     */
    public void setCount(long count) {
        this.count = count;
    }
}
