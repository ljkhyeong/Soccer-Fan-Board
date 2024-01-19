import {Button, Form, ListGroup} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {axiosInstance} from "../../../service/ApiService";
import {handleInputChange, handleKeyDown, initStateObject, resetStates} from "../../../service/CommonService";
import {formatDateTime} from "../../../service/ApiService";
import {useParams} from "react-router-dom";
const Comments = () => {
    const {teamCode, postId} = useParams();
    const [comments, setComments] = useState([]);
    const [commentForm, setCommentForm] = useState({
        parentId : '',
        comment : ''
    });
    const [replyForm, setReplyForm] = useState({
        parentId : '',
        comment : ''
    });
    const [error, setError] = useState("");
    const [activeCommentId, setActiveCommentId] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPage, setTotalPage] = useState(0);
    const [reload, setReload] = useState(true);


    useEffect(() => {
        renderCommentList();
    }, [currentPage, reload]);

    const renderCommentList = () => {
        axiosInstance.get(`${teamCode}/posts/${postId}/comments`, {
            params: {
                page : currentPage,
                size : 10
            }
            }
        ).then(response => {
                console.log(response);
                setComments(response.data.result.content);
                setTotalPage(response.data.result.totalPages);
            }).catch(error => {
            console.log(error);
        })
    }

    const handleCommentSubmit = () => {
        axiosInstance.post(`${teamCode}/posts/${postId}/comment`, commentForm
        ).then(response => {
            console.log(response);
            setError('');
            setCommentForm(initStateObject(commentForm));
            setReplyForm(initStateObject(replyForm));
            setActiveCommentId(null);
            setReload(!reload);
        }).catch(error => {
            console.log(error);
            const errorResult = error.response.data.result;
            setError(errorResult.valid_comment);
        })
    }

    const handleReplySubmit = () => {
        axiosInstance.post(`${teamCode}/posts/${postId}/comment`, replyForm
        ).then(response => {
            console.log(response);
            setError('');
            setCommentForm(initStateObject(commentForm));
            setReplyForm(initStateObject(replyForm));
            setActiveCommentId(null);
            setReload(!reload);
        }).catch(error => {
            console.log(error);
            const errorResult = error.response.data.result;
            setError(errorResult.valid_comment);
        })
    }

    const handleReplyOpen = (commentId) => {
        setActiveCommentId(commentId);
        setReplyForm({
            parentId: commentId,
            comment: ''
        })
    }


    return (
        <>
            <Form className="mt-4">
                <Form.Group className="mb-3">
                    <Form.Control
                        name="comment"
                        as="textarea"
                        value={commentForm.comment}
                        rows={2}
                        placeholder="댓글을 입력하세요"
                        onChange={(e) => handleInputChange(e, commentForm, setCommentForm)}
                        onKeyDown={(e) => handleKeyDown(e, handleCommentSubmit)}
                    />
                    {error && <Form.Text className="valid-error">{error}</Form.Text>}
                </Form.Group>
                <Button onClick={handleCommentSubmit}>댓글 작성</Button>
            </Form>
            <ListGroup className="mt-4">
                {comments.map((comment, index) => (comment.reply ?
                        <ListGroup.Item style={{
                            marginLeft: '20px',
                            backgroundColor: '#f0f0f0',
                            fontSize: '0.9em',
                            borderLeft: '3px solid #ddd', // 경계선 효과
                            boxShadow: '0px 0px 5px 0px rgba(0,0,0,0.1)', // 그림자 효과
                            padding: '10px',
                            borderRadius: '5px'
                        }}
                                        key={comment.commentId} onClick={() => handleReplyOpen(comment.commentId)}>
                            <div>{index + 1} . {comment.commenter} - {comment.comment} - {formatDateTime(comment.createdAt)}</div>
                        </ListGroup.Item> :
                        <ListGroup.Item key={comment.commentId} onClick={() => handleReplyOpen(comment.commentId)}>
                            <div>{index + 1} . {comment.commenter} - {comment.comment} - {formatDateTime(comment.createdAt)}</div>
                            {activeCommentId === comment.commentId && (
                                <Form className="mt-4">
                                    <Form.Group className="mb-3">
                                        <Form.Control
                                            name="comment"
                                            as="textarea"
                                            value={replyForm.comment}
                                            rows={2}
                                            placeholder="댓글을 입력하세요"
                                            onChange={(e) => handleInputChange(e, replyForm, setReplyForm)}
                                            onKeyDown={(e) => handleKeyDown(e, handleReplySubmit)}
                                        />
                                        {error && <Form.Text className="valid-error">{error}</Form.Text>}
                                    </Form.Group>
                                    <Button onClick={handleReplySubmit}>댓글 작성</Button>
                                </Form>
                            )}
                        </ListGroup.Item>
                ))}
            </ListGroup>
            <div>
                <Button onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 0}>이전</Button>
                {Array.from({length: totalPage}, (_,index) => (
                  <Button variant="light" key={index} onClick={() => setCurrentPage(index)}>{index+1}</Button>
                ))}
                <Button onClick={() => setCurrentPage(currentPage + 1)} disabled={comments.length < 10}>다음</Button>
            </div>
        </>
    );
};

export default Comments;