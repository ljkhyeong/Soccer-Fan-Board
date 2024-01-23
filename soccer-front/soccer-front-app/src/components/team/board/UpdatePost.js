import {useEffect, useState} from "react";
import {Container, Form, Button} from "react-bootstrap";
import {axiosInstance} from "../../../service/ApiService";
import {handleInputChange, handleKeyDown, initStateObject} from "../../../service/CommonService";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";

const UpdatePost = (props) => {

    const {teamCode, postId} = useParams();
    const [postForm, setPostForm] = useState({
        title:'',
        content:''
    })
    const [errors, setErrors] = useState({
        titleError:'',
        contentError:''
    })
    const navigate = useNavigate();

    useEffect(() => {
        axiosInstance.get(`/${teamCode}/posts/${postId}`
        ).then(response => {
            console.log(response);
            const postInfo = response.data.result;
            setPostForm({
                title: postInfo.title,
                content: postInfo.content
            })
        }).catch(error => {
            console.log(error);
        })
    }, [teamCode, postId]);
    const handlePostUpdate = (e) => {
        e.preventDefault();
        axiosInstance.put(`/${teamCode}/posts/${postId}`, postForm)
            .then(response => {
            console.log(response);
            setErrors(initStateObject(errors));
            alert("게시글 수정 성공");
            navigate(`../board/${postId}`);
        }).catch(error => {
            console.log(error);
            const errorResult = error.response.data.result;

            if (!errorResult.valid_title && !errorResult.valid_content) {
                alert(errorResult);
            } else {
                setErrors({
                    titleError: errorResult.valid_title,
                    contentError: errorResult.valid_content
                })
            }
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
                        value={postForm.title}
                        placeholder="제목을 입력하세요"
                        onChange={(e) => handleInputChange(e,postForm,setPostForm)}
                        required
                    />
                    { errors.titleError && <Form.Text className="valid-error">{errors.titleError}</Form.Text>}
                </Form.Group>
                <Form.Group className="mb-3" controlId="formContent">
                    <Form.Label>내용</Form.Label>
                    <Form.Control
                        name="content"
                        as="textarea"
                        rows={10}
                        value={postForm.content}
                        placeholder="내용을 입력하세요"
                        onChange={(e) => handleInputChange(e,postForm,setPostForm)}
                        required
                    />
                    { errors.contentError && <Form.Text className="valid-error">{errors.contentError}</Form.Text>}
                </Form.Group>
                <Button variant="outline-success" onClick={handlePostUpdate}>수정</Button>
            </Form>
        </Container>
    );
};

export default UpdatePost;