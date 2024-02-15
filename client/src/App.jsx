
import 'bootstrap/dist/css/bootstrap.min.css';
import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
} from "react-router-dom";

import AuthGuardWhenLogout from './components/authguard/AuthGuardWhenLogout';
import AuthGuardWhenLogin from './components/authguard/AuthGuardWhenLogin';
import CustomerRegister from './pages/register/CustomerRegister';
import CustomerLogin from './pages/Login/CustomerLogin';



const App = createBrowserRouter(
  createRoutesFromElements(
    <>
        <Route element={<AuthGuardWhenLogout />}>
          <Route path="/">
            <Route path="login" element={<CustomerLogin/>} />
            <Route path="register" element={<CustomerRegister/>} />
          
          </Route>

        </Route>
          <Route element={<AuthGuardWhenLogin />}>
        
        </Route>
    </>
  )
);
export default App