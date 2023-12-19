import {Table} from "react-bootstrap";

const Board = () => {

    return (
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
                <tr>
                    <td>1</td>
                    <td>The starting</td>
                    <td>John Doe</td>
                    <td>2023-01-15</td>
                    <td>0</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>Sample Post 2</td>
                    <td>Jane Smith</td>
                    <td>2023-01-20</td>
                    <td>0</td>
                </tr>
            </tbody>
        </Table>
    )
}

export default Board;