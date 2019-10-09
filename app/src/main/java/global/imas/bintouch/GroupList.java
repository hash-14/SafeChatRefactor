package global.imas.bintouch;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class GroupList extends AppCompatActivity {

    private RecyclerView mUsersList;
    private FirebaseUser mCurrent_user;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    Context context = this;
    Dialog dialog;
    EditText EE;
    private DatabaseReference mdata, mUsersDatabase, mChatDatabase, mGroupsDatabase,mfriendsDatabase,mfriendsCurrentUserDatabase;
    EditText Esearch;
    Button Badd;
    String uuser_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Group list");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();


        LinearLayoutManager ll = new LinearLayoutManager(this);
        mUsersList = (RecyclerView) findViewById(R.id.recyclerView);
        mUsersList.setHasFixedSize(true);
        ll.setReverseLayout(true);
        ll.setStackFromEnd(true);
        mUsersList.setLayoutManager(ll);
        mUsersList.setVisibility(View.VISIBLE);
        mUsersList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chats");
        mChatDatabase.keepSynced(true);

        mfriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mfriendsDatabase.keepSynced(true);

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();


        String uuser_id = "x123error";
        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

        mdata = FirebaseDatabase.getInstance().getReference();
        mdata.keepSynced(true);

        adapter();

        Badd= (Button)findViewById(R.id.button19);
        Badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Badd.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Badd.setAlpha(1.0F);

                          dialog = new Dialog(GroupList.this);
                        dialog.setContentView(R.layout.msglayout_create_group);
                        dialog.setTitle("Please select :");

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        Button BB = (Button) dialog.findViewById(R.id.button15);
                          EE = (EditText) dialog.findViewById(R.id.editText8);

                        dialog.show();


                        BB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                creategroup();
                            }
                        });
                    }
                }.start();
            }
        });

        Esearch = (EditText) findViewById(R.id.editText4);

        Esearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                firebaseRecyclerAdapter.notifyDataSetChanged();
            }
        });

        Button Baudio = (Button)findViewById(R.id.button14);

        Baudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Baudio.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Baudio.setAlpha(1.0F);
                    }
                }.start();


                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, String.valueOf(this));
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, search by voice (don't click anywhere, just speak)");

                try {
                    startActivityForResult(intent, 102);
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(), "Your device doesn't support Speech to Text!", Toast.LENGTH_SHORT);
                    t.show();
                }

            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(GroupList.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Toast.makeText(GroupList.this, swipeDir+"on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView

                final Dialog dialog = new Dialog(GroupList.this);
                dialog.setContentView(R.layout.msglayout_delete_withmsg);
                dialog.setTitle("Please select :");

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Button BB = (Button) dialog.findViewById(R.id.button15);
                Button BB2 = (Button) dialog.findViewById(R.id.button46);
                TextView TT = (TextView) dialog.findViewById(R.id.textView42);

                //   TextView TT2 = (TextView) findViewById(R.id.user_single_name);
                final int positions = viewHolder.getAdapterPosition();
                String name = ((TextView) mUsersList.findViewHolderForAdapterPosition(positions).itemView.findViewById(R.id.user_single_name)).getText().toString();
                final String kkey = ((TextView) mUsersList.findViewHolderForAdapterPosition(positions).itemView.findViewById(R.id.textView43)).getText().toString();

                TT.setText(name);
                dialog.show();

                firebaseRecyclerAdapter.notifyDataSetChanged();

                BB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        final int position = viewHolder.getAdapterPosition();

                        String uuser_id = "";
                        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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
                        mfriendsDatabase.child(uuser_id).child(kkey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                firebaseRecyclerAdapter.notifyDataSetChanged();
                            }
                        });

                        mChatDatabase.child(uuser_id).child(kkey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

                        //  firebaseRecyclerAdapter.notifyItemRemoved(position);
                    }
                });
                BB2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        Intent startIntent = new Intent(GroupList.this, ViewProfileFriends.class);
                        startIntent.putExtra("user_id", kkey);
                        startActivity(startIntent);


                    }
                });

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mUsersList);


    }

    private ProgressDialog mRegProgress;
    String passito;

    void creategroup()
    {

        String uuser_id = "x123error";
        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

            if(EE.getText().toString().equals(""))
            {
                Toast.makeText(context, "Enter the group name", Toast.LENGTH_SHORT).show();
                return;
            }

            dialog.dismiss();
            mRegProgress=new ProgressDialog(GroupList.this);
            mRegProgress.setTitle("Creating group");
            // mRegProgress.setMessage("Please wait while creating your account");
            mRegProgress.setCanceledOnTouchOutside(false);
            mRegProgress.show();

            HashMap<String, String> userMap = new HashMap<>();
            userMap.put("Name", EE.getText().toString());
            userMap.put("Participants", uuser_id+";;;;;;;;;;");
            userMap.put("Admin", uuser_id);
            userMap.put("Image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/user-group-icon.png?alt=media&token=772e44b3-9d93-4b99-a96d-09f6790de324");
            userMap.put("Status", "");

            StringBuilder b = new StringBuilder();
            Random r = new Random();
            String subset = "123456789ab!cdefghijklmnpqrstuvwxyzQWERTYU!IPLKJHGFDSAZXCVBNM!-?-";
            for (int i = 0; i < 8; i++) {
                int index = r.nextInt(subset.length());
                char c = subset.charAt( index );
                b.append( c );
            }
            passito="GroupsBinTouchBestAppEver"+uuser_id+b.toString();

        mGroupsDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");
        mGroupsDatabase.keepSynced(true);

            mGroupsDatabase.child(passito).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {


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

                        int count = 0;


                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("count", DateTime+count);
                        userMap.put("image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/user-group-icon.png?alt=media&token=772e44b3-9d93-4b99-a96d-09f6790de324");
                        userMap.put("name", EE.getText().toString());
                        userMap.put("status", "");

                        String uuser_id = "x123error";
                        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
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

                        mfriendsDatabase.child(uuser_id).child(passito).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()) {

                                    mRegProgress.dismiss();
                                /*    Intent startIntent = new Intent (GroupList.this, Create_Account.class);
                                    startIntent.putExtra("group_name", EE.getText().toString());
                                    startIntent.putExtra("passito", passito);
                                    startActivity(startIntent);
                                    */
                                }
                            }
                        });



                    }
                }
            });

    }


    void adapter()
    {


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Friends, GroupList.UsersViewHolder>(
                Friends.class,
                R.layout.users_single_layout,
                GroupList.UsersViewHolder.class,
                mfriendsCurrentUserDatabase.orderByChild("count")

        ) {
            @Override
            protected void populateViewHolder(final GroupList.UsersViewHolder viewHolder, final Friends model, final int position)
            {

                final String user_id = getRef(position).getKey();

                if (model.getName().equals("")) {
                    viewHolder.mView.setVisibility(View.GONE);
                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                } else if (!Esearch.getText().toString().equals("")) {
                    if (!(model.getName().toLowerCase().contains(Esearch.getText().toString().toLowerCase()) )) {
                        viewHolder.mView.setVisibility(View.GONE);
                        viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                    } else {
                        viewHolder.mView.setVisibility(View.VISIBLE);
                        viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                } else {
                    viewHolder.mView.setVisibility(View.VISIBLE);
                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }

                if(!user_id.substring(0,25).equals("GroupsBinTouchBestAppEver"))
                {
                    viewHolder.mView.setVisibility(View.GONE);
                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }


                final String dateInString  = new android.icu.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                android.icu.text.SimpleDateFormat sdf = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
                android.icu.util.Calendar c = android.icu.util.Calendar.getInstance();
                try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                c.add(android.icu.util.Calendar.DATE, 0);
                Date resultdate;
                resultdate = new Date(c.getTimeInMillis());
                String s =sdf.format(resultdate);
                android.icu.util.Calendar rightNow = android.icu.util.Calendar.getInstance();
                int currentHour = rightNow.get(android.icu.util.Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                int currentmin = rightNow.get(android.icu.util.Calendar.MINUTE); //
                String ffp = String.valueOf(currentmin).toString();
                if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                String DateTime = s + hour;

                String ccount = model.getCount().substring(15);
                String ddate = model.getCount().substring(0,15);

                String y = ddate.substring(0,4);
                String m = ddate.substring(5,7);
                String d = ddate.substring(8,10);

                String h = ddate.substring(10,12);
                String mi = ddate.substring(13,15);

                if(DateTime.substring(0,10).equals(model.getCount().substring(0,10)))
                {
                    ddate = h + ":" + mi;
                }
                else
                {
                    ddate = d + "/" + m+ "/"+y;
                }


                if(ccount.equals("0")) {
                    ccount = "";
                    ddate = "<font color=\"#333333\" size=\"3\">"+ ddate;
                }
                else
                {   ddate = "<font color=\"#00bb00\" size=\"3\">"+ ddate;
                    ccount = "</font><b><font color=\"#00bb00\" size=\"5\">"+ ccount;
                }
                String cd = ddate + "<br><br>" + ccount;

                viewHolder.setCount(cd,ccount);



                viewHolder.setOffline();
                viewHolder.setid(user_id);
               // viewHolder.setCount(model.getCount());
                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());

                mdata.child("Profile_friends").child(user_id).child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshots) {
                        viewHolder.setImage(dataSnapshots.getValue(String.class), getApplicationContext());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(user_id.substring(0,25).equals("GroupsBinTouchBestAppEver"))
                        {
                            Intent startIntent = new Intent (GroupList.this, Create_Account.class);
                            startIntent.putExtra("group_name", model.getName());
                            startIntent.putExtra("passito", user_id);
                            startActivity(startIntent);
                        }
                        else {


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

                            if (!user_id.equals(uuser_id)) {


                                int f = 0;

                                if (ActivityCompat.checkSelfPermission(GroupList.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(GroupList.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                                    f++;
                                }
                                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.RECORD_AUDIO},0);
                                    f++;
                                }

                                if(f==0) {

                                    mdata.child("blocked_contacts").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.hasChild(user_id))
                                            {
                                                mdata.child("blocked_contacts").child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Iterable < DataSnapshot > children = dataSnapshot.getChildren();
                                                        int cc = 0;
                                                        for (DataSnapshot child : children) {
                                                            if (uuser_id.equals(child.child("key").getValue(String.class))) {
                                                                cc++;
                                                            }
                                                        }
                                                        if(cc == 0)

                                                        {
                                                            Intent startIntent = new Intent(GroupList.this, activity_chats.class);
                                                            startIntent.putExtra("user_id", user_id);
                                                            startIntent.putExtra("user_image", model.getImage());
                                                            startActivity(startIntent);

                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(context, "This contact is inaccessible!", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });


                                            }
                                            else
                                            {
                                                Intent startIntent = new Intent(GroupList.this, activity_chats.class);
                                                startIntent.putExtra("user_id", user_id);
                                                startIntent.putExtra("user_image", model.getImage());
                                                startActivity(startIntent);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });


                                }




                            } else {
                                Toast.makeText(context, "This is just for test. You cannot chat with your self.", Toast.LENGTH_LONG).show();
                            }
                        }

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

        public void  setOnline()
        {
            ImageView IVonline = mView.findViewById(R.id.imageView21);
            IVonline.setImageResource(R.drawable.roundbutton);

        }
        public void  setOffline()
        {
            ImageView IVoffline = mView.findViewById(R.id.imageView21);
            IVoffline.setImageResource(R.drawable.roundbuttongray);

        }
        public void setName(String x)
        {
            TextView userNameView = mView.findViewById(R.id.user_single_name);
            userNameView.setText(x);

            TextView tessasd = mView.findViewById(R.id.textView94);
            tessasd.setText("fgsfgfdgf f g  fgfgfgffggf g fdgf fgfdg fddgfdgd f");
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
        public void setCount(String x, String countf)
        {
            if(countf.equals("")) {
                TextView userNameViews = mView.findViewById(R.id.user_single_status);
                userNameViews.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                userNameViews.setTextColor(Color.rgb(130,130,130));
            }
            else
            {
                TextView userNameViews = mView.findViewById(R.id.user_single_status);
                userNameViews.setTypeface(Typeface.DEFAULT_BOLD);
                userNameViews.setTextColor(Color.rgb(0,200,0));
            }
            TextView userNameViews = mView.findViewById(R.id.textView15);
            userNameViews.setText(Html.fromHtml(x));
        }
        public void setImage(final String x, final Context c)
        {
            final ImageView userNameView = mView.findViewById(R.id.user_single_image);

            Picasso.with(c).load(x).placeholder(R.drawable.pp).into(userNameView);




        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //   if (!bp.handleActivityResult(requestCode, resultCode, data))
        //     super.onActivityResult(requestCode, resultCode, data);

        if (data!=null)
        {
            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Esearch.setText(text.get(0));
        }
        else
            Toast.makeText(this,"Empty voice", 1).show();



    }
}
