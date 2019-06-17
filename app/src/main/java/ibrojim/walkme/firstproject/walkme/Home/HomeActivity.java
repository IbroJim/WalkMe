package ibrojim.walkme.firstproject.walkme.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.HashMap;

import ibrojim.walkme.firstproject.walkme.Model.User;
import ibrojim.walkme.firstproject.walkme.R;
import ibrojim.walkme.firstproject.walkme.Util.BottomNavigationViewExHelper;
import me.gujun.android.taggroup.TagGroup;

public class HomeActivity extends AppCompatActivity {


    private Context mContext = HomeActivity.this;
    private static final int ACTIVITY_NUM = 0;
    private static final String GENDER="gender";
    private FirebaseDatabase database;
    private DatabaseReference myRef, myRefMyself;
    private HashMap<String, String> responceTag;
    private ArrayList<String> tagFirebase = new ArrayList<>();
    private String myselfStr;


    private TextView myself;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homee);
        setupBottomNavigationView();
    }
    private void setupBottomNavigationView() {
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation_view_ex);
        BottomNavigationViewExHelper.setupBottomNavigationEx(bottomNavigationViewEx);
        BottomNavigationViewExHelper.enableBottom(bottomNavigationViewEx, mContext, ACTIVITY_NUM);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


    private void setupDatabase() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("tag");
        myRefMyself = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("myself");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    GenericTypeIndicator<HashMap<String, String>> t = new GenericTypeIndicator<HashMap<String, String>>() {
                    };
                    responceTag = dataSnapshot.getValue(t);
                    if (responceTag.isEmpty()) {
                        setupTagGroup();
                        return;
                    }
                    for (String i : responceTag.values()) {
                        Log.d("KEY", " " + i);
                        tagFirebase.add(i);
                    }

                }
                setupTagGroup();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (myRefMyself != null) {
            myRefMyself.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        myselfStr = dataSnapshot.getValue().toString();
                        Log.d("KEY", "" + myselfStr);

                    }
                    setupAlertMySelf();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    //Setup profil
    private void setupMyProfil() {
    DatabaseReference ref = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           showData(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }
    private void showData(DataSnapshot dataSnapshot){
        TextView name=findViewById(R.id.name_user);
        TextView age=findViewById(R.id.age);
        TextView areya=findViewById(R.id.areya);
        TextView surname=findViewById(R.id.edit_text_surname);
        name.setText(dataSnapshot.getValue(User.class).getName());
        surname.setText(dataSnapshot.getValue(User.class).getSurname());
        age.setText(dataSnapshot.getValue(User.class).getAge().toString());
        areya.setText(dataSnapshot.getValue(User.class).getAreya());
        saveUserGender(dataSnapshot.getValue(User.class).getGender());

    }

    //SetupAlertDialog  TagGroup
    private void setupTagGroup() {
        final TagGroup tagGroup=(TagGroup) findViewById(R.id.interest);
        if(tagFirebase.isEmpty()) {
            tagGroup.setTags("Добавить интерес");
        }else {
            tagGroup.setTags(tagFirebase);
        }
        tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
               setupAlertTag();
                    }
        });
    }
    private void setupAlertTag(){
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(mContext);
        View view=getLayoutInflater().inflate(R.layout.dialog_tag_layout,null);
        final ArrayList<String> arrayList=new ArrayList<>();
        final TagGroup tagGroup=(TagGroup) view.findViewById(R.id.dialog_tag);
        final TagGroup tagGroupExample=(TagGroup) view.findViewById(R.id.example_tag_group);
        final EditText editText=(EditText) view.findViewById(R.id.edit_tag);
        final Button button=view.findViewById(R.id.add_tag);
        final  TextView save=view.findViewById(R.id.save);
        final  TextView cansel=view.findViewById(R.id.cancel);
        setupTagInterest(tagGroup,editText,button,arrayList);
        setupTagExample(tagGroupExample);
        mBuilder.setView(view);
        AlertDialog dialog=mBuilder.create();
        setupInsertTagToDatabase(cansel,save,dialog,arrayList);
        dialog.show();


    }
    private void setupTagExample(TagGroup tagGroup){
        final ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Кино");
        arrayList.add("Hip-Hop");
        arrayList.add("Танцы");
        tagGroup.setTags(arrayList);
    }
    private void setupTagInterest(final TagGroup tagGroup, final EditText editText, Button button, final ArrayList<String> arrayList){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add(editText.getText().toString());
                tagGroup.setTags(arrayList);
                editText.setText(" ");

            }
        });


    }
    private void setupInsertTagToDatabase(TextView cancel, TextView save, final AlertDialog dialog, final ArrayList<String> arrayList){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myRef.getDatabase()!=null) {
                    deleteData();
                }
                addToDatabase(arrayList);
                dialog.dismiss();
            }
        });
    }
    private void addToDatabase(ArrayList<String> strings){
        for(String i: strings){
            myRef.push().setValue(i);
        }
    }
    private void deleteData(){
        myRef.setValue(null);
    }

    //SetupAlertDialog Myself
    private void setupAlertMySelf() {
        myself = (TextView) findViewById(R.id.myself);
        if(myselfStr.isEmpty()){
            myself.setText("Введите информацию о себе");
        }
        else { myself.setText(myselfStr);}
        myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(mContext);
                View view=getLayoutInflater().inflate(R.layout.dialog_myself_layout,null);
                final EditText editText=view.findViewById(R.id.edit_myself);
                final TextView save=view.findViewById(R.id.save);
                final TextView cancel=view.findViewById(R.id.cancel);
                mBuilder.setView(view);
                AlertDialog dialog=mBuilder.create();
                setupMySelfToDatabase(save,cancel,dialog,editText);
                dialog.show();
            }
        });
    }
    private void setupMySelfToDatabase(TextView save, TextView cancel, final AlertDialog dialog, final EditText editText){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMySelfData();
                addToDatabaseMyself(editText);
                dialog.dismiss();
            }
        });
    }
    private void deleteMySelfData() {
        myRefMyself.setValue(null);
    }
    private void addToDatabaseMyself(EditText editText) {
        String myslef=editText.getText().toString();
        myRefMyself.setValue(myslef);
    }

    private void saveUserGender(String gender){
        SharedPreferences sharedPreferences1 = getSharedPreferences("gender", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorInt = sharedPreferences1.edit();
        editorInt.putString(GENDER,gender);
        editorInt.apply();
    }





}
