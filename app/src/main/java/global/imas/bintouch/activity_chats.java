package global.imas.bintouch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;
import ja.burhanrashid52.photoeditor.SaveSettings;
import uk.co.senab.photoview.PhotoViewAttacher;

public class activity_chats extends AppCompatActivity implements SensorEventListener {

    int boldd = 0;
    String effect = "";
    static int first = 0;
    Uri imageUri;
    ImageView myImageView;
    Bitmap thumbnail;
    TextView ttb, tt;
    int yearf;
    int monthf;
    int dayf;
    int hourf;
    int minf;
    String Name_uuser_id = "x";
    String Name_user_id = "x";
    DatabaseReference mPublicProfileDatabase;
    CountDownTimer CDT;
    private ProgressDialog mRegProgress;
    Button bcgray;
    int color_r = 0, color_g = 0, color_b = 0;
    String uuser_id = "x";
    String msgg = "";
    String gf = "";
    Context context = this;

    Button Bsettings;
    String statewithyou = "", stateinapp = "";
    TextView Tnote1, Tnote2, Tnote3;
    String refreshedToken;

    View slideView, slideView2, slideView3;
    View slideViewD, slideView2D, slideView3D;

    Button Bnote, bnoteback;
    Button BnoteSave;
    EditText Enote;

    Button Bcoc, Bcocback;
    Button BcocSave;
    EditText Econc;

    String url = "http://ichef.bbci.co.uk/onesport/cps/480/cpsprodpb/11136/production/_95324996_defoe_rex.jpg";
    File file;
    String dirPath, fileName;

    private FirebaseUser mCurrent_user;
    private DatabaseReference mUsersDatabase, mUsersDatabase2, mUsersDatabase3, mAutoReplyDatabase, mUsersDatabaseo;
    private DatabaseReference mChatDatabase, mNotificationDatabase, mfriendsDatabase, mVideoCallRoomDatabase, mForward;
    private DatabaseReference mDelayedDatabase, mNoteDatabase, mStateDatabase_with_you, mStateDatabase_in_app, mHistoryDatabase;

    private RecyclerView mChatList;
    MediaPlayer mp;

    Button Bsend, Battach, Baudio, Bcallapp, Bcallapp2;
    EditText Emsg;
    LinearLayoutManager mLinearLayoutManager;
    int flag = 0;
    TextView Tstatus;
    int flag2 = 0;
    int flagito = 0;
    Uri FilePathUri;

    String outputaa;

    String Storage_Path = "All_Image_Uploads/";
    String Storage_Path2 = "Music_Uploads/";
    String Database_Path = "All_Image_Uploads_Database";
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    Button B89;
    PhotoEditorView mPhotoEditorView;
    Button B90cancel;
    Button B91sendwithmod;
    Button B92sendwithoutmod;
    TextView T96;

    View vieww;
    PhotoEditor mPhotoEditor;

    Button B93 ;
    Button B95 ;
    Button B96 ;
    Button B97 ;
    Button B98 ;
    Button B99 ;
    Button B100, B101 ;

    public boolean isApplicationSentToBackground(final Context context)
    {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onStop()
    {
        if (isApplicationSentToBackground(this)){
            //put your code here what u want to do
            DatabaseReference userStatussssa = FirebaseDatabase.getInstance().getReference().child("State_in_app").child(uuser_id);
            userStatussssa.setValue("Offline_in_app");
        }
        super.onStop();
        // FirebaseDatabase.getInstance().goOffline();

    }


    private void performCrop() {
        // take care of exceptions
        flagito = 3;


        try {

             B93 = (Button)findViewById(R.id.button93);
             B95 = (Button)findViewById(R.id.button95);
             B96 = (Button)findViewById(R.id.button96);
             B97 = (Button)findViewById(R.id.button97);
             B98 = (Button)findViewById(R.id.button98);
             B99 = (Button)findViewById(R.id.button99);
             B100 = (Button)findViewById(R.id.button100);
            B101 = (Button)findViewById(R.id.button94);

            B93.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = findViewById(R.id.editText15);
                    editText.setTextColor(Color.rgb(255,0,0));
                    mPhotoEditor.setBrushColor(Color.rgb(255,0,0));
                }
            });
            B95.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = findViewById(R.id.editText15);
                    editText.setTextColor(Color.rgb(0,255,0));
                    mPhotoEditor.setBrushColor(Color.rgb(0,255,0));
                }
            });
            B96.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = findViewById(R.id.editText15);
                    editText.setTextColor(Color.rgb(0,0,255));
                    mPhotoEditor.setBrushColor(Color.rgb(0,0,255));
                }
            });
            B97.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = findViewById(R.id.editText15);
                    editText.setTextColor(Color.rgb(255,255,255));
                    mPhotoEditor.setBrushColor(Color.rgb(255,255,255));
                }
            });
            B98.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = findViewById(R.id.editText15);
                    editText.setTextColor(Color.rgb(0,0,0));
                    mPhotoEditor.setBrushColor(Color.rgb(0,0,0));
                }
            });
            B99.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = findViewById(R.id.editText15);
                    editText.setTextColor(Color.rgb(240,240,0));
                    mPhotoEditor.setBrushColor(Color.rgb(240,240,0));
                }
            });
            B100.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = findViewById(R.id.editText15);
                    editText.setTextColor(Color.rgb(255,0,128));
                    mPhotoEditor.setBrushColor(Color.rgb(255,0,128));
                }
            });

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            //"com.android.camera.action.EDITOR_CROP"

            B101.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                EditText editText = findViewById(R.id.editText15);

                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                    editText.setEnabled(true);
                    editText.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

                }
            });

            mPhotoEditorView = findViewById(R.id.photoEditorView);
            B90cancel = (Button) findViewById(R.id.button90);
            B92sendwithoutmod = (Button) findViewById(R.id.button92);
            B89 = (Button) findViewById(R.id.button89);
            B91sendwithmod = (Button) findViewById(R.id.button91);
            vieww = (View) findViewById(R.id.view);

            T96 = (TextView) findViewById(R.id.textView96);
            T96.setVisibility(View.VISIBLE);

            mPhotoEditorView.setVisibility(View.VISIBLE);
            B89.setVisibility(View.VISIBLE);
            B90cancel.setVisibility(View.VISIBLE);
            B91sendwithmod.setVisibility(View.VISIBLE);
            B92sendwithoutmod.setVisibility(View.VISIBLE);
            vieww.setVisibility(View.VISIBLE);

             B93.setVisibility(View.VISIBLE) ;
             B95.setVisibility(View.VISIBLE) ;
             B96.setVisibility(View.VISIBLE) ;
             B97.setVisibility(View.VISIBLE) ;
             B98.setVisibility(View.VISIBLE) ;
             B99.setVisibility(View.VISIBLE) ;
             B100.setVisibility(View.VISIBLE) ;
            B101.setVisibility(View.VISIBLE) ;
            // mPhotoEditorView.getSource().setImageResource(R.drawable.emptyi);


            mPhotoEditorView.getSource().setImageURI(FilePathUri);

            //Use custom font using latest support library
            // Typeface mTextRobotoTf = ResourcesCompat.getFont(this, 1);

//loading font from assest
            //   Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");

             mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                    .setPinchTextScalable(true)
                    // .setDefaultTextTypeface(mTextRobotoTf)
                    // .setDefaultEmojiTypeface(mEmojiTypeFace)
                    .build();

            mPhotoEditor.setBrushDrawingMode(true);


            B90cancel.setVisibility(View.VISIBLE);
            B90cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Toast.makeText(activity_chats.this, "tes", Toast.LENGTH_SHORT).show();
                    mPhotoEditorView.setVisibility(View.INVISIBLE);
                    B89.setVisibility(View.INVISIBLE);
                    B90cancel.setVisibility(View.INVISIBLE);
                    B91sendwithmod.setVisibility(View.INVISIBLE);
                    B92sendwithoutmod.setVisibility(View.INVISIBLE);
                    vieww.setVisibility(View.INVISIBLE);

                    B93.setVisibility(View.INVISIBLE) ;
                    B95.setVisibility(View.INVISIBLE) ;
                    B96.setVisibility(View.INVISIBLE) ;
                    B97.setVisibility(View.INVISIBLE) ;
                    B98.setVisibility(View.INVISIBLE) ;
                    B99.setVisibility(View.INVISIBLE) ;
                    B100.setVisibility(View.INVISIBLE) ;
                    B101.setVisibility(View.INVISIBLE) ;



                    mPhotoEditor.clearAllViews();


                }
            });


            B92sendwithoutmod.setVisibility(View.VISIBLE);
            B92sendwithoutmod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPhotoEditorView.setVisibility(View.INVISIBLE);
                    B89.setVisibility(View.INVISIBLE);
                    B90cancel.setVisibility(View.INVISIBLE);
                    B91sendwithmod.setVisibility(View.INVISIBLE);
                    B92sendwithoutmod.setVisibility(View.INVISIBLE);
                    vieww.setVisibility(View.INVISIBLE);
                    T96.setVisibility(View.INVISIBLE);

                    B93.setVisibility(View.INVISIBLE) ;
                    B95.setVisibility(View.INVISIBLE) ;
                    B96.setVisibility(View.INVISIBLE) ;
                    B97.setVisibility(View.INVISIBLE) ;
                    B98.setVisibility(View.INVISIBLE) ;
                    B99.setVisibility(View.INVISIBLE) ;
                    B100.setVisibility(View.INVISIBLE) ;
                    B101.setVisibility(View.INVISIBLE) ;

                    mPhotoEditor.clearAllViews();

                    attachimage();
                }
            });


            B91sendwithmod.setVisibility(View.VISIBLE);
            B91sendwithmod.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View view) {

                    mPhotoEditorView.setVisibility(View.INVISIBLE);
                    B89.setVisibility(View.INVISIBLE);
                    vieww.setVisibility(View.INVISIBLE);
                    B90cancel.setVisibility(View.INVISIBLE);
                    B91sendwithmod.setVisibility(View.INVISIBLE);
                    B92sendwithoutmod.setVisibility(View.INVISIBLE);

                    B93.setVisibility(View.INVISIBLE) ;
                    B95.setVisibility(View.INVISIBLE) ;
                    B96.setVisibility(View.INVISIBLE) ;
                    B97.setVisibility(View.INVISIBLE) ;
                    B98.setVisibility(View.INVISIBLE) ;
                    B99.setVisibility(View.INVISIBLE) ;
                    B100.setVisibility(View.INVISIBLE) ;
                    T96.setVisibility(View.INVISIBLE);
                    B101.setVisibility(View.INVISIBLE);

                    File file = new File(Environment.getExternalStorageDirectory()
                            + File.separator + ""
                            + System.currentTimeMillis() + ".png");
                    try {
                        file.createNewFile();

                        SaveSettings saveSettings = new SaveSettings.Builder()
                                .setClearViewsEnabled(true)
                                .setTransparencyEnabled(true)
                                .build();


                        mPhotoEditor.saveAsFile(file.getAbsolutePath(), new PhotoEditor.OnSaveListener() {
                            @Override
                            public void onSuccess(@NonNull String imagePath) {

                                FilePathUri = Uri.fromFile(new File(imagePath));

                               // Toast.makeText(activity_chats.this, ""+FilePathUri, Toast.LENGTH_SHORT).show();

                                mPhotoEditorView.setVisibility(View.INVISIBLE);
                                B89.setVisibility(View.INVISIBLE);
                                vieww.setVisibility(View.INVISIBLE);
                                B90cancel.setVisibility(View.INVISIBLE);
                                B91sendwithmod.setVisibility(View.INVISIBLE);
                                B92sendwithoutmod.setVisibility(View.INVISIBLE);


                                B93.setVisibility(View.INVISIBLE) ;
                                B95.setVisibility(View.INVISIBLE) ;
                                B96.setVisibility(View.INVISIBLE) ;
                                B97.setVisibility(View.INVISIBLE) ;
                                B98.setVisibility(View.INVISIBLE) ;
                                B99.setVisibility(View.INVISIBLE) ;
                                B100.setVisibility(View.INVISIBLE) ;
                                B101.setVisibility(View.INVISIBLE);
                                attachimage();
                            }

                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.e("PhotoEditor", "Failed to save Image");
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

            B89.setVisibility(View.VISIBLE);
            B89.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    B90cancel.setVisibility(View.INVISIBLE);
                    B91sendwithmod.setVisibility(View.INVISIBLE);
                    B92sendwithoutmod.setVisibility(View.INVISIBLE);
                    B89.setVisibility(View.INVISIBLE);
                    T96.setVisibility(View.INVISIBLE);

                    File file = new File(Environment.getExternalStorageDirectory()
                            + File.separator + ""
                            + System.currentTimeMillis() + ".png");
                    try {
                        file.createNewFile();


                        if (ActivityCompat.checkSelfPermission(activity_chats.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(activity_chats.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                            return;
                        }

                        mPhotoEditor.saveAsFile(file.getAbsolutePath(), new PhotoEditor.OnSaveListener() {
                            @Override
                            public void onSuccess(@NonNull String imagePath) {

                               // FilePathUri = Uri.parse(file.getAbsolutePath());
                                FilePathUri = Uri.fromFile(new File(imagePath));

                                mPhotoEditorView.setVisibility(View.INVISIBLE);
                                B89.setVisibility(View.INVISIBLE);
                                vieww.setVisibility(View.INVISIBLE);
                                B90cancel.setVisibility(View.INVISIBLE);
                                B91sendwithmod.setVisibility(View.INVISIBLE);
                                B92sendwithoutmod.setVisibility(View.INVISIBLE);


                                B93.setVisibility(View.INVISIBLE) ;
                                B95.setVisibility(View.INVISIBLE) ;
                                B96.setVisibility(View.INVISIBLE) ;
                                B97.setVisibility(View.INVISIBLE) ;
                                B98.setVisibility(View.INVISIBLE) ;
                                B99.setVisibility(View.INVISIBLE) ;
                                B100.setVisibility(View.INVISIBLE) ;
                                B101.setVisibility(View.INVISIBLE);

                                flagito = 3;

                              //  Toast.makeText(activity_chats.this, ""+FilePathUri, Toast.LENGTH_SHORT).show();

                                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                StrictMode.setVmPolicy(builder.build());

                                Intent editIntent = new Intent(Intent.ACTION_EDIT);
                                editIntent.setDataAndType(FilePathUri, "image/*");
                                editIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivityForResult(Intent.createChooser(editIntent, null), 1);

                            }

                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.e("PhotoEditor", "Failed to save Image");
                            }
                        });

                                } catch (IOException e) {
                                    e.printStackTrace();
                          }




















                }
            });


        }

        catch (ActivityNotFoundException anfe) {
        }
    }

    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    String output;
    void attachimage()
    {

        final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
        c.add(Calendar.DATE, 0);
        Date resultdate;
        resultdate = new Date(c.getTimeInMillis());
        String s =sdf.format(resultdate);

        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
        int currentmin = rightNow.get(Calendar.MINUTE); //

        String ffp = String.valueOf(currentmin).toString();
        if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

        String hour = String.valueOf(currentHour).toString() + ":" + ffp;
        if(hour.length()<5) hour = "0"+hour;
        String DateTime = s + hour;

        HashMap<String, String> object = new HashMap<String, String>();
        object.put("from", uuser_id);
        object.put("msg_or_url", "Uploading<br><br><br><br>Image");
        object.put("msg_type", "image"+System.currentTimeMillis());
        object.put("dateTime", DateTime);
        object.put("sent_received_seen", "sent"); //  sent , received, seen

        output = mChatDatabase.child(uuser_id).child(user_id).push().getKey();

        object.put("from_orignal_id", uuser_id);
        object.put("from_orignal_key", output); //  sent , received, seen


     //   mChatDatabase.child(uuser_id).child(user_id).child(pushKey).setValue(user);
    /*
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < 40; i++) {
            char c2 = chars[random.nextInt(chars.length)];
            sb.append(c2);
        }
         output = sb.toString();
*/




        mChatDatabase.child(uuser_id).child(user_id).child(output).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


                // Checking whether FilePathUri Is empty or not.
                if (FilePathUri != null) {
                    StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
                    Bitmap bmp = null;
                    try {
                        bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                    byte[] data = baos.toByteArray();

                     //   Toast.makeText(activity_chats.this, "tes", Toast.LENGTH_SHORT).show();
                    //uploading the image
                    UploadTask uploadTask2 = storageReference2nd.putBytes(data);
                    // progressBar = ProgressDialog.show(this, "Uploading...\n", " ");

                     //   Toast.makeText(activity_chats.this, ""+bmp, Toast.LENGTH_SHORT).show();

                    uploadTask2.addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    String hdf = taskSnapshot.getDownloadUrl().toString();


                                    final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    Calendar c = Calendar.getInstance();
                                    try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                    c.add(Calendar.DATE, 0);
                                    Date resultdate;
                                    resultdate = new Date(c.getTimeInMillis());
                                    String s =sdf.format(resultdate);

                                    Calendar rightNow = Calendar.getInstance();
                                    int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                    int currentmin = rightNow.get(Calendar.MINUTE); //

                                    String ffp = String.valueOf(currentmin).toString();
                                    if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

                                    String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                    if(hour.length()<5) hour = "0"+hour;
                                    String DateTime = s + hour;

                                    HashMap<String, String> object = new HashMap<String, String>();
                                    object.put("from", uuser_id);
                                    object.put("msg_or_url", hdf);
                                    object.put("msg_type", "image"+System.currentTimeMillis());
                                    object.put("dateTime", DateTime);
                                    object.put("sent_received_seen", "sent"); //  sent , received, seen

                                  String  outputs = mChatDatabase.child(user_id).child(uuser_id).push().getKey();

                                    object.put("from_orignal_id", uuser_id);
                                    object.put("from_orignal_key", outputs); //  sent , received, seen

                                    mChatDatabase.child(user_id).child(uuser_id).child(outputs).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });

                                    mChatDatabase.child(uuser_id).child(user_id).child(output).child("msg_or_url").setValue(hdf).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {


                                            final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            Calendar c = Calendar.getInstance();
                                            try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                            c.add(Calendar.DATE, 0);
                                            Date resultdate;
                                            resultdate = new Date(c.getTimeInMillis());
                                            String s =sdf.format(resultdate);

                                            Calendar rightNow = Calendar.getInstance();
                                            int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                            int currentmin = rightNow.get(Calendar.MINUTE); //

                                            String ffp = String.valueOf(currentmin).toString();
                                            if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

                                            String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                            if(hour.length()<5) hour = "0"+hour;
                                            String DateTime = s + hour;

                                            HashMap<String, String> object2 = new HashMap<String, String>();
                                            object2.put("msg_or_url", "New image ("+DateTime+")");

                                            if(!namito.equals(""))

                                                mNotificationDatabase.child(uuser_id).child(user_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                });
                                            else
                                                Toast.makeText(context, "The user account is deactivated", Toast.LENGTH_SHORT).show();




                                            mfriendsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.hasChild(user_id)) {
                                                        mfriendsDatabase.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                if (dataSnapshot.hasChild(uuser_id)) {
                                                                    mfriendsDatabase.child(user_id).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                                            final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                                            Calendar c = Calendar.getInstance();
                                                                            try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                                            c.add(Calendar.DATE, 0);
                                                                            Date resultdate;
                                                                            resultdate = new Date(c.getTimeInMillis());
                                                                            String s =sdf.format(resultdate);
                                                                            Calendar rightNow = Calendar.getInstance();
                                                                            int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                                            int currentmin = rightNow.get(Calendar.MINUTE); //
                                                                            String ffp = String.valueOf(currentmin).toString();
                                                                            if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                                            String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                                            if(hour.length()<5) hour = "0"+hour;
                                                                            String DateTime = s + hour;

                                                                            //checked
                                                                            int count = Integer.parseInt(dataSnapshot.child("count").getValue(String.class).substring(15));
                                                                            count++;


                                                                            //checked
                                                                            mfriendsDatabase.child(user_id).child(uuser_id).child("count").setValue(DateTime+ count).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {

                                                                                }
                                                                            });
                                                                            mfriendsDatabase.child(uuser_id).child(user_id).child("count").setValue(DateTime+ "0").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {

                                                                                }
                                                                            });
                                                                            mfriendsDatabase.child(user_id).child(uuser_id).child("status").setValue("Sent an image").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {

                                                                                }
                                                                            });
                                                                            mfriendsDatabase.child(uuser_id).child(user_id).child("status").setValue("Sent an image").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {

                                                                                }
                                                                            });


                                                                        }

                                                                        @Override
                                                                        public void onCancelled(DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                                } else {
                                                                    mUsersDatabaseo.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                                            final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                                            Calendar c = Calendar.getInstance();
                                                                            try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                                            c.add(Calendar.DATE, 0);
                                                                            Date resultdate;
                                                                            resultdate = new Date(c.getTimeInMillis());
                                                                            String s =sdf.format(resultdate);
                                                                            Calendar rightNow = Calendar.getInstance();
                                                                            int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                                            int currentmin = rightNow.get(Calendar.MINUTE); //
                                                                            String ffp = String.valueOf(currentmin).toString();
                                                                            if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                                            String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                                            if(hour.length()<5) hour = "0"+hour;
                                                                            String DateTime = s + hour;


                                                                            HashMap<String, String> userMap2 = new HashMap<>();
                                                                            userMap2.put("name", dataSnapshot.child("name").getValue(String.class));
                                                                            userMap2.put("status", "Sent an image");
                                                                            userMap2.put("image", dataSnapshot.child("image").getValue(String.class));
                                                                            userMap2.put("count", DateTime+"1");
                                                                            //checked

                                                                            mfriendsDatabase.child(user_id).child(uuser_id).setValue(userMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {

                                                                                }
                                                                            });
                                                                            userMap2.put("count", DateTime+"0");
                                                                            mfriendsDatabase.child(uuser_id).child(user_id).setValue(userMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {

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


                                                    } else {
                                                        mUsersDatabaseo.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshotw) {

                                                                final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                                Calendar c = Calendar.getInstance();
                                                                try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                                c.add(Calendar.DATE, 0);
                                                                Date resultdate;
                                                                resultdate = new Date(c.getTimeInMillis());
                                                                String s =sdf.format(resultdate);
                                                                Calendar rightNow = Calendar.getInstance();
                                                                int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                                int currentmin = rightNow.get(Calendar.MINUTE); //
                                                                String ffp = String.valueOf(currentmin).toString();
                                                                if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                                String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                                if(hour.length()<5) hour = "0"+hour;
                                                                String DateTime = s + hour;

                                                                HashMap<String, String> userMaps2 = new HashMap<>();
                                                                userMaps2.put("name", dataSnapshotw.child("name").getValue(String.class));
                                                                userMaps2.put("status", "Sent an image");
                                                                userMaps2.put("image", "" + dataSnapshotw.child("image").getValue(String.class));
                                                                userMaps2.put("count", DateTime+"1");
                                                                //checked

                                                                mfriendsDatabase.child(user_id).child(uuser_id).setValue(userMaps2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

                                                                    }
                                                                });

                                                                userMaps2.put("count", DateTime+"0");
                                                                mfriendsDatabase.child(uuser_id).child(user_id).setValue(userMaps2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

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
                                    });

                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                        mChatDatabase.child(uuser_id).child(user_id).child(output).removeValue();
                }
                }

                                }
                            });


                }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( flagito == 3 ) {
            if (resultCode == RESULT_OK)
                FilePathUri=data.getData();
               attachimage();

        }
        else if ( flagito == -1 ) {
            FilePathUri = imageUri;
            //   FilePathUri=data.getData();
            if (resultCode == RESULT_OK) {
                performCrop();
            }

        }
        else if(data!=null && flagito == 0) {
            FilePathUri=data.getData();
            performCrop();
         }
         else
         {
             super.onActivityResult(requestCode, resultCode, data);

         }

           // if (flagito == 1) {
            //    FilePathUri = data.getData();
             //   attachaudio();
          //  }
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
                   //
                }
            }
        }*/

    }


    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation( 0,0, -150, 0);
        shake.setDuration(500000);
        shake.setInterpolator(new CycleInterpolator(500));
        return shake;
    }

    Double countaudio = 0.0;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    CountDownTimer CDTaudio;

    private void startRecording()
    {
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        try {
            myAudioRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myAudioRecorder.start();


        //Baudiostop.setVisibility(View.VISIBLE);

        String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dateInString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 0);
        Date resultdate;
        resultdate = new Date(c.getTimeInMillis());
        String s =sdf.format(resultdate);

        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
        int currentmin = rightNow.get(Calendar.MINUTE); //

        String ffp = String.valueOf(currentmin).toString();
        if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

        String hour = String.valueOf(currentHour).toString() + ":" + ffp;
        if(hour.length()<5) hour = "0"+hour;


        countaudio = 0.0;
        flag =0;

        CDTaudio = new CountDownTimer(100000000, 100) {
            @Override
            public void onTick(long l) {

                double ti = countaudio;
                int m =0, s=0, t=0;

                if(ti>=60) m = (int)ti/60;
                ti=ti-m*60;
                if(ti>=1) s = (int)ti;
                ti=ti-s;
                t=(int)(ti*10);

                String mm ="", ss="", tt="";

                if(m<10) mm="0"+m; else mm=""+m;
                if(s<10) ss="0"+s; else ss=""+s;

                tt=t+"0";


                Emsg.setText(Html.fromHtml("<font color=\"red\">"+mm+":"+ss+":"+tt+ "  sec</font>"));

                // DecimalFormat df = new DecimalFormat("0.0");
                // Taudiotime.setText(df.format(countaudio) + "  sec");

                if((int)(countaudio*10)%4==0)
                {
                    if(flag==0)
                    {
                       // IVaudio.setImageResource(R.drawable.roundbutton4);
                        flag=1;
                    }
                    else
                    {
                       // IVaudio.setImageResource(R.drawable.roundbuttongray);
                        flag=0;
                    }
                }

                countaudio+=0.1;
            }

            @Override
            public void onFinish() {

            }
        }.start();









    }

    int baba = 1;

    private void stopRecording()
    {
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;

        CDTaudio.cancel();

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.msglayout_addaccount);
        dialog.setTitle("View:");

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setCanceledOnTouchOutside(false);

        Button Bsend = (Button) dialog.findViewById(R.id.button15);
        Button Bcancel = (Button) dialog.findViewById(R.id.button60);

        dialog.show();

        Bsend.setText("Send");
        Bcancel.setText("Cancel");

        Bsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wow = Emsg.getText().toString();
                Emsg.setText("");
                effect = "";

                uploadAudio();
                dialog.cancel();
            }
        });

        Bcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Emsg.setText("");
                effect = "";
                dialog.cancel();
            }
        });


        // Emsg.setVisibility(View.VISIBLE);
