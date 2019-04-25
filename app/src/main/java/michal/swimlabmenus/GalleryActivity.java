package michal.swimlabmenus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import michal.swimlabmenus.adapters.NoteListAdapter;
import michal.swimlabmenus.data.Note;
import michal.swimlabmenus.data.NotesDatabase;

public class GalleryActivity extends AppCompatActivity {

    private static NotesDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        database = NotesDatabase.getInstance(getApplicationContext());

        RecyclerView notesGallery = findViewById(R.id.notes_gallery);
        notesGallery.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this);
        notesGallery.setLayoutManager(layoutManager);

        // Get notes from database
        List<Note> notes = database
                .noteRepo()
                .getAll();
        RecyclerView.Adapter adapter =
                new NoteListAdapter(notes);
        notesGallery.setAdapter(adapter);
    }
}
