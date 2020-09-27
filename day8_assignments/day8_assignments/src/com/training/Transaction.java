package com.training;

import java.sql.*;

import com.training.utils.ConnectionUtility;

public class Transaction {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection con=ConnectionUtility.getDerbyConnection();
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
		Savepoint save1=null;
		
		try {
			con.setAutoCommit(false);
			
			String sqlOne="insert into invoice values(?,?,?)";
			String sqlTwo="inseert into invoice values(?,?,?)";
			
			pstmt=con.prepareStatement(sqlOne);
			
			pstmt.setInt(1,100);
			pstmt.setString(2, "ram");
			pstmt.setDouble(3, 100);
			
			pstmt.executeUpdate();
			
			save1=con.setSavepoint("save1");
			
			pstmt2=con.prepareStatement(sqlTwo);
			
			pstmt.setInt(1,200);
			pstmt.setString(2, "sam");
			pstmt.setDouble(3, 200);
			
			pstmt2.executeUpdate();
			
			con.commit();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			try {
				con.rollback(save1);
				System.out.println("rollback");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}

}
