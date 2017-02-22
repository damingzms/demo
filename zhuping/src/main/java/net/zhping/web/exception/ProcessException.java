package net.zhping.web.exception;

/**
 * 系统异常
 * @author SAM
 * 
 */
public class ProcessException extends Exception {

	private static final long serialVersionUID = 861568617187113860L;

	public ProcessException(String message) {
        super(message);
    }

	public ProcessException(Throwable cause) {
        super(cause);
    }

	public ProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
