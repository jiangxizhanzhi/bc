package com.cyberpeace.config.log4j;

import com.cyberpeace.common.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 自定义日志附加器类，继承自FileAppender
 * 用于将日志消息附加到文件中此自定义类可以扩展或修改日志记录行为
 */
@Slf4j
public class CustomLogAppender extends FileAppender {

    // Defines the date format pattern used for logging
    private String datePattern = "yyyy-MM-dd";

    // Defines the maximum number of backup log files
    private int maxBackupIndex = 5;

    // Defines the maximum number of days to retain log files
    private int maxDaysIndex = 15;

    // Defines the maximum size of a log file
    private long maxFileSize = 20971520L;

    // Defines the name of the scheduled log file
    private String scheduledFilename;

    // Defines the next check time for log rolling, initially set to the start time of the program
    private long nextCheck = System.currentTimeMillis() - 1;

    // Defines the log type, -1 indicates uninitialized
    private int type = -1;

    // Creates a Date object representing the current time
    Date now = new Date();

    // Creates a SimpleDateFormat object for date formatting
    SimpleDateFormat sdf;

    /**
     * The default constructor does nothing.
     */
    public CustomLogAppender() {
    }
    /**
     * 改造过的构造器
     *
     * @param layout          The layout to use.
     * @param filename        The name of the file to append to.
     * @param maxBackupIndex  The maximum number of backup files.
     * @throws IOException    If there is an error opening the file.
     */
    public CustomLogAppender(Layout layout, String filename, int maxBackupIndex) throws IOException {
        super(layout, filename, true);
        this.maxBackupIndex = maxBackupIndex;
        activateOptions();
    }

    /**
     * Sets the QuietWriter for file appender.
     *
     * @param writer  The writer to use.
     */
    protected void setQWForFiles(Writer writer) {
        qw = new CustomCountingQuietWriter(writer, errorHandler, this.encoding);
    }

