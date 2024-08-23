package com.rubenxi.aditivosalimentarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button atras, alante;
    int add=0;
    ArrayList<String> otrosNombresArray;
    ArrayList<String> EsArray;
    ArrayList<String> nombresArray;
    ArrayList<String> nivelesArray;
    private int pagina;
    private ArrayList <String> lineas;
    private InputStream inputStream;
    private TextView textView6;
    private EditText editText;
    private TableLayout tableLayout;
    private Button button;
    private String nivel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout=findViewById(R.id.tableLayout);
        editText = findViewById(R.id.editTextText);
        button=findViewById(R.id.button);
        tableLayout.removeAllViews();
        pagina=0;
        atras=findViewById(R.id.atras);
        alante=findViewById(R.id.alante);

        textView6 = findViewById(R.id.textView6);
        atras.setEnabled(false);
        alante.setEnabled(false);

        try {
            inputStream=getAssets().open("lista.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        lineas = new ArrayList();
        String linea;
        while (true){
            try {
                if (!((linea = bufferedReader.readLine())!=null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            lineas.add(linea);
        }

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    button.setScaleX(1.1f);
                    button.setScaleY(1.1f);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    button.setScaleX(1.0f);
                    button.setScaleY(1.0f);
                }
                return false;
            }

        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                atras.setVisibility(View.INVISIBLE);
                atras.setEnabled(false);
                alante.setVisibility(View.INVISIBLE);
                alante.setEnabled(false);

                buscar();
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event!=null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    atras.setVisibility(View.INVISIBLE);
                    atras.setEnabled(false);
                    alante.setVisibility(View.INVISIBLE);
                    alante.setEnabled(false);
                    buscar();
                }
                return false;
            }
        });

        atras.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    atras.setScaleX(1.1f);
                    atras.setScaleY(1.1f);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    atras.setScaleX(1.0f);
                    atras.setScaleY(1.0f);
                }
                return false;
            }

        });
        alante.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    alante.setScaleX(1.1f);
                    alante.setScaleY(1.1f);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    alante.setScaleX(1.0f);
                    alante.setScaleY(1.0f);
                }
                return false;
            }

        });
    }

    public static String eliminarAcentos(String texto){
        return java.text.Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    public void buscar(){
        Animation animationRotation = new RotateAnimation(0F,360F, Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF,0.5F);

        animationRotation.setDuration(100);
        animationRotation.setInterpolator(new LinearInterpolator());

        animationRotation.setRepeatCount(5);
        animationRotation.setRepeatMode(Animation.RESTART);
        animationRotation.setFillAfter(true);
        button.startAnimation(animationRotation);
        editText = findViewById(R.id.editTextText);
        pagina=0;
        String palabra = editText.getText().toString();
        if (palabra.length()>=1) {

            otrosNombresArray = new ArrayList<>();
            EsArray = new ArrayList<>();
            nombresArray = new ArrayList<>();
            nivelesArray = new ArrayList<>();
            ArrayList<String> resultados = new ArrayList<>();

            int cont = 0;

            for (String frase : lineas) {
                if (frase.contains("XXXXXXXXX")) {
                    cont = 0;
                } else if (cont == 1) {
                    otrosNombresArray.add(frase);
                } else if (cont == 2) {
                    //Espacio
                } else if (cont == 3) {
                    EsArray.add(frase);
                } else if (cont == 4) {
                    if (otrosNombresArray.get(otrosNombresArray.size() - 1).contains("En revisi")) {
                        otrosNombresArray.set(otrosNombresArray.size() - 1, frase);
                        nombresArray.add(frase);
                    } else {
                        otrosNombresArray.set(otrosNombresArray.size() - 1, frase + "," + otrosNombresArray.get(otrosNombresArray.size() - 1));
                        nombresArray.add(frase);
                    }
                } else if (cont == 5) {
                    nivelesArray.add(frase);

                    if ((eliminarAcentos(otrosNombresArray.get(otrosNombresArray.size() - 1).toLowerCase()).contains(eliminarAcentos(palabra.toLowerCase())) || (EsArray.get(EsArray.size() - 1).toUpperCase()).contains(eliminarAcentos(palabra.toUpperCase())))
                            && !resultados.contains(otrosNombresArray.get(otrosNombresArray.size() - 1))) {
                        resultados.add(EsArray.get(EsArray.size() - 1) + "\n" + otrosNombresArray.get(otrosNombresArray.size() - 1));
                    } else {
                        otrosNombresArray.remove(otrosNombresArray.size() - 1);
                        EsArray.remove(EsArray.size() - 1);
                        nombresArray.remove(nombresArray.size() - 1);
                        nivelesArray.remove(nivelesArray.size() - 1);

                    }

                }
                cont++;
            }
            pagina = 0;
            if (resultados.size() == 1) {
                irAditivo(0);
            }
            if (resultados.size() == 0) {
                Toast.makeText(MainActivity.this, "No se han encontrado resultados", Toast.LENGTH_LONG).show();
                tableLayout.removeAllViews();
                pagina=0;
                textView6.setText("");
            } else {
                crearTabla(resultados);
            }
        }
        else{
            tableLayout.removeAllViews();
            textView6.setText("");
        }
    }

    private void crearTabla(ArrayList<String> resultados){
        tableLayout.removeAllViews();
        textView6.setText("Página: "+(int) Math.ceil(pagina+1)+"/"+ (int) Math.ceil((float)resultados.size()/2));
       loop:
       {
           for (int i = 0; i < 3; i++) {
               TableRow fila = new TableRow(getApplicationContext());
               TableLayout.LayoutParams lpFila = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
               lpFila.weight = 1;
               fila.setLayoutParams(lpFila);
               for (int y = 0; y < 1; y++) {
                   Button button = new Button(getApplicationContext());
                   button.setId(View.generateViewId());
                   TableRow.LayoutParams lpButton = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
                   lpButton.weight = 1;
                   button.setLayoutParams(lpButton);
                   button.setBackgroundResource(R.drawable.border_button);
                   button.setTextColor(Color.WHITE);
                   Typeface typeface = Typeface.createFromAsset(getAssets(),"ABeeZee-Regular.otf");
                   button.setTypeface(typeface);
                   button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                   if (i == 2 ) {

                       alante.setEnabled(true);
                       alante.setVisibility(View.VISIBLE);
                       alante.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               alante.setEnabled(false);
                               alante.setVisibility(View.INVISIBLE);
                               atras.setEnabled(true);
                               atras.setVisibility(View.VISIBLE);
                               atras.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       alante.setEnabled(true);
                                       alante.setVisibility(View.VISIBLE);
                                       pagina--;

                                       if (pagina==0) {
                                           atras.setEnabled(false);
                                           atras.setVisibility(View.INVISIBLE);
                                       }
                                           crearTabla(resultados);
                                       //anterior pagina
                                   }
                               });
                               pagina++;
                               crearTabla(resultados);
                               //siguiente pagina
                           }
                       });
                       if (i + pagina +add >= resultados.size()) {
                        alante.setEnabled(false);
                        alante.setVisibility(View.INVISIBLE);
                       }
                           break loop;

                   } else {
                       if (i + pagina +add < resultados.size()) {

                          if (pagina==0){
                              add=0;
                          }
                          else{
                              add=1*pagina;
                          }
                           button.setText(resultados.get(i + pagina+add));

                            nivel=nivelesArray.get(i+pagina+add);

                           if (nivel.contains("Seguro")){
                               button.setText("\uD83E\uDD51 "+button.getText());
                               button.setTextColor(0xFF00A00D);
                           }
                           else if (nivel.contains("Sospechoso")){
                               button.setText("\uD83C\uDF56 "+button.getText());
                               button.setTextColor(0xFFFF6A00);
                           }
                           else if (nivel.contains("Peligroso")){
                               button.setText("\uD83C\uDF55 "+button.getText());
                               button.setTextColor(0xFFFF0000);
                           }
                           else {
                               button.setText("❓ "+button.getText());
                               button.setTextColor(0xFFFFFFFF);
                           }

                           int pos = i;
                           button.setOnTouchListener(new View.OnTouchListener() {
                               @Override
                               public boolean onTouch(View v, MotionEvent event) {
                                   if (event.getAction()==MotionEvent.ACTION_DOWN){
                                       button.setScaleX(1.1f);
                                       button.setScaleY(1.1f);
                                   }else if(event.getAction()==MotionEvent.ACTION_UP){
                                       button.setScaleX(1.0f);
                                       button.setScaleY(1.0f);
                                   }
                                   return false;
                               }

                           });
                           button.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   irAditivo(pos+pagina+add);
                               }
                           });
                           fila.addView(button);
                       }
                   }
               }

               tableLayout.addView(fila);
           }
       }
    }
    public void irAditivo(int posicion){
        Intent intent = new Intent(this, AditivoActivity.class);
        intent.putExtra("otrosNombres",otrosNombresArray.get(posicion));
        intent.putExtra("Es",EsArray.get(posicion));
        intent.putExtra("nombre",nombresArray.get(posicion));
        intent.putExtra("nivel",nivelesArray.get(posicion));

        startActivity(intent);
    }
}