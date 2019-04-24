package michal.swimlabmenus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import top.defaults.colorpicker.ColorPickerPopup;

public class MainActivity extends AppCompatActivity {

    private Note.Builder noteBuilder = new Note.Builder();

    private enum ColorableItem {
        TEXT, BACKGROUND, TITLE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        }
        return super.onOptionsItemSelected(item);
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
}

