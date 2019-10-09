package global.imas.bintouch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
public class Add_person_to_group extends AppCompatActivity {

    String msg = "";
    private RecyclerView mUsersList;
    String uuser_id = "x";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person_to_group);





        LinearLayoutManager ll = new LinearLayoutManager(this);
        mUsersList = (RecyclerView) findViewById(R.id.listt);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(ll);
        mUsersList.setVisibility(View.VISIBLE);

        Group_name  = getIntent().getStringExtra("group_name");
        passito  = getIntent().getStringExtra("passito");

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();


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
        mUsersDatabaseo = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabaseo.keepSynced(true);


        mfriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mfriendsDatabase.keepSynced(true);

        mGroupsDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");
        mGroupsDatabase.keepSynced(true);

        mfriendsCurrentUserDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(uuser_id);
        mfriendsCurrentUserDatabase.keepSynced(true);

        mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chats");
        mChatDatabase.keepSynced(true);

        adapter();

    }

    String passito, Group_name;
    private FirebaseUser mCurrent_user;
    private DatabaseReference mfriendsCurrentUserDatabase,mGroupsDatabase,mUsersDatabaseo,mChatDatabase;


    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    void adapter ()
    {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Friends, Add_person_to_group.UsersViewHolder>(
                Friends.class,
                R.layout.users_single_layout,
                Add_person_to_group.UsersViewHolder.class,
                mfriendsCurrentUserDatabase

        ) {
            @Override
            protected void populateViewHolder(final Add_person_to_group.UsersViewHolder viewHolder, final Friends model, final int position) {

                final String user_id = getRef(position).getKey();






                viewHolder.setid(user_id);
                viewHolder.setCount(model.getCount());
                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setImage(model.getImage(), getApplicationContext());



                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mRegProgress=new ProgressDialog(Add_person_to_group.this);
                        mRegProgress.setTitle("Adding friend");
                        // mRegProgress.setMessage("Please wait while creating your account");
                        mRegProgress.setCanceledOnTouchOutside(false);
                        mRegProgress.show();

                        if(user_id.substring(0,25).equals("GroupsBinTouchBestAppEver"))
                        {
                            Toast.makeText(context, "You cannot add a group to a group!", Toast.LENGTH_LONG).show();
                            mRegProgress.dismiss();

                        }
                        else {
                            if (!user_id.equals(uuser_id)) {






                                            mGroupsDatabase.child(passito).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String Participants = dataSnapshot.child("Participants").getValue(String.class);
                                                    Participants += user_id + ";;;;;;;;;;";

                                                    String image = dataSnapshot.child("Image").getValue(String.class);
                                                    String status = dataSnapshot.child("Status").getValue(String.class);

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


                                                    final HashMap<String, String> userMap = new HashMap<>();
                                                    userMap.put("count", DateTime+"0");
                                                    userMap.put("image", image);
                                                    userMap.put("name", Group_name);
                                                    userMap.put("status", status);

                                                    mGroupsDatabase.child(passito).child("Participants").setValue(Participants).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            mfriendsDatabase.child(user_id).child(passito).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    if(task.isSuccessful()) {





                                                                                mUsersDatabaseo.child(uuser_id).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                        String namee = dataSnapshot.getValue(String.class);
                                                                                        msg = "<b>" +model.getName()+ " is added to the group" + "</b>";

                                                                                        mGroupsDatabase.child(passito).child("Participants").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                            @Override
                                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                String Participants = dataSnapshot.getValue(String.class);
                                                                                                while (Participants.length() > 12) {
                                                                                                    String keyy = Participants.substring(0, Participants.indexOf(";;;;;;;;;;"));
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

                                                                                                    HashMap<String, String> object = new HashMap<String, String>();
                                                                                                    object.put("from", uuser_id);
                                                                                                    object.put("msg_or_url", msg);
                                                                                                    object.put("msg_type", "text");
                                                                                                    object.put("dateTime", DateTime);
                                                                                                    object.put("sent_received_seen", "sent"); //  sent , received, seen


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


                                                                                    }

                                                                                    @Override
                                                                                    public void onCancelled(DatabaseError databaseError) {

                                                                                    }
                                                                                });








                                                            mRegProgress.dismiss();
                                                            Intent startIntent = new Intent (Add_person_to_group.this, Create_Account.class);
                                                            startIntent.putExtra("group_name", Group_name);
                                                            startIntent.putExtra("passito", passito);
                                                            startActivity(startIntent);

                                                                    }
                                                                }
                                                            });
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });









                            } else {
                                Toast.makeText(context, "This is just for test. You cannot chat with your self.", Toast.LENGTH_LONG).show();
                                mRegProgress.dismiss();
                            }
                        }

                    }
                });








            }


        };
        mUsersList.setAdapter(firebaseRecyclerAdapter);
    }

    private DatabaseReference mfriendsDatabase;
    ProgressDialog mRegProgress;
    Context context = this;
    public static class UsersViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String x)
        {
            TextView userNameView = mView.findViewById(R.id.user_single_name);
            userNameView.setText(x);
            //   TextView userNameViews = mView.findViewById(R.id.textView15);
            //   userNameViews.setText(count);
        }
        public void setid(String x)
        {
            TextView userNameView = mView.findViewById(R.id.textView43);
            userNameView.setText(x);
        }
        public void setStatus(String x)
        {
            TextView userNameView = mView.findViewById(R.id.user_single_status);
            userNameView.setText(x);
        }
        public void setCount(String x)
        {
            if(x.equals("0")) x ="";
            TextView userNameViews = mView.findViewById(R.id.textView15);
            userNameViews.setText(x);
        }
        public void setImage(final String x, final Context c)
        {
            final ImageView userNameView = mView.findViewById(R.id.user_single_image);

            Picasso.with(c).load(x).placeholder(R.drawable.pp).into(userNameView);




        }
    }


}
