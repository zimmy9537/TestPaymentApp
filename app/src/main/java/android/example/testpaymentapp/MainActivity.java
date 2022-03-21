package android.example.testpaymentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText amount;
    private EditText description;

    private Button googlePay;
    private Button phonePe;
    private Button amazonPayUpi;
    private Button payTm;

    private TextView transactionStatus;

    String rawStringBharatPe="upi://pay?pa=BHARATPE90718988349@yesbankltd&pn=BharatPe%20Merchant&cu=INR&tn=Verified%20Merchant";
    String rawStringGpay = "upi://pay?pa=9988890048@okbizaxis&pn=N.K%20MEDICOS&mc=5912&aid=uGICAgIC3joqcAQ&tr=BCR2DN6TR6SKRMR2";
    String rawStringPaytm = "upi://pay?pa=paytmqr281005050101mm617cyacrl1@paytm&pn=Paytm%20Merchant&mc=5499&mode=02&orgid=000000&paytmqr=281005050101MM617CYACRL1&sign=MEQCID0NFi3MYLXf8Yqjqwp7AqyIM7K0nlnQNBmke8X6Ou0fAiBErCzcP25K2wUYvXyt8nJG2OOqoDEAYyVkFKVjhloZYQ==";
    String upiId2 = "paytmqr281005050101mm617cyacrl1@paytm";
    String upiId = "9872598189@okbizaxis";
    String merchantId = "JsuLrn83183937545946";
    String merchantPn = "Paytm Merchant";
    String accountName = "SAHGAL KUMAR";

    private int REQUEST_CODE = 125;
    private String BHIM_UPI = "in.org.npci.upiapp";
    private String GOOGLE_PAY = "com.google.android.apps.nbu.paisa.user";
    private String PHONE_PE = "com.phonepe.app";
    private String PAYTM = "net.one97.paytm";
    private String AMAZON_PAY="in.amazon.mShop.android.shopping";

    private static final int TEZ_REQUEST_CODE = 123;

    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";

    private String LOG_CAT = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);

        googlePay = findViewById(R.id.googlePay);
        phonePe = findViewById(R.id.phonePe);
        amazonPayUpi = findViewById(R.id.amazonPayUpi);
        payTm = findViewById(R.id.payTm);

        transactionStatus = findViewById(R.id.transaction_status);

        ArrayList<String> upiApps = new ArrayList<>();
        upiApps.add(new String(PAYTM));
        upiApps.add(new String(GOOGLE_PAY));
        upiApps.add(new String(PHONE_PE));
        upiApps.add(new String(BHIM_UPI));

        googlePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewsEmpty()) {

                    String p = GOOGLE_TEZ_PACKAGE_NAME;
                    if (isAppInstalled(p) && isUpiReady(p)) {

                        Toast.makeText(MainActivity.this, "amount 1.0 default", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(getBharatUri());
                        intent.setPackage(p);
                        startActivityForResult(intent, TEZ_REQUEST_CODE);
                    } else {
                        Toast.makeText(MainActivity.this, "app not present or not upi ready", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "edittext empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        phonePe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewsEmpty()) {

                    String p = PHONE_PE;
                    if (isAppInstalled(p) && isUpiReady(p)) {
                        Toast.makeText(MainActivity.this, "amount 0.1 default", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Intent.ACTION_VIEW, getUpiUri());
                        intent.setData(getUpiUri());
                        intent.setPackage(p);
                        startActivityForResult(intent, REQUEST_CODE);

                    } else {
                        Toast.makeText(MainActivity.this, "app not installed or not upi ready", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "edittext empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        amazonPayUpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewsEmpty()) {

                    String bhim="in.org.npci.upiapp";

                    String p = AMAZON_PAY;
                    if (isAppInstalled(bhim) && isUpiReady(bhim)) {
                        Toast.makeText(MainActivity.this, "amount 1.0 default", Toast.LENGTH_SHORT).show();

                        Uri uriGoogle=Uri.parse("upi://pay?pa=9988890048@okbizaxis&am=1.0&tn=test&pn=N.K%20MEDICOS&mc=5912&aid=uGICAgIC3joqcAQ&tr=BCR2DN6TR6SKRMR2");
                        Uri uriBharatPe=Uri.parse("upi://pay?pa=BHARATPE90718988349@yesbankltd&am=1.0&tn=test&pn=BharatPe%20Merchant&cu=INR&tn=Verified%20Merchant");

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(uriGoogle);
                        intent.setPackage(bhim);
                        startActivityForResult(intent, REQUEST_CODE);
                    } else {
                        Toast.makeText(MainActivity.this, "app not present or not upi ready", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "edittext empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        payTm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionStatus.setVisibility(View.INVISIBLE);
                if (!viewsEmpty()) {

                    String p = PAYTM;
                    if (isAppInstalled(p) && isUpiReady(p)) {
                        Toast.makeText(MainActivity.this, "amount 0.1 default", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(getUpiUri());
                        intent.setPackage(p);
                        startActivityForResult(intent, REQUEST_CODE);

                    } else {
                        Toast.makeText(MainActivity.this, "app not installed or not upi ready", Toast.LENGTH_SHORT).show();
                    }

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

    boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean isUpiReady(String packageName) {
        boolean appUpiReady = false;
        Intent upiIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("upi://pay"));
        PackageManager pm = getPackageManager();
        List<ResolveInfo> upiActivities = pm.queryIntentActivities(upiIntent, 0);
        for (ResolveInfo a : upiActivities) {
            if (a.activityInfo.packageName.equals(packageName)) {
                appUpiReady = true;
            }
        }
        return appUpiReady;
    }

    boolean viewsEmpty() {
        if (amount.getText().toString().trim().isEmpty()
                || description.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    Uri getUpiUri() {
        Uri uri = new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", accountName)
                .appendQueryParameter("mc", "7372")
                .appendQueryParameter("tr", "2123123434423458786")
                .appendQueryParameter("tn", "test transaction note")
                .appendQueryParameter("am", "1.00")
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("url", "https://www.gastos.in/")
                .build();
        return uri;
    }

    Uri getBharatUri() {
//        return Uri.parse("upi://pay?pa=BHARATPE90718988349@yesbankltd&am=1.00&pn=BharatPe%20Merchant&cu=INR&tn=Verified%20Merchant");

        Uri uri = new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", "BHARATPE90718988349@yesbankltd")
                .appendQueryParameter("pn", "BharatPe Merchant")
                .appendQueryParameter("tr", "123123767556565612123")
                .appendQueryParameter("tn", "test transaction note")
                .appendQueryParameter("am", "1.00")
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("url", "https://www.gastos.in/")
                .build();
        return uri;


    }

    Uri getUpiUri2() {

        //phone pe to google pay worked, with success response.
        return Uri.parse("upi://pay?mode=02&pa=Q09575115@ybl&purpose=00&mc=0000&am=1.00&tn=test&pn=PhonePeMerchant&orgid=180001&sign=MEUCICPlo8QO10/oVQY3jp8lF7v+f+Ud6w6fWz6THZ40SXkIAiEAx5JG1pmqeCFntaGqIopgssixTQcSkQC/SDaLEcBmLeI=");

//        //google pay on google pay fails, says that the transaction is risky.
//        return Uri.parse("upi://pay?pa=9872598189@okbizaxis&am=1.00&tn=test&pn=SAHGAL%20%20KUMAR&mc=7372&aid=uGICAgIDXtpLiAw&tr=BCR2DN6T56OLBTBY");

//        //paytm qr on gpay worked, with SUCCESS response
//        return Uri.parse("upi://pay?pa=paytmqr281005050101mm617cyacrl1@paytm&am=1.00&tn=test&pn=Paytm%20Merchant&mc=5499&mode=02&orgid=000000&paytmqr=281005050101MM617CYACRL1&sign=MEQCID0NFi3MYLXf8Yqjqwp7AqyIM7K0nlnQNBmke8X6Ou0fAiBErCzcP25K2wUYvXyt8nJG2OOqoDEAYyVkFKVjhloZYQ==");

//        //paytm
//        Uri uri = new Uri.Builder()
//                .scheme("upi")
//                .authority("pay")
//                .appendQueryParameter("pa", upiId2)
//                .appendQueryParameter("pn", merchantPn)
//                .appendQueryParameter("mc", "5499")
//                .appendQueryParameter("tr", "123123767556565612123")
//                .appendQueryParameter("tn", "test transaction note")
//                .appendQueryParameter("am", "1.00")
//                .appendQueryParameter("cu", "INR")
//                .appendQueryParameter("url", "https://www.gastos.in/")
//                .build();
//        return uri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEZ_REQUEST_CODE) {

            // Process based on the data in response.
            Log.d("result", data.getStringExtra("Status"));
            Toast.makeText(this, "status " + data.getStringExtra("Status"), Toast.LENGTH_SHORT).show();
            return;
        }


        if (requestCode == this.REQUEST_CODE) {
            Log.d("result", String.valueOf(data));
            String var10000;
            String var4;
            if (data != null) {
                var10000 = data.getStringExtra("Status");
                if (var10000 != null) {
                    var4 = var10000;
                    Log.d("result is ", var4);
                }
            }

            if (data != null) {
                var10000 = data.getStringExtra("Status");
                if (var10000 != null) {
                    var4 = var10000;
                    Toast.makeText(this.getApplicationContext(), "here " + (CharSequence) var4, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}