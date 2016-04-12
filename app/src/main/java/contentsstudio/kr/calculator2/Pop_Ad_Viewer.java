package contentsstudio.kr.calculator2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

/**
 * Created by hoyoung on 2016-04-06.
 */
public class Pop_Ad_Viewer extends Activity {
    private WebView mWebView;
    private Button btnCancel;
    private Button btnClose;


    MainActivity Pop_Ad_Viewer = (MainActivity) MainActivity.activity;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.pop_ad_view);

        mWebView = (WebView) findViewById(R.id.webview);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnClose = (Button) findViewById(R.id.btnClose);

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Pop_Ad_Viewer.this.finish();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Pop_Ad_Viewer.this.finish();
                //close();
                Pop_Ad_Viewer.finish();
                finish();
//                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });


        // 홈페이지 지정
        mWebView.loadUrl("http://suwonsmartapp.iptime.org/test/lhy/ad.html");
        // WebViewClient 지정
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                finish();
                return true;
            }
        });

    }

    public void noTouch() {
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            finish();
        } else {
            noTouch();
        }
        return true;
    }
}