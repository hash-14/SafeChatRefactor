package global.imas.bintouch;

// attach document and video to chatpage
// divert sent to both persons - or group with no notifications
// editing image library
// group chat fix


/*
<uses-permission android:name="android.permission.SEND_SMS" />

 <uses-feature android:name="android.hardware.sip.voip" android:required="true" />
    <uses-feature android:name="android.hardware.wifi" android:required="true" />



    <uses-feature
        android:glEsVersion="0x00020000"
        android:required="true" />


1 00 1 00  copy
1 11 1 11  forward
1 00 0 00  edit
1 00 1 00  history
1 11 1 11  delete
1 11 0 00  delete everyone
*/

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


//<receiver android:name=".IncomingCallReceiver" android:label="Call Receiver" />

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    String uuser_id = "x123error";
   EditText Esearch;
    DatabaseReference  mData;
    int count1=0;
    int count2=0;
    int count3=0;
    int count4=0;
    int count5=0;


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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {

       // FirebaseDatabase.getInstance().goOffline();
        DatabaseReference userStatussssa = FirebaseDatabase.getInstance().getReference().child("State_in_app").child(uuser_id);
        userStatussssa.setValue("Offline_in_app");

        finishAffinity();
    }

    public boolean isApplicationSentToBackground(final Context context)
    {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onStop()
    {
        if (isApplicationSentToBackground(this)){
            //put your code here what u want to do
            DatabaseReference userStatussssa = FirebaseDatabase.getInstance().getReference().child("State_in_app").child(uuser_id);
            userStatussssa.setValue("Offline_in_app");
        }
        super.onStop();
        // FirebaseDatabase.getInstance().goOffline();

    }

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    MediaPlayer mp;
    ImageView IVmsg;

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    AlertDialog alertDialog1;
    NavigationView nav_view;
    View hView;
    Menu nav_Menu;

    EditText Emsg;

    private RecyclerView mUsersList;
    private DatabaseReference mD, mdata, mUsersDatabase, mChatDatabase, mGroupsDatabase,mfriendsDatabase,mfriendsCurrentUserDatabase;

    private FirebaseUser mCurrent_user;

    ImageView IVchat, IVcontacts, IVcalls, IVdelayedmsg, IVautoReply, IVDiver, IVsettings;

    Context context = this;
    private TabLayout mTabLayout;

    EditText E;
    TextView T, Tmsg;
    Button B, Bmsg,  Baudio;
    int flagg=1;
    int xxx = 0;
    String yyy = "";

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                   // finish();
                }
                   else if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_CONTACTS}, 2);
                    }
                break;
            case 2:
                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                { // finish();

                }
                else
                {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, 3);
                    }
                }
                break;
            case 3:
                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                {
                    //finish();
                }
                else
                {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
                }
                    // Close your app
                }
                break;
            case 4:
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                   // finish();
                }
                else
                {
                    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECORD_AUDIO},5);
                }
                    // Close your app

                }
                break;
            case 5:
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                {
                   // finish();
                }
                else
                {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA},6);
                    }
                    // Close your app

                }
                break;
            case 6:
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                   // finish();
                }
                else
                {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SET_ALARM},7);
                    }

                }
                break;
        case 7:
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED)
        {
       //     finish();
        }
        else
        {
            // Close your app
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WAKE_LOCK},8);
            }

        }
        break;


      case 8:
              if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED)
    {
    //    finish();
    }
                else
    {
        // Close your app
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},9);
        }

    }
                break;


  case 9:
          if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED)
          {
       //   finish();
          }
          else
          {
          // Close your app

          }
          break;

          }
}
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        mRegProgress1=new ProgressDialog(MainActivity.this);
        mRegProgress1.setTitle("Loading profile");
        // mRegProgress.setMessage("Please wait while creating your account");
        mRegProgress1.setCanceledOnTouchOutside(false);
        mRegProgress1.show();
        mRegProgress1.setMax(0);
        mRegProgress1.setProgress(0);



/*

        FirebaseAuth.getInstance().signOut();
        Intent startIntenft = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntenft);
        finish();
*/
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(startIntent);
            finish();
        }
        else {



        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_CONTACTS}, 2);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, 3);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
        }
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECORD_AUDIO},5);
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA},6);
        }


            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SET_ALARM}, 7);
            }
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WAKE_LOCK},8);
            }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},9);
            }


         count1=0;
         count2=0;
         count3=0;
         count4=0;
         count5=0;
/*
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        AlarmManager alarms = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        Receiver receiver = new Receiver();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);

        Intent intent = new Intent("ALARM_ACTION");
        intent.putExtra("param", "My scheduled message is sent");
        PendingIntent operation = PendingIntent.getBroadcast(this, 0, intent, 0);
        // I choose 3s after the launch of my application
        alarms.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000, operation) ;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
*/
        SharedPreferences settings11l = PreferenceManager.getDefaultSharedPreferences(context);
        String language = settings11l.getString("language", "");  /// 0 is default if variable not found
        if(language.equals("Ar"))
        {
            E = (EditText)findViewById(R.id.editText);
            E.setHint("اكتب رسالة");

            T = (TextView)findViewById(R.id.textView5);
            T.setText("بحث");

            T = (TextView)findViewById(R.id.textView6);
            T.setText("محادثات");

            T = (TextView)findViewById(R.id.textView7);
            T.setText("مكالمات");

            T = (TextView)findViewById(R.id.textView8);
            T.setText("رسالة متأخرة");

            T = (TextView)findViewById(R.id.textView9);
            T.setText("رد آلي");

            T = (TextView)findViewById(R.id.textView10);
            T.setText("تحويل");

            T = (TextView)findViewById(R.id.textView11);
            T.setText("ضبط");
        }



        //  FirebaseAuth.getInstance().signOut();

/*

        SharedPreferences settings1 = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor1 = settings1.edit();

            editor1.putString("select", "");
            editor1.putString("Acc1Username", "");
            editor1.putString("Acc1Password", "");

            editor1.putString("select", "");
            editor1.putString("Acc2Username", "");
            editor1.putString("Acc2Password", "");

            editor1.putString("select", "");
            editor1.putString("Acc3Username", "");
            editor1.putString("Acc3Password", "");

            editor1.putString("select", "");
            editor1.putString("Acc4Username", "");
            editor1.putString("Acc4Password", "");

            editor1.putString("select", "");
            editor1.putString("Acc5Username", "");
            editor1.putString("Acc5Password", "");

        editor1.commit();
     */

