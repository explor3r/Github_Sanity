package com.example.tomdong.sanity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import Controller.OnGetDataListener;
import Model.Budget;
import Model.BudgetModel;
import Model.CategoryModel;
import Model.StorageModel;
import Model.TransactionModel;
import Model.Variable;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    private ImageView SanityImage;
    private EditText Account;
    private EditText PassWord;
    private Button Login;
    private Button Register;
    private TextView ForgetPassword;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Declare and Initialization
        firebaseAuth = FirebaseAuth.getInstance();
        SanityImage = findViewById(R.id.SanityImage);
        Account = findViewById(R.id.UserT);
        PassWord = findViewById(R.id.PwT);
        Login = findViewById(R.id.LoginButton);
        Register = findViewById(R.id.RegisterButton);
        ForgetPassword = findViewById(R.id.ForgetTextView);

        //Login Animation
        Account.setVisibility(View.GONE);
        PassWord.setVisibility(View.GONE);
        Login.setVisibility(View.GONE);
        Register.setVisibility(View.GONE);
        ForgetPassword.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sanityicon_move);
        animation.setFillAfter(true);
        animation.setAnimationListener(this);
        SanityImage.startAnimation(animation);

        //AddListener
        Login.setOnClickListener(this);
        Register.setOnClickListener(this);
        ForgetPassword.setOnClickListener(this);

        // Init StorageModel
        StorageModel.GetInstance();
        StorageModel.SetContext(getApplicationContext());

        /**
         * ------------------ Test Database Model Functionality -------------------
         */
        StorageModel.GetInstance().DeleteFiles();

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            LoadData();
//        BudgetModel.GetInstance().AddBudget(new Budget("Budget1", 1508457600L, 30, new ArrayList<Long>()));
//        BudgetModel.GetInstance().AddBudget(new Budget("Budget2", 1508457600L, 30, new ArrayList<Long>()));
//        BudgetModel.GetInstance().AddBudget(new Budget("Budget3", 1508457600L, 30, new ArrayList<Long>()));

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //Animate other GUI
        Account.setAlpha(0f);
        Account.setVisibility(View.VISIBLE);
        PassWord.setAlpha(0f);
        PassWord.setVisibility(View.VISIBLE);
        Login.setAlpha(0f);
        Login.setVisibility(View.VISIBLE);
        Register.setAlpha(0f);
        Register.setVisibility(View.VISIBLE);
        ForgetPassword.setAlpha(0f);
        ForgetPassword.setVisibility(View.VISIBLE);
        //Finish animating and show
        int AnimationTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);
        Account.animate().alpha(1f).setDuration(AnimationTime).setListener(null);
        PassWord.animate().alpha(1f).setDuration(AnimationTime).setListener(null);

        Login.animate().alpha(0.8f).setDuration(AnimationTime).setListener(null);
        Register.animate().alpha(0.8f).setDuration(AnimationTime).setListener(null);
        ForgetPassword.animate().alpha(1f).setDuration(AnimationTime).setListener(null);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View view) {
        view.setEnabled(false);
        Log.d("MyApp", "I am here");
        //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        if (view == Register) {
            Log.d("MyApp", "Register");
            RegisterUser();
        }
        if (view == Login) {
            Log.d("MyApp", "Login");
            LoginUser();
        }
        if (view == ForgetPassword) {
            Log.d("MyApp", "Forget");
            ResetPassword();
        }
        // 下面的Code不要删！测试用
        /*List<Long> catIdSet = new ArrayList<>();
        catIdSet.add(123456789L);
        Budget b = new Budget("Life", 123456, 10, 500, catIdSet);
        Model.BudgetModel bm = Model.BudgetModel.GetInstance();
        bm.AddBudget(b);
        Model.StorageModel sm = new Model.StorageModel(getApplicationContext());
        boolean exists = sm.FilesExist();
        sm.DeleteFiles();
        exists = sm.FilesExist();
        sm.SaveObject(bm);
        exists = sm.FilesExist();
        bm.DeleteBudget(b.getmBudgetId());
        exists = sm.FilesExist();
        bm = sm.ReadObject("BudgetModel");
        exists = sm.FilesExist();
        bm.InitDataBase();
        exists = sm.FilesExist();*/
    }

    public void ResetPassword() {
        Account.setEnabled(false);
        PassWord.setEnabled(false);
        String emailAdd = Account.getText().toString();
        if (emailAdd.isEmpty()) {
            Toast.makeText(MainActivity.this, "Email Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(MainActivity.this, emailAdd, Toast.LENGTH_SHORT).show();
        firebaseAuth.sendPasswordResetEmail(emailAdd)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Email sent!!!", Toast.LENGTH_SHORT).show();
                            Log.d("MyApp", "Email sent.");
                        } else {
                            Toast.makeText(MainActivity.this, "Email sent Failed!!!", Toast.LENGTH_SHORT).show();
                        }
                        Account.setEnabled(true);
                        PassWord.setEnabled(true);
                        ForgetPassword.setEnabled(true);
                    }
                });
    }

    public void RegisterUser() {
        Account.setEnabled(false);
        PassWord.setEnabled(false);
        String email = Account.getText().toString();
        String pw = PassWord.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(MainActivity.this, "Email Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(MainActivity.this, "Password Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Register Succeeded", Toast.LENGTH_SHORT).show();
                } else {
                    Account.setEnabled(true);
                    PassWord.setEnabled(true);
                    Register.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void LoginUser() {
        Account.setEnabled(false);
        PassWord.setEnabled(false);
        String email = Account.getText().toString();
        String pw = PassWord.getText().toString();

        if (TextUtils.isEmpty(email)) {
            //Toast.makeText(MainActivity.this, "Email Empty!???", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pw)) {
            //Toast.makeText(MainActivity.this, "Password Empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    //Toast.makeText(MainActivity.this, "Login Succeeded", Toast.LENGTH_SHORT).show();
                    LoadData();
                } else {
                    Account.setEnabled(true);
                    PassWord.setEnabled(true);
                    Login.setEnabled(true);
                    //Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void LoadData() {
        // set the current user uid to Variable object
        Variable.GetInstance().setmUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());

        if (StorageModel.GetInstance().AreFilesExist()) {
            // If internal storage files exist, load locally
            StorageModel.GetInstance().ReadAll();
            StartActivity();
        } else {
            // If not, load from firebase
            BudgetModel.GetInstance().CloudGet(new OnGetDataListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(DataSnapshot data) {
                    CategoryModel.GetInstance().ReadCategoryFromDatabase(new OnGetDataListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(DataSnapshot data) {
                            TransactionModel.GetInstance().ReadTransaction(new OnGetDataListener() {
                                @Override
                                public void onStart() {
                                }

                                @Override
                                public void onSuccess(DataSnapshot data) {
                                    /*List<Long> newList = new ArrayList<Long>();
                                    newList.add(1508202725272L);
                                    BudgetModel.GetInstance().AddBudget(new Budget("Transportation", 1508457600L, 10, newList));
                                    newList = new ArrayList<Long>();
                                    BudgetModel.GetInstance().AddBudget(new Budget("Other", 1508457600L, 30, newList));*/
                                    StorageModel.GetInstance().SaveAll();
                                    StartActivity();
                                }

                                @Override
                                public void onFailed(DatabaseError databaseError) {
                                }
                            });
                        }

                        @Override
                        public void onFailed(DatabaseError databaseError) {
                        }
                    });
                }

                @Override
                public void onFailed(DatabaseError databaseError) {
                }
            });
        }
    }

    private void StartActivity() {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
