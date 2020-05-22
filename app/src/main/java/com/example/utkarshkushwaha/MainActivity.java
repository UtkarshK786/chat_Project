package com.example.utkarshkushwaha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference firebaseDatabase;
    ArrayList<String> chats;
    ArrayList<String> senders;
    ArrayAdapter arrayAdapter;
    chatAdapter adapter;
    List<String> chatList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase=FirebaseDatabase.getInstance().getReference().child("123456");
        // listView=findViewById(R.id.listView);
        chats=new ArrayList<String>();
        senders=new ArrayList<String>();
        chatList=new ArrayList<String>();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);  //experiment with this please
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,chats);
//        listView.setAdapter(arrayAdapter);
        adapter= new chatAdapter(this,chatList,senders);
        recyclerView.setAdapter(adapter);
        load_chats();
        display_chats();
    }

    public void display_chats(){

        Log.i("chats",chatList.toString());
    }
    public void load_chats(){

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("its working","yes");
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren()) {
                        for(DataSnapshot dataSnapshot3:dataSnapshot2.getChildren()) {
                            Log.i(dataSnapshot3.getKey().toString(), dataSnapshot3.getValue().toString());
                            chatList.add(dataSnapshot3.getValue().toString());
                            senders.add(dataSnapshot3.getKey().toString());

                            //   arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }
                adapter= new chatAdapter(MainActivity.this,chatList,senders);
                recyclerView.setAdapter(adapter);
//                Log.i("chats",chatList.toString());
//                Log.i("senders",senders.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
