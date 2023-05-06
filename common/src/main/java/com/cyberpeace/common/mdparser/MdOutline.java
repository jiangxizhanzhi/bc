package com.cyberpeace.common.mdparser;

import java.io.Serializable;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;

import lombok.Data;

/**
 * md大纲
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MdOutline implements Serializable {

	@MdHeadLine(level = 2, name = "课时大纲")
	private String outline;

}
