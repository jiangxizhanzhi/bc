package com.cyberpeace.demo.controller;

import java.io.Serializable;

import lombok.Data;

/**
 * 演示req
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class DemoReq1 implements Serializable {

	private String htmlFilePath;

	private String pdfFilePath;
}
