package com.lcat.common.exception;

import com.lcat.common.lang.ErrorCode;
import com.lcat.common.lang.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class WebResultHandler {

    public WebResultHandler() {
        log.debug(">>>>>>>>>>>>>>>>>>>>> WebResultHandler 注册 <<<<<<<<<<<<<<<<<<<<");
    }

    private static final Logger log = LoggerFactory.getLogger(WebResultHandler.class);

    @Autowired
    ApplicationContext applicationContext;


    /**
     * 自定义异常
     *
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(value = {BusinesException.class})
    public ResultBean poseidonExceptionHandler(BusinesException e, HttpServletRequest req) {
        log.error("业务异常,错误码：{}", e.getCode(), e);
        ResultBean error = ResultBean.error(e.getMessage(), e.getCode() == null ? 1001 : e.getCode());
        return error;
    }

    /**
     * 参数未通过校验
     *
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResultBean methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest req) {
        log.error("参数未通过校验", e);
        ResultBean error = ResultBean.error(e.getBindingResult().getFieldError().getDefaultMessage());
        return error;
    }

    /**
     * BeanPropertyBindingResult
     */
    @ExceptionHandler(value = {BindException.class})
    public ResultBean beanPropertyBindingResult(BindException e, HttpServletRequest req) {
        log.error("参数未通过校验", e);
        ResultBean error = ResultBean.error(e.getFieldError().getDefaultMessage());
        return error;
    }

    /**
     * 错误的请求方式
     *
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResultBean notsupported(Exception e, HttpServletRequest req) {
        log.error("错误的请求方式", e);
        ResultBean error = ResultBean.error("错误的请求方式");
        return error;
    }

    /**
     * 请求路径不存在
     *
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResultBean notFoundUrl(Exception e, HttpServletRequest req) {
        log.error("请求路径不存在", e);
        ResultBean error = ResultBean.error("请求路径不存在");
        return error;
    }


    /**
     * 未知异常，需要通知到管理员,对于线上未知的异常，我们应该严肃处理
     * 这里的处理方式是抛出事件
     *
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public ResultBean exceptionHandler(Exception e, HttpServletRequest req) {
        // todo
        log.error("未知异常",e);
        return ResultBean.error(ErrorCode.UNKNOW_ERROR);
    }
}
