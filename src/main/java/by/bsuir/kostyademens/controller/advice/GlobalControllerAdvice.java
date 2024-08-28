package by.bsuir.kostyademens.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(Throwable.class)
    public String handleAllUncommonException() {
        return "error";
    }
}
