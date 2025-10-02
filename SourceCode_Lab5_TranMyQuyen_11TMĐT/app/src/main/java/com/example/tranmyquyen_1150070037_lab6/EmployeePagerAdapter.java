package com.example.tranmyquyen_1150070037_lab6;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Một FragmentStateAdapter đơn giản để cung cấp dữ liệu (dưới dạng EmployeeFragment)
 * cho một ViewPager2.
 */
public class EmployeePagerAdapter extends FragmentStateAdapter {

    /**
     * Tổng số trang (Fragments) mà ViewPager sẽ hiển thị.
     */
    private static final int NUM_PAGES = 5;

    /**
     * Constructor cho PagerAdapter.
     * @param fragmentActivity Activity chứa ViewPager2.
     */
    public EmployeePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     * Được gọi bởi ViewPager2 để tạo một Fragment mới cho một vị trí cụ thể.
     *
     * @param position Vị trí (trang) của Fragment cần được tạo.
     * @return Một instance mới của EmployeeFragment được cấu hình cho vị trí đó.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Sử dụng một câu lệnh switch để cung cấp dữ liệu khác nhau cho mỗi trang.
        // Mỗi case sẽ tạo một EmployeeFragment mới với tên và chức vụ riêng.
        switch (position) {
            case 0:
                return EmployeeFragment.newInstance("Elizabeth Johnson", "Project Manager");
            case 1:
                return EmployeeFragment.newInstance("Catherine Williams", "President of Sales");
            case 2:
                return EmployeeFragment.newInstance("John Smith", "Lead Developer");
            case 3:
                return EmployeeFragment.newInstance("Anna Brown", "UX/UI Designer");
            case 4:
                return EmployeeFragment.newInstance("David Jones", "QA Engineer");
            default:
                // Trường hợp mặc định để tránh lỗi, mặc dù không bao giờ nên xảy ra
                // nếu getItemCount() được triển khai đúng.
                return EmployeeFragment.newInstance("Default Employee", "Default Position");
        }
    }

    /**
     * Trả về tổng số mục (trang) mà adapter sẽ quản lý.
     *
     * @return Tổng số trang.
     */
    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
