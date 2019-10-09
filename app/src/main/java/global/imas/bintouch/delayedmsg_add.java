package global.imas.bintouch;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
public class delayedmsg_add extends AppCompatActivity {

    int ii=0;
    Button Bdatefrom, Btime, Bdateto, Btimefrom, Btimeto, BSch;
    Context c = this;
    CheckBox CB1;
    int countt=0;


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

    EditText Emsg;

    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase, mfriendsCurrentUserDatabase, mChatDatabase,mAutoReplyDatabase,mAutoReplyDatabaseFromMe;
    String checked = "No";
    int count = 0;
    private FirebaseUser mCurrent_user;

    public static ArrayList<String> list = new ArrayList<String>();
    public static ArrayList<String> list2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delayedmsg_add);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Delayed message");
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



                Calendar calendar = Calendar.getInstance();
                date_to_d = calendar.get(Calendar.DAY_OF_MONTH);
                date_to_y = calendar.get(Calendar.YEAR);
                date_to_m = calendar.get(Calendar.MONTH);

                if(date_from_y<date_to_y)
                {
                    Toast.makeText(delayedmsg_add.this,"Date cannot be less than today" + date_from_y + " " +date_to_y, Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    if(date_from_m<date_to_m)
                    {
                        Toast.makeText(delayedmsg_add.this,"Date cannot be less than today"+date_from_m + " " +date_to_m, Toast.LENGTH_LONG).show();
                        return;
                    }
                    else
                    {
                        if(date_from_d<date_to_d)
                        {
                            Toast.makeText(delayedmsg_add.this,"Date cannot be less than today"+date_from_d + " " +date_to_d, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }


                if(Emsg.getText().toString().equals(""))
                {
                    Toast.makeText(delayedmsg_add.this, "Empty message cannot be scheduled!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(list.isEmpty())
                {
                    Toast.makeText(delayedmsg_add.this, "Select at least one friend!", Toast.LENGTH_LONG).show();
                    return;
                }


                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(delayedmsg_add.this);
                /////////////save////////////////
                //    SharedPreferences.Editor editor = settings.edit();
                //    editor.putString("statepara1", ts);
                //   editor.commit();
                //////////////get//////////////////
                String ret = settings.getString("state_autoreply_list", "");  /// 0 is default if variable not found


                String dateInString  = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
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

                String llist = "";
                String llist2 = "";

                for(int i =0; i<list.size();i++) {
                    count++;
                    llist += list.get(i) + ";;;;;;;;;;";
                    llist2 += list2.get(i) + ";;;;;;;;;;";
                    ret +=list.get(i)+" " ;
                }

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("state_autoreply_list", ret);
                editor.commit();

                HashMap<String, String> object2 = new HashMap<String, String>();
                HashMap<String, String> object22 = new HashMap<String, String>();


                String uusser_id = "x123error";
                SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(delayedmsg_add.this);
                SharedPreferences.Editor editor11 = settings11.edit();

                String select11 = settings11.getString("select", "");  /// 0 is default if variable not found

                if (select11.equals("1")) {
                    uusser_id = mCurrent_user.getUid() + ";;;;;profile_1";
                }
                if (select11.equals("2")) {
                    uusser_id = mCurrent_user.getUid() + ";;;;;profile_2";
                }
                if (select11.equals("3")) {
                    uusser_id = mCurrent_user.getUid() + ";;;;;profile_3";
                }
                if (select11.equals("4")) {
                    uusser_id = mCurrent_user.getUid() + ";;;;;profile_4";
                }
                if (select11.equals("5")) {
                    uusser_id = mCurrent_user.getUid() + ";;;;;profile_5";
                }

                object2.put("from", uusser_id);
                object2.put("auto_reply_msg", Emsg.getText().toString());
                object2.put("dateTime", DateTime);
                object2.put("date_from_d", date_from_d +"");
                object2.put("date_from_m", date_from_m +"");
                object2.put("date_from_y", date_from_y +"");
                object2.put("date_to_d", date_to_d +"");
                object2.put("date_to_m", date_to_m +"");
                object2.put("date_to_y", date_to_y +"");
                object2.put("time_from_h",time_from_h +"");
                object2.put("time_from_m",time_from_m +"");
                object2.put("time_to_h",time_to_h +"");
                object2.put("time_to_m",time_to_m +"");


                object22.put("from", uusser_id);
                object22.put("auto_reply_msg", Emsg.getText().toString());
                object22.put("dateTime", DateTime);
                object22.put("date_from_d", date_from_d +"");
                object22.put("date_from_m", date_from_m +"");
                object22.put("date_from_y", date_from_y +"");
                object22.put("date_to_d", date_to_d +"");
                object22.put("date_to_m", date_to_m +"");
                object22.put("date_to_y", date_to_y +"");
                object22.put("time_from_h",time_from_h +"");
                object22.put("time_from_m",time_from_m +"");
                object22.put("time_to_h",time_to_h +"");
                object22.put("time_to_m",time_to_m +"");
                object22.put("list_keys",llist);
                object22.put("list_names",llist2);

                String givenDateString = date_from_m+" "+ date_from_d+" "+date_from_y+" "+time_from_h+":"+time_from_m;
                SimpleDateFormat sdfd = new SimpleDateFormat("MM dd yyyy HH:mm");
                long timeInMilliseconds = 0;
                try {
                    Date mDate = sdfd.parse(givenDateString);
                     timeInMilliseconds = mDate.getTime();
                    System.out.println("Date in milli :: " + timeInMilliseconds);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long ccc= timeInMilliseconds;

                countt = 0;
                mAutoReplyDatabaseFromMe.child(uusser_id).push().setValue(object22).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                    }
                });

                for(int i =0; i<list.size();i++) {



                    String user_id = list.get(i);


                    mAutoReplyDatabase.child(user_id).child(uusser_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            countt++;
                            if(countt==list.size())
                            {
                                Toast.makeText(delayedmsg_add.this, "Scheduling message done!", Toast.LENGTH_LONG).show();
/////////////////////////////////////////////////////////////
                                AlarmManager alarms = (AlarmManager)delayedmsg_add.this.getSystemService(Context.ALARM_SERVICE);

                                Receiver receiver = new Receiver();
                                IntentFilter filter = new IntentFilter("ALARM_ACTION");
                                registerReceiver(receiver, filter);

                                Intent intent = new Intent("ALARM_ACTION");
                                intent.putExtra("param", "BnTouch: My scheduled message is sent");
                                PendingIntent operation = PendingIntent.getBroadcast(delayedmsg_add.this, 0, intent, 0);
                                // I choose 3s after the launch of my application
                                alarms.set(AlarmManager.RTC_WAKEUP, ccc, operation) ;

                                if (Build.VERSION.SDK_INT >= 23) {
                                    alarms.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, ccc, operation);
                                } else if (Build.VERSION.SDK_INT >= 19) {
                                    alarms.setExact(AlarmManager.RTC_WAKEUP, ccc, operation);
                                } else {
                                    alarms.set(AlarmManager.RTC_WAKEUP, ccc, operation);
                                }
/////////////////////////////////////////////////
                                Intent startIntent = new Intent(delayedmsg_add.this, delayedmsg.class);
                                startActivity(startIntent);
                            }

                        }
                    });

                }









            }
        });


