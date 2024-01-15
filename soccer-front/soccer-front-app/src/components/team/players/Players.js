import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Button, Table} from "react-bootstrap";
import {axiosInstance, formatDateTime} from "../../../service/ApiService";

const Players = () => {
    const {teamCode} = useParams();
    const [players, setPlayers] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPage, setTotalPage] = useState(0);

    useEffect(() => {
        axiosInstance.get(`/${teamCode}/players`, {
            params: {
                page: currentPage,
                size: 10
            }
        })
            .then(response => {
                setPlayers(response.data.result.content);
                setTotalPage(response.data.result.totalPages);
                console.log(response);
            }).catch(error => {
                console.log(error);
        })
    }, [currentPage]);


    return (
        <>
            <Table className="mt-4" hover>
                <thead>
                <tr>
                    <th>#</th>
                    <th>#</th>
                    <th>이름</th>
                    <th>포지션</th>
                    <th>국적</th>
                </tr>
                </thead>
                <tbody>
                {players.map((player, index) => (
                    <tr key={player.playerId} onClick={() => window.location.href = player.infoLink}
                        style={{cursor: "pointer"}}>
                        <td>{index + 1}</td>
                        <td><img src={player.imagePath}/></td>
                        <td>{player.name}</td>
                        <td>{player.position}</td>
                        <td>{player.country}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
            <div>
                <Button onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 0}>이전</Button>
                {Array.from({length: totalPage}, (_,index) => (
                    <Button variant="light" key = {index} onClick={() => setCurrentPage(index)}> {index+1} </Button>
                ))}
                <Button onClick={() => setCurrentPage(currentPage + 1)} disabled={players.length < 10}>다음</Button>
            </div>
        </>
    );
}

export default Players;