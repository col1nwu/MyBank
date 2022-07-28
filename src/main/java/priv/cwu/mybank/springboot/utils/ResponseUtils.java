package priv.cwu.mybank.springboot.utils;

import org.springframework.http.HttpStatus;
import priv.cwu.mybank.springboot.constant.ResponseCode;

public class ResponseUtils {

    /**
     * 返回通用结果。
     *
     * @param httpStatus http状态
     * @return 返回结果
     */
    public static HttpResponse newResult(HttpStatus httpStatus) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(httpStatus.value());
        httpResponse.setMsg(httpStatus.getReasonPhrase());
        return httpResponse;
    }


    public static HttpResponse newResult(HttpStatus httpStatus, String msg) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(httpStatus.value());
        httpResponse.setMsg(msg);
        return httpResponse;
    }


    /**
     * 返回通用结果。
     *
     * @param responseCode 返回结果类型
     * @return 返回结果
     */
    public static HttpResponse newResult(ResponseCode responseCode) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(responseCode.code);
        httpResponse.setMsg(responseCode.msg);
        return httpResponse;
    }


    /**
     * 返回通用结果。
     *
     * @param responseCode 返回结果类型
     * @return 返回结果
     */
    public static HttpResponse newResult(ResponseCode responseCode, Object data) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(responseCode.code);
        httpResponse.setMsg(responseCode.msg);
        httpResponse.setData(data);
        return httpResponse;
    }


    /**
     * 返回不带数据的成功结果。
     *
     * @return 返回结果
     */
    public static HttpResponse newSuccessResult() {
        return ResponseUtils.newResult(HttpStatus.OK);
    }


    /**
     * 返回成功的结果。
     *
     * @param data 返回结果中包含的数据
     * @return 返回结果
     */
    public static HttpResponse newSuccessResult(Object data) {
        HttpResponse httpResponse = ResponseUtils.newSuccessResult();
        httpResponse.setData(data);
        return httpResponse;
    }


    /**
     * 返回不带数据的失败结果。
     *
     * @return 返回结果
     */
    public static HttpResponse newFailResult() {
        return ResponseUtils.newResult(HttpStatus.BAD_REQUEST);
    }


    /**
     * 返回带数据的失败结果。
     *
     * @param data 返回结果中包含的数据
     * @return 返回结果
     */
    public static HttpResponse newFailResult(Object data) {
        HttpResponse httpResponse = ResponseUtils.newFailResult();
        httpResponse.setData(data);
        return httpResponse;
    }


    /**
     * 返回自定义错误码和自定义错误信息的结果。
     *
     * @param code 错误码
     * @param msg 错误信息
     * @return 返回结果
     */
    public static HttpResponse newResult(int code, String msg) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(code);
        httpResponse.setMsg(msg);
        return httpResponse;
    }


    /**
     * 返回带数据且自定义错误码和自定义错误信息的结果。
     *
     * @param data 返回结果中包含的数据
     * @param code 错误码
     * @param msg 错误信息
     * @return 返回结果
     */
    public static HttpResponse newResult(int code, String msg, Object data) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setData(data);
        httpResponse.setCode(code);
        httpResponse.setMsg(msg);
        return httpResponse;
    }
}
