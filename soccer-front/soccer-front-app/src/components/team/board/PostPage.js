import {useState} from "react";
import {Container, Form, Button} from "react-bootstrap";
import {axiosInstance} from "../../../service/ApiService";

const PostPage = (props) => {

    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        axiosInstance.post('/post', {
            title:title,
            content:content
        }).then(response => {
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
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formContent">
                    <Form.Label>내용</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={10}
                        placeholder="내용을 입력하세요"
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        required
                    />
                </Form.Group>
                <Button onClick={handleSubmit}>작성</Button>
            </Form>
        </Container>
    );
};

export default PostPage;