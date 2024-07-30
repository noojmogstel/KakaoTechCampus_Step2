package gift.exception;


import gift.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static gift.exception.ErrorCode.*;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    //잘못된 파라미터 handling
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException ex) {
        final ErrorResponse response = ErrorResponse.of(ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //지원하지 않는 HTTP요청 핸들링
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException ex) {
        final ErrorResponse response = ErrorResponse.of(METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, METHOD_NOT_ALLOWED.getStatus());
    }

    //@Valid 에러 핸들링
    protected ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        final ErrorResponse response = ErrorResponse.of(INVALID_TYPE_VALUE, ex.getBindingResult());
        return new ResponseEntity<>(response, INVALID_TYPE_VALUE.getStatus());
    }

    //비즈니스 과정 중 CustomException 핸들링
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        final ErrorCode errorCode = ex.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode, ex.getErrors());
        return new ResponseEntity<>(response, errorCode.getStatus());
    }


}