//if(baba==1)

    }

    private StorageReference mStorage;

String wow = "";
    private void uploadAudio() {
        mStorage=FirebaseStorage.getInstance().getReference();





        final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
        c.add(Calendar.DATE, 0);
        Date resultdate;
        resultdate = new Date(c.getTimeInMillis());
        String s =sdf.format(resultdate);

        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
        int currentmin = rightNow.get(Calendar.MINUTE); //

        String ffp = String.valueOf(currentmin).toString();
        if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

        String hour = String.valueOf(currentHour).toString() + ":" + ffp;
        if(hour.length()<5) hour = "0"+hour;
        String DateTime = s + hour;

        HashMap<String, String> object = new HashMap<String, String>();
        object.put("from", uuser_id);
        object.put("msg_or_url", "<br><br><br><br><br><i>Uploading Audio");
        object.put("msg_type", "audio"+System.currentTimeMillis()+";;;;;;;;;;"+wow);
        object.put("dateTime", DateTime);
        object.put("sent_received_seen", "sent"); //  sent , received, seen


        output = mChatDatabase.child(uuser_id).child(user_id).push().getKey();

        object.put("from_orignal_id", uuser_id);
        object.put("from_orignal_key", output); //  sent , received, seen


        mChatDatabase.child(uuser_id).child(user_id).child(output).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


                final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                SecureRandom rnd = new SecureRandom();

                StringBuilder sb = new StringBuilder( 35 );
                for( int i = 0; i < 35; i++ )
                    sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );

                StorageReference filepath = mStorage.child("Audio").child(sb.toString()+".3gp");
                Uri uri = Uri.fromFile(new File(outputFile));
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        String hdf = taskSnapshot.getDownloadUrl().toString();


                        final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c = Calendar.getInstance();
                        try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                        c.add(Calendar.DATE, 0);
                        Date resultdate;
                        resultdate = new Date(c.getTimeInMillis());
                        String s =sdf.format(resultdate);

                        Calendar rightNow = Calendar.getInstance();
                        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                        int currentmin = rightNow.get(Calendar.MINUTE); //

                        String ffp = String.valueOf(currentmin).toString();
                        if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

                        String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                        if(hour.length()<5) hour = "0"+hour;
                        String DateTime = s + hour;

                        HashMap<String, String> object = new HashMap<String, String>();
                        object.put("from", uuser_id);
                        object.put("msg_or_url", hdf);
                        object.put("msg_type", "audio"+System.currentTimeMillis()+";;;;;;;;;;"+wow);
                      // Emsg.setText("");
                        object.put("dateTime", DateTime);
                        object.put("sent_received_seen", "sent"); //  sent , received, seen

                        String  outputsss = mChatDatabase.child(user_id).child(uuser_id).push().getKey();

                        object.put("from_orignal_id", uuser_id);
                        object.put("from_orignal_key", outputsss); //  sent , received, seen


                        mChatDatabase.child(user_id).child(uuser_id).child(outputsss).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

                        mChatDatabase.child(uuser_id).child(user_id).child(output).child("msg_or_url").setValue(hdf).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar c = Calendar.getInstance();
                                try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                c.add(Calendar.DATE, 0);
                                Date resultdate;
                                resultdate = new Date(c.getTimeInMillis());
                                String s =sdf.format(resultdate);

                                Calendar rightNow = Calendar.getInstance();
                                int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                int currentmin = rightNow.get(Calendar.MINUTE); //

                                String ffp = String.valueOf(currentmin).toString();
                                if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

                                String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                if(hour.length()<5) hour = "0"+hour;
                                String DateTime = s + hour;

                                HashMap<String, String> object2 = new HashMap<String, String>();
                                object2.put("msg_or_url", "New audio message ("+DateTime+")");

                                if(!namito.equals(""))

                                    mNotificationDatabase.child(uuser_id).child(user_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });
                                else
                                    Toast.makeText(context, "The user account is deactivated", Toast.LENGTH_SHORT).show();




                                mfriendsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild(user_id)) {
                                            mfriendsDatabase.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    if (dataSnapshot.hasChild(uuser_id)) {
                                                        mfriendsDatabase.child(user_id).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                                Calendar c = Calendar.getInstance();
                                                                try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                                c.add(Calendar.DATE, 0);
                                                                Date resultdate;
                                                                resultdate = new Date(c.getTimeInMillis());
                                                                String s =sdf.format(resultdate);
                                                                Calendar rightNow = Calendar.getInstance();
                                                                int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                                int currentmin = rightNow.get(Calendar.MINUTE); //
                                                                String ffp = String.valueOf(currentmin).toString();
                                                                if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                                String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                                if(hour.length()<5) hour = "0"+hour;
                                                                String DateTime = s + hour;
                                                                //checked

                                                                int count = Integer.parseInt(dataSnapshot.child("count").getValue(String.class).substring(15));
                                                                count++;

                                                                //checked
                                                                mfriendsDatabase.child(user_id).child(uuser_id).child("count").setValue(DateTime+count ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

                                                                    }
                                                                });
                                                                mfriendsDatabase.child(uuser_id).child(user_id).child("count").setValue(DateTime+"0").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

                                                                    }
                                                                });

                                                                mfriendsDatabase.child(user_id).child(uuser_id).child("status").setValue("Sent an audio").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

                                                                    }
                                                                });
                                                                mfriendsDatabase.child(uuser_id).child(user_id).child("status").setValue("Sent an audio").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

                                                                    }
                                                                });
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                                    } else {
                                                        mUsersDatabaseo.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                                Calendar c = Calendar.getInstance();
                                                                try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                                c.add(Calendar.DATE, 0);
                                                                Date resultdate;
                                                                resultdate = new Date(c.getTimeInMillis());
                                                                String s =sdf.format(resultdate);
                                                                Calendar rightNow = Calendar.getInstance();
                                                                int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                                int currentmin = rightNow.get(Calendar.MINUTE); //
                                                                String ffp = String.valueOf(currentmin).toString();
                                                                if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                                String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                                if(hour.length()<5) hour = "0"+hour;
                                                                String DateTime = s + hour;

                                                                HashMap<String, String> userMap2 = new HashMap<>();
                                                                userMap2.put("name", dataSnapshot.child("name").getValue(String.class));
                                                                userMap2.put("status", "Sent an audio");
                                                                userMap2.put("image", dataSnapshot.child("image").getValue(String.class));
                                                                userMap2.put("count", DateTime+"1");
                                                                //checked

                                                                mfriendsDatabase.child(user_id).child(uuser_id).setValue(userMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

                                                                    }
                                                                });
                                                                userMap2.put("count", DateTime+"0");
                                                                mfriendsDatabase.child(uuser_id).child(user_id).setValue(userMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

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


                                        } else {
                                            mUsersDatabaseo.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshotw) {

                                                    final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                    Calendar c = Calendar.getInstance();
                                                    try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                    c.add(Calendar.DATE, 0);
                                                    Date resultdate;
                                                    resultdate = new Date(c.getTimeInMillis());
                                                    String s =sdf.format(resultdate);
                                                    Calendar rightNow = Calendar.getInstance();
                                                    int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                    int currentmin = rightNow.get(Calendar.MINUTE); //
                                                    String ffp = String.valueOf(currentmin).toString();
                                                    if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                    String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                    if(hour.length()<5) hour = "0"+hour;
                                                    String DateTime = s + hour;


                                                    HashMap<String, String> userMaps2 = new HashMap<>();
                                                    userMaps2.put("name", dataSnapshotw.child("name").getValue(String.class));
                                                    userMaps2.put("status", "Sent an audio");
                                                    userMaps2.put("image", "" + dataSnapshotw.child("image").getValue(String.class));
                                                    userMaps2.put("count", DateTime+"1");
                                                    //checked

                                                    mfriendsDatabase.child(user_id).child(uuser_id).setValue(userMaps2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    });
                                                    userMaps2.put("count", DateTime+"0");
                                                    mfriendsDatabase.child(uuser_id).child(user_id).setValue(userMaps2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

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
                        });


                    }
                });

            }
        });


    }

String x1, x2;

    void checkx2()
    {
        mdata.child("Status_each_page").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id))
                {
                    mdata.child("Status_each_page").child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(uuser_id))
                            {
                                mdata.child("Status_each_page").child(user_id).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {


                                            x2 = dataSnapshot.child("status").getValue(String.class).toString();

                                        String r = "<font color ='#A06A40'><b>" +name2 + "</b>: "+ x2+"</font> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <b>"+name1 + "</b>: "+ x1 +"      ";
                                        while (r.length() < 70) { r += "" + r; }
                                        Tstatus.setText(Html.fromHtml(r));

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                            else
                            {
                                x2 = "Hello, I am using BnTouch!";

                                String r = "<font color ='#A06A40'><b>" +name2 + "</b>: "+ x2+"</font> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <b>"+name1 + "</b>: "+ x1 +"      ";
                                while (r.length() < 70) { r += "" + r; }
                                Tstatus.setText(Html.fromHtml(r));


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    x2 = "Hello, I am using BnTouch!";

                    String r = "<font color ='#A06A40'><b>" +name2 + "</b>: "+ x2+"</font> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <b>"+name1 + "</b>: "+ x1 +"      ";
                    while (r.length() < 70) { r += "" + r; }
                    Tstatus.setText(Html.fromHtml(r));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void checkx1()
    {
        mdata.child("Status_each_page").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(uuser_id))
                {
                    mdata.child("Status_each_page").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(user_id))
                            {
                                mdata.child("Status_each_page").child(uuser_id).child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {


                                        x1 = dataSnapshot.child("status").getValue(String.class).toString();

                                        checkx2();


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                            else {
                                x1 = "Hello, I am using BnTouch!";
                                checkx2();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    x1 = "Hello, I am using BnTouch!";
                    checkx2();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    String name1="";
    String name2="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);


        first = 0;
        if(CDT!=null)
            CDT.cancel();

        Button bsdb = (Button)findViewById(R.id.button37);
        bsdb.setText(Html.fromHtml("<u>U"));


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA},6);
        }
        if (ActivityCompat.checkSelfPermission(activity_chats.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity_chats.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (ActivityCompat.checkSelfPermission(activity_chats.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity_chats.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
        }
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECORD_AUDIO},5);
        }

        if (ActivityCompat.checkSelfPermission(activity_chats.this, Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity_chats.this, new String[]{Manifest.permission.SET_ALARM}, 7);
        }
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WAKE_LOCK},8);
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},9);
        }


      //   Paint mPaint;

     //   mPaint = new Paint();
        // on button click
     //   new ColorPickerDialog(this, this, mPaint.getColor()).show();

/*
        // Initialization Of DownLoad Button
        AndroidNetworking.initialize(getApplicationContext());
        //Folder Creating Into Phone Storage
        dirPath = Environment.getExternalStorageDirectory() +"/BinTouch";
        long timemill= System.currentTimeMillis();
        fileName = timemill +"";
        //file Creating With Folder & Fle Name
        file = new File(dirPath, fileName);

        AndroidNetworking.download(url, dirPath, fileName)
                .build()
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {

                        Toast.makeText(activity_chats.this, "DownLoad Complete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(activity_chats.this, anError.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

*/




        statewithyou ="";
        stateinapp="";

        Baudio = (Button) findViewById(R.id.button8);

        Baudio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
                {
                    Baudio.startAnimation(shakeError());
baba = 1;
                    startRecording();
                }
                else  if(motionEvent.getAction()==MotionEvent.ACTION_UP)
                {
                    new CountDownTimer(400,100) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {

                            final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
                            // Use bounce interpolator with amplitude 0.2 and frequency 20
                            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.3, 20);
                            myAnim.setInterpolator(interpolator);
                            Baudio.startAnimation(myAnim);

                            stopRecording();
                        }
                    }.start();

                }
               /* else  if(motionEvent.getAction()==MotionEvent.ACTION_HOVER_MOVE)
                {
baba = 0;
                }
*/
                return false;
            }
        });

        mdata = FirebaseDatabase.getInstance().getReference();
        mdata.keepSynced(true);

        Battach = (Button) findViewById(R.id.button9);
        Battach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Battach.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Battach.setAlpha(1.0F);
                    }
                }.start();

                flagito = 0;

                File newDirectory = new File(Environment.getExternalStorageDirectory()+"/test/");
                FilePathUri = Uri.fromFile(newDirectory);

                Intent pickImageIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);//, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickImageIntent.setType("image/*");

             //   pickImageIntent.putExtra("crop", "true");
              //  pickImageIntent.putExtra("outputX", 800);
              //  pickImageIntent.putExtra("outputY", 800);
              //  pickImageIntent.putExtra("aspectX", 1);
              //  pickImageIntent.putExtra("aspectY", 1);
             //   pickImageIntent.putExtra("scale", true);
             //   pickImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, FilePathUri);
                startActivityForResult(pickImageIntent, 7);

            }
        });

        Tstatus = (TextView)findViewById(R.id.textView46);
        Tstatus.setSelected(true);

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        final String user_id = getIntent().getStringExtra("user_id");

        uuser_id = "x";

        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor11 = settings11.edit();

        String select11 = settings11.getString("select", "1");  /// 0 is default if variable not found

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

        x1="Beirut123";
        x2="Beirut123";

        mdata.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name1=dataSnapshot.child(uuser_id).child("name").getValue(String.class).toString();
                name2=dataSnapshot.child(user_id).child("name").getValue(String.class).toString();
                checkx1();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


/*
        DatabaseReference mPublicProfileDatabase = FirebaseDatabase.getInstance().getReference().child("Profile_friends");
         mPublicProfileDatabase.keepSynced(true);

        mPublicProfileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id))
                { mPublicProfileDatabase.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String x = dataSnapshot.child("status").getValue().toString();

                        while (x.length()<70)
                        {
                            x+=  "            "+x;
                        }
                        // Tname.setText(dataSnapshot.child("name").getValue().toString());
                        Tstatus.setText(x);

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
*/
        Tnote1 = (TextView)findViewById(R.id.textView18);
        Tnote2 = (TextView)findViewById(R.id.textView13);
        Tnote3 = (TextView)findViewById(R.id.textView12);
        BnoteSave = (Button) findViewById(R.id.button12);
        Enote = (EditText) findViewById(R.id.editText5);


         Bcoc = (Button) findViewById(R.id.button112);
        Bcocback = (Button) findViewById(R.id.button85);
         BcocSave = (Button) findViewById(R.id.button122);
     Econc = (EditText) findViewById(R.id.editText5);




        mAutoReplyDatabase = FirebaseDatabase.getInstance().getReference().child("Auto_reply");
        mAutoReplyDatabase.keepSynced(true);
        mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chats");
        mChatDatabase.keepSynced(true);

        mfriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mfriendsDatabase.keepSynced(true);

        mNotificationDatabase= FirebaseDatabase.getInstance().getReference().child("Notifications");

        mVideoCallRoomDatabase= FirebaseDatabase.getInstance().getReference().child("Video_Call_Rooms");

        mStateDatabase_with_you = FirebaseDatabase.getInstance().getReference().child("State_with_you");
        mStateDatabase_in_app = FirebaseDatabase.getInstance().getReference().child("State_in_app");

        mHistoryDatabase = FirebaseDatabase.getInstance().getReference().child("History");
        mHistoryDatabase.keepSynced(true);

        mNoteDatabase = FirebaseDatabase.getInstance().getReference().child("Notes");
        mNoteDatabase.keepSynced(true);



        mdata.child("Typing_State").child(user_id).child(uuser_id).child("state").setValue("not typing").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        Button Bcam = (Button)findViewById(R.id.button72);
        Bcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA},6);
                }
                if (ActivityCompat.checkSelfPermission(activity_chats.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity_chats.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }

                if (ActivityCompat.checkSelfPermission(activity_chats.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity_chats.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
                }
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECORD_AUDIO},5);
                }

                if (ActivityCompat.checkSelfPermission(activity_chats.this, Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity_chats.this, new String[]{Manifest.permission.SET_ALARM}, 7);
                }
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WAKE_LOCK},8);
                }
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},9);
                }

                flagito = -1;

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

               /*
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 2);
                */

                try {
                    // use standard intent to capture an image
                    Intent captureIntent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    // we will handle the returned data in onActivityResult
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA},6);
                    }
                    startActivityForResult(captureIntent, 1);


                } catch (ActivityNotFoundException anfe) {
                    Toast toast = Toast.makeText(context, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        mDelayedDatabase  = FirebaseDatabase.getInstance().getReference();
        mDelayedDatabase.keepSynced(true);

        mDelayedDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Delayed_msg_From_Me"))
                {
                    mDelayedDatabase.child("Delayed_msg_From_Me").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(uuser_id))
                            {
                                mDelayedDatabase.child("Delayed_msg_From_Me").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                        for (DataSnapshot child : children) {
                                            if(child.child("list_keys").getValue(String.class).contains(user_id))
                                            {

                                                 tt= (TextView)findViewById(R.id.textView74);
                                                ttb= (Button)findViewById(R.id.button70);

                                                ttb.setVisibility(View.VISIBLE);

                                                tt.setVisibility(View.VISIBLE);

                                                yearf = Integer.parseInt(child.child("date_from_y").getValue(String.class));
                                                monthf = Integer.parseInt(child.child("date_from_m").getValue(String.class));
                                                dayf = Integer.parseInt(child.child("date_from_d").getValue(String.class));
                                                hourf = Integer.parseInt(child.child("time_from_h").getValue(String.class));
                                                minf = Integer.parseInt(child.child("time_from_m").getValue(String.class));


                                                CDT = new CountDownTimer(3600000,1000) {
                                                    @Override
                                                    public void onTick(long l) {


                                                        tt.setTextColor(Color.RED);
                                                        new CountDownTimer(600, 100) {
                                                            @Override
                                                            public void onTick(long l) {

                                                            }

                                                            @Override
                                                            public void onFinish() {
                                                                tt.setTextColor(Color.rgb(80, 80, 80));
                                                            }
                                                        }.start();


                                                        Calendar calendar = Calendar.getInstance();
                                                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                                                        int year = calendar.get(Calendar.YEAR);
                                                        int month = calendar.get(Calendar.MONTH) + 1;
                                                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                                        int min = calendar.get(Calendar.MINUTE);

                                                        int y = 0, m = 0, d = 0, h = 0, mi = 0;
                                                        y = Math.abs(year - yearf);
                                                        m = Math.abs(month - monthf);
                                                        d = Math.abs(day - dayf);
                                                        h = Math.abs(hour - hourf);
                                                        mi = Math.abs(min - minf);

                                                        int f = hourf * 60 + minf;
                                                        int s = hour * 60 + min;

                                                        mi = Math.abs(f - s);

                                                        d += (int) y * 365 + (int) m * 30.4;
                                                        String r = "";
                                                        r += d + " days, ";
                                                        r += ((int) (mi / 60)) + "h ";
                                                        r += ((int) (mi % 60)) + "m";

                                                        tt.setText("In "+r);

                                                        if (tt.getText().toString().equals("In 0 days, 0h 0m") && tt.getVisibility()== View.VISIBLE)
                                                        {
                                                            tt.setVisibility(View.INVISIBLE);
                                                            ttb.setVisibility(View.INVISIBLE);

                                                            SharedPreferences settings8s = PreferenceManager.getDefaultSharedPreferences(context);
                                                            String backcolora = settings8s.getString("timitto", " ");  /// 0 is default if variable not found
                                                            if(!backcolora.equals(" "+day +" "+year +" "+month +" "+hour +" "+min)) {
                                                                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                                                                SharedPreferences.Editor editor = settings.edit();
                                                                editor.putString("timitto", " " + day + " " + year + " " + month + " " + hour + " " + min);
                                                                editor.commit();

                                                                senddelayed();
                                                            }
                                                        }

                                                        if(
                                                                (yearf<year)
                                                                        || ((yearf==year)&&(monthf<month))
                                                                        || ((yearf==year)&&(monthf==month)&&(dayf<day))
                                                                        || ((yearf==year)&&(monthf==month)&&(dayf==day)&&(hourf<hour))
                                                                        || ((yearf==year)&&(monthf==month)&&(dayf==day)&&(hourf==hour)&&(minf<min))
                                                                )
                                                        {
                                                            tt.setVisibility(View.INVISIBLE);
                                                            ttb.setVisibility(View.INVISIBLE);
                                                        }

                                                    }

                                                    @Override
                                                    public void onFinish() {

                                                    }
                                                }.start();


                                                tt.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Intent startIntent = new Intent(activity_chats.this, delayedmsg.class);
                                                        startActivity(startIntent);
                                                    }
                                                });
                                                ttb.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Intent startIntent = new Intent(activity_chats.this, delayedmsg.class);
                                                        startActivity(startIntent);
                                                    }
                                                });
                                            }

                                        }
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mChatDatabase.child(uuser_id).child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                   if(child.getValue(Chats.class).getFrom().toString().equals(user_id))
                   {
                       if(child.getValue(Chats.class).getSent_received_seen().toString().equals("sent"))
                       {
                           HashMap<String, Object> resultm = new HashMap<>();
                           resultm.put("sent_received_seen","received");
                           mChatDatabase.child(uuser_id).child(user_id).child(child.getKey()).updateChildren(resultm);
                       }
                   }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
        c.add(Calendar.DATE, 0);
        Date resultdate;
        resultdate = new Date(c.getTimeInMillis());
        String s =sdf.format(resultdate);
        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
        int currentmin = rightNow.get(Calendar.MINUTE); //
        String ffp = String.valueOf(currentmin).toString();
        if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
        String hour = String.valueOf(currentHour).toString() + ":" + ffp;
        if(hour.length()<5) hour = "0"+hour;
        String DateTime = s + hour;
//ListenerForSingleValueEvent

        mfriendsDatabase.child(uuser_id).child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              String ggg =  dataSnapshot.child("count").getValue(String.class);
                ggg=ggg.substring(0,15);

                mfriendsDatabase.child(uuser_id).child(user_id).child("count").setValue(ggg+"0").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mChatDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id))
                {

                    mChatDatabase.child("user_id").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(uuser_id))
                            {

                                mChatDatabase.child(user_id).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                        for (DataSnapshot child : children) {
                                            if(child.getValue(Chats.class).getFrom().toString().equals(user_id))
                                            {
                                                if(child.getValue(Chats.class).getSent_received_seen().toString().equals("sent"))
                                                {
                                                    HashMap<String, Object> resultm = new HashMap<>();
                                                    resultm.put("sent_received_seen","received");
                                                    mChatDatabase.child(user_id).child(uuser_id).child(child.getKey()).updateChildren(resultm);
                                                }
                                            }

                                        }

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mUsersDatabase2 = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mUsersDatabase2.keepSynced(true);


        mUsersDatabase3 = FirebaseDatabase.getInstance().getReference().child("Users").child(uuser_id);
        mUsersDatabase3.keepSynced(true);

        BnoteSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg="";
                if (Enote.getText().toString().equals(""))msg="";
                else
                    msg = Enote.getText().toString();


                String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                c.add(Calendar.DATE, 0);
                Date resultdate;
                resultdate = new Date(c.getTimeInMillis());
                String s =sdf.format(resultdate);

                Calendar rightNow = Calendar.getInstance();
                int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                int currentmin = rightNow.get(Calendar.MINUTE); //

                String ffp = String.valueOf(currentmin).toString();
                if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

                String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                if(hour.length()<5) hour = "0"+hour;
                String DateTime = s + hour;


                HashMap<String, String> object2 = new HashMap<String, String>();
                object2.put("from", uuser_id);
                object2.put("note_msg", msg);
                object2.put("dateTime", DateTime);
                object2.put("sent_received_seen", "sent"); //  sent , received, seen


                mNoteDatabase.child(uuser_id).child(user_id).child("note").setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(activity_chats.this, "Conclusion saved!", Toast.LENGTH_LONG).show();

                        mNoteDatabase.child(uuser_id).child(user_id).child("note").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Enote.setText(dataSnapshot.getValue(Notes.class).getNote_msg());

                                String x = dataSnapshot.getValue(Notes.class).getDateTime();
                                String y = x.substring(0,4);
                                String m = x.substring(5,7);
                                String d = x.substring(8,10);

                                String time = x.substring(10);

                                String hour = x.substring(10,x.indexOf(':'));
                                String min = x.substring(x.indexOf(':')+1);


                                String r = "";

                                if (m.equals("01")) m = "Jan";
                                if (m.equals("02")) m = "Feb";
                                if (m.equals("03")) m = "Mar";
                                if (m.equals("04")) m = "Apr";
                                if (m.equals("05")) m = "May";
                                if (m.equals("06")) m = "June";
                                if (m.equals("07")) m = "July";
                                if (m.equals("08")) m = "Aug";
                                if (m.equals("09")) m = "Sep";
                                if (m.equals("10")) m = "Oct";
                                if (m.equals("11")) m = "Nov";
                                if (m.equals("12")) m = "Dec";

                                r = m + " " + d + ", " + y + "\nat " + time;

                                Tnote1.setText("Modified on \n" +r);

                                if(dataSnapshot.getValue(Notes.class).getFrom().toString().equals(user_id))
                                {

                                    mUsersDatabase2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String display_name = dataSnapshot.child("name").getValue().toString();
                                            Tnote2.setText("by " +display_name);


                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });


                                }
                                else
                                {
                                    mUsersDatabase3.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String display_name = dataSnapshot.child("name").getValue().toString();
                                            Tnote2.setText("by " +display_name);
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });

                                }

                                if(dataSnapshot.getValue(Notes.class).getSent_received_seen().equals("sent"))
                                {
                                    Tnote3.setText("Not seen");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });                    }
                });
                mNoteDatabase.child(user_id).child(uuser_id).child("note").setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mNoteDatabase.child(uuser_id).child(user_id).child("note").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Enote.setText(dataSnapshot.getValue(Notes.class).getNote_msg());

                                String x = dataSnapshot.getValue(Notes.class).getDateTime();
                                String y = x.substring(0,4);
                                String m = x.substring(5,7);
                                String d = x.substring(8,10);

                                String time = x.substring(10);

                                String hour = x.substring(10,x.indexOf(':'));
                                String min = x.substring(x.indexOf(':')+1);


                                String r = "";

                                if (m.equals("01")) m = "Jan";
                                if (m.equals("02")) m = "Feb";
                                if (m.equals("03")) m = "Mar";
                                if (m.equals("04")) m = "Apr";
                                if (m.equals("05")) m = "May";
                                if (m.equals("06")) m = "June";
                                if (m.equals("07")) m = "July";
                                if (m.equals("08")) m = "Aug";
                                if (m.equals("09")) m = "Sep";
                                if (m.equals("10")) m = "Oct";
                                if (m.equals("11")) m = "Nov";
                                if (m.equals("12")) m = "Dec";

                                r = m + " " + d + ", " + y + "\nat " + time;

                                Tnote1.setText("Modified on \n" +r);

                                if(dataSnapshot.getValue(Notes.class).getFrom().toString().equals(user_id))
                                {

                                    mUsersDatabase2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String display_name = dataSnapshot.child("name").getValue().toString();
                                            Tnote2.setText("by " +display_name);


                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });


                                }
                                else
                                {
                                    mUsersDatabase3.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            String display_name = dataSnapshot.child("name").getValue().toString();
                                            Tnote2.setText("by " +display_name);
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });

                                }

                                if(dataSnapshot.getValue(Notes.class).getSent_received_seen().equals("sent"))
                                {
                                    Tnote3.setText("Not seen");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });                    }
                });


            }
        });






        mNoteDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(uuser_id)) {

                    mNoteDatabase.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChild(user_id))
                            {
                                mNoteDatabase.child(uuser_id).child(user_id).child("note").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        Enote.setText(dataSnapshot.getValue(Notes.class).getNote_msg());

                                        String x = dataSnapshot.getValue(Notes.class).getDateTime();
                                        String y = x.substring(0,4);
                                        String m = x.substring(5,7);
                                        String d = x.substring(8,10);

                                        String time = x.substring(10);

                                        String hour = x.substring(10,x.indexOf(':'));
                                        String min = x.substring(x.indexOf(':')+1);


                                        String r = "";

                                        if (m.equals("01")) m = "Jan";
                                        if (m.equals("02")) m = "Feb";
                                        if (m.equals("03")) m = "Mar";
                                        if (m.equals("04")) m = "Apr";
                                        if (m.equals("05")) m = "May";
                                        if (m.equals("06")) m = "June";
                                        if (m.equals("07")) m = "July";
                                        if (m.equals("08")) m = "Aug";
                                        if (m.equals("09")) m = "Sep";
                                        if (m.equals("10")) m = "Oct";
                                        if (m.equals("11")) m = "Nov";
                                        if (m.equals("12")) m = "Dec";

                                        r = m + " " + d + ", " + y + "\nat " + time;

                                        Tnote1.setText("Modified on \n" +r);

                                        if(dataSnapshot.getValue(Notes.class).getFrom().toString().equals(user_id))
                                        {

                                            mUsersDatabase2.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String display_name = dataSnapshot.child("name").getValue().toString();
                                                    Tnote2.setText("by " +display_name);


                                                }
                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                }
                                            });


                                        }
                                        else
                                        {
                                            mUsersDatabase3.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String display_name = dataSnapshot.child("name").getValue().toString();
                                                    Tnote2.setText("by " +display_name);
                                                }
                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                }
                                            });

                                        }

                                        if(dataSnapshot.getValue(Notes.class).getSent_received_seen().equals("sent"))
                                        {
                                            Tnote3.setText("Not seen");
                                        }
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



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        mChatList = (RecyclerView)findViewById(R.id.recyclerView2);
        mChatList.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        mChatList.setLayoutManager(mLinearLayoutManager);


        SharedPreferences settings8 = PreferenceManager.getDefaultSharedPreferences(context);
        String backcolor = settings8.getString(user_id+"_color", "c4");  /// 0 is default if variable not found
        if(backcolor.equals("c1"))
        {mChatList.setBackgroundColor(Color.rgb(243,45,45));}
        if(backcolor.equals("c2"))
        {mChatList.setBackgroundColor(Color.rgb(221,255,221));}
        if(backcolor.equals("c3"))
        {mChatList.setBackgroundColor(Color.rgb(67,67,222));}
        if(backcolor.equals("c4"))
        {mChatList.setBackgroundColor(Color.rgb(223,223,223));}
        if(backcolor.equals("c5"))
        {mChatList.setBackgroundColor(Color.rgb(240,154,80));}

