package global.imas.bintouch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class forward_msg extends AppCompatActivity {

    private RecyclerView mUsersList;
    CheckBox CB1;

    String checked = "No";
    private FirebaseUser mCurrent_user;
    String uuser_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forward_msg);

        list.clear();
        list2.clear();
        checked = "No";

        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Forward to");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String dateTime = getIntent().getStringExtra("forward_dateTime");
        final String from = getIntent().getStringExtra("forward_from");
        final String msg_or_url = getIntent().getStringExtra("forward_msg_or_url");
        final String msg_type = getIntent().getStringExtra("forward_msg_type");
        final String sent_received_seen = getIntent().getStringExtra("forward_sent_received_seen");
        final String from_orignal_id = getIntent().getStringExtra("forward_from_orignal_id");
        final String from_orignal_key = getIntent().getStringExtra("forward_from_orignal_key");

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();




        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(this);
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

        CB1 = (CheckBox)findViewById(R.id.checkBox);

        CB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CB1.isChecked())
                {
                    checked = "Yes";
                    firebaseRecyclerAdapter.notifyDataSetChanged();
                }
                else
                {
                    checked = "No";
                    list.clear();
                    list2.clear();
                    firebaseRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });

        LinearLayoutManager ll = new LinearLayoutManager(this);

        mUsersList = (RecyclerView) findViewById(R.id.recy);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(ll);
        mUsersList.setVisibility(View.VISIBLE);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mfriendsCurrentUserDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(uuser_id);
        mfriendsCurrentUserDatabase.keepSynced(true);

        adapter ();

        BSch = (Button)findViewById(R.id.date_time_set);

        mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chats");
        mChatDatabase.keepSynced(true);

        mNotificationDatabase= FirebaseDatabase.getInstance().getReference().child("Notifications");

        mForward= FirebaseDatabase.getInstance().getReference().child("Forward");
        mForward.keepSynced(true);

        BSch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int counn = list.size();

                if(counn<1)
                {
                    Toast.makeText(forward_msg.this, "Select at least one friend", Toast.LENGTH_LONG).show();
                    return;
                }

                final String dateInStringvv  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                SimpleDateFormat sdfv = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cv = Calendar.getInstance();
                try {cv.setTime(sdfv.parse(dateInStringvv));} catch (ParseException e) {e.printStackTrace();}
                cv.add(Calendar.DATE, 0);
                Date resultdatev;
                resultdatev = new Date(cv.getTimeInMillis());
                String svvv =sdfv.format(resultdatev);

                Calendar rightNowv = Calendar.getInstance();
                int currentHourv = rightNowv.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                int currentminv = rightNowv.get(Calendar.MINUTE); //

                String ffpvv = String.valueOf(currentminv).toString();
                if(currentminv<10) ffpvv = "0"+String.valueOf(currentminv).toString();

                String hourvv = String.valueOf(currentHourv).toString() + ":" + ffpvv;

                String DateTime = svvv + hourvv;

                HashMap<String, String> object = new HashMap<String, String>();
                object.put("from", uuser_id);
                object.put("msg_or_url", msg_or_url);
                object.put("msg_type", msg_type);
                object.put("dateTime", DateTime);
                object.put("sent_received_seen", "sent"); //  sent , received, seen
                object.put("from_orignal_id", from_orignal_id);
                object.put("from_orignal_key", from_orignal_key);

                countt = 0;



                mForward.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(from_orignal_id))
                        {
                            mForward.child(from_orignal_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshota) {
                                    if(dataSnapshota.hasChild(from_orignal_key))
                                    {
                                        mForward.child(from_orignal_id).child(from_orignal_key).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshotss) {

                                                int counst = Integer.parseInt(dataSnapshotss.child("forward").getValue(String.class));
                                                counst += list.size();

                                                mForward.child(from_orignal_id).child(from_orignal_key).child("forward").setValue(counst+ "").addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                        HashMap<String, String> object2s = new HashMap<String, String>();
                                        object2s.put("forward", list.size()+"");
                                        object2s.put("deleted", "No");

                                        mForward.child(from_orignal_id).child(from_orignal_key).setValue(object2s).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        else {

                            HashMap<String, String> object2s = new HashMap<String, String>();
                            object2s.put("forward", list.size() + "");
                            object2s.put("deleted", "No");

                            mForward.child(from_orignal_id).child(from_orignal_key).setValue(object2s).addOnSuccessListener(new OnSuccessListener<Void>() {
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

                for(int i =0; i<list.size();i++) {

                    String user_id = list.get(i);


                    if(msg_type.equals("text"))
                    {
                        HashMap<String, String> object2 = new HashMap<String, String>();
                        object2.put("msg_or_url", msg_or_url);

                        mNotificationDatabase.child(uuser_id).child(user_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }

                    else if(msg_type.substring(0,3).equals("ima"))
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

                        String DateTimed = s + hour;

                        HashMap<String, String> object2 = new HashMap<String, String>();
                        object2.put("msg_or_url", "New image ("+DateTimed+")");


                        mNotificationDatabase.child(uuser_id).child(user_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

                    }

                    else
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

                        String DateTimes = s + hour;

                        HashMap<String, String> object2 = new HashMap<String, String>();
                        object2.put("msg_or_url", "New audio message ("+DateTimes+")");


                        mNotificationDatabase.child(uuser_id).child(user_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }

                    mChatDatabase.child(user_id).child(uuser_id).push().setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                    mChatDatabase.child(uuser_id).child(user_id).push().setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            countt++;
                            if(countt==list.size()) {
                                Toast.makeText(forward_msg.this, "Forward done!", Toast.LENGTH_LONG).show();

                                Intent startIntent = new Intent(forward_msg.this, MainActivity.class);
                                startActivity(startIntent);

                            }
                        }
                    });

                }


            }
        });

    }


    int countt = 0;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    int count = 0;
    private DatabaseReference mUsersDatabase, mChatDatabase, mfriendsCurrentUserDatabase,mNotificationDatabase, mForward;

    public static ArrayList<String> list = new ArrayList<String>();
    public static ArrayList<String> list2 = new ArrayList<String>();

    void adapter ()
    {
        count=0;
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Friends, forward_msg.UsersViewHolder>(
                Friends.class,
                R.layout.users_single_layout_autoreply_add,
                forward_msg.UsersViewHolder.class,
                mfriendsCurrentUserDatabase

        ) {


            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Friends model, int position) {

              count++;
                final String user_id = getRef(position).getKey();

                viewHolder.setCheck(checked, user_id, model.getName());
                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setImage(model.getImage(), getApplicationContext());
/*
                mChatDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(uuser_id))
                        {
                            mChatDatabase.child(uuser_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {


                                    if(dataSnapshot.hasChild(user_id))
                                    {


                                        mChatDatabase.child(uuser_id).child(user_id).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                                viewHolder.setCheck(checked, user_id, model.getName());
                                                viewHolder.setName(model.getName());
                                                viewHolder.setStatus(model.getStatus());
                                                viewHolder.setImage(model.getImage(), getApplicationContext());


                                                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {



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
                                        viewHolder.itemView.getLayoutParams().height = 0;
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

*/



            }


        };
        mUsersList.setAdapter(firebaseRecyclerAdapter);
    }

   Button BSch;

    public static class UsersViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setCheck(String x, String userID, String name)
        {
            CheckBox CB = mView.findViewById(R.id.checkBox2);
            CB.setText(userID+";;;;;;;;;;"+name);

            CB.setTextColor(Color.TRANSPARENT);

            CB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox c = (CheckBox)view;

                    String useridd = c.getText().toString().substring(0,c.getText().toString().indexOf(";;;;;;;;;;"));
                    String namee = c.getText().toString().substring(c.getText().toString().indexOf(";;;;;;;;;;")+10);
                    if(c.isChecked())
                    {
                        forward_msg.list.add(useridd);
                        forward_msg.list2.add(namee);



                    }
                    else
                    {
                        forward_msg.list2.remove(useridd);
                        forward_msg.list.remove(namee);
                    }

                }
            });

            if(x.equals("Yes")) {
                CB.setChecked(true);
                forward_msg.list.add(userID);
                forward_msg.list2.add(name);
            }
            else {
                CB.setChecked(false);
            }

        }

        public void setName(String x)
        {
            TextView userNameView = mView.findViewById(R.id.user_single_name);
            userNameView.setText(x);

        }
        public void setStatus(String x)
        {
            TextView userNameView = mView.findViewById(R.id.user_single_status);
            userNameView.setText(x);
        }
        public void setImage(final String x, final Context c)
        {
            final ImageView userNameView = mView.findViewById(R.id.user_single_image);

            Picasso.with(c).load(x).placeholder(R.drawable.pp).into(userNameView);




        }
    }
}
