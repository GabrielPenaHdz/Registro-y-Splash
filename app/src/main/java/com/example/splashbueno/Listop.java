package com.example.splashbueno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashbueno.Json.MyData;
import com.example.splashbueno.Json.MyInfo;
import com.example.splashbueno.MyAdapter.MyAdapter;
import com.example.splashbueno.des.MyDesUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Listop extends AppCompatActivity {
    private ListView listView;
    private List<MyInfo> list;
    public static String TAG = "mensaje";
    public static String json = null;
    private List<MyData> listo;
    public boolean bandera = false;
    public  int pos = 0;
    public static MyInfo myInfo = null;
    String aux;
    private TextView user;
    private int []perfiles = {R.drawable.facebook,R.drawable.instagram,R.drawable.tiktok,R.drawable.twitter};
    EditText editText, editText1;
    Button button, button1, button2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Object object = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listop);
        Intent intent = getIntent();
        if (intent != null){
            if (intent.getExtras() != null){
                object = intent.getExtras().get("Objeto");
                if (object != null){
                    if (object instanceof  MyInfo){
                        myInfo = (MyInfo) object;
                    }
                }
            }
        }

        list = new ArrayList<>();
        list = Login.list;
        editText = findViewById(R.id.editText1);
        editText1 = findViewById(R.id.editText2);
        button = findViewById(R.id.Borrar);
        button1 = findViewById(R.id.Edit);
        button2 = findViewById(R.id.Ops);
        listView = (ListView) findViewById(R.id.listViewId);
        MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
        listView.setAdapter(myAdapter);
        button.setEnabled(false);
        button1.setEnabled(false);

        if(listo.isEmpty()){
            Toast.makeText(getApplicationContext(), "Para agregar una contraseña de click en el menú", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Escriba en los campos", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getApplicationContext(), "Para modificar o eliminar una contraseña haga click en ella", Toast.LENGTH_LONG).show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(listo.get(i).getRed());
                editText1.setText(listo.get(i).getPassRed());
                pos =i ;
                button.setEnabled(true);
                button1.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Para guardar los cambios haga click en guardar cambios", Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listo.remove(pos);
                myInfo.setContras(listo);
                MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                listView.setAdapter(myAdapter);
                editText.setText("");
                editText1.setText("");
                Toast.makeText(getApplicationContext(), "Se eliminó la contraseña" ,Toast.LENGTH_LONG).show();
                button.setEnabled(false);
                button1.setEnabled(false);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String redn = String.valueOf(editText.getText());
                String contran = String.valueOf(editText1.getText());
                if (redn.equals("")||contran.equals("")){
                    Toast.makeText(getBaseContext(), "Llene los campos", Toast.LENGTH_LONG).show();
                }else{
                    listo.get(pos).setRed(redn);
                    listo.get(pos).setPassRed(contran);
                    myInfo.setContras(listo);
                    MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                    listView.setAdapter(myAdapter);
                    editText.setText("");
                    editText1.setText("");
                    Toast.makeText(getApplicationContext(), "Se modificó la contraseña", Toast.LENGTH_LONG).show();
                    button.setEnabled(false);
                    button1.setEnabled(false);
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String redn = String.valueOf(editText.getText());
                String contran = String.valueOf(editText1.getText());
                if(redn.equals("")||contran.equals("")){
                    Toast.makeText(getApplicationContext(),"Llena los campos", Toast.LENGTH_LONG).show();
                }else{
                    MyData myData = new MyData();
                    myData.setPassRed(contran);
                    myData.setPassRed(redn);
                    listo.add(myData);
                    MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                    listView.setAdapter(myAdapter);
                    editText.setText("");
                    editText1.setText("");
                    Toast.makeText(getApplicationContext(), "Se agregó la contraseña", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if(id==R.id.menuNuevoId){
            String red = String.valueOf(editText.getText());
            String contra = String.valueOf(editText1.getText());
            if(red.equals("")||contra.equals("")){
                Toast.makeText(getApplicationContext(), "Llena los campos", Toast.LENGTH_LONG).show();
            }else{
                MyData myData = new MyData();
                myData.setPassRed(contra);
                myData.setRed(red);
                listo.add(myData);
                MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                listView.setAdapter(myAdapter);
                editText.setText("");
                editText1.setText("");
                Toast.makeText(getApplicationContext(), "Se agregó la contraseña", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if(id==R.id.menuGuardarId){
            int i = 0;
            for(MyInfo inf : list){
                if (myInfo.getUsuario().equals(inf.getUsuario())){
                    list.get(i).setContras(listo);
                }
                i++;
            }
            List2Json(myInfo,list);
            return true;
        }
        if(id==R.id.menuCerrarId){
            Intent intent = new Intent(Listop.this, Login.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void List2Json(MyInfo info,List<MyInfo> list){
        Gson gson = null;
        String json = null;
        gson = new Gson();
        json = gson.toJson(list, ArrayList.class);
        if (json == null){
            Log.d(TAG, "Error json");
        }
        else{
            Log.d(TAG, json);
            json = MyDesUtil.cifrar(json);
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_LONG).show();
    }
    private boolean writeFile(String text){
        File file = null;
        FileOutputStream fileOutputStream = null;
        try {
            file = getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.close();
            Log.d(TAG, "Hola");
            return true;

        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private File getFile(){return new File(getDataDir(),Formulario.archivo);}
}