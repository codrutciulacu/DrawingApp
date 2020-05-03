package com.codrut.drawingapp.view.listView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codrut.drawingapp.R;
import com.codrut.drawingapp.model.domain.Drawing;
import com.codrut.drawingapp.utils.ImageEncoder;

import java.util.ArrayList;
import java.util.List;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridViewHolder> {
    private final String TAG = "Image Grid Adapter";
    private List<Drawing> imagesList = new ArrayList<>();
    private Context context;

    public ImageGridAdapter(List<Drawing> imagesList, Context context) {
        this.imagesList = imagesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
        return new ImageGridViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageGridViewHolder holder, int position) {
        Drawing image = imagesList.get(position);
        drawImage(holder, image);
    }

    private void drawImage(@NonNull ImageGridViewHolder holder, Drawing image) {
        Log.d(TAG, image.getName());
        Bitmap imageContentDecoded = ImageEncoder.decodeImage(image.getContent());

        if(imageContentDecoded != null)
            Glide.with(context)
                    .load(imageContentDecoded)
                    .centerCrop()
                    .into(holder.getImageView());

        holder.getTextView().setText(image.getName());
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }
}

class ImageGridViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView textView;

    public ImageGridViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.grid_item_image_view);
        textView = itemView.findViewById(R.id.grid_item_text_view);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
