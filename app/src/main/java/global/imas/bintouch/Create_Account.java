package global.imas.bintouch;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Create_Account extends AppCompatActivity {

    String Group_name, passito;
    String msgg = "";
    String gf = "";
    Context context = this;

    Button  Bsettings,Battach;
    String statewithyou = "", stateinapp = "";
    TextView Tnote1, Tnote2, Tnote3,Tstatus;
    Button Baudio;

    String refreshedToken;
    Button Binfo;


    View slideView, slideView2, slideView3;
    View slideViewD, slideView2D, slideView3D;

    Button Bnote;
    Button BnoteSave;
    EditText Enote;

    Button Bcoc;
    Button BcocSave;
    EditText Econc;

    Button Badd;



    private FirebaseUser mCurrent_user;
    private DatabaseReference mUsersDatabase, mUsersDatabase2, mUsersDatabase3, mAutoReplyDatabase, mUsersDatabaseo;
    private DatabaseReference mChatDatabase, mNotificationDatabase, mfriendsDatabase;
    private DatabaseReference mNoteDatabase, mStateDatabase_with_you, mStateDatabase_in_app, mGroupsDatabase;

    private RecyclerView mChatList;
    MediaPlayer mp;

    Button Bsend;
    EditText Emsg;
    LinearLayoutManager mLinearLayoutManager;
String ss="";

    int flag = 0;
    int flag2 = 0;
    int flagito=0;
    Uri FilePathUri;

    String Storage_Path = "All_Image_Uploads/";
    String Storage_Path2 = "Music_Uploads/";
    String Database_Path = "All_Image_Uploads_Database";
    StorageReference storageReference= FirebaseStorage.getInstance().getReference();

    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    String output;
    void attachimage()
    {

        mUsersDatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 namito = dataSnapshot.child("name").getValue(String.class);


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

                String DateTime = s + hour;

                String uuser_id = "x";
                SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                final String ui = uuser_id;
                HashMap<String, String> object = new HashMap<String, String>();
                object.put("from", uuser_id);
                object.put("msg_or_url", "Uploading<br><br><br><br>Image");
                object.put("msg_type", "image"+System.currentTimeMillis());
                object.put("dateTime", DateTime + ";;;;;;;;;;;;" + namito);
                object.put("sent_received_seen", "sent"); //  sent , received, seen


                output = mChatDatabase.child(uuser_id).child(passito).push().getKey();
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


                String finalUuser_id = uuser_id;
                mChatDatabase.child(uuser_id).child(passito).child(output).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                //uploading the image
                                UploadTask uploadTask2 = storageReference2nd.putBytes(data);
                                // progressBar = ProgressDialog.show(this, "Uploading...\n", " ");

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

                                                String DateTime = s + hour;

                                                String uuser_id = "x";
                                                SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                                                final HashMap<String, String> object = new HashMap<String, String>();
                                                object.put("from", uuser_id);
                                                object.put("msg_or_url", hdf);
                                                object.put("msg_type", "image"+System.currentTimeMillis());
                                                object.put("dateTime", DateTime+ ";;;;;;;;;;;;" + namito);
                                                object.put("sent_received_seen", "sent"); //  sent , received, seen
///////////////////////////////

                                                mGroupsDatabase.child(passito).child("Participants").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        String Participants = dataSnapshot.getValue(String.class);

                                                        while (Participants.length() > 12) {
                                                            final String keyy = Participants.substring(0, Participants.indexOf(";;;;;;;;;;"));
                                                            Participants = Participants.substring(Participants.indexOf(";;;;;;;;;;") + 10);

                                                            String uuser_id = "x";
                                                            SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

                                                            if(!keyy.equals(uuser_id))
                                                                mChatDatabase.child(keyy).child(passito).push().setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
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



///////////////////////////

                                                mChatDatabase.child(uuser_id).child(passito).child(output).child("msg_or_url").setValue(hdf).addOnSuccessListener(new OnSuccessListener<Void>() {
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

                                                        String DateTime = s + hour;

                                                        final HashMap<String, String> object2 = new HashMap<String, String>();
                                                        object2.put("msg_or_url", "To "+ Group_name +": "+ "New image ("+DateTime+")");

                                                        mGroupsDatabase.child(passito).child("Participants").addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                String Participants = dataSnapshot.getValue(String.class);

                                                                while (Participants.length() > 12) {
                                                                    final String keyy = Participants.substring(0, Participants.indexOf(";;;;;;;;;;"));
                                                                    Participants = Participants.substring(Participants.indexOf(";;;;;;;;;;") + 10);

                                                                    String uuser_id = "x";
                                                                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                                                                    mNotificationDatabase.child(uuser_id).child(keyy).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {

                                                                        }
                                                                    });

                                                                    if(!keyy.equals(uuser_id))
                                                                        mfriendsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                if (dataSnapshot.hasChild(keyy)) {
                                                                                    mfriendsDatabase.child(keyy).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                                                            if (dataSnapshot.hasChild(passito)) {
                                                                                                mfriendsDatabase.child(keyy).child(passito).addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                                        mfriendsDatabase.child(keyy).child(passito).child("count").setValue(DateTime + count + "").addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                                                                                mGroupsDatabase.child(passito).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                                        userMap2.put("name", dataSnapshot.child("Name").getValue(String.class));
                                                                                                        userMap2.put("status", dataSnapshot.child("Status").getValue(String.class));
                                                                                                        userMap2.put("image", dataSnapshot.child("Image").getValue(String.class));
                                                                                                        userMap2.put("count", DateTime + "1");

                                                                                                        mfriendsDatabase.child(keyy).child(passito).setValue(userMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                                                                    String uuser_id = "x";
                                                                                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                                                                                            userMaps2.put("status", dataSnapshotw.child("status").getValue(String.class));
                                                                                            userMaps2.put("image", "" + dataSnapshotw.child("image").getValue(String.class));
                                                                                            userMaps2.put("count", DateTime + "1");

                                                                                            String uuser_id = "x";
                                                                                            SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

                                                                                            mfriendsDatabase.child(keyy).child(uuser_id).setValue(userMaps2).addOnSuccessListener(new OnSuccessListener<Void>() {
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



                                                    }
                                                });

                                            }
                                        });
                            } catch (IOException e) {
                                e.printStackTrace();
                                mChatDatabase.child(ui).child(passito).child(output).removeValue();
                            }
                        }

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }









    private void performCrop() {
        // take care of exceptions
        flagito = 3;
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(FilePathUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            //  cropIntent.putExtra("aspectX", 1);
            //  cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            //  cropIntent.putExtra("outputX", 800);
            //  cropIntent.putExtra("outputY", 800);
            // retrieve data on return
            cropIntent.putExtra("scale", true);
            cropIntent.putExtra("return-data", true);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, FilePathUri);
            // start the activity - we handle returning in onActivityResult

            // startActivityForResult(cropIntent, 15);

            Intent editIntent = new Intent(Intent.ACTION_EDIT);
            editIntent.setDataAndType(FilePathUri, "image/*");
            editIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(Intent.createChooser(editIntent, null),1);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {


        if (flagito == 3) {
            if (resultCode == RESULT_OK)
                FilePathUri = data.getData();
            attachimage();

        } else if (flagito == -1) {
            FilePathUri = imageUri;
            //   FilePathUri=data.getData();
            if (resultCode == RESULT_OK) {
                performCrop();
            }

        } else if (data != null && flagito == 0) {
            FilePathUri = data.getData();
            performCrop();


        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }


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
    private void stopRecording()
    {
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;

        CDTaudio.cancel();

        // Emsg.setVisibility(View.VISIBLE);

        uploadAudio();
    }

    private StorageReference mStorage;

    String namito;
    private void uploadAudio() {
        mStorage=FirebaseStorage.getInstance().getReference();


mUsersDatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
         namito = dataSnapshot.child("name").getValue(String.class);
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

        String DateTime = s + hour;

        String uuser_id = "x";
        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

        HashMap<String, String> object = new HashMap<String, String>();
        object.put("from",uuser_id);
        object.put("msg_or_url", "<br><br><br><br><br><i>Uploading Audio");
        object.put("msg_type", "audio"+System.currentTimeMillis()+";;;;;;;;;;"+Emsg.getText().toString());
        object.put("dateTime", DateTime+ ";;;;;;;;;;;;" + namito);
        object.put("sent_received_seen", "sent"); //  sent , received, seen


        output = mChatDatabase.child(uuser_id).child(passito).push().getKey();



        mChatDatabase.child(uuser_id).child(passito).child(output).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
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

                        String DateTime = s + hour;

                        String uuser_id = "x";
                        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

                        final HashMap<String, String> object = new HashMap<String, String>();
                        object.put("from", uuser_id);
                        object.put("msg_or_url", hdf);
                        object.put("msg_type", "audio"+System.currentTimeMillis()+";;;;;;;;;;"+Emsg.getText().toString());
                        Emsg.setText("");
                        object.put("dateTime", DateTime+ ";;;;;;;;;;;;" + namito);
                        object.put("sent_received_seen", "sent"); //  sent , received, seen


                        mGroupsDatabase.child(passito).child("Participants").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String Participants = dataSnapshot.getValue(String.class);

                                while (Participants.length() > 12) {
                                    final String keyy = Participants.substring(0, Participants.indexOf(";;;;;;;;;;"));
                                    Participants = Participants.substring(Participants.indexOf(";;;;;;;;;;") + 10);

                                    String uuser_id = "x";
                                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

                                    if(!keyy.equals(uuser_id))
                                        mChatDatabase.child(keyy).child(passito).push().setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
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


                        mChatDatabase.child(uuser_id).child(passito).child(output).child("msg_or_url").setValue(hdf).addOnSuccessListener(new OnSuccessListener<Void>() {
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

                                String DateTime = s + hour;

                                final HashMap<String, String> object2 = new HashMap<String, String>();
                                object2.put("msg_or_url", "To "+ Group_name +": "+ "New audio ("+DateTime+")");


                                mGroupsDatabase.child(passito).child("Participants").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String Participants = dataSnapshot.getValue(String.class);

                                        while (Participants.length() > 12) {
                                            final String keyy = Participants.substring(0, Participants.indexOf(";;;;;;;;;;"));
                                            Participants = Participants.substring(Participants.indexOf(";;;;;;;;;;") + 10);

                                            String uuser_id = "x";
                                            SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                                            mNotificationDatabase.child(uuser_id).child(keyy).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });

                                            if(!keyy.equals(uuser_id))
                                                mfriendsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.hasChild(keyy)) {
                                                            mfriendsDatabase.child(keyy).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                                    if (dataSnapshot.hasChild(passito)) {
                                                                        mfriendsDatabase.child(keyy).child(passito).addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                                                mfriendsDatabase.child(keyy).child(passito).child("count").setValue(DateTime + count).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                                                        mGroupsDatabase.child(passito).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                                                userMap2.put("name", dataSnapshot.child("Name").getValue(String.class));
                                                                                userMap2.put("status", dataSnapshot.child("Status").getValue(String.class));
                                                                                userMap2.put("image", dataSnapshot.child("Image").getValue(String.class));
                                                                                userMap2.put("count", DateTime + "1");

                                                                                mfriendsDatabase.child(keyy).child(passito).setValue(userMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                                            String uuser_id = "x";
                                                            SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                                                                    userMaps2.put("status", dataSnapshotw.child("status").getValue(String.class));
                                                                    userMaps2.put("image", "" + dataSnapshotw.child("image").getValue(String.class));
                                                                    userMaps2.put("count", DateTime+"1");

                                                                    String uuser_id = "x";
                                                                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

                                                                    mfriendsDatabase.child(keyy).child(uuser_id).setValue(userMaps2).addOnSuccessListener(new OnSuccessListener<Void>() {
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




                            }
                        });


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

    Button bcgray;
    int color_r=0, color_g=0,color_b=0;

    int stop = 0;
    int start = 0;
    String EmsgMsg ="";

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
    Uri imageUri;
    String uuser_id1 = "x";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__account);

        Tstatus = (TextView)findViewById(R.id.textView46);
        Tstatus.setSelected(true);


        Button bsdb = (Button)findViewById(R.id.button37);
        bsdb.setText(Html.fromHtml("<u>U"));
        bcgray = (Button)findViewById(R.id.button79);

        Button Bbold = (Button)findViewById(R.id.button35);
        Button Bitalic = (Button)findViewById(R.id.button36);
        Button Bunder = (Button)findViewById(R.id.button37);


        Button Bcam = (Button)findViewById(R.id.button80);
        Bcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    startActivityForResult(captureIntent, 1);


                } catch (ActivityNotFoundException anfe) {
                    Toast toast = Toast.makeText(context, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });


        Bbold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatText_bold();
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

        bcgray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(Create_Account.this);
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
        Group_name = getIntent().getStringExtra("group_name");
        passito = getIntent().getStringExtra("passito");

        Bsend = (Button) findViewById(R.id.button7);
        Emsg = (EditText) findViewById(R.id.editText4);

        Binfo= (Button) findViewById(R.id.button52);

        Binfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(Create_Account.this);
                dialog.setContentView(R.layout.msglayout_info);
                dialog.setTitle("Users in the group:");

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Button Bcancel = (Button) dialog.findViewById(R.id.button10);
                final TextView Tname = (TextView) dialog.findViewById(R.id.textView47);
                ss="";
                dialog.show();

                mGroupsDatabase.child(passito).child("Participants").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String Participants = dataSnapshot.getValue(String.class);
                        while (Participants.length() > 12) {
                            final String keyy = Participants.substring(0, Participants.indexOf(";;;;;;;;;;"));
                            Participants = Participants.substring(Participants.indexOf(";;;;;;;;;;") + 10);

                            mUsersDatabaseo.child(keyy).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String name = dataSnapshot.child("name").getValue(String.class);
                                    String email = dataSnapshot.child("email").getValue(String.class);

                                    ss=ss+"<b>"+name+"</b><br><i><font color=\"gray\">" +email + "</font></i><br><br>";
                                    Tname.setText(Html.fromHtml(ss));
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







                Bcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        // Intent startIntent = new Intent(publics.this, ViewProfile.class);
                        //  startIntent.putExtra("user_id", user_id);
                        // startActivity(startIntent);
                    }
                });


            }
        });

        TextView TGoupName = (TextView) findViewById(R.id.textView30);
        TGoupName.setText(Group_name);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Group");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Tnote1 = (TextView) findViewById(R.id.textView18);
        Tnote2 = (TextView) findViewById(R.id.textView13);
        Tnote3 = (TextView) findViewById(R.id.textView12);

        Bnote = (Button) findViewById(R.id.button11);
        BnoteSave = (Button) findViewById(R.id.button12);
        Enote = (EditText) findViewById(R.id.editText5);



        Bcoc = (Button) findViewById(R.id.button112);
        BcocSave = (Button) findViewById(R.id.button122);
        Econc = (EditText) findViewById(R.id.editText52);

        slideView = (View) findViewById(R.id.cc3);
        slideView2 = (View) findViewById(R.id.cccc);

        slideViewD = (View) findViewById(R.id.cc32);
        slideView2D = (View) findViewById(R.id.cccc2);


        /////////////////////////////////////////////
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String ret = settings.getString("NotePrivate"+passito, "");  /// 0 is default if variable not found
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
                editor.putString("NotePrivate"+passito, msg);
                editor.commit();

                Toast.makeText(Create_Account.this, "Note saved!", Toast.LENGTH_LONG).show();

                //////////////get//////////////////
