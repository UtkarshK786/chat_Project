package com.example.utkarshkushwaha;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.chatViewHolder>{

    private Context context;
    private List<String> chatList;
    private List<String> sendersList;

    public chatAdapter(Context context,List<String> chatList, List<String> sendersList){
        this.context=context;
        this.chatList=chatList;
        this.sendersList=sendersList;
    }

    @NonNull
    @Override
    public chatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.chat_layout,null);
        chatViewHolder holder=new chatViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull chatViewHolder holder, int position) {
           String cht=chatList.get(position);
           if(sendersList.get(position).equals("Radhika")) {
               holder.textView3.setText(cht);
               holder.textView3.setVisibility(View.VISIBLE);
           }
           else{
               holder.textView4.setText(cht);
               holder.textView4.setVisibility(View.VISIBLE);
           }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class chatViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView, imageView2;
        TextView textView3, textView4;
        public chatViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView);
            textView3=itemView.findViewById(R.id.textView3);
            textView4=itemView.findViewById(R.id.textView4);
            imageView2=itemView.findViewById(R.id.imageView2);

        }
    }
}
