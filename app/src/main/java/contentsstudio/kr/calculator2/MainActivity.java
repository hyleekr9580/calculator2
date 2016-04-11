package contentsstudio.kr.calculator2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditTotal;
    private EditText mEditMoney;
    private EditText mEditVat;
    private Button mButtonClean;

    private String mReault = "";
    private String mReault2 = "0";

    private DecimalFormat mDecimalFormat;
    private static final String TAG = MainActivity.class.getSimpleName();

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //어플종료
        activity = MainActivity.this;

        mEditTotal = (EditText) findViewById(R.id.total_edt);
        mEditMoney = (EditText) findViewById(R.id.money_edt);
        mEditVat = (EditText) findViewById(R.id.vat_edt);

        mButtonClean = (Button) findViewById(R.id.clean_btn);
        mButtonClean.setOnClickListener(this);

        mEditTotal.addTextChangedListener(watcher1);
        mEditMoney.addTextChangedListener(watcher2);
        mEditVat.addTextChangedListener(watcher3);

        mDecimalFormat = new DecimalFormat("###,###");

        mEditTotal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEditMoney.removeTextChangedListener(watcher2);
                    mEditVat.removeTextChangedListener(watcher3);
                } else {
                    mEditMoney.addTextChangedListener(watcher2);
                    mEditVat.addTextChangedListener(watcher3);
                }
            }
        });

        mEditMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEditTotal.removeTextChangedListener(watcher1);
                    mEditVat.removeTextChangedListener(watcher3);

                } else {
                    mEditTotal.addTextChangedListener(watcher1);
                    mEditVat.addTextChangedListener(watcher3);
                }
            }
        });

        mEditVat.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEditTotal.removeTextChangedListener(watcher1);
                    mEditMoney.removeTextChangedListener(watcher2);
                } else {
                    mEditTotal.addTextChangedListener(watcher1);
                    mEditMoney.addTextChangedListener(watcher2);
                }
            }
        });
    }

    //  합계를 입력 하였을때 공급가, 부가세 자동변경
    TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // 입력하기 전에
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // 입력되는 텍스트에 변화가 있을 때
            double num, res, vat;

            if (!s.toString().equals(mReault)) {
                mReault = makeStringComma(s.toString().replaceAll(",", ""));
                mEditTotal.setText(mReault);
                Editable e = mEditTotal.getText();
                Selection.setSelection(e, mReault.length());

                if (mEditTotal.getText().length() > 13 || mEditTotal.getText().length() < 1) {
                    mEditTotal.setText(mReault2);
                } else {
                    num = Double.parseDouble(mEditTotal.getText().toString().replace(",", ""));

                    //  공급가 계산
                    res = num / 1.10d;
                    //  부가세 계산
                    vat = num - res;

                    //  공급가 보여주기
                    mEditMoney.setText("" + mDecimalFormat.format(res));
                    //  부가세 보여주기
                    mEditVat.setText("" + mDecimalFormat.format(vat));
                }
            } else {
                mEditMoney.setText(mReault2);
                mEditVat.setText(mReault2);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // 입력이 끝났을 때
        }
    };


    //  공급가를 입력 하였을때 합계, 부가세 자동변경
    TextWatcher watcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            double num, res, vat;
            if (!s.toString().equals(mReault)) {

                mReault = makeStringComma(s.toString().replaceAll(",", ""));
                mEditMoney.setText(mReault);
                Editable e = mEditMoney.getText();
                Selection.setSelection(e, mReault.length());

                if (mEditMoney.getText().length() > 13 || mEditMoney.getText().length() < 1) {
                    mEditMoney.setText(mReault2);
                } else {
                    num = Double.parseDouble(mEditMoney.getText().toString().replace(",", ""));

                    //  부가세 계산
                    vat = num * 0.10d;
                    //  합계 계산
                    res = num + vat;

                    //  합계 보여주기
                    mEditTotal.setText("" + mDecimalFormat.format(res));
                    //  부가세 보여주기
                    mEditVat.setText("" + mDecimalFormat.format(vat));
                }
            } else {
                mEditTotal.setText(mReault2);
                mEditVat.setText(mReault2);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    //  부가세를 입력 하였을때 합계, 공급가 자동변경

    TextWatcher watcher3 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            double num, res, total;
            if (!s.toString().equals(mReault)) {

                mReault = makeStringComma(s.toString().replaceAll(",", ""));
                mEditVat.setText(mReault);
                Editable e = mEditVat.getText();
                Selection.setSelection(e, mReault.length());

                if (mEditVat.getText().length() > 11 || mEditVat.getText().length() < 1) {
                    mEditVat.setText(mReault2);
                } else {
                    num = Double.parseDouble(mEditVat.getText().toString().replace(",", ""));

                    //  공급가 계산
                    res = num * 10.0d;
                    //  합계 계산
                    total = num + res;

                    //  공급가 보여주기
                    mEditMoney.setText("" + mDecimalFormat.format(res));
                    //  합계 보여주기
                    mEditTotal.setText("" + mDecimalFormat.format(total));
                }
            } else {
                mEditMoney.setText(mReault2);
                mEditTotal.setText(mReault2);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    //  클릭 이벤트
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.clean_btn:
                Toast.makeText(MainActivity.this, "모든 항목이 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                mEditTotal.setText(mReault2);
                mEditMoney.setText(mReault2);
                mEditVat.setText(mReault2);
                break;
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, Pop_Ad_Viewer.class);
            startActivity(intent);
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



