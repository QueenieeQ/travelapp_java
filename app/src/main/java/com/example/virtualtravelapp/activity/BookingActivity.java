package com.example.virtualtravelapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.virtualtravelapp.R;
import com.example.virtualtravelapp.database.DBManager;
import com.example.virtualtravelapp.model.Booking;
import com.example.virtualtravelapp.model.DiaDanh;

import java.text.NumberFormat;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {
    int id_diadanh;
    Booking booking;
    DBManager db;

    private ImageView imgView;
    private TextView tvName;
    private TextView tvGia;
    private TextView tvSoLuong;
    private Button buttonConfirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        id_diadanh = getIntent().getIntExtra("id_diadanh", 0);
        db = new DBManager(this);
        booking = db.getBooking(id_diadanh);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initData();
    }
    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        imgView = findViewById(R.id.image);
        tvName = findViewById(R.id.tv_name);
        tvGia = findViewById(R.id.tv_gia);
        tvSoLuong = findViewById(R.id.tv_soLuong);
        buttonConfirm = findViewById(R.id.buttonConfirm);

        if (booking != null) {
            tvName.setText(booking.getName());
            tvGia.setText("Giá: " + booking.getPrice() + " VND");
            tvSoLuong.setText("Số lượng: " + booking.getQuantity());
        } else {
            tvName.setText("No booking found");
        }
        buttonConfirm.setOnClickListener(view -> {
            showBookingConfirmationDialog();
        });
    }

    private void initData() {
        DiaDanh diaDanh = db.getDiaDanhByID(id_diadanh);
        if (diaDanh != null) {
            tvName.setText(String.format("Tên điểm du lịch %s", diaDanh.getNameDiaDanh()));
            updatePriceTextView(tvGia, diaDanh.getPrice());
            if (diaDanh.getQuantity() == 0) {
                tvSoLuong.setText("Hết tour");
                buttonConfirm.setEnabled(false);
                Toast.makeText(this, "Tour đã hết !!!", Toast.LENGTH_SHORT).show();
            } else {
                tvSoLuong.setText(String.format("Số lượng tour: %s", diaDanh.getQuantity()));
            }
            String imageUrl = diaDanh.getImDiaDanh();
            Glide.with(this)
                    .load(imageUrl)
                    .into(imgView);
        }
    }

    public void updatePriceTextView(TextView textView, int amount) {
        NumberFormat currencyFormat = NumberFormat.getInstance(Locale.getDefault());
        String formattedAmount = currencyFormat.format(amount);
        textView.setText(String.format("Giá: %s VND", formattedAmount));
    }

    private void showBookingConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận đặt tour")
                .setMessage("Bạn có chắc chắn muốn đặt tour không?")
                .setPositiveButton("Đặt", (dialog, which) -> {
                    saveBookingToDatabase();
                    Intent booking = new Intent(BookingActivity.this, BookingSuccess.class);
                    startActivity(booking);
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private void saveBookingToDatabase() {
        String name = tvName.getText().toString();
        int price = Integer.parseInt(tvGia.getText().toString().replaceAll("[^0-9]", ""));
        int quantity = Integer.parseInt(tvSoLuong.getText().toString().replaceAll("[^0-9]", ""));
        Booking newBooking = new Booking();
        newBooking.setName(name);
        newBooking.setPrice(price);
        newBooking.setQuantity(quantity);
        db.addBooking(newBooking);
    }
}
