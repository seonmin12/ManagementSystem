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

    private static ParttimeDAO getInstance() {

        if(dao == null) dao = new ParttimeDAO();
        return dao;
    }

    private ArrayList<ParttimeVO> parttimeList;
    private Connection conn;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    // 사용한 자원 반납하는 메소드
    private void disconnect(){
        if (rs != null) try {rs.close();} catch (SQLException e) {}
        if (stmt != null) try {stmt.close();} catch (SQLException e) {}
        if (pstmt != null) try {pstmt.close();} catch (SQLException e) {}
        if (conn != null) try {conn.close();} catch (SQLException e) {}


    }


    @Override
    public void input(EmployeeVO newEmployee) {
        ParttimeVO parttime = new ParttimeVO();
        if(newEmployee instanceof ParttimeVO){
            parttime = (ParttimeVO) newEmployee;

        }else {
            System.out.println("업데이트 할 직원은 파트타임이 아닙니다.");
            return;

        }
        try {


            conn = DBUtil.getConnection();
            String sql = "insert into Parttime (name,empNo,wage) values (?,?,?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1,newEmployee.getName());
            pstmt.setString(2, newEmployee.getEmpNo());
            pstmt.setInt(3,parttime.getWage());


            pstmt.executeUpdate();
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
            String sql = "update Parttime set "
                    + "name = ?, "
                    + "hourWage =?, "
                    + "workHour = ?, "
                    + "where empNo = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newEmployee.getName());
            pstmt.setInt(2,parttime.getHourWage());
            pstmt.setInt(3,parttime.getWorkHour());
            pstmt.setString(4, newEmployee.getEmpNo());

            pstmt.executeUpdate();
            parttimeList.add((ParttimeVO) newEmployee);
            parttime.setWage(parttime.getWorkHour() * parttime.getHourWage());

            for(ParttimeVO parttimeVO : parttimeList){
                System.out.println(newEmployee);
            }




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
            String sql = "delete from Parttime where empNo = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deleteNum);

            pstmt.executeUpdate();
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
                parttimeVO.setName(rs.getString("name"));
                parttimeVO.setEmpNo(rs.getString("empNo"));
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
