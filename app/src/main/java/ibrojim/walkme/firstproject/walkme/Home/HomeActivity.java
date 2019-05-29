package ibrojim.walkme.firstproject.walkme.Home;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import ibrojim.walkme.firstproject.walkme.R;
import ibrojim.walkme.firstproject.walkme.Util.BottomNavigationViewExHelper;
import ibrojim.walkme.firstproject.walkme.db.MyDbHelper;
import me.gujun.android.taggroup.TagGroup;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    private Context mContext=HomeActivity.this;
    private static final int ACTIVITY_NUM=0;


    private TextView editMyself;
    private ImageView alertTag, zoomMySelfInformation;
    private MyDbHelper dbHelper;

    private ArrayList<String> arrayListTag=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homee);
        setupBottomNavigationView();
        setupTagGroup();
        setupMySelf();
        setupDB();

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
        arrayListTag=new ArrayList<>();
        arrayListTag.add("Пробежка");
        arrayListTag.add("Кино");
        arrayListTag.add("Кальян");
        arrayListTag.add("Клуб");
        arrayListTag.add("Мафия");
        arrayListTag.add("Наруто");
        arrayListTag.add("Шопинг");
        arrayListTag.add("Философия");
        arrayListTag.add("Dota");
        tagGroup.setTags(arrayListTag);
    }
    private void setupMySelf() {
        editMyself = (TextView) findViewById(R.id.edit_myself);
    }
    private void setupZoom(){
        alertTag=(ImageView) findViewById(R.id.zoom_tag);
        alertTag.setOnClickListener(this);
    }
    private void setupAlertTag(){
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(mContext);
        View view=getLayoutInflater().inflate(R.layout.dialog_tag_layout,null);
        final TagGroup tagGroup=(TagGroup) view.findViewById(R.id.dialog_tag);
        final EditText editText=(EditText) view.findViewById(R.id.edit_tag);
        final ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Кино");
        arrayList.add("Кино");
        arrayList.add("Кальян");
        tagGroup.setTag(arrayList);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    arrayList.add(editText.getText().toString());
                    tagGroup.setTags(arrayList);
                    editText.setText(" ");
                    return true;
                }
                return false;
            }
        });

        mBuilder.setView(view);
        AlertDialog dialog=mBuilder.create();
        dialog.show();

    }
    private void setupDB(){
        dbHelper=new MyDbHelper(mContext);
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zoom_tag:
                    setupAlertTag();
                break;
        }
    }

}
