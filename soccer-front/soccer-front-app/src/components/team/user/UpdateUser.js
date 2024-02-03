import {Button, Col, Form, Row} from "react-bootstrap";
import {useState} from "react";
import {handleInputChange, handleKeyDown} from "../../../service/CommonService";
import {axiosInstance} from "../../../service/ApiService";

const UpdateUser = () => {

    const [passwordForm, setPasswordForm] = useState({
        type:'password',
        currentPassword:'',
        newPassword:'',
        confirmNewPassword:''
    })
    const [errors, setErrors] = useState({
        valid_currentPassword:'',
        valid_newPassword:'',
        valid_confirmNewPassword:''
    })

    const handleUpdatePassword = () => {
        axiosInstance.put(`user`, passwordForm
        ).then(response => {
            console.log(response);
        }).catch(error => {
            console.log(error);
            const errorResult = error.response.data.result
            if (errorResult.valid_currentPassword || errorResult.valid_newPassword || errorResult.valid_confirmNewPassword) {
                setErrors({
                    valid_currentPassword: errorResult.valid_currentPassword,
                    valid_newPassword: errorResult.valid_newPassword,
                    valid_confirmNewPassword: errorResult.valid_confirmNewPassword
                });
            } else {
                alert(errorResult);
            }
        });
    };


    return (
        <>
            <Row className="justify-content-md-center">
                <Col md={6}>
                    <Form>
                        <h2>비밀번호 변경</h2>
                        <Form.Group controlId="currentPassword">
                            <Form.Label>현재 비밀번호</Form.Label>
                            <Form.Control
                                name="currentPassword"
                                type="password"
                                placeholder="현재 비밀번호"
                                onChange={e => handleInputChange(e, passwordForm, setPasswordForm)}
                                onKeyDown={e => handleKeyDown(e, handleUpdatePassword)}
                            />
                            {errors.valid_currentPassword &&
                            <Form.Text className="valid-error">{errors.valid_currentPassword}</Form.Text> }
                        </Form.Group>
                        <Form.Group controlId="newPassword">
                            <Form.Label>새 비밀번호</Form.Label>
                            <Form.Control
                                name="newPassword"
                                type="password"
                                placeholder="새 비밀번호"
                                onChange={e => handleInputChange(e, passwordForm, setPasswordForm)}
                                onKeyDown={e => handleKeyDown(e, handleUpdatePassword)}
                            />
                            {errors.valid_newPassword &&
                                <Form.Text className="valid-error">{errors.valid_newPassword}</Form.Text> }
                        </Form.Group>
                        <Form.Group controlId="confirmNewPassword">
                            <Form.Label>새 비밀번호 확인</Form.Label>
                            <Form.Control
                                name="confirmNewPassword"
                                type="password"
                                placeholder="새 비밀번호 확인"
                                onChange={e => handleInputChange(e, passwordForm, setPasswordForm)}
                                onKeyDown={e => handleKeyDown(e, handleUpdatePassword)}
                            />
                            {errors.valid_confirmNewPassword &&
                                <Form.Text className="valid-error">{errors.valid_confirmNewPassword}</Form.Text> }
                        </Form.Group>
                        <div style={{
                            marginTop: "20px"
                        }}>
                            <Button variant="primary" onClick={handleUpdatePassword}>
                                비밀번호 수정
                            </Button>
                        </div>
                    </Form>
                </Col>
            </Row>
        </>
    );
}

export default UpdateUser;