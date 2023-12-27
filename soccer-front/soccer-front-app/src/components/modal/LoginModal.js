import { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import '../../css/LoginModal.css';
import axios from "axios";
import {axiosInstance} from "../../service/ApiService";

const SPRING_SERVER_URL = process.env.REACT_APP_SPRING_SERVER_URL;

const LoginModal = (props) => {
    const [loginId, setLoginId] = useState('');
    const [password, setPassword] = useState('');
    const [loginError, setLoginError] = useState('');

    const handleSignIn = (event) => {

        if (loginId && password) {
            axiosInstance.post("/auth/login", {
                loginId: loginId,
                password: password
            }).then((response) => {
                console.log(response);
                alert("로그인 되었습니다.")
                props.setIsLogin(true);
                props.onHide();
            }).catch((error) => {
                console.log(error);
                setLoginError(error.response.data.result);
            })
        } else {
            setLoginError("아이디 혹은 비밀번호를 입력해주세요.");
        }
        // 로그인 로직 처리
    };

    return (
        <Modal show={props.show} onHide={props.onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>로그인</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>아이디</Form.Label>
                        <Form.Control
                            type="text"
                            required
                            autoComplete="username"
                            onChange={(e) => setLoginId(e.target.value)}
                            onKeyUp={(e) => {if (e.key === 'Enter') handleSignIn()}}
                            />
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>비밀번호</Form.Label>
                        <Form.Control
                            type="password"
                            required
                            onChange={(e) => setPassword(e.target.value)}
                            onKeyUp={(e) => {if (e.key === 'Enter') handleSignIn()}}
                            />
                    </Form.Group>
                    <Button variant="primary" onClick={handleSignIn}>로그인</Button>
                    {loginError && <Form.Text className="login-error">{loginError}</Form.Text>}
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <div className="d-flex justify-content-around mt-3">
                    <a href="#" className="btn btn-outline-primary" onClick={() => props.toggleModals("join")}>계정 생성</a>
                    <a href="#" className="btn btn-outline-secondary">비밀번호 찾기</a>
                </div>
            </Modal.Footer>
        </Modal>
    );
};

export default LoginModal;
