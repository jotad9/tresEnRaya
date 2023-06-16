package com.example.tresenraya;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;


public class TresEnRaya extends View {
    int filaFinal = 0;

    public int getFilaFinal() {

        return filaFinal;
    }

    public int getColumnaFinal() {

        return columnaFinal;
    }

    int columnaFinal = 0;

    static class ficha {
        private static final int VACIA = 0;
        private static final int FICHA_O = 1;
        private static final int FICHA_X = 2;

        public int getVACIA() {
            return VACIA;
        }

        public int getFICHA_O() {

            return FICHA_O;
        }

        public int getFICHA_X() {

            return FICHA_X;
        }
    }

    private int[][] tablero = new int[3][3];
    private int fichaActiva = ficha.FICHA_X;
    private int xCOlor = Color.BLACK;
    private int oCOlor = Color.BLUE;
    private Paint pBorde = darBorde();
    private Paint pMarcaX = darMarcas();
    private Paint pMarcaO = darMarcas();




    private OnCasillaSeleccionadaListener listener = null;

    public TresEnRaya(Context context) {
        super(context);
    }

    public TresEnRaya(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TresEnRaya, 0, 0);

    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int ancho = calcularAncho(widthMeasureSpec);
        int alto = calcularAlto(heightMeasureSpec);
        if (ancho < alto)
            alto = ancho;
        else
            ancho = alto;

