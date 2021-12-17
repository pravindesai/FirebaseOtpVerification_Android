package com.pravin.barcodeapp.firebaseotpverification_android.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.pravin.barcodeapp.firebaseotpverification_android.Constants;
import com.pravin.barcodeapp.firebaseotpverification_android.R;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "**"+ this.getClass().getName();
    Button sendOtpButton;
    EditText editTextPhone;
    String phoneNumer;

    PhoneAuthOptions options;
    FirebaseAuth firebaseAuth;

    private String mVerificationId = null;
    private PhoneAuthProvider.ForceResendingToken mResendToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        initUI();
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }

    }

    private void initUI() {
        sendOtpButton = findViewById(R.id.sendOtpButton);
        editTextPhone = findViewById(R.id.editTextPhone);
        sendOtpButton.setOnClickListener(v -> sendOtop());
    }

    private void sendOtop() {
        phoneNumer = editTextPhone.getText().toString();
        if (phoneNumer.length()!=10){
            editTextPhone.setError("Phone Number not valid");
            editTextPhone.requestFocus();
            return;
        }
        options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(Constants.KEY_PHONE_PREFIX+phoneNumer)                 // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS)   // Timeout and unit
                .setActivity(this)                          // Activity (for callback binding)
                .setCallbacks(mCallbacks)                   // OnVerificationStateChangedCallbacks
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);


    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks
            = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            Log.e(TAG, "onVerificationCompleted: Automatic" + credential);
//            OtpActivity.signInWithPhoneAuthCredential(LoginActivity.this, credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(LoginActivity.this, "Invalid request", Toast.LENGTH_LONG).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                Toast.makeText(LoginActivity.this, "Testing message quota is over", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(LoginActivity.this, "Verification Failed", Toast.LENGTH_LONG).show();
            // Show a message and update the UI
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            Log.e(TAG, "onCodeSent:" + verificationId);
            Toast.makeText(LoginActivity.this, "On code sent", Toast.LENGTH_SHORT).show();
            mVerificationId = verificationId;
            mResendToken = token;

            Intent otpActIntent = new Intent(LoginActivity.this, OtpActivity.class);
            otpActIntent.putExtra(Constants.KEY_PHONENUM, phoneNumer);
            otpActIntent.putExtra(Constants.FIREBASE_VERIFICATION_ID, mVerificationId);
            startActivity(otpActIntent);
        }
    };


}

