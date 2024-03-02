package com.project.accomatch.JWT;

import com.project.accomatch.Model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class CustomUserDetailsService implements UserDetailsService {

    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;


    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            Connection connect;
            Statement statement;
            ResultSet rs;
            Map<String, String> additionalData = new HashMap<>();
            // Connect to the database.
            connect = DriverManager.getConnection(JDBC, username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //statement.execute("use accomatch;");
            rs = statement.executeQuery("SELECT user_id, email, `name`, password, age, gender, mobile, address, is_admin, is_leaseholder, createdAt, updatedAt FROM user " +
                    "where email = '"+email+"';");
            UserModel userModel = new UserModel();
            if(rs.next()){
                userModel.setUserID(rs.getInt(1));
                userModel.setEmail(rs.getString(2));
                userModel.setName(rs.getString(3));
                userModel.setPassword(rs.getString(4));
                userModel.setAge(rs.getInt(5));
                userModel.setGender(rs.getString(6));
                userModel.setMobile(rs.getString(7));
                userModel.setAddress(rs.getString(8));
                userModel.setIs_admin(rs.getInt(9));
                userModel.setIs_leaseholder(rs.getInt(10));
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();



                additionalData.put("Email", userModel.getEmail());
                additionalData.put("User_id", String.valueOf(userModel.getUserID()));
                additionalData.put("Name", userModel.getName());

                int admin = userModel.getIs_admin();
                int leaseHolder = userModel.getIs_leaseholder();
                if (admin == 0 && leaseHolder == 0) {
                    additionalData.put("Type", "AP");
                    authorities.add(new SimpleGrantedAuthority("AP"));
                } else if (admin == 1 && leaseHolder == 0) {
                    additionalData.put("Type", "AD");
                    authorities.add(new SimpleGrantedAuthority("AD"));
                } else if (admin == 0 && leaseHolder == 1) {
                    additionalData.put("Type", "LH");
                    authorities.add(new SimpleGrantedAuthority("LH"));
                } else {
                    additionalData.put("Type", "AP");
                    authorities.add(new SimpleGrantedAuthority("AP"));
                }

                additionalData.put("Status", "Success");

                // Custom UserDetails implementation that extends the User class
                return new CustomUserDetails(
                        userModel.getEmail(),
                        userModel.getPassword(),
                        additionalData,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
            }
            else{
                statement.close();
                connect.close();
                return new CustomUserDetails(null,null,null,null);
            }
        }catch(Exception e){
            return new CustomUserDetails(null,null,null,null);
        }
    }
}
