package com.cyberpeace.demo.controller;

import java.io.Serializable;

import lombok.Data;

@Data
public class DemoReq1 implements Serializable {

	private String htmlFilePath;

	private String pdfFilePath;
}
