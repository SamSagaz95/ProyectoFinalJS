package com.example.proyectofinal;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LeagueOfLegends extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_of_legends);

        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        ViewPagerAdapterLol adapter = new ViewPagerAdapterLol(getSupportFragmentManager());
        //Añade las pestañas
        adapter.addFragment(new FragmentLolNoticias(), "Noticias");
        adapter.addFragment(new FragmentLolRanking(), "Ranking");
        adapter.addFragment(new FragmentLolEquipos(), "Equipos");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
