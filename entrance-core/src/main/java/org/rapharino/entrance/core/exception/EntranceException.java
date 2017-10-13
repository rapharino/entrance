
package org.rapharino.entrance.core.exception;

/**
 * Created By Rapharino on 11/10/2017 2:46 PM
 *
 * 异常
 */
public class EntranceException extends RuntimeException{

    public EntranceException(String message) {
        super(message);
    }

    public EntranceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntranceException(Throwable cause) {
        super(cause);
    }
}
