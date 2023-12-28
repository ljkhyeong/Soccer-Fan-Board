import {useEffect, useRef, useState} from "react";
import {Container, Row, Col, Card} from "react-bootstrap";
import {axiosInstance, formatDateTime} from "../../../service/ApiService";
import Comments from "./Comments";


const PostDetail = (props) => {

    const [postDetail, setPostDetail] = useState({});

    useEffect(() => {
        renderPostDetail();
    }, []);

    const renderPostDetail = () => {
        axiosInstance.get(`/posts/${props.postId}`)
            .then(response => {
                console.log(response)
                setPostDetail(response.data.result)
            }).catch(error => {
            console.log(error);
        });
    }

    return (
      <Container>
          <Row className="justify-content-md-center mt-5">
              <Col md={8}>
                  <Card>
                      <Card.Header as="h5">{postDetail.title}</Card.Header>
                      <Card.Body>
                          <Card.Title>작성자 : {postDetail.writer}</Card.Title>
                          <Card.Text>
                              {postDetail.content}
                          </Card.Text>
                      </Card.Body>
                      <Card.Footer>
                          작성일: {formatDateTime(postDetail.createdAt)}
                      </Card.Footer>
                  </Card>
                  <Comments postId={props.postId}/>
              </Col>
          </Row>
      </Container>
    );
};

export default PostDetail;