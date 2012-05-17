package org.ei.drishti.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.service.LoginService;

import static org.ei.drishti.util.Log.logVerbose;

public class LoginActivity extends Activity {
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logVerbose("Initializing ...");
        setContentView(R.layout.login);

        context = Context.getInstance().updateApplicationContext(this.getApplicationContext());
    }

    public void login(View view) {
        String userName = ((EditText) findViewById(R.id.userNameText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordText)).getText().toString();
        showMessage("User: " + userName + ", Password: " + password);

        LoginService loginService = context.loginService();
        if (loginService.isValidLocalLogin(userName, password)) {
            loginWith(userName, password);
        }
        else {
            showMessage("Logging in using CommCare ...");
            if (loginService.isValidRemoteLogin(userName, password)) {
                loginWith(userName, password);
            }
            else {
                showMessage("Login failed. Please check the credentials.");
            }
        }
    }

    private void loginWith(String userName, String password) {
        context.loginService().loginWith(userName, password);
        startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
