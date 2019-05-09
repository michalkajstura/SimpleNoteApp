package michal.swimlabmenus;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;

import java.util.List;

import michal.swimlabmenus.adapters.NoteListAdapter;
import michal.swimlabmenus.data.Note;
import michal.swimlabmenus.data.NotesDatabase;

public class GalleryActivity extends AppCompatActivity {

    private static NotesDatabase database;
    private static final int NUM_OF_COLUMNS = 2;
    private static final int PADDING = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        database = NotesDatabase.getInstance(getApplicationContext());

        RecyclerView notesGallery = findViewById(R.id.notes_gallery);

//        notesGallery.setHasFixedSize(true);
        RecyclerView.LayoutManager  layoutManager =
                new GridLayoutManager(this, NUM_OF_COLUMNS);
        notesGallery.setLayoutManager(layoutManager);

        // Set the space between items
        notesGallery.addItemDecoration(new VerticalSpaceItemDecoration(PADDING));

        // Get notes from database
        List<Note> notes = database
                .noteRepo()
                .getAll();

        RecyclerView.Adapter adapter =
                new NoteListAdapter(this, notes);
        notesGallery.setAdapter(adapter);

        FloatingActionButton gotoMain = findViewById(R.id.note_btn);
        Intent gotoGallery = new Intent(this, MainActivity.class);
        gotoMain.setOnClickListener(v -> startActivity(gotoGallery));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem delete = menu.add(Menu.NONE, 0, Menu.NONE, "Delete all");
        delete.setIcon(R.drawable.delete);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete all")) {
            database.noteRepo()
                    .deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }
}