//restore
///////////////////////////////////////////////////////////////////////////////////////////////////////
        mChatList.setHasFixedSize(true);
        mChatList.setItemViewCacheSize(20);
        mChatList.setDrawingCacheEnabled(true);
        mChatList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//////////////////////////////////////////////////////////////////////////////////////////////////////


        mChatList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               // super.onScrollStateChanged(recyclerView, newState);
            }
              @Override
              public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
             }
        });

        mNoteDatabase = FirebaseDatabase.getInstance().getReference().child("Notes");
        mNoteDatabase.keepSynced(true);

        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Chats");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        slideView = (View) findViewById(R.id.cc3);
        slideView2 = (View) findViewById(R.id.cccc);
       // slideView3 = (View) findViewById(R.id.linearLayout);

        slideViewD = (View) findViewById(R.id.cc32);
        slideView2D = (View) findViewById(R.id.cccc2);
       // slideView3D = (View) findViewById(R.id.linearLayout2);


        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

                //For 3G check
                boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                //For WiFi Check
                boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();


                if (!is3g && !isWifi)
                {
                    //Toast.makeText(getApplicationContext(),"Network Connection is OFF", Toast.LENGTH_LONG).show();

                }
                else
                {
                    // Toast.makeText(getApplicationContext(),"Network Connection is ON", Toast.LENGTH_LONG).show();

                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                       if(isNetworkAvailable())
                       {
                           mStateDatabase_with_you.addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(DataSnapshot dataSnapshot) {

                                   TextView Tstate = (TextView)findViewById(R.id.textView44);

                                   String start = Tstate.getText().toString();

                                   if(dataSnapshot.hasChild(user_id))
                                   {


                                       statewithyou = dataSnapshot.child(user_id).getValue(String.class);

                                       if(!stateinapp.equals("Online_in_app"))
                                       {
                                      //     Tstate.setBackgroundResource(R.drawable.roundbuttongray);
                                           Tstate.setTextColor(Color.WHITE);
                                           Tstate.setText("Offline");
                                           ImageView ivggd = (ImageView)findViewById(R.id.imageView26);
                                           ivggd.setImageResource(R.drawable.ic_fiber_gray);

                                           mdata.child("Typing_State").addListenerForSingleValueEvent(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(DataSnapshot dataSnapshot) {
                                                   if(dataSnapshot.hasChild(uuser_id))
                                                   {
                                                       mdata.child("Typing_State").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                           @Override
                                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                                               if(dataSnapshot.hasChild(user_id))
                                                               {
                                                                   mdata.child("Typing_State").child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                                                       @Override
                                                                       public void onDataChange(DataSnapshot dataSnapshot) {

                                                                           String x = dataSnapshot.child("state").getValue(String.class);

                                                                           TextView Tstate = (TextView)findViewById(R.id.textView44);
                                                                           String start = Tstate.getText().toString();

                                                                           if(x.equals("typing") && start.equals("Online with me"))
                                                                           {
                                                                               TextView t = (TextView)findViewById(R.id.textView75);
                                                                               t.setVisibility(View.VISIBLE);
                                                                           }
                                                                           else
                                                                           {
                                                                               TextView t = (TextView)findViewById(R.id.textView75);
                                                                               t.setVisibility(View.INVISIBLE);
                                                                           }
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
                                               }

                                               @Override
                                               public void onCancelled(DatabaseError databaseError) {

                                               }
                                           });

                                       }
                                       else
                                       {
                                           if(uuser_id.equals(statewithyou))
                                           {
                                               Tstate.setText("Online with me");
                                             //  Tstate.setBackgroundResource(R.drawable.roundbutton);
                                               Tstate.setTextColor(Color.WHITE);
                                               ImageView ivggd = (ImageView)findViewById(R.id.imageView26);
                                               ivggd.setImageResource(R.drawable.ic_fiber_green);

                                               mdata.child("Typing_State").addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                       if(dataSnapshot.hasChild(uuser_id))
                                                       {
                                                           mdata.child("Typing_State").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                               @Override
                                                               public void onDataChange(DataSnapshot dataSnapshot) {
                                                                   if(dataSnapshot.hasChild(user_id))
                                                                   {
                                                                       mdata.child("Typing_State").child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                                                           @Override
                                                                           public void onDataChange(DataSnapshot dataSnapshot) {

                                                                               String x = dataSnapshot.child("state").getValue(String.class);

                                                                               TextView Tstate = (TextView)findViewById(R.id.textView44);
                                                                               String start = Tstate.getText().toString();

                                                                               if(x.equals("typing") && start.equals("Online with me"))
                                                                               {
                                                                                   TextView t = (TextView)findViewById(R.id.textView75);
                                                                                   t.setVisibility(View.VISIBLE);
                                                                               }
                                                                               else
                                                                               {
                                                                                   TextView t = (TextView)findViewById(R.id.textView75);
                                                                                   t.setVisibility(View.INVISIBLE);
                                                                               }
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
                                                   }

                                                   @Override
                                                   public void onCancelled(DatabaseError databaseError) {

                                                   }
                                               });

                                           }
                                           else
                                           {
                                             //  Tstate.setBackgroundResource(R.drawable.roundbuttondar);
                                               Tstate.setTextColor(Color.WHITE);
                                               Tstate.setText("Online in app");
                                               ImageView ivggd = (ImageView)findViewById(R.id.imageView26);
                                               ivggd.setImageResource(R.drawable.ic_fiber_orange);

                                               mdata.child("Typing_State").addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                       if(dataSnapshot.hasChild(uuser_id))
                                                       {
                                                           mdata.child("Typing_State").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                               @Override
                                                               public void onDataChange(DataSnapshot dataSnapshot) {
                                                                   if(dataSnapshot.hasChild(user_id))
                                                                   {
                                                                       mdata.child("Typing_State").child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                                                           @Override
                                                                           public void onDataChange(DataSnapshot dataSnapshot) {

                                                                               String x = dataSnapshot.child("state").getValue(String.class);

                                                                               TextView Tstate = (TextView)findViewById(R.id.textView44);
                                                                               String start = Tstate.getText().toString();

                                                                               if(x.equals("typing") && start.equals("Online with me"))
                                                                               {
                                                                                   TextView t = (TextView)findViewById(R.id.textView75);
                                                                                   t.setVisibility(View.VISIBLE);
                                                                               }
                                                                               else
                                                                               {
                                                                                   TextView t = (TextView)findViewById(R.id.textView75);
                                                                                   t.setVisibility(View.INVISIBLE);
                                                                               }
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
                                                   }

                                                   @Override
                                                   public void onCancelled(DatabaseError databaseError) {

                                                   }
                                               });

                                           }
                                       }
                                   }
                                   else
                                   {
                                     //  Tstate.setBackgroundResource(R.drawable.roundbuttongray);
                                       Tstate.setTextColor(Color.WHITE);
                                       Tstate.setText("Offline");
                                       ImageView ivggd = (ImageView)findViewById(R.id.imageView26);
                                       ivggd.setImageResource(R.drawable.ic_fiber_gray);

                                       mdata.child("Typing_State").addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                               if(dataSnapshot.hasChild(uuser_id))
                                               {
                                                   mdata.child("Typing_State").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                       @Override
                                                       public void onDataChange(DataSnapshot dataSnapshot) {
                                                           if(dataSnapshot.hasChild(user_id))
                                                           {
                                                               mdata.child("Typing_State").child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                                                   @Override
                                                                   public void onDataChange(DataSnapshot dataSnapshot) {

                                                                       String x = dataSnapshot.child("state").getValue(String.class);

                                                                       TextView Tstate = (TextView)findViewById(R.id.textView44);
                                                                       String start = Tstate.getText().toString();

                                                                       if(x.equals("typing") && start.equals("Online with me"))
                                                                       {
                                                                           TextView t = (TextView)findViewById(R.id.textView75);
                                                                           t.setVisibility(View.VISIBLE);
                                                                       }
                                                                       else
                                                                       {
                                                                           TextView t = (TextView)findViewById(R.id.textView75);
                                                                           t.setVisibility(View.INVISIBLE);
                                                                       }
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
                                           }

                                           @Override
                                           public void onCancelled(DatabaseError databaseError) {

                                           }
                                       });


                                   }

                                   if(!start.equals(Tstate.getText().toString())) {
                                       final Animation myAnim1 = AnimationUtils.loadAnimation(activity_chats.this, R.anim.bouncec);
                                       MyBounceInterpolator interpolator1 = new MyBounceInterpolator(0.4, 1);  // with amplitude 0.2 and frequency 20
                                       myAnim1.setInterpolator(interpolator1);
                                       Tstate.startAnimation(myAnim1);
                                   }

                               }

                               @Override
                               public void onCancelled(DatabaseError databaseError) {

                               }
                           });

                       }
                       else
                       {
                           TextView t = (TextView)findViewById(R.id.textView75);
                           t.setVisibility(View.INVISIBLE);

                           TextView Tstate = (TextView)findViewById(R.id.textView44);

                        //   Tstate.setBackgroundResource(R.drawable.roundbuttongray);
                           Tstate.setTextColor(Color.WHITE);
                           Tstate.setText("Offline");
                           ImageView ivggd = (ImageView)findViewById(R.id.imageView26);
                           ivggd.setImageResource(R.drawable.ic_fiber_gray);
                       }

                    }
                });

            }
        }, 0, 5000);

        Bnote = (Button) findViewById(R.id.button11);

        bnoteback = (Button) findViewById(R.id.button86);

        Bsend = (Button) findViewById(R.id.button7);
        Emsg = (EditText) findViewById(R.id.editText4);

        SharedPreferences settings2s = PreferenceManager.getDefaultSharedPreferences(context);
        String retss = settings2s.getString(user_id+uuser_id, "");  /// 0 is default if variable not found

        Emsg.setText(retss);

        Emsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               // Emsg.setText(Emsg.getText().toString() + effect);

                SharedPreferences settingsssa = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editorssa = settingsssa.edit();
                editorssa.putString(user_id+uuser_id, Emsg.getText().toString());
                editorssa.commit();

                if(Emsg.getText().toString().equals(""))
                {
                    mdata.child("Typing_State").child(user_id).child(uuser_id).child("state").setValue("not typing").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                }
                else
                {



                    mdata.child("Typing_State").child(user_id).child(uuser_id).child("state").setValue("typing").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
               // String ee = Emsg.getText().toString();
              //  Emsg.setText((ee+effect));
            }
        });

        Bsettings = (Button) findViewById(R.id.button13);
        Bsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent (activity_chats.this, chatsettings.class);
                startIntent.putExtra("user_id", user_id);
                startActivity(startIntent);
            }
        });

        flag = 0;
        flag2 = 0;

        Bcoc = (Button) findViewById(R.id.button112);
        BcocSave = (Button) findViewById(R.id.button122);
        Econc = (EditText) findViewById(R.id.editText52);

        /////////////////////////////////////////////
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String ret = settings.getString("NotePrivate"+user_id, "");  /// 0 is default if variable not found
        Econc.setText(ret);

/////////////////////////////////////////////////////

        BcocSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = "";
                if(Econc.getText().toString().equals(""))
                    msg = "";
                else
                    msg = Econc.getText().toString();

                /////////////////////////////////////////////
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                /////////////save////////////////
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("NotePrivate"+user_id, msg);
                editor.commit();

                Toast.makeText(activity_chats.this, "Note saved!", Toast.LENGTH_LONG).show();

                //////////////get//////////////////
/////////////////////////////////////////////////////
            }
        });

        Bcocback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag2=0;
                Bcoc.setBackgroundResource(R.drawable.ic_note_add_black_24dp);
                slideView2D.animate().translationX(0);
                //  slideView3D.animate().translationX(0);
                slideViewD.animate().translationX(0);
                Bcoc.setVisibility(View.VISIBLE);
            }
        });

        Bcoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bnote.setVisibility(View.VISIBLE);

                if(!(flag==0))
                {
                    flag=0;
                    Bnote.setBackgroundResource(R.drawable.ic_comment_black_24dp);
                    slideView2.animate().translationX(0);
                  //  slideView3.animate().translationX(0);
                    slideView.animate().translationX(0);
                    Bcoc.setVisibility(View.VISIBLE);

                }

                if(flag2==0) {
                    flag2=1;
                    Bcoc.setBackgroundResource(R.drawable.ic_arrow_back_black_24dpg);
                    slideView2D.animate().translationX( slideView.getWidth() -1);
                 //   slideView3D.animate().translationX( slideView.getWidth()-1 );
                    slideViewD.animate().translationX( slideView.getWidth() -1);
                    Bcoc.setVisibility(View.INVISIBLE);

                }
                else
                { flag2=0;
                    Bcoc.setBackgroundResource(R.drawable.ic_note_add_black_24dp);
                    slideView2D.animate().translationX(0);
                  //  slideView3D.animate().translationX(0);
                    slideViewD.animate().translationX(0);
                    Bcoc.setVisibility(View.VISIBLE);

                }


                }
        });

        bnoteback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=0;
                Bnote.setBackgroundResource(R.drawable.ic_comment_black_24dp);
                slideView2.animate().translationX(0);
                // slideView3.animate().translationX(0);
                slideView.animate().translationX(0);

                Bnote.setVisibility(View.VISIBLE);
            }
        });

        Bnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bcoc.setVisibility(View.VISIBLE);

                if(!(flag2==0)) {
                    flag2=0;
                    Bcoc.setBackgroundResource(R.drawable.ic_note_add_black_24dp);
                    slideView2D.animate().translationX(0);
                   // slideView3D.animate().translationX(0);
                    slideViewD.animate().translationX(0);


                }
                if(flag==0) {
                    flag=1;
                    Bnote.setBackgroundResource(R.drawable.ic_arrow_forward_black_24dpg);
                    slideView2.animate().translationX(-1 * slideView.getWidth() - 1);
                  //  slideView3.animate().translationX(-1 * slideView.getWidth() - 1);
                    slideView.animate().translationX(-1 * slideView.getWidth() - 1);

                    Bnote.setVisibility(View.INVISIBLE);

                    mNoteDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChild(uuser_id)) {

                                mNoteDatabase.child(uuser_id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.hasChild(user_id))
                                        {

                                            mNoteDatabase.child(uuser_id).child(user_id).child("note").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {


                                                    if(dataSnapshot.getValue(Notes.class).getFrom().toString().equals(uuser_id))
                                                    {

                                                        mNoteDatabase.child(uuser_id).child(user_id).child("note").child("sent_received_seen").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Tnote3.setText("received");
                                                                // mprofilesendRequest.setEnabled(true);
                                                                //  mCurrent_state = "req_sent";
                                                                // mprofilesendRequest.setText("Cancel friend request");
                                                                //  mDecline.setVisibility(View.INVISIBLE);
                                                                //  mDecline.setEnabled(false);
                                                            }
                                                        });
                                                        mNoteDatabase.child(user_id).child(uuser_id).child("note").child("sent_received_seen").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Tnote3.setText("received");
                                                                // mprofilesendRequest.setEnabled(true);
                                                                //  mCurrent_state = "req_sent";
                                                                // mprofilesendRequest.setText("Cancel friend request");
                                                                //  mDecline.setVisibility(View.INVISIBLE);
                                                                //  mDecline.setEnabled(false);
                                                            }
                                                        });


                                                    }

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                            mNoteDatabase.child(user_id).child(uuser_id).child("note").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {


                                                    if(dataSnapshot.getValue(Notes.class).getFrom().toString().equals(uuser_id))
                                                    {

                                                        mNoteDatabase.child(uuser_id).child(user_id).child("note").child("sent_received_seen").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Tnote3.setText("received");
                                                                // mprofilesendRequest.setEnabled(true);
                                                                //  mCurrent_state = "req_sent";
                                                                // mprofilesendRequest.setText("Cancel friend request");
                                                                //  mDecline.setVisibility(View.INVISIBLE);
                                                                //  mDecline.setEnabled(false);
                                                            }
                                                        });
                                                        mNoteDatabase.child(user_id).child(uuser_id).child("note").child("sent_received_seen").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Tnote3.setText("received");
                                                                // mprofilesendRequest.setEnabled(true);
                                                                //  mCurrent_state = "req_sent";
                                                                // mprofilesendRequest.setText("Cancel friend request");
                                                                //  mDecline.setVisibility(View.INVISIBLE);
                                                                //  mDecline.setEnabled(false);
                                                            }
                                                        });


                                                    }

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



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });








                }
                else
                { flag=0;
                    Bnote.setBackgroundResource(R.drawable.ic_comment_black_24dp);
                    slideView2.animate().translationX(0);
                   // slideView3.animate().translationX(0);
                    slideView.animate().translationX(0);

                    Bnote.setVisibility(View.VISIBLE);

                }
            }
        });




        final String user_image = getIntent().getStringExtra("user_image");

        mUsersDatabaseo = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabaseo.keepSynced(true);



        mUsersDatabaseo.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              Name_uuser_id =  dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mUsersDatabaseo.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Name_user_id =  dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mUsersDatabase.keepSynced(true);

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String display_name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                getSupportActionBar().setTitle(display_name);

                TextView Tname = (TextView)findViewById(R.id.textView30);
                Tname.setText(display_name);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Bcallapp2 = (Button)findViewById(R.id.button32);

        Bcallapp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Under testing. Will be available soon.", Toast.LENGTH_SHORT).show();

                /*
                SharedPreferences settingsssa = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editorssa = settingsssa.edit();
                editorssa.putString("callvideo", "call");
                editorssa.commit();

                Bcallapp2.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Bcallapp2.setAlpha(1.0F);

                        mUsersDatabaseo.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String name = dataSnapshot.child("name").getValue().toString();


                                char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                                StringBuilder sb = new StringBuilder(20);
                                Random random = new Random();
                                for (int i = 0; i < 15; i++) {
                                    char c2 = chars[random.nextInt(chars.length)];
                                    sb.append(c2);
                                }
                                outputaa = sb.toString();
                                outputaa = outputaa + "calls";

                                HashMap<String, String> object1 = new HashMap<String, String>();
                                object1.put("room", outputaa);

                                mVideoCallRoomDatabase.child(uuser_id).child(user_id).setValue(object1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mVideoCallRoomDatabase.child(user_id).child(uuser_id).setValue(object1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                HashMap<String, String> object2 = new HashMap<String, String>();
                                                object2.put("msg_or_url", "Video call from "+name + ";;;;;;;;;;"+uuser_id+";;;;;;;;;;"+outputaa);

                                                if(!namito.equals(""))
                                                    mNotificationDatabase.child(uuser_id).child(user_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            TextView Tname = (TextView)findViewById(R.id.textView30);

                                                            //   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gruveo.com/"+outputaa));
                                                            //  startActivity(browserIntent);

                                                            Intent startIntent = new Intent(activity_chats.this, AppRTCMainActivity
                                                                    .class);
                                                            final String user_id = getIntent().getStringExtra("user_id");
                                                            final String user_image = getIntent().getStringExtra("user_image");
                                                            final String user_name = Tname.getText().toString();


                                                            startIntent.putExtra("user_id", user_id);
                                                            startIntent.putExtra("user_image", user_image);
                                                            startIntent.putExtra("user_name", user_name);
                                                            startIntent.putExtra("roomfire", outputaa);
                                                            startIntent.putExtra("messa", "x");

                                                            startActivity(startIntent);

                                                             AudioManager m_amAudioManager;
                                                            m_amAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                                                            m_amAudioManager.setMode(AudioManager.MODE_IN_CALL);
                                                            m_amAudioManager.setSpeakerphoneOn(false);
                                                            //    Intent startIntent = new Intent(activity_chats.this, WalkieTalkieActivity.class);
                                                            //   startActivity(startIntent);



                                                        }
                                                    });
                                                else
                                                    Toast.makeText(context, "The user account is deactivated", Toast.LENGTH_SHORT).show();



                                            }
                                        });
                                    }
                                });



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }.start();


*/


            }
        });

        Bcallapp = (Button)findViewById(R.id.button33);

        Bcallapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(context, "Under testing. Will be available soon.", Toast.LENGTH_SHORT).show();

                /*
                SharedPreferences settingsssa = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editorssa = settingsssa.edit();
                editorssa.putString("callvideo", "video");
                editorssa.commit();

                Bcallapp.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Bcallapp.setAlpha(1.0F);

                        mUsersDatabaseo.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String name = dataSnapshot.child("name").getValue().toString();


                                char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                                StringBuilder sb = new StringBuilder(20);
                                Random random = new Random();
                                for (int i = 0; i < 15; i++) {
                                    char c2 = chars[random.nextInt(chars.length)];
                                    sb.append(c2);
                                }
                                outputaa = sb.toString();
                                outputaa = outputaa + "video";

                                HashMap<String, String> object1 = new HashMap<String, String>();
                                object1.put("room", outputaa);

                                mVideoCallRoomDatabase.child(uuser_id).child(user_id).setValue(object1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mVideoCallRoomDatabase.child(user_id).child(uuser_id).setValue(object1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                HashMap<String, String> object2 = new HashMap<String, String>();
                                                object2.put("msg_or_url", "Video call from "+name + ";;;;;;;;;;"+uuser_id+";;;;;;;;;;"+outputaa);

                                                if(!namito.equals(""))
                                                    mNotificationDatabase.child(uuser_id).child(user_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            TextView Tname = (TextView)findViewById(R.id.textView30);

                                                            //   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gruveo.com/"+outputaa));
                                                            //  startActivity(browserIntent);

                                                            Intent startIntent = new Intent(activity_chats.this, AppRTCMainActivity
                                                                    .class);
                                                            final String user_id = getIntent().getStringExtra("user_id");
                                                            final String user_image = getIntent().getStringExtra("user_image");
                                                            final String user_name = Tname.getText().toString();


                                                            startIntent.putExtra("user_id", user_id);
                                                            startIntent.putExtra("user_image", user_image);
                                                            startIntent.putExtra("user_name", user_name);
                                                            startIntent.putExtra("roomfire", outputaa);
                                                            startIntent.putExtra("messa", "x");

                                                            startActivity(startIntent);


                                                            //    Intent startIntent = new Intent(activity_chats.this, WalkieTalkieActivity.class);
                                                            //   startActivity(startIntent);



                                                        }
                                                    });
                                                else
                                                    Toast.makeText(context, "The user account is deactivated", Toast.LENGTH_SHORT).show();



                                            }
                                        });
                                    }
                                });



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }.start();



*/

            }
        });

        Button Bbold = (Button)findViewById(R.id.button35);
        Button Bitalic = (Button)findViewById(R.id.button36);
        Button Bunder = (Button)findViewById(R.id.button37);
        Button Bcblue = (Button)findViewById(R.id.button38);
        Button Bcred = (Button)findViewById(R.id.button39);
        Button bcwhite = (Button)findViewById(R.id.button40);
        Button bcblack = (Button)findViewById(R.id.button41);
         bcgray = (Button)findViewById(R.id.button42);
        Button Bcgreen = (Button)findViewById(R.id.button43);

        Button Bsup = (Button)findViewById(R.id.button87);
        Button Bsub = (Button)findViewById(R.id.button88);

        Bsup.setText(Html.fromHtml("x<sup>SUP"));
        Bsub.setText(Html.fromHtml("x<sub>SUB"));

        Bsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatText_sup();
            }
        });
        Bsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatText_sub();
            }
        });


        Bbold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                effect = "<font color=\"gray\"><sup>Bold</sup></font><b>";
                String ee = Emsg.getText().toString();
                Emsg.setText(Html.fromHtml(ee + effect));
                EmsgMsg = ee + effect;
