package global.imas.bintouch;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class publics extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase,mfriendsDatabase;

    private DatabaseReference  mdata;
    Context context = this;

    private FirebaseUser mCurrent_user;
EditText Esearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publics);

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
     //   mUsersDatabase.keepSynced(true);

        mfriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
       // mfriendsDatabase.keepSynced(true);




        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(publics.this);
        SharedPreferences.Editor editor11 = settings11.edit();

        String select11 = settings11.getString("select", "1");  /// 0 is default if variable not found

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

        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mUsersList = (RecyclerView)findViewById(R.id.Users_list);

        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));

        Button Baudio = (Button)findViewById(R.id.button13);

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


    }

    @Override
    protected void onStart() {
        super.onStart();
        mdata = FirebaseDatabase.getInstance().getReference();
        mdata.keepSynced(true);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
         adapter();
    }

    FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter;

    void adapter()
    {

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, publics.UsersViewHolder>(
                Users.class,
                R.layout.users_single_layout,
                publics.UsersViewHolder.class,
                mUsersDatabase

        ) {
            @Override
            protected void populateViewHolder(publics.UsersViewHolder viewHolder, final Users model, int position) {

                final String user_id = getRef(position).getKey();

                if(Esearch.getText().toString().length()<2)
                {
                    viewHolder.mView.setVisibility(View.GONE);
                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }
                else {
                    if (model.getName().equals("")) {
                        viewHolder.mView.setVisibility(View.GONE);
                        viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                    } else if (!Esearch.getText().toString().equals("")) {
                        if (!(model.getName().toLowerCase().contains(Esearch.getText().toString().toLowerCase()) ||
                                model.getEmail().toLowerCase().contains(Esearch.getText().toString().toLowerCase()))) {
                            viewHolder.mView.setVisibility(View.GONE);
                            viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        } else {
                            if(model.getCountry().equals("yesboth")|| model.getCountry().equals("")) {
                                viewHolder.mView.setVisibility(View.VISIBLE);
                                viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                           else if(model.getCountry().equals("yesname")) {
                                if(model.getName().toLowerCase().contains(Esearch.getText().toString().toLowerCase())) {
                                    viewHolder.mView.setVisibility(View.VISIBLE);
                                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                }
                                else
                                {
                                    viewHolder.mView.setVisibility(View.GONE);
                                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                                }
                             }
                           else if(model.getCountry().equals("yesphone")) {
                                if(model.getEmail().toLowerCase().contains(Esearch.getText().toString().toLowerCase())) {
                                    viewHolder.mView.setVisibility(View.VISIBLE);
                                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                }
                                else
                                {
                                    viewHolder.mView.setVisibility(View.GONE);
                                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                                }
                            }
                            else
                            {
                                viewHolder.mView.setVisibility(View.GONE);
                                viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                            }

                        }
                    } else {
                        if(model.getCountry().equals("yesboth") || model.getCountry().equals("")) {
                            viewHolder.mView.setVisibility(View.VISIBLE);
                            viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        }
                        else if(model.getCountry().equals("yesname")) {
                            if(model.getName().toLowerCase().contains(Esearch.getText().toString().toLowerCase())) {
                                viewHolder.mView.setVisibility(View.VISIBLE);
                                viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            else
                            {
                                viewHolder.mView.setVisibility(View.GONE);
                                viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                            }
                        }
                        else if(model.getCountry().equals("yesphone")) {
                            if(model.getEmail().toLowerCase().contains(Esearch.getText().toString().toLowerCase())) {
                                viewHolder.mView.setVisibility(View.VISIBLE);
                                viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            else
                            {
                                viewHolder.mView.setVisibility(View.GONE);
                                viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                            }
                        }
                        else
                        {
                            viewHolder.mView.setVisibility(View.GONE);
                            viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                        }
                    }
                }

if(model.getCountry().equals("no"))
{
    viewHolder.mView.setVisibility(View.GONE);
    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
}

                viewHolder.setImage(model.getImage(), getApplicationContext());
                viewHolder.setStatus(model.getEmail(), model.getStatus());
                viewHolder.setName(model.getName());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Dialog dialog = new Dialog(publics.this);
                        dialog.setContentView(R.layout.msglayout_publics);
                        dialog.setTitle("Choose:");

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        Button Bprofile = (Button) dialog.findViewById(R.id.button15);
                        Button Bchat = (Button) dialog.findViewById(R.id.button10);
                        TextView Tname = (TextView) dialog.findViewById(R.id.textView16);

                        Tname.setText(model.getName());




                        if(user_id.substring(0,user_id.indexOf(";;;;;profile_")).equals(mCurrent_user.getUid())) Bchat.setVisibility(View.INVISIBLE);

                        dialog.show();

                        Bprofile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                Intent startIntent = new Intent(publics.this, ViewProfile.class);
                                startIntent.putExtra("user_id", user_id);
                                startIntent.putExtra("user_image", model.getImage());
                                startActivity(startIntent);
                            }
                        });

                        Bchat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();

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
                                String DateTime = s + hour;

                                HashMap<String, String> userMap = new HashMap<>();
                                userMap.put("name", model.getName());
                                userMap.put("status", model.getStatus());
                                userMap.put("image", model.getImage());
                                userMap.put("count", DateTime+"0");




                                mfriendsDatabase.child(uuser_id).child(user_id).setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });


                                mUsersDatabase.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

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
                                        String DateTime = s + hour;

                                        HashMap<String, String> userMap2 = new HashMap<>();
                                        userMap2.put("name", dataSnapshot.child("name").getValue(String.class));
                                        userMap2.put("status", dataSnapshot.child("status").getValue(String.class));
                                        userMap2.put("image", dataSnapshot.child("image").getValue(String.class));
                                        userMap2.put("count", DateTime+"0");

                                        mfriendsDatabase.child(user_id).child(uuser_id).setValue(userMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


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
                                                        Intent startIntent = new Intent(publics.this, activity_chats.class);
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
                                            Intent startIntent = new Intent(publics.this, activity_chats.class);
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
                        });


                    }
                });

            }
        };
        mUsersList.setAdapter(firebaseRecyclerAdapter);

    }

    String uuser_id = "x";

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


        }
        public void setStatus(String xemail, String xstatus)
        {
            TextView userNameView = mView.findViewById(R.id.user_single_status);
            userNameView.setText(Html.fromHtml(xemail + "<br><font color=\"#00AA00\">" + xstatus));
            userNameView.setVisibility(View.INVISIBLE);
        }
        public void setImage(final String x, final Context c)
        {
            final ImageView userNameView = mView.findViewById(R.id.user_single_image);

         //   Picasso.with(c).load(x).placeholder(R.drawable.pp).into(userNameView);




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
