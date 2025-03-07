package model;

import util.DBUtil;
import vo.EmployeeVO;
import vo.FulltimeVO;
import vo.ParttimeVO;

import java.sql.*;
import java.util.ArrayList;

public class ParttimeDAO implements Parttime{


    private static ParttimeDAO dao;

    private ParttimeDAO() {}

    public static ParttimeDAO getInstance() {

        if(dao == null) dao = new ParttimeDAO();
        return dao;
    }

    private ArrayList<ParttimeVO> parttimeList;
    private Connection conn;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    private CallableStatement cs;

    // 사용한 자원 반납하는 메소드
    private void disconnect(){
        if (rs != null) try {rs.close();} catch (SQLException e) {}
        if (stmt != null) try {stmt.close();} catch (SQLException e) {}
        if (pstmt != null) try {pstmt.close();} catch (SQLException e) {}
        if (conn != null) try {conn.close();} catch (SQLException e) {}


    }


    @Override
    public void input(EmployeeVO newEmployee) {

        ParttimeVO parttime = null;
        if(newEmployee instanceof ParttimeVO){
            parttime = (ParttimeVO) newEmployee;

        }else {
            System.out.println("업데이트 할 직원은 파트타임이 아닙니다.");
            return;

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

            boolean flag = cs.execute();
            System.out.println(flag);

            int resultMsg = cs.getInt(5);
            System.out.println(resultMsg);




        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

    }

    @Override
    public void update(EmployeeVO newEmployee) {
        ParttimeVO parttime = null;
        if(newEmployee instanceof ParttimeVO){
            parttime = (ParttimeVO) newEmployee;
        } else {
            System.out.println("업데이트 할 직원은 파트타임이 아닙니다.");
            return;
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
            boolean flag = cs.execute();
            System.out.println(flag);

            int resultMsg = cs.getInt(5);
            System.out.println(resultMsg);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

    }



    @Override
    public void delete(String deleteNum) {
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            cs = conn.prepareCall("{call PARTTIME_DELETE(?,?)}");

            cs.setString(1, deleteNum);
            cs.registerOutParameter(2,java.sql.Types.INTEGER);

            boolean flag = cs.execute();
            System.out.println(flag);

            String resultMsg = cs.getString(2);
            System.out.println(resultMsg);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void totalSearch() {
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from Parttime ";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            parttimeList = new ArrayList<>();

            //  fulltimeList가 null이면 새로 생성, 이미 있으면 초기화
            if (parttimeList == null) {
                parttimeList = new ArrayList<>();
            } else {
                parttimeList.clear(); // 기존 데이터 유지하면서 내용만 삭제
            }

            while(rs.next()){
                ParttimeVO parttime = new ParttimeVO();
                parttime.setName(rs.getString("name"));
                parttime.setEmpNo(rs.getString("empNo"));
                parttime.setHourWage(rs.getInt("hourWage"));
                parttime.setWorkHour(rs.getInt("workHour"));
                parttimeList.add(parttime);

                int wage = parttime.getHourWage() * parttime.getWorkHour();
                parttime.setWage(wage);


            }


            // 출력
            for (ParttimeVO parttimeVO : parttimeList) {
                System.out.println(parttimeVO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }


    }

    @Override
    public void search(String searchNum) {
        try {
            parttimeList = new ArrayList<>();
            conn = DBUtil.getConnection();
            String sql = "select * from Parttime where empNo = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, searchNum);

            rs = pstmt.executeQuery();


            while(rs.next()){
                ParttimeVO parttimeVO = new ParttimeVO();
                parttimeVO.setEmpNo(rs.getString("empNo"));
                parttimeVO.setName(rs.getString("name"));
                parttimeVO.setHourWage(rs.getInt("hourWage"));
                parttimeVO.setWorkHour(rs.getInt("workHour"));


                parttimeList.add(parttimeVO);

                System.out.println("검색된 직원 정보: " + parttimeVO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

    }

    @Override
    public void sort(int sortNum) {
        try {
            conn = DBUtil.getConnection();
            String sql = "";
            switch (sortNum) {
                case 1: sql = "select * from Parttime order by empNo ";
                    break;
                case 2: sql = "select * from Parttime order by name  ";
                    break;
                case 3: sql = "select * from Parttime order by workHour desc  ";
                    break;
                default:
                    System.out.println("잘못 입력했습니다.");
                    return;
            }
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // fulltimeList가 null이면 초기화
            if (parttimeList == null) {
                parttimeList = new ArrayList<>();
            } else {
                parttimeList.clear(); // 기존 리스트 초기화
            }



            while(rs.next()){
                ParttimeVO parttimeVO = new ParttimeVO();
                parttimeVO.setName(rs.getString("name"));
                parttimeVO.setEmpNo(rs.getString("empNo"));
                parttimeVO.setHourWage(rs.getInt("hourWage"));
                parttimeVO.setWorkHour(rs.getInt("workHour"));


                parttimeList.add(parttimeVO); // DB에서 모든 데이터를 fulltimelist에 먼저 추가 해야함


            }


            // 계산된 전체 리스트 출력
            for (ParttimeVO parttimeVO : parttimeList) {
                System.out.println(parttimeVO);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

    }
}
