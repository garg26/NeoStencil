package com.neostencilmobileapp.fragment;

import android.support.v4.app.Fragment;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.neostencilmobileapp.common.AppConstants;
import java.io.IOException;
import java.net.URISyntaxException;

public class MediaFragment extends Fragment {

    private MediaListener mediaListener;
    public final int REQUEST_CODE_GALLARY = 50;
    public final int REQUEST_CODE_GET_PDF = 54;

    public void getDoc(final MediaListener mediaListener, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, new String[]{"Gallery", "Pdf"});
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    getImageFromGallery(mediaListener);
                } else if (which == 1) {
                    getPdfFromGallery(mediaListener);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setTitle("Choose a File");
        dialog.show();
    }


    public void getImageFromGallery(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLARY);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("msg", "onActivity result is called... requestCode=" + requestCode);
        switch (requestCode) {
            case REQUEST_CODE_GALLARY:
                try {
                    mediaListener.setUri(data.getData(), AppConstants.MEDIA_TYPES.IMAGE);
                    mediaListener.setBitmap(getBitmapFromUri(getActivity(), data.getData()), AppConstants.MEDIA_TYPES.IMAGE);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;

            case REQUEST_CODE_GET_PDF:
                Uri uri1 = data.getData();
                String filePath = null;
                try {
                    filePath = getFilePath(getActivity(), uri1);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                mediaListener.setUri(data.getData(), AppConstants.MEDIA_TYPES.DOC, filePath);
                break;
        }
    }


    public interface MediaListener {
        void setUri(Uri uri, String MediaType);

        void setUri(Uri uri, String MediaType, String path);

        void setBitmap(Bitmap bitmap, String MediaType);


    }

    public void getPdfFromGallery(MediaListener mediaListener) {
        this.mediaListener = mediaListener;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, REQUEST_CODE_GET_PDF);
    }

    public static Bitmap getBitmapFromUri(Context ctx, Uri imageUri) {
        try {
            return MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
