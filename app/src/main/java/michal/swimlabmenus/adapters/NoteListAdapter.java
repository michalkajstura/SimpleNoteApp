package michal.swimlabmenus.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import michal.swimlabmenus.R;
import michal.swimlabmenus.data.Note;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NotesViewHolder> {

    private List<Note> notes;

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTitle;
        public TextView noteBody;
        public NotesViewHolder(View v) {
            super(v);
            noteTitle = v.findViewById(R.id.note_title_row);
            noteBody = v.findViewById(R.id.note_text_row);
        }
    }

    public NoteListAdapter(List<Note> notes) {
        this.notes = notes;
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
        holder.noteTitle.setText(notes.get(position).title);
        holder.noteBody.setText(notes.get(position).text);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}

