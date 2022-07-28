package com.cyberpeace.config.CustomLog4j;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;

import com.cyberpeace.common.util.FileUtil;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 扩展的一个按天滚动的appender类, 暂时不支持datePattern设置, 但是可以配置maxBackupIndex
 *
 * @author Dan Shan
 */
public class CustomLogAppender extends FileAppender {


    private String datePattern = "yyyy-MM-dd-HH-mm";
    /**
     * 最多文件增长个数
     */
    private int maxBackupIndex = 5;

    private int maxDaysIndex = 15;

    private long maxFileSize = 20480L;

    /**
     * 文件名+上次最后更新时间
     */
    private String scheduledFilename;

    private long nextRollover = 0L;

    /**
     * The next time we estimate a rollover should occur.
     */
    private long nextCheck = System.currentTimeMillis() - 1;

    private int type = -1;

    Date now = new Date();
    SimpleDateFormat sdf;

    /**
     * The default constructor does nothing.
     */
    public CustomLogAppender() {
    }

    /**
     * 改造过的构造器
     */
    public CustomLogAppender(Layout layout, String filename, int maxBackupIndex) throws IOException {
        super(layout, filename, true);
        this.maxBackupIndex = maxBackupIndex;
        activateOptions();
    }

    protected void setQWForFiles(Writer writer) {
        qw = new CustomCountingQuietWriter(writer, errorHandler, this.encoding);
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public void setMaxDaysIndex(int maxDaysIndex) {
        maxDaysIndex = maxDaysIndex;
    }

    public int getMaxBackupIndex() {
        return maxBackupIndex;
    }

    public void setMaxBackupIndex(int maxBackupIndex) {
        this.maxBackupIndex = maxBackupIndex;
    }

    public String getDatePattern() {
        return datePattern;
    }

    /**
     * 初始化本Appender对象的时候调用一次
     */
    @Override
    public void activateOptions() {
        super.activateOptions();
        if (fileName != null) {
            // perf.log now.setTime(System.currentTimeMillis());
            sdf = new SimpleDateFormat(datePattern);
            File file = new File(fileName);
            String[] strings = fileName.split(".");
            // 获取最后更新时间拼成的文件名
            scheduledFilename = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + sdf.format(new Date(file.lastModified())) + "." + FileUtil.getFileSuffix(fileName);
//            scheduledFilename = fileName + sdf.format(new Date(file.lastModified()));
        } else {
            LogLog.error("File is not set for appender [" + name + "].");
        }
        if (maxBackupIndex <= 0) {
            LogLog.error("maxBackupIndex reset to default value[2],orignal value is:" + maxBackupIndex);
            maxBackupIndex = 5;
        }
        if(maxDaysIndex <= 0){
            LogLog.error("maxDaysIndex reset to default value[2],orignal value is:" + maxDaysIndex);
            maxDaysIndex = 15;
        }
    }

    /**
     * 滚动文件的函数:<br>
     * 1. 对文件名带的时间戳进行比较, 确定是否更新<br>
     * 2. if需要更新, 当前文件rename到文件名+日期, 重新开始写文件<br>
     * 3. 针对配置的maxBackupIndex,删除过期的文件
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
            LogLog.debug(fileName + " -> " + scheduledFilename);
        } else {
            LogLog.error("Failed to rename [" + fileName + "] to [" + scheduledFilename + "].");
        }

        // 删除过期文件
        if (maxDaysIndex > 0) {
            File folder = new File(file.getParent());
            List<String> maxDaysIndexDates = getMaxDaysIndexDates();
            for (File ff : folder.listFiles()) {
                // 遍历目录，将日期不在备份范围内的日志删掉
                if (ff.getName().startsWith(FileUtil.getFileName(file.getPath(), false) + "_") && !ff.getName().equals(file.getName())) {
                    // 获取文件名带的日期时间戳
                    String markedDate = getFileNameTime(ff);
                    if (!maxDaysIndexDates.contains(markedDate)) {
                        result = ff.delete();
                    }
                    if (result) {
                        LogLog.debug(ff.getName() + " -> deleted ");
                    } else {
                        LogLog.error("Failed to deleted old DayRollingFileAppender file :" + ff.getName());
                    }
                }
            }
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
    }

    void rollOverBySize() {
        if (this.qw != null) {
            long size = ((CustomCountingQuietWriter) this.qw).getCount();
            LogLog.debug("rolling over count=" + size);
            this.nextRollover = size + this.maxFileSize;
        }

        LogLog.debug("maxBackupIndex=" + this.maxBackupIndex);
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
                    LogLog.debug("Renaming file " + file + " to " + target);
                    System.out.println("Renaming file " + file + " to " + target);
                    renameSucceeded = file.renameTo(target);
                }
            }

            if (renameSucceeded) {
                target = new File(file.getParent() + File.separator + realFileName + "_" + sdf.format(now) + '.' + 1 + ".log");
                this.closeFile();
                file = new File(this.fileName);
                LogLog.debug("Renaming file " + file + " to " + target);
                System.out.println("Renaming file " + file + " to " + target);
                renameSucceeded = file.renameTo(target);
                if (!renameSucceeded) {
                    try {
                        this.setFile(this.fileName, true, this.bufferedIO, this.bufferSize);
                    } catch (IOException var6) {
                        if (var6 instanceof InterruptedIOException) {
                            Thread.currentThread().interrupt();
                        }
                        LogLog.error("setFile(" + this.fileName + ", true) call failed.", var6);
                    }
                }
            }
        }

        if (renameSucceeded) {
            try {
                this.setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
                this.nextRollover = 0L;
            } catch (IOException var5) {
                if (var5 instanceof InterruptedIOException) {
                    Thread.currentThread().interrupt();
                }

                LogLog.error("setFile(" + this.fileName + ", false) call failed.", var5);
            }
        }
    }

    /**
     * Actual writing occurs here.
     * 这个方法是写操作真正的执行过程.
     */
    @Override
    protected void subAppend(LoggingEvent event) {

        long n = System.currentTimeMillis();
        if (n >= nextCheck) {

            // 在每次写操作前判断一下是否需要滚动文件
            now.setTime(n);
            Calendar calendar = Calendar.getInstance();
            if(type>=0 && type <= 5){
                nextCheck = getNextCheckDate(calendar, now, 1).getTimeInMillis();
            }
            else {
                nextCheck = getNextDayCheckPoint(now);
            }
            try {
                rollOverByTime();
            } catch (IOException ioe) {
                LogLog.error("rollOver() failed.", ioe);
            }
        }

        super.subAppend(event);

        if (this.fileName != null && this.qw != null) {
            long size = ((CustomCountingQuietWriter) this.qw).getCount();
            if (size >= this.maxFileSize && size >= this.nextRollover) {
                this.rollOverBySize();
            }
        }

    }

    /**
     * 获取下一天的时间变更点
     *
     * @param now
     * @return
     */
    long getNextDayCheckPoint(Date now) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);// 注意MILLISECOND,毫秒也要置0.。。否则错了也找不出来的 calendar.add(Calendar.DATE, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * 根据maxBackupIndex配置的备份文件个数，获取要保留log文件的日期范围集合
     *
     * @return list<' fileName + yyyy-MM-dd '>
     */
    List<String> getMaxDaysIndexDates() {

        List<String> result = new ArrayList<String>();
        computeCheckPeriod();
        if (maxDaysIndex > 0) {
            for (int i = 1; i <= maxDaysIndex; i++) {
                Calendar calendar = Calendar.getInstance();
                getNextCheckDate(calendar, now, -i);
                result.add(sdf.format(calendar.getTime()));
            }
        }

        return result;
    }

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
                if (r0 != null && r1 != null && !r0.equals(r1)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public Calendar getNextCheckDate(Calendar calendar, Date now, int i) {
        calendar.setTime(now);
        switch (this.type) {
            case 0:
                calendar.set(13, 0);
                calendar.set(14, 0);
                calendar.add(12, i);
                break;
            case 1:
                calendar.set(12, 0);
                calendar.set(13, 0);
                calendar.set(14, 0);
                calendar.add(11, i);
                break;
            case 2:
                calendar.set(12, 0);
                calendar.set(13, 0);
                calendar.set(14, 0);
                int hour = calendar.get(11);
                if (hour < 12) {
                    calendar.set(11, 12);
                } else {
                    calendar.set(11, 0);
                    calendar.add(5, i);
                }
                break;
            case 3:
                calendar.set(11, 0);
                calendar.set(12, 0);
                calendar.set(13, 0);
                calendar.set(14, 0);
                calendar.add(5, i);
                break;
            case 4:
                calendar.set(7, calendar.getFirstDayOfWeek());
                calendar.set(11, 0);
                calendar.set(12, 0);
                calendar.set(13, 0);
                calendar.set(14, 0);
                calendar.add(3, i);
                break;
            case 5:
                calendar.set(5, 1);
                calendar.set(11, 0);
                calendar.set(12, 0);
                calendar.set(13, 0);
                calendar.set(14, 0);
                calendar.add(2, i);
                break;
            default:
                throw new IllegalStateException("Unknown periodicity type.");
        }
        return calendar;
    }

    String getFileNameTime(File file) {
        String fileName = file.getName().substring(0, file.getName().indexOf("."));
        String[] str = fileName.split("_");
        if (str.length < 2) {
            return null;
        }
        return str[1];
    }
}