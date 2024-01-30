import { Button, ListGroup } from 'react-bootstrap';
import { useEffect, useState } from 'react';
import { axiosInstance, formatDateTime } from '../../../service/ApiService';
import { useLocation, useNavigate, useParams } from 'react-router-dom';

const DocHistory = () => {
    const { teamCode } = useParams();
    const location = useLocation();
    const wikiDoc = location.state?.wikiDoc;
    const wikiDocId = wikiDoc.wikiDocId;
    const [history, setHistory] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axiosInstance
            .get(`/${teamCode}/wiki/${wikiDocId}/list`)
            .then((response) => {
                console.log(response);
                setHistory(response.data.result.content);
            })
            .catch((error) => {
                console.log(error);
            });
    }, []);

    return (
        <>
            <h1>{wikiDoc.title}</h1>
            <ListGroup>
                {history.map((doc, index) => (
                    <ListGroup.Item key={doc.docVersionId}>
                        {index + 1} - V{doc.version} - {formatDateTime(doc.createdAt)} -{' '}
                        {doc.writer}
                        <Button onClick={() => navigate(`../wiki/${wikiDocId}/${doc.version}`)}>
                            보기
                        </Button>
                    </ListGroup.Item>
                ))}
            </ListGroup>
        </>
    );
};

export default DocHistory;
