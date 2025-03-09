package model;

import util.DBUtil;
import vo.FulltimeVO;
import vo.ParttimeVO;
import vo.PersonVO;
import vo.StudentVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

public class StudentDAO implements Student {
    private static StudentDAO dao;



    private StudentDAO() {}

    public static StudentDAO getInstance() {
        if (dao == null) dao = new StudentDAO();

        return dao;
    }

    private ArrayList<StudentVO> studentlist = new ArrayList<>();
    private Connection conn;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    private CallableStatement cs;


    private void disConnect() {
        if (rs != null) try {rs.close();} catch (SQLException e) {}
        if (stmt != null) try {stmt.close();} catch (SQLException e) {}
        if (pstmt != null) try {pstmt.close();} catch (SQLException e) {}
        if (conn != null) try {conn.close();} catch (SQLException e) {}
    }

    private void connect() {
        try{
            conn = DBUtil.getConnection();
            String sql = "select * from Student ";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                // 임시 parttime 객체 생성
                StudentVO student = new StudentVO();

                // rs에서 임시 parttime 객체로 데이터 읽어오기
                student.setName(rs.getString("name"));
                student.setSno(rs.getString("sno"));
                student.setKorean(rs.getInt("korean"));
                student.setMath(rs.getInt("math"));
                student.setEnglish(rs.getInt("english"));
                student.setScience(rs.getInt("science"));

                // 총점, 평균, 학점 계산
                int total = student.getKorean() + student.getEnglish() + student.getMath() + student.getScience();
                student.setTotal(total);
                float average = total / 4.0f;
                student.setAverage(average);

                String grade;
                if (average >= 90) {
                    grade = "A";
                } else if (average >= 80) {
                    grade = "B";
                } else if (average >= 70) {
                    grade = "C";
                } else if (average >= 60) {
                    grade = "D";
                } else {
                    grade = "F";
                }
                student.setGrade(grade);


                // 임시 parttime 객체 리스트에 저장
                studentlist.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }

    @Override
    public void update(PersonVO personVO)  {

        StudentVO newStudent = (StudentVO) personVO;
        if(studentlist.size() == 0) this.connect();

        try {

            conn = DBUtil.getConnection();
            cs = conn.prepareCall("{call STUDENT_UPDATE(?,?,?,?,?,?,?)}");

            cs.setString(1, newStudent.getSno());
            cs.setString(2, newStudent.getName());
            cs.setInt(3, newStudent.getKorean());
            cs.setInt(4, newStudent.getEnglish());
            cs.setInt(5, newStudent.getMath());
            cs.setInt(6, newStudent.getScience());
            cs.execute();

            int rtn = cs.getInt(7);
            String resultString = null;
            if (rtn == 100) {
                System.out.println("DB 수정 실패");
            } else {
                // 총점, 평균, 학점 계산
                int total = newStudent.getKorean() + newStudent.getEnglish() + newStudent.getMath() + newStudent.getScience();
                newStudent.setTotal(total);
                float average = total / 4.0f;
                newStudent.setAverage(average);

                String grade;
                if (average >= 90) {
                    grade = "A";
                } else if (average >= 80) {
                    grade = "B";
                } else if (average >= 70) {
                    grade = "C";
                } else if (average >= 60) {
                    grade = "D";
                } else {
                    grade = "F";
                }
                newStudent.setGrade(grade);
                studentlist.set(studentlist.indexOf(newStudent),newStudent);
                System.out.println("DB 수정 성공");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            disConnect();
        }
    }

    @Override
    public void delete(String deleteNum) {
        if (studentlist.size() == 0) this.connect();
        try {

            conn = DBUtil.getConnection();
            cs = conn.prepareCall("{call STUDENT_DELETE(?,?)}");

            cs.setString(1, deleteNum);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();

            int rtn = cs.getInt(2);

            if (rtn == 100) {
                System.out.println("DB 삭제 실패");
            } else {
                studentlist.remove(studentlist.indexOf(new StudentVO(deleteNum,null,0,0,0,0)
                ));

                System.out.println("DB 삭제 성공");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            disConnect();
        }

    }

    @Override
    public void totalSearch(int sortNum) {
        if (studentlist.size() == 0) this.connect();

        switch(sortNum){
            case 1:
                this.sort(1);
                break;

                case 2:
                this.sort(2);
                break;

                case 3:
                    this.sort(3);
                    break;
        }

        studentlist.forEach(System.out::println);


    }

    @Override
    public void search(String searchNum) {

        if(studentlist.size() == 0) this.connect();

        StudentVO temp = studentlist.get(studentlist.indexOf(new StudentVO(searchNum,null,0,0,0,0)
        ));
        System.out.println(temp);


    }



    @Override
    public void sort(int sortNum) {

        switch(sortNum){
            case 1:
                studentlist.sort(Comparator.comparing(StudentVO ::getName));
                break;

            case 2:
                studentlist.sort((o1, o2) -> o1.compareTo(o2));
                break;

            case 3:
                studentlist.sort(Comparator.comparing(StudentVO ::getGrade));
                break;
        }

    }

    @Override
    public void input(PersonVO personVO)  {

        StudentVO newStudent = (StudentVO) personVO;

        //  studentList가 비어있으면 DB에서 데이터 읽어오기
        if (studentlist.size() == 0) {
            this.connect();
        }



        try {
            conn = DBUtil.getConnection();

            cs = conn.prepareCall("{call STUDENT_INSERT(?,?,?,?,?,?,?)}");

            cs.setString(1, newStudent.getSno());
            cs.setString(2, newStudent.getName());
            cs.setInt(3, newStudent.getKorean());
            cs.setInt(4, newStudent.getEnglish());
            cs.setInt(5, newStudent.getMath());
            cs.setInt(6, newStudent.getScience());

            cs.registerOutParameter(7, Types.INTEGER);
            cs.execute();

            int rtn = cs.getInt(7);
            String resultString = null;
            if (rtn == 100) {
                System.out.println("DB 입력 실패");
            } else {
                // 총점, 평균, 학점 계산
                int total = newStudent.getKorean() + newStudent.getEnglish() + newStudent.getMath() + newStudent.getScience();
                newStudent.setTotal(total);
                float average = total / 4.0f;
                newStudent.setAverage(average);

                String grade;
                if (average >= 90) {
                    grade = "A";
                } else if (average >= 80) {
                    grade = "B";
                } else if (average >= 70) {
                    grade = "C";
                } else if (average >= 60) {
                    grade = "D";
                } else {
                    grade = "F";
                }
                newStudent.setGrade(grade);
                studentlist.add(newStudent);
                System.out.println("DB 입력 성공");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            disConnect();
        }

    }
}

