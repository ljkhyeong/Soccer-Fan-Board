import { Button, Form, ListGroup } from 'react-bootstrap';
import React, { useEffect, useState } from 'react';
import { axiosInstance } from '../../../service/ApiService';
import {
    handleInputChange,
    handleKeyDown,
    initStateObject,
    resetStates,
} from '../../../service/CommonService';
import { formatDateTime } from '../../../service/ApiService';
import { useParams } from 'react-router-dom';
import { useAuth } from '../../../auth/AuthContext';
const Comments = () => {
    const { teamCode, postId } = useParams();
    const { loginId, isLogin } = useAuth();
    const [comments, setComments] = useState([]);
    const [commentForm, setCommentForm] = useState({
        parentId: '',
        tempNickname: loginId ? loginId : '비회원',
        comment: '',
    });
    const [replyForm, setReplyForm] = useState({
        parentId: '',
        tempNickname: loginId ? loginId : '비회원',
        comment: '',
    });
    const [errors, setErrors] = useState({
        valid_tempNickname: '',
        valid_comment: '',
    });
    const [replyErrors, setReplyErrors] = useState({
        valid_tempNickname: '',
        valid_comment: '',
    });
    const [activeCommentId, setActiveCommentId] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPage, setTotalPage] = useState(0);
    const [reload, setReload] = useState(true);

    useEffect(() => {
        renderCommentList();
    }, [currentPage, reload]);

    useEffect(() => {
        setCommentForm((prevState) => ({
            ...prevState,
            tempNickname: loginId ? loginId : '비회원',
        }));

        setReplyForm((prevState) => ({
            ...prevState,
            tempNickname: loginId ? loginId : '비회원',
        }));
    }, [loginId]);

    const renderCommentList = () => {
        axiosInstance
            .get(`/${teamCode}/posts/${postId}/comments`, {
                params: {
                    page: currentPage,
                    size: 10,
                },
            })
            .then((response) => {
                console.log(response);
                setComments(response.data.result.content);
                setTotalPage(response.data.result.totalPages);
            })
            .catch((error) => {
                console.log(error);
            });
    };

    const handleCommentSubmit = () => {
        axiosInstance
            .post(`/${teamCode}/posts/${postId}/comment`, commentForm)
            .then((response) => {
                console.log(response);
                setErrors(initStateObject(errors));
                setCommentForm({
                    parentId: '',
                    tempNickname: loginId ? loginId : '비회원',
                    comment: '',
                });
                setReplyForm({
                    parentId: '',
                    tempNickname: loginId ? loginId : '비회원',
                    comment: '',
                });
                setActiveCommentId(null);
                setReload(!reload);
            })
            .catch((error) => {
                console.log(error);
                const errorResult = error.response.data.result;
                setErrors({
                    valid_tempNickname: errorResult.valid_tempNickname,
                    valid_comment: errorResult.valid_comment,
                });
            });
    };

    const handleReplySubmit = () => {
        axiosInstance
            .post(`/${teamCode}/posts/${postId}/comment`, replyForm)
            .then((response) => {
                console.log(response);
                setReplyErrors({
                    parentId: '',
                    tempNickname: loginId ? loginId : '비회원',
                    comment: '',
                });
                setCommentForm({
                    parentId: '',
                    tempNickname: loginId ? loginId : '비회원',
                    comment: '',
                });
                setReplyForm(initStateObject(replyForm));
                setActiveCommentId(null);
                setReload(!reload);
            })
            .catch((error) => {
                console.log(error);
                const errorResult = error.response.data.result;
                setReplyErrors({
                    valid_tempNickname: errorResult.valid_tempNickname,
                    valid_comment: errorResult.valid_comment,
                });
            });
    };

    const handleReplyOpen = (commentId) => {
        setActiveCommentId(commentId);
        setReplyForm({
            parentId: commentId,
            tempNickname: loginId ? loginId : '비회원',
            comment: '',
        });
    };

    const handleDeleteComment = (commentId) => {
        axiosInstance
            .delete(`/${teamCode}/posts/${postId}/comment/${commentId}`)
            .then((response) => {
                console.log(response);
                alert('삭제되었습니다.');
                setReload(!reload);
            })
            .catch((error) => {
                console.log(error);
                alert(error.response.data.result);
            });
    };

    return (
        <>
            <Form className="mt-4">
                <Form.Group className="mb-3">
                    {!isLogin && (
                        <div style={{ display: 'flex', justifyItems: 'center' }}>
                            <Form.Label style={{ marginTop: '5px' }}>작성자 :</Form.Label>
                            <Form.Control
                                name="tempNickname"
                                placeholder="임시닉네임"
                                type="text"
                                value={commentForm.tempNickname}
                                onChange={(e) => handleInputChange(e, commentForm, setCommentForm)}
                                onKeyDown={(e) => handleKeyDown(e, handleCommentSubmit)}
                                required
                                style={{ marginLeft: '1vw', height: '5vh', width: '95px' }}
                            />
                            {errors.valid_tempNickname && (
                                <Form.Text className="valid-error" style={{ marginLeft: '10px' }}>
                                    {errors.valid_tempNickname}
                                </Form.Text>
                            )}
                        </div>
                    )}
                    <Form.Control
                        name="comment"
                        as="textarea"
                        value={commentForm.comment}
                        rows={2}
                        placeholder="댓글을 입력하세요"
                        onChange={(e) => handleInputChange(e, commentForm, setCommentForm)}
                        onKeyDown={(e) => handleKeyDown(e, handleCommentSubmit)}
                    />
                    {errors.valid_comment && (
                        <Form.Text className="valid-error">{errors.valid_comment}</Form.Text>
                    )}
                </Form.Group>
                <Button onClick={handleCommentSubmit}>댓글 작성</Button>
            </Form>
            <ListGroup className="mt-4">
                {comments.map((comment, index) =>
                    comment.reply ? (
                        <ListGroup.Item
                            style={{
                                marginLeft: '20px',
                                backgroundColor: '#f0f0f0',
                                fontSize: '0.9em',
                                borderLeft: '3px solid #ddd', // 경계선 효과
                                boxShadow: '0px 0px 5px 0px rgba(0,0,0,0.1)', // 그림자 효과
                                padding: '10px',
                                borderRadius: '5px',
                            }}
                            key={comment.commentId}
                            onClick={() => handleReplyOpen(comment.commentId)}
                        >
                            <div>
                                {index + 1} . {comment.commenter} - {comment.comment} -{' '}
                                {formatDateTime(comment.createdAt)}
                                <img
                                    src="/images/delete_remove_bin_icon-icons.com_72400%20(1).png"
                                    style={{
                                        height: '3vh',
                                        marginLeft: '2px',
                                        paddingBottom: '1.5px',
                                    }}
                                    onClick={() => handleDeleteComment(comment.commentId)}
                                />
                            </div>
                        </ListGroup.Item>
                    ) : (
                        <ListGroup.Item key={comment.commentId}>
                            <div
                                onClick={() => handleReplyOpen(comment.commentId)}
                                style={{ cursor: !comment.removed ? 'pointer' : 'default' }}
                            >
                                {index + 1} . {comment.commenter} - {comment.comment} -{' '}
                                {formatDateTime(comment.createdAt)}
                                <img
                                    src="/images/delete_remove_bin_icon-icons.com_72400%20(1).png"
                                    style={{
                                        height: '3vh',
                                        marginLeft: '2px',
                                        paddingBottom: '1.5px',
                                    }}
                                    onClick={() => handleDeleteComment(comment.commentId)}
                                />
                            </div>
                            {activeCommentId === comment.commentId && !comment.removed && (
                                <Form className="mt-4">
                                    <Form.Group className="mb-3">
                                        {!isLogin && (
                                            <div
                                                style={{ display: 'flex', justifyItems: 'center' }}
                                            >
                                                <Form.Label style={{ marginTop: '5px' }}>
                                                    작성자 :
                                                </Form.Label>
                                                <Form.Control
                                                    name="tempNickname"
                                                    placeholder="임시닉네임"
                                                    type="text"
                                                    value={replyForm.tempNickname}
                                                    onChange={(e) =>
                                                        handleInputChange(
                                                            e,
                                                            replyForm,
                                                            setReplyForm,
                                                        )
                                                    }
                                                    onKeyDown={(e) =>
                                                        handleKeyDown(e, handleReplySubmit)
                                                    }
                                                    required
                                                    style={{
                                                        marginLeft: '1vw',
                                                        height: '5vh',
                                                        width: '95px',
                                                    }}
                                                />
                                                {replyErrors.valid_tempNickname && (
                                                    <Form.Text
                                                        className="valid-error"
                                                        style={{ marginLeft: '10px' }}
                                                    >
                                                        {replyErrors.valid_tempNickname}
                                                    </Form.Text>
                                                )}
                                            </div>
                                        )}
                                        <Form.Control
                                            name="comment"
                                            as="textarea"
                                            value={replyForm.comment}
                                            rows={2}
                                            placeholder="댓글을 입력하세요"
                                            onChange={(e) =>
                                                handleInputChange(e, replyForm, setReplyForm)
                                            }
                                            onKeyDown={(e) => handleKeyDown(e, handleReplySubmit)}
                                        />
                                        {replyErrors.valid_comment && (
                                            <Form.Text className="valid-error">
                                                {replyErrors.valid_comment}
                                            </Form.Text>
                                        )}
                                    </Form.Group>
                                    <Button onClick={handleReplySubmit}>댓글 작성</Button>
                                </Form>
                            )}
                        </ListGroup.Item>
                    ),
                )}
            </ListGroup>
            <div>
                <Button
                    onClick={() => setCurrentPage(currentPage - 1)}
                    disabled={currentPage === 0}
                >
                    이전
                </Button>
                {Array.from({ length: totalPage }, (_, index) => (
                    <Button
                        variant="light"
                        key={index}
                        onClick={() => setCurrentPage(index)}
                        disabled={index === currentPage}
                    >
                        {index + 1}
                    </Button>
                ))}
                <Button
                    onClick={() => setCurrentPage(currentPage + 1)}
                    disabled={comments.length < 10}
                >
                    다음
                </Button>
            </div>
        </>
    );
};

export default Comments;
