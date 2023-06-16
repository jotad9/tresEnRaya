package com.example.tresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Portada extends AppCompatActivity {
    private Button boton;
    private TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);
        boton=findViewById(R.id.button);
        titulo=findViewById(R.id.textView);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Portada.this,MainActivity.class);

                /*Iniciamos el metodo con la opción StartActivity*/

                startActivity(intent);

                finish();
            }
        });
    }
}