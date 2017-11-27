package com.example.chipodeil.authproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by chipodeil on 27.11.2017.
 */

public class NotesFragment extends Fragment{

    View view;
    Realm realm;
    ListView notes;
    EditText newNote;
    ArrayList<Note> noteList;
    User current;
    Button sendNote;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notes_fragment,
                container, false);
        Realm.init(view.getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("Notes.realm")
                .build();
        notes = (ListView)view.findViewById(R.id.notes);
        newNote = (EditText)view.findViewById(R.id.newNote);
        sendNote = (Button)view.findViewById(R.id.addCommentButton);
        realm = Realm.getInstance(config);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            current = (User)bundle.getSerializable("user");
        }
        noteList = getNotes();

        if(Holder.shouldRandomize)
            Collections.shuffle(noteList);

        setAdapter();
        sendNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });
        return view;
    }

    public void addNote(){
        String noteText = newNote.getText().toString();
        if(noteText.length() == 0) {
            Toast.makeText(view.getContext(), "Вы не можете добавить пустой комментарий", Toast.LENGTH_SHORT).show();
            return;
        }
        newNote.setText("");
        Note newNote = new Note();
        newNote.setLogin(current.getLogin());
        newNote.setText(noteText);
        noteList.add(newNote);
        realm.beginTransaction();
        realm.insert(newNote);
        realm.commitTransaction();
        setAdapter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public ArrayList<Note> getNotes(){
        realm.beginTransaction();
        RealmQuery<Note> query = realm.where(Note.class).equalTo("login", current.getLogin());
        RealmResults<Note> result = query.findAll();
        noteList =(ArrayList<Note>)realm.copyFromRealm(result);
        realm.commitTransaction();
        return noteList;
    }

    public void setAdapter(){
        ArrayList<String> text = new ArrayList<>();
        for (Note n : noteList){
            text.add(n.getText());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_list_item_1,
                text);
        notes.setAdapter(adapter);
    }
}
