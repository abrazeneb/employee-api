package com.bcf.employee.exception.advice;

import com.bcf.employee.dto.ErrorResponseDTO;
import com.bcf.employee.exception.RecordNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static java.util.stream.Collectors.toList;


@ControllerAdvice(basePackages = "com.bcf.employee.controller")
public class ApiResponseHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<CustomFieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new CustomFieldError(
                        fieldError.getField(),
                        fieldError.getCode(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage())
                )
                .collect(toList());

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .reason(fieldErrors.toString())
                .errorStatusCode(1000).build();

        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .reason(ex.getMessage())
                .errorStatusCode(1000).build();
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleRecordNotFoundException(RecordNotFoundException ex) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .reason(ex.getMessage())
                .errorStatusCode(1000).build();
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleInternalServerError(RuntimeException ex) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .reason(ex.getMessage())
                .errorStatusCode(1000).build();
        return ResponseEntity.internalServerError().body(errorResponseDTO);
    }
}

@Data
@AllArgsConstructor
class CustomFieldError {
    private String field;
    private String code;
    private Object rejectedValue;
    private String reason;
}