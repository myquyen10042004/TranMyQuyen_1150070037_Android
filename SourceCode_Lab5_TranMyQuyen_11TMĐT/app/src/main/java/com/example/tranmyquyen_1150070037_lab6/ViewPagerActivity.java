package com.example.tranmyquyen_1150070037_lab6;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new EmployeePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }
}