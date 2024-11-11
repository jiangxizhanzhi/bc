package com.cyberpeace.config.log4j;

import org.apache.log4j.helpers.QuietWriter;
import org.apache.log4j.spi.ErrorHandler;

import java.io.IOException;
import java.io.Writer;

/**
 * 重写CustomCountingQuietWriter
 * 计算长度时,设置字符级
 * Created by xxx on 2018/7/24.
 */
public class CustomCountingQuietWriter extends QuietWriter {
    // 记录已写入字符的数量
    protected long count;
    // 字符编码格式
    protected String encoding;

    /**
     * 构造函数
     * 初始化父类QuietWriter，并设置默认编码格式为UTF-8
     *
     * @param writer 用于输出的Writer对象
     * @param eh 错误处理对象
     * @param encoding 字符编码格式
     */
    public CustomCountingQuietWriter(Writer writer, ErrorHandler eh, String encoding) {
        super(writer, eh);
        this.encoding = "utf-8";
    }

    /**
     * 写入字符串并计算长度
     * 重写write方法，计算写入字符的长度，并累加到count中
     *
     * @param string 要写入的字符串
     */
    public void write(String string) {
        try {
            this.out.write(string);
            // 计算字符串长度，累加到count中
//            this.count += (long)string.getBytes(this.encoding).length;
            this.count += (long)string.length();
        } catch (IOException var3) {
            // 当写入操作失败时，调用错误处理器处理错误
            this.errorHandler.error("Write failure.", var3, 1);
        }

    }

    /**
     * 获取已写入字符的数量
     *
     * @return 已写入字符的数量
     */
    public long getCount() {
        return this.count;
    }

    /**
     * 设置已写入字符的数量
     *
     * @param count 要设置的字符数量
     */
    public void setCount(long count) {
        this.count = count;
    }
}

