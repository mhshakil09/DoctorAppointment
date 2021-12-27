package com.example.doctorappointment.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.doctorappointment.databinding.ActivityMainBinding;
import com.example.doctorappointment.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SessionManager.init(this);
    }
}