*/
                formatText_bold();
              /*
               if(boldd==0) {
                   wow = "<b>";
                   Emsg.setText(Html.fromHtml(EmsgMsg));
                   boldd=1;
               }
                else
               {
                   wow = "</b>";
                   Emsg.setText(Html.fromHtml(EmsgMsg));
                   boldd = 0;
               }
*/
            }
        });
        Bitalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatText_italic();
            }
        });
        Bunder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatText_under();
            }
        });
        Bcblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatText_cblue();
            }
        });
        Bcred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatText_cred();
            }
        });
        bcwhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatText_cwhite();
            }
        });
        bcblack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatText_cblack();
            }
        });
        bcgray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(activity_chats.this);
                dialog.setContentView(R.layout.msglayout_color);
                dialog.setTitle("Please select :");

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

              //  Button rd1 = (Button) dialog.findViewById(R.id.button15);
              //  Button rd2 = (Button) dialog.findViewById(R.id.button10);

                dialog.show();

                Button rd1 = (Button) dialog.findViewById(R.id.button1663);
                rd1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 0;
                        color_g = 0;
                        color_b = 0;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd2 = (Button) dialog.findViewById(R.id.button623);
                rd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 0;
                        color_g = 0;
                        color_b = 255;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd3 = (Button) dialog.findViewById(R.id.button653);
                rd3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 0;
                        color_g = 128;
                        color_b = 255;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd4 = (Button) dialog.findViewById(R.id.button643);
                rd4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 128;
                        color_g = 128;
                        color_b = 255;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd5 = (Button) dialog.findViewById(R.id.button633);
                rd5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 0;
                        color_g = 255;
                        color_b = 255;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd6 = (Button) dialog.findViewById(R.id.button1662);
                rd6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 128;
                        color_g = 128;
                        color_b = 64;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd7 = (Button) dialog.findViewById(R.id.button622);
                rd7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 0;
                        color_g = 128;
                        color_b = 0;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd8 = (Button) dialog.findViewById(R.id.button652);
                rd8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 64;
                        color_g = 128;
                        color_b = 128;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd9 = (Button) dialog.findViewById(R.id.button642);
                rd9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 0;
                        color_g = 255;
                        color_b = 0;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd0 = (Button) dialog.findViewById(R.id.button632);
                rd0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 128;
                        color_g = 255;
                        color_b = 128;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd10 = (Button) dialog.findViewById(R.id.button1661);
                rd10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 128;
                        color_g = 0;
                        color_b = 128;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd21 = (Button) dialog.findViewById(R.id.button621);
                rd21.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 255;
                        color_g = 0;
                        color_b = 0;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd12 = (Button) dialog.findViewById(R.id.button651);
                rd12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 255;
                        color_g = 0;
                        color_b = 128;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd23 = (Button) dialog.findViewById(R.id.button641);
                rd23.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 255;
                        color_g = 0;
                        color_b = 255;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd14 = (Button) dialog.findViewById(R.id.button631);
                rd14.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 255;
                        color_g = 128;
                        color_b = 0;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd25 = (Button) dialog.findViewById(R.id.button166);
                rd25.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 128;
                        color_g = 0;
                        color_b = 0;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd16 = (Button) dialog.findViewById(R.id.button62);
                rd16.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 128;
                        color_g = 64;
                        color_b = 0;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd27 = (Button) dialog.findViewById(R.id.button65);
                rd27.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 200;
                        color_g = 200;
                        color_b = 200;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd18 = (Button) dialog.findViewById(R.id.button64);
                rd18.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 255;
                        color_g = 255;
                        color_b = 255;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });
                Button rd29 = (Button) dialog.findViewById(R.id.button63);
                rd29.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        color_r = 240;
                        color_g = 240;
                        color_b = 0;
                        formatText_cgray();
                        dialog.dismiss();
                    }
                });


                //
            }
        });
        Bcgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatText_cgreen();
            }
        });

        ImageView IVpp = (ImageView)findViewById(R.id.imageView24);

        mPublicProfileDatabase = FirebaseDatabase.getInstance().getReference().child("Profile_friends");
        mPublicProfileDatabase.keepSynced(true);

        mPublicProfileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id))
                { mPublicProfileDatabase.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Picasso.with(activity_chats.this).load(dataSnapshot.child("image").getValue().toString()).placeholder(R.drawable.errf).into(IVpp);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        IVpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mRegProgress=new ProgressDialog(activity_chats.this);
                mRegProgress.setTitle("Opening profile");

                mRegProgress.setCanceledOnTouchOutside(false);
                mRegProgress.show();

                mPublicProfileDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(user_id))
                        { mPublicProfileDatabase.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                mRegProgress.dismiss();
                                Intent startIntent = new Intent(activity_chats.this, ViewProfileFriends.class);
                                startIntent.putExtra("user_id", user_id);
                                startActivity(startIntent);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });
                        }
                        else
                        {
                            mRegProgress.dismiss();
                            Toast.makeText(context, "The profile is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
        });

        ImageView Bbackk = (ImageView)findViewById(R.id.imageView25);

        Bbackk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(activity_chats.this, MainActivity.class);
                startActivity(startIntent);
            }
        });

        mStateDatabase_in_app.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TextView Tstate = (TextView)findViewById(R.id.textView44);

                String start = Tstate.getText().toString();

                if(dataSnapshot.hasChild(user_id))
                {

                    stateinapp = dataSnapshot.child(user_id).getValue(String.class);

                    if(!stateinapp.equals("Online_in_app"))
                    {
                     //   Tstate.setBackgroundResource(R.drawable.roundbuttongray);
                        Tstate.setTextColor(Color.WHITE);
                        Tstate.setText("Offline");
                        ImageView ivggsd = (ImageView)findViewById(R.id.imageView26);
                        ivggsd.setImageResource(R.drawable.ic_fiber_gray);

                        mdata.child("Typing_State").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild(uuser_id))
                                {
                                    mdata.child("Typing_State").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.hasChild(user_id))
                                            {
                                                mdata.child("Typing_State").child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                        String x = dataSnapshot.child("state").getValue(String.class);

                                                        TextView Tstate = (TextView)findViewById(R.id.textView44);
                                                        String start = Tstate.getText().toString();

                                                        if(x.equals("typing") && start.equals("Online with me"))
                                                        {
                                                            TextView t = (TextView)findViewById(R.id.textView75);
                                                            t.setVisibility(View.VISIBLE);
                                                        }
                                                        else
                                                        {
                                                            TextView t = (TextView)findViewById(R.id.textView75);
                                                            t.setVisibility(View.INVISIBLE);
                                                        }
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
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    else
                    {
                        if(uuser_id.equals(statewithyou))
                        {
                            Tstate.setText("Online with me");
                          //  Tstate.setBackgroundResource(R.drawable.roundbutton);
                            Tstate.setTextColor(Color.WHITE);
                            ImageView ivggd = (ImageView)findViewById(R.id.imageView26);
                            ivggd.setImageResource(R.drawable.ic_fiber_green);

                            mdata.child("Typing_State").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(uuser_id))
                                    {
                                        mdata.child("Typing_State").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild(user_id))
                                                {
                                                    mdata.child("Typing_State").child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            String x = dataSnapshot.child("state").getValue(String.class);

                                                            TextView Tstate = (TextView)findViewById(R.id.textView44);
                                                            String start = Tstate.getText().toString();

                                                            if(x.equals("typing") && start.equals("Online with me"))
                                                            {
                                                                TextView t = (TextView)findViewById(R.id.textView75);
                                                                t.setVisibility(View.VISIBLE);
                                                            }
                                                            else
                                                            {
                                                                TextView t = (TextView)findViewById(R.id.textView75);
                                                                t.setVisibility(View.INVISIBLE);
                                                            }
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
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                        else
                        {
                          //  Tstate.setBackgroundResource(R.drawable.roundbuttondar);
                            Tstate.setTextColor(Color.WHITE);
                            Tstate.setText("Online in app");
                            ImageView ivggd = (ImageView)findViewById(R.id.imageView26);
                            ivggd.setImageResource(R.drawable.ic_fiber_orange);

                            mdata.child("Typing_State").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(uuser_id))
                                    {
                                        mdata.child("Typing_State").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild(user_id))
                                                {
                                                    mdata.child("Typing_State").child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            String x = dataSnapshot.child("state").getValue(String.class);

                                                            TextView Tstate = (TextView)findViewById(R.id.textView44);
                                                            String start = Tstate.getText().toString();

                                                            if(x.equals("typing") && start.equals("Online with me"))
                                                            {
                                                                TextView t = (TextView)findViewById(R.id.textView75);
                                                                t.setVisibility(View.VISIBLE);
                                                            }
                                                            else
                                                            {
                                                                TextView t = (TextView)findViewById(R.id.textView75);
                                                                t.setVisibility(View.INVISIBLE);
                                                            }
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
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                }
                else
                {

                   // Tstate.setBackgroundResource(R.drawable.roundbuttongray);
                    Tstate.setTextColor(Color.WHITE);
                    Tstate.setText("Offline");
                    ImageView ivggd = (ImageView)findViewById(R.id.imageView26);
                    ivggd.setImageResource(R.drawable.ic_fiber_gray);

                    mdata.child("Typing_State").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(uuser_id))
                            {
                                mdata.child("Typing_State").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild(user_id))
                                        {
                                            mdata.child("Typing_State").child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    String x = dataSnapshot.child("state").getValue(String.class);

                                                    TextView Tstate = (TextView)findViewById(R.id.textView44);
                                                    String start = Tstate.getText().toString();

                                                    if(x.equals("typing") && start.equals("Online with me"))
                                                    {
                                                        TextView t = (TextView)findViewById(R.id.textView75);
                                                        t.setVisibility(View.VISIBLE);
                                                    }
                                                    else
                                                    {
                                                        TextView t = (TextView)findViewById(R.id.textView75);
                                                        t.setVisibility(View.INVISIBLE);
                                                    }
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
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

                if(!start.equals(Tstate.getText().toString())) {
                    final Animation myAnim1 = AnimationUtils.loadAnimation(activity_chats.this, R.anim.bouncec);
                    MyBounceInterpolator interpolator1 = new MyBounceInterpolator(0.4, 1);  // with amplitude 0.2 and frequency 20
                    myAnim1.setInterpolator(interpolator1);
                    Tstate.startAnimation(myAnim1);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mStateDatabase_with_you.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TextView Tstate = (TextView)findViewById(R.id.textView44);

                String start = Tstate.getText().toString();

                if(dataSnapshot.hasChild(user_id))
                {


                    statewithyou = dataSnapshot.child(user_id).getValue(String.class);

                    if(!stateinapp.equals("Online_in_app"))
                    {
                    //    Tstate.setBackgroundResource(R.drawable.roundbuttongray);
                        Tstate.setTextColor(Color.WHITE);
                        Tstate.setText("Offline");
                        ImageView ivggd = (ImageView)findViewById(R.id.imageView26);
                        ivggd.setImageResource(R.drawable.ic_fiber_gray);

                        mdata.child("Typing_State").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild(uuser_id))
                                {
                                    mdata.child("Typing_State").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.hasChild(user_id))
                                            {
                                                mdata.child("Typing_State").child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                        String x = dataSnapshot.child("state").getValue(String.class);

                                                        TextView Tstate = (TextView)findViewById(R.id.textView44);
                                                        String start = Tstate.getText().toString();

                                                        if(x.equals("typing") && start.equals("Online with me"))
                                                        {
                                                            TextView t = (TextView)findViewById(R.id.textView75);
                                                            t.setVisibility(View.VISIBLE);
                                                        }
                                                        else
                                                        {
                                                            TextView t = (TextView)findViewById(R.id.textView75);
                                                            t.setVisibility(View.INVISIBLE);
                                                        }
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
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    else
                    {
                        if(uuser_id.equals(statewithyou))
                        {
                            Tstate.setText("Online with me");
                          //  Tstate.setBackgroundResource(R.drawable.roundbutton);
                            Tstate.setTextColor(Color.WHITE);
                            ImageView ivggd = (ImageView)findViewById(R.id.imageView26);
                            ivggd.setImageResource(R.drawable.ic_fiber_green);

                            mdata.child("Typing_State").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(uuser_id))
                                    {
                                        mdata.child("Typing_State").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild(user_id))
                                                {
                                                    mdata.child("Typing_State").child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            String x = dataSnapshot.child("state").getValue(String.class);

                                                            TextView Tstate = (TextView)findViewById(R.id.textView44);
                                                            String start = Tstate.getText().toString();

                                                            if(x.equals("typing") && start.equals("Online with me"))
                                                            {
                                                                TextView t = (TextView)findViewById(R.id.textView75);
                                                                t.setVisibility(View.VISIBLE);
                                                            }
                                                            else
                                                            {
                                                                TextView t = (TextView)findViewById(R.id.textView75);
                                                                t.setVisibility(View.INVISIBLE);
                                                            }
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
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                        else
                        {
                          //  Tstate.setBackgroundResource(R.drawable.roundbuttondar);
                            Tstate.setTextColor(Color.WHITE);
                            Tstate.setText("Online in app");
                            ImageView ivggd = (ImageView)findViewById(R.id.imageView26);
                            ivggd.setImageResource(R.drawable.ic_fiber_orange);

                            mdata.child("Typing_State").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(uuser_id))
                                    {
                                        mdata.child("Typing_State").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild(user_id))
                                                {
                                                    mdata.child("Typing_State").child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            String x = dataSnapshot.child("state").getValue(String.class);

                                                            TextView Tstate = (TextView)findViewById(R.id.textView44);
                                                            String start = Tstate.getText().toString();

                                                            if(x.equals("typing") && start.equals("Online with me"))
                                                            {
                                                                TextView t = (TextView)findViewById(R.id.textView75);
                                                                t.setVisibility(View.VISIBLE);
                                                            }
                                                            else
                                                            {
                                                                TextView t = (TextView)findViewById(R.id.textView75);
                                                                t.setVisibility(View.INVISIBLE);
                                                            }
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
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                }
                else
                {
                 //   Tstate.setBackgroundResource(R.drawable.roundbuttongray);
                    Tstate.setTextColor(Color.WHITE);
                    Tstate.setText("Offline");
                    ImageView ivggd = (ImageView)findViewById(R.id.imageView26);
                    ivggd.setImageResource(R.drawable.ic_fiber_gray);

                    mdata.child("Typing_State").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(uuser_id))
                            {
                                mdata.child("Typing_State").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild(user_id))
                                        {
                                            mdata.child("Typing_State").child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    String x = dataSnapshot.child("state").getValue(String.class);

                                                    TextView Tstate = (TextView)findViewById(R.id.textView44);
                                                    String start = Tstate.getText().toString();

                                                    if(x.equals("typing") && start.equals("Online with me"))
                                                    {
                                                        TextView t = (TextView)findViewById(R.id.textView75);
                                                        t.setVisibility(View.VISIBLE);
                                                    }
                                                    else
                                                    {
                                                        TextView t = (TextView)findViewById(R.id.textView75);
                                                        t.setVisibility(View.INVISIBLE);
                                                    }
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
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

                if(!start.equals(Tstate.getText().toString())) {
                    final Animation myAnim1 = AnimationUtils.loadAnimation(activity_chats.this, R.anim.bouncec);
                    MyBounceInterpolator interpolator1 = new MyBounceInterpolator(0.4, 1);  // with amplitude 0.2 and frequency 20
                    myAnim1.setInterpolator(interpolator1);
                    Tstate.startAnimation(myAnim1);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


DatabaseReference userStatus = FirebaseDatabase.getInstance().getReference("State_with_you").child(uuser_id);
userStatus.setValue(user_id);

userStatus.onDisconnect().setValue("");


        Bsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences settingsssa = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editorssa = settingsssa.edit();
                editorssa.putString(user_id+uuser_id, "");
                editorssa.commit();


if (EmsgMsg.equals(""))  EmsgMsg = Emsg.getText().toString().replace("\n","<br>");

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("statepara1", "");
                editor.commit();

                ccount = 0;

                Bsend.setBackgroundResource(R.drawable.ic_menu_send_green);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Bsend.setBackgroundResource(R.drawable.ic_menu_send);
                    }
                }.start();

                if (!Emsg.getText().toString().equals(""))
                {


                    final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                    c.add(Calendar.DATE, 0);
                    Date resultdate;
                    resultdate = new Date(c.getTimeInMillis());
                    String s =sdf.format(resultdate);

                    Calendar rightNow = Calendar.getInstance();
                    int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                    int currentmin = rightNow.get(Calendar.MINUTE); //

                    String ffp = String.valueOf(currentmin).toString();
                    if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

                    String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                    if(hour.length()<5) hour = "0"+hour;
                    String DateTime = s + hour;

                    HashMap<String, String> object = new HashMap<String, String>();
                    object.put("from", uuser_id);
                    object.put("msg_or_url", EmsgMsg);
                    gf=EmsgMsg;
                    EmsgMsg = "";
                    object.put("msg_type", "text");
                    object.put("dateTime", DateTime);
                    object.put("sent_received_seen", "sent"); //  sent , received, seen

                    String  outputssss = mChatDatabase.child(uuser_id).child(user_id).push().getKey();

                    object.put("from_orignal_id", uuser_id);
                    object.put("from_orignal_key", outputssss); //  sent , received, seen



                mChatDatabase.child(uuser_id).child(user_id).child(outputssss).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        gf = gf.replace("<b>", "");
                        gf = gf.replace("</b>", "");
                        gf = gf.replace("<u>", "");
                        gf = gf.replace("</u>", "");
                        gf = gf.replace("<i>", "");
                        gf = gf.replace("</i>", "");
                        gf = gf.replace("</font>", "");
                        gf = gf.replace("<font color=\"black\">", "");
                        gf = gf.replace("<font color=\"blue\">", "");
                        gf = gf.replace("<font color=\"red\">", "");
                        gf = gf.replace("<font color=\"gray\">", "");
                        gf = gf.replace("<font color=\"white\">", "");
                        gf = gf.replace("<font color=\"green\">", "");
                        gf = gf.replace("                     ", " ");
                        gf = gf.replace("                    ", " ");
                        gf = gf.replace("                   ", " ");
                        gf = gf.replace("                  ", " ");
                        gf = gf.replace("                 ", " ");
                        gf = gf.replace("                ", " ");
                        gf = gf.replace("               ", " ");
                        gf = gf.replace("              ", " ");
                        gf = gf.replace("             ", " ");
                        gf = gf.replace("            ", " ");
                        gf = gf.replace("           ", " ");
                        gf = gf.replace("          ", " ");
                        gf = gf.replace("         ", " ");
                        gf = gf.replace("        ", " ");
                        gf = gf.replace("       ", " ");
                        gf = gf.replace("      ", " ");
                        gf = gf.replace("     ", " ");
                        gf = gf.replace("    ", " ");
                        gf = gf.replace("   ", " ");
                        gf = gf.replace("  ", " ");

                        HashMap<String, String> object2 = new HashMap<String, String>();
                        object2.put("msg_or_url", gf);

                        if(!namito.equals(""))

                            mNotificationDatabase.child(uuser_id).child(user_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                        else
                            Toast.makeText(context, "The user account is deactivated", Toast.LENGTH_SHORT).show();


                        mfriendsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild(user_id))
                                {
                                    mfriendsDatabase.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            if(dataSnapshot.hasChild(uuser_id)) {
                                                mfriendsDatabase.child(user_id).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                        Calendar c = Calendar.getInstance();
                                                        try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                        c.add(Calendar.DATE, 0);
                                                        Date resultdate;
                                                        resultdate = new Date(c.getTimeInMillis());
                                                        String s =sdf.format(resultdate);
                                                        Calendar rightNow = Calendar.getInstance();
                                                        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                        int currentmin = rightNow.get(Calendar.MINUTE); //
                                                        String ffp = String.valueOf(currentmin).toString();
                                                        if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                        String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                        if(hour.length()<5) hour = "0"+hour;
                                                        String DateTime = s + hour;

                                                        int count = Integer.parseInt(dataSnapshot.child("count").getValue(String.class).substring(15));
                                                        count++;
                                                        mfriendsDatabase.child(user_id).child(uuser_id).child("count").setValue(DateTime+count).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                            }
                                                        });
                                                        mfriendsDatabase.child(uuser_id).child(user_id).child("count").setValue(DateTime+"0").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                            }
                                                        });
                                                        mfriendsDatabase.child(user_id).child(uuser_id).child("status").setValue(gf).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                            }
                                                        });
                                                        mfriendsDatabase.child(uuser_id).child(user_id).child("status").setValue(gf).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                            }
                                                        });

                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                            else
                                            {
                                                mUsersDatabaseo.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshoti) {

                                                        final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                        Calendar c = Calendar.getInstance();
                                                        try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                        c.add(Calendar.DATE, 0);
                                                        Date resultdate;
                                                        resultdate = new Date(c.getTimeInMillis());
                                                        String s =sdf.format(resultdate);
                                                        Calendar rightNow = Calendar.getInstance();
                                                        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                        int currentmin = rightNow.get(Calendar.MINUTE); //
                                                        String ffp = String.valueOf(currentmin).toString();
                                                        if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                        String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                        if(hour.length()<5) hour = "0"+hour;
                                                        String DateTime = s + hour;

                                                        TextView Tname = (TextView)findViewById(R.id.textView30);

                                                        HashMap<String, String> userMap2 = new HashMap<>();
                                                        userMap2.put("name", Tname.getText().toString());
                                                        userMap2.put("status", gf);
                                                        userMap2.put("image", user_image);
                                                        userMap2.put("count", DateTime+"1");

                                                        mfriendsDatabase.child(user_id).child(uuser_id).setValue(userMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                            }
                                                        });
                                                        userMap2.put("count", DateTime+"0");
                                                        mfriendsDatabase.child(uuser_id).child(user_id).setValue(userMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                            }
                                                        });
                                                        mdata.child("Typing_State").child(user_id).child(uuser_id).child("state").setValue("not typing").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                            }
                                                        });
                                                        mdata.child("Typing_State").child(uuser_id).child(user_id).child("state").setValue("not typing").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

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
                                else
                                {
                                    mUsersDatabaseo.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshotw) {

                                            final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            Calendar c = Calendar.getInstance();
                                            try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                            c.add(Calendar.DATE, 0);
                                            Date resultdate;
                                            resultdate = new Date(c.getTimeInMillis());
                                            String s =sdf.format(resultdate);
                                            Calendar rightNow = Calendar.getInstance();
                                            int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                            int currentmin = rightNow.get(Calendar.MINUTE); //
                                            String ffp = String.valueOf(currentmin).toString();
                                            if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                            String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                            if(hour.length()<5) hour = "0"+hour;
                                            String DateTime = s + hour;

                                            TextView Tname = (TextView)findViewById(R.id.textView30);

                                            HashMap<String, String> userMaps2 = new HashMap<>();
                                            userMaps2.put("name", Tname.getText().toString());
                                            userMaps2.put("status", gf);
                                            userMaps2.put("image",""+ user_image);
                                            userMaps2.put("count", DateTime+"1");

                                            mfriendsDatabase.child(user_id).child(uuser_id).setValue(userMaps2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });
                                            userMaps2.put("count", DateTime+"0");
                                            mfriendsDatabase.child(uuser_id).child(user_id).setValue(userMaps2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });
                                            mdata.child("Typing_State").child(user_id).child(uuser_id).child("state").setValue("not typing").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });
                                            mdata.child("Typing_State").child(uuser_id).child(user_id).child("state").setValue("not typing").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

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
                });
                mChatDatabase.child(user_id).child(uuser_id).push().setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {



                            mAutoReplyDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(uuser_id))
                                    {
                                        mAutoReplyDatabase.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild(user_id))
                                                {
                                                    mAutoReplyDatabase.child(uuser_id).child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                             msgg = dataSnapshot.getValue(AutoReplyForMe.class).getAuto_reply_msg();

                                                            int date_from_y = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getDate_from_y());
                                                            int date_from_m = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getDate_from_m());
                                                            int date_from_d = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getDate_from_d());
                                                            int date_to_y = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getDate_to_y());
                                                            int date_to_m = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getDate_to_m());
                                                            int date_to_d = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getDate_to_d());
                                                            int time_from_h = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getTime_from_h());
                                                            int time_from_m = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getTime_from_m());
                                                            int time_to_h = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getTime_to_h());
                                                            int time_to_m = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getTime_to_m());




                                                            final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                            Calendar c = Calendar.getInstance();
                                                            try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                            c.add(Calendar.DATE, 0);
                                                            Date resultdate;
                                                            resultdate = new Date(c.getTimeInMillis());
                                                            String s =sdf.format(resultdate);

                                                            Calendar rightNow = Calendar.getInstance();
                                                            int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                            int currentmin = rightNow.get(Calendar.MINUTE); //

                                                            String ffp = String.valueOf(currentmin).toString();
                                                            if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

                                                            String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                            if(hour.length()<5) hour = "0"+hour;
                                                            String DateTime = s + hour;

                                                            int y = Integer.parseInt( DateTime.substring(0,4));
                                                            int m = Integer.parseInt( DateTime.substring(5,7));
                                                            int d = Integer.parseInt( DateTime.substring(8,10));

                                                            double from = time_from_m+time_from_h*60+date_from_d*60*24+date_from_m*60*24*30.5+ date_from_y*60*24*30.5*12;
                                                            double too = time_to_m+time_to_h*60+date_to_d*60*24+date_to_m*60*24*30.5+ date_to_y*60*24*30.5*12;
                                                            double current = currentmin+currentHour*60+d*60*24+m*60*24*30.5+ y*60*24*30.5*12;

                                                            if(current>=from && current<=too)
                                                            f();
                                                        /*
                                                            if(y>date_from_y && y<date_to_y)
                                                            {
                                                                if(currentHour>time_from_h && currentHour<time_to_h) {

                                                                    f();


                                                                }
                                                                if(currentHour==time_from_h)
                                                                {
                                                                    if(currentmin>time_from_m)
                                                                        f();

                                                                }
                                                                if(currentHour==time_to_h)
                                                                {
                                                                    if(currentmin<time_to_m)
                                                                        f();

                                                                }
                                                            }
                                                            else if(y==date_from_y)
                                                            {
                                                                if(m>date_from_m)
                                                                    if(y>date_from_y && y<date_to_y)
                                                                    {
                                                                        if(currentHour>time_from_h && currentHour<time_to_h) {

                                                                            f();


                                                                        }
                                                                        if(currentHour==time_from_h)
                                                                        {
                                                                            if(currentmin>time_from_m)
                                                                                f();

                                                                        }
                                                                        if(currentHour==time_to_h)
                                                                        {
                                                                            if(currentmin<time_to_m)
                                                                                f();

                                                                        }
                                                                    }
                                                                else if (m==date_from_m)
                                                                {
                                                                    if(d>date_from_d)
                                                                        if(y>date_from_y && y<date_to_y)
                                                                        {
                                                                            if(currentHour>time_from_h && currentHour<time_to_h) {

                                                                                f();


                                                                            }
                                                                            if(currentHour==time_from_h)
                                                                            {
                                                                                if(currentmin>time_from_m)
                                                                                    f();

                                                                            }
                                                                            if(currentHour==time_to_h)
                                                                            {
                                                                                if(currentmin<time_to_m)
                                                                                    f();

                                                                            }
                                                                        }



                                                                }
                                                            }
                                                            else if(y==date_to_y)
                                                            {
                                                                if(m>date_to_m)
                                                                    if(y>date_from_y && y<date_to_y)
                                                                    {
                                                                        if(currentHour>time_from_h && currentHour<time_to_h) {

                                                                            f();


                                                                        }
                                                                        if(currentHour==time_from_h)
                                                                        {
                                                                            if(currentmin>time_from_m)
                                                                                f();

                                                                        }
                                                                        if(currentHour==time_to_h)
                                                                        {
                                                                            if(currentmin<time_to_m)
                                                                                f();

                                                                        }
                                                                    }
                                                                else if (m==date_to_m)
                                                                {
                                                                    if(d>date_to_d)
                                                                        if(y>date_from_y && y<date_to_y)
                                                                        {
                                                                            if(currentHour>time_from_h && currentHour<time_to_h) {

                                                                                f();


                                                                            }
                                                                            if(currentHour==time_from_h)
                                                                            {
                                                                                if(currentmin>time_from_m)
                                                                                    f();

                                                                            }
                                                                            if(currentHour==time_to_h)
                                                                            {
                                                                                if(currentmin<time_to_m)
                                                                                    f();

                                                                            }
                                                                        }




                                                                }
                                                            }

*/



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
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            // mprofilesendRequest.setEnabled(true);
                            //  mCurrent_state = "req_sent";
                            // mprofilesendRequest.setText("Cancel friend request");
                            //  mDecline.setVisibility(View.INVISIBLE);
                            //  mDecline.setEnabled(false);
                        }
                    });

                mdata.child("Diver_assign_by_me").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(user_id))
                            {
                                String user_id_diver = dataSnapshot.child(user_id).child("id").getValue(String.class);
                                object.put("msg_or_url", "<i>Divert message: Sent from " + Name_uuser_id + " to " + Name_user_id +":</i><br>"+ gf);

                                mChatDatabase.child(uuser_id).child(user_id_diver).child(outputssss).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        gf = gf.replace("<b>", "");
                                        gf = gf.replace("</b>", "");
                                        gf = gf.replace("<u>", "");
                                        gf = gf.replace("</u>", "");
                                        gf = gf.replace("<i>", "");
                                        gf = gf.replace("</i>", "");
                                        gf = gf.replace("</font>", "");
                                        gf = gf.replace("<font color=\"black\">", "");
                                        gf = gf.replace("<font color=\"blue\">", "");
                                        gf = gf.replace("<font color=\"red\">", "");
                                        gf = gf.replace("<font color=\"gray\">", "");
                                        gf = gf.replace("<font color=\"white\">", "");
                                        gf = gf.replace("<font color=\"green\">", "");
                                        gf = gf.replace("                     ", " ");
                                        gf = gf.replace("                    ", " ");
                                        gf = gf.replace("                   ", " ");
                                        gf = gf.replace("                  ", " ");
                                        gf = gf.replace("                 ", " ");
                                        gf = gf.replace("                ", " ");
                                        gf = gf.replace("               ", " ");
                                        gf = gf.replace("              ", " ");
                                        gf = gf.replace("             ", " ");
                                        gf = gf.replace("            ", " ");
                                        gf = gf.replace("           ", " ");
                                        gf = gf.replace("          ", " ");
                                        gf = gf.replace("         ", " ");
                                        gf = gf.replace("        ", " ");
                                        gf = gf.replace("       ", " ");
                                        gf = gf.replace("      ", " ");
                                        gf = gf.replace("     ", " ");
                                        gf = gf.replace("    ", " ");
                                        gf = gf.replace("   ", " ");
                                        gf = gf.replace("  ", " ");

                                        HashMap<String, String> object2 = new HashMap<String, String>();
                                        object2.put("msg_or_url", "Divert message: Sent from " + Name_uuser_id + " to " + Name_user_id +": "+ gf);

                                        if(!namito.equals(""))

                                            mNotificationDatabase.child(uuser_id).child(user_id_diver).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });
                                        else
                                            Toast.makeText(context, "The user account is deactivated", Toast.LENGTH_SHORT).show();



                                        mfriendsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild(user_id_diver))
                                                {
                                                    mfriendsDatabase.child(user_id_diver).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            if(dataSnapshot.hasChild(uuser_id)) {
                                                                mfriendsDatabase.child(user_id_diver).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                                        Calendar c = Calendar.getInstance();
                                                                        try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                                        c.add(Calendar.DATE, 0);
                                                                        Date resultdate;
                                                                        resultdate = new Date(c.getTimeInMillis());
                                                                        String s =sdf.format(resultdate);
                                                                        Calendar rightNow = Calendar.getInstance();
                                                                        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                                        int currentmin = rightNow.get(Calendar.MINUTE); //
                                                                        String ffp = String.valueOf(currentmin).toString();
                                                                        if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                                        String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                                        if(hour.length()<5) hour = "0"+hour;
                                                                        String DateTime = s + hour;

                                                                        int count = Integer.parseInt(dataSnapshot.child("count").getValue(String.class).substring(15));
                                                                        count++;

                                                                        mfriendsDatabase.child(user_id_diver).child(uuser_id).child("count").setValue(DateTime+count).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {

                                                                            }
                                                                        });
                                                                        mfriendsDatabase.child(user_id_diver).child(uuser_id).child("status").setValue("Divert message: Sent from " + Name_uuser_id + " to " + Name_user_id +": "+ gf).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {

                                                                            }
                                                                        });

                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {

                                                                    }
                                                                });
                                                            }
                                                            else
                                                            {
                                                                mUsersDatabaseo.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                                        final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                                        Calendar c = Calendar.getInstance();
                                                                        try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                                        c.add(Calendar.DATE, 0);
                                                                        Date resultdate;
                                                                        resultdate = new Date(c.getTimeInMillis());
                                                                        String s =sdf.format(resultdate);
                                                                        Calendar rightNow = Calendar.getInstance();
                                                                        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                                        int currentmin = rightNow.get(Calendar.MINUTE); //
                                                                        String ffp = String.valueOf(currentmin).toString();
                                                                        if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                                        String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                                        if(hour.length()<5) hour = "0"+hour;
                                                                        String DateTime = s + hour;

                                                                        HashMap<String, String> userMap2 = new HashMap<>();
                                                                        userMap2.put("name", dataSnapshot.child("name").getValue(String.class));
                                                                        userMap2.put("status", "Divert message: Sent from " + Name_uuser_id + " to " + Name_user_id +": "+ gf);
                                                                        userMap2.put("image", dataSnapshot.child("image").getValue(String.class));
                                                                        userMap2.put("count", DateTime+"1");

                                                                        mfriendsDatabase.child(user_id_diver).child(uuser_id).setValue(userMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {

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
                                                else
                                                {
                                                    mUsersDatabaseo.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshotw) {

                                                            final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                            Calendar c = Calendar.getInstance();
                                                            try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                            c.add(Calendar.DATE, 0);
                                                            Date resultdate;
                                                            resultdate = new Date(c.getTimeInMillis());
                                                            String s =sdf.format(resultdate);
                                                            Calendar rightNow = Calendar.getInstance();
                                                            int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                            int currentmin = rightNow.get(Calendar.MINUTE); //
                                                            String ffp = String.valueOf(currentmin).toString();
                                                            if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                            String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                            if(hour.length()<5) hour = "0"+hour;
                                                            String DateTime = s + hour;

                                                            HashMap<String, String> userMaps2 = new HashMap<>();
                                                            userMaps2.put("name", dataSnapshotw.child("name").getValue(String.class));
                                                            userMaps2.put("status", "Divert message: Sent from " + Name_uuser_id + " to " + Name_user_id +": "+ gf);
                                                            userMaps2.put("image",""+ dataSnapshotw.child("image").getValue(String.class));
                                                            userMaps2.put("count", DateTime+"1");

                                                            mfriendsDatabase.child(user_id_diver).child(uuser_id).setValue(userMaps2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {

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
                                });
                                mChatDatabase.child(user_id_diver).child(uuser_id).push().setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {



                                        mAutoReplyDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild(uuser_id))
                                                {
                                                    mAutoReplyDatabase.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.hasChild(user_id))
                                                            {
                                                                mAutoReplyDatabase.child(uuser_id).child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                                        msgg = dataSnapshot.getValue(AutoReplyForMe.class).getAuto_reply_msg();

                                                                        int date_from_y = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getDate_from_y());
                                                                        int date_from_m = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getDate_from_m());
                                                                        int date_from_d = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getDate_from_d());
                                                                        int date_to_y = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getDate_to_y());
                                                                        int date_to_m = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getDate_to_m());
                                                                        int date_to_d = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getDate_to_d());
                                                                        int time_from_h = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getTime_from_h());
                                                                        int time_from_m = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getTime_from_m());
                                                                        int time_to_h = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getTime_to_h());
                                                                        int time_to_m = Integer.parseInt( dataSnapshot.getValue(AutoReplyForMe.class).getTime_to_m());




                                                                        final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                                        Calendar c = Calendar.getInstance();
                                                                        try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                                        c.add(Calendar.DATE, 0);
                                                                        Date resultdate;
                                                                        resultdate = new Date(c.getTimeInMillis());
                                                                        String s =sdf.format(resultdate);

                                                                        Calendar rightNow = Calendar.getInstance();
                                                                        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                                        int currentmin = rightNow.get(Calendar.MINUTE); //

                                                                        String ffp = String.valueOf(currentmin).toString();
                                                                        if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

                                                                        String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                                        if(hour.length()<5) hour = "0"+hour;
                                                                        String DateTime = s + hour;

                                                                        int y = Integer.parseInt( DateTime.substring(0,4));
                                                                        int m = Integer.parseInt( DateTime.substring(5,7));
                                                                        int d = Integer.parseInt( DateTime.substring(8,10));


                                                                        double from = time_from_m+time_from_h*60+date_from_d*60*24+date_from_m*60*24*30.5+ date_from_y*60*24*30.5*12;
                                                                        double too = time_to_m+time_to_h*60+date_to_d*60*24+date_to_m*60*24*30.5+ date_to_y*60*24*30.5*12;
                                                                        double current = currentmin+currentHour*60+d*60*24+m*60*24*30.5+ y*60*24*30.5*12;

                                                                        if(current>=from && current<=too)
                                                                            f();


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
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                        // mprofilesendRequest.setEnabled(true);
                                        //  mCurrent_state = "req_sent";
                                        // mprofilesendRequest.setText("Cancel friend request");
                                        //  mDecline.setVisibility(View.INVISIBLE);
                                        //  mDecline.setEnabled(false);
                                    }
                                });




                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });







                    Emsg.setText("");
                    effect = "";
                    EmsgMsg="";


            }

            }
        });

        mNoteDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(uuser_id)) {

                    mNoteDatabase.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(!dataSnapshot.hasChild(user_id)) {

                                String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar c = Calendar.getInstance();
                                try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                c.add(Calendar.DATE, 0);
                                Date resultdate;
                                resultdate = new Date(c.getTimeInMillis());
                                String s =sdf.format(resultdate);

                                Calendar rightNow = Calendar.getInstance();
                                int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                int currentmin = rightNow.get(Calendar.MINUTE); //

                                String ffp = String.valueOf(currentmin).toString();
                                if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

                                String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                if(hour.length()<5) hour = "0"+hour;
                                String DateTime = s + hour;

                                HashMap<String, String> object2 = new HashMap<String, String>();
                                object2.put("from", uuser_id);
                                object2.put("note_msg", "");
                                object2.put("dateTime", DateTime);
                                object2.put("sent_received_seen", "sent"); //  sent , received, seen


                                mNoteDatabase.child(uuser_id).child(user_id).child("note").setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // mprofilesendRequest.setEnabled(true);
                                        //  mCurrent_state = "req_sent";
                                        // mprofilesendRequest.setText("Cancel friend request");
                                        //  mDecline.setVisibility(View.INVISIBLE);
                                        //  mDecline.setEnabled(false);
                                    }
                                });
                                mNoteDatabase.child(user_id).child(uuser_id).child("note").setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                    c.add(Calendar.DATE, 0);
                    Date resultdate;
                    resultdate = new Date(c.getTimeInMillis());
                    String s =sdf.format(resultdate);

                    Calendar rightNow = Calendar.getInstance();
                    int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                    int currentmin = rightNow.get(Calendar.MINUTE); //

                    String ffp = String.valueOf(currentmin).toString();
                    if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

                    String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                    if(hour.length()<5) hour = "0"+hour;
                    String DateTime = s + hour;

                    HashMap<String, String> object2 = new HashMap<String, String>();
                    object2.put("from", uuser_id);
                    object2.put("note_msg", "");
                    object2.put("dateTime", DateTime);
                    object2.put("sent_received_seen", "sent"); //  sent , received, seen

                    mNoteDatabase.child(uuser_id).child(user_id).child("note").setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                    mNoteDatabase.child(user_id).child(uuser_id).child("note").setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

  String  user_image = "";

    int ccount = 0;

