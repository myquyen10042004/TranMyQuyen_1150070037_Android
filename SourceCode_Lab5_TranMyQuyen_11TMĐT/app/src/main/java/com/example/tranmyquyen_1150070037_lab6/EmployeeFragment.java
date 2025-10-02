package com.example.tranmyquyen_1150070037_lab6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class EmployeeFragment extends Fragment {

    // Các hằng số để làm key khi truyền dữ liệu qua Bundle
    // Using constants is a good practice to avoid typos
    private static final String ARG_NAME = "employee_name";
    private static final String ARG_POSITION = "employee_position";

    // Biến để lưu trữ dữ liệu của nhân viên
    private String employeeName;
    private String employeePosition;

    public EmployeeFragment() {
        // Required empty public constructor
    }

    public static EmployeeFragment newInstance(String name, String position) {
        EmployeeFragment fragment = new EmployeeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Lấy dữ liệu từ Bundle khi Fragment được tạo
            employeeName = getArguments().getString(ARG_NAME);
            employeePosition = getArguments().getString(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate (tạo) layout XML cho Fragment này
        return inflater.inflate(R.layout.fragment_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Phương thức này được gọi ngay sau khi onCreateView() hoàn tất.
        // Đây là nơi an toàn để thao tác với các View của Fragment.
        // Ánh xạ các TextView từ layout
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvPosition = view.findViewById(R.id.tv_position);

        // Gán dữ liệu đã nhận được lên các TextView
        if (employeeName != null) {
            tvName.setText(employeeName);
        }
        if (employeePosition != null) {
            // Thêm tiền tố "Position: " để giao diện rõ ràng hơn
            tvPosition.setText("Position: " + employeePosition);
        }
    }
}