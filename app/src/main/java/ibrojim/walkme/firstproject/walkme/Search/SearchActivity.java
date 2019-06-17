package ibrojim.walkme.firstproject.walkme.Search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ibrojim.walkme.firstproject.walkme.Account.FragmentCode;
import ibrojim.walkme.firstproject.walkme.Messege.FragmentChat;
import ibrojim.walkme.firstproject.walkme.Messege.MessegeActivity;
import ibrojim.walkme.firstproject.walkme.Model.UserSearchInformation;
import ibrojim.walkme.firstproject.walkme.R;
import ibrojim.walkme.firstproject.walkme.Util.BottomNavigationViewExHelper;
import me.gujun.android.taggroup.TagGroup;

public class SearchActivity extends AppCompatActivity {
    private static final int ACTIVITY_NUM=1;
    private static final String GENDER="gender";

    private Context mContext=SearchActivity.this;
    private FirebaseDatabase database;
    private DatabaseReference myRef,chatRef;
    private ArrayList<UserSearchInformation> userList=new ArrayList<>();
    private ArrayList<UserSearchInformation> userSortGenderList=new ArrayList<>();
    private ArrayList<String> keyList =new ArrayList<>();
    private ArrayList<String> keySort=new ArrayList<>();
    private String key;



    private ImageButton next, invite;
    private TextView myself,name ,surname, age,areya;
    private TagGroup interest;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seacrh);
        setupBottomNavigationView();
        setupView();
        setupFirebase();
    }


    private void setupView(){
        progressBar=findViewById(R.id.searh_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        myself=findViewById(R.id.myself);
        name=findViewById(R.id.name);
        surname=findViewById(R.id.surname);
        age=findViewById(R.id.age);
        areya=findViewById(R.id.areya);
        interest=(TagGroup) findViewById(R.id.interest);
        next=findViewById(R.id.next);
        invite=findViewById(R.id.invite);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomUser();
            }
        });
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChat();
            }
        });
    }
    private void openChat() {
        Log.d("KEY",key);
        Intent intent=new Intent(mContext,MessegeActivity.class);
        intent.putExtra(MessegeActivity.KEY,key);
        startActivity(intent);
    }


    private void setupFirebase(){
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot keyNode: dataSnapshot.getChildren()){
                    UserSearchInformation user=keyNode.getValue(UserSearchInformation.class);
                    keyList.add(keyNode.getKey());
                    userList.add(user);

                }
                sortUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void sortUser(){
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getGender().equals("W")) {
                userSortGenderList.add(userList.get(i));
                keySort.add(keyList.get(i));
            }
        }
        randomUser();
    }
    private void randomUser() {
        Random random=new Random();
        final int i=random.nextInt(userSortGenderList.size());
        bindingUI(i);
    }

    private void bindingUI(final int i){
        key=keyList.get(i);
        name.setText(userSortGenderList.get(i).getName());
        surname.setText(userSortGenderList.get(i).getSurname());
        String ageStr=userSortGenderList.get(i).getAge().toString();
        if(ageStr!=null){
            age.setText(ageStr);
        }

        areya.setText(userSortGenderList.get(i).getAreya());
        myself.setText(userSortGenderList.get(i).getMyself());
        setupInterest(userSortGenderList.get(i).getTags());
        myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupAlertMyself(userSortGenderList.get(i).getMyself());
            }
        });
    }
    private void setupInterest(HashMap<String,String> hashMap){
        final ArrayList<String> tagList=new ArrayList<>();
        for (String tag: hashMap.values()) {
            tagList.add(tag);
        }
        interest.setTags(tagList);
        interest.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                setupAlertInterest(tagList);
            }
        });
        progressBar.setVisibility(View.GONE);
    }



    private void checkGender(){
        for(int i=0;i<userList.size();i++){
            if(getUserGender()=="M"){
               if(userList.get(i).getGender().equals("W")){
                   userSortGenderList.add(userList.get(i));
               }
            }else {
                if (userList.get(i).getGender().equals("M")){
                    userSortGenderList.add(userList.get(i));
                }
            }
        }
    }


    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx) findViewById(R.id.bottom_navigation_view_ex);
        BottomNavigationViewExHelper.setupBottomNavigationEx(bottomNavigationViewEx);
        BottomNavigationViewExHelper.enableBottom(bottomNavigationViewEx,mContext,ACTIVITY_NUM);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
    private String getUserGender() {
        SharedPreferences sharedPreferences = getSharedPreferences("myToken", Context.MODE_PRIVATE);
        return sharedPreferences.getString(GENDER, "");
    }

    //setup AlertDialog
    private void setupAlertMyself(String myself){
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        View view= getLayoutInflater().inflate( R.layout.alert_myself_search, null);
        TextView textView=view.findViewById(R.id.myself_alert_dialog);
        textView.setText(myself);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    private void setupAlertInterest(ArrayList<String> tagList) {
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        View view= getLayoutInflater().inflate( R.layout.alert_interest_search, null);
        TagGroup tagGroup=(TagGroup) view.findViewById(R.id.interest_alert_dialog);
        tagGroup.setTags(tagList);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

}
