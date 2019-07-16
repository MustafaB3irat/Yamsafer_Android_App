package com.example.firstapp.views.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.firstapp.R;
import com.example.firstapp.databinding.IntPhoneDialogBinding;
import com.example.firstapp.views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import java.util.concurrent.TimeUnit;

public class InternationalPhoneDialog extends DialogFragment {

    private IntPhoneDialogBinding phoneDialogBinding;
    private IntlPhoneInput phoneInput;
    private String phoneNum;
    private String codeSent;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        phoneDialogBinding = DataBindingUtil.inflate(inflater, R.layout.int_phone_dialog, null, true);

        phoneDialogBinding.phoneInput.setNumber("+970598894790");
        phoneDialogBinding.send.setVisibility(View.VISIBLE);
        phoneDialogBinding.phoneInput.setVisibility(View.VISIBLE);
        phoneDialogBinding.confirmationCode.setVisibility(View.GONE);
        phoneDialogBinding.SignIn.setVisibility(View.GONE);
        builder.setView(phoneDialogBinding.getRoot());


        phoneDialogBinding.phoneInput.setOnClickListener(view -> {
            if (phoneDialogBinding.phoneInput.getNumber().equals("+970598894790"))
                phoneDialogBinding.phoneInput.setNumber("+970000000000");
        });

        phoneDialogBinding.send.setOnClickListener(view -> {

            if (phoneDialogBinding.phoneInput.getNumber().equals("+970598894790")) {
                Toast.makeText(getActivity(), R.string.please_enter_phone, Toast.LENGTH_SHORT).show();
                return;
            }

            phoneInput = phoneDialogBinding.phoneInput;

            if (phoneInput.isValid()) {
                phoneNum = phoneInput.getNumber();
            }


            signInViaPhone();

        });

        phoneDialogBinding.SignIn.setOnClickListener(view -> checkCode());


        return builder.create();

    }


    private void signInViaPhone() {

        PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(getContext(), R.string.phone_failed_to_send_code, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                codeSent = s;
                phoneDialogBinding.confirmationCode.setVisibility(View.VISIBLE);
                phoneDialogBinding.SignIn.setVisibility(View.VISIBLE);
                phoneDialogBinding.send.setVisibility(View.GONE);
                phoneDialogBinding.phoneInput.setVisibility(View.GONE);
                phoneInput.hideKeyboard();

            }
        };


        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, 60, TimeUnit.SECONDS, getActivity(), callbacks);

    }


    private void signInWithCredintials(PhoneAuthCredential credential) {

        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                FirebaseUser user = task.getResult().getUser();
                Toast.makeText(getContext(), getActivity().getString(R.string.welcome) + " " + user.getPhoneNumber(), Toast.LENGTH_SHORT).show();
                dismiss();

                ((MainActivity) getActivity()).getSignInState(true);
            } else {
                Toast.makeText(getContext(), R.string.sign_in_failed, Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void checkCode() {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, phoneDialogBinding.confirmationCode.getText().toString());
        signInWithCredintials(credential);
    }
}
