package com.teamjaj.jajmeup.preferences;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.teamjaj.jajmeup.R;
import com.teamjaj.jajmeup.activities.SettingsActivity;
import com.teamjaj.jajmeup.utilities.BitmapUtils;
import com.teamjaj.jajmeup.utilities.Caller;

import java.io.IOException;

/*
    NOT USED AT THIS POINT

    THIS CLASS SHOULD PROVIDE A CONVENIENT WAY
    TO CREATE A CUSTOM PREFERENCE FIELD
    IN ORDER TO SELECT / SAVE A PROFILE
    PICTURE

    DO NOT DELETE
 */
public class ProfilePicturePreference extends Preference {

    private ImageView mImageView;

    public static final int GALLERY_ACTIVITY_CODE = 200;
    public static final int CROP_ACTIVITY_CODE = 400;
    public static final String PROFILE_PICTURE_SETTING = "profile_picture";

    public ProfilePicturePreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfilePicturePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWidgetLayoutResource(R.layout.profile_picture_preference);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        mImageView = (ImageView) view.findViewById(R.id.ProfilePicIV);

        String profilePictureFilename = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(PROFILE_PICTURE_SETTING, "");

        if(!profilePictureFilename.isEmpty()) {
            Log.d(this.getClass().getSimpleName(), "Retrieve profile picture from settings: " + profilePictureFilename);
            Bitmap picture;
            try {
                picture = BitmapUtils.getBitmapFromURI(getContext(), Uri.parse(profilePictureFilename));
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
            Caller.getPicture();
        }
    }

    @Override
    protected void onClick() {
        SettingsActivity activity = (SettingsActivity) getContext();
        if (activity != null) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            activity.startActivityForResult(intent, GALLERY_ACTIVITY_CODE);
        }
    }
}
