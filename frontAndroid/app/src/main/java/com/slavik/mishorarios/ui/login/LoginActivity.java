package com.slavik.mishorarios.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.slavik.mishorarios.ui.main.MainActivity;
import com.slavik.mishorarios.R;
import com.slavik.mishorarios.ui.sign_up.SignUpActivity;
import com.slavik.mishorarios.util.TextWatcherLite;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginVM vm = new ViewModelProvider(this).get(LoginVM.class);

        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtPasswd = findViewById(R.id.txtPasswd);
        btnLogIn = findViewById(R.id.btnLogIn);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        vm.getErrorMessage().observe(this, error -> {
            if (error.length() == 0) return;

            Toast.makeText(
                    LoginActivity.this,
                    error,
                    Toast.LENGTH_SHORT)
                    .show();
        });

        vm.getLogInCorrect().observe(this, isCorrect -> {
            if (isCorrect) {
                navigate(MainActivity.class);
            }
        });

        findViewById(R.id.btnLogIn).setOnClickListener(v -> vm.logIn());

        txtEmail.addTextChangedListener(new TextWatcherLite() {
            @Override
            public void onTextChanged(String text) {
                vm.onInputChange(LoginInputs.email, text);
            }
        });

        txtPasswd.addTextChangedListener(new TextWatcherLite() {
            @Override
            public void onTextChanged(String text) {
                vm.onInputChange(LoginInputs.passwd, text);
            }
        });

        btnSignUp.setOnClickListener(v -> navigate(SignUpActivity.class));

        vm.getLogInEnabled().observe(this, isEnabled -> btnLogIn.setEnabled(isEnabled));
    }

    private void navigate(Class<? extends AppCompatActivity> klass) {
        Intent i = new Intent(LoginActivity.this, klass);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}