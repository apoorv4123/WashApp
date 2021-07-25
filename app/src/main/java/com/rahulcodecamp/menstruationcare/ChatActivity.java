package com.rahulcodecamp.menstruationcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rahulcodecamp.menstruationcare.adapters.MessageAdapter;
import com.rahulcodecamp.menstruationcare.models.AllMethods;
import com.rahulcodecamp.menstruationcare.models.Message;
//import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference messagedb;
    MessageAdapter messageAdapter;
    Users u;
    List<Message> messages;

    RecyclerView msgRv;
    EditText msgEdtv;
    ImageView sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(v -> {
            String msg = msgEdtv.getText().toString();
            if (msg.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Type a Message", Toast.LENGTH_SHORT).show();
            } else {
                Message message = new Message(msgEdtv.getText().toString(), u.getName());
                msgEdtv.setText("");
                messagedb.push().setValue(message);
            }

        });
    }

    private void init() {

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        u = new Users();

        msgRv = findViewById(R.id.msgRv);
        msgEdtv = findViewById(R.id.msgEdtv);
//        sendBtn = findViewById(R.id.sendBtn);
//        sendBtn.setOnClickListener(this);
        messages = new ArrayList<>();
    }

//    @Override
//    public void onClick(View v) {
//
//        if (!TextUtils.isEmpty(msgEdtv.getText().toString())) {
//            Message message = new Message(msgEdtv.getText().toString(), u.getName());
//            msgEdtv.setText("");
//            messagedb.push().setValue(message);
//        } else {
//            Toast.makeText(getApplicationContext(), "Type a Message", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser = auth.getCurrentUser(); // get current firebase user

//        if(currentUser == null){
//            Toast.makeText(getApplicationContext(), "No user registered", Toast.LENGTH_LONG).show();
//        }
//        FirebaseAuth.getInstance().getUid();
//        assert currentUser != null;
        u.setUid(currentUser.getUid());
//        u.setUid(FirebaseAuth.getInstance().getUid());
        u.setEmail(currentUser.getEmail());

        database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                u = snapshot.getValue(Users.class);
//                assert u != null;
                u.setUid(currentUser.getUid());
//                u.setEmail(currentUser.getEmail());
                AllMethods.name = u.getName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        messagedb = database.getReference("messages");
        messagedb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // This method will display all the messages
                Message message = snapshot.getValue(Message.class);
//                assert message != null;
                message.setKey(snapshot.getKey());
                messages.add(message);
                displayMessages(messages);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
//                assert message != null;
                message.setKey(snapshot.getKey());

                List<Message> newMessages = new ArrayList<Message>();

                for (Message m : messages) {
                    if (m.getKey().equals(message.getKey())) {
                        newMessages.add(message);
                    } else {
                        newMessages.add(m);
                    }
                }
                messages = newMessages;
                displayMessages(messages);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                Message message = snapshot.getValue(Message.class);
//                message.setKey(snapshot.getKey());
//
//                List<Message> newMessages = new ArrayList<>();
//
//              for (Message m : messages) {
//                    if (!m.getKey().equals(message.getKey())) {
//                        newMessages.add(m);
//                    }
//                }
//
//                messages = newMessages;
//                displayMessages(messages);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        messages = new ArrayList<>();
    }

    private void displayMessages(List<Message> messages) {
        msgRv.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
//        msgRv.setHasFixedSize(true);
        messageAdapter = new MessageAdapter(ChatActivity.this, messages, messagedb);
        msgRv.setAdapter(messageAdapter);
    }
}


// PREVIOUS CODE
// package com.rahulcodecamp.menstruationcare;

// import androidx.annotation.NonNull;
// import androidx.annotation.Nullable;
// import androidx.appcompat.app.AppCompatActivity;
// import androidx.recyclerview.widget.LinearLayoutManager;
// import androidx.recyclerview.widget.RecyclerView;

// import android.os.Bundle;
// import android.text.TextUtils;
// import android.widget.EditText;
// import android.widget.ImageView;
// import android.widget.Toast;

// import com.google.firebase.auth.FirebaseAuth;
// import com.google.firebase.auth.FirebaseUser;
// import com.google.firebase.database.ChildEventListener;
// import com.google.firebase.database.DataSnapshot;
// import com.google.firebase.database.DatabaseError;
// import com.google.firebase.database.DatabaseReference;
// import com.google.firebase.database.FirebaseDatabase;
// import com.google.firebase.database.ValueEventListener;
// import com.rahulcodecamp.menstruationcare.adapters.MessageAdapter;
// import com.rahulcodecamp.menstruationcare.models.AllMethods;
// import com.rahulcodecamp.menstruationcare.models.Message;

