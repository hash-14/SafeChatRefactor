package global.imas.bintouch;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.TimeUnit;
public class LoginActivity extends AppCompatActivity {


    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPhone;
    private TextInputLayout mLoginPassword;
    private Button mLogin_Btn,mLogin_phone;
private DatabaseReference mUserDatabase;
    private Toolbar mToolbar;
    Dialog dialog2;
    TextView Tforgetpass;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog mRegProgress;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mToolbar = (Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress=new ProgressDialog(this);


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("select", "1");

        editor.commit();

        mAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mLoginEmail = (TextInputLayout)findViewById(R.id.login_email);
        mLoginPassword = (TextInputLayout)findViewById(R.id.login_password);
        mLogin_Btn = (Button)findViewById(R.id.login_btn);

        mLoginPhone = (TextInputLayout)findViewById(R.id.login_phone);
        mLogin_phone = (Button)findViewById(R.id.button5);


        Tforgetpass = (TextView)findViewById(R.id.textView66);
        Tforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mLoginEmail.getEditText().getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Enter an email", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!mLoginEmail.getEditText().getText().toString().contains("@"))
                {
                    Toast.makeText(LoginActivity.this, "Enter a valid email", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(mLoginEmail.getEditText().getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Email sent!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


        mLogin_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = mLoginPhone.getEditText().getText().toString();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        LoginActivity.this,               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks

                dialog2 = new Dialog(LoginActivity.this);
                dialog2.setContentView(R.layout.msglayout_codephone);
                dialog2.setTitle("Please enter code:");

                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                dialog2.setCanceledOnTouchOutside(false);

                final EditText ET = (EditText) dialog2.findViewById(R.id.editText6);
                Button B = (Button) dialog2.findViewById(R.id.button10);

                dialog2.show();

                B.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(ET.getText().toString().equals(""))
                        {
                            Toast.makeText(LoginActivity.this, "Enter a code", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(ET.getText().toString().equals(mVerificationId))
                        {

                            Toast.makeText(LoginActivity.this, "Phone number verified", Toast.LENGTH_LONG).show();
                            dialog2.dismiss();

                            Toast.makeText(LoginActivity.this, "Logged in done!", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Wrong code!", Toast.LENGTH_LONG).show();
                        }


                    }
                });
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(LoginActivity.this, "Error!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                // ...
            }

        };

        mLogin_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mLoginEmail.getEditText().getText().toString();
                String password = mLoginPassword.getEditText().getText().toString();

                if(!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password))
                {
                    mRegProgress.setTitle("Login User");
                    mRegProgress.setMessage("Please wait while logging in to your account");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    login_user( email,password);
                }

            }


        });

    }


    private void login_user(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            mRegProgress.dismiss();

                            String currentUserid = mAuth.getCurrentUser().getUid();
                            String Token = FirebaseInstanceId.getInstance().getToken();

                            FirebaseUser mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uuser_id = "";
                            SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
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

                            mUserDatabase.child(uuser_id).child("device_token").setValue(Token).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent mainIntent = new Intent (LoginActivity.this, MainActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |     Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            });

                        }
                        else
                        {
                            mRegProgress.hide();
                            Toast.makeText(LoginActivity.this, "Cannot sign in. Please check the form and try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();

                            Toast.makeText(LoginActivity.this, "Phone number verified", Toast.LENGTH_LONG).show();
                            dialog2.dismiss();

                            mRegProgress.setTitle("Logging in");
                            mRegProgress.setMessage("Please wait");
                            mRegProgress.setCanceledOnTouchOutside(false);
                            mRegProgress.show();

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            // HashMap<String, String> userMap = new HashMap<>();
                            String Token = FirebaseInstanceId.getInstance().getToken();
                            // userMap.put("device_token", Token);

                            FirebaseUser mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uuser_id = "";
                            SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
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
                            mUserDatabase.child(uuser_id).child("device_token").setValue(Token).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        mRegProgress.dismiss();
                                        Intent mainIntent = new Intent (LoginActivity.this, MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |     Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                }
                            });

                        }
                        else
                        {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                // The verification code entered was invalid
                                Toast.makeText(LoginActivity.this, "Error!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
