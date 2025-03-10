package model;

import util.DBUtil;
import vo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * {@code FulltimeDAO} 클래스는 정규직 직원 데이터를 관리하기 위한 데이터 접근 객체(DAO)입니다.
 * 데이터베이스에 연결하여 정규직 직원 데이터를 추가, 삭제, 수정, 검색, 정렬하는 기능을 제공합니다.
 *
 * 주요 기능:
 * - 데이터베이스 연결 및 쿼리 실행
 * - 정규직 직원 데이터 추가, 수정, 삭제, 정렬
 * - 월급 계산 및 인상 처리
 */
public class FulltimeDAO implements Fulltime  {

    /**
     * 싱글톤(Singleton)으로 구현된 DAO 인스턴스
     */
    private static FulltimeDAO dao;

    /**
     * 생성자를 private으로 설정하여 외부에서 객체 생성을 제한합니다.
     */
    private FulltimeDAO(){ }

    /**
     * DAO 인스턴스를 반환하는 싱글톤(Singleton) 메서드
     *
     * @return {@code FulltimeDAO} 인스턴스
     */
    public static FulltimeDAO getInstance(){
        if(dao==null) dao = new FulltimeDAO();

        return dao;
    }

    /** 정규직 직원 데이터를 저장하는 리스트 */
    private ArrayList<FulltimeVO> fulltimeList = new ArrayList<>();
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
     * 데이터베이스 연결 및 정규직 직원 데이터 읽어오기
     * DB에서 `Fulltime` 테이블의 데이터를 읽어와 `fulltimeList` 리스트에 저장합니다.
     */
    private void connect() {
        try{
            conn = DBUtil.getConnection();
            String sql = "select * from Fulltime ";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                // 임시 fulltime 객체 생성
                FulltimeVO fulltime = new FulltimeVO();

                // rs에서 임시 fulltime 객체로 데이터 읽어오기
                fulltime.setName(rs.getString("name"));
                fulltime.setEmpNo(rs.getString("empNo"));
                fulltime.setResult(rs.getInt("result"));
                fulltime.setBasicSalary(rs.getInt("basicSalary"));

                // 임시 parttime 객체 리스트에 저장
                fulltimeList.add(fulltime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    /**
     * 정규직 직원 데이터를 추가합니다.
     *
     * 데이터베이스와 리스트에 새 데이터를 추가로 저장합니다.
     *
     * @param personVO 추가할 정규직 직원 데이터를 포함하는 객체
     */
    @Override
    public void input(PersonVO personVO) {

        FulltimeVO fulltime = (FulltimeVO) personVO;

        if (fulltimeList.size() == 0) {
            this.connect();
        }

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            cs = conn.prepareCall("{call FULLTIME_INSERT(?,?,?,?,?)}");

            cs.setString(1, fulltime.getEmpNo());
            cs.setString(2, fulltime.getName());
            cs.setInt(3, fulltime.getResult());
            cs.setInt(4, fulltime.getBasicSalary());

            cs.registerOutParameter(5, java.sql.Types.INTEGER);
            cs.execute();

            int resultMsg = cs.getInt(5);
            if(resultMsg == 100){
                System.out.println("DB 입력 실패");
            }else {
                fulltimeList.add(fulltime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    /**
     * 정규직 직원 데이터를 수정합니다.
     *
     * 데이터베이스와 리스트에서 정규직 직원 데이터를 갱신합니다.
     *
     * @param personVO 수정할 정규직 직원 데이터를 포함하는 객체
     */
    @Override
    public void update(PersonVO personVO) {

        FulltimeVO fulltime = (FulltimeVO) personVO;

        if(fulltimeList.size() == 0) this.connect();

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            cs = conn.prepareCall("{call FULLTIME_UPDATE(?,?,?,?,?)}");


          // 바인드 변수에 값 세팅(in parameter)
            cs.setString(1, fulltime.getEmpNo());
            cs.setString(2, fulltime.getName());
            cs.setInt(3,fulltime.getResult());
            cs.setInt(4, fulltime.getBasicSalary());

            // out 파라미터에 저장된 프로시저의 수행결과에 대한 외부 변수 등록
            cs.registerOutParameter(5, java.sql.Types.INTEGER);

            // 쿼리 수행, flag 값은 RS의 경우 true, 갱신, 카운트 또는 결과가 없는 경우 false 리턴
            cs.execute();

            int resultMsg = cs.getInt(5);
            if(resultMsg == 100){
                System.out.println("DB 수정 실패");
            }else{
                fulltimeList.set(fulltimeList.indexOf(fulltime), fulltime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    /**
     * 정규직 직원 데이터를 삭제합니다.
     *
     * 데이터베이스와 리스트에서 특정 사번에 해당하는 데이터를 삭제합니다.
     * 삭제 실패 시 오류 메시지를 출력합니다.
     *
     * @param deleteNum 삭제할 정규직 직원의 사번
     */
    @Override
    public void delete(String deleteNum) {
        //fulltimeList가 비어있으면 DB에서 데이터 읽어오기
        if(fulltimeList.size() == 0) this.connect();

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            cs = conn.prepareCall("{call FULLTIME_DELETE(?,?)}");

            cs.setString(1, deleteNum);
            cs.registerOutParameter(2,java.sql.Types.INTEGER);

            cs.execute();
            int resultMsg = cs.getInt(2);

            if(resultMsg == 100){
                System.out.println("DB 삭제 실패");
            }else{
                fulltimeList.remove(fulltimeList.indexOf(
                        new FulltimeVO(deleteNum, null, 0, 0)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 정규직 직원 데이터의 월급 정보를 인상합니다.
     *
     * 저장 프로시저를 호출하여 모든 직원의 월급을 인상하고, 변경된 데이터를 데이터베이스에서 다시 읽어옵니다.
     * 월급 인상 실패 시 오류 메시지를 출력합니다.
     */
    @Override
    public void calcincreasesalary() {
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            cs = conn.prepareCall("{call FULLTIME_SALARY_RAISE(?)}");

            // out 파라미터에 저장된 프로시저의 수행결과에 대한 외부 변수 등록
            cs.registerOutParameter(1, java.sql.Types.INTEGER);

            cs.execute();

            int resultMsg = cs.getInt(1);
            if(resultMsg == 100){
                System.out.println("월급 인상 실패");
            }else{
                conn.commit(); // 인상된 월급 커밋
            }

            // 변경된 월급 정보를 조회하기 전, 기존 리스트 초기화
            fulltimeList.clear();

            // 변경된 월급 정보를 조회하기 위한 SELECT 쿼리 실행
            String sql = "SELECT name, empNo, result,basicSalary FROM Fulltime";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // 결과셋을 FulltimeVO 객체로 변환하여 리스트에 추가
            while (rs.next()) {
                FulltimeVO fulltime = new FulltimeVO();
                fulltime.setName(rs.getString("name"));
                fulltime.setEmpNo(rs.getString("empNo"));
                fulltime.setResult(rs.getInt("result"));
                fulltime.setBasicSalary(rs.getInt("basicSalary"));//
                fulltimeList.add(fulltime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    /**
     * 정규직 직원 데이터를 특정 조건에 따라 정렬하여 출력합니다.
     *
     * `sortNum`에 따라 이름순(1), 사번순(2), 실적순(3) 등의 정렬을 수행하며,
     * 정렬된 데이터는 단순 출력 목적으로 사용됩니다.
     *
     * @param sortNum 정렬 조건을 나타내는 번호
     */
    @Override
    public void totalSearch(int sortNum) {
        if(fulltimeList.size() == 0) this.connect();

        // 리스트의 내용을 sortNum 으로 정렬
        this.sort(sortNum);

        // 리스트 출력
        fulltimeList.forEach(System.out::println);
    }

    /**
     * 특정 정규직 직원 데이터를 검색하여 출력합니다.
     *
     * `fulltimeList`에 저장된 특정 사번을 가진 정규직 직원 데이터를 찾아 출력합니다.
     *
     * @param searchNum 검색할 정규직 직원의 사번
     */
    @Override
    public void search(String searchNum) {
        //fulltimeList가 비어있으면 DB에서 데이터 가져오기
        if (fulltimeList.size() == 0) this.connect();

        FulltimeVO temp = fulltimeList.get(fulltimeList.indexOf(
                new FulltimeVO(searchNum, null, 0, 0)
        ));

        System.out.println(temp);
    }

    /**
     * 정규직 직원 데이터를 정렬합니다.
     *
     * `sortNum` 변수에 따라 정규직 직원 데이터를 다양한 기준으로 정렬합니다:
     * - 1: 이름순
     * - 2: 사번순
     * - 3: 실적순 (내림차순)
     *
     * @param sortNum 정렬 조건을 나타내는 번호
     */
    @Override
    public void sort(int sortNum) {
        switch(sortNum){
            case 1:// 이름순
                fulltimeList.sort(Comparator.comparing(FulltimeVO::getName));
                break;

            case 2: // 사번순
                fulltimeList.sort( (o1, o2) -> o1.compareTo(o2));
                break;

            case 3: // 실적순
                fulltimeList.sort(Comparator.comparing(FulltimeVO::getResult).reversed());
                break;
        }
    }
}
