package global.imas.bintouch;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class delayedmsg extends AppCompatActivity {

    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase, mChatDatabase,mAutoReplyDatabase,mAutoReplyD;
    private FirebaseUser mCurrent_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delayedmsg);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Delayed messages");
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
        mAutoReplyDatabase = FirebaseDatabase.getInstance().getReference().child("Delayed_msg_From_Me").child(uuser_id);
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
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AutoReplyForMe, delayedmsg.UsersViewHolder>(
                AutoReplyForMe.class,
                R.layout.users_single_layout_autoreplymsgs,
                delayedmsg.UsersViewHolder.class,
                mAutoReplyDatabase

        ) {



            @Override
            protected void populateViewHolder(final delayedmsg.UsersViewHolder viewHolder, final AutoReplyForMe model, final int position) {
                // count++;
                //final String user_id = getRef(position).getKey();

                viewHolder.setOn(model.getDateTime());
                viewHolder.setmsg(model.getAuto_reply_msg());
                viewHolder.setfr(model.getList_names());

                String r = "";
                String m = model.getDate_from_m();

                if (m.equals("1")) m = "Jan";
                if (m.equals("2")) m = "Feb";
                if (m.equals("3")) m = "Mar";
                if (m.equals("4")) m = "Apr";
                if (m.equals("5")) m = "May";
                if (m.equals("6")) m = "June";
                if (m.equals("7")) m = "July";
                if (m.equals("8")) m = "Aug";
                if (m.equals("9")) m = "Sep";
                if (m.equals("10")) m = "Oct";
                if (m.equals("11")) m = "Nov";
                if (m.equals("12")) m = "Dec";

                String m2 = model.getDate_to_m();

                if (m2.equals("1")) m2 = "Jan";
                if (m2.equals("2")) m2 = "Feb";
                if (m2.equals("3")) m2 = "Mar";
                if (m2.equals("4")) m2 = "Apr";
                if (m2.equals("5")) m2 = "May";
                if (m2.equals("6")) m2 = "June";
                if (m2.equals("7")) m2 = "July";
                if (m2.equals("8")) m2 = "Aug";
                if (m2.equals("9")) m2 = "Sep";
                if (m2.equals("10")) m2 = "Oct";
                if (m2.equals("11")) m2 = "Nov";
                if (m2.equals("12")) m2 = "Dec";

                String t1 = model.getTime_from_m();
                if(Integer.parseInt(model.getTime_from_m())<=9)
                {
                    t1="0"+model.getTime_from_m();
                }
                String t2 = model.getTime_to_m();
                if(Integer.parseInt(model.getTime_to_m())<=9)
                {
                    t2="0"+model.getTime_to_m();
                }
                viewHolder.setdt(model.getDate_from_d()+" " + m + " "+model.getDate_from_y(),
                        model.getDate_to_d()+" " + m2 + " "+model.getDate_to_y(),
                        model.getTime_from_h()+":" +t1 ,
                        model.getTime_to_h()+":" +t2 );


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String user_id = getRef(position).getKey();

                        final String tdatetime = model.getDateTime();
                        final String tmsg = model.getAuto_reply_msg();

                        final Dialog dialog = new Dialog(delayedmsg.this);
                        dialog.setContentView(R.layout.msglayout_delete_just);
                        dialog.setTitle("Select date:");
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.show();

                        Button Bset = (Button) dialog.findViewById(R.id.button15);

                        Bset.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mAutoReplyDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                        String key = "";
                                        for (DataSnapshot child : children) {
                                            if (child.getValue(AutoReplyForMe.class).getAuto_reply_msg().toString().equals(tmsg))
                                            {
                                                if (child.getValue(AutoReplyForMe.class).getDateTime().toString().equals(tdatetime))
                                                {
                                                    key = child.getKey();

                                                    String x =child.getValue(AutoReplyForMe.class).getList_keys();
                                                    while(x.length()>12)
                                                    {
                                                        String y = x.substring(0,x.indexOf(";;;;;;;;;;"));
                                                        x=x.substring(x.indexOf(";;;;;;;;;;")+10);

                                                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(delayedmsg.this);
                                                        /////////////save////////////////
                                                        //    SharedPreferences.Editor editor = settings.edit();
                                                        //    editor.putString("statepara1", ts);
                                                        //   editor.commit();
                                                        //////////////get//////////////////
                                                        String ret = settings.getString("state_autoreply_list", "");  /// 0 is default if variable not found
                                                        ret = ret.replace(y,"");

                                                        SharedPreferences.Editor editor = settings.edit();
                                                        editor.putString("state_autoreply_list", ret);
                                                        editor.commit();

                                                        String uuser_id = "x123error";
                                                        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(delayedmsg.this);
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

                                                        mAutoReplyD.child(y).child(uuser_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                                //Toast.makeText(autoreply.this,"Deleted!",Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                    }

                                                }
                                            }
                                        }



                                        mAutoReplyDatabase.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {


                                                Toast.makeText(delayedmsg.this,"Deleted!",Toast.LENGTH_LONG).show();
                                                new CountDownTimer(500, 100) {
                                                    @Override
                                                    public void onTick(long l) {

                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        dialog.dismiss();
                                                        Intent startIntent = new Intent(delayedmsg.this, delayedmsg.class);
                                                        startActivity(startIntent);
                                                    }
                                                }.start();
                                            }
                                        });
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

        public void setOn(String x)
        {

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

            r = m + " " + d + ", " + y + " at " + time;

            TextView datetime = mView.findViewById(R.id.textView36);
            datetime.setText(r);

        }

        public void setmsg(String x)
        {
            TextView msg = mView.findViewById(R.id.textView37);
            msg.setText(x);

        }
        public void setdt(String x, String y, String a , String b)
        {
            TextView date = mView.findViewById(R.id.textView39);
            date.setText("Will be sent on "+x);
            TextView time = mView.findViewById(R.id.textView40);
            time.setText("at "+a);
        }
        public void setfr(String x)
        {

            TextView fr = mView.findViewById(R.id.textView41);
            x= x.replace(";;;;;;;;;;",", ");
            x =x.substring(0,x.length()-2);
            fr.setText("To: "+x);


        }
    }
}

