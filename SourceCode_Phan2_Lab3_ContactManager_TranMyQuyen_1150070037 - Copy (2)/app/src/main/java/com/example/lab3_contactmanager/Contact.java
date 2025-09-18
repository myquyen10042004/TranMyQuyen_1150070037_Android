package com.example.lab3_contactmanager;

public class Contact {
    private int id;
    private String name;
    private String phoneNumber;

    // Constructor (phương thức khởi tạo) rỗng
    public Contact() {
    }

    // Constructor để tạo đối tượng Contact mới (chưa có ID)
    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Constructor đầy đủ các thuộc tính
    public Contact(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Các phương thức để lấy và gán giá trị (Getters and Setters)
    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
