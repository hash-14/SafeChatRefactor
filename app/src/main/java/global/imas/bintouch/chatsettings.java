package global.imas.bintouch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class chatsettings extends AppCompatActivity  {

    String user_id, my_id;
Button B1,B2,B3,B4,B5;
    private FirebaseUser mCurrent_user;
    private DatabaseReference mdata;
    String uuser_id;
ImageView IV;

void ass()
{
    SharedPreferences settings8 = PreferenceManager.getDefaultSharedPreferences(this);
    String backcolor = settings8.getString(user_id+"_color", "c4");  /// 0 is default if variable not found
    if(backcolor.equals("c1"))
    {IV.setBackgroundColor(Color.rgb(243,45,45));}
    if(backcolor.equals("c2"))
    {IV.setBackgroundColor(Color.rgb(221,255,221));}
    if(backcolor.equals("c3"))
    {IV.setBackgroundColor(Color.rgb(67,67,222));}
    if(backcolor.equals("c4"))
    {IV.setBackgroundColor(Color.rgb(223,223,223));}
    if(backcolor.equals("c5"))
    {IV.setBackgroundColor(Color.rgb(240,154,80));}
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatsettings);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        user_id  = getIntent().getStringExtra("user_id");

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        mdata = FirebaseDatabase.getInstance().getReference();
        mdata.keepSynced(true);

        uuser_id = "error";
        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(chatsettings.this);
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

         IV = (ImageView)findViewById(R.id.imageView12);
        ass();





        Button bb = (Button)findViewById(R.id.button78);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText = (EditText)findViewById(R.id.editText14);
               // if(editText.getText().toString().equals(""))editText.setText(" ");
                mdata.child("Status_each_page").child(uuser_id).child(user_id).child("status").setValue(editText.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                      Toast.makeText(chatsettings.this, "Updated!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

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


                                           String x = dataSnapshot.child("status").getValue(String.class).toString();
                                            EditText editText = (EditText)findViewById(R.id.editText14);
                                            editText.setText(x);


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

        /*
/////////////////////////////////////////////
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
         /////////////save////////////////
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(user_id+"_color", ts);
            editor.commit();
         //////////////get//////////////////
            String ret = settings.getString("statepara1", "0");  /// 0 is default if variable not found
/////////////////////////////////////////////////////
*/

        Button B1 = (Button)findViewById(R.id.button21);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                B1.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        B1.setAlpha(1.0F);
                    }
                }.start();

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(chatsettings.this);
                /////////////save////////////////
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(user_id+"_color", "c4");
                editor.commit();
                ass();
            }
        });
        Button B2 = (Button)findViewById(R.id.button22);
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                B2.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        B2.setAlpha(1.0F);
                    }
                }.start();
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(chatsettings.this);
                /////////////save////////////////
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(user_id+"_color", "c2");
                editor.commit();
                ass();
            }
        });
        Button B3 = (Button)findViewById(R.id.button23);
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                B3.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        B3.setAlpha(1.0F);
                    }
                }.start();
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(chatsettings.this);
                /////////////save////////////////
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(user_id+"_color", "c3");
                editor.commit();
                ass();
            }
        });
        Button B4 = (Button)findViewById(R.id.button24);
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                B4.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        B4.setAlpha(1.0F);
                    }
                }.start();
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(chatsettings.this);
                /////////////save////////////////
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(user_id+"_color", "c1");
                editor.commit();
                ass();
            }
        });
        Button B5 = (Button)findViewById(R.id.button25);
        B5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                B5.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        B5.setAlpha(1.0F);
                    }
                }.start();
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(chatsettings.this);
                /////////////save////////////////
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(user_id+"_color", "c5");
                editor.commit();
                ass();
            }
        });

        Button B50 = (Button)findViewById(R.id.button50);
        B50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent (chatsettings.this, activity_chats.class);
                startIntent.putExtra("user_id", user_id);
                startActivity(startIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent startIntent = new Intent (chatsettings.this, activity_chats.class);
        startIntent.putExtra("user_id", user_id);
        startActivity(startIntent);
    }


}
