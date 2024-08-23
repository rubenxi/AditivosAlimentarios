package com.rubenxi.aditivosalimentarios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AditivoActivity extends AppCompatActivity {

    private TextView textView2,textView3,textView4,textView5, emojianimado;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aditivo);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        emojianimado = findViewById(R.id.emojianimado);
        constraintLayout=findViewById(R.id.layoutt);
        View view = this.getWindow().getDecorView();

        Intent intent = getIntent();
        String otrosNombres = intent.getStringExtra("otrosNombres");
        String Es = intent.getStringExtra("Es");
        String nombre = intent.getStringExtra("nombre");
        String nivel = intent.getStringExtra("nivel");
        String enlace = "https://e-aditivos.com/E-"+Es.substring(1);

        String[] nombres = otrosNombres.split(",");

        textView3.setText(Es);

        textView4.setMovementMethod(new ScrollingMovementMethod());

        textView2.setText(nombre);
        textView5.setText(nivel);

        textView3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    textView3.setScaleX(1.1f);
                    textView3.setScaleY(1.1f);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    textView3.setScaleX(1.0f);
                    textView3.setScaleY(1.0f);
                }
                return false;
            }

        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irWeb(enlace);
            }
        });
        textView5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    textView5.setScaleX(1.1f);
                    textView5.setScaleY(1.1f);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    textView5.setScaleX(1.0f);
                    textView5.setScaleY(1.0f);
                }
                return false;
            }

        });
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irWeb(enlace);
            }
        });

        String emoji="";
        if (nivel.contains("Seguro")){
            view.setBackgroundColor(0xFF00A00D);
            textView2.setBackgroundColor(0xFF00A00D);
            getWindow().setNavigationBarColor(0xFF00A00D);
            getWindow().setStatusBarColor(0xFF00A00D);
            emoji="\uD83E\uDD51";
        }
        else if (nivel.contains("Sospechoso")){
            view.setBackgroundColor(0xFFFF6A00);
            textView2.setBackgroundColor(0xFFFF6A00);
            getWindow().setNavigationBarColor(0xFFFF6A00);
            getWindow().setStatusBarColor(0xFFFF6A00);
            emoji="\uD83C\uDF56";
            Toast.makeText(AditivoActivity.this, "Se recomienda revisar la web\nPulsa \"Sospechoso\"", Toast.LENGTH_SHORT).show();
        }
        else if (nivel.contains("Peligroso")){
            view.setBackgroundColor(0xFFFF0000);
            textView2.setBackgroundColor(0xFFFF0000);
            getWindow().setNavigationBarColor(0xFFFF0000);
            getWindow().setStatusBarColor(0xFFFF0000);
            emoji="\uD83C\uDF55";
        }
        else{
            view.setBackgroundColor(0x000000);
            textView2.setBackgroundColor(0xFF81A7FF);
            getWindow().setNavigationBarColor(0xFF81A7FF);
            getWindow().setStatusBarColor(0xFF81A7FF);
            emoji="❓";
        }

        new Animacion(emojianimado,emoji).animacion();


        for (String nom : nombres){
            if (nom.substring(nom.length()-1).contains(".")){
                nom=nom.substring(0,nom.length()-1);
            }
            if (textView4.getText().length()<=1){
                textView4.setText(emoji+" "+nom);

            }
            else{
                textView4.setText(textView4.getText()+"\n"+emoji+" "+nom);

            }

        }
    }
    public void irWeb(String enlace){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(enlace));

        startActivity(intent);
    }
}