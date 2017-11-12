package com.example.chipodeil.hookahfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullViewActivity extends AppCompatActivity {

    ArrayList<Comment> comments;
    CoffeeShops item;
    Realm realm;
    ListView commentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);
        Intent intent = this.getIntent();
        Bundle serializedObj = intent.getExtras();
        item = (CoffeeShops) serializedObj.getSerializable("data");
        TextView title = (TextView)findViewById(R.id.title);
        TextView desc = (TextView)findViewById(R.id.desc);
        title.setText("Название: " + item.getName());
        desc.setText("Описание: " + item.getDescription());
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("Comments.realm")
                .build();
        realm = Realm.getInstance(config);
        loadData();
        commentsList = (ListView) findViewById(R.id.commentsList);
    }

    private void loadData(){
        App.getApi().getComments(item.getId()).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                comments = new ArrayList<>();
                comments.addAll(response.body());
                ArrayList<String> text = getText(comments);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        FullViewActivity.this,
                        android.R.layout.simple_list_item_1,
                        text);
                commentsList.setAdapter(adapter);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(comments);
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                realm.beginTransaction();
                RealmQuery<Comment> query = realm.where(Comment.class).equalTo("assoc_id", item.getId());
                RealmResults<Comment> result = query.findAll();
                if(result.isEmpty()) {
                    Toast.makeText(FullViewActivity.this, "Вы никогда не загружали данную страницу", Toast.LENGTH_SHORT).show();
                    comments = (ArrayList<Comment>)realm.copyFromRealm(result);
                    realm.commitTransaction();
                    return;
                }
                comments = (ArrayList<Comment>)realm.copyFromRealm(result);
                realm.commitTransaction();
                ArrayList<String> text = getText(comments);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        FullViewActivity.this,
                        android.R.layout.simple_list_item_1,
                        text);
                commentsList.setAdapter(adapter);

            }
        });
    }

    public void addComment(View view){
        final EditText text = (EditText) findViewById(R.id.newComment);
        if(text.length() == 0) {
            Toast.makeText(this, "Вы не можете отправить пустой комментарий", Toast.LENGTH_SHORT).show();
            return;
        }
        NewComment newComment = new NewComment();
        newComment.text = text.getText().toString();
        App.getApi().addComment(item.getId(), newComment).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Toast.makeText(FullViewActivity.this, "comment added", Toast.LENGTH_SHORT).show();
                loadData();
                text.setText("");
            }
            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Toast.makeText(FullViewActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public ArrayList<String> getText(ArrayList<Comment> comments){
        ArrayList<String> text = new ArrayList<>();
        for (Comment comment : comments) {
            text.add(comment.getText());
            comment.setAssoc_id(item.getId());
        }
        return text;
    }

}
