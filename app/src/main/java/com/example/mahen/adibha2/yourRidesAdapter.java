package com.example.mahen.adibha2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mahen.adibha2.DBhelper.recycleAdapter;

import java.util.List;

public class yourRidesAdapter extends RecyclerView.Adapter<yourRidesAdapter.holder>{

    List<recycleAdapter> dataModelArrayList;

    public yourRidesAdapter(List<recycleAdapter> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.package_list,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        recycleAdapter dataModel=dataModelArrayList.get(i);

        holder.book_id.setText(dataModel.getBook_id());
        holder.v_type.setText(dataModel.getV_type());
        holder.travel_type.setText(dataModel.getTravel_type());
        holder.ori.setText(dataModel.getOrigin());
        holder.dest.setText(dataModel.getDestination());
        holder.dateof.setText(dataModel.getTimeat());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class holder extends RecyclerView.ViewHolder{

        TextView book_id, travel_type, v_type, dateof, ori, dest;

        public holder(@NonNull View itemView) {
            super(itemView);

            book_id = itemView.findViewById(R.id.book_id);
            travel_type = itemView.findViewById(R.id.travel_type);
            v_type = itemView.findViewById(R.id.v_type);
            dateof = itemView.findViewById(R.id.dateof);
            ori = itemView.findViewById(R.id.ori);
            dest = itemView.findViewById(R.id.dest);
        }
    }
}
