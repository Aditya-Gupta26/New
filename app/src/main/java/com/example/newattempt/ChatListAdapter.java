package com.example.newattempt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolde> {

    private List<ChatsList> userList;

    public ChatListAdapter(List<ChatsList> userList){this.userList=userList;}

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new ViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, int position) {

        int resource = userList.get(position).getImageView();
        String name =  userList.get(position).getName();
        String msg = userList.get(position).getRecentChat();
        String time = userList.get(position).getLastSeen();

        holder.setData(resource, name, msg, time);
    }

    @Override
    public int getItemCount() {

        return userList.size();
    }
    public class ViewHolde extends RecyclerView.ViewHolder{


        private ImageView image;
        private TextView contact ;
        private TextView lastSeen ;
        private TextView lastmsg ;


        public ViewHolde(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
            contact = itemView.findViewById(R.id.name);
            lastSeen = itemView.findViewById(R.id.lastSeen);
            lastmsg = itemView.findViewById(R.id.recentChat);

        }

        public void setData(int resource, String name, String msg, String time) {

            image.setImageResource(resource);
            contact.setText(name);
            lastmsg.setText(msg);
            lastSeen.setText(time);




        }
    }
}
