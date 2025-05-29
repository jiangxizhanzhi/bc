package com.cyberpeace.config.log4j;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;

import com.cyberpeace.common.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 自定义日志附加程序
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see FileAppender
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 功能概述 
 * 该代码实现了一个自定义的日志附加程序 `CustomLogAppender`，继承自 `org.apache.log4j.FileAppender`。它扩展了标准日志文件追加功能，支持按时间或文件大小进行日志滚动（roll over），并提供了多种配置选项，如最大备份文件数量、最大文件大小、日期格式等。 
 * --- 
 * #### 主要功能模块 
 * 1. **属性定义**： 
 * - 定义了多个属性，包括日志文件的日期模式 (`datePattern`)、最大备份文件数量 (`maxBackupIndex`)、最大保留天数 (`maxDaysIndex`)、最大文件大小 (`maxFileSize`) 等。 
 * - 提供了 getter 和 setter 方法以访问和修改这些属性。 
 * 2. **构造函数**： 
 * - 提供了两个构造函数：一个无参构造函数和一个带有布局、文件名和最大备份索引参数的构造函数。 
 * - 构造函数中调用了 `activateOptions()` 方法以初始化相关配置。 
 * 3. **日志滚动逻辑**： 
 * - 实现了两种日志滚动方式： 
 * - **按时间滚动**：通过 `rollOverByTime()` 方法，根据日期模式和当前时间决定是否滚动文件。 
 * - **按文件大小滚动**：通过 `rollOverBySize()` 方法，当文件大小超过指定值时触发滚动。 
 * 4. **日志写入逻辑**： 
 * - 覆写了父类的 `subAppend(LoggingEvent event)` 方法，在每次写入日志之前检查是否需要滚动文件。 
 * - 如果需要滚动，则调用相应的滚动方法。 
 * 5. **辅助方法**： 
 * - `computeCheckPeriod()`：计算日志滚动的时间周期。 
 * - `getNextCheckDate()`：根据给定的类型计算下一次滚动时间。 
 * - `getMaxDaysIndexDates()`：生成最近 `maxDaysIndex` 天的日期列表。 
 * - `getFileName()` 和 `getFileNameTime()`：从文件名中提取日期信息。 
 * --- 
 * #### 关键点总结 
 * - **灵活性**：支持多种日志滚动策略，可根据需求灵活配置。 
 * - **安全性**：在文件操作中加入了异常处理，避免因文件操作失败导致程序崩溃。 
 * - **性能优化**：通过检查当前时间和上次检查时间，避免频繁的文件操作。 
 * - **扩展性**：提供了丰富的配置选项和辅助方法，便于后续扩展和维护。 
 * --- 
 * #### 可能的改进点 
 * 1. **线程安全**：在多线程环境下，某些方法可能需要同步处理以保证线程安全。 
 * 2. **日志清理机制**：删除旧日志文件的操作可以进一步优化，例如增加并发处理能力。 
 * 3. **日志格式化**：目前仅使用默认的 `SimpleDateFormat`，可以考虑支持更多的日期格式化选项。 
 * --- 
 * #### 总结 
 * 该代码实现了功能强大且灵活的日志管理工具，适用于需要复杂日志管理的场景。通过合理的配置和滚动策略，能够有效管理和控制日志文件的大小和数量，避免磁盘空间不足的问题。 
**/
@Slf4j
public class CustomLogAppender extends FileAppender {

    /**
     * 日期模式
     *
     * @see String
     */
    private String datePattern = "yyyy-MM-dd";

    /**
     * 最大备份编号
     */
    private int maxBackupIndex = 5;

    /**
     * 最大日编号
     */
    private int maxDaysIndex = 15;

    /**
     * 最大文件size
     */
    private long maxFileSize = 20971520L;

    /**
     * @see String
     */
    private String scheduledFilename;

    /**
     * 下次检查
     */
    private long nextCheck = System.currentTimeMillis() - 1;

    /**
     * 类型
     */
    private int type = -1;

    /**
     * 现在
     *
     * @see Date
     */
    Date now = new Date();

    /**
     * 自卫队
     *
     * @see SimpleDateFormat
     */
    SimpleDateFormat sdf;

    /**
     * The default constructor does nothing.
     *
     * @throws
     */
    public CustomLogAppender() {
    }

    /**
     * 改造过的构造器
     *
     * @param layout
     * @param filename       文件名
     * @param maxBackupIndex 最大备份编号
     * @throws @throws IOException io异常
     * @see Layout
     * @see String
     * @see int
     */
    public CustomLogAppender(Layout layout, String filename, int maxBackupIndex) throws IOException {
        super(layout, filename, true);
        this.maxBackupIndex = maxBackupIndex;
        activateOptions();
    }

    /**
     * 设置qw用于文件
     *
     * @param writer 作家
     * @throws
     * @see Writer
     */
    protected void setQWForFiles(Writer writer) {
        qw = new CustomCountingQuietWriter(writer, errorHandler, this.encoding);
    }

