package com.revworkforce.employee_management_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
import com.revworkforce.employee_management_service.exception.DepartmentNotFoundException;
import com.revworkforce.employee_management_service.exception.DesignationNotFoundException;
import com.revworkforce.employee_management_service.exception.AnnouncementNotFoundException;
import com.revworkforce.employee_management_service.exception.SystemConfigurationNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmployeeNotFound(EmployeeNotFoundException exception) {
        return "Employee not found";
    }
    @ExceptionHandler(DepartmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDepartmentNotFound(
            DepartmentNotFoundException exception) {

        return "Department not found";
    }

    @ExceptionHandler(DesignationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDesignationNotFound(
            DesignationNotFoundException exception) {

        return "Designation not found";
    }

    @ExceptionHandler(AnnouncementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAnnouncementNotFound(
            AnnouncementNotFoundException exception) {

        return "Announcement not found";
    }

    @ExceptionHandler(SystemConfigurationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSystemConfigurationNotFound(
            SystemConfigurationNotFoundException exception) {

        return "System configuration not found";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(
            IllegalArgumentException exception) {

        return exception.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return errors;
    }
}
