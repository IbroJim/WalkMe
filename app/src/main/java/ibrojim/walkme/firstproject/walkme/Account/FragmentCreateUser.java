package ibrojim.walkme.firstproject.walkme.Account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ibrojim.walkme.firstproject.walkme.Home.HomeActivity;
import ibrojim.walkme.firstproject.walkme.Model.User;
import ibrojim.walkme.firstproject.walkme.R;
import io.ghyeok.stickyswitch.widget.StickySwitch;

public class FragmentCreateUser extends Fragment {

    private EditText editName, editSurname;
    private StickySwitch aSwitch;
    private Spinner spinnerArea, spinnerAge;
    private Button registry;
    private ProgressBar progressBar;


    private String areya;
    private Integer age;
    private FirebaseDatabase database;
    private DatabaseReference myRef,myRefTwo;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_create_user, container,false);
        seupFirebaseDatabase();
        setupView(view);
        setupSpinnerAge();
        setupSpinnerArea();
        return view;
    }
    private void seupFirebaseDatabase(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    }
    private void setupView(View view){
        editName=view.findViewById(R.id.edit_text_name);
        editSurname=view.findViewById(R.id.edit_text_surname);
        spinnerAge=view.findViewById(R.id.spinner_age);
        spinnerArea=view.findViewById(R.id.spinner_area);
        registry=view.findViewById(R.id.registry);
        progressBar=view.findViewById(R.id.progress_registry);
        progressBar.setVisibility(View.GONE);
        registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupCreateUser();
            }
        });
    }
    private void setupSpinnerArea(){
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getActivity(),R.array.areya, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(adapter);
        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areya= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setupSpinnerAge(){
        List<Integer> list=new ArrayList<>();
        for (int i=16;i<100;i++){
            list.add(i); }
        ArrayAdapter<Integer> adapter=new ArrayAdapter<Integer>(getActivity(), R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapter);
        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age= Integer.valueOf(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void setupCreateUser() {
        if (editName.getText().toString().isEmpty() || editSurname.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Заполните поля", Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        User user = new User(editName.getText().toString(), editSurname.getText().toString(), areya, age);
        myRef.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(getActivity(),HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

    }





}
