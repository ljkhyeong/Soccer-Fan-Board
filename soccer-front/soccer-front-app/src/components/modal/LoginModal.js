import { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import '../../css/LoginModal.css';
import axios from "axios";
import {axiosInstance} from "../../service/ApiService";
import {handleKeyDown, handleInputChange, initStateObject} from "../../service/CommonService";

const LoginModal = (props) => {
    const [errors, setErrors] = useState({
        noExistError: '',
        loginIdError: '',
        passwordError: ''
    });
    const [loginForm, setLoginForm] = useState({
        loginId: '',
        password: ''
    })

    const handleSignIn = (e) => {
        axiosInstance.post("/auth/login", loginForm)
            .then((response) => {
            console.log(response);
            setLoginForm(initStateObject(loginForm));
            setErrors(initStateObject(errors));
            alert("로그인 되었습니다.")
            props.setIsLogin(true);
            props.onHide();
        }).catch((error) => {
            console.log(error);
            const errorResult = error.response.data.result;
            if (typeof errorResult == 'object') {
                setErrors({
                    noExistError: '',
                    loginIdError: errorResult.valid_loginId,
                    passwordError: errorResult.valid_password
                });
            } else {
                setErrors({
                    noExistError: errorResult,
                    loginIdError: '',
                    passwordError: ''
                });
            }
        })
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
                            name="loginId"
                            type="text"
                            required
                            autoComplete="username"
                            onChange={(e) => handleInputChange(e,loginForm, setLoginForm)}
                            onKeyDown={(e) => handleKeyDown(e,handleSignIn)}
                            />
                        {errors.loginIdError && <Form.Text className="valid-error">{errors.loginIdError}</Form.Text>}
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>비밀번호</Form.Label>
                        <Form.Control
                            name="password"
                            type="password"
                            required
                            onChange={(e) => handleInputChange(e,loginForm, setLoginForm)}
                            onKeyDown={(e) => handleKeyDown(e,handleSignIn)}
                            />
                        {errors.passwordError && <Form.Text className="valid-error">{errors.passwordError}</Form.Text>}
                    </Form.Group>
                    {errors.noExistError && <Form.Text className="valid-error">{errors.noExistError}</Form.Text>}
                    <Button variant="primary" onClick={handleSignIn}>로그인</Button>
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
