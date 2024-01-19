import {useEffect, useRef, useState} from "react";
import {Container, Row, Col, Card, Button} from "react-bootstrap";
import {axiosInstance, formatDateTime} from "../../../service/ApiService";
import Comments from "./Comments";
import {useParams} from "react-router-dom";


const PostDetail = () => {
    const {teamCode, postId} = useParams();
    const [postDetail, setPostDetail] = useState({});

    useEffect(() => {
        renderPostDetail();
    }, []);

    const renderPostDetail = () => {
        axiosInstance.get(`${teamCode}/posts/${postId}`
        ).then(response => {
                console.log(response);
                setPostDetail(response.data.result);
        }).catch(error => {
            console.log(error);
        });
    }

    const handleLike = () => {
        axiosInstance.post(`/${teamCode}/posts/${postId}/heart`
        ).then(response => {
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

    return (
      <Container>
          <Row className="justify-content-md-center mt-5">
              <Col md={8}>
                  <Card>
                      <Card.Header as="h3">{postDetail.title}</Card.Header>
                      <Card.Header>
                          작성일: {formatDateTime(postDetail.createdAt)}
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
                          </div>
                      </Card.Body>

                  </Card>
                  <Comments />
              </Col>
          </Row>
      </Container>
    );
};

export default PostDetail;