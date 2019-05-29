package ibrojim.walkme.firstproject.walkme.Account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ibrojim.walkme.firstproject.walkme.Home.HomeActivity;
import ibrojim.walkme.firstproject.walkme.R;

public class FragmentLogin extends Fragment {

    private Button login;
    private EditText editPhone;
    private TextView noAccount;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,container,false);
        setupAuth();
        setupView(view);
        return view;
    }
    private void setupAuth(){
        mAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    Intent intent=new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                }else {

                }
            }
        };
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser!=null){
            ((AccountActivity)getActivity()).nextFragmentRegistry();
        }
    }

    private void setupView(View view){
        noAccount=view.findViewById(R.id.create_account);
        editPhone=view.findViewById(R.id.edit_phone);
        login=view.findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();

            }
        });
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AccountActivity)getActivity()).nextFragment();
            }
        });
    }
    private void sendVerificationCode(){
        String phone=editPhone.getText().toString();
        if(phone.isEmpty()|| phone.length()<10){
            editPhone.setError(" Проверьте номер");
            editPhone.requestFocus();
            return;
        }
        ((AccountActivity)getActivity()).nextFragmentLogin(phone);

    }



}
