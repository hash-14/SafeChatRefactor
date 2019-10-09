package global.imas.bintouch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.preference.PreferenceManager;
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
import java.util.Date;
import java.util.HashMap;

/**
 * Created by bachirhabib on 2/19/2019.
 */

public class Receiver extends BroadcastReceiver {

    private DatabaseReference mdata, mfriendsDatabase;
    String uuser_id = "x123error";
    private FirebaseUser mCurrent_user;

    @Override
    public void onReceive(Context context, Intent intent) {

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

        mfriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mfriendsDatabase.keepSynced(true);

        mdata = FirebaseDatabase.getInstance().getReference();

        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Delayed_msg_From_Me"))
                {
                    mdata.child("Delayed_msg_From_Me").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                        //    Toast.makeText(context, uuser_id,Toast.LENGTH_LONG).show();

                            if(dataSnapshot.hasChild(uuser_id))
                            {
                                mdata.child("Delayed_msg_From_Me").child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                            int m = calendar.get(Calendar.MONTH) + 1;
                                            int h = calendar.get(Calendar.HOUR_OF_DAY);
                                            int mi = calendar.get(Calendar.MINUTE);


                                            if (
                                                    ((yf == y) && (mf == m) && (df == d) && (hf == h) && (mif <= mi))
                                                    ) {

                                                final String dateInString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                Calendar c = Calendar.getInstance();
                                                try {
                                                    c.setTime(sdf.parse(dateInString));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                c.add(Calendar.DATE, 0);
                                                Date resultdate;
                                                resultdate = new Date(c.getTimeInMillis());
                                                String s = sdf.format(resultdate);

                                                Calendar rightNow = Calendar.getInstance();
                                                int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                int currentmin = rightNow.get(Calendar.MINUTE); //

                                                String ffp = String.valueOf(currentmin).toString();
                                                if (currentmin < 10)
                                                    ffp = "0" + String.valueOf(currentmin).toString();

                                                String hour = String.valueOf(currentHour).toString() + ":" + ffp;

                                                String DateTime = s + hour;

                                                HashMap<String, String> object = new HashMap<String, String>();
                                                object.put("from", child.child("from").getValue(String.class));
                                                object.put("msg_or_url", child.child("auto_reply_msg").getValue(String.class));
                                                object.put("msg_type", "text");
                                                object.put("dateTime", DateTime);
                                                object.put("sent_received_seen", "sent"); //  sent , received, seen
                                                object.put("from_orignal_id", child.child("from").getValue(String.class));

                                                String to = child.child("list_keys").getValue(String.class);

                                         while (to. length()>12)
                                         {
                                                String keykito = to.substring(0, to.indexOf(";;;;;;;;;;"));
                                                to = to.substring(to.indexOf(";;;;;;;;;;") + 10);

                                            // Toast.makeText(context,keykito , Toast.LENGTH_LONG).show();

                                             String outputs1 = mdata.child("Chats").child(uuser_id).child(keykito).push().getKey();
                                                String outputs2 = mdata.child("Chats").child(keykito).child(uuser_id).push().getKey();

                                                if (child.child("from").getValue(String.class).equals(uuser_id))
                                                    object.put("from_orignal_key", outputs1); //  sent , received, seen
                                                else
                                                    object.put("from_orignal_key", outputs2); //  sent , received, seen


                                                mdata.child("Chats").child(uuser_id).child(keykito).child(outputs1).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                });
                                                mdata.child("Chats").child(keykito).child(uuser_id).child(outputs2).setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                });

                                                HashMap<String, String> object2 = new HashMap<String, String>();
                                                object2.put("msg_or_url", child.child("auto_reply_msg").getValue(String.class));

                                                if (child.child("from").getValue(String.class).equals(uuser_id))
                                                    mdata.child("Notifications").child(uuser_id).child(keykito).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    });
                                                else
                                                    mdata.child("Notifications").child(keykito).child(uuser_id).setValue(object2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    });

                                                String keyy = keykito;
                                                String msgss = child.child("auto_reply_msg").getValue(String.class);

                                                mfriendsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                        if (dataSnapshot.hasChild(uuser_id)) {
                                                            mfriendsDatabase.child(uuser_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {


                                                                    if (dataSnapshot.hasChild(keyy)) {
                                                                        mfriendsDatabase.child(uuser_id).child(keyy).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                                final String dateInString  = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());  // Start date, today s date
                                                                                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                                                                                java.util.Calendar c = java.util.Calendar.getInstance();
                                                                                try {c.setTime(sdf.parse(dateInString));} catch (ParseException e) {e.printStackTrace();}
                                                                                c.add(java.util.Calendar.DATE, 0);
                                                                                Date resultdate;
                                                                                resultdate = new Date(c.getTimeInMillis());
                                                                                String s =sdf.format(resultdate);
                                                                                java.util.Calendar rightNow = java.util.Calendar.getInstance();
                                                                                int currentHour = rightNow.get(java.util.Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                                                                                int currentmin = rightNow.get(java.util.Calendar.MINUTE); //
                                                                                String ffp = String.valueOf(currentmin).toString();
                                                                                if(currentmin<10) ffp = "0"+String.valueOf(currentmin).toString();
                                                                                String hour = String.valueOf(currentHour).toString() + ":" + ffp;
                                                                                String DateTime = s + hour;

                                                                                int count = Integer.parseInt(dataSnapshot.child("count").getValue(String.class).substring(15));
                                                                                count++;

                                                                                mfriendsDatabase.child(keyy).child(uuser_id).child("count").setValue(DateTime+count).addOnSuccessListener(new OnSuccessListener<Void>() {
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


                                                mdata.child("Delayed_msg_From_Me").child(uuser_id).child(child.getKey()).removeValue();


                                            }
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

        Toast.makeText(context, intent.getStringExtra("param"),Toast.LENGTH_LONG).show();
    }

}
