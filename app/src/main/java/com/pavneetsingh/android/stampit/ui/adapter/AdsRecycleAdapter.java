package com.pavneetsingh.android.stampit.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pavneetsingh.android.stampit.R;
import com.pavneetsingh.android.stampit.model.PostBean;

import java.util.List;



/**
 * Created by Pavneet Singh on 05/05/18.
 * Contact : dev.pavneet@gmail.com
 */

public class AdsRecycleAdapter extends  RecyclerView.Adapter<AdsRecycleAdapter.ViewHolder>  {

    private List<PostBean> postList;
    private int rowLayout;
    private LayoutInflater layoutInflater;
    private static final String TAG = "NewsRecycleAdapter";
    private RecyclerViewCallbacks callbacks;
    private Context context;

    public AdsRecycleAdapter( Context context,RecyclerViewCallbacks callbacks, int resource, List<PostBean> objects) {
        this.postList = objects;
        this.rowLayout = resource;
        this.callbacks = callbacks;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        return new ViewHolder(layoutInflater.inflate(R.layout.list_item_photos,parent,false),callbacks);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostBean postBean = postList.get(position);
        String thumbnailURL ;
        holder.itemTitle.setText(postBean.getName());
        holder.itemCost.setText(String.valueOf(postBean.getCost())+" $");
        holder.itemDesc.setText("Description\n"+String.valueOf(postBean.getDesc()));
        Glide.with(context).load(postBean.getUrl()).into(holder.imageView);
    }

    public PostBean getNewsObject(int pos){
        return postList.get(pos);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void updateData(List<PostBean> tasks) {
        setList(tasks);
        notifyDataSetChanged();
    }

    private void setList(List<PostBean> tasks) {
        postList = tasks;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView itemTitle,itemCost,itemDesc;
        ImageView imageView;
        private RecyclerViewCallbacks callbacks;

        public ViewHolder(View itemView , RecyclerViewCallbacks callbacks) {
            super(itemView);
            this.callbacks = callbacks;
            itemView.setOnClickListener(this);
            itemTitle = (TextView) itemView.findViewById(R.id.li_post_title);
            itemDesc = (TextView) itemView.findViewById(R.id.li_post_desc);
            itemCost = (TextView) itemView.findViewById(R.id.li_post_cost);
            imageView = (ImageView) itemView.findViewById(R.id.post_image);
        }

        @Override
        public void onClick(View view) {
            callbacks.itemClick(getAdapterPosition(),view);
        }
    }
    public interface RecyclerViewCallbacks{
        void itemClick(int position,View view);
    }
}
