package com.example.arzoo.chatbot;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final int SELECTED_PICTURE = 1;
    ImageView iv;
    private ListView mListView;
    private FloatingActionButton mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    private ChatMessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        iv=(ImageView)findViewById((R.id.imageview1);
        mListView = (ListView) findViewById(R.id.listView);
        mButtonSend = (FloatingActionButton) findViewById(R.id.btn_send);
        mEditTextMessage = (EditText) findViewById(R.id.et_message);
        mImageView = (ImageView) findViewById(R.id.iv_image);
        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);

        ChatMessage chm = new ChatMessage("What is your name", false, false);
        mAdapter.add(chm);

//code for sending the message
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mListView.getCount()) {
                    case 1:
                        String message = mEditTextMessage.getText().toString();
                        sendMessage(message);
                        mEditTextMessage.setText("");
                        mListView.setSelection(mAdapter.getCount() - 1);
                        mimicOtherMessage("What is your age?");
                        break;
                    case 3:
                        message = mEditTextMessage.getText().toString();
                        sendMessage(message);
                        mEditTextMessage.setText("");
                        mListView.setSelection(mAdapter.getCount() - 1);
                        mimicOtherMessage("What are the Symptoms?");
                        break;
                    case 5:
                        message = mEditTextMessage.getText().toString();
                        sendMessage(message);
                        mEditTextMessage.setText("");
                        mListView.setSelection(mAdapter.getCount() - 1);
                        mimicOtherMessage("Provide us with some attachments");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        mAdapter.add(chatMessage);
        //respond as Helloworld
    }

    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        mAdapter.add(chatMessage);

    }

    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage(null, true, true);
        mAdapter.add(chatMessage);

        mimicOtherMessage();
    }

    private void mimicOtherMessage() {
        ChatMessage chatMessage = new ChatMessage(null, false, true);
        mAdapter.add(chatMessage);
    }

    public void btnClick(View v) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECTED_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PICTURE:
                if (requestCode == RESULT_OK) {

                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bitmap;

//                    Bitmap yourSelectedImage= BitmapFactory.decodeFile(filePath);
//                    Drawable d= new BitmapDrawable(yourSelectedImage);
                    bitmap = BitmapFactory.decodeFile(filePath);
                    iv.setImageBitmap(bitmap);


                }
                break;

            default:
                break;


        }
    }
}

