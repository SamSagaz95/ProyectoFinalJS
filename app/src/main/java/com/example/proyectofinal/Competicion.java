package com.example.proyectofinal;
import android.app.Application;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Competicion extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private ImageView imgView;

    private String usuarioActual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competicion);

        imgView = (ImageView)findViewById(R.id.imgView);

        Bundle parametros = getIntent().getExtras();
        if (parametros != null) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

            databaseAccess.open();
            int competicion = Integer.parseInt(parametros.getString("competicion"));

            String iconName = databaseAccess.getIconCompeticion(competicion);
            String icon = iconName.substring(0, iconName.length()-4);
            int id = this.getResources().getIdentifier(icon, "mipmap", getPackageName());
            imgView.setImageResource(id);


        }

        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        ViewPagerAdapterLol adapter = new ViewPagerAdapterLol(getSupportFragmentManager());
        //Añade las pestañas
        adapter.addFragment(new FragmentNoticias(), "Noticias");
        adapter.addFragment(new FragmentEquipos(), "Ranking");
        adapter.addFragment(new FragmentEventos(), "Eventos");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
