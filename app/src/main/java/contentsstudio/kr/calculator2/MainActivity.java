package contentsstudio.kr.calculator2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditTotal;
    private EditText mEditMoney;
    private EditText mEditVat;
    private Button mButtonClean;

    private String mReault = "";
    private final String DEFAULT_RESULT = "0";

    private DecimalFormat mDecimalFormat;
    private static final String TAG = MainActivity.class.getSimpleName();

    public static Activity activity;

    private List<EditText> mEditTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //어플종료
//        activity = MainActivity.this;

        mEditTotal = (EditText) findViewById(R.id.total_edt);
        mEditMoney = (EditText) findViewById(R.id.money_edt);
        mEditVat = (EditText) findViewById(R.id.vat_edt);

        mEditTexts = new ArrayList<>();
        mEditTexts.add(mEditMoney);
        mEditTexts.add(mEditTotal);
        mEditTexts.add(mEditVat);

        mButtonClean = (Button) findViewById(R.id.clean_btn);
        mButtonClean.setOnClickListener(this);

        mEditTotal.addTextChangedListener(watcher);
        mEditMoney.addTextChangedListener(watcher);
        mEditVat.addTextChangedListener(watcher);

        mDecimalFormat = new DecimalFormat("###,###");

        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                for (EditText e : mEditTexts) {
                    if (!e.equals(v)) {
                        if (hasFocus) {
                            e.removeTextChangedListener(watcher);
                        } else {
                            e.addTextChangedListener(watcher);
                        }
                    }
                }
            }
        };
        mEditTotal.setOnFocusChangeListener(focusChangeListener);
        mEditMoney.setOnFocusChangeListener(focusChangeListener);
        mEditVat.setOnFocusChangeListener(focusChangeListener);
    }


    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // 현재 EditText
            EditText currentEditText = mEditTexts.get(0);
            for (EditText e : mEditTexts) {
                if (e.isFocused()) {
                    currentEditText = e;
                }
            }

            if (!s.toString().equals(mReault)) {

                mReault = makeStringComma(s.toString().replaceAll(",", ""));
                currentEditText.setText(mReault);
                Editable e = currentEditText.getText();
                Selection.setSelection(e, mReault.length());

                if (currentEditText.getText().length() > 13 || currentEditText.getText().length() < 1) {
                    currentEditText.setText(DEFAULT_RESULT);
                } else {
                    calculate(currentEditText);

                }
            } else {
                // 나머지 EditText 모두 0으로 설정
                for (EditText e : mEditTexts) {
                    if (!e.isFocused()) {
                        e.setText(DEFAULT_RESULT);
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void calculate(EditText currentEditText) {
        double num, res, vat, total;
        num = Double.parseDouble(currentEditText.getText().toString().replace(",", ""));

        switch (currentEditText.getId()) {
            case R.id.total_edt:
                //  공급가 계산
                res = num / 1.10d;
                //  부가세 계산
                vat = num - res;

                //  공급가 보여주기
                mEditMoney.setText("" + mDecimalFormat.format(res));
                //  부가세 보여주기
                mEditVat.setText("" + mDecimalFormat.format(vat));
                break;
            case R.id.money_edt:
                //  부가세 계산
                vat = num * 0.10d;
                //  합계 계산
                res = num + vat;

                //  합계 보여주기
                mEditTotal.setText("" + mDecimalFormat.format(res));
                //  부가세 보여주기
                mEditVat.setText("" + mDecimalFormat.format(vat));

                break;
            case R.id.vat_edt:
                //  공급가 계산
                res = num * 10.0d;
                //  합계 계산
                total = num + res;

                //  공급가 보여주기
                mEditMoney.setText("" + mDecimalFormat.format(res));
                //  합계 보여주기
                mEditTotal.setText("" + mDecimalFormat.format(total));
                break;
        }
    }

    //  클릭 이벤트
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.clean_btn:
                Toast.makeText(MainActivity.this, "모든 항목이 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                mEditTotal.setText(DEFAULT_RESULT);
                mEditMoney.setText(DEFAULT_RESULT);
                mEditVat.setText(DEFAULT_RESULT);
                break;
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
//            Intent intent = new Intent(this, Pop_Ad_Viewer.class);
//            startActivity(intent);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View root = LayoutInflater.from(this).inflate(R.layout.pop_ad_view, null, false);
            root.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            WebView webView = (WebView) root.findViewById(R.id.webview);
            // 홈페이지 지정
            webView.loadUrl("http://suwonsmartapp.iptime.org/test/lhy/ad.html");
            // WebViewClient 지정
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    finish();
                    return true;
                }
            });
            builder.setView(root);
//            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//            builder.setNegativeButton("취소", null);
            builder.show();
            return true;
        }
        return super.onKeyDown(keycode, event);
    }


    // 옵션메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "별점구걸(동해)");
        menu.add(0, 2, 0, "관리자문의 URL(학원)");
        menu.add(0, 3, 0, "이메일");
        return true;
    }

    //옵션메뉴설정
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                star();
                break;
            case 2:
                admin();
                break;
            case 3:
                email();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void email() {
        Toast.makeText(MainActivity.this, "이메일", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, EmailActivity.class);
        startActivity(intent);
    }

    private void star() {
        Toast.makeText(MainActivity.this, "별점", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.hyunseo.donghae.maplestoryexpmanager"));
        startActivity(intent);
    }

    private void admin() {
        Toast.makeText(MainActivity.this, "관리자", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.suwonsmartapp.com/qna"));
        startActivity(intent);

    }

    protected String makeStringComma(String str) {
        if (str.length() == 0)
            return "";
        long value = Long.parseLong(str);
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(value);
    }
}



