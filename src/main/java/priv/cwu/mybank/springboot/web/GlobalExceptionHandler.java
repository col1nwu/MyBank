package priv.cwu.mybank.springboot.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import priv.cwu.mybank.springboot.constant.ResponseCode;
import priv.cwu.mybank.springboot.exception.UnauthorizedException;
import priv.cwu.mybank.springboot.exception.UnownedException;
import priv.cwu.mybank.springboot.utils.ResponseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Object handleMethodNotSupportedRequest(Exception exception, HttpServletRequest request) {
        log.error("request url:{}, e:{}", request.getRequestURL(), exception);
        return ResponseUtils.newResult(HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleMissingParameterRequest(Exception exception, HttpServletRequest request) {
        log.error("request url:{}, e:{}", request.getRequestURL(), exception);
        return ResponseUtils.newResult(HttpStatus.BAD_REQUEST.value(), "Missing Params");
    }


    @ExceptionHandler(value = {TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleBadRequest(Exception exception, HttpServletRequest request) {
        log.error("request url:{}, e:{}", request.getRequestURL(), exception);
        return ResponseUtils.newFailResult();
    }


    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object handleUnauthorizedRequest(Exception exception, HttpServletRequest request) {
        log.error("request url:{}, e:{}", request.getRequestURL(), exception);
        return ResponseUtils.newResult(HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(value = UnownedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleUnownedRequest(Exception exception, HttpServletRequest request) {
        log.error("request url:{}, e:{}", request.getRequestURL(), exception);
        return ResponseUtils.newResult(ResponseCode.OWNERSHIP_ERROR);
    }


    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleConstraintViolationException(Exception exception, HttpServletRequest request) {
        log.error("request url:{}, e:{}", request.getRequestURL(), exception);
        return ResponseUtils.newResult(HttpStatus.BAD_REQUEST, exception.getMessage());
    }


//    @ExceptionHandler(value = {ForbiddenException.class})
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public Object handleForbidden(Exception exception, WebRequest request) {
//        return ResponseUtils.newResult(HttpStatus.FORBIDDEN);
//    }


    /**
     * 处理其他异常。这里返回的HTTP状态码和错误码都是500。
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleException(Exception exception, WebRequest request) {
        log.error("@@ServerException Exception={} Request={}", exception, request.getDescription(true));
        log.error("@@StackTrace", exception);
        return ResponseUtils.newResult(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
