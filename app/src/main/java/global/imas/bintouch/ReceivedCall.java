package global.imas.bintouch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class ReceivedCall extends AppCompatActivity {

    String ss="";
    MediaPlayer mp;
    FirebaseUser mCurrent_user;
    String uuser_id="err";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_call);

        final String message = getIntent().getStringExtra("messa");

        TextView TV65 = (TextView)findViewById(R.id.textView65);
        TV65.setText(message.substring(0,message.indexOf(";;;;;;;;;;")));

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


         mp = MediaPlayer.create(ReceivedCall.this, R.raw.iphone_x);
         mp.start();


        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();



        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor11 = settings11.edit();

        String select11 = settings11.getString("select", "1");  /// 0 is default if variable not found

        if (select11.equals("1")) {
            uuser_id = mCurrent_user.getUid() + ";;;;;profile_1";
        }
        if (select11.equals("2")) {
            uuser_id = mCurrent_user.getUid() + ";;;;;profile_2";
        }
        if (select11.equals("3")) {
            uuser_id = mCurrent_user.getUid() + ";;;;;profile_3";
        }
        if (select11.equals("4")) {
            uuser_id = mCurrent_user.getUid() + ";;;;;profile_4";
        }
        if (select11.equals("5")) {
            uuser_id = mCurrent_user.getUid() + ";;;;;profile_5";
        }



        Button Byes = (Button)findViewById(R.id.button58);
        Byes.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if (mp != null)
             mp.release();



             mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

             DatabaseReference mUsersDatabase;
             mUsersDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

             DatabaseReference mVideoRoomDatabase;
             mVideoRoomDatabase= FirebaseDatabase.getInstance().getReference().child("Video_Call_Rooms");



             final String user_idss = message.substring(message.indexOf(";;;;;;;;;;")+10);
             final String user_id = user_idss.substring(0,user_idss.indexOf(";;;;;;;;;;"));
             ss = user_idss.substring(user_idss.indexOf(";;;;;;;;;;")+10);

             mUsersDatabase.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {

                     final String user_name = dataSnapshot.child("name").getValue().toString();
                     final String user_image = dataSnapshot.child("image").getValue().toString();

                     mVideoRoomDatabase.child(uuser_id).child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {
                             String s = "";
                             if(mCurrent_user.getUid().compareTo(user_id)<0)
                                 s= mCurrent_user.getUid()+user_id;
                             else
                                 s= user_id + mCurrent_user.getUid();

                               //ss = dataSnapshot.child("room").getValue().toString();
//Toast.makeText(ReceivedCall.this,dataSnapshot.child("room").getValue().toString(),Toast.LENGTH_SHORT).show();
                             Intent startIntent = new Intent(ReceivedCall.this, AppRTCMainActivity.class);

                             startIntent.putExtra("user_id", user_id);
                             startIntent.putExtra("user_image", user_image);
                             startIntent.putExtra("user_name", user_name);
                             startIntent.putExtra("roomfire", ss);

                             startActivity(startIntent);
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
         });


                Button Bno = (Button) findViewById(R.id.button59);
        Bno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mp != null)
                    mp.release();

                char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                StringBuilder sb = new StringBuilder(20);
                Random random = new Random();
                for (int i = 0; i < 40; i++) {
                    char c2 = chars[random.nextInt(chars.length)];
                    sb.append(c2);
                }
               String output = sb.toString();

                HashMap<String, String> object2 = new HashMap<String, String>();
                object2.put("msg_or_url", "Rejected Video call "+output);

                FirebaseUser mCurrent_user;
                mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();



                DatabaseReference mNotificationDatabase;
                mNotificationDatabase= FirebaseDatabase.getInstance().getReference().child("Notifications");
                mNotificationDatabase.child(mCurrent_user.getUid()).child(message.substring(message.indexOf(";;;;;;;;;;")+10)).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });

                Toast.makeText(ReceivedCall.this,"Call ended", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(ReceivedCall.this, MainActivity.class);
                startActivity(startIntent);


            }
        });


    }



}
