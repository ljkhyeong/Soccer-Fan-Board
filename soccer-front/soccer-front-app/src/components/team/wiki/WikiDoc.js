import { useEffect, useState } from 'react';
import { Badge, Button, Col, Container, Row, Card } from 'react-bootstrap';
import { axiosInstance, formatDateTime } from '../../../service/ApiService';
import { useNavigate, useParams } from 'react-router-dom';
import MarkdownIt from 'markdown-it';
import '../../../css/WikiDoc.css';

const WikiDoc = () => {
    const { teamCode, wikiDocId, version } = useParams();
    const [wikiDoc, setWikiDoc] = useState(null);
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const mdParser = new MarkdownIt();

    useEffect(() => {
        if (wikiDocId && version) {
            showDoc(wikiDocId, version);
        } else {
            showFirstDoc();
        }
    }, [teamCode]);

    const showFirstDoc = () => {
        axiosInstance
            .get(`/${teamCode}/wiki`)
            .then((response) => {
                console.log(response);
                setWikiDoc(response.data.result);
            })
            .catch((error) => {
                console.log(error);
                setError(error.response.data.result);
            });
    };

    const showDoc = (wikiDocId, version) => {
        axiosInstance
            .get(`/${teamCode}/wiki/${wikiDocId}`, {
                params: {
                    version: version,
                },
            })
            .then((response) => {
                console.log(response);
                setWikiDoc(response.data.result);
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return (
        <Row className="justify-content-md-center">
            <Col md={10}>
                <Card>
                    <Card.Body>
                        {wikiDoc && (
                            <>
                                <div className="wiki-doc-header">
                                    <Card.Title className="wiki-doc-title">
                                        {wikiDoc.title}
                                    </Card.Title>
                                    <div className="wiki-doc-badge-group">
                                        <Badge bg="secondary">작성자: {wikiDoc.writer}</Badge>{' '}
                                        <Badge bg="info">
                                            생성일: {formatDateTime(wikiDoc.createdAt)}
                                        </Badge>
                                    </div>
                                </div>
                                <Card.Text
                                    dangerouslySetInnerHTML={{
                                        __html: mdParser.render(wikiDoc.body),
                                    }}
                                />
                            </>
                        )}
                        <div className="wiki-doc-buttons">
                            {wikiDoc ? (
                                <>
                                    <Button
                                        onClick={() =>
                                            navigate('../wiki/create', { state: { wikiDoc } })
                                        }
                                    >
                                        편집
                                    </Button>
                                    <Button
                                        onClick={() =>
                                            navigate('../wiki/history', { state: { wikiDoc } })
                                        }
                                    >
                                        역사
                                    </Button>
                                </>
                            ) : (
                                <Button
                                    onClick={() => navigate('./create', { state: { wikiDoc } })}
                                >
                                    새 문서 만들기
                                </Button>
                            )}
                        </div>
                        {error && <Card.Text>{error}</Card.Text>}
                    </Card.Body>
                </Card>
            </Col>
        </Row>
    );
};

export default WikiDoc;