/*
        DatabaseReference userStatus = FirebaseDatabase.getInstance().getReference("State").child(mCurrent_user.getUid());
        userStatus.setValue("Online");

        userStatus.onDisconnect().setValue("Offline");
*/
        //   FirebaseUser currentUserd = mAuth.getCurrentUser();


            IVmsg = (ImageView)findViewById(R.id.imageView23);
            Bmsg = (Button) findViewById(R.id.button74);
            Tmsg = (TextView) findViewById(R.id.textView84);

            mp = MediaPlayer.create(MainActivity.this, R.raw.unconvinced);


            Emsg = (EditText) findViewById(R.id.editText);
            Emsg.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (Emsg.getText().toString().equals("Beirut123")) {
                        Emsg.setText("");
                        Intent startIntent = new Intent(MainActivity.this, secret.class);
                        startActivity(startIntent);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            mAuth = FirebaseAuth.getInstance();

            mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("");

            TextView aasdasd = (TextView)findViewById(R.id.textView6);
            aasdasd.setText(Html.fromHtml("<u><b>Chats"));

            //   mViewPager = (ViewPager)findViewById(R.id.tabPager);
            //   mSectionsPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());
            //   mViewPager.setAdapter(mSectionsPagerAdapter);

            //   mTabLayout = (TabLayout)findViewById(R.id.main_tabs);
            //   mTabLayout.setupWithViewPager(mViewPager);


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();



            Esearch = (EditText) findViewById(R.id.editText4);
             Baudio = (Button) findViewById(R.id.button13);





            toolbarsetinit();



            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            nav_view = (NavigationView) findViewById(R.id.nav_view);
            nav_view.setNavigationItemSelectedListener(this);

            hView = nav_view.getHeaderView(0);
            nav_Menu = nav_view.getMenu();


            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = settings.edit();

            String select = settings.getString("select", "");  /// 0 is default if variable not found

            nav_Menu.findItem(R.id.nav_acc1).setVisible(false);
            nav_Menu.findItem(R.id.nav_acc2).setVisible(false);
            nav_Menu.findItem(R.id.nav_acc3).setVisible(false);
            nav_Menu.findItem(R.id.nav_acc4).setVisible(false);
            nav_Menu.findItem(R.id.nav_acc5).setVisible(false);

            if (select.equals("1")) {
                nav_Menu.findItem(R.id.nav_acc1).setIcon(R.drawable.ic_play_arrow_red_24dp);
                nav_Menu.findItem(R.id.nav_acc1).setCheckable(true).setChecked(true);
                nav_Menu.findItem(R.id.nav_acc2).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc3).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc4).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc5).setIcon(R.drawable.emptyi);
                xxx = 1;
            }
            if (select.equals("2")) {
                nav_Menu.findItem(R.id.nav_acc1).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc2).setIcon(R.drawable.ic_play_arrow_red_24dp);
                nav_Menu.findItem(R.id.nav_acc2).setCheckable(true).setChecked(true);
                nav_Menu.findItem(R.id.nav_acc3).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc4).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc5).setIcon(R.drawable.emptyi);
                xxx = 2;
            }
            if (select.equals("3")) {

                nav_Menu.findItem(R.id.nav_acc1).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc2).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc3).setIcon(R.drawable.ic_play_arrow_red_24dp);
                nav_Menu.findItem(R.id.nav_acc3).setCheckable(true).setChecked(true);
                nav_Menu.findItem(R.id.nav_acc4).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc5).setIcon(R.drawable.emptyi);
                xxx = 3;
            }
            if (select.equals("4")) {
                nav_Menu.findItem(R.id.nav_acc1).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc2).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc3).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc4).setIcon(R.drawable.ic_play_arrow_red_24dp);
                nav_Menu.findItem(R.id.nav_acc4).setCheckable(true).setChecked(true);
                nav_Menu.findItem(R.id.nav_acc5).setIcon(R.drawable.emptyi);
                xxx = 4;
            }
            if (select.equals("5")) {
                nav_Menu.findItem(R.id.nav_acc1).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc2).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc3).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc4).setIcon(R.drawable.emptyi);
                nav_Menu.findItem(R.id.nav_acc5).setIcon(R.drawable.ic_play_arrow_red_24dp);
                nav_Menu.findItem(R.id.nav_acc5).setCheckable(true).setChecked(true);
                xxx = 5;
            }





            if(language.equals("Ar"))
            {
                nav_Menu.findItem(R.id.nav_share).setTitle("شارك");
                nav_Menu.findItem(R.id.nav_signout).setTitle("خروج");
                nav_Menu.findItem(R.id.nav_add).setTitle("إضافة حساب");
               // nav_Menu.findItem(R.id.csd).setTitle("حسابات");
              //  nav_Menu.findItem(R.id.sssd).setTitle("نقل");

            }

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chats");
            mChatDatabase.keepSynced(true);
            mfriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
            mfriendsDatabase.keepSynced(true);


            mGroupsDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");
            mGroupsDatabase.keepSynced(true);

            LinearLayoutManager ll = new LinearLayoutManager(this);
            mUsersList = (RecyclerView) findViewById(R.id.recyclerView);
            mUsersList.setHasFixedSize(true);
            ll.setReverseLayout(true);
            ll.setStackFromEnd(true);
            mUsersList.setLayoutManager(ll);
            mUsersList.setVisibility(View.VISIBLE);
            mUsersList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));




            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    Toast.makeText(MainActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    // Toast.makeText(MainActivity.this, swipeDir+"on Swiped ", Toast.LENGTH_SHORT).show();
                    //Remove swiped item from list and notify the RecyclerView

                    final Dialog dialog = new Dialog(MainActivity.this);
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

                    yii = 0;
                    firebaseRecyclerAdapter.notifyDataSetChanged();

                    BB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            final int position = viewHolder.getAdapterPosition();


                            mfriendsDatabase.child(uuser_id).child(kkey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    yii = 0;
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

                            Intent startIntent = new Intent(MainActivity.this, ViewProfileFriends.class);
                            startIntent.putExtra("user_id", kkey);
                            startActivity(startIntent);


                        }
                    });

                }
            };

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(mUsersList);


            mData = FirebaseDatabase.getInstance().getReference().child("Users");
            mData.keepSynced(true);

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

            mfriendsCurrentUserDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(uuser_id);
            mfriendsCurrentUserDatabase.keepSynced(true);

            DatabaseReference userStatusssa = FirebaseDatabase.getInstance().getReference().child("State_with_you").child(uuser_id);
            userStatusssa.setValue("");

            if(!uuser_id.equals("x123error")) {
                DatabaseReference userStatuss = FirebaseDatabase.getInstance().getReference().child("State_with_you").child(uuser_id);
                userStatuss.setValue("");

                userStatuss.onDisconnect().setValue("");



                DatabaseReference userStatus = FirebaseDatabase.getInstance().getReference().child("State_in_app").child(uuser_id);
                userStatus.setValue("Online_in_app");

                userStatus.onDisconnect().setValue("Offline_in_app");
            }
            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mUsersDatabase.keepSynced(true);

            mdata = FirebaseDatabase.getInstance().getReference();
            mdata.keepSynced(true);

            mdata.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    for (DataSnapshot child : children) {

                        if(child.hasChild("name"))
                        {

                        }
                        else
                        {
                            mdata.child("Users").child(child.getKey()).removeValue();
                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mdata.child("blocked_contacts").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mdata.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor11 = settings11.edit();

                    String select11 = settings11.getString("select", "1");  /// 0 is default if variable not found

                    if (!select11.equals("1")) {
                        mdata.child("State_in_app").child(mCurrent_user.getUid() + ";;;;;profile_1").setValue("Offline_in_app");
                    }
                    if (!select11.equals("2")) {
                        mdata.child("State_in_app").child(mCurrent_user.getUid() + ";;;;;profile_2").setValue("Offline_in_app");
                    }
                    if (!select11.equals("3")) {
                        mdata.child("State_in_app").child(mCurrent_user.getUid() + ";;;;;profile_3").setValue("Offline_in_app");
                    }
                    if (!select11.equals("4")) {
                        mdata.child("State_in_app").child(mCurrent_user.getUid() + ";;;;;profile_4").setValue("Offline_in_app");
                    }
                    if (!select11.equals("5")) {
                        mdata.child("State_in_app").child(mCurrent_user.getUid() + ";;;;;profile_5").setValue("Offline_in_app");
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            IVmsg.setVisibility(View.INVISIBLE);
            Bmsg.setVisibility(View.INVISIBLE);
            Tmsg.setVisibility(View.INVISIBLE);

            mdata.child("Diver_assign_by_me").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Toast.makeText(context, "Received messages will be diverted to ", Toast.LENGTH_LONG).show();
                    if(dataSnapshot.hasChild(uuser_id))
                    {
                        IVmsg.setVisibility(View.VISIBLE);
                        Bmsg.setVisibility(View.VISIBLE);
                        Tmsg.setVisibility(View.VISIBLE);
                        Tmsg.setText("Received messages will be diverted to\n"+dataSnapshot.child(uuser_id).child("name").getValue(String.class));
                        Bmsg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                IVmsg.setVisibility(View.INVISIBLE);
                                Bmsg.setVisibility(View.INVISIBLE);
                                Tmsg.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




            ////////////to check if delayed msgs must trigger or not//////////////////
/*
            mdata.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild("Delayed_msg"))
                    {
                        mdata.child("Delayed_msg").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                if(dataSnapshot.hasChild(uuser_id))
                                {
                                    mdata.child("Delayed_msg").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {




                                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                            for (DataSnapshot child : children) {

                                                int yf = Integer.parseInt(child.child("date_from_y").getValue(String.class));
                                                int mf = Integer.parseInt(child.child("date_from_m").getValue(String.class));
                                                int df = Integer.parseInt(child.child("date_from_d").getValue(String.class));
                                                int hf = Integer.parseInt(child.child("time_from_h").getValue(String.class));
                                                int mif = Integer.parseInt(child.child("time_from_m").getValue(String.class));

                                                Calendar calendar = Calendar.getInstance();
                                                int d = calendar.get(Calendar.DAY_OF_MONTH);
                                                int y = calendar.get(Calendar.YEAR);
                                                int m = calendar.get(Calendar.MONTH)+1;
                                                int h = calendar.get(Calendar.HOUR_OF_DAY);
                                                int mi = calendar.get(Calendar.MINUTE);

                                                if(
                                                        (yf<y)
                                                        || ((yf==y)&&(mf<m))
                                                        || ((yf==y)&&(mf==m)&&(df<d))
                                                        || ((yf==y)&&(mf==m)&&(df==d)&&(hf<h))
                                                        || ((yf==y)&&(mf==m)&&(df==d)&&(hf==h)&&(mif<mi))
                                                        )
                                                {

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

                                                    HashMap<String, String> object = new HashMap<String, String>();
                                                    object.put("from", child.child("from").getValue(String.class));
                                                    object.put("msg_or_url", child.child("auto_reply_msg").getValue(String.class));
                                                    object.put("msg_type", "text");
                                                    object.put("dateTime", DateTime);
                                                    object.put("sent_received_seen", "sent"); //  sent , received, seen
                                                    object.put("from_orignal_id", child.child("from").getValue(String.class));

                                                    String  outputs1 = mdata.child("Chats").child(uuser_id).child(child.getKey()).push().getKey();
                                                    String  outputs2 = mdata.child("Chats").child(child.getKey()).child(uuser_id).push().getKey();

                                                    if(child.child("from").getValue(String.class).equals(uuser_id))
                                                    object.put("from_orignal_key", outputs1); //  sent , received, seen
                                                    else
                                                        object.put("from_orignal_key", outputs2); //  sent , received, seen


                                                    mdata.child("Chats").child(uuser_id).child(child.getKey()).child(outputs1).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    });
                                                    mdata.child("Chats").child(child.getKey()).child(uuser_id).child(outputs2).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    });

                                                    HashMap<String, String> object2 = new HashMap<String, String>();
                                                    object2.put("msg_or_url", child.child("auto_reply_msg").getValue(String.class));

                                                    if(child.child("from").getValue(String.class).equals(uuser_id))
                                                        mdata.child("Notifications").child(uuser_id).child(child.getKey()).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                            }
                                                        });
                                                    else
                                                        mdata.child("Notifications").child(child.getKey()).child(uuser_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                            }
                                                        });

                                                    String keyy = child.getKey();
                                                    String msgss = child.child("auto_reply_msg").getValue(String.class);

                                                    mfriendsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            if(dataSnapshot.hasChild(uuser_id))
                                                            {
                                                                mfriendsDatabase.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {


                                                                        if(dataSnapshot.hasChild(keyy)) {
                                                                            mfriendsDatabase.child(uuser_id).child(keyy).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                    int count = Integer.parseInt(dataSnapshot.child("count").getValue(String.class));
                                                                                    count++;

                                                                                    mfriendsDatabase.child(keyy).child(uuser_id).child("count").setValue(count+"").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {

                                                                                        }
                                                                                    });
                                                                                    mfriendsDatabase.child(uuser_id).child(keyy).child("status").setValue(msgss).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {

                                                                                        }
                                                                                    });

                                                                                }

                                                                                @Override
                                                                                public void onCancelled(DatabaseError databaseError) {

                                                                                }
                                                                            });
                                                                        }

                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {

                                                                    }
                                                                });


                                                            }


                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });


                                                    mdata.child("Delayed_msg").child(uuser_id).child(child.getKey()).removeValue();


                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
*/
            refreshAcc();

           // mfriendsCurrentUserDatabase



                    adapter();


        mdata.child("Friends").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(mCurrent_user.getUid() +";;;;;profile_1"))
                {
                    mdata.child("Users").child(mCurrent_user.getUid() +";;;;;profile_1").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.child("name").getValue(String.class).equals("")) {
                                mdata.child("Friends").child(mCurrent_user.getUid() +";;;;;profile_1").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                        count1 = 0;
                                        for (DataSnapshot child : children) {
                                            count1 += Integer.parseInt(child.child("count").getValue(String.class).substring(15));
                                        }
                                        if(count1==0)
                                        {
                                            TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc1).getActionView();
                                            view.setVisibility(View.INVISIBLE);
                                        }
                                        else
                                        {
                                            TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc1).getActionView();
                                            view.setVisibility(View.VISIBLE);
                                            view.setText(""+count1);
                                        }

                                        TextView T = (TextView)findViewById(R.id.hamburger_count);

                                        if((count1+count2+count3+count4+count5)==0) T.setVisibility(View.INVISIBLE);
                                        else T.setVisibility(View.VISIBLE);

                                        T.setText((count1+count2+count3+count4+count5)+"");
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            mdata.child("Friends").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(mCurrent_user.getUid() +";;;;;profile_2"))
                    {
                        mdata.child("Users").child(mCurrent_user.getUid() +";;;;;profile_2").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.child("name").getValue(String.class).equals(""))
                                {
                                    mdata.child("Friends").child(mCurrent_user.getUid() +";;;;;profile_2").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                            count2 = 0;
                                            for (DataSnapshot child : children) {
                                                count2 += Integer.parseInt(child.child("count").getValue(String.class).substring(15));
                                            }
                                            if(count2==0)
                                            {
                                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc2).getActionView();
                                                view.setVisibility(View.INVISIBLE);
                                            }
                                            else
                                            {
                                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc2).getActionView();
                                                view.setVisibility(View.VISIBLE);
                                                view.setText(""+count2);
                                            }
                                            TextView T = (TextView)findViewById(R.id.hamburger_count);

                                            if((count1+count2+count3+count4+count5)==0) T.setVisibility(View.INVISIBLE);
                                            else T.setVisibility(View.VISIBLE);

                                            T.setText((count1+count2+count3+count4+count5)+"");
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mdata.child("Friends").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(mCurrent_user.getUid() +";;;;;profile_3"))
                    {
                        mdata.child("Users").child(mCurrent_user.getUid() +";;;;;profile_3").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.child("name").getValue(String.class).equals("")) {
                                    mdata.child("Friends").child(mCurrent_user.getUid() +";;;;;profile_3").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                            count3 = 0;
                                            for (DataSnapshot child : children) {
                                                count3 += Integer.parseInt(child.child("count").getValue(String.class).substring(15));
                                            }
                                            if(count3==0)
                                            {
                                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc3).getActionView();
                                                view.setVisibility(View.INVISIBLE);
                                            }
                                            else
                                            {
                                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc3).getActionView();
                                                view.setVisibility(View.VISIBLE);
                                                view.setText(""+count3);
                                            }
                                            TextView T = (TextView)findViewById(R.id.hamburger_count);

                                            if((count1+count2+count3+count4+count5)==0) T.setVisibility(View.INVISIBLE);
                                            else T.setVisibility(View.VISIBLE);

                                            T.setText((count1+count2+count3+count4+count5)+"");
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });


                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mdata.child("Friends").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(mCurrent_user.getUid() +";;;;;profile_4"))
                    {
                        mdata.child("Users").child(mCurrent_user.getUid() +";;;;;profile_4").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.child("name").getValue(String.class).equals("")) {
                                    mdata.child("Friends").child(mCurrent_user.getUid() +";;;;;profile_4").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                            count4 = 0;
                                            for (DataSnapshot child : children) {
                                                count4 += Integer.parseInt(child.child("count").getValue(String.class).substring(15));
                                            }
                                            if(count4==0)
                                            {
                                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc4).getActionView();
                                                view.setVisibility(View.INVISIBLE);
                                            }
                                            else
                                            {
                                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc4).getActionView();
                                                view.setVisibility(View.VISIBLE);
                                                view.setText(""+count4);
                                            }
                                            TextView T = (TextView)findViewById(R.id.hamburger_count);

                                            if((count1+count2+count3+count4+count5)==0) T.setVisibility(View.INVISIBLE);
                                            else T.setVisibility(View.VISIBLE);

                                            T.setText((count1+count2+count3+count4+count5)+"");
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mdata.child("Friends").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(mCurrent_user.getUid() +";;;;;profile_5"))
                    {
                        mdata.child("Users").child(mCurrent_user.getUid() +";;;;;profile_5").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.child("name").getValue(String.class).equals("")){
                                    mdata.child("Friends").child(mCurrent_user.getUid() +";;;;;profile_5").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                            count5 = 0;
                                            for (DataSnapshot child : children) {
                                                count5 += Integer.parseInt(child.child("count").getValue(String.class).substring(15));
                                            }
                                            if(count5==0)
                                            {
                                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc5).getActionView();
                                                view.setVisibility(View.INVISIBLE);
                                            }
                                            else
                                            {
                                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc5).getActionView();
                                                view.setVisibility(View.VISIBLE);
                                                view.setText(""+count5);
                                            }
                                            TextView T = (TextView)findViewById(R.id.hamburger_count);

                                            if((count1+count2+count3+count4+count5)==0) T.setVisibility(View.INVISIBLE);
                                            else T.setVisibility(View.VISIBLE);

                                            T.setText((count1+count2+count3+count4+count5)+"");
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


      mChatDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                if (dataSnapshot.hasChild(uuser_id)) {

                    mChatDatabase.child(uuser_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //adapter();
                            yii = 0;
                            firebaseRecyclerAdapter.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






            Esearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    yii = 0;
                    firebaseRecyclerAdapter.notifyDataSetChanged();
                }
            });



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



            IVchat = (ImageView) findViewById(R.id.imageView8);
        IVchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IVchat.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        IVchat.setAlpha(1.0F);
                    }
                }.start();

              //  Intent startIntent = new Intent(MainActivity.this, chatList.class);
              //  startActivity(startIntent);
            }
        });

           ImageView IVgroup = (ImageView) findViewById(R.id.imageView420);
            IVgroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    IVgroup.setAlpha(0.3F);
                    new CountDownTimer(200,100) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            IVgroup.setAlpha(1.0F);
                            Intent startIntent = new Intent (MainActivity.this, GroupList.class);
                            startActivity(startIntent);
                        }
                    }.start();

