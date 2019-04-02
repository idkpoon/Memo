package com.example.android.memo.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.memo.CustomTypefaceSpan;
import com.example.android.memo.Fragments.InboxFragment;
import com.example.android.memo.Fragments.ListFragment;
import com.example.android.memo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private android.support.v7.widget.Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    public Typeface futuraFont;
    private TextView mTitle;
    private NavigationView navigationView;
    private FloatingActionButton fabAdd;

    String lastVisitedFragment = "Inbox";

    TextView name;
    TextView productivityLevel;

    private FirebaseAuth mAuth;

    private View navHeader;

    @TargetApi(3)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(i);
        }


        futuraFont = Typeface.createFromAsset(this.getAssets(), "Fonts/Futura-Medium.ttf");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);
            applyFontToMenuItem(mi);
        }

        navHeader = navigationView.getHeaderView(0);

        name = (TextView) navHeader.findViewById(R.id.navName);
        name.setTypeface(futuraFont);

        productivityLevel = (TextView) navHeader.findViewById(R.id.navProductivity);
        productivityLevel.setTypeface(futuraFont);

        fabAdd = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTodoActivity = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(addTodoActivity);
            }
        });

        mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar_menu);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(futuraFont);
        mToolbar.setNavigationIcon(R.drawable.ic_dehaze);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.syncState();

        if(savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_main_container, new InboxFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_inbox);
        }

    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lastVisitedFragment.equals(R.string.title_inbox)){
            navigationView.setCheckedItem(R.id.nav_inbox);
        }
        else if(lastVisitedFragment.equals(R.string.title_list)){
            navigationView.setCheckedItem(R.id.nav_list);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch(item.getItemId()){
            case R.id.nav_inbox:
                fragmentTransaction.replace(R.id.fragment_main_container, new InboxFragment()).commit();
                mTitle.setText(R.string.title_inbox);
                Toast.makeText(this, "Frag changed to inbox", Toast.LENGTH_SHORT).show();
                lastVisitedFragment = getString(R.string.title_inbox);
                break;
            case R.id.nav_list:
                fragmentTransaction.replace(R.id.fragment_main_container, new ListFragment()).commit();
                mTitle.setText(R.string.title_list);
                Toast.makeText(this, "Frag changed to list", Toast.LENGTH_SHORT).show();
                navigationView.setCheckedItem(R.id.nav_list);
                lastVisitedFragment = getString(R.string.title_list);
                break;
            case R.id.nav_settings:
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                break;

            case R.id.nav_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                i = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(i);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();

        }

    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/Futura-Medium.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }



}
