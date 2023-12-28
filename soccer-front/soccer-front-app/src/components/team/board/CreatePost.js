import {useState} from "react";
import {Container, Form, Button} from "react-bootstrap";
import {axiosInstance} from "../../../service/ApiService";
import {handleInputChange, handleKeyDown} from "../../../service/CommonService";

const CreatePost = (props) => {

    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [postForm, setPostForm] = useState({
        title:'',
        content:''
    })

    const handleSubmit = (e) => {
        e.preventDefault();
        axiosInstance.post('/post', postForm)
            .then(response => {
            console.log(response);
            alert("게시글 작성 성공");
            props.setShowContainer('board');
        }).catch(error => {
            console.log(error);
        })
    };

    return (
        <Container>
            <Form>
                <Form.Group className="mb-3" controlId="formTitle">
                    <Form.Label>제목</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="제목을 입력하세요"
                        value={title}
                        onChange={(e) => handleInputChange(e,postForm,setPostForm)}
                        required
                        onKeyDown={(e) => handleKeyDown(e,handleSubmit)}
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formContent">
                    <Form.Label>내용</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={10}
                        placeholder="내용을 입력하세요"
                        value={content}
                        onChange={(e) => handleInputChange(e,postForm,setPostForm)}
                        required
                        onKeyDown={(e) => handleKeyDown(e,handleSubmit)}
                    />
                </Form.Group>
                <Button onClick={handleSubmit}>작성</Button>
            </Form>
        </Container>
    );
};

export default CreatePost;