package com.lanzhou.servicebase.exceptionHandler;

import com.lanzhou.commouils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public R error(Exception e)
    {
        e.printStackTrace();
        return R.ERROR().message("数据错误");
    }


    @ExceptionHandler
    @ResponseBody
    public R error(ArithmeticException e)
    {
        e.printStackTrace();
        return R.ERROR().message("特定异常处理");
    }
}
