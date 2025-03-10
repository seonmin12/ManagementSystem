package model;

import jdk.jfr.Unsigned;
import util.DBUtil;
import vo.EmployeeVO;
import vo.ParttimeVO;
import vo.PersonVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * {@code ParttimeDAO} 클래스는 시간제 직원 데이터를 관리하기 위한 데이터 접근 객체(DAO)입니다.
 * 데이터베이스에 연결하여 시간제 직원 데이터를 추가, 삭제, 수정, 검색, 정렬하는 기능을 제공합니다.
 *
 * 주요 기능:
 * - 데이터베이스 연결 및 쿼리 실행
 * - 시간제 직원 데이터 추가, 수정, 삭제, 정렬
 * - 임금 계산
 */
public class ParttimeDAO implements Parttime{
    /**
     * 싱글톤(Singleton)으로 구현된 DAO 인스턴스
     */
    private static ParttimeDAO dao;

    /**
     * 생성자를 private으로 설정하여 외부에서 객체 생성을 제한합니다.
     */
    private ParttimeDAO() {}

    /**
     * DAO 인스턴스를 반환하는 싱글톤(Singleton) 메서드
     *
     * @return {@code ParttimeDAO} 인스턴스
     */
    public static ParttimeDAO getInstance() {

        if(dao == null) dao = new ParttimeDAO();
        return dao;
    }

    /** 시간제 직원 데이터를 저장하는 리스트 */
    private ArrayList<ParttimeVO> parttimeList = new ArrayList<>();
    /** 데이터베이스 연결 객체 */
    private Connection conn;
    /** SQL 문 실행을 위한 PreparedStatement 객체 */
    private PreparedStatement pstmt;
    /** SQL 문 실행을 위한 Statement 객체 */
    private Statement stmt;
    /** SQL 쿼리 결과를 저장하는 ResultSet 객체 */
    private ResultSet rs;
    /** 저장 프로시저 호출을 위한 CallableStatement 객체 */
    private CallableStatement cs;

    /**
     * 데이터베이스 연결 종료 메서드
     * 사용한 ResultSet, Statement, PreparedStatement, Connection 객체를 닫습니다.
     */
    private void disconnect(){
        if (rs != null) try {rs.close();} catch (SQLException e) {}
        if (stmt != null) try {stmt.close();} catch (SQLException e) {}
        if (pstmt != null) try {pstmt.close();} catch (SQLException e) {}
        if (conn != null) try {conn.close();} catch (SQLException e) {}
    }

