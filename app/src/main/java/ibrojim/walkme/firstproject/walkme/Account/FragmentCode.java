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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import ibrojim.walkme.firstproject.walkme.Home.HomeActivity;
import ibrojim.walkme.firstproject.walkme.R;

public class FragmentCode extends Fragment {

    public    static final String KEY = "phone";
    public    static final String KEY_INT = "0";
    private EditText editCode;
    private Button confirm;


    private FirebaseAuth mAuth;
    private String codeSent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_code,container,false);
        mAuth=FirebaseAuth.getInstance();
        sendVerificationCode();
        setupView(view);
        return view;
    }
    private void setupView(View view){
        editCode=view.findViewById(R.id.code);
        final String code=editCode.getText().toString();
        confirm=view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    verifySignInCode();
            }
        });
    }
    private void verifySignInCode(){
        String code=editCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }
    private String getPhone(){

        String phone = getArguments().getString(KEY);
        Log.d("KEY"," " +codeSent);
        return phone;
    }
    private Integer getKey(){
        Integer i = getArguments().getInt(KEY_INT);
        Log.d("KEY"," " +i);
        return i;
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (getKey()==1){
                                Intent intent=new Intent(getActivity(),HomeActivity.class);
                                startActivity(intent);
                            }else{
                                ((AccountActivity)getActivity()).nextFragmentRegistry();
                            }

                        }else {
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                              Toast.makeText(getActivity()," Произошла ошибка",Toast.LENGTH_LONG).show();
                        }
                    }

                });

    }
    private void sendVerificationCode(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                getPhone(),        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks);
    }
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent=s;
        }
    };


}
