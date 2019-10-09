package global.imas.bintouch;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
public class ProfileSeenByPublic extends AppCompatActivity {

    private DatabaseReference mUsersDatabase, mChatDatabase, mPublicProfileDatabase;
    private FirebaseUser mCurrent_user;

    ImageView IV;
    TextView Tname, Tstatus;

    MediaPlayer mp;
    int flagito = 0;

    Uri FilePathUri;

    Button B47,B48,B49;

    String Storage_Path = "All_Image_Uploads/";
    String Storage_Path2 = "Music_Uploads/";
    String Database_Path = "All_Image_Uploads_Database";
    StorageReference storageReference= FirebaseStorage.getInstance().getReference();

    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    String hdf;
    ProgressDialog progressBar;
    private StorageReference mStorage;

    private ProgressDialog mProgress;

    void attachaudio()
    {
        mStorage=FirebaseStorage.getInstance().getReference();
        progressBar = new ProgressDialog(this);

        progressBar.setMessage("Uploading background music");
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();


        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder( 35 );
        for( int i = 0; i < 35; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );


        StorageReference storageReference2nd = storageReference.child(Storage_Path2 + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

        storageReference2nd.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                if (progressBar.isShowing()) progressBar.dismiss();


                hdf = taskSnapshot.getDownloadUrl().toString();

                //  HashMap<String, String> object = new HashMap<String, String>();
                // object.put("image", hdf);

                mPublicProfileDatabase.child(uuser_id).child("music").setValue(hdf).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        if(mp!=null)
                            mp.stop();
                        Toast.makeText(ProfileSeenByPublic.this, "Uploaded!", Toast.LENGTH_LONG).show();

                    }
                });


            }
        });




    }

    void attachimage()
    {
        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            Bitmap bmp = null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            byte[] data = baos.toByteArray();
            //uploading the image
            UploadTask uploadTask2 = storageReference2nd.putBytes(data);
            progressBar = ProgressDialog.show(this, "Uploading...\n", " ");

            uploadTask2.addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if (progressBar.isShowing()) progressBar.dismiss();


                            hdf = taskSnapshot.getDownloadUrl().toString();

                          //  HashMap<String, String> object = new HashMap<String, String>();
                           // object.put("image", hdf);

                            mPublicProfileDatabase.child(uuser_id).child("image").setValue(hdf).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if(mp!=null)
                                        mp.stop();

                                    Toast.makeText(ProfileSeenByPublic.this, "Uploaded!", Toast.LENGTH_LONG).show();

                                }
                            });



                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            if (progressBar.isShowing()) progressBar.dismiss();
                            Toast.makeText(ProfileSeenByPublic.this, "Error!", Toast.LENGTH_LONG).show();

                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                            // progressDialog.setTitle("");

                        }
                    });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


