const express= require('express');
const axios = require('axios');
const cors=require('cors');
const fs=require('fs');
const { type } = require('os');
const app=express();
app.use(express.json());
const corsOptions = {
    origin: 'http://service2:3300',
    methods: ['GET', 'POST'],
    allowedHeaders: ['Content-Type', 'Authorization'],
  };
app.use(cors(corsOptions));
const port= process.env.port||6000
app.post('/calculate',(req,res)=>   {
if(req.body.file===null||!req.body.file){
    const err={
        "file":null,
        "error":"Invalid JSON input."
    }
    res.json(err)
}
else{
    const filepath='/app/data/'+req.body.file;
    fs.readFile(filepath, 'utf8', (err, data) => {
        if (err) {
            const msg={
                "file":req.body.file,
                "error":"File not found."
            }
            res.json(msg)
        }
        else{
            axios.post('http://service2:3300/calculate',req.body)
            .then((resp)=>{
                if(Object.is(parseInt(resp.data),NaN)){
                const response={
                    "file":req.body.file,
                    "error":resp.data
                }
                res.json(response)
               }
               else{
                const response={
                    "file":req.body.file,
                    "sum":resp.data
                }
                res.json(response)
               }
                
            }
                )

        }
    });
}  
});
app.post('/store-file',(req,resp)=>{
    if(req.body.file===null||!req.body.file){
        const err={
            "file":null,
            "error":"Invalid JSON input."
        }
        resp.json(err)
    }
    else{
        fs.writeFile('/app/data/'+req.body.file, req.body.data.replace(/ /g, ''), function (err) {
            if (err) throw err;
            console.log('Saved!');
        const ret={
            "file": req.body.file,
            "message": "Success."
            }    
        resp.json(ret)    
          });
    }
})
app.listen(port,()=>{
    console.log("Container1 Active");
});


// const express=require('express');
// const cors=require('cors');
// const app=express();
// app.use(cors);
// app.use(express.json());
// const port=process.env.port||3300
// app.post('/enroll',(req,res)=>{
//     console.log(req.body.name);
// });
// app.get('/',(req,res)=>{
//     res.send("hi")
// });
// app.listen(port,()=>console.log('Backend active'));