import React from 'react'
import {Button, Card} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css'
import axios from "axios";
import Swal from 'sweetalert2';
export function ForgetPassword() {
    const handlereset=()=>{
        let pass;
        let payload;
        if(document.getElementById("New").value === document.getElementById("Retypenew").value){
            const searchParams = new URLSearchParams(window.location.search);
            const hashedEmail = searchParams.get('email');
            console.log(hashedEmail);
            pass = document.getElementById("New").value;
            payload= {
                "email":hashedEmail,
                "password":pass
            }
        }
        else{
            Swal.fire("Please Retype the Password correctly")
        }

        axios.post("/api/users/update/password",payload)
            .then((resp)=>{
                if(resp.data==="Success"){
                    Swal.fire("Password has been Reset")
                }
                else{
                    Swal.fire("Error Occured")
                }
            })
            .catch((error) => {
                console.log(error);
                Swal.fire("Please Retype the Password correctly");
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
                    <Card.Title>Confirm Your Password</Card.Title>
                    <input type="password" id="New" placeholder="new Password" />
                    <br/>
                    <br/>
                    <input type="password" id="Retypenew" placeholder="Retype new Password" />
                    <br />
                    <br />
                    <Button variant="primary" onClick={handlereset}>Reset Password</Button>
                </Card.Body>
            </Card>
        </>
    )
}

