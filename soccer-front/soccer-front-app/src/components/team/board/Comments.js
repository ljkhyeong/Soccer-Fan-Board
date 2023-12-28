import {Button, Form, ListGroup} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {axiosInstance} from "../../../service/ApiService";
import {handleKeyDown, resetStates} from "../../../service/CommonService";

const Comments = (props) => {
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState("");
    const [reload, setReload] = useState(true);

    useEffect(() => {
        renderCommentList();
    }, [reload]);

    const renderCommentList = () => {
        axiosInstance.get(`/post/${props.postId}/comments`)
            .then(response => {
                console.log(response);
                setComments(response.data.result.content);
            }).catch(error => {
            console.log(error);
        })
    }

    const handleCommentSubmit = () => {
        axiosInstance.post(`/post/${props.postId}/comment`, {
            comment : newComment
        }).then(response => {
            console.log(response);
            setReload(!reload);
            resetStates(setNewComment);
        }).catch(error => {
            console.log(error);
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
            </Form.Group>
            <Button onClick={() => handleCommentSubmit()}>댓글 작성</Button>
        </Form>
        <ListGroup className="mt-4">
            {comments.map((comment,index) => (
                <ListGroup.Item key={comment.commentId}>
                    {index+1} . {comment.commenter} - {comment.comment}
                </ListGroup.Item>
            ))}
        </ListGroup>
        </>
    );
};

export default Comments;