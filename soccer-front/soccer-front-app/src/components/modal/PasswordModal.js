import {Button, Form, Modal} from "react-bootstrap";
import {useState} from "react";

const PasswordModal = (props) => {

    const [password, setPassword] = useState("");

    return (
        <Modal show={props.show} onHide={props.onHide} centered>
            <Modal.Header>비밀번호</Modal.Header>
            <Modal.Body>
                <Form>
                    <div style={{display:"flex"}}>
                        <Form.Control
                            name="password"
                            type="password"
                            required
                            onChange={(e) => setPassword(e.target.value)}
                        />
                        <Button variant="primary"
                                style={{marginLeft: "10px", width: "5vw"}}
                                onClick={() => props.onPasswordSubmit(password)}
                        >확인</Button>
                    </div>
                </Form>
            </Modal.Body>
        </Modal>
    );
}

export default PasswordModal;