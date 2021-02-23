package com.example.rxvolley.Services;

import android.util.Log;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
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
