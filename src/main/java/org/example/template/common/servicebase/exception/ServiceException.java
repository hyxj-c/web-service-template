package org.example.template.common.servicebase.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.template.common.utils.ResponseCode;

@Getter
@NoArgsConstructor
public class ServiceException extends RuntimeException {
    private Integer code = ResponseCode.ERROR; // 响应码
    private String msg = "失败"; //异常信息

    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