/*
                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.msglayout_create_group);
                        dialog.setTitle("Please select :");

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        Button BB = (Button) dialog.findViewById(R.id.button15);
                        final EditText EE = (EditText) dialog.findViewById(R.id.editText8);

                        dialog.show();


                        BB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(EE.getText().toString().equals(""))
                                {
                                    Toast.makeText(context, "Enter the group name", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                dialog.dismiss();
                                mRegProgress=new ProgressDialog(MainActivity.this);
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

                                mGroupsDatabase.child(passito).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {

                                            HashMap<String, String> userMap = new HashMap<>();
                                            userMap.put("count", "0");
                                            userMap.put("image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/user-group-icon.png?alt=media&token=772e44b3-9d93-4b99-a96d-09f6790de324");
                                            userMap.put("name", EE.getText().toString());
                                            userMap.put("status", "");




                                            mfriendsDatabase.child(uuser_id).child(passito).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()) {

                                                        mRegProgress.dismiss();
                                                        Intent startIntent = new Intent (MainActivity.this, Create_Account.class);
                                                        startIntent.putExtra("group_name", EE.getText().toString());
                                                        startIntent.putExtra("passito", passito);
                                                        startActivity(startIntent);




                                                    }
                                                }
                                            });



                                        }
                                    }
                                });


                            }
                        });
*/







                }
            });



            IVcontacts = (ImageView) findViewById(R.id.imageView7);
        IVcontacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IVcontacts.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        IVcontacts.setAlpha(1.0F);
                    }
                }.start();

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.msglayout_search);
                dialog.setTitle("Please select :");

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Button rd1 = (Button) dialog.findViewById(R.id.button15);
                Button rd2 = (Button) dialog.findViewById(R.id.button10);


                SharedPreferences settings11l = PreferenceManager.getDefaultSharedPreferences(context);
                String language = settings11l.getString("language", "");  /// 0 is default if variable not found
                if(language.equals("Ar")) {
                    rd1.setText("البحث العام");
                    rd2.setText("البحث بالمفكرة");
                }
                dialog.show();

                rd1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent startIntent = new Intent(MainActivity.this, publics.class);
                        startActivity(startIntent);
                    }
                });
                rd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        Intent startIntent = new Intent(MainActivity.this, contacts.class);
                        startActivity(startIntent);
                        /*
                        int f = 0;

                        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_CONTACTS}, 0);
                            f = 1;
                        }
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, 0);
                            f = 1;
                        }
                        if(f==0) {
                            Intent startIntent = new Intent(MainActivity.this, contacts.class);
                            startActivity(startIntent);
                        }
                        */
                    }
                });


            }
        });


        IVcalls = (ImageView) findViewById(R.id.imageView6);
        IVcalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IVcalls.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        IVcalls.setAlpha(1.0F);
                    }
                }.start();

               Toast.makeText(context, "Will be available in next version", Toast.LENGTH_SHORT).show();

                // Intent startIntent = new Intent(MainActivity.this, calls.class);
               // startActivity(startIntent);
            }
        });


        IVdelayedmsg = (ImageView) findViewById(R.id.imageView9);
        IVdelayedmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IVdelayedmsg.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        IVdelayedmsg.setAlpha(1.0F);
                    }
                }.start();


                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.msglayout);
                dialog.setTitle("Please select :");

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Button rd1 = (Button) dialog.findViewById(R.id.button15);
                Button rd2 = (Button) dialog.findViewById(R.id.button10);

                dialog.show();

                rd1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent startIntent = new Intent(MainActivity.this, delayedmsg.class);
                        startActivity(startIntent);
                    }
                });
                rd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent startIntent = new Intent(MainActivity.this, delayedmsg_add.class);
                        startActivity(startIntent);
                    }
                });


            }
        });


        IVautoReply = (ImageView) findViewById(R.id.imageView5);
        IVautoReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IVautoReply.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        IVautoReply.setAlpha(1.0F);
                    }
                }.start();


                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.msglayout2);
                dialog.setTitle("Please select :");

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Button rd1 = (Button) dialog.findViewById(R.id.button15);
                Button rd2 = (Button) dialog.findViewById(R.id.button10);

                dialog.show();

                rd1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent startIntent = new Intent(MainActivity.this, autoreply.class);
                        startActivity(startIntent);
                    }
                });
                rd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent startIntent = new Intent(MainActivity.this, autoreply_add.class);
                        startActivity(startIntent);
                    }
                });


            }
        });


        IVDiver = (ImageView) findViewById(R.id.imageView4);
        IVDiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IVDiver.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        IVDiver.setAlpha(1.0F);
                    }
                }.start();
                // Toast.makeText(context, "Will be available in next version", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(MainActivity.this, diver.class);
                startActivity(startIntent);
            }
        });




        IVsettings = (ImageView) findViewById(R.id.imageView3);
        IVsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IVsettings.setAlpha(0.3F);
                new CountDownTimer(200,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        IVsettings.setAlpha(1.0F);
                    }
                }.start();

                Intent startIntent = new Intent(MainActivity.this, settings.class);
                startActivity(startIntent);
            }
        });

            Timer timer = new Timer();
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                   ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

                    //For 3G check
                    boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                    //For WiFi Check
                    boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();


                    if (!is3g && !isWifi)
                    {
                        //Toast.makeText(getApplicationContext(),"Network Connection is OFF", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                       // Toast.makeText(getApplicationContext(),"Network Connection is ON", Toast.LENGTH_LONG).show();

                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            firebaseRecyclerAdapter.notifyDataSetChanged();

                        }
                    });

                }
            }, 0, 20000);


              nIV = (ImageView) hView.findViewById(R.id.imageView);
              nT1 = (TextView) hView.findViewById(R.id.ttt);
              nT2 = (TextView) hView.findViewById(R.id.textView);

            nIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent startIntent = new Intent (MainActivity.this, ProfileSeenByFriends.class);
                    startActivity(startIntent);
                }
            });
            nT1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent startIntent = new Intent (MainActivity.this, ProfileSeenByFriends.class);
                    startActivity(startIntent);
                }
            });
            nT2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent startIntent = new Intent (MainActivity.this, ProfileSeenByFriends.class);
                    startActivity(startIntent);
                }
            });

    }
}
    ImageView  nIV;
    TextView  nT1,nT2;

    @Override
    public void onStart() {
        super.onStart();

       // mUsersList.setEnabled(true);

        TextView tv97 = (TextView)findViewById(R.id.textView97);
        tv97.setVisibility(View.INVISIBLE);
        tv97.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });

                mRegProgress1.dismiss();

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(startIntent);
            finish();
        }
        else {

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

            DatabaseReference userStatusssa = FirebaseDatabase.getInstance().getReference().child("State_with_you").child(uuser_id);
            userStatusssa.setValue("").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    mRegProgress1.dismiss();
                }
            });

            DatabaseReference userStatussssa = FirebaseDatabase.getInstance().getReference().child("State_in_app").child(uuser_id);
            userStatussssa.setValue("Online_in_app");
        }

        //Check if user is signed in and update accordingly

    }

    int yii = 0;
    FirebaseRecyclerAdapter  firebaseRecyclerAdapter;

    void adapter()
    {
        yii = 0;
          firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Friends, MainActivity.UsersViewHolder>(
                  Friends.class,
                R.layout.users_single_layout,
                MainActivity.UsersViewHolder.class,
                  mfriendsCurrentUserDatabase.orderByChild("count")
          ) {

              @Override
              protected void populateViewHolder(final MainActivity.UsersViewHolder viewHolder, final Friends model, final int position) {

                  final String user_id = getRef(position).getKey();

                  int ff = 0;
                  if(true) {
                      if (model.getName().equals("")) {
                          ff++;
                          viewHolder.mView.setVisibility(View.GONE);
                          viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                      } else if (!Esearch.getText().toString().equals("")) {
                          if (!(model.getName().toLowerCase().contains(Esearch.getText().toString().toLowerCase()))) {
                              ff++;
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

                  }

                  if (ff == 0) {
                      if (user_id.substring(0, 25).equals("GroupsBinTouchBestAppEver")) {
                          viewHolder.mView.setVisibility(View.GONE);
                          viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                      }

                      if (!user_id.substring(0, 25).equals("GroupsBinTouchBestAppEver")) {
                          try {
                              mdata.child("State_in_app").child(user_id).addValueEventListener(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(DataSnapshot dataSnapshot) {

                                      if (dataSnapshot.getValue(String.class).equals("Online_in_app")) {
                                         if(isNetworkAvailable()) viewHolder.setOnline();
                                          else viewHolder.setOffline();
                                      } else {
                                          viewHolder.setOffline();
                                      }
                                  }

                                  @Override
                                  public void onCancelled(DatabaseError databaseError) {

                                  }
                              });

                          } catch (Exception e) {
                              mdata.child("State_in_app").child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(DataSnapshot dataSnapshot) {

                                      if (dataSnapshot.getValue(String.class).equals("Online_in_app")) {
                                          if(isNetworkAvailable()) viewHolder.setOnline();
                                          else viewHolder.setOffline();
                                      } else {
                                          viewHolder.setOffline();
                                      }
                                  }

                                  @Override
                                  public void onCancelled(DatabaseError databaseError) {

                                  }
                              });

                          }
                      }

                      if (Integer.parseInt(model.getCount().substring(15)) > 0) {
if(yii==0) {
    //mp.stop();
    mp.start();
    yii = 1;
}
                      }

                      mdata.child("blocked_contacts").addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(DataSnapshot dataSnapshot) {
                              if (dataSnapshot.hasChild(uuser_id)) {
                                  mdata.child("blocked_contacts").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(DataSnapshot dataSnapshot) {
                                          Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                          int cc = 0;
                                          for (DataSnapshot child : children) {
                                              if (user_id.equals(child.child("key").getValue(String.class))) {
                                                  cc++;
                                              }
                                          }
                                          if (cc == 0)

                                          {
                                              viewHolder.setblock(0);

                                          } else {
                                              viewHolder.setblock(1);
                                          }
                                      }

                                      @Override
                                      public void onCancelled(DatabaseError databaseError) {

                                      }
                                  });


                              } else {
                                  viewHolder.setblock(0);
                              }
                          }

                          @Override
                          public void onCancelled(DatabaseError databaseError) {

                          }
                      });

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


                      viewHolder.setid(user_id);
                      viewHolder.setCount(cd,ccount);
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

                             // mUsersList.setEnabled(false);
                              /*
                              mRegProgress1=new ProgressDialog(MainActivity.this);
                              mRegProgress1.setTitle("Loading profile");
                              // mRegProgress.setMessage("Please wait while creating your account");
                              mRegProgress1.setCanceledOnTouchOutside(false);
                              mRegProgress1.show();
                              mRegProgress1.setMax(0);
                              mRegProgress1.setProgress(0);
                              */

                              TextView tv97 = (TextView)findViewById(R.id.textView97);
                              tv97.setVisibility(View.VISIBLE);

                              if (user_id.substring(0, 25).equals("GroupsBinTouchBestAppEver")) {
                                  Intent startIntent = new Intent(MainActivity.this, Create_Account.class);
                                  startIntent.putExtra("group_name", model.getName());
                                  startIntent.putExtra("passito", user_id);
                                  startActivity(startIntent);
                              } else {

                                  if (!user_id.equals(uuser_id)) {

                                      int f = 0;

                                      if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                          ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                                          f++;
                                      }
                                      if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                                          ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECORD_AUDIO}, 0);
                                          f++;
                                      }
                                      if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                          ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA},6);
                                          f++;
                                      }
                                      if (true) {
                                     // if (f == 0) {

                                          Intent startIntent = new Intent(MainActivity.this, activity_chats.class);
                                          startIntent.putExtra("user_id", user_id);
                                          startIntent.putExtra("user_image", model.getImage());
                                          startActivity(startIntent);
                                          /*
                                          mdata.child("blocked_contacts").addListenerForSingleValueEvent(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(DataSnapshot dataSnapshot) {
                                                  if (dataSnapshot.hasChild(user_id)) {
                                                      mdata.child("blocked_contacts").child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                          @Override
                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                              Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                                              int cc = 0;
                                                              for (DataSnapshot child : children) {
                                                                  if (uuser_id.equals(child.child("key").getValue(String.class))) {
                                                                      cc++;
                                                                  }
                                                              }
                                                              if (cc == 0)

                                                              {
                                                                  Intent startIntent = new Intent(MainActivity.this, activity_chats.class);
                                                                  startIntent.putExtra("user_id", user_id);
                                                                  startIntent.putExtra("user_image", model.getImage());
                                                                  startActivity(startIntent);

                                                              } else {
                                                                  Toast.makeText(context, "This contact is inaccessible!", Toast.LENGTH_SHORT).show();

                                                              }
                                                          }

                                                          @Override
                                                          public void onCancelled(DatabaseError databaseError) {

                                                          }
                                                      });


                                                  } else {
                                                      Intent startIntent = new Intent(MainActivity.this, activity_chats.class);
                                                      startIntent.putExtra("user_id", user_id);
                                                      startIntent.putExtra("user_image", model.getImage());
                                                      startActivity(startIntent);
                                                  }
                                             //     mRegProgress1.dismiss();
                                              }

                                              @Override
                                              public void onCancelled(DatabaseError databaseError) {

                                              }
                                          });
*/

                                      }


                                  } else {
                                      Toast.makeText(context, "The contact you are trying to reach put you as divert.", Toast.LENGTH_LONG).show();
                                    //  mRegProgress1.dismiss();
                                  }
                              }

                          }
                      });


                  }

              }
              }

              ;

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
            //by default offline
            ImageView IVoffline = mView.findViewById(R.id.imageView21);
            IVoffline.setImageResource(R.drawable.roundbuttongray);

            //check if connection is valid, then put online
            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ImageView IVonline = mView.findViewById(R.id.imageView21);
                    IVonline.setImageResource(R.drawable.roundbuttongreen);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



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
        public void setblock(int x)
        {
            ImageView IV = mView.findViewById(R.id.imageView22);
            if(x==0)
                IV.setVisibility(View.INVISIBLE);
            else
                IV.setVisibility(View.VISIBLE);

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



            Picasso.with(c).load(x).placeholder(R.drawable.pp).networkPolicy(NetworkPolicy.OFFLINE)
                    .into(userNameView, new Callback() {
                        @Override
                        public void onSuccess() {
                        }
                        @Override
                        public void onError() {
                            // Try again online if cache failed
                            Picasso.with(c).load(x).into(userNameView);
                        }
                    });

            //restore Picasso.with(c).load(x).placeholder(R.drawable.pp).into(userNameView);




        }
    }

    //Context context = this;
    private void sendToStart() {
        Intent startIntent = new Intent (MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

       // if(item.getItemId()==R.id.main_logout_btn)
     //   {
      //
      //
      //  }
        if(item.getItemId()==R.id.create_a_group)
        {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.msglayout_create_group);
            dialog.setTitle("Please select :");

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button BB = (Button) dialog.findViewById(R.id.button15);
            final EditText EE = (EditText) dialog.findViewById(R.id.editText8);

            dialog.show();


            BB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(EE.getText().toString().equals(""))
                    {
                        Toast.makeText(context, "Enter the group name", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    dialog.dismiss();
                    mRegProgress=new ProgressDialog(MainActivity.this);
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
                                String DateTime = s + hour;


                                                HashMap<String, String> userMap = new HashMap<>();
                                                userMap.put("count", DateTime+"0");
                                                userMap.put("image", "https://firebasestorage.googleapis.com/v0/b/bintouch-c21fe.appspot.com/o/user-group-icon.png?alt=media&token=772e44b3-9d93-4b99-a96d-09f6790de324");
                                                userMap.put("name", EE.getText().toString());
                                                userMap.put("status", "");




                                mfriendsDatabase.child(uuser_id).child(passito).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if(task.isSuccessful()) {

                                                            mRegProgress.dismiss();
                                                            Intent startIntent = new Intent (MainActivity.this, Create_Account.class);
                                                            startIntent.putExtra("group_name", EE.getText().toString());
                                                            startIntent.putExtra("passito", passito);
                                                            startActivity(startIntent);




                                                        }
                                                    }
                                                });



                            }
                        }
                    });


                }
            });

        }
        if(item.getItemId()==R.id.my_profiles)
        {
            Intent startIntent = new Intent (MainActivity.this, settings.class);
            startActivity(startIntent);


        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
/*
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        /////////////save////////////////
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("statepara1", ts);
        editor.commit();
        //////////////get//////////////////
        String ret = settings.getString("statepara1", "0");  /// 0 is default if variable not found
*/
        if (id == R.id.nav_signout) {


            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.msglayout_delete);
            dialog.setTitle("Please select :");

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button Bsignout = (Button) dialog.findViewById(R.id.button15);
            Button Bcancel = (Button) dialog.findViewById(R.id.button10);

            Button bbb = (Button) dialog.findViewById(R.id.button66);
            Button bbbb = (Button) dialog.findViewById(R.id.button67);
            Button bbbbb = (Button) dialog.findViewById(R.id.button68);
            Button bbbbbb = (Button) dialog.findViewById(R.id.button84);

            TextView ttt = (TextView) dialog.findViewById(R.id.textView71);

            bbb.setVisibility(View.INVISIBLE);
            bbbb.setVisibility(View.INVISIBLE);
            bbbbb.setVisibility(View.INVISIBLE);
            bbbbbb.setVisibility(View.INVISIBLE);

            ttt.setVisibility(View.INVISIBLE);

            Bsignout.setText("Sign out");
            Bcancel.setText("Cancel");

            dialog.show();

            Bsignout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    sendToStart();
                }
            });
            Bcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });



        }
        if (id == R.id.nav_add) {
            f2();
        }
        if (id == R.id.nav_acc1) {




            final Dialog dialog = new Dialog(MainActivity.this);
                                                        dialog.setContentView(R.layout.msglayout_addaccount);
                                                        dialog.setTitle("Please select :");

                                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                                        Button Bopen = (Button) dialog.findViewById(R.id.button15);
                                                        Button Bedit = (Button) dialog.findViewById(R.id.button60);


                                                        dialog.show();

                                                        Bopen.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                nav_Menu.findItem(R.id.nav_acc1).setIcon(R.drawable.ic_play_arrow_red_24dp);
                                                                nav_Menu.findItem(R.id.nav_acc2).setIcon(R.drawable.emptyi);
                                                                nav_Menu.findItem(R.id.nav_acc3).setIcon(R.drawable.emptyi);
                                                                nav_Menu.findItem(R.id.nav_acc4).setIcon(R.drawable.emptyi);
                                                                nav_Menu.findItem(R.id.nav_acc5).setIcon(R.drawable.emptyi);

                                                                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                                                                SharedPreferences.Editor editor = settings.edit();
                                                                editor.putString("select", "1");
                                                                editor.commit();

                                                                dialog.dismiss();
                                                                Intent intent = getIntent();
                                                                finish();
                                                                startActivity(intent);
                                                            }
                                                        });
            Bedit.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                Intent startIntent = new Intent(MainActivity.this, edit_account.class);
                                                                startIntent.putExtra("acc_id", mCurrent_user.getUid()+ ";;;;;profile_1");
                                                                startIntent.putExtra("name1", name1);
                                                                startIntent.putExtra("name2", name2);
                                                                startIntent.putExtra("name3", name3);
                                                                startIntent.putExtra("name4", name4);
                                                                startIntent.putExtra("name5", name5);
                                                                startActivity(startIntent);
                                                            }
                                                        });



        }
        if (id == R.id.nav_acc2) {


            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.msglayout_addaccount);
            dialog.setTitle("Please select :");

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button Bopen = (Button) dialog.findViewById(R.id.button15);
            Button Bedit = (Button) dialog.findViewById(R.id.button60);


            dialog.show();

            Bopen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nav_Menu.findItem(R.id.nav_acc1).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc2).setIcon(R.drawable.ic_play_arrow_red_24dp);
                    nav_Menu.findItem(R.id.nav_acc3).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc4).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc5).setIcon(R.drawable.emptyi);

                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("select", "2");
                    editor.commit();
                    dialog.dismiss();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            Bedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent startIntent = new Intent(MainActivity.this, edit_account.class);
                    startIntent.putExtra("acc_id", mCurrent_user.getUid()+ ";;;;;profile_2");
                    startIntent.putExtra("name1", name1);
                    startIntent.putExtra("name2", name2);
                    startIntent.putExtra("name3", name3);
                    startIntent.putExtra("name4", name4);
                    startIntent.putExtra("name5", name5);
                    startActivity(startIntent);
                }
            });

        }
        if (id == R.id.nav_acc3) {


            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.msglayout_addaccount);
            dialog.setTitle("Please select :");

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button Bopen = (Button) dialog.findViewById(R.id.button15);
            Button Bedit = (Button) dialog.findViewById(R.id.button60);


            dialog.show();

            Bopen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nav_Menu.findItem(R.id.nav_acc1).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc2).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc3).setIcon(R.drawable.ic_play_arrow_red_24dp);
                    nav_Menu.findItem(R.id.nav_acc4).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc5).setIcon(R.drawable.emptyi);

                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("select", "3");
                    editor.commit();
                    dialog.dismiss();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            Bedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent startIntent = new Intent(MainActivity.this, edit_account.class);
                    startIntent.putExtra("acc_id", mCurrent_user.getUid()+ ";;;;;profile_3");
                    startIntent.putExtra("name1", name1);
                    startIntent.putExtra("name2", name2);
                    startIntent.putExtra("name3", name3);
                    startIntent.putExtra("name4", name4);
                    startIntent.putExtra("name5", name5);
                    startActivity(startIntent);
                }
            });


        }
        if (id == R.id.nav_acc4) {


            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.msglayout_addaccount);
            dialog.setTitle("Please select :");

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button Bopen = (Button) dialog.findViewById(R.id.button15);
            Button Bedit = (Button) dialog.findViewById(R.id.button60);


            dialog.show();

            Bopen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nav_Menu.findItem(R.id.nav_acc1).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc2).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc3).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc4).setIcon(R.drawable.ic_play_arrow_red_24dp);
                    nav_Menu.findItem(R.id.nav_acc5).setIcon(R.drawable.emptyi);

                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("select", "4");
                    editor.commit();
                    dialog.dismiss();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            Bedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent startIntent = new Intent(MainActivity.this, edit_account.class);
                    startIntent.putExtra("acc_id", mCurrent_user.getUid()+ ";;;;;profile_4");
                    startIntent.putExtra("name1", name1);
                    startIntent.putExtra("name2", name2);
                    startIntent.putExtra("name3", name3);
                    startIntent.putExtra("name4", name4);
                    startIntent.putExtra("name5", name5);
                    startActivity(startIntent);
                }
            });


        }
        if (id == R.id.nav_acc5) {




            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.msglayout_addaccount);
            dialog.setTitle("Please select :");

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button Bopen = (Button) dialog.findViewById(R.id.button15);
            Button Bedit = (Button) dialog.findViewById(R.id.button60);


            dialog.show();

            Bopen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nav_Menu.findItem(R.id.nav_acc1).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc2).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc3).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc4).setIcon(R.drawable.emptyi);
                    nav_Menu.findItem(R.id.nav_acc5).setIcon(R.drawable.ic_play_arrow_red_24dp);

                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("select", "5");
                    editor.commit();
                    dialog.dismiss();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            Bedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent startIntent = new Intent(MainActivity.this, edit_account.class);
                    startIntent.putExtra("acc_id", mCurrent_user.getUid()+ ";;;;;profile_5");
                    startIntent.putExtra("name1", name1);
                    startIntent.putExtra("name2", name2);
                    startIntent.putExtra("name3", name3);
                    startIntent.putExtra("name4", name4);
                    startIntent.putExtra("name5", name5);
                    startActivity(startIntent);
                }
            });



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


   void f2()

        {


            count_profiles = 0;

            mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();


            mData.child(mCurrent_user.getUid() +";;;;;profile_1").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!(dataSnapshot.child("name").getValue(String.class).equals("")))
                    {
                        count_profiles++;
                    }
                    else
                    {
                        indexAcc =1;
                    }
                    mData.child(mCurrent_user.getUid()+";;;;;profile_2").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            if(!(dataSnapshot2.child("name").getValue(String.class).equals("")))
                            {
                                count_profiles++;
                            }
                            else
                            {
                                indexAcc =2;
                            }
                            mData.child(mCurrent_user.getUid()+";;;;;profile_3").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot3) {
                                    if(!(dataSnapshot3.child("name").getValue(String.class).equals("")))
                                    {
                                        count_profiles++;
                                    }
                                    else
                                    {
                                        indexAcc =3;
                                    }
                                    mData.child(mCurrent_user.getUid()+";;;;;profile_4").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot4) {
                                            if(!(dataSnapshot4.child("name").getValue(String.class).equals("")))
                                            {
                                                count_profiles++;
                                            }
                                            else
                                            {
                                                indexAcc =4;
                                            }
                                            mData.child(mCurrent_user.getUid()+";;;;;profile_5").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot5) {
                                                    if(!(dataSnapshot5.child("name").getValue(String.class).equals("")))
                                                    {
                                                        count_profiles++;
                                                    }
                                                    else
                                                    {
                                                        indexAcc =5;
                                                    }
                                                    if(count_profiles==5)
                                                    {
                                                        Toast.makeText(MainActivity.this,"A maximum of 5 accounts can be opened", Toast.LENGTH_LONG).show();
                                                    }
                                                    else
                                                    {


                                                        Intent startIntent = new Intent(MainActivity.this, open_another_acount.class);
                                                        startIntent.putExtra("acc_num", count_profiles+"");
                                                        startIntent.putExtra("acc_num_index", indexAcc+"");
                                                        startActivity(startIntent);


                                                    }

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



    }

    private ProgressDialog mRegProgress,mRegProgress1;
    String passito;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
  String  mVerificationId = "";
    String  mResendToken = "";

    private void login_user_phone(String email)
    {
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
                mResendToken = token.toString();

                // ...
            }


        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                email,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                MainActivity.this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();

                            mRegProgress.setTitle("Logging in");
                            mRegProgress.setMessage("Please wait");
                            mRegProgress.setCanceledOnTouchOutside(false);
                            mRegProgress.show();


                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            DatabaseReference  mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            //       HashMap<String, String> userMap = new HashMap<>();
                            String Token = FirebaseInstanceId.getInstance().getToken();
                            //      userMap.put("device_token", Token);



                            mData.child(uuser_id).child("device_token").setValue(Token).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        mRegProgress.dismiss();
                                        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();
                                        Intent i = getBaseContext().getPackageManager()
                                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                    }
                                }
                            });


                        }
                    }
                });
    }


    private void login_user(String email, String password) {
        FirebaseAuth.getInstance().signOut();
        DatabaseReference mUserDatabase;
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserDatabase.keepSynced(true);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            mRegProgress.dismiss();

                            String currentUserid = mAuth.getCurrentUser().getUid();
                            String Token = FirebaseInstanceId.getInstance().getToken();


                             mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();



                            mUserDatabase.child(uuser_id).child("device_token").setValue(Token).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();
                                    Intent i = getBaseContext().getPackageManager()
                                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);

                                }
                            });


                        }
                        else
                        {
                            mRegProgress.hide();

                        }
                    }


                });
    }


