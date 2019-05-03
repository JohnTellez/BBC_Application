package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import com.bumptech.glide.Glide;
import com.varunest.sparkbutton.SparkButton;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.bbc1,R.drawable.bbc2,R.drawable.bbc3,R.drawable.bbc4,R.drawable.bbc5,R.drawable.bbc6,R.drawable.bbc7};

    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        ImageView foto = (ImageView)header.findViewById(R.id.fotoUsuario);

        try{
            Bundle bundle = getIntent().getExtras();
            Usuario user = bundle.getParcelable("DATA_USER");
            if(bundle!=null){
                int ident = user.getId();
                ((TextView) header.findViewById(R.id.txtNombreUsuario)).setText(user.getNombre());
                ((TextView) header.findViewById(R.id.txtEmailUsuario)).setText(user.getEmail());
                if(!user.getFoto().equals("sin imagen")){
                    String url_image = Conexion.URL_WEB_SERVICE + user.getFoto();
                    url_image = url_image.replace(" ","%20");
                    try {
                        Log.i("RESPUESTA IMAGEN: ",""+url_image);
                        Glide.with(this).load(url_image).into(foto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Reemplace con su propia acción", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        // Boton Catalogo
        SparkButton button = (SparkButton) findViewById(R.id.catalogobtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Catalogo.class);
                startActivity(intent);
            }
        });
        //Fin

        // Boton Cupon
        SparkButton botonCupon = (SparkButton) findViewById(R.id.cuponbtn);

        botonCupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Catalogo.class);
                startActivity(intent);
            }
        });
        //Fin

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(MainActivity.this,XMENArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }







    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {

            switch (id) {
                case R.id.nav_inicio: {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }
            }


        } else if (id == R.id.nav_reg_prod) {

            switch (id) {
                case R.id.nav_reg_prod: {
                    startActivity(new Intent(MainActivity.this, Registrar_pro.class));
                }
            }

        } else if (id == R.id.nav_catalogo) {

            switch (id) {
                case R.id.nav_catalogo: {
                    startActivity(new Intent(MainActivity.this, Catalogo.class));
                }
            }
        } else if (id == R.id.nav_cupones) {
            switch (id) {
                case R.id.nav_cupones: {

                    Toast.makeText(getApplicationContext(),"Modulo en construcción", Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(MainActivity.this, MainActivity.class)); // Falta Implementar

                }
            }

        } else if (id == R.id.nav_merchan) {

            switch (id) {
                case R.id.nav_merchan: {

                    Toast.makeText(getApplicationContext(),"Modulo en construcción", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(MainActivity.this, MainActivity.class)); // Falta Implementar
                }
            }


        } else if (id == R.id.nav_sucursales) {
            switch (id) {
                case R.id.nav_sucursales: {

                    Toast.makeText(getApplicationContext(),"Modulo en construcción", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(MainActivity.this, MainActivity.class)); // Falta Implementar
                }
            }

        } else if (id == R.id.nav_iniciarsesion) {
                switch (id) {
                    case R.id.nav_iniciarsesion: {
                        startActivity(new Intent(MainActivity.this, Inicio.class));
                    }
                }

        } else if (id == R.id.nav_about) {
                switch (id) {
                    case R.id.nav_about: {

                        Toast.makeText(getApplicationContext(),"Modulo en construcción", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(MainActivity.this, MainActivity.class)); // Falta Implementar
                    }
                }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


