package com.example.utkarshkushwaha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public  class  MainActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
   static DatabaseReference firebaseDatabase;
    static ArrayList<String> chats;
    static ArrayList<String> senders;
    static chatAdapter adapter;
     static List<String> chatList;
     static RecyclerView recyclerView;
     static Context mContext;
     static int i=1;
     static List<String> imgs;
     static ImageView imageView3;
    static Button button;

    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase=FirebaseDatabase.getInstance().getReference().child("123456");
        chats=new ArrayList<String>();
        senders=new ArrayList<String>();
        chatList=new ArrayList<String>();
        imgs=new ArrayList<String>();
        mContext=this;
        imageView3=findViewById(R.id.imageView3);
        button=findViewById(R.id.retry);
        relativeLayout=findViewById(R.id.rel);
        recyclerView=findViewById(R.id.recyclerView);



        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("yes, it","is clicked indeed");
                load_chats(v);

                display_chats();
            }
        });
        recyclerView.setHasFixedSize(true);  //experiment with this please
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter= new chatAdapter(mContext,chatList,senders,imgs);
        recyclerView.setAdapter(adapter);

    }

    public void display_chats(){

        Log.i("chats",chatList.toString());
    }
  static  public void load_chats(View view){
  Log.i("it gets called","yes");

      ConnectivityManager manager=(ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo activeNetwork=manager.getActiveNetworkInfo();
      if(activeNetwork==null){
          Toast.makeText(mContext, "Please connect to the internet!", Toast.LENGTH_SHORT).show();
          recyclerView.setClickable(false);
      }else {
          recyclerView.setClickable(true);
          firebaseDatabase.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  Log.i("its working", "yes");
                  for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                      for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                          for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                              if (String.valueOf(i).equals(dataSnapshot2.getKey())) {
                                  if (dataSnapshot3.getKey().equals("Radhika") || dataSnapshot3.getKey().equals("Harry")) {
                                      Log.i("required key:", dataSnapshot2.getKey());
                                      Log.i(dataSnapshot3.getKey(), dataSnapshot3.getValue().toString());
                                      chatList.add(dataSnapshot3.getValue().toString());
                                      senders.add(dataSnapshot3.getKey());
                                      imgs.add("");

                                      break;
                                  } else {

                                      for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                          Log.i("Image sender", dataSnapshot4.getKey());
                                          Log.i("image url", dataSnapshot4.getValue().toString());
                                          chatList.add("_imagz_");
                                          senders.add(dataSnapshot4.getKey());
                                          imgs.add(dataSnapshot4.getValue().toString());

                                          break;
                                      }
                                  }
                              }
                          }
                      }
                  }
                  Log.i("chats", chatList.toString());
                  Log.i("senders", senders.toString());
                  Log.i("images", imgs.toString());
                  i++;
                  recyclerView.setVisibility(View.VISIBLE);
                  button.setVisibility(View.GONE);
                  imageView3.setVisibility(View.GONE);
                  adapter.notifyItemInserted(adapter.getItemCount());
                  recyclerView.scrollToPosition(adapter.getItemCount() - 1);

              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {
              }
          });
      }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectivityManager manager=(ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=manager.getActiveNetworkInfo();
        if(activeNetwork==null){
            recyclerView.setClickable(false);
            recyclerView.setVisibility(View.GONE);
            imageView3.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            Toast.makeText(mContext, "Please connect to the internet!", Toast.LENGTH_SHORT).show();
        }
    }

    public  static void check_connection(){
        ConnectivityManager manager=(ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=manager.getActiveNetworkInfo();
        if(activeNetwork==null){
            Toast.makeText(mContext, "Please connect to the internet!", Toast.LENGTH_SHORT).show();
        }
    }

}
