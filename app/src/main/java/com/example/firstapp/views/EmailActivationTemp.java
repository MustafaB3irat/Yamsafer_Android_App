package com.example.firstapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.firstapp.R;
import com.example.firstapp.databinding.ActivationTempBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class EmailActivationTemp extends AppCompatActivity {

    private ActivationTempBinding activationTempBinding;
    private AppCompatActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        activationTempBinding = DataBindingUtil.setContentView(this, R.layout.activation_temp);
        activity = this;

        activationTempBinding.navigateToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseAuth.getInstance().getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                            Intent intent = new Intent(activity, MainActivity.class);
                            startActivity(intent);
                            activity.finish();
                        } else {
                            Toast.makeText(activity, "Please Verify Your Account ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}
