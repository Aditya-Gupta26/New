package com.example.newattempt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity7 extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ChatsList> userlist;
    ChatListAdapter chatListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        initData();
        initRecyclerView();
    }
    private void initData() {

        userlist = new ArrayList<>();

        userlist.add(new ChatsList(R.drawable.ic_launcher_background, "NAME1", "10:00 am", "Hello!"));
        userlist.add(new ChatsList(R.drawable.ic_launcher_foreground, "NAME2", "11:00 am", "Bye!"));
        userlist.add(new ChatsList(R.drawable.ic_launcher_background, "NAME3", "12:00 am", "Abc"));
        userlist.add(new ChatsList(R.drawable.ic_launcher_foreground, "NAME4", "1:00 am", "Why?"));
        userlist.add(new ChatsList(R.drawable.ic_launcher_background, "NAME5", "5:00 am", "random message"));
        userlist.add(new ChatsList(R.drawable.ic_launcher_background, "NAME6", "6:00 am", "a random message"));
        userlist.add(new ChatsList(R.drawable.ic_launcher_background, "NAME7", "11:00 am", "a random number 3478932687562145"));
        userlist.add(new ChatsList(R.drawable.ic_launcher_foreground, "NAME8", "6:00 am", "message"));
        userlist.add(new ChatsList(R.drawable.ic_launcher_foreground, "NAME9", "7:00 am", "completed"));
        userlist.add(new ChatsList(R.drawable.ic_launcher_background, "NAME10", "3:00 am", "recycler view"));

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        chatListAdapter = new ChatListAdapter(userlist);
        recyclerView.setAdapter(chatListAdapter);
        chatListAdapter.notifyDataSetChanged();

    }
}