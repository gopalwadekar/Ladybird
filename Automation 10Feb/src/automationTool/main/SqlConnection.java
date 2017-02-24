package automationTool.main;

import java.sql.*;

public class SqlConnection {

	public static String connString = null;
	public static String sqlQuery = null;
	public static Connection con = null;
	public static Statement sqlStmt = null;
	public static ResultSet sqlRslt = null;
	public static ResultSetMetaData sqlRsltMtaDta = null;
	public static PreparedStatement prpStmt = null;
	public static String tableName = "[dbo].[Test_Result]";

//	public static String insertTableQuery = "INSERT INTO "
//			+ "[prepord_mobileservice].Test_Result" + " (" + "[execution_id],"
//			+ "[module]," + "[sub_module]," + "[test_scenario],"
//			+ "[test_case_id]," + "[test_case]," + "[test_data_id],"
//			+ "[test_data]," + "[expected_result]," + "[actual_result],"
//			+ "[test_result]," + "[execution_time]"
//			+ ")VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
	
	
	public static String insertTableQuery = "INSERT INTO "
			+ "[dbo].[Test_Result]" + " (" + "[execution_id],"
			+ "[module]," + "[sub_module]," + "[test_scenario],"
			+ "[test_case_id]," + "[test_case]," + "[test_data_id],"
			+ "[test_data]," + "[expected_result]," + "[actual_result],"
			+ "[test_result]," + "[execution_time]"
			+ ")VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";

	public static String insertTestResultsInTable(String[] testResuts,
			String ActualResult, String Result, String ExepectedResult,
			String exeTme) {
		String sts = "";
		try {

			if (Constants.rootDB_InsFlag.equals("YES")) {

				if (!connString.isEmpty()) {

					con = DriverManager.getConnection(connString);
					if (con != null) {

						if (!insertTableQuery.isEmpty()) {
							prpStmt = con.prepareStatement(insertTableQuery);

							if (prpStmt != null || testResuts != null) {

								prpStmt.setString(1, Utilities.ExecutionId);
								prpStmt.setString(2, testResuts[1]);
								prpStmt.setString(3, testResuts[2]);
								prpStmt.setString(4, testResuts[3]);
								prpStmt.setString(5, testResuts[4]);
								prpStmt.setString(6, testResuts[5]);
								prpStmt.setString(7, testResuts[6]);
								prpStmt.setString(8, testResuts[7]);
								prpStmt.setString(9, ExepectedResult);
								prpStmt.setString(10, ActualResult);
								prpStmt.setString(11, Result);
								prpStmt.setString(12, exeTme);

								boolean d = prpStmt.execute();

								if (!d) {
									sts = "[ INSERTED ]";
								} else {
									sts = "[ NOT INSERTED ]";
								}

							}

						}

					}

				}

			} else if (Constants.rootDB_InsFlag.equals("NO")
					|| Constants.rootDB_InsFlag.isEmpty()) {

				sts = "[ NOT SET ]";
			}

		} catch (Exception e) 
		{
			
			sts = "[ EXCEPTION ]";
		} finally {
			if (con != null) {

				try {
					con.close();
				} catch (Exception e) {
				}

			}
		}

		return sts;

	}

	public static String getScriptsResultsCount(String id) {
		int count = 0;
		String q1, q2, q3, q4, rslt = null;
		int[] cnts = new int[4];
		String query[] = null;
		
		try {
			if (Constants.rootDB_InsFlag.equals("YES")) {

				System.out.println("..............................");
				System.out.println("Fetching Scripts Counts From Database");

				q1 = "select * from [dbo].[Test_Result] where execution_id='"
						+ id + "'";
				q2 = "select * from [dbo].[Test_Result]   where execution_id='"
						+ id + "' AND test_result='PASSED'";
				q3 = "select * from [dbo].[Test_Result]  where execution_id='"
						+ id + "' AND test_result='FAILED'";
				// q4 =
				// "select * from [prepord_mobileservice].[Test_Result]  where execution_id='"
				// + id + "' AND test_result='SKIPPED'";

				query = new String[] { q1, q2, q3 };

				if (!connString.isEmpty()) {

					con = DriverManager.getConnection(connString);
					if (con != null) {
						for (int i = 0; i < query.length; i++) {

							ResultSet rs = executeSqlStatementToGetTableValues(query[i]);
							count = 0;

							if (rs == null) {
								cnts[i] = 0;
							} else if (rs != null) {
								while (rs.next()) {
									count++;
								}
								cnts[i] = count;

							}

						}

					}
				}

				rslt = "\n________________________________________________________\n";
				rslt += "\nNo. Of Scripts Cases RUN   :  " + cnts[0];
				rslt += "\nNo. Of Scripts Cases PASS  :  " + cnts[1];
				rslt += "\nNo. Of Scripts Cases FAIL  :  " + cnts[2];
				// rslt += "\nNo. Of Scripts Cases SKIPPED     :  " + cnts[3];

			}

			else {
				System.out.println("..............................");

				System.out.println("Test Cases is Not Set in Databases");
			}
		} catch (Exception e) {
			rslt += "\n Some Exception Found to Find Srcipts Count";
			System.out.println("Exception in getResultsCount");
		} finally {

			if (con != null) {

				try {
					con.close();
				} catch (Exception e) {
				}

			}

		}

		rslt += "\n________________________________________________________\n";
		System.out.println("..............................");
		return rslt;
	}

	public static ResultSet executeSqlStatementToGetTableValues(String sqlQuery) {
		ResultSet rs = null;
		try {
			sqlStmt = con.createStatement();
			if (sqlStmt != null) {
				rs = sqlStmt.executeQuery(sqlQuery);
				System.out
						.println("SQL Statement created and executed to  GetTableValues");
			} else {
				System.out.println("SQL Statement Not Found to GetTableValues");
			}
		} catch (Exception e) {
			System.out
					.println("SQL Statement Failed to execute to GetTableValues");
		}
		return rs;
	}

}