    /**
     * 设置最大文件size
     *
     * @param maxFileSize 最大文件大小
     * @throws
     * @see String
     */
    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = OptionConverter.toFileSize(maxFileSize, 20971520L);
    }

    /**
     * 设置日期pattern
     *
     * @param datePattern 日期模式
     * @throws
     * @see String
     */
    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    /**
     * 设置最大日编号
     *
     * @param maxDaysIndex 最大日编号
     * @throws
     * @see int
     */
    public void setMaxDaysIndex(int maxDaysIndex) {
        this.maxDaysIndex = maxDaysIndex;
    }

    /**
     * 获取最大备份编号
     *
     * @return int
     * @throws
     * @see int
     */
    public int getMaxBackupIndex() {
        return maxBackupIndex;
    }

    /**
     * 设置最大备份编号
     *
     * @param maxBackupIndex 最大备份编号
     * @throws
     * @see int
     */
    public void setMaxBackupIndex(int maxBackupIndex) {
        this.maxBackupIndex = maxBackupIndex;
    }

    /**
     * 获取日期模式
     *
     * @return 字符串
     * @throws
     * @see String
     */
    public String getDatePattern() {
        return datePattern;
    }

    /**
     * 初始化本Appender对象的时候调用一次
     *
     * @throws
     */
    @Override
    public void activateOptions() {
        super.activateOptions();
        if (fileName != null) {
            sdf = new SimpleDateFormat(datePattern);
            File file = new File(fileName);
            // 获取最后更新时间拼成的文件名
            scheduledFilename = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + sdf.format(new Date(file.lastModified())) + "." + FileUtil.getFileSuffix(fileName);
        } else {
            log.error("File is not set for appender [" + name + "].");
        }
        if (maxBackupIndex <= 0) {
            log.error("maxBackupIndex reset to default 5,original value is:" + maxBackupIndex);
            maxBackupIndex = 5;
        }
        if (maxDaysIndex <= 0) {
            log.error("maxDaysIndex reset to default 15,original value is:" + maxDaysIndex);
            maxDaysIndex = 15;
        }
        if (maxFileSize <= 0) {
            log.error("maxFileSize reset to default 20MB,original value is:" + maxFileSize);
            maxFileSize = 20971520L;
        }
    }

    /**
     * 按时间切片
     *
     * @throws @throws IOException io异常
     */
    void rollOverByTime() throws IOException {
        String datedFilename = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + sdf.format(now) + "." + FileUtil.getFileSuffix(fileName);
        // 如果上次写的日期跟当前日期相同，不需要换文件
        if (scheduledFilename.equals(datedFilename)) {
            return;
        }
        // close current file, and rename it to datedFilename
        this.closeFile();
        File target = new File(scheduledFilename);
        if (target.exists()) {
            try {
                target.delete();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        File file = new File(fileName);
        boolean result = file.renameTo(target);
        if (result) {
            log.debug(fileName + " -> " + scheduledFilename);
        } else {
            log.error("Failed to rename [" + fileName + "] to [" + scheduledFilename + "].");
        }


        try {
            // This will also close the file. This is OK since multiple
            // close operations are safe.
            this.setFile(fileName, false, this.bufferedIO, this.bufferSize);
        } catch (IOException e) {
            errorHandler.error("setFile(" + fileName + ", false) call failed.");
        }

        scheduledFilename = datedFilename;
        // 更新最后更新日期戳

        // 删除过期文件
        if (maxDaysIndex > 0) {
            File folder = new File(file.getParent());
            List<String> maxDaysIndexDates = getMaxDaysIndexDates();
            for (File ff : folder.listFiles()) {
                // 遍历目录，将日期不在备份范围内的日志删掉
                // 首次启动当前时间和上次时间不相同时，无法调用CommonConstant.ZERO模块导致项目启动失败，重新实现getFileName方法
                if (ff.getName().startsWith(getFileName(file.getPath()) + "_") && !ff.getName().equals(file.getName())) {
                    // 获取文件名带的日期时间戳
                    String markedDate = getFileNameTime(ff);
                    if (!maxDaysIndexDates.contains(markedDate)) {
                        if (ff.delete()) {
                            log.debug(ff.getName() + " -> deleted ");
                        } else {
                            log.error("Failed to deleted old DayRollingFileAppender file :" + ff.getName());
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取文件名称
     *
     * @param filePath
     * @return 字符串
     * @throws
     * @see String
     * @see String
     */
    public static String getFileName(String filePath) {
        File file = new File(filePath);
        return file.getName().substring(0, file.getName().lastIndexOf("."));
    }

    /**
     * 按文件大小切片
     *
     * @throws
     */
    void rollOverBySize() {

        log.debug("maxBackupIndex=" + this.maxBackupIndex);
        boolean renameSucceeded = true;
        if (this.maxBackupIndex > 0) {
            File file = new File(this.fileName);
            String realFileName = file.getName().substring(0, file.getName().indexOf("."));
            file = new File(file.getParent() + File.separator + realFileName + "_" + sdf.format(now) + '.' + this.maxBackupIndex + ".log");
            if (file.exists()) {
                renameSucceeded = file.delete();
            }

            File target;
            for (int i = this.maxBackupIndex - 1; i >= 1 && renameSucceeded; --i) {
                file = new File(file.getParent() + File.separator + realFileName + "_" + sdf.format(now) + '.' + i + ".log");
                if (file.exists()) {
                    target = new File(file.getParent() + File.separator + realFileName + "_" + sdf.format(now) + '.' + (i + 1) + ".log");
                    log.debug("Renaming file " + file + " to " + target);
                    renameSucceeded = file.renameTo(target);
                }
            }

            if (renameSucceeded) {
                target = new File(file.getParent() + File.separator + realFileName + "_" + sdf.format(now) + '.' + 1 + ".log");
                this.closeFile();
                file = new File(this.fileName);
                log.debug("Renaming file " + file + " to " + target);
                renameSucceeded = file.renameTo(target);
                if (!renameSucceeded) {
                    try {
                        this.setFile(this.fileName, true, this.bufferedIO, this.bufferSize);
                    } catch (IOException var6) {
                        if (var6 instanceof InterruptedIOException) {
                            Thread.currentThread().interrupt();
                        }
                        log.error("setFile(" + this.fileName + ", true) call failed.", var6);
                    }
                }
            }
        }

        if (renameSucceeded) {
            try {
                this.setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
            } catch (IOException var5) {
                if (var5 instanceof InterruptedIOException) {
                    Thread.currentThread().interrupt();
                }
                log.error("setFile(" + this.fileName + ", false) call failed.", var5);
            }
        }
    }

    /**
     * Actual writing occurs here.
     * <p>
     * <p>
     * 这个方法是写操作真正的执行过程.
     *
     * @param event 事件
     * @throws
     * @see LoggingEvent
     */
    @Override
    protected void subAppend(LoggingEvent event) {

        long n = System.currentTimeMillis();
        if (n >= nextCheck) {

            // 在每次写操作前判断一下是否需要滚动文件
            now.setTime(n);
            Calendar calendar = Calendar.getInstance();
            if (type < 0) {
                computeCheckPeriod();
            }
            nextCheck = getNextCheckDate(calendar, now, 1).getTimeInMillis();
            try {
                rollOverByTime();
            } catch (IOException ioe) {
                log.error("rollOver() failed.", ioe);
            }
        }

        super.subAppend(event);

        if (this.fileName != null) {
            File file = new File(fileName);
            if (file.length() >= this.maxFileSize ) {
                this.rollOverBySize();
            }
        }

    }

    /**
     * 获取前maxDaysIndex天的日期
     *
     * @return list<字符串>
     * @throws
     * @see List<String>
     */
    List<String> getMaxDaysIndexDates() {

        List<String> result = new ArrayList<>();
        if (maxDaysIndex > 0) {
            for (int i = 1; i <= maxDaysIndex; i++) {
                Calendar calendar = Calendar.getInstance();
                getNextCheckDate(calendar, now, -i);
                result.add(sdf.format(calendar.getTime()));
            }
        }

        return result;
    }

    /**
     * 根据datePattern来设定type
     *
     * @return int
     * @throws
     * @see int
     */
    int computeCheckPeriod() {
        Calendar calendar = Calendar.getInstance();
        Date epoch = new Date(0L);
        if (this.datePattern != null) {
            for (int i = 0; i <= 5; ++i) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.datePattern);
                String r0 = simpleDateFormat.format(epoch);
                this.type = i;
                Date next = this.getNextCheckDate(calendar, epoch, 1).getTime();
                String r1 = simpleDateFormat.format(next);
                if (!r0.equals(r1)) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * 根据type来获取下i的时间
     *
     * @param calendar 日历
     * @param now      现在
     * @param i
     * @return 日历
     * @throws
     * @see Calendar
     * @see Date
     * @see int
     * @see Calendar
     */
    public Calendar getNextCheckDate(Calendar calendar, Date now, int i) {
        calendar.setTime(now);
        switch (this.type) {
            case 0:
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.MINUTE, i);
                break;
            case 1:
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.HOUR_OF_DAY, i);
                break;
            case 2:
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                if (hour < 12) {
                    calendar.set(Calendar.HOUR_OF_DAY, 12);
                } else {
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.add(Calendar.DAY_OF_MONTH, i);
                }
                break;
            case 3:
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                break;
            case 4:
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.WEEK_OF_YEAR, i);
                break;
            case 5:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.MONTH, i);
                break;
            default:
                throw new IllegalStateException("Unknown periodicity type.");
        }
        return calendar;
    }

    /**
     * 获取文件名中的日期
     *
     * @param file 文件
     * @return 字符串
     * @throws
     * @see File
     * @see String
     */
    String getFileNameTime(File file) {
        String fileName = file.getName().substring(0, file.getName().indexOf("."));
        String[] str = fileName.split("_");
        if (str.length < 2) {
            return null;
        }
        return str[1];
    }

}
