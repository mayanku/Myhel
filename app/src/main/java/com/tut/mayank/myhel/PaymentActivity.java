package com.tut.mayank.myhel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.content.ContentValues.TAG;

public class PaymentActivity extends AppCompatActivity {
    WebView webviewPayment;
    WebView mwebview;
    TextView txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        webviewPayment = (WebView) findViewById(R.id.webView1);
        webviewPayment.getSettings().setJavaScriptEnabled(true);
        webviewPayment.getSettings().setDomStorageEnabled(true);
        webviewPayment.getSettings().setLoadWithOverviewMode(true);
        webviewPayment.getSettings().setUseWideViewPort(true);
        webviewPayment.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webviewPayment.getSettings().setSupportMultipleWindows(true);
        webviewPayment.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webviewPayment.addJavascriptInterface(new PayUJavaScriptInterface(), "PayUMoney");

        StringBuilder url_s = new StringBuilder();

        url_s.append("https://test.payu.in/_payment");

        Log.e(TAG, "call url " + url_s);
        //	webviewPayment.postUrl(url_s.toString(),EncodingUtils.getBytes(getPostString(), "utf-8"));

        webviewPayment.postUrl(url_s.toString(), getPostString().getBytes(Charset.forName("UTF-8")));

        webviewPayment.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @SuppressWarnings("unused")
            public void onReceivedSslError(WebView view, SslErrorHandler handler) {
                Log.e("Error", "Exception caught!");
                handler.cancel();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
    }

    private final class PayUJavaScriptInterface {
        PayUJavaScriptInterface() {
        }

        @JavascriptInterface
        public void success(long id, final String paymentId) {
            runOnUiThread(new Runnable() {
                public void run() {

                    Toast.makeText(PaymentActivity.this, "Status is txn is success " + " payment id is " + paymentId,Toast.LENGTH_LONG).show();
                    //String str="Status is txn is success "+" payment id is "+paymentId;
                    // new MainActivity().writeStatus(str);

                    TextView txtview;
                    txtview = (TextView) findViewById(R.id.textView1);
                    txtview.setText("Status is txn is success " + " payment id is " + paymentId);

                }
            });
        }

        @JavascriptInterface
        public void failure(long id, final String paymentId) {
            runOnUiThread(new Runnable() {
                public void run() {

                    Toast.makeText(PaymentActivity.this, "Status is txn is failed " + " payment id is " + paymentId,Toast.LENGTH_SHORT).show();
                    //String str="Status is txn is failed "+" payment id is "+paymentId;
                    // new MainActivity().writeStatus(str);

                    TextView txtview;
                    txtview = (TextView) findViewById(R.id.textView1);
                    txtview.setText("Status is txn is failed " + " payment id is " + paymentId);

                }
            });
        }

    }




    private String getPostString()
    {
        String key  = "dRQuiA";
        String salt  = "teEkuVg2";
        String txnid = "TXN_1";
        String amount = "200";
        String firstname = "test";
        String email = "test@gmail.com";
        String productInfo = "Product1";

        StringBuilder post = new StringBuilder();
        post.append("key=");
        post.append(key);
        post.append("&");
        post.append("txnid=");
        post.append(txnid);
        post.append("&");
        post.append("amount=");
        post.append(amount);
        post.append("&");
        post.append("productinfo=");
        post.append(productInfo);
        post.append("&");
        post.append("firstname=");
        post.append(firstname);
        post.append("&");
        post.append("email=");
        post.append(email);
        post.append("&");
        post.append("phone=");
        post.append("9999999999");
        post.append("&");
        post.append("surl=");
        post.append("https://www.payumoney.com/mobileapp/payumoney/success.php");
        //https://www.payumoney.com/mobileapp/payumoney/success.php
        //https://www.payumoney.com/mobileapp/payumoney/failure.php
        post.append("&");
        post.append("furl=");
        post.append("https://www.payumoney.com/mobileapp/payumoney/failure.php");
        post.append("&");

        StringBuilder checkSumStr = new StringBuilder();
		/* =sha512(key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||salt) */
        MessageDigest digest=null;
        String hash;
        try {
            digest = MessageDigest.getInstance("SHA-512");// MessageDigest.getInstance("SHA-256");

            checkSumStr.append(key);
            checkSumStr.append("|");
            checkSumStr.append(txnid);
            checkSumStr.append("|");
            checkSumStr.append(amount);
            checkSumStr.append("|");
            checkSumStr.append(productInfo);
            checkSumStr.append("|");
            checkSumStr.append(firstname);
            checkSumStr.append("|");
            checkSumStr.append(email);
            checkSumStr.append("|||||||||||");
            checkSumStr.append(salt);

            digest.update(checkSumStr.toString().getBytes());

            hash = bytesToHexString(digest.digest());
            post.append("hash=");
            post.append(hash);
            post.append("&");
            Log.i(TAG, "SHA result is " + hash);
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        post.append("service_provider=");
        post.append("payu_paisa");
        return post.toString();
    }

    private JSONObject getProductInfo()
    {
        try {
            //create payment part object
            JSONObject productInfo = new JSONObject();

            JSONObject jsonPaymentPart = new JSONObject();
            jsonPaymentPart.put("name", "TapFood");
            jsonPaymentPart.put("description", "Lunchcombo");
            jsonPaymentPart.put("value", "500");
            jsonPaymentPart.put("isRequired", "true");
            jsonPaymentPart.put("settlementEvent", "EmailConfirmation");

            //create payment part array
            JSONArray jsonPaymentPartsArr = new JSONArray();
            jsonPaymentPartsArr.put(jsonPaymentPart);

            //paymentIdentifiers
            JSONObject jsonPaymentIdent = new JSONObject();
            jsonPaymentIdent.put("field", "CompletionDate");
            jsonPaymentIdent.put("value", "31/10/2012");

            //create payment part array
            JSONArray jsonPaymentIdentArr = new JSONArray();
            jsonPaymentIdentArr.put(jsonPaymentIdent);

            productInfo.put("paymentParts", jsonPaymentPartsArr);
            productInfo.put("paymentIdentifiers", jsonPaymentIdentArr);

            Log.e(TAG, "product Info = " + productInfo.toString());
            return productInfo;


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');

            }
            sb.append(hex);
        }
        return sb.toString();
    }

    }


