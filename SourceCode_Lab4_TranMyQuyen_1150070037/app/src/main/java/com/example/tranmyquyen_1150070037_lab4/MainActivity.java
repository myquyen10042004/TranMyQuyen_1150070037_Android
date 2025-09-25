package com.example.tranmyquyen_1150070037_lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

       //Ánh xạ qua view tìm id của 2 button HelloWorld và Register
        Button btnHelloWorld = (Button) findViewById(R.id.btnHelloWorld);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        //sử dung phương thức setOnClickListener và Intent để chuyển màn hình
        btnHelloWorld.setOnClickListener(v -> startActivity(new Intent(this, HelloActivity.class)));
        btnRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }
}