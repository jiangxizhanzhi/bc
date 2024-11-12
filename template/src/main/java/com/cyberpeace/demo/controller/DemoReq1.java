package com.cyberpeace.demo.controller;

import lombok.Data;

import java.io.Serializable;

/**
 * 演示req1
 *
 * @author admin
 * @date 2024/11/12
 */
@Data
public class DemoReq1 implements Serializable {

	/**
	 * html文件路径
	 */
	private String htmlFilePath;

	/**
	 * pdf文件路径
	 */
	private String pdfFilePath;
}
