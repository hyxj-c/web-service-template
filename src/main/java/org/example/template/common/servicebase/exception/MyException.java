package org.example.template.common.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.template.common.utils.ResponseCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyException extends RuntimeException {
    private Integer code = ResponseCode.ERROR; // 响应码
    private String msg = "失败"; //异常信息
}
