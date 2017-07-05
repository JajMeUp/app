package com.teamjaj.agourd.valoulou.jajmeup.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamjaj.agourd.valoulou.jajmeup.preferences.ClockSettings;
import com.teamjaj.agourd.valoulou.jajmeup.preferences.UserSettings;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.BitmapUtils;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.Caller;
import  com.teamjaj.agourd.valoulou.jajmeup.R;
import com.teamjaj.agourd.valoulou.jajmeup.receivers.ProfilePictureReceiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    private ImageView mImageView;
    private final String PHOTO_PATH = "PhotoPath";
    private final int GALLERY_ACTIVITY_CODE=200;
    private final int CROP_ACTIVITY_CODE = 400;
    private final Context ctx = this;
    private final ProfilePictureReceiver receiver = new ProfilePictureReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametre);
        mImageView = (ImageView) findViewById(R.id.ProfilePicIV);

        String profilePictureFilename = PreferenceManager.getDefaultSharedPreferences(this).getString(PHOTO_PATH, "defaultStringIfNothingFound");

        if(!profilePictureFilename.equals("defaultStringIfNothingFound")) {
            Log.d(this.getClass().getSimpleName(), "Retrieve profile picture from settings: " + profilePictureFilename);
            Bitmap picture;
            try {
                picture = BitmapUtils.getBitmapFromURI(this, Uri.parse(profilePictureFilename));
            } catch (IOException e) {
                Log.e(this.getClass().getSimpleName(), "Unable to load the profile picture from settings URI " + profilePictureFilename);
                e.printStackTrace();
                return;
            }
            mImageView.setImageBitmap(picture);
        }
        else {
            Log.d(this.getClass().getSimpleName(), "Requesting profile picture picture to the server");
            IntentFilter filter = new IntentFilter();
            filter.addAction("ekto.valou.picbroadcast");
            registerReceiver(receiver, filter);
            Caller.getPicture();
        }

        mImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_ACTIVITY_CODE);
            }
        });

        TextView usersettings=(TextView) findViewById(R.id.user_settings);

        usersettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(getApplicationContext(), UserSettings.class);
                startActivityForResult(i, 1);
            }
        });

        TextView clocksettings=(TextView) findViewById(R.id.clock_settings);

        clocksettings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i;
                i = new Intent(getApplicationContext(), ClockSettings.class);
                startActivityForResult(i, 1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GALLERY_ACTIVITY_CODE:
                handleGalleryActivityResult(resultCode, data);
                break;
            case CROP_ACTIVITY_CODE:
                handleCropActivityResult(resultCode, data);
                break;
            default:
                Log.w(this.getClass().getSimpleName(), requestCode + " is unknown ...");
                break;
        }
    }

    private boolean performCrop(Uri uri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(uri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 280);
        cropIntent.putExtra("outputY", 280);
        cropIntent.putExtra("return-data", true);
        try {
            startActivityForResult(cropIntent, CROP_ACTIVITY_CODE);
            return true;
        }
        catch (ActivityNotFoundException e) {
            Log.e(this.getClass().getSimpleName(), "Device does NOT support croping ...");
            return false;
        }
    }

    private void handleGalleryActivityResult(int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            Uri uri;
            if (data != null) {
                uri = data.getData();
                Log.d(this.getClass().getSimpleName(), "Selected profile picture URI: " + uri.toString());
                Bitmap picture;
                try {
                    picture = BitmapUtils.getBitmapFromURI(this, uri);
                }
                catch (IOException e) {
                    Log.e(this.getClass().getSimpleName(), "The selected URI(" + uri + ") does NOT exists ...");
                    e.printStackTrace();
                    return;
                }

                // If cropping is not supported, then the selected picture will be used as it is
                if(!performCrop(uri)) {
                    setProfilePicture(uri, picture);
                }
            }
        }
        else {
            Log.e(this.getClass().getSimpleName(), "Something went wrong in the gallery activity");
        }
    }

    private void handleCropActivityResult(int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap selectedBitmap = extras.getParcelable("data");

            Uri uri = saveProfilePictureLocally(selectedBitmap);
            if(uri != null) {
                setProfilePicture(uri, selectedBitmap);
            }
        }
    }

    public Uri saveProfilePictureLocally(Bitmap picture) {
        File croppedPictureFile = new File(this.getFilesDir(), "profile_picture");
        FileOutputStream out;
        try {
            out = new FileOutputStream(croppedPictureFile);
            picture.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.d(this.getClass().getSimpleName(), croppedPictureFile.getPath() + " has been saved");
            return Uri.fromFile(croppedPictureFile);
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), "Unable to open an output stream to " + croppedPictureFile.getPath());
            e.printStackTrace();
            return null;
        }
    }

    public void setProfilePicture(Uri uri, Bitmap picture) {
        PreferenceManager.getDefaultSharedPreferences(ctx).edit()
                .putString(PHOTO_PATH, uri.toString()).apply();

        mImageView.setImageBitmap(picture);
        Caller.sendPicture(BitmapUtils.bitmapToString(picture));
    }
}
