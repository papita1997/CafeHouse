package com.pawan.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pawan.myapplication.R;
import com.pawan.myapplication.SingleCategoryList;
import com.pawan.myapplication.ViewItems;
import com.pawan.myapplication.model.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    ArrayList<CategoryModel> list;
    Context context;
    String unique;
    public CategoryAdapter(Context context, ArrayList<CategoryModel> list,String unique) {
        this.list = list;
        this.context = context;
        this.unique=unique;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.category_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CategoryModel model=list.get(position);
        Glide.with(context).load(model.getPicUrl()).into(holder.cdfoodimg);
        holder.cdbtn.setText(model.getText());
//        Log.d("food","data bind");
        holder.cdfoodimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(unique.equals("CategoryList")) {
                    Intent intent = new Intent(context, SingleCategoryList.class);
                    intent.putExtra("categoryName", model.getText());
                    context.startActivity(intent);
                }
                else{
                    Intent intent=new Intent(context, ViewItems.class);
                    intent.putExtra("idMeal",model.getMealId());
                    context.startActivity(intent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cdfoodimg;
        TextView cdbtn;
        public ViewHolder(View itemView) {
            super(itemView);
            cdfoodimg=itemView.findViewById(R.id.cdfoodimg);
            cdbtn=itemView.findViewById(R.id.cdbtn);
        }
    }
}
