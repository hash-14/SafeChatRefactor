package global.imas.bintouch;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ViewProfileFriends extends AppCompatActivity {

    private DatabaseReference mUsersDatabase,mPublicProfileDatabase;

    ImageView IV;
    TextView Tname, Tstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        IV = (ImageView)findViewById(R.id.imageView2);
        Tname = (TextView)findViewById(R.id.profile_display_name);

        Tstatus = (TextView)findViewById(R.id.profile_status);
        Tstatus.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        Tstatus.setMarqueeRepeatLimit(-1);
        Tstatus.setSingleLine(true);
        Tstatus.setSelected(true);

        final String user_id = getIntent().getStringExtra("user_id");

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mUsersDatabase.keepSynced(true);

        mUsersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tname.setText(dataSnapshot.child("name").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mPublicProfileDatabase = FirebaseDatabase.getInstance().getReference().child("Profile_friends");
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

                        Picasso.with(ViewProfileFriends.this).load(dataSnapshot.child("image").getValue().toString()).placeholder(R.drawable.errf).into(IV);

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
                    Tname.setText("NA");
                    Tstatus.setText("NA");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    MediaPlayer mp;

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
