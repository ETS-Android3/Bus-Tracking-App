package edu.neag.tia.Sign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.neag.tia.MainActivity;
import edu.neag.tia.R;
import edu.neag.tia.RestClient.ResComment;
import edu.neag.tia.RestClient.client.UserService;
import edu.neag.tia.RestClient.client.Utils;
import edu.neag.tia.RestClient.dto.CommentDTO;
import edu.neag.tia.Tracking.TrackingActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements AddCommentActivity.UpdateDialogListener {

    public static HomeActivity instance = null;
    private String credentials, emailIntent, firstNameFromResp, lastNameFromResp;
    private UserService userService;
    private Toolbar toolbar;
    private FloatingActionButton btnOpenPopup;
    private LinearLayout linearLayout;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        instance = this;

        Bundle extras = getIntent().getExtras();
        credentials = extras.getString("credentials");
        emailIntent = extras.getString("email");
        lastNameFromResp = extras.getString("lastName");
        firstNameFromResp = extras.getString("firstName");
        userService = Utils.getUserService();

        linearLayout = (LinearLayout) findViewById(R.id.rlMainHome);
        cardView = (CardView) findViewById(R.id.cdHome);

        btnOpenPopup = (FloatingActionButton) findViewById(R.id.btnOpenPopup);
        btnOpenPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.homeToolBar);
        toolbar.inflateMenu(R.menu.menu_app);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_update) {
                    openActivityFromMenu(HomeActivity.this, UpdateActivity.class, true);

                }
                if (item.getItemId() == R.id.menu_tracking) {
                    openActivityFromMenu(HomeActivity.this, TrackingActivity.class, true);


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
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));

                }
                return false;
            }
        });

        doGetAllComments(credentials);

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

    private void doAddComment(String comment, String credentials) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment(comment);

        Call<ResComment> call = userService.addComment(commentDTO, credentials);
        call.enqueue(new Callback<ResComment>() {
            @Override
            public void onResponse(Call<ResComment> call, Response<ResComment> response) {
                if (response.code() == 200) {
                    if (response.isSuccessful()) {
                        ResComment comment = response.body();
                        Toast.makeText(HomeActivity.this, "Successfully added!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Error " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResComment> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void doGetAllComments(String credentials) {

        Call<List<ResComment>> call = userService.getAllComments(credentials);
        call.enqueue(new Callback<List<ResComment>>() {
            @Override
            public void onResponse(Call<List<ResComment>> call, Response<List<ResComment>> response) {
                if (response.code() == 200) {
                    if (response.isSuccessful()) {
                        List<ResComment> responseArray = response.body();
                        for (int i = responseArray.size() - 1; i >= 0; i--) {

                            TextView tvComment = new TextView(HomeActivity.this);
                            TextView tvDate = new TextView(HomeActivity.this);
                            TextView tvSpace = new TextView(HomeActivity.this);

                            tvDate.setText(responseArray.get(i).getCreationTime() + "");
                            linearLayout.addView(tvDate);

                            tvComment.setText(responseArray.get(i).getComment());
                            linearLayout.addView(tvComment);

                            tvSpace.setText("\n\n");
                            linearLayout.addView(tvSpace);
                        }


                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Error " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResComment>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openDialog() {
        AddCommentActivity addCommentActivity = new AddCommentActivity();
        addCommentActivity.show(getSupportFragmentManager(), "Add News");
    }

    @Override
    public void applyText(String comment) {
        doAddComment(comment, credentials);
    }
}