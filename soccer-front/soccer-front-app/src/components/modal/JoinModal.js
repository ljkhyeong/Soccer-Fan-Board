import { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import '../../css/LoginModal.css';
import axios from "axios";
import {handleInputChange, handleKeyDown, initStateObject} from "../../service/CommonService";

const SPRING_SERVER_URL = process.env.REACT_APP_SPRING_SERVER_URL;

const initialJoinForm = {
    loginId: '',
    password: '',
    nickname: '',
    email: '',
    phoneNumber: '',
    role: "USER"
}

const JoinModal = (props) => {
    const [joinForm, setJoinForm] = useState(initialJoinForm);
    const [errors, setErrors] = useState({
        loginIdError: '',
        passwordError: '',
        nicknameError: '',
        emailError: '',
        phoneNumberError: ''
    });

    const handleJoin = () => {
            axios.post(SPRING_SERVER_URL + "/user", joinForm
            ).then((response) => {
                console.log(response);
                setJoinForm(initialJoinForm);
                setErrors(initStateObject(errors));
                alert("회원가입 되었습니다.")
                props.onHide();
            }).catch((error) => {
                let result = error.response.data.result;
                console.log(error);
                setErrors({
                    loginIdError: result.valid_loginId,
                    passwordError: result.valid_password,
                    nicknameError: result.valid_nickname,
                    emailError: result.valid_email,
                    phoneNumberError: result.valid_phoneNumber
                })
            })

        // 로그인 로직 처리
    };


    return (
        <Modal show={props.show} onHide={props.onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>회원가입</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>아이디</Form.Label>
                        <Form.Control
                            name="loginId"
                            type="text"
                            required
                            onChange={(e) => handleInputChange(e,joinForm,setJoinForm)}
                            onKeyDown={(e) => handleKeyDown(e,handleJoin)}
                        />
                        {errors.loginIdError && <Form.Text className="join-error">{errors.loginIdError}</Form.Text>}
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>비밀번호</Form.Label>
                        <Form.Control
                            name="password"
                            type="password"
                            required
                            onChange={(e) => handleInputChange(e,joinForm,setJoinForm)}
                            onKeyDown={(e) => handleKeyDown(e,handleJoin)}
                        />
                        {errors.passwordError && <Form.Text className="valid-error">{errors.passwordError}</Form.Text>}
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>닉네임</Form.Label>
                        <Form.Control
                            name="nickname"
                            type="text"
                            required
                            onChange={(e) => handleInputChange(e,joinForm,setJoinForm)}
                            onKeyDown={(e) => handleKeyDown(e,handleJoin)}
                        />
                        {errors.nicknameError && <Form.Text className="valid-error">{errors.nicknameError}</Form.Text>}
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>이메일</Form.Label>
                        <Form.Control
                            name="email"
                            type="email"
                            required
                            onChange={(e) => handleInputChange(e,joinForm,setJoinForm)}
                            onKeyDown={(e) => handleKeyDown(e,handleJoin)}
                        />
                        {errors.emailError && <Form.Text className="valid-error">{errors.emailError}</Form.Text>}
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>전화번호</Form.Label>
                        <Form.Control
                            name="phoneNumber"
                            type="tel"
                            required
                            onChange={(e) => handleInputChange(e,joinForm,setJoinForm)}
                            onKeyDown={(e) => handleKeyDown(e,handleJoin)}
                        />
                        {errors.phoneNumberError && <Form.Text className="join-error">{errors.phoneNumberError}</Form.Text>}
                    </Form.Group>
                    <Button variant="primary" onClick={handleJoin}>회원가입</Button>

                </Form>
            </Modal.Body>
            <Modal.Footer>
                <div className="d-flex justify-content-around mt-3">
                    <a href="#" className="btn btn-outline-secondary" onClick={() => props.toggleModals("login")}>로그인</a>
                    <a href="#" className="btn btn-outline-secondary">비밀번호 찾기</a>
                </div>
            </Modal.Footer>
        </Modal>
    );
};

export default JoinModal;
