import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import {w3cwebsocket as WebSocket} from "websocket";
import axios from "axios";
export const ChatModel = () => {
  const [message, setMessage] = useState('');
  const [messages,setMessages] = useState([]);
  const [receivedMessage, setReceivedMessage] = useState('');
  const [connection, setConnection] = useState(null);
  const [errMsg, setErrMsg] =useState ('');
  const [success, setSuccess] = useState(false);
  const {roomId}=useParams();

  useEffect(() => {
    const ws = new WebSocket('ws://localhost:8080/chat/'+roomId); // Replace with your WebSocket server URL
    setConnection(ws);

    ws.onopen = () => {
      console.log('WebSocket connection established.');
    };

    ws.onmessage = (data) => {
      console.log(data);
      console.log(typeof data);
      const newMessage = JSON.parse(data.data);
      setMessages((prevMessages)=>[...prevMessages,{message:newMessage.message,sender:false}]);
      console.log(newMessage)
    };

    ws.onclose = () => {
      console.log('WebSocket connection closed.');
    };

    return () => {
      ws.close(); // Close the WebSocket connection when the component unmounts
    };
  }, []);
  const onOpenHandler=async()=>{
    const response = await axios.get(`/api/chat/get/${Number(roomId)}`,{
      headers:{
        Authorization:`Bearer ${sessionStorage.getItem("token")}`
      }
    })
    console.log(response)
    await response.data.map(async(messageObj)=>{
      if(messageObj.user_id===Number(sessionStorage.getItem("user_id"))){
        setMessages((prevMessages)=>[...prevMessages,{message:messageObj.message,sender:true}]);
      } else{
        setMessages((prevMessages)=>[...prevMessages,{message:messageObj.message,sender:false}]);
      }
    })
  }
  useEffect(() => {
    // This effect will run whenever messages change (new messages are received)
    // Scroll to the bottom of the chat container to show the latest message
    const chatContainer = document.getElementById('chat-container');
    chatContainer.scrollTop = chatContainer.scrollHeight;
    onOpenHandler();
    
    
  }, []);
  const sendMessage = (event) => {
    event.preventDefault();
    if (message.trim() !== '') {
      console.log(message)
      // Send the message to the server
      const messageBody = {
        user_id:Number(sessionStorage.getItem("user_id")),
        message: message,
        room_id:Number(roomId),
      };
      const jsonData = JSON.stringify(messageBody);
      if(connection){
        connection.send(jsonData);
        setMessages((prevMessages)=>[...prevMessages,{message:messageBody.message,sender:true}]);
        setMessage('');
      }
      fetch("/api/chat/send",{
        method:"POST",
        headers:{
          "Content-Type":"application/json",
          Authorization:`Bearer ${sessionStorage.getItem("token")}`
        },
        body:jsonData
      })
      .then((data) => {
        console.log(data); // Log the response data
        if (data === "success") {
        setSuccess(true);
        } else {
        setErrMsg("Login failed. Please try again."); // Set an appropriate error message
        }
      })
      .catch((error) => {
        setErrMsg("An error occurred. Please try again."); // Set an appropriate error message
      });
    }
  }
    const handleKeyPress = (e) => {
      if (e.key === 'Enter') {
        sendMessage(e);
      }
    };
  return (
    <div>
      <div
        id="chat-container"
        style={{ minHeight: '200px', maxHeight: '400px', overflowY: 'scroll', border: '1px solid #ccc', padding: '10px' }}
      >
        {messages.map((msg, index) => (
          <div key={index} style={{ textAlign: msg.sender ? 'right' : 'left' }}>
            <span style={{ padding: '5px', borderRadius: '5px', background: msg.sender ? '#d3f5e2' : '#f0f0f0' }}>
              {msg.message}
            </span>
          </div>
        ))}
      </div>
      <input
        type="text"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        onKeyDown={handleKeyPress}
      />
      <button onClick={sendMessage}>Send</button>
    </div>
  );
  };

