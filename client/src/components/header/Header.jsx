import React from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import Logo from './../../assets/images/logo.png';

const Header = () => {
  return (
    <Navbar expand="lg" className="bg-body-tertiary header-color">
      <Container fluid>
        <Navbar.Brand href="#">
        <img
            src={Logo}
            alt="Navbar Logo"
            width="200"
            height="50"
            className="d-inline-block align-top mx-3"
          />
      </Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav className="me-auto my-2 my-lg-0" style={{ maxHeight: '100px' }} navbarScroll>
            <Nav.Link href="#action1">Home</Nav.Link>
            <Nav.Link href="#action2">Link</Nav.Link>
            <NavDropdown title="Link" id="navbarScrollingDropdown">
              <NavDropdown.Item href="#action3">Action</NavDropdown.Item>
              <NavDropdown.Item href="#action4">Another action</NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="#action5">Something else here</NavDropdown.Item>
            </NavDropdown>
            <Nav.Link href="#" disabled>
              Link
            </Nav.Link>
          </Nav>
          <div className="d-flex align-items-center justify-content-center flex-grow-1">
            <div className="search-container">
              <div className="input-group">
                <div className="input-group-btn search-panel">
                  <button type="button" className="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <span id="search_concept">All</span> <span className="caret"></span>
                  </button>
                  <ul className="dropdown-menu scrollable-dropdown" role="menu">
                    <li><a href="#">Automotive Accessories</a></li>
                  </ul>
                </div>
                <input type="hidden" name="search_param" value="all" id="search_param" />
                <input type="text" className="form-control" name="x" id="search" placeholder="Search" style={{width: '800px'}} />
                <span className="input-group-btn">
                  <button className="btn btn-default" type="button">
                    <FontAwesomeIcon icon={faMagnifyingGlass} />
                  </button>
                </span>
              </div>
            </div>
          </div>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
