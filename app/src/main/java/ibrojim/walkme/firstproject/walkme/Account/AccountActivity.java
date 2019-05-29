package ibrojim.walkme.firstproject.walkme.Account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ibrojim.walkme.firstproject.walkme.R;

public class AccountActivity extends AppCompatActivity {

    private Fragment fragmentVerify,fragmentCode, fragmentCreateUser,fragmentLogin;
    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setupFragment();
    }
    private void setupFragment(){
        fragmentLogin=new FragmentLogin();
        fragmentVerify=new FragmentVerify();
        fragmentCode=new FragmentCode();
        fm=getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragmentContainer,fragmentLogin).commit();
    }
    public void nextFragment(String string){
        Bundle bundle = new Bundle();
        bundle.putString(FragmentCode.KEY, string);
        fragmentCode.setArguments(bundle);
        fm.beginTransaction().replace(R.id.fragmentContainer,fragmentCode).commit();
    }
    public void nextFragment(){
             fm.beginTransaction().replace(R.id.fragmentContainer,fragmentVerify).commit();
    }
    public void nextFragmentLogin(String string){
        Bundle bundle = new Bundle();
        Bundle bundle1= new Bundle();
        bundle1.putInt(FragmentCode.KEY_INT,1);
        bundle.putString(FragmentCode.KEY, string);
        fragmentCode.setArguments(bundle);
        fm.beginTransaction().replace(R.id.fragmentContainer,fragmentCode).commit();
    }
    public void nextFragmentRegistry(){
        fragmentCreateUser=new FragmentCreateUser();
        fm.beginTransaction().replace(R.id.fragmentContainer,fragmentCreateUser).commit();
    }

}
