import { Button, Container, Col, Nav, Navbar, NavDropdown, Table } from 'react-bootstrap';
import { Link, Route, Routes, useNavigate, useParams } from 'react-router-dom';
import '../../css/Team.css';
import Board from '../team/board/Board';
import { useEffect, useState } from 'react';
import Intro from '../team/Intro';
import LoginModal from '../modal/LoginModal';
import JoinModal from '../modal/JoinModal';
import { getAccessToken, logout } from '../../service/ApiService';
import axios from 'axios';
import { axiosInstance } from '../../service/ApiService';
import CreatePost from '../team/board/CreatePost';
import PostDetail from '../team/board/PostDetail';
import WikiDoc from '../team/wiki/WikiDoc';
import CreateWikiDoc from '../team/wiki/CreateWikiDoc';
import DocHistory from '../team/wiki/DocHistory';
import Players from '../team/players/Players';
import UpdatePost from '../team/board/UpdatePost';
import { useAuth } from '../../auth/AuthContext';
import UpdatePassword from "../team/user/UpdatePassword";
import UpdateNickname from "../team/user/UpdateNickname";

const SPRING_SERVER_URL = process.env.REACT_APP_SPRING_SERVER_URL;

const Team = () => {
    const { teamCode } = useParams();
    const { isLogin, setIsLogin, setLoginId, loginId } = useAuth();
    const [showLoginModal, setShowLoginModal] = useState(false);
    const [showJoinModal, setShowJoinModal] = useState(false);
    const [postId, setPostId] = useState(0);
    const [team, setTeam] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        axiosInstance
            .get(`/${teamCode}`)
            .then((response) => {
                console.log(response);
                setTeam(response.data.result);
            })
            .catch((error) => {
                console.log(error);
            });
    }, []);

    const toggleModals = (modal) => {
        if (modal === 'login') {
            setShowLoginModal(true);
            setShowJoinModal(false);
        } else if (modal === 'join') {
            setShowLoginModal(false);
            setShowJoinModal(true);
        }
    };

    const handleLogout = () => {
        axios
            .delete(SPRING_SERVER_URL + '/auth/refresh', { withCredentials: true })
            .then(() => {
                logout();
                setIsLogin(false);
                setLoginId('');
                alert('로그아웃되었습니다.');
            })
            .catch((e) => {
                console.log(e);
            });
    };

    return (
        <Container fluid className="d-flex" id="wrapper">
            <Col xs={2} id="sidebar-wrapper" className="border-end bg-white">
                <div className="sidebar-heading border-bottom bg-light">
                    <img src={team.imagePath} />
                    {team.name}
                </div>
                <Nav className="flex-column">
                    <Nav.Link
                        className="list-group-item-action list-group-item-light p-3"
                        onClick={() => navigate(`./wiki`)}
                    >
                        팀 정보(Wiki)
                    </Nav.Link>
                    <Nav.Link
                        className="list-group-item-action list-group-item-light p-3"
                        onClick={() => navigate(`./players`)}
                    >
                        선수단
                    </Nav.Link>
                    <Nav.Link
                        className="list-group-item-action list-group-item-light p-3"
                        href="#!"
                    >
                        경기일정
                    </Nav.Link>
                    <Nav.Link
                        className="list-group-item-action list-group-item-light p-3"
                        onClick={() => navigate(`./board`)}
                    >
                        게시판
                    </Nav.Link>
                    <Nav.Link
                        className="list-group-item-action list-group-item-light p-3"
                        onClick={() => navigate(`../`)}
                    >
                        메인으로
                    </Nav.Link>
                </Nav>
            </Col>
            <LoginModal
                show={showLoginModal}
                onHide={() => setShowLoginModal(false)}
                toggleModals={toggleModals}
            />
            <JoinModal
                show={showJoinModal}
                onHide={() => setShowJoinModal(false)}
                toggleModals={toggleModals}
            />
            <Col id="page-content-wrapper">
                <Navbar expand="lg" className="navbar-light bg-light border-bottom">
                    <Container fluid>
                        <Navbar.Toggle aria-controls="navbarSupportedContent" />
                        <Navbar.Collapse
                            id="navbarSupportedContent"
                            className="justify-content-end"
                        >
                            <Nav>
                                <Nav.Link as={Link} to="/" className="nav-link active">
                                    Home
                                </Nav.Link>
                                {isLogin ? (
                                    <Nav.Link href="#!" className="nav-link" onClick={handleLogout}>
                                        Logout
                                    </Nav.Link>
                                ) : (
                                    <Nav.Link
                                        href="#!"
                                        className="nav-link"
                                        onClick={() => setShowLoginModal(true)}
                                    >
                                        Login
                                    </Nav.Link>
                                )}
                                {isLogin && <NavDropdown title="회원정보" id="navbarDropdown">
                                    <NavDropdown.Item onClick={() => navigate("./user/update/password")}>비밀번호 수정</NavDropdown.Item>
                                    <NavDropdown.Item onClick={() => navigate("./user/update/nickname")}>닉네임 수정</NavDropdown.Item>
                                    <NavDropdown.Divider />
                                    <NavDropdown.Item href="#!">
                                        Something else here
                                    </NavDropdown.Item>
                                </NavDropdown>}
                            </Nav>
                        </Navbar.Collapse>
                    </Container>
                </Navbar>
                <Container fluid>
                    <Routes>
                        <Route index element={<Intro />} />
                        <Route path="wiki" element={<WikiDoc />} />
                        <Route path="wiki/create" element={<CreateWikiDoc />} />
                        <Route path="wiki/history" element={<DocHistory />} />
                        <Route path="wiki/:wikiDocId/:version" element={<WikiDoc />} />
                        <Route path="players" element={<Players />} />
                        <Route path="board" element={<Board />} />
                        <Route path="board/create" element={<CreatePost />} />
                        <Route path="board/:postId" element={<PostDetail />} />
                        <Route path="board/:postId/update" element={<UpdatePost />} />
                        <Route path="user/update/password" element={<UpdatePassword/>} />
                        <Route path="user/update/nickname" element={<UpdateNickname/>} />
                    </Routes>
                </Container>
            </Col>
        </Container>
    );
};

export default Team;
