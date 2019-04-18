package com.mobile.assigment.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mobile.assigment.MainActivity;
import com.mobile.assigment.R;

public class LogInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener {
    Button btnLogIn;
    EditText edtEmailLogIn;
    EditText edtPasswordLogIn;

    Button btnLoginGoogle;
    GoogleApiClient apiClient;
    FirebaseAuth firebaseAuth;
    GoogleSignInAccount account;

    TextView txtSignUp;
    TextView txtForgetPassword;

    int CHECK_LOGIN_METHOD = 0;
    final int REQUESTCODE_GOOGLE_LOGIN = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        addControls();
        addEvents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    private void addControls() {
        //google setup
        btnLoginGoogle = findViewById(R.id.btnLoginGoogle);
        createGoogleClient();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        //UI
        txtSignUp = findViewById(R.id.txtSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        edtEmailLogIn = findViewById(R.id.edtEmailLogIn);
        edtPasswordLogIn = findViewById(R.id.edtPasswordLogIn);
        txtForgetPassword = findViewById(R.id.txtForgetPassword);
    }


    private void addEvents() {
        btnLoginGoogle.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
        txtForgetPassword.setOnClickListener(this);
    }

    private void createGoogleClient() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();
    }

    private void signInWithEmailAndPassword() {
        String email = edtEmailLogIn.getText().toString();
        String password = edtPasswordLogIn.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful())
                {
                    Toast.makeText(LogInActivity.this,getString(R.string.error_Login),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signinGoogle(GoogleApiClient apiClient) {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        CHECK_LOGIN_METHOD = 1;
        startActivityForResult(intent,REQUESTCODE_GOOGLE_LOGIN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_GOOGLE_LOGIN)
        {
            if (resultCode == RESULT_OK)
            {
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                account = signInResult.getSignInAccount();
                String tokenID = account.getIdToken();
                authenticateFirebase(tokenID);
            }
        }

    }

    private void authenticateFirebase(String tokenID) {
        if (CHECK_LOGIN_METHOD == 1)
        {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID,null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user!=null)
        {
            Toast.makeText(this,"Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            Intent intentTC = new Intent(this, MainActivity.class);
            startActivity(intentTC);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnLoginGoogle: signinGoogle(apiClient);break;
//            case R.id.txtSignUp: signUpAccount();break;
            case R.id.btnLogIn: signInWithEmailAndPassword();break;
//            case R.id.txtForgetPassword: forgetPassword();
        }
    }


}
