package com.example.myapplication;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;

public class Registrar_pro extends AppCompatActivity {

    private TextInputEditText etNombre, etDescripcion, etCalificacion, etPrecio, etImagen ;
    private Button btnAlmacenar;
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

        btnAlmacenar = (Button) findViewById(R.id.btnAlmacenar);


        cliente = new AsyncHttpClient();

        botonAlmacenar();



    }


    private void botonAlmacenar(){
        btnAlmacenar.setOnClickListener(new View.OnClickListener() {
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
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }






}
