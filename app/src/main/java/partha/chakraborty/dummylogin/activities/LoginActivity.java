package partha.chakraborty.dummylogin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import partha.chakraborty.dummylogin.R;
import partha.chakraborty.dummylogin.utils.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Utils utils;

    private EditText etUserId;
    private EditText etPassword;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        utils = new Utils();

        initLayout();
    }

    private void initLayout() {
        etUserId = findViewById(R.id.et_userid);
        etPassword = findViewById(R.id.et_password);
        tvLogin = findViewById(R.id.tv_login);

        tvLogin.setOnClickListener(this);
    }

    private boolean validateLogin() {
        boolean isValidLogin = false;
        if (etUserId.getText().toString().trim().length() <= 0) {
            etUserId.setError("User ID required...");
        } else if (etPassword.getText().toString().trim().length() <= 0) {
            etPassword.setError("Password required...");
        } else {
            isValidLogin = true;
        }
        return isValidLogin;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                if (utils.isNetworkAvailable(LoginActivity.this)) {
                    if (validateLogin()) {
                        dummyPostLogin(etUserId.getText().toString().trim(),etPassword.getText().toString().trim());
                    }
                }
                break;
        }
    }

    private void dummyPostLogin(String userId, String password) {
        try {
            utils.showLoader(this);
            JSONObject jsonObjSend = new JSONObject();
            try {
                jsonObjSend.put("UserID", userId);
                jsonObjSend.put("Password", password);
            } catch (JSONException e) {
                e.printStackTrace();
                utils.hideLoader();
            }
            String loginAPI = "https://dummy_login_api/";                   //Any web service
            final JsonObjectRequest jsObjRequest = new JsonObjectRequest(loginAPI, jsonObjSend, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonResponse) {
                    try {
                        utils.hideLoader();
                        if (jsonResponse.getString("messages").equals("success")) {
                            utils.showAlertSuccessMessage("Hi " +jsonResponse.getString("username"), LoginActivity.this);
                        } else {
                            utils.showAlertErrorMessage(jsonResponse.getString("messages"), LoginActivity.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        utils.hideLoader();
                    }catch (Exception e) {
                        e.printStackTrace();
                        utils.hideLoader();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse == null) {
                        Log.v("error", error.toString());
                        utils.showAlertErrorMessage("Please check internet connection", LoginActivity.this);
                    }
                    utils.hideLoader();
                }
            });
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(getApplicationContext()).add(jsObjRequest);
        } catch (Exception e) {
            e.printStackTrace();
            utils.hideLoader();
        }
    }
}
