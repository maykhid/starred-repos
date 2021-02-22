package com.example.rxvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.rxvolley.Adapters.GitHubRepoAdapter;
import com.example.rxvolley.Services.GitHubClient;

import org.reactivestreams.Subscription;

import java.io.IOException;

import io.reactivex.rxjava3.exceptions.OnErrorNotImplementedException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GitHubRepoAdapter adapter = new GitHubRepoAdapter();
    GitHubClient gitHubClient = new GitHubClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ListView listView = (ListView) findViewById(R.id.list_view_repos);
        listView.setAdapter(adapter);

        final EditText editTextUsername = (EditText) findViewById(R.id.edit_text_username);
        final Button buttonSearch = (Button) findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final String username = editTextUsername.getText().toString();
                if (!TextUtils.isEmpty(username)) {
                    getStarredRepos(username);
                }
            }
        });
    }
/*
* The getStarredRepos() passes username to doGetRequest() which runs the network request.
* The result is passed to setGitHubRepos() in GitHubRepoAdapter class.
* */
    private void getStarredRepos(String username) {
        gitHubClient.doGetRequest(username).subscribe( next -> {
                    Log.d(TAG, "In onNext() " + next);
                    adapter.setGitHubRepos(next);
                },
                error -> {
                    error.printStackTrace();
                    Log.d(TAG, "In onError()");
                });
    }
}