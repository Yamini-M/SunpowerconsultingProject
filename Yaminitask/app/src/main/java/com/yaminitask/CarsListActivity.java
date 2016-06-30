package com.yaminitask;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.yaminitask.api.ApiEndpointInterface;
import com.yaminitask.database.DataBaseHandler;
import com.yaminitask.model.AllCarMakes;
import com.yaminitask.utils.SecureSharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yamini on 28/06/16.
 */
public class CarsListActivity extends AppCompatActivity {

    private AllCarMakes allCarMakes;
    private View mProgressView;
    private View mRootLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_cars);
        getSupportActionBar().setTitle("Manufacturers");
        mProgressView = findViewById(R.id.loading_progress);
        mRootLayout = findViewById(R.id.root_layout);
        unpackIntent();
    }

    public static Intent packIntent(Context context, AllCarMakes allCarMakes) {
        Intent intent = new Intent(context, CarsListActivity.class);
        intent.putExtra("allCars", allCarMakes);
        return intent;
    }

    private void unpackIntent() {
        allCarMakes = getIntent().getParcelableExtra("allCars");
        if (allCarMakes != null) {
            addCarListFragment(allCarMakes);
        } else {
            if (haveNetworkConnection()) {
                init();
            } else {
                DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
                allCarMakes = new Gson().fromJson(dataBaseHandler.getAllCarMakes(), AllCarMakes.class);
                if(allCarMakes != null){
                    addCarListFragment(allCarMakes);
                }
            }
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void addCarListFragment(AllCarMakes allCarMakes) {
        CarsListFragment carsListFragment = CarsListFragment.getInstance(allCarMakes);
        getSupportFragmentManager().beginTransaction().add(R.id.root_layout, carsListFragment).commit();
    }

    private void init() {
        showProgress(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edmunds.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpointInterface apiEndpointInterface = retrofit.create(ApiEndpointInterface.class);
        Call<AllCarMakes> call = apiEndpointInterface.getMakesCall("zk3f8td9qyyzrvxmetytmzky");
        call.enqueue(new Callback<AllCarMakes>() {
            @Override
            public void onResponse(Call<AllCarMakes> call, Response<AllCarMakes> response) {
                AllCarMakes allCarMakes = response.body();
                showProgress(false);
                CarsListFragment carsListFragment = CarsListFragment.getInstance(allCarMakes);
                getSupportFragmentManager().beginTransaction().add(R.id.root_layout, carsListFragment).commit();
            }

            @Override
            public void onFailure(Call<AllCarMakes> call, Throwable t) {
                showProgress(false);
                System.out.println("failed");
            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRootLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mRootLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRootLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRootLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);

        MenuItem signoutMenu = menu.findItem(R.id.menu_sign_out);
        signoutMenu.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                SecureSharedPreferences secureSharedPreferences = new SecureSharedPreferences(CarsListActivity.this, "my-preferences", "Manas47256Secret", true);
                secureSharedPreferences.put(SecureSharedPreferences.KEY_LOGIN_ID, "");
                secureSharedPreferences.put(SecureSharedPreferences.KEY_PASSWORD, "");
                startActivity(new Intent(CarsListActivity.this, LoginActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
