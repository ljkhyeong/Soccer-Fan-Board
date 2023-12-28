import {useState} from "react";
import {Container, Form, Button} from "react-bootstrap";
import {axiosInstance} from "../../../service/ApiService";
import {handleInputChange, handleKeyDown} from "../../../service/CommonService";

const CreatePost = (props) => {

    const [postForm, setPostForm] = useState({
        title:'',
        content:''
    })
    const [errors, setErrors] = useState({
        titleError:'',
        contentError:''
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
            const errorResult = error.response.data.result;
            setErrors({
                titleError: errorResult.valid_title,
                contentError: errorResult.valid_content
            })
        })
    };

    return (
        <Container>
            <Form>
                <Form.Group className="mb-3" controlId="formTitle">
                    <Form.Label>제목</Form.Label>
                    <Form.Control
                        name="title"
                        type="text"
                        placeholder="제목을 입력하세요"
                        onChange={(e) => handleInputChange(e,postForm,setPostForm)}
                        required
                        onKeyDown={(e) => handleKeyDown(e,handleSubmit)}
                    />
                    { errors.titleError && <Form.Text className="valid-error">{errors.titleError}</Form.Text>}
                </Form.Group>
                <Form.Group className="mb-3" controlId="formContent">
                    <Form.Label>내용</Form.Label>
                    <Form.Control
                        name="content"
                        as="textarea"
                        rows={10}
                        placeholder="내용을 입력하세요"
                        onChange={(e) => handleInputChange(e,postForm,setPostForm)}
                        required
                        onKeyDown={(e) => handleKeyDown(e,handleSubmit)}
                    />
                    { errors.contentError && <Form.Text className="valid-error">{errors.contentError}</Form.Text>}
                </Form.Group>
                <Button onClick={handleSubmit}>작성</Button>
            </Form>
        </Container>
    );
};

export default CreatePost;