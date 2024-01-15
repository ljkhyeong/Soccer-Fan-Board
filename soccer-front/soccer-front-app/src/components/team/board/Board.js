import {Button, Table} from "react-bootstrap";
import {useEffect, useState} from "react";
import axios from "axios";
import {axiosInstance, formatDateTime} from "../../../service/ApiService";
import {useNavigate, useParams} from "react-router-dom";

const Board = (props) => {
    const {teamCode} = useParams();
    const [posts, setPosts] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPage, setTotalPage] = useState(0);
    const navigate = useNavigate();

    useEffect(() => {
        axiosInstance.get(`/${teamCode}/posts`, {
            params : {
                page: currentPage,
                size: 10
            }
        })
            .then(response => {
                setPosts(response.data.result.content);
                setTotalPage(response.data.result.totalPages);
                console.log(response);
            })
            .catch(error => {
                console.log(error);
            })
    }, [currentPage]);

    const handlePostForm = () => {
        navigate('./create');
    };

    const handleShowPost = (postId) => {
        navigate(`./${postId}`)
    };


    return (
        <>
            <Button onClick={handlePostForm}>글 작성</Button>
            <Table className="mt-4" hover>
                <thead>
                <tr>
                    <th>#</th>
                    <th>제목</th>
                    <th>글쓴이</th>
                    <th>작성일</th>
                    <th>조회수</th>
                </tr>
                </thead>
                <tbody>
                {posts.map((post, index) => (
                    <tr key={post.postId} onClick={() => handleShowPost(post.postId)} style={{cursor: "pointer"}}>
                        <td>{index + 1}</td>
                        <td>{post.title}</td>
                        <td>{post.writer}</td>
                        <td>{formatDateTime(post.createdAt)}</td>
                        <td>{post.viewCount}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
            <div>
                <Button onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 0}>이전</Button>
                {Array.from({length:totalPage},(_,index) => (
                    <Button variant="light" key={index} onClick={() => setCurrentPage(index)}>{index+1}</Button>
                ))}
                <Button onClick={() => setCurrentPage(currentPage + 1)} disabled={posts.length < 10}>다음</Button>
            </div>
        </>
    );
}

export default Board;