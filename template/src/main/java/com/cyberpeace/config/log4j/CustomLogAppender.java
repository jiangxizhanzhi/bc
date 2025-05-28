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