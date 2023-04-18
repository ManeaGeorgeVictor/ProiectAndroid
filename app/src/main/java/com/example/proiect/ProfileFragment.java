package com.example.proiect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {


    List<Item> items;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        items=new ArrayList<Item>();
        items.add(new Item("Kylian Mbappe",180,R.drawable.kylian_mbappe_face));
        items.add(new Item("Erling Haaland",170,R.drawable.erling_haaland_face));
        items.add(new Item("Jude Bellingham",120,R.drawable.jude_bellingham_face));
        items.add(new Item("Vinicius Junior",120,R.drawable.vinicius_junior_face));
        items.add(new Item("Jamal Musiala",110,R.drawable.jamal_musiala_face));
        items.add(new Item("Buyako Saka",110,R.drawable.bukayo_saka_face));
        items.add(new Item("Phil Foden",110,R.drawable.phil_foden_face));
        items.add(new Item("Pedri",100,R.drawable.pedri_face));
        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter myAdapter=new MyAdapter(view.getContext(),items);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
}