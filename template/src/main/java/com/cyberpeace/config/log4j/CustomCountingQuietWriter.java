package com.cyberpeace.config.log4j;

import org.apache.log4j.helpers.QuietWriter;
import org.apache.log4j.spi.ErrorHandler;

import java.io.IOException;
import java.io.Writer;

/**
 * 重写CustomCountingQuietWriter
 * <p>
 * <p>
 * 计算长度时,设置字符级
 * <p>
 * <p>
 * Created by xxx on 2018/7/24.
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see QuietWriter
 * @since 1.0.0
 */
public class CustomCountingQuietWriter extends QuietWriter {
    protected long count;
    protected String encoding;


    public CustomCountingQuietWriter(Writer writer, ErrorHandler eh, String encoding) {
        super(writer, eh);
        this.encoding = "utf-8";
    }

    public void write(String string) {
        try {
            this.out.write(string);
//            this.count += (long)string.getBytes(this.encoding).length;
            this.count += (long)string.length();
        } catch (IOException var3) {
            this.errorHandler.error("Write failure.", var3, 1);
        }

    }

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
