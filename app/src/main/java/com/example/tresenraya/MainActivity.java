package com.example.tresenraya;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;


public class MainActivity extends AppCompatActivity {

    private TresEnRaya tablero;
    private TextView casilla;
    private boolean laCasilla;

    @SuppressLint("ResourceType")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tablero = findViewById(R.id.tablero);
        casilla = findViewById(R.id.lblCasilla);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.cancion);
        mediaPlayer.start();


        tablero.setOnCasillaSeleccionadaListener((fila, columna) -> {

        });



    }

}