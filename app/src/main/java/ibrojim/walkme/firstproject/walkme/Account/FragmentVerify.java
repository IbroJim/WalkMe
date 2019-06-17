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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import ibrojim.walkme.firstproject.walkme.Home.HomeActivity;
import ibrojim.walkme.firstproject.walkme.R;

public class FragmentVerify extends Fragment {

    private Button login;
    private EditText editPhone;
    public Integer NO_ACCOUNT=0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_phone,container,false);
        setupView(view);
        return view;
    }


    private void setupView(View view){
     editPhone=view.findViewById(R.id.number);
     login=view.findViewById(R.id.confirm);
     login.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             sendVerificationCode();

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
        ((AccountActivity)getActivity()).nextFragmentLogin(phone,NO_ACCOUNT);

    }



}
