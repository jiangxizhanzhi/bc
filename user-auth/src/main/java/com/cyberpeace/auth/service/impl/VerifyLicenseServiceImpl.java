package com.cyberpeace.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.auth.service.VerifyLicenseService;
import com.cyberpeace.common.util.ConfigUtil;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/15 16:47
 * @desc :
 **/
@Slf4j
@Service
public class VerifyLicenseServiceImpl implements VerifyLicenseService {

    private static final String LICENSE_SO_FILE = ConfigUtil.getContextProperty("license.so.file");

    public interface LicenseInterface extends Library {

        LicenseInterface INSTANCE = (LicenseInterface) Native.loadLibrary(LICENSE_SO_FILE, LicenseInterface.class);

        public class GoString extends Structure {
            public static class ByValue extends GoString implements Structure.ByValue {
            }

            public String p;
            public long n;

            @Override
            protected List getFieldOrder() {
                return Arrays.asList(new String[]{"p", "n"});
            }

        }
        public int Verify(GoString.ByValue js, GoString.ByValue code);
    }

    @Override
    public Integer verifyCode(String serialNumber, Integer range, String verifyCode, Integer number) {

        LicenseInterface.GoString.ByValue str = new LicenseInterface.GoString.ByValue();
        JSONObject strObject = new JSONObject();
        strObject.put("machinecode", serialNumber);
        strObject.put("deadline", range);
        JSONObject info = new JSONObject();
        info.put("number", number);
        strObject.put("info", info);
        str.p = strObject.toString();
        str.n = str.p.length();
        LicenseInterface.GoString.ByValue code = new LicenseInterface.GoString.ByValue();
        code.p = verifyCode;
        code.n = code.p.length();

        int ret = LicenseInterface.INSTANCE.Verify(str, code);
        if (0 == ret) {
//            log.info("verify system license pass: serialNumber {} deadline {} code {}",serialNumber,range,verifyCode);
            return ret;
        }

        log.error("verify system license failed: serialNumber {} deadline {} code {}", serialNumber, range, verifyCode);
        return ret;
    }

    public static Boolean verifyTimeValid(Long time, Integer range) {

        Calendar nowCalendar = Calendar.getInstance();
        Calendar verifyCalendar = Calendar.getInstance();
        verifyCalendar.setTimeInMillis(time);
        nowCalendar.add(Calendar.MONTH, new Integer(0) - range);
        System.out.println(nowCalendar.getTime());
        if (verifyCalendar.after(nowCalendar)) {

            return Boolean.TRUE;
        }
        log.info("system verify time is over");
        return Boolean.FALSE;
    }
}
