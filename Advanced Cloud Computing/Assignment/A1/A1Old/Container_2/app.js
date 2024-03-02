const express = require('express');
const csv=require('csv-parser');
const cors= require('cors');
const app=express();
const fs= require('fs');
const { count } = require('console');
app.use(express.json());
const corsOptions = {
    origin: 'http://service1:6000',
    methods: ['GET', 'POST'],
    allowedHeaders: ['Content-Type', 'Authorization'],
  };
app.use(cors(corsOptions));
const port=process.env.port||3300
app.post('/calculate',(req,res)=>{
let sum=0;    
let flag=0;
fs.createReadStream('/app/data/'+req.body.file)
.pipe(csv())
.on('data',(row)=>{
    if(typeof(row.product)!=='string'||Object.is(parseInt(row.amount),NaN)||Object.keys(row).length!=2){
        console.log(row.trim())
        sum="Input file not in CSV format."
        flag=1
        return
    }
    if(row.product===req.body.product && flag!=1){
        
       sum=sum+parseInt(row.amount);
    }    
}
)
.on('error',()=>{
    let callback="Input file not in CSV format";
    res.send(callback);
})
.on('end',()=>res.json(sum))

}
);
app.listen(port,()=>console.log('Container2 Active'));
// const express = require('express');
// const cors = require('cors');

// const app = express();
// app.use(cors());
// app.use(express.json());

// const port = process.env.PORT || 3300;

// app.post('/enroll', (req, res) => {
//   console.log(req.body.name);
//   // Add appropriate response
//   res.sendStatus(200); // Sending a successful response status code (e.g., 200)
// });

// app.get('/', (req, res) => {
//   res.send('hi');
// });

// app.listen(port, () => console.log('Backend active'));
