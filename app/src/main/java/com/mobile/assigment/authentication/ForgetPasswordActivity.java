package com.mobile.assigment.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mobile.assigment.R;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtSignUpFP;
    Button btnResetPassword;
    EditText edtEmailFP;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        addControls();
        addEvents();
    }
    private void addControls() {
        txtSignUpFP = findViewById(R.id.txtSignUpFP);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        edtEmailFP = findViewById(R.id.edtEmailFP);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void addEvents() {
        txtSignUpFP.setOnClickListener(this);
        btnResetPassword.setOnClickListener(this);
    }

    private boolean checkEmailString(String email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void resetPassword() {
        String email = edtEmailFP.getText().toString();
        boolean checkEmail = checkEmailString(email);
        if (checkEmail)
        {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(ForgetPasswordActivity.this,getString(R.string.toast_PasswordResetSuccessfully),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        } else
        {
            Toast.makeText(ForgetPasswordActivity.this,getString(R.string.error_EmailType),Toast.LENGTH_SHORT).show();
        }
    }

    private void signUp() {
        Intent SU_intent = new Intent(this,SignUpActivity.class);
        startActivity(SU_intent);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnResetPassword: resetPassword(); break;
            case R.id.txtSignUpFP : signUp(); break;
        }
    }

}
