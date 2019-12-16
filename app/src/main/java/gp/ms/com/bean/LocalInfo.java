package gp.ms.com.bean;

import java.io.Serializable;

public class LocalInfo implements Serializable {

    private int tId;

    private String tName;

    private int backRouse;


    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public int getBackRouse() {
        return backRouse;
    }

    public void setBackRouse(int backRouse) {
        this.backRouse = backRouse;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public LocalInfo(int tId, String tName, int backRouse) {
        this.tId = tId;
        this.tName = tName;
        this.backRouse = backRouse;
    }
}
