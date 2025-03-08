package util;

import java.util.ResourceBundle;

/**
 * {@code ResourceBundleTest} 클래스는 {@link ResourceBundle}을 사용하여
 * properties 파일에서 값을 읽고 이를 콘솔에 출력하는 방법을 보여줍니다.
 * 이 예제에서는 `dbinfo.properties` 파일에서 데이터베이스 연결 정보를 읽습니다.
 */
public class ResourceBundleTest {

    /**
     * 프로그램 실행의 시작점인 main 메서드.
     * `dbinfo.properties` 파일에서 "driver", "url", "user", "password" 값을 읽어와
     * 콘솔에 출력합니다.
     *
     * @param args 명령행 매개변수 (이 예제에서는 사용되지 않음).
     */
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