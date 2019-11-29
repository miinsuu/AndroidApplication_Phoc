package com.example.phoc.DatabaseConnection;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatabaseQueryClass {
    private static DatabaseQueryClass databaseQuery = new DatabaseQueryClass();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private  DatabaseQueryClass(){

    }
    private static void findUserIdByNickname(String nick, final DataListener dataListener){
        CollectionReference postRef = db.collection("users");
        Query query = postRef.whereEqualTo("nick", nick);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        dataListener.getData(document.getId());
                    }
                } else {
                    Log.d("User", "Error getting documents: ", task.getException());
                }
            }
        });
    }
    public static class Theme{
        public static void getTodayTheme(final DataListener dataListener){
            Log.d("Theme", "getTodayTheme called");
            CollectionReference themeRef = db.collection("themes");
            themeRef.document("todaytheme")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d("Theme", "get today theme success" );
                        dataListener.getData(task.getResult().getData());
                    } else {
                        Log.d("Post", "Error getting documents: ", task.getException());
                    }
                }
            });
        }
    }

    public static class Post{
        public static void getPostsByTheme(String theme, final DataListener dataListener){
            Log.d("Post", "byTheme called");
            CollectionReference postRef = db.collection("posts");
            Query query = postRef.whereEqualTo("theme", theme);

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Post", document.getId() + " => " + document.getData());
                            dataListener.getData(task.getResult());
                        }
                    } else {
                        Log.d("Post", "Error getting documents: ", task.getException());
                    }
                }
            });
        }
        public static void getPostsByNickname(String nickname, final DataListener dataListener){
            Log.d("Post", "by nick called");
            findUserIdByNickname(nickname, new DataListener() {
                @Override
                public void getData(Object userId) {
                    CollectionReference postRef = db.collection("posts");
                    Query query = postRef.whereEqualTo("userId", userId);

                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            ArrayList<JsonObject> dataList = new ArrayList<JsonObject>();

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("Post", document.getId() + " => " + document.getData());

                                    String json = new Gson().toJson(document.getData());
                                    JsonElement element = new JsonParser().parse(json);
                                    JsonObject jobj = element.getAsJsonObject();

                                    dataList.add(jobj);
                                }
                                dataListener.getData(dataList);
                            } else {
                                Log.d("Post", "Error getting documents: ", task.getException());
                            }
                        }
                    });
                }
            });
        }
        public static void getPostsByUserId(String userId, final DataListener dataListener){
            Log.d("Post", "by userId called");
            CollectionReference postRef = db.collection("posts");
            Query query = postRef.whereEqualTo("userId", userId);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        //ArrayList<>
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Post", document.getId() + " => " + document.getData());
                            dataListener.getData(document.getData());
                        }
                    } else {
                        Log.d("Post", "Error getting documents: ", task.getException());
                    }
                }
            });
        }

        public static void getPostBySubscribing(String[] subs){
        }
        public static void createPost(final String cameraSettingJson,
                                      final String content,
                                      final String imgUrl,
                                      final String theme)
        {
            Map<String, Object> post  = new HashMap<>();
            post.put("camera", cameraSettingJson);
            post.put("content",content );
            post.put("img", imgUrl);
            post.put("theme", theme);
            post.put("createdAt",  (new Timestamp(new Date().getTime())).toString());
            post.put("num_phoc", 0);
            //userId 추가할것

            db.collection("posts")
                    .add(post)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Post", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Post", "Error adding document", e);
                        }
                    });
        }


    }
    public static class User{
        public static void subscribe(String userId, String followingId){
            Map<String, Object> subscribing  = new HashMap<>();
            subscribing.put("followerId", userId);
            subscribing.put("followingId",followingId );


            db.collection("subscribe")
                    .add(subscribing)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("subscribe", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("subscribe", "Error adding document", e);
                        }
                    });
        }
        public static void cancelSubscribe(String userId, String followingId){

          db.collection("subscribe")
                  .whereEqualTo("followerId", userId)
                  .whereEqualTo("followingId", followingId)
                  .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  if (task.isSuccessful()) {
                      for (QueryDocumentSnapshot document : task.getResult()) {
                          db.collection("subscribe").document(document.getId())
                                  .delete()
                                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                                      public void onSuccess(Void aVoid) {
                                          Log.d("subscribe", "DocumentSnapshot successfully deleted!");
                                      }
                                  })
                                  .addOnFailureListener(new OnFailureListener() {
                                      @Override
                                      public void onFailure(@NonNull Exception e) {
                                          Log.w("subscribe", "Error deleting document", e);
                                      }
                                  });
                      }
                  } else {
                      Log.d("subscribe", "Error getting documents: ", task.getException());
                  }
              }
          });
        }
        public static void getUserInfoByEmail(String email, final DataListener dataListener){
            db.collection("users")
                    .whereEqualTo("email", email)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        String data = null;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String json = new Gson().toJson(document.getData());
                            JsonElement element = new JsonParser().parse(json);
                            JsonObject jobj = element.getAsJsonObject();
                            jobj.addProperty("userId", document.getId().toString());

                            data = new Gson().toJson(jobj);
                        }
                        if(data!=null)
                            dataListener.getData(data);
                    } else {
                        Log.d("user", "Error getting documents: ", task.getException());
                    }
                }
            });
        }
    }


}
