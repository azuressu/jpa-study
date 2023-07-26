package me.whitebear.jpastudy.jdbc.dao;

import me.whitebear.jpastudy.jdbc.vo.AccountVO;

import java.sql.*;

public class AccountDAO {
    // JDBC 관련 변수
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    // IntelliJ Database 에서도 조회
    private static final String url = "jdbc:postgresql://localhost:5432/messenger";
    private static final String username = "suyeon";
    private static final String password = "pass";

    // SQL 쿼리
    private final String ACCOUNT_INSERT = "INSERT INTO account(ID, USERNAME, PASSWORD) "
            + "VALUES((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), ?, ?)";
    private final String ACCOUNT_SELECT = "SELECT * FROM account WHERE ID = ?";

    // CRUD 기능 메소드
    public Integer insertAccount(AccountVO vo) {
        var id = -1;  // id 값을 받아오는 것
        try {
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.prepareStatement(ACCOUNT_INSERT);
            stmt.setString(1, vo.getUsername());
            stmt.setString(2, vo.getPassword());
            stmt.executeUpdate();

            // getGeneratedKeys(): key 값들을 가져옴
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public AccountVO selectAccount(Integer id) {
        AccountVO vo = null;

        try {
            String[] returnId = {"id"};
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.prepareStatement(ACCOUNT_SELECT, returnId); // 이 id로 넣겠다는 의미
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();

            if (rs.next()) { // 다음 값을 줘 라는 의미
                vo = new AccountVO();
                vo.setId(rs.getInt("ID"));
                vo.setUsername(rs.getString("USERNAME"));
                vo.setPassword(rs.getString("PASSWORD"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vo;
    }
}
