package com.minis.jdbc.core;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class JdbcTemplate {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public JdbcTemplate() {
    }

    public Object query(StatementCallback stmtcallback) {
        Connection con = null;
        Statement stmt = null;

        try {
            con = dataSource.getConnection();

            stmt = con.createStatement();

            return stmtcallback.doInStatement(stmt);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
                con.close();
            } catch (Exception e) {

            }
        }

        return null;

    }
    public Object query(String sql, Object[] args, PreparedStatementCallback pstmtcallback) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = dataSource.getConnection();

            pstmt = con.prepareStatement(sql);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (arg instanceof String) {
                        pstmt.setString(i+1, (String)arg);
                    }
                    else if (arg instanceof Integer) {
                        pstmt.setInt(i+1, (int)arg);
                    }
                    else if (arg instanceof java.util.Date) {
                        pstmt.setDate(i+1, new java.sql.Date(((java.util.Date)arg).getTime()));

                    }
                }
            }

            return pstmtcallback.doInPreparedStatement(pstmt);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt.close();
                con.close();
            } catch (Exception e) {

            }
        }

        return null;

    }
    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
        RowMapperResultSetExtractor<T> resultExtractor = new RowMapperResultSetExtractor<>(rowMapper);
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //建立数据库连接
            con = dataSource.getConnection();
            //准备SQL命令语句
            pstmt = con.prepareStatement(sql);
            //设置参数
            ArgumentPreparedStatementSetter argumentSetter = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(pstmt);
            //执行语句
            rs = pstmt.executeQuery();
            //数据库结果集映射为对象列表，返回
            return resultExtractor.extractData(rs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt.close();
                con.close();
            } catch (Exception e) {
            }
        }
        return null;
    }
}