int stop = 0;
    int start = 0;

    private void formatText_bold() {
        int end = Emsg.length();
        // EmsgMsg = Emsg.getText().toString();

        start = Emsg.getSelectionStart();
        stop = Emsg.getSelectionEnd();

        if (start > stop) {
            stop = Emsg.getSelectionStart();
            start = Emsg.getSelectionEnd();}

        String textBefore = Emsg.getText().subSequence(0, start).toString();
        String selectedText =Emsg.getText().subSequence(start, stop).toString();
        String textAfter = Emsg.getText().subSequence(stop, end).toString();

        if (!selectedText.equals("") || start != stop) {

            String formatted = "<b>" + selectedText + "</b>";
            EmsgMsg = textBefore+formatted+textAfter;
            Emsg.setText(Html.fromHtml(textBefore+formatted+textAfter));
            Emsg.setSelection(stop);}
        else {
            Toast.makeText(context, "select a text", Toast.LENGTH_SHORT).show();
        }
    }

    private void formatText_sup() {
        int end = Emsg.length();
        // EmsgMsg = Emsg.getText().toString();

        start = Emsg.getSelectionStart();
        stop = Emsg.getSelectionEnd();

        if (start > stop) {
            stop = Emsg.getSelectionStart();
            start = Emsg.getSelectionEnd();}

        String textBefore = Emsg.getText().subSequence(0, start).toString();
        String selectedText =Emsg.getText().subSequence(start, stop).toString();
        String textAfter = Emsg.getText().subSequence(stop, end).toString();

        if (!selectedText.equals("") || start != stop) {

            String formatted = "<sup>" + selectedText + "</sup>";
            EmsgMsg = textBefore+formatted+textAfter;
            Emsg.setText(Html.fromHtml(textBefore+formatted+textAfter));
            Emsg.setSelection(stop);}
        else {
            Toast.makeText(context, "select a text", Toast.LENGTH_SHORT).show();
        }
    }

    private void formatText_sub() {
        int end = Emsg.length();
        // EmsgMsg = Emsg.getText().toString();

        start = Emsg.getSelectionStart();
        stop = Emsg.getSelectionEnd();

        if (start > stop) {
            stop = Emsg.getSelectionStart();
            start = Emsg.getSelectionEnd();}

        String textBefore = Emsg.getText().subSequence(0, start).toString();
        String selectedText =Emsg.getText().subSequence(start, stop).toString();
        String textAfter = Emsg.getText().subSequence(stop, end).toString();

        if (!selectedText.equals("") || start != stop) {

            String formatted = "<sub>" + selectedText + "</sub>";
            EmsgMsg = textBefore+formatted+textAfter;
            Emsg.setText(Html.fromHtml(textBefore+formatted+textAfter));
            Emsg.setSelection(stop);}
        else {
            Toast.makeText(context, "select a text", Toast.LENGTH_SHORT).show();
        }
    }

    private void formatText_italic() {
        int end = Emsg.length();
      //  EmsgMsg = Emsg.getText().toString();

        start = Emsg.getSelectionStart();
        stop = Emsg.getSelectionEnd();

        if (start > stop) {
            stop = Emsg.getSelectionStart();
            start = Emsg.getSelectionEnd();}

        String textBefore = Emsg.getText().subSequence(0, start).toString();
        String selectedText =Emsg.getText().subSequence(start, stop).toString();
        String textAfter = Emsg.getText().subSequence(stop, end).toString();

        if (!selectedText.equals("") || start != stop) {

            String formatted = "<i>" + selectedText + "</i>";
            EmsgMsg = textBefore+formatted+textAfter;
            Emsg.setText(Html.fromHtml(textBefore+formatted+textAfter));
            Emsg.setSelection(stop);}
        else {
            Toast.makeText(context, "select a text", Toast.LENGTH_SHORT).show();
        }
    }

    private void formatText_under() {
        int end = Emsg.length();
      //  EmsgMsg = Emsg.getText().toString();

        start = Emsg.getSelectionStart();
        stop = Emsg.getSelectionEnd();

        if (start > stop) {
            stop = Emsg.getSelectionStart();
            start = Emsg.getSelectionEnd();}

        String textBefore = Emsg.getText().subSequence(0, start).toString();
        String selectedText =Emsg.getText().subSequence(start, stop).toString();
        String textAfter = Emsg.getText().subSequence(stop, end).toString();

        if (!selectedText.equals("") || start != stop) {

            String formatted = "<u>" + selectedText + "</u>";
            EmsgMsg = textBefore+formatted+textAfter;
            Emsg.setText(Html.fromHtml(textBefore+formatted+textAfter));
            Emsg.setSelection(stop);}
        else {
            Toast.makeText(context, "select a text", Toast.LENGTH_SHORT).show();
        }
    }

    private void formatText_cred() {
        int end = Emsg.length();
      //  EmsgMsg = Emsg.getText().toString();

        start = Emsg.getSelectionStart();
        stop = Emsg.getSelectionEnd();

        if (start > stop) {
            stop = Emsg.getSelectionStart();
            start = Emsg.getSelectionEnd();}

        String textBefore = Emsg.getText().subSequence(0, start).toString();
        String selectedText =Emsg.getText().subSequence(start, stop).toString();
        String textAfter = Emsg.getText().subSequence(stop, end).toString();

        if (!selectedText.equals("") || start != stop) {

            String formatted = "<font color=\"red\">" + selectedText + "</font>";
            EmsgMsg = textBefore+formatted+textAfter;
            Emsg.setText(Html.fromHtml(textBefore+formatted+textAfter));
            Emsg.setSelection(stop);}
        else {
            Toast.makeText(context, "select a text", Toast.LENGTH_SHORT).show();
        }
    }

    private void formatText_cblue() {
        int end = Emsg.length();
       // EmsgMsg = Emsg.getText().toString();

        start = Emsg.getSelectionStart();
        stop = Emsg.getSelectionEnd();

        if (start > stop) {
            stop = Emsg.getSelectionStart();
            start = Emsg.getSelectionEnd();}

        String textBefore = Emsg.getText().subSequence(0, start).toString();
        String selectedText =Emsg.getText().subSequence(start, stop).toString();
        String textAfter = Emsg.getText().subSequence(stop, end).toString();

        if (!selectedText.equals("") || start != stop) {

            String formatted = "<font color=\"blue\">" + selectedText + "</font>";
            EmsgMsg = textBefore+formatted+textAfter;
            Emsg.setText(Html.fromHtml(textBefore+formatted+textAfter));
            Emsg.setSelection(stop);}
        else {
            Toast.makeText(context, "select a text", Toast.LENGTH_SHORT).show();
        }
    }

    private void formatText_cblack() {
        int end = Emsg.length();
       // EmsgMsg = Emsg.getText().toString();

        start = Emsg.getSelectionStart();
        stop = Emsg.getSelectionEnd();

        if (start > stop) {
            stop = Emsg.getSelectionStart();
            start = Emsg.getSelectionEnd();}

        String textBefore = Emsg.getText().subSequence(0, start).toString();
        String selectedText =Emsg.getText().subSequence(start, stop).toString();
        String textAfter = Emsg.getText().subSequence(stop, end).toString();

        if (!selectedText.equals("") || start != stop) {

            String formatted = "<font color=\"black\">" + selectedText + "</font>";
            EmsgMsg = textBefore+formatted+textAfter;
            Emsg.setText(Html.fromHtml(textBefore+formatted+textAfter));
            Emsg.setSelection(stop);}
        else {
            Toast.makeText(context, "select a text", Toast.LENGTH_SHORT).show();
        }
    }

    private void formatText_cgreen() {
        int end = Emsg.length();
     //   EmsgMsg = Emsg.getText().toString();

        start = Emsg.getSelectionStart();
        stop = Emsg.getSelectionEnd();

        if (start > stop) {
            stop = Emsg.getSelectionStart();
            start = Emsg.getSelectionEnd();}

        String textBefore = Emsg.getText().subSequence(0, start).toString();
        String selectedText =Emsg.getText().subSequence(start, stop).toString();
        String textAfter = Emsg.getText().subSequence(stop, end).toString();

        if (!selectedText.equals("") || start != stop) {

            String formatted = "<font color=\"green\">" + selectedText + "</font>";
            EmsgMsg = textBefore+formatted+textAfter;
            Emsg.setText(Html.fromHtml(textBefore+formatted+textAfter));
            Emsg.setSelection(stop);}
        else {
            Toast.makeText(context, "select a text", Toast.LENGTH_SHORT).show();
        }
    }

    private void formatText_cwhite() {
        int end = Emsg.length();
       // EmsgMsg = Emsg.getText().toString();

        start = Emsg.getSelectionStart();
        stop = Emsg.getSelectionEnd();

        if (start > stop) {
            stop = Emsg.getSelectionStart();
            start = Emsg.getSelectionEnd();}

        String textBefore = Emsg.getText().subSequence(0, start).toString();
        String selectedText =Emsg.getText().subSequence(start, stop).toString();
        String textAfter = Emsg.getText().subSequence(stop, end).toString();

        if (!selectedText.equals("") || start != stop) {

            String formatted = "<font color=\"white\">" + selectedText + "</font>";
            EmsgMsg = textBefore+formatted+textAfter;
            Emsg.setText(Html.fromHtml(textBefore+formatted+textAfter));
            Emsg.setSelection(stop);}
        else {
            Toast.makeText(context, "select a text", Toast.LENGTH_SHORT).show();
        }
    }

    private void formatText_cgray() {

     //   Button bbb = (Button)findViewById(R.id.button66);
    //    bbb.setBackgroundColor(Color.rgb(color_r,color_g,color_b));

        int end = Emsg.length();
     //   EmsgMsg = Emsg.getText().toString();

        start = Emsg.getSelectionStart();
        stop = Emsg.getSelectionEnd();

        if (start > stop) {
            stop = Emsg.getSelectionStart();
            start = Emsg.getSelectionEnd();}

        String textBefore = Emsg.getText().subSequence(0, start).toString();
        String selectedText =Emsg.getText().subSequence(start, stop).toString();
        String textAfter = Emsg.getText().subSequence(stop, end).toString();

        if (!selectedText.equals("") || start != stop) {

String rr = Integer.toHexString(color_r);
            if(rr.length()!=2) rr = "0" + rr;
            if(rr.length()!=2) rr = "0" + rr;

            String gg = Integer.toHexString(color_g);
            if(gg.length()!=2) gg = "0" + gg;
            if(gg.length()!=2) gg = "0" + gg;

            String bb = Integer.toHexString(color_b);
            if(bb.length()!=2) bb = "0" + bb;
            if(bb.length()!=2) bb = "0" + bb;

            String rh = rr+ gg+ bb;

            String formatted = "<font color='#"+rh+"'>" + selectedText + "</font>";
            EmsgMsg = textBefore+formatted+textAfter;
            Emsg.setText(Html.fromHtml(textBefore+formatted+textAfter));
            Emsg.setSelection(stop);}
        else {
            Toast.makeText(context, "select a text", Toast.LENGTH_SHORT).show();
        }
    }


    String EmsgMsg ="";
    DatabaseReference mdata;

    void senddelayed()
    {
        CDT.cancel();



        mdata.child("Delayed_msg").child(user_id).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {





                    int yf = Integer.parseInt(dataSnapshot.child("date_from_y").getValue(String.class));
                    int mf = Integer.parseInt(dataSnapshot.child("date_from_m").getValue(String.class));
                    int df = Integer.parseInt(dataSnapshot.child("date_from_d").getValue(String.class));
                    int hf = Integer.parseInt(dataSnapshot.child("time_from_h").getValue(String.class));
                    int mif = Integer.parseInt(dataSnapshot.child("time_from_m").getValue(String.class));

                    Calendar calendar = Calendar.getInstance();
                    int d = calendar.get(Calendar.DAY_OF_MONTH);
                    int y = calendar.get(Calendar.YEAR);
                    int m = calendar.get(Calendar.MONTH)+1;
                    int h = calendar.get(Calendar.HOUR_OF_DAY);
                    int mi = calendar.get(Calendar.MINUTE);



                    final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                    c.add(Calendar.DATE, 0);
                    Date resultdate;
                    resultdate = new Date(c.getTimeInMillis());
                    String s =sdf.format(resultdate);

                    Calendar rightNow = Calendar.getInstance();
                    int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                    int currentmin = rightNow.get(Calendar.MINUTE); //

                    String ffp = String.valueOf(currentmin).toString();
                    if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

                    String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                if(hour.length()<5) hour = "0"+hour;
                    String DateTime = s + hour;

                    HashMap<String, String> object = new HashMap<String, String>();
                    object.put("from", dataSnapshot.child("from").getValue(String.class));
                    object.put("msg_or_url", dataSnapshot.child("auto_reply_msg").getValue(String.class));
                    object.put("msg_type", "text");
                    object.put("dateTime", DateTime);
                    object.put("sent_received_seen", "sent"); //  sent , received, seen
                    object.put("from_orignal_id", dataSnapshot.child("from").getValue(String.class));

                    String  outputs1 = mdata.child("Chats").child(uuser_id).child(user_id).push().getKey();
                    String  outputs2 = mdata.child("Chats").child(user_id).child(uuser_id).push().getKey();

                    if(dataSnapshot.child("from").getValue(String.class).equals(uuser_id))
                        object.put("from_orignal_key", outputs1); //  sent , received, seen
                    else
                        object.put("from_orignal_key", outputs2); //  sent , received, seen


                    mdata.child("Chats").child(uuser_id).child(user_id).child(outputs1).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                    mdata.child("Chats").child(user_id).child(uuser_id).child(outputs2).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });





                HashMap<String, String> object2 = new HashMap<String, String>();
                    object2.put("msg_or_url", dataSnapshot.child("auto_reply_msg").getValue(String.class));

                if(!namito.equals("")) {
                    if (dataSnapshot.child("from").getValue(String.class).equals(uuser_id))
                        mdata.child("Notifications").child(uuser_id).child(user_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    else
                        mdata.child("Notifications").child(user_id).child(uuser_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                }
                else
                    Toast.makeText(context, "The user account is deactivated", Toast.LENGTH_SHORT).show();


                String keyy = user_id;
                    String msgss = dataSnapshot.child("auto_reply_msg").getValue(String.class);

                    mfriendsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChild(uuser_id))
                            {
                                mfriendsDatabase.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {


                                        if(dataSnapshot.hasChild(keyy)) {
                                            mfriendsDatabase.child(uuser_id).child(keyy).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                    Calendar c = Calendar.getInstance();
                                                    try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                    c.add(Calendar.DATE, 0);
                                                    Date resultdate;
                                                    resultdate = new Date(c.getTimeInMillis());
                                                    String s =sdf.format(resultdate);
                                                    Calendar rightNow = Calendar.getInstance();
                                                    int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                    int currentmin = rightNow.get(Calendar.MINUTE); //
                                                    String ffp = String.valueOf(currentmin).toString();
                                                    if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                    String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                    if(hour.length()<5) hour = "0"+hour;
                                                    String DateTime = s + hour;

                                                    int count = Integer.parseInt(dataSnapshot.child("count").getValue(String.class).substring(15));
                                                    count++;

                                                    mfriendsDatabase.child(keyy).child(uuser_id).child("count").setValue(DateTime+count).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    });
                                                    mfriendsDatabase.child(uuser_id).child(keyy).child("status").setValue(msgss).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

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


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mdata.child("Delayed_msg").child(uuser_id).child(user_id).removeValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

String namito="x";

    private ProgressDialog mRegProgress1;

    @Override
    protected void onStart() {
        super.onStart();

        mRegProgress1=new ProgressDialog(activity_chats.this);
        mRegProgress1.setTitle("Loading profile");
        // mRegProgress.setMessage("Please wait while creating your account");
        mRegProgress1.setCanceledOnTouchOutside(false);
     //   mRegProgress1.show();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("statepara1", "");
        editor.commit();

        user_id = getIntent().getStringExtra("user_id");
        user_image = getIntent().getStringExtra("user_image");

        mdata = FirebaseDatabase.getInstance().getReference();
        mdata.keepSynced(true);

        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mRegProgress1.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mdata.child("Users").child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                namito=dataSnapshot.child("name").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ccount = 0;

         final  FirebaseRecyclerAdapter<Chats, activity_chats.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Chats, activity_chats.UsersViewHolder>(
                Chats.class,
                R.layout.chat_msgs_single_layout,
                activity_chats.UsersViewHolder.class,
                mChatDatabase.child(uuser_id).child(user_id)

        ) {


            @Override
            protected void populateViewHolder( activity_chats.UsersViewHolder viewHolder, final Chats model, int position) {

                final String message_key = getRef(position).getKey();
                ccount++;

                viewHolder.setdatetime(
                        model.getdateTime(),
                        user_id,
                        model.getFrom(),
                        ccount,
                        getApplicationContext(),
                        user_image,
                        model.getMsg_or_url(),
                        model.getMsg_type(),
                        model.getSent_received_seen(),
                        activity_chats.this,
                        FilePathUri,
                        getContentResolver()
                );




                final String user_id = getIntent().getStringExtra("user_id");

                mChatDatabase.child(uuser_id).child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        for (DataSnapshot child : children) {
                            if(child.getValue(Chats.class).getFrom().toString().equals(user_id))
                            {
                                if(child.getValue(Chats.class).getSent_received_seen().toString().equals("sent"))
                                {

                                    HashMap<String, Object> resultm = new HashMap<>();
                                    resultm.put("sent_received_seen","received");
                                    mChatDatabase.child(uuser_id).child(user_id).child(child.getKey()).updateChildren(resultm);
                                   // mp = MediaPlayer.create(activity_chats.this, R.raw.unconvinced);
                                   // mp.start();
                                }
                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                mChatDatabase.child(user_id).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        for (DataSnapshot child : children) {
                            if(child.getValue(Chats.class).getFrom().toString().equals(user_id))
                            {
                                if(child.getValue(Chats.class).getSent_received_seen().toString().equals("sent"))
                                {

                                    HashMap<String, Object> resultm = new HashMap<>();
                                    resultm.put("sent_received_seen","received");
                                    mChatDatabase.child(user_id).child(uuser_id).child(child.getKey()).updateChildren(resultm);
                                    mp = MediaPlayer.create(activity_chats.this, R.raw.unconvinced);
                                  //  mp.start();
                                }
                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View view) {

                        TextView userNameViewHim1 = view.findViewById(R.id.chat_msg);
                        TextView userNameViewMe1 = view.findViewById(R.id.textView31);
                        TextView userNameViewHim = view.findViewById(R.id.chat_time);
                        TextView userNameViewMe = view.findViewById(R.id.textView33);

                         LinearLayout Lhims = view.findViewById(R.id.hj);
                         LinearLayout LMes = view.findViewById(R.id.hjs);

                         Button BaudioMe = view.findViewById(R.id.button581);
                         Button BaudioHim = view.findViewById(R.id.button581c);
                         ProgressBar BprogMe = view.findViewById(R.id.progressBar4);
                         ProgressBar BprogHim = view.findViewById(R.id.progressBar3);

                        ConstraintLayout cvv = view.findViewById(R.id.audiohim);
                        ConstraintLayout cvvv = view.findViewById(R.id.audiome);


                            final Dialog dialog = new Dialog(activity_chats.this);


                        if(userNameViewHim1.getVisibility()==0) //0 stands for visible
                        {
                            //2 or 3

                            ImageView ivb = (ImageView) view.findViewById(R.id.imageView19);
                            ImageView ivb1 = (ImageView) view.findViewById(R.id.imageView17);

                            if(cvv.getVisibility()==0 || cvvv.getVisibility()==0 || ivb.getVisibility()==0 || ivb1.getVisibility()==0) //0 stands for visible
                                dialog.setContentView(R.layout.msglayout_delete3);
                            else
                                dialog.setContentView(R.layout.msglayout_delete2);

                        }
                        else
                        {
                            //0 or 1
                            ImageView ivb = (ImageView) view.findViewById(R.id.imageView19);
                            ImageView ivb1 = (ImageView) view.findViewById(R.id.imageView17);

                            if(cvv.getVisibility()==0 || cvvv.getVisibility()==0 || ivb.getVisibility()==0 || ivb1.getVisibility()==0) //0 stands for visible
                                dialog.setContentView(R.layout.msglayout_delete1);
                            else
                                dialog.setContentView(R.layout.msglayout_delete);
                        }


                        dialog.setTitle("Choose:");

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        Button BdeleteForMe = (Button) dialog.findViewById(R.id.button15);
                        Button BdeleteForEveryone = (Button) dialog.findViewById(R.id.button10);
                        Button Bedit = (Button) dialog.findViewById(R.id.button66);
                        Button Bhistory = (Button) dialog.findViewById(R.id.button67);
                        Button Bforward = (Button) dialog.findViewById(R.id.button68);
                        TextView Ttotalshare = (TextView) dialog.findViewById(R.id.textView71);
                        Button Bcopy = (Button) dialog.findViewById(R.id.button84);

                        /*
                        BdeleteForMe.setHeight(300);
                        BdeleteForEveryone.setMaxHeight(10);
                        Bedit.setHeight(100);
                        Bhistory.setHeight(100);
                        Bforward.setHeight(100);
                        Ttotalshare.setHeight(100);
                        Bcopy.setHeight(100);
                        */

                        Bhistory.setVisibility(View.VISIBLE);
                        Bforward.setVisibility(View.VISIBLE);

                        state = "";
                        msg = "";
                        date = "";

                        if(cvv.getVisibility()==0) //0 stands for visible
                        {
                            BdeleteForEveryone.setVisibility(View.INVISIBLE);
                            msg = userNameViewHim1.getText().toString();
                            date = userNameViewHim.getText().toString();
                            state = "Him";
                        }
                        if(cvvv.getVisibility()==0) //0 stands for visible
                         {
                            BdeleteForEveryone.setVisibility(View.VISIBLE);
                             msg = userNameViewMe1.getText().toString();
                             date = userNameViewMe.getText().toString();
                            state = "Me";
                        }
                        if(userNameViewHim1.getVisibility()==0) //0 stands for visible
                        {
                            BdeleteForEveryone.setVisibility(View.INVISIBLE);
                            Bedit.setVisibility(View.INVISIBLE);
                            Ttotalshare.setVisibility(View.INVISIBLE);
                            msg = userNameViewHim1.getText().toString();
                            date = userNameViewHim.getText().toString();
                            state = "Him";
                        }
                        if(userNameViewMe1.getVisibility()==0) //0 stands for visible
                        {
                            Bedit.setVisibility(View.VISIBLE);
                            Ttotalshare.setVisibility(View.VISIBLE);
                            BdeleteForEveryone.setVisibility(View.VISIBLE);
                            msg = userNameViewMe1.getText().toString();
                            date = userNameViewMe.getText().toString();
                            state = "Me";
                        }



                        if(!msg.equals("Message deleted..."))
                            view.setBackgroundColor(Color.rgb(200,200,200));

                        if(!msg.equals("Message deleted..."))
                        dialog.show();


                        ImageView ivb = (ImageView) view.findViewById(R.id.imageView19);
                        ImageView ivb1 = (ImageView) view.findViewById(R.id.imageView17);

                        if(cvv.getVisibility()==0 || cvvv.getVisibility()==0 || ivb.getVisibility()==0 || ivb1.getVisibility()==0) //0 stands for visible
                        {
                             Bedit.setVisibility(View.INVISIBLE); ;
                             Bhistory.setVisibility(View.INVISIBLE) ;
                            Bcopy.setVisibility(View.INVISIBLE) ;
                        }

                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                         @Override
                         public void onCancel(DialogInterface dialog) {

                             SharedPreferences settings8 = PreferenceManager.getDefaultSharedPreferences(context);
                             String backcolor = settings8.getString(user_id+"_color", "c4");  /// 0 is default if variable not found
                             if(backcolor.equals("c1"))
                             {view.setBackgroundColor(Color.rgb(243,45,45));}
                             if(backcolor.equals("c2"))
                             {view.setBackgroundColor(Color.rgb(221,255,221));}
                             if(backcolor.equals("c3"))
                             {view.setBackgroundColor(Color.rgb(67,67,222));}
                             if(backcolor.equals("c4"))
                             {view.setBackgroundColor(Color.rgb(223,223,223));}
                             if(backcolor.equals("c5"))
                             {view.setBackgroundColor(Color.rgb(240,154,80));}

                            }
                         });


                        mForward= FirebaseDatabase.getInstance().getReference().child("Forward");
                        mForward.keepSynced(true);

                        mForward.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild(uuser_id))
                                {
                                    mForward.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshota) {
                                            if(dataSnapshota.hasChild( model.getFrom_orignal_key()))
                                            {
                                                mForward.child(uuser_id).child( model.getFrom_orignal_key()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshotss) {

                                                        Ttotalshare.setText(dataSnapshotss.child("forward").getValue(String.class) +" forward\nin total");

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

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        Bcopy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                                String text;
                                text = msg;

                                myClipboard.setPrimaryClip(ClipData.newPlainText("text", text));

                                Toast.makeText(getApplicationContext(), "Text Copied",Toast.LENGTH_SHORT).show();
                            }
                        });

                        Bhistory.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        dialog.cancel();
        dialog.dismiss();

        final Dialog dialogss = new Dialog(activity_chats.this);
        dialogss.setContentView(R.layout.msglayout_info);
        dialogss.setTitle("Choose:");

        dialogss.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogss.show();


        TextView T1 = (TextView) dialogss.findViewById(R.id.textView48);
        TextView T2 = (TextView) dialogss.findViewById(R.id.textView47);
        Button Bback = (Button) dialogss.findViewById(R.id.button10);

        T1.setText("Message history");

        Bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogss.dismiss();
            }
        });



        if(userNameViewHim1.getVisibility()==0) //0 stands for visible
        {

            mHistoryDatabase.child(user_id).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    String lo = "";

                    for (DataSnapshot childz : children) {
                        if ( childz.getValue(History.class).getKeyUser().toString().equals(message_key)) {

                            String date2 = childz.child("dateTime").getValue(String.class);

                            String y = date2.substring(0, 4);
                            String m = date2.substring(5, 7);
                            String d = date2.substring(8, 10);

                            String dd = y + "-" + m + "-" + d;

                            String time = date2.substring(10);

                            String hour = date2.substring(10, date2.indexOf(':'));
                            String min = date2.substring(date2.indexOf(':') + 1);


                            String r = "";

                            if (m.equals("01")) m = "Jan";
                            if (m.equals("02")) m = "Feb";
                            if (m.equals("03")) m = "Mar";
                            if (m.equals("04")) m = "Apr";
                            if (m.equals("05")) m = "May";
                            if (m.equals("06")) m = "June";
                            if (m.equals("07")) m = "July";
                            if (m.equals("08")) m = "Aug";
                            if (m.equals("09")) m = "Sep";
                            if (m.equals("10")) m = "Oct";
                            if (m.equals("11")) m = "Nov";
                            if (m.equals("12")) m = "Dec";

                            r = m + " " + d + ", " + y;

                            String dateInString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar c = Calendar.getInstance();
                            try {
                                c.setTime(sdf.parse(dateInString));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            c.add(Calendar.DATE, 0);
                            Date resultdate;
                            resultdate = new Date(c.getTimeInMillis());
                            String s = sdf.format(resultdate);

                            date2 = time + ", " + r;

                            lo = lo + date2 + "<br>" + childz.child("note_msg").getValue(String.class)+"<br><br>" ;
                        }

                    }
                    if(lo.equals("")) lo="No history";
                    T2.setText(Html.fromHtml(lo));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
        {
            mHistoryDatabase.child(uuser_id).child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshotv) {
                    Iterable<DataSnapshot> childrenv = dataSnapshotv.getChildren();

                    String lo = "";

                    for (DataSnapshot childz : childrenv) {
                        if ( childz.getValue(History.class).getKeyCurrent().toString().equals(message_key)) {


                            String date2 = childz.child("dateTime").getValue(String.class);

                            String y = date2.substring(0, 4);
                            String m = date2.substring(5, 7);
                            String d = date2.substring(8, 10);

                            String dd = y + "-" + m + "-" + d;

                            String time = date2.substring(10);

                            String hour = date2.substring(10, date2.indexOf(':'));
                            String min = date2.substring(date2.indexOf(':') + 1);


                            String r = "";

                            if (m.equals("01")) m = "Jan";
                            if (m.equals("02")) m = "Feb";
                            if (m.equals("03")) m = "Mar";
                            if (m.equals("04")) m = "Apr";
                            if (m.equals("05")) m = "May";
                            if (m.equals("06")) m = "June";
                            if (m.equals("07")) m = "July";
                            if (m.equals("08")) m = "Aug";
                            if (m.equals("09")) m = "Sep";
                            if (m.equals("10")) m = "Oct";
                            if (m.equals("11")) m = "Nov";
                            if (m.equals("12")) m = "Dec";

                            r = m + " " + d + ", " + y;

                            String dateInString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar c = Calendar.getInstance();
                            try {
                                c.setTime(sdf.parse(dateInString));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            c.add(Calendar.DATE, 0);
                            Date resultdate;
                            resultdate = new Date(c.getTimeInMillis());
                            String s = sdf.format(resultdate);

                            date2 = time + ", " + r;

                        lo = lo + date2 + "<br>" + childz.child("note_msg").getValue(String.class)+"<br><br>" ;
                        }

                    }
                    if(lo.equals("")) lo="No history";
                    T2.setText(Html.fromHtml(lo));

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
});

                        Bforward.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, forward_msg.class);
                                intent.putExtra("forward_dateTime", model.getdateTime());
                                intent.putExtra("forward_from", model.getFrom());
                                intent.putExtra("forward_msg_or_url", model.getMsg_or_url());
                                intent.putExtra("forward_msg_type", model.getMsg_type());
                                intent.putExtra("forward_sent_received_seen", model.getSent_received_seen());
                                intent.putExtra("forward_from_orignal_id", model.getFrom_orignal_id());
                                intent.putExtra("forward_from_orignal_key", model.getFrom_orignal_key());
                                startActivity(intent);
                            }
                        });

                        Bedit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                                dialog.dismiss();

                                final Dialog dialogs = new Dialog(activity_chats.this);
                                dialogs.setContentView(R.layout.msglayout_edit);
                                dialogs.setTitle("Choose:");

                                dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialogs.show();

                                EditText Eedit = (EditText) dialogs.findViewById(R.id.editText10);
                                Button Beditsave = (Button) dialogs.findViewById(R.id.button10);

                                Eedit.setText(Html.fromHtml( model.getMsg_or_url()));

                                Beditsave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(Eedit.getText().toString().equals(""))
                                        {
                                            Toast.makeText(activity_chats.this, "Enter a message", Toast.LENGTH_LONG).show();
                                            return;
                                        }

                                        mChatDatabase.child(user_id).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                                String ke = "";
                                                for (DataSnapshot child : children) {


                                                    if (child.getValue(Chats.class).getFrom().toString().equals(uuser_id)
                                                            && child.getValue(Chats.class).getMsg_or_url().toString().equals(model.getMsg_or_url())
                                                            && child.getValue(Chats.class).getdateTime().toString().equals(model.getdateTime())) {

                                                        ke=child.getKey();
                                                    }
                                                }

                                                    HashMap<String, String> object2 = new HashMap<String, String>();
                                                    object2.put("from", uuser_id);
                                                    object2.put("note_msg", model.getMsg_or_url());
                                                    object2.put("dateTime", model.getdateTime());
                                                    object2.put("keyCurrent", message_key); //  sent , received, seen
                                                    object2.put("keyUser", ke); //  sent , received, seen



                                                    mHistoryDatabase.child(uuser_id).child(user_id).push().setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {


                                                            String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                            Calendar c = Calendar.getInstance();
                                                            try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                            c.add(Calendar.DATE, 0);
                                                            Date resultdate;
                                                            resultdate = new Date(c.getTimeInMillis());
                                                            String s =sdf.format(resultdate);

                                                            Calendar rightNow = Calendar.getInstance();
                                                            int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                            int currentmin = rightNow.get(Calendar.MINUTE); //

                                                            String ffp = String.valueOf(currentmin).toString();
                                                            if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

                                                            String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                            if(hour.length()<5) hour = "0"+hour;
                                                            String DateTime = s +" "+ hour;

                                                            mChatDatabase.child(user_id).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                                                    for (DataSnapshot child : children) {

                                                                        if (child.getValue(Chats.class).getFrom().toString().equals(uuser_id)
                                                                                && child.getValue(Chats.class).getMsg_or_url().toString().equals(model.getMsg_or_url())
                                                                                && child.getValue(Chats.class).getdateTime().toString().equals(model.getdateTime())) {

                                                                            HashMap<String, Object> resultm1h = new HashMap<>();
                                                                            resultm1h.put("msg_or_url",Eedit.getText().toString());
                                                                            resultm1h.put("dateTime",DateTime);

                                                                            mChatDatabase.child(user_id).child(uuser_id).child(child.getKey()).updateChildren(resultm1h).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    dialogs.dismiss();
                                                                                }
                                                                            });
                                                                        }
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });


                                                            HashMap<String, Object> resultm1 = new HashMap<>();
                                                            resultm1.put("msg_or_url",Eedit.getText().toString());
                                                            resultm1.put("dateTime",DateTime);

                                                            mChatDatabase.child(uuser_id).child(user_id).child(message_key).updateChildren(resultm1);
                                                        }
                                                    });



                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });



                                    }
                                });
                             //   mHistoryDatabase


                            }
                        });

                        BdeleteForMe.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View vieww) {
                                view.setBackgroundColor(Color.rgb(223,223,223));

                                if(state == "Me") {
                                    mChatDatabase.child(uuser_id).child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                            for (DataSnapshot child : children) {

                                                if (child.getValue(Chats.class).getFrom().toString().equals(uuser_id)) {
                                                    String date2 = child.getValue(Chats.class).getdateTime().toString();

                                                    String y = date2.substring(0, 4);
                                                    String m = date2.substring(5, 7);
                                                    String d = date2.substring(8, 10);

                                                    String dd = y + "-" + m + "-" + d;

                                                    String time = date2.substring(10);

                                                    String hour = date2.substring(10, date2.indexOf(':'));
                                                    String min = date2.substring(date2.indexOf(':') + 1);


                                                    String r = "";

                                                    if (m.equals("01")) m = "Jan";
                                                    if (m.equals("02")) m = "Feb";
                                                    if (m.equals("03")) m = "Mar";
                                                    if (m.equals("04")) m = "Apr";
                                                    if (m.equals("05")) m = "May";
                                                    if (m.equals("06")) m = "June";
                                                    if (m.equals("07")) m = "July";
                                                    if (m.equals("08")) m = "Aug";
                                                    if (m.equals("09")) m = "Sep";
                                                    if (m.equals("10")) m = "Oct";
                                                    if (m.equals("11")) m = "Nov";
                                                    if (m.equals("12")) m = "Dec";

                                                    r = m + " " + d + ", " + y;

                                                    String dateInString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                    Calendar c = Calendar.getInstance();
                                                    try {
                                                        c.setTime(sdf.parse(dateInString));
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    c.add(Calendar.DATE, 0);
                                                    Date resultdate;
                                                    resultdate = new Date(c.getTimeInMillis());
                                                    String s = sdf.format(resultdate);

                                                    if (s.equals(dd)) r = "today";
                                                    // if(user_id.equals(uuser_id)) Bchat.setVisibility(View.INVISIBLE);

                                                    date2 = time + ", " + r;

                                                    String str = child.getValue(Chats.class).getMsg_or_url().toString();

                                                    str = str.replace("<b>", "");
                                                    str = str.replace("</b>", "");
                                                    str = str.replace("<u>", "");
                                                    str = str.replace("</u>", "");
                                                    str = str.replace("<i>", "");
                                                    str = str.replace("</i>", "");
                                                    str = str.replace("</font>", "");
                                                    str = str.replace("<font color=\"black\">", "");
                                                    str = str.replace("<font color=\"blue\">", "");
                                                    str = str.replace("<font color=\"red\">", "");
                                                    str = str.replace("<font color=\"gray\">", "");
                                                    str = str.replace("<font color=\"white\">", "");
                                                    str = str.replace("<font color=\"green\">", "");
                                                    str = str.replace("                     ", " ");
                                                    str = str.replace("                    ", " ");
                                                    str = str.replace("                   ", " ");
                                                    str = str.replace("                  ", " ");
                                                    str = str.replace("                 ", " ");
                                                    str = str.replace("                ", " ");
                                                    str = str.replace("               ", " ");
                                                    str = str.replace("              ", " ");
                                                    str = str.replace("             ", " ");
                                                    str = str.replace("            ", " ");
                                                    str = str.replace("           ", " ");
                                                    str = str.replace("          ", " ");
                                                    str = str.replace("         ", " ");
                                                    str = str.replace("        ", " ");
                                                    str = str.replace("       ", " ");
                                                    str = str.replace("      ", " ");
                                                    str = str.replace("     ", " ");
                                                    str = str.replace("    ", " ");
                                                    str = str.replace("   ", " ");
                                                    str = str.replace("  ", " ");

                                                   // Toast.makeText(activity_chats.this, "Deletedf!", Toast.LENGTH_LONG).show();

                                                    if (str.equals(msg) && date2.equals(date)) {
                                                        mChatDatabase.child(uuser_id).child(user_id).child(child.getKey()).removeValue();
                                                        Toast.makeText(activity_chats.this, "Deleted!", Toast.LENGTH_LONG).show();
                                                        // firebaseRecyclerAdapter.notifyDataSetChanged();
                                                    }
                                                }

                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                                else
                                {

                                    mChatDatabase.child(uuser_id).child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {


                                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                            for (DataSnapshot child : children) {

                                                if (child.getValue(Chats.class).getFrom().toString().equals(user_id)) {
                                                    String date2 = child.getValue(Chats.class).getdateTime().toString();

                                                    String y = date2.substring(0, 4);
                                                    String m = date2.substring(5, 7);
                                                    String d = date2.substring(8, 10);

                                                    String dd = y + "-" + m + "-" + d;

                                                    String time = date2.substring(10);

                                                    String hour = date2.substring(10, date2.indexOf(':'));
                                                    String min = date2.substring(date2.indexOf(':') + 1);


                                                    String r = "";

                                                    if (m.equals("01")) m = "Jan";
                                                    if (m.equals("02")) m = "Feb";
                                                    if (m.equals("03")) m = "Mar";
                                                    if (m.equals("04")) m = "Apr";
                                                    if (m.equals("05")) m = "May";
                                                    if (m.equals("06")) m = "June";
                                                    if (m.equals("07")) m = "July";
                                                    if (m.equals("08")) m = "Aug";
                                                    if (m.equals("09")) m = "Sep";
                                                    if (m.equals("10")) m = "Oct";
                                                    if (m.equals("11")) m = "Nov";
                                                    if (m.equals("12")) m = "Dec";

                                                    r = m + " " + d + ", " + y;

                                                    String dateInString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                    Calendar c = Calendar.getInstance();
                                                    try {
                                                        c.setTime(sdf.parse(dateInString));
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    c.add(Calendar.DATE, 0);
                                                    Date resultdate;
                                                    resultdate = new Date(c.getTimeInMillis());
                                                    String s = sdf.format(resultdate);

                                                    if (s.equals(dd)) r = "today";
                                                    // if(user_id.equals(uuser_id)) Bchat.setVisibility(View.INVISIBLE);

                                                    date2 = time + ", " + r;

                                                    String str = child.getValue(Chats.class).getMsg_or_url().toString();

                                                    str = str.replace("<b>", "");
                                                    str = str.replace("</b>", "");
                                                    str = str.replace("<u>", "");
                                                    str = str.replace("</u>", "");
                                                    str = str.replace("<i>", "");
                                                    str = str.replace("</i>", "");
                                                    str = str.replace("</font>", "");
                                                    str = str.replace("<font color=\"black\">", "");
                                                    str = str.replace("<font color=\"blue\">", "");
                                                    str = str.replace("<font color=\"red\">", "");
                                                    str = str.replace("<font color=\"gray\">", "");
                                                    str = str.replace("<font color=\"white\">", "");
                                                    str = str.replace("<font color=\"green\">", "");
                                                    str = str.replace("                     ", " ");
                                                    str = str.replace("                    ", " ");
                                                    str = str.replace("                   ", " ");
                                                    str = str.replace("                  ", " ");
                                                    str = str.replace("                 ", " ");
                                                    str = str.replace("                ", " ");
                                                    str = str.replace("               ", " ");
                                                    str = str.replace("              ", " ");
                                                    str = str.replace("             ", " ");
                                                    str = str.replace("            ", " ");
                                                    str = str.replace("           ", " ");
                                                    str = str.replace("          ", " ");
                                                    str = str.replace("         ", " ");
                                                    str = str.replace("        ", " ");
                                                    str = str.replace("       ", " ");
                                                    str = str.replace("      ", " ");
                                                    str = str.replace("     ", " ");
                                                    str = str.replace("    ", " ");
                                                    str = str.replace("   ", " ");
                                                    str = str.replace("  ", " ");

                                                 //   Toast.makeText(activity_chats.this, "Deleteds!", Toast.LENGTH_LONG).show();

                                                    if (str.equals(msg) && date2.equals(date)) {
                                                        mChatDatabase.child(uuser_id).child(user_id).child(child.getKey()).removeValue();
                                                        Toast.makeText(activity_chats.this, "Deleted!", Toast.LENGTH_LONG).show();
                                                        // firebaseRecyclerAdapter.notifyDataSetChanged();
                                                    }
                                                }

                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }


                                dialog.dismiss();
                            }
                        });

                        BdeleteForEveryone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View vieww) {
                                view.setBackgroundColor(Color.rgb(223,223,223));


                                    mChatDatabase.child(uuser_id).child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                            for (DataSnapshot child : children) {

                                                if (child.getValue(Chats.class).getFrom().toString().equals(uuser_id)) {
                                                    String date2 = child.getValue(Chats.class).getdateTime().toString();

                                                    String y = date2.substring(0, 4);
                                                    String m = date2.substring(5, 7);
                                                    String d = date2.substring(8, 10);

                                                    String dd = y + "-" + m + "-" + d;

                                                    String time = date2.substring(10);

                                                    String hour = date2.substring(10, date2.indexOf(':'));
                                                    String min = date2.substring(date2.indexOf(':') + 1);


                                                    String r = "";

                                                    if (m.equals("01")) m = "Jan";
                                                    if (m.equals("02")) m = "Feb";
                                                    if (m.equals("03")) m = "Mar";
                                                    if (m.equals("04")) m = "Apr";
                                                    if (m.equals("05")) m = "May";
                                                    if (m.equals("06")) m = "June";
                                                    if (m.equals("07")) m = "July";
                                                    if (m.equals("08")) m = "Aug";
                                                    if (m.equals("09")) m = "Sep";
                                                    if (m.equals("10")) m = "Oct";
                                                    if (m.equals("11")) m = "Nov";
                                                    if (m.equals("12")) m = "Dec";

                                                    r = m + " " + d + ", " + y;

                                                    String dateInString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                    Calendar c = Calendar.getInstance();
                                                    try {
                                                        c.setTime(sdf.parse(dateInString));
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    c.add(Calendar.DATE, 0);
                                                    Date resultdate;
                                                    resultdate = new Date(c.getTimeInMillis());
                                                    String s = sdf.format(resultdate);

                                                    if (s.equals(dd)) r = "today";
                                                    // if(user_id.equals(uuser_id)) Bchat.setVisibility(View.INVISIBLE);

                                                    date2 = time + ", " + r;

                                                    String str = child.getValue(Chats.class).getMsg_or_url().toString();

                                                    str = str.replace("<b>", "");
                                                    str = str.replace("</b>", "");
                                                    str = str.replace("<u>", "");
                                                    str = str.replace("</u>", "");
                                                    str = str.replace("<i>", "");
                                                    str = str.replace("</i>", "");
                                                    str = str.replace("</font>", "");
                                                    str = str.replace("<font color=\"black\">", "");
                                                    str = str.replace("<font color=\"blue\">", "");
                                                    str = str.replace("<font color=\"red\">", "");
                                                    str = str.replace("<font color=\"gray\">", "");
                                                    str = str.replace("<font color=\"white\">", "");
                                                    str = str.replace("<font color=\"green\">", "");
                                                    str = str.replace("                     ", " ");
                                                    str = str.replace("                    ", " ");
                                                    str = str.replace("                   ", " ");
                                                    str = str.replace("                  ", " ");
                                                    str = str.replace("                 ", " ");
                                                    str = str.replace("                ", " ");
                                                    str = str.replace("               ", " ");
                                                    str = str.replace("              ", " ");
                                                    str = str.replace("             ", " ");
                                                    str = str.replace("            ", " ");
                                                    str = str.replace("           ", " ");
                                                    str = str.replace("          ", " ");
                                                    str = str.replace("         ", " ");
                                                    str = str.replace("        ", " ");
                                                    str = str.replace("       ", " ");
                                                    str = str.replace("      ", " ");
                                                    str = str.replace("     ", " ");
                                                    str = str.replace("    ", " ");
                                                    str = str.replace("   ", " ");
                                                    str = str.replace("  ", " ");


                                                    if (str.equals(msg)
                                                            && date2.equals(date)) {

                                                        HashMap<String, Object> resultm1 = new HashMap<>();
                                                        resultm1.put("msg_or_url","Message deleted...");

                                                        mChatDatabase.child(uuser_id).child(user_id).child(child.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                if(dataSnapshot.child("sent_received_seen").getValue(String.class).toString().equals("received"))
                                                                {
                                                                    mChatDatabase.child(uuser_id).child(user_id).child(child.getKey()).updateChildren(resultm1);
                                                                }
                                                                else
                                                                {
                                                                    mChatDatabase.child(uuser_id).child(user_id).child(child.getKey()).removeValue();
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });


                                                        mChatDatabase.child(user_id).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                                                for (DataSnapshot child : children) {

                                                                    if (child.getValue(Chats.class).getFrom().toString().equals(uuser_id)) {
                                                                        String date2 = child.getValue(Chats.class).getdateTime().toString();

                                                                        String y = date2.substring(0, 4);
                                                                        String m = date2.substring(5, 7);
                                                                        String d = date2.substring(8, 10);

                                                                        String dd = y + "-" + m + "-" + d;

                                                                        String time = date2.substring(10);

                                                                        String hour = date2.substring(10, date2.indexOf(':'));
                                                                        String min = date2.substring(date2.indexOf(':') + 1);


                                                                        String r = "";

                                                                        if (m.equals("01")) m = "Jan";
                                                                        if (m.equals("02")) m = "Feb";
                                                                        if (m.equals("03")) m = "Mar";
                                                                        if (m.equals("04")) m = "Apr";
                                                                        if (m.equals("05")) m = "May";
                                                                        if (m.equals("06")) m = "June";
                                                                        if (m.equals("07")) m = "July";
                                                                        if (m.equals("08")) m = "Aug";
                                                                        if (m.equals("09")) m = "Sep";
                                                                        if (m.equals("10")) m = "Oct";
                                                                        if (m.equals("11")) m = "Nov";
                                                                        if (m.equals("12")) m = "Dec";

                                                                        r = m + " " + d + ", " + y;

                                                                        String dateInString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                                        Calendar c = Calendar.getInstance();
                                                                        try {
                                                                            c.setTime(sdf.parse(dateInString));
                                                                        } catch (ParseException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                        c.add(Calendar.DATE, 0);
                                                                        Date resultdate;
                                                                        resultdate = new Date(c.getTimeInMillis());
                                                                        String s = sdf.format(resultdate);

                                                                        if (s.equals(dd)) r = "today";
                                                                        // if(user_id.equals(uuser_id)) Bchat.setVisibility(View.INVISIBLE);

                                                                        date2 = time + ", " + r;

                                                                        String str = child.getValue(Chats.class).getMsg_or_url().toString();

                                                                        str = str.replace("<b>", "");
                                                                        str = str.replace("</b>", "");
                                                                        str = str.replace("<u>", "");
                                                                        str = str.replace("</u>", "");
                                                                        str = str.replace("<i>", "");
                                                                        str = str.replace("</i>", "");
                                                                        str = str.replace("</font>", "");
                                                                        str = str.replace("<font color=\"black\">", "");
                                                                        str = str.replace("<font color=\"blue\">", "");
                                                                        str = str.replace("<font color=\"red\">", "");
                                                                        str = str.replace("<font color=\"gray\">", "");
                                                                        str = str.replace("<font color=\"white\">", "");
                                                                        str = str.replace("<font color=\"green\">", "");
                                                                        str = str.replace("                     ", " ");
                                                                        str = str.replace("                    ", " ");
                                                                        str = str.replace("                   ", " ");
                                                                        str = str.replace("                  ", " ");
                                                                        str = str.replace("                 ", " ");
                                                                        str = str.replace("                ", " ");
                                                                        str = str.replace("               ", " ");
                                                                        str = str.replace("              ", " ");
                                                                        str = str.replace("             ", " ");
                                                                        str = str.replace("            ", " ");
                                                                        str = str.replace("           ", " ");
                                                                        str = str.replace("          ", " ");
                                                                        str = str.replace("         ", " ");
                                                                        str = str.replace("        ", " ");
                                                                        str = str.replace("       ", " ");
                                                                        str = str.replace("      ", " ");
                                                                        str = str.replace("     ", " ");
                                                                        str = str.replace("    ", " ");
                                                                        str = str.replace("   ", " ");
                                                                        str = str.replace("  ", " ");


                                                                        if (str.equals(msg)
                                                                                && date2.equals(date)) {

                                                                            HashMap<String, Object> resultm2 = new HashMap<>();
                                                                            resultm2.put("msg_or_url","Message deleted...");

                                                                            mChatDatabase.child(user_id).child(uuser_id).child(child.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                    if(dataSnapshot.child("sent_received_seen").getValue(String.class).toString().equals("received"))
                                                                                    {
                                                                                        mChatDatabase.child(user_id).child(uuser_id).child(child.getKey()).updateChildren(resultm1);
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        mChatDatabase.child(user_id).child(uuser_id).child(child.getKey()).removeValue();
                                                                                    }
                                                                                    Toast.makeText(activity_chats.this, "Deleted!", Toast.LENGTH_LONG).show();
                                                                                }

                                                                                @Override
                                                                                public void onCancelled(DatabaseError databaseError) {

                                                                                }
                                                                            });

                                                                            mChatDatabase.child(user_id).child(uuser_id).child(child.getKey()).updateChildren(resultm2);


                                                                            // firebaseRecyclerAdapter.notifyDataSetChanged();
                                                                        }
                                                                    }

                                                                }

                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }

                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });


                                dialog.dismiss();
                                /*
                                Intent startIntent = new Intent(publics.this, activity_chats.class);
                                startIntent.putExtra("user_id", user_id);
                                startActivity(startIntent);
                                */

                            }
                        });





                        return false;
                    }
