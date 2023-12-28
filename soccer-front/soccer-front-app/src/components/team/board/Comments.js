import {Button, Form, ListGroup} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {axiosInstance} from "../../../service/ApiService";
import {handleKeyDown, resetStates} from "../../../service/CommonService";
import {formatDateTime} from "../../../service/ApiService";
const Comments = (props) => {
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState("");
    const [error, setError] = useState("");
    const [reload, setReload] = useState(true);

    useEffect(() => {
        renderCommentList();
    }, [reload]);

    const renderCommentList = () => {
        axiosInstance.get(`/posts/${props.postId}/comments`)
            .then(response => {
                console.log(response);
                setComments(response.data.result.content);
            }).catch(error => {
            console.log(error);
        })
    }

    const handleCommentSubmit = () => {
        axiosInstance.post(`/posts/${props.postId}/comment`, {
            comment : newComment
        }).then(response => {
            console.log(response);
            setError('');
            resetStates(setNewComment);
            setReload(!reload);
        }).catch(error => {
            console.log(error);
            const errorResult = error.response.data.result;
            setError(errorResult.valid_comment);
        })
    }


    return (
        <>
        <Form className="mt-4">
            <Form.Group className="mb-3">
                <Form.Control
                    as="textarea"
                    rows={3}
                    placeholder="댓글을 입력하세요"
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                    onKeyDown={(e) => handleKeyDown(e, handleCommentSubmit)}
                />
                {error && <Form.Text className="valid-error">{error}</Form.Text>}
            </Form.Group>
            <Button onClick={() => handleCommentSubmit()}>댓글 작성</Button>
        </Form>
        <ListGroup className="mt-4">
            {comments.map((comment,index) => (
                <ListGroup.Item key={comment.commentId}>
                    {index+1} . {comment.commenter} - {comment.comment} - {formatDateTime(comment.createdAt)}
                </ListGroup.Item>
            ))}
        </ListGroup>
        </>
    );
};

export default Comments;