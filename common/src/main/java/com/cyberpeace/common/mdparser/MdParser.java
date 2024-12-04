package com.cyberpeace.common.mdparser;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.MdConvert;
import com.cyberpeace.common.mdparser.convert.MdConvertEnum;
import com.cyberpeace.common.mdparser.convert.NoConvert;
import com.cyberpeace.common.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenzhuo
 * 仅支持标题和列表 列表仅支持二级
 */
@Slf4j
public class MdParser {

    private String HEAD_SYMBOL = "#";

    private List<String> content;

    private Map<String, String> titleMap;

    private Map<Class, MdContentParser> parserMap;

    public MdParser(String file) {
        setFile(file);
        collectParse();
    }

    public MdParser() {
        collectParse();
    }

    public void setFile(String file) {
        if (StringUtils.isBlank(file) || !new File(file).exists()) {
            log.error("File not exist! file_path=[{}]", file);
            throw new BusinessException(ErrorCode.FILE_READ_FAIL, file);
        }

        content = FileUtil.listContent(new File(file));
        titleMap = new ConcurrentHashMap<>();
    }

    public synchronized <T> T read(Class<T> cls) {
        try {
            return doRead(cls);
        } catch (IllegalAccessException e) {
            log.error("Markdown file to entity failed! cls=[{}], catch error:", cls.getName(), e);
            throw new BusinessException(ErrorCode.FILE_READ_FAIL, cls.getName());
        }
    }

    public synchronized void write(Object obj, File file) {
        doWrite(obj, file);
    }

    private void doWrite(Object obj, File file) {
        FileUtil.mkdirs(file.getParentFile().getAbsolutePath());
        if (file.exists()) {
            if (file.delete()){
                log.debug("File delete success! file_path=[{}]", file.getAbsolutePath());
            } else {
                log.error("File delete failed! file_path=[{}]", file.getAbsolutePath());
            }
        }

        Field[] fields = ObjUtil.getFields(obj);
        if (fields.length <= CommonConstant.ZERO) {
            try {
                boolean newFileFlag = file.createNewFile();
                if (!newFileFlag) {
                    log.info("doWrite failed to create new file: [{}]", file.getAbsolutePath());
                }
            } catch (IOException e) {
                log.error("File create failed! file_path=[{}]", file.getAbsolutePath());
            }
            return;
        }

        FileWriter fw = null;
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;

        try {
            osw = new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8);
            bw = new BufferedWriter(osw);

            for (Field field : fields) {
                MdHeadLine annotation = field.getAnnotation(MdHeadLine.class);
                if (null == annotation) {
                    continue;
                }

                String title = getAssignStr(annotation.level(), HEAD_SYMBOL) + CommonConstant.SPACE + annotation.name();

                Object value = ObjUtil.getFieldValueByName(field.getName(), obj);
                if (ObjectUtils.isNotEmpty(value)) {
                    Class<? extends MdConvertEnum> convert = annotation.convert();
                    if (convert != NoConvert.class) {
                        value = MdConvert.convertValue(value, convert);
                    }

                    MdContentParser parser = parserMap.get(annotation.parser());
                    value = parser.write(value);
                } else {
                    value = LocalStringUtil.frontBackLine(CommonConstant.EMPTY_STRING);
                }

                bw.write(title);
                bw.write(String.valueOf(value));
            }

            bw.flush();
        } catch (Exception e) {
            log.error("Markdown content write failed！file_path=[{}], catch error:", file.getAbsolutePath(), e);
            if (file.delete()) {
                log.debug("File delete success! file_path=[{}]", file.getAbsolutePath());
            } else {
                log.error("File delete failed! file_path=[{}]", file.getAbsolutePath());
            }
            throw new BusinessException(ErrorCode.FAILED_TO_WRITE_FILE);
        } finally {
            try {
                if (null != fw) {
                    fw.close();
                }
                if (null != bw) {
                    bw.close();
                }
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                // do nothing
            }

        }
    }

    private <T> T doRead(Class<T> cls) throws IllegalAccessException {
        mdKeyMap();
        Object instance = BeanUtils.instantiateClass(cls);

        Field[] fields = ObjUtil.getFields(cls);
        for (Field field : fields) {
            MdHeadLine annotation = field.getAnnotation(MdHeadLine.class);
            if (null == annotation) {
                continue;
            }

            MdContentParser parser = parserMap.get(annotation.parser());
            if (null == parser) {
                continue;
            }

            String matchLine = getAssignStr(annotation.level(), HEAD_SYMBOL) + CommonConstant.SPACE + annotation.name();

            Object parse = parser.parse(titleMap.get(matchLine));

            if (ObjectUtils.isNotEmpty(parse)) {
                Class<? extends MdConvertEnum> convert = annotation.convert();
                if (convert != NoConvert.class) {
                    parse = MdConvert.convertKey(parse, convert);
                }
            }

            field.setAccessible(true);
            field.set(instance, parse);
        }

        return (T) instance;
    }

    /**
     * 解析markdown文件 返回json数据
     *
     * @return
     */
    public void mdKeyMap() {

        // 遍历content数据
        String key = CommonConstant.BLANK_STR;
        Integer startIndex = CommonConstant.NEGATIVE_ONE;

        for (int index = 0; index < content.size(); index++) {

            if (content.get(index).matches(PatternConstant.PARSER_MD_TITLE)) {
                // 标题数据获取
                if (StringUtils.isNotBlank(key) && startIndex != CommonConstant.NEGATIVE_ONE) {
                    String value = ListUtil.joinList(content, startIndex, index);
                    titleMap.put(key, value);
                }

                // 数据校验
                key = content.get(index).trim();
                startIndex = index + CommonConstant.ONE;
            }

            if (index + 1 == content.size()) {
                String value = ListUtil.joinList(content, startIndex, content.size());
                titleMap.put(key, value);
            }
        }
    }

    private String getAssignStr(int length, String str) {

        String assignStr = str;
        for (int i = 1; i < length; i++) {
            assignStr += str;
        }

        return assignStr;
    }

    /**
     * 内置解析器
     */
    private void collectParse() {
        Map<String, MdContentParser> beansOfType = SpringContextUtil.getBeansOfType(MdContentParser.class);
        parserMap = StreamApiUtils.toMap(beansOfType.values(), MdContentParser::getClass, mdContentParser -> mdContentParser);
    }

    public void addParser(Class cls, MdContentParser mdContentParser) {
        parserMap.put(cls, mdContentParser);
    }


}