/*
                    @Override
                    public void onClick(View view) {

                        final Dialog dialog = new Dialog(publics.this);
                        dialog.setContentView(R.layout.msglayout_publics);
                        dialog.setTitle("Choose:");

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        Button Bprofile = (Button) dialog.findViewById(R.id.button15);
                        Button Bchat = (Button) dialog.findViewById(R.id.button10);
                        TextView Tname = (TextView) dialog.findViewById(R.id.textView16);

                        Tname.setText(model.getName());

                        if(user_id.equals(uuser_id)) Bchat.setVisibility(View.INVISIBLE);

                        dialog.show();

                        Bprofile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               // Intent startIntent = new Intent(publics.this, ViewProfile.class);
                              //  startIntent.putExtra("user_id", user_id);
                               // startActivity(startIntent);
                            }
                        });

                        Bchat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent startIntent = new Intent(publics.this, activity_chats.class);
                                startIntent.putExtra("user_id", user_id);
                                startActivity(startIntent);

                            }
                        });


                    }
                    */
                });

            }



/*
             @Override
             public int getItemViewType(int position)
             {
                 return getItemCount();
             }

             @Override
             public long getItemId(int position)
             {
                 return position;
             }
*/

        };


        firebaseRecyclerAdapter.notifyDataSetChanged();
        mChatList.setAdapter(firebaseRecyclerAdapter);



        firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {

                mChatList.smoothScrollToPosition(firebaseRecyclerAdapter.getItemCount());
                firebaseRecyclerAdapter.notifyDataSetChanged();
             //   mLinearLayoutManager.scrollToPositionWithOffset (firebaseRecyclerAdapter.getItemCount(),0);
              //  mChatList.scrollTo(0,firebaseRecyclerAdapter.getItemCount());
            }
        });

       // mChatList.smoothScrollToPosition(firebaseRecyclerAdapter.getItemCount() );

    }

     String user_id = "";
