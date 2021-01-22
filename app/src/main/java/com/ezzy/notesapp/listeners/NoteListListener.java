package com.ezzy.notesapp.listeners;

import com.ezzy.notesapp.entities.Note;

public interface NoteListListener {
    void onNoteClicked(Note note, int position);
}
