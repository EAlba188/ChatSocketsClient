package com.example.chatsockets;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private Button btEntrar;
    private EditText etNick;
    public static String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNick = findViewById(R.id.etNick);
        btEntrar = findViewById(R.id.btEntrar);


        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNick.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Introduce un nick", Toast.LENGTH_SHORT).show();
                }else{
                    nickname = etNick.getText().toString();
                    Intent intent = new Intent(MainActivity.this, Chat.class);
                    startActivity(intent);
                }
            }
        });

    }
















}