if(data!=null) {
    if (flagito == 0) {
        FilePathUri = data.getData();
        attachimage();
    }
    if (flagito == 1) {
        FilePathUri = data.getData();
        attachaudio();
    }
    //  bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
    //   IVlogo.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 500, 500, false));
    // IVlogo.getLayoutParams().height = 500;
    // IVlogo.getLayoutParams().width = 500;
    //   Blogoupload.setText("Change logo (150x150 Px)");
    //  Picasso.with(context).load(FilePathUri).resize(500, 500).centerCrop().into(IVlogo);
    //  }



       /* else if (flagito==855)
        {
            if (resultCode == Activity.RESULT_OK) {
                Uri contactData = data.getData();
                Cursor c =  managedQuery(contactData, null, null, null, null);
                if (c.moveToFirst()) {
                    String name = c.getString(c.getColumnIndexOrThrow(People.NAME));
                    // TODO Whatever you want to do with the selected contact name.
                }
            }
        }*/
}
else
{
    super.onActivityResult(requestCode, resultCode, data);
}

    }

    Button B30, Battach;
    String uuser_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_seen_by_public);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(ProfileSeenByPublic.this);
        SharedPreferences.Editor editor11 = settings11.edit();

        String select11 = settings11.getString("select", "");  /// 0 is default if variable not found

        if(select11.equals("1"))
        {
            uuser_id = mCurrent_user.getUid()+ ";;;;;profile_1";
        }
        if(select11.equals("2"))
        {
            uuser_id = mCurrent_user.getUid()+ ";;;;;profile_2";
        }
        if(select11.equals("3"))
        {
            uuser_id = mCurrent_user.getUid()+ ";;;;;profile_3";
        }
        if(select11.equals("4"))
        {
            uuser_id = mCurrent_user.getUid()+ ";;;;;profile_4";
        }
        if(select11.equals("5"))
        {
            uuser_id = mCurrent_user.getUid()+ ";;;;;profile_5";
        }

        // Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
      //  setSupportActionBar(mToolbar);
       // getSupportActionBar().setTitle("Profile seen by public");
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        B47=(Button) findViewById(R.id.button47);
        B48=(Button) findViewById(R.id.button48);
        B49=(Button) findViewById(R.id.button49);

        B30=(Button) findViewById(R.id.button30);

        Battach=(Button) findViewById(R.id.button29);


        B30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                B30.setBackgroundResource(R.drawable.ic_play_arrow_red_24dp);
                mPublicProfileDatabase.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(mp!=null)
                            mp.stop();
                         mp = new MediaPlayer();
                        try {
                            mp.setDataSource(dataSnapshot.child("music").getValue().toString());
                            mp.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mp.start();
                        B30.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        B47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flagito = 0;
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), 7);
            }
        });
        Battach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagito = 1;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                // Setting intent type as image to select image from phone storage.
                intent.setType("audio/*");
               // intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select a music"), 8);

            }
        });
        B48.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ProfileSeenByPublic.this);
                dialog.setContentView(R.layout.msglayout_name);
                dialog.setTitle("Please select :");

                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Button BB = (Button) dialog.findViewById(R.id.button10);
                final EditText EE = (EditText) dialog.findViewById(R.id.editText7);

                dialog.show();

                BB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        mPublicProfileDatabase.child(uuser_id).child("name").setValue(EE.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(ProfileSeenByPublic.this, "Updated!", Toast.LENGTH_LONG).show();

                            }
                        });


                        //  firebaseRecyclerAdapter.notifyItemRemoved(position);
                    }
                });

            }
        });
        B49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ProfileSeenByPublic.this);
                dialog.setContentView(R.layout.msglayout_status);
                dialog.setTitle("Please select :");

                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Button BB = (Button) dialog.findViewById(R.id.button10);
                final EditText EE = (EditText) dialog.findViewById(R.id.editText7);

                dialog.show();

                BB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        mPublicProfileDatabase.child(uuser_id).child("status").setValue(EE.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(ProfileSeenByPublic.this, "Updated!", Toast.LENGTH_LONG).show();

                            }
                        });


                        //  firebaseRecyclerAdapter.notifyItemRemoved(position);
                    }
                });
            }
        });

        IV = (ImageView)findViewById(R.id.imageView2);
        Tname = (TextView)findViewById(R.id.profile_display_name);
        Tstatus = (TextView)findViewById(R.id.profile_status);

        Tname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(ProfileSeenByPublic.this);
                dialog.setContentView(R.layout.msglayout_name);
                dialog.setTitle("Please select :");

                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Button BB = (Button) dialog.findViewById(R.id.button10);
                final EditText EE = (EditText) dialog.findViewById(R.id.editText7);

                dialog.show();

                BB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        mPublicProfileDatabase.child(uuser_id).child("name").setValue(EE.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(ProfileSeenByPublic.this, "Updated!", Toast.LENGTH_LONG).show();

                            }
                        });


                        //  firebaseRecyclerAdapter.notifyItemRemoved(position);
                    }
                });


            }
        });


        Tstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(ProfileSeenByPublic.this);
                dialog.setContentView(R.layout.msglayout_status);
                dialog.setTitle("Please select :");

                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Button BB = (Button) dialog.findViewById(R.id.button10);
                final EditText EE = (EditText) dialog.findViewById(R.id.editText7);

                dialog.show();

                BB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        mPublicProfileDatabase.child(uuser_id).child("status").setValue(EE.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(ProfileSeenByPublic.this, "Updated!", Toast.LENGTH_LONG).show();

                            }
                        });

                        //  firebaseRecyclerAdapter.notifyItemRemoved(position);
                    }
                });


            }
        });

        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                flagito = 0;
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), 7);
*/
            }
        });


        if(mp!=null)
            mp.stop();

        mPublicProfileDatabase = FirebaseDatabase.getInstance().getReference().child("Profile_public");
        mPublicProfileDatabase.keepSynced(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);


        mPublicProfileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(uuser_id))
                {
                    mPublicProfileDatabase.child(uuser_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Tname.setText(dataSnapshot.child("name").getValue().toString());
                            Tstatus.setText(dataSnapshot.child("status").getValue().toString());
                            Picasso.with(ProfileSeenByPublic.this).load(dataSnapshot.child("image").getValue().toString()).placeholder(R.drawable.errf).into(IV);

                            if(mp!=null)
                                mp.stop();
                             mp = new MediaPlayer();
                            try {
                                mp.setDataSource(dataSnapshot.child("music").getValue().toString());
                                mp.prepare();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mp.start();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else
                {

                    mUsersDatabase.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String display_name = dataSnapshot.child("name").getValue().toString();
                            String image = dataSnapshot.child("image").getValue().toString();

                            Tname.setText(display_name);
                            Tstatus.setText("Hi, I am using BinTouch!");


                            HashMap<String, String> object = new HashMap<String, String>();
                            object.put("image", image);
                            object.put("name", display_name);
                            object.put("status", "Hi, I am using BinTouch!");
                            object.put("music", "");

                            mPublicProfileDatabase.child(uuser_id).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // mprofilesendRequest.setEnabled(true);
                                    //  mCurrent_state = "req_sent";
                                    // mprofilesendRequest.setText("Cancel friend request");
                                    //  mDecline.setVisibility(View.INVISIBLE);
                                    //  mDecline.setEnabled(false);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        if (mp!= null) {
            if(mp.isPlaying()) {
                mp.stop();
            }
            mp.release();
            mp= null;
        }
       super.onBackPressed();
    }
}
