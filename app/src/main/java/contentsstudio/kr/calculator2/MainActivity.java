package contentsstudio.kr.calculator2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mEditTotal;
    private EditText mEditMoney;
    private EditText mEditVat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTotal = (EditText) findViewById(R.id.total_edt);
        mEditMoney = (EditText) findViewById(R.id.money_edt);
        mEditVat = (EditText) findViewById(R.id.vat_edt);


        mEditTotal.addTextChangedListener(watcher1);
        mEditMoney.addTextChangedListener(watcher2);

        mEditTotal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEditMoney.removeTextChangedListener(watcher2);
                } else {
                    mEditMoney.addTextChangedListener(watcher2);
                }
            }
        });

        mEditMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEditTotal.removeTextChangedListener(watcher1);
                } else {
                    mEditTotal.addTextChangedListener(watcher1);
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

            float num1, res, vat;

            num1 = Float.parseFloat(mEditTotal.getText().toString());

            //  공급가 계산
            res = num1 / 1.1F;
            //  부가세 계산
            vat = num1 - res;

            //  공급가 보여주기
            mEditMoney.setText(String.format("%.1f", res));
            //  부가세 보여주기
            mEditVat.setText(String.format("%.1f", vat));

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
            float num2, res, vat;

            num2 = Float.parseFloat(mEditMoney.getText().toString());

            //  부가세 계산
            vat = num2 * 0.1F;
            //  합계 계산
            res = num2 + vat;


            //  합계 보여주기
            mEditTotal.setText(String.format("%.1f", res));
            //  부가세 보여주기
            mEditVat.setText(String.format("%.1f", vat));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}

