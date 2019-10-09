package global.imas.bintouch;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
public class ProfileSeenBySpecifics extends AppCompatActivity {

    Button BAddProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_seen_by_specifics);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Profiles for specific users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BAddProfile = (Button) findViewById(R.id.button28);
        BAddProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent (ProfileSeenBySpecifics.this, ProfileSeenBySpecificsAdd.class);
                startActivity(startIntent);
            }
        });

    }
}
