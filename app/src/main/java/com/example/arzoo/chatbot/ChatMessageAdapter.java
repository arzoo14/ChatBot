package com.example.arzoo.chatbot;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by arzoo on 3/24/2018.
 */

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

    private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1, SYMPTOM = 2, IMAGEVIEW = 3;

    public ChatMessageAdapter(Context context, List<ChatMessage> data) {
        super(context, R.layout.item_mine_message, data);
    }

    @Override
    public int getViewTypeCount() {
        // my message, other message, my image, other image
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage item = getItem(position);
        if (item.isMine() && !item.isImage()) return MY_MESSAGE;
        else if (!item.isMine() && !item.isImage()) return OTHER_MESSAGE;
        else if (item.isMine() && item.isImage()) return SYMPTOM;
        else return IMAGEVIEW;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int viewType = getItemViewType(position);
        if (viewType == MY_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_mine_message, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());
        } else if (viewType == OTHER_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tem_other_message, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());
        } else if (viewType == SYMPTOM) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.symptom, parent, false);


        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.imageview, parent, false);
            ImageView imageView = convertView.findViewById(R.id.imageView);
            imageView.setImageURI(Uri.parse(getItem(position).getContent()));
        }

//        convertView.findViewById(R.id.chatMessageView).setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        Toast.makeText(getContext(), "onClick", Toast.LENGTH_LONG).show();
//        }
//        });
        return convertView;
    }

}
