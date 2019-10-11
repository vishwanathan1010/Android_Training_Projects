package com.atmecs.digiwallet.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.atmecs.digiwallet.Activities.HomeActivity;
import com.atmecs.digiwallet.R;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class LoginFragment extends Fragment {
    public EditText editLoginPhoneNumber, editLoginPassword;
    public AwesomeValidation awesomeValidation;
    public Button createWallet;
    TextView forgotPasswordText;
    public Button openWallet;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        System.out.println("onCreateView");
        TextView forgotPassword = rootView.findViewById(R.id.link_forgot_password);
        forgotPassword.setText(Html.fromHtml("<u>Forgot Password?</u>"));
        forgotPassword.setClickable(true);
        forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        editLoginPhoneNumber = rootView.findViewById(R.id.phone_number);
        editLoginPassword = rootView.findViewById(R.id.password);

        openWallet = rootView.findViewById(R.id.btn_open_wallet);

        createWallet = rootView.findViewById(R.id.btn_create_wallet);
        createWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCreateWalletFragment();
            }
        });

        forgotPasswordText = rootView.findViewById(R.id.link_forgot_password);
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadForgotPasswordFragment();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated");

//        awesomeValidation.addValidation(getActivity(), R.id.phone_number, "^[0-9]{10}", R.string.login_phonenumber_error);
//        awesomeValidation.addValidation(getActivity(), R.id.password, "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", R.string.login_password_error);
        openWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
                    loadHomeActivity();
                }
            }
        });
    }

    public void loadCreateWalletFragment() {
        CreateWalletFragment createWalletFragment = new CreateWalletFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content_frame, createWalletFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void loadForgotPasswordFragment() {
        ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content_frame, forgotPasswordFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void loadHomeActivity() {

        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        Toast.makeText(getContext(), "Login Success", Toast.LENGTH_LONG).show();
    }
}
