package com.example.onlineshopp.ActivityLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.onlineshopp.InterFace;
import com.example.onlineshopp.MainActivity;
import com.example.onlineshopp.Object.User;
import com.example.onlineshopp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements InterFace {
    private static final String TAG = "SignupActivity";

    private TextInputLayout  userNameInputLayout,emailInputLayout, mobileNumberInputLayout, passwordInputLayout,
            confirmPasswordInputLayout;

    private TextView loginText;
    private Button signupButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setMapping();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
       DatabaseReference databaseReference;
       databaseReference=FirebaseDatabase.getInstance().getReference("users");
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                   User sa=dataSnapshot.getValue(User.class);

                   Log.w("FirebaseStore",sa.getID()+"\n"+sa.getName()+"\t"+sa.getGender()+"\n"+sa.getDate()+sa.getPhone());
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        // Khởi tạo Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Tham chiếu đến tài liệu "SF" trong collection "cities"
        DocumentReference docRef = db.collection("user").document("INaqKmwPVwguxQNfVZmI");

// Lấy dữ liệu từ tài liệu
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                // Lấy dữ liệu từ tài liệu
                                String address = document.getString("address");
                                String date = document.getString("date");
                                String gender = document.getString("gender");
                                String id = document.getString("id");
                                String name = document.getString("name");
                                String phone = document.getString("phone");
                                Long role = document.getLong("role");

                                // Hiển thị dữ liệu
                                Log.d(TAG, "Address: " + address);
                                Log.d(TAG, "Date: " + date);
                                Log.d(TAG, "Gender: " + gender);
                                Log.d(TAG, "ID: " + id);
                                Log.d(TAG, "Name: " + name);
                                Log.d(TAG, "Phone: " + phone);
                                Log.d(TAG, "Role: " + role);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.w(TAG, "Get failed with ", task.getException());
                        }
                    }
                });

    }

    @Override
    public void setMapping() {
        loginText=findViewById(R.id.loginText);
        signupButton=findViewById(R.id.signupButton);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        mobileNumberInputLayout = findViewById(R.id.mobileNumberInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        confirmPasswordInputLayout=findViewById(R.id.confirmPasswordInputLayout);
        signupButton = findViewById(R.id.signupButton);
        loginText = findViewById(R.id.loginText);
    }

    @Override
    public void eVentCompoment() {
        //Btn Register
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


        //Btn Login

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void registerUser() {
        String email = emailInputLayout.getEditText().getText().toString().trim();
        String mobileNumber = mobileNumberInputLayout.getEditText().getText().toString().trim();
        String password = passwordInputLayout.getEditText().getText().toString().trim();
        String confirmPassword = confirmPasswordInputLayout.getEditText().getText().toString().trim();

        if (email.isEmpty() || mobileNumber.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUserInfoToFirestore(user, email, password, mobileNumber);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void saveUserInfoToFirestore(FirebaseUser user, String email, String password, String mobileNumber) {
        String userId = user.getUid();
        DocumentReference accountRef = db.collection("accounts").document(userId);
        DocumentReference customerRef = db.collection("customers").document(userId);

        // Dữ liệu cho collection accounts
        Map<String, Object> accountData = new HashMap<>();
        accountData.put("createdAt", new Date());
        accountData.put("updatedAt", new Date());
        accountData.put("email", email);
        accountData.put("password", password);
        accountData.put("phoneNumber", mobileNumber);
        accountData.put("roleId", "CUSTOMER");
        accountData.put("status", true);
        accountData.put("uid", userId);

        // Dữ liệu cho collection customers
        Map<String, Object> customerData = new HashMap<>();
        customerData.put("uid", userId);
        customerData.put("createdAt", new Date());
        customerData.put("updatedAt", new Date());

        db.runTransaction(transaction -> {
            transaction.set(accountRef, accountData);
            transaction.set(customerRef, customerData);
            return null;
        }).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "User profile created in both accounts and customers collections.");
            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }).addOnFailureListener(e -> {
            Log.w(TAG, "Error writing user data", e);
            String errorMessage = "Lỗi khi lưu thông tin tài khoản: ";
            if (e instanceof FirebaseFirestoreException) {
                FirebaseFirestoreException ffe = (FirebaseFirestoreException) e;
                errorMessage += ffe.getCode().toString();
            } else {
                errorMessage += e.getMessage();
            }
            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            // Xóa tài khoản khỏi Authentication nếu lưu Firestore thất bại
            user.delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User account deleted from Authentication.");
                } else {
                    Log.w(TAG, "Failed to delete user account from Authentication.", task.getException());
                }
            });
        });
    }
}