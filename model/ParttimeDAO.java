package model;

import util.DBUtil;
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
    public void input(ParttimeVO newParttime) {
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into Fulltime (name,empNo,wage) values (?,?,?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1,newParttime.getName());
            pstmt.setString(2, newParttime.getEmpNo());
            pstmt.setInt(3,newParttime.getWage());


            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

    }

    @Override
    public void update(ParttimeVO newParttime) {
        try {
            conn = DBUtil.getConnection();
            String sql = "update Parttime set "
                    + "name = ?, "
                    + "hourWage =?, "
                    + "workHour = ?, "
                    + "where empNo = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newParttime.getName());
            pstmt.setInt(2,newParttime.getHourWage());
            pstmt.setInt(3,newParttime.getWorkHour());
            pstmt.setString(4, newParttime.getEmpNo());

            pstmt.executeUpdate();
            parttimeList.add(newParttime);
            newParttime.setWage(newParttime.getWorkHour() * newParttime.getHourWage());




        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }


    }

    @Override
    public void calcWage() {

    }

    @Override
    public void delete(int deleteNum) {

    }

    @Override
    public void totalSearch() {

    }

    @Override
    public void search(int searchNum) {

    }

    @Override
    public void sort(int sortNum) {

    }
}
