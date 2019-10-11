package com.atmecs.digiwallet.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.atmecs.digiwallet.R;
import com.atmecs.digiwallet.Utilities.Utils;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;


public class CreateWalletFragment extends Fragment {
    public EditText editFirstName, editLastName, editEmailId, editphoneNumber, editPassword, editConfirmPassword;
    public CheckBox checkBox;
    public AwesomeValidation awesomeValidation;
    Toolbar toolbar;
    Button createWalletButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_createwallet, container, false);
        TextView textView = rootView.findViewById(R.id.text_checkbox);
        final String text = "By checking, I agree to the Terms & Conditions and Privacy Policy of the Digi Wallet";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        ClickableSpan termsClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Toast.makeText(getContext(), "Terms & Conditions clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(true);
                textPaint.setTypeface(Typeface.DEFAULT_BOLD);
                textPaint.setColor(Color.BLACK);
                textPaint.bgColor = (Color.WHITE);
            }
        };

        ClickableSpan policyClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Privacy Policy is clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(true);
                textPaint.setTypeface(Typeface.DEFAULT_BOLD);
                textPaint.setColor(Color.BLACK);
                textPaint.bgColor = (Color.WHITE);
            }
        };
        spannableStringBuilder.setSpan(
                termsClickableSpan, // Span to add
                text.indexOf("Terms & Conditions"), // Start of the span (inclusive)
                text.indexOf("Terms & Conditions") + String.valueOf("Terms & Conditions").length(), // End of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE // Do not extend the span when text add later
        );
        spannableStringBuilder.setSpan(
                policyClickableSpan,
                text.indexOf("Privacy Policy"),
                text.indexOf("Privacy Policy") + String.valueOf("Privacy Policy").length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        textView.setText(spannableStringBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        toolbar = rootView.findViewById(R.id.create_wallet_toolbar);
        toolbar.setTitle(Utils.getStringValue(R.string.create_wallet_title, getContext()));
        toolbar.setTitleTextColor(getResources().getColor(R.color.blackColor));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });


        editFirstName = rootView.findViewById(R.id.text_first_name);
        editLastName = rootView.findViewById(R.id.text_last_name);
        editEmailId = rootView.findViewById(R.id.text_email);
        editphoneNumber = rootView.findViewById(R.id.text_phone_number);
        editPassword = rootView.findViewById(R.id.createwallet_password);
        editConfirmPassword = rootView.findViewById(R.id.createwallet_confirm_password);
        checkBox = rootView.findViewById(R.id.button_checkbox);

        createWalletButton = rootView.findViewById(R.id.button_create_wallet);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        awesomeValidation.addValidation(getActivity(), R.id.text_first_name, "^([A-Za-z ]+)", R.string.firstname_error);
        awesomeValidation.addValidation(getActivity(), R.id.text_last_name, "^^([A-Za-z ]+)", R.string.lastname_error);
        awesomeValidation.addValidation(getActivity(), R.id.text_email, Patterns.EMAIL_ADDRESS, R.string.email_error);
        awesomeValidation.addValidation(getActivity(), R.id.text_phone_number, "^[0-9]{10}", R.string.phonenumber_error);
        awesomeValidation.addValidation(getActivity(), R.id.createwallet_password, "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&!*]).{6,30})", R.string.password_error);
        awesomeValidation.addValidation(getActivity(), R.id.createwallet_confirm_password, "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&!*]).{6,30})", R.string.confirmpassword_error);

        createWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {

                    String passwordString = editPassword.getText().toString();
                    String confirmPasswordString = editConfirmPassword.getText().toString();

                    if (!passwordString.equals(confirmPasswordString)) {
                        editConfirmPassword.setError("Password mismatch");
                        return;
                    }
                    if (checkBox.isChecked()) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                        // set title
                        alertDialogBuilder.setTitle("Registration");
                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Your wallet is successfully created. Please login to activate.")
                                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentManager.popBackStackImmediate();
                                    }
                                });
                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();

                    } else {
                        Toast.makeText(getContext(), "Please accept the Terms & Conditions", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}