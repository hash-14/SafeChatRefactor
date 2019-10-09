package global.imas.bintouch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class settings extends AppCompatActivity {
    RadioButton RBEng, RBAr;
    Button BprofilePublic, BprofileFriends,BblockedView,BblockedAdd;
RadioButton RBon, RBoff, RseenName, RseenPhone, RseenBoth, Rnotseen, RBen, RBdi;
    Context context = this;
    private DatabaseReference  mdata;
    private FirebaseUser mCurrent_user;
    String uuser_id = "error_Settings_SeenOrNot_in_public_search";
    Button Badd;

    TextView T1 ;
    TextView T2 ;
    TextView T3 ;
    TextView T4 ;
    TextView T5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        mdata = FirebaseDatabase.getInstance().getReference();
        mdata.keepSynced(true);


        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();



        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor11 = settings11.edit();

        String select11 = settings11.getString("select", "1");  /// 0 is default if variable not found

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

        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button Bhide = (Button)findViewById(R.id.button76);
        Button Bshow = (Button)findViewById(R.id.button77);

        Bhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor11 = settings11.edit();
                String pass = settings11.getString("passhide", "x");  /// 0 is default if variable not found

                if(pass.equals("x"))
                {

                    final Dialog dialog = new Dialog(settings.this);
                    dialog.setContentView(R.layout.msglayout_hide);
                    dialog.setTitle("Please select :");

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    //  Button rd1 = (Button) dialog.findViewById(R.id.button15);
                    //  Button rd2 = (Button) dialog.findViewById(R.id.button10);

                    dialog.show();

                    Button Bsave = (Button) dialog.findViewById(R.id.button10);
                    EditText E1s = (EditText) dialog.findViewById(R.id.editText10);
                    EditText E2s = (EditText) dialog.findViewById(R.id.editText13);



                    Bsave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String E1 = E1s.getText().toString();
                            String E2 = E2s.getText().toString();
                           if(E1.equals(""))
                           {
                               Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show();
                               return;
                           }
                            if(E1.length()<4)
                            {
                                Toast.makeText(context, "Password length must be at least 4 numbers", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(E2.equals(""))
                            {
                                Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(E2.length()<4)
                            {
                                Toast.makeText(context, "Password length must be at least 4 numbers", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(!E1.equals(E2))
                            {
                                Toast.makeText(context, "You entered two different passwords", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("passhide", E1);
                            editor.commit();

                            dialog.dismiss();

                            Hideaccounts();
                        }
                    });


                }
                else
                {

                    final Dialog dialog = new Dialog(settings.this);
                    dialog.setContentView(R.layout.msglayout_hide);
                    dialog.setTitle("Please select :");

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    //  Button rd1 = (Button) dialog.findViewById(R.id.button15);
                    //  Button rd2 = (Button) dialog.findViewById(R.id.button10);

                    dialog.show();

                    Button Bsave = (Button) dialog.findViewById(R.id.button10);
                    EditText E1s = (EditText) dialog.findViewById(R.id.editText10);
                    EditText E2s = (EditText) dialog.findViewById(R.id.editText13);
                    Bsave.setText("Enter");

                    E2s.setVisibility(View.INVISIBLE);

                    Bsave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String E1 = E1s.getText().toString();
                            String E2 = E2s.getText().toString();
                            if(E1.equals(""))
                            {
                                Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor11 = settings11.edit();
                            String pass = settings11.getString("passhide", "x");  /// 0 is default if variable not found
                            if(!E1.equals(pass))
                            {
                                Toast.makeText(context, "Wrong password!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            dialog.dismiss();

                            Hideaccounts();
                        }
                    });

                }
            }
        });

        Bshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor11 = settings11.edit();
                String pass = settings11.getString("passhide", "x");  /// 0 is default if variable not found

                if(pass.equals("x"))
                {
                    final Dialog dialog = new Dialog(settings.this);
                    dialog.setContentView(R.layout.msglayout_hide);
                    dialog.setTitle("Please select :");

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    //  Button rd1 = (Button) dialog.findViewById(R.id.button15);
                    //  Button rd2 = (Button) dialog.findViewById(R.id.button10);

                    dialog.show();

                    Button Bsave = (Button) dialog.findViewById(R.id.button10);
                    EditText E1s = (EditText) dialog.findViewById(R.id.editText10);
                    EditText E2s = (EditText) dialog.findViewById(R.id.editText13);



                    Bsave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String E1 = E1s.getText().toString();
                            String E2 = E2s.getText().toString();

                            if(E1.equals(""))
                            {
                                Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(E1.length()<4)
                            {
                                Toast.makeText(context, "Password length must be at least 4 numbers", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(E2.equals(""))
                            {
                                Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(E2.length()<4)
                            {
                                Toast.makeText(context, "Password length must be at least 4 numbers", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(!E1.equals(E2))
                            {
                                Toast.makeText(context, "You entered two different passwords", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("passhide", E1);
                            editor.commit();

                            dialog.dismiss();

                            Hideaccounts();
                        }
                    });



                }
                else
                {

                    final Dialog dialog = new Dialog(settings.this);
                    dialog.setContentView(R.layout.msglayout_hide);
                    dialog.setTitle("Please select :");

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    //  Button rd1 = (Button) dialog.findViewById(R.id.button15);
                    //  Button rd2 = (Button) dialog.findViewById(R.id.button10);

                    dialog.show();

                    Button Bsave = (Button) dialog.findViewById(R.id.button10);
                    EditText E1s = (EditText) dialog.findViewById(R.id.editText10);
                    EditText E2s = (EditText) dialog.findViewById(R.id.editText13);
                    Bsave.setText("Enter");

                    E2s.setVisibility(View.INVISIBLE);

                    Bsave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String E1 = E1s.getText().toString();
                            String E2 = E2s.getText().toString();
                            if(E1.equals(""))
                            {
                                Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor11 = settings11.edit();
                            String pass = settings11.getString("passhide", "x");  /// 0 is default if variable not found
                            if(!E1.equals(pass))
                            {
                                Toast.makeText(context, "Wrong password!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            dialog.dismiss();

                            Hideaccounts();
                        }
                    });

                }

            }
        });

        BprofilePublic = (Button)findViewById(R.id.button17);
        BprofileFriends = (Button)findViewById(R.id.button18);

        RBon = (RadioButton)findViewById(R.id.radioButton4);
        RBoff = (RadioButton)findViewById(R.id.radioButton3);

        RBen = (RadioButton)findViewById(R.id.radioButton15);
        RBdi = (RadioButton)findViewById(R.id.radioButton16);

        RBEng = (RadioButton)findViewById(R.id.radioButton7);
        RBAr = (RadioButton)findViewById(R.id.radioButton8);

        RseenName = (RadioButton)findViewById(R.id.radioButton100);
        RseenPhone = (RadioButton)findViewById(R.id.radioButton1000);
        RseenBoth = (RadioButton)findViewById(R.id.radioButton9);
        Rnotseen = (RadioButton)findViewById(R.id.radioButton10);

        BblockedView = (Button)findViewById(R.id.button71);
        BblockedAdd = (Button)findViewById(R.id.button73);


        SharedPreferences settings8 = PreferenceManager.getDefaultSharedPreferences(context);
        String lang = settings8.getString("language", "Eng");  /// 0 is default if variable not found
        if(lang.equals("Eng")) RBEng.setChecked(true);
        if(lang.equals("Ar")) RBAr.setChecked(true);

        RseenName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RseenPhone.setChecked(false);
                RseenBoth.setChecked(false);
                Rnotseen.setChecked(false);

                mdata.child("Users").child(uuser_id).child("country").setValue("yesname").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("seen", "yesname");
                        editor.commit();
                    }
                });

            }
        });
        RseenPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RseenName.setChecked(false);
                RseenBoth.setChecked(false);
                Rnotseen.setChecked(false);

                mdata.child("Users").child(uuser_id).child("country").setValue("yesphone").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("seen", "yesphone");
                        editor.commit();
                    }
                });

            }
        });
        RseenBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RseenName.setChecked(false);
                RseenPhone.setChecked(false);
                Rnotseen.setChecked(false);

                mdata.child("Users").child(uuser_id).child("country").setValue("yesboth").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("seen", "yesboth");
                        editor.commit();
                    }
                });

            }
        });
        Rnotseen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RseenName.setChecked(false);
                RseenPhone.setChecked(false);
                RseenBoth.setChecked(false);

                mdata.child("Users").child(uuser_id).child("country").setValue("no").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("seen", "no");
                        editor.commit();
                    }
                });


            }
        });


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

        BblockedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BblockedView.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        BblockedView.setAlpha(1.0F);
                        Intent startIntent = new Intent(settings.this, ViewAndDeleteBlocked.class);
                        startActivity(startIntent);
                    }
                }.start();
            }
        });
        BblockedAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BblockedAdd.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        BblockedAdd.setAlpha(1.0F);
                        Intent startIntent = new Intent(settings.this, AddBlockPerson.class);
                        startActivity(startIntent);
                    }
                }.start();
            }
        });
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String ret = settings.getString("drive", "off");  /// 0 is default if variable not found

        if(ret.equals("on"))
            RBon.setChecked(true);
        if(ret.equals("off"))
            RBoff.setChecked(true);

        SharedPreferences settingss = PreferenceManager.getDefaultSharedPreferences(this);
        String rets = settingss.getString("notitest", "enable");  /// 0 is default if variable not found

        if(rets.equals("enable"))
            RBen.setChecked(true);
        if(rets.equals("disable"))
            RBdi.setChecked(true);

        SharedPreferences settings2 = PreferenceManager.getDefaultSharedPreferences(this);
        String ret2 = settings.getString("seen", "yesboth");  /// 0 is default if variable not found

        if(ret2.equals("yesname"))
            RseenName.setChecked(true);
        if(ret2.equals("no"))
            Rnotseen.setChecked(true);
        if(ret2.equals("yesphone"))
            RseenPhone.setChecked(true);
        if(ret2.equals("yesboth"))
            RseenBoth.setChecked(true);

        RBen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RBdi.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                /////////////save////////////////
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("notitest", "enable");
                editor.commit();

            }
        });


        RBdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RBen.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                /////////////save////////////////
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("notitest", "disable");
                editor.commit();

            }
        });

        RBon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RBoff.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                /////////////save////////////////
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("drive", "on");
                editor.commit();

            }
        });


        RBoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RBon.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                /////////////save////////////////
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("drive", "off");
                editor.commit();

            }
        });

        BprofilePublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BprofilePublic.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        BprofilePublic.setAlpha(1.0F);
                    }
                }.start();

                Intent startIntent = new Intent (settings.this, ProfileSeenByPublic.class);
                startActivity(startIntent);
            }
        });
        BprofileFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BprofileFriends.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        BprofileFriends.setAlpha(1.0F);
                    }
                }.start();
                Intent startIntent = new Intent (settings.this, ProfileSeenByFriends.class);
                startActivity(startIntent);
            }
        });



    }
    void    Hideaccounts()
    {
        final Dialog dialog = new Dialog(settings.this);
        dialog.setContentView(R.layout.msglayout_hide2);
        dialog.setTitle("Please select :");

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //  Button rd1 = (Button) dialog.findViewById(R.id.button15);
        //  Button rd2 = (Button) dialog.findViewById(R.id.button10);

        dialog.show();

        Button Bback = (Button) dialog.findViewById(R.id.button10);
        Bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

         T1 = (TextView) dialog.findViewById(R.id.textView85);
         T2 = (TextView) dialog.findViewById(R.id.textView87);
         T3 = (TextView) dialog.findViewById(R.id.textView88);
         T4 = (TextView) dialog.findViewById(R.id.textView89);
         T5 = (TextView) dialog.findViewById(R.id.textView90);

        RadioButton Rh1 = (RadioButton) dialog.findViewById(R.id.radioButton12);
        RadioButton Rh2 = (RadioButton) dialog.findViewById(R.id.radioButton14);
        RadioButton Rh3 = (RadioButton) dialog.findViewById(R.id.radioButton20);
        RadioButton Rh4 = (RadioButton) dialog.findViewById(R.id.radioButton22);
        RadioButton Rh5 = (RadioButton) dialog.findViewById(R.id.radioButton24);

        RadioButton Rs1 = (RadioButton) dialog.findViewById(R.id.radioButton11);
        RadioButton Rs2 = (RadioButton) dialog.findViewById(R.id.radioButton13);
        RadioButton Rs3 = (RadioButton) dialog.findViewById(R.id.radioButton19);
        RadioButton Rs4 = (RadioButton) dialog.findViewById(R.id.radioButton21);
        RadioButton Rs5 = (RadioButton) dialog.findViewById(R.id.radioButton23);

        ref();

        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor11 = settings11.edit();
        String state = settings11.getString("hidestate1", "show");  /// 0 is default if variable not found
        if(state.equals("hide"))  Rh1.setChecked(true);
        else Rs1.setChecked(true);
        String state2 = settings11.getString("hidestate2", "show");  /// 0 is default if variable not found
        if(state2.equals("hide"))  Rh2.setChecked(true);
        else Rs2.setChecked(true);
        String state3 = settings11.getString("hidestate3", "show");  /// 0 is default if variable not found
        if(state3.equals("hide"))  Rh3.setChecked(true);
        else Rs3.setChecked(true);
        String state4 = settings11.getString("hidestate4", "show");  /// 0 is default if variable not found
        if(state4.equals("hide"))  Rh4.setChecked(true);
        else Rs4.setChecked(true);
        String state5 = settings11.getString("hidestate5", "show");  /// 0 is default if variable not found
        if(state5.equals("hide"))  Rh5.setChecked(true);
        else Rs5.setChecked(true);


        Rh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rs1.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("hidestate1", "hide");
                editor.commit();
            }
        });
        Rh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rs2.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("hidestate2", "hide");
                editor.commit();
            }
        });
        Rh3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rs3.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("hidestate3", "hide");
                editor.commit();
            }
        });
        Rh4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rs4.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("hidestate4", "hide");
                editor.commit();
            }
        });
        Rh5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rs5.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("hidestate5", "hide");
                editor.commit();
            }
        });
        Rs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rh1.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("hidestate1", "show");
                editor.commit();
            }
        });
        Rs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rh2.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("hidestate2", "show");
                editor.commit();
            }
        });
        Rs3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rh3.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("hidestate3", "show");
                editor.commit();
            }
        });
        Rs4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rh4.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("hidestate4", "show");
                editor.commit();
            }
        });
        Rs5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rh5.setChecked(false);
                SharedPreferences settingst = PreferenceManager.getDefaultSharedPreferences(settings.this);
                SharedPreferences.Editor editor = settingst.edit();
                editor.putString("hidestate5", "show");
                editor.commit();
            }
        });
    }

    DatabaseReference  mData;
    void ref()
    {



        mData = FirebaseDatabase.getInstance().getReference().child("Users");
            mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();



            mData.child(mCurrent_user.getUid() +";;;;;profile_1").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!(dataSnapshot.child("name").getValue(String.class).equals("")))
                        T1.setText(dataSnapshot.child("name").getValue(String.class));
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

        mData.child(mCurrent_user.getUid() +";;;;;profile_2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("name").getValue(String.class).equals("")))
                    T2.setText(dataSnapshot.child("name").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        mData.child(mCurrent_user.getUid() +";;;;;profile_3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("name").getValue(String.class).equals("")))
                    T3.setText(dataSnapshot.child("name").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        mData.child(mCurrent_user.getUid() +";;;;;profile_4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("name").getValue(String.class).equals("")))
                    T4.setText(dataSnapshot.child("name").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        mData.child(mCurrent_user.getUid() +";;;;;profile_5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("name").getValue(String.class).equals("")))
                    T5.setText(dataSnapshot.child("name").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });



    }
}
