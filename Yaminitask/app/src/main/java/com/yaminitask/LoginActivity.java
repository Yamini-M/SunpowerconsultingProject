package com.yaminitask;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.yaminitask.api.ApiEndpointInterface;
import com.yaminitask.database.DataBaseHandler;
import com.yaminitask.model.AllCarMakes;
import com.yaminitask.utils.EngineUtils;
import com.yaminitask.utils.SecureSharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Sign In");
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            hideKeyboard();
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            // Ignore exceptions if any
            Log.e("KeyBoardUtil", e.toString(), e);
        }
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        //No conditions were given for the password as of now, but can update here accordingly
        return password.length() > 0;
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

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //Not at all advisable to store the user id and password here but as we don't have any support currently
            SecureSharedPreferences secureSharedPreferences = new SecureSharedPreferences(LoginActivity.this, "my-preferences", "Manas47256Secret", true);
            secureSharedPreferences.put(SecureSharedPreferences.KEY_PASSWORD, mPassword);
            secureSharedPreferences.put(SecureSharedPreferences.KEY_LOGIN_ID, mEmail);
            if (EngineUtils.haveNetworkConnection(LoginActivity.this)) {
                doAllMakes();
            } else {
                DataBaseHandler dataBaseHandler = new DataBaseHandler(LoginActivity.this);
                AllCarMakes allCarMakes = new Gson().fromJson(dataBaseHandler.getAllCarMakes(), AllCarMakes.class);
                if(allCarMakes != null){
                    startActivity(CarsListActivity.packIntent(LoginActivity.this, allCarMakes));
                }
            }


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
//                finish();
            }
        }

        private void doAllMakes(){
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
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(LoginActivity.this);
                    dataBaseHandler.addAllCarMakes(new Gson().toJson(allCarMakes));
                    finish();
                    startActivity(CarsListActivity.packIntent(LoginActivity.this, allCarMakes));
                }

                @Override
                public void onFailure(Call<AllCarMakes> call, Throwable t) {
                    showProgress(false);
                    System.out.println("failed");
                }
            });
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

