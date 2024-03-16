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

@Slf4j
public class CustomLogAppender extends FileAppender {

    private String datePattern = "yyyy-MM-dd";

    private int maxBackupIndex = 5;

    private int maxDaysIndex = 15;

    private long maxFileSize = 20971520L;

    private String scheduledFilename;

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

    @Override
    protected void setQWForFiles(Writer writer) {
        qw = new CustomCountingQuietWriter(writer, errorHandler);
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = OptionConverter.toFileSize(maxFileSize, 20971520L);
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public void setMaxDaysIndex(int maxDaysIndex) {
        this.maxDaysIndex = maxDaysIndex;
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

    public static String getFileName(String filePath) {
        File file = new File(filePath);
        return file.getName().substring(0, file.getName().lastIndexOf("."));
    }

    /**
     * 按文件大小切片
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
     * 这个方法是写操作真正的执行过程.
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