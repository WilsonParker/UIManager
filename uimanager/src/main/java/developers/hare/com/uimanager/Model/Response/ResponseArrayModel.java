package developers.hare.com.uimanager.Model.Response;

import java.util.ArrayList;

/**
 * @description
 *
 * 
 * @author Hare
 * @since 2018-08-25
 **/
public class ResponseArrayModel<T> {
    private int success;
    private String message;
    private ArrayList<T> result;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public ArrayList<T> getResult() {
        return result;
    }

    public void setResult(ArrayList<T> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
