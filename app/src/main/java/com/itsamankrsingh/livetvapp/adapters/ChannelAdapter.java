package com.itsamankrsingh.livetvapp.adapters;

import static com.itsamankrsingh.livetvapp.Constants.DETAILS_TYPE;
import static com.itsamankrsingh.livetvapp.Constants.SLIDER_TYPE;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itsamankrsingh.livetvapp.DetailsActivity;
import com.itsamankrsingh.livetvapp.R;
import com.itsamankrsingh.livetvapp.models.Channel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    List<Channel> channels;
    String type;


    public ChannelAdapter(List<Channel> channels, String type) {
        this.channels = channels;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (type.equals(SLIDER_TYPE)) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.big_slider_view, parent, false);
        }else if(type.equals(DETAILS_TYPE)) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_details_view, parent, false);
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_cat_view, parent, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelAdapter.ViewHolder holder, int position) {
        holder.channelName.setText(channels.get(position).getName());
        Picasso.get().load(channels.get(position).getThumbnail()).into(holder.channelImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), DetailsActivity.class);
                intent.putExtra("channel",channels.get(position));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView channelImage;
        TextView channelName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            channelImage = itemView.findViewById(R.id.channelThumbnail);
            channelName = itemView.findViewById(R.id.channelName);
        }
    }
}
