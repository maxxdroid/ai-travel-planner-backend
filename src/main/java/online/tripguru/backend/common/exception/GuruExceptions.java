package online.tripguru.backend.common.exception;

public class GuruExceptions {

    public static class GenericException extends RuntimeException {
        private final int code; // your error code
        public GenericException(String message, int code) {
            super(message);
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

}
