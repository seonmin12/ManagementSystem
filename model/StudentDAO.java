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

    private void connect() {
        try {
            conn = DBUtil.getConnection();
            String sql = " select * from student ";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                StudentVO studentVO = new StudentVO();

                studentVO.setSno(rs.getString("sno"));
                studentVO.setName(rs.getString("name"));
                studentVO.setKorean(rs.getInt("korean"));
                studentVO.setEnglish(rs.getInt("english"));
                studentVO.setMath(rs.getInt("math"));
                studentVO.setScience(rs.getInt("science"));

                if (studentVO.getKorean() < 0) studentVO.setKorean(0);
                else if (studentVO.getKorean() > 100) studentVO.setKorean(100);
                if (studentVO.getEnglish() < 0) studentVO.setEnglish(0);
                else if (studentVO.getEnglish() > 100) studentVO.setEnglish(100);
                if (studentVO.getMath() < 0) studentVO.setMath(0);
                else if (studentVO.getMath() > 100) studentVO.setMath(100);
                if (studentVO.getScience() < 0) studentVO.setScience(0);
                else if (studentVO.getScience() > 100) studentVO.setScience(100);

                // 합계, 평균, 등급 계산
                this.total(studentVO);
                this.average(studentVO);
                this.grade(studentVO);

                // 리스트에 추가
                studentlist.add(studentVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }


    @Override
    public void input(PersonVO personVO) {

        StudentVO newStudent = (StudentVO) personVO;
        if(studentlist.size() == 0) this.connect();

        //  studentlist가 비어있으면 DB에서 데이터 읽어오기
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

            int resultMsg = cs.getInt(7);
            if (resultMsg == 100) {
                System.out.println("디비 입력 실패");
            } else {
                if (newStudent.getKorean() < 0) newStudent.setKorean(0);
                else if (newStudent.getKorean() > 100) newStudent.setKorean(100);
                if (newStudent.getEnglish() < 0) newStudent.setEnglish(0);
                else if (newStudent.getEnglish() > 100) newStudent.setEnglish(100);
                if (newStudent.getMath() < 0) newStudent.setMath(0);
                else if (newStudent.getMath() > 100) newStudent.setMath(100);
                if (newStudent.getScience() < 0) newStudent.setScience(0);
                else if (newStudent.getScience() > 100) newStudent.setScience(100);

                // 합계, 평균, 등급 계산
                this.total(newStudent);
                this.average(newStudent);
                this.grade(newStudent);

                // 리스트에 추가
                studentlist.add(newStudent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }

    @Override
    public void update(PersonVO personVO) {

        StudentVO newStudent = (StudentVO) personVO;

        //  studentlist가 비어있으면 DB에서 데이터 읽어오기
        if (studentlist.size() == 0) {
            this.connect();
        }

        try {

            conn = DBUtil.getConnection();

            cs = conn.prepareCall("{call STUDENT_UPDATE(?,?,?,?,?,?,?)}");

            cs.setString(1, newStudent.getSno());
            cs.setString(2, newStudent.getName());
            cs.setInt(3, newStudent.getKorean());
            cs.setInt(4, newStudent.getEnglish());
            cs.setInt(5, newStudent.getMath());
            cs.setInt(6, newStudent.getScience());

            cs.registerOutParameter(7, Types.INTEGER);
            cs.execute();

            int resultMsg = cs.getInt(7);
            if (resultMsg == 100) {
                System.out.println("디비 수정 실패");
            } else {
                if (newStudent.getKorean() < 0) newStudent.setKorean(0);
                else if (newStudent.getKorean() > 100) newStudent.setKorean(100);
                if (newStudent.getEnglish() < 0) newStudent.setEnglish(0);
                else if (newStudent.getEnglish() > 100) newStudent.setEnglish(100);
                if (newStudent.getMath() < 0) newStudent.setMath(0);
                else if (newStudent.getMath() > 100) newStudent.setMath(100);
                if (newStudent.getScience() < 0) newStudent.setScience(0);
                else if (newStudent.getScience() > 100) newStudent.setScience(100);

                // 합계, 평균, 등급 계산
                this.total(newStudent);
                this.average(newStudent);
                this.grade(newStudent);

                // 리스트에서 수정
                studentlist.set(studentlist.indexOf(newStudent), newStudent);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }

    @Override
    public void delete(String deleteNum) {
        //  studentlist가 비어있으면 DB에서 데이터 읽어오기
        if (studentlist.size() == 0) {
            this.connect();
        }

        try {
            conn = DBUtil.getConnection();

            cs = conn.prepareCall("{call STUDENT_DELETE(?,?)}");

            cs.setString(1, deleteNum);

            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();

            int resultMsg = cs.getInt(2);
            if (resultMsg == 100) {
                System.out.println("디비 삭제 실패");
            } else {
                studentlist.remove(studentlist.indexOf(
                        new StudentVO(deleteNum, null, 0, 0, 0, 0)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }

        studentlist.forEach(System.out::println);


    }

    public void totalSearch(int sortNum) {
        //  studentlist가 비어있으면 DB에서 데이터 읽어오기
        if (studentlist.size() == 0) {
            this.connect();
        }

        // 리스트의 내용을 sortNum 으로 정렬
        this.sort(sortNum);
      
        // 리스트의 내용을 출력
        studentlist.forEach(System.out::println);
    }

    @Override
    public void search(String searchNum) {
        //  studentlist가 비어있으면 DB에서 데이터 읽어오기
        if (studentlist.size() == 0) {
            this.connect();
        }

        StudentVO temp = studentlist.get(studentlist.indexOf(
                new StudentVO(searchNum, null, 0, 0, 0, 0)
        ));
        System.out.println(temp);
    }


    @Override
    public void sort(int sortNum) {
        switch (sortNum) {
            case 1: // 이름순
                studentlist.sort(Comparator.comparing(StudentVO::getName));
                break;
            case 2: // 사번순
                studentlist.sort((o1, o2) -> o1.compareTo(o2));
                break;
            case 3: // 일한시간순
                studentlist.sort(Comparator.comparing(StudentVO::getTotal).reversed());
                break;
            case 4:
                break;
        }
    }

    public void total(StudentVO studentVO){
        int total = studentVO.getKorean()
                    + studentVO.getEnglish()
                    + studentVO.getMath()
                    + studentVO.getScience();
        studentVO.setTotal(total);
    }

    @Override
    public void average(StudentVO studentVO){
        float average = studentVO.getTotal() / 4.0f;
        studentVO.setAverage(average);
    }

    @Override
    public void grade(StudentVO studentVO){
        String grade;
        if (studentVO.getAverage() >= 90) {
            grade = "A";
        } else if (studentVO.getAverage() >= 80) {
            grade = "B";
        } else if (studentVO.getAverage() >= 70) {
            grade = "C";
        } else if (studentVO.getAverage() >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }
        studentVO.setGrade(grade);
    }
}