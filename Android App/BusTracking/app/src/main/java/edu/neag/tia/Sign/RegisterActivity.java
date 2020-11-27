package edu.neag.tia.Sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private TextView tvLogin, tvPass;
    private EditText etFirstName, etLastName, etEmail, etPassword;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = (Button) findViewById(R.id.btnSave);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        etFirstName = (EditText) findViewById(R.id.etfirstName);
        etLastName = (EditText) findViewById(R.id.etlastName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvPass = (TextView)findViewById(R.id.tvPass);
        userService = Utils.getUserService();
        tvPass.setError("Password must be at least 8 characters long!");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastName = etLastName.getText().toString();
                String firstName = etFirstName.getText().toString();
                String email = etEmail.getText().toString();
                String password  = etPassword.getText().toString();

                if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please complete all required fields!", Toast.LENGTH_SHORT).show();
                }else if(password.length() < 8){
                    Toast.makeText(RegisterActivity.this, "Please check password!", Toast.LENGTH_SHORT).show();
                }else{
                    doRegister(firstName, lastName, email, password);
                }

            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void doRegister(final String firstName, final String lastName, final String email, final String password) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(firstName, lastName, email, password);
        Call<ResUser> call = userService.register(userRegisterDTO);

        call.enqueue(new Callback<ResUser>() {
            @Override
            public void onResponse(Call<ResUser> call, Response<ResUser> response) {
                if (response.code() == 200) {
                    if (response.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Succes !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Error ! Please verify your data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResUser> call, Throwable t) {
                finish();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}