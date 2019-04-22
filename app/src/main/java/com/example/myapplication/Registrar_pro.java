package com.example.myapplication;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class Registrar_pro extends AppCompatActivity {

    private TextInputEditText etNombre, etPrecio, etCantidad;
    private Button btnAlmacenar;
    private ListView lvDatos;
    private AsyncHttpClient cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_pro);

        etNombre = (TextInputEditText) findViewById(R.id.etNombre);
        etPrecio = (TextInputEditText) findViewById(R.id.etPrecio);
        etCantidad = (TextInputEditText) findViewById(R.id.etCantidad);
        btnAlmacenar = (Button) findViewById(R.id.btnAlmacenar);
        lvDatos = (ListView) findViewById(R.id.lvDatos);

        cliente = new AsyncHttpClient();
        botonAlmacenar();


    }


    private void botonAlmacenar(){
        btnAlmacenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNombre.getText().toString().isEmpty() || etPrecio.getText().toString().isEmpty() || etCantidad.getText().toString().isEmpty()){
                    Toast.makeText(Registrar_pro.this, "Hay campos vacios!!", Toast.LENGTH_SHORT).show();

                } else {
                    Producto p = new Producto();
                    p.setNombre(etNombre.getText().toString());
                    p.setPrecio(Integer.parseInt(etPrecio.getText().toString()));
                    p.setCantidad(Integer.parseInt(etCantidad.getText().toString()));
                    p.setTotal(p.getPrecio()*p.getCantidad());
                    agregarProducto(p);

                }
            }
        });

    }

    private void agregarProducto(Producto p) {

        String url = "https://myappmovilbbc.000webhostapp.com/NavigationDrawer/agregar.php?";
        String parametros = "Nombre="+p.getNombre()+"&Precio="+p.getPrecio()+"&Cantidad="+p.getCantidad()+"&Total="+p.getTotal();
        cliente.post(url + parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200){
                    Toast.makeText(Registrar_pro.this, "Producto agregado correctamente!!", Toast.LENGTH_SHORT).show();
                    etNombre.setText("");
                    etPrecio.setText("");
                    etCantidad.setText("");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }







}
