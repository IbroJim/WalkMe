package ibrojim.walkme.firstproject.walkme.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import ibrojim.walkme.firstproject.walkme.R;
import ibrojim.walkme.firstproject.walkme.Util.BottomNavigationViewExHelper;
import me.gujun.android.taggroup.TagGroup;

public class HomeActivity extends AppCompatActivity {


    private Context mContext=HomeActivity.this;
    private static final int ACTIVITY_NUM=0;


    private EditText addTag,addMySelfInformation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBottomNavigationView();
        setupTagGroup();
        setupMySelf();

    }
    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx) findViewById(R.id.bottom_navigation_view_ex);
        BottomNavigationViewExHelper.setupBottomNavigationEx(bottomNavigationViewEx);
        BottomNavigationViewExHelper.enableBottom(bottomNavigationViewEx,mContext,ACTIVITY_NUM);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
    private void setupTagGroup() {
        final TagGroup tagGroup=(TagGroup) findViewById(R.id.home_tag_layout);
        final ArrayList<String> arrayListTag; arrayListTag=new ArrayList<>();
        addTag=(EditText)findViewById(R.id.home_add_tag);
             arrayListTag.add("Кино");
             arrayListTag.add("Кино");
             arrayListTag.add("Кальян");
             tagGroup.setTags(arrayListTag);

             addTag.setOnKeyListener(new View.OnKeyListener() {
                 @Override
                 public boolean onKey(View v, int keyCode, KeyEvent event) {
                     if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                             (keyCode == KeyEvent.KEYCODE_ENTER)) {
                         // Perform action on key press
                         if(addTag.getText()!=null) {
                             arrayListTag.add(addTag.getText().toString());
                             tagGroup.setTags(arrayListTag);
                             addTag.setText(" ");
                         }
                         return true;
                     }
                     return false;
                 }
             });
    }


    private void setupMySelf() {
        addMySelfInformation = (EditText) findViewById(R.id.edit_myself);
        addMySelfInformation.setHorizontallyScrolling(false);
        addMySelfInformation.setMaxLines(Integer.MAX_VALUE);
    }
}
