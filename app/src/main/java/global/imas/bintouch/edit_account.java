package global.imas.bintouch;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class edit_account extends AppCompatActivity {
    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private TextInputLayout mPhone,mCountry;
    private Button mCreateBtn, mDelete;
    Context context = this;
    private Toolbar mToolbar;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrent_user;
    private DatabaseReference mDatabase, mData;

    private ProgressDialog mRegProgress , mRegProgress2;
    private RadioButton RBphone, RBemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final String acc_id = getIntent().getStringExtra("acc_id");

        final String name1 = getIntent().getStringExtra("name1");
        final String name2 = getIntent().getStringExtra("name2");
        final String name3 = getIntent().getStringExtra("name3");
        final String name4 = getIntent().getStringExtra("name4");
        final String name5 = getIntent().getStringExtra("name5");


        mToolbar = (Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mData = FirebaseDatabase.getInstance().getReference().child("Users");

        mRegProgress=new ProgressDialog(edit_account.this);
        mRegProgress.setTitle("Loading...");
        // mRegProgress.setMessage("Please wait while creating your account");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        mCreateBtn = (Button)findViewById(R.id.reg_create_button);
        mDisplayName = (TextInputLayout)findViewById(R.id.reg_display_name2);
        mCountry =  (TextInputLayout)findViewById(R.id.reg_display_name21);

        mDelete= (Button)findViewById(R.id.button61);

        mData.child(acc_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDisplayName.getEditText().setText(dataSnapshot.child("name").getValue().toString());
                mCountry.getEditText().setText(dataSnapshot.child("country").getValue().toString());
                mRegProgress.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mDisplayName.getEditText().getText().toString().equals(""))
                {
                    Toast.makeText(context, "Enter a name", Toast.LENGTH_LONG).show();
                    return;
                }


                mData.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int cou = 0;

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        for (DataSnapshot child : children) {
                            if(child.child("name").getValue(String.class).equals(mDisplayName.getEditText().getText().toString()))
                            {
                                cou = 1;
                            }

                        }

                        if(cou==1)
                        {
                            Toast.makeText(edit_account.this, "Username already exists!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            ////vvv

                            if(mDisplayName.getEditText().getText().toString().equals(name1) ||
                                    mDisplayName.getEditText().getText().toString().equals(name2) ||
                                    mDisplayName.getEditText().getText().toString().equals(name3) ||
                                    mDisplayName.getEditText().getText().toString().equals(name4) ||
                                    mDisplayName.getEditText().getText().toString().equals(name5)
                            )
                            {
                                Toast.makeText(context, "Name already exist!", Toast.LENGTH_LONG).show();
                                return;
                            }

                            HashMap<String, Object> resultm = new HashMap<>();
                            resultm.put("name",mDisplayName.getEditText().getText().toString());
                            resultm.put("country",mCountry.getEditText().getText().toString());
                            mData.child(acc_id).updateChildren(resultm);

                            Toast.makeText(edit_account.this,"Saved!", Toast.LENGTH_LONG).show();



                            ////vvvv
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(edit_account.this);
                dialog.setContentView(R.layout.msglayout_delete_withmsg);
                dialog.setTitle("Please select :");

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                TextView ttt = (TextView) dialog.findViewById(R.id.textView42);
                Button BB1 = (Button) dialog.findViewById(R.id.button15);
                Button BBs1 = (Button) dialog.findViewById(R.id.button46);

                ttt.setVisibility(View.INVISIBLE);

                BB1.setText("Delete");
                BBs1.setText("Cancel");

                dialog.show();

                BB1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String, Object> resultm = new HashMap<>();
                        resultm.put("name","");
                        resultm.put("country","");
                        mData.child(acc_id).updateChildren(resultm).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(edit_account.this,"Deleted!", Toast.LENGTH_LONG).show();
                                Intent startIntent = new Intent (edit_account.this, MainActivity.class);
                                startActivity(startIntent);
                            }
                        });


                    }
                });

                BBs1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });


    }
}
