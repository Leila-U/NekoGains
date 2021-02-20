package com.example.nekogains;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private TextView errorMessage;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        errorMessage = findViewById(R.id.errorMessage);
    }

    public void login(View view) {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        validate(username, password);
    }

    private void validate(String username, String password) {
        //TODO: Implement database

        if(username.equals("user") && password.equals("1234")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            errorMessage.setText(R.string.login_failed);
        }
    }

    public void register(View view) {
        //TODO: Uncomment this when registration is implemented
        //Intent intent = new Intent(this, RegisterActivity.class);
        //startActivity(intent);
    }
}