    /**
     * Sets the maximum file size.
     *
     * @param maxFileSize     The maximum file size as a string.
     */
    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = OptionConverter.toFileSize(maxFileSize, 20971520L);
    }

    /**
     * Sets the date pattern.
     *
     * @param datePattern     The date pattern to use.
     */
    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    /**
     * Sets the maximum number of days to keep files.
     *
     * @param maxDaysIndex    The maximum number of days to keep files.
     */
    public void setMaxDaysIndex(int maxDaysIndex) {
        this.maxDaysIndex = maxDaysIndex;
    }

    /**
     * Returns the maximum backup index.
     *
     * @return The maximum backup index.
     */
    public int getMaxBackupIndex() {
        return maxBackupIndex;
    }

    /**
     * Sets the maximum backup index.
     *
     * @param maxBackupIndex  The maximum backup index to set.
     */
    public void setMaxBackupIndex(int maxBackupIndex) {
        this.maxBackupIndex = maxBackupIndex;
    }

    /**
     * Returns the date pattern.
     *
     * @return The date pattern.
     */
    public String getDatePattern() {
        return datePattern;
    }

    /**
     * 初始化本Appender对象的时候调用一次
     */
    @Override
    public void activateOptions() {
        super.activateOptions();
        // 检查是否设置了文件名
        if (fileName != null) {
            // 使用指定的日期模式创建SimpleDateFormat对象
            sdf = new SimpleDateFormat(datePattern);
            // 创建File对象以获取文件信息
            File file = new File(fileName);
            // 获取最后更新时间拼成的文件名
            scheduledFilename = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + sdf.format(new Date(file.lastModified())) + "." + FileUtil.getFileSuffix(fileName);
        } else {
            // 如果未设置文件名，则记录错误信息
            log.error("File is not set for appender [" + name + "].");
        }
        // 检查并设置最大备份文件索引，如果小于等于0则设置为默认值5
        if (maxBackupIndex <= 0) {
            log.error("maxBackupIndex reset to default 5,original value is:" + maxBackupIndex);
            maxBackupIndex = 5;
        }
        // 检查并设置最大天数索引，如果小于等于0则设置为默认值15
        if (maxDaysIndex <= 0) {
            log.error("maxDaysIndex reset to default 15,original value is:" + maxDaysIndex);
            maxDaysIndex = 15;
        }
        // 检查并设置最大文件大小，如果小于等于0则设置为默认值20MB
        if (maxFileSize <= 0) {
            log.error("maxFileSize reset to default 20MB,original value is:" + maxFileSize);
            maxFileSize = 20971520L;
        }
    }

    /**
     * 按时间切片
     */
    void rollOverByTime() throws IOException {
        // 根据当前时间和文件名生成新的带日期的文件名
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
     * 获取文件名
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        File file = new File(filePath);
        return file.getName().substring(0, file.getName().lastIndexOf("."));
    }

    /**
     * 按文件大小切片
     */
    void rollOverBySize() {
        // 输出最大备份索引值
        log.debug("maxBackupIndex=" + this.maxBackupIndex);
        // 标记重命名操作是否成功
        boolean renameSucceeded = true;
        // 当最大备份索引大于0时，进行文件重命名操作
        if (this.maxBackupIndex > 0) {
            // 获取当前日志文件
            File file = new File(this.fileName);
            // 提取文件名（不包含扩展名）
            String realFileName = file.getName().substring(0, file.getName().indexOf("."));
            // 构造最大备份索引对应的文件名
            file = new File(file.getParent() + File.separator + realFileName + "_" + sdf.format(now) + '.' + this.maxBackupIndex + ".log");
            // 如果该文件存在，则尝试删除
            if (file.exists()) {
                renameSucceeded = file.delete();
            }

            File target;
            // 从次高备份索引开始，逐个重命名文件
            for (int i = this.maxBackupIndex - 1; i >= 1 && renameSucceeded; --i) {
                file = new File(file.getParent() + File.separator + realFileName + "_" + sdf.format(now) + '.' + i + ".log");
                if (file.exists()) {
                    target = new File(file.getParent() + File.separator + realFileName + "_" + sdf.format(now) + '.' + (i + 1) + ".log");
                    log.debug("Renaming file " + file + " to " + target);
                    renameSucceeded = file.renameTo(target);
                }
            }

            // 如果重命名成功，关闭当前日志文件并重命名为1号备份
            if (renameSucceeded) {
                target = new File(file.getParent() + File.separator + realFileName + "_" + sdf.format(now) + '.' + 1 + ".log");
                this.closeFile();
                file = new File(this.fileName);
                log.debug("Renaming file " + file + " to " + target);
                renameSucceeded = file.renameTo(target);
                // 如果重命名失败，尝试重新设置日志文件
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

        // 如果重命名成功，重新设置日志文件为写入状态
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
     * 这个方法是写操作真正的执行过程.
     */
    @Override
    protected void subAppend(LoggingEvent event) {
        // 获取当前时间
        long n = System.currentTimeMillis();
        // 检查是否需要进行时间滚动
        if (n >= nextCheck) {
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

        // 执行实际的日志事件写入操作
        super.subAppend(event);

        // 检查文件大小，如果超过最大值，则按大小滚动
        if (this.fileName != null) {
            File file = new File(fileName);
            if (file.length() >= this.maxFileSize ) {
                this.rollOverBySize();
            }
        }
    }

    /**
     * 获取前maxDaysIndex天的日期
     * 该方法用于生成一个日期列表，列表中的每个日期都是从当前日期向前数的
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
     * 该方法通过比较不同时间点格式化后的字符串，来计算检查周期
     * 它主要用于确定日期模式中的哪一部分发生了变化
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
     * 此方法用于根据当前时间和一个整数i来计算下一次检查或操作的时间
     * 它通过修改给定的Calendar对象的特定时间字段来实现这一点
     *
     * @param calendar 一个Calendar对象，用于设置和获取时间
     * @param now 当前时间
     * @param i 增加的时间单位数量
     * @return 返回更新后的Calendar对象
     */
    public Calendar getNextCheckDate(Calendar calendar, Date now, int i) {
        calendar.setTime(now);
        switch (this.type) {
            case 0:
                // 设置秒和毫秒为0，然后增加分钟
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.MINUTE, i);
                break;
            case 1:
                // 设置分钟、秒和毫秒为0，然后增加小时
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.HOUR_OF_DAY, i);
                break;
            case 2:
                // 设置分钟、秒和毫秒为0，根据当前小时数增加到中午或下一天
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
                // 设置小时、分钟、秒和毫秒为0，然后增加天数
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.DAY_OF_MONTH, i);
                break;
            case 4:
                // 设置为本周的第一天，并将小时、分钟、秒和毫秒设为0，然后增加周数
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.WEEK_OF_YEAR, i);
                break;
            case 5:
                // 设置为本月的第一天，并将小时、分钟、秒和毫秒设为0，然后增加月数
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.add(Calendar.MONTH, i);
                break;
            default:
                // 如果type不在已知范围内，抛出异常
                throw new IllegalStateException("Unknown periodicity type.");
        }
        return calendar;
    }

    /**
     * 获取文件名中的日期
     * 此方法用于解析文件名，提取其中的日期部分
     * 假设文件名以"_"字符分隔日期和其他信息，且日期部分位于文件名的后半部分
     *
     * @param file 要解析的文件
     * @return 返回文件名中的日期部分，如果解析失败则返回null
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