String msg = "";
    String date = "";
    String   state = "";

    private SensorManager mSensorManager;
    private Sensor mProximity;
    private static final int SENSOR_SENSITIVITY = 2;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                //near
              //  Toast.makeText(getApplicationContext(), "near", Toast.LENGTH_SHORT).show();
            } else {
                //far
              //  Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public static class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener
    {
        private static final String TAG = "Touch";
        @SuppressWarnings("unused")
        private static final float MIN_ZOOM = 1f,MAX_ZOOM = 1f;

        private static MediaPlayer mediaPlayer = new MediaPlayer();
        private static  Timer timers = new Timer();
        // These matrices will be used to scale points of the image
        Matrix matrix = new Matrix();
        Matrix savedMatrix = new Matrix();

        // The 3 states (events) which the user is trying to perform
        static final int NONE = 0;
        static final int DRAG = 1;
        static final int ZOOM = 2;
        int mode = NONE;

        // these PointF objects are used to record the point(s) the user is touching
        PointF start2 = new PointF();
        PointF mid = new PointF();
        float oldDist = 1f;

        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            ImageView view = (ImageView) v;
            view.setScaleType(ImageView.ScaleType.MATRIX);
            float scale;

            dumpEvent(event);
            // Handle touch events here...

            switch (event.getAction() & MotionEvent.ACTION_MASK)
            {
                case MotionEvent.ACTION_DOWN:   // first finger down only
                    savedMatrix.set(matrix);
                    start2.set(event.getX(), event.getY());
                    Log.d(TAG, "mode=DRAG"); // write to LogCat
                    mode = DRAG;
                    break;

                case MotionEvent.ACTION_UP: // first finger lifted

                case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                    mode = NONE;
                    Log.d(TAG, "mode=NONE");
                    break;

                case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                    oldDist = spacing(event);
                    Log.d(TAG, "oldDist=" + oldDist);
                    if (oldDist > 5f) {
                        savedMatrix.set(matrix);
                        midPoint(mid, event);
                        mode = ZOOM;
                        Log.d(TAG, "mode=ZOOM");
                    }
                    break;

                case MotionEvent.ACTION_MOVE:

                    if (mode == DRAG)
                    {
                        matrix.set(savedMatrix);
                        matrix.postTranslate(event.getX() - start2.x, event.getY() - start2.y); // create the transformation in the matrix  of points
                    }
                    else if (mode == ZOOM)
                    {
                        // pinch zooming
                        float newDist = spacing(event);
                        Log.d(TAG, "newDist=" + newDist);
                        if (newDist > 5f)
                        {
                            matrix.set(savedMatrix);
                            scale = newDist / oldDist; // setting the scaling of the
                            // matrix...if scale > 1 means
                            // zoom in...if scale < 1 means
                            // zoom out
                            matrix.postScale(scale, scale, mid.x, mid.y);
                        }
                    }
                    break;
            }

            view.setImageMatrix(matrix); // display the transformation on screen

            return true; // indicate event was handled
        }

    /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

        private float spacing(MotionEvent event)
        {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float) Math.sqrt(x * x + y * y);
        }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

        private void midPoint(PointF point, MotionEvent event)
        {
            float x = event.getX(0) + event.getX(1);
            float y = event.getY(0) + event.getY(1);
            point.set(x / 2, y / 2);
        }

        /** Show an event in the LogCat view, for debugging */
        private void dumpEvent(MotionEvent event)
        {
            String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
            StringBuilder sb = new StringBuilder();
            int action = event.getAction();
            int actionCode = action & MotionEvent.ACTION_MASK;
            sb.append("event ACTION_").append(names[actionCode]);

            if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
            {
                sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
                sb.append(")");
            }

            sb.append("[");
            for (int i = 0; i < event.getPointerCount(); i++)
            {
                sb.append("#").append(i);
                sb.append("(pid ").append(event.getPointerId(i));
                sb.append(")=").append((int) event.getX(i));
                sb.append(",").append((int) event.getY(i));
                if (i + 1 < event.getPointerCount())
                    sb.append(";");
            }

            sb.append("]");
            Log.d("Touch Events ---------", sb.toString());
        }

        View mView;
        ImageView IVprofile;
        int counth=0;


        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


        }


        private static void scanFile(Context context, Uri imageUri){
            Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            scanIntent.setData(imageUri);
            context.sendBroadcast(scanIntent);

        }

        public void setdatetime(String datetime,
                                final String userID,
                                final String from,
                                int ccount,
                                final Context context,
                                String userImage,
                                String Msg_or_url,
                                String   Msg_type,
                                String Sent_received_seen,
                                final Context con,
                                Uri uriii,
                                ContentResolver CR
                                )
        {




            TextView userNameViewHim1 = mView.findViewById(R.id.chat_msg);
            TextView userNameViewMe1 = mView.findViewById(R.id.textView31);



            userNameViewHim1.setText("");
            userNameViewMe1.setText("");

            TextView userNameViewFromHim = mView.findViewById(R.id.textView62);
            TextView userNameViewFromMe = mView.findViewById(R.id.textView63);
            userNameViewFromHim.setVisibility(View.INVISIBLE);
            userNameViewFromMe.setVisibility(View.INVISIBLE);

            TextView userNameViewHim = mView.findViewById(R.id.chat_time);
            TextView userNameViewMe = mView.findViewById(R.id.textView33);
            ImageView IVHim = mView.findViewById(R.id.imageView13);
            ImageView IVMe = mView.findViewById(R.id.imageView18);
            IVHim.setVisibility(View.INVISIBLE);

            //"2018-09-041:35"
            // 0123456789

            String y = datetime.substring(0,4);
            String m = datetime.substring(5,7);
            String d = datetime.substring(8,10);

            String dd = y +"-"+m+"-"+d;

            String time = datetime.substring(10);

            String hour = datetime.substring(10,datetime.indexOf(':'));
            String min = datetime.substring(datetime.indexOf(':')+1);


            String r = "";

            if (m.equals("01")) m = "Jan";
            if (m.equals("02")) m = "Feb";
            if (m.equals("03")) m = "Mar";
            if (m.equals("04")) m = "Apr";
            if (m.equals("05")) m = "May";
            if (m.equals("06")) m = "June";
            if (m.equals("07")) m = "July";
            if (m.equals("08")) m = "Aug";
            if (m.equals("09")) m = "Sep";
            if (m.equals("10")) m = "Oct";
            if (m.equals("11")) m = "Nov";
            if (m.equals("12")) m = "Dec";

            r = m + " " + d + ", " + y;
/*
/////////////////////////////////////////////
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
         /////////////save////////////////
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("statepara1", ts);
            editor.commit();
         //////////////get//////////////////
            String ret = settings.getString("statepara1", "0");  /// 0 is default if variable not found
/////////////////////////////////////////////////////
*/
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            String ret = settings.getString("statepara1", "");

            TextView Tdate = mView.findViewById(R.id.textView2);
            if(!ret.equals(r))
            {
                Tdate.setText(r);
                Tdate.setVisibility(View.INVISIBLE);
            }
            else
            {
                Tdate.setVisibility(View.INVISIBLE);
            }

            SharedPreferences.Editor editor = settings.edit();
            editor.putString("statepara1", r);
            editor.commit();




            String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
            c.add(Calendar.DATE, 0);
            Date resultdate;
            resultdate = new Date(c.getTimeInMillis());
            String s =sdf.format(resultdate);

            if(s.equals(dd)) r = "today";

            Calendar rightNow = Calendar.getInstance();
            int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
            int currentmin = rightNow.get(Calendar.MINUTE); //
            String min2 = String.valueOf(currentmin).toString();
            if(currentmin<10) min2 = "0"+String.valueOf(currentmin).toString();
            int minc = Integer.parseInt(min2);


            if (from.equals(userID)) {
                userNameViewHim.setText(time +", " +r);
                userNameViewHim1.setText(Html.fromHtml(Msg_or_url));

                userNameViewMe.setVisibility(View.INVISIBLE);
                IVMe.setVisibility(View.INVISIBLE);
                userNameViewMe1.setVisibility(View.INVISIBLE);
                userNameViewHim.setVisibility(View.VISIBLE);

                userNameViewHim1.setVisibility(View.VISIBLE);
            }
            else
            {
                userNameViewMe.setText(time +", " +r);

                userNameViewMe1.setText(Html.fromHtml(Msg_or_url));

                if(Sent_received_seen.equals("received"))
                    IVMe.setBackgroundResource(R.drawable.ic_fiber_green);
                else
                    IVMe.setBackgroundResource(R.drawable.ic_fiber_gray);




                userNameViewMe.setVisibility(View.VISIBLE);
                IVMe.setVisibility(View.VISIBLE);
                userNameViewMe1.setVisibility(View.VISIBLE);
                userNameViewHim.setVisibility(View.INVISIBLE);
                IVHim.setVisibility(View.INVISIBLE);
                userNameViewHim1.setVisibility(View.INVISIBLE);
            }

            userNameViewMe1.setBackgroundResource(R.drawable.buttonchatt);
            userNameViewHim1.setBackgroundResource(R.drawable.buttonchattgray);

            ProgressBar PBc = mView.findViewById(R.id.progressBar2);
            PBc.setVisibility(View.INVISIBLE);
            ImageView IV1 = mView.findViewById(R.id.imageView17);
            ImageView IV2 = mView.findViewById(R.id.imageView19);
            IV1.setVisibility(View.INVISIBLE);
            IV2.setVisibility(View.INVISIBLE);

            userNameViewHim1.setTextSize(16);
            userNameViewMe1.setTextSize(16);

            final ProgressBar BprogMe = mView.findViewById(R.id.progressBar4);
            final ProgressBar BprogHim = mView.findViewById(R.id.progressBar3);
            final ProgressBar BprogMee = mView.findViewById(R.id.progressBar2);

            final LinearLayout Lhim = mView.findViewById(R.id.hj);
            final LinearLayout LMe = mView.findViewById(R.id.hjs);
            final Button Bhim = mView.findViewById(R.id.button75);
            final Button BMe = mView.findViewById(R.id.button75s);

            Lhim.setVisibility(View.INVISIBLE);
            LMe.setVisibility(View.INVISIBLE);

            if(!Msg_or_url.equals("Message deleted..."))
            {
            if (from.equals(userID)) {
                if (!Msg_type.equals("text"))
                    if (Msg_type.substring(0, 5).equals("image")) {

                        Lhim.setVisibility(View.VISIBLE);
                        LMe.setVisibility(View.INVISIBLE);

                        Bhim.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mView.performLongClick();
                            }
                        });

                        userNameViewHim1.setVisibility(View.VISIBLE);
                        userNameViewHim1.setText(Msg_or_url);
                        userNameViewHim1.setTextSize(20);

                        ImageView IV = mView.findViewById(R.id.imageView19);
                        IV.setVisibility(View.VISIBLE);
                        IV.setBackgroundResource(R.drawable.erff3);

                        Picasso.with(context)
                                .load(Msg_or_url)
                                .networkPolicy(NetworkPolicy.OFFLINE)
                                .into(IV, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                    }
                                    @Override
                                    public void onError() {
                                        // Try again online if cache failed
                                        Picasso.with(context)
                                                .load(Msg_or_url)
                                                .into(IV);
                                    }
                                });

                     //restore   Picasso.with(context).load(Msg_or_url).into(IV);



                        IV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                View IVview = (ImageView)view;

                                final Dialog dialog = new Dialog(con);
                                dialog.setContentView(R.layout.msglayout_image);
                                dialog.setTitle("View:");

                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                Button Bprofile = (Button) dialog.findViewById(R.id.button10);
                                 IVprofile = (ImageView) dialog.findViewById(R.id.imageView20);

                                ImageView IVpas = (ImageView) IVview;

                                counth = 0;

                                IVprofile.setRotation((float)counth);

                                Button Br = (Button) dialog.findViewById(R.id.button82);
                                Br.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        counth+=90;
                                        IVprofile.setRotation((float)counth);
                                       // dialog.dismiss();
                                    }
                                });

                                Button Bsave = (Button) dialog.findViewById(R.id.button69);
                                Bsave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        IVprofile.buildDrawingCache();
                                        Bitmap bm=IVprofile.getDrawingCache();




                                        OutputStream fOut = null;
                                        Uri outputFileUri;

                                        File root = new File(Environment.getExternalStorageDirectory()
                                                + File.separator + "BinTouch_Images" + File.separator);


                                            root.mkdirs();

                                            char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                                            StringBuilder sb = new StringBuilder(20);
                                            Random random = new Random();
                                            for (int i = 0; i < 20; i++) {
                                                char c2 = chars[random.nextInt(chars.length)];
                                                sb.append(c2);
                                            }
                                            String output = sb.toString()+".jpg";

                                            File sdImageMainDirectory = new File(root, output);
                                            outputFileUri = Uri.fromFile(sdImageMainDirectory);
                                        try {
                                            fOut = new FileOutputStream(sdImageMainDirectory);
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }

                                        try {
                                            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                            fOut.flush();
                                            fOut.close();
                                            Bsave.setText("saved");
                                        } catch (Exception e) {
                                            Bsave.setText("error");
                                        }

                                        scanFile(context, Uri.fromFile(sdImageMainDirectory));


