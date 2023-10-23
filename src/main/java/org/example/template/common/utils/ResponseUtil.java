package org.example.template.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应工具类（Response对象发送数据给前端）
 */
public class ResponseUtil {

    public static void out(HttpServletResponse response, Object obj) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        try {
            // 把obj对象转化为json字符串，并返回给客户端
            mapper.writeValue(response.getWriter(), obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void out(HttpServletResponse response, String str) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().print(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
