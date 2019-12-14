package com.example.phoc;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.phoc.MyFeedActivity.Myfeed;
import com.example.phoc.ParticularTitleActivity.ParticularTitle;
import com.example.phoc.SearchUserActivity.SearchUser;
import com.example.phoc.SubscribeUserActivity.SubscribeUser;
import com.example.phoc.TitleListActivity.TitleList;
import com.example.phoc.UserFeedActivity.UserFeed;
import com.google.android.material.navigation.NavigationView;

public class main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback{

    Home home;
    SubscribeUser subscribeUser;
    TitleList titleList;
    SearchUser searchUser;
    Myfeed myfeed;
    MakeFeed makefeed;
    ParticularTitle particularTitle;
    UserFeed userFeed;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        home = new Home();
        subscribeUser = new SubscribeUser();
        titleList = new TitleList();
        searchUser = new SearchUser();
        myfeed = new Myfeed();
        makefeed = new MakeFeed();
        particularTitle = new ParticularTitle();
        userFeed = new UserFeed();

        getSupportFragmentManager().beginTransaction().add(R.id.container, home).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu1) {
            onFragmentSelected(0, null);
        } else if(id == R.id.menu2) {
            onFragmentSelected(1, null);
        } else if(id == R.id.menu3) {
            onFragmentSelected(2, null);
        } else if(id == R.id.menu4) {
            onFragmentSelected(3, null);
        } else if(id == R.id.menu5) {
            onFragmentSelected(4, null);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentSelected(int position, Bundle bundle) {

        Fragment curFragment = null;

        if (position == 0) {
            curFragment = home;
            toolbar.setTitle("오늘의 주제");
        } else if(position == 1) {
            curFragment = subscribeUser;
            toolbar.setTitle("구독작가들의 작품");
        } else if(position == 2) {
            curFragment = titleList;
            toolbar.setTitle("주제 목록");
        } else if(position == 3) {
            curFragment = searchUser;
            toolbar.setTitle("작가 검색");
        } else if(position == 4) {
            curFragment = myfeed;
            toolbar.setTitle("내 작품");
        } else if(position == 5) {
            curFragment = makefeed;
            toolbar.setTitle("작품 만들기");
        } else if(position == 6) {
            curFragment = particularTitle;
            toolbar.setTitle("특정 주제 이름");
        } else if(position == 7) {
            curFragment = userFeed;
            toolbar.setTitle("User 이름");
        }
        if(bundle != null){
            Log.d("ParticularTitle", bundle.toString());
            curFragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();
    }

}
