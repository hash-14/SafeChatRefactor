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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private TextInputLayout mPhone,mCountry;
    private Button mCreateBtn;

    private Toolbar mToolbar;

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase, mData;

    private  ProgressDialog mRegProgress , mRegProgress2;
    private RadioButton RBphone, RBemail;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

RadioButton RBEng, RBAr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SharedPreferences settingssa = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editorsa = settingssa.edit();
        editorsa.putString("seen", "yesboth");
        editorsa.commit();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mToolbar = (Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RBEng = (RadioButton)findViewById(R.id.radioButtonEnglish);
        RBAr = (RadioButton)findViewById(R.id.radioButtonArabic);
        RBEng.setChecked(true);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("language", "Eng");
        editor.commit();

        RBEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RBAr.setChecked(false);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("language", "Eng");
                editor.commit();
            }
        });
        RBAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RBEng.setChecked(false);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("language", "Ar");
                editor.commit();
            }
        });

        mDisplayName = (TextInputLayout)findViewById(R.id.reg_display_name2);
        mCountry =  (TextInputLayout)findViewById(R.id.reg_display_name21);
        RBphone = (RadioButton)findViewById(R.id.radioButton);
        RBemail = (RadioButton)findViewById(R.id.radioButton2);
        mPhone = (TextInputLayout)findViewById(R.id.login_phone);
        mEmail = (TextInputLayout)findViewById(R.id.login_email);
        mPassword = (TextInputLayout)findViewById(R.id.login_password);
        mCreateBtn = (Button)findViewById(R.id.reg_create_button);

        spcountry = (Spinner)findViewById(R.id.spinner4);
        countries();

        RBphone.setChecked(true);
        mEmail.setVisibility(View.INVISIBLE);
        mPassword.setVisibility(View.INVISIBLE);
        mPhone.setVisibility(View.VISIBLE);

        RBphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmail.setVisibility(View.INVISIBLE);
                mPassword.setVisibility(View.INVISIBLE);
                mPhone.setVisibility(View.VISIBLE);
                RBemail.setChecked(false);
            }
        });

        RBemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmail.setVisibility(View.VISIBLE);
                mPassword.setVisibility(View.VISIBLE);
                mPhone.setVisibility(View.INVISIBLE);
                RBphone.setChecked(false);
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

                if(spcountry.getSelectedItem().toString().equals("Choose a country"))
                {
                    Toast.makeText(context, "Choose a country", Toast.LENGTH_LONG).show();
                    return;
                }

                if(mPhone.getEditText().getText().toString().equals("") && RBphone.isChecked())
                {
                    Toast.makeText(context, "Enter a phone number", Toast.LENGTH_LONG).show();
                    return;
                }

                if(mEmail.getEditText().getText().toString().equals("") && RBemail.isChecked())
                {
                    Toast.makeText(context, "Enter an email", Toast.LENGTH_LONG).show();
                    return;
                }

                if(mPassword.getEditText().getText().toString().equals("") && RBemail.isChecked())
                {
                    Toast.makeText(context, "Enter a password", Toast.LENGTH_LONG).show();
                    return;
                }

                mData = FirebaseDatabase.getInstance().getReference().child("Users");

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
                               Toast.makeText(RegisterActivity.this, "Username already exists!", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            ////vvv

                            if(RBemail.isChecked())
                            {

                                StringBuilder b = new StringBuilder();
                                Random r = new Random();
                                String subset = "123456789";
                                for (int i = 0; i < 4; i++) {
                                    int index = r.nextInt(subset.length());
                                    char c = subset.charAt( index );
                                    b.append( c );
                                }
                                passito=b.toString();

                                String subject = "BinTouch";
                                String message = "Hello,\n\nYour code is: " + passito + "\n\nRegards\nBinTouch team";

                                //Creating SendMail object
                                SendMail   sm = new SendMail(context, mEmail.getEditText().getText().toString(), subject, message);
                                //Executing sendmail to send email
                                sm.execute();

                                final Dialog dialog = new Dialog(RegisterActivity.this);
                                dialog.setContentView(R.layout.msglayout_codeemail);
                                dialog.setTitle("Please enter code:");

                                dialog.setCanceledOnTouchOutside(false);

                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                final EditText ET = (EditText) dialog.findViewById(R.id.editText6);
                                Button B = (Button) dialog.findViewById(R.id.button10);

                                dialog.show();

                                B.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        if(ET.getText().toString().equals(""))
                                        {
                                            Toast.makeText(context, "Enter a code", Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        if(ET.getText().toString().equals(passito))
                                        {
                                            Toast.makeText(context, "Email verified", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();

                                            mRegProgress.setTitle("Registering User");
                                            mRegProgress.setMessage("Please wait while creating your account");
                                            mRegProgress.setCanceledOnTouchOutside(false);
                                            mRegProgress.show();

                                            String display_name = mDisplayName.getEditText().getText().toString();
                                            String email = mEmail.getEditText().getText().toString();
                                            String password = mPassword.getEditText().getText().toString();
                                            String country = "yesboth";



                                            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                                            SharedPreferences.Editor editor = settings.edit();



                                            editor.putString("select", "1");

                                            editor.commit();

                                            register_user(display_name, email,  password,country);


                                        }
                                        else
                                        {
                                            Toast.makeText(context, "Wrong code!", Toast.LENGTH_LONG).show();
                                        }


                                    }
                                });



                            }
                            if(RBphone.isChecked())
                            {
                                String phoneNumber = mPhone.getEditText().getText().toString();


                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                        phoneNumber,        // Phone number to verify
                                        60,                 // Timeout duration
                                        TimeUnit.SECONDS,   // Unit of timeout
                                        RegisterActivity.this,               // Activity (for callback binding)
                                        mCallbacks);        // OnVerificationStateChangedCallbacks

                                dialog2 = new Dialog(RegisterActivity.this);
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
                                            Toast.makeText(context, "Enter a code", Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        if(ET.getText().toString().equals(mVerificationId))
                                        {



                                            Toast.makeText(context, "Phone number verified", Toast.LENGTH_LONG).show();
                                            dialog2.dismiss();



                                            mRegProgress.setTitle("Registering User");
                                            mRegProgress.setMessage("Please wait while creating your account");
                                            mRegProgress.setCanceledOnTouchOutside(false);
                                            mRegProgress.show();

                                            String display_name = mDisplayName.getEditText().getText().toString();
                                            String phone = mPhone.getEditText().getText().toString();
                                            String country = "yesboth";



                                            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                                            SharedPreferences.Editor editor = settings.edit();



                                            editor.putString("select", "1");

                                            editor.commit();

                                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                            String uid = current_user.getUid();

                                            String Token = FirebaseInstanceId.getInstance().getToken();

                                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                                            mData = FirebaseDatabase.getInstance().getReference().child("Users");

                                            HashMap<String, String> userMap = new HashMap<>();
                                            userMap.put("name", display_name);
                                            userMap.put("status", "Hi there, I'm using BinTouch");
                                            userMap.put("image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/pp.png?alt=media&token=45bf4ab0-69c3-4ed8-9696-a3019b98b02d");
                                            userMap.put("Thumb_image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/pp%20-%20thum.png?alt=media&token=a3d528f6-5368-48ec-8ba8-8c0558a44e92");
                                            userMap.put("email", phone);
                                            userMap.put("country",  "yesboth");
                                            userMap.put("device_token", Token);

                                            HashMap<String, String> userMap0 = new HashMap<>();
                                            userMap0.put("image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/pp.png?alt=media&token=45bf4ab0-69c3-4ed8-9696-a3019b98b02d");
                                            userMap0.put("status", "Hi there, I'm using BinTouch");
                                            userMap0.put("name", "");
                                            userMap0.put("Thumb_image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/pp%20-%20thum.png?alt=media&token=a3d528f6-5368-48ec-8ba8-8c0558a44e92");
                                            userMap0.put("email", phone);
                                            userMap0.put("country",  "");
                                            userMap0.put("device_token", Token);

                                            mData.child(uid+";;;;;profile_1").setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });
                                            mData.child(uid+";;;;;profile_2").setValue(userMap0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                }
                                            });
                                            mData.child(uid+";;;;;profile_3").setValue(userMap0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                }
                                            });

                                            mData.child(uid+";;;;;profile_4").setValue(userMap0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                }
                                            });

                                            mData.child(uid+";;;;;profile_5").setValue(userMap0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()) {
                                                        mRegProgress.dismiss();
                                                        Intent mainIntent = new Intent (RegisterActivity.this, MainActivity.class);
                                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |     Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(mainIntent);
                                                        finish();
                                                    }
                                                }
                                            });



                                        }
                                        else
                                        {
                                            Toast.makeText(context, "Wrong code!", Toast.LENGTH_LONG).show();
                                        }


                                    }
                                });



                            }

                            ////vvvv
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

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
                Toast.makeText(context, "Error!\n\n"+e, Toast.LENGTH_LONG).show();

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


    }

    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    Dialog dialog2;

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();


                            Toast.makeText(context, "Phone number verified", Toast.LENGTH_LONG).show();
                            dialog2.dismiss();



                            mRegProgress.setTitle("Registering User");
                            mRegProgress.setMessage("Please wait while creating your account");
                            mRegProgress.setCanceledOnTouchOutside(false);
                            mRegProgress.show();

                            String display_name = mDisplayName.getEditText().getText().toString();
                            String phone = mPhone.getEditText().getText().toString();
                            String country = spcountry.getSelectedItem().toString();



                            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = settings.edit();



                                editor.putString("select", "1");


                            editor.commit();


                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            String Token = FirebaseInstanceId.getInstance().getToken();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            mData = FirebaseDatabase.getInstance().getReference().child("Users");

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", display_name);
                            userMap.put("status", "Hi there, I'm using BinTouch");
                            userMap.put("image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/pp.png?alt=media&token=45bf4ab0-69c3-4ed8-9696-a3019b98b02d");
                            userMap.put("Thumb_image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/pp%20-%20thum.png?alt=media&token=a3d528f6-5368-48ec-8ba8-8c0558a44e92");
                            userMap.put("email", phone);
                            userMap.put("country",  "yesboth");
                            userMap.put("device_token", Token);

                            HashMap<String, String> userMap0 = new HashMap<>();
                            userMap0.put("image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/pp.png?alt=media&token=45bf4ab0-69c3-4ed8-9696-a3019b98b02d");
                            userMap0.put("status", "Hi there, I'm using BinTouch");
                            userMap0.put("name", "");
                            userMap0.put("Thumb_image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/pp%20-%20thum.png?alt=media&token=a3d528f6-5368-48ec-8ba8-8c0558a44e92");
                            userMap0.put("email", phone);
                            userMap0.put("country",  "");
                            userMap0.put("device_token", Token);

                            mData.child(uid+";;;;;profile_1").setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                            mData.child(uid+";;;;;profile_2").setValue(userMap0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                            mData.child(uid+";;;;;profile_3").setValue(userMap0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                            mData.child(uid+";;;;;profile_4").setValue(userMap0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                            mData.child(uid+";;;;;profile_5").setValue(userMap0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        mRegProgress.dismiss();
                                        Intent mainIntent = new Intent (RegisterActivity.this, MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |     Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                }
                            });



                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                });
    }


    String passito = "";
  Context context = this;

    private  void register_user (final String display_name, final String email, String password, final String country)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();


                            String Token = FirebaseInstanceId.getInstance().getToken();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            mData = FirebaseDatabase.getInstance().getReference().child("Users");

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", display_name);
                            userMap.put("status", "Hi there, I'm using BinTouch");
                            userMap.put("image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/pp.png?alt=media&token=45bf4ab0-69c3-4ed8-9696-a3019b98b02d");
                            userMap.put("Thumb_image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/pp%20-%20thum.png?alt=media&token=a3d528f6-5368-48ec-8ba8-8c0558a44e92");
                            userMap.put("email", email);
                            userMap.put("country",  "yesboth");
                            userMap.put("device_token", Token);

                            HashMap<String, String> userMap0 = new HashMap<>();
                            userMap0.put("name", "");
                            userMap0.put("status", "Hi there, I'm using BinTouch");
                            userMap0.put("image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/pp.png?alt=media&token=45bf4ab0-69c3-4ed8-9696-a3019b98b02d");
                            userMap0.put("Thumb_image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/pp%20-%20thum.png?alt=media&token=a3d528f6-5368-48ec-8ba8-8c0558a44e92");
                            userMap0.put("email", email);
                            userMap0.put("country",  "");
                            userMap0.put("device_token", Token);

                            mData.child(uid+";;;;;profile_1").setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                            mData.child(uid+";;;;;profile_2").setValue(userMap0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                            mData.child(uid+";;;;;profile_3").setValue(userMap0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                            mData.child(uid+";;;;;profile_4").setValue(userMap0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                            mData.child(uid+";;;;;profile_5").setValue(userMap0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        mRegProgress.dismiss();
                                        Intent mainIntent = new Intent (RegisterActivity.this, MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |     Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                }
                            });


                        }
                        else
                        {
                            mRegProgress.hide();
                            Toast.makeText(RegisterActivity.this, "Cannot sign in. Possible reasons: \n- You already have an account\n- Your email is not valid\n- Your password is too short",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }




    List<String> Lbcountry = new ArrayList<String>();
    Spinner spcountry;
    ArrayAdapter<String> adapter1;

    void countries()
    {


        Lbcountry.clear();
        Lbcountry.add("Choose a country");
        Lbcountry.add("Afghanistan");
        Lbcountry.add("Albania");
        Lbcountry.add("Algeria");
        Lbcountry.add("Andorra");
        Lbcountry.add("Angola");
        Lbcountry.add("Antigua and Barbuda");
        Lbcountry.add("Argentina");
        Lbcountry.add("Armenia");
        Lbcountry.add("Australia");
        Lbcountry.add("Austria");
        Lbcountry.add("Azerbaijan");
        Lbcountry.add("Bahamas");
        Lbcountry.add("Bahrain");
        Lbcountry.add("Bangladesh");
        Lbcountry.add("Barbados");
        Lbcountry.add("Belarus");
        Lbcountry.add("Belgium");
        Lbcountry.add("Belize");
        Lbcountry.add("Benin");
        Lbcountry.add("Bhutan");
        Lbcountry.add("Bolivia");
        Lbcountry.add("Bosnia and Herzegovina");
        Lbcountry.add("Botswana");
        Lbcountry.add("Brazil");
        Lbcountry.add("Brunei");
        Lbcountry.add("Bulgaria");
        Lbcountry.add("Burkina Faso");
        Lbcountry.add("Burundi");
        Lbcountry.add("Cabo Verde");
        Lbcountry.add("Cambodia");
        Lbcountry.add("Cameroon");
        Lbcountry.add("Canada");
        Lbcountry.add("Central African Republic");
        Lbcountry.add("Chad");
        Lbcountry.add("Chile");
        Lbcountry.add("China");
        Lbcountry.add("Colombia");
        Lbcountry.add("Comoros");
        Lbcountry.add("Democratic Republic of the Congo");
        Lbcountry.add("Republic of the Congo");
        Lbcountry.add("Costa Rica");
        Lbcountry.add("Cote d'Ivoire");
        Lbcountry.add("Croatia");
        Lbcountry.add("Cuba");
        Lbcountry.add("Cyprus");
        Lbcountry.add("Czech Republic");
        Lbcountry.add("Denmark");
        Lbcountry.add("Djibouti");
        Lbcountry.add("Dominica");
        Lbcountry.add("Dominican Republic");
        Lbcountry.add("Ecuador");
        Lbcountry.add("Egypt");
        Lbcountry.add("El Salvador");
        Lbcountry.add("Equatorial Guinea");
        Lbcountry.add("Eritrea");
        Lbcountry.add("Estonia");
        Lbcountry.add("Ethiopia");
        Lbcountry.add("Fiji");
        Lbcountry.add("Finland");
        Lbcountry.add("France");
        Lbcountry.add("Gabon");
        Lbcountry.add("Gambia");
        Lbcountry.add("Georgia");
        Lbcountry.add("Germany");
        Lbcountry.add("Ghana");
        Lbcountry.add("Greece");
        Lbcountry.add("Grenada");
        Lbcountry.add("Guatemala");
        Lbcountry.add("Guinea");
        Lbcountry.add("Guinea-Bissau");
        Lbcountry.add("Guyana");
        Lbcountry.add("Haiti");
        Lbcountry.add("Honduras");
        Lbcountry.add("Hungary");
        Lbcountry.add("Iceland");
        Lbcountry.add("India");
        Lbcountry.add("Indonesia");
        Lbcountry.add("Iran");
        Lbcountry.add("Iraq");
        Lbcountry.add("Ireland");
        Lbcountry.add("Israel");
        Lbcountry.add("Italy");
        Lbcountry.add("Jamaica");
        Lbcountry.add("Japan");
        Lbcountry.add("Jordan");
        Lbcountry.add("Kazakhstan");
        Lbcountry.add("Kenya");
        Lbcountry.add("Kiribati");
        Lbcountry.add("Kosovo");
        Lbcountry.add("Kuwait");
        Lbcountry.add("Kyrgyzstan");
        Lbcountry.add("Laos");
        Lbcountry.add("Latvia");
        Lbcountry.add("Lebanon");
        Lbcountry.add("Lesotho");
        Lbcountry.add("Liberia");
        Lbcountry.add("Libya");
        Lbcountry.add("Liechtenstein");
        Lbcountry.add("Lithuania");
        Lbcountry.add("Luxembourg");
        Lbcountry.add("Macedonia");
        Lbcountry.add("Madagascar");
        Lbcountry.add("Malawi");
        Lbcountry.add("Malaysia");
        Lbcountry.add("Maldives");
        Lbcountry.add("Mali");
        Lbcountry.add("Malta");
        Lbcountry.add("Marshall Islands");
        Lbcountry.add("Mauritania");
        Lbcountry.add("Mauritius");
        Lbcountry.add("Mexico");
        Lbcountry.add("Micronesia");
        Lbcountry.add("Moldova");
        Lbcountry.add("Monaco");
        Lbcountry.add("Mongolia");
        Lbcountry.add("Montenegro");
        Lbcountry.add("Morocco");
        Lbcountry.add("Mozambique");
        Lbcountry.add("Myanmar");
        Lbcountry.add("Namibia");
        Lbcountry.add("Nauru");
        Lbcountry.add("Nepal");
        Lbcountry.add("Netherlands");
        Lbcountry.add("New Zealand");
        Lbcountry.add("Nicaragua");
        Lbcountry.add("Niger");
        Lbcountry.add("Nigeria");
        Lbcountry.add("North Korea");
        Lbcountry.add("Norway");
        Lbcountry.add("Oman");
        Lbcountry.add("Pakistan");
        Lbcountry.add("Palau");
        Lbcountry.add("Palestine");
        Lbcountry.add("Panama");
        Lbcountry.add("Papua New Guinea");
        Lbcountry.add("Paraguay");
        Lbcountry.add("Peru");
        Lbcountry.add("Philippines");
        Lbcountry.add("Poland");
        Lbcountry.add("Portugal");
        Lbcountry.add("Qatar");
        Lbcountry.add("Romania");
        Lbcountry.add("Russia");
        Lbcountry.add("Rwanda");
        Lbcountry.add("Saint Kitts and Nevis");
        Lbcountry.add("Saint Lucia");
        Lbcountry.add("Saint Vincent and the Grenadines");
        Lbcountry.add("Samoa");
        Lbcountry.add("San Marino");
        Lbcountry.add("Sao Tome and Principe");
        Lbcountry.add("Saudi Arabia");
        Lbcountry.add("Senegal");
        Lbcountry.add("Serbia");
        Lbcountry.add("Seychelles");
        Lbcountry.add("Sierra Leone");
        Lbcountry.add("Singapore");
        Lbcountry.add("Slovakia");
        Lbcountry.add("Slovenia");
        Lbcountry.add("Solomon Islands");
        Lbcountry.add("Somalia");
        Lbcountry.add("South Africa");
        Lbcountry.add("South Korea");
        Lbcountry.add("South Sudan");
        Lbcountry.add("Spain");
        Lbcountry.add("Sri Lanka");
        Lbcountry.add("Sudan");
        Lbcountry.add("Suriname");
        Lbcountry.add("Swaziland");
        Lbcountry.add("Sweden");
        Lbcountry.add("Switzerland");
        Lbcountry.add("Syria");
        Lbcountry.add("Taiwan");
        Lbcountry.add("Tajikistan");
        Lbcountry.add("Tanzania");
        Lbcountry.add("Thailand");
        Lbcountry.add("Timor-Leste");
        Lbcountry.add("Togo");
        Lbcountry.add("Tonga");
        Lbcountry.add("Trinidad and Tobago");
        Lbcountry.add("Tunisia");
        Lbcountry.add("Turkey");
        Lbcountry.add("Turkmenistan");
        Lbcountry.add("Tuvalu");
        Lbcountry.add("Uganda");
        Lbcountry.add("Ukraine");
        Lbcountry.add("United Arab Emirates");
        Lbcountry.add("United Kingdom");
        Lbcountry.add("United States of America");
        Lbcountry.add("Uruguay");
        Lbcountry.add("Uzbekistan");
        Lbcountry.add("Vanuatu");
        Lbcountry.add("Vatican City");
        Lbcountry.add("Venezuela");
        Lbcountry.add("Vietnam");
        Lbcountry.add("Yemen");
        Lbcountry.add("Zambia");
        Lbcountry.add("Zimbabwe");

        adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Lbcountry);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spcountry.setAdapter(adapter1);
    }

}
