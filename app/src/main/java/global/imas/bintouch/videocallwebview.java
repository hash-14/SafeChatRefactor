package global.imas.bintouch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class videocallwebview extends AppCompatActivity {

    String ss="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videocallwebview);

        Button B = (Button)findViewById(R.id.button83);
        WebView webView = (WebView) findViewById(R.id.webv);

        final String messa = getIntent().getStringExtra("messa");
        if(!messa.equals("x"))
        {


            FirebaseUser mCurrent_user;
            mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference mUsersDatabase;
            mUsersDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

            DatabaseReference mVideoRoomDatabase;
            mVideoRoomDatabase= FirebaseDatabase.getInstance().getReference().child("Video_Call_Rooms");



            final String user_idss = messa.substring(messa.indexOf(";;;;;;;;;;")+10);
            final String user_id = user_idss.substring(0,user_idss.indexOf(";;;;;;;;;;"));
            ss = user_idss.substring(user_idss.indexOf(";;;;;;;;;;")+10);

            mUsersDatabase.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    final String user_name = dataSnapshot.child("name").getValue().toString();
                    final String user_image = dataSnapshot.child("image").getValue().toString();

                    mVideoRoomDatabase.child(user_id).child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.loadUrl("https://www.gruveo.com/" + dataSnapshot.child("room").getValue(String.class));

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




            B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   finish();
                }
            });
        }
        else {
            final String room = getIntent().getStringExtra("roomfire");
            final String user_id = getIntent().getStringExtra("user_id");
            final String user_image = getIntent().getStringExtra("user_image");

            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://www.gruveo.com/" + room);

            B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent startIntent = new Intent(videocallwebview.this, activity_chats.class);
                    startIntent.putExtra("user_id", user_id);
                    startIntent.putExtra("user_image", user_image);
                    startActivity(startIntent);
                }
            });
        }


    }
}
