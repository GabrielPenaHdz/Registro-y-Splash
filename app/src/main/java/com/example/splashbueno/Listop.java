package com.example.splashbueno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashbueno.Json.MyData;
import com.example.splashbueno.Json.MyInfo;
import com.example.splashbueno.MyAdapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class Listop extends AppCompatActivity {
    private ListView listView;
    private List<MyData> list;
    private TextView user;
    private int []perfiles = {R.drawable.facebook,R.drawable.instagram,R.drawable.tiktok,R.drawable.twitter};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listop);
        String aux = null;
        MyInfo info = null;
        MyData myData = null;
        Object object = null;
        user = findViewById(R.id.NewId);
        listView = (ListView) findViewById(R.id.listViewId);
        list = new ArrayList<MyData>();
        Intent intent = getIntent();

        for(int i = 0; i <4; i++){
            myData = new MyData();
            myData.setPassRed(String.format("Contraseña: %d" , (int)(Math.random()*1000000)));
            if(i == 0){
                myData.setRed(String.format("Facebook"));
                myData.setPerfil(perfiles[0]);
            }
            if(i == 1){
                myData.setRed(String.format("Instagram"));
                myData.setPerfil(perfiles[1]);
            }
            if(i == 2){
                myData.setRed(String.format("TikTok"));
                myData.setPerfil(perfiles[2]);
            }
            if(i == 3){
                myData.setRed(String.format("Twitter"));
                myData.setPerfil(perfiles[3]);
            }

            list.add(myData);
        }

        MyAdapter myAdapter = new MyAdapter(list, getBaseContext());
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                toast(i);
            }
        });




        if( intent != null){
            aux = intent.getStringExtra("Usuario");
            if(aux != null && aux.length()>0){
                user.setText(aux);
            }
            if( intent.getExtras() != null){
                object = intent.getExtras().get("MyInfo");
                if(object != null){
                    if(object instanceof MyInfo){
                        info = (MyInfo) object;
                        user.setText("Bienvenido " + info.getUsuario());
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean flag = false;
        MenuInflater menuInflater;
        flag = super.onCreateOptionsMenu(menu);
        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String seleccion = null;
        switch (item.getItemId()){
            case R.id.menuNuevoId:
            case R.id.menuCopiarId:
            case R.id.menuAyudaId:
            case R.id.menuPegarId:
                seleccion = String.format("opción %s", item.getTitle().toString());
                break;
            default:
                seleccion = "Sin opción";
                break;
        }
        Toast.makeText(getBaseContext(), seleccion, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    private void toast(int i)
    {
        Toast.makeText(getBaseContext(), list.get(i).getPassRed(),Toast.LENGTH_SHORT).show();
    }
}