String name1 = "";
    String name2 = "";
    String name3 = "";
    String name4 = "";
    String name5 = "";

    void refreshAcc()
    {


        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        //  nIV;
        //  nT1,nT2;

        mData.child(mCurrent_user.getUid() +";;;;;profile_1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("name").getValue(String.class).equals("")))
                {
                   // BottomNavigationView navigation = (BottomNavigationView) nav_view.getChildAt(0);
                   // navigation.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) context);
                   // navigation.setSelectedItemId(R.id.nav_acc1);
                  //  BottomNavigationMenuView bottomNavigationMenuView =(BottomNavigationMenuView) navigation.getChildAt(0);
              //      View v = (View) nav_view.getMenu();//getChildAt(4); // number of menu from left
               //     new QBadgeView(context).bindTarget(v).setBadgeNumber(5);

                   // View v = (View) nav_Menu.findItem(R.id.nav_acc1).getActionView();
                  //  view.setText("5");
                  //  new QBadgeView(context).bindTarget(v).setBadgeNumber(5);

                    mdata.child("Friends").child(mCurrent_user.getUid() +";;;;;profile_1").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                            int count = 0;
                            for (DataSnapshot child : children) {
                                count += Integer.parseInt(child.child("count").getValue(String.class).substring(15));
                            }
                            if(count==0)
                            {
                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc1).getActionView();
                                view.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc1).getActionView();
                                view.setVisibility(View.VISIBLE);
                                view.setText(""+count);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    nav_Menu.findItem(R.id.nav_acc1).setTitle(dataSnapshot.child("name").getValue(String.class));
                    nav_Menu.findItem(R.id.nav_acc1).setVisible(true);
                    name1=dataSnapshot.child("name").getValue(String.class);
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = settings.edit();
                    String select = settings.getString("select", "");  /// 0 is default if variable not found
                    if (select.equals("1")) {
                        yyy=dataSnapshot.child("name").getValue(String.class);
                      //  toolbarset();
                        nT1.setText(dataSnapshot.child("name").getValue(String.class));
                        nT2.setText(dataSnapshot.child("email").getValue(String.class));

                        mdata.child("Profile_friends").child(mCurrent_user.getUid() +";;;;;profile_1").child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshots) {

                                Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).placeholder(R.drawable.pp).networkPolicy(NetworkPolicy.OFFLINE)
                                        .into(nIV, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                            }
                                            @Override
                                            public void onError() {
                                                // Try again online if cache failed
                                                Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).into(nIV);
                                            }
                                        });

                                //restore Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).placeholder(R.drawable.pp).into(nIV);



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor11 = settings11.edit();
                    String state = settings11.getString("hidestate1", "show");  /// 0 is default if variable not found
                    if(state.equals("hide"))  nav_Menu.findItem(R.id.nav_acc1).setVisible(false);

                }
                else
                {
                    nav_Menu.findItem(R.id.nav_acc1).setVisible(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mData.child(mCurrent_user.getUid()+";;;;;profile_2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {
                if(!(dataSnapshot2.child("name").getValue(String.class).equals("")))
                {

                    mdata.child("Friends").child(mCurrent_user.getUid() +";;;;;profile_2").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                            int count = 0;
                            for (DataSnapshot child : children) {
                                count += Integer.parseInt(child.child("count").getValue(String.class).substring(15));
                            }
                            if(count==0)
                            {
                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc2).getActionView();
                                view.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc2).getActionView();
                                view.setVisibility(View.VISIBLE);
                                view.setText(""+count);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    nav_Menu.findItem(R.id.nav_acc2).setTitle(dataSnapshot2.child("name").getValue(String.class));
                    name2=dataSnapshot2.child("name").getValue(String.class);
                    nav_Menu.findItem(R.id.nav_acc2).setVisible(true);
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = settings.edit();
                    String select = settings.getString("select", "");  /// 0 is default if variable not found
                    if (select.equals("2")) {
                        yyy=dataSnapshot2.child("name").getValue(String.class);
                      //  toolbarset();
                        nT1.setText(dataSnapshot2.child("name").getValue(String.class));
                        nT2.setText(dataSnapshot2.child("email").getValue(String.class));


                        mdata.child("Profile_friends").child(mCurrent_user.getUid() +";;;;;profile_2").child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshots) {

                                Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).placeholder(R.drawable.pp).networkPolicy(NetworkPolicy.OFFLINE)
                                        .into(nIV, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                            }
                                            @Override
                                            public void onError() {
                                                // Try again online if cache failed
                                                Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).into(nIV);
                                            }
                                        });

                                //restore Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).placeholder(R.drawable.pp).into(nIV);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor11 = settings11.edit();
                    String state = settings11.getString("hidestate2", "show");  /// 0 is default if variable not found
                    if(state.equals("hide"))  nav_Menu.findItem(R.id.nav_acc2).setVisible(false);
                }
                else
                {
                    nav_Menu.findItem(R.id.nav_acc2).setVisible(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mData.child(mCurrent_user.getUid()+";;;;;profile_3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot3) {
                if(!(dataSnapshot3.child("name").getValue(String.class).equals("")))
                {

                    mdata.child("Friends").child(mCurrent_user.getUid() +";;;;;profile_3").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                            int count = 0;
                            for (DataSnapshot child : children) {
                                count += Integer.parseInt(child.child("count").getValue(String.class).substring(15));
                            }
                            if(count==0)
                            {
                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc3).getActionView();
                                view.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc3).getActionView();
                                view.setVisibility(View.VISIBLE);
                                view.setText(""+count);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    nav_Menu.findItem(R.id.nav_acc3).setTitle(dataSnapshot3.child("name").getValue(String.class));
                    nav_Menu.findItem(R.id.nav_acc3).setVisible(true);
                    name3=dataSnapshot3.child("name").getValue(String.class);
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = settings.edit();
                    String select = settings.getString("select", "");  /// 0 is default if variable not found
                    if (select.equals("3")) {
                        yyy=dataSnapshot3.child("name").getValue(String.class);
                      //  toolbarset();
                        nT1.setText(dataSnapshot3.child("name").getValue(String.class));
                        nT2.setText(dataSnapshot3.child("email").getValue(String.class));


                        mdata.child("Profile_friends").child(mCurrent_user.getUid() +";;;;;profile_3").child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshots) {


                                Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).placeholder(R.drawable.pp).networkPolicy(NetworkPolicy.OFFLINE)
                                        .into(nIV, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                            }
                                            @Override
                                            public void onError() {
                                                // Try again online if cache failed
                                                Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).into(nIV);
                                            }
                                        });

                                //restore Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).placeholder(R.drawable.pp).into(nIV);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor11 = settings11.edit();
                    String state = settings11.getString("hidestate3", "show");  /// 0 is default if variable not found
                    if(state.equals("hide"))  nav_Menu.findItem(R.id.nav_acc3).setVisible(false);
                }
                else
                {
                    nav_Menu.findItem(R.id.nav_acc3).setVisible(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mData.child(mCurrent_user.getUid()+";;;;;profile_4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot4) {
                if(!(dataSnapshot4.child("name").getValue(String.class).equals("")))
                {
                    mdata.child("Friends").child(mCurrent_user.getUid() +";;;;;profile_4").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                            int count = 0;
                            for (DataSnapshot child : children) {
                                count += Integer.parseInt(child.child("count").getValue(String.class).substring(15));
                            }
                            if(count==0)
                            {
                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc4).getActionView();
                                view.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc4).getActionView();
                                view.setVisibility(View.VISIBLE);
                                view.setText(""+count);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    nav_Menu.findItem(R.id.nav_acc4).setTitle(dataSnapshot4.child("name").getValue(String.class));
                    name4=dataSnapshot4.child("name").getValue(String.class);
                    nav_Menu.findItem(R.id.nav_acc4).setVisible(true);
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = settings.edit();
                    String select = settings.getString("select", "");  /// 0 is default if variable not found
                    if (select.equals("4")) {
                        yyy=dataSnapshot4.child("name").getValue(String.class);
                      //  toolbarset();
                        nT1.setText(dataSnapshot4.child("name").getValue(String.class));
                        nT2.setText(dataSnapshot4.child("email").getValue(String.class));


                        mdata.child("Profile_friends").child(mCurrent_user.getUid() +";;;;;profile_4").child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshots) {

                                Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).placeholder(R.drawable.pp).networkPolicy(NetworkPolicy.OFFLINE)
                                        .into(nIV, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                            }
                                            @Override
                                            public void onError() {
                                                // Try again online if cache failed
                                                Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).into(nIV);
                                            }
                                        });

                                //restore Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).placeholder(R.drawable.pp).into(nIV);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor11 = settings11.edit();
                    String state = settings11.getString("hidestate4", "show");  /// 0 is default if variable not found
                    if(state.equals("hide"))  nav_Menu.findItem(R.id.nav_acc4).setVisible(false);
                }
                else
                {
                    nav_Menu.findItem(R.id.nav_acc4).setVisible(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mData.child(mCurrent_user.getUid()+";;;;;profile_5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot5) {
                if(!(dataSnapshot5.child("name").getValue(String.class).equals("")))
                {
                    mdata.child("Friends").child(mCurrent_user.getUid() +";;;;;profile_5").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                            int count = 0;
                            for (DataSnapshot child : children) {
                                count += Integer.parseInt(child.child("count").getValue(String.class).substring(15));
                            }
                            if(count==0)
                            {
                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc5).getActionView();
                                view.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                TextView view = (TextView) nav_view.getMenu().findItem(R.id.nav_acc5).getActionView();
                                view.setVisibility(View.VISIBLE);
                                view.setText(""+count);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    nav_Menu.findItem(R.id.nav_acc5).setTitle(dataSnapshot5.child("name").getValue(String.class));
                    name5=dataSnapshot5.child("name").getValue(String.class);
                    nav_Menu.findItem(R.id.nav_acc5).setVisible(true);
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = settings.edit();
                    String select = settings.getString("select", "");  /// 0 is default if variable not found
                    if (select.equals("5")) {
                        yyy=dataSnapshot5.child("name").getValue(String.class);
                       // toolbarset();
                        nT1.setText(dataSnapshot5.child("name").getValue(String.class));
                        nT2.setText(dataSnapshot5.child("email").getValue(String.class));


                        mdata.child("Profile_friends").child(mCurrent_user.getUid() +";;;;;profile_5").child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshots) {

                                Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).placeholder(R.drawable.pp).networkPolicy(NetworkPolicy.OFFLINE)
                                        .into(nIV, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                            }
                                            @Override
                                            public void onError() {
                                                // Try again online if cache failed
                                                Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).into(nIV);
                                            }
                                        });

                                //restore Picasso.with(MainActivity.this).load(dataSnapshots.getValue(String.class)).placeholder(R.drawable.pp).into(nIV);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor11 = settings11.edit();
                    String state = settings11.getString("hidestate5", "show");  /// 0 is default if variable not found
                    if(state.equals("hide"))  nav_Menu.findItem(R.id.nav_acc5).setVisible(false);
                }
                else
                {
                    nav_Menu.findItem(R.id.nav_acc5).setVisible(false);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void toolbarset()
    {
        LinearLayout LLLLL = new LinearLayout(context);
        Esearch = new EditText(context);
        Esearch.setHint("Search");
        Esearch.setBackgroundResource(R.drawable.roundbuttongray2);
        Esearch.setTextSize(14);
        ImageView Baudio = new ImageView(context);
        Baudio.setImageResource(R.drawable.ic_mic_white);

        int w1 = (int) ( 0.1* Resources.getSystem().getDisplayMetrics().widthPixels);
        int w3 = (int) ( 0.14* Resources.getSystem().getDisplayMetrics().widthPixels);
        LinearLayout.LayoutParams params1 = new LinearLayout
                .LayoutParams(w3, w1);
        Baudio.setLayoutParams(params1);

        int w2 = (int) ( 0.65* Resources.getSystem().getDisplayMetrics().widthPixels);
        LinearLayout.LayoutParams params2 = new LinearLayout
                .LayoutParams(w2, w1);
        Esearch.setLayoutParams(params2);

        TextView T555 = new TextView(this);
        T555.setText(Html.fromHtml("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>BnTouch "));
        T555.setTextColor(Color.WHITE);
       // T555.setTextSize(18);

        TextView T555s = new TextView(this);
        T555s.setText(Html.fromHtml("<u>"+yyy));
        T555s.setTextColor(Color.WHITE);

        Baudio.setImageResource(R.drawable.lofo);

        LLLLL.setBackgroundColor(Color.rgb(24,134,24));
        //   LLLLL.addView(Esearch);

        LLLLL.setGravity(Gravity.CENTER);
        T555.setGravity(Gravity.CENTER);
        //  Baudio.setGravity(Gravity.CENTER);
        T555s.setGravity(Gravity.CENTER);

        LLLLL.addView(T555);
        LLLLL.addView(Baudio);
        LLLLL.addView(T555s);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        LLLLL.setGravity(Gravity.CENTER);

        int w = (int) ( 1.0* Resources.getSystem().getDisplayMetrics().widthPixels);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(metrics.widthPixels, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        LLLLL.setLayoutParams(params);

        ActionBar.LayoutParams paramss = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER );

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(LLLLL, paramss);
        getSupportActionBar().show();
    }


    void toolbarsetinit()
    {
        LinearLayout LLLLL = new LinearLayout(context);
        Esearch = new EditText(context);
        Esearch.setHint("Search");
        Esearch.setBackgroundResource(R.drawable.buttonchattgray);
        Esearch.setTextSize(15);
        ImageView Baudio = new ImageView(context);
        Baudio.setImageResource(R.drawable.ic_mic_white);

        int w1 = (int) ( 0.092* Resources.getSystem().getDisplayMetrics().widthPixels);
        int w3 = (int) ( 0.14* Resources.getSystem().getDisplayMetrics().widthPixels);
        LinearLayout.LayoutParams params1 = new LinearLayout
                .LayoutParams(w3, w1);
        Baudio.setLayoutParams(params1);

        int w2 = (int) ( 0.77* Resources.getSystem().getDisplayMetrics().widthPixels);
        LinearLayout.LayoutParams params2 = new LinearLayout
                .LayoutParams(w2, w1);
        Esearch.setLayoutParams(params2);

        TextView T555 = new TextView(this);
        T555.setText(Html.fromHtml("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>BnTouch "));
        T555.setTextColor(Color.WHITE);
        //T555.setTextSize(18);

        TextView T555s = new TextView(this);
        T555s.setText(Html.fromHtml("Loading..."));
        T555s.setTextColor(Color.WHITE);

        Baudio.setImageResource(R.drawable.lofo);

        LLLLL.setBackgroundColor(Color.TRANSPARENT);
           LLLLL.addView(Esearch);

        LLLLL.setGravity(Gravity.CENTER);
        T555.setGravity(Gravity.CENTER);
        //  Baudio.setGravity(Gravity.CENTER);
        T555s.setGravity(Gravity.CENTER);

        //LLLLL.addView(T555);
       // LLLLL.addView(Baudio);
        //LLLLL.addView(T555s);


        int w = (int) ( 1.0* Resources.getSystem().getDisplayMetrics().widthPixels);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
        LLLLL.setLayoutParams(params);



        ActionBar.LayoutParams paramss = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER );

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(LLLLL, paramss);
        getSupportActionBar().show();
    }

    int count_profiles = 0;
    int indexAcc = 0;
}
