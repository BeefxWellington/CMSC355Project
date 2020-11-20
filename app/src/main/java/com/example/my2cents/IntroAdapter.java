package com.example.my2cents;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.IntroViewHolder> {

    private List<IntroItem> introItems;

    public IntroAdapter(List<IntroItem> introItems) {
        this.introItems = introItems;
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {
        holder.setIntroData(introItems.get(position));
    }

    @Override
    public int getItemCount() {
        return introItems.size();
    }

    class IntroViewHolder extends RecyclerView.ViewHolder{

        private TextView textTitle;
        private TextView textDesc;
        private ImageView imageIntro;

        IntroViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.intro_title);
            textDesc = itemView.findViewById(R.id.intro_desc);
            imageIntro = itemView.findViewById(R.id.intro_pic);
        }

        void setIntroData (IntroItem introItem) {
            textTitle.setText(introItem.getTitle());
            textDesc.setText(introItem.getDesc());
            imageIntro.setImageResource(introItem.getImage());
        }
    }

}
