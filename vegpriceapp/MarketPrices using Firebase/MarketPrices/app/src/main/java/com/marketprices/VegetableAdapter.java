package com.marketprices;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VegetableAdapter extends RecyclerView.Adapter<VegetableAdapter.VegetableViewHolder> {
    private List<VegetableItem> vegetableList;
    private static final HashMap<String,Integer> hash = MainActivity.getHash();
    public static class VegetableViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        public final TextView mTextView1;
        public final TextView mTextView3;
        public final TextView mTextView5;
        public final TextView mTextView7;

        public VegetableViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView3 = itemView.findViewById(R.id.textView3);
            mTextView5 = itemView.findViewById(R.id.textView5);
            mTextView7 = itemView.findViewById(R.id.textView7);
        }
    }
    public VegetableAdapter(){
        vegetableList=new ArrayList<>();
    }
    public VegetableAdapter(List<VegetableItem> vegetableList) {
        this.vegetableList = vegetableList;
    }
    public void setData(List<VegetableItem> data) {
        this.vegetableList.addAll(data);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public VegetableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vegetable_item, parent, false);
        return new VegetableViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VegetableViewHolder holder, int position){
        VegetableItem currentItem = vegetableList.get(position);
        String vegName = currentItem.getVegname();
        holder.mTextView1.setText(vegName); // set vegname
        if(hash.containsKey(vegName)) {     // set vegetable Image
            holder.mImageView.setImageResource(hash.get(vegName));
        } else {
            holder.mImageView.setImageResource(R.drawable.logo);
        }
        holder.mTextView3.setText(currentItem.getMarket()); // set Market price
        holder.mTextView5.setText(currentItem.getRetail()); // set Retail price
        holder.mTextView7.setText(currentItem.getShopping()); // set Shopping price
    }

    @Override
    public int getItemCount() {
        return vegetableList.size();
    }
}