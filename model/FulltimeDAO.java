package model;

import util.DBUtil;
import vo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

public class FulltimeDAO implements Fulltime  {

    private static FulltimeDAO dao;

    private FulltimeDAO(){ }

    public static FulltimeDAO getInstance(){
        if(dao==null) dao = new FulltimeDAO();

        return dao;
    }

    private ArrayList<FulltimeVO> fulltimeList = new ArrayList<>();
    private Connection conn;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    private CallableStatement cs;

    private void disconnect(){
        if (rs != null) try {rs.close();} catch (SQLException e) {}
        if (stmt != null) try {stmt.close();} catch (SQLException e) {}
        if (pstmt != null) try {pstmt.close();} catch (SQLException e) {}
        if (conn != null) try {conn.close();} catch (SQLException e) {}
    }

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

    @Override
    public void totalSearch(int sortNum) {
        if(fulltimeList.size() == 0) this.connect();

        // 리스트의 내용을 sortNum 으로 정렬
        this.sort(sortNum);

        // 리스트 출력
        fulltimeList.forEach(System.out::println);
    }


    @Override
    public void search(String searchNum) {
        //fulltimeList가 비어있으면 DB에서 데이터 가져오기
        if (fulltimeList.size() == 0) this.connect();

        FulltimeVO temp = fulltimeList.get(fulltimeList.indexOf(
                new FulltimeVO(searchNum, null, 0, 0)
        ));

        System.out.println(temp);
    }

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
    };
}
