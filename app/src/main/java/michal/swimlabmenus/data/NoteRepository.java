package michal.swimlabmenus.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
import java.util.Optional;

@Dao
public interface NoteRepository {

    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Insert
    void insert(Note note);

    @Query(("DELETE FROM note"))
    void deleteAll();

    @Query("SELECT * FROM note WHERE noteId = :id")
    Optional<Note> getNoteById(int id);

    @Query("DELETE FROM note WHERE noteId = :id")
    void deleteNote(int id);
}
