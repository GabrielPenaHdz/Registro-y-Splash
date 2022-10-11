package com.example.splashbueno;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.splashbueno.Json.MyInfo;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class Formulario extends AppCompatActivity {
    private Button Registro;
    private static final String TAG = "MainActivity";
    public static final String archivo = "archivo.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        List<MyInfo> list = new ArrayList<MyInfo>();
        Registro = findViewById(R.id.Registro);
        Button Registro = findViewById(R.id.Registro);
        Button Volver = findViewById(R.id.volver);
        EditText Nombre = findViewById(R.id.NombreR);
        EditText Usuario = findViewById(R.id.UsuarioR);
        EditText Contraseña = findViewById(R.id.ContraseñaR);
        EditText Correo = findViewById(R.id.EmailR);
        RadioButton Masc = findViewById(R.id.Masc);
        RadioButton Fem = findViewById(R.id.Fem);
        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Formulario.this, Login.class);
                startActivity(intent);
            }
        });

        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyInfo info = new MyInfo();
                info.setNombre(String.valueOf(Nombre.getText()));
                info.setUsuario(String.valueOf(Usuario.getText()));
                info.setContraseña(String.valueOf(Contraseña.getText()));
                info.setCorreo(String.valueOf(Correo.getText()));
                List2Json(info,list);
            }
        });
    }
    public void Objet2Json(MyInfo info){
        Gson gson = null;
        String json = null;
        String mensaje = null;
        gson = new Gson();
        json = gson.toJson(info);
        if(json != null){
            Log.d(TAG, json);
            mensaje ="object2Json OK";
        }
        else{
            mensaje = "Error object2Json";
        }
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }
    public void List2Json(MyInfo info, List<MyInfo> list){
        Gson gson = null;
        String json = null;
        gson = new Gson();
        list.add(info);
        json = gson.toJson(list, ArrayList.class);
        if(json == null){
            Log.d(TAG, "Error json");
        }
        else{
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_SHORT).show();
    }
    private boolean writeFile(String text){
        File file = null;
        FileOutputStream fileOutputStream = null;
        try{
            file = getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
            Log.d(TAG, "Listo");
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    private File getFile(){
        return new File(getDataDir(),archivo);
    }
}