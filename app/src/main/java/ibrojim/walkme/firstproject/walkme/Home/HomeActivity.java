package ibrojim.walkme.firstproject.walkme.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import ibrojim.walkme.firstproject.walkme.R;
import ibrojim.walkme.firstproject.walkme.Util.BottomNavigationViewExHelper;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx) findViewById(R.id.bottom_navigation_view_ex);
        BottomNavigationViewExHelper.setupBottomNavigationEx(bottomNavigationViewEx);
    }
}
