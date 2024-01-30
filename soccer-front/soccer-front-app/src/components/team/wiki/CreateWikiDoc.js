import { Row, Col, Button, Form } from 'react-bootstrap';
import { useEffect, useState } from 'react';
import { handleInputChange, handleKeyDown } from '../../../service/CommonService';
import { axiosInstance } from '../../../service/ApiService';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import MarkdownIt from 'markdown-it';
import MdEditor from 'react-markdown-editor-lite';
import '../../../css/CreateWikiDoc.css';
import 'react-markdown-editor-lite/lib/index.css';

const CreateWikiDoc = () => {
    const { teamCode } = useParams();
    const location = useLocation();
    const wikiDoc = location.state?.wikiDoc;
    const [title, setTitle] = useState('');
    const [body, setBody] = useState(wikiDoc?.body || '');
    const [errors, setErrors] = useState('');
    const navigate = useNavigate();
    const mdParser = new MarkdownIt();

    useEffect(() => {
        axiosInstance
            .get(`/${teamCode}`)
            .then((response) => {
                console.log(response);
                setTitle(response.data.result.name);
            })
            .catch((error) => {
                console.log(error);
            });
    }, [teamCode]);

    const handleDocSubmit = () => {
        axiosInstance
            .post(`/${teamCode}/wiki`, {
                title: title,
                body: body,
            })
            .then((response) => {
                console.log(response);
                navigate('../wiki');
            })
            .catch((error) => {
                console.log(error);
                setErrors(error.response.data.result.valid_body);
            });
    };

    const handleEditorChange = ({ html, text }) => {
        setBody(text);
    };

    return (
        <>
            <Row className="justify-content-md-center">
                <Col md={10}>
                    <Form>
                        <h1 className="wiki-doc-edit-title">
                            {title} (
                            {wikiDoc?.version ? 'v' + (wikiDoc.version + 1) : '새 문서 작성'})
                        </h1>
                        <Form.Group controlId="wikiDocBody">
                            <MdEditor
                                value={body}
                                style={{ height: '500px' }}
                                renderHTML={(text) => mdParser.render(text)}
                                onChange={handleEditorChange}
                            />
                        </Form.Group>
                        {errors && <Form.Text className="valid-error">{errors}</Form.Text>}
                        <Button variant="primary" onClick={handleDocSubmit}>
                            문서 저장
                        </Button>
                    </Form>
                </Col>
            </Row>
        </>
    );
};

export default CreateWikiDoc;
