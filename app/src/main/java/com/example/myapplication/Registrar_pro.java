package com.example.myapplication;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.loopj.android.http.*;
import com.spark.submitbutton.SubmitButton;

import cz.msebera.android.httpclient.Header;

public class Registrar_pro extends AppCompatActivity {

    private TextInputEditText etNombre, etDescripcion, etCalificacion, etPrecio, etImagen ;
    private SubmitButton btnAlmacenar2;
    private AsyncHttpClient cliente;



  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_pro);



        etNombre = (TextInputEditText) findViewById(R.id.etNombre);
        etDescripcion = (TextInputEditText) findViewById(R.id.etDescripcion);
        etCalificacion = (TextInputEditText) findViewById(R.id.etCalificacion);
        etPrecio = (TextInputEditText) findViewById(R.id.etPrecio);
        etImagen = (TextInputEditText) findViewById(R.id.etImagen);
        btnAlmacenar2 = (SubmitButton) findViewById(R.id.btnAlmacenar2);


        cliente = new AsyncHttpClient();


        botonAlmacenar2();

    }

    // Implementacion volver en layout
    // Inicio
    private void setSupportActionBar (Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   }
    //Fin


    // Inicio


    private void botonAlmacenar2(){
        btnAlmacenar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNombre.getText().toString().isEmpty() || etDescripcion.getText().toString().isEmpty() || etCalificacion.getText().toString().isEmpty() ||
                        etPrecio.getText().toString().isEmpty() || etImagen.getText().toString().isEmpty() ) {
                    Toast.makeText(Registrar_pro.this, "Hay campos vacios!!", Toast.LENGTH_SHORT).show();

                } else {
                    Producto p = new Producto();
                    p.setNombre(etNombre.getText().toString().replaceAll(" ", "%20"));
                    p.setDescripcion(etDescripcion.getText().toString().replaceAll(" ", "%20"));
                    p.setCalificacion(Integer.parseInt(etCalificacion.getText().toString()));
                    p.setPrecio(Integer.parseInt(etPrecio.getText().toString()));
                    p.setImagen(etImagen.getText().toString());

                    //p.setTotal(p.getPrecio() * p.getCantidad());
                    agregarProducto(p);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

            }
        });

    }

    private void agregarProducto(Producto p) {

        String url = "https://myappmovilbbc.000webhostapp.com/NavigationDrawer/agregar_prod.php?";
        String parametros = "Nombre="+p.getNombre()+"&Descripcion="+p.getDescripcion()+"&Calificacion="+p.getCalificacion()
                +"&Precio="+p.getPrecio()+"&Imagen="+p.getImagen();
        //String parametros = "Nombre="+p.getNombre()+"&Precio="+p.getPrecio()+"&Cantidad="+p.getCantidad()+"&Total="+p.getTotal();
        cliente.post(url + parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200){
                    Toast.makeText(Registrar_pro.this, "Producto agregado correctamente!!", Toast.LENGTH_SHORT).show();
                    etNombre.setText("");
                    etDescripcion.setText("");
                    etImagen.setText("");
                    etPrecio.setText("");
                    etCalificacion.setText("");

                    Intent miIntent = new Intent( Registrar_pro.this, Catalogo.class);
                    startActivity(miIntent);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }






}
