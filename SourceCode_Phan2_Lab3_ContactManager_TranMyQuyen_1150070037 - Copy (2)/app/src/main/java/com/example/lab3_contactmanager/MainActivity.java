package com.example.lab3_contactmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private List<Contact> contacts; // Danh sách các đối tượng Contact
    private ArrayAdapter<String> adapter;
    private ArrayList<String> contactDisplayList; // Danh sách chuỗi để hiển thị
    private ListView lvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ ListView từ file layout
        lvContacts = findViewById(R.id.lv_contacts);
        db = new DatabaseHandler(this);

        // Xóa dữ liệu cũ để mỗi lần chạy lại ứng dụng sẽ không bị lặp lại dữ liệu
        db.deleteAllContacts();

        // Thêm dữ liệu mẫu vào CSDL như trong tài liệu
        Log.d("Insert: ", "Đang thêm dữ liệu mẫu...");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        // Gọi hàm để tải và hiển thị dữ liệu
        loadDataToListView();

        // Cài đặt sự kiện nhấn giữ (Long Click) cho ListView
        lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy đối tượng Contact tại vị trí được nhấn giữ
                Contact contactToDelete = contacts.get(position);

                // Gọi hàm xóa contact khỏi CSDL
                db.deleteContact(contactToDelete);

                // Hiển thị một thông báo ngắn để người dùng biết
                Toast.makeText(MainActivity.this, "Đã xóa " + contactToDelete.getName(), Toast.LENGTH_SHORT).show();

                // Tải lại danh sách để cập nhật giao diện
                loadDataToListView();

                return true; // Trả về true để báo hiệu rằng sự kiện đã được xử lý
            }
        });
    }

    // Hàm để tải dữ liệu từ CSDL và hiển thị lên ListView
    private void loadDataToListView() {
        Log.d("Reading: ", "Đang đọc tất cả contact...");
        // Lấy danh sách contact mới nhất từ CSDL
        contacts = db.getAllContacts();
        contactDisplayList = new ArrayList<>();
// Chuyển đổi danh sách đối tượng Contact thành danh sách chuỗi để hiển thị
        for (Contact cn : contacts) {
            String log = "Id: " + cn.getID() + ", Name: " + cn.getName() + ", Phone: " + cn.getPhoneNumber();
            Log.d("Contact Info: ", log); // Ghi log để kiểm tra (có thể xem ở tab Logcat)
            contactDisplayList.add(cn.getName() + "\n" + cn.getPhoneNumber());
        }

        // Tạo Adapter để kết nối dữ liệu với ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactDisplayList);
        lvContacts.setAdapter(adapter);
    }
}
