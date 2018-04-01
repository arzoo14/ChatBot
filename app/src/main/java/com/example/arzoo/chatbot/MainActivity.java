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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.arzoo.chatbot.api.AckResponse;
import com.example.arzoo.chatbot.api.rest.ApiClient;
import com.example.arzoo.chatbot.api.rest.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final int SELECTED_PICTURE = 1;
    ImageView iv;
    private ListView mListView;
    private FloatingActionButton mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    private ChatMessageAdapter mAdapter;
    private String name, age, symptoms;
    private Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        iv=(ImageView)findViewById((R.id.imageview1);
        mImageView = findViewById(R.id.bot_image);
        mListView = (ListView) findViewById(R.id.listView);
        mButtonSend = (FloatingActionButton) findViewById(R.id.btn_send);
        mEditTextMessage = (EditText) findViewById(R.id.et_message);
        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);
        AndroidNetworking.initialize(getApplicationContext());
        data = new Data("aa","aa","aa");
        ChatMessage chm = new ChatMessage("What is your name?", false, false);
        mAdapter.add(chm);


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClick();
            }
        });
//code for sending the message
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mListView.getCount()) {
                    case 1:
                        String message = mEditTextMessage.getText().toString();
                        if(!message.isEmpty()) {
                            sendMessage(message);

                            name = message;
                            mEditTextMessage.setText("");
                            mListView.setSelection(mAdapter.getCount() - 1);
                            mimicOtherMessage("What is your age?");
                        }
                        else
                            Toast.makeText(MainActivity.this, "Enter your name !", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        message = mEditTextMessage.getText().toString();
                        if(!message.isEmpty()) {
                            sendMessage(message);

                            age = message;
                            mEditTextMessage.setText("");


                            // mimicOtherMessage("What are the Symptoms?");
                            ChatMessage chatMessage = new ChatMessage(message, true, true);
                            mAdapter.add(chatMessage);
                        }
                        else
                            Toast.makeText(MainActivity.this, "Enter your age !", Toast.LENGTH_SHORT).show();
                        break;
//                    case 5:
//                        mListView.setSelection(mAdapter.getCount() - 1);
//                        Log.d("saa", String.valueOf(mAdapter.getCount()));
//                        mEditTextMessage.setText("");
//                        Button btn =  mListView.getChildAt(4).findViewById(R.id.button);
//                        Log.d("message", String.valueOf(btn.getId()));
////                        btn.setOnClickListener(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View view) {
////                                startActivity(new Intent(MainActivity.this,Main2Activity.class));
////                            }
////                        });
////                        mListView.setSelection(mAdapter.getCount() - 1);
//                        mimicOtherMessage("Provide us with some attachments");
//                        mImageView.setVisibility(View.VISIBLE);
//
//                        break;
                    case 5:
                        message = mEditTextMessage.getText().toString();
                        sendMessage(message);
                        mEditTextMessage.setText("");
                        mListView.setSelection(mAdapter.getCount() - 1);
                        mimicOtherMessage("Do you want to send some attachments? \nIf yes, send some media file ! \nElse, send the text about your problem !");
                        mImageView.setVisibility(View.VISIBLE);

                        mListView.setSelection(mAdapter.getCount() - 1);
                        break;
                    default:
                        message = mEditTextMessage.getText().toString();
                        sendMessage(message);
                        mEditTextMessage.setText("");

                        sendData();
//                        mListView.setSelection(mAdapter.getCount() - 1);
//                        mimicOtherMessage("Anything else u wanna add");
//                        mImageView.setVisibility(View.VISIBLE);
//                        mListView.setSelection(mAdapter.getCount() - 1);
                        break;

                }
            }
        });
    }

    private void sendData(){


        AndroidNetworking.post("https://arzooapp.herokuapp.com/data")
                .addBodyParameter(data) // posting java object
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(MainActivity.this, response.get("msg").toString() , Toast.LENGTH_SHORT).show();
                            mButtonSend.setEnabled(false);
                            mImageView.setEnabled(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        Toast.makeText(MainActivity.this, "OOPS! Internet is not working!!" +
                                "", Toast.LENGTH_SHORT).show();

                    }
                });



//        Data data =new Data("jats","11","hdhdh");
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<AckResponse> ackResponseCall = apiService.getResponse("name", "ss", ":ss");
//
//
//        ackResponseCall.enqueue(new Callback<AckResponse>() {
//            @Override
//            public void onResponse(Call<AckResponse> call, Response<AckResponse> response) {
//                if(response.isSuccessful())
//                {
//                    Toast.makeText(MainActivity.this, "Upload successfully completed", Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(MainActivity.this,   "Upload successfully cod", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<AckResponse> call, Throwable t) {
//                Toast.makeText(MainActivity.this,   "Uplofully cod", Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        mAdapter.add(chatMessage);
        //respond as Helloworld
    }

    private void sendURI (Uri uri){
        String ImageUri = uri.toString();
        ChatMessage chatMessage = new ChatMessage(ImageUri,false,true);
        mAdapter.add(chatMessage);
    }


    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        mAdapter.add(chatMessage);

    }

//    private void sendMessage() {
//        ChatMessage chatMessage = new ChatMessage(null, true, true);
//        mAdapter.add(chatMessage);
//        mimicOtherMessage();
//    }

//    private void mimicOtherMessage() {
//        ChatMessage chatMessage = new ChatMessage(null, false, true);
//        mAdapter.add(chatMessage);
//    }

    public void btnClick() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECTED_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PICTURE:
                //if (requestCode == RESULT_OK) {

                    Uri uri = data.getData();
                    sendURI(uri);


                    /*
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bitmap;
                    //thumbView.setImageURI(uri);
//                    Bitmap yourSelectedImage= BitmapFactory.decodeFile(filePath);
//                    Drawable d= new BitmapDrawable(yourSelectedImage);
                    bitmap = BitmapFactory.decodeFile(filePath);
                    iv.setImageBitmap(bitmap);
                    */
                sendData();
                break;

            default:
                break;


        }
    }
}

