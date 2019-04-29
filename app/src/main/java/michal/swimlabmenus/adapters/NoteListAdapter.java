package michal.swimlabmenus.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import michal.swimlabmenus.GalleryActivity;
import michal.swimlabmenus.MainActivity;
import michal.swimlabmenus.R;
import michal.swimlabmenus.data.Note;
import michal.swimlabmenus.data.NotesDatabase;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NotesViewHolder> {

    private List<Note> notes;
    private Context context;
    private ActionMode actionMode;
    private List<Integer> selectedItems = new ArrayList<>();

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle;
        TextView noteBody;
        LinearLayout galleryRow;

        public NotesViewHolder(View v) {
            super(v);
            noteTitle = v.findViewById(R.id.note_title_row);
            noteBody = v.findViewById(R.id.note_text_row);
            galleryRow = v.findViewById(R.id.gallery_row);
        }
    }

    public NoteListAdapter(Context context, List<Note> notes) {
        this.notes = notes;
        this.context = context;
    }

    @Override
    public NoteListAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                     int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater
                .inflate(R.layout.gallery_row, parent, false);

        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteBody.setText(note.getText());
        Drawable border = context.getResources().getDrawable(R.drawable.border);
//        border.mutate().setColorFilter(
//                new PorterDuffColorFilter(0xffcccc, PorterDuff.Mode.SRC_IN));
        holder.galleryRow.setBackground(border);

        holder.galleryRow.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("note", notes.get(position));
            context.startActivity(intent);
        });

        holder.galleryRow.setOnLongClickListener(v -> {
            selectedItems.add(position);
            if (actionMode != null)
                return true;

            actionMode = ((GalleryActivity) context).startSupportActionMode(actionModeCallback);
            return true;
        });
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.action_delte) {
                selectedItems.forEach(i -> {
                    Note note = notes.get(i);
                    notes.remove(i.intValue());
                    NotesDatabase.getInstance(context).
                            noteRepo().
                            deleteNote(note.getNoteId());});
                notifyDataSetChanged();
                mode.finish();
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            selectedItems.clear();
            actionMode = null;
        }
    };

    @Override
    public int getItemCount() {
        return notes.size();
    }
}

