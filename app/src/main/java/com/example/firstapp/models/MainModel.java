package com.example.firstapp.models;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.firstapp.Presenters.ActivityMainPresenter;
import com.example.firstapp.mvpinterfaces.Main;
import com.example.firstapp.views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainModel implements Main.MainActivityModel {

    private FirebaseRemoteConfig firebaseRemoteConfig;
    private FirebaseDynamicLinks firebaseDynamicLinks;
    private Main.ActivityMainPresenter presenter;


    public MainModel(ActivityMainPresenter presenter) {

        this.presenter = presenter;

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        firebaseDynamicLinks = FirebaseDynamicLinks.getInstance();
    }

    @Override
    public void initiateRemoteConfig() {

        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true).setFetchTimeoutInSeconds(3600)
                .build();

        firebaseRemoteConfig.setConfigSettings(remoteConfigSettings);
        firebaseRemoteConfig.setDefaults(MainActivity.defaultValuesForRemoteConfig);
    }

    @Override
    public void fetchNewValues() {

    }

    @Override
    public void buildDynamicLink(OnCompleteListener<ShortDynamicLink> onCompleteListener) {
        String longLink = firebaseDynamicLinks.createDynamicLink()
                .setDomainUriPrefix("https://yamsafermustafa.page.link")
                .setLink(Uri.parse("https://www.youtube.com/channel/UCo1_4CUyejRBFY3sW_nqzyQ?view_as=subscriber"))
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().setTitle("Share this app").setDescription("This is a clone of Yamsafer App").setImageUrl(Uri.parse("http://www.krugerpark.co.za/images/1-lion-charge-gc590a.jpg")).build())
                .buildDynamicLink().getUri().toString();

        firebaseDynamicLinks.createDynamicLink().setLongLink(Uri.parse(longLink))
                .buildShortDynamicLink()
                .addOnCompleteListener(onCompleteListener);

    }

    @Override
    public void shareApp() {

        OnCompleteListener<ShortDynamicLink> onCompleteListener = task -> {

            if (task.isSuccessful()) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Share this App " + task.getResult().getShortLink());
                intent.setType("text/plain");

                presenter.setIntentFromDynamicLink(intent);
            }

        };

        buildDynamicLink(onCompleteListener);


    }
}
