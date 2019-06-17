package ibrojim.walkme.firstproject.walkme.Messege;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.HashMap;
import java.util.Map;

import ibrojim.walkme.firstproject.walkme.R;
import ibrojim.walkme.firstproject.walkme.Util.BottomNavigationViewExHelper;

public class MessegeActivity extends AppCompatActivity {
    private static final int ACTIVITY_NUM=2;
    public static final String KEY="key";

    private Context mContext= MessegeActivity.this;
    private Fragment fragment;
    private FragmentManager fragmentManager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messege);
        setupBottomNavigationView();
        setupFragment();
    }
    private void setupFragment(){
        fragment=new FragmentChat();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragmentContainer,fragment).commit();

    }
    public String getID() {
        String id=getIntent().getStringExtra(KEY);
        return id;
    }
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx) findViewById(R.id.bottom_navigation_view_ex);
        BottomNavigationViewExHelper.setupBottomNavigationEx(bottomNavigationViewEx);
        BottomNavigationViewExHelper.enableBottom(bottomNavigationViewEx,mContext,ACTIVITY_NUM);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

}
