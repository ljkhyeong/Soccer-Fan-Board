import {Button, Form} from "react-bootstrap";
import {useEffect, useState} from "react";
import {handleInputChange, handleKeyDown} from "../../../service/CommonService";
import {axiosInstance} from "../../../service/ApiService";
import {useLocation, useNavigate, useParams} from "react-router-dom";

const CreateWikiDoc = () => {
    const {teamCode} = useParams();
    const location = useLocation();
    const wikiDoc = location.state?.wikiDoc;
    const [title, setTitle] = useState('');
    const [body, setBody] = useState(wikiDoc?.body || '');
    const [errors, setErrors] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        axiosInstance.get(`/${teamCode}`)
            .then(response => {
                console.log(response);
                setTitle(response.data.result.name);
            }).catch(error => {
                console.log(error);
        })
    }, [teamCode]);


    const handleDocSubmit = () => {
        axiosInstance.post(`/${teamCode}/wiki`, {
            title: title,
            body: body
        })
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
                    <h1>{title} ({wikiDoc?.version ? 'v'+(wikiDoc.version+1) : '새 문서 작성'})</h1>
                    <Form.Control
                        name="body"
                        as="textarea"
                        rows={10}
                        value={body}
                        required
                        onChange={(e) => setBody(e.target.value)}
                        />
                </Form.Group>
                {errors && <Form.Text className="valid-error">{errors}</Form.Text>}
                <Button onClick={handleDocSubmit}>새 문서 생성</Button>
            </Form>
        </>
    )
}

export default CreateWikiDoc;