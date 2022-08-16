package com.example.pristineseed;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pristineseed.SessionManageMent.SessionManagement;
import com.example.pristineseed.global.ApiRequestFailure;
import com.example.pristineseed.model.login.LoginModel;
import com.example.pristineseed.retrofitApi.ApiUtils;
import com.example.pristineseed.retrofitApi.NetworkInterface;
import com.example.pristineseed.ui.bootmMainScreen.BottomMainActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button cirLoginButton;
    TextInputEditText editTextEmail, editTextPassword;
    TextInputLayout ti_email_layout, ti_password_layout;
    ProgressBar loading_login_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        ti_email_layout = findViewById(R.id.ti_email_layout);
        ti_password_layout = findViewById(R.id.ti_password_layout);
        loading_login_button = findViewById(R.id.loading_login_button);
        cirLoginButton.setOnClickListener(this);

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editTextEmail.getText().length() <= 0) {
                    ti_email_layout.setError("Please Enter Email.");
                } else {
                    ti_email_layout.setError(null);
                }
            }
        });
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editTextPassword.getText().length() <= 0) {
                    ti_password_layout.setError("Please Enter Password.");
                } else {
                    ti_password_layout.setError(null);
                }
            }
        });

        sessionManagement = new SessionManagement(getApplicationContext());
        if (sessionManagement.getUserEmail() != null && !sessionManagement.getUserEmail().equalsIgnoreCase("")) {
            Intent mainIntent = new Intent(LoginActivity.this, BottomMainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
            finish();
        }
        editTextEmail.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item)
            {
                return false;
            }
        });

        editTextPassword.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;

            }

            public void onDestroyActionMode(ActionMode mode) {

            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

        });
    }

private  SessionManagement sessionManagement;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.cirLoginButton: {
                if (editTextEmail.getText().toString().trim().equalsIgnoreCase("")) {
                    ti_email_layout.setError("Please Enter Email.");
                    editTextEmail.requestFocus();
                } else if (editTextPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    ti_password_layout.setError("Please Enter Password.");
                    editTextPassword.requestFocus();
                } else {
                    cirLoginButton.setVisibility(View.GONE);
                    loading_login_button.setVisibility(View.VISIBLE);
                    NetworkInterface mAPIService = ApiUtils.getPristineAPIService();
                    JsonObject postedJson = new JsonObject();
                    postedJson.addProperty("Email",editTextEmail.getText().toString().trim());
                    postedJson.addProperty("password",editTextPassword.getText().toString().trim());
                    postedJson.addProperty("token",sessionManagement.getFcmToken());
                    Call<LoginModel> call = mAPIService.Login(postedJson);
                    call.enqueue(new Callback<LoginModel>() {
                        @Override
                        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                            try {
                                if (response.isSuccessful()) {
                                    LoginModel getResponse = response.body();
                                    if (getResponse!=null && getResponse.condition) {
                                        //todo request success
                                        sessionManagement.setUserName(getResponse.name);
                                        sessionManagement.setPassword(editTextPassword.getText().toString().trim());
                                        sessionManagement.setUserEmail(getResponse.email);
                                        sessionManagement.setMenu(new Gson().toJson(getResponse.menu));
                                        sessionManagement.setApprover_id(getResponse.approver_id);
                                        sessionManagement.setUser_type(getResponse.user_type);
                                        sessionManagement.setemp_id(getResponse.emp_id);
                                        sessionManagement.setuser_app_inspection_type(getResponse.user_app_inspection_type);
                                        sessionManagement.setGender(getResponse.empGender);
                                        sessionManagement.setEmpGrade(getResponse.empGrade);
                                        sessionManagement.setSalePersonCode(getResponse.salespersoncode);
                                       // String menu=sessionManagement.getMenu();
                                        sessionManagement.setLastSession("1");
                                        //this.deleteDataBase();
                                        Intent mainIntent1 = new Intent(LoginActivity.this, BottomMainActivity.class);
                                        mainIntent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(mainIntent1);
                                        finish();
                                    } else {
                                        if (getResponse.message.contains("Email is wrong")) {
                                            ti_email_layout.setError(getResponse.message);
                                        } else if (getResponse.message.contains("Wrong Password")) {
                                            ti_password_layout.setError(getResponse.message);
                                        } else {
                                            Snackbar.make(cirLoginButton, getResponse.message, Snackbar.LENGTH_LONG).show();
                                        }
                                    }
                                } else {
                                    Snackbar.make(cirLoginButton, response.message() + ". Error Code:" + response.code(), Snackbar.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                ApiRequestFailure.PostExceptionToServer(e, getClass().getName(), "login", LoginActivity.this);
                            } finally {
                                cirLoginButton.setVisibility(View.VISIBLE);
                                loading_login_button.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onFailure(Call<LoginModel> call,Throwable t) {
                            cirLoginButton.setVisibility(View.VISIBLE);
                            loading_login_button.setVisibility(View.GONE);
                            ApiRequestFailure.PostExceptionToServer(t, getClass().getName(), "login", LoginActivity.this);
                        }
                    });
                }
                break;
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
