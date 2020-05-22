package com.example.utkarshkushwaha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
   static DatabaseReference firebaseDatabase;
    static ArrayList<String> chats;
    static ArrayList<String> senders;
    static chatAdapter adapter;
     static List<String> chatList;
     static RecyclerView recyclerView;
     static Context mContext;
     static int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase=FirebaseDatabase.getInstance().getReference().child("123456");
        chats=new ArrayList<String>();
        senders=new ArrayList<String>();
        chatList=new ArrayList<String>();
        mContext=this;
        relativeLayout=(RelativeLayout)findViewById(R.id.rel);
        recyclerView=findViewById(R.id.recyclerView);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("yes, it","is clicked indeed");
                load_chats();

                display_chats();
            }
        });
        recyclerView.setHasFixedSize(true);  //experiment with this please
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void display_chats(){

        Log.i("chats",chatList.toString());
    }
  static  public void load_chats(){
  Log.i("it gets called","yes");
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("its working","yes");
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren()) {
//                         if(String.valueOf(i).equals(dataSnapshot1.getKey()))
//                          Log.i("required key:",dataSnapshot1.getKey());
                        for(DataSnapshot dataSnapshot3:dataSnapshot2.getChildren()) {
                            if(String.valueOf(i).equals(dataSnapshot2.getKey())) {
                                Log.i("required key:", dataSnapshot2.getKey());
                                Log.i(dataSnapshot3.getKey(), dataSnapshot3.getValue().toString());
                                chatList.add(dataSnapshot3.getValue().toString());
                                senders.add(dataSnapshot3.getKey());
                                break;
                            }
                            //   arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }
                Log.i("chats",chatList.toString());
                Log.i("senders",senders.toString());
                i++;

        adapter= new chatAdapter(mContext,chatList,senders);
        recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
