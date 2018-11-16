package com.semenindonesia.sisi.mtbf_mttr.activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lamudi.phonefield.PhoneEditText;
import com.lamudi.phonefield.PhoneInputLayout;
import com.semenindonesia.sisi.mtbf_mttr.R;
import com.semenindonesia.sisi.mtbf_mttr.config.Config;
import com.semenindonesia.sisi.mtbf_mttr.config.SessionManager;
import com.semenindonesia.sisi.mtbf_mttr.constructor.consUser;
import com.semenindonesia.sisi.mtbf_mttr.database.User;
import com.semenindonesia.sisi.mtbf_mttr.retrofit.ApiClient;
import com.semenindonesia.sisi.mtbf_mttr.retrofit.ApiService;
import com.semenindonesia.sisi.mtbf_mttr.retrofit.resultLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static String TAG = LoginActivity.class.getSimpleName();

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Button btnRequestSms, btnVerifyOtp;
    private EditText inputName, inputEmail, inputMobile, inputOtp;
    private ProgressBar progressBar;
    private SessionManager pref;
    private ImageButton btnEditMobile;
    private TextView txtEditMobile;
    private LinearLayout layoutEditMobile;
    private SessionManager session;
    private List<User> userlist;
    private PhoneEditText phoneEditText;
    private PhoneInputLayout phoneInputLayout;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseMessaging.getInstance().subscribeToTopic("global");
        Log.d("Firebase", "token ="+ FirebaseInstanceId.getInstance().getToken());
        displayFirebaseRegId();

        //inisiasi
        viewPager = (ViewPager) findViewById(R.id.login_viewpager);
        inputOtp = (EditText) findViewById(R.id.inputOtp);
        btnVerifyOtp = (Button) findViewById(R.id.btn_verify_otp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnEditMobile = (ImageButton) findViewById(R.id.btn_edit_mobile);
        btnRequestSms = (Button) findViewById(R.id.sign_in);
        txtEditMobile = (TextView) findViewById(R.id.txt_edit_mobile);
        layoutEditMobile = (LinearLayout) findViewById(R.id.layout_edit_mobile);

        // hiding the edit mobile number
        layoutEditMobile.setVisibility(View.GONE);

        phoneInputLayout = (PhoneInputLayout) findViewById(R.id.phone_input_layout);
        phoneInputLayout.setHint(R.string.phone_hint);
        phoneInputLayout.setDefaultCountry("ID");

        //viewpager
        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //session manager
        session = new SessionManager(getApplicationContext());

        // view click listeners
        btnRequestSms.setOnClickListener(this);
        btnEditMobile.setOnClickListener(this);
        btnVerifyOtp.setOnClickListener(this);

        pref = new SessionManager(this);
        if (pref.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

    }

    //onclick listener
    //==============================================================================================
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                boolean valid = true;

                validateForm();
                // checks if the field is valid
                if (phoneInputLayout.isValid()) {
                    phoneInputLayout.setError(null);
                } else {
                    // set error message
                    phoneInputLayout.setError(getString(R.string.invalid_phone_number));
                    valid = false;
                }
                break;

            case R.id.btn_verify_otp:
                verifyOtp();
                break;

            case R.id.btn_edit_mobile:
                viewPager.setCurrentItem(0);
                layoutEditMobile.setVisibility(View.GONE);
                pref.setIsWaitingForSms(false);
                break;
        }
    }

    //==============================================================================================
    private void validateForm() {
        String mobile = phoneInputLayout.getPhoneNumber().trim();
        String token =  String.valueOf(FirebaseInstanceId.getInstance().getToken());

        // validating mobile number
        // it should be of 10 digits length
        //if (isValidPhoneNumber(mobile)) {

        // request for sms
        progressBar.setVisibility(View.VISIBLE);

        // saving the mobile number in shared preferences
        pref.setMobileNumber(mobile);

        // requesting for sms
        //requestForSMS(mobile,token);
        login(mobile,token);


        /*} else {
            Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
        }*/
    }

    //viewpager adapter
    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.layout_sms;
                    break;
                case 1:
                    resId = R.id.layout_otp;
                    break;
            }
            return findViewById(resId);
        }
    }

    //=====================================RETROFIT=================================================
    private void login(String mobile, String token){

        consUser user = new consUser();
        ApiService apiservice = ApiClient.getClient().create(ApiService.class);

        Call<resultLogin> call = apiservice.login(mobile,token);
        call.enqueue(new Callback<resultLogin>() {
            @Override
            public void onResponse(Call<resultLogin> call, retrofit2.Response<resultLogin> response) {
                consUser userlogin = response.body().getUser();
                if (response.body().isError()){
                    Toast.makeText(LoginActivity.this,response.body().getError_msg(),Toast.LENGTH_SHORT).show();
                }else {
                    // boolean flag saying device is waiting for sms
                    pref.setIsWaitingForSms(true);

                    // moving the screen to next pager item i.e otp screen
                    viewPager.setCurrentItem(1);
                    txtEditMobile.setText(pref.getMobileNumber());
                    layoutEditMobile.setVisibility(View.VISIBLE);

                    //Toast.makeText(LoginActivity.this,"nama: "+userlogin.getNama(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<resultLogin> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LoginActivity.this,"network connection issues.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyOtp(){
        String otp = inputOtp.getText().toString().trim();
        String mobile = phoneInputLayout.getPhoneNumber().trim();
        String token =  String.valueOf(FirebaseInstanceId.getInstance().getToken());

        if (!otp.isEmpty() && !mobile.isEmpty()) {
            verifyCode(mobile,token,otp);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyCode(String mobile, String token, String otp){
        consUser user = new consUser();
        ApiService apiservice = ApiClient.getClient().create(ApiService.class);

        Call<resultLogin> call = apiservice.verify(mobile,otp,token);
        call.enqueue(new Callback<resultLogin>() {
            @Override
            public void onResponse(Call<resultLogin> call, retrofit2.Response<resultLogin> response) {
                consUser userlogin = response.body().getUser();
                if (response.body().isError()){
                    Toast.makeText(LoginActivity.this,response.body().getError_msg(),Toast.LENGTH_SHORT).show();
                }else {


                    Toast.makeText(LoginActivity.this,"id: "+userlogin.getId(),Toast.LENGTH_SHORT).show();

                    //=============================================================================
                    Realm.init(LoginActivity.this);
                    realm=Realm.getDefaultInstance();
                    realm.beginTransaction();

                        User user = realm.createObject(User.class,userlogin.getId());
                    user.setName(userlogin.getNama());
                    user.setPhone(userlogin.getPhone());
                    user.setToken(userlogin.getToken());

                    realm.commitTransaction();
                    //=============================================================================

                    // boolean flag saying device user logged in
                    SessionManager pref = new SessionManager(getApplicationContext());
                    pref.setLogin(true);

                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();


                }
            }

            @Override
            public void onFailure(Call<resultLogin> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LoginActivity.this,"network connection issues.",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
