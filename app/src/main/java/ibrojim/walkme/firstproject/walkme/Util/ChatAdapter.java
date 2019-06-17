package ibrojim.walkme.firstproject.walkme.Util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.Query;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<String> usersList;

    public ChatAdapter(List<String> usersList) {
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public   class ChatViewHolder extends RecyclerView.ViewHolder{
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
