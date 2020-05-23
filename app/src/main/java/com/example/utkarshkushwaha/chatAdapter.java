package com.example.utkarshkushwaha;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.BitmapFactory.decodeFile;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.chatViewHolder>{

    private Context context;
    private List<String> chatList;
    private List<String> sendersList;
    private List<String> imgs;
    private int img_or_not;
    private FirebaseStorage firebaseStorage;

    public chatAdapter(Context context, List<String> chatList, List<String> sendersList, List<String> imgs){
        this.context=context;
        this.chatList=chatList;
        this.sendersList=sendersList;
        this.imgs=imgs;
//        this.img_or_not=img_or_not;
    }



    @NonNull
    @Override
    public chatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        chatViewHolder holder = null;
        View view = inflater.inflate(R.layout.chat_layout, null);
            holder = new chatViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final chatViewHolder holder, int position) {
        String cht = chatList.get(position);
        if (sendersList.get(position).equals("Harry")) {
             if(chatList.get(position).equals("")){
                 String imag=imgs.get(position);
                 firebaseStorage = FirebaseStorage.getInstance();
                 final StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://blushed-chat.appspot.com/Radhika:Harry/imgs/"+imag);
                 Log.i("storage","gs://blushed-chat.appspot.com/Radhika:Harry/imgs/"+cht);
                 try {
                     final File file = File.createTempFile("image", "jpg");
                     storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                             Bitmap bitmap = decodeFile(file.getAbsolutePath());
                             holder.imageView.setImageBitmap(bitmap);
                             holder.textView3.setVisibility(View.GONE);
                             holder.rel2.setVisibility(View.VISIBLE);

                             Log.i("Stroage reference", bitmap.toString());

                         }
                     });

                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }else {
                 holder.textView3.setText(cht);
                 holder.rel2.setVisibility(View.VISIBLE);
                 holder.imageView.setVisibility(View.GONE);
             }

        } else {
            if(chatList.get(position).equals("")) {
                String imag = imgs.get(position);
                firebaseStorage = FirebaseStorage.getInstance();
                final StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://blushed-chat.appspot.com/Radhika:Harry/imgs/" + imag);
                Log.i("storage", "gs://blushed-chat.appspot.com/Radhika:Harry/imgs/" + imag);
                try {
                    final File file = File.createTempFile("image", "jpg");
                    storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = decodeFile(file.getAbsolutePath());
                            holder.imageView2.setImageBitmap(bitmap);
                            holder.textView4.setVisibility(View.GONE);
                            holder.rel1.setVisibility(View.VISIBLE);
                            Log.i("Stroage reference", bitmap.toString());

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                    holder.textView4.setText(cht);
                    holder.imageView2.setVisibility(View.GONE);
                holder.rel1.setVisibility(View.VISIBLE);
                }

        }
    }



    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class chatViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView, imageView2;
        TextView textView3, textView4;
        RelativeLayout rel1,rel2;
        public chatViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView);
            textView3=itemView.findViewById(R.id.textView3);
            textView4=itemView.findViewById(R.id.textView4);
            imageView2=itemView.findViewById(R.id.imageView2);
            rel1=itemView.findViewById(R.id.rel1);
            rel2=itemView.findViewById(R.id.rel2);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.load_chats();
                    Log.d("Item","clicked");
                }
            });

        }
    }
}

//                   storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                       @Override
//                       public void onSuccess(byte[] bytes) {
//                           Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                           holder.imageView2.setImageBitmap(bitmap);
//                       }
//                   });
//                   holder.rel2.setVisibility(View.VISIBLE);
//                   holder.imageView2.setVisibility(View.VISIBLE);
