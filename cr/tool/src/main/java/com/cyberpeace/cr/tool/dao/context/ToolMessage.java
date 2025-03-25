package com.cyberpeace.cr.tool.dao.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ToolMessage
 *
 * @author Jerry(姜源)
 * @since 2025/03/25 3:15
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToolMessage implements Serializable {
    private String toolName;
    private String version;
    private String type;
    private String subType;
    private String platform;
    private String language;
    private String title;
    private String message;
}
