package com.example.phoc;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.example.phoc.MyFeedActivity.Myfeed;
import com.example.phoc.MySession.MySession;
import com.example.phoc.ParticularTitleActivity.ParticularTitle;
import com.example.phoc.SearchUserActivity.SearchUser;
import com.example.phoc.SubscribeUserActivity.SubscribeUser;
import com.example.phoc.TitleListActivity.TitleList;
import com.example.phoc.UserFeedActivity.UserFeed;
import com.example.phoc.SubscribeUserListActivity.SubscribeUserList;
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
    SubscribeUserList subscribeUserList;

    Toolbar toolbar;
    Button subscribebtn;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //구독버튼 눌렀을 때
        subscribebtn = findViewById(R.id.subscribeBtn);

        subscribebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("subsc", "subcribe called");
                if(subscribebtn.getText().toString() == "구독하기"){
                    Toast myToast = Toast.makeText(getApplicationContext(),"구독 완료!", Toast.LENGTH_SHORT);
                    myToast.show();
                    subscribebtn.setTextColor(Color.RED);
                    subscribebtn.setText("구독취소");
                    DatabaseQueryClass.User.subscribe(MySession.getSession().getUserId(), userId);
                }else {
                    Toast myToast = Toast.makeText(getApplicationContext(),"구독 취소!", Toast.LENGTH_SHORT);
                    myToast.show();
                    subscribebtn.setTextColor(Color.WHITE);
                    subscribebtn.setText("구독하기");
                    DatabaseQueryClass.User.cancelSubscribe(MySession.getSession().getUserId(), userId);
                }
            }
        });
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
        subscribeUserList = new SubscribeUserList();


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
        } else if(id == R.id.menu6) {
            onFragmentSelected(8, null);
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
            subscribebtn.setVisibility(View.GONE);
        } else if(position == 1) {
            curFragment = subscribeUser;
            toolbar.setTitle("구독작가들의 작품");
            subscribebtn.setVisibility(View.GONE);
        } else if(position == 2) {
            curFragment = titleList;
            toolbar.setTitle("주제 목록");
            subscribebtn.setVisibility(View.GONE);
        } else if(position == 3) {
            curFragment = searchUser;
            toolbar.setTitle("작가 검색");
            subscribebtn.setVisibility(View.GONE);
        } else if(position == 4) {
            curFragment = myfeed;
            toolbar.setTitle("내 작품");
            subscribebtn.setVisibility(View.GONE);
        } else if(position == 5) {
            curFragment = makefeed;
            curFragment.setArguments(bundle);
            toolbar.setTitle("작품 만들기");
            subscribebtn.setVisibility(View.GONE);
        } else if(position == 6) {
            curFragment = particularTitle;
            toolbar.setTitle("#" + bundle.get("theme").toString());
            subscribebtn.setVisibility(View.GONE);
        } else if(position == 7) {
            curFragment = userFeed;
            toolbar.setTitle(bundle.get("nick").toString() + "의 갤러리");
            userId = bundle.get("userId").toString();

            DatabaseQueryClass.User.isSubscribed(userId, new DataListener() {
                @Override
                public void getData(Object data, String id) {
                    boolean flag = (boolean)data;
                    if(flag){
                        subscribebtn.setTextColor(Color.RED);
                        subscribebtn.setText("구독취소");
                    }else{
                        subscribebtn.setTextColor(Color.WHITE);
                        subscribebtn.setText("구독하기");
                    }
                    subscribebtn.setVisibility(View.VISIBLE);
                }
            });
        } else if(position == 8) {
            curFragment = subscribeUserList;
            toolbar.setTitle("구독중인 유저들");
            subscribebtn.setVisibility(View.GONE);
        }
        if(bundle != null){
            curFragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();
    }

}