        setMeasuredDimension(ancho, alto);
    }
    protected Paint darMarcas() {
        Paint p2 = new Paint();
        p2.setStyle(Paint.Style.STROKE);
        p2.setStrokeWidth(10f);
        return p2;
    }
    protected Paint darBorde() {
        Paint p2 = new Paint();
        p2.setStyle(Paint.Style.STROKE);
        p2.setStrokeWidth(10f);
        return p2;
    }

    private int calcularAlto(int limitesSpec) {
        int res = 100; //Alto por defecto

        int modo = MeasureSpec.getMode(limitesSpec);
        int limite = MeasureSpec.getSize(limitesSpec);

        if (modo == MeasureSpec.AT_MOST) {
            res = limite;
        } else if (modo == MeasureSpec.EXACTLY) {
            res = limite;
        }

        return res;
    }

    private int calcularAncho(int limitesSpec) {
        int res = 100; //Ancho por defecto

        int modo = MeasureSpec.getMode(limitesSpec);
        int limite = MeasureSpec.getSize(limitesSpec);

        if (modo == MeasureSpec.AT_MOST) {
            res = limite;
        } else if (modo == MeasureSpec.EXACTLY) {
            res = limite;
        }

        return res;
    }

    void limpiar() {
       for (int x=0;x<tablero.length;x++){
           for (int y=0;y<tablero[0].length;y++){
               tablero[x][y]=0;
           }
       }
    }
    public void limpiarEntero(){
        int contador=0;
        AlertDialog.Builder laAlerta=new AlertDialog.Builder(getContext());
        laAlerta.setMessage("!!NINGÚN GANADOR!!");
        laAlerta.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        for (int x=0;x<tablero.length;x++){
            for (int y=0;y<tablero[0].length;y++){
                if(tablero[x][y]==ficha.FICHA_O || tablero[x][y]==ficha.FICHA_X ){
                    contador++;
                }
                if(contador==9){
                    for (int z=0;z<tablero.length;z++){
                        for(int j=0;j<tablero[0].length;j++){
                            tablero[z][j]=0;
                        }
                    }
                    laAlerta.show();
                }

            }
        }
    }

    public int getCasilla(int fila, int columna) {
        return tablero[fila][columna];
    }

    public void setCasilla(int fila, int columna, int valor) {
        tablero[fila][columna] = valor;
    }

    public void alternarFicha() {
        if (fichaActiva == ficha.FICHA_O) {
            fichaActiva = ficha.FICHA_X;
        } else {
            fichaActiva = ficha.FICHA_O;
        }
    }

    public void onDraw(@NonNull Canvas canva) {


        int alto = getMeasuredHeight();
        int ancho = getMeasuredWidth();
        canva.drawLine(Float.parseFloat(String.valueOf(ancho / 3)),
                0f, Float.parseFloat(String.valueOf(ancho / 3)),
                Float.parseFloat(String.valueOf(alto)), pBorde);

        canva.drawLine(
                Float.parseFloat(String.valueOf((2 * ancho / 3)))
                , 0f,
                Float.parseFloat(String.valueOf((2 * ancho / 3))),
                Float.parseFloat(String.valueOf(alto)), pBorde
        );

        canva.drawLine(0f, Float.parseFloat(String.valueOf((alto / 3))), Float.parseFloat(String.valueOf(ancho)), Float.parseFloat(String.valueOf(alto / 3)), pBorde);
        canva.drawLine(
                0f,
                Float.parseFloat(String.valueOf((2 * alto / 3))),
                Float.parseFloat(String.valueOf(ancho)),
                Float.parseFloat(String.valueOf(2 * alto / 3)), pBorde
        );
        canva.drawRect(0f, 0f, Float.parseFloat(String.valueOf(ancho)), Float.parseFloat(String.valueOf(alto)), pBorde);
        pMarcaO.setColor( oCOlor);
        pMarcaX.setColor(xCOlor);
        for (int fil = 0; fil < 3; fil++) {
            for (int col = 0; col < tablero[0].length; col++) {
                if (tablero[fil][col] == ficha.FICHA_X) {
                    //Cruz
                    canva.drawLine(
                            col * (ancho / 3) + ancho / 3 * 0.1f,
                            fil * (alto / 3) + alto / 3 * 0.1f,
                            col * (ancho / 3) + ancho / 3 * 0.9f,
                            fil * (alto / 3) + alto / 3 * 0.9f,
                            pMarcaX
                    );
                    canva.drawLine(
                            col * (ancho / 3) + ancho / 3 * 0.1f,
                            fil * (alto / 3) + alto / 3 * 0.9f,
                            col * (ancho / 3) + ancho / 3 * 0.9f,
                            fil * (alto / 3) + alto / 3 * 0.1f,
                            pMarcaX
                    );
                } else if (tablero[fil][col] == ficha.FICHA_O) {
                    //Circulo
                    canva.drawCircle(
                            Float.parseFloat(String.valueOf((col * (ancho / 3) + ancho / 6)))
                            ,
                            Float.parseFloat(String.valueOf((fil * (alto / 3) + alto / 6))),
                            ancho / 6 * 0.8f, pMarcaO
                    );
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int fil = (int) (event.getY() / Integer.parseInt(String.valueOf(getMeasuredHeight() / 3)));
        int col = (int) (event.getX() / Integer.parseInt(String.valueOf(getMeasuredWidth() / 3)));

        //Actualizamos el tablero
        if(tablero[(int)fil][(int)col]==ficha.VACIA){
            alternarFicha();
            tablero[((int) fil)][((int) col)] = fichaActiva;

            //Lanzamos el evento de pulsación
            listener.onCasillaSeleccionada(fil,col);

            //Refrescamos el control
            this.invalidate();
            ganador(fil,col);
            limpiarEntero();

        }

        return super.onTouchEvent(event);
    }

    public void setOnCasillaSeleccionadaListener(OnCasillaSeleccionadaListener listener) {
        this.listener = listener;
    }

    public void setOnCasillaSeleccionadaListener(int fila, int columna) {
        listener = new OnCasillaSeleccionadaListener() {

            @Override
            public void onCasillaSeleccionada(int fila, int columna) {
                if(tablero[fila][columna]==ficha.VACIA){
                    seleccion(fila, columna);

                }

            }


        };
    }

    ;

    protected void seleccion(int fila, int columna) {
        filaFinal = fila;
        columnaFinal = columna;
    }
    public void ganador(int fila,int columna){
        AlertDialog.Builder laAlerta=new AlertDialog.Builder(getContext());
        laAlerta.setMessage("!!HAS GANADO!!");
            laAlerta.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }

        });
        fila=0;
        columna=0;
        //compruebo todas las maneras posibles de ganar con las dos fichas
        if(tablero[fila][columna]==ficha.FICHA_X){
            if(tablero[fila+1][columna]==ficha.FICHA_X){
                if(tablero[fila+2][columna]==ficha.FICHA_X){
                    laAlerta.show();
                    limpiar();

                }
            }
        }
        if(tablero[fila][columna+1]==ficha.FICHA_X){
            if(tablero[fila+1][columna+1]==ficha.FICHA_X){
                if(tablero[fila+2][columna+1]==ficha.FICHA_X){
                    laAlerta.show();
                    limpiar();

                }
            }
        }
        if(tablero[fila][columna+2]==ficha.FICHA_X){
            if(tablero[fila+1][columna+2]==ficha.FICHA_X){
                if(tablero[fila+2][columna+2]==ficha.FICHA_X){
                    laAlerta.show();
                    limpiar();

                }
            }
        }

        if(tablero[fila][columna]==ficha.FICHA_X){
            if(tablero[fila][columna+1]==ficha.FICHA_X){
                if(tablero[fila][columna+2]==ficha.FICHA_X){
                    laAlerta.show();
                    limpiar();

                }
            }
        }

        if(tablero[fila+1][columna]==ficha.FICHA_X){
            if(tablero[fila+1][columna+1]==ficha.FICHA_X){
                if(tablero[fila+1][columna+2]==ficha.FICHA_X){
                    laAlerta.show();
                    limpiar();

                }
            }
        }
        if(tablero[fila+2][columna]==ficha.FICHA_X){
            if(tablero[fila+2][columna+1]==ficha.FICHA_X){
                if(tablero[fila+2][columna+2]==ficha.FICHA_X){
                    laAlerta.show();
                    limpiar();

                }
            }
        }
        if(tablero[fila][columna]==ficha.FICHA_X){
            if(tablero[fila+1][columna+1]==ficha.FICHA_X){
                if(tablero[fila+2][columna+2]==ficha.FICHA_X){
                    laAlerta.show();
                    limpiar();

                }
            }
        }
        if(tablero[fila+2][columna]==ficha.FICHA_X){
            if(tablero[fila+1][columna+1]==ficha.FICHA_X){
                if(tablero[fila][columna+2]==ficha.FICHA_X){
                    laAlerta.show();
                    limpiar();

                }
            }
        }










        if(tablero[fila][columna]==ficha.FICHA_O){
            if(tablero[fila+1][columna]==ficha.FICHA_O){
                if(tablero[fila+2][columna]==ficha.FICHA_O){
                    laAlerta.show();
                    limpiar();

                }
            }
        }

        if(tablero[fila][columna+1]==ficha.FICHA_O){
            if(tablero[fila+1][columna+1]==ficha.FICHA_O){
                if(tablero[fila+2][columna+1]==ficha.FICHA_O){
                    laAlerta.show();
                    limpiar();

                }
            }
        }
        if(tablero[fila][columna+2]==ficha.FICHA_O){
            if(tablero[fila+1][columna+2]==ficha.FICHA_O){
                if(tablero[fila+2][columna+2]==ficha.FICHA_O){
                    laAlerta.show();
                    limpiar();

                }
            }
        }

        if(tablero[fila][columna]==ficha.FICHA_O){
            if(tablero[fila][columna+1]==ficha.FICHA_O){
                if(tablero[fila][columna+2]==ficha.FICHA_O){
                    laAlerta.show();
                    limpiar();

                }
            }
        }

        if(tablero[fila+1][columna]==ficha.FICHA_O){
            if(tablero[fila+1][columna+1]==ficha.FICHA_O){
                if(tablero[fila+1][columna+2]==ficha.FICHA_O){
                    laAlerta.show();
                    limpiar();

                }
            }
        }
        if(tablero[fila+2][columna]==ficha.FICHA_O){
            if(tablero[fila+2][columna+1]==ficha.FICHA_O){
                if(tablero[fila+2][columna+2]==ficha.FICHA_O){
                    laAlerta.show();
                    limpiar();

                }
            }
        }
        if(tablero[fila][columna]==ficha.FICHA_O){
            if(tablero[fila+1][columna+1]==ficha.FICHA_O){
                if(tablero[fila+2][columna+2]==ficha.FICHA_O){
                    laAlerta.show();
                    limpiar();

                }
            }
        }
        if(tablero[fila+2][columna]==ficha.FICHA_O){
            if(tablero[fila+1][columna+1]==ficha.FICHA_O){
                if(tablero[fila][columna+2]==ficha.FICHA_O){
                    laAlerta.show();
                    limpiar();

                }
            }
        }


    }

}
