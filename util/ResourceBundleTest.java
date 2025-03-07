package util;

import java.util.ResourceBundle;

public class ResourceBundleTest {

    public static void main(String[] args) {
        // ResourceBundle객체를 이용하여 파일 읽어오기
        ResourceBundle bundle = ResourceBundle.getBundle("util.dbinfo");

        // 읽어온 내용 출력하기
        System.out.println("driver : " + bundle.getString("driver"));
        System.out.println("url : " + bundle.getString("url"));
        System.out.println("user : " + bundle.getString("user"));
        System.out.println("pass : " + bundle.getString("password"));
    }

}