package com.internousdev.template.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.internousdev.template.dto.LoginDTO;
import com.internousdev.template.util.DBConnector;

public class LoginDAO {
public LoginDTO getLoginUserInfo(String loginUserId, String loginPassword){
	DBConnector dbConnector=new DBConnector();
	Connection connection =dbConnector.getConnection();
	LoginDTO loginDTO=new LoginDTO();
	String sql="SELECT * FROM login_user_transaction WHERE login_id=? AND login_pass=?";

	try{
		/*セキュリティの関係で名前をuniqueにする必要がある？*/
		PreparedStatement preparedStatement =connection.prepareStatement(sql);
		preparedStatement.setString(1, loginUserId);
		preparedStatement.setString(2, loginPassword);

		ResultSet resultSet=preparedStatement.executeQuery();

		if(resultSet.next()){
			loginDTO.setLoginId(resultSet.getString("login_id"));
			loginDTO.setLoginPassword(resultSet.getString("login_pass"));
			loginDTO.setUserName(resultSet.getString("user_name"));

			/*login_idがnullじゃなかった場合Login_flgが立つ
			 * !=正しければfalse違えばtrue*/
			if(resultSet.getString("login_id") !=null){
				loginDTO.setLoginFlg(true);
			}
		}
		/*代表的なエラーが対象のファイルを開いたのに
		対象のファイルが存在しなかった場合にエラーとしてくれる*/
		}catch (Exception e){
			e.printStackTrace();
		}
		return loginDTO;
	}
}
