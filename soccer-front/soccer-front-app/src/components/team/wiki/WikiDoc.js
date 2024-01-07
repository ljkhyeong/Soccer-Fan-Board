import {useEffect, useState} from "react";
import {Button, Container} from "react-bootstrap";
import {axiosInstance, formatDateTime} from "../../../service/ApiService";
import {useNavigate, useParams} from "react-router-dom";


const WikiDoc = () => {
    const {teamName} = useParams();
    const [wikiDoc, setWikiDoc] = useState(null);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        axiosInstance.get(`/${teamName}/wiki`)
            .then(response => {
                console.log(response);
                setWikiDoc(response.data.result);
            }).catch(error => {
                console.log(error);
                setError(error.response.data.result);
        })
    }, [teamName]);

    return (
        <>
            {wikiDoc && <><h1>{wikiDoc.title}</h1>
                <p>{wikiDoc.body}</p></>}
            {error && <p>{error}</p>}
            <Button onClick={() => navigate('./create', {state: { body : wikiDoc?.body}})}>새 문서 만들기</Button>
        </>
    )
}

export default WikiDoc;