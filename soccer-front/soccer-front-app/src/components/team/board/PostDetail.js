import {useEffect, useRef, useState} from "react";
import {Container, Row, Col, Card, Button} from "react-bootstrap";
import {axiosInstance, formatDateTime} from "../../../service/ApiService";
import Comments from "./Comments";
import {useNavigate, useParams} from "react-router-dom";


const PostDetail = () => {
    const {teamCode, postId} = useParams();
    const [postDetail, setPostDetail] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        renderPostDetail();
    }, []);

    const renderPostDetail = () => {
        axiosInstance.get(`/${teamCode}/posts/${postId}`
        ).then(response => {
                console.log(response);
                setPostDetail(response.data.result);
        }).catch(error => {
            console.log(error);
        });
    }

    const handleLike = () => {
        axiosInstance.post(`/${teamCode}/posts/${postId}/heart`, {
            notHeart: false
        }).then(response => {
                console.log(response);
                setPostDetail(prevDetails => ({
                    ...prevDetails,
                    heartCount : prevDetails.heartCount+1
                }))
        }).catch(error => {
            console.log(error);
            alert(error.response.data.result);
        })
    }

    const handleDeletePost = () => {
        axiosInstance.delete(`/${teamCode}/posts/${postId}`
        ).then(response => {
            console.log(response);
            navigate(`../board`);
            alert("삭제되었습니다.");
        }).catch(error => {
            console.log(error);
            alert(error.response.data.result);
        })
    }

    const handleDislike = () => {
        axiosInstance.post(`/${teamCode}/posts/${postId}/heart`, {
            notHeart: true
        }).then(response => {
            console.log(response);
            setPostDetail(prevDetails => ({
                ...prevDetails,
                notHeartCount : prevDetails.notHeartCount+1
            }))
        }).catch(error => {
            console.log(error);
            alert(error.response.data.result);
        })
    };

    return (
      <Container>
          <Row className="justify-content-md-center mt-5">
              <Col md={8}>
                  <Card>
                      <Card.Header as="h3">{postDetail.title}</Card.Header>
                      <Card.Header>
                          작성일: {formatDateTime(postDetail.createdAt)}
                          {formatDateTime(postDetail.createdAt) !== formatDateTime(postDetail.modifiedAt) && (
                          " | 수정일: " + formatDateTime(postDetail.modifiedAt)
                          )}
                      </Card.Header>
                      <Card.Body>
                      <Card.Title style={{ textAlign: 'right' }}>작성자 : {postDetail.writer}</Card.Title>
                          <div style={{ textAlign: 'right' }}>조회수: {postDetail.viewCount} 좋아요: {postDetail.heartCount}</div>
                          <Card.Text>
                              {postDetail.content}
                          </Card.Text>
                          <div style={{display: 'flex', justifyContent: 'center'}}>
                          <Button onClick={handleLike}
                              variant="outline-primary"
                          >
                              좋아요: {postDetail.heartCount}
                          </Button>
                              <Button onClick={handleDislike}
                                      variant="outline-secondary"
                              >
                                  싫어요: {postDetail.notHeartCount}
                              </Button>
                          </div>
                      </Card.Body>
                  </Card>
                  <div style={{display:'flex', justifyContent: "right"}}>
                      <Button onClick={() => navigate('./update')} variant="outline-success">수정</Button>
                      <Button onClick={handleDeletePost} variant="outline-danger">삭제</Button>
                      <Button onClick={() => navigate("../board")} variant="outline-dark">목록으로</Button>
                  </div>
                  <Comments />
              </Col>
          </Row>
      </Container>
    );
};

export default PostDetail;