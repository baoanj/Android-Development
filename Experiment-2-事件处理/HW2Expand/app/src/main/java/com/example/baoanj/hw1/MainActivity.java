package com.example.baoanj.hw1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by baoanj on 2016/9/22.
 */
public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private TextInputLayout usernameLayout;
    private TextInputLayout passwordLayout;
    private EditText username;
    private EditText password;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        usernameLayout = (TextInputLayout) findViewById(R.id.textInputLayoutName1);
        passwordLayout = (TextInputLayout) findViewById(R.id.textInputLayoutName2);
        username = usernameLayout.getEditText();
        password = passwordLayout.getEditText();
        radioGroup = (RadioGroup) findViewById(R.id.id0);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameLayout.setErrorEnabled(false);
                passwordLayout.setErrorEnabled(false);
                if (TextUtils.isEmpty(username.getText().toString())) {
                    showError(usernameLayout, "用户名不能为空");
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    showError(passwordLayout, "密码不能为空");
                } else if ("Android".equals(username.getText().toString()) &&
                           "123456".equals(password.getText().toString())) {
                    showSnackbar(view, "登录成功");
                } else {
                    showSnackbar(view, "登录失败");
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedRadioButtonId);
                String radioString = checkedRadioButton.getText().toString() + "身份注册功能尚未开启";
                showSnackbar(view, radioString);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                String radioString = checkedRadioButton.getText().toString() + "身份被选中";
                showSnackbar(group, radioString);
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEditText(s.toString(), usernameLayout);
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEditText(s.toString(), passwordLayout);
            }
        });
    }

    private void showSnackbar(View view, String string) {
        Snackbar.make(view, string, Snackbar.LENGTH_SHORT)
                .setAction("按钮", new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.primaryGreen))
                .setDuration(3000)
                .show();
    }

    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(error);
    }

    private boolean checkEditText(CharSequence name, TextInputLayout textInputLayout) {
        if (!TextUtils.isEmpty(name)) {
            textInputLayout.setError(null);
        }
        return true;
    }

}
