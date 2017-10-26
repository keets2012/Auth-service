package com.blueskykong.auth.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class RemoteException extends AbstractException {

    public RemoteException() {
        super(INTERNAL_SERVER_ERROR);
    }

    public RemoteException(ErrorCode errorCode) {
        super(INTERNAL_SERVER_ERROR, errorCode);
    }

    public RemoteException(ErrorCode errorCode, Throwable cause) {
        super(INTERNAL_SERVER_ERROR, errorCode, cause);
    }
}
