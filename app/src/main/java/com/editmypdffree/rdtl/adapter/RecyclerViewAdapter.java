package com.editmypdffree.rdtl.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.editmypdffree.rdtl.R;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<com.editmypdffree.rdtl.adapter.RecyclerViewAdapter.MyViewHolder>{


    private Context mContext;
//    private List<SettersAndGetters> mData;
    private ArrayList<String> mData;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflator= LayoutInflater.from(mContext);
        view=mInflator.inflate(R.layout.imagelist_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        //holder.tv_press_title.setText(mData.get(position).getTitle());
        holder.img_press_thumbnail.setImageURI(Uri.parse(mData.get(position)));
       // Toast.makeText(mContext, "jj: "+mData.get(position).getThumbnail(), Toast.LENGTH_SHORT).show();
      //  Log.e("jj",""+mData.get(position).getThumbnail());


        Uri imgUri=Uri.parse(""+mData.get(position));
        holder.img_press_thumbnail.setImageURI(null);
        holder.img_press_thumbnail.setImageURI(imgUri);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);

            }
        });

        //setting onclick listener
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent=new Intent(mContext, DetailsActivity.class);
////                passing data to book activity
//                intent.putExtra("Title",mData.get(position).getTitle());
//                intent.putExtra("Category",mData.get(position).getCategory());
//                intent.putExtra("Description",mData.get(position).getDescription());
//
//                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
//
////                start the activity
//                mContext.startActivity(intent);
//
//
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

//        TextView tv_press_title;
        ImageView img_press_thumbnail, deleteBtn;
        //        cardview to enable onclick
//        CardView cardView;



        public MyViewHolder(View itemView) {
            super(itemView);
//            tv_press_title=(TextView)itemView.findViewById(R.id.press_title_id);
            img_press_thumbnail=(ImageView)itemView.findViewById(R.id.idListIV);
            deleteBtn=(ImageView)itemView.findViewById(R.id.deleteImage);

            //cardView=(CardView)itemView.findViewById(R.id.cardview1_id);
        }
    }


    public void removeAt(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }

}