package global.imas.bintouch;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class AddBlockPerson extends AppCompatActivity {

    int ii=0;
    Button Bdatefrom, Btime, Bdateto, Btimefrom, Btimeto, BSch;
    Context c = this;
    CheckBox CB1;
    int countt=0;
    String uuser_id = "x123error_addBlockPersonClass";

    int date_from_y = 2022;
    int date_from_m = 9;
    int date_from_d = 20;
    int date_to_y = 2022;
    int date_to_m = 9;
    int date_to_d = 25;
    int time_from_h = 10;
    int time_from_m = 0;
    int time_to_h = 11;
    int time_to_m = 30;
    String llist = "";
    String llist2 = "";
    EditText Emsg;

    private RecyclerView mUsersList;
    private DatabaseReference mdata, mUsersDatabase, mfriendsCurrentUserDatabase, mChatDatabase,mAutoReplyDatabase,mAutoReplyDatabaseFromMe;
    String checked = "No";
    int count = 0;
    private FirebaseUser mCurrent_user;

    public static ArrayList<String> list = new ArrayList<String>();
    public static ArrayList<String> list2 = new ArrayList<String>();
   ProgressDialog mRegProgress;
    Context context  = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_block_person);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Block contacts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list.clear();
        list2.clear();
        checked = "No";

        mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chats");
        mChatDatabase.keepSynced(true);

        LinearLayoutManager ll = new LinearLayoutManager(this);
        mUsersList = (RecyclerView) findViewById(R.id.recy);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(ll);
        mUsersList.setVisibility(View.VISIBLE);

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mAutoReplyDatabase = FirebaseDatabase.getInstance().getReference().child("Delayed_msg");
        mAutoReplyDatabaseFromMe = FirebaseDatabase.getInstance().getReference().child("Delayed_msg_From_Me");

        mdata = FirebaseDatabase.getInstance().getReference();
        mdata.keepSynced(true);

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();


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

        mfriendsCurrentUserDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(uuser_id);
        mfriendsCurrentUserDatabase.keepSynced(true);

        adapter();

        CB1 = (CheckBox)findViewById(R.id.checkBox);
        Bdatefrom = (Button)findViewById(R.id.button16);
        Btimefrom = (Button)findViewById(R.id.button17);
        Bdateto = (Button)findViewById(R.id.button16s);
        Btimeto = (Button)findViewById(R.id.button17s);

        Emsg = (EditText) findViewById(R.id.editText5);

        BSch = (Button)findViewById(R.id.date_time_set);

        BSch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mRegProgress=new ProgressDialog(AddBlockPerson.this);
                mRegProgress.setTitle("Blocking...");
                // mRegProgress.setMessage("Please wait while creating your account");
                mRegProgress.setCanceledOnTouchOutside(false);
                mRegProgress.show();


                mdata.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("blocked_contacts"))
                        {
                            mdata.child("blocked_contacts").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(uuser_id))
                                    {
                                        Iterable<DataSnapshot> children = dataSnapshot.child(uuser_id).getChildren();

                                        for (DataSnapshot child : children) {
                                            if (list.contains(child.child("key").getValue(String.class))) {
                                                mdata.child("blocked_contacts").child(uuser_id).child(child.getKey()).removeValue();
                                            }
                                        }

                                        for(int i =0; i<list.size();i++) {

                                            HashMap<String, String> userMap = new HashMap<>();
                                            userMap.put("name", list2.get(i));
                                            userMap.put("key", list.get(i));

                                            mdata.child("blocked_contacts").child(uuser_id).push().setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });
                                            if(i==list.size()-1) {
                                                mRegProgress.dismiss();
                                                Toast.makeText(context, "blocked!", Toast.LENGTH_SHORT).show();
                                            }
                                        }



                                    }
                                    else
                                    {
                                        for(int i =0; i<list.size();i++) {

                                            HashMap<String, String> userMap = new HashMap<>();
                                            userMap.put("name", list2.get(i));
                                            userMap.put("key", list.get(i));

                                            mdata.child("blocked_contacts").child(uuser_id).push().setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });
                                            if(i==list.size()-1) {
                                                mRegProgress.dismiss();
                                                Toast.makeText(context, "blocked!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        else
                        {

                            for(int i =0; i<list.size();i++) {

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", list2.get(i));
                            userMap.put("key", list.get(i));

                            mdata.child("blocked_contacts").child(uuser_id).push().setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                                if(i==list.size()-1) {
                                    mRegProgress.dismiss();
                                    Toast.makeText(context, "blocked!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });







            }
        });


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


        Bdatefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final Dialog dialog = new Dialog(AddBlockPerson.this);
                dialog.setContentView(R.layout.msglayout_date);
                dialog.setTitle("Select date:");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();

                Button Bset = (Button) dialog.findViewById(R.id.button166);

                Bset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                5,
                                5);

                        date_from_y = datePicker.getYear();
                        date_from_m = datePicker.getMonth()+1;
                        date_from_d = datePicker.getDayOfMonth();


                        // long time = calendar.getTimeInMillis();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date resultdate;
                        resultdate = new Date(calendar.getTimeInMillis());
                        String s =sdf.format(resultdate);

                        String r = "";
                        String m = s.substring(5, 7);
                        String d = s.substring(8);
                        String y = s.substring(0, 4);

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

                        Bdatefrom.setText(r);
                        dialog.dismiss();

                    }
                });

            }
        });
        Btimefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AddBlockPerson.this);
                dialog.setContentView(R.layout.msglayout_time);
                dialog.setTitle("Select time:");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();

                Button Bset = (Button) dialog.findViewById(R.id.button166);

                Bset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.datePicker);

                        Calendar calendar = new GregorianCalendar(2020,
                                5,
                                5,
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());


                        time_from_h = timePicker.getCurrentHour();
                        time_from_m = timePicker.getCurrentMinute();


                        // long time = calendar.getTimeInMillis();
                        int currentHour = calendar.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                        int currentmin = calendar.get(Calendar.MINUTE); //

                        String ss= ""+currentmin ;
                        if(currentmin<10)
                            ss="0"+currentmin;


                        Btimefrom.setText(currentHour + ":"+ ss);
                        dialog.dismiss();

                    }
                });

            }
        });

        Bdateto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AddBlockPerson.this);
                dialog.setContentView(R.layout.msglayout_date);
                dialog.setTitle("Select date:");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();

                Button Bset = (Button) dialog.findViewById(R.id.button166);

                Bset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                5,
                                5);

                        date_to_y = datePicker.getYear();
                        date_to_m = datePicker.getMonth() +1;
                        date_to_d = datePicker.getDayOfMonth();



                        // long time = calendar.getTimeInMillis();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date resultdate;
                        resultdate = new Date(calendar.getTimeInMillis());
                        String s =sdf.format(resultdate);

                        String r = "";
                        String m = s.substring(5, 7);
                        String d = s.substring(8);
                        String y = s.substring(0, 4);

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

                        Bdateto.setText(r);
                        dialog.dismiss();

                    }
                });

            }
        });
        Btimeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AddBlockPerson.this);
                dialog.setContentView(R.layout.msglayout_time);
                dialog.setTitle("Select time:");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();

                Button Bset = (Button) dialog.findViewById(R.id.button166);

                Bset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.datePicker);

                        Calendar calendar = new GregorianCalendar(2020,
                                5,
                                5,
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());

                        time_to_h = timePicker.getCurrentHour();
                        time_to_m = timePicker.getCurrentMinute();

                        // long time = calendar.getTimeInMillis();
                        int currentHour = calendar.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                        int currentmin = calendar.get(Calendar.MINUTE); //


                        String ss= ""+currentmin ;
                        if(currentmin<10)
                            ss="0"+currentmin;

                        Btimeto.setText(currentHour + ":"+ ss);
                        dialog.dismiss();

                    }
                });

            }
        });

    }

    FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    void adapter ()
    {
        count=0;
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Friends, AddBlockPerson.UsersViewHolder>(
                Friends.class,
                R.layout.users_single_layout_autoreply_add,
                AddBlockPerson.UsersViewHolder.class,
                mfriendsCurrentUserDatabase

        ) {
            @Override
            protected void populateViewHolder(final AddBlockPerson.UsersViewHolder viewHolder, final Friends model, final int position) {
                count++;
                final String user_id = getRef(position).getKey();

                viewHolder.setCheck(checked, user_id, model.getName());
                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setImage(model.getImage(), getApplicationContext());

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
                        AddBlockPerson.list.add(useridd);
                        AddBlockPerson.list2.add(namee);



                    }
                    else
                    {
                        AddBlockPerson.list2.remove(useridd);
                        AddBlockPerson.list.remove(namee);
                    }

                }
            });

            if(x.equals("Yes")) {
                CB.setChecked(true);
                AddBlockPerson.list.add(userID);
                AddBlockPerson.list2.add(name);
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
