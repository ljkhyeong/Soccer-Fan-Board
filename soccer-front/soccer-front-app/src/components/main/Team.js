import {Button, Container, Col, Nav, Navbar, NavDropdown, Table} from "react-bootstrap";
import { Link } from 'react-router-dom';
import '../../css/Team.css';
import Board from '../team/Board';
import {useState} from "react";
import Intro from "../team/Intro";
import LoginModal from "../modal/LoginModal";
const Team = () => {

    const [showContainer, setShowContainer] = useState('intro');
    const [showModal, setShowModal] = useState(false);


    return (
        <Container fluid className="d-flex" id="wrapper">
            <Col xs={2} id="sidebar-wrapper" className="border-end bg-white">
                <div className="sidebar-heading border-bottom bg-light">Tottenham Hotspur</div>
                <Nav className="flex-column">
                    <Nav.Link className="list-group-item-action list-group-item-light p-3" href="#!"
                              onClick={() => setShowContainer('intro')}>팀 정보(Wiki)</Nav.Link>
                    <Nav.Link className="list-group-item-action list-group-item-light p-3" href="#!"
                              onClick={() => setShowContainer('players')}>선수단</Nav.Link>
                    <Nav.Link className="list-group-item-action list-group-item-light p-3" href="#!"
                              onClick={() => setShowContainer('intro')}>경기일정</Nav.Link>
                    <Nav.Link className="list-group-item-action list-group-item-light p-3" href="#!"
                              onClick={() => setShowContainer('board')}>게시판</Nav.Link>

                </Nav>
            </Col>
            <LoginModal show={showModal} onHide={() => setShowModal(false)}/>
            <Col id="page-content-wrapper">
                <Navbar expand="lg" className="navbar-light bg-light border-bottom">
                    <Container fluid>
                        <Button variant="primary" id="sidebarToggle">Toggle Menu</Button>
                        <Navbar.Toggle aria-controls="navbarSupportedContent" />
                        <Navbar.Collapse id="navbarSupportedContent" className="justify-content-end">
                            <Nav>
                                <Nav.Link as={Link} to="/" className="nav-link active">Home</Nav.Link>
                                <Nav.Link href="#!" className="nav-link"
                                          onClick={() => setShowModal(true)}>Login</Nav.Link>
                                <NavDropdown title="Dropdown" id="navbarDropdown">
                                    <NavDropdown.Item href="#!">Action</NavDropdown.Item>
                                    <NavDropdown.Item href="#!">Another action</NavDropdown.Item>
                                    <NavDropdown.Divider />
                                    <NavDropdown.Item href="#!">Something else here</NavDropdown.Item>
                                </NavDropdown>
                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>
                <Container fluid>
                    { showContainer==='board' ? <Board/> : <Intro/>}
                </Container>
            </Col>
        </Container>
    );
}

export default Team;