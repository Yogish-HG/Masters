import axios from "axios";
export const imageUpload=async (file) =>{
    const formData = new FormData();
    formData.append("file",file);
    formData.append("upload_preset",process.env.REACT_APP_uploadPresent);
    let data;
    await axios.post(process.env.REACT_APP_cloudinaryBaseUrl,formData)
    .then((response)=>{
        data=response.data["secure-url"];
    })
    return data;
}