package global.imas.bintouch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    private Button mRegBtn, mLoginBtn;
    int init = 0;
    int flag1 = 0;
    private ImageView IVlogo;
    CountDownTimer countDownTimer1;
    ImageButton Bhome;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mRegBtn = (Button)findViewById(R.id.start_reg_btn);
        mLoginBtn = (Button)findViewById(R.id.start_login_btn);
        IVlogo = (ImageView)findViewById(R.id.imageView);

        Bhome = (ImageButton)findViewById(R.id.imageButton);
        Bhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = settings.edit();

                String Acc1Username = settings.getString("Acc1Username", "");  /// 0 is default if variable not found
                String Acc2Username = settings.getString("Acc2Username", "");  /// 0 is default if variable not found
                String Acc3Username = settings.getString("Acc3Username", "");  /// 0 is default if variable not found
                String Acc4Username = settings.getString("Acc4Username", "");  /// 0 is default if variable not found
                String Acc5Username = settings.getString("Acc5Username", "");  /// 0 is default if variable not found

                int cou = 0;

                if(!Acc1Username.equals("")) {
                    cou++;
                }
                if(!Acc2Username.equals(""))
                {
                    cou++;
                }
                if(!Acc3Username.equals(""))
                {
                    cou++;
                }
                if(!Acc4Username.equals(""))
                {
                    cou++;
                }
                if(!Acc5Username.equals(""))
                {
                    cou++;
                }

                if(cou==0)
                {
                    Toast.makeText(context,"You have to sign in at least to one account", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent startIntent = new Intent(context, MainActivity.class);
                    startActivity(startIntent);
                    finish();
                }
            }
        });

        mRegBtn.setVisibility(View.INVISIBLE);
        IVlogo.setVisibility(View.INVISIBLE);

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent reg_intent = new Intent (StartActivity.this, RegisterActivity.class);
                startActivity(reg_intent);

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent reg_intent = new Intent (StartActivity.this, LoginActivity.class);
                startActivity(reg_intent);

            }
        });

        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        //  E1.startAnimation(fadeIn);
        mLoginBtn.startAnimation(fadeIn);
        fadeIn.setDuration(1700);
        fadeIn.setFillAfter(true);


        final Animation myAnim1 = AnimationUtils.loadAnimation(StartActivity.this, R.anim.bounce);
        MyBounceInterpolator interpolator1 = new MyBounceInterpolator(0.4, 1);  // with amplitude 0.2 and frequency 20
        myAnim1.setInterpolator(interpolator1);

        IVlogo.startAnimation(myAnim1);
        IVlogo.setVisibility(View.VISIBLE);

        init =0;
        flag1 = 0;

        countDownTimer1 = new CountDownTimer(400, 100) {
            public void onTick(long millisUntilFinished) {


            }
            @Override
            public void onFinish() {
                AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                //  E1.startAnimation(fadeIn);
                mRegBtn.startAnimation(fadeIn);
                fadeIn.setDuration(1700);
                fadeIn.setFillAfter(true);
            }
        }.start();

    }
}
