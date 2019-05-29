package ibrojim.walkme.firstproject.walkme.Util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import ibrojim.walkme.firstproject.walkme.Home.HomeActivity;
import ibrojim.walkme.firstproject.walkme.Notification.NotificationActivity;
import ibrojim.walkme.firstproject.walkme.R;
import ibrojim.walkme.firstproject.walkme.Messege.MessegeActivity;
import ibrojim.walkme.firstproject.walkme.Search.SearchActivity;

public class BottomNavigationViewExHelper {

    public  static void setupBottomNavigationEx(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }
    public static void enableBottom(final BottomNavigationViewEx bottomNavigationViewEx, final Context context, final int positionActivity){
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.icon_home:
                        if(positionActivity!=0){
                            Intent intent=new Intent(context,HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            break;
                        }
                        break;
                    case R.id.icon_search:
                        if(positionActivity!=1){
                            Intent intent=new Intent(context,SearchActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            break;
                        }
                        break;
                    case R.id.icon_rating:
                        if(positionActivity!=2){
                            Intent intent=new Intent(context, MessegeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            break;
                        }
                        break;
                    case R.id.icon_notification:
                        if(positionActivity!=3){
                            Intent intent=new Intent(context,NotificationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            break;
                        }
                        break;
                }

                return false;
            }
        });
    }
}
