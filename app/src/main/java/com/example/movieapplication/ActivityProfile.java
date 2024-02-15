package com.example.movieapplication;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }

        Button logout = findViewById(R.id.logout);

        // Atur email pada TextView di activity
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = (user != null) ? user.getEmail() : "No Email";
        TextView emailTextView = findViewById(R.id.tvemail);
        emailTextView.setText(userEmail);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        // Menambahkan tombol back
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });

    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Logout");

        // set message for the dialog
        alertDialogBuilder
                .setMessage("Yakin Logout?")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the logout action here
                        Intent intent = new Intent(ActivityProfile.this, ActivityLogin.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If this button is clicked, close the dialog
                        // and do nothing
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}