import {Button, Form} from "react-bootstrap";
import {useEffect, useState} from "react";
import {handleInputChange, handleKeyDown} from "../../../service/CommonService";
import {axiosInstance} from "../../../service/ApiService";
import {useLocation, useNavigate, useParams} from "react-router-dom";

const CreateWikiDoc = () => {
    const {teamName} = useParams();
    const location = useLocation();
    const initialDocBody = location.state?.body || '';
    const [newDocVersion, setNewDocVersion] = useState({
        title: teamName,
        body: initialDocBody
    })
    const [errors, setErrors] = useState('');
    const navigate = useNavigate();


    const handleDocSubmit = () => {
        axiosInstance.post(`/${teamName}/wiki`, newDocVersion)
            .then(response => {
                console.log(response);
                navigate('../wiki');
            }).catch(error => {
            console.log(error);
            setErrors(error.response.data.result.valid_body);
        })
    }

    return (
        <>
            <Form>
                <Form.Group>
                    <Form.Label>문서 작성</Form.Label>
                    <Form.Control
                        name="body"
                        as="textarea"
                        rows={10}
                        value={newDocVersion.body}
                        required
                        onChange={(e) => handleInputChange(e,newDocVersion,setNewDocVersion)}
                        />
                </Form.Group>
                {errors && <Form.Text className="valid-error">{errors}</Form.Text>}
                <Button onClick={handleDocSubmit}>새 문서 생성</Button>
            </Form>
        </>
    )
}

export default CreateWikiDoc;