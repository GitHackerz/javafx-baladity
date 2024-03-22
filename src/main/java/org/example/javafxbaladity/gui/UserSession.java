package org.example.javafxbaladity.gui;

public class UserSession {
    private static UserSession instance;

    private int userId;
    private String userName;
    private String prenom;
    private String role;


    public UserSession(int userId, String userName, String prenom, String role) {
        this.userId = userId;
        this.userName = userName;
        this.prenom = prenom;
        this.role = role;

    }

    public static UserSession getInstace(int userId, String userName, String prenom, String role) {
        if(instance == null) {
            instance = new UserSession(userId, userName, prenom, role);
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getRole() {
        return role;
    }



    public void cleanUserSession() {
        userId = 0;
        userName = "";
        prenom = "";
        role = "";

    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", prenom='" + prenom + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
