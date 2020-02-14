package com.example.qrder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter_main_musteri extends RecyclerView.Adapter<adapter_main_musteri.PostHolder> {

    private ArrayList<String> yemekAdiArray;
    private ArrayList<String> yemekAciklamaArray;
    private ArrayList<String> yemekFiyatArray;

    public adapter_main_musteri (ArrayList<String> yemekAdiArray, ArrayList<String> yemekAciklamaArray, ArrayList<String> yemekFiyatArray) {
        this.yemekAdiArray      = yemekAdiArray;
        this.yemekAciklamaArray = yemekAciklamaArray;
        this.yemekFiyatArray    = yemekFiyatArray;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from (parent.getContext ());
        View view = layoutInflater.inflate (R.layout.recycle_raw_activity,parent,false);

        return new PostHolder (view);
    }

    @Override
    public void onBindViewHolder (@NonNull PostHolder holder, int position) {
        holder.yemekAdiText.setText (yemekAdiArray.get (position));
        holder.yemekAciklamaText.setText (yemekAciklamaArray.get (position));
        holder.yemekFiyatText.setText (yemekFiyatArray.get (position));

        holder.yemekAdiText.setTag (yemekAdiArray.get (position));
        holder.yemekFiyatText.setTag (yemekFiyatArray.get (position));



    }

    @Override
    public int getItemCount () {
        return yemekAdiArray.size ();
    }

    class  PostHolder extends RecyclerView.ViewHolder{

        TextView yemekAdiText;
        TextView yemekAciklamaText;
        TextView yemekFiyatText;

        public PostHolder (@NonNull View itemView) {
            super (itemView);

            yemekAdiText = itemView.findViewById (R.id.yemekAdiTv);
            yemekAciklamaText = itemView.findViewById (R.id.yemekAciklamaTv);
            yemekFiyatText = itemView.findViewById (R.id.yemekFiyatTv);





        }
    }

}
