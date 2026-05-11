package com.example.lin_new_project.room.data;

public class UserData {
    private String userName="";//使用者名稱
    private String ImageName="";//圖片名稱
    private int userType=0; // 0 訪客 ,1 user,-1 ADD,-2無


    public UserData(){

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }


}
