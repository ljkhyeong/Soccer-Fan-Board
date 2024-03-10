import { useEffect, useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { axiosInstance } from '../../../service/ApiService';
import { handleInputChange, handleKeyDown, initStateObject } from '../../../service/CommonService';
import { useNavigate, useParams } from 'react-router-dom';
import { useAuth } from '../../../auth/AuthContext';

const CreatePost = (props) => {
    const { isLogin } = useAuth();
    const { teamCode } = useParams();
    const [postForm, setPostForm] = useState({
        loginState: isLogin ? true : false,
        tempNickname: isLogin ? '' : '비회원',
        password: '',
        title: '',
        content: '',
    });
    const [errors, setErrors] = useState({
        nicknameError: '',
        passwordError: '',
        titleError: '',
        contentError: '',
    });
    const navigate = useNavigate();

    useEffect(() => {
        console.log(isLogin);
        setPostForm((prevState) => ({
            ...prevState,
            loginState: isLogin,
            tempNickname: isLogin ? '' : '비회원',
            password: '',
        }));

        console.log(postForm.loginState);
    }, [isLogin]);

    const handlePostSubmit = (e) => {
        e.preventDefault();
        axiosInstance
            .post(`/${teamCode}/posts`, postForm)
            .then((response) => {
                console.log(response);
                setErrors(initStateObject(errors));
                alert('게시글 작성 성공');
                navigate('../board');
            })
            .catch((error) => {
                console.log(error);
                const errorResult = error.response.data.result;
                setErrors({
                    nicknameError: errorResult.valid_tempNickname,
                    passwordError: errorResult.valid_password,
                    titleError: errorResult.valid_title,
                    contentError: errorResult.valid_content,
                });
            });
    };

    const handlePrevent = (e) => {
        if (e.key === 'Enter') {
            e.preventDefault();
        }
    };

    const imsifunction = () => {
         const errorResuls = "ll";
    }

    return (
        <Container>
            <Form>
                <Form.Group className="mb-3" controlId="formTitle">
                    <Form.Label>제목</Form.Label>
                    <div style={{ display: 'flex', justifyItems: 'center' }}>
                        <Form.Control
                            name="title"
                            type="text"
                            placeholder="제목을 입력하세요"
                            onChange={(e) => handleInputChange(e, postForm, setPostForm)}
                            onKeyDown={(e) => handlePrevent(e)}
                            required
                        />
                        {errors.titleError && (
                            <Form.Text
                                className="valid-error"
                                style={{ marginLeft: '10px', marginTop: '10px' }}
                            >
                                {errors.titleError}
                            </Form.Text>
                        )}
                    </div>
                </Form.Group>
                {!isLogin && (
                    <Form.Group>
                        <div style={{ display: 'flex', justifyItems: 'center' }}>
                            <Form.Label>작성자</Form.Label>
                            <Form.Control
                                name="tempNickname"
                                placeholder="임시닉네임"
                                type="text"
                                value={postForm.tempNickname}
                                onChange={(e) => handleInputChange(e, postForm, setPostForm)}
                                required
                                style={{ marginLeft: '2vw', height: '5vh', width: '95px' }}
                            />
                            {errors.nicknameError && (
                                <Form.Text className="valid-error" style={{ marginLeft: '10px' }}>
                                    {errors.nicknameError}
                                </Form.Text>
                            )}
                            <Form.Control
                                name="password"
                                placeholder="비밀번호"
                                type="password"
                                value={postForm.password}
                                onChange={(e) => handleInputChange(e, postForm, setPostForm)}
                                required
                                style={{ marginLeft: '2vw', height: '5vh', width: '120px' }}
                            />
                            {errors.passwordError && (
                                <Form.Text className="valid-error" style={{ marginLeft: '10px' }}>
                                    {errors.passwordError}
                                </Form.Text>
                            )}
                        </div>
                    </Form.Group>
                )}
                <Form.Group className="mb-3" controlId="formContent">
                    <Form.Label>내용</Form.Label>
                    <Form.Control
                        name="content"
                        as="textarea"
                        rows={10}
                        placeholder="내용을 입력하세요"
                        onChange={(e) => handleInputChange(e, postForm, setPostForm)}
                        onKeyDown={(e) => handlePrevent(e)}
                        required
                    />
                    {errors.contentError && (
                        <Form.Text className="valid-error">{errors.contentError}</Form.Text>
                    )}
                </Form.Group>
                <Button onClick={handlePostSubmit}>작성</Button>
            </Form>
        </Container>
    );
};

export default CreatePost;
