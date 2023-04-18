package com.example.proiect;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    TextView nameView,marketValueView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView= itemView.findViewById(R.id.name);
        marketValueView=itemView.findViewById(R.id.marketValue);
        imageView=itemView.findViewById(R.id.imageview);
    }
}
