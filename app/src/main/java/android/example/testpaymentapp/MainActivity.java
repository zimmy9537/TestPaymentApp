package android.example.testpaymentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.PaymentApp;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;


public class MainActivity extends AppCompatActivity implements PaymentStatusListener {

    private EditText name;
    private EditText upiId;
    private EditText amount;
    private EditText description;

    private Button googlePay;
    private Button phonePe;
    private Button amazonPayUpi;
    private Button payTm;

    private TextView transactionStatus;

    private EasyUpiPayment easyUpiPayment;

    private String LOG_CAT = MainActivity.class.getSimpleName();

    private String G_PAY = "upi://pay?pa=9872598189@okbizaxis&pn=SAHGAL  KUMAR&mc=7372&aid=uGICAgIDXtpLiAw&tr=BCR2DN6T56OLBTBY";

    private String PAYTM = "upi://pay?pa=paytmqr281005050101mm617cyacrl1@paytm&pn=Paytm Merchant&mc=5499&mode=02&orgid=000000&paytmqr=281005050101MM617CYACRL1&sign=MEQCID0NFi3MYLXf8Yqjqwp7AqyIM7K0nlnQNBmke8X6Ou0fAiBErCzcP25K2wUYvXyt8nJG2OOqoDEAYyVkFKVjhloZYQ==";

