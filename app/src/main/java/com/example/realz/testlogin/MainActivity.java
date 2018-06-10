package com.example.realz.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    TextView txtLoginStatus;
    CallbackManager callbackManager;
    String ss,ss1;
    private TextView fbname;
    private ImageView fbpic;
    private ProfilePictureView profilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        loginButton = (LoginButton) findViewById(R.id.fb_login_bn);
        txtLoginStatus = (TextView)findViewById(R.id.txtLoginStatus);
        fbname = (TextView)findViewById(R.id.fb_name);
        fbpic = (ImageView) findViewById(R.id.fb_pic);
        callbackManager = CallbackManager.Factory.create();
        profilePictureView = (ProfilePictureView)findViewById(R.id.image);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                txtLoginStatus.setText("Login Success \n" +
//                        loginResult.getAccessToken().getUserId()+
//                        "\n" + loginResult.getAccessToken().getToken());

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("Main", response.toString());
                                setProfileToView(object);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                txtLoginStatus.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        txtLoginStatus.setText(ss1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    protected void setProfileToView(JSONObject jsonObject) {
        try {
            //gender.setText(jsonObject.getString("gender"));
            fbname.setText(jsonObject.getString("name"));
            profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
            profilePictureView.setProfileId(jsonObject.getString("id"));
            //infoLayout.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
