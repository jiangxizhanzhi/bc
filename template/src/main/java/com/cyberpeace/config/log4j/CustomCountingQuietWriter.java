package com.cyberpeace.config.log4j;

import org.apache.log4j.helpers.QuietWriter;
import org.apache.log4j.spi.ErrorHandler;

import java.io.IOException;
import java.io.Writer;

/**
 *
 * 重写CustomCountingQuietWriter
 * 计算长度时,设置字符级
 * Created by xxx on 2018/7/24.
 *
 * @author cyberpeace
 * @date 2023/05/06
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