    private String PHONE_PE = "upi://pay?mode=02&pa=Q09575115@ybl&purpose=00&mc=0000&pn=PhonePeMerchant&orgid=180001&sign=MEUCICPlo8QO10/oVQY3jp8lF7v+f+Ud6w6fWz6THZ40SXkIAiEAx5JG1pmqeCFntaGqIopgssixTQcSkQC/SDaLEcBmLeI=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        upiId = findViewById(R.id.upiId);
        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);

        googlePay = findViewById(R.id.googlePay);
        phonePe = findViewById(R.id.phonePe);
        amazonPayUpi = findViewById(R.id.amazonPayUpi);
        payTm = findViewById(R.id.payTm);

        transactionStatus = findViewById(R.id.transaction_status);

        googlePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewsEmpty()) {
                    transactionStatus.setVisibility(View.INVISIBLE);
                    if (!viewsEmpty()) {
                        googlePay();
                    } else {
                        Toast.makeText(MainActivity.this, "edittext empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        phonePe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewsEmpty()) {
                    Phone_pay();
                } else {
                    Toast.makeText(MainActivity.this, "edittext empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        amazonPayUpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewsEmpty()) {
                }
            }
        });

        payTm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionStatus.setVisibility(View.INVISIBLE);
                if (!viewsEmpty()) {
                    paytm_pay();
                } else {
                    Toast.makeText(MainActivity.this, "edittext empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    String getTransactionId() {
        String transactionId = "TID" + System.currentTimeMillis();
        return transactionId;
    }

    boolean viewsEmpty() {
        if (name.getText().toString().trim().isEmpty()
                || upiId.getText().toString().trim().isEmpty()
                || amount.getText().toString().trim().isEmpty()
                || description.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
//
//    @SuppressWarnings("NonConstantResourceId")
//    private void GooglePay(PaymentApp paymentApp)
//    {
//        //vpa is same as the upi id;
//        String payeeVpa=upiId.getText().toString().trim();
//        String payeeName=name.getText().toString().trim();
//        String paymentDescription=description.getText().toString().trim();
//        String paymentAmount=amount.getText().toString().trim();
//        String transactionId=getTransactionId();
//
//        EasyUpiPayment.Builder builder=new EasyUpiPayment.Builder(this)
//                .with(paymentApp)
//                .setPayeeVpa(payeeVpa)
//                .setPayeeName(payeeName)
//                .setTransactionId();
//    }

    private void googlePay() {
        String payeeVpa = upiId.getText().toString().trim();
        String payeeName = name.getText().toString().trim();
        String paymentDescription = description.getText().toString().trim();
        String paymentAmount = amount.getText().toString().trim();
        String transactionId = getTransactionId();

        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                .with(PaymentApp.GOOGLE_PAY)
                .setPayeeVpa(getGooglePayUpi())
                .setPayeeName(payeeName)
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionId)
                .setPayeeMerchantCode("BCR2DN6T56OLBTBY")
                .setDescription(paymentDescription)
                .setAmount(paymentAmount);

        try {
            easyUpiPayment = builder.build();
            easyUpiPayment.setPaymentStatusListener(this);
            easyUpiPayment.startPayment();
        } catch (Exception exception) {
            exception.printStackTrace();
            Toast.makeText(this, "Error:- " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void Phone_pay() {
        String payeeVpa = upiId.getText().toString().trim();
        String payeeName = name.getText().toString().trim();
        String paymentDescription = description.getText().toString().trim();
        String paymentAmount = amount.getText().toString().trim();
        String transactionId = getTransactionId();

        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                .with(PaymentApp.PHONE_PE)
                .setPayeeVpa(getphonePeUpi())
                .setPayeeName(payeeName)
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionId)
                .setPayeeMerchantCode("MS2004151341148690000798")
                .setDescription(paymentDescription)
                .setAmount(paymentAmount);

        try {
            easyUpiPayment = builder.build();
            easyUpiPayment.setPaymentStatusListener(this);
            easyUpiPayment.startPayment();
        } catch (Exception exception) {
            exception.printStackTrace();
            Toast.makeText(this, "Error:- " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void paytm_pay() {
        String payeeVpa = upiId.getText().toString().trim();
        String payeeName = name.getText().toString().trim();
        String paymentDescription = description.getText().toString().trim();
        String paymentAmount = amount.getText().toString().trim();
        String transactionId = getTransactionId();

        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                .with(PaymentApp.PAYTM)
                .setPayeeVpa(getPaytmUpi())
                .setPayeeName(payeeName)
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionId)
                .setPayeeMerchantCode("paytm")
                .setDescription(paymentDescription)
                .setAmount(paymentAmount);

        try {
            easyUpiPayment = builder.build();
            easyUpiPayment.setPaymentStatusListener(this);
            easyUpiPayment.startPayment();
        } catch (Exception exception) {
            exception.printStackTrace();
            Toast.makeText(this, "Error:- " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String getPaytmUpi() {
        String upi = "paytmqr281005050101mm617cyacrl1@paytm";
        return upi;
    }

    private String getGooglePayUpi() {
        String upi = "9872598189@okbizaxis";
        return upi;
    }

    private String getphonePeUpi() {
        String upi = "Q09575115@ybl";
        return upi;
    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        Toast.makeText(this, "Transaction Completed", Toast.LENGTH_SHORT).show();
        Log.v(LOG_CAT, "Transaction details  :- " + transactionDetails);
        switch (transactionDetails.getTransactionStatus()) {
            case SUCCESS:
                onTransactionSuccess();
                break;
            case FAILURE:
                onTransactionFailure();
                break;
            case SUBMITTED:
                onTransactionSubmitted();
                break;
        }
    }

    private void onTransactionSubmitted() {
        Toast.makeText(this, "Transaction Pending", Toast.LENGTH_SHORT).show();
        transactionStatus.setText("Transaction Pending");
    }

    private void onTransactionFailure() {
        Toast.makeText(this, "Transaction Failed", Toast.LENGTH_SHORT).show();
        transactionStatus.setText("Transaction Failed");
    }

    private void onTransactionSuccess() {
        Toast.makeText(this, "Transaction Successful", Toast.LENGTH_SHORT).show();
        transactionStatus.setText("Transaction Successful");
    }

    @Override
    public void onTransactionCancelled() {
        Toast.makeText(this, "Transaction cancelled", Toast.LENGTH_SHORT).show();
        transactionStatus.setVisibility(View.VISIBLE);
        transactionStatus.setText("Transaction cancelled");
    }
}