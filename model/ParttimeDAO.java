package model;

import util.DBUtil;
import vo.EmployeeVO;
import vo.ParttimeVO;
import vo.PersonVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

public class ParttimeDAO implements Parttime{
    private static ParttimeDAO dao;

    private ParttimeDAO() {}

    public static ParttimeDAO getInstance() {

        if(dao == null) dao = new ParttimeDAO();
        return dao;
    }

    private ArrayList<ParttimeVO> parttimeList = new ArrayList<>();
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

                // 임금 계산
                int wage = parttime.getHourWage() * parttime.getWorkHour();
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
                // 임금 계산
                int wage = parttime.getHourWage() * parttime.getWorkHour();
                parttime.setWage(wage);

                parttimeList.add(parttime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

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
                // 임금 계산
                int wage = parttime.getHourWage() * parttime.getWorkHour();
                parttime.setWage(wage);

                parttimeList.set(parttimeList.indexOf(parttime), parttime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

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

    @Override
    public void totalSearch(int sortNum) {
        //  parttimeList가 비어있으면 DB에서 데이터 읽어오기
        if (parttimeList.size() == 0) this.connect();


        // 리스트의 내용을 sortNum 으로 정렬
        switch(sortNum){
            case 1:
                this.sort(1); //이름순
                break;
            case 2:
                this.sort(2);// 사번순
                break;
            case 3:
                this.sort(3); // 노동시간순
                break;
        }

        // 리스트의 내용을 출력
        parttimeList.forEach(System.out::println);
    }

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
