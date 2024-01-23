import {Button, Form, FormControl, Tab, Table, Tabs} from "react-bootstrap";
import {useEffect, useState} from "react";
import axios from "axios";
import {axiosInstance, formatDateTime} from "../../../service/ApiService";
import {useNavigate, useParams} from "react-router-dom";
import {handleInputChange, handleKeyDown} from "../../../service/CommonService";
import "../../../css/Board.css";

const Board = (props) => {
    const {teamCode} = useParams();
    const [posts, setPosts] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPage, setTotalPage] = useState(0);
    const [searchCondition, setSearchCondition] = useState({
        type: 'title',
        keyword: ''
    })
    const [activeTab, setActiveTab] = useState('allPosts');
    const navigate = useNavigate();

    useEffect(() => {
        handleRenderPosts();
    }, [currentPage, activeTab]);


    const handleRenderPosts = () => {
        if (activeTab === 'allPosts') {
            renderPostList(searchCondition);
        }

        if (activeTab === 'bestPosts') {
            renderBestPostList(searchCondition)
        }
    }

    const renderPostList = (condition = {}) => {
        axiosInstance.get(`/${teamCode}/posts`, {
            params : {
                ...condition,
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
    }

    const renderBestPostList = (condition = {}) => {
        axiosInstance.get(`/${teamCode}/posts/best`, {
            params : {
                ...condition,
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
    }

    const handlePostForm = () => {
        navigate('./create');
    };

    const handleShowPost = (postId) => {
        navigate(`./${postId}`)
    };

    const handleSearch = () => {
        setCurrentPage(0);
        handleRenderPosts();
        console.log(searchCondition);
    }

    const highlightKeyword = (text, keyword) => {
        const regex = new RegExp(keyword, 'gi');
        return text.replace(regex, (match) => `<span class="highlight">${match}</span>`)
    };


    return (
        <>
            <Button onClick={handlePostForm}>글 작성</Button>
            <Tabs activeKey={activeTab} onSelect={(k) => setActiveTab(k)} className="mb-3">
                <Tab eventKey="allPosts" title="전체목록">
                    <Table className="mt-4" hover>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>제목</th>
                            <th>글쓴이</th>
                            <th>작성일</th>
                            <th>조회수</th>
                            <th>좋아요</th>
                        </tr>
                        </thead>
                        <tbody>
                        {posts.map((post, index) => (
                            <tr key={post.postId} onClick={() => handleShowPost(post.postId)} style={{cursor: "pointer"}}>
                                <td>{post.postId}</td>
                                <td dangerouslySetInnerHTML={{__html: highlightKeyword(post.title, searchCondition.keyword)}}></td>
                                <td>{post.writer}</td>
                                <td>{formatDateTime(post.createdAt)}</td>
                                <td>{post.viewCount}</td>
                                <td>{post.heartCount}</td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                    <div>
                        <Button onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 0}>이전</Button>
                        {Array.from({length: totalPage}, (_, index) => (
                            <Button variant="light" key={index}
                                    onClick={() => setCurrentPage(index)}
                                    disabled={index===currentPage}>{index + 1}</Button>
                        ))}
                        <Button onClick={() => setCurrentPage(currentPage + 1)} disabled={posts.length < 10}>다음</Button>
                    </div>
                </Tab>
                <Tab eventKey="bestPosts" title="추천글">
                    <Table className="mt-4" hover>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>제목</th>
                            <th>글쓴이</th>
                            <th>작성일</th>
                            <th>조회수</th>
                            <th>좋아요</th>
                        </tr>
                        </thead>
                        <tbody>
                        {posts.map((post, index) => (
                            <tr key={post.postId} onClick={() => handleShowPost(post.postId)} style={{cursor: "pointer"}}>
                                <td>{post.postId}</td>
                                <td dangerouslySetInnerHTML={{__html: highlightKeyword(post.title, searchCondition.keyword)}}></td>
                                <td>{post.writer}</td>
                                <td>{formatDateTime(post.createdAt)}</td>
                                <td>{post.viewCount}</td>
                                <td>{post.heartCount}</td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                    <div>
                        <Button onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 0}>이전</Button>
                        {Array.from({length: totalPage}, (_, index) => (
                            <Button variant="light" key={index}
                                    onClick={() => setCurrentPage(index)}
                                    disabled={index===currentPage}>{index + 1}</Button>
                        ))}
                        <Button onClick={() => setCurrentPage(currentPage + 1)} disabled={posts.length < 10}>다음</Button>
                    </div>
                </Tab>
            </Tabs>
            <div style={{ display: 'flex', marginTop: '10px' }}>
                <Form.Select className="mr-2" name="type" style={{ width: 'auto', height: '38px' }}
                             onChange={(e) => handleInputChange(e, searchCondition, setSearchCondition)}>
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                    <option value="writer">작성자</option>
                </Form.Select>
                <FormControl className="mr-2" name="keyword" type="text" placeholder="검색할 단어를 입력하세요."
                             onChange={e => handleInputChange(e, searchCondition, setSearchCondition)}
                             onKeyDown={e => handleKeyDown(e, handleSearch)} style={{ height: '38px', marginLeft: '1vw' }} />
                <Button variant="outline-success"
                        onClick={handleSearch} style={{height:'38px', marginLeft: '1vw'}}>검색</Button>
            </div>
        </>
    );
}

export default Board;