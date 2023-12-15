package com.cyberpeace.auth.util;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.model.BaseRsp;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CommonUtil {

    public static void responseJson(HttpServletResponse response, BaseRsp baseRsp, int status) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setStatus(status);
            out = response.getWriter();
            out.println(JSON.toJSONString(baseRsp));
        } catch (Exception e) {
            log.error("JSON output error: " + e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public static Boolean verifyTimeValid(Long time, Integer range) {

        Calendar nowCalendar = Calendar.getInstance();
        Calendar verifyCalendar = Calendar.getInstance();
        verifyCalendar.setTimeInMillis(time);
        nowCalendar.add(Calendar.MONTH, 0 - range);
        System.out.println(nowCalendar.getTime());
        if (verifyCalendar.after(nowCalendar)) {

            return Boolean.TRUE;
        }
        log.info("system verify time is over");
        return Boolean.FALSE;
    }

    public static String getSystemUuid(){

        String systemUuid = execCmd("dmidecode | grep \"UUID\"", null);

        if(null != systemUuid) {

            String pattern = "\\s+UUID:\\s+([A-Za-z0-9-]+)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(systemUuid);
            if(m.find()){

                String uuid = m.group(1);
                return uuid;
            } else {

                log.error("can not find system uuid");
            }
        }
        return null;
    }

    private static String execCmd(String cmd, File dir){

        StringBuilder result = new StringBuilder();
        StringBuilder errInfo = new StringBuilder();
        Process process = null;
        BufferedReader inReader = null;
        BufferedReader errReader = null;

        try{

            String[] cmdArrary = {"/bin/bash", "-c", cmd};
            process = Runtime.getRuntime().exec(cmdArrary,null,dir);
            process.waitFor();

            inReader = new BufferedReader(new InputStreamReader(process.getInputStream(),"utf-8"));
            errReader = new BufferedReader(new InputStreamReader(process.getErrorStream(),"utf-8"));
            String line = null;
            while ((line = inReader.readLine()) != null){

                result.append(line).append("\n");
            }
            inReader.toString();
            while ((line = errReader.readLine()) != null){

                errInfo.append(line).append("\n");
            }

            if(null != process){

                process.destroy();
            }
            if(null != inReader){

                inReader.close();
            }
            if(null != errReader){

                errReader.close();
            }

            if(null != result && result.toString().length() > 0){

                return result.toString();
            }
            if(null != errInfo && errInfo.toString().length() > 0){

                log.error("exec cmd{} failed ,errinfo {}", cmd,errInfo.toString());
                return null;
            }

            log.error("exec cmd{} failed ,result and errinfo are null", cmd);
            return null;

        } catch (Exception e){

            log.error("exec cmd {} exception {}",cmd,e);
            return null;
        }
    }
}
