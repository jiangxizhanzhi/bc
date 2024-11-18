package com.cyberpeace.common.mdparser;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import lombok.Data;

import java.io.Serializable;

/**
 * MD 大纲
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MdOutline implements Serializable {

    /**
     * 大纲
     *
     * @see String
     */
    @MdHeadLine(level = 2, name = "课时大纲")
    private String outline;

}
