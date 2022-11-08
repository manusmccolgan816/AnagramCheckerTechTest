import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Entry {

    private String username;
    private String value1;
    private String value2;
    private boolean isAnagram;

    public Entry(String username, String value1, String value2, boolean isAnagram) {
        this.username = username;
        this.value1 = value1;
        this.value2 = value2;
        this.isAnagram = isAnagram;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public boolean getIsAnagram() {
        return isAnagram;
    }

    public void setIsAnagram(boolean isAnagram) {
        this.isAnagram = isAnagram;
    }
}
