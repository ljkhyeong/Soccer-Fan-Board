import { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import '../../css/LoginModal.css';
import axios from "axios";

const SPRING_SERVER_URL = process.env.REACT_APP_SPRING_SERVER_URL;

const JoinModal = (props) => {
    const [loginId, setLoginId] = useState('');
    const [password, setPassword] = useState('');
    const [nickname, setNickname] = useState('');
    const [email, setEmail] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [loginIdError, setLoginIdError] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [nicknameError, setNicknameError] = useState('');
    const [emailError, setEmailError] = useState('');
    const [phoneNumberError, setPhoneNumberError] = useState('');

    const handleJoin = () => {
            axios.post(SPRING_SERVER_URL + "/user", {
                loginId: loginId,
                password: password,
                nickname: nickname,
                email: email,
                phoneNumber: phoneNumber,
                role: "USER"
            }).then((response) => {
                console.log(response);
                alert("회원가입 되었습니다.")
                props.onHide();
            }).catch((error) => {
                let result = error.response.data.result;
                console.log(error);
                setLoginIdError(result.valid_loginId);
                setPasswordError(result.valid_password);
                setNicknameError(result.valid_nickname);
                setEmailError(result.valid_email);
                setPhoneNumberError(result.valid_phoneNumber);
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
                            type="text"
                            required
                            onChange={(e) => setLoginId(e.target.value)}
                            onKeyUp={(e) => {if (e.key === 'Enter') handleJoin()}}
                        />
                        {loginIdError && <Form.Text className="join-error">{loginIdError}</Form.Text>}
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>비밀번호</Form.Label>
                        <Form.Control
                            type="password"
                            required
                            onChange={(e) => setPassword(e.target.value)}
                            onKeyUp={(e) => {if (e.key === 'Enter') handleJoin()}}
                        />
                        {passwordError && <Form.Text className="join-error">{passwordError}</Form.Text>}
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>닉네임</Form.Label>
                        <Form.Control
                            type="text"
                            required
                            onChange={(e) => setNickname(e.target.value)}
                            onKeyUp={(e) => {if (e.key === 'Enter') handleJoin()}}
                        />
                        {nicknameError && <Form.Text className="join-error">{nicknameError}</Form.Text>}
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>이메일</Form.Label>
                        <Form.Control
                            type="email"
                            required
                            onChange={(e) => setEmail(e.target.value)}
                            onKeyUp={(e) => {if (e.key === 'Enter') handleJoin()}}
                        />
                        {emailError && <Form.Text className="join-error">{emailError}</Form.Text>}
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>전화번호</Form.Label>
                        <Form.Control
                            type="tel"
                            required
                            onChange={(e) => setPhoneNumber(e.target.value)}
                            onKeyUp={(e) => {if (e.key === 'Enter') handleJoin()}}
                        />
                        {phoneNumberError && <Form.Text className="join-error">{phoneNumberError}</Form.Text>}
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