/*
                                        File file;
                                        String dirPath, fileName;
                                        // Initialization Of DownLoad Button
                                        AndroidNetworking.initialize(context);
                                        //Folder Creating Into Phone Storage
                                        dirPath = Environment.getExternalStorageDirectory() +"/BinTouch/";
                                        long timemill= System.currentTimeMillis();
                                        fileName = timemill +".jpg";
                                        //file Creating With Folder & Fle Name
                                        file = new File(dirPath, fileName);
                                        AndroidNetworking.download(Msg_or_url, dirPath, fileName)
                                                .build()
                                                .startDownload(new DownloadListener() {
                                                    @Override
                                                    public void onDownloadComplete() {
                                                        // Toast.makeText(activity_chats.this, "DownLoad Complete", Toast.LENGTH_SHORT).show();
                                                    }
                                                    @Override
                                                    public void onError(ANError anError) {
                                                        // Toast.makeText(activity_chats.this, anError.toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
*/


                                    }
                                });

                                dialog.show();
                                IVprofile.setImageDrawable(IVpas.getDrawable());
                            //    IVprofile.setOnTouchListener(UsersViewHolder.this);

                                PhotoViewAttacher pAttacher;
                                pAttacher = new PhotoViewAttacher(IVprofile);
                                pAttacher.update();

                                ConstraintLayout cl=(ConstraintLayout)dialog.findViewById(R.id.kis);

                                int w = (int) (1.0 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                int ww = (int) (0.85 * Resources.getSystem().getDisplayMetrics().heightPixels);

                                ConstraintLayout.LayoutParams params;
                                params = new ConstraintLayout.LayoutParams(w,ww);
                                IVprofile.setLayoutParams(params);



                                Bprofile.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });


                            }
                        });


                    }
            }
            else {
                if (!Msg_type.equals("text"))
                    if (Msg_type.substring(0, 5).equals("image")) {
                        if (Msg_or_url.equals("Uploading<br><br><br><br>Image")) {

                            BprogMee.setVisibility(View.INVISIBLE);

                            if(Math.abs(1.0*(Integer.parseInt(min)-minc))<2) {
                            /*    ProgressBar PB = mView.findViewById(R.id.progressBar2);
                                PB.setVisibility(View.VISIBLE);*/

                                BprogMee.setVisibility(View.VISIBLE);
                                userNameViewMe1.setVisibility(View.VISIBLE);
                                userNameViewMe1.setText("https://firebasestorxxe.goo1meapis.com/v0/b/bintouch-c21fe.appspot.com/o/AudioxxFWyVrKdyJ7YIHqcVa1K8OdY9Rp68O1axxt9e.3gp?alt=media&token=6011e0a0-e9bb-4095-892e-5dcb207fxxxx");

                                userNameViewMe1.setTextSize(20);

                                ImageView IV = mView.findViewById(R.id.imageView17);
                                IV.setVisibility(View.VISIBLE);

                                Bitmap bmp = null;

                                try {
                                    bmp = MediaStore.Images.Media.getBitmap(CR, uriii);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                //Bitmap myBitmap = BitmapFactory.decodeFile(uriii.getAbsolutePath());
                                   // IV.setBackgroundResource(myBitmap);
                                IV.setImageBitmap(bmp);

                            }
                            else
                            {
                                userNameViewMe1.setVisibility(View.VISIBLE);
                                ProgressBar PB = mView.findViewById(R.id.progressBar2);
                                PB.setVisibility(View.INVISIBLE);
                                userNameViewMe1.setText("\uD83D\uDD34 Uploading Image failed");
                            }

                        } else {

                            LMe.setVisibility(View.VISIBLE);
                            Lhim.setVisibility(View.INVISIBLE);

                            BMe.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mView.performLongClick();
                                }
                            });

                            userNameViewMe1.setVisibility(View.VISIBLE);
                            userNameViewMe1.setText(Msg_or_url);
                            userNameViewMe1.setTextSize(20);
                           // userNameViewMe1.setText("                                               \n\n\n\n\n\n\n\n");
                            final ImageView IV = mView.findViewById(R.id.imageView17);
                            IV.setVisibility(View.VISIBLE);
                            BprogMee.setVisibility(View.INVISIBLE);
                            IV.setBackgroundResource(R.drawable.erff3);

                            Picasso.with(context)
                                    .load(Msg_or_url)
                                    .networkPolicy(NetworkPolicy.OFFLINE)
                                    .into(IV, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                        }
                                        @Override
                                        public void onError() {
                                            // Try again online if cache failed
                                            Picasso.with(context)
                                                    .load(Msg_or_url)
                                                    .into(IV);
                                        }
                                    });

                         //restore   Picasso.with(context).load(Msg_or_url).into(IV);


                            IV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    View IVview = (ImageView) view;

                                    final Dialog dialog = new Dialog(con);
                                    dialog.setContentView(R.layout.msglayout_image);
                                    dialog.setTitle("View:");

                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                    Button Bprofile = (Button) dialog.findViewById(R.id.button10);
                                     IVprofile = (ImageView) dialog.findViewById(R.id.imageView20);

                                    counth = 0;

                                    IVprofile.setRotation((float)counth);
                                    Button Br = (Button) dialog.findViewById(R.id.button82);
                                    Br.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            counth+=-90;
                                            IVprofile.setRotation((float)counth);
                                            // dialog.dismiss();
                                        }
                                    });

                                    Button Bsave = (Button) dialog.findViewById(R.id.button69);
                                    Bsave.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {


                                            IVprofile.buildDrawingCache();
                                            Bitmap bm=IVprofile.getDrawingCache();

                                            OutputStream fOut = null;
                                            Uri outputFileUri;

                                                File root = new File(Environment.getExternalStorageDirectory()
                                                        + File.separator + "BinTouch_Images" + File.separator);
                                                root.mkdirs();

                                                char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                                                StringBuilder sb = new StringBuilder(20);
                                                Random random = new Random();
                                                for (int i = 0; i < 20; i++) {
                                                    char c2 = chars[random.nextInt(chars.length)];
                                                    sb.append(c2);
                                                }
                                               String output = sb.toString()+".jpg";

                                                File sdImageMainDirectory = new File(root, output);
                                                outputFileUri = Uri.fromFile(sdImageMainDirectory);
                                                try {  fOut = new FileOutputStream(sdImageMainDirectory);
                                            } catch (Exception e) { Bsave.setText("error");

                                            }
                                            try {
                                                bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                                fOut.flush();
                                                fOut.close();
                                                Bsave.setText("saved");
                                            } catch (Exception e) { Bsave.setText("error");
                                            }
                                            scanFile(context, Uri.fromFile(sdImageMainDirectory));

                                        }
                                    });

                                    ImageView IVpas = (ImageView) IVview;

                                    dialog.show();

                                    IVprofile.setImageDrawable(IVpas.getDrawable());

                                   // IVprofile.setOnTouchListener(UsersViewHolder.this);
                                    PhotoViewAttacher pAttacher;
                                    pAttacher = new PhotoViewAttacher(IVprofile);
                                    pAttacher.update();

                                    ConstraintLayout cl = (ConstraintLayout) dialog.findViewById(R.id.kis);

                                    int w = (int) (1.0 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                    int ww = (int) (0.85 * Resources.getSystem().getDisplayMetrics().heightPixels);

                                    ConstraintLayout.LayoutParams params;
                                    params = new ConstraintLayout.LayoutParams(w, ww);
                                    IVprofile.setLayoutParams(params);


                                    dialog.show();

                                    Bprofile.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });



                                }
                            });





                        }
                    }
                    }
            }

            ConstraintLayout Chim = mView.findViewById(R.id.audiohim);
            ConstraintLayout Cme = mView.findViewById(R.id.audiome);

            Chim.setVisibility(View.INVISIBLE);
            Cme.setVisibility(View.INVISIBLE);

            TextView TtimeAudioHim = mView.findViewById(R.id.textView60);
            final TextView TtimeAudioMe = mView.findViewById(R.id.textView61);

            final Button BaudioMe = mView.findViewById(R.id.button581);
            final Button BaudioHim = mView.findViewById(R.id.button581c);



            //seekbarme.setProgress(0);

            final SeekBar seekbarme = mView.findViewById((R.id.seekBar1));
            final SeekBar seekbarhim = mView.findViewById((R.id.seekBar1c));

            if(!Msg_or_url.equals("Message deleted..."))
            {
            if (from.equals(userID)) {
                if (!Msg_type.equals("text"))
                    if (Msg_type.substring(0, 5).equals("audio")) {


                        BaudioHim.setVisibility(View.INVISIBLE);
                        BprogHim.setVisibility(View.VISIBLE);

                        userNameViewHim1.setVisibility(View.INVISIBLE);
                        userNameViewHim1.setText(Msg_or_url);
                        userNameViewHim1.setTextSize(8);
                     //   userNameViewHim1.setText(Html.fromHtml("Audio ▶ "+Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;")+10)+""));
                        BaudioHim.setTag(Msg_or_url);
                        ImageView IV = mView.findViewById(R.id.imageView19);
                        IV.setVisibility(View.INVISIBLE);

                        TtimeAudioHim.setText(Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;")+10 , Msg_type.length()-7));

                    //    if(TtimeAudioHim.getText().toString().substring(0,3).equals("00:"))
                    //        TtimeAudioHim.setText(Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;")+13));


//                        TtimeAudioHim.setText(TtimeAudioHim.getText().toString().substring(0,TtimeAudioHim.getText().toString().indexOf(" sec")));


                        Chim.setVisibility(View.VISIBLE);
                     //   userNameViewHim1.setBackgroundResource(R.drawable.roundbuttonchatblue);

                    //    IV.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);

                        BaudioHim.setText(Environment.getExternalStorageDirectory() + "/BinTouch/" + Msg_type + ".mp3");
                        BaudioHim.setTextColor(Color.TRANSPARENT);
                        BaudioHim.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                final Button BBview = (Button) view;

                                if (BBview.getId()!=11)
                                {
                                    BBview.setId(11);

                                    BBview.setBackgroundResource(R.drawable.ic_pause_black_24dp);

                                    mediaPlayer.release();
                                    if (timers != null)
                                        timers.cancel();

                                    timers = new Timer();
                                    mediaPlayer = new MediaPlayer();



                                    final Dialog dialog = new Dialog(con);
                                    dialog.setContentView(R.layout.msglayout_addaccount);
                                    dialog.setTitle("View:");

                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                    dialog.setCanceledOnTouchOutside(false);

                                    Button Bsend = (Button) dialog.findViewById(R.id.button15);
                                    Button Bcancel = (Button) dialog.findViewById(R.id.button60);

                                    dialog.show();

                                    Bsend.setText("Hear as loud audio");
                                    Bcancel.setText("Hear as on earphones");

                                    Bsend.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


                                            try {
                                                mediaPlayer.reset();
                                                FileInputStream fis = new FileInputStream(BBview.getText().toString());
                                                mediaPlayer.setDataSource(fis.getFD());

                                                //  mediaPlayer.setDataSource(BBview.getText().toString());
                                                mediaPlayer.prepare();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                                // Toast.makeText(con, e+"", Toast.LENGTH_LONG).show();
                                            }
                                            seekbarhim.setProgress(0);

                                            try {
                                                // mediaPlayer.setDataSource(BBview.getTag().toString());

                                                mediaPlayer.start();


                                                seekbarhim.setMax(mediaPlayer.getDuration());

                                                timers.scheduleAtFixedRate(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            seekbarhim.setProgress(mediaPlayer.getCurrentPosition());
                                                        } catch (Exception e) {
                                                        }

                                                    }
                                                }, 0, 10);
                                                seekbarhim.setEnabled(false);

                                                mediaPlayer.setOnCompletionListener(new
                                                                                            MediaPlayer.OnCompletionListener() {
                                                                                                @Override
                                                                                                public void onCompletion(MediaPlayer arg0) {
                                                                                                    //  TTview.setText(Html.fromHtml(TTview.getText().toString().substring(0,TTview.getText().toString().indexOf("sec")+3)+""));
                                                                                                    seekbarhim.setProgress(0);
                                                                                                    timers.cancel();
                                                                                                    BBview.setBackgroundResource(R.drawable.ic_mic_greenn);
                                                                                                    BBview.setId(12);
                                                                                                }
                                                                                            });

                                                //     Toast.makeText(con, "Playing Audio", Toast.LENGTH_LONG).show();
                                            } catch (Exception e) {
                                                // make something
                                            }

                                            dialog.cancel();
                                        }
                                    });

                                    Bcancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);


                                            try {
                                                mediaPlayer.reset();
                                                FileInputStream fis = new FileInputStream(BBview.getText().toString());
                                                mediaPlayer.setDataSource(fis.getFD());

                                                //  mediaPlayer.setDataSource(BBview.getText().toString());
                                                mediaPlayer.prepare();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                                // Toast.makeText(con, e+"", Toast.LENGTH_LONG).show();
                                            }
                                            seekbarhim.setProgress(0);

                                            try {
                                                // mediaPlayer.setDataSource(BBview.getTag().toString());

                                                mediaPlayer.start();


                                                seekbarhim.setMax(mediaPlayer.getDuration());

                                                timers.scheduleAtFixedRate(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            seekbarhim.setProgress(mediaPlayer.getCurrentPosition());
                                                        } catch (Exception e) {
                                                        }

                                                    }
                                                }, 0, 10);
                                                seekbarhim.setEnabled(false);

                                                mediaPlayer.setOnCompletionListener(new
                                                                                            MediaPlayer.OnCompletionListener() {
                                                                                                @Override
                                                                                                public void onCompletion(MediaPlayer arg0) {
                                                                                                    //  TTview.setText(Html.fromHtml(TTview.getText().toString().substring(0,TTview.getText().toString().indexOf("sec")+3)+""));
                                                                                                    seekbarhim.setProgress(0);
                                                                                                    timers.cancel();
                                                                                                    BBview.setBackgroundResource(R.drawable.ic_mic_greenn);
                                                                                                    BBview.setId(12);
                                                                                                }
                                                                                            });

                                                //     Toast.makeText(con, "Playing Audio", Toast.LENGTH_LONG).show();
                                            } catch (Exception e) {
                                                // make something
                                            }

                                            dialog.cancel();
                                        }
                                    });






                                }
                                else
                                {
                                    mediaPlayer.release();
                                    seekbarhim.setProgress(0);
                                    timers.cancel();
                                    BBview.setBackgroundResource(R.drawable.ic_mic_greenn);
                                    BBview.setId(12);
                                }
                            }
                        });


                        File file;
                        String dirPath, fileName;
                        // Initialization Of DownLoad Button
                        AndroidNetworking.initialize(context);
                        //Folder Creating Into Phone Storage
                        dirPath = Environment.getExternalStorageDirectory() +"/BinTouch/";
                        long timemill= System.currentTimeMillis();
                        fileName = Msg_type +".mp3";
                        //file Creating With Folder & Fle Name
                        file = new File(dirPath, fileName);
                        AndroidNetworking.download(Msg_or_url, dirPath, fileName)
                                .build()
                                .startDownload(new DownloadListener() {
                                    @Override
                                    public void onDownloadComplete() {
                                        BaudioHim.setVisibility(View.VISIBLE);
                                        BprogHim.setVisibility(View.INVISIBLE);
                                    }
                                    @Override
                                    public void onError(ANError anError) {
                                        // Toast.makeText(activity_chats.this, anError.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
            }
            else {
                if (!Msg_type.equals("text"))
                    if (Msg_type.substring(0, 5).equals("audio")) {
                        if (Msg_or_url.equals("<br><br><br><br><br><i>Uploading Audio")) {
                            if(Math.abs(1.0*(Integer.parseInt(min)-minc))<2){
                                BaudioMe.setVisibility(View.INVISIBLE);
                                BprogMe.setVisibility(View.VISIBLE);

                                userNameViewMe1.setVisibility(View.INVISIBLE);
                                userNameViewMe1.setText("https://firebasestorxxe.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/AudioxxFWyVrKdyJ7YIHqcVa1K8OdY9Rp68O1axxt9e.3gp?alt=media&token=6011e0a0-e9bb-4095-892e-5dcb207fxxxx");
                                userNameViewMe1.setTextSize(8);
                                //    userNameViewMe1.setText(Html.fromHtml("Audio ▶ "+Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;")+10)+""));
                                BaudioMe.setTag(Msg_or_url);
                                final ImageView IV = mView.findViewById(R.id.imageView17);
                                IV.setVisibility(View.INVISIBLE);

                                TtimeAudioMe.setText(Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;") + 10, Msg_type.length() - 7));

                                //     if(TtimeAudioMe.getText().toString().substring(0,3).equals("00:"))
                                //        TtimeAudioMe.setText(Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;")+13));

                                //   TtimeAudioMe.setText(TtimeAudioMe.getText().toString().substring(0,TtimeAudioMe.getText().toString().indexOf(" sec")));

                                Cme.setVisibility(View.VISIBLE);

                                //    userNameViewMe1.setBackgroundResource(R.drawable.roundbuttonchatred);

                                //     IV.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                                BaudioMe.setTextColor(Color.TRANSPARENT);
                                BaudioMe.setText(Environment.getExternalStorageDirectory() + "/BinTouch/" + Msg_type + ".mp3");

                            }
                           else
                           {
                               userNameViewMe1.setVisibility(View.VISIBLE);
                               ProgressBar PB = mView.findViewById(R.id.progressBar2);
                               PB.setVisibility(View.INVISIBLE);
                               userNameViewMe1.setText("\uD83D\uDD34 Uploading Audio failed");
                           }
                        } else {

                            BaudioMe.setVisibility(View.VISIBLE);
                            BprogMe.setVisibility(View.INVISIBLE);

                            userNameViewMe1.setVisibility(View.INVISIBLE);
                            userNameViewMe1.setText(Msg_or_url);
                            userNameViewMe1.setTextSize(8);
                            //    userNameViewMe1.setText(Html.fromHtml("Audio ▶ "+Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;")+10)+""));
                            BaudioMe.setTag(Msg_or_url);
                            final ImageView IV = mView.findViewById(R.id.imageView17);
                            IV.setVisibility(View.INVISIBLE);

                            TtimeAudioMe.setText(Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;") + 10, Msg_type.length() - 7));

                            //     if(TtimeAudioMe.getText().toString().substring(0,3).equals("00:"))
                            //        TtimeAudioMe.setText(Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;")+13));

                            //   TtimeAudioMe.setText(TtimeAudioMe.getText().toString().substring(0,TtimeAudioMe.getText().toString().indexOf(" sec")));

                            Cme.setVisibility(View.VISIBLE);

                            //    userNameViewMe1.setBackgroundResource(R.drawable.roundbuttonchatred);

                            //     IV.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                            BaudioMe.setTextColor(Color.TRANSPARENT);
                            BaudioMe.setText(Environment.getExternalStorageDirectory() + "/BinTouch/" + Msg_type + ".mp3");

                            BaudioMe.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    final Button BBview = (Button) view;
/*

                                     final int x =60000* Integer.parseInt(BBview.getText().toString().substring(8,10))+
                                            1000*Integer.parseInt(BBview.getText().toString().substring(11,13))+
                                            10* Integer.parseInt(BBview.getText().toString().substring(14,16));

                                 //   TTview.setText(Html.fromHtml(TTview.getText().toString().substring(0,TTview.getText().toString().indexOf("sec")+3)+"<font color=\"red\"> "+ "Playing" +""));


                                  new CountDownTimer(x+1000, 100) {
                                        @Override
                                        public void onTick(long l) {


                                        }

                                        @Override
                                        public void onFinish() {
                                        }
                                    }.start();


*/


                                    if (BBview.getId() != 11) {

                                        BBview.setId(11);
                                        BBview.setBackgroundResource(R.drawable.ic_pause_black_24dp);


                                        mediaPlayer.release();
                                        if (timers != null)
                                            timers.cancel();

                                        timers = new Timer();
                                        mediaPlayer = new MediaPlayer();



                                        final Dialog dialog = new Dialog(con);
                                        dialog.setContentView(R.layout.msglayout_addaccount);
                                        dialog.setTitle("View:");

                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                        dialog.setCanceledOnTouchOutside(false);

                                        Button Bsend = (Button) dialog.findViewById(R.id.button15);
                                        Button Bcancel = (Button) dialog.findViewById(R.id.button60);

                                        dialog.show();

                                        Bsend.setText("Hear as loud audio");
                                        Bcancel.setText("Hear as on earphones");

                                        Bsend.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


                                                try {
                                                    mediaPlayer.reset();
                                                    FileInputStream fis = new FileInputStream(BBview.getText().toString());
                                                    mediaPlayer.setDataSource(fis.getFD());

                                                    //  mediaPlayer.setDataSource(BBview.getText().toString());
                                                    mediaPlayer.prepare();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                    //  Toast.makeText(con, e+"", Toast.LENGTH_LONG).show();
                                                }

                                                seekbarme.setProgress(0);

                                                try {
                                                    //   mediaPlayer.setDataSource(BBview.getTag().toString());

                                                    mediaPlayer.start();

                                                    //   Toast.makeText(con, mediaPlayer.getDuration(), Toast.LENGTH_LONG).show();

                                                    seekbarme.setMax(mediaPlayer.getDuration());

                                                    timers.scheduleAtFixedRate(new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                seekbarme.setProgress(mediaPlayer.getCurrentPosition());
                                                            } catch (Exception e) {
                                                            }

                                                        }
                                                    }, 0, 10);
                                                    seekbarme.setEnabled(false);


                                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                        @Override
                                                        public void onCompletion(MediaPlayer arg0) {
                                                            //  TTview.setText(Html.fromHtml(TTview.getText().toString().substring(0,TTview.getText().toString().indexOf("sec")+3)+""));
                                                            seekbarme.setProgress(0);
                                                            timers.cancel();
                                                            BBview.setBackgroundResource(R.drawable.ic_mic_orange);
                                                            BBview.setId(12);
                                                        }
                                                    });

                                                    //     Toast.makeText(con, "Playing Audio", Toast.LENGTH_LONG).show();
                                                } catch (Exception e) {
                                                    // Toast.makeText(context, "Error reading!"+"", Toast.LENGTH_SHORT).show();

                                                    // make something
                                                }

                                                dialog.cancel();
                                            }
                                        });

                                        Bcancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);


                                                try {
                                                    mediaPlayer.reset();
                                                    FileInputStream fis = new FileInputStream(BBview.getText().toString());
                                                    mediaPlayer.setDataSource(fis.getFD());

                                                    //  mediaPlayer.setDataSource(BBview.getText().toString());
                                                    mediaPlayer.prepare();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                    //  Toast.makeText(con, e+"", Toast.LENGTH_LONG).show();
                                                }

                                                seekbarme.setProgress(0);

                                                try {
                                                    //   mediaPlayer.setDataSource(BBview.getTag().toString());

                                                    mediaPlayer.start();

                                                    //   Toast.makeText(con, mediaPlayer.getDuration(), Toast.LENGTH_LONG).show();

                                                    seekbarme.setMax(mediaPlayer.getDuration());

                                                    timers.scheduleAtFixedRate(new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                seekbarme.setProgress(mediaPlayer.getCurrentPosition());
                                                            } catch (Exception e) {
                                                            }

                                                        }
                                                    }, 0, 10);
                                                    seekbarme.setEnabled(false);



                                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                        @Override
                                                        public void onCompletion(MediaPlayer arg0) {
                                                            //  TTview.setText(Html.fromHtml(TTview.getText().toString().substring(0,TTview.getText().toString().indexOf("sec")+3)+""));
                                                            seekbarme.setProgress(0);
                                                            timers.cancel();
                                                            BBview.setBackgroundResource(R.drawable.ic_mic_orange);
                                                            BBview.setId(12);
                                                        }
                                                    });

                                                    //     Toast.makeText(con, "Playing Audio", Toast.LENGTH_LONG).show();
                                                } catch (Exception e) {
                                                    // Toast.makeText(context, "Error reading!"+"", Toast.LENGTH_SHORT).show();

                                                    // make something
                                                }

                                                dialog.cancel();
                                            }
                                        });





                                    } else {

                                        mediaPlayer.release();
                                        seekbarme.setProgress(0);
                                        timers.cancel();
                                        BBview.setBackgroundResource(R.drawable.ic_mic_orange);
                                        BBview.setId(12);
                                    }
                                }
                            });


                            File file;
                            String dirPath, fileName;
                            // Initialization Of DownLoad Button
                            AndroidNetworking.initialize(context);
                            //Folder Creating Into Phone Storage
                            dirPath = Environment.getExternalStorageDirectory() +"/BinTouch/";
                            long timemill= System.currentTimeMillis();
                            fileName = Msg_type +".mp3";
                            //file Creating With Folder & Fle Name
                            file = new File(dirPath, fileName);
                            AndroidNetworking.download(Msg_or_url, dirPath, fileName)
                                    .build()
                                    .startDownload(new DownloadListener() {
                                        @Override
                                        public void onDownloadComplete() {

                                          //  Toast.makeText(con, dirPath+fileName, Toast.LENGTH_LONG).show();

                                        }
                                        @Override
                                        public void onError(ANError anError) {
                                            // Toast.makeText(activity_chats.this, anError.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                        }
                    }
            }
            }


        }


    }



void f()
{

    final String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
    c.add(Calendar.DATE, 0);
    Date resultdate;
    resultdate = new Date(c.getTimeInMillis());
    String s =sdf.format(resultdate);

    Calendar rightNow = Calendar.getInstance();
    int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
    int currentmin = rightNow.get(Calendar.MINUTE); //

    String ffp = String.valueOf(currentmin).toString();
    if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();

    String hour = String.valueOf(currentHour).toString() + ":" + ffp;
    if(hour.length()<5) hour = "0"+hour;
    String DateTime = s + hour;

        HashMap<String, String> object = new HashMap<String, String>();
        object.put("from", user_id);
        object.put("msg_or_url", msgg);
        object.put("msg_type", "text");
        object.put("dateTime", DateTime);
        object.put("sent_received_seen", "sent"); //  sent , received, seen
    object.put("from_orignal_id", uuser_id); //  sent , received, seen


   String outputs = mChatDatabase.child(uuser_id).child(user_id).push().getKey();
    object.put("from_orignal_key", outputs); //  sent , received, seen

    mChatDatabase.child(uuser_id).child(user_id).child(outputs).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // mprofilesendRequest.setEnabled(true);
                //  mCurrent_state = "req_sent";
                // mprofilesendRequest.setText("Cancel friend request");
                //  mDecline.setVisibility(View.INVISIBLE);
                //  mDecline.setEnabled(false);
            }
        });

    String outputss = mChatDatabase.child(user_id).child(uuser_id).push().getKey();
    object.put("forward_from_orignal_key", outputss); //  sent , received, seen

    mChatDatabase.child(user_id).child(uuser_id).child(outputss).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });


}


/*
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
               //     Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                  //  Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case 2: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //    Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    SaveImage(bitmap);
                } else {
                //    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    */
}
