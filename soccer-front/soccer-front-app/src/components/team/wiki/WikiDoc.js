import {useEffect, useState} from "react";
import {Button, Container} from "react-bootstrap";
import {axiosInstance, formatDateTime} from "../../../service/ApiService";
import {useNavigate, useParams} from "react-router-dom";


const WikiDoc = () => {
    const {teamCode, wikiDocId, version} = useParams();
    const [wikiDoc, setWikiDoc] = useState(null);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (wikiDocId && version) {
            showDoc(wikiDocId, version)
        } else {
            showFirstDoc();
        }
    }, [teamCode]);

    const showFirstDoc = () => {
        axiosInstance.get(`/${teamCode}/wiki`)
            .then(response => {
                console.log(response);
                setWikiDoc(response.data.result);
            }).catch(error => {
            console.log(error);
            setError(error.response.data.result);
        })
    }

    const showDoc = (wikiDocId, version) => {
        axiosInstance.get(`/${teamCode}/wiki/${wikiDocId}`, {
            params : {
                version : version
            }}).then(response => {
            console.log(response);
            setWikiDoc(response.data.result);
        }).catch(error => {
            console.log(error);
        })
    }

    return (
        <>
            {wikiDoc && <><h1>{wikiDoc.title}</h1>
                <p>{wikiDoc.body}</p></>}
            {error && <p>{error}</p>}
            {!wikiDocId && <Button onClick={() => navigate('../wiki/create', {state: {wikiDoc}})}>편집</Button>}
            {wikiDoc ? <Button onClick={() => navigate('../wiki/history', {state: {wikiDoc}})}>역사</Button>
                : <Button onClick={() => navigate('./create', {state: {wikiDoc}})}>새 문서 만들기</Button>}
        </>
    )
}

export default WikiDoc;