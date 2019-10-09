package global.imas.bintouch;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewAndDeleteBlocked extends AppCompatActivity {

    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase, mChatDatabase,mAutoReplyDatabase,mAutoReplyD;
    private FirebaseUser mCurrent_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_delete_blocked);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Blocked contacts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chats");
        mChatDatabase.keepSynced(true);

        LinearLayoutManager ll = new LinearLayoutManager(this);
        mUsersList = (RecyclerView) findViewById(R.id.rec1);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(ll);
        mUsersList.setVisibility(View.VISIBLE);

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        String uuser_id = "x123error";
        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor11 = settings11.edit();

        String select11 = settings11.getString("select", "");  /// 0 is default if variable not found

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

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);
        mAutoReplyDatabase = FirebaseDatabase.getInstance().getReference().child("blocked_contacts").child(uuser_id);
        mAutoReplyDatabase.keepSynced(true);
        mAutoReplyD = FirebaseDatabase.getInstance().getReference().child("Delayed_msg");
        mAutoReplyD.keepSynced(true);

        adapter();
    }



    int count=0;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    void adapter ()
    {
        count=0;
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blocked, ViewAndDeleteBlocked.UsersViewHolder>(
                Blocked.class,
                R.layout.users_single_layout_autoreplymsgs2,
                ViewAndDeleteBlocked.UsersViewHolder.class,
                mAutoReplyDatabase

        ) {



            @Override
            protected void populateViewHolder(final ViewAndDeleteBlocked.UsersViewHolder viewHolder, final Blocked model, final int position) {
                // count++;
                final String user_id = getRef(position).getKey();

                viewHolder.setName(model.getName());
                viewHolder.setKey(model.getKey());




                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String user_id = getRef(position).getKey();

                        final String tdatetime = model.getName();
                        final String tmsg = model.getKey();

                        final Dialog dialog = new Dialog(ViewAndDeleteBlocked.this);
                        dialog.setContentView(R.layout.msglayout_delete_just);
                        dialog.setTitle("Select date:");
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.show();

                        Button Bset = (Button) dialog.findViewById(R.id.button15);
                        Bset.setText("Unblock");

                        Bset.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mAutoReplyDatabase.child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ViewAndDeleteBlocked.this, "Unblocked!", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                });


                            }
                        });





                    }
                });



            }


        };

        mUsersList.setAdapter(firebaseRecyclerAdapter);
    }



    public static class UsersViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String x)
        {

            TextView t = mView.findViewById(R.id.textView37);
            t.setText(x);

        }

        public void setKey(String x)
        {
            TextView tk = mView.findViewById(R.id.textView39);
            tk.setText(x);

        }

    }
}

