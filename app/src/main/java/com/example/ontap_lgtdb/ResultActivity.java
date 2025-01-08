package com.example.ontap_lgtdb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Kết nối với các thành phần giao diện
        TextView resultText = findViewById(R.id.resultText);
        LinearLayout reviewContainer = findViewById(R.id.reviewContainer);
        Button buttonBack = findViewById(R.id.buttonBack);

        // Lấy dữ liệu từ Intent
        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);
        List<Question> questions = (List<Question>) getIntent().getSerializableExtra("questions");
        List<Boolean> userAnswers = (List<Boolean>) getIntent().getSerializableExtra("userAnswers");

        // Hiển thị kết quả tổng hợp
        resultText.setText("Bạn đã làm đúng " + correctAnswers + "/" + totalQuestions + " câu.");

        // Kiểm tra dữ liệu và hiển thị danh sách câu hỏi, đáp án
        if (questions != null && userAnswers != null) {
            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                boolean isCorrect = userAnswers.get(i);

                // Hiển thị câu hỏi
                addTextViewToContainer(reviewContainer, "Câu " + (i + 1) + ": " + question.getQuestion(), 16, android.R.color.black);

                // Hiển thị hình ảnh (nếu có)
                if (question.getImage() != null && !question.getImage().isEmpty()) {
                    addImageViewToContainer(reviewContainer, question.getImage());
                }

                // Hiển thị danh sách các đáp án
                StringBuilder optionsBuilder = new StringBuilder();
                optionsBuilder.append("1. ").append(question.getOption1()).append("\n")
                        .append("2. ").append(question.getOption2()).append("\n");
                if (question.getOption3() != null) {
                    optionsBuilder.append("3. ").append(question.getOption3()).append("\n");
                }
                if (question.getOption4() != null) {
                    optionsBuilder.append("4. ").append(question.getOption4()).append("\n");
                }
                addTextViewToContainer(reviewContainer, optionsBuilder.toString(), 14, android.R.color.black);

                // Hiển thị đáp án đúng và kết quả của người dùng
                int color = isCorrect ? android.R.color.holo_green_dark : android.R.color.holo_red_dark;
                addTextViewToContainer(reviewContainer, "Đáp án đúng: " + question.getCorrectAnswer(), 14, android.R.color.holo_green_dark);
                addTextViewToContainer(reviewContainer, "Bạn trả lời: " + (isCorrect ? "Đúng" : "Sai"), 14, color);

                // Hiển thị đáp án người dùng chọn
                addTextViewToContainer(reviewContainer, "Bạn đã chọn đáp án: " + getSelectedAnswer(question), 14, android.R.color.darker_gray);

                // Hiển thị giải thích (nếu có)
                if (question.getExplanation() != null && !question.getExplanation().isEmpty()) {
                    addTextViewToContainer(reviewContainer, "Giải thích: " + question.getExplanation(), 14, android.R.color.darker_gray);
                }

                // Thêm khoảng cách giữa các câu hỏi
                addSeparatorToContainer(reviewContainer);
            }
        }

        // Xử lý sự kiện nút quay lại màn hình chính
        buttonBack.setOnClickListener(view -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    // Hàm thêm TextView vào container
    private void addTextViewToContainer(LinearLayout container, String text, int textSize, int colorRes) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(getResources().getColor(colorRes));
        container.addView(textView);
    }

    // Hàm thêm ImageView vào container
    private void addImageViewToContainer(LinearLayout container, String imageName) {
        ImageView imageView = new ImageView(this);
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        imageView.setImageResource(imageResId);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        container.addView(imageView);
    }

    // Hàm thêm khoảng cách (separator) giữa các câu hỏi
    private void addSeparatorToContainer(LinearLayout container) {
        View separator = new View(this);
        separator.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                16
        ));
        container.addView(separator);
    }

    // Hàm lấy đáp án người dùng đã chọn
    private String getSelectedAnswer(Question question) {
        int selectedId = Integer.parseInt(question.getCorrectAnswer());
        switch (selectedId) {
            case 1:
                return question.getOption1();
            case 2:
                return question.getOption2();
            case 3:
                return question.getOption3();
            case 4:
                return question.getOption4();
            default:
                return "Không rõ";
        }
    }
}
