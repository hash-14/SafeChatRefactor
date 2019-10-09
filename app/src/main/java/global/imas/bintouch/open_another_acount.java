package global.imas.bintouch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class open_another_acount extends AppCompatActivity {

    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private TextInputLayout mPhone,mCountry;
    private Button mCreateBtn;
Context context = this;
    private Toolbar mToolbar;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrent_user;
    private DatabaseReference mDatabase, mData;

    private ProgressDialog mRegProgress , mRegProgress2;
    private RadioButton RBphone, RBemail;
    String acc_num;
    String acc_num_index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_another_acount);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

          acc_num = getIntent().getStringExtra("acc_num");
          acc_num_index = getIntent().getStringExtra("acc_num_index");

        mToolbar = (Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress=new ProgressDialog(this);
        mRegProgress2=new ProgressDialog(this);

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();
        mData = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        mCreateBtn = (Button)findViewById(R.id.reg_create_button);
        mDisplayName = (TextInputLayout)findViewById(R.id.reg_display_name2);
        mCountry =  (TextInputLayout)findViewById(R.id.reg_display_name21);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mDisplayName.getEditText().getText().toString().replace(" ","").equals(""))
                {
                    Toast.makeText(context, "Enter a name", Toast.LENGTH_LONG).show();
                    return;
                }
                if(mDisplayName.getEditText().getText().toString().substring(mDisplayName.getEditText().getText().toString().length()-1).equals(" "))
                {
                    Toast.makeText(context, "Account cannot end with a space character", Toast.LENGTH_LONG).show();
                    return;
                }


             f1();


            }
        });
    }

    String name1 = "";
    String name2 = "";
    String name3 = "";
    String name4 = "";
    String name5 = "";



    void f1()
    {

        mRegProgress=new ProgressDialog(open_another_acount.this);
        mRegProgress.setTitle("Checking name");
        // mRegProgress.setMessage("Please wait while creating your account");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable < DataSnapshot > children = dataSnapshot.getChildren();

                int cc = 0;

                for (DataSnapshot child : children) {

                  String  x=child.child("name").getValue(String.class).toLowerCase();
                    String y = mDisplayName.getEditText().getText().toString().toLowerCase();
                    if(x.length()>0) {
                        while (x.substring(0, 1).equals(" ")) {
                            x = x.substring(1);
                        }
                        while (x.substring(x.length() - 1, x.length()).equals(" ")) {
                            x = x.substring(0, x.length() - 1);
                        }
                        while (y.substring(0, 1).equals(" ")) {
                            y = y.substring(1);
                        }
                        while (y.substring(y.length() - 1, y.length()).equals(" ")) {
                            y = y.substring(0, y.length() - 1);
                        }
                        if (x.equals(y)) {
                            cc++;
                        }
                    }
                }

                mRegProgress.dismiss();
                if(cc == 0)

                {
                    f2();

                }
                else
                {
                        Toast.makeText(context, "Account name already exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    void f2()
    {

        if(acc_num.equals("1"))
        {
            HashMap<String, Object> resultm = new HashMap<>();
            resultm.put("name",mDisplayName.getEditText().getText().toString());
            resultm.put("country",mCountry.getEditText().getText().toString());
            mData.child(mCurrent_user.getUid() +";;;;;profile_"+acc_num_index).updateChildren(resultm);
        }
        if(acc_num.equals("2"))
        {
            HashMap<String, Object> resultm = new HashMap<>();
            resultm.put("name",mDisplayName.getEditText().getText().toString());
            resultm.put("country",mCountry.getEditText().getText().toString());
            mData.child(mCurrent_user.getUid() +";;;;;profile_"+acc_num_index).updateChildren(resultm);
        }
        if(acc_num.equals("3"))
        {
            HashMap<String, Object> resultm = new HashMap<>();
            resultm.put("name",mDisplayName.getEditText().getText().toString());
            resultm.put("country",mCountry.getEditText().getText().toString());
            mData.child(mCurrent_user.getUid() +";;;;;profile_"+acc_num_index).updateChildren(resultm);
        }
        if(acc_num.equals("4"))
        {
            HashMap<String, Object> resultm = new HashMap<>();
            resultm.put("name",mDisplayName.getEditText().getText().toString());
            resultm.put("country",mCountry.getEditText().getText().toString());
            mData.child(mCurrent_user.getUid() +";;;;;profile_"+acc_num_index).updateChildren(resultm);
        }

        SharedPreferences settings1 = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor1 = settings1.edit();

        editor1.putString("select", acc_num_index+"");

        editor1.commit();

        Toast.makeText(open_another_acount.this,"Account Added!", Toast.LENGTH_LONG).show();

        Intent startIntent = new Intent(open_another_acount.this, MainActivity.class);
        startActivity(startIntent);
    }

}
