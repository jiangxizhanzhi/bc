package com.cyberpeace.auth.util;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.model.BaseRsp;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
}
