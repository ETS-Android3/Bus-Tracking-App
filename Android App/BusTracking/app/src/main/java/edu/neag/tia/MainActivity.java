package edu.neag.tia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import edu.neag.tia.RestClient.ResUser;
import edu.neag.tia.RestClient.client.RetrofitClient;
import edu.neag.tia.RestClient.client.UserService;
import edu.neag.tia.RestClient.client.Utils;
import edu.neag.tia.RestClient.dto.UserLoginDTO;
import edu.neag.tia.Sign.RegisterActivity;
import edu.neag.tia.Tracking.TrackingActivity;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etEmail, etPassword;
    private TextView tvRegister;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        userService = Utils.getUserService();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please complete all required fields!", Toast.LENGTH_SHORT).show();
                } else {

                    doLogin(email, password);

                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);


            }
        });

    }

    private void doLogin(final String email, final String password) {

        UserLoginDTO userLoginDTO = new UserLoginDTO(email, password);
        final String credentials = Credentials.basic(email, password);
        Call<ResUser> call = userService.login(userLoginDTO, credentials);

        call.enqueue(new Callback<ResUser>() {


            @Override
            public void onResponse(Call<ResUser> call, Response<ResUser> response) {
                if (response.code() == 200) {
                    if (response.isSuccessful()) {
                        ResUser resUser = (ResUser) response.body();
                        Intent intent = new Intent(MainActivity.this, TrackingActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("credentials", credentials);
                        intent.putExtra("firstName", resUser.getFirstName());
                        intent.putExtra("lastName", resUser.getLastName());
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "The email or password are incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResUser> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}