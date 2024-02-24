import React from 'react'
import { useState } from 'react'
import Choveka from "../../assets/images/dedo.png"
import backgroundImage from "../../assets/images/cher2.jpg"
import "./customerRegister.css"
import { useNavigate } from "react-router-dom";
import authService from '../../services/auth-service';



const CustomerRegister = () => {


    const [user, setUser] = useState({
        firstName:"",
        lastName:"",
        email:"",
        password:"",
        confirmPassword:"",
        role:"CUSTOMER"
    })

    let navigate = useNavigate();
    
    


    const [inputErrors, setInputErrors] = useState({
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      confirmPassword: ''
    });
    
    const handleChange = (e) => {
      const value = e.target.value;
      setUser({...user,[e.target.name]: value})
      setInputErrors({}); // Clear all errors when input changes

  }






   const saveUser = async (e) => {
        e.preventDefault();
        await authService.makeRegisterRequest(user)
        .then((response) => {
          alert("Subject: Tick, Tock! 🕒 Verify Your Account Now!\nHi "+user.username+",\n\nHope you're doing awesome! 🌈 Quick thing: we need you to give your account a thumbs up by clicking the link we sent to your email. Easy peasy! Need a hand? Holler at us.\n\nCheers,\nYour TODO List 🚀")
            navigate("/login");
        })  
        .catch((error) => {
          console.log(error.response.data.errors);
          const errorMessage = error.response.data.errors[error.response.data.errors.length - 1];
          console.log(errorMessage);
          if(errorMessage === "The First Name must be between 3 and 18 characters long!"){
            setInputErrors({ ...inputErrors, firstName: 'The first name must be between 3 and 18 characters long!' });
            setUser(prevUser => ({ ...prevUser, firstName: '' })); 
          }
          else if(errorMessage === "The Last Name must be between 3 and 18 characters long!"){
            setInputErrors({ ...inputErrors, lastName: 'The last name must be between 3 and 18 characters long!' });
            setUser(prevUser => ({ ...prevUser, lastName: '' })); 
          }else if(errorMessage === "The password must be at least 8 characters long!"){
            setInputErrors({ ...inputErrors, password: 'The password must be at least 8 characters long!' });
            setUser(prevUser => ({ ...prevUser, password: '' }));
          }else if(errorMessage === "The email is already taken!"){
            setInputErrors({ ...inputErrors, email: 'The email is already taken!' });
            setUser(prevUser => ({ ...prevUser, email: '' }));
          }else if(errorMessage === "Password does not match!"){
            setInputErrors({ ...inputErrors, confirmPassword: 'Password does not match!' });
            setUser(prevUser => ({ ...prevUser, confirmPassword: '' }));
          }


        });     
    }


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
              <div className="card-body p-md-5 shaded-background">
                <div className="row justify-content-center">
                  <div className="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                    <p className="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign up</p>

                   
                    <form onSubmit={saveUser}>

                      
                        <div className="form-floating mb-3">
                             <input required
                             name="firstName" value={user.firstName} onChange={(e)=> handleChange(e)}
                             type="text" className="form-control bg-white text-dark" id="floatingUsername" placeholder="First Name"/>                 
                             <label htmlFor="floatingUsername">First Name</label>
                             <span className="error-message" style={{color: 'red',   animationDelay: `0.5s`}}  ><br/>{inputErrors.firstName}</span>
                             
                                               
                        </div>
                        
                        <div className="form-floating mb-3">
                             <input required
                             name="lastName" value={user.lastName} onChange={(e)=> handleChange(e)}
                             type="text" className="form-control bg-white text-dark" id="floatingUsername" placeholder="Last Name"/>
                             <label htmlFor="floatingUsername">Last Name</label>
                             <span className="error-message" style={{color: 'red',   animationDelay: ` 0.5`}}  ><br/>{inputErrors.lastName}</span>
                        </div>
                      

                        <div className="form-floating mb-3">
                        <input type="email"  required
                         name="email" value={user.email} onChange={(e) => handleChange(e)}
                        className="form-control bg-white fg-black text-dark" id="floatingInput" placeholder="name@example.com"/>
                        <label  htmlFor="floatingInput">Email</label>
                        <span className="error-message" style={{color: 'red',   animationDelay: `0.5s`}}  ><br/>{inputErrors.email}</span>
                            <div id="emailHelp" className="form-text">We'll never share your email with anyone else.</div>
                         </div>  

                      <div className="form-floating mb-3">
                      <input type="password" required
                      name="password" value={user.password} onChange={(e) => handleChange(e)}
                      className="form-control bg-white text-dark" id="floatingPassord" placeholder="Password"/>
                             <label htmlFor="floatingPassord">Password</label>
    
                             <div id="passwordHelpBlock" className="form-text">
                             <span className="error-message" style={{color: 'red',   animationDelay: `0.5s`}}  ><br/>{inputErrors.password}</span>
                             The password must be at least 8 characters long.
                            </div>  
                        </div>

                            
                  
                        <div className="form-floating mb-3 ">
                        <input type="password" required
                        name="confirmPassword"
                        value={user.confirmPassword}
                        onChange={(e)=> handleChange(e)}
                        className="form-control bg-white text-dark" id="floatingConfirmPassword" placeholder="ConfirmPassword"/>
                             <label htmlFor="floatingConfirmPassword">Confirm Password</label> 
                             <span className="error-message" style={{color: 'red',   animationDelay:`0.5s`}}  ><br/>{inputErrors.confirmPassword}</span>
                        </div>
                    

                      <div className="form-check d-flex justify-content-center mb-5">Already have an account?&nbsp;
                      <a className="pe-auto" style={{ cursor: 'pointer' }} onClick={() => navigate('/login')}>Sign In Here</a>
                      </div>

                      <div className="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                        <button type="sumbit"
                        
                        className="btn btn-primary btn-lg" >Register</button>
                      </div>

                    </form>
                
                     
                

                  </div>
                  <div className="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

                    <img src={Choveka}
                      className="img-fluid  " alt="Sample image" />

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
  )
}

export default CustomerRegister