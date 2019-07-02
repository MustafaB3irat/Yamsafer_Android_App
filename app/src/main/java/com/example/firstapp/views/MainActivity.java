package com.example.firstapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.firstapp.databinding.SignInBinding;
import com.example.firstapp.mvpinterfaces.MainPresenter;
import com.example.firstapp.mvpinterfaces.MainView;
import com.example.firstapp.models.Hotel;
import com.example.firstapp.recyclerviewadapters.HotelsListAdapter;
import com.example.firstapp.R;
import com.example.firstapp.databinding.ActivityMainBinding;

import java.util.List;


@BindingMethods({
        @BindingMethod(type = ImageView.class, attribute = "android:src", method = "setImageResource"),
        @BindingMethod(type = RatingBar.class, attribute = "android:numStars", method = "setNumStar")
})
public class MainActivity extends AppCompatActivity implements MainView {

    //    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private androidx.recyclerview.widget.RecyclerView.Adapter adapter;
//    private androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager;

    private ActivityMainBinding mainBinding;
    private SignInBinding signInBinding;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        presenter = new com.example.firstapp.Presenters.MainPresenter(this);
        presenter.onRequestHotels();

        setSignInActivity(this);

    }


    private void setSignInActivity(final AppCompatActivity activity) {

        mainBinding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SigninActivity = new Intent(activity, SignInActivity.class);
                startActivity(SigninActivity);
            }
        });
    }


    @Override
    public void onHotelsRequestResponse(List<Hotel> hotels) {
        adapter = new HotelsListAdapter(hotels);
        mainBinding.recyclerview.setAdapter(adapter);


    }


}
