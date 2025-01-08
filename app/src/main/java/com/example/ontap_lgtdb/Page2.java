package com.example.ontap_lgtdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Page2 extends AppCompatActivity {
    private TextView questionNumber, questionText, explanationText; // Thêm TextView để quản lý giải thích
    private RadioGroup optionsContainer;
    private ImageView questionImage;
    private Button buttonNext, buttonBack1;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;

    private List<Boolean> userAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        questionNumber = findViewById(R.id.questionNumber);
        questionText = findViewById(R.id.questionText);
        explanationText = findViewById(R.id.explanationText);
        optionsContainer = findViewById(R.id.optionsContainer);
        questionImage = findViewById(R.id.questionImage);
        buttonNext = findViewById(R.id.buttonNext);
        buttonBack1 = findViewById(R.id.buttonBack1);

        // Lấy danh sách câu hỏi hoặc groupId từ Intent
        questions = (List<Question>) getIntent().getSerializableExtra("questions");

        if (questions == null) {
            int groupId = getIntent().getIntExtra("groupId", -1);
            if (groupId == -1) {
                Toast.makeText(this, "Không tìm thấy nhóm câu hỏi!", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            questions = loadQuestionsByGroup(groupId);
        }

        if (questions == null || questions.isEmpty()) {
            Toast.makeText(this, "Không có câu hỏi nào!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        userAnswers = new ArrayList<>(questions.size());
        displayQuestion();

        buttonNext.setOnClickListener(view -> {
            if (!checkAnswer()) {
                Toast.makeText(this, "Hãy chọn đáp án!", Toast.LENGTH_SHORT).show();
                return;
            }

            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                displayQuestion();
            } else {
                showResult();
            }
        });

        buttonBack1.setOnClickListener(view -> finish());
    }




    private List<Question> loadQuestionsByGroup(int groupId) {
        List<Question> questionList = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getQuestionsByGroup(groupId, 30);

        if (cursor != null) {
            try {
                int colQuestion = cursor.getColumnIndex(DBHelper.COLUMN_QUESTION);
                int colOption1 = cursor.getColumnIndex(DBHelper.COLUMN_OPTION1);
                int colOption2 = cursor.getColumnIndex(DBHelper.COLUMN_OPTION2);
                int colOption3 = cursor.getColumnIndex(DBHelper.COLUMN_OPTION3);
                int colOption4 = cursor.getColumnIndex(DBHelper.COLUMN_OPTION4);
                int colCorrectAnswer = cursor.getColumnIndex(DBHelper.COLUMN_CORRECT_ANSWER);
                int colImage = cursor.getColumnIndex("image");
                int colExplanation = cursor.getColumnIndex("explanation");

                while (cursor.moveToNext()) {
                    String explanation = colExplanation >= 0 ? cursor.getString(colExplanation) : null;
                    Question question = new Question(
                            cursor.getString(colQuestion),
                            cursor.getString(colOption1),
                            cursor.getString(colOption2),
                            cursor.getString(colOption3),
                            cursor.getString(colOption4),
                            cursor.getString(colCorrectAnswer),
                            colImage >= 0 ? cursor.getString(colImage) : null,
                            explanation
                    );
                    questionList.add(question);
                }
            } finally {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
        }
        return questionList;
    }

    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        questionNumber.setText("Câu " + (currentQuestionIndex + 1) + ":");
        questionText.setText(currentQuestion.getQuestion());

        // Hiển thị các lựa chọn
        optionsContainer.removeAllViews();
        RadioButton option1 = new RadioButton(this);
        option1.setText(currentQuestion.getOption1());
        optionsContainer.addView(option1);

        RadioButton option2 = new RadioButton(this);
        option2.setText(currentQuestion.getOption2());
        optionsContainer.addView(option2);

        if (currentQuestion.getOption3() != null) {
            RadioButton option3 = new RadioButton(this);
            option3.setText(currentQuestion.getOption3());
            optionsContainer.addView(option3);
        }

        if (currentQuestion.getOption4() != null) {
            RadioButton option4 = new RadioButton(this);
            option4.setText(currentQuestion.getOption4());
            optionsContainer.addView(option4);
        }

        // Hiển thị hình ảnh nếu có
        if (currentQuestion.getImage() != null) {
            int imageResId = getResources().getIdentifier(currentQuestion.getImage(), "drawable", getPackageName());
            questionImage.setImageResource(imageResId);
            questionImage.setVisibility(View.VISIBLE);
        } else {
            questionImage.setVisibility(View.GONE);
        }
    }


    private boolean checkAnswer() {
        int selectedId = optionsContainer.getCheckedRadioButtonId();
        if (selectedId == -1) {
            return false; // Người dùng chưa chọn đáp án
        }

        // Lấy đáp án người dùng chọn
        RadioButton selectedButton = findViewById(selectedId);
        String selectedAnswer = selectedButton.getText().toString().trim();
        Question currentQuestion = questions.get(currentQuestionIndex);

        // Lấy đáp án đúng từ câu hỏi hiện tại
        String correctAnswer = currentQuestion.getCorrectAnswer().trim();
        String correctAnswerText = "";

        if (correctAnswer.equals("1")) {
            correctAnswerText = currentQuestion.getOption1();
        } else if (correctAnswer.equals("2")) {
            correctAnswerText = currentQuestion.getOption2();
        } else if (correctAnswer.equals("3")) {
            correctAnswerText = currentQuestion.getOption3();
        } else if (correctAnswer.equals("4")) {
            correctAnswerText = currentQuestion.getOption4();
        }

        boolean isCorrect = selectedAnswer.equals(correctAnswerText);

        userAnswers.add(isCorrect);
        if (isCorrect) {
            correctAnswers++;
        }

        return true;
    }

    private void showResult() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("correctAnswers", correctAnswers);
        intent.putExtra("totalQuestions", questions.size());
        intent.putExtra("questions", new ArrayList<>(questions)); // Đảm bảo danh sách câu hỏi kèm explanation được truyền qua
        intent.putExtra("userAnswers", new ArrayList<>(userAnswers));
        startActivity(intent);
    }

}
