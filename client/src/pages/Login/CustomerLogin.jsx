import React, { useState, useEffect } from "react";
import "./customerLogin.css";
import Chasovnika from "../../assets/images/woman.png"
import backgroundImage from "../../assets/images/try.jpeg"
import {  useNavigate } from "react-router-dom";
import authService from '../../services/auth-service';





const Login = () => {
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
   <div  style={{
        backgroundImage: `url(${backgroundImage})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center center',
        backgroundAttachment: 'fixed',
        backgroundRepeat: 'no-repeat',
        minHeight: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center', // Center the content vertically and horizontally
        padding: '5rem 0', // Add padding to the top and bottom to center the content vertically
      }}
    >
   
    <section className="vh-100" style={{ marginTop: '5rem' }}>
    <div className="container h-100">
      <div className="row d-flex justify-content-center align-items-center h-100">
        <div className="col-lg-12 col-xl-11">
          <div className="card text-black" style={{ borderRadius: '25px' }}>
            <div className="card-body p-md-5">
              <div className="row justify-content-center">
                <div className="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1 mt-5">

                  <p className="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign in</p>

                  <form onSubmit={submit}>

                    
                      <div className="form-floating mb-3">
                           <input type="email" 
                            id="email"
                            name="email"
                            value={loginRequest.email} onChange={(e) => handleChange(e)}
                           className="form-control bg-white text-dark" required placeholder="Email"/>
                           <label htmlFor="floatingUsername">Email</label>
                      </div>
                   

                    <div className="form-floating mb-3 mt-5">
                           <input type="password" 
                            id="password"
                            name="password"
                            value={loginRequest.password} onChange={(e) => handleChange(e)}
                           className="form-control bg-white text-dark" required placeholder="Password"/>
                           <label htmlFor="floatingPassord">Password</label>
                      </div>

                      
                      <div className="form-check d-flex justify-content-center mb-3">Don't have an account yet?&nbsp;
                      <a className="pe-auto" style={{ cursor: 'pointer' }} onClick={() => navigate("/register")}>Sign up here</a>
                      </div>
                      <div className="form-check d-flex justify-content-center mb-5">Forget Password?&nbsp;
                      <a className="pe-auto" style={{ cursor: 'pointer' }} onClick={() => navigate("/forgotPassword")}>Reset password</a>
                      </div>

                    <div className="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                      <button type="sumbit" className="btn btn-primary btn-lg">Login</button>
                    </div>

                  </form>
                  {invalidCredentialsMessage && <p style={{ color: 'red' }}>{invalidCredentialsMessage}</p>}
                  {notEnabledAcc && <p style={{ color: 'red' }}>{notEnabledAcc}</p>}
                </div>
                <div className="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

                  <img src={Chasovnika}
                    className="img-fluid rounded-circle" alt="Sample image" />

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  </div>
  </>
  );
};

export default Login;
