package bean;
import java.sql.*;
public class LoginDao {

	public static int validate(LoginBean bean){
		boolean status = false;
		boolean is_root = false;
		int ret_val = -1;
		try{
			Connection con=ConnectionProvider.getCon();
			
			PreparedStatement ps=con.prepareStatement("select * from users where emailid=? and password=?");
			ps.setString(1,bean.getEmail());
			ps.setString(2, bean.getPass());

			String usernameValue = (String) bean.getEmail();
            String root = "root";
            if (usernameValue.equals(root))
				is_root = true;
				
			ResultSet rs=ps.executeQuery();
			status=rs.next();
			
		}catch(Exception e){}
		
		if (status == true)
		{
			if (is_root == false)
				ret_val = 1;
			else
				ret_val = 0; //root user
		}
		return ret_val;
	}
}
