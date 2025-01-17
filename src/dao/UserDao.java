package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.User;

public class UserDao extends Dao {







	public User get(Integer UserID) throws Exception {
		// 教員インスタンスを初期化
		User user = new User();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from user where userID=?");
			// プリペアードステートメントにメールアドレスをバインド
			statement.setInt(1, UserID);
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 利用者インスタンスに検索結果をセット
				user.setEmailAddress(rSet.getString("EmailAddress"));
				user.setPassword(rSet.getString("password"));
				user.setUserID(rSet.getInt("userID"));

			} else {
				// リザルトセットが存在しない場合
				// 利用者インスタンスにnullをセット
				user = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return user;
	}

	public boolean save(Integer userID, String newPassword) throws Exception {
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    try {
	        // パスワードを更新するSQL文（テーブル名をuserに修正）
	        statement = connection.prepareStatement("UPDATE user SET password = ? WHERE userID = ?");
	        statement.setString(1, newPassword);  // 新しいパスワードをセット
	        statement.setInt(2, userID);  // userIDをセット

	        // クエリを実行し、更新が成功したかどうかを確認
	        int rowsAffected = statement.executeUpdate();

	        return rowsAffected > 0;  // 更新が成功した場合はtrueを返す
	    } catch (Exception e) {
	        throw e;  // エラーが発生した場合は例外をスロー
	    } finally {
	        // リソースを閉じる
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }
	}



    /**
     * loginメソッド メールアドレスとパスワードで認証する
     *
     * @return 認証成功:教員クラスのインスタンス, 認証失敗:null
     * @throws Exception
     */
	public User login(String emailAddress, String password) throws Exception {
	    User user = get(emailAddress); // メールアドレスからユーザー情報を取得

	    if (user == null) {
	        return null; // ユーザーが見つからない場合
	    }

	    // 入力されたパスワードをハッシュ化
	    String hashedPassword = hashPassword(password);

	    // ハッシュ化されたパスワードを比較
	    if (user.getPassword().equals(hashedPassword)) {
	        return user; // 認証成功
	    } else {
	        return null; // 認証失敗
	    }
	}

	// パスワードのハッシュ化
	public String hashPassword(String password) {
	    try {
	        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
	        byte[] hashedBytes = md.digest(password.getBytes());
	        StringBuilder sb = new StringBuilder();
	        for (byte b : hashedBytes) {
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();
	    } catch (Exception e) {
	        throw new RuntimeException("パスワードのハッシュ化に失敗しました。", e);
	    }
	}


    /**
     * メールアドレスで教員情報を取得するメソッド
     */
    public User get(String emailAddress) throws Exception {
        User user = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM user WHERE EmailAddress = ?");
            statement.setString(1, emailAddress);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setEmailAddress(resultSet.getString("EmailAddress"));
                user.setPassword(resultSet.getString("password"));
                user.setUserID(resultSet.getInt("userID"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return user;
    }

    /**
     * メールアドレスがすでに登録済みか確認するメソッド
     */
    public boolean isEmailExists(String emailAddress) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT COUNT(*) FROM user WHERE EmailAddress = ?");
            statement.setString(1, emailAddress);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true; // メールアドレスが存在する
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return false; // メールアドレスが存在しない
    }

    /**
     * 登録用トークンを保存するメソッド
     */
    public void saveToken(String tableName, String column1, String value1, String column2, String value2) throws Exception {
        String sql = String.format("INSERT INTO %s (%s, %s, created_at) VALUES (?, ?, NOW())", tableName, column1, column2);
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value1);
            ps.setString(2, value2);

            // デバッグ用ログ
            System.out.println("Executing SQL: " + sql);
            System.out.println("Value 1: " + value1);
            System.out.println("Value 2: " + value2);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("トークンの保存中にエラーが発生しました。");
        }
    }



    /**
     * トークンからメールアドレスを取得するメソッド
     */
    public String getEmailByToken(String token) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        String emailAddress = null;

        try {
            statement = connection.prepareStatement("SELECT EmailAddress FROM registration_tokens WHERE token = ?");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                emailAddress = resultSet.getString("EmailAddress");
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return emailAddress;
    }

    /**
     * トークンを削除するメソッド（任意：有効期限切れなどで使用）
     */
    public void deleteToken(String token) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("DELETE FROM registration_tokens WHERE token = ?");
            statement.setString(1, token);
            statement.executeUpdate();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * 認証コードの検証
     */
    public boolean isValidOTP(String emailAddress, String otp) throws Exception {
        String sql = "SELECT COUNT(*) FROM user_otp WHERE email_address = ? AND otp = ? AND expires_at > NOW()";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emailAddress);
            ps.setString(2, otp);

            // ログを追加
            System.out.println("Validating OTP: email = " + emailAddress + ", otp = " + otp);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Validation result: count = " + count);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("OTPの検証中にエラーが発生しました。");
        }
        return false;
    }



    public int getUserIdByEmail(String emailAddress) throws Exception {
        String sql = "SELECT UserID FROM User WHERE EmailAddress = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emailAddress);

            // デバッグログを追加
            System.out.println("Executing SQL: " + sql);
            System.out.println("EmailAddress: " + emailAddress);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("UserID");
                    System.out.println("Found UserID: " + userId);
                    return userId;
                } else {
                    System.out.println("No user found for EmailAddress: " + emailAddress);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ユーザーIDの取得に失敗しました。");
        }
        return -1; // ユーザーが見つからない場合
    }

    public void saveOTP(String emailAddress, String otp) throws Exception {
        String sql = "INSERT INTO user_otp (email_address, otp, expires_at) " +
                     "VALUES (?, ?, NOW() + INTERVAL 10 MINUTE) " +
                     "ON DUPLICATE KEY UPDATE otp = VALUES(otp), expires_at = VALUES(expires_at)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emailAddress);
            ps.setString(2, otp);

            // ログを追加
            System.out.println("Saving OTP: email = " + emailAddress + ", otp = " + otp);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("OTPの保存に失敗しました。");
        }
    }

    public void saveUser(String emailAddress, String password) throws Exception {
        String sql = "INSERT INTO user (EmailAddress, Password) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emailAddress);
            ps.setString(2, password);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ユーザーの保存に失敗しました。", e);
        }
    }




    public void EmailUpdateOTP(Integer userID, String emailAddress, String otp) throws Exception {

    	String sql = "INSERT INTO Email_otp (user_ID, email_address, otp, expires_at) " +
                "VALUES (?, ?, ?, NOW() + INTERVAL 10 MINUTE) " +
                "ON DUPLICATE KEY UPDATE otp = VALUES(otp), expires_at = VALUES(expires_at);";


        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setInt(1, userID);
            ps.setString(2, emailAddress);
            ps.setString(3, otp);

            // ログを追加
            System.out.println("Saving OTP: email = " + emailAddress + ", otp = " + otp);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("OTPの保存に失敗しました。");
        }
    }



    /**
     * 認証コードの検証
     */
    public boolean EmailOTP(Integer userID, String emailAddress, String otp) throws Exception {
    	 String sql = "SELECT COUNT(*) FROM Email_otp WHERE user_id = ? AND email_address = ? AND otp = ? AND expires_at > NOW()";        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

    		 ps.setInt(1, userID);
    		ps.setString(2, emailAddress);
            ps.setString(3, otp);



            // ログを追加
            System.out.println("Validating OTP: email = " + emailAddress + ", otp = " + otp);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Validation result: count = " + count);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("OTPの検証中にエラーが発生しました。");
        }
        return false;
    }



}