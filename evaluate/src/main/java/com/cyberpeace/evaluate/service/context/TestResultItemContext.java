package com.cyberpeace.evaluate.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.evaluate.service.context.TestResultIndexContext;
import lombok.Data;

/**
 * 测评结果保存--测试项
 */
@Data
public class TestResultItemContext implements Serializable {

    private String itemName;

    private String itemId;

    private List<TestResultIndexContext> indices;
}
