package edu.neag.tia.Sign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import edu.neag.tia.MainActivity;
import edu.neag.tia.R;
import edu.neag.tia.RestClient.ResUser;
import edu.neag.tia.RestClient.client.UserService;
import edu.neag.tia.RestClient.client.Utils;
import edu.neag.tia.RestClient.dto.UserRegisterDTO;
import edu.neag.tia.Tracking.TrackingActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private UserService userService;
    private Button btnUpdateDate;
    public static UpdateActivity instance = null;
    private String credentials, emailIntent, lastNameFromResp, firstNameFromResp, lastPassword;
    private Toolbar toolbar;
    private TextView tvCurrentLast, tvCurrentFirst, tvCurrentEmail;
    private EditText etCurrentPassword, etNewPassword, etNewLastName, etNewFirstName, etNewEmail;
    private String newEmail, newPassword, newFirstName, newLastName, oldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        instance = this;

        Bundle extras = getIntent().getExtras();
        credentials = extras.getString("credentials");
        emailIntent = extras.getString("email");
        lastNameFromResp = extras.getString("lastName");
        firstNameFromResp = extras.getString("firstName");
        userService = Utils.getUserService();

        btnUpdateDate = (Button) findViewById(R.id.btnUpdateData);
        btnUpdateDate.setOnClickListener(this);
        tvCurrentLast = (TextView) findViewById(R.id.tvCurrentLast);
        tvCurrentFirst = (TextView) findViewById(R.id.tvCurrentFirst);
        tvCurrentEmail = (TextView) findViewById(R.id.tvCurrentEmail);
        etCurrentPassword = (EditText) findViewById(R.id.etCurrentPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etNewEmail = (EditText) findViewById(R.id.etNewEmail);
        etNewLastName = (EditText) findViewById(R.id.etNewLastName);
        etNewFirstName = (EditText) findViewById(R.id.etNewFirstName);

        tvCurrentEmail.setText(emailIntent);
        tvCurrentLast.setText(lastNameFromResp);
        tvCurrentFirst.setText(firstNameFromResp);

        toolbar = (Toolbar) findViewById(R.id.updateToolBar);
        toolbar.inflateMenu(R.menu.menu_app);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_home) {
                    openActivityFromMenu(UpdateActivity.this, HomeActivity.class, true);

                }
                if (item.getItemId() == R.id.menu_tracking) {
                    openActivityFromMenu(UpdateActivity.this, TrackingActivity.class, true);


                }
                if (item.getItemId() == R.id.menu_logout) {
                    if (TrackingActivity.instance != null) {
                        TrackingActivity.instance.finish();
                    }
                    if (UpdateActivity.instance != null) {
                        UpdateActivity.instance.finish();
                    }
                    if (HomeActivity.instance != null) {
                        HomeActivity.instance.finish();
                    }
                    startActivity(new Intent(UpdateActivity.this, MainActivity.class));

                }
                return false;
            }
        });


    }

    private void openActivityFromMenu(Context packageContext, Class<?> cls, boolean flagState) {
        Intent intent = new Intent(packageContext, cls);
        if (flagState) {
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        }
        intent.putExtra("credentials", credentials);
        intent.putExtra("email", emailIntent);
        intent.putExtra("lastName", lastNameFromResp);
        intent.putExtra("firstName", firstNameFromResp);
        startActivity(intent);

    }

    private void doUpdateDate(String emailIntent, String lastPassword, String newPassword, String newEmail, String newLastName, String newFirstName, String credentials) {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail(newEmail);
        userRegisterDTO.setFirstName(newFirstName);
        userRegisterDTO.setLastName(newLastName);
        userRegisterDTO.setPassword(newPassword);

        Call<ResUser> call = userService.updateDate(emailIntent, lastPassword, userRegisterDTO, credentials);
        call.enqueue(new Callback<ResUser>() {
            @Override
            public void onResponse(Call<ResUser> call, Response<ResUser> response) {
                if (response.code() == 200) {
                    if (response.isSuccessful()) {
                        ResUser user = response.body();
                        Toast.makeText(UpdateActivity.this, "Successfully update!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(UpdateActivity.this, "Error " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResUser> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdateData:
                oldPassword = etCurrentPassword.getText().toString();
                newPassword = etNewPassword.getText().toString();
                newEmail = etNewEmail.getText().toString();
                newLastName = etNewLastName.getText().toString();
                newFirstName = etNewFirstName.getText().toString();

                if (oldPassword.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "Please complete the old password field!", Toast.LENGTH_SHORT).show();
                }
                if (newPassword.isEmpty()) {
                    newPassword = oldPassword;
                }
                if (newEmail.isEmpty()) {
                    newEmail = tvCurrentEmail.getText().toString();
                }
                if (newFirstName.isEmpty()) {
                    newFirstName = tvCurrentFirst.getText().toString();
                }
                if (newLastName.isEmpty()) {
                    newLastName = tvCurrentLast.getText().toString();
                }

                doUpdateDate(emailIntent, oldPassword, newPassword, newEmail, newLastName, newFirstName, credentials);
                break;
        }
    }
}