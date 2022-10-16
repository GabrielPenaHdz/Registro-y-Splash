package com.example.splashbueno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.splashbueno.Json.MyInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OlvidePass extends AppCompatActivity {
    private List<MyInfo> list;
    public static String TAG = "mensaje";
    String json = null;
    public static String UsrOlv,passwOlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_pass);
        Button Cambi = findViewById(R.id.BotonPa);
        Button VolviLog = findViewById(R.id.Volv);
        EditText usuario = findViewById(R.id.UsuarioOlv);
        EditText passws = findViewById(R.id.PassOlv);
        Read();
        json2List(json);
        Cambi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsrOlv= String.valueOf(usuario.getText());
                passwOlv = String.valueOf(passws.getText());
                Cambiar();
            }
        });
        VolviLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OlvidePass.this, Login.class);
                startActivity(intent);
            }
        });
    }
    public boolean Read(){
        if(!isFileExits()){
            return false;
        }
        File file = getFile();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int)file.length()];
        try{
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            json = new String(bytes);
            Log.d(TAG, json);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    public void json2List(String json){
        Gson gson = null;
        String mensaje = null;
        if(json == null || json.length() == 0){
            Toast.makeText(getApplicationContext(), "Error json null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<MyInfo>>(){}.getType();
        list = gson.fromJson(json, listType);
        if(list == null || list.size() == 0){
            Toast.makeText(getApplicationContext(), "Error list is null or empty", Toast.LENGTH_LONG).show();
            return;
        }
    }
    private File getFile(){
        return new File(getDataDir() , Formulario.archivo);
    }

    private boolean isFileExits(){
        File file = getFile();
        if(file == null){
            return false;
        }
        return file.isFile() && file.exists();
    }
    public void Cambiar(){
        int i = 0;
        for(MyInfo myInfo : list){
            if (myInfo.getUsuario().equals(UsrOlv)){
                Intent intent = new Intent(OlvidePass.this, ContraRecu.class);
                startActivity(intent);
                i = 1;

            }
        }
        if (i == 0){
            Toast.makeText(getApplicationContext(), "El usuario no existe o es incorrecto ",Toast.LENGTH_LONG).show();
        }
    }
}
