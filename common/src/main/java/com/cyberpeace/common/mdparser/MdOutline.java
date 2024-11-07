package com.cyberpeace.common.mdparser;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import lombok.Data;

import java.io.Serializable;

/**
 * MD 大纲
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MdOutline implements Serializable {

    @MdHeadLine(level = 2, name = "课时大纲")
    private String outline;

}
