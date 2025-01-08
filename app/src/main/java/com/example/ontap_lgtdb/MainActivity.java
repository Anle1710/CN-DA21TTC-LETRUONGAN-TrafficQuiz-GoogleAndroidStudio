package com.example.ontap_lgtdb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatDelegate;
import java.util.List;
import java.util.ArrayList;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        // Tắt chế độ Dark Mode cho toàn bộ ứng dụng
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_page2);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the CardView by ID and set an onClickListener
        findViewById(R.id.cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a new activity when the CardView is clicked
                Intent intent = new Intent(MainActivity.this, Page2.class);
                intent.putExtra("groupId", 1); // Nhóm 1
                startActivity(intent);
            }
        });

        findViewById(R.id.CardView1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                List<Question> allQuestions = new ArrayList<>();

                // Lấy ngẫu nhiên 5 câu hỏi từ mỗi groupId
                allQuestions.addAll(dbHelper.getRandomQuestionsByGroup(1, 5));
                allQuestions.addAll(dbHelper.getRandomQuestionsByGroup(2, 5));
                allQuestions.addAll(dbHelper.getRandomQuestionsByGroup(3, 5));
                allQuestions.addAll(dbHelper.getRandomQuestionsByGroup(4, 5));

                // Kiểm tra nếu không có câu hỏi
                if (allQuestions.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Không có câu hỏi nào!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Chuyển danh sách câu hỏi tới Page2
                Intent intent = new Intent(MainActivity.this, Page2.class);
                intent.putExtra("questions", (ArrayList<Question>) allQuestions); // Truyền danh sách câu hỏi
                startActivity(intent);
            }
        });
        findViewById(R.id.CardView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a new activity when the CardView is clicked
                Intent intent = new Intent(MainActivity.this, Page2.class);
                intent.putExtra("groupId", 2);
                startActivity(intent);
            }
        });
        findViewById(R.id.CardView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a new activity when the CardView is clicked
                Intent intent = new Intent(MainActivity.this, Page2.class);
                intent.putExtra("groupId", 3);
                startActivity(intent);
            }
        });
        findViewById(R.id.CardView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a new activity when the CardView is clicked
                Intent intent = new Intent(MainActivity.this, Page2.class);
                intent.putExtra("groupId", 4);
                startActivity(intent);
            }
        });
    }
}
