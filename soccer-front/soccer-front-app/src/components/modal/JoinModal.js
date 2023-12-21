import { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import '../../css/LoginModal.css';
import axios from "axios";

const JoinModal = (props) => {
    const [loginId, setLoginId] = useState('');
    const [password, setPassword] = useState('');
    const [nickname, setNickname] = useState('');
    const [email, setEmail] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [joinError, setJoinError] = useState('');

    const handleJoin = (event) => {

        if (loginId && password) {
            axios.post("http://localhost:8080/api/v1/user", {
                loginId: loginId,
                password: password,
                nickname: nickname,
                email: email,
                phoneNumber: phoneNumber,
                role: "USER"
            }).then((response) => {
                console.log(response);
                props.onHide();
            }).catch((error) => {
                console.log(error);
                setJoinError(error.response.data.result);
            })
        } else {
            setJoinError("아이디 혹은 비밀번호를 입력해주세요.");
        }
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
                            autoComplete="username"
                            onChange={(e) => setLoginId(e.target.value)}
                            onKeyUp={(e) => {if (e.key === 'Enter') handleJoin()}}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>비밀번호</Form.Label>
                        <Form.Control
                            type="password"
                            required
                            onChange={(e) => setPassword(e.target.value)}
                            onKeyUp={(e) => {if (e.key === 'Enter') handleJoin()}}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>닉네임</Form.Label>
                        <Form.Control
                            type="text"
                            required
                            onChange={(e) => setNickname(e.target.value)}
                            onKeyUp={(e) => {if (e.key === 'Enter') handleJoin()}}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>이메일</Form.Label>
                        <Form.Control
                            type="email"
                            required
                            onChange={(e) => setEmail(e.target.value)}
                            onKeyUp={(e) => {if (e.key === 'Enter') handleJoin()}}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3 custom-form-group">
                        <Form.Label>전화번호</Form.Label>
                        <Form.Control
                            type="tel"
                            required
                            onChange={(e) => setPhoneNumber(e.target.value)}
                            onKeyUp={(e) => {if (e.key === 'Enter') handleJoin()}}
                        />
                    </Form.Group>
                    <Button variant="primary" onClick={handleJoin}>회원가입</Button>
                    {joinError && <Form.Text className="join-error">{joinError}</Form.Text>}
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
