package com.example.ontap_lgtdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ontap_lgtdb.db";
    public static final int DATABASE_VERSION = 5;

    public static final String TABLE_QUESTIONS = "questions";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_OPTION1 = "option1";
    public static final String COLUMN_OPTION2 = "option2";
    public static final String COLUMN_OPTION3 = "option3";
    public static final String COLUMN_OPTION4 = "option4";
    public static final String COLUMN_CORRECT_ANSWER = "correct_answer";
    public static final String COLUMN_GROUP_ID = "group_id";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_EXPLANATION = "explanation";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_QUESTION + " TEXT, "
                + COLUMN_OPTION1 + " TEXT, "
                + COLUMN_OPTION2 + " TEXT, "
                + COLUMN_OPTION3 + " TEXT, "
                + COLUMN_OPTION4 + " TEXT, "
                + COLUMN_CORRECT_ANSWER + " TEXT, "
                + COLUMN_GROUP_ID + " INTEGER, "
                + COLUMN_IMAGE + " TEXT, "
                + COLUMN_EXPLANATION + " TEXT)";
        db.execSQL(CREATE_QUESTIONS_TABLE);
        addSampleQuestions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 5) {
            db.execSQL("ALTER TABLE " + TABLE_QUESTIONS + " ADD COLUMN " + COLUMN_EXPLANATION + " TEXT");
        }
    }

    // Hàm thêm câu hỏi vào cơ sở dữ liệu
    private void addQuestion(SQLiteDatabase db, String question, String option1, String option2, String option3, String option4, String correctAnswer, int groupId) {
        addQuestion(db, question, option1, option2, option3, option4, correctAnswer, groupId, null, null);
    }

    private void addQuestion(SQLiteDatabase db, String question, String option1, String option2, String option3, String option4, String correctAnswer, int groupId, String image) {
        addQuestion(db, question, option1, option2, option3, option4, correctAnswer, groupId, image, null);
    }

    private void addQuestion(SQLiteDatabase db, String question, String option1, String option2, String option3, String option4, String correctAnswer, int groupId, String image, String explanation) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, question);
        values.put(COLUMN_OPTION1, option1);
        values.put(COLUMN_OPTION2, option2);
        values.put(COLUMN_OPTION3, option3);
        values.put(COLUMN_OPTION4, option4);
        values.put(COLUMN_CORRECT_ANSWER, correctAnswer);
        values.put(COLUMN_GROUP_ID, groupId);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_EXPLANATION, explanation);
        db.insert(TABLE_QUESTIONS, null, values);
    }

    // Hàm thêm các câu hỏi mẫu vào cơ sở dữ liệu
    private void addSampleQuestions(SQLiteDatabase db) {
        // Ví dụ thêm câu hỏi cho các nhóm groupId
        addQuestion(db,
                "Khái niệm 'đường bộ' được hiểu như thế nào là đúng?", // Câu hỏi
                "\"Đường bộ\" gồm: Đường, cầu đường bộ, hầm đường bộ", // Lựa chọn 1
                "\"Đường bộ\" gồm: Đường, cầu đường bộ, hầm đường bộ, bến phà đường bộ", // Lựa chọn 2
                null, // Lựa chọn 3 (không có)
                null, // Lựa chọn 4 (không có)
                "2",// Đáp án đúng
                1//nhóm 1
        );
        addQuestion(db,
                "Khái niệm 'Công trình đường bộ' được hiểu như thế nào là đúng?", // Câu hỏi
                "\"Công trình đường bộ\" gồm: Đường bộ, nơi dừng xe, đỗ xe trên đường, đèn tím hiệu, cọc tiêu, biển báo hiệu, dải phân cách", // Lựa chọn 1
                "\"Công trình đường bộ\" gồm: Đường bộ, nơi dừng xe, đỗ xe trên đường, đèn tín hiệu, cọc tiêu, biển báo hiệu, dải phân cách, hệ thống thoát nước và công trình, thiết bị phụ trợ khác", // Lựa chọn 2
                null, // Lựa chọn 3 (không có)
                null, // Lựa chọn 4 (không có)
                "2", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Khái niệm 'Phần đường xe chạy' được hiểu như thế nào là đúng?", // Câu hỏi
                "Là phần của đường bộ được sử dụng cho các phương tiện giao thông qua lại", // Lựa chọn 1
                "Là phần của đường bộ được sử dụng cho các phương tiện giao thông qua lại, dải đất dọc hai bên đường để đảm bảo an toàn giao thông", // Lựa chọn 2
                "Cả hai ý trên", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "1", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Khái niệm 'Làn đường' được hiểu như thế nào là đúng?", // Câu hỏi
                "Là một phần của phần đường xe chạy được chia theo chiều dọc của đường", // Lựa chọn 1
                "Là một phần của phần đường xe chạy được chia theo chiều dọc của đường, có bề rộng đủ cho xe chạy an toàn", // Lựa chọn 2
                null, // Lựa chọn 3 (không có)
                null, // Lựa chọn 4 (không có)
                "2", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Khái niệm 'Khổ giới hạn của đường bộ' được hiểu như thế nào là đúng?", // Câu hỏi
                "Là khoảng trống có kích thước giới hạn về chiều cao, chiều rộng của đường, cầu, hầm trên đường bộ để các xe kể cả hàng hoá xếp trên xe đi qua được an toàn", // Lựa chọn 1
                "Là khoảng trống có kích thước giới hạn về chiều rộng của đường, cầu, hầm trên đường bộ để các xe kể cả hàng hoá xếp trên xe đi qua được an toàn", // Lựa chọn 2
                "Là khoảng trống có kích thước giới hạn về chiều cao, chiều rộng của đường, cầu, hầm trên đường bộ để các xe đi qua được an toàn", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "1", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Khái niệm 'Dải phân cách' được hiểu như thế nào là đúng?", // Câu hỏi
                "Là bộ phận của đường để phân chia mặt đường thành hai chiều chạy riêng biệt", // Lựa chọn 1
                "Là bộ phận của đường để phân chia phần đường của xe cơ giới và xe thô sơ", // Lựa chọn 2
                "Cả hai ý trên", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "3", // Đáp án đúng
                1 //nhóm 1
        );

        addQuestion(db,
                "Có mấy loại dải phân cách?", // Câu hỏi
                "Loại cố định", // Lựa chọn 1
                "Loại di động", // Lựa chọn 2
                "Cả hai loại trên", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "3", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trong Luật giao thông đường bộ khái niệm 'Đường cao tốc' được hiểu như thế nào là đúng?", // Câu hỏi
                "Là đường chỉ dành riêng cho xe cơ giới chạy với tốc độ cao, có dải phân cách chia đường cho xe chạy theo hai chiều ngược nhau riêng biệt và không giao cắt cùng mức với đường khác", // Lựa chọn 1
                "Là đường chỉ dành riêng cho xe cơ giới chạy với tốc độ cao, có dải phân cách chia đường cho xe chạy theo hai chiều ngược nhau riêng biệt và xe thô sơ, không giao cắt cùng mức với đường khác", // Lựa chọn 2
                null, // Lựa chọn 3 (không có)
                null, // Lựa chọn 4 (không có)
                "1", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "'Phương tiện giao thông đường bộ' gồm những loại nào?", // Câu hỏi
                "Phương tiện giao thông cơ giới đường bộ", // Lựa chọn 1
                "Phương tiện giao thông thô sơ đường bộ", // Lựa chọn 2
                "Cả hai loại nêu trên", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "3", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "'Phương tiện giao thông cơ giới đường bộ' gồm những loại nào?", // Câu hỏi
                "Ô-tô, máy kéo, xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy", // Lựa chọn 1
                "Ô-tô, máy kéo, xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy và các loại xe tương tự, kể cả xe cơ giới dùng cho người tàn tật", // Lựa chọn 2
                null, // Lựa chọn 3 (không có)
                null, // Lựa chọn 4 (không có)
                "2", // Đáp án đúng
                1 //nhóm 1
        );

        addQuestion(db,
                "'Phương tiện giao thông thô sơ đường bộ' gồm những loại nào?", // Câu hỏi
                "Những loại xe không di chuyển bằng sức động cơ như xe đạp, xe xích lô", // Lựa chọn 1
                "Xe súc vật kéo và các loại xe tương tự", // Lựa chọn 2
                "Cả hai ý trên", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "3", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "'Phương tiện tham gia giao thông đường bộ' gồm những loại nào?", // Câu hỏi
                "Phương tiện giao thông cơ giới đường bộ", // Lựa chọn 1
                "Phương tiện giao thông thô sơ đường bộ", // Lựa chọn 2
                "Xe máy chuyên dùng", // Lựa chọn 3
                "Cả ba loại trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "'Người tham gia giao thông đường bộ' gồm những thành phần nào?", // Câu hỏi
                "Người điều khiển, người sử dụng phương tiện tham gia giao thông đường bộ", // Lựa chọn 1
                "Người điều khiển, dẫn dắt súc vật", // Lựa chọn 2
                "Người đi bộ trên đường bộ", // Lựa chọn 3
                "Cả ba thành phần nêu trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "'Người điều khiển phương tiện tham gia giao thông' gồm những thành phần nào?", // Câu hỏi
                "Người điều khiển xe cơ giới", // Lựa chọn 1
                "Người điều khiển xe thô sơ", // Lựa chọn 2
                "Người điều khiển xe máy chuyên dùng tham gia giao thông đường bộ", // Lựa chọn 3
                "Cả ba thành phần nêu trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "'Người điều khiển giao thông' gồm những thành phần nào?", // Câu hỏi
                "Người điều khiển phương tiện tham gia giao thông", // Lựa chọn 1
                "Cảnh sát giao thông, người được giao nhiệm vụ hướng dẫn giao thông tại nơi thi công, nơi ùn tắc giao thông, ở bến phà, tại cầu đường bộ đi chung với đường sắt", // Lựa chọn 2
                null, // Lựa chọn 3 (không có)
                null, // Lựa chọn 4 (không có)
                "2", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Người tham gia giao thông phải làm gì để đảm bảo an toàn giao thông đường bộ?", // Câu hỏi
                "Phải nghiêm chỉnh chấp hành quy tắc giao thông", // Lựa chọn 1
                "Phải giữ gìn an toàn cho mình và cho người khác", // Lựa chọn 2
                "Cả hai ý trên", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "3", // Đáp án đúng
                1 //nhóm 1
        );

        addQuestion(db,
                "Bảo đảm an toàn giao thông đường bộ là trách nhiệm của ai?", // Câu hỏi
                "Là trách nhiệm của ngành Giao thông vận tải", // Lựa chọn 1
                "Là trách nhiệm của các cơ quan, tổ chức, cá nhân và của toàn xã hội", // Lựa chọn 2
                "Là trách nhiệm của Cảnh sát giao thông", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "2", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Mọi hành vi vi phạm Luật giao thông đường bộ được xử lý như thế nào?", // Câu hỏi
                "Phải được xử lý nghiêm minh", // Lựa chọn 1
                "Phải được xử lý kịp thời", // Lựa chọn 2
                "Phải được xử lý đúng pháp luật", // Lựa chọn 3
                "Cả ba ý trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Người lái xe đang điều khiển xe trên đường mà trong máu có nồng độ cồn vượt quá bao nhiêu thì bị nghiêm cấm?", // Câu hỏi
                "Nồng độ cồn vượt quá 60 miligam/100 mililít máu", // Lựa chọn 1
                "Nồng độ cồn vượt quá 80 miligam/100 mililít máu", // Lựa chọn 2
                "Nồng độ cồn vượt quá 100 miligam/100 mililít máu", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "2", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Người lái xe đang điều khiển xe trên đường mà trong khí thở có nồng độ cồn vượt quá bao nhiêu thì bị nghiêm cấm?", // Câu hỏi
                "Nồng độ cồn vượt quá 40 miligam/1 lít khí thở", // Lựa chọn 1
                "Nồng độ cồn vượt quá 60 miligam/1 lít khí thở", // Lựa chọn 2
                "Nồng độ cồn vượt quá 80 miligam/1 lít khí thở", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "1", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Người tham gia giao thông phải đi như thế nào là đúng quy tắc giao thông?", // Câu hỏi
                "Đi bên phải theo chiều đi của mình", // Lựa chọn 1
                "Đi đúng phần đường quy định", // Lựa chọn 2
                "Chấp hành hệ thống báo hiệu đường bộ", // Lựa chọn 3
                "Tất cả các ý trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Biển báo hiệu đường bộ gồm những nhóm nào, ý nghĩa của từng nhóm?", // Câu hỏi
                "Nhóm biển báo cấm để biểu thị các điều cấm, nhóm biển báo nguy hiểm để cảnh báo các tình huống nguy hiểm có thể xảy ra", // Lựa chọn 1
                "Nhóm hiệu lệnh để báo các hiệu lệnh phải thi hành, Nhóm biển chỉ dẫn để chỉ dẫn hướng đi hoặc các điều cần biết", // Lựa chọn 2
                "Nhóm biển phụ để thuyết minh bổ sung các loại biển báo cấm, biển báo nguy hiểm, biển hiệu lệnh và biển chỉ dẫn", // Lựa chọn 3
                "Tất cả các nhóm nêu trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Người lái xe phải làm gì khi điều kiển xe vào đường cao tốc?", // Câu hỏi
                "Phải có tín hiệu xin vào và phải nhường đường cho xe đang chạy trên đường", // Lựa chọn 1
                "Khi thấy an toàn mới cho xe nhập vào dòng xe ở làn đường sát mép ngoài", // Lựa chọn 2
                "Nếu có làn đường tăng tốc thì phải cho xe chạy trên làn đường đó trước khi vào các làn đường của đường cao tốc", // Lựa chọn 3
                "Tất cả các ý nêu trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Người lái xe phải làm gì khi điều kiển xe ra khỏi đường cao tốc?", // Câu hỏi
                "Phải thực hiện chuyển dần sang các làn đường phía bên phải, nếu có làn đường giảm tốc thì phải cho xe chạy trên làn đường đó trước khi rời khỏi đường cao tốc", // Lựa chọn 1
                "Phải thực hiện chuyển dần sang các làn đường phía bên trái hoặc bên phải, nếu có làn đường giảm tốc thì phải cho xe chạy trên làn đường đó trước khi rời khỏi đường cao tốc", // Lựa chọn 2
                null, // Lựa chọn 3 (không có)
                null, // Lựa chọn 4 (không có)
                "1", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Khi điều khiển xe trên đường cao tốc, những việc làm nào không cho phép?", // Câu hỏi
                "Không được cho xe chạy ở phần lề đường, không được quay đầu xe, lùi xe, không được cho xe chạy quá tốc độ tối đa và dưới tốc độ tối thiểu ghi trên biển báo, sơn kẻ trên mặt đường", // Lựa chọn 1
                "Không được quay đầu xe, lùi xe, không được cho xe chạy quá tốc độ tối đa và dưới tốc độ tối thiểu ghi trên biển báo, sơn kẻ trên mặt đường", // Lựa chọn 2
                null, // Lựa chọn 3 (không có)
                null, // Lựa chọn 4 (không có)
                "1", // Đáp án đúng
                1 //nhóm 1
        );

        addQuestion(db,
                "Người điều khiển phương tiện tham gia giao thông trong hầm đường bộ phải tuân thủ những điểm gì là đúng quy tắc giao thông?", // Câu hỏi
                "Xe cơ giới phải bật đèn ngay cả khi đường hầm sáng, xe thô sơ phải có đèn hoặc có vật phát sáng báo hiệu", // Lựa chọn 1
                "Chỉ được dừng xe, đỗ xe ở những nơi quy định", // Lựa chọn 2
                "Không được quay đầu xe, lùi xe", // Lựa chọn 3
                "Tất cả các ý trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trường hợp xe kéo xe và kéo rơ moóc, những hành vi nào bị cấm?", // Câu hỏi
                "Xe kéo r moóc, xe sơ mi rơ moóc kéo theo rơ moóc hoặc xe khác", // Lựa chọn 1
                "Chở người trên xe được kéo", // Lựa chọn 2
                "Xe ôtô kéo theo xe thô sơ, xe mô tô hai bánh, xe mô tô ba bánh, xe gắn máy hoặc kéo lê vật trên đường", // Lựa chọn 3
                "Tất cả các ý nêu trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Người tham gia giao thông khi phát hiện công trình đường bộ bị hư hỏng hoặc bị xâm hại, phải có những nghĩa vụ gì?", // Câu hỏi
                "Kịp thời báo cáo cho chính quyền địa phương", // Lựa chọn 1
                "Kịp thời báo cáo cho cơ quan quản lý đường bộ hoặc cơ quan công an nơi gần nhất, để xử lý", // Lựa chọn 2
                "Trong trường hợp cần thiết có biện pháp báo hiệu ngay cho người tham gia giao thông biết", // Lựa chọn 3
                "Tất cả các nghĩa vụ trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Các xe tham gia giao thông đường bộ phải bảo đảm các tiêu chuẩn chất lượng, an toàn kỹ thuật bảo vệ môi trường nào trong các điều ghi dưới đây?", // Câu hỏi
                "Kính chắn gió, kính cửa phải là loại kính an toàn, bảo đảm tầm nhìn cho người điều khiển", // Lựa chọn 1
                "Có đủ hệ thống hãm và hệ thống chuyển hướng có hiệu lực, tay lái của xe ôtô ở bên trái của xe có còi với âm lượng đúng tiêu chuẩn", // Lựa chọn 2
                "Có đủ đèn chiếu sáng gần và xa, đèn soi biển số, đèn báo hãm, đèn tín hiệu, có đủ bộ phận giảm thanh, giảm khói, các kết cấu phải đủ độ bền và đảm bảo tính năng vận hành ổn định", // Lựa chọn 3
                "Tất cả những điều ghi trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Chủ xe và người lái xe bánh xích, xe quá khổ, quá tải có trách nhiệm gì khi cho xe tham gia giao thông đường bộ?", // Câu hỏi
                "Xin phép lưu hành đặc biệt", // Lựa chọn 1
                "Chịu sự kiểm soát về tải trọng và khổ giới hạn của cơ quan quản lý đường bộ", // Lựa chọn 2
                "Chịu phí tổn gia cố, bảo vệ công trình giao thông, hạ tải, xếp lại hàng hoá, tự bảo quản hàng đã bị dỡ xuống và nộp phạt theo quy định", // Lựa chọn 3
                "Tất cả các trách nhiệm trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Những người có mặt tại nơi xảy ra tai nạn giao thông có trách nhiệm gì?", // Câu hỏi
                "Bảo vệ hiện trường, giúp đỡ, cứu chữa kịp thời, bảo vệ tài sản của người bị nạn", // Lựa chọn 1
                "Báo tin ngay cho cơ quan công an hoặc Uỷ ban nhân dân nơi gần nhất", // Lựa chọn 2
                "Cung cấp thông tin sát thực về vụ tai nạn theo yêu cầu của cơ quan công an", // Lựa chọn 3
                "Tất cả ba trách nhiệm nêu trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Khi ở một khu vực đồng thời có đặt biển báo cố định và biển báo tạm thời mà ý nghĩa hiệu lực khác nhau, thì người lái xe phải chấp hành theo hiệu lệnh nào?", // Câu hỏi
                "Biển báo cố định", // Lựa chọn 1
                "Biển báo tạm thời", // Lựa chọn 2
                null, // Lựa chọn 3 (không có)
                null, // Lựa chọn 4 (không có)
                "2", // Đáp án đúng
                1 //nhóm 1
        );

        addQuestion(db,
                "Cơ quan nào quy định tải trọng, khổ giới hạn của đường bộ?", // Câu hỏi
                "Bộ Giao thông vận tải", // Lựa chọn 1
                "Cục Đường bộ Việt Nam", // Lựa chọn 2
                "Sở Giao thông vận tải, Giao thông công chính các tỉnh thành phố", // Lựa chọn 3
                "Cảnh sát giao thông", // Lựa chọn 4
                "1", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trong trường hợp đặc biệt, xe quá tải trọng, quá khổ giới hạn của đường bộ khi lưu hành phải xin giấy phép của cơ quan nào?", // Câu hỏi
                "Cơ quan Cảnh sát giao thông có thẩm quyền", // Lựa chọn 1
                "Cơ quan Quản lý giao thông có thẩm quyền", // Lựa chọn 2
                "Uỷ ban nhân dân tỉnh", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "2", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Hai xe đi ngược chiều nhường đường khi tránh nhau như thế nào là đúng quy tắc giao thông?", // Câu hỏi
                "Nơi đường hẹp chỉ đủ cho một xe chạy và có chỗ tránh xe thì xe nào ở gần chỗ tránh hơn phải vào vị trí tránh, nhường đường cho xe kia đi", // Lựa chọn 1
                "Xe xuống dốc phải nhường đường cho xe lên dốc", // Lựa chọn 2
                "Xe nào có chướng ngại vật phía trước phải nhường đường cho xe kia đi", // Lựa chọn 3
                "Tất cả các ý nêu trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Xe ôtô tham gia giao thông trên đường phố có bắt buộc phải có bộ phận giảm thanh, giảm khói không?", // Câu hỏi
                "Không bắt buộc", // Lựa chọn 1
                "Bắt buộc", // Lựa chọn 2
                null, // Lựa chọn 3 (không có)
                null, // Lựa chọn 4 (không có)
                "2", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Xe ôtô tham gia giao thông trên đường phải có các loại đèn gì?", // Câu hỏi
                "Đèn chiếu sáng gần và xa", // Lựa chọn 1
                "Đèn soi biển số", // Lựa chọn 2
                "Đèn báo hãm và đèn tín hiệu", // Lựa chọn 3
                "Có đủ các loại đèn ghi trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Ban đêm, xe cơ giới đi ngược chiều gặp nhau, đèn chiếu sáng phải sử dụng như thế nào là đúng quy tắc giao thông?", // Câu hỏi
                "Chuyển từ đèn chiếu gần sang đèn chiếu xa", // Lựa chọn 1
                "Phải chuyển từ đèn chiếu xa sang đèn chiếu gần", // Lựa chọn 2
                null, // Lựa chọn 3 (không có)
                null, // Lựa chọn 4 (không có)
                "2", // Đáp án đúng
                1 //nhóm 1
        );

        addQuestion(db,
                "Xe cơ giới 2-3 bánh có được kéo đẩy nhau hoặc vật gì khác trên đường không?", // Câu hỏi
                "Được phép", // Lựa chọn 1
                "Tuỳ trường hợp", // Lựa chọn 2
                "Tuyệt đối không", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "3", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trong đô thị, đoạn đường có dải phân cách cố định, khi không có biển hạn chế tốc độ, với điều kiện đường khô ráo và thời tiết bình thường, loại phương tiện nào chạy với tốc độ tối đa 50km/h?", // Câu hỏi
                "Xe xích lô máy, xe gắn máy", // Lựa chọn 1
                "Các loại xe con, xe taxi đến 9 chỗ ngồi", // Lựa chọn 2
                "Xe tải có tải trọng từ 3.500 kg trở lên, xe ôtô chở người trên 30 chỗ ngồi", // Lựa chọn 3
                "Xe môtô 2-3 bánh, xe tải có tải trọng dưới 3.500kg, xe ôtô chở người từ 10 đến 30 chỗ ngồi", // Lựa chọn 4
                "2", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trong đô thị, đoạn đường có dải phân cách cố định, khi không có biển hạn chế tốc độ, với điều kiện đường khô ráo và thời tiết bình thường, loại phương tiện nào chạy với tốc độ tối đa 35km/h?", // Câu hỏi
                "Xe xích lô máy, xe gắn máy", // Lựa chọn 1
                "Các loại xe con, xe taxi đến 9 chỗ ngồi", // Lựa chọn 2
                "Xe tải có tải trọng từ 3.500 kg trở lên, xe ôtô chở người trên 30 chỗ ngồi", // Lựa chọn 3
                "Xe môtô 2-3 bánh, xe tải có tải trọng dưới 3.500kg, xe ôtô chở người từ 10 đến 30 chỗ ngồi", // Lựa chọn 4
                "3", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trong đô thị, đoạn đường có dải phân cách cố định, khi không có biển hạn chế tốc độ, với điều kiện đường khô ráo và thời tiết bình thường, loại phương tiện nào chạy với tốc độ tối đa 30km/h?", // Câu hỏi
                "Xe xích lô máy, xe gắn máy", // Lựa chọn 1
                "Các loại xe con, xe taxi đến 9 chỗ ngồi", // Lựa chọn 2
                "Xe tải có tải trọng từ 3.500 kg trở lên, xe ôtô chở người trên 30 chỗ ngồi", // Lựa chọn 3
                "Xe môtô 2-3 bánh, xe tải có tải trọng dưới 3.500kg, xe ôtô chở người từ 10 đến 30 chỗ ngồi", // Lựa chọn 4
                "1", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trong đô thị, đoạn đường không có biển hạn chế tốc độ, với điều kiện đường khô ráo và thời tiết bình thường, loại phương tiện nào chạy với tốc độ tối đa 20km/h?", // Câu hỏi
                "Xe xích lô máy, xe gắn máy", // Lựa chọn 1
                "Các loại xe con, xe taxi đến 9 chỗ ngồi", // Lựa chọn 2
                "Xe tải có tải trọng từ 3.500 kg trở lên, xe ôtô chở người trên 30 chỗ ngồi", // Lựa chọn 3
                "Xe môtô 2-3 bánh, xe tải có tải trọng dưới 3.500kg, xe ôtô chở người từ 10 đến 30 chỗ ngồi", // Lựa chọn 4
                "2", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trong đô thị, đoạn đường không có dải phân cách cố định, khi không có biển hạn chế tốc độ, với điều kiện đường khô ráo và thời tiết bình thường, loại phương tiện nào chạy với tốc độ tối đa 30km/h?", // Câu hỏi
                "Xe xích lô máy, xe gắn máy", // Lựa chọn 1
                "Các loại xe con, xe taxi đến 9 chỗ ngồi", // Lựa chọn 2
                "Xe tải có tải trọng từ 3.500 kg trở lên, xe ôtô chở người trên 30 chỗ ngồi", // Lựa chọn 3
                "Xe môtô 2-3 bánh, xe tải có tải trọng dưới 3.500kg, xe ôtô chở người từ 10 đến 30 chỗ ngồi", // Lựa chọn 4
                "3", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trong đô thị, đoạn đường không có dải phân cách cố định, khi không có biển hạn chế tốc độ, với điều kiện đường khô ráo và thời tiết bình thường, loại phương tiện nào chạy với tốc độ tối đa 35km/h?", // Câu hỏi
                "Xe xích lô máy, xe gắn máy", // Lựa chọn 1
                "Các loại xe con, xe taxi đến 9 chỗ ngồi", // Lựa chọn 2
                "Xe tải có tải trọng từ 3.500 kg trở lên, xe ôtô chở người trên 30 chỗ ngồi", // Lựa chọn 3
                "Xe môtô 2-3 bánh, xe tải có tải trọng dưới 3.500kg, xe ôtô chở người từ 10 đến 30 chỗ ngồi", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );

        addQuestion(db,
                "Trong đô thị, đoạn đường không có dải phân cách cố định, khi không có biển hạn chế tốc độ, với điều kiện đường khô ráo và thời tiết bình thường, loại phương tiện nào chạy với tốc độ tối đa 45km/h?", // Câu hỏi
                "Xe xích lô máy, xe gắn máy", // Lựa chọn 1
                "Các loại xe con, xe taxi đến 9 chỗ ngồi", // Lựa chọn 2
                "Xe tải có tải trọng từ 3.500 kg trở lên, xe ôtô chở người trên 30 chỗ ngồi", // Lựa chọn 3
                "Xe môtô 2-3 bánh, xe tải có tải trọng dưới 3.500kg, xe ôtô chở người từ 10 đến 30 chỗ ngồi", // Lựa chọn 4
                "2", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trong đô thị, đoạn đường không có dải phân cách cố định, khi không có biển hạn chế tốc độ, với điều kiện đường khô ráo và thời tiết bình thường, loại phương tiện nào chạy với tốc độ tối đa 25km/h?", // Câu hỏi
                "Xe xích lô máy, xe gắn máy", // Lựa chọn 1
                "Các loại xe con, xe taxi đến 9 chỗ ngồi", // Lựa chọn 2
                "Xe tải có tải trọng từ 3.500 kg trở lên, xe ôtô chở người trên 30 chỗ ngồi", // Lựa chọn 3
                "Xe môtô 2-3 bánh, xe tải có tải trọng dưới 3.500kg, xe ôtô chở người từ 10 đến 30 chỗ ngồi", // Lựa chọn 4
                "1", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trên đường ngoài đô thị có dải phân cách cố định, trừ đường cao tốc, khi không có biển báo hạn chế tốc độ, với điều kiện đường khô ráo và thời tiết bình thường, loại phương tiện nào chạy với tốc độ tối đa 80km/h?", // Câu hỏi
                "Xe xích lô máy, xe gắn máy", // Lựa chọn 1
                "Các loại xe con, xe taxi đến 9 chỗ ngồi", // Lựa chọn 2
                "Xe tải có tải trọng từ 3.500 kg trở lên, xe ôtô chở người trên 30 chỗ ngồi", // Lựa chọn 3
                "Xe môtô 2-3 bánh, xe tải có tải trọng dưới 3.500kg, xe ôtô chở người từ 10 đến 30 chỗ ngồi", // Lựa chọn 4
                "2", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trên đường ngoài đô thị có dải phân cách cố định, trừ đường cao tốc, khi không có biển hạn chế tốc độ, với điều kiện đường khô ráo và thời tiết bình thường, loại phương tiện nào chạy với tốc độ tối đa 60km/h?", // Câu hỏi
                "Xe xích lô máy, xe gắn máy", // Lựa chọn 1
                "Các loại xe con, xe taxi đến 9 chỗ ngồi", // Lựa chọn 2
                "Xe tải có tải trọng từ 3.500 kg trở lên, xe ôtô chở người trên 30 chỗ ngồi", // Lựa chọn 3
                "Xe môtô 2-3 bánh, xe tải có tải trọng dưới 3.500kg, xe ôtô chở người từ 10 đến 30 chỗ ngồi", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trên đường ngoài đô thị có dải phân cách cố định, trừ đường cao tốc, khi không có biển hạn chế tốc độ, với điều kiện đường khô ráo và thời tiết bình thường, loại phương tiện nào chạy với tốc độ tối đa 50km/h?", // Câu hỏi
                "Xe xích lô máy, xe gắn máy", // Lựa chọn 1
                "Các loại xe con, xe taxi đến 9 chỗ ngồi", // Lựa chọn 2
                "Xe tải có tải trọng từ 3.500 kg trở lên, xe ôtô chở người trên 30 chỗ ngồi", // Lựa chọn 3
                "Xe môtô 2-3 bánh, xe tải có tải trọng dưới 3.500kg, xe ôtô chở người từ 10 đến 30 chỗ ngồi", // Lựa chọn 4
                "3", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trên đường ngoài đô thị không có dải phân cách cố định, trừ đường cao tốc, khi không có biển hạn chế tốc độ, với điều kiện đường khô ráo và thời tiết bình thường, loại phương tiện nào chạy với tốc độ tối đa 50km/h?", // Câu hỏi
                "Xe xích lô máy, xe gắn máy", // Lựa chọn 1
                "Các loại xe con, xe taxi đến 9 chỗ ngồi", // Lựa chọn 2
                "Xe tải có tải trọng từ 3.500 kg trở lên, xe ôtô chở người trên 30 chỗ ngồi", // Lựa chọn 3
                "Xe môtô 2-3 bánh, xe tải có tải trọng dưới 3.500kg, xe ôtô chở người từ 10 đến 30 chỗ ngồi", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );

        addQuestion(db,
                "Trong khu dân cư, ở nơi nào cho phép người lái xe quay đầu xe?", // Câu hỏi
                "ở nơi có đường giao nhau và những chỗ có biển báo cho phép quay đầu xe", // Lựa chọn 1
                "ở nơi có đường rộng để cho các loại xe chạy hai chiều", // Lựa chọn 2
                "ở bất cứ nơi nào", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "1", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Ở những nơi nào cấm quay đầu xe?", // Câu hỏi
                "ở phần đường dành cho người đi bộ qua đường", // Lựa chọn 1
                "Trên cầu, đầu cầu, gầm cầu vượt, ngầm, trong hầm đường bộ, tại nơi đường bộ giao cắt đường sắt", // Lựa chọn 2
                "Đường hẹp, đoạn đường cong tầm nhìn bị che khuất", // Lựa chọn 3
                "Tất cả các trường hợp nêu trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Khi lùi xe người lái phải làm gì để bảo đảm an toàn?", // Câu hỏi
                "Quan sát phía sau và cho lùi xe", // Lựa chọn 1
                "Lợi dụng nơi đường giao nhau đủ chiều rộng để lùi", // Lựa chọn 2
                "Phải quan sát phía sau, có tín hiệu cần thiết và chỉ khi nào thấy không nguy hiểm mới được lùi", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "3", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Ở những nơi nào cấm lùi xe?", // Câu hỏi
                "Ở khu vực cấm dừng và trên phần đường dành cho người đi bộ qua đường", // Lựa chọn 1
                "Nơi đường bộ giao nhau, đường bộ giao cắt đường sắt, nơi tầm nhìn bị che khuất, trong hầm đường bộ", // Lựa chọn 2
                "Tất cả những trường hợp", // Lựa chọn 3
                null, // Lựa chọn 4 (không có)
                "3", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Trên đường có nhiều làn đường cho xe chạy cùng chiều được phân biệt bằng vạch kẻ phân làn đường, người lái xe cho xe chạy như thế nào là đúng quy tắc giao thông?", // Câu hỏi
                "Cho xe chạy trên bất kỳ làn đường nào, khi chuyển làn phải có đèn tín hiệu báo trước, phải bảo đảm an toàn", // Lựa chọn 1
                "Phải cho xe chạy trong một làn đường và chỉ được chuyển làn đường ở những nơi cho phép, khi chuyển làn đường phải có tín hiệu báo trước và phải đảm bảo an toàn", // Lựa chọn 2
                null, // Lựa chọn 3 (không có)
                null, // Lựa chọn 4 (không có)
                "2", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Khi vượt xe khác phải đảm bảo những điều kiện gì?", // Câu hỏi
                "Không có chướng ngại vật ở phía trước, không có xe chạy ngược chiều trong đoạn đường định vượt", // Lựa chọn 1
                "Xe chạy trước không có tín hiệu vượt xe khác và đã tránh về bên phải, xe vượt phải vượt về bên trái (trừ các trường hợp đặc biệt)", // Lựa chọn 2
                "Phải báo hiệu bằng đèn hoặc còi, trong đô thị và khu đông dân từ 22h đến 5h chỉ được báo hiệu xin vượt bằng đèn", // Lựa chọn 3
                "Tất cả những điều kiện trên", // Lựa chọn 4
                "4", // Đáp án đúng
                1 //nhóm 1
        );
        addQuestion(db,
                "Người lái xe vận tải hàng hóa cần thực hiện những nhiệm vụ gì ghi ở dưới đây?",
                "Thực hiện nghiêm chỉnh những nội dung hợp đồng giữa chủ phương tiện với chủ hàng trong việc vận chuyển và bảo quản hàng hóa trong quá trình vận chuyển; không chở hàng cấm, không xếp hàng quá trọng tải của xe, quá trọng tải cho phép của cầu, đường; khi vận chuyển hàng quá khổ, quá tải, hàng nguy hiểm, hàng siêu trường, siêu trọng phải có giấy phép.",
                "Thực hiện nghiêm chỉnh những nội dung hợp đồng giữa chủ hàng với khách hàng trong việc vận chuyển và bảo quản hàng hóa trong quá trình vận chuyển; trong trường hợp cần thiết có thể xếp hàng quá trọng tải của xe, quá trọng tải cho phép của cầu theo yêu cầu của chủ hàng; khi vận chuyển hàng quá khổ, quá tải, hàng nguy hiểm, hàng siêu trường, siêu trọng phải được chủ hàng cho phép.",
                null,
                null,
                "1",
                2
        );

        addQuestion(db,
                "Người lái xe kinh doanh vận tải cần thực hiện những công việc gì ghi ở dưới đây để thường xuyên rèn luyện nâng cao đạo đức nghề nghiệp?",
                "Phải yêu quý xe, quản lý và sử dụng xe tốt; bảo dưỡng xe đúng định kỳ; thực hành tiết kiệm vật tư, nhiên liệu; luôn tu dưỡng bản thân, có lối sống lành mạnh, tác phong làm việc công nghiệp.",
                "Nắm vững các quy định của pháp luật, tự giác chấp hành pháp luật, lái xe an toàn; coi hành khách như người thân, là đối tác tin cậy; có ý thức tổ chức kỷ luật và xây dựng doanh nghiệp vững mạnh; có tinh thần hợp tác, tương trợ, giúp đỡ đồng nghiệp.",
                "Cả ý 1 và ý 2.",
                null,
                "3",
                2
        );

        addQuestion(db,
                "Người lái xe và nhân viên phục vụ trên xe ô tô vận tải hành khách phải có những trách nhiệm gì theo quy định dưới đây?",
                "Kiểm tra các điều kiện bảo đảm an toàn của xe sau khi khởi hành; có trách nhiệm lái xe thật nhanh khi chậm giờ của khách.",
                "Kiểm tra các điều kiện bảo đảm an toàn của xe trước khi khởi hành; có thái độ văn minh, lịch sự, hướng dẫn hành khách ngồi đúng nơi quy định; kiểm tra việc sắp xếp, chằng buộc hành lý, bảo đảm an toàn.",
                "Có biện pháp bảo vệ tính mạng, sức khỏe, tài sản của hành khách đi xe, giữ gìn trật tự, vệ sinh trong xe; đóng cửa lên xuống của xe trước và trong khi xe chạy.",
                "Cả ý 2 và ý 3.",
                "4",
                2
        );

        addQuestion(db,
                "Khái niệm về văn hóa giao thông được hiểu như thế nào là đúng?",
                "Là sự hiểu biết và chấp hành nghiêm chỉnh pháp luật về giao thông; là ý thức trách nhiệm với cộng đồng khi tham gia giao thông.",
                "Là ứng xử có văn hóa, có tình yêu thương con người trong các tình huống không may xảy ra khi tham gia giao thông.",
                "Cả ý 1 và ý 2.",
                null,
                "3",
                2
        );

        addQuestion(db,
                "Trên làn đường dành cho ô tô có vũng nước lớn, có nhiều người đi xe mô tô trên làn đường bên cạnh, người lái xe ô tô xử lý như thế nào là có văn hóa giao thông?",
                "Cho xe chạy thật nhanh qua vũng nước.",
                "Giảm tốc độ cho xe chạy chậm qua vũng nước.",
                "Giảm tốc độ cho xe chạy qua làn đường dành cho mô tô để tránh vũng nước.",
                null,
                "2",
                2
        );

        addQuestion(db,
                "Người lái xe cố tình không phân biệt làn đường, vạch phân làn, phóng nhanh, vượt ẩu, vượt đèn đỏ, đi vào đường cấm, đường một chiều được coi là hành vi nào trong các hành vi dưới đây?",
                "Là bình thường.",
                "Là thiếu văn hóa giao thông.",
                "Là có văn hóa giao thông.",
                null,
                "2",
                2
        );
        addQuestion(db,
                "Khi sơ cứu người bị tai nạn giao thông đường bộ, có vết thương chảy máu ngoài, màu đỏ tươi phun thành tia và phun mạnh khi mạch đập, bạn phải làm gì dưới đây?",
                "Thực hiện cầm máu trực tiếp.",
                "Thực hiện cầm máu không trực tiếp (chặn động mạch).",
                null,
                null,
                "2",
                2
        );

        addQuestion(db,
                "Người lái xe có văn hóa khi tham gia giao thông phải đáp ứng các điều kiện nào dưới đây?",
                "Có trách nhiệm với bản thân và với cộng đồng; tôn trọng, nhường nhịn người khác.",
                "Tận tình giúp đỡ người tham gia giao thông gặp hoạn nạn; giúp đỡ người khuyết tật, trẻ em và người cao tuổi.",
                "Cả ý 1 và ý 2.",
                null,
                "3",
                2
        );

        addQuestion(db,
                "Trong các hành vi dưới đây, người lái xe mô tô có văn hóa giao thông phải ứng xử như thế nào?",
                "Điều khiển xe đi trên phần đường, làn đường có ít phương tiện tham gia giao thông, chỉ đội mũ bảo hiểm ở nơi có biển báo bắt buộc đội mũ bảo hiểm.",
                "Chấp hành quy định về tốc độ, đèn tín hiệu, biển báo hiệu, vạch kẻ đường khi lái xe; chấp hành hiệu lệnh, chỉ dẫn của người điều khiển giao thông; nhường đường cho người đi bộ, người già, trẻ em, người khuyết tật.",
                "Cả ý 1 và ý 2.",
                null,
                "2",
                2
        );

        addQuestion(db,
                "Trong các hành vi dưới đây, người lái xe mô tô có văn hóa giao thông phải ứng xử như thế nào?",
                "Điều khiển xe đi bên phải theo chiều đi của mình; đi đúng phần đường, làn đường quy định; đội mũ bảo hiểm đạt chuẩn, cài quai đúng quy cách.",
                "Điều khiển xe đi trên phần đường, làn đường có ít phương tiện tham gia giao thông.",
                "Điều khiển xe và đội mũ bảo hiểm ở nơi có biển báo bắt buộc đội mũ bảo hiểm.",
                null,
                "1",
                2
        );

        addQuestion(db,
                "Trong các hành vi dưới đây, người lái xe ô tô, mô tô có văn hóa giao thông phải ứng xử như thế nào?",
                "Điều khiển xe đi bên phải theo chiều đi của mình; đi đúng phần đường, làn đường quy định; dừng, đỗ xe đúng nơi quy định; đã uống rượu, bia thì không lái xe.",
                "Điều khiển xe đi trên phần đường, làn đường có ít phương tiện giao thông; dừng xe, đỗ xe ở nơi thuận tiện hoặc theo yêu cầu của hành khách, của người thân.",
                "Dừng và đỗ xe ở nơi thuận tiện cho việc chuyên chở hành khách và giao nhận hàng hóa; sử dụng ít rượu, bia thì có thể lái xe.",
                null,
                "1",
                2
        );

        addQuestion(db,
                "Người có văn hóa giao thông khi điều khiển xe cơ giới tham gia giao thông đường bộ phải đảm bảo các điều kiện gì dưới đây?",
                "Có giấy phép lái xe phù hợp với loại xe được phép điều khiển; xe cơ giới đảm bảo các quy định về chất lượng, an toàn kỹ thuật và bảo vệ môi trường.",
                "Có giấy chứng nhận bảo hiểm trách nhiệm dân sự của chủ xe cơ giới còn hiệu lực; nộp phí sử dụng đường bộ theo quy định.",
                "Cả ý 1 và ý 2.",
                null,
                "3",
                2
        );

        addQuestion(db,
                "Khi xảy ra tai nạn giao thông, người lái xe và người có mặt tại hiện trường vụ tai nạn phải thực hiện các công việc gì dưới đây?",
                "Đặt các biển cảnh báo hoặc vật báo hiệu ở phía trước và phía sau hiện trường xảy ra tai nạn để cảnh báo; kiểm tra khả năng xảy ra hỏa hoạn do nhiên liệu bị rò rỉ; bảo vệ hiện trường vụ tai nạn và cấp cứu người bị thương.",
                "Đặt các biển cảnh báo hoặc vật báo hiệu ở phía trên nóc xe xảy ra tai nạn để cảnh báo; kiểm tra khả năng xảy ra mất an toàn do nước làm mát bị rò rỉ.",
                "Cả ý 1 và ý 2.",
                null,
                "1",
                2
        );

        addQuestion(db,
                "Khi xảy ra tai nạn giao thông, có người bị thương nghiêm trọng, người lái xe và người có mặt tại hiện trường vụ tai nạn phải thực hiện các công việc gì dưới đây?",
                "Thực hiện sơ cứu ban đầu trong trường hợp khẩn cấp; thông báo vụ tai nạn đến cơ quan thi hành pháp luật.",
                "Nhanh chóng lái xe gây tai nạn hoặc đi nhờ xe khác ra khỏi hiện trường vụ tai nạn.",
                "Cả ý 1 và ý 2.",
                null,
                "1",
                2
        );

        addQuestion(db,
                "Khi sơ cứu ban đầu cho người bị tai nạn giao thông đường bộ không còn hô hấp, người lái xe và người có mặt tại hiện trường vụ tai nạn phải thực hiện các công việc gì dưới đây?",
                "Đặt nạn nhân nằm ngửa, khai thông đường thở của nạn nhân.",
                "Thực hiện các biện pháp hô hấp nhân tạo.",
                "Cả ý 1 và ý 2.",
                null,
                "3",
                2
        );

        addQuestion(db,
                "Hành vi bỏ trốn sau khi gây tai nạn để trốn tránh trách nhiệm hoặc khi có điều kiện mà cố ý không cứu giúp người bị tai nạn giao thông có bị nghiêm cấm hay không?",
                "Không bị nghiêm cấm.",
                "Nghiêm cấm tuỳ từng trường hợp cụ thể.",
                "Bị nghiêm cấm.",
                null,
                "3",
                2
        );
        addQuestion(db,
                "Khi xảy ra tai nạn giao thông, những hành vi nào dưới đây bị nghiêm cấm?",
                "Xâm phạm tính mạng, sức khoẻ, tài sản của người bị nạn và người gây tai nạn.",
                "Bỏ trốn sau khi gây ra tai nạn để trốn tránh trách nhiệm.",
                "Cả ý 1 và ý 2.",
                null,
                "3",
                2
        );

        addQuestion(db,
                "Khi xảy ra tai nạn giao thông, những hành vi nào dưới đây bị nghiêm cấm?",
                "Xâm phạm tính mạng, sức khoẻ, tài sản của người bị nạn và người gây tai nạn.",
                "Sơ cứu người bị nạn khi cơ quan có thẩm quyền chưa cho phép.",
                "Sơ cứu người gây tai nạn khi cơ quan có thẩm quyền chưa cho phép.",
                null,
                "1",
                2
        );

        addQuestion(db,
                "Trong đoạn đường hai chiều tại khu đông dân cư đang ùn tắc, người điều khiển xe mô tô hai bánh có văn hóa giao thông sẽ lựa chọn xử lý tình huống nào dưới đây?",
                "Cho xe lấn sang làn ngược chiều để nhanh chóng thoát khỏi nơi ùn tắc.",
                "Điều khiển xe lên vỉa hè để nhanh chóng thoát khỏi nơi ùn tắc.",
                "Kiên nhẫn tuân thủ hướng dẫn của người điều khiển giao thông hoặc tín hiệu giao thông, di chuyển trên đúng phần đường bên phải theo chiều đi, nhường đường cho các phương tiện đi ngược chiều để nút tắc nhanh chóng được giải tỏa.",
                null,
                "3",
                2
        );

        addQuestion(db,
                "Trên đường đang xảy ra ùn tắc những hành vi nào sau đây là thiếu văn hóa khi tham gia giao thông?",
                "Bấm còi liên tục thúc giục các phương tiện phía trước nhường đường.",
                "Đi lên vỉa hè, tận dụng mọi khoảng trống để nhanh chóng thoát khỏi nơi ùn tắc.",
                "Lấn sang trái đường cố gắng vượt lên xe khác.",
                "Tất cả các ý nêu trên.",
                "4",
                2
        );
        addQuestion(db,
                "Biển nào cấm người đi bộ?", // Câu hỏi
                "Biển 1.",                   // Lựa chọn 1
                "Biển 1 và 3.",              // Lựa chọn 2
                "Biển 2.",                   // Lựa chọn 3
                "Biển 2 và 3",               // Lựa chọn 4
                "3",                         // Đáp án đúng
                3,                           // Nhóm câu hỏi
                "bien_bao_cam_nguoi_di_bo"   // Tên hình ảnh trong drawable
        );
        addQuestion(db,
                "Gặp biển nào người lái xe phải nhường đường cho người đi bộ?", // Câu hỏi
                "Biển 1.",                   // Lựa chọn 1
                "Biển 3.",                   // Lựa chọn 2
                "Biển 2.",                   // Lựa chọn 3
                "Biển 1 và 3.",              // Lựa chọn 4
                "1",                         // Đáp án đúng
                3,                           // Nhóm câu hỏi (groupId 3)
                "nhuong_nguoi_di_bo"         // Tên hình ảnh (không cần đuôi file)
        );
        addQuestion(db,
                "Biển nào chỉ đường dành cho người đi bộ, các loại xe không được đi vào khi gặp biển này?", // Câu hỏi
                "Biển 1.",                   // Lựa chọn 1
                "Biển 1 và 3.",              // Lựa chọn 2
                "Biển 3.",                   // Lựa chọn 3
                "Cả ba biển",                // Lựa chọn 4
                "3",                         // Đáp án đúng
                3,                           // Nhóm câu hỏi (groupId 3)
                "gianh_cho_nguoi_di_bo"      // Tên hình ảnh (không cần đuôi file)
        );
        addQuestion(db,
                "Biển nào báo hiệu sắp đến chỗ giao nhau nguy hiểm?", // Câu hỏi
                "Biển 1 và 2.",              // Lựa chọn 1
                "Biển 2 và 3.",              // Lựa chọn 2
                "Biển 1.",                   // Lựa chọn 3
                "Cả ba biển.",               // Lựa chọn 4
                "2",                         // Đáp án đúng
                3,                           // Nhóm câu hỏi (groupId 3)
                "giao_nhau_nguy_hiem"        // Tên hình ảnh (không cần đuôi file)
        );
        addQuestion(db,
                "Biển nào báo hiệu giao nhau có tín hiệu đèn?", // Câu hỏi
                "Biển 2.",                   // Lựa chọn 1
                "Biển 1.",                   // Lựa chọn 2
                "Biển 3.",                   // Lựa chọn 3
                "Cả ba biển.",               // Lựa chọn 4
                "3",                         // Đáp án đúng
                3,                           // Nhóm câu hỏi (groupId 3)
                "giao_nhau_co_tin_hieu_den"  // Tên hình ảnh (không cần đuôi file)
        );
        addQuestion(db,
                "Biển nào báo hiệu nguy hiểm giao nhau với đường sắt?", // Câu hỏi
                "Biển 1 và 3.",                                         // Lựa chọn 1
                "Biển 1 và 2.",                                         // Lựa chọn 2
                "Biển 2 và 3.",                                         // Lựa chọn 3
                "Cả ba biển.",                                          // Lựa chọn 4
                "1",                                                    // Đáp án đúng
                3,                                                      // Nhóm câu hỏi (Group ID)
                "giao_nhau_voi_duong_sat"                               // Tên hình ảnh trong drawable (không cần đuôi .jpg)
        );
        addQuestion(db,
                "Biển nào báo hiệu đường sắt giao nhau với đường bộ không có rào chắn?", // Câu hỏi
                "Biển 1 và 2.",                                                          // Lựa chọn 1
                "Biển 2 và 3.",                                                          // Lựa chọn 2
                "Biển 1 và 3.",                                                          // Lựa chọn 3
                "Cả ba biển.",                                                           // Lựa chọn 4
                "2",                                                                     // Đáp án đúng
                3,                                                                       // Nhóm câu hỏi (Group ID)
                "duong_sat_giao_duong_bo_khong_rao_chan"                                 // Tên hình ảnh trong drawable
        );
        addQuestion(db,
                "Biển nào báo hiệu “Hết đoạn đường ưu tiên”?", // Câu hỏi
                "Biển 3.",                                    // Lựa chọn 1
                "Biển 1.",                                    // Lựa chọn 2
                "Biển 2.",                                    // Lựa chọn 3
                null,                                         // Lựa chọn 4 (Không có lựa chọn thứ 4)
                "1",                                          // Đáp án đúng
                3,                                            // Nhóm câu hỏi (Group ID)
                "het_doan_duong_uu_tien"                      // Tên hình ảnh trong drawable
        );
        addQuestion(db,
                "Biển nào báo hiệu, chỉ dẫn xe đi trên đường này được quyền ưu tiên qua nơi giao nhau?", // Câu hỏi
                "Biển 1 và 2.",                            // Lựa chọn 1
                "Biển 1 và 3.",                            // Lựa chọn 2
                "Cả ba biển.",                             // Lựa chọn 3
                "Biển 2 và 3.",                            // Lựa chọn 4
                "1",                                       // Đáp án đúng
                3,                                         // Nhóm câu hỏi (Group ID)
                "xe_tren_duong_uu_tien_qua_noi_giao_nhau"  // Tên hình ảnh trong drawable
        );
        addQuestion(db,
                "Biển nào báo hiệu giao nhau với đường không ưu tiên?", // Câu hỏi
                "Biển 1.",                            // Lựa chọn 1
                "Biển 2 và 3.",                        // Lựa chọn 2
                "Biển 2.",                             // Lựa chọn 3
                "Biển 3.",                             // Lựa chọn 4
                "1",                                   // Đáp án đúng
                3,                                     // Nhóm câu hỏi (Group ID)
                "giao_voi_duong_khong_uu_tien"         // Tên hình ảnh trong drawable
        );
        addQuestion(db,
                "Biển nào báo hiệu giao nhau với đường ưu tiên?", // Câu hỏi
                "Biển 1 và 3.",                     // Lựa chọn 1
                "Biển 2.",                          // Lựa chọn 2
                "Biển 3.",                          // Lựa chọn 3
                null,                               // Lựa chọn 4 (không có đáp án thứ 4)
                "1",                                // Đáp án đúng
                3,                                  // Nhóm câu hỏi (Group ID)
                "giao_voi_duong_uu_tien"            // Tên hình ảnh trong drawable
        );
        addQuestion(db,
                "Biển nào báo hiệu 'Đường giao nhau' của các tuyến đường cùng cấp?", // Câu hỏi
                "Biển 1.",                          // Lựa chọn 1
                "Biển 3.",                          // Lựa chọn 2
                "Biển 2.",                          // Lựa chọn 3
                null,                               // Lựa chọn 4 (không có đáp án thứ 4)
                "2",                                // Đáp án đúng
                3,                                  // Nhóm câu hỏi (Group ID)
                "giao_nhau_cac_tuyen_cung_cap"      // Tên hình ảnh trong drawable
        );
        addQuestion(db,
                "Biển nào chỉ dẫn được ưu tiên qua đường hẹp?", // Câu hỏi
                "Biển 1.",                          // Lựa chọn 1
                "Biển 2.",                          // Lựa chọn 2
                "Biển 3.",                          // Lựa chọn 3
                "Biển 2 và 3.",                     // Lựa chọn 4
                "3",                                // Đáp án đúng
                3,                                  // Nhóm câu hỏi (Group ID)
                "uu_tien_qua_duong_hep"             // Tên hình ảnh trong drawable
        );
        addQuestion(db,
                "Biển nào báo hiệu cấm xe mô tô hai bánh đi vào?", // Câu hỏi
                "Biển 1.",                          // Lựa chọn 1
                "Biển 2.",                          // Lựa chọn 2
                "Biển 3.",                          // Lựa chọn 3
                null,                               // Lựa chọn 4 (không có)
                "1",                                // Đáp án đúng
                3,                                  // Nhóm câu hỏi (Group ID)
                "cam_moto_2_banh"                   // Tên hình ảnh trong drawable
        );
        addQuestion(db,
                "Khi gặp biển nào thì xe mô tô hai bánh được đi vào?", // Câu hỏi
                "Không biển nào.",                 // Lựa chọn 1
                "Biển 1 và 2.",                    // Lựa chọn 2
                "Biển 2 và 3.",                    // Lựa chọn 3
                "Cả ba biển.",                     // Lựa chọn 4
                "3",                               // Đáp án đúng
                3,                                 // Nhóm câu hỏi (Group ID)
                "moto_2_banh_duoc_di_vao"          // Tên hình ảnh trong drawable
        );
        addQuestion(db,
                "Biển nào cấm quay xe?",       // Câu hỏi
                "Biển 1.",                     // Lựa chọn 1
                "Biển 2.",                     // Lựa chọn 2
                "Không biển nào",              // Lựa chọn 3
                null,                          // Lựa chọn 4 (không có)
                "2",                           // Đáp án đúng
                3,                             // Nhóm câu hỏi (Group ID)
                "cam_quay_xe"                  // Tên hình ảnh trong drawable
        );
        addQuestion(db,
                "Biển nào cấm xe rẽ trái?",    // Câu hỏi
                "Biển 1.",                     // Lựa chọn 1
                "Biển 2.",                     // Lựa chọn 2
                "Cả hai biển.",                // Lựa chọn 3
                null,                          // Lựa chọn 4 (không có)
                "1",                           // Đáp án đúng
                3,                             // Nhóm câu hỏi (Group ID)
                "cam_re_trai"                  // Tên hình ảnh trong drawable
        );
// Câu 108
        addQuestion(db,
                "Khi gặp biển nào xe ưu tiên theo luật định vẫn phải dừng lại?", // Câu hỏi
                "Biển 1.",                                                      // Lựa chọn 1
                "Biển 2.",                                                      // Lựa chọn 2
                "Cả ba biển.",                                                  // Lựa chọn 3
                null,                                                           // Lựa chọn 4
                "2",                                                            // Đáp án đúng
                3,                                                              // Nhóm câu hỏi (Group ID)
                "bb108"                                                         // Tên hình ảnh trong drawable
        );

// Câu 109
        addQuestion(db,
                "Khi đến chỗ giao nhau, gặp biển nào thì người lái xe không được cho xe đi thẳng, phải rẽ sang hướng khác?", // Câu hỏi
                "Biển 1 và 3.",                                                                                              // Lựa chọn 1
                "Biển 2 và 3.",                                                                                              // Lựa chọn 2
                "Biển 1 và 2.",                                                                                              // Lựa chọn 3
                "Cả ba biển.",                                                                                               // Lựa chọn 4
                "3",                                                                                                         // Đáp án đúng
                3,                                                                                                           // Nhóm câu hỏi (Group ID)
                "bb109"                                                                                                      // Tên hình ảnh trong drawable
        );

// Câu 110
        addQuestion(db,
                "Khi gặp biển này, xe lam và mô tô ba bánh có được phép rẽ trái hay rẽ phải hay không?", // Câu hỏi
                "Được phép.",                                                                           // Lựa chọn 1
                "Không được phép.",                                                                     // Lựa chọn 2
                null,                                                                                   // Lựa chọn 3
                null,                                                                                   // Lựa chọn 4
                "2",                                                                                    // Đáp án đúng
                3,                                                                                      // Nhóm câu hỏi (Group ID)
                "bb110"                                                                                 // Tên hình ảnh trong drawable
        );

// Câu 111
        addQuestion(db,
                "Biển này có hiệu lực đối với xe mô tô hai, ba bánh không?", // Câu hỏi
                "Có.",                                                      // Lựa chọn 1
                "Không.",                                                   // Lựa chọn 2
                null,                                                       // Lựa chọn 3
                null,                                                       // Lựa chọn 4
                "1",                                                        // Đáp án đúng
                3,                                                          // Nhóm câu hỏi (Group ID)
                "bb111"                                                     // Tên hình ảnh trong drawable
        );

// Câu 112
        addQuestion(db,
                "Biển nào báo hiệu cầu vượt liên thông?", // Câu hỏi
                "Biển 2 và 3.",                          // Lựa chọn 1
                "Biển 1 và 2.",                          // Lựa chọn 2
                "Biển 1 và 3.",                          // Lựa chọn 3
                "Cả ba biển.",                           // Lựa chọn 4
                "3",                                     // Đáp án đúng
                3,                                       // Nhóm câu hỏi (Group ID)
                "bb112"                                  // Tên hình ảnh trong drawable
        );

// Câu 113
        addQuestion(db,
                "Biển nào báo hiệu đoạn đường hay xảy ra tai nạn?", // Câu hỏi
                "Biển 1.",                                         // Lựa chọn 1
                "Biển 2.",                                         // Lựa chọn 2
                "Biển 2 và 3.",                                    // Lựa chọn 3
                null,                                              // Lựa chọn 4
                "2",                                               // Đáp án đúng
                3,                                                 // Nhóm câu hỏi (Group ID)
                "bb113"                                            // Tên hình ảnh trong drawable
        );

// Câu 114
        addQuestion(db,
                "Biển nào báo hiệu tuyến đường cầu vượt cắt qua?", // Câu hỏi
                "Biển 1 và 2.",                                   // Lựa chọn 1
                "Biển 1 và 3.",                                   // Lựa chọn 2
                "Biển 2 và 3.",                                   // Lựa chọn 3
                null,                                             // Lựa chọn 4
                "1",                                              // Đáp án đúng
                3,                                                // Nhóm câu hỏi (Group ID)
                "bb114"                                           // Tên hình ảnh trong drawable
        );

// Câu 115
        addQuestion(db,
                "Biển nào chỉ dẫn tên đường trên các tuyến đường đối ngoại?", // Câu hỏi
                "Biển 1.",                                                   // Lựa chọn 1
                "Biển 2.",                                                   // Lựa chọn 2
                "Biển 3.",                                                   // Lựa chọn 3
                "Biển 1 và 2.",                                              // Lựa chọn 4
                "3",                                                         // Đáp án đúng
                3,                                                           // Nhóm câu hỏi (Group ID)
                "bb115"                                                      // Tên hình ảnh trong drawable
        );





        addQuestion(db,
                "Thứ tự các xe đi như thế nào là đúng quy tắc giao thông?", // Câu hỏi
                "Xe tải, xe lam, xe con, mô tô.",                          // Lựa chọn 1
                "Xe tải, mô tô, xe lam, xe con.",                          // Lựa chọn 2
                "Xe lam, xe tải, xe con, mô tô.",                          // Lựa chọn 3
                "Mô tô, xe lam, xe tải, xe con.",                          // Lựa chọn 4
                "2",                                                       // Đáp án đúng
                4,                                                         // Nhóm câu hỏi
                "sa_hinh_116",                                             // Tên hình ảnh trong drawable
                "Theo thứ tự:\n\n"
                        + "1. Không có xe vào giao lộ.\n"
                        + "2. Không có xe ưu tiên.\n"
                        + "3. Theo đường ưu tiên: Xe tải và xe mô tô nằm trên đường ưu tiên nên được đi trước. "
                        + "Trong đó, xe tải đi thẳng nên được đi trước, còn lại là xe mô tô. Tương tự: xe lam và xe con cùng nằm trên đường không ưu tiên, "
                        + "tuy nhiên xe lam đi thẳng nên được đi trước, xe con rẽ trái đi sau cùng." // Phần giải thích
        );
        addQuestion(db,
                "Thứ tự các xe đi như thế nào là đúng quy tắc giao thông ?", // Câu hỏi
                "Xe tải, xe lam, mô tô.",                                    // Lựa chọn 1
                "Xe lam, xe tải, mô tô.",                                    // Lựa chọn 2
                "Mô tô, xe lam, xe tải.",                                    // Lựa chọn 3
                "Xe lam, mô tô, xe tải.",                                    // Lựa chọn 4
                "3",                                                         // Đáp án đúng
                4,                                                           // Nhóm câu hỏi
                "sa_hinh_117",                                               // Tên hình ảnh trong drawable
                "Theo thứ tự:\n\n"
                        + "1. Không có xe đã vào giao lộ.\n"
                        + "2. Không có xe ưu tiên.\n"
                        + "3. Không có biển báo hiệu đường ưu tiên.\n"
                        + "4. Các xe cùng cấp: Xe bên phải trống được đi trước. "
                        + "Vậy xe mô tô đi trước, sau khi xe mô tô đi rồi, bên phải xe lam trống nên xe lam đi tiếp theo, "
                        + "cuối cùng là xe tải."                              // Phần giải thích
        );
        addQuestion(db,
                "Trường hợp này xe nào được quyền đi trước?",              // Câu hỏi
                "Mô tô.",                                                 // Lựa chọn 1
                "Xe con.",                                                // Lựa chọn 2
                null,                                                     // Lựa chọn 3
                null,                                                     // Lựa chọn 4
                "2",                                                      // Đáp án đúng
                4,                                                        // Nhóm câu hỏi
                "sa_hinh_118",                                            // Tên hình ảnh trong drawable
                "Giải thích: Xe mô tô gặp biển STOP nên dừng lại nhường đường." // Phần giải thích
        );
        addQuestion(db,
                "Thứ tự các xe đi như thế nào là đúng quy tắc giao thông?", // Câu hỏi
                "Xe lam, xe cứu thương, xe con.",                          // Lựa chọn 1
                "Xe cứu thương, xe con, xe lam.",                          // Lựa chọn 2
                "Xe con, xe lam, xe cứu thương.",                          // Lựa chọn 3
                null,                                                     // Lựa chọn 4
                "1",                                                      // Đáp án đúng
                4,                                                        // Nhóm câu hỏi
                "sa_hinh_119",                                            // Tên hình ảnh trong drawable
                "Giải thích: Theo thứ tự:\n\n"
                        + "1. Xe lam đã vào giao lộ nên được đi trước.\n"
                        + "2. Xe cứu thương là xe ưu tiên nên đi tiếp theo.\n"
                        + "3. Cuối cùng là xe con."                       // Phần giải thích
        );
        addQuestion(
                db,
                "Xe nào được quyền đi trước trong trường hợp này?", // Câu hỏi
                "Mô tô.",                                           // Lựa chọn 1
                "Xe cứu thương.",                                   // Lựa chọn 2
                null,                                               // Lựa chọn 3
                null,                                               // Lựa chọn 4
                "2",                                                // Đáp án đúng
                4,                                                  // Nhóm câu hỏi
                "sa_hinh_120",                                      // Tên hình ảnh trong drawable
                null                                                // Không có giải thích
        );
        addQuestion(
                db,
                "Theo tín hiệu đèn, xe nào được phép đi?",          // Câu hỏi
                "Xe con và xe khách.",                               // Lựa chọn 1
                "Mô tô.",                                            // Lựa chọn 2
                null,                                                // Lựa chọn 3
                null,                                                // Lựa chọn 4
                "1",                                                 // Đáp án đúng
                4,                                                   // Nhóm câu hỏi
                "sa_hinh_121",                                       // Tên hình ảnh trong drawable
                "Xe con và xe khách đèn xanh nên được đi."          // Giải thích
        );
        addQuestion(
                db,
                "Theo tín hiệu đèn, xe nào được quyền đi là đúng quy tắc giao thông?",  // Câu hỏi
                "Xe khách, mô tô.",                                                     // Lựa chọn 1
                "Xe tải, mô tô.",                                                       // Lựa chọn 2
                "Xe con, xe tải.",                                                      // Lựa chọn 3
                null,                                                                   // Lựa chọn 4
                "3",                                                                    // Đáp án đúng
                4,                                                                      // Nhóm câu hỏi
                "sa_hinh_122",                                                          // Tên hình ảnh trong drawable
                "Xe con và xe tải đèn xanh nên được đi."                                // Giải thích
        );

        addQuestion(
                db,
                "Các xe đi theo mũi tên, xe nào vi phạm quy tắc giao thông?", // Câu hỏi
                "Xe khách, xe tải, mô tô.",                                   // Lựa chọn 1
                "Xe tải, xe con, mô tô.",                                     // Lựa chọn 2
                "Xe khách, xe con, mô tô.",                                   // Lựa chọn 3
                null,                                                         // Không có lựa chọn 4
                "1",                                                          // Đáp án đúng
                4,                                                            // Nhóm câu hỏi
                "sa_hinh_123",                                                // Tên hình ảnh
                "Xe khách đi sai hướng (lẽ ra phải rẽ trái). Xe tải vượt đèn đỏ. Xe mô tô đi sai hướng (lẽ ra phải rẽ trái)." // Giải thích
        );

        addQuestion(
                db,
                "Thứ tự các xe đi như thế nào là đúng quy tắc giao thông?",   // Câu hỏi
                "Xe khách, xe tải, mô tô, xe con.",                           // Lựa chọn 1
                "Xe con, xe khách, xe tải, mô tô.",                           // Lựa chọn 2
                "Mô tô, xe tải, xe khách, xe con.",                           // Lựa chọn 3
                "Mô tô, xe tải, xe con, xe khách.",                           // Lựa chọn 4
                "3",                                                          // Đáp án đúng
                4,                                                            // Nhóm câu hỏi
                "sa_hinh_124",                                                // Tên hình ảnh
                "Nơi giao nhau cùng cấp, không có biển báo hiệu đi theo vòng xuyến thì nhường cho xe bên phải. Bên phải xe mô tô không vướng xe khác nên không phải nhường. Xe mô tô đi trước. Sau khi mô tô đi rồi thì xe tải bên phải không vướng nên đi tiếp theo. Cứ như vậy cho đến hết." // Giải thích
        );

        addQuestion(
                db,
                "Trong trường hợp này xe nào đỗ vi phạm quy tắc giao thông?", // Câu hỏi
                "Xe tải.",                                                    // Lựa chọn 1
                "Xe con và mô tô.",                                           // Lựa chọn 2
                "Cả ba xe.",                                                  // Lựa chọn 3
                "Xe con và xe tải.",                                          // Lựa chọn 4
                "1",                                                          // Đáp án đúng
                4,                                                            // Nhóm câu hỏi
                "sa_hinh_125",                                                // Tên hình ảnh
                "Quan sát biển báo phụ bên dưới biển cấm dừng và đỗ, đó là hình xe tải. Ý nghĩa: cấm xe tải dừng và đỗ ở đây." // Giải thích
        );

        addQuestion(
                db,
                "Xe nào được quyền đi trước?",                                // Câu hỏi
                "Xe tải.",                                                    // Lựa chọn 1
                "Xe con.",                                                    // Lựa chọn 2
                "Xe lam.",                                                    // Lựa chọn 3
                null,                                                         // Không có lựa chọn 4
                "2",                                                          // Đáp án đúng
                4,                                                            // Nhóm câu hỏi
                "sa_hinh_126",                                                // Tên hình ảnh
                "Xe con nằm trên đường ưu tiên nên được đi trước."            // Giải thích
        );

        addQuestion(
                db,
                "Theo mũi tên, những hướng nào xe gắn máy đi được?",          // Câu hỏi
                "Cả ba hướng.",                                               // Lựa chọn 1
                "Hướng 1 và 3.",                                              // Lựa chọn 2
                "Chỉ hướng 1.",                                               // Lựa chọn 3
                null,                                                         // Không có lựa chọn 4
                "1",                                                          // Đáp án đúng
                4,                                                            // Nhóm câu hỏi
                "sa_hinh_127",                                                // Tên hình ảnh
                "Hướng số 2 cấm xe mô tô nên xe gắn máy vào được."            // Giải thích
        );

        addQuestion(
                db,
                "Xe nào đỗ vi phạm quy tắc giao thông?",                      // Câu hỏi
                "Cả hai xe.",                                                 // Lựa chọn 1
                "Không xe nào vi phạm.",                                      // Lựa chọn 2
                "Chỉ xe mô tô vi phạm.",                                      // Lựa chọn 3
                "Chỉ xe tải vi phạm.",                                        // Lựa chọn 4
                "1",                                                          // Đáp án đúng
                4,                                                            // Nhóm câu hỏi
                "sa_hinh_128",                                                // Tên hình ảnh
                "Biển phụ đặt dưới biển báo cấm dừng và đỗ mang ý nghĩa là: Cấm dừng và đỗ phía trước và sau biển này." // Giải thích
        );

        addQuestion(
                db,
                "Xe nào đỗ vi phạm quy tắc giao thông?",                      // Câu hỏi
                "Mô tô.",                                                     // Lựa chọn 1
                "Xe tải.",                                                    // Lựa chọn 2
                "Cả ba xe.",                                                  // Lựa chọn 3
                "Mô tô và xe tải.",                                           // Lựa chọn 4
                "3",                                                          // Đáp án đúng
                4,                                                            // Nhóm câu hỏi
                "sa_hinh_129",                                                // Tên hình ảnh
                "Xe tải đỗ ngược chiều. Xe mô tô đỗ trên vạch người đi bộ. Xe con cán qua vạch dừng." // Giải thích
        );

        addQuestion(
                db,
                "Xe tải kéo mô tô ba bánh như hình này có đúng quy tắc giao thông không?", // Câu hỏi
                "Đúng.",                                                                    // Lựa chọn 1
                "Không đúng.",                                                              // Lựa chọn 2
                null,                                                                       // Không có lựa chọn 3
                null,                                                                       // Không có lựa chọn 4
                "2",                                                                        // Đáp án đúng
                4,                                                                          // Nhóm câu hỏi
                "sa_hinh_130",                                                              // Tên hình ảnh
                "Có biển báo cấm ô tô kéo mooc. Với lại, theo quy định, ô tô không được kéo xe mô tô." // Giải thích
        );

        addQuestion(
                db,
                "Xe nào được quyền đi trước trong trường hợp này?", // Câu hỏi
                "Xe lam.",                                         // Lựa chọn 1
                "Xe xích lô.",                                     // Lựa chọn 2
                null,                                              // Không có lựa chọn 3
                null,                                              // Không có lựa chọn 4
                "2",                                               // Đáp án đúng
                4,                                                 // Nhóm câu hỏi
                "sa_hinh_131",                                     // Tên hình ảnh
                null                                               // Không có giải thích
        );


        addQuestion(
                db,
                "Thứ tự các xe đi như thế nào là đúng quy tắc giao thông?", // Câu hỏi
                "Xe lam, mô tô, xe con, xe đạp.",                          // Lựa chọn 1
                "Xe con, xe đạp, mô tô, xe lam.",                          // Lựa chọn 2
                "Xe lam, xe con, mô tô + xe đạp.",                         // Lựa chọn 3
                "Mô tô + xe đạp, xe lam, xe con.",                         // Lựa chọn 4
                "4",                                                       // Đáp án đúng
                4,                                                         // Nhóm câu hỏi
                "sa_hinh_132",                                             // Tên hình ảnh
                "Xe cùng cấp, xe nào bên phải trống được đi trước."        // Giải thích
        );


        addQuestion(
                db,
                "Xe nào được quyền đi trước trong trường hợp này?", // Câu hỏi
                "Mô tô.",                                          // Lựa chọn 1
                "Xe con.",                                         // Lựa chọn 2
                null,                                              // Không có lựa chọn 3
                null,                                              // Không có lựa chọn 4
                "1",                                               // Đáp án đúng
                4,                                                 // Nhóm câu hỏi
                "sa_hinh_133",                                     // Tên hình ảnh
                null                                               // Không có giải thích
        );

        addQuestion(
                db,
                "Xe nào vi phạm quy tắc giao thông?", // Câu hỏi
                "Xe khách.",                         // Lựa chọn 1
                "Mô tô.",                            // Lựa chọn 2
                "Xe con.",                           // Lựa chọn 3
                "Xe con và mô tô.",                  // Lựa chọn 4
                "3",                                 // Đáp án đúng
                4,                                   // Nhóm câu hỏi
                "sa_hinh_134",                       // Tên hình ảnh
                "Xe con vượt qua vạch liền nên vi phạm." // Giải thích
        );

        addQuestion(
                db,
                "Các xe đi như thế nào là đúng quy tắc giao thông?", // Câu hỏi
                "Các xe ở phía tay phải và tay trái của người điều khiển được phép đi thẳng.", // Lựa chọn 1
                "Cho phép các xe ở mọi hướng được rẽ phải.",                                   // Lựa chọn 2
                "Tất cả các xe phải dừng lại trước ngã tư, trừ những xe đã ở trong ngã tư được phép tiếp tục đi.", // Lựa chọn 3
                null,                                                                          // Không có lựa chọn 4
                "3",                                                                           // Đáp án đúng
                4,                                                                             // Nhóm câu hỏi
                "sa_hinh_135",                                                                 // Tên hình ảnh
                "Người điều khiển giao thông giơ tay thẳng đứng thì tất cả các xe phải dừng lại, trừ xe đã vào giao lộ." // Giải thích
        );

        addQuestion(
                db,
                "Theo hướng mũi tên, xe nào được phép đi?", // Câu hỏi
                "Mô tô, xe con.",                           // Lựa chọn 1
                "Xe con, xe tải.",                          // Lựa chọn 2
                "Mô tô, xe tải.",                           // Lựa chọn 3
                "Cả ba xe.",                                // Lựa chọn 4
                "3",                                        // Đáp án đúng
                4,                                          // Nhóm câu hỏi
                "sa_hinh_136",                              // Tên hình ảnh
                "Người điều khiển giao thông giơ 2 tay dang ngang thì phía trước và phía sau người điều khiển phải dừng lại, bên phải và bên trái được đi." // Giải thích
        );

        addQuestion(
                db,
                "Trong hình dưới đây, xe nào chấp hành đúng quy tắc giao thông?", // Câu hỏi
                "Xe khách, mô tô.",                                               // Lựa chọn 1
                "Tất cả các loại xe trên.",                                       // Lựa chọn 2
                "Không xe nào chấp hành đúng quy tắc giao thông.",                // Lựa chọn 3
                null,                                                             // Không có lựa chọn 4
                "2",                                                              // Đáp án đúng
                4,                                                                // Nhóm câu hỏi
                "sa_hinh_137",                                                    // Tên hình ảnh
                "Người điều khiển giao thông giơ 2 tay dang ngang thì phía trước và phía sau người điều khiển phải dừng lại, bên phải và bên trái được đi." // Giải thích
        );

        addQuestion(
                db,
                "Theo hướng mũi tên, những hướng nào xe mô tô được phép đi.", // Câu hỏi
                "Cả ba hướng.",                                               // Lựa chọn 1
                "Hướng 1 và 2.",                                              // Lựa chọn 2
                "Hướng 1 và 3.",                                              // Lựa chọn 3
                "Hướng 2 và 3.",                                              // Lựa chọn 4
                "3",                                                          // Đáp án đúng
                4,                                                            // Nhóm câu hỏi
                "sa_hinh_138",                                                // Tên hình ảnh
                null                                                          // Không có giải thích
        );

        addQuestion(
                db,
                "Trong trường hợp này, thứ tự các xe đi như thế nào là đúng quy tắc giao thông?", // Câu hỏi
                "Xe công an, xe quân sự, xe con + mô tô.",                                       // Lựa chọn 1
                "Xe quân sự, xe công an, xe con + mô tô.",                                       // Lựa chọn 2
                "Xe mô tô + xe con, xe quân sự, xe công an.",                                    // Lựa chọn 3
                null,                                                                            // Không có lựa chọn 4
                "2",                                                                             // Đáp án đúng
                4,                                                                               // Nhóm câu hỏi
                "sa_hinh_139",                                                                   // Tên hình ảnh
                null                                                                             // Không có giải thích
        );

        addQuestion(
                db,
                "Trong hình dưới, những xe nào vi phạm quy tắc giao thông?", // Câu hỏi
                "Xe con (E), mô tô (C).",                                    // Lựa chọn 1
                "Xe tải (A), mô tô (D).",                                    // Lựa chọn 2
                "Xe khách (B), mô tô (C).",                                  // Lựa chọn 3
                "Xe khách (B), mô tô (D).",                                  // Lựa chọn 4
                "1",                                                         // Đáp án đúng
                4,                                                           // Nhóm câu hỏi
                "sa_hinh_140",                                               // Tên hình ảnh
                null                                                         // Không có giải thích
        );

    }
    public List<Question> getRandomQuestionsByGroup(int groupId, int limit) {
        List<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM questions WHERE group_id = ? ORDER BY RANDOM() LIMIT ?",
                new String[]{String.valueOf(groupId), String.valueOf(limit)}
        );

        if (cursor != null) {
            try {
                int colQuestion = cursor.getColumnIndex("question");
                int colOption1 = cursor.getColumnIndex("option1");
                int colOption2 = cursor.getColumnIndex("option2");
                int colOption3 = cursor.getColumnIndex("option3");
                int colOption4 = cursor.getColumnIndex("option4");
                int colCorrectAnswer = cursor.getColumnIndex("correct_answer");
                int colImage = cursor.getColumnIndex("image");
                int colExplanation = cursor.getColumnIndex("explanation");

                while (cursor.moveToNext()) {
                    Question question = new Question(
                            cursor.getString(colQuestion),
                            cursor.getString(colOption1),
                            cursor.getString(colOption2),
                            cursor.getString(colOption3),
                            cursor.getString(colOption4),
                            cursor.getString(colCorrectAnswer),
                            cursor.getString(colImage),
                            cursor.getString(colExplanation)
                    );
                    questionList.add(question);
                }
            } finally {
                cursor.close();
            }
        }
        return questionList;
    }




    // Hàm lấy danh sách câu hỏi theo groupId
    public Cursor getQuestionsByGroup(int groupId, int limit) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTIONS +
                " WHERE " + COLUMN_GROUP_ID + " = ?" +
                " ORDER BY RANDOM() LIMIT ?";
        return db.rawQuery(query, new String[]{String.valueOf(groupId), String.valueOf(limit)});
    }

}
