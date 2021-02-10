package com.example.chatsockets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.example.chatsockets.MainActivity.nickname;

public class Chat extends AppCompatActivity {
    private boolean run=true;
    private Thread listeningThread;
    private Socket client;
    private DataInputStream flujoE;
    private DataOutputStream flujoS;
    private Button btSalir;
    private Button btSend;
    private TextView tvChat;
    private EditText etMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btSend = findViewById(R.id.btSend);
        tvChat = findViewById(R.id.tvChat);
        etMensaje = findViewById(R.id.etMensaje);
        btSalir = findViewById(R.id.btSalir);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
        thread.start();

    }



    private void init() {
        try {

            client = new Socket("10.0.2.2", 5000);
            flujoE = new DataInputStream(client.getInputStream());
            flujoS = new DataOutputStream(client.getOutputStream());

            listeningThread = new Thread(){
                @Override
                public void run(){
                    try {
                        flujoS.writeUTF(nickname);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while(run){
                        String text;
                        try {
                            text = flujoE.readUTF();
                            tvChat.append(text+"\n");
                        } catch (IOException ex) {
                            System.out.println("Run: "+ex.getLocalizedMessage());
                        }
                    }
                }
            };
            listeningThread.start();
        } catch (IOException ex) {
            System.out.println("startClient: "+ex.getLocalizedMessage());
        }


        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String text = etMensaje.getText().toString();
                        try {
                            if(text.length()!=0){
                                flujoS.writeUTF(text);
                            }

                        } catch (IOException ex) {
                            System.out.println("btSend: "+ex.getLocalizedMessage());
                        }

                        etMensaje.setText("");
                    }
                });
                thread.start();


            }
        });


        btSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    flujoE.close();
                    flujoS.close();
                    Intent intent = new Intent(Chat.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(Chat.this, "Saliste del chat", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }




}