// import org.jetbrains.annotations.NotNull;

// import java.util.ArrayList;
// import java.util.List;

// public class ChatActivity extends AppCompatActivity {

//     FirebaseAuth auth;
//     FirebaseDatabase database;
//     DatabaseReference messagedb;
//     MessageAdapter messageAdapter;
//     Users u;
//     List<Message> messages;

//     RecyclerView msgRv;
//     EditText msgEdtv;
//     ImageView sendBtn;

//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_main);

//         init();
//         sendBtn = findViewById(R.id.sendBtn);
//         sendBtn.setOnClickListener(v -> {

//             if (!TextUtils.isEmpty(msgEdtv.getText().toString())) {
//                 Message message = new Message(msgEdtv.getText().toString(), u.getName());
//                 msgEdtv.setText("");
//                 messagedb.push().setValue(message);
//             } else {
//                 Toast.makeText(getApplicationContext(), "Type a Message", Toast.LENGTH_SHORT).show();
//             }

//         });
//     }

//     private void init() {

//         auth = FirebaseAuth.getInstance();
//         database = FirebaseDatabase.getInstance();
//         u = new Users();

//         msgRv = findViewById(R.id.msgRv);
//         msgEdtv = findViewById(R.id.msgEdtv);
// //        sendBtn = findViewById(R.id.sendBtn);
// //        sendBtn.setOnClickListener(this);
//         messages = new ArrayList<>();
//     }

// //    @Override
// //    public void onClick(View v) {
// //
// //        if (!TextUtils.isEmpty(msgEdtv.getText().toString())) {
// //            Message message = new Message(msgEdtv.getText().toString(), u.getName());
// //            msgEdtv.setText("");
// //            messagedb.push().setValue(message);
// //        } else {
// //            Toast.makeText(getApplicationContext(), "Type a Message", Toast.LENGTH_SHORT).show();
// //        }
// //    }

//     @Override
//     protected void onStart() {
//         super.onStart();

//         final FirebaseUser currentUser = auth.getCurrentUser();

//         assert currentUser != null;
//         u.setUid(currentUser.getUid());
//         u.setEmail(currentUser.getEmail());

//         database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//             @Override
//             public void onDataChange(@NotNull DataSnapshot snapshot) {
//                 u = snapshot.getValue(Users.class);
//                 assert u != null;
//                 u.setUid(currentUser.getUid());
//                 AllMethods.name = u.getName();
//             }

//             @Override
//             public void onCancelled(@NotNull DatabaseError error) {

//             }
//         });

//         messagedb = database.getReference("messages");
//         messagedb.addChildEventListener(new ChildEventListener() {
//             @Override
//             public void onChildAdded(@NotNull DataSnapshot snapshot, String previousChildName) {
//                 // This method will display all the messages
//                 Message message = snapshot.getValue(Message.class);
//                 assert message != null;
//                 message.setKey(snapshot.getKey());
//                 messages.add(message);
//                 displayMessages(messages);
//             }

//             @Override
//             public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//                 Message message = snapshot.getValue(Message.class);
//                 assert message != null;
//                 message.setKey(snapshot.getKey());

//                 List<Message> newMessages = new ArrayList<>();

//                 for (Message m : messages) {
//                     if (m.getKey().equals(message.getKey())) {
//                         newMessages.add(message);
//                     } else {
//                         newMessages.add(m);
//                     }
//                 }

//                 messages = newMessages;

//                 displayMessages(messages);
//             }

//             @Override
//             public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
//                 Message message = snapshot.getValue(Message.class);
//                 assert message != null;
//                 message.setKey(snapshot.getKey());

//                 List<Message> newMessages = new ArrayList<>();

//                 for (Message m : messages) {
//                     if (!m.getKey().equals(message.getKey())) {
//                         newMessages.add(m);
//                     }
//                 }

//                 messages = newMessages;

//                 displayMessages(messages);
//             }

//             @Override
//             public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

//             }

//             @Override
//             public void onCancelled(@NonNull @NotNull DatabaseError error) {

//             }
//         });
//     }

//     @Override
//     protected void onResume() {
//         super.onResume();
//         messages = new ArrayList<>();
//     }

//     private void displayMessages(List<Message> messages) {
//         msgRv.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
// //        msgRv.setHasFixedSize(true);
//         messageAdapter = new MessageAdapter(ChatActivity.this, messages, messagedb);
//         msgRv.setAdapter(messageAdapter);
//     }
// }
