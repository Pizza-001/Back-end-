package com.pet.hospital.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "统一通用响应封装结构")
public class Result<T> {
    @Schema(description = "状态响应码，200代表成功，其余代表失败", example = "200")
    private Integer code;
    @Schema(description = "随同提示消息", example = "success")
    private String message;
    @Schema(description = "真实的业务负载/响应数据")
    private T data;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> error(String message) {
        return error(500, message);
    }
}
