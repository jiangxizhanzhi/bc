package com.cyberpeace.common.mdparser;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.MdConvert;
import com.cyberpeace.common.mdparser.convert.MdConvertEnum;
import com.cyberpeace.common.mdparser.convert.NoConvert;
import com.cyberpeace.common.mdparser.parser.MdContentParser;
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
 * md 解析器
 *
 * @author chenzhuo
 * 仅支持标题和列表 列表仅支持二级@date 2024/11/08
 */
/**
 * ### 代码分析 
 * #### 功能概述 
 * 该代码实现了一个 Markdown 文件解析器 `MdParser`，能够将 Markdown 文件内容转换为 Java 对象（反序列化），或从 Java 对象生成 Markdown 文件内容（序列化）。它支持标题和列表（目前仅支持二级）。 
 * --- 
 * #### 主要功能模块 
 * 1. **初始化与文件处理** 
 * - 构造函数 `MdParser(String file)` 和 `MdParser()` 分别用于加载指定文件或初始化空解析器。 
 * - `setFile(String file)` 方法检查文件是否存在并加载其内容到 `content` 列表中，并初始化标题映射 `titleMap`。 
 * 2. **Markdown 文件解析** 
 * - `mdKeyMap()` 方法遍历 Markdown 文件内容，提取标题（符合正则表达式）及其对应的内容，存储在 `titleMap` 中。 
 * - `doRead(Class<T> cls)` 方法通过反射机制，结合 `titleMap` 和解析器 `parserMap` 将 Markdown 数据映射到目标 Java 对象。 
 * 3. **Markdown 文件生成** 
 * - `doWrite(Object obj, File file)` 方法通过反射获取对象字段，结合注解 `@MdHeadLine` 和解析器 `parserMap`，将对象数据生成 Markdown 文件内容。 
 * - 支持值的转换（如 `NoConvert` 和其他自定义转换器）。 
 * 4. **解析器管理** 
 * - `collectParse()` 方法通过 Spring 容器收集所有实现了 `MdContentParser` 接口的解析器，并存入 `parserMap`。 
 * - `addParser(Class cls, MdContentParser mdContentParser)` 方法允许动态添加自定义解析器。 
 * 5. **工具方法** 
 * - `getAssignStr(int length, String str)`：根据长度生成指定字符的字符串。 
 * - `write(Object obj, File file)` 和 `read(Class<T> cls)` 是对外接口，分别用于写入和读取。 
 * --- 
 * #### 关键特性 
 * - **注解驱动**： 
 * 使用 `@MdHeadLine` 注解标记需要解析的字段，支持标题级别、名称、解析器类型和转换器类型。 
 * - **灵活性**： 
 * 支持动态添加解析器，便于扩展不同类型的 Markdown 数据解析逻辑。 
 * - **异常处理**： 
 * 提供了详细的日志记录和异常抛出机制，例如文件不存在、解析失败等。 
 * - **线程安全**： 
 * 在多线程环境下，`read` 和 `write` 方法使用了 `synchronized` 关键字保证线程安全。 
 * --- 
 * #### 总结 
 * 该代码设计合理，功能明确，适用于基于 Markdown 的数据交换场景。核心功能包括 Markdown 文件的解析与生成，支持注解驱动和动态解析器扩展。但需注意以下几点： 
 * 1. 当前仅支持标题和二级列表，扩展性有限。 
 * 2. 异常处理虽完善，但在某些场景下可能需要更细粒度的控制。 
 * 3. 文件操作部分可以进一步优化以提高性能和可维护性。 
**/
@Slf4j
public class MdParser {

    /**
     * head 符号
     */
    private String HEAD_SYMBOL = "#";

    /**
     * 内容
     */
    private List<String> content;

    /**
     * 标题地图
     */
    private Map<String, String> titleMap;

    /**
     * 解析器映射
     */
    private Map<Class, MdContentParser> parserMap;

    /**
     * md 解析器
     *
     * @param file 文件
     */
    public MdParser(String file) {
        setFile(file);
        collectParse();
    }

    /**
     * md 解析器
     */
    public MdParser() {
        collectParse();
    }

    /**
     * 设置文件
     *
     * @param file 文件
     */
    public void setFile(String file) {
        if (StringUtils.isBlank(file) || !new File(file).exists()) {
            log.error("File not exist! file_path=[{}]", file);
            throw new BusinessException(ErrorCode.FILE_READ_FAIL, file);
        }

        content = FileUtil.listContent(new File(file));
        titleMap = new ConcurrentHashMap<>();
    }

    /**
     * 读
     *
     * @param cls CLS
     * @return {@code T }
     */
    public synchronized <T> T read(Class<T> cls) {
        try {
            return doRead(cls);
        } catch (IllegalAccessException e) {
            log.error("Markdown file to entity failed! cls=[{}], catch error:", cls.getName(), e);
            throw new BusinessException(ErrorCode.FILE_READ_FAIL, cls.getName());
        }
    }

    /**
     * 写入
     *
     * @param obj  对象
     * @param file 文件
     */
    public synchronized void write(Object obj, File file) {
        doWrite(obj, file);
    }

    /**
     * 做写入
     *
     * @param obj  对象
     * @param file 文件
     */
    private void doWrite(Object obj, File file) {
        File dirs = FileUtil.mkdirs(file.getParentFile().getAbsolutePath());
        if (file.exists()) {
            boolean b = file.delete();
            if (!b) {
                log.warn("Failed to delete file: {}", file.getAbsolutePath());
            }
        }

        Field[] fields = ObjUtil.getFields(obj);
        if (fields.length <= CommonConstant.ZERO) {
            try {
                file.createNewFile();
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
            boolean success = file.delete();
            if (!success) {
                log.warn("Failed to delete file: {}", file.getAbsolutePath());
            }
            throw new BusinessException(ErrorCode.FAILED_TO_WRITE_FILE);
        } finally {
            IOException suppressedException = null;

            // 按创建顺序的逆序关闭资源
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                suppressedException = e;
                log.error("Failed to close BufferedWriter", e);
            }

            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                if (suppressedException == null) {
                    suppressedException = e;
                }
                log.error("Failed to close OutputStreamWriter", e);
            }

            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                if (suppressedException == null) {
                    suppressedException = e;
                }
                log.error("Failed to close FileWriter", e);
            }

            // 可以选择记录或处理最终异常
            if (suppressedException != null) {
                log.warn("One or more resources failed to close properly");
            }
        }
    }

    /**
     * 做阅读
     *
     * @param cls CLS
     * @return {@code T }
     * @throws IllegalAccessException 非法访问异常
     */
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

    /**
     * 获取分配str
     *
     * @param length 长度
     * @param str    str
     * @return {@code String }
     */
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

    /**
     * 新增解析 器
     *
     * @param cls             CLS
     * @param mdContentParser MD 内容解析器
     */
    public void addParser(Class cls, MdContentParser mdContentParser) {
        parserMap.put(cls, mdContentParser);
    }


}

