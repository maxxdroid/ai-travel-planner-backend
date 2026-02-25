package online.tripguru.backend.common.exception;

import online.tripguru.backend.common.labels.ExceptionLabels;
import org.springframework.http.HttpStatus;

public class AuthExceptions {

    public static class InvalidTokenException extends ApiException {
        public InvalidTokenException() {
            super(ExceptionLabels.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
        }
    }

    public static class UserNotFoundException extends ApiException {
        public UserNotFoundException() {
            super(ExceptionLabels.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public static class UserDoesNotExistException extends ApiException {
        public UserDoesNotExistException() {
            super(ExceptionLabels.USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND);
        }
    }

    public static class AuthenticationFailedException extends ApiException {
        public AuthenticationFailedException() {
            super(ExceptionLabels.AUTHENTICATION_ERROR, HttpStatus.UNAUTHORIZED);
        }
    }

    public static class UserAlreadyExistsException extends ApiException {
        public UserAlreadyExistsException() {
            super(ExceptionLabels.USER_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }
    }

    public static class UserNotAuthorizedException extends ApiException {
        public UserNotAuthorizedException() {
            super(ExceptionLabels.USER_NOT_AUTHORIZED, HttpStatus.FORBIDDEN);
        }
    }

}
