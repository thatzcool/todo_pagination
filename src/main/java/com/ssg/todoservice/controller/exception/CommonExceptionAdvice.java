package com.ssg.todoservice.controller.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

//스프링컨트롤러에서 발생하는 예외를 처리하는 일반적인 방식으로  컨트롤러에 공통으로 처리할 작업을 Advice로 지정
@ControllerAdvice
@Log4j2
public class CommonExceptionAdvice {

//    @ResponseBody    // 작성한 예외 메시지를 브라우저에게 전송한다.
//    @ExceptionHandler(value = NumberFormatException.class)
//    public String exceptNumber(NumberFormatException numberFormatException){
//        log.error("numberFormatException: " + numberFormatException.getMessage());
//        return "Number Format Exception!";
//    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)   // 예외 발생 시 전달할 상태코드
    public String notFound(){
        return "custom404";
    }



}
