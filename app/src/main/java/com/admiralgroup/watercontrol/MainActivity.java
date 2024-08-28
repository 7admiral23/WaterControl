package com.admiralgroup.watercontrol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        fetchPermissionsAndSetupTabs();
    }

    private void fetchPermissionsAndSetupTabs() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String token = prefs.getString("Token", null);

        if (token == null) {
            navigateToLogin();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<PermissionsResponse> call = apiService.getPermissions(token);

        call.enqueue(new Callback<PermissionsResponse>() {
            @Override
            public void onResponse(Call<PermissionsResponse> call, Response<PermissionsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    setupTabs(response.body().getActions());
                } else {
                    navigateToLogin();
                }
            }

            @Override
            public void onFailure(Call<PermissionsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to load permissions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupTabs(Map<String, PermissionsResponse.Action> actions) {
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), actions);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}