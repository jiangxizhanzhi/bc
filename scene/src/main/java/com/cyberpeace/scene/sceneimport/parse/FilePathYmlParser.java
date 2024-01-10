package com.cyberpeace.scene.sceneimport.parse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

/**
 * @Description
 * @Author maojun
 * @Date 2021/11/2
 */
@Slf4j
class FilePathYmlParser<T> implements YmlParse<T> {
    private String filePath;

    private Class<T> resultType;

    public FilePathYmlParser(String filePath, Class<T> resultType) {
        this.filePath = filePath;
        this.resultType = resultType;
    }

    @Override
    public T parse() {
        InputStream inputStream = null;
        try {
            File file = new File(filePath);
            inputStream = new FileInputStream(file);
            BaseConstructor constructor = new Constructor();
            constructor.setPropertyUtils(new YamlPropertyParseUtils());
            Yaml yaml = new Yaml(constructor);
            return yaml.loadAs(inputStream, resultType);
        } catch (FileNotFoundException e) {
            log.error("yml file not exists , file path is {}, {}", filePath, ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("BaseErrorCode.PARAMS_INVALID");
        } catch (Exception e) {
            log.error("failed to parse yml , file path is {}, {}", filePath, ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("BaseErrorCode.ERROR");
        } finally {
            try {
                if (inputStream != null) {
                    IOUtils.close(inputStream);
                }
            } catch (IOException e) {
                log.error("failed to close the inputStream");
            }
        }
    }
}