/////////////////////////////////////////////////////
            }
        });
        Bcoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!(flag==0))
                {
                    flag=0;
                    Bnote.setBackgroundResource(R.drawable.ic_comment_black_24dp);
                    slideView2.animate().translationX(0);
                    //  slideView3.animate().translationX(0);
                    slideView.animate().translationX(0);

                }

                if(flag2==0) {
                    flag2=1;
                    Bcoc.setBackgroundResource(R.drawable.ic_arrow_back_black_24dpg);
                    slideView2D.animate().translationX( slideView.getWidth() -1);
                    //   slideView3D.animate().translationX( slideView.getWidth()-1 );
                    slideViewD.animate().translationX( slideView.getWidth() -1);

                }
                else
                { flag2=0;
                    Bcoc.setBackgroundResource(R.drawable.ic_note_add_black_24dp);
                    slideView2D.animate().translationX(0);
                    //  slideView3D.animate().translationX(0);
                    slideViewD.animate().translationX(0);

                }


            }
        });

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        uuser_id1 = "x";

        SharedPreferences settings111 = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor113 = settings111.edit();

        String select111 = settings111.getString("select", "");  /// 0 is default if variable not found

        if(select111.equals("1"))
        {
            uuser_id1 = mCurrent_user.getUid()+ ";;;;;profile_1";
        }
        if(select111.equals("2"))
        {
            uuser_id1 = mCurrent_user.getUid()+ ";;;;;profile_2";
        }
        if(select111.equals("3"))
        {
            uuser_id1 = mCurrent_user.getUid()+ ";;;;;profile_3";
        }
        if(select111.equals("4"))
        {
            uuser_id1 = mCurrent_user.getUid()+ ";;;;;profile_4";
        }
        if(select111.equals("5"))
        {
            uuser_id1 = mCurrent_user.getUid()+ ";;;;;profile_5";
        }

        mNoteDatabase = FirebaseDatabase.getInstance().getReference().child("Notes");
        mNoteDatabase.keepSynced(true);

        Bnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int x = slideView.getWidth();
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
                    slideView2.animate().translationX(-1 * x - 1);
                    //  slideView3.animate().translationX(-1 * slideView.getWidth() - 1);
                    slideView.animate().translationX(-1 * x - 1);



                    mNoteDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChild(uuser_id1)) {

                                mNoteDatabase.child(uuser_id1).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.hasChild(passito))
                                        {

                                            mNoteDatabase.child(uuser_id1).child(passito).child("note").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {


                                                    if(dataSnapshot.getValue(Notes.class).getFrom().toString().equals(uuser_id1))
                                                    {

                                                        mNoteDatabase.child(uuser_id1).child(passito).child("note").child("sent_received_seen").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                                        mNoteDatabase.child(passito).child(uuser_id1).child("note").child("sent_received_seen").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
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

                                            mNoteDatabase.child(passito).child(uuser_id1).child("note").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {


                                                    if(dataSnapshot.getValue(Notes.class).getFrom().toString().equals(uuser_id1))
                                                    {

                                                        mNoteDatabase.child(uuser_id1).child(passito).child("note").child("sent_received_seen").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                                        mNoteDatabase.child(passito).child(uuser_id1).child("note").child("sent_received_seen").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
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

                }
            }
        });





        mUsersDatabase3 = FirebaseDatabase.getInstance().getReference().child("Users").child(uuser_id1);
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

                String DateTime = s + hour;


                HashMap<String, String> object2 = new HashMap<String, String>();
                object2.put("from", uuser_id1);
                object2.put("note_msg", msg);
                object2.put("dateTime", DateTime);
                object2.put("sent_received_seen", "sent"); //  sent , received, seen


                mNoteDatabase.child(uuser_id1).child(passito).child("note").setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Create_Account.this, "Conclusion saved!", Toast.LENGTH_LONG).show();

                        mNoteDatabase.child(uuser_id1).child(passito).child("note").addValueEventListener(new ValueEventListener() {
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
                mNoteDatabase.child(passito).child(uuser_id1).child("note").setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mNoteDatabase.child(uuser_id1).child(passito).child("note").addValueEventListener(new ValueEventListener() {
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

                if(dataSnapshot.hasChild(uuser_id1)) {

                    mNoteDatabase.child(uuser_id1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChild(passito))
                            {
                                mNoteDatabase.child(uuser_id1).child(passito).child("note").addValueEventListener(new ValueEventListener() {
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


        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chats");
        mChatDatabase.keepSynced(true);

        mfriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mfriendsDatabase.keepSynced(true);

        mNotificationDatabase = FirebaseDatabase.getInstance().getReference().child("Notifications");

        mNoteDatabase = FirebaseDatabase.getInstance().getReference().child("Notes");
        mNoteDatabase.keepSynced(true);

        mGroupsDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");
        mGroupsDatabase.keepSynced(true);

        String uuser_id = "x";
        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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


        mfriendsDatabase.child(uuser_id).child(passito).child("count").setValue(DateTime+"0").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

        mUsersDatabase2 = FirebaseDatabase.getInstance().getReference().child("Users").child(passito);
        mUsersDatabase2.keepSynced(true);


        mUsersDatabase3 = FirebaseDatabase.getInstance().getReference().child("Users").child(uuser_id);
        mUsersDatabase3.keepSynced(true);


        mChatList = (RecyclerView) findViewById(R.id.recyclerView2);
        mChatList.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);

        mChatList.setLayoutManager(mLinearLayoutManager);

        mChatList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


            }
        });

        mUsersDatabaseo = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabaseo.keepSynced(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(passito);
        mUsersDatabase.keepSynced(true);

        mNotificationDatabase= FirebaseDatabase.getInstance().getReference().child("Notifications");
        /*


         */
        Badd = (Button) findViewById(R.id.button51);

        mGroupsDatabase.child(passito).child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id = dataSnapshot.getValue(String.class);

                String uuser_id = "x";
                SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

                if (id.equals(uuser_id)) {
                    Badd.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent startIntent = new Intent(Create_Account.this, Add_person_to_group.class);
                startIntent.putExtra("group_name", Group_name);
                startIntent.putExtra("passito", passito);
                startActivity(startIntent);
            }
        });

        ccount = 0;
        ccount1 = 0;
        adapter();

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

                Intent pickImageIntent = new Intent(Intent.ACTION_PICK);//, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

          mfriendsDatabase.child(uuser_id).child(passito).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
if(ccount1==0)
{
    ccount1++;
}
else {
    /////inc the count/////
    MediaPlayer mp1;
    mp1 = MediaPlayer.create(context, R.raw.unconvinced);
    mp1.start();
}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Baudio = (Button) findViewById(R.id.button8);

        Baudio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
                {
                    Baudio.startAnimation(shakeError());

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


                return false;
            }
        });


        Bsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (EmsgMsg.equals(""))  EmsgMsg = Emsg.getText().toString();

               // msg = Emsg.getText().toString();
               // Emsg.setText("");

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("statepara1", "");
                editor.commit();

                ccount = 0;

                Bsend.setBackgroundResource(R.drawable.ic_menu_send_green);
                new CountDownTimer(200, 100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Bsend.setBackgroundResource(R.drawable.ic_menu_send);
                    }
                }.start();

                if (!Emsg.getText().toString().equals("")){
                    String uuser_id = "x";
                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                    mUsersDatabaseo.child(uuser_id).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String namee = dataSnapshot.getValue(String.class);
                            msg = "<b>" + namee + "</b><br>" ;

                            mGroupsDatabase.child(passito).child("Participants").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String Participants = dataSnapshot.getValue(String.class);

                                    while (Participants.length() > 12) {
                                        final String keyy = Participants.substring(0, Participants.indexOf(";;;;;;;;;;"));
                                        Participants = Participants.substring(Participants.indexOf(";;;;;;;;;;") + 10);


                                        final String dateInString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
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

                                        Calendar rightNow = Calendar.getInstance();
                                        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                        int currentmin = rightNow.get(Calendar.MINUTE); //

                                        String ffp = String.valueOf(currentmin).toString();
                                        if (currentmin < 10)
                                            ffp = "0" + String.valueOf(currentmin).toString();

                                        String hour = String.valueOf(currentHour).toString() + ":" + ffp;

                                        String DateTime = s + hour;
                                        String uuser_id = "x";
                                        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                                        HashMap<String, String> object = new HashMap<String, String>();
                                        object.put("from", uuser_id);
                                        object.put("msg_or_url", msg+EmsgMsg);
                                        gf=EmsgMsg;
                                        //EmsgMsg = "";
                                        object.put("msg_type", "text");
                                        object.put("dateTime", DateTime);
                                        object.put("sent_received_seen", "sent"); //  sent , received, seen


                                        mChatDatabase.child(keyy).child(passito).push().setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {





                                            }
                                        });


                                        if(!keyy.equals(uuser_id))
                                        mfriendsDatabase.child(keyy).child(passito).child("count").addListenerForSingleValueEvent(new ValueEventListener() {
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

                                                int count = Integer.parseInt(dataSnapshot.getValue(String.class).substring(15));
                                                count++;


                                                mfriendsDatabase.child(keyy).child(passito).child("count").setValue(DateTime+count).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        /////inc the count/////
                                                      //  MediaPlayer mp1;
                                                     //   mp1 = MediaPlayer.create(context, R.raw.unconvinced);
                                                      //  mp1.start();
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                       // gf=msg;

                                        gf ="To "+ Group_name +": "+ gf;

                                      //  gf = gf.replace("<br>", ": ");
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

                                        HashMap<String, String> objectb2 = new HashMap<String, String>();
                                        objectb2.put("msg_or_url", gf);


                                        mNotificationDatabase.child(uuser_id).child(keyy).setValue(objectb2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });

                                    }

                                    Emsg.setText("");
                                    EmsgMsg="";

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });


    }

    int ccount = 0;
    int ccount1 = 0;

    void adapter() {

        String uuser_id = "x";
        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

        final FirebaseRecyclerAdapter<Chats, Create_Account.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Chats, Create_Account.UsersViewHolder>(
                Chats.class,
                R.layout.chat_msgs_single_layout,
                Create_Account.UsersViewHolder.class,
                mChatDatabase.child(uuser_id).child(passito)

        ) {


            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Chats model, int position) {

                //   final String user_id = getRef(position).getKey();


                String uuser_id = "x";
                SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

                viewHolder.setdatetime(
                        model.getdateTime(),
                        uuser_id,
                        model.getFrom(),
                        ccount,
                        getApplicationContext(),
                        "",
                        model.getMsg_or_url(),
                        model.getMsg_type(),
                        model.getSent_received_seen(),
                        Create_Account.this,
                        uuser_id
                );
                ccount++;

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



                mfriendsDatabase.child(uuser_id).child(passito).child("count").setValue(DateTime+"0").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });


                final String passito = getIntent().getStringExtra("passito");

                mChatDatabase.child(uuser_id).child(passito).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        for (DataSnapshot child : children) {
                            if (child.getValue(Chats.class).getFrom().toString().equals(passito)) {
                                if (child.getValue(Chats.class).getSent_received_seen().toString().equals("sent")) {

                                    HashMap<String, Object> resultm = new HashMap<>();
                                    resultm.put("sent_received_seen", "received");

                                    String uuser_id = "x";
                                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

                                    mChatDatabase.child(uuser_id).child(passito).child(child.getKey()).updateChildren(resultm);
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

                mChatDatabase.child(passito).child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        for (DataSnapshot child : children) {
                            if (child.getValue(Chats.class).getFrom().toString().equals(passito)) {
                                if (child.getValue(Chats.class).getSent_received_seen().toString().equals("sent")) {

                                    String uuser_id = "x";
                                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                                    HashMap<String, Object> resultm = new HashMap<>();
                                    resultm.put("sent_received_seen", "received");
                                    mChatDatabase.child(passito).child(uuser_id).child(child.getKey()).updateChildren(resultm);
                                  //  mp = MediaPlayer.create(Create_Account.this, R.raw.unconvinced);
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


                        final Dialog dialog = new Dialog(Create_Account.this);
                        dialog.setContentView(R.layout.msglayout_delete);
                        dialog.setTitle("Choose:");

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        Button BdeleteForMe = (Button) dialog.findViewById(R.id.button15);
                        Button BdeleteForEveryone = (Button) dialog.findViewById(R.id.button10);

                        if (userNameViewHim1.getVisibility() == 0) //0 stands for visible
                            BdeleteForEveryone.setVisibility(View.INVISIBLE);

                        state = "";
                        msg = "";
                        date = "";

                        if (userNameViewHim1.getVisibility() == 0) //0 stands for visible
                        {
                            msg = userNameViewHim1.getText().toString();
                            date = userNameViewHim.getText().toString();
                            state = "Him";
                        } else {
                            msg = userNameViewMe1.getText().toString();
                            date = userNameViewMe.getText().toString();
                            state = "Me";
                        }

                        if (!msg.equals("Message deleted..."))
                            view.setBackgroundColor(Color.rgb(200, 200, 200));

                        if (!msg.equals("Message deleted..."))
                            dialog.show();


                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                view.setBackgroundColor(Color.rgb(223, 223, 223));
                            }
                        });


                        BdeleteForMe.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View vieww) {
                                view.setBackgroundColor(Color.rgb(223, 223, 223));

                                if (state == "Me") {
                                    String uuser_id = "x";
                                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                                    mChatDatabase.child(uuser_id).child(passito).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                            for (DataSnapshot child : children) {

                                                String uuser_id = "x";
                                                SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

                                                    date2 = time + ", " + r;

                                                    String str = child.getValue(Chats.class).getMsg_or_url().toString();

                                                    str = str.replace("<br>", "\n");
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

                                                  //  Toast.makeText(Create_Account.this, str+" "+msg, Toast.LENGTH_LONG).show();

                                                    if (str.equals(msg)
                                                            && date2.equals(date)) {
                                                        mChatDatabase.child(uuser_id).child(passito).child(child.getKey()).removeValue();
                                                        Toast.makeText(Create_Account.this, "Deleted!", Toast.LENGTH_LONG).show();
                                                        // firebaseRecyclerAdapter.notifyDataSetChanged();
                                                    }
                                                }

                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                } else
                                {
                                    String uuser_id = "x";
                                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                                    mChatDatabase.child(uuser_id).child(passito).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                            for (DataSnapshot child : children) {


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

                                                    date2 = time + ", " + r;

                                                    String str = child.getValue(Chats.class).getMsg_or_url().toString();

                                                    str = str.replace("<br>", "\n");
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

                                                    //  Toast.makeText(Create_Account.this, str+" "+msg, Toast.LENGTH_LONG).show();

                                                    if (str.equals(msg)
                                                            && date2.equals(date)) {
                                                        String uuser_id = "x";
                                                        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                                                        mChatDatabase.child(uuser_id).child(passito).child(child.getKey()).removeValue();
                                                        Toast.makeText(Create_Account.this, "Deleted!", Toast.LENGTH_LONG).show();
                                                        // firebaseRecyclerAdapter.notifyDataSetChanged();
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
                                view.setBackgroundColor(Color.rgb(223, 223, 223));


                                mGroupsDatabase.child(passito).child("Participants").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        String Participants = dataSnapshot.getValue(String.class);
                                        while (Participants.length() > 12) {
                                            final String keyy = Participants.substring(0, Participants.indexOf(";;;;;;;;;;"));
                                            Participants = Participants.substring(Participants.indexOf(";;;;;;;;;;") + 10);



                                            mChatDatabase.child(keyy).child(passito).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                                    for (DataSnapshot child : children) {


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

                                                        date2 = time + ", " + r;

                                                        String str = child.getValue(Chats.class).getMsg_or_url().toString();

                                                        str = str.replace("<br>", "\n");
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

                                                        //  Toast.makeText(Create_Account.this, str+" "+msg, Toast.LENGTH_LONG).show();

                                                        if (str.equals(msg)
                                                                && date2.equals(date)) {
                                                            HashMap<String, Object> resultm2 = new HashMap<>();
                                                            resultm2.put("msg_or_url", "Message deleted...");
                                                            mChatDatabase.child(keyy).child(passito).child(child.getKey()).updateChildren(resultm2);

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






                                dialog.dismiss();




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
    String state = "";


    public static class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener
    {
        private static final String TAG = "Touch";
        @SuppressWarnings("unused")
        private static final float MIN_ZOOM = 1f,MAX_ZOOM = 1f;

        private static MediaPlayer mediaPlayer = new MediaPlayer();
        private static Timer timers = new Timer();
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
        ImageView IVprofile ;

        int counth = 0;


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
                                String currentID
        )
        {


            TextView userNameViewHim1 = mView.findViewById(R.id.chat_msg);
            TextView userNameViewMe1 = mView.findViewById(R.id.textView31);

            userNameViewHim1.setText("");
            userNameViewMe1.setText("");

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

            String time="";

           if( datetime.contains(";;;;;;;;;;;;"))
             time = datetime.substring(10, datetime.indexOf(";;;;;;;;;;;;"));
            else
               time = datetime.substring(10);

            String hour = datetime.substring(10,datetime.indexOf(':'));
            String min ="";

            if( datetime.contains(";;;;;;;;;;;;"))
                min = datetime.substring(datetime.indexOf(':')+1, datetime.indexOf(";;;;;;;;;;;;"));
else
                min = datetime.substring(datetime.indexOf(':')+1);


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

            if (!from.equals(userID)) {
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

            TextView userNameViewFromHim = mView.findViewById(R.id.textView62);
            TextView userNameViewFromMe = mView.findViewById(R.id.textView63);
            userNameViewFromHim.setVisibility(View.INVISIBLE);
            userNameViewFromMe.setVisibility(View.INVISIBLE);

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

            if(!Msg_or_url.equals("Message deleted..."))
            {
                if (!from.equals(userID)) {
                    if (!Msg_type.equals("text"))
                        if (Msg_type.substring(0, 5).equals("image")) {

                            userNameViewHim1.setVisibility(View.VISIBLE);
                            userNameViewHim1.setText(Msg_or_url);
                            userNameViewHim1.setTextSize(20);

                            userNameViewFromHim.setVisibility(View.VISIBLE);
                            userNameViewFromHim.setText("From "+datetime.substring(datetime.indexOf(";;;;;;;;;;;;")+12));

                            ImageView IV = mView.findViewById(R.id.imageView19);
                            IV.setVisibility(View.VISIBLE);
                            Picasso.with(context).load(Msg_or_url).placeholder(R.drawable.erff).into(IV);


                            IV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    View IVview = (ImageView)view;

                                    final Dialog dialog = new Dialog(con);
                                    dialog.setContentView(R.layout.msglayout_image);
                                    dialog.setTitle("View:");

                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                    Button Bprofile = (Button) dialog.findViewById(R.id.button10);
                                    ImageView IVprofile = (ImageView) dialog.findViewById(R.id.imageView20);

                                    counth = 0;
                                    Button Br = (Button) dialog.findViewById(R.id.button82);
                                    Br.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            counth+=-90;
                                            IVprofile.setRotation((float)counth);
                                            // dialog.dismiss();
                                        }
                                    });

                                    ImageView IVpas = (ImageView) IVview;

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
                                   // IVprofile.setOnTouchListener(Create_Account.UsersViewHolder.this);

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


                            File file;
                            String dirPath, fileName;
                            // Initialization Of DownLoad Button
                            AndroidNetworking.initialize(context);
                            //Folder Creating Into Phone Storage
                            dirPath = Environment.getExternalStorageDirectory() +"/BinTouch";
                            long timemill= System.currentTimeMillis();
                            fileName = timemill +"";
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
                        }
                }
                else {
                    if (!Msg_type.equals("text"))
                        if (Msg_type.substring(0, 5).equals("image")) {
                            if (Msg_or_url.equals("Uploading<br><br><br><br>Image")) {
                                if(Math.abs(1.0*(Integer.parseInt(min)-minc))<2){
                                    ProgressBar PB = mView.findViewById(R.id.progressBar2);
                                    PB.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    userNameViewMe1.setVisibility(View.VISIBLE);
                                    ProgressBar PB = mView.findViewById(R.id.progressBar2);
                                    PB.setVisibility(View.INVISIBLE);
                                    userNameViewMe1.setText("\uD83D\uDD34 Uploading Image failed");
                                }
                            } else {
                                userNameViewMe1.setVisibility(View.VISIBLE);
                                userNameViewMe1.setText(Msg_or_url);
                                userNameViewMe1.setTextSize(20);
                                // userNameViewMe1.setText("                                               \n\n\n\n\n\n\n\n");
                                final ImageView IV = mView.findViewById(R.id.imageView17);
                                IV.setVisibility(View.VISIBLE);

                                userNameViewFromMe.setVisibility(View.VISIBLE);
                                userNameViewFromMe.setText("From "+datetime.substring(datetime.indexOf(";;;;;;;;;;;;")+12));

                                Picasso.with(context).load(Msg_or_url).placeholder(R.drawable.erff).into(IV);


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
                                        Button Br = (Button) dialog.findViewById(R.id.button82);
                                        Br.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                counth+=-90;
                                                IVprofile.setRotation((float)counth);
                                                // dialog.dismiss();
                                            }
                                        });

                                        ImageView IVpas = (ImageView) IVview;

                                        dialog.show();

                                        IVprofile.setImageDrawable(IVpas.getDrawable());

                                    //    IVprofile.setOnTouchListener(Create_Account.UsersViewHolder.this);

                                        PhotoViewAttacher pAttacher;
                                        pAttacher = new PhotoViewAttacher(IVprofile);
                                        pAttacher.update();

                                        ConstraintLayout cl = (ConstraintLayout) dialog.findViewById(R.id.kis);

                                        int w = (int) (1.0 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                        int ww = (int) (0.85 * Resources.getSystem().getDisplayMetrics().heightPixels);

                                        ConstraintLayout.LayoutParams params;
                                        params = new ConstraintLayout.LayoutParams(w, ww);
                                        IVprofile.setLayoutParams(params);

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

                                        dialog.show();

                                        Bprofile.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });


                                    }
                                });


                                File file;
                                String dirPath, fileName;
                                // Initialization Of DownLoad Button
                                AndroidNetworking.initialize(context);
                                //Folder Creating Into Phone Storage
                                dirPath = Environment.getExternalStorageDirectory() + "/BinTouch";
                                long timemill = System.currentTimeMillis();
                                fileName = timemill + "";
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
                if (!from.equals(userID)) {
                    if (!Msg_type.equals("text"))
                        if (Msg_type.substring(0, 5).equals("audio")) {

                            userNameViewHim1.setVisibility(View.INVISIBLE);
                            userNameViewHim1.setText(Msg_or_url);
                            userNameViewHim1.setTextSize(8);
                            //   userNameViewHim1.setText(Html.fromHtml("Audio ▶ "+Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;")+10)+""));
                            BaudioHim.setTag(Msg_or_url);
                            ImageView IV = mView.findViewById(R.id.imageView19);
                            IV.setVisibility(View.INVISIBLE);

                            userNameViewFromHim.setVisibility(View.VISIBLE);
                            userNameViewFromHim.setText("From "+datetime.substring(datetime.indexOf(";;;;;;;;;;;;")+12));

                            TtimeAudioHim.setText(Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;")+10 , Msg_type.length()-7));

                            //    if(TtimeAudioHim.getText().toString().substring(0,3).equals("00:"))
                            //        TtimeAudioHim.setText(Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;")+13));


//                        TtimeAudioHim.setText(TtimeAudioHim.getText().toString().substring(0,TtimeAudioHim.getText().toString().indexOf(" sec")));


                            Chim.setVisibility(View.VISIBLE);
                            //   userNameViewHim1.setBackgroundResource(R.drawable.roundbuttonchatblue);

                            //    IV.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);


                            BaudioHim.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    final Button BBview = (Button) view;

                                    if (BBview.getId()!=11)
                                    {
                                        BBview.setId(11);

                                        BBview.setBackgroundResource(R.drawable.ic_pause_black_24dp);

 /*                          final int x =60000* Integer.parseInt(BBview.getText().toString().substring(8,10))+
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

                                        mediaPlayer.release();
                                        if (timers != null)
                                            timers.cancel();

                                        timers = new Timer();
                                        mediaPlayer = new MediaPlayer();
                                        seekbarhim.setProgress(0);

                                        try {
                                            mediaPlayer.setDataSource(BBview.getTag().toString());
                                            mediaPlayer.prepare();
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
                                       /* seekbarme.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                            @Override
                                            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                                mediaPlayer.seekTo(i);
                                            }

                                            @Override
                                            public void onStartTrackingTouch(SeekBar seekBar) {

                                            }

                                            @Override
                                            public void onStopTrackingTouch(SeekBar seekBar) {

                                            }
                                        });

*/
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
                            dirPath = Environment.getExternalStorageDirectory() +"/BinTouch";
                            long timemill= System.currentTimeMillis();
                            fileName = timemill +"";
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
                        }
                }
                else {
                    if (!Msg_type.equals("text"))
                        if (Msg_type.substring(0, 5).equals("audio")) {
                            if (Msg_or_url.equals("<br><br><br><br><br><i>Uploading Audio")) {
                                if(Math.abs(1.0*(Integer.parseInt(min)-minc))<2) {
                                    ProgressBar PB = mView.findViewById(R.id.progressBar2);
                                    PB.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    userNameViewMe1.setVisibility(View.VISIBLE);
                                    ProgressBar PB = mView.findViewById(R.id.progressBar2);
                                    PB.setVisibility(View.INVISIBLE);
                                    userNameViewMe1.setText("\uD83D\uDD34 Uploading Audio failed");
                                }
                            } else {
                                userNameViewMe1.setVisibility(View.INVISIBLE);
                                userNameViewMe1.setText(Msg_or_url);
                                userNameViewMe1.setTextSize(8);
                                //    userNameViewMe1.setText(Html.fromHtml("Audio ▶ "+Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;")+10)+""));
                                BaudioMe.setTag(Msg_or_url);
                                final ImageView IV = mView.findViewById(R.id.imageView17);
                                IV.setVisibility(View.INVISIBLE);

                                userNameViewFromMe.setVisibility(View.VISIBLE);
                                userNameViewFromMe.setText("From "+datetime.substring(datetime.indexOf(";;;;;;;;;;;;")+12));

                                TtimeAudioMe.setText(Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;") + 10, Msg_type.length() - 7));

                                //     if(TtimeAudioMe.getText().toString().substring(0,3).equals("00:"))
                                //        TtimeAudioMe.setText(Msg_type.substring(Msg_type.indexOf(";;;;;;;;;;")+13));

                                //   TtimeAudioMe.setText(TtimeAudioMe.getText().toString().substring(0,TtimeAudioMe.getText().toString().indexOf(" sec")));

                                Cme.setVisibility(View.VISIBLE);

                                //    userNameViewMe1.setBackgroundResource(R.drawable.roundbuttonchatred);

                                //     IV.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);


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

                                            seekbarme.setProgress(0);

                                            try {
                                                mediaPlayer.setDataSource(BBview.getTag().toString());
                                                mediaPlayer.prepare();
                                                mediaPlayer.start();

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
                                       /* seekbarme.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                            @Override
                                            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                                mediaPlayer.seekTo(i);
                                            }

                                            @Override
                                            public void onStartTrackingTouch(SeekBar seekBar) {

                                            }

                                            @Override
                                            public void onStopTrackingTouch(SeekBar seekBar) {

                                            }
                                        });

*/
                                                mediaPlayer.setOnCompletionListener(new
                                                                                            MediaPlayer.OnCompletionListener() {
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
                                                // make something
                                            }


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
                                dirPath = Environment.getExternalStorageDirectory() + "/BinTouch";
                                long timemill = System.currentTimeMillis();
                                fileName = timemill + "";
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


                            }
                        }
                }
            }


        }


    }




}
