package com.example.virtualtravelapp.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.virtualtravelapp.R;
import com.example.virtualtravelapp.model.Booking;

import java.util.List;

public class BookTourAdapter extends RecyclerView.Adapter<BookTourAdapter.BookTourViewHolder> {
    private List<Booking> bookTourList;
    private OnItemLongClickListener onItemLongClickListener;

    public BookTourAdapter(List<Booking> bookTourList) {
        this.bookTourList = bookTourList;
    }

    @NonNull
    @Override
    public BookTourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tour_booking, parent, false);
        return new BookTourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookTourViewHolder holder, int position) {
        Booking booking = bookTourList.get(position);
        holder.nameTextView.setText(booking.getName());
        holder.priceTextView.setText(String.format("%d VND", booking.getPrice()));

        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return bookTourList.size();
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public void removeItem(int position) {
        if (position >= 0 && position < bookTourList.size()) {
            bookTourList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public static class BookTourViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;

        public BookTourViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textNameDiaDanh);
            priceTextView = itemView.findViewById(R.id.giaTien);
        }
    }
}
