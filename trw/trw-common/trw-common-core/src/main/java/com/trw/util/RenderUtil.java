package com.trw.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.trw.enums.TrwExceptionEnum;
import com.trw.exception.TrwException;

/**
 * 渲染工具类
 *
 */
public class RenderUtil {

    /**
     * 渲染json对象
     */
    public static void renderJson(HttpServletResponse response, Object jsonObject) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonObject));
        } catch (IOException e) {
            throw new TrwException(TrwExceptionEnum.WRITE_ERROR);
        }
    }
}
