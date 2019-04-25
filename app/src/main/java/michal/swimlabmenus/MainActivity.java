package michal.swimlabmenus;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import michal.swimlabmenus.data.Note;
import michal.swimlabmenus.data.NotesDatabase;
import top.defaults.colorpicker.ColorPickerPopup;

public class MainActivity extends AppCompatActivity {

    private Note.Builder noteBuilder = new Note.Builder();

    private enum ColorableItem {
        TEXT, BACKGROUND, TITLE
    }

    private class SavedStyles {
        boolean bold = false;
        boolean italic = false;
    }

    private final SavedStyles saved = new SavedStyles();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotesDatabase database = NotesDatabase.getInstance(getApplicationContext());
        FloatingActionButton saveButton = findViewById(R.id.save_btn);
        saveButton.setOnClickListener(v -> saveNote(database));
        FloatingActionButton gotoGalleryButtnon = findViewById(R.id.gallery_btn);
        Intent gotoGallery = new Intent(this, GalleryActivity.class);
        gotoGalleryButtnon.setOnClickListener(v -> startActivity(gotoGallery));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_text_color:
                openColorPicker(ColorableItem.TEXT);
                return true;
            case R.id.action_change_bg_color:
                openColorPicker(ColorableItem.BACKGROUND);
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
                            EditText bg = findViewById(R.id.note_text);
                            EditText bgTitle = findViewById(R.id.note_text);
                            bg.setBackgroundColor(color);
                            bgTitle.setBackgroundColor(color);
                        } else if (colorableItem == ColorableItem.TITLE) {
                            noteBuilder.titleColor(color);
                            EditText title = findViewById(R.id.note_title);
                            title.setTextColor(color);
                        }
                    }
                });

    }

    private void saveNote(NotesDatabase database) {
        EditText text = findViewById(R.id.note_text);
        EditText title = findViewById(R.id.note_title);
        Note note = noteBuilder
                .title(title.getText().toString())
                .text(text.getText().toString())
                .build();

        database.noteRepo()
                .insert(note);

        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
    }
}