/*
        new CountDownTimer(600000, 5000) {
            @Override
            public void onTick(long l) {
                String ss = "";
                for(int i =0;i<delayedmsg_add.list.size();i++)
                    ss +=delayedmsg_add.list.get(i) + "\n";
                Toast.makeText(delayedmsg_add.this, ss, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinish() {

            }
        };
*/
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

                final Dialog dialog = new Dialog(delayedmsg_add.this);
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
                final Dialog dialog = new Dialog(delayedmsg_add.this);
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
                final Dialog dialog = new Dialog(delayedmsg_add.this);
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
                final Dialog dialog = new Dialog(delayedmsg_add.this);
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
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Friends, delayedmsg_add.UsersViewHolder>(
                Friends.class,
                R.layout.users_single_layout_autoreply_add,
                delayedmsg_add.UsersViewHolder.class,
                mfriendsCurrentUserDatabase

        ) {
            @Override
            protected void populateViewHolder(final delayedmsg_add.UsersViewHolder viewHolder, final Friends model, final int position) {
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
                        delayedmsg_add.list.add(useridd);
                        delayedmsg_add.list2.add(namee);



                    }
                    else
                    {
                        delayedmsg_add.list2.remove(useridd);
                        delayedmsg_add.list.remove(namee);
                    }

                }
            });

            if(x.equals("Yes")) {
                CB.setChecked(true);
                delayedmsg_add.list.add(userID);
                delayedmsg_add.list2.add(name);
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
