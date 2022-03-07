package com.slavik.mishorarios.ui.sign_up;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.slavik.mishorarios.R;
import com.slavik.mishorarios.ui.login.LoginActivity;
import com.slavik.mishorarios.ui.main.MainActivity;
import com.slavik.mishorarios.util.TextWatcherLite;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SignUpVM vm = new ViewModelProvider(this).get(SignUpVM.class);

        EditText txtName, txtLastName, txtEmail, txtPasswd, txtPasswdRepeat;
        txtName = findViewById(R.id.txtName);
        txtLastName = findViewById(R.id.txtLastName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPasswd = findViewById(R.id.txtPasswd);
        txtPasswdRepeat = findViewById(R.id.txtPasswdRepeat);

        Button btnSignUp = findViewById(R.id.btnRegistrarse);

        txtName.addTextChangedListener(new TextWatcherLite() {
            @Override
            public void onTextChanged(String text) {
                vm.setName(text);
            }
        });
        txtLastName.addTextChangedListener(new TextWatcherLite() {
            @Override
            public void onTextChanged(String text) {
                vm.setLastName(text);
            }
        });
        txtEmail.addTextChangedListener(new TextWatcherLite() {
            @Override
            public void onTextChanged(String text) {
                vm.setEmail(text);
            }
        });
        txtPasswd.addTextChangedListener(new TextWatcherLite() {
            @Override
            public void onTextChanged(String text) {
                vm.setPasswd(text);
            }
        });
        txtPasswdRepeat.addTextChangedListener(new TextWatcherLite() {
            @Override
            public void onTextChanged(String text) {
                vm.setPasswdRepeat(text);
            }
        });

        btnSignUp.setOnClickListener(v -> vm.signUp());

        vm.getIsSignUpEnabled().observe(this, btnSignUp::setEnabled);

        vm.getIsSuccessful().observe(this, isSuccessful -> {
            if (!isSuccessful) return;
            Intent i = new Intent(SignUpActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });

        vm.getError().observe(this, error -> {
            if (error.length() == 0) return;

            Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_SHORT).show();
        });
    }
}