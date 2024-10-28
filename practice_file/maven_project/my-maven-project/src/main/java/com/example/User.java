package com.example;

public class User {
    private final String firstName; // 必需
    private final String lastName;  // 必需
    private final int age;          // 可选
    private final String phone;     // 可选
    private final String address;   // 可选

    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    // UserBuilder静态类
    public static class UserBuilder {
        private final String firstName;
        private final String lastName;
        private int age = 0;
        private String phone = "";
        private String address = "";

        public UserBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public static void main(String[] args) {
        User user = new User.UserBuilder("John", "Doe").age(30)
                            .phone("1234567890")
                            .address("Fake Address 1234")
                            .build();
    }
}