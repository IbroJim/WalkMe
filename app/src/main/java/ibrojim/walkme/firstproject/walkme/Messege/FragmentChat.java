package ibrojim.walkme.firstproject.walkme.Messege;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ibrojim.walkme.firstproject.walkme.Model.Messege;
import ibrojim.walkme.firstproject.walkme.R;
import ibrojim.walkme.firstproject.walkme.Util.MessegeAdapter;

public class FragmentChat extends Fragment {

    private DatabaseReference chatRef;
    private ImageButton send;
    private EditText editMessege;
    private RecyclerView messegeRecyclerView;

    private final String myKey="user106";
    private List<Messege> messeges=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private MessegeAdapter messegeAdapter;
    private DatabaseReference messegeRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_chat,container,false);
        setupView(view);
        createChat();
        return view;
    }

    private void setupView(View view){

        messegeRecyclerView=(RecyclerView) view.findViewById(R.id.recycler_messege);
        editMessege=view.findViewById(R.id.edit_meesege);
        send =view.findViewById(R.id.send_messege);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessege();
            }
        });


        layoutManager= new LinearLayoutManager(getActivity());
        messegeAdapter=new MessegeAdapter(messeges);
        messegeRecyclerView.setHasFixedSize(true);
        messegeRecyclerView.setLayoutManager(layoutManager);
        messegeRecyclerView.setAdapter(messegeAdapter);
        loadMesseges();
    }

    private void loadMesseges()
    {
        messegeRef=FirebaseDatabase.getInstance().getReference();
        messegeRef.child("Messages").child(myKey).child(getKey()).addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

               Messege messege=dataSnapshot.getValue(Messege.class);
               messeges.add(messege);
               messegeAdapter.notifyDataSetChanged();
               editMessege.setText(" ");
           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }


    private String getKey() {
        String key=((MessegeActivity)getActivity()).getID();
        return key;
    }
    private void createChat() {
        chatRef= FirebaseDatabase.getInstance().getReference();
        chatRef.child("Chat").child(myKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(getKey())) {

                    Map chatAddMap=new HashMap();
                    chatAddMap.put("seen",false);
                    chatAddMap.put("timeStamp", ServerValue.TIMESTAMP);

                    Map chatUserMap=new HashMap();
                    chatUserMap.put("Chat/"+myKey+"/"+getKey()+"/",chatAddMap);
                    chatUserMap.put("Chat/"+getKey()+"/"+myKey+"/",chatAddMap);
                    chatRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if(databaseError!=null){
                                Log.d("KEY","Ошибка"+databaseError.getMessage().toString());
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void sendMessege() {
        String messege=editMessege.getText().toString();
        if(!TextUtils.isEmpty(messege)){
            String current_user_ref="Messages/"+myKey+"/"+getKey();
            String chat_user_ref="Messages/"+getKey()+"/"+myKey;

            DatabaseReference user_message_push=chatRef.child("Messages").child(myKey).child(getKey()).push();

            String push_id=user_message_push.getKey();


            Map messageMap=new HashMap();
            messageMap.put("message",messege);
            messageMap.put("seen",false);
            messageMap.put("type","text");
            messageMap.put("time",ServerValue.TIMESTAMP);
            messageMap.put("from",myKey);

            Map messageUserMap=new HashMap();
            messageUserMap.put(current_user_ref+"/"+push_id,messageMap);
            messageUserMap.put(chat_user_ref+"/"+push_id,messageMap);
            chatRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if(databaseError!=null){
                        Log.d("KEY","Ошибка"+databaseError.getMessage().toString());
                    }
                }
            });

        }
    }
}
