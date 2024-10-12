package com.example.onlineshopp.ActivityLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.onlineshopp.R;
import com.example.onlineshopp.databinding.ActivityLoginBinding;
import com.google.android.material.textfield.TextInputLayout;

public class Activity_login extends AppCompatActivity {
    String TAG=Activity_login.class.getName();
    ActivityLoginBinding binding;
    TextInputLayout pwdtxtLayout,usertxtLayout;
    Button btnlogin;
    TextView signUpText;
    boolean canLogin=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setMapping();

        eventCompment();
    }

    @SuppressLint("ResourceAsColor")
    private void eventCompment() {
        String pwd=pwdtxtLayout.getEditText().getText().toString();
        String user=usertxtLayout.getEditText().getText().toString();
        if(pwd.isEmpty() || user.isEmpty()){
            btnlogin.setEnabled(false);
        }


        binding.passwordLogin.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validatePassword(editable.toString());
                enabelbtnLogin(user,pwd);
            }
        });



        //Listent Click
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Activity_login.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    private void setMapping(){
        usertxtLayout=binding.userNameLogin;
        pwdtxtLayout=binding.passwordLogin;
        btnlogin=binding.loginButton;
        signUpText=binding.signUpText;
    }
    private void validatePassword(String password) {

        // Kiểm tra điều kiện mật khẩu
        if (password.length() < 5 || !hasUpperCase(password)) {
            // Hiện thị biểu tượng lỗi
            pwdtxtLayout.setError(getString(R.string.invalid_password));
            canLogin=false;
        } else {
            // Xóa thông báo lỗi
            pwdtxtLayout.setError(null);
            canLogin=true;
        }
    }
    @SuppressLint("ResourceAsColor")
    void enabelbtnLogin(String user,String pwd){
        if (!canLogin && (user.isEmpty() || pwd.isEmpty()) ){
            btnlogin.setEnabled(false);
            btnlogin.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrey));
        }else{
            btnlogin.setEnabled(true);
            btnlogin.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            checkLogin();
        }
    }
    private boolean hasUpperCase(String str) {
        // Kiểm tra xem chuỗi có ký tự in hoa không
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private void checkLogin(){
        String user=usertxtLayout.getEditText().getText().toString().trim();
        String pwd=pwdtxtLayout.getEditText().getText().toString().trim();
       btnlogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                if(user.equals("admin") && pwd.equals("H123123")){
                    Log.i(TAG,"Login Successfully"+ user.toString()+"\n"+pwd.toString());
                    Toast.makeText(Activity_login.this,R.string.welcome+ user + "Đã quay trở lại",Toast.LENGTH_LONG);
                    finish();
                }else{
                    Log.d(TAG,"Login Failed !!!!!!!!"+user);
                    Toast.makeText(Activity_login.this,R.string.login_failed,Toast.LENGTH_LONG);
                }
           }
       });
    }
}