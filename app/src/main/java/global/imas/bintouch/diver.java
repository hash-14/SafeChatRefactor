package global.imas.bintouch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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

import java.util.HashMap;

public class diver extends AppCompatActivity {

    Button Bschedule;
   // RecyclerView
    Context c = this;
    private RecyclerView mUsersList;
    private DatabaseReference mfriendsCurrentUserDatabase, mdata;
    private FirebaseUser mCurrent_user;
    String uuser_id = "x123error";
    TextView Tname;
    Button Bdelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Divert");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        LinearLayoutManager ll = new LinearLayoutManager(this);
        mUsersList = (RecyclerView) findViewById(R.id.recyclerView4);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(ll);
        mUsersList.setVisibility(View.VISIBLE);

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

        mdata = FirebaseDatabase.getInstance().getReference();
        mdata.keepSynced(true);



        Bschedule = (Button)findViewById(R.id.date_time_set);

        Bschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count!=1) {
                    Toast.makeText(diver.this, "One friend must be selected" , Toast.LENGTH_LONG).show();
                    return;
                }

                mRegProgress=new ProgressDialog(diver.this);
                mRegProgress.setTitle("scheduling...");
                // mRegProgress.setMessage("Please wait while creating your account");
                mRegProgress.setCanceledOnTouchOutside(false);
                mRegProgress.show();

                mdata.child("Diver_assign_by_me").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(uuser_id))
                        {

                            mdata.child("Diver_check").child(dataSnapshot.child(uuser_id).child("id").getValue(String.class)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mdata.child("Diver_assign_by_me").child(uuser_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid)
                                        {
                                            mdata.child("Diver_check").child(user_id_divered_to).child(uuser_id).child("state").setValue("Yes");

                                            HashMap<String, String> object = new HashMap<String, String>();
                                            object.put("name", name_id_divered_to);
                                            object.put("id", user_id_divered_to);

                                            mdata.child("Diver_assign_by_me").child(uuser_id).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    mdata.child("Diver_assign_by_me").addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.hasChild(uuser_id))
                                                            {
                                                                Tname.setVisibility(View.VISIBLE);
                                                                Bdelete.setVisibility(View.VISIBLE);
                                                                mRegProgress.dismiss();
                                                                Tname.setText("Current divert: "+dataSnapshot.child(uuser_id).child("name").getValue(String.class));
                                                                Toast.makeText(diver.this, "Done!" , Toast.LENGTH_LONG).show();

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
                            }) ;

                        }
                        else
                        {
                            mdata.child("Diver_check").child(user_id_divered_to).child(uuser_id).child("state").setValue("Yes");

                            HashMap<String, String> object = new HashMap<String, String>();
                            object.put("name", name_id_divered_to);
                            object.put("id", user_id_divered_to);

                            mdata.child("Diver_assign_by_me").child(uuser_id).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mdata.child("Diver_assign_by_me").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.hasChild(uuser_id))
                                            {
                                                Tname.setVisibility(View.VISIBLE);
                                                Bdelete.setVisibility(View.VISIBLE);
                                                mRegProgress.dismiss();
                                                Tname.setText("Current divert: "+dataSnapshot.child(uuser_id).child("name").getValue(String.class));
                                                Toast.makeText(diver.this, "Done!" , Toast.LENGTH_LONG).show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });

         Tname = (TextView)findViewById(R.id.textView20dd);
        Bdelete = (Button)findViewById(R.id.button18);

        Bdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mRegProgress=new ProgressDialog(diver.this);
                mRegProgress.setTitle("deleting...");
                // mRegProgress.setMessage("Please wait while creating your account");
                mRegProgress.setCanceledOnTouchOutside(false);
                mRegProgress.show();

                mdata.child("Diver_assign_by_me").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mdata.child("Diver_check").child(dataSnapshot.child("id").getValue(String.class)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mdata.child("Diver_assign_by_me").child(uuser_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Tname.setVisibility(View.INVISIBLE);
                                        Bdelete.setVisibility(View.INVISIBLE);
                                        mRegProgress.dismiss();
                                        Toast.makeText(diver.this, "Deleted!!" , Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        mdata.child("Diver_assign_by_me").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(uuser_id))
                {
                    Tname.setVisibility(View.VISIBLE);
                    Bdelete.setVisibility(View.VISIBLE);
                    Tname.setText("Current divert: "+dataSnapshot.child(uuser_id).child("name").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        adapter();

    }

      public static int count=0;
    public static String user_id_divered_to="";
    public static String name_id_divered_to="";

    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private ProgressDialog mRegProgress;

    void adapter ()
    {
        count=0;
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Friends, diver.UsersViewHolder>(
                Friends.class,
                R.layout.users_single_layout_autoreply_add,
                diver.UsersViewHolder.class,
                mfriendsCurrentUserDatabase

        ) {
            @Override
            protected void populateViewHolder(final diver.UsersViewHolder viewHolder, final Friends model, final int position) {

                final String user_id = getRef(position).getKey();

                viewHolder.setCheck( user_id, model.getName());
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

    public void setCheck( String userID, String name)
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

                if(c.isChecked()) {
                  diver.count++;
                  diver.user_id_divered_to=useridd;
                  diver.name_id_divered_to=namee;
              }
                else
                  diver.count--;






            }
        });



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
