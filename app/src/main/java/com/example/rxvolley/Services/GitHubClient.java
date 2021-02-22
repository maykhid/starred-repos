package com.example.rxvolley.Services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rxvolley.Helpers.GitHubRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GitHubClient {

    /*
    * The doGetRequest() does the necessary network request to get a users starred repositories.
    * It returns an observable string of the response it's gets (which is in Json format -- so a Json String,
    * by taking username passed by the user. This is called from getStarredRepos() in MainActivity.
    * */
    public Observable<String>  doGetRequest(String username) {
        final  OkHttpClient okHttpClient = new OkHttpClient();
        return Observable.defer(() -> {
            try {
                Response response = okHttpClient.newCall(
                        new Request.Builder().url("https://api.github.com/users/"+username+"/starred").build()
                ).execute();
                Log.d("The JSON response", "LOADING.....");
//                Log.d("The JSON response", response.body().string());
                return Observable.just(response.body().string());
            } catch (IOException e) {
                return Observable.error(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
