import { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import '../../css/LoginModal.css';

const LoginModal = (props) => {
    const [loginId, setLoginId] = useState('');
    const [password, setPassword] = useState('');

    const handleSignIn = (event) => {

        // 로그인 로직 처리
        console.log('로그인 시도: ', loginId, password);
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
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <div className="d-flex justify-content-around mt-3">
                    <a href="#" className="btn btn-outline-primary">계정 생성</a>
                    <a href="#" className="btn btn-outline-secondary">비밀번호 찾기</a>
                </div>
            </Modal.Footer>
        </Modal>
    );
};

export default LoginModal;
