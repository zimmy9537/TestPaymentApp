package android.example.testpaymentapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private int UPI_REQUEST_CODE = 123;

    private RadioGroup payGroup;
    private RadioButton payRadio;
    private Button googlePay;
    private EditText amountEt;
    private TextView resultTv;
    private Button bhimUpi;

    private final String googlePayPackage = "com.google.android.apps.nbu.paisa.user";
    private final String bhimUpiPackage = "in.org.npci.upiapp";

    private final String googlePayBaseString = "upi://pay?pa=9988890048@okbizaxis&pn=N.K MEDICOS&mc=5912&aid=uGICAgIC3joqcAQ&tr=BCR2DN6TR6SKRMR2";
    private final String paytmBaseString = "upi://pay?pa=paytmqr2810050501011gj3dtvouykl@paytm&pn=Paytm Merchant&mc=5499&mode=02&orgid=000000&paytmqr=2810050501011GJ3DTVOUYKL&sign=MEQCID5V1zKeK5cPAy1nQFPRffDc1VicVDEje9iL96JgZfiUAiB+CgEV6kbnm49jJ5G9yNi1aZj32eJibgjWkyf3EzDq4g==";
    private final String phonePeBaseString = "upi://pay?mode=02&pa=Q355808006@ybl&purpose=00&mc=0000&pn=PhonePeMerchant&orgid=180001&sign=MEUCIQCgOK+Hp9+axAqSCvciooSDb7vWekq7SxgvHs09NCNwLwIgdgg7FDGQEySDDAuNM6cCKG4V+LvZ4tbTA2ZmCWpo0+Y=";
    private final String bharatPeBaseString = "upi://pay?pa=BHARATPE90718988349@yesbankltd&pn=BharatPe Merchant&cu=INR&tn=Verified Merchant";

    private String amazonPayUpi = "upi://pay?pa=9988890048@okbizaxis&pn=N.K MEDICOS&mc=5912&aid=uGICAgIC3joqcAQ&tr=BCR2DN6TR6SKRMR2";

    private String baseUpiString = googlePayBaseString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //todo set base upi string to some value here
        googlePay = findViewById(R.id.googlePay);
        amountEt = findViewById(R.id.amount);

        //todo set in decimal two digit
        resultTv = findViewById(R.id.transaction_status);
        bhimUpi = findViewById(R.id.bhimUpi);
        payGroup = findViewById(R.id.radioGroup);


        googlePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkRadioGroup();

                if (!isAppInstalled(googlePayPackage)) {
                    Toast.makeText(MainActivity.this, "Google pay not installed ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isUpiReady(googlePayPackage)) {
                    Toast.makeText(MainActivity.this, "Google pay is not upi ready", Toast.LENGTH_SHORT).show();
                    return;
                }

                baseUpiString = baseUpiString + "&am=";
                baseUpiString = baseUpiString + amountEt.getText().toString().trim();

                Log.v(MainActivity.class.getSimpleName(), "base " + baseUpiString);

                Log.v(MainActivity.class.getSimpleName(), "amount " + amountEt.getText().toString());

                Log.v(MainActivity.class.getSimpleName(), "base " + baseUpiString);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(baseUpiString));

                intent.setPackage(googlePayPackage);

                startActivityForResult(intent, UPI_REQUEST_CODE);

            }
        });

        bhimUpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkRadioGroup();

                if (!isAppInstalled(bhimUpiPackage)) {
                    Toast.makeText(MainActivity.this, "Bhim Upi not installed ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isUpiReady(bhimUpiPackage)) {
                    Toast.makeText(MainActivity.this, "Bhim Upi is not upi ready", Toast.LENGTH_SHORT).show();
                    return;
                }

                baseUpiString = baseUpiString + "&am=";
                baseUpiString = baseUpiString + amountEt.getText().toString().trim();

                Log.v(MainActivity.class.getSimpleName(), "base " + baseUpiString);

                Log.v(MainActivity.class.getSimpleName(), "amount " + amountEt.getText().toString());

                Log.v(MainActivity.class.getSimpleName(), "base " + baseUpiString);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(baseUpiString));

                intent.setPackage(bhimUpiPackage);

                startActivityForResult(intent, UPI_REQUEST_CODE);

            }
        });

    }

    private void checkRadioGroup() {
        int selectedId = payGroup.getCheckedRadioButtonId();
        payRadio = findViewById(selectedId);
        switch (payRadio.getText().toString()) {

            case "Google pay":
                baseUpiString = googlePayBaseString;
                break;

            case "PayTm":
                baseUpiString = paytmBaseString;
                break;

            case "PhonePe":
                baseUpiString = phonePeBaseString;
                break;

            case "BharatPe":
                baseUpiString = bharatPeBaseString;
                break;
        }
    }

//    private void initializeQrFormation(String uriString) {
//        MultiFormatWriter writer = new MultiFormatWriter();
//        try {
//            BitMatrix matrix = writer.encode(uriString, BarcodeFormat.QR_CODE, 360, 360);
//            BarcodeEncoder encoder = new BarcodeEncoder();
//            bitmap = encoder.createBitmap(matrix);
//            shareImageandText(bitmap);
//        } catch (WriterException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "problem", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void shareImageandText(Bitmap bitmap) {
//        Uri uri = getmageToShare(bitmap);
//        Log.v(MainActivity.class.getSimpleName(), "shared uri is " + uri.toString());
//        Intent intent = new Intent(Intent.ACTION_SEND);
//
//        //Gpay Package name Payemnt Succesfull
//        String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
//        intent.setData(uri);
//        intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
//        startActivityForResult(intent, TEZ_REQUEST_CODE);
//
//
//        // putting uri of image to be shared
//        intent.putExtra(Intent.EXTRA_STREAM, uri);
//
//        // adding text to share
//        intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image");
//
//
//        // setting type to image
//        intent.setType("image/png");
//
//        // calling startactivity() to share
//        startActivity(Intent.createChooser(intent, "Share Via"));
//
//    }

//    private Uri getmageToShare(Bitmap bitmap) {
//        File imagefolder = new File(getCacheDir(), "images");
//        Uri uri = null;
//        try {
//            imagefolder.mkdirs();
//            File file = new File(imagefolder, "shared_image.png");
//            FileOutputStream outputStream = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
//            outputStream.flush();
//            outputStream.close();
//            uri = FileProvider.getUriForFile(this, "com.ann.shareimage.fileprovider", file);
//        } catch (Exception e) {
//            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//        return uri;
//    }


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPI_REQUEST_CODE) {

            if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                if (data != null) {
                    String trxt = data.getStringExtra("response");
                    Log.d("UPI", "onActivityResult: " + trxt);
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            } else {
                Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList);
            }
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(MainActivity.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                    resultTv.setText("Payment cancelled by user.");
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(MainActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: " + approvalRefNo);
                resultTv.setText("Transaction Successful");
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(MainActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                resultTv.setText("Payment cancelled by user.");
            } else {
                Toast.makeText(MainActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                resultTv.setText("Transaction failed.Please try again");
            }
        } else {
            Toast.makeText(MainActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }
}