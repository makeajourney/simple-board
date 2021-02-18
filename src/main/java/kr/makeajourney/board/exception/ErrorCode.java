package kr.makeajourney.board.exception;


public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "Server Error"),
    EXPIRED_TOKEN(401, "Expired Token")
    ;

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }
}
