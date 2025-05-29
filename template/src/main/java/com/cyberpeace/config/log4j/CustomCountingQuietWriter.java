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
/**
 * 这段代码实现了一个自定义的日志工具类 `CustomCountingQuietWriter`，它继承了 `org.apache.log4j.helpers.QuietWriter` 类，并添加了计数功能。以下是代码的主要分析： 
 * 1. **类的功能**： 
 * - `CustomCountingQuietWriter` 是一个用于记录日志输出的类，它在写入日志时会统计字符的数量。 
 * - 它通过覆盖父类的方法实现了自定义的写入逻辑。 
 * 2. **关键字段**： 
 * - `count`：用于记录写入字符的数量。 
 * - `encoding`：指定字符编码，默认为 `"utf-8"`。 
 * 3. **构造方法**： 
 * - 接受三个参数：`Writer` 对象、`ErrorHandler` 对象和字符串类型的编码。 
 * - 在构造方法中，初始化了 `encoding` 字段为 `"utf-8"`。 
 * 4. **写入方法**： 
 * - 覆盖了父类的 `write(String string)` 方法。 
 * - 在写入字符串时，不仅将内容传递给底层的 `Writer`，还更新了 `count` 字段。 
 * - 注释掉的部分使用了 `getBytes(encoding).length` 来计算字节数，而当前实现直接使用 `string.length()` 来计算字符数量。 
 * 5. **计数方法**： 
 * - `getCount()`：返回当前累计的字符数量。 
 * - `setCount(long count)`：设置新的字符数量。 
 * 6. **异常处理**： 
 * - 如果写入过程中发生 `IOException`，会调用 `errorHandler.error()` 方法来处理错误。 
 * 总结来说，这个类的主要目的是在日志写入的同时统计字符数量，适用于需要监控日志输出规模的场景。 
**/
public class CustomCountingQuietWriter extends QuietWriter {
    /**
     * 计数
     */
    protected long count;
    /**
     * @see String
     */
    protected String encoding;


    /**
     * 自定义计数安静writer
     *
     * @param writer
     * @param eh       啊
     * @param encoding 编码
     * @throws
     * @see Writer
     * @see ErrorHandler
     * @see String
     */
    public CustomCountingQuietWriter(Writer writer, ErrorHandler eh, String encoding) {
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
    public void write(String string) {
        try {
            this.out.write(string);
//            this.count += (long)string.getBytes(this.encoding).length;
            this.count += (long)string.length();
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

