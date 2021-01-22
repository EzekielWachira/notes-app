package com.ezzy.notesapp.adapters;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzy.notesapp.R;
import com.ezzy.notesapp.entities.Note;
import com.ezzy.notesapp.listeners.NoteListListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;
    private NoteListListener noteListListener;
    private Timer timer;
    private List<Note> noteSource;

    public NoteAdapter(List<Note> noteList, NoteListListener noteListListener) {
        this.noteList = noteList;
        this.noteListListener = noteListListener;
        noteSource = noteList;
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
        holder.setNote(noteList.get(position));
        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteListListener.onNoteClicked(noteList.get(position), position);
            }
        });
//        holder.textViewTitle.setText(noteList.get(position).getTitle());
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
        private LinearLayout layoutNote;
        private ImageView noteImage;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textTitle);
            textViewSubtitle = itemView.findViewById(R.id.textSubtitle);
            textViewDateTime = itemView.findViewById(R.id.textDatetime);
            layoutNote = itemView.findViewById(R.id.layoutNote);
            noteImage = itemView.findViewById(R.id.imageNote);
        }

        void setNote(Note note){
            textViewTitle.setText(note.getTitle());
            if (note.getSubtitle().trim().isEmpty()){
                textViewSubtitle.setVisibility(View.GONE);
            } else {
                textViewSubtitle.setText(note.getSubtitle());
            }
            textViewDateTime.setText(note.getDateTime());

            GradientDrawable gradientDrawable = (GradientDrawable) layoutNote.getBackground();
            if (note.getColor() != null){
                gradientDrawable.setColor(Color.parseColor(note.getColor()));
            } else {
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }

            if (note.getImagePath() != null){
                noteImage.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                noteImage.setVisibility(View.VISIBLE);
            } else  {
                noteImage.setVisibility(View.GONE);
            }
        }

    }

    private boolean isTextEmpty(EditText editText){
        return editText.getText().toString().trim().isEmpty();
    }

    public void searchNotes(final String searchKeyWord) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyWord.trim().isEmpty()){
                    noteList = noteSource;
                } else {
                    ArrayList<Note> temp = new ArrayList<>();
                    for (Note note : noteSource){
                        if (note.getTitle().toLowerCase().contains(searchKeyWord.toLowerCase())
                                || note.getSubtitle().toLowerCase().contains(searchKeyWord.toLowerCase())
                                || note.getNoteText().toLowerCase().contains(searchKeyWord.toLowerCase())
                        ){
                            temp.add(note);
                        }
                    }
                    noteList = temp;
                }
                new Handler(Looper.getMainLooper()).post(() -> notifyDataSetChanged());
            }
        }, 500);

    }

    public void cancelTimer() {
        if (timer != null){
            timer.cancel();
        }
    }
}
