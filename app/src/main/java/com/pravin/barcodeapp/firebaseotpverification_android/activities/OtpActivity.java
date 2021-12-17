package com.pravin.barcodeapp.firebaseotpverification_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.pravin.barcodeapp.firebaseotpverification_android.Constants;
import com.pravin.barcodeapp.firebaseotpverification_android.R;

import java.util.Objects;

public class OtpActivity extends AppCompatActivity {
    private String TAG = "**OTPActivity";
    Button verifyButton;
    EditText editTextOtp;
    TextView msgTv;

    private String phoneNumer;

    FirebaseAuth mAuth;

    private String mVerificationId = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Objects.requireNonNull(getSupportActionBar()).hide();


        initUI();

        Intent intent = getIntent();
        phoneNumer = intent.getStringExtra(Constants.KEY_PHONENUM);
        mVerificationId = intent.getStringExtra(Constants.FIREBASE_VERIFICATION_ID);

        msgTv.setText(Constants.KEY_PHONE_PREFIX+"-"+phoneNumer);

        mAuth = FirebaseAuth.getInstance();

    }

    private void initUI() {
        verifyButton  = findViewById(R.id.verifyButton);
        editTextOtp = findViewById(R.id.editTextOtp);
        msgTv = findViewById(R.id.msgTv);
        verifyButton.setOnClickListener(v -> verifyOTP());
    }

    private void verifyOTP() {
        String otp = editTextOtp.getText().toString();
        if (otp.length()<=0 || otp.trim().isEmpty()){
            editTextOtp.setError("OTP required");
            editTextOtp.requestFocus();
            return;
        }
        editTextOtp.clearFocus();

        if (mVerificationId != null){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
            signInWithPhoneAuthCredential(this, credential);
        }
    }


    public void signInWithPhoneAuthCredential(Context context, PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Log.e(TAG, "onComplete: userid --> "+user.getUid() );
                            stratMainActivity();

                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OtpActivity.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                                editTextOtp.requestFocus();
                                editTextOtp.setError("Wrong OTP");
                                return;
                            }
                        }
                });


    }

    private void stratMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(mainActivity);
        finishAffinity();
    }

}