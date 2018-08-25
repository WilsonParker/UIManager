package developers.hare.com.uimanager.Model.Response;

/**
 * @description
 *
 * 
 * @author Hare
 * @since 2018-08-25
 **/
public class ResponseModel<T> {
    private int success;
    private String message;
    private T result;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {

        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
