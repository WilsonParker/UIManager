package developers.hare.com.uimanager.Util.Parser;

import android.graphics.Bitmap;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import developers.hare.com.uimanager.UI.PhotoManager;
import developers.hare.com.uimanager.Util.File.FileManager;
import developers.hare.com.uimanager.Util.Image.ImageManager;
import developers.hare.com.uimanager.Util.LogManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @description
 *
 * 
 * @author Hare
 * @since 2018-08-24
 **/
public class RetrofitBodyParser {
    private static RetrofitBodyParser retrofitBodyParser = new RetrofitBodyParser();
    private static final int UPLOAD_MAX_SIZE = 1080;
    private Map<String, RequestBody> dataMap;
    private Map<String, Method> methodMap;
    private final String GET_KEY = "GET";

    public static RetrofitBodyParser getInstance() {
        return retrofitBodyParser;
    }

    public RequestBody createRequestBody(Object object) {
        RequestBody requestBody = null;
        if (object instanceof String) {
            requestBody = RequestBody.create(MediaType.parse("text/plain"), (String) object);
        } else if (object instanceof Integer) {
            requestBody = RequestBody.create(MediaType.parse("text/plain"), ((int) object) + "");
        } else if (object instanceof ArrayList) {
            requestBody = RequestBody.create(MediaType.parse("text/plain"), ((ArrayList) object).toString());
        }
        return requestBody;
    }

    public MultipartBody.Part createImageMultipartBodyPart(String key, String fileName, byte[] byteDate) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), byteDate);
        MultipartBody.Part multipartBodyPart = MultipartBody.Part.createFormData(key, fileName, requestBody);
        return multipartBodyPart;
    }

    public MultipartBody.Part createImageMultipartBodyPart(String key, File file) {
        FileManager fileManager = FileManager.getInstance();
        Bitmap rImage = PhotoManager.getInstance().rotate(file);
        byte[] byteData = fileManager.encodeBitmapToByteArray(ImageManager.getInstance().resizeImage(rImage, UPLOAD_MAX_SIZE));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), byteData);
        MultipartBody.Part multipartBodyPart = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
        return multipartBodyPart;
    }

    public Map<String, RequestBody> parseMapRequestBody(Object obj) {
        dataMap = new HashMap<>();
        methodMap = new HashMap<>();
        Class<? extends Object> objClass = obj.getClass();
        for (Method method : objClass.getMethods()) {
            String methodName = method.getName().toUpperCase();
            if (methodName.contains(GET_KEY)) {
                methodMap.put(methodName, method);
            }
        }
        for (Field field : objClass.getDeclaredFields()) insertFieldData(field, obj);
        return dataMap;
    }

    private void insertFieldData(Field field, Object obj) {
        String fieldName = field.getName();
        try {
            Method method = methodMap.get(GET_KEY + fieldName.toUpperCase());
            if (method != null) {
                Object value = method.invoke(obj);
                if (value != null)
                    dataMap.put(fieldName, createRequestBody(value));
            }
        } catch (Exception e) {
            LogManager.log(getClass(), "parseMapRequestBody(Object obj)", e);
        }
    }
}
