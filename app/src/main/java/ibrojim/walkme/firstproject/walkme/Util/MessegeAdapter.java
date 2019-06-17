package ibrojim.walkme.firstproject.walkme.Util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ibrojim.walkme.firstproject.walkme.Model.Messege;
import ibrojim.walkme.firstproject.walkme.R;

public class MessegeAdapter extends RecyclerView.Adapter<MessegeAdapter.MessegeViewHolder> {

    private List<Messege> messeges;
    private String userID="user106";

    public MessegeAdapter(List<Messege> list) {
        this.messeges = list;
    }

    @NonNull
    @Override
    public MessegeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item,viewGroup,false);
        return new MessegeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessegeViewHolder messegeViewHolder, int i) {
        Messege mes=messeges.get(i);
        if(mes.getFrom().equals(userID)) {
         messegeViewHolder.myMessege.setText(mes.getMessege());
         messegeViewHolder.messegeText.setVisibility(View.GONE);
         messegeViewHolder.imageProfil.setVisibility(View.GONE);
        }else{
            messegeViewHolder.messegeText.setText(mes.getMessege());
           messegeViewHolder.myMessege.setText(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return messeges.size();
    }


    public class MessegeViewHolder extends RecyclerView.ViewHolder{

        private TextView messegeText,myMessege;
        private CircleImageView imageProfil;

        public MessegeViewHolder(@NonNull View itemView) {
            super(itemView);

            messegeText=itemView.findViewById(R.id.messege);
            imageProfil=itemView.findViewById(R.id.image_user);
            myMessege=itemView.findViewById(R.id.my_message);



        }


    }


}
