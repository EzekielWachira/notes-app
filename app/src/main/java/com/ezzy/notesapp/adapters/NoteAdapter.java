package com.ezzy.notesapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzy.notesapp.R;
import com.ezzy.notesapp.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    List<Note> noteList;

    public NoteAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_container_note,
                parent, false
        );
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.textViewTitle.setText(noteList.get(position).getTitle());
//        holder.textViewSubtitle.setText(noteList.get(position).getSubtitle());
//        holder.textViewDateTime.setText(noteList.get(position).getDateTime());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle, textViewSubtitle, textViewDateTime;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textTitle);
            textViewSubtitle = itemView.findViewById(R.id.textSubtitle);
            textViewDateTime = itemView.findViewById(R.id.textDatetime);
        }

        void setNote(Note note){
            textViewTitle.setText(note.getTitle());
            if (note.getSubtitle().trim().isEmpty()){
                textViewSubtitle.setVisibility(View.GONE);
            } else {
                textViewSubtitle.setText(note.getSubtitle());
            }
            textViewDateTime.setText(note.getDateTime());
        }

        private boolean isTextEmpty(EditText editText){
            return editText.getText().toString().trim().isEmpty();
        }
    }
}
