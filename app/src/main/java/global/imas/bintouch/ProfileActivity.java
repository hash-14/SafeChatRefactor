package global.imas.bintouch;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
public class ProfileActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mDisplayName, mProfilestatus, mprofilefriendscount;
    private Button mprofilesendRequest, mDecline;
    private ImageView mProfileImage;

    private DatabaseReference mNotificationDatabase;

    private DatabaseReference mFriendRequestDatabase;
    private DatabaseReference mFriendDatabase;
    private DatabaseReference mUsersDatabase;
    private String mCurrent_state;
    private ProgressDialog pd;
    private FirebaseUser mCurrent_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mToolbar = (Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final String user_id = getIntent().getStringExtra("user_id");
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        mFriendRequestDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req");
        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mNotificationDatabase = FirebaseDatabase.getInstance().getReference().child("notifications");

        mDisplayName = (TextView)findViewById(R.id.profile_display_name);
        mProfileImage = (ImageView) findViewById(R.id.imageView2);
        mProfilestatus = (TextView)findViewById(R.id.profile_status);
        mprofilefriendscount = (TextView)findViewById(R.id.profile_friend_request);
        mprofilesendRequest = (Button) findViewById(R.id.button3);
        mDecline = (Button) findViewById(R.id.button4);


        mCurrent_state = "not_friends";


        pd = new ProgressDialog(this);
        pd.setTitle("Loading profile");
        pd.setCanceledOnTouchOutside(false);
        pd.show();


        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String display_name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                getSupportActionBar().setTitle(display_name+" profile");

                mDisplayName.setText(display_name);
                mProfilestatus.setText(status);
                Picasso.with(ProfileActivity.this).load(image).placeholder(R.drawable.pp).into(mProfileImage);


                mFriendRequestDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(user_id))
                    {
                        String req_type = dataSnapshot.child(user_id).child("request_type").getValue().toString();
                        if(req_type.equals("received"))
                        {
                            mCurrent_state = "req_received";
                            mprofilesendRequest.setText("Accept friend request");
                            mDecline.setVisibility(View.VISIBLE);
                            mDecline.setEnabled(true);
                        }
                        else   if(req_type.equals("sent"))
                        {
                            mCurrent_state = "req_sent";
                            mprofilesendRequest.setText("Cancel friend request");
                            mDecline.setVisibility(View.INVISIBLE);
                            mDecline.setEnabled(false);

                        }
                        pd.dismiss();
                    } else
                        {
                            mFriendDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(user_id))
                                    {
                                        mCurrent_state = "friends";
                                        mprofilesendRequest.setText("Unfriend");

                                        mDecline.setVisibility(View.INVISIBLE);
                                        mDecline.setEnabled(false);

                                    }
                                    pd.dismiss();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    pd.dismiss();
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
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mprofilesendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mprofilesendRequest.setEnabled(false);

                if(mCurrent_state.equals("not_friends"))
                {
                    mFriendRequestDatabase.child(mCurrent_user.getUid()).child(user_id).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                mFriendRequestDatabase.child(user_id).child(mCurrent_user.getUid()).child("request_type").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                       // Toast.makeText(ProfileActivity.this, "sent",Toast.LENGTH_SHORT).show();

                                        HashMap<String,String> notificationData = new HashMap<String, String>();
                                        notificationData.put("from", mCurrent_user.getUid());
                                        notificationData.put("type", "request");

                                        mNotificationDatabase.child(user_id).push().setValue(notificationData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                mprofilesendRequest.setEnabled(true);
                                                mCurrent_state = "req_sent";
                                                mprofilesendRequest.setText("Cancel friend request");

                                                mDecline.setVisibility(View.INVISIBLE);
                                                mDecline.setEnabled(false);
                                            }
                                        });


                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(ProfileActivity.this, "Error. Try Again",
                                        Toast.LENGTH_SHORT).show();
                                mprofilesendRequest.setEnabled(true);

                            }

                        }
                    });
                }


                if(mCurrent_state.equals("req_sent"))
                {
                    mFriendRequestDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendRequestDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mprofilesendRequest.setEnabled(true);
                                    mCurrent_state = "not_friends";
                                    mprofilesendRequest.setText("Send friend request");

                                    mDecline.setVisibility(View.INVISIBLE);
                                    mDecline.setEnabled(false);
                                }
                            });
                        }
                    });
                }


                if(mCurrent_state.equals("req_received"))
                {
                    mFriendDatabase.child(mCurrent_user.getUid()).child(user_id).setValue(now()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendDatabase.child(user_id).child(mCurrent_user.getUid()).setValue(now()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    mFriendRequestDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mFriendRequestDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    mprofilesendRequest.setEnabled(true);
                                                    mCurrent_state = "friends";
                                                    mprofilesendRequest.setText("Unfriend");

                                                    mDecline.setVisibility(View.INVISIBLE);
                                                    mDecline.setEnabled(false);
                                                }
                                            });
                                        }
                                    });


                                }
                            });
                        }
                    });
                }

            }
        });



    }


    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }



}


