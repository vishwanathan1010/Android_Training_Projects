package com.atmecs.digiwallet.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.atmecs.digiwallet.R;
import com.atmecs.digiwallet.Utilities.Utils;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class ForgotPasswordFragment extends Fragment {
    public EditText editForgotPasswordEmailId;
    public AwesomeValidation awesomeValidation;
    Button sendPassword;
    Toolbar toolbar;
    EditText forgotPasswordEmail;
    public static View rootView;
    public static String emailAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        toolbar = rootView.findViewById(R.id.forgot_toolbar);
        toolbar.setTitle(Utils.getStringValue(R.string.forgot_password_title, getContext()));
        toolbar.setTitleTextColor(getResources().getColor(R.color.blackColor));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        editForgotPasswordEmailId = rootView.findViewById(R.id.text_forgotpassword_email);

        sendPassword = rootView.findViewById(R.id.button_send_password);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated");

        awesomeValidation.addValidation(getActivity(), R.id.text_forgotpassword_email, Patterns.EMAIL_ADDRESS, R.string.forgot_password__email_error);
        sendPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    forgotPasswordEmail = rootView.findViewById(R.id.text_forgotpassword_email);
                    emailAddress = forgotPasswordEmail.getText().toString();
                    showConfirmationMessage();
                }
            }
        });
    }

    public void showConfirmationMessage() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        // set title
        alertDialogBuilder.setTitle("Forgot Password");
        // set dialog message
        alertDialogBuilder
                .setMessage("Successfully sent a verification link to " + emailAddress)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.popBackStackImmediate();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}