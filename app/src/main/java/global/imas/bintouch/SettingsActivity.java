package global.imas.bintouch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import id.zelory.compressor.Compressor;
public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    private ImageView mDisplayImage;
    private TextView mName;
    private TextView mStatus;

    private Button bchangestatus, mImageBtn;

    private Toolbar mToolbar;
    private ProgressDialog pd;

    private static final int GALLERY_PICK = 1;

    private StorageReference mImageStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mToolbar = (Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDisplayImage = (ImageView)findViewById(R.id.settings_image);
        mName= (TextView)findViewById(R.id.settings_display_name);
        mStatus= (TextView)findViewById(R.id.settings_status);

        mImageStorage = FirebaseStorage.getInstance().getReference();

        bchangestatus = (Button)findViewById(R.id.settings_status_bts) ;
        bchangestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = mStatus.getText().toString();

                Intent reg_intent = new Intent (SettingsActivity.this, StatusActivity.class);
                reg_intent.putExtra("status", s);
                startActivity(reg_intent);

            }
        });

        mImageBtn = (Button)findViewById(R.id.Settings_image_button) ;
        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent galleryIntent = new Intent ();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select image"),GALLERY_PICK);

            }
        });
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                final String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String thumb_image = dataSnapshot.child("Thumb_image").getValue().toString();

                Picasso.with(SettingsActivity.this).load(image).placeholder(R.drawable.pp).into(mDisplayImage);
/*
                if(!image.equals("")){
    Picasso.with(SettingsActivity.this).load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.pp).into(mDisplayImage, new Callback() {
        @Override
        public void onSuccess() {
            Picasso.with(SettingsActivity.this).load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.pp).into(mDisplayImage);

        }

        @Override
        public void onError() {
            Picasso.with(SettingsActivity.this).load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.pp).into(mDisplayImage);

        }
    });
}
*/

                mName.setText(name);
                mStatus.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(resultCode, resultCode,data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK)
        {
            pd = new ProgressDialog(SettingsActivity.this);
            pd.setTitle("Uploading profile photo");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

           Uri resulturi =  data.getData();

            final File thf = new File(resulturi.getPath());
             Bitmap thu = new Compressor(this) .setMaxWidth(200).setQuality(75)
                    .setMaxHeight(200).compressToBitmap(thf);
            try {
                thu = MediaStore.Images.Media.getBitmap(getContentResolver(), resulturi);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thu.compress(Bitmap.CompressFormat.JPEG,20,baos);
            final byte[] thb = baos.toByteArray();

           StorageReference filepath = mImageStorage.child("Profile_images").child(now()+System.currentTimeMillis() +".jpg");
            final StorageReference filepathTu = mImageStorage.child("Profile_images").child("Thumbs").child(now()+System.currentTimeMillis() +".jpg");


filepath.putFile(resulturi).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
        if(task.isSuccessful())
        {
         final String d = task.getResult().getDownloadUrl().toString();

            UploadTask uploadTask = filepathTu.putBytes(thb);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> tum_task) {

                    String dd = tum_task.getResult().getDownloadUrl().toString();
                    if(tum_task.isSuccessful())
                    {
                        Map u = new HashMap();
                        u.put("image", d);
                        u.put("Thumb_image", dd);

                        mUserDatabase.updateChildren(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    pd.dismiss();
                                }

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(SettingsActivity.this, "Error. Try Again",
                                Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });


        }
        else
        {

            Toast.makeText(SettingsActivity.this, "Error. Try Again",
                    Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }
});

        }


    }

    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }


    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(50);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
