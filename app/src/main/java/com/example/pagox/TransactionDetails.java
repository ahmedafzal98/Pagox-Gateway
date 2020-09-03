package com.example.pagox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class TransactionDetails extends AppCompatActivity {

    EditText edit_tenId,edit_amount,edit_remAmount,edit_procId,edit_last4digit,edit_status,edit_message,edit_authCode,edit_payId,edit_expMonth,editexoyear,edit_isprof,edit_acccNum,edit_accName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        edit_acccNum = findViewById(R.id.edit_accNumb);
        edit_accName = findViewById(R.id.edit_accName);
        edit_amount = findViewById(R.id.edit_amount);
        edit_authCode = findViewById(R.id.edit_auth_code);
        edit_expMonth = findViewById(R.id.edit_expMonth);
        edit_isprof = findViewById(R.id.edit_isProfile);
        edit_last4digit = findViewById(R.id.edit_last4digit);
        edit_message = findViewById(R.id.edit_message);
        edit_payId = findViewById(R.id.edit_payId);
        edit_remAmount = findViewById(R.id.edit_remaAmount);
        edit_status = findViewById(R.id.edit_status);
        edit_tenId = findViewById(R.id.edit_tenderId);
        editexoyear = findViewById(R.id.edit_expYear);

        Intent intent = getIntent();
        String tenId = intent.getStringExtra("TenderTypeID");
        String amount = intent.getStringExtra("Amount");
        String remainingAmount = intent.getStringExtra("RemainingAmount");
        String last4Digit = intent.getStringExtra("Last4Digit");
        String status = intent.getStringExtra("Status");
        String message = intent.getStringExtra("Message");
        String authCode = intent.getStringExtra("AuthCode");
        String paymentTypeID = intent.getStringExtra("PaymentTypeID");
        String expiryMonth = intent.getStringExtra("ExpiryMonth");
        String expiryYear = intent.getStringExtra("ExpiryYear");
        String isProfileSave = intent.getStringExtra("IsProfileSave");
        String accountNumber = intent.getStringExtra("AccountNumber");
        String accountName = intent.getStringExtra("AccountName");

        editexoyear.setText(expiryYear);
        edit_tenId.setText(tenId);
        edit_status.setText(status);
        edit_remAmount.setText(remainingAmount);
        edit_payId.setText(paymentTypeID);
        edit_message.setText(message);
        edit_last4digit.setText(last4Digit);
        edit_isprof.setText(isProfileSave);
        edit_expMonth.setText(expiryMonth);
        edit_authCode.setText(authCode);
        edit_amount.setText(amount);
        edit_accName.setText(accountName);
        edit_acccNum.setText(accountNumber);


    }
}
