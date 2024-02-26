import React, { useState } from 'react';
import signInImage from "../../assets/images/reg.svg"
import signUpImage from "../../assets/images/log.svg"
import authService from '../../services/auth-service';
import {  useNavigate } from "react-router-dom";
import "./login2.css";

const Login2 = () => {
  const navigate = useNavigate();

  const [loginRequest, setLoginRequest] = useState(
    {
      email: "",
      password: ""
    }
  )

  const [invalidCredentialsMessage, setInvalidCredentialsMessage] = useState(null);
  const [notEnabledAcc, setNotEnabledAcc] = useState(null);
  
  const handleChange = (e) => {
    const value = e.target.value;
    setLoginRequest({...loginRequest,[e.target.name]: value})
    setInvalidCredentialsMessage(null);
    setNotEnabledAcc(null);
  
}

  



const submit = async (e) => {
  e.preventDefault();
  try {
      await authService.makeLoginRequest(loginRequest);
     
      navigate("/dashboard");
  } catch (error) {
    console.log(error.response.data.errors);
     const errorMessage = error.response.data.errors[0];
  if(errorMessage === "Invalid email or password" || errorMessage === "Invalid Email"){
    setInvalidCredentialsMessage("Invalid email or password!");
    setLoginRequest(prevLoginRequest => ({ ...prevLoginRequest, password: '' , email: ''})); 
  }
  if(errorMessage === "User is not verified!"){
    setNotEnabledAcc("User is not verified!");
    setLoginRequest(prevLoginRequest => ({ ...prevLoginRequest, password: '' , email: ''}));
    }
    
  }
};


  return (
    <>
    <div className='sign-up-form'></div>
      <h1>Sign Up Now</h1>
      <form>
          <input type="email" 
              id="email"
              name="email"
              value={loginRequest.email} onChange={(e) => handleChange(e)}
              className="form-control bg-white text-dark" required placeholder="Email"/>
           <input type="password" 
              id="password"
              name="password"
              value={loginRequest.password} onChange={(e) => handleChange(e)}
              className="form-control bg-white text-dark" required placeholder="Password"/>
              <p>I agree of terms of services</p>
      </form>

    </>
      
  );
};

export default Login2;
