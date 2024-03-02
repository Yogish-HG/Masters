import React from 'react'
import {Button, Card} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css'
import axios from "axios";
import Swal from 'sweetalert2';
export function ForgetPasswordEmail() {
    const handlereset=()=>{
        const payload= {
           "email":document.getElementById("mail").value
        }
        axios.post("/api/users/forgot/password",payload)
            .then((resp)=>{
                if(resp.data==="Mail Sent"){
                    Swal.fire("Recovery mail has been sent check your Email")
                }
                else{
                    Swal.fire("Specified mail does not exist in our database")
                }
            })
            .catch((error) => {
                console.log(error);
                Swal.fire("Error while sending mail: ", error);
              });
        };


    return (
         <>
             <br />
             <br />
             <br />
             <br />
             <Card className="text-center">
                 <Card.Body>
                     <Card.Title>Forgot Password</Card.Title>
                     <Card.Text>
                         Please enter your registered Email ID
                     </Card.Text>
                     <input type={Text} id="mail" placeholder="Example@gmail.com" />
                     <br />
                     <br />
                     <Button variant="primary" onClick={handlereset}>Send reset link</Button>
                 </Card.Body>
             </Card>
         </>
    )
}
