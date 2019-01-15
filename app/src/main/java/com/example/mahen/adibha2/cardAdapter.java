package com.example.mahen.adibha2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class cardAdapter extends RecyclerView.Adapter<cardAdapter.ViewHolder> {
    private Context context;
    private List<CardView> cardViewList = new ArrayList<>();

    private ArrayList<String> packages;
//    private ArrayList<String> mDataset;

    cardAdapter(Context context,ArrayList<String> packages) {
        super();
        this.context = context;
        this.packages = packages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.package_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        if (!cardViewList.contains(viewHolder.mainCard)) {
            cardViewList.add(viewHolder.mainCard);
        }
//        viewHolder.mFare.setText(mDataset.get(i));
        viewHolder.mPackage.setText(packages.get(i));
        viewHolder.mainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (CardView cardView : cardViewList) {
                    cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                }
                viewHolder.mainCard.setCardBackgroundColor(context.getResources().getColor(R.color.black));
                Toast.makeText(context, "reyclervdiw  "+viewHolder.mPackage.getText().toString(), Toast.LENGTH_SHORT).show();
            }

        });

    }


    @Override
    public int getItemCount() {
        return packages.size();
    }

    //this line have error may be
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mPackage;
        CardView mainCard;
        Context context;

        //Initializes the selected image and title of image to local variable
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
//            mFare = itemView.findViewById(R.id.p1rate);
            mPackage = itemView.findViewById(R.id.ori);//Changed for Temporary use   -Naveen
            mainCard = itemView.findViewById(R.id.p1card);
        }
    }
}

