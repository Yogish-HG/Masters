import React, { useEffect, useState } from 'react'
import{Navbar, Container, Nav, Card, Button} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css';
import './Dashboard.css';
import axios from'axios';
import groc from '../images/card.jpg'
import bg from '../images/background.jpg'
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';
export default function Dashboard() {
    const [items,setItems]=useState([]);
    const [userId, setUserId] = useState('');
    const navigate = useNavigate();
    const handleItems=(async()=>{
      
        let ord=[];
        let amount=0;
        let checker = 0;
        items.map((item)=>{
          checker = parseInt(document.getElementById(item.name).value);
          if(!checker == 0 || !checker === NaN){
            ord.push({
              "name":item.name,
              "quantity":String(parseInt(document.getElementById(item.name).value)),
              "metric": item.metric
            })
            amount=amount+parseInt(document.getElementById(item.name).value*item.price)
          }
        })
        
        ord.push({"total amount": String(amount)})
        console.log(ord)
        let payload={
          "email": userId,
          "orders":ord
        }
        if(amount !== 0){
          // let resp = await axios.post("https://b5nnj9oqef.execute-api.us-east-1.amazonaws.com/dev/order", payload)
        let resp = await axios.post(process.env.REACT_APP_API_URL+"/dev/order", payload)
        console.log(resp)
        // send post request to lambda which sends mail here
        if(resp.data === 'Orders added successfully'){
          // let resp2 = await axios.post("https://b5nnj9oqef.execute-api.us-east-1.amazonaws.com/dev/mail", payload)
          let resp2 = await axios.post(process.env.REACT_APP_API_URL+"/dev/mail", payload)
          console.log("resp frm mail");
          console.log(resp2);
          alert("Invoice has been sent to the respective mail")
          window.location.reload();
        }
        else{
          alert("Error while placing order")
        }
        }else{
          alert("No items added to the cart");
        }
        
        
    }
    )

    useEffect(() => {
      setUserId(Cookies.get('userId'));
      let pl = {
        "email": Cookies.get('userId')
      }
      // axios.post("https://b5nnj9oqef.execute-api.us-east-1.amazonaws.com/dev/getAllItems",pl)
      axios.post(process.env.REACT_APP_API_URL+"/dev/getAllItems", pl)
        .then((resp) => {
          console.log(resp)
          setItems(resp.data);
        })
        .catch((error) => {
            console.error("Error in async operation:", error);
          })
      
    }, []);
    
    return (
      <div style={{ backgroundImage: `url(${bg})`, backgroundSize: 'cover', minHeight: '100vh', width:'97rem' }}>
      <br/>
      <br />
      <div style={{justifyContent:"center",display:"flex"}}>
      <h3 style={{color:"white"}}>List of Items</h3>
      </div>
      <br />
      <br />
      <div>
      {items && items.map((item)=>
             <Card style={{ width: '18rem', float: 'left', margin: '5px' }}>
             {/* <Card.Img variant="top" src={food}/> */}
             <Card.Body>
             <Card.Img variant="top" src={groc}/>
               <Card.Title>{item.type} : {item.name}</Card.Title>
               <Card.Text>
               Price: {item.price} CAD/{item.metric}<br />
                Quantity : <input type="text" className="input-box" id={item.name}placeholder="Enter quantity..." />
               </Card.Text>
             </Card.Body>
           </Card>
      )}
      </div>
      <div style={{ clear: 'both' }}></div>
      <div style={{marginTop:'4rem', marginLeft:'46rem'}}>
      <Button className="centered-button" style={{marginRight:"100rem"}} variant="primary" onClick={handleItems}>
  Order
</Button>


     </div>
      </div>
    )
}
