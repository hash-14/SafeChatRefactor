package global.imas.bintouch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Locale;

/**
 * Created by bachirhabib on 9/14/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService implements TextToSpeech.OnInitListener {

    TextToSpeech tts ;
    private FirebaseUser mCurrent_user;
    String uuser_id = "error_notifcation_enable_or_not";
    Context c = this;
    @Override
    public void handleIntent(Intent intent)
    {

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();



        SharedPreferences settings11 = PreferenceManager.getDefaultSharedPreferences(c);
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


        SharedPreferences settingss = PreferenceManager.getDefaultSharedPreferences(this);
        String rets = settingss.getString("notitest", "enable");  /// 0 is default if variable not found

        if(rets.equals("enable")) {

            tts = new TextToSpeech(this, this);

            try {
                if (intent.getExtras() != null) {
                    RemoteMessage.Builder builder = new RemoteMessage.Builder("MyFirebaseMessagingService");

                    for (String key : intent.getExtras().keySet()) {
                        builder.addData(key, intent.getExtras().get(key).toString());
                    }


                    x = builder.build().getNotification().getTitle().toString() + ", " + builder.build().getNotification().getBody().toString();


                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
                    String ret = settings.getString("drive", "off");  /// 0 is default if variable not found

                    if (ret.equals("on")) {
                        tts.setLanguage(Locale.US);
                        tts.setSpeechRate(0.8f);
                        tts.speak(x, TextToSpeech.QUEUE_ADD, null);
                    }


                    if (builder.build().getNotification().getBody().toString().length() > 9) {
                        if (builder.build().getNotification().getBody().substring(0, 10).equals("Video call")
                                || builder.build().getNotification().getBody().substring(0, 10).equals("Missed Vid")
                                || builder.build().getNotification().getBody().substring(0, 10).equals("Rejected V"))
                            onMessageReceived(builder.build());
                        else super.handleIntent(intent);
                    } else super.handleIntent(intent);

                } else super.handleIntent(intent);

            } catch (Exception e) {
                // super.handleIntent(intent);
            }

        }
    }
    String x ="";

    private TextToSpeech mTextToSpeech;

    public void sayText(Context context, final String message) {

        mTextToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                try {
                    if (mTextToSpeech != null && status == TextToSpeech.SUCCESS) {
                        mTextToSpeech.setLanguage(Locale.US);
                        mTextToSpeech.speak(message, TextToSpeech.QUEUE_ADD, null);
                    }
                } catch (Exception ex) {
                  //  System.out.print("Error handling TextToSpeech GCM notification " + ex.getMessage());
                }
            }
        });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
       // Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
          //  Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
      //      Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());


       // if(remoteMessage.getNotification().getBody().toString().length()>9) {
            if (remoteMessage.getNotification().getBody().substring(0, 10).equals("Video call")) {

                //  Intent startIntent = new Intent(this, videocallwebview.class);
                //  startIntent.putExtra("messa", remoteMessage.getNotification().getBody());
                // startActivity(startIntent);

                String ds = remoteMessage.getNotification().getBody().substring(remoteMessage.getNotification().getBody().length()-5);

                if(ds.equals("video")) {
                    SharedPreferences settingsssa = PreferenceManager.getDefaultSharedPreferences(MyFirebaseMessagingService.this);
                    SharedPreferences.Editor editorssa = settingsssa.edit();
                    editorssa.putString("callvideo", "video");
                    editorssa.commit();
                }
                else
                {
                    SharedPreferences settingsssa = PreferenceManager.getDefaultSharedPreferences(MyFirebaseMessagingService.this);
                    SharedPreferences.Editor editorssa = settingsssa.edit();
                    editorssa.putString("callvideo", "call");
                    editorssa.commit();
                }

                Intent startIntent = new Intent(this, ReceivedCall.class);
                startIntent.putExtra("messa", remoteMessage.getNotification().getBody());
                startActivity(startIntent);
            } else if (remoteMessage.getNotification().getBody().substring(0, 10).equals("Missed Vid")) {
                Intent startIntent = new Intent(this, MainActivity.class);
                startActivity(startIntent);
            } else if (remoteMessage.getNotification().getBody().substring(0, 10).equals("Rejected V")) {
                // Toast.makeText(this,"Call has been rejected", Toast.LENGTH_SHORT).show();
                Intent startIntent = new Intent(this, MainActivity.class);
                startActivity(startIntent);
            }
     //   }
    }



       // remoteMessage.getData()

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onInit(int i) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String ret = settings.getString("drive", "off");  /// 0 is default if variable not found

        if(ret.equals("on")) {
            tts.setLanguage(Locale.US);
            tts.setSpeechRate(0.8f);
            tts.speak(x, TextToSpeech.QUEUE_ADD, null);
        }

    }


    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!

        super.onDestroy();
    }

}
