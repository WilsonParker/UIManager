package developers.hare.com.uimanager.UI;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData;
import com.miguelbcr.ui.rx_paparazzo2.entities.Response;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import developers.hare.com.uimanager.R;
import developers.hare.com.uimanager.Util.File.FileManager;
import developers.hare.com.uimanager.Util.LogManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;


/**
 * @description
 *
 *
 * implementation "com.github.miguelbcr:RxPaparazzo:0.6.0-2.x"
 * implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
 *
 * @author Hare
 * @since 2018-08-24
 **/
public class PhotoManager {
    private static final PhotoManager ourInstance = new PhotoManager();
    private OnPhotoBindListener onPhotoBindListener;
    private UCrop.Options options = new UCrop.Options();

    public static PhotoManager getInstance() {
        return ourInstance;
    }

    private void createOptions(Activity activity) {
        options.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));

    }

    public void onGalleryMultiSelect(Activity activity, OnPhotoBindListener onPhotoBindListener) {
        this.onPhotoBindListener = onPhotoBindListener;
        createOptions(activity);

        RxPaparazzo.multiple(activity)
                .usingGallery()  //  바로 앨범을 실행
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.resultCode() != RESULT_OK) {
                        return;
                    }
                    response.data().forEach(this::bindData);
                });
    }

    public void onGallerySingleSelect(Activity activity, OnPhotoBindListener onPhotoBindListener) {
        this.onPhotoBindListener = onPhotoBindListener;
        createOptions(activity);

        RxPaparazzo.single(activity)
                .crop(options)
                .usingGallery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.resultCode() != RESULT_OK) {
                        return;
                    }
                    bindData(response.data());

                });
    }

    public void onCameraSelect(Activity activity, OnPhotoBindListener onPhotoBindListener) {
        this.onPhotoBindListener = onPhotoBindListener;
        createOptions(activity);
        RxPaparazzo.single(activity)
                .crop(options)
                .usingCamera()  //  바로 카메라를 실행
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable, Response<Activity, FileData>>() {
                    @Override
                    public Response<Activity, FileData> apply(@NonNull Throwable throwable){
                        LogManager.log(PhotoManager.class, "apply(@NonNull Throwable throwable)", throwable);
                        return null;
                    }
                })
                .subscribe(response -> {
                    // See response.resultCode() doc
                    if (response.resultCode() != RESULT_OK) {
                        return;
                    }
                    bindData(response.data());
                });
    }

    private void bindData(FileData fileData) {
        onPhotoBindListener.bindData(fileData);
    }

    public Bitmap rotate(File file) {
        FileManager fileManager = FileManager.getInstance();
        Bitmap bitmap = fileManager.encodeFileToBitmap(file);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(file.getCanonicalPath());
        } catch (Exception e) {
            LogManager.log(getClass(), "rotate(File)", e);
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap bmRotated = rotateBitmap(bitmap, orientation);
        return bmRotated;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface OnPhotoBindListener {
        void bindData(FileData fileData);
    }
}
