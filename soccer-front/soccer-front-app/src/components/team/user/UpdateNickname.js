import {useState} from "react";
import {Button, Form} from "react-bootstrap";
import {handleInputChange, handleKeyDown} from "../../../service/CommonService";
import {axiosInstance} from "../../../service/ApiService";

const UpdateNickname = () => {
    const [nicknameForm, setNicknameForm] = useState({
        type: 'nickname',
        nickname: ''
    })
    const [error, setError] = useState('');


    const handleUpdateNickname = () => {
        axiosInstance.put(`user`, nicknameForm
        ).then(response => {
            console.log(response);
            alert("수정되었습니다.");
        }).catch(error => {
            console.log(error);
            const errorResult = error.response.data.result;
            if (errorResult.valid_nickname) {
                setError(errorResult.valid_nickname);
            } else {
                alert(errorResult);
            }
        })
    }

    return (
        <div style={{display:'flex', justifyContent:'center', alignItems:'center', height:'80vh'}}>
            <Form >
                <Form.Group
                    style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }} >
                        <Form.Label>닉네임</Form.Label>
                        <Form.Control
                            name="nickname"
                            type="text"
                            required
                            onChange={(e) => handleInputChange(e, nicknameForm, setNicknameForm)}
                            onKeyDown={(e) => handleKeyDown(e,handleUpdateNickname)}
                            style={{
                                marginLeft: '15px'
                            }}
                        />
                        {error && <Form.Text className="valid-error">{error}</Form.Text>}
                        <Button onClick={handleUpdateNickname}
                                style={{marginLeft: '15px', height:'45px', marginBottom:'10px'}}
                        >수정
                        </Button>
                </Form.Group>
            </Form>
        </div>
    );
}

export default UpdateNickname;