package com.project.accomatch.Repository.Implementation;

import com.project.accomatch.Model.ChatMessageModel;
import com.project.accomatch.Repository.ChatOperationsInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class ChatOperations implements ChatOperationsInterface {
    @Value("${username.db.accomatch}")
    private String username;

    @Value("${password.db.accomatch}")
    private String password;

    @Value("${Connection.db.accomatch}")
    private String JDBC;
    public String createMessage(ChatMessageModel chatMessageModel){
        try{
            Connection connect;
            Statement statement;
            // Connect to the database.
            //getCredentials();
            connect = DriverManager.getConnection(JDBC, username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "INSERT INTO chat (room_id,user_id,message,time) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,chatMessageModel.getRoom_id());
            preparedStatement.setInt(2,chatMessageModel.getUser_id());
            preparedStatement.setString(3,chatMessageModel.getMessage());
            preparedStatement.setTimestamp(4,chatMessageModel.getTime());
            preparedStatement.executeUpdate();
            return "Success";
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public ArrayList<ChatMessageModel> getMessages(int room_id){
        try{
            Connection connect;
            Statement statement;
            // Connect to the database.
            //getCredentials();
            connect = DriverManager.getConnection(JDBC, username, password);
            // Create a statement object.
            statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM chat WHERE room_id = ? ORDER BY time ASC";
            PreparedStatement preparedStatement = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,room_id);
            preparedStatement.executeQuery();
            ResultSet resultSet= preparedStatement.getResultSet();
            ArrayList<ChatMessageModel> allMessages = new ArrayList<>();
            while (resultSet.next()){
                ChatMessageModel chatMessageModel= new ChatMessageModel(resultSet.getString("message"), resultSet.getInt("room_id"), resultSet.getInt("user_id"),resultSet.getTimestamp("time"));
                allMessages.add(chatMessageModel);
            }
            return allMessages;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
