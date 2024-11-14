package com.cyberpeace.oj.practice.service.bo;

import java.util.Map;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;

import com.cyberpeace.common.mdparser.parser.ListKeyValueParser;
import lombok.Data;

/**
 * markdownCTF随机
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class MdCtfRandom extends MdCtfBase {

    /**
     * flag
     */
    @MdHeadLine(level = 3, name = "FLAG", parser = ListKeyValueParser.class)
    private Map<String, String> flag;

}
