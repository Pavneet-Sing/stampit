package com.pavneetsingh.android.stampit.util;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pavneetsingh.android.stampit.R;
import com.pavneetsingh.android.stampit.model.PostBean;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;


/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

final public class ActivityHelperClass {

    public static final int CAPTURE_IMAGE = 0 ;
    public static final int PICK_IMAGE_FROM_GALLARY = 1 ;
    public static final int PERMISSION_REQUEST = 2 ;
    public static final String TAG = "stamit " ;

    private ActivityHelperClass() {
        throw new RuntimeException("Cannot be instantiated");
    }

    public static Intent getImageIntent(int i){
        Intent intent = null;
        switch (i){
            case CAPTURE_IMAGE:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                break;
            case PICK_IMAGE_FROM_GALLARY:
                intent = new Intent(Intent.ACTION_PICK);
                break;
        }
        return intent;
    }

    public static void getPermissions(Activity activity){
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST);
        }
    }

    public static String getImagePathFromURI(Uri uri, Context context){

        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = context.getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        return picturePath;
    }

    public static Intent pickImage(){
        Intent intent = null;
//        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        if (Build.VERSION.SDK_INT <19){
//            intent = new Intent();
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//        } else {
//            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//        }
//        intent.setType("image/*");
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        return  chooserIntent;
//        return intent;
    }

    public  static Intent clickCamera(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, "Required camera and storage permissions", Toast.LENGTH_SHORT).show();
            return null;
            }else{
            Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentPicture.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri currentImageUri = getImageFileUri(activity,intentPicture);
            intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, currentImageUri); // set the image file name
                // start the image capture Intent
            return intentPicture;
            }
        }

    
    private static Uri getImageFileUri(Activity activity, Intent intent){
        // Create a storage directory for the images
        // To be safe(er), you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this

        File imagePath = new File(Environment.getExternalStorageDirectory(), "stampit");
        if (! imagePath.exists()){
            if (! imagePath.mkdirs()){
                return null;
            }
        }

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File image = new File(imagePath,"stamp"+ timeStamp + ".jpg");
        intent.putExtra("path", image.getAbsolutePath()); // set the image file name

        if(!image.exists()){
            try {
                image.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e(TAG, "getImageFileUri: " +activity.getApplicationContext().getPackageName() + ".provider"+"  "+image);
        // Create an File Uri
        return FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".fileprovider", image);
    }

    public static String getStringFromTextView(TextView textView){
        String str = textView.getText().toString().trim();
        if (str.isEmpty())
            textView.setError("Input cannot be empty");
        return str;
    }

    public static boolean verifyInputFromTextView(TextView textView){
        String str = textView.getText().toString().trim();
        if (str.isEmpty()){
            textView.setError("Input cannot be empty");
            Log.e(TAG, "verifyInputFromTextView: " );
            return false;
        }
        return true;
    }

    public static void setEmptyText(TextView textView){
        textView.setText("");
    }

    public static boolean verifyCostInput(TextView tvcost){
        String cost = tvcost.getText().toString().trim();
        if (!cost.matches("^\\d+(\\.\\d+)?$")){
            tvcost.setError("Invalid cost");
            return false;
        }
        return true;
    }

    public static boolean verfifyPostInput(TextView... tvWithStrings){
        boolean flag = true;
        for (TextView textView : tvWithStrings){
            flag = flag & verifyInputFromTextView(textView);
        }
        return flag;
    }

    public static boolean verifyEmail(TextView textView){
        boolean flag = true;
        String mail = textView.getText().toString().trim();
        StringBuilder builder = new StringBuilder();
        if (mail.isEmpty()){
            builder.append("Cannot be emppty");
            flag = false;
        }
        if (!mail.matches("^\\w{4,}@\\w+\\.\\w+.*")){
            builder.append(", Invalid Email pattern");
            flag = false;
        }
        if (!builder.toString().isEmpty()){
            textView.setError(builder.toString());
        }

        return flag;
    }
    public static boolean verifyPassword(TextView textView){
        boolean flag = true;
        String pw = textView.getText().toString().trim();
        StringBuilder builder = new StringBuilder();
        if (pw.isEmpty()){
            builder.append("Cannot be emppty");
            flag = false;
        }
        if (pw.length()<6){
            flag = false;
            builder.append(", Length must be greaater than 6 or contain numaric and an special characters !@$%_");
        }
        if (!builder.toString().isEmpty()){
            textView.setError(builder.toString());
        }
        return flag;
    }

    public static void showDetailDialog(Activity activity, PostBean postBean){
        Log.e(TAG, "showDetailDialog: "+postBean.toString() );
        final Dialog dialog = new Dialog(activity,android.R.style.Theme_Holo_Light_DarkActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ad_details);
        ImageView imageView = dialog.findViewById(R.id.img_item);
        ((TextView)dialog.findViewById(R.id.tv_ad_name)).setText(postBean.getName());
        ((TextView)dialog.findViewById(R.id.tv_ad_cost)).setText(String.valueOf(postBean.getCost()));
        ((TextView)dialog.findViewById(R.id.tv_ad_desc)).setText(postBean.getDesc());
        ((TextView)dialog.findViewById(R.id.tv_ad_location)).setText(postBean.getLoc());
        Glide.with(activity).load(postBean.getUrl()).into(imageView);
        ((Button)dialog.findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
