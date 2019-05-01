package com.mobile.assigment.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mobile.assigment.MainActivity;
import com.mobile.assigment.R;
import com.mobile.assigment.UserInfo;
import com.mobile.assigment.model.User;

public class SignUpActivity extends AppCompatActivity {
    //UI
    Button btnSignUp;
    EditText edtEmailSignUp;
    EditText edtPasswordSignUp;
    EditText edtPasswordSignUpConfirm;
    ProgressDialog dialog;
    //Firebase
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        addControls();
        addEvents();
    }
    private void addControls() {
        btnSignUp = findViewById(R.id.btnSignUp);
        edtEmailSignUp = findViewById(R.id.edtEmailSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        edtPasswordSignUpConfirm = findViewById(R.id.edtPasswordSignUpConfirm);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.processing));

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void addEvents() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }
    private void createNewAccount() {
        final String email = edtEmailSignUp.getText().toString();
        final String password = edtPasswordSignUp.getText().toString();
        String password_confirm = edtPasswordSignUpConfirm.getText().toString();
        if (email.trim().isEmpty())
            Toast.makeText(this
                    ,getString(R.string.error_Toast)+" "+ getString(R.string.email)
                    ,Toast.LENGTH_SHORT).show();
        else if (password.trim().isEmpty())
            Toast.makeText(this
                    ,getString(R.string.error_Toast)+" "+getString(R.string.password)
                    ,Toast.LENGTH_SHORT).show();
        else if (!password.equals(password_confirm))
            Toast.makeText(this
                    ,getString(R.string.error_ConfirmPassword)
                    ,Toast.LENGTH_SHORT).show();
        else
        {
            dialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(SignUpActivity.this
                                ,getString(R.string.signUp_Successfully)
                                ,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        logInWithEmailAndPassword(email,password);

                    }

                }
            });

        }
    }
    private void logInWithEmailAndPassword(final String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful())
                {
                    Toast.makeText(SignUpActivity.this,getString(R.string.error_Login),Toast.LENGTH_SHORT).show();
                }
                else if (task.isSuccessful())
                {
                    Toast.makeText(SignUpActivity.this,"Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    //add user to database
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    UserInfo.getInstance().setUserEmail(email);
                    UserInfo.getInstance().setUserName(email.split("@")[0]);
                    UserInfo.getInstance().setId(userId);
                    User user = new User();
                    user.setName(email.split("@")[0]);
                    user.setEmail(email);
                    user.setUserId(userId);
                    User.createUser(user, userId);
                    //switch to main activity
                    Intent intentTC = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intentTC);
                    finish();
                }
            }
        });
    }
}