    /**
     * 데이터베이스 연결 및 시간제 직원 데이터 읽어오기
     * DB에서 `Parttime` 테이블의 데이터를 읽어와 `parttimeList` 리스트에 저장합니다.
     */
    private void connect() {
        try{
            conn = DBUtil.getConnection();
            String sql = "select * from Parttime ";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                // 임시 parttime 객체 생성
                ParttimeVO parttime = new ParttimeVO();

                // rs에서 임시 parttime 객체로 데이터 읽어오기
                parttime.setName(rs.getString("name"));
                parttime.setEmpNo(rs.getString("empNo"));
                parttime.setHourWage(rs.getInt("hourWage"));
                parttime.setWorkHour(rs.getInt("workHour"));

                if (parttime.getHourWage() < 10030) parttime.setHourWage(10030);
                else if (parttime.getHourWage() > 100000000) parttime.setHourWage(100000000);
                if (parttime.getWorkHour() < 0) parttime.setWorkHour(0);
                else if (parttime.getWorkHour() > 100000000) parttime.setWorkHour(100000000);

                // 임금 계산
                int wage = parttime.getHourWage() * parttime.getWorkHour();
                if (wage < 0) wage = Integer.MAX_VALUE;
                parttime.setWage(wage);

                // 임시 parttime 객체 리스트에 저장
                parttimeList.add(parttime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    /**
     * 시간제 직원 데이터를 추가합니다.
     *
     * 데이터베이스와 리스트에 새 데이터 추가 및 필요한 수치를 계산합니다.
     *
     * @param personVO 추가할 시간제 직원 데이터를 포함하는 객체
     */
    @Override
    public void input(PersonVO personVO) {

        ParttimeVO parttime = (ParttimeVO) personVO;

        //  fulltimeList가 비어있으면 DB에서 데이터 읽어오기
        if (parttimeList.size() == 0) {
            this.connect();
        }

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            cs = conn.prepareCall("{call PARTTIME_INSERT(?,?,?,?,?)}");

            cs.setString(1, parttime.getEmpNo());
            cs.setString(2, parttime.getName());
            cs.setInt(3, parttime.getHourWage());
            cs.setInt(4, parttime.getWorkHour());

            cs.registerOutParameter(5, java.sql.Types.INTEGER);
            cs.execute();

            int resultMsg = cs.getInt(5);
            if (resultMsg == 100) {
                System.out.println("DB 입력 실패");
            } else {
                if (parttime.getHourWage() < 10030) parttime.setHourWage(10030);
                else if (parttime.getHourWage() > 100000000) parttime.setHourWage(100000000);
                if (parttime.getWorkHour() < 0) parttime.setWorkHour(0);
                else if (parttime.getWorkHour() > 100000000) parttime.setWorkHour(100000000);

                // 임금 계산
                int wage = parttime.getHourWage() * parttime.getWorkHour();
                if (wage < 0) wage = Integer.MAX_VALUE;
                parttime.setWage(wage);

                parttimeList.add(parttime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    /**
     * 시간제 직원 데이터를 수정합니다.
     *
     * 데이터베이스와 리스트에서 시간제 직원 데이터를 갱신합니다.
     * 수정된 데이터에 따라 임금 계산도 업데이트됩니다.
     *
     * @param personVO 수정할 시간제 직원 데이터를 포함하는 객체
     */
    @Override
    public void update(PersonVO personVO) {

        ParttimeVO parttime = (ParttimeVO) personVO;

        //  fulltimeList가 비어있으면 DB에서 데이터 읽어오기
        if (parttimeList.size() == 0) {
            this.connect();
        }

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            cs = conn.prepareCall("{call PARTTIME_UPDATE(?,?,?,?,?)}");

            // 바인드 변수에 값 세팅(in parameter)
            cs.setString(1, parttime.getEmpNo());
            cs.setString(2, parttime.getName());
            cs.setInt(3,parttime.getHourWage());
            cs.setInt(4, parttime.getWorkHour());

            // out 파라미터에 저장된 프로시저의 수행결과에 대한 외부 변수 등록
            cs.registerOutParameter(5, java.sql.Types.INTEGER);

            // 쿼리 수행, flag 값은 RS의 경우 true, 갱신, 카운트 또는 결과가 없는 경우 false 리턴
            cs.execute();
            int resultMsg = cs.getInt(5);

            if (resultMsg == 100) {
                System.out.println("DB 수정 실패");
            } else {
                if (parttime.getHourWage() < 10030) parttime.setHourWage(10030);
                else if (parttime.getHourWage() > 100000000) parttime.setHourWage(100000000);
                if (parttime.getWorkHour() < 0) parttime.setWorkHour(0);
                else if (parttime.getWorkHour() > 100000000) parttime.setWorkHour(100000000);

                // 임금 계산
                int wage = parttime.getHourWage() * parttime.getWorkHour();
                if (wage < 0) wage = Integer.MAX_VALUE;
                parttime.setWage(wage);

                parttimeList.set(parttimeList.indexOf(parttime), parttime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    /**
     * 시간제 직원 데이터를 삭제합니다.
     *
     * 데이터베이스와 `parttimeList`에서 특정 사번에 해당하는 데이터를 삭제합니다.
     * 삭제 실패 시 오류 메시지를 출력합니다.
     *
     * @param deleteNum 삭제할 시간제 직원의 사번
     */
    @Override
    public void delete(String deleteNum) {
        //  parttimeList가 비어있으면 DB에서 데이터 읽어오기
        if (parttimeList.size() == 0) this.connect();


        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            cs = conn.prepareCall("{call PARTTIME_DELETE(?,?)}");

            cs.setString(1, deleteNum);
            cs.registerOutParameter(2,java.sql.Types.INTEGER);

            cs.execute();
            int resultMsg = cs.getInt(2);

            if (resultMsg == 100) {
                System.out.println("DB 삭제 실패");
            } else {
                parttimeList.remove(parttimeList.indexOf(
                        new ParttimeVO(deleteNum, null, 0, 0)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 시간제 직원 데이터를 특정 조건에 따라 정렬하여 출력합니다.
     *
     * `sortNum`에 따라 이름순(1), 사번순(2), 일한시간순(3) 등의 정렬을 수행하며,
     * 정렬된 데이터는 단순 출력 목적으로 사용됩니다.
     *
     * @param sortNum 정렬 조건을 나타내는 번호
     */
    @Override
    public void totalSearch(int sortNum) {
        //  parttimeList가 비어있으면 DB에서 데이터 읽어오기
        if (parttimeList.size() == 0) this.connect();

        // 리스트의 내용을 sortNum 으로 정렬
        this.sort(sortNum);

        // 리스트의 내용을 출력
        parttimeList.forEach(System.out::println);
    }

    /**
     * 특정 시간제 직원 데이터를 검색하여 출력합니다.
     *
     * `parttimeList`에 저장된 특정 사번을 가진 시간제 직원 데이터를 찾아 출력합니다.
     *
     * @param searchNum 검색할 시간제 직원의 사번
     */
    @Override
    public void search(String searchNum) {
        //  parttimeList가 비어있으면 DB에서 데이터 읽어오기
        if (parttimeList.size() == 0) {
            this.connect();
        }

        ParttimeVO temp = parttimeList.get(parttimeList.indexOf(
                                new ParttimeVO(searchNum, null, 0, 0)
                        ));
        System.out.println(temp);
    }

    /**
     * 시간제 직원 데이터를 정렬합니다.
     *
     * `sortNum` 변수에 따라 시간제 직원 데이터를 다양한 기준으로 정렬합니다:
     * - 1: 이름순
     * - 2: 사번순
     * - 3: 일한시간순 (내림차순) 등
     *
     * @param sortNum 정렬 조건을 나타내는 번호
     */
    @Override
    public void sort(int sortNum) {
        switch (sortNum) {
            case 1: // 이름순
                parttimeList.sort(Comparator.comparing(ParttimeVO::getName));
                break;
            case 2: // 사번순
                parttimeList.sort((o1, o2) -> o1.compareTo(o2));
                break;
            case 3: // 일한시간순
                parttimeList.sort(Comparator.comparing(ParttimeVO::getWorkHour).reversed());
                break;
            case 4:
                break;
        }
    }
}
