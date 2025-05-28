package com.cyberpeace.demo.controller;

import java.io.Serializable;

import lombok.Data;

/**
 * 演示 req1
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class DemoReq1 implements Serializable {

	private String htmlFilePath;

	private String pdfFilePath;
}
