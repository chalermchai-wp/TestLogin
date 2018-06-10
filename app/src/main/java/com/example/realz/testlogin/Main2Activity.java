package com.example.realz.testlogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private String userid;
    private String fbname;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        TextView fb_name = (TextView)findViewById(R.id.fb_name);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userid = bundle.getString("userid");
        }

/* make the API call */

    }

}
