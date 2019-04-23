package com.example.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import java.util.ArrayList;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

public class Catalogo extends AppCompatActivity {

    public ListView lvDatos;
    private AsyncHttpClient cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingBtnBack);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       startActivity(new Intent(Catalogo.this, MainActivity.class));
                                                                          }
                               });

        lvDatos = (ListView) findViewById(R.id.lvDatos);
        cliente = new AsyncHttpClient();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        obtenerProductos();

    }

    private void obtenerProductos(){

        String url = "https://myappmovilbbc.000webhostapp.com/NavigationDrawer/obtenerDatos.php";

        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if(statusCode == 200) {
                    listarProductos(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private void listarProductos (String respuesta){

        final ArrayList<Producto> lista = new ArrayList<Producto>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);

            for (int i=0; i<jsonArreglo.length();i++){
                Producto p = new Producto();
                p.setId((jsonArreglo.getJSONObject(i).getInt("id_pro")));
                p.setNombre((jsonArreglo.getJSONObject(i).getString("nom_pro")));
                p.setPrecio((jsonArreglo.getJSONObject(i).getInt("pre_pro")));
                p.setCantidad((jsonArreglo.getJSONObject(i).getInt("can_pro")));
                p.setTotal((jsonArreglo.getJSONObject(i).getInt("tot_pro")));
                lista.add(p);

            }
            ArrayAdapter <Producto> a = new ArrayAdapter (this,android.R.layout.simple_list_item_1,lista);
            lvDatos.setAdapter(a);

            lvDatos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    Producto p = lista.get(position);
                    String url = "https://myappmovilbbc.000webhostapp.com/NavigationDrawer/eliminar.php?id="+p.getId();

                    cliente.post(url, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                            if (statusCode == 200) {
                                Toast.makeText(Catalogo.this, "Producto eliminado correctamente!!", Toast.LENGTH_SHORT).show();
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                obtenerProductos();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });

                    return true;

                }
            });

            lvDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Producto p = lista.get(position);
                    StringBuffer b = new StringBuffer();
                    b.append("ID: " + p.getId() + "\n");
                    b.append("NOMBRE: " + p.getNombre() + "\n");
                    b.append("PRECIO: $" + p.getPrecio() + "\n");
                    b.append("CANTIDAD: " + p.getCantidad() + "\n");
                    b.append("TOTAL: $" + p.getTotal());

                    AlertDialog.Builder a = new AlertDialog.Builder(Catalogo.this);
                    a.setCancelable(true);
                    a.setTitle("Detalle");
                    a.setMessage(b.toString());
                    a.setIcon(R.drawable.okicon);
                    a.show();
               }
            });


        } catch (Exception e1) {

            e1.printStackTrace();
        }

    }


}
