package global.imas.bintouch;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
public class contacts extends AppCompatActivity {

    EditText Esearch;
    LinearLayout L1,L2,L3;
    String namec,phoneNumberc, tis = "";
    Button bc, Blaunch;
    private ProgressDialog mRegProgress;

    private DatabaseReference mUsersDatabase;

    MYDBHelper_inv dbhelper_inv;
    List<String> Lbchat = new ArrayList<String>();
    List<String> Lbchatkey = new ArrayList<String>();
    List<String> Lbchatimage = new ArrayList<String>();
    int flag = 0;
    CountDownLatch done;
    String user_id = "";
    String user_image = "";
    private FirebaseUser mCurrent_user;
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //
        }
    }
*/

    private DatabaseReference mfriendsDatabase;
    String uuser_id = "x";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contacts);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

Blaunch = (Button)findViewById(R.id.button81);
        // done = new CountDownLatch(1);
        //  done.countDown();
        //  done.await();


        if (true)
        {


        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();



            mfriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
            // mfriendsDatabase.keepSynced(true);


            SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(contacts.this);
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




        dbhelper_inv = new MYDBHelper_inv(this, null, null, 1);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        L1 = (LinearLayout) findViewById(R.id.Lout1);
        L1.removeAllViews();
        L2 = (LinearLayout) findViewById(R.id.Lout2);
        L2.removeAllViews();
        L3 = (LinearLayout) findViewById(R.id.Lout3);
        L3.removeAllViews();

        mUsersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                Lbchat.clear();
                Lbchatimage.clear();
                Lbchatkey.clear();
                for (DataSnapshot child : children) {
                    Lbchat.add((String) child.child("email").getValue());
                    Lbchatkey.add(child.getKey());
                    Lbchatimage.add((String) child.child("image").getValue());
                }
                search1();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Esearch = (EditText) findViewById(R.id.editText4);

        Esearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                Blaunch.setVisibility(View.VISIBLE);
            }
        });

            Blaunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    search1();
                }
            });

            Button BvoiceInsearch = (Button) findViewById(R.id.button13);
          BvoiceInsearch.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view)
                  {
                      BvoiceInsearch.setAlpha(0.3F);
                      new CountDownTimer(200,100) {
                          @Override
                          public void onTick(long l) {

                          }

                          @Override
                          public void onFinish() {
                              BvoiceInsearch.setAlpha(1.0F);
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

/*
        mRegProgress = new ProgressDialog(this);

        mRegProgress.setTitle("Loading contacts");
        mRegProgress.setMessage("Please wait");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        new CountDownTimer(200, 100) {
            @Override
            public void onTick(long l) {

            }


            @Override
            public void onFinish() {
                try {
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

                    flag = 0;
                    tis = "";

                    while (phones.moveToNext()) {
                        namec = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        phoneNumberc = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                        if (!namec.equals(tis)) {
                            tis = namec;

                            if (flag == 0) {
                                TextView t3 = new TextView(contacts.this);
                                t3.setTextSize(6);
                                t3.setText("fffff");
                                t3.setTextColor(Color.TRANSPARENT);
                                L1.addView(t3);

                                bc = new Button(contacts.this);
                                bc.setTransformationMethod(null);
                                bc.setTextSize(12);

                                int w = (int) (0.28 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                int ww = (int) (0.2 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(w, ww); // Width , height
                                bc.setLayoutParams(lparams);

                                bc.setText(Html.fromHtml(namec + "<br><font color=\"gray\"><i>☎ " + phoneNumberc));
                                bc.setBackgroundResource(R.drawable.roundbuttonwhitetogray);

                                L1.addView(bc);


                            }
                            if (flag == 1) {
                                TextView t3 = new TextView(contacts.this);
                                t3.setTextSize(6);
                                t3.setText("fffff");
                                t3.setTextColor(Color.TRANSPARENT);
                                L2.addView(t3);

                                bc = new Button(contacts.this);
                                bc.setTransformationMethod(null);
                                bc.setTextSize(12);

                                int w = (int) (0.28 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                int ww = (int) (0.2 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(w, ww);// Width , height
                                bc.setLayoutParams(lparams);

                                bc.setText(Html.fromHtml(namec + "<br><font color=\"gray\"><i>☎ " + phoneNumberc));
                                bc.setBackgroundResource(R.drawable.roundbuttonwhitetogray);

                                L2.addView(bc);

                            }
                            if (flag == 2) {
                                TextView t3 = new TextView(contacts.this);
                                t3.setTextSize(6);
                                t3.setText("fffff");
                                t3.setTextColor(Color.TRANSPARENT);
                                L3.addView(t3);

                                bc = new Button(contacts.this);
                                bc.setTransformationMethod(null);
                                bc.setTextSize(12);

                                int w = (int) (0.28 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                int ww = (int) (0.2 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(w, ww); // Width , height
                                bc.setLayoutParams(lparams);

                                bc.setText(Html.fromHtml(namec + "<br><font color=\"gray\"><i>☎ " + phoneNumberc));
                                bc.setBackgroundResource(R.drawable.roundbuttonwhitetogray);

                                L3.addView(bc);

                            }
                            flag++;
                            if (flag == 3) flag = 0;


                            if (dbhelper_inv.MAX_SCORE(phoneNumberc).equals("true")) {
                                bc.setBackgroundResource(R.drawable.roundbutton4);
                            } else {
                                bc.setBackgroundResource(R.drawable.roundbuttongray);
                            }


                            String xx = phoneNumberc;
                            xx=xx.replace(" ", "");
                            if (xx.length()>2) xx= xx.substring(2);

                           // Toast.makeText(contacts.this, xx, Toast.LENGTH_SHORT).show();

                            if (Lbchat.contains(xx))
                            {
                                bc.setBackgroundResource(R.drawable.roundbuttonb);


                            }


                            bc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    try {

                                        Button b = (Button) view;

                                        String gg = b.getText().toString();
                                        String name = gg.substring(0, gg.indexOf("☎ "));

                                        gg = gg.substring(gg.indexOf("☎ ") + 2);

                                        if (Lbchat.contains(gg)) {


                                            for (int i = 0; i < Lbchat.size(); i++)
                                                if (Lbchat.get(i).equals(gg)) {
                                                    user_id = Lbchatkey.get(i);
                                                    user_image = Lbchatimage.get(i);
                                                }


                                            final Dialog dialog = new Dialog(contacts.this);
                                            dialog.setContentView(R.layout.msglayout_publics);
                                            dialog.setTitle("Choose:");

                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            Button Bprofile = (Button) dialog.findViewById(R.id.button15);
                                            Button Bchat = (Button) dialog.findViewById(R.id.button10);
                                            TextView Tname = (TextView) dialog.findViewById(R.id.textView16);

                                            Tname.setText(name);


                                            if (user_id.equals(mCurrent_user.getUid()))
                                                Bchat.setVisibility(View.INVISIBLE);

                                            dialog.show();

                                            Bprofile.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.dismiss();
                                                    Intent startIntent = new Intent(contacts.this, ViewProfile.class);
                                                    startIntent.putExtra("user_id", user_id);
                                                    startIntent.putExtra("user_image", user_image);
                                                    startActivity(startIntent);
                                                }
                                            });

                                            Bchat.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.dismiss();
                                                    Intent startIntent = new Intent(contacts.this, activity_chats.class);
                                                    startIntent.putExtra("user_id", user_id);
                                                    startIntent.putExtra("user_image", user_image);
                                                    startActivity(startIntent);

                                                }
                                            });


                                        } else {
                                            //    sendSMS(gg, "Hi, check this app. Best chatting app ever! https://play.google.com/store/apps/details?id=imas.bintouch");
                                            Toast.makeText(contacts.this, "Invitation sent!", Toast.LENGTH_SHORT).show();


                                            Books B = new Books(gg);
                                            dbhelper_inv.addBook(B);

                                            b.setBackgroundResource(R.drawable.roundbutton4);
                                        }
                                    } catch (Exception ex) {

                                    }

                                }
                            });
                        }
                    }

                }
                //  if(flag!=3)L1.addView(L2);

                //  phones.close();

                //  }
                catch (Exception ex) {

                }
                mRegProgress.dismiss();
            }
        }.start();

*/
        //  flagito=855;

        //Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        //  Cursor managedCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        //                  new String[] {Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER}, null, null,  Phone.DISPLAY_NAME + " ASC");




          /*  Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
             while (phones.moveToNext())
            {
                String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Button b = new Button(context);
                b.setText(name);
                L1.addView(b);
            }
            phones.close();
*/

    }
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
    void search1()
    {

        mRegProgress = new ProgressDialog(this);

        mRegProgress.setTitle("Loading contacts");
        mRegProgress.setMessage("Please wait");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        Blaunch.setVisibility(View.INVISIBLE);

        new CountDownTimer(200, 100) {
            @Override
            public void onTick(long l) {

            }


            @Override
            public void onFinish() {

                L1.removeAllViews();
                L2.removeAllViews();
                L3.removeAllViews();


                try {
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

                    flag = 0;
                    tis = "";

                    while (phones.moveToNext()) {
                        namec = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        phoneNumberc = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                        if (namec.toLowerCase().contains(Esearch.getText().toString().toLowerCase()) || phoneNumberc.contains(Esearch.getText().toString())) {


                            if (!namec.equals(tis)) {
                                tis = namec;

                                if (flag == 0) {
                                    TextView t3 = new TextView(contacts.this);
                                    t3.setTextSize(6);
                                    t3.setText("fffff");
                                    t3.setTextColor(Color.TRANSPARENT);
                                    L1.addView(t3);

                                    bc = new Button(contacts.this);
                                    bc.setTransformationMethod(null);
                                    bc.setTextSize(12);

                                    int w = (int) (0.28 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                    int ww = (int) (0.2 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                    final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(w, ww); // Width , height
                                    bc.setLayoutParams(lparams);

                                    bc.setText(Html.fromHtml(namec + "<br><font color=\"gray\"><i>☎ " + phoneNumberc));
                                    bc.setBackgroundResource(R.drawable.roundbuttonwhitetogray);

                                    L1.addView(bc);


                                }
                                if (flag == 1) {
                                    TextView t3 = new TextView(contacts.this);
                                    t3.setTextSize(6);
                                    t3.setText("fffff");
                                    t3.setTextColor(Color.TRANSPARENT);
                                    L2.addView(t3);

                                    bc = new Button(contacts.this);
                                    bc.setTransformationMethod(null);
                                    bc.setTextSize(12);

                                    int w = (int) (0.28 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                    int ww = (int) (0.2 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                    final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(w, ww);// Width , height
                                    bc.setLayoutParams(lparams);

                                    bc.setText(Html.fromHtml(namec + "<br><font color=\"gray\"><i>☎ " + phoneNumberc));
                                    bc.setBackgroundResource(R.drawable.roundbuttonwhitetogray);

                                    L2.addView(bc);

                                }
                                if (flag == 2) {
                                    TextView t3 = new TextView(contacts.this);
                                    t3.setTextSize(6);
                                    t3.setText("fffff");
                                    t3.setTextColor(Color.TRANSPARENT);
                                    L3.addView(t3);

                                    bc = new Button(contacts.this);
                                    bc.setTransformationMethod(null);
                                    bc.setTextSize(12);

                                    int w = (int) (0.28 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                    int ww = (int) (0.2 * Resources.getSystem().getDisplayMetrics().widthPixels);
                                    final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(w, ww); // Width , height
                                    bc.setLayoutParams(lparams);

                                    bc.setText(Html.fromHtml(namec + "<br><font color=\"gray\"><i>☎ " + phoneNumberc));
                                    bc.setBackgroundResource(R.drawable.roundbuttonwhitetogray);

                                    L3.addView(bc);

                                }
                                flag++;
                                if (flag == 3) flag = 0;


                                if (dbhelper_inv.MAX_SCORE(phoneNumberc).equals("true")) {
                              //      bc.setBackgroundResource(R.drawable.roundbutton4);
                                } else {
                                    bc.setBackgroundResource(R.drawable.roundbuttongray);
                                }

                                String xx = phoneNumberc;
                                xx=xx.replace(" ", "");
                                while (xx.length()>2 && xx.substring(0,1).equals("0"))
                                    xx= xx.substring(1);

                                if(!xx.substring(0,1).equals("+"))
                                    xx = "+"+xx;
                                // Toast.makeText(contacts.this, xx, Toast.LENGTH_SHORT).show();

                                if (Lbchat.contains(xx))
                                {
                                    bc.setBackgroundResource(R.drawable.roundbuttonb);


                                }


                                bc.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        try {

                                            Button b = (Button) view;

                                            String gg = b.getText().toString();
                                            String name = gg.substring(0, gg.indexOf("☎ "));

                                            gg = gg.substring(gg.indexOf("☎ ") + 2);


                                            String xx = gg;
                                            xx=xx.replace(" ", "");
                                            while (xx.length()>2 && xx.substring(0,1).equals("0"))
                                                xx= xx.substring(1);

                                            if(!xx.substring(0,1).equals("+"))
                                                xx = "+"+xx;

                                            if (Lbchat.contains(xx)) {


                                                for (int i = 0; i < Lbchat.size(); i++)
                                                    if (Lbchat.get(i).equals(xx)) {
                                                        user_id = Lbchatkey.get(i);
                                                        user_image = Lbchatimage.get(i);
                                                        break;
                                                    }

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

                                                mfriendsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if(dataSnapshot.hasChild(uuser_id))
                                                        {
                                                            mfriendsDatabase.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    if(dataSnapshot.hasChild(user_id))
                                                                    {

                                                                    }
                                                                    else
                                                                    {
                                                                        HashMap<String, String> userMap = new HashMap<>();
                                                                        userMap.put("name", b.getText().toString().substring(0, b.getText().toString().indexOf("☎ ")));
                                                                        userMap.put("status", "");
                                                                        userMap.put("image", "");
                                                                        userMap.put("count", DateTime+"0");


                                                                        mfriendsDatabase.child(uuser_id).child(user_id).setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {

                                                                            }
                                                                        });
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });
                                                        }
                                                        else
                                                        {
                                                            HashMap<String, String> userMap = new HashMap<>();
                                                            userMap.put("name", b.getText().toString().substring(0, b.getText().toString().indexOf("☎ ")));
                                                            userMap.put("status", "");
                                                            userMap.put("image", "");
                                                            userMap.put("count", DateTime+"0");


                                                            mfriendsDatabase.child(uuser_id).child(user_id).setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {

                                                                }
                                                            });

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });



                                                final Dialog dialog = new Dialog(contacts.this);
                                                dialog.setContentView(R.layout.msglayout_publics);
                                                dialog.setTitle("Choose:");

                                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                                Button Bprofile = (Button) dialog.findViewById(R.id.button15);
                                                Button Bchat = (Button) dialog.findViewById(R.id.button10);
                                                TextView Tname = (TextView) dialog.findViewById(R.id.textView16);

                                                Tname.setText(name);


                                                if (user_id.substring(0,user_id.indexOf(";;;;;")).equals(mCurrent_user.getUid()))
                                                    Bchat.setVisibility(View.INVISIBLE);

                                                dialog.show();

                                                Bprofile.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        dialog.dismiss();
                                                        Intent startIntent = new Intent(contacts.this, ViewProfile.class);
                                                        startIntent.putExtra("user_id", user_id);
                                                        startIntent.putExtra("user_image", user_image);
                                                        startActivity(startIntent);
                                                    }
                                                });

                                                Bchat.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        dialog.dismiss();
                                                        Intent startIntent = new Intent(contacts.this, activity_chats.class);
                                                        startIntent.putExtra("user_id", user_id);
                                                        startIntent.putExtra("user_image", user_image);
                                                        startActivity(startIntent);

                                                    }
                                                });


                                            } else {
                                                //    sendSMS(gg, "Hi, check this app. Best chatting app ever! https://play.google.com/store/apps/details?id=imas.bintouch");
                                           /*     Toast.makeText(contacts.this, "Invitation sent!", Toast.LENGTH_SHORT).show();


                                                Books B = new Books(gg);
                                                dbhelper_inv.addBook(B);

                                                b.setBackgroundResource(R.drawable.roundbutton4);
                                                */
                                            }
                                        } catch (Exception ex) {

                                        }

                                    }
                                });
                            }
                        }
                    }

                }
                //  if(flag!=3)L1.addView(L2);

                //  phones.close();

                //  }
                catch (Exception ex) {

                }

                mRegProgress.dismiss();
            }}.start();

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
