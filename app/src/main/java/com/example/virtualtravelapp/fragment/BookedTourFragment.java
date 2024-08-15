package com.example.virtualtravelapp.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtualtravelapp.R;
import com.example.virtualtravelapp.adapter.BookTourAdapter;
import com.example.virtualtravelapp.database.DBManager;
import com.example.virtualtravelapp.model.Booking;

import java.util.List;

public class BookedTourFragment extends Fragment implements BookTourAdapter.OnItemLongClickListener {
    private RecyclerView recyclerView;

    private BookTourAdapter bookTourAdapter;
    private List<Booking> bookTourList;
    private DBManager dbManager;
    private Booking bookingToDelete;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booked_tour, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewBooked);
        getActivity().setTitle("Tour đã đặt");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbManager = new DBManager(getContext());
        bookTourList = dbManager.getAllBookTours();
        bookTourAdapter = new BookTourAdapter(bookTourList);
        bookTourAdapter.setOnItemLongClickListener(this);
        recyclerView.setAdapter(bookTourAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onItemLongClick(int position) {
        bookingToDelete = bookTourList.get(position);
        showDialog(position);
    }
    private void showDialog(int position) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Xóa Item")
                .setMessage("Bạn có chắc chắn muốn xóa item này?")
                .setPositiveButton("Có", (dialog, which) -> {
                    dbManager.deleteBooking(bookingToDelete);
                    bookTourAdapter.removeItem(position);
                })
                .setNegativeButton("Không", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }
}
