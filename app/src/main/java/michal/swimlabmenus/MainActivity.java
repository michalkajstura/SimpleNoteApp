package michal.swimlabmenus;

import android.app.ActionBar;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;

import michal.swimlabmenus.data.Note;
import michal.swimlabmenus.data.NotesDatabase;
import top.defaults.colorpicker.ColorPickerPopup;

public class MainActivity extends AppCompatActivity {

    private Note.Builder noteBuilder = new Note.Builder();
    private NotesDatabase database;

    private enum ColorableItem {
        TEXT, BACKGROUND
    }

    private class SavedStyles {
        boolean bold = false;
        boolean italic = false;
        int font;
    }

    private final SavedStyles saved = new SavedStyles();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = NotesDatabase.getInstance(getApplicationContext());
        FloatingActionButton gotoGalleryButtnon = findViewById(R.id.gallery_btn);
        Intent gotoGallery = new Intent(this, GalleryActivity.class);
        gotoGalleryButtnon.setOnClickListener(v -> startActivity(gotoGallery));

        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        EditText noteBody = findViewById(R.id.note_text);
        registerForContextMenu(noteBody);

        getIncomingIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("note")) {
            Bundle bundle = getIntent().getExtras();
            Note note = bundle.getParcelable("note");
            noteBuilder.copy(note);
            setTextAndTitle(note);
            if (note.getFont() != 0)
                setTextStyles(ResourcesCompat.getFont(this, note.getFont()));
        }
    }

    private void setTextAndTitle(Note note) {
        EditText head = findViewById(R.id.note_title);
        EditText body = findViewById(R.id.note_text);
        head.setText(note.getTitle());
        body.setText(note.getText());
        body.setTextColor(note.getTextColor());
        setBackground(note.getBackgroundColor());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNote();
                return true;
            case R.id.action_change_text_color:
                openColorPicker(ColorableItem.TEXT);
                return true;
            case R.id.action_italic:
                saved.italic = !item.isChecked();
                item.setChecked(!item.isChecked());
                setTextStyles();
                return true;
            case R.id.action_bold:
                saved.bold = !item.isChecked();
                item.setChecked(!item.isChecked());
                setTextStyles();
                return true;
            case R.id.calibri:
                setTextStyles(ResourcesCompat.getFont(this, R.font.calibri));
                saved.font = R.font.calibri;
                return true;
            case R.id.comic_sans:
                setTextStyles(ResourcesCompat.getFont(this, R.font.comic));
                saved.font = R.font.comic;
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setTextStyles() {
        EditText text = findViewById(R.id.note_text);
        SpannableString span = new SpannableString(text.getText().toString());

        if (saved.bold && saved.italic)
            span.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),
                    0, span.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        else if (saved.bold)
            span.setSpan(new StyleSpan(Typeface.BOLD),
                    0, span.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        else if (saved.italic)
            span.setSpan(new StyleSpan(Typeface.ITALIC),
                    0, span.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        text.setText(span);
    }

    private void setTextStyles(Typeface font) {
        EditText text = findViewById(R.id.note_text);
        if (font != null)
            text.setTypeface(font);
        setTextStyles();
    }


    public void openColorPicker(ColorableItem colorableItem) {
        new ColorPickerPopup.Builder(this)
                .enableBrightness(true)
                .enableAlpha(true)
                .cancelTitle("Cancel")
                .build()
                .show(new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {

                        // Set the color of the colorable item and save it in the note builder
                        if (colorableItem == ColorableItem.TEXT) {
                            noteBuilder.textColor(color);
                            EditText body =  findViewById(R.id.note_text);
                            body.setTextColor(color);
                        }
                        else if (colorableItem == ColorableItem.BACKGROUND) {
                            noteBuilder.backgroundColor(color);
                            setBackground(color);
                        }
                    }
                });

    }

    private void saveNote() {
        EditText text = findViewById(R.id.note_text);
        EditText title = findViewById(R.id.note_title);
        Note note = noteBuilder
                .title(title.getText().toString())
                .text(text.getText().toString())
                .font(saved.font)
                .build();

        database.noteRepo()
                .insert(note);

        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
    }

    private void setBackground(int color) {
        EditText bg = findViewById(R.id.note_text);
        EditText bgTitle = findViewById(R.id.note_title);
        bg.setBackgroundColor(color);
        bgTitle.setBackgroundColor(color);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Clear");
        menu.add(0, v.getId(), 0, "Set background color");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        EditText body = findViewById(R.id.note_text);
        if (item.getTitle().equals("Clear")) {
            setBackground(getResources().getColor(R.color.colorBackground));
            body.setText("");
        } else if (item.getTitle().equals("Set background color")) {
            openColorPicker(ColorableItem.BACKGROUND);
        }
        return super.onContextItemSelected(item);
